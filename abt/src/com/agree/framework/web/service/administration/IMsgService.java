package com.agree.framework.web.service.administration;

import com.agree.framework.web.form.administration.TBsmsMsgbook;

public interface IMsgService {
	/** 
	 * @Title: addMsgRecord 
	 * @Description: 添加报文记录
	 * @param msg    
	 */ 
	public void addMsgRecord(TBsmsMsgbook msg);
	
	/** 
	 * @Title: updateMsgRecord 
	 * @Description: 修改报文记录
	 * @param msg    
	 */ 
	public void updateMsgRecord(TBsmsMsgbook msg);
	
	public String getMsgId();
	
	/** 
	 * @Title: insertPushlet 
	 * @Description: 判断是否为需要推送的交易
	 * @param @param eventtask    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void insertPushlet(TBsmsMsgbook book) ;
	
}
