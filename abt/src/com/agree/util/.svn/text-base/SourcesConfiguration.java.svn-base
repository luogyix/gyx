package com.agree.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: SourcesConfiguration 
 * @Description:
 * @company agree   
 * @author lilei
 * @date 2013-9-21 下午03:52:56 
 *  
 */ 
public class SourcesConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(SourcesConfiguration.class);
	public Properties propertie;
    private String path;
    private FileInputStream inputFile;
    private FileOutputStream outputFile;
    
    public SourcesConfiguration(){
        propertie = new Properties();
    }
    
    public SourcesConfiguration(String filePath){
    	path = filePath;
        propertie = new Properties();
        try {
            inputFile = new FileInputStream(path);
            propertie.load(inputFile);
            inputFile.close();
        } catch (FileNotFoundException ex){
        	logger.error("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在",ex);
        } catch (IOException ex){
        	logger.error("装载文件--->失败!",ex);
        }
    }
    
    public String getValue(String key){
        if(propertie.containsKey(key)){
            String value = propertie.getProperty(key);
            return value;
        }else{
            return "";
        }
    }
    
    public void clear(){
        propertie.clear();
    }
    
    public void setValue(String key, String value){
    	try {
			key = new String(key.getBytes("ISO-8859-1"), "GBK");// 处理中文乱码  
			value = new String(value.getBytes("ISO-8859-1"), "GBK");// 处理中文乱码  
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} 
        propertie.setProperty(key, value);
    }
    
    public void delValue(String key){
        propertie.remove(key);
    }
    
    public void saveFile(String description){
    	try {
    		outputFile = new FileOutputStream(path);
    		propertie.store(outputFile, description);
    		outputFile.close();
    	} catch (FileNotFoundException e) {
    		logger.error(e.getMessage(), e);
    	} catch (IOException ioe){
    		logger.error(ioe.getMessage(), ioe);
    	}
    }
    
    public void saveAsFile(String fileName, String description){
        try {
            outputFile = new FileOutputStream(fileName);
            propertie.store(outputFile, description);
            outputFile.close();
        } catch (FileNotFoundException e) {
        	logger.error(e.getMessage(), e);
        } catch (IOException ioe){
        	logger.error(ioe.getMessage(), ioe);
        }
    }
    
    public void reloadFile(String key,String description,String ope){
//    	SourcesConfiguration sca = new SourcesConfiguration("WebRoot/WEB-INF/classes/sources_pushlet.properties");
//    	SourcesConfiguration scn = new SourcesConfiguration("WebRoot/WEB-INF/classes/sources.properties");
    	
    	SourcesConfiguration sca = new SourcesConfiguration("WEB-INF/classes/sources_pushlet.properties");
    	SourcesConfiguration scn = new SourcesConfiguration("WEB-INF/classes/sources.properties");
    	
    	
    	
    	if(ope.equals("add")){
    		if(!scn.propertie.containsKey(key)){
        		scn.setValue(key, sca.getValue(key));
        	}
    	}else if(ope.equals("del")){
    		if(scn.propertie.containsKey(key)){
        		scn.propertie.remove(key);
        	}
    	}
    	scn.saveFile(description);

		try {
			FileReader in = new FileReader("WEB-INF/classes/sources.properties");
//			FileReader in = new FileReader("WebRoot/WEB-INF/classes/sources.properties");
			new Properties().load(in);
			in.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
    }
    
//    public static void main(String[] args){
//    	SourcesConfiguration sc = new SourcesConfiguration();
//    	sc.reloadFile("source1","aaa","add");
//    	
//    }
    
}
