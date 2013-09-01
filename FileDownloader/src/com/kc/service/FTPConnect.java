package com.kc.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.kc.constants.CommonConstants;
import com.kc.utils.FileUtils;
import com.kc.vo.AuthenticationVO;
 
public class FTPConnect {
 
	AuthenticationVO authenticationVO;
	
    public static void main(String[] args) {
 
    	FTPConnect ftpConnect = new FTPConnect();
        ftpConnect.startFTP("ADLnames.txt");
 
    }
 
    public boolean startFTP(String fileName){
 
    	authenticationVO = new AuthenticationVO();
    	
        try {
 
        	authenticationVO = FileUtils.readFTPDetails();
            //new ftp client
            FTPClient ftp = new FTPClient();
            
            //try to connect
            ftp.connect(authenticationVO.getIp());
           
            //login to server
            if(!ftp.login(authenticationVO.getUsername(), authenticationVO.getPassword()))
            {
                ftp.logout();
                return false;
            }
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            int reply = ftp.getReplyCode();
            //FTPReply stores a set of constants for FTP reply codes. 
            if (!FTPReply.isPositiveCompletion(reply))
            {
                ftp.disconnect();
                return false;
            }
 
            //enter passive mode
            ftp.enterLocalPassiveMode();
            //get system name
            System.out.println("Remote system is " + ftp.getSystemType());
            //change current directory
            System.out.println("Current directory is " + ftp.printWorkingDirectory());
              
            //get list of filenames
            FTPFile[] ftpFiles = ftp.listFiles();  
            
            if (ftpFiles != null && ftpFiles.length > 0) {
                //loop thru files
                for (FTPFile file : ftpFiles) {
                    if (!file.isFile()) {
                        continue;
                    }
                    if(file.getName().equals(fileName)){
                    	System.out.println("File is " + file.getName());
                        //get output stream
                        OutputStream output;
                        output = new FileOutputStream("../"+fileName);
                        //get the file from the remote system
                        ftp.retrieveFile(file.getName(), output);
                        //close output stream
                        output.close();
                    }
                }
            }
            ftp.logout();
            ftp.disconnect();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
