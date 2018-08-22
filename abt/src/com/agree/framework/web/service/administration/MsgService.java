/**
 * 
 */
package com.agree.framework.web.service.administration;

import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.form.administration.TBsmsEventtask;
import com.agree.framework.web.form.administration.TBsmsMsgbook;
import com.agree.framework.web.form.administration.TBsmsTaskcfg;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;
import com.agree.util.StringUtil;

/**
 * @author haoruibing
 *
 */
public class MsgService extends BaseService implements IMsgService {
	/* (non-Javadoc)
	 * <p>Title: addMsgRecord</p> 
	 * <p>Description: 添加报文记录</p> 
	 * @param msg 
	 * @see com.agree.bsms.service.common.IBaseInfoService#addMsgRecord(com.agree.bsms.model.common.TBsmsMsgbook) 
	 */ 
	public void addMsgRecord(TBsmsMsgbook msg) {
		sqlDao_h.saveRecord(msg);
		
	}

	/* (non-Javadoc)
	 * <p>Title: updateMsgRecord</p> 
	 * <p>Description: 修改报文记录</p> 
	 * @param msg 
	 * @see com.agree.bsms.service.common.IBaseInfoService#updateMsgRecord(com.agree.bsms.model.common.TBsmsMsgbook) 
	 */ 
	public void updateMsgRecord(TBsmsMsgbook msg) {
		sqlDao_h.updateRecord(msg);
	}

	/* (non-Javadoc)
	 * <p>Title: getMsgId</p> 
	 * <p>Description: 获得报文id号，取TBsmsMsgbook表里往帐最大流水号值加1</p> 
	 * @return 
	 * @see com.agree.bsms.service.common.IBaseInfoService#getMsgId() 
	 */ 
	public String getMsgId() {
		Object msgid= sqlDao_h.getRecord("select max(mesgid) from TBsmsMsgbook t where t.mbflag='1'");
		String next="00000001";
		if(msgid==null){
		}else{
			Integer i=new Integer((String)msgid);
			next= StringUtil.fillZeroLeft(String.valueOf((i.intValue()+1)), 8);
		}
		return next;
	}

	/* (non-Javadoc)
	 * <p>Title: insertPushlet</p> 
	 * <p>Description: </p> 
	 * @param eventtask 
	 * @see com.agree.framework.web.service.administration.IMsgService#insertPushlet(com.agree.framework.web.form.administration.TBsmsEventtask) 
	 */ 
	public void insertPushlet(TBsmsMsgbook book) {
		String hql = "from TBsmsTaskcfg where servicecode='"+book.getServicecode()+"'";
		
		List<TBsmsTaskcfg> list = sqlDao_h.getRecordList(hql,false);
		
		if(list.size() > 0){
			TBsmsEventtask eventtask = new TBsmsEventtask();
			eventtask.setChannelcode("010");
			eventtask.setChanneldate(book.getMesgdate());
			eventtask.setChannelserno(book.getChannelserno());
			eventtask.setTasksql("from "+list.get(0).getResultobject());
			eventtask.setListenevent(list.get(0).getListenevent());
			eventtask.setResultobject(list.get(0).getResultobject());
			User logonUser = (User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
			eventtask.setUsercode(logonUser.getUsercode());
			eventtask.setStarttime(new Date());
			eventtask.setEnabled("0");
			eventtask.setTempath(list.get(0).getTempath());
			
			sqlDao_h.saveRecord(eventtask);
		}
		
	}

}
