package com.kc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import com.kc.vo.AuthenticationVO;

public class FileUtils {
	
	public static void saveFTPDetails(AuthenticationVO aunthenticationVO) {
		 try {
		        Properties props = new Properties();
		        props.setProperty("ftpServerAddress", aunthenticationVO.getIp());
		        props.setProperty("userId", aunthenticationVO.getUsername());
		        props.setProperty("password", aunthenticationVO.getPassword());
		        File f = new File("../ftpauthentication.properties");
		        OutputStream out = new FileOutputStream( f );
		        props.store(out, "Default Properties Saved");
		    }
		    catch (Exception e ) {
		        e.printStackTrace();
		    }
	}
	
	public static void saveHTTPDetails(AuthenticationVO authenticationVO) {
		 try {
		        Properties props = new Properties();
		        props.setProperty("Address", authenticationVO.getUrl());
		        props.setProperty("httpScriptAddress", authenticationVO.getServerScript());
		        props.setProperty("httpServerAddress", authenticationVO.getServerDefaultUrl());
		        File f = new File("../httpauthentication.properties");
		        OutputStream out = new FileOutputStream( f );
		        props.store(out, "Default Properties Saved");
		    }
		    catch (Exception e ) {
		        e.printStackTrace();
		    }
	}
	
	public static AuthenticationVO readFTPDetails()
	{
		AuthenticationVO aunthenticationVO = new AuthenticationVO();
		try{
			File f = new File("../ftpauthentication.properties");
			InputStream in = new FileInputStream(f);
			Properties p = new Properties();
			p.load(in);
			aunthenticationVO.setIp(p.getProperty("ftpServerAddress"));
			aunthenticationVO.setUsername(p.getProperty("userId"));
			aunthenticationVO.setPassword(p.getProperty("password"));
		}
		catch (Exception e) {
			 e.printStackTrace();
		}
		return aunthenticationVO;
	}
	
	public static AuthenticationVO readHTTPDetails()
	{
		AuthenticationVO aunthenticationVO = new AuthenticationVO();
		try{
			File f = new File("../httpauthentication.properties");
			InputStream in = new FileInputStream(f);
			Properties p = new Properties();
			p.load(in);
			aunthenticationVO.setUrl(p.getProperty("Address"));
			aunthenticationVO.setServerDefaultUrl(p.getProperty("httpServerAddress"));
			aunthenticationVO.setServerScript(p.getProperty("httpScriptAddress"));
		}
		catch (Exception e) {
			 e.printStackTrace();
		}
		return aunthenticationVO;
	}
	
	public static String readDefault()
	{
		String defaultProtocol=null;
		try{
			File f = new File("../default.properties");
			InputStream in = new FileInputStream(f);
			Properties p = new Properties();
			p.load(in);
			return p.getProperty("default");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return defaultProtocol;
	}
	
	public static void saveDefault(String defaultProtocol) {
		 try {
		        Properties props = new Properties();
		        props.setProperty("default", defaultProtocol);
		        File f = new File("../default.properties");
		        OutputStream out = new FileOutputStream( f );
		        props.store(out, "Default Properties Saved");
		    }
		    catch (Exception e ) {
		        e.printStackTrace();
		    }
	}
	
	
	
}
