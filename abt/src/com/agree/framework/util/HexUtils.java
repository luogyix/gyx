/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.agree.framework.util;

/**
 * 
 * 
 * @author Bean
 * @date 2014-7-10 下午2:37:04
 * @version 1.0
 * 
 */
public class HexUtils {

    /**
     * 字节数组转化为十六进制字符串
     * 
     * @param bytesArray
     * @return
     */
    public static String bytesToHexString(byte[] bytesArray) {
        StringBuffer sb = new StringBuffer(bytesArray.length);
        String sTemp;
        for (int i = 0; i < bytesArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytesArray[i]).toUpperCase();
            if (sTemp.length() < 2) {
                sb.append(0);                
            }
            sb.append(sTemp);
            sb.append(" ");
        }
        return sb.toString();
    }
    
    /**
     * 生成int数值的8位十六进制表示的字符串，用于dump日志的行首。
     *
     * @param indicator
     * @return
     */
    public static String generateRowHeader(int indicator) {
        String sTemp = Integer.toHexString(indicator).toUpperCase();
        StringBuffer sb = new StringBuffer();
        final int offset = 8 - sTemp.length();
        if (offset == 0) {
            sb.append(sTemp);
        } else {
            sb.append(fillString("0", offset, sTemp, 0));
        }
        return sb.append("h: ").toString();
    }
    
    /**
     * 字符串补齐
     *
     * @param replacement 填充字符串
     * @param num 填充次数
     * @param str 需要填充的字符串
     * @param direction 填充方向    0：向左，1：向右。默认向左
     * @return
     */
    public static String fillString(String replacement, final int num, String str, int direction) {
        StringBuffer sb = new StringBuffer();
        if (direction == 1) {
            sb.append(str);
            for (int i = 0; i < num; i++) {
                sb.append(replacement);
            }
        } else {
            for (int i = 0; i < num; i++) {
                sb.append(replacement);
            }
            sb.append(str);
        }
        return sb.toString();
    }
}
