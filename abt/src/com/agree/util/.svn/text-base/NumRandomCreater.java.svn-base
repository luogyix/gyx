package com.agree.util;

import java.util.Random;

public class NumRandomCreater {


	public static String findNumFromStr(String str){
		int n = 0;
		for (int i = 0; i < str.length(); i++) {
			String temp = str.substring(i,i+1);
			try {
				Integer.parseInt(temp);
				n++;
			} catch (NumberFormatException e) {
				
			}
		}
		return n==5?str:NumRandomCreater.createNum();
	}
	
	/**
	 * 随机生成5位数字
	 * @return
	 */
	public static String createNum(){
		Random r = new Random();
		//循环5次,每次1-9       r.nextInt(10)
		String num = "";
		for (int i = 0; i < 5; i++) {
			num+=String.valueOf(r.nextInt(10));
		}
		return num;
	}
}
