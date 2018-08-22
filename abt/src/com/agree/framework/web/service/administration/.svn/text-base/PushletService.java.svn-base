package com.agree.framework.web.service.administration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.web.form.administration.TBsmsEventtask;
import com.agree.framework.web.service.base.BaseService;

/** 
 * @ClassName: PushletService 
 * @Description: TODO
 * @company agree   
 * @author lilei
 * @date 2013-3-21 下午01:19:43 
 *  
 */ 
public class PushletService extends BaseService implements IPushletService {
	private static final Logger logger = LoggerFactory.getLogger(PushletService.class);
	/* (non-Javadoc)
	 * <p>Title: insertUserPushlet</p> 
	 * <p>Description: </p> 
	 * @param eventtask
	 * @throws Exception 
	 * @see com.agree.framework.web.service.administration.IPushletService#insertUserPushlet(com.agree.framework.web.form.administration.TBsmsEventtask) 
	 */ 
	public void insertUserPushlet(TBsmsEventtask eventtask) throws Exception {
		try {
			sqlDao_h.saveRecord(eventtask);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

}
