package cn.gyx.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

public class ReadMethod {
	
	@SuppressWarnings({"resource"})
	@Test
	public void readTest() throws IOException{
		String separator = File.separator;
		String path = "C:"+separator+"Users"+separator+"root"+separator+"Desktop"+separator+"百度经验.txt";
		File file = new File(path);
		File parentFile = file.getParentFile();
		if(!parentFile.exists()){
			parentFile.mkdirs();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"UTF-8"));
		System.out.println(br.markSupported());
		System.out.println(br.readLine());
		br.close();
	}
	
	/**
	 * 获取键盘输入流
	 * @throws IOException
	 */
	@Test
	public void ioTest() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		System.out.println("输入值并回车");
		str = reader.readLine();
		System.out.println(str);
		reader.close();
	}
	
	@Test
	public void fileInputStreamTest() throws IOException{
		String separator = File.separator;
		String path = "C:"+separator+"Users"+separator+"root"+separator+"Desktop"+separator+"影像控件"+separator+"Adobe Flash Player ActiveX_23.0.0.162.exe";
		File file = new File(path);
		if(file.exists()){
			file.mkdirs();
		}
		FileInputStream fis = new FileInputStream(file);
		byte[] bt = new byte[fis.available()];
		for(int read = fis.read(bt);read > 0;read =fis.read(bt) ){
			System.out.println("文件大小："+file.length()/1024+"kb\r\nread返回："+read+"\r\nbt长度："+bt.length);
			bt = new byte[fis.available()];
		}
		
		fis.close();
	}
}
