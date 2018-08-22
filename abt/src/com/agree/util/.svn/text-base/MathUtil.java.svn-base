package com.agree.util;

import java.util.Map;

public class MathUtil {
	
	/**
	 * 格式化利率,将map中某个字段的格式从X.XXXXXXXXX更改成XX.XXX% (例:0.01252352432格式化成1.25%)
	 * @param map 传入map
	 * @param str 要格式化的字段
	 * @param num 小数点后保留几位
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void formatRate(Map map, String str, int num){
		num = num + 1;
		if(String.valueOf(Float.parseFloat((map.get(str).toString()))*100).length()-String.valueOf(Float.parseFloat((map.get(str).toString()))*100).indexOf(".")>=num){
			map.put(str, String.valueOf(Float.parseFloat((map.get(str).toString()))*100).substring(0,String.valueOf(Float.parseFloat((map.get(str).toString()))*100).indexOf(".")+num) + "%");
		}else {
			map.put(str, String.valueOf(Float.parseFloat((map.get(str).toString()))*100) + "%");
		}
	}
	
	public static String formatRate(String str, int num){
		num = num + 1;
		if(String.valueOf(Float.parseFloat(str)*100).length()-String.valueOf(Float.parseFloat(str)*100).indexOf(".")>=num){
			return String.valueOf(Float.parseFloat(str)*100).substring(0,String.valueOf(Float.parseFloat(str)*100).indexOf(".")+num) + "%";
		}else {
			return String.valueOf(Float.parseFloat(str)*100) + "%";
		}
	}
}
