package com.agree.framework.web.service.administration;

import com.agree.framework.web.service.base.BaseService;

public class ClearHisLogServer extends BaseService implements IClearHisLogServer {

	/**
	 * 删除日志表超过30天的数据
	 */
	public void clearHisLogData() throws Exception {
//		String sql ="insert into t_his_mnguseroprlog(UNITID,LOGDATE,LOGTIME,USERNAME,HOSTIP,OPERATION,RESULT,USERCODE,UNITNAME,OPERDETAIL) " +
//				"select UNITID,LOGDATE,LOGTIME,USERNAME,HOSTIP,OPERATION,RESULT,USERCODE,UNITNAME,OPERDETAIL from t_bsms_mnguseroprlog";
		String[] test={};
//		sqlDao_h.excuteSql(sql,test);
		String delsql = "delete from t_bsms_mnguseroprlog where logdate < to_char(sysdate - 30, 'YYYYMMDD')";
		sqlDao_h.excuteSql(delsql,test);
		//String delmsg = "delete from t_bsms_msgbook where 1=1";
		//sqlDao_h.excuteSql(delmsg,test);
		
		//String delbak = "delete from T_BAK_UPPTABLE where 1=1";
		//sqlDao_h.excuteSql(delbak,test);
	}

}
