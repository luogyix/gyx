/**   
 * @Title: ReportDayService.java 
 * @Package com.agree.bsms.service.report 
 * @Description: TODO 
 * @company agree   
 * @author authorname   
 * @date 2013-3-12 下午04:42:21 
 * @version V1.0   
 */ 

package com.agree.framework.web.service.base;

import java.sql.CallableStatement;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.util.DateUtil;

/** 
 * @ClassName: ReportDayService 
 * @Description: 每日跑存储过程，出报表
 * @company agree   
 * @author haoruibing   
 * @date 2013-3-12 下午04:42:21 
 *  
 */

public class ReportDayService extends BaseService {
	private Logger logger = LoggerFactory.getLogger(ReportDayService.class);
	
	public void runProcedure() throws Exception{
		logger.info("开始调用报表存储过程！");
		Connection conn=sqlDao_h.getConn();
		CallableStatement call = conn.prepareCall("{Call BSMS_REPORT.bsms_report_main(?)}");  
		String today=DateUtil.getCurrentDateByFormat("yyyyMMdd");
		String reportdate=DateUtil.preDate(today);
		logger.info("报表日期："+reportdate);
		call.setString(1,reportdate );  
		call.execute(); 
		call.close();
		conn.close(); 
		logger.info("存储过程执行完毕！");
	}

}
