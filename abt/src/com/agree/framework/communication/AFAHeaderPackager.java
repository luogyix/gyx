package com.agree.framework.communication;



import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.util.Configuration;
import com.agree.util.Constants;
import com.agree.util.StringUtil;


/**
 * <p>afa报文头处理类</p>
 * Author: haoruibing
 */
public class AFAHeaderPackager implements Packager {
	private static final Logger logger = LoggerFactory.getLogger(AFAHeaderPackager.class);
	private static Configuration conf = Configuration.getInstance("AFAHeader");

	/**
	 * 解析报文
	 */
	public Message unpack(String bytes)  {
		Message msg = new Message();
		String str = new String(bytes);
		 
		String[] bodyKeyArray = conf.getProperty("MsgHeader", "keys").split(",");
		String[] bodyFormatArray = conf.getProperty("MsgHeader","format").split(",");

		int index=0;
		updateMessageByFormat(bodyKeyArray, bodyFormatArray, str, index, msg);
		return msg;
	}

	private int updateMessageByFormat(String[] keyArray, String[] formatArray,String str, int index, Message msg) {
		for (int i = 0; i < keyArray.length; i++) {
			String keyValue = keyArray[i];
			if (keyValue == null || keyValue.equals("")) {
				break;
			}
			String keyFormatDesc = formatArray[i];
			String dataType = keyFormatDesc.substring(0, 1);
			int length = Integer.parseInt(keyFormatDesc.substring(1,
					keyFormatDesc.length()));
			try {
				if (index >= str.getBytes("GB2312").length) {
					break;
				}
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(),e);
			}
			String value = StringUtil.getStringByBytesRange(str, index, index + length)
					.trim();
			
			if (dataType.equals("X")||dataType.equals("D")||dataType.equals("T")) {
				msg.put(Constants.HEADER,keyValue, value);
			}else if (dataType.equals("N")) {
				msg.put(Constants.HEADER,keyValue, StringUtil.getStrPassPriZone(value));
			}
			index += length;
		}
		return index;
		
	}

	/**
	 * 将Message转化为报文头
	 */
    public String pack(Message msg) {
        StringBuffer result = new StringBuffer();
        //报文体
        msg.put(Constants.HEADER, "起始标识", "{H:");
        msg.put(Constants.HEADER, "结束标识", "}\r\n");
        String[] keyArray = conf.getProperty("MsgHeader", "keys").split(",");
        String[] formatArray = conf.getProperty("MsgHeader", "format").split(",");
        try {
			result.append(packByFormat(msg, keyArray, formatArray));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
        return result.toString();
    }

    /**
     * 根据报文格式读取Message中的值生成结果字符串
     * @throws UnsupportedEncodingException 
     */
    private String packByFormat(Message msg, String[] keyArray,
			String[] formatArray) throws UnsupportedEncodingException {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (key.equals("")) {
				continue;
			}
			String keyFormatDesc = formatArray[i];
			String dataType = keyFormatDesc.substring(0, 1);

			int length = Integer.parseInt(keyFormatDesc.substring(1,
					keyFormatDesc.length()));
			String value = msg.getString(Constants.HEADER,key);
			
			if (dataType.equals("X")) {
				// 字符型
				if(value==null){
					value="";
				}
				result.append(StringUtil.fillBlankRight(value, length));
			} else if (dataType.equals("D")) {
				if (value == null) {
					value = "0";
				}
				value = Integer.toString((int) (Double.parseDouble(value) * 100));
				// 日期型
				result.append(StringUtil.fillZeroLeft(value, length));
			}else if (dataType.equals("T")) {
				if (value == null) {
					value = "0";
				}
				value = Integer.toString((int) (Double.parseDouble(value) * 100));
				// 时间型
				result.append(StringUtil.fillZeroLeft(value, length));
			} 
			else if (dataType.equals("N")) {
				// 数字型
				if (value == null) {
					value = "0";
				}
				result.append(StringUtil.fillZeroLeft(value, length));
			}
		}
		return result.toString();
	}

 
}
