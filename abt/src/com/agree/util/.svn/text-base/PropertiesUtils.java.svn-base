package com.agree.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class PropertiesUtils {
	/**
	 * 
	 * 得到文件的输入流
	 **/
	private static Properties file = new Properties();
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

	public void getFile(String fileName) {
		InputStream inputStream = getClass().getResourceAsStream(fileName);
		if (inputStream == null) {
			logger.info(fileName + " is  exist!");
		} else {
			try {
				file.load(inputStream);
				inputStream.close();
			} catch (IOException e) {
				logger.error(e.getLocalizedMessage(),e);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						logger.error(e.getLocalizedMessage(),e);
					}
				}
			}
		}
	}

	/**
	 * @param propertyName
	 *            读取 和写入
	 * @return key
	 */
	public String read(String propertyName) {
		return file.getProperty(propertyName);
	}

	public void write(String name, String value) {
		file.setProperty(name, value);
	}

	/**
	 * 关闭文件
	 */
	public void close() {
		OutputStream os = null;
		try {
			os = new FileOutputStream("conf.properties");
			file.store(os, null);
			os.close();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(),e);
			logger.error("conf.properties无法正常关闭");
		}
		finally
		{
			if(os != null)
			{
				try {
					os.close();
				} catch (IOException e) {
					logger.error(e.getLocalizedMessage(),e);
				}
				
			}
		}
	}

	/**
	 * 获取src下的配置文件,遍历返回Map数据
	 *	@param path  文件路径+文件名
	 *	@return 返回Map数据
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> getProperties(String path){
		Map<String, String> map = new HashMap<String, String>();
//		InputStreamReader reader = null;
		try {
			Properties prop = new Properties();
			ClassPathResource cr = new ClassPathResource(path);
			prop.load(cr.getInputStream());
			Set keyValue = prop.keySet();
			for(Iterator it = keyValue.iterator();it.hasNext();){
				String key = (String)it.next();
				String value = prop.getProperty(key);
				map.put(key, value);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return map;
	}
}
