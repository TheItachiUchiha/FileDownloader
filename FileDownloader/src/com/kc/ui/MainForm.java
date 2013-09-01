package com.kc.ui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.kc.constants.CommonConstants;
import com.kc.service.FTPConnect;
import com.kc.service.HttpConnection;
import com.kc.service.ReadSplitTextFiles;
import com.kc.service.UnZip;
import com.kc.utils.FileUtils;
import com.kc.vo.AuthenticationVO;

@SuppressWarnings("serial")
public class MainForm extends JFrame {

	static MainForm mainForm;
	 ReadSplitTextFiles readSplitTextFiles;
	 FTPConnect ftpConnect;
	 HttpConnection httpConnection;
	 UnZip unZip;
	
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
		ftpConnect = new FTPConnect();
		httpConnection = new HttpConnection();
		unZip = new UnZip();
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
        
        final DefaultListModel resultList = new DefaultListModel();
        final JList<String> jList = new JList<String>(resultList);

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
		
		getList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				{
					File file = new File("../"+ CommonConstants.TEMP_TXT_FILE);
					if(!file.exists())
					{
						JOptionPane.showMessageDialog( mainForm, "No File Found. Please Save Settings");
					}
					else
					{
	    				List<String> list = readSplitTextFiles.readTextFile(CommonConstants.TEMP_TXT_FILE);
	    				resultList.clear();
	    				for(int i=list.size()-1;i >=0; i--)
	    				{
	    					resultList.add(list.size()-(i+1), list.get(i));
	    				}
					}
    			}
				
			}
		});
		
		
		JButton getData = new JButton(dataImage);
		getData.setBorder(new EmptyBorder(3, 0, 3, 0));
		getData.setBounds(50, 60, 100, 30);
		vertical.add(getData);
		
		getData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(null == jList.getSelectedValue() || "".equals(jList.getSelectedValue()))
				{
					JOptionPane.showMessageDialog( mainForm, "Please Select An Item from the List");
				}
				else{
					String selectedFileName = jList.getSelectedValue();
					if(FileUtils.readDefault().equals(CommonConstants.HTTP))
					{
						AuthenticationVO authenticationVO = new AuthenticationVO();
						authenticationVO = FileUtils.readHTTPDetails();
						authenticationVO.setUrl(authenticationVO.getUrl().substring(0,authenticationVO.getUrl().lastIndexOf('/')+1)+selectedFileName);
						FileUtils.saveHTTPDetails(authenticationVO);
						httpConnection.run();
						File file = new File("../"+selectedFileName);
						file.setReadable(true);
						unZip.unZipIt(selectedFileName, "../"+selectedFileName.substring(0, selectedFileName.lastIndexOf('.')));
					}
					else if(FileUtils.readDefault().equals(CommonConstants.FTP)){
						ftpConnect.startFTP(selectedFileName);
						File file = new File("../"+selectedFileName);
						file.setReadable(true);
						unZip.unZipIt(selectedFileName, "../"+selectedFileName.substring(0, selectedFileName.lastIndexOf('.')));
					}
					JOptionPane.showMessageDialog( mainForm, "Downloading and Unzipping Finished");
				}
				
		
			}
		});
		
	
		add(vertical, BorderLayout.WEST);
		add(jList, BorderLayout.CENTER);

		setTitle("File Downloader");
		setSize(700, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
