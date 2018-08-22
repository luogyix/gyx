package com.agree.framework.web.service.administration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.TBsmsDictinfo;
import com.agree.framework.web.service.base.BaseService;

/** 
* @ClassName: DictinfoService 
* @Description: 字典信息管理业务接口实现
* @company agree   
* @author dhs   
* @date 2011-08-24 
* 
*/
@SuppressWarnings({"unchecked","rawtypes"})
public class DictinfoService extends BaseService implements IDictinfoService{

	/**
	 * 实现添加字典信息方法
	 * @param dictinfo
	 * @return ServiceReturn 
	 */
	public ServiceReturn addDictinfo(TBsmsDictinfo dictinfo) throws Exception {
		dictinfo.setItem(String.valueOf(getMaxItem()));
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		try{
			String[] params ={dictinfo.getDicttype(),dictinfo.getDicttypedesc(),dictinfo.getDictvalue(),dictinfo.getDictvaluedesc(),dictinfo.getEnable(),dictinfo.getEditable(),dictinfo.getRemark(),dictinfo.getItem()};
			sqlDao_h.excuteSql("insert into T_BSMS_DICTINFO (DICTTYPE, DICTTYPEDESC, DICTVALUE, DICTVALUEDESC, ENABLE, EDITABLE, REMARK, ITEM) values (?, ?, ?, ?, ?, ?, ?, ?)", params);
		}catch(Exception e){
			ret = new ServiceReturn(ServiceReturn.SUCCESS, "字典名称或代码已存在,请重新输入!");
		}
		return ret;
	}
	
	/**
	 * 获取ID最大值,并使后来加入的字典信息id递增
	 * */
	public int getMaxItem()throws Exception{
		String sql = "select max(d.item) as item from TBsmsDictinfo d";
		String maxItem = (String)sqlDao_h.getRecord(sql);
		int item = Integer.parseInt(maxItem) + 1;
		return item;
	}
	/**
	 * 实现删除字典信息方法
	 * @param dictinfos
	 * @return ServiceReturn
	 */
	public ServiceReturn delDictinfo(List<TBsmsDictinfo> dictinfos) throws Exception {
		for(TBsmsDictinfo dictinfo : dictinfos){
			sqlDao_h.deleteById(TBsmsDictinfo.class, dictinfo.getItem());
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		return ret;
	}
	/**
	 * 实现修改字典信息
	 * @param dictinfo
	 * @return ServiceReturn
	 */
	public ServiceReturn editDictinfo(TBsmsDictinfo dictinfo) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		try{
			String[] params ={dictinfo.getDicttype(),dictinfo.getDicttypedesc(),dictinfo.getDictvalue(),dictinfo.getDictvaluedesc(),dictinfo.getEnable(),dictinfo.getEditable(),dictinfo.getRemark(),dictinfo.getItem()};
			sqlDao_h.excuteSql("update T_BSMS_DICTINFO set DICTTYPE=?, DICTTYPEDESC=?, DICTVALUE=?, DICTVALUEDESC=?, ENABLE=?, EDITABLE=?, REMARK=? where ITEM=?", params);
		}catch(Exception e){
			ret = new ServiceReturn(ServiceReturn.SUCCESS, "字典名称或代码已存在,请重新输入!");
		}
		return ret;
	}

	/**
	 * 实现查询字典所有信息
	 * @param map
	 * @param pageInfo
	 * @return List
	  */
	public List<TBsmsDictinfo> findDictinfoAll(Map map, Page pageInfo) throws Exception {
		List dictinfoList = new ArrayList<Object>();
		String sql = "from TBsmsDictinfo d " +
				     "where 1=1 ";
		if(StringUtils.isNotEmpty((String)map.get("dicttypedesc"))){
			sql += "and d.dicttypedesc like :dicttypedesc ";
		}
		if(StringUtils.isNotEmpty((String)map.get("enable"))){
			sql += "and d.enable = :enable ";
		}
		if(StringUtils.isNotEmpty((String)map.get("editable"))){
			sql += "and d.editable = :editable ";
		}
		if(StringUtils.isNotEmpty((String)map.get("dicttype"))){
			sql += "and d.dicttype = :dicttype ";
		}
	//	sql += "order by d.item  asc";
		dictinfoList = sqlDao_h.queryPage(sql,map,pageInfo,false);
		return dictinfoList;
	}

}
