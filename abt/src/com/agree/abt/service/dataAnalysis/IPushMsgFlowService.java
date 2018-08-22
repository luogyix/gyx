package com.agree.abt.service.dataAnalysis;

import java.util.List;
import java.util.Map;

import com.agree.framework.dao.entity.Page;

/**
 * 消息流水查询
 * service接口
 * @ClassName: IPushMsgFlowService 
 * @company 赞同科技   
 * @author  zhaojianyong
 * @date 2015-6-12 下午04:45:20 
 *
 */
@SuppressWarnings("all")
public interface IPushMsgFlowService {
	public List queryPushMsgList(Map map ,Page pageInfo) throws Exception;
}
