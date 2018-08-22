package com.agree.framework.communication;

import com.agree.util.StringUtil;

public class AFASecurityPackager implements Packager {


	public String pack(Message paramMessage) {
		String securityMsg="";
		//进行安全域算法，得出安全信息域
		
		String string = "{S:"+securityMsg+"}\r\n";
		
		return StringUtil.fillCharLeft(String.valueOf(string.length()), 8, '0')+string;
	}


	public Message unpack(String msg) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
