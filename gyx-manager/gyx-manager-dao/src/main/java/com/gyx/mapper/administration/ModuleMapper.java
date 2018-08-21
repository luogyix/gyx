package com.gyx.mapper.administration;

import java.util.List;
import java.util.Map;

import com.gyx.administration.Module;

public interface ModuleMapper {
	public List<Module> selectModuleInRoleIdList(Map<String,List<String>> map );
}
