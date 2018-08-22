package com.agree.abt.service.confmanager;

import java.util.List;

import net.sf.json.JSONObject;

import com.agree.abt.model.confManager.CompScreenDisplay;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;

public interface ICompScreenDisplayService {
	public List<CompScreenDisplay> queryCompScreenInfo(String branch);
	public List<CompScreenDisplay> queryCompScreen4Page(JSONObject jsonObj, Page pageInfo, String branch) throws Exception;
	public ServiceReturn addCompScreenInfo(String jsonString) throws Exception;
	public ServiceReturn editCompScreenInfo(String jsonString) throws Exception;
	public ServiceReturn delCompScreenInfo(String jsonString) throws Exception;

}
