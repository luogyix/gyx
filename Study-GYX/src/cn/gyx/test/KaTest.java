package cn.gyx.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class KaTest {
	
	public static void main(String[] args) throws IOException {
		//InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("C:\\Users\\root\\Desktop")));
		//BufferedReader bufferedReader = new BufferedReader(isr);
		//bufferedReader.
		//isr.close();
		//bufferedReader.close();
		File file = new File("C:\\Users\\root\\Desktop\\AWEB×Ü½á");
		File[] listFiles = file.listFiles();
		System.out.println(System.getProperty("line.separator"));
	}

}
