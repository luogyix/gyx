package com.agree.abt.service.dataAnalysis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.agree.abt.service.dataAnalysis.IRetailCustomerWaitingTimeService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.service.base.BaseService;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RetailCustomerWaitingTimeService extends BaseService implements IRetailCustomerWaitingTimeService {
	
	@Transactional
	public List<?> queryList(Map map,Page pageinfo) throws Exception {
		StringBuffer sql = new StringBuffer();
		//拼接sql
		sql.append("select t.work_date,t.branch,t.branch_name," +
				"  sum(case when t.custtype_i in ('BJ','ZS','HK','HJ','PT') then 1 else 0 end) ticket_num," +
				"  sum(case when t.custtype_i in ('BJ','ZS','HK','HJ','PT') and t.queue_status in (2,4) then 1 else 0 end) valid_ticket_num," +
				"  sum(case when t.custtype_i in ('BJ','ZS','HK','HJ','PT') and t.queue_status = 3 then 1 else 0 end) discarded_ticket_num," +
				"  sum(case when t.custtype_i in ('BJ','ZS','HK') then 1 else 0 end) platinum_ticket_num," +
				"  sum(case when t.custtype_i in ('BJ','ZS','HK') and t.queue_status in (2,4) then 1 else 0 end) platinum_valid_ticket_num," +
				"  sum(case when t.custtype_i in ('BJ','ZS','HK') and t.queue_status = 3 then 1 else 0 end) platinum_discarded_ticket_num," +
				"  case " +
				"    when sum(case when t.custtype_i in ('BJ','ZS','HK') then 1 else 0 end) = 0 then 0 " +
				"    when sum(case when t.custtype_i in ('BJ','ZS','HK') and t.queue_status = 3 then 1 else 0 end) = 0 then 0 " +
				"    else sum(case when t.custtype_i in ('BJ','ZS','HK') and t.queue_status = 3 then 1 else 0 end)/sum(case when t.custtype_i in ('BJ','ZS','HK') then 1 else 0 end) " +
				"  end platinum_discarded_ticket_rate," +
				"  sum(case when t.custtype_i in ('BJ','ZS','HK') and t.queue_status in (2,4) and secdiff(t.en_queue_time,t.de_queue_time)/60<5 then 1 else 0 end) platinum_standard_ticket_num," +
				"  case" +
				"    when sum(case when t.custtype_i in ('BJ','ZS','HK') and t.queue_status in (2,4) and secdiff(t.en_queue_time,t.de_queue_time)/60<5 then 1 else 0 end)=0 then 0" +
				"    when sum(case when t.custtype_i in ('BJ','ZS','HK') and t.queue_status in (2,4) then 1 else 0 end) = 0 then 0" +
				"    else sum(case when t.custtype_i in ('BJ','ZS','HK') and t.queue_status in (2,4) and secdiff(t.en_queue_time,t.de_queue_time)/60<5 then 1 else 0 end)/sum(case when t.custtype_i in ('BJ','ZS','HK') and t.queue_status in (2,4) then 1 else 0 end)" +
				"  end platinum_standard_ticket_rate," +
				"  sum(case when t.custtype_i = 'HJ' then 1 else 0 end) gold_ticket_num," +
				"  sum(case when t.custtype_i = 'HJ' and t.queue_status in (2,4) then 1 else 0 end) gold_valid_ticket_num," +
				"  sum(case when t.custtype_i = 'HJ' and t.queue_status = 3 then 1 else 0 end) gold_discarded_ticket_num," +
				"  case " +
				"    when sum(case when t.custtype_i = 'HJ' then 1 else 0 end) = 0 then 0 " +
				"    when sum(case when t.custtype_i = 'HJ' and t.queue_status = 3 then 1 else 0 end) = 0 then 0 " +
				"    else sum(case when t.custtype_i = 'HJ' and t.queue_status = 3 then 1 else 0 end)/sum(case when t.custtype_i = 'HJ' then 1 else 0 end) " +
				"  end gold_discarded_ticket_rate," +
				"  sum(case when t.custtype_i = 'HJ' and t.queue_status in (2,4) and secdiff(t.en_queue_time,t.de_queue_time)/60<10 then 1 else 0 end) gold_standard_ticket_num," +
				"  case" +
				"    when sum(case when t.custtype_i = 'HJ' and t.queue_status in (2,4) and secdiff(t.en_queue_time,t.de_queue_time)/60<10 then 1 else 0 end)=0 then 0" +
				"    when sum(case when t.custtype_i = 'HJ' and t.queue_status in (2,4) then 1 else 0 end) = 0 then 0" +
				"    else sum(case when t.custtype_i = 'HJ' and t.queue_status in (2,4) and secdiff(t.en_queue_time,t.de_queue_time)/60<10 then 1 else 0 end)/sum(case when t.custtype_i = 'HJ' and t.queue_status in (2,4) then 1 else 0 end)" +
				"  end gold_standard_ticket_rate," +
				"  sum(case when t.custtype_i = 'PT' then 1 else 0 end) common_ticket_num," +
				"  sum(case when t.custtype_i = 'PT' and t.queue_status in (2,4) then 1 else 0 end) common_valid_ticket_num," +
				"  sum(case when t.custtype_i = 'PT' and t.queue_status = 3 then 1 else 0 end) common_discarded_ticket_num," +
				"  case " +
				"    when sum(case when t.custtype_i = 'PT' then 1 else 0 end) = 0 then 0 " +
				"    when sum(case when t.custtype_i = 'PT' and t.queue_status = 3 then 1 else 0 end) = 0 then 0 " +
				"    else sum(case when t.custtype_i = 'PT' and t.queue_status = 3 then 1 else 0 end)/sum(case when t.custtype_i = 'PT' then 1 else 0 end) " +
				"  end common_discarded_ticket_rate," +
				"  sum(case when t.custtype_i = 'PT' and t.queue_status in (2,4) and secdiff(t.en_queue_time,t.de_queue_time)/60<20 then 1 else 0 end) common_standard_ticket_num," +
				"  case " +
				"    when sum(case when t.custtype_i = 'PT' and t.queue_status in (2,4) and secdiff(t.en_queue_time,t.de_queue_time)/60<20 then 1 else 0 end)=0 then 0 " +
				"    when sum(case when t.custtype_i = 'PT' and t.queue_status in (2,4) then 1 else 0 end) = 0 then 0 " +
				"    else sum(case when t.custtype_i = 'PT' and t.queue_status in (2,4) and secdiff(t.en_queue_time,t.de_queue_time)/60<20 then 1 else 0 end)/sum(case when t.custtype_i = 'PT' and t.queue_status in (2,4) then 1 else 0 end) " +
				"  end common_standard_ticket_rate " +
				"from bt_qm_queue_his t ");
		HashMap paramaHashMap=new HashMap();
		//{limit=20, startDate=20131128, start=1, branch=756017, endDate=20131227, pageflag=0}
		paramaHashMap.putAll(map);
		HashMap fMap=new HashMap();
		sql.append(" where t.branch in ( " +
				" select branch " +
				" from bt_sys_branch_info " +
				" where subbank_num in (" +
				" select branch  " +
				" from bt_sys_branch_info  " +
				" where subbank_num in ( " +
				" select branch " +
				" from bt_sys_branch_info " +
				" where subbank_num = :branch " +
				" or branch = :branch " +
				" ) or branch = :branch " +
				" ) or branch = :branch " +
				" )");
		fMap.put("branch", paramaHashMap.get("branch"));
		sql.append(" and t.work_date between :startDate and :endDate");
		fMap.put("startDate", paramaHashMap.get("startDate"));
		fMap.put("endDate", paramaHashMap.get("endDate"));
		if(paramaHashMap.get("business")!=null && !paramaHashMap.get("business").toString().equals("")){
			sql.append(" and t.bs_id = :bs_id");
			fMap.put("bs_id", paramaHashMap.get("business"));
		}
		if(paramaHashMap.get("custtype")!=null && !paramaHashMap.get("custtype").toString().equals("")){
			sql.append(" and t.custtype_i = :custtype_i");
			fMap.put("custtype_i", paramaHashMap.get("custtype"));
		}
		sql.append(" group by t.work_date,t.branch,t.branch_name order by t.branch,t.work_date");
		String queryName = sql.toString();
		List reaultList = sqlDao_h.queryPageBySql(queryName, fMap, null, pageinfo);
		for (int i = 0; i < reaultList.size(); i++) {
			Map rMap = (HashMap)reaultList.get(i);
			String work_date = (String)rMap.get("WORK_DATE");
			work_date = work_date.substring(0,4) + "-" + work_date.substring(4,6) + "-" + work_date.substring(6);
			rMap.put("WORK_DATE", work_date);
		}
		return reaultList;
	}

}
