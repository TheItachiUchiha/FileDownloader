package com.kc.ui;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class MainForm extends JFrame {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainForm mainForm = new MainForm();
				mainForm.setVisible(true);
			}
		});
	}

	public MainForm() {

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

		JButton settings = new JButton(settingImage);
		settings.setBorder(new EmptyBorder(3, 0, 3, 0));
		settings.setBounds(50, 60, 100, 30);
		vertical.add(settings);
		
		JButton getList = new JButton(listImage);
		getList.setBorder(new EmptyBorder(3, 0, 3, 0));
		getList.setBounds(50, 60, 100, 30);
		vertical.add(getList);
		
		JButton getData = new JButton(dataImage);
		getData.setBorder(new EmptyBorder(3, 0, 3, 0));
		getData.setBounds(50, 60, 100, 30);
		vertical.add(getData);
		
		String array[] = new String[5];
		JComboBox<String> comboBox = new JComboBox<String>(array);
		
		
		
		add(vertical, BorderLayout.WEST);
		add(comboBox, BorderLayout.CENTER);

		setTitle("File Downloader");
		setSize(700, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
