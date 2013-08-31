package com.kc.ui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.kc.service.ReadSplitTextFiles;

@SuppressWarnings("serial")
public class MainForm extends JFrame {

	static MainForm mainForm;
	ReadSplitTextFiles readSplitTextFiles;
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainForm = new MainForm();
				mainForm.setVisible(true);
			}
		});
	}

	public MainForm() {

		readSplitTextFiles = new ReadSplitTextFiles();
		loadUI();
	}

	private void loadUI() {

		JPanel panel = new JPanel();
		getContentPane().add(panel);

		JToolBar vertical = new JToolBar(JToolBar.VERTICAL);
        vertical.setFloatable(false);
        vertical.setMargin(new Insets(10, 5, 5, 5));
		
        ImageIcon settingImage = new ImageIcon("images/settings.png");
        ImageIcon listImage = new ImageIcon("images/file.png");
        ImageIcon dataImage = new ImageIcon("images/data.png");

		final JButton settings = new JButton(settingImage);
		settings.setBorder(new EmptyBorder(3, 0, 3, 0));
		settings.setBounds(50, 60, 100, 30);
		vertical.add(settings);
		
		settings.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 LoginDialog loginDlg = new LoginDialog(mainForm);
                 loginDlg.setVisible(true);
                 // if logon successfully
                 if(loginDlg.isSucceeded()){
                     settings.setText("Hi " + loginDlg.getUsername() + "!");
                 }
			}
		});
		
		
		JButton getList = new JButton(listImage);
		getList.setBorder(new EmptyBorder(3, 0, 3, 0));
		getList.setBounds(50, 60, 100, 30);
		vertical.add(getList);
		
		JButton getData = new JButton(dataImage);
		getData.setBorder(new EmptyBorder(3, 0, 3, 0));
		getData.setBounds(50, 60, 100, 30);
		vertical.add(getData);
		
		
		List<String> list = readSplitTextFiles.readTextFile();
		
		JList<String> jList = new JList<String>(list.toArray(new String[list.size()]));
		
		
		add(vertical, BorderLayout.WEST);
		add(jList, BorderLayout.CENTER);

		setTitle("File Downloader");
		setSize(700, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
