package com.letvyidao.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.message.BasicNameValuePair;

public class ReadFileUtils {
public static String propertyFile =System.getProperty("user.dir")+"/src/com/letv/dataprovider/fileData/proper.properties";;
public static void main(String [] args){
	ReadFileUtils utils = new ReadFileUtils();
	String profilepath = "D:/workspace/letvinterface/serverconfig.properties";
	System.out.println(utils.getProperty("lepaymail", profilepath));
}
public void setProperty(String pro,String value){
	
	Properties props = null;
	try{
	 File file=new File(propertyFile); 		  
	 if(!file.exists()){
			file.createNewFile();
			System.out.println("create success");
		}
	    FileInputStream fis = new FileInputStream(propertyFile);
		props = new Properties();
		props.load(fis);
		fis.close();
		props.setProperty(pro, value);
		// 以适合使用 load 方法加载到 Properties 表中的格式，   
		// 将此 Properties 表中的属性列表（键和元素对）写入输出流   
		FileOutputStream fos = new FileOutputStream(propertyFile);   
		props.store(fos, "Update  value");  
		fos.flush();
		fos.close();
		} catch (FileNotFoundException e) {   
			e.printStackTrace();   
			System.exit(-1);   
		} catch (IOException e) {          
			System.exit(-1);   
		} 		
}
public String getProperty(String pro,String filePath){
	Properties props = new Properties();
	String rs = "";
	try{
		FileInputStream fios = new FileInputStream(filePath);
		props.load(fios);	
		rs = props.getProperty(pro);
		fios.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return rs;	
}
public String getProperty(String pro){
	Properties props = new Properties();
	String rs = "";
	try{
		FileInputStream fios = new FileInputStream(propertyFile);
		props.load(fios);	
		rs = props.getProperty(pro);
		fios.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return rs;	
}
public Map<String,String> getParameterFromFileAsMap(String fileName){
	String filePath = System.getProperty("user.dir")+"/dataFiles/"+fileName;
	//	String filePath = System.getProperty("user.dir")+"/"+fileName;
	File file = new File(filePath);
	Map<String,String> paramsMap = new HashMap<String,String>();
	BufferedReader breader =null;
	InputStreamReader reader = null;
	try{
		reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
		breader = new BufferedReader(reader);		
		String line = "";
		while((line=breader.readLine())!=null){
		String [] params = line.split("#");
		if(params.length==2){			
			paramsMap.put(params[0].trim(), params[1].trim());
		}else{
			paramsMap.put(params[0].trim(), "");	
		}
	}
		breader.close();
		reader.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	return paramsMap;	
}
public List<BasicNameValuePair> getParameterFromFileAsList(String fileName){
	String filePath = System.getProperty("user.dir")+"/src/com/letv/dataprovider/fileData/"+fileName;
	File file = new File(filePath);
	List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
	BufferedReader breader =null;
	InputStreamReader reader = null;
	try{
		reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
		breader = new BufferedReader(reader);	
		String line = "";
		while((line=breader.readLine())!=null){
		String [] params = line.split("#");
		if(params.length==2){			
			paramsList.add(new BasicNameValuePair(params[0], params[1]));	
		}else{
			paramsList.add(new BasicNameValuePair(params[0], ""));
		}
	}
		breader.close();
		reader.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	return paramsList;	
}
}
