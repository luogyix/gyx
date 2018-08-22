package com.agree.abt.service.confmanager;

import java.util.List;
import java.util.Map;

import com.agree.abt.model.confManager.BtSysBranchNeig;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 周边网点配置接口
 * @ClassName: IAroundBranchService.java
 * @company 赞同科技
 * @author XiWang
 * @date 2015-1-14
 */
@SuppressWarnings({ "rawtypes" })
public interface IAroundBranchService {
	public List queryAroundBranch(Map<String,String> map, Page pageInfo) throws Exception;
	public ServiceReturn addAroundBranch(BtSysBranchNeig btSysBranchNeig) throws Exception;
	public ServiceReturn editAroundBranch(BtSysBranchNeig btSysBranchNeig) throws Exception;
	public ServiceReturn delAroundBranch(List<BtSysBranchNeig> ids) throws Exception;
}
