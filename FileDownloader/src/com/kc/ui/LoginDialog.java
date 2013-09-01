package com.kc.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.kc.constants.CommonConstants;
import com.kc.service.FTPConnect;
import com.kc.service.HttpConnection;
import com.kc.service.ReadSplitTextFiles;
import com.kc.utils.FileUtils;
import com.kc.vo.AuthenticationVO;

public class LoginDialog extends JDialog/* implements ActionListener*/{
	 
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JRadioButton rbStdHttp;
    private JRadioButton rbFtp;
    private ButtonGroup group;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;
    private FTPConnect ftpConnect;
    private HttpConnection httpConnection;
    private JLabel lbUrlIp;
    private JTextField urlIP;
    private AuthenticationVO ftpAuthenticationVO;
    private AuthenticationVO httpAuthenticationVO;
   
 
    public LoginDialog(final Frame parent) {
        super(parent, "Login", true);
        //
        JPanel panel = new JPanel(new GridBagLayout());
        ftpConnect = new FTPConnect();
        httpConnection = new HttpConnection();
        ftpAuthenticationVO = new AuthenticationVO();
        httpAuthenticationVO = new AuthenticationVO();
        httpAuthenticationVO = FileUtils.readHTTPDetails();
        ftpAuthenticationVO = FileUtils.readFTPDetails();
       
        
        GridBagConstraints cs = new GridBagConstraints();
        
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        
        rbStdHttp = new JRadioButton("Standard http");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(rbStdHttp, cs);
        
        rbStdHttp.setActionCommand("HTTP");
        //rbStdHttp.addActionListener(this);
        
        rbFtp = new JRadioButton("FTP");
        rbFtp.setSelected(true);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(rbFtp, cs);
        
        rbFtp.setActionCommand("FTP");    
        //rbFtp.addActionListener(this);
        
        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);
 
        tfUsername = new JTextField(20);
        tfUsername.setText(ftpAuthenticationVO.getUsername());
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);
 
        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);
 
        pfPassword = new JPasswordField(20);
        pfPassword.setText(ftpAuthenticationVO.getPassword());
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        
        lbUrlIp = new JLabel("IP");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbUrlIp, cs);
        
        urlIP = new JTextField(30);
        urlIP.setText(ftpAuthenticationVO.getIp());
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 2;
        panel.add(urlIP, cs);
        
        
       
        panel.setBorder(new LineBorder(Color.GRAY));
        
        group = new ButtonGroup();
        group.add(rbStdHttp);
        group.add(rbFtp);
        
 
        btnLogin = new JButton("Save");
        
        btnLogin.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	if(null == tfUsername.getText() || "".equals(tfUsername.getText()) || 
            			null == pfPassword.getText() || "".equals(pfPassword.getText()) ||
            			null == urlIP.getText() || "".equals(urlIP.getText())) 
            	{
            		JOptionPane.showMessageDialog(parent, "Please Fill all Details");
            	}
            	else
            	{
            		if(rbStdHttp.isSelected())
            		{
            			FileUtils.saveDefault(CommonConstants.HTTP); 
            		}
            		else if(rbFtp.isSelected())
            		{
            			ftpAuthenticationVO.setIp(urlIP.getText());
            			ftpAuthenticationVO.setUsername(tfUsername.getText());
            			ftpAuthenticationVO.setPassword(pfPassword.getText());
            			FileUtils.saveFTPDetails(ftpAuthenticationVO);
            			FileUtils.saveDefault(CommonConstants.FTP);          			
            		}
            	}
            	dispose();
            }
        });
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
 
    public String getUsername() {
        return tfUsername.getText().trim();
    }
 
    public String getPassword() {
        return new String(pfPassword.getPassword());
    }
 
    public boolean isSucceeded() {
        return succeeded;
    }
    
    
    /*public void actionPerformed(ActionEvent e) {
        if ("HTTP".equals(e.getActionCommand())) {
        	urlIP.setVisible(false);       	
            lbUrlIp.setVisible(false);   
        }
        if ("FTP".equals(e.getActionCommand())) {
        	tfUsername.setText(ftpAuthenticationVO.getUsername());
        	pfPassword.setText(ftpAuthenticationVO.getPassword());
        	urlIP.setText(ftpAuthenticationVO.getIp()); 
            lbUrlIp.setText("IP Address:");   
            urlIP.setVisible(true);
            lbUrlIp.setVisible(true); 
        }
    }*/
    
}