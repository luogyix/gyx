package com.agree.framework.web.service.administration;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.agree.framework.exception.AppException;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.service.base.BaseService;

public class WaitingTimeService extends BaseService implements
		IWaitingTimeService {
private static final Logger logger = LoggerFactory.getLogger(WaitingTimeService.class);
	@Transactional
	public ServiceReturn resetAnalysis(Map<String, String> map)
			throws Exception {
		// 判断是否超过3个
		// 20131221-20140101
		// 2013 12 21
		// 2014 01 01
		String dateStart = map.get("dateStart");
		String dateEnd = map.get("dateEnd");
		int sYear = Integer.parseInt(dateStart.substring(0, 4));
		int eYear = Integer.parseInt(dateEnd.substring(0, 4));
		int sMonth = Integer.parseInt(dateStart.substring(4, 6));
		int eMonth = Integer.parseInt(dateEnd.substring(4, 6));
		int sDay = Integer.parseInt(dateStart.substring(6));
		int eDay = Integer.parseInt(dateEnd.substring(6));
		if (sYear != eYear) {
			eMonth += 12;
		}
		if (eDay - sDay > 0) {
			eMonth += 1;
		}
		if (eMonth - sMonth > 3) {
			throw new AppException("请确认生成时间段不超过3个月!");
		}
		// for循环日期段
		Connection conn = sqlDao_h.getConn();
		; // 用session对象获取连接
		int start = Integer.parseInt(map.get("dateStart"));
		int end = Integer.parseInt(map.get("dateEnd"));
		try {
			for (int date = start; date <= end; date++) {
				// 判断日期跑批 20131031
				// 1.3.5.7.8.10.12 31天
				String workdate = String.valueOf(date);
				String year = workdate.substring(0, 4);
				String month = workdate.substring(4, 6);
				String day = workdate.substring(6);
				if ("01".equals(month) || "03".equals(month)
						|| "05".equals(month) || "07".equals(month)
						|| "08".equals(month) || "10".equals(month)
						|| "12".equals(month)) {
					if (Integer.parseInt(day) > 31) {
						if (Integer.parseInt(month) == 12) {// 跨年
							date = Integer.parseInt(String.valueOf(Integer
									.parseInt(year) + 1) + "0101") - 1;
						} else {// 跨月
							String mo = String
									.valueOf(Integer.parseInt(month) + 1);
							if (mo.length() == 1) {
								date = Integer.parseInt(year + "0" + mo + "01") - 1;
							} else {
								date = Integer.parseInt(year + mo + "01") - 1;
							}
						}
						continue;
					}
				} else {
					if (Integer.parseInt(day) > 30) {
						if (Integer.parseInt(month) == 12) {// 跨年
							date = Integer.parseInt(String.valueOf(Integer
									.parseInt(year) + 1) + "0101") - 1;
						} else {// 跨月
							String mo = String
									.valueOf(Integer.parseInt(month) + 1);
							if (mo.length() == 1) {
								date = Integer.parseInt(year + "0" + mo + "01") - 1;
							} else {
								date = Integer.parseInt(year + mo + "01") - 1;
							}
						}
						continue;
					}
				}
				String retcode = "";
				String retmsg = "";
				CallableStatement call = conn
						.prepareCall("{Call SP_PUB_DAYENDRPT(?,?,?,?)}");
				call.setString(1, String.valueOf(date));
				call.setString(2, "1");
				call.setString(3, retcode);
				call.setString(4, retmsg);
				call.execute();
				call.close();// 关闭
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			conn.close();
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return ret;
	}

}
