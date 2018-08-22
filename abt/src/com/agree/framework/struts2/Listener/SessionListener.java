package com.agree.framework.struts2.Listener;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.agree.framework.struts2.webserver.ApplicationConstants;

public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent arg0) {

	}

	/**
	 * session销毁时，去掉保存的手持设备session信息
	 */
	@SuppressWarnings("unchecked")
	public void sessionDestroyed(HttpSessionEvent se) {
		Map<String, HttpSession> sessionMap = (Map<String, HttpSession>)se.getSession().getServletContext().getAttribute(ApplicationConstants.SESSIONMAP);
		if(sessionMap != null){
			sessionMap.remove(se.getSession().getId());
		}
	}
}
