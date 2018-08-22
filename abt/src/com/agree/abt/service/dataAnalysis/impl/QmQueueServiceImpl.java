package com.agree.abt.service.dataAnalysis.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agree.abt.model.dataAnalysis.BtQmQueuetype;
import com.agree.abt.service.dataAnalysis.IQmQueueService;
import com.agree.framework.web.service.base.BaseService;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class QmQueueServiceImpl extends BaseService implements IQmQueueService {

	public List getChildDetails(String branch) throws Exception {
		String Hql = "select new Map(unitid,unitname)from Unit where parentunitid =:branch ";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("branch", branch);
		List<Map<String,String>> list = sqlDao_h.getRecordList(Hql, map, false);//
		List<BtQmQueuetype> list2 = new ArrayList<BtQmQueuetype>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (Map<String,String> currentmap : list) {
			String hql1 = "select count(*) ,sum(secdiff(t.en_queue_time,to_char(sysdate,'hh24miss')))/count(*)  from bt_qm_queue t where t.branch in (select branch from bt_sys_branch_info where unitlist like '%|"
					+ currentmap.get("0")
					+ "|%' ) and t.work_date = '"
					+ sdf.format(new Date()) + "' and t.queue_status = '0'";
			HashMap<String, String> map1 = new HashMap<String, String>();
			String[] keys = { "btqmsum", "averagewaittime" };
			List<Object> list3 = sqlDao_h.getRecordListBySql(hql1, map1, keys);
			BtQmQueuetype btQmQueuetype = new BtQmQueuetype();
			btQmQueuetype.setBranch(currentmap.get("0").toString());
			btQmQueuetype.setBranch_name(currentmap.get("1").toString());
			Map map3 = (Map) list3.get(0);
			String btqmsum = map3.get("btqmsum").toString();
			btQmQueuetype.setBtqmsum(btqmsum);
			if (map3.get("averagewaittime") == null||"".equals(map3.get("averagewaittime") .toString())) {
				btQmQueuetype.setAveragewaittime("0");
			} else if(Float.parseFloat(map3.get("averagewaittime").toString())>0){
				
				btQmQueuetype.setAveragewaittime(map3.get("averagewaittime")
						.toString());
			}else{
				btQmQueuetype.setAveragewaittime("0");
			}
			list2.add(btQmQueuetype);
		}
		return list2;
	}
}
