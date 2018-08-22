package com.agree.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 密码加密类
 * @ClassName: DES 
 * @company agree   
 * @author
 * @date 2011-8-15 下午04:02:06 
 *
 */
public class DES {
private static final Logger logger = LoggerFactory.getLogger(DES.class);
	 private static final byte[] enkeystore = {
         0x08, 0x02, 0x0b, 0x0c, 0x01, 0x0a, 0x00, 0x0d, 0x07, 0x03, 0x0e,
         0x05, 0x0f, 0x06, 0x04, 0x09
     };
	 
	 private static final byte[] dekeystore = {
         0x06, 0x04, 0x01, 0x09, 0x0e, 0x0b, 0x0d, 0x08, 0x00, 0x0f, 0x05,
         0x02, 0x03, 0x07, 0x0a, 0x0c
     }; 
	 /**
	  * 字节数组加密
	  * @Title: encode 
	  * @param @param data
	  * @param @return    参数 
	  * @return byte[]    返回类型 
	  * @throws
	  */
	 public static byte[] encode(byte[] data) {
		 byte[] result = new byte[data.length];

	     for (int i = 0; i < data.length; i++) {
	         result[i] += (enkeystore[(data[i] >>> 4) & 0x0F] << 4);
	         result[i] += (enkeystore[data[i] & 0x0F]);
	     }

	     return result;
	 }
	 /**
	  * 字节数组解密
	  * @Title: decode 
	  * @param @param data
	  * @param @return    参数 
	  * @return byte[]    返回类型 
	  * @throws
	  */
	 public static byte[] decode(byte[] data) {
	     byte[] result = new byte[data.length];
	
	     for (int i = 0; i < data.length; i++) {
	         result[i] += (dekeystore[(data[i] >>> 4) & 0x0F] << 4);
	         result[i] += (dekeystore[data[i] & 0x0F]);
	     }
	
	     return result;
	 }
	
		/** 
		* 加密String明文输入,String密文输出 
		* @Title：getEncString
		* @param 	strMing String类型字符串，密码明文
		* @return 	String	返回密码密文
		*/ 
	public static String getEncString(String strMing) { 
		byte[] byteenc=null;
		try {
			byteenc = DES.encode(strMing.getBytes("GB2312"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
        return byte2hex(byteenc);
	
	} 
	
	/** 
	* 解密 以String密文输入,String明文输出 
	* @Title：getDesString
	* @param strMi String类型的密文
	* @return String	返回明文
	*/
	public static String getDesString(String strMi) { 
		
		byte[] bytedeenc=null;
		try {
			bytedeenc = hex2byte(strMi.getBytes("GB2312"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		
		byte[] byteMing=DES.decode(bytedeenc);
		
		return new String(byteMing);
        	
	} 
	
	/**
	 * 
	 * @Title: byte2hex 
	 * @param @param b
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String byte2hex(byte[] b) { //一个字节的数，
//		 转成16进制字符串
		String hs = ""; 
		String stmp = ""; 
		for (int n = 0; n < b.length; n++) { 
//		整数转成十六进制表示
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF)); 
			if (stmp.length() == 1) 
				hs = hs + "0" + stmp; 
			else 
				hs = hs + stmp; 
			} 
		
		return hs.toUpperCase(); //转成大写
	} 

	/**
	 * 
	 * @Title: hex2byte 
	 * @param @param b
	 * @param @return    参数 
	 * @return byte[]    返回类型 
	 * @throws
	 */
	public static byte[] hex2byte(byte[] b) { 
		if((b.length%2)!=0) 
			throw new IllegalArgumentException("长度不是偶数"); 
		byte[] b2 = new byte[b.length/2]; 
		for (int n = 0; n < b.length; n+=2) { 
			String item = new String(b,n,2); 
//		 	两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
			b2[n/2] = (byte)Integer.parseInt(item,16); 
		} 

		return b2; 
	} 
	
//	public static void main(String[] args) throws Exception {       
//        
//        String strEnc = DES.getEncString("web");// 加密字符串,返回String的密文       
//        System.out.println(strEnc);       
//      
//        String strDes = DES.getDesString("DD0A0BDD0A0B");// 把String 类型的密文解密        
//        System.out.println(strDes);       
//    }  
		
}
