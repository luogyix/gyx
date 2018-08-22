package com.agree.framework.web.service.administration;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.web.form.administration.BsmsMnguseroprlog;
import com.agree.framework.web.service.base.BaseService;
/**
 * 
 * @ClassName: LogService 
 * @Description: 实现日志服务接口
 * @company agree   
 * @author dhs  
 * @date 2011-10-14 下午03:41:08 
 *
 */
public class LogService extends BaseService implements
		ILogService {
	private static final Logger logger = LoggerFactory.getLogger(LogService.class);
	/**
	 * 
	 * @Title: insertUserOprLog_itransc 
	 * @Description: 实现用户操作日志查询
	 * @param useroprlog
	 * @throws Exception
	 */
	public void insertUserOprLog_itransc(BsmsMnguseroprlog useroprlog)
	{
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("unitid", useroprlog.getUnitid());
			map.put("logdate", useroprlog.getLogdate());
			map.put("logtime", useroprlog.getLogtime());
			map.put("username", useroprlog.getUsername());
			map.put("hostip", useroprlog.getHostip());
			map.put("operation", useroprlog.getOperation());
			String hql = "select count(*) from BsmsMnguseroprlog " +
					"where unitid=:unitid " +
					"and logdate=:logdate " +
					"and logtime=:logtime " +
					"and username=:username " +
					"and hostip=:hostip " +
					"and operation=:operation";
			Long count = sqlDao_h.getRecordCount(hql,map);
			if(count==0){
				sqlDao_h.saveRecord(useroprlog);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
}
