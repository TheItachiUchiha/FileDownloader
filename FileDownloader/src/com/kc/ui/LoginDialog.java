package com.kc.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.kc.service.FTPConnect;
import com.kc.service.HttpConnection;
import com.kc.service.Login;

public class LoginDialog extends JDialog {
	 
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
 
    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        //
        JPanel panel = new JPanel(new GridBagLayout());
        ftpConnect = new FTPConnect();
        httpConnection = new HttpConnection();
        
        
        
        
        GridBagConstraints cs = new GridBagConstraints();
        
        
        
        
        
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);
 
        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);
 
        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);
 
        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        
        
        rbStdHttp = new JRadioButton("Standard http");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(rbStdHttp, cs);
        
        rbFtp = new JRadioButton("FTP");
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(rbFtp, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        group = new ButtonGroup();
        group.add(rbStdHttp);
        group.add(rbFtp);
        
 
        btnLogin = new JButton("Login");
        
        
        btnLogin.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	if(rbFtp.isSelected())
            	{
            		System.out.println("ftp");
            	}
            	else
            	{
            		System.out.println("http");
            	}
                if (Login.authenticate(getUsername(), getPassword())) {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Hi " + getUsername() + "! You have successfully logged in.",
                            "Login",
                            JOptionPane.INFORMATION_MESSAGE);
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Invalid username or password",
                            "Login",
                            JOptionPane.ERROR_MESSAGE);
                    // reset username and password
                    tfUsername.setText("");
                    pfPassword.setText("");
                    succeeded = false;
 
                }
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
}