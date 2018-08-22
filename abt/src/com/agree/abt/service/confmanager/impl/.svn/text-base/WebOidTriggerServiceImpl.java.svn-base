package com.agree.abt.service.confmanager.impl;

import com.agree.abt.service.confmanager.IWebOidTriggerService;
import com.agree.framework.struts2.webserver.IStartupObject;

/**
 * WebOid刷新定时调度类
 * @ClassName: WebOidTriggerServiceImpl.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-5-6
 */
public class WebOidTriggerServiceImpl implements IWebOidTriggerService {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private IStartupObject initService;
	public void runTrigger4FlushWebOid() throws Exception {
		initService.regInterface();
	}
	public IStartupObject getInitService() {
		return initService;
	}
	public void setInitService(IStartupObject initService) {
		this.initService = initService;
	}
}
