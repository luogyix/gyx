package com.agree.framework.web.service.administration;

import java.util.List;
import java.util.Map;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.TBsmsDictinfo;
import com.agree.framework.web.service.base.IBaseService;

/** 
* @ClassName: IDictinfoService 
* @Description: 字典信息管理业务接口
* @company agree   
* @author dhs   
* @date 2011-08-24 
* 
*/
@SuppressWarnings({"rawtypes"})
public interface IDictinfoService extends IBaseService{

	/**
	 * 查询字典所有信息
	 * @param map
	 * @param pageInfo
	 * @return List
	 * */
	List<TBsmsDictinfo> findDictinfoAll(Map map, Page pageInfo)throws Exception;
	
	/**
	 * 添加字典信息
	 * @param dictinfo
	 * @return ServiceReturn
	 * */
	ServiceReturn addDictinfo(TBsmsDictinfo dictinfo) throws Exception;
	
	/**
	 * 修改字典信息
	 * @param dictinfo
	 * @return ServiceReturn
	 * */
	ServiceReturn editDictinfo(TBsmsDictinfo dictinfo) throws Exception;
	
	/**
	 * 删除字典信息
	 * @param dictinfos
	 * @return ServiceReturn
	 * */
	ServiceReturn delDictinfo(List<TBsmsDictinfo> dictinfos) throws Exception;
}
