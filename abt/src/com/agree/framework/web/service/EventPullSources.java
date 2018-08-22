/**   
 * @Title: EventPullSources.java 
 * @Package com.agree.framework.web.service 
 * @Description:  
 * @company agree   
 * @author authorname   
 * @date Feb 1, 2013 2:44:24 PM 
 * @version V1.0   
 */ 

package com.agree.framework.web.service;


import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.agree.framework.web.service.administration.IEventService;
/** 
 * 
 * @ClassName: EventPullSources 
 * @Description: 
 * @company agree   
 * @author authorname   
 * @date Feb 1, 2013 2:44:24 PM 
 *  
 */
public class EventPullSources extends EventPullSource {
	
	private Logger logger = LoggerFactory.getLogger(EventPullSources.class);
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();			  
	IEventService eventService = (IEventService) context.getBean("eventService");
	/** 
	 * @Title: pullEvent 
	 * @Description: 
	 * @return     
	 */ 
	protected Event pullEvent() {
		//定义页面主题
		Event event=null;
		try {
			event = eventService.run();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return event;
	}
	
	protected long getSleepTime() {
		return 6000;
	}

}
