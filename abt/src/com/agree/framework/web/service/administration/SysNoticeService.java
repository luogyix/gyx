/**   
 * @Title: SysNoticeService.java 
 * @Package com.agree.framework.web.service.administration 
 * @Description: TODO 
 * @company agree   
 * @author authorname   
 * @date 2012-10-31 下午03:23:06 
 * @version V1.0   
 */ 

package com.agree.framework.web.service.administration;

import java.util.List;
import java.util.Map;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.form.TBsmsSysNotice;
import com.agree.framework.web.service.base.BaseService;

/** 
 * @ClassName: SysNoticeService 
 * @Description: TODO
 * @company agree   
 * @author haoruibing   
 * @date 2012-10-31 下午03:23:06 
 *  
 */
@SuppressWarnings({"rawtypes"})
public class SysNoticeService extends BaseService  implements ISysNoticeService{

	
	public void addNotice(TBsmsSysNotice notice) throws Exception {
		Long id= (Long)sqlDao_h.getRecord("select max(id) from TBsmsSysNotice t ");
		if(id==null){
			id=new Long(0);
		}
		notice.setId(id.longValue()+1);
		sqlDao_h.saveRecord(notice);
	}

	
	public void delNotice(List<TBsmsSysNotice> ids) throws Exception {
		for(TBsmsSysNotice notice:ids){
			sqlDao_h.deleteById(TBsmsSysNotice.class, notice);
		}
		
	}

	
	/* (non-Javadoc)
	 * <p>Title: getNoticeMsg</p> 
	 * <p>Description: 根据用户的部门，选择通知信息</p> 
	 * @return
	 * @throws Exception 
	 * @see com.agree.framework.web.service.administration.ISysNoticeService#getNoticeMsg() 
	 */ 
	public String getNoticeMsg(String unitid) throws Exception {
		List<TBsmsSysNotice> notices=sqlDao_h.getRecordList("from TBsmsSysNotice",false);
		String msg="";
		for(TBsmsSysNotice notice:notices){
			if(notice.getUnitid().equals(unitid)||notice.getUnitid().equals("0")){
				msg+=notice.getMsg();
			}
		}
		return msg;
	}

	
	public void updateNotice(TBsmsSysNotice notice) throws Exception {
		sqlDao_h.updateRecord(notice);
		
	}


	/* (non-Javadoc)
	 * <p>Title: queryNotice</p> 
	 * <p>Description: 根据条件查询本部门辖属部门的通知</p> 
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception 
	 * @see com.agree.framework.web.service.administration.ISysNoticeService#queryNotice(java.util.Map, com.agree.framework.dao.entity.Page) 
	 */ 
	public List queryNotice(Map map, Page page) throws Exception {
		//return sqlDao_h.queryPage(TBsmsSysNotice.class, map, null, page, false);
		String hql="select new map(n.unitid as unitid,u.unitname as unitname,n.msg as msg,n.id as id) " +
				"from TBsmsSysNotice n,Unit u where u.unitlist like concat(concat('%|',:myunitid),'|%') and cast(n.unitid as big_decimal)=u.unitid";
		if(map.get("unitid")!=null && !map.get("unitid").toString().equals("")){
			hql = hql+" and n.unitid = :unitid";
		}
		return sqlDao_h.queryPage(hql,map,page,false);
		
		
	}

}
