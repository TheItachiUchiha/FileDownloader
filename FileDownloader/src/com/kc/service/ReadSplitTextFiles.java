package com.kc.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kc.constants.CommonConstants;

public class ReadSplitTextFiles {
	
	static Properties props;
	
	public ReadSplitTextFiles()
	{
		props = new Properties();
	}
	public List<String> readTextFile()
	{
		String line = null;
		List<String> collection = null;
		try{
			props.load(new FileInputStream("properties/" + CommonConstants.PROPERTIES_FILE));
            BufferedReader br = new BufferedReader(new FileReader(props.getProperty("localDirectory").trim()));
			collection = new ArrayList<String>();
			String[] temp;
			
			while ((line = br.readLine()) != null) {
			   //temp = line.split(",");
			   if (line.length() > 0) {
			      collection.add(line);
			   }
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return collection;
	}
	
	
	public static void main(String args[])
	{
		ReadSplitTextFiles  files = new ReadSplitTextFiles();
		files.readTextFile();
	}

}
