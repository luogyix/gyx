package com.agree.abt.service.confmanager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.agree.abt.model.confManager.BtSysBranchNeig;
import com.agree.abt.service.confmanager.IAroundBranchService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.service.base.BaseService;

/**
 * 周边网点配置实现类
 * @ClassName: AroundBranchServiceImpl.java
 * @company 赞同科技
 * @author XiWang
 * @date 2015-1-14
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AroundBranchServiceImpl extends BaseService implements IAroundBranchService {
	
	private static final Logger logger = LoggerFactory.getLogger(AroundBranchServiceImpl.class);
	@Transactional
	public List<?> queryAroundBranch(Map<String,String> map, Page pageInfo) throws Exception {
		List<?> list = null;
		HashMap<String,String> paramaHashMap = new HashMap<String,String>();
		String hql = "select new map(branch, branch_neighbous, note, branch_temp) " +
				" from BtSysBranchNeig where branch in ( select unitid  from Unit where unitlist like :branch)";
		paramaHashMap.put("branch", "%" + map.get("branch") + "%");
		list = sqlDao_h.queryPage(hql,paramaHashMap,pageInfo,false);//此方法传入的hql为完整的hql，不需传入排序参数
		//[755003, 755001|755002, null, null]
		List list2 = new ArrayList();
		for (Object obj : list) {
			Map map2 = (Map)obj;
			BtSysBranchNeig neig = new BtSysBranchNeig();
			neig.setBranch(map2.get("0").toString());
			neig.setBranch_neighbous(map2.get("1").toString());
			neig.setNote(map2.get("2")==null?"":map2.get("2").toString());
			list2.add(neig);
		}
		return list2;
	}
	
	@Transactional
	public ServiceReturn addAroundBranch(BtSysBranchNeig btSysBranchNeig) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		//判断有没有,有的话报错
		Map<String,String> map=new HashMap<String,String>();
		map.put("branch", btSysBranchNeig.getBranch());
		Long count = sqlDao_h.getRecordCount("select count(*) from BtSysBranchNeig where branch=:branch",map);
		if(count > 0){//该机构的周边信息已存在
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("该机构的周边信息已存在！请删除后再添加");
		}else{//添加
			sqlDao_h.saveRecord(btSysBranchNeig);
		}
		return ret;
	}
	
	@Transactional
	public ServiceReturn editAroundBranch(BtSysBranchNeig btSysBranchNeig)
			throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sqlDao_h.updateRecord(btSysBranchNeig);
		return ret;
	}
	
	@Transactional
	public ServiceReturn delAroundBranch(List<BtSysBranchNeig> ids) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		for (BtSysBranchNeig neig :ids){
			//删除
			Object[] paramuser={neig.getBranch()};
			try {
				sqlDao_h.excuteHql("delete from BtSysBranchNeig where branch = ?",paramuser);
			} catch (Exception e) {
				ret.setSuccess(false);
				ret.setOperaterResult(false);
				ret.setErrmsg("机构删除失败,机构号:" + neig.getBranch());
				logger.error(e.getMessage(),e);
				return ret;
			}
		}
		return ret;
	}
}
