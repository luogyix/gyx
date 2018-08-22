/**   
 * @Title: ISysNoticeService.java 
 * @Package com.agree.framework.web.service.administration 
 * @Description: TODO 
 * @company agree   
 * @author authorname   
 * @date 2012-10-31 下午03:23:24 
 * @version V1.0   
 */ 

package com.agree.framework.web.service.administration;

import java.util.List;
import java.util.Map;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.form.TBsmsSysNotice;

/** 
 * @ClassName: ISysNoticeService 
 * @Description: TODO
 * @company agree   
 * @author haoruibing   
 * @date 2012-10-31 下午03:23:24 
 *  
 */
@SuppressWarnings({"rawtypes"})
public interface ISysNoticeService {
	
	/** 
	 * @Title: getNoticeMsg 
	 * @Description: 获得通知信息
	 * @return
	 * @throws Exception     
	 */ 
	public String getNoticeMsg(String unitid) throws Exception;
	
	/** 
	 * @Title: getNoticeMsg 
	 * @Description: 获得通知信息
	 * @return
	 * @throws Exception     
	 */ 
	public List queryNotice(Map map,Page page) throws Exception;
	
	/** 
	 * @Title: addNotice 
	 * @Description: 新增通知信息
	 * @param notice
	 * @throws Exception     
	 */ 
	public void addNotice(TBsmsSysNotice notice) throws Exception;
	
	/** 
	 * @Title: updateNotice 
	 * @Description: 更新通知信息
	 * @param notice
	 * @throws Exception     
	 */ 
	public void updateNotice(TBsmsSysNotice notice) throws Exception;
	
	/** 
	 * @Title: delNotice 
	 * @Description: 删除通知信息
	 * @param notice
	 * @throws Exception     
	 */ 
	public void delNotice(List<TBsmsSysNotice> notices) throws Exception;

}
