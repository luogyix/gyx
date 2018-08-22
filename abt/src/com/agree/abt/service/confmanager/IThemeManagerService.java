package com.agree.abt.service.confmanager;

import java.util.List;

import net.sf.json.JSONObject;

import com.agree.abt.model.confManager.ThemeInfo;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;

public interface IThemeManagerService {
	public List<ThemeInfo> queryThemeInfo(String branch);
	public List<ThemeInfo> queryTheme4Page(JSONObject jsonObj, Page pageInfo, String branch) throws Exception;
	public ServiceReturn addThemeInfo(String jsonString) throws Exception;
	public ServiceReturn editThemeInfo(String jsonString) throws Exception;
	public ServiceReturn delThemeInfo(String theme_id) throws Exception;
}
