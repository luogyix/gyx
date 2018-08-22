package com.agree.abt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@SuppressWarnings({ "rawtypes" })
public class IPJsonUtil {
	
	/**
	 * 通过节点类型、节点属性、节点参数，从指定的接口节点中查询匹配的第一个节点
	 * @param rootJson	查询的源节点
	 * @param nodeType_p	节点类型
	 * @param attr_p	节点属性
	 * @param param_p	节点参数
	 * @return	节点
	 * @throws Exception
	 */
	public static JSONObject getNode(JSONObject rootJson, String nodeType_p, Map attr_p, Map param_p) throws Exception{
		JSONObject retNode = null;
		String nodeType = rootJson.getString("nodeType");
		boolean equalProperty = false;
		if(nodeType.equals(nodeType_p)){
			equalProperty = true;
			if(attr_p != null){
				JSONObject attr = rootJson.getJSONObject("attr");
				for(Object key :attr_p.keySet()){
					Object attrvalue = attr.get(key);
					if( !(attrvalue != null && attrvalue.equals(attr_p.get(key))) ){
						equalProperty = true;
						break;
					}
				}
			}
			
			if(equalProperty && param_p != null){
				JSONObject param = rootJson.getJSONObject("param");
				for(Object key :param_p.keySet()){
					Object paramvalue = param.get(key);
					if( !(paramvalue != null && paramvalue.equals(param_p.get(key))) ){
						equalProperty = true;
						break;
					}
				}
			}
		}else{
			equalProperty = false;
		}
		
		if(equalProperty){
			retNode = rootJson;
		}else{
			JSONArray children = rootJson.getJSONArray("children");
			for (Object object : children) {
				JSONObject jsonObj = (JSONObject)object;
				retNode = getNode(jsonObj, nodeType_p, attr_p, param_p);
				if(retNode != null){
					break;
				}
			}
		}
		return retNode;
	}
	
	/**
	 * 通过节点类型、节点属性、节点参数，从指定的接口节点中查询匹配的所有节点
	 * @param rootJson	查询的源节点
	 * @param nodeType_p	节点类型
	 * @param attr_p	节点属性
	 * @param param_p	节点参数
	 * @return	节点集合
	 * @throws Exception
	 */
	public static List<JSONObject> getNodes(JSONObject rootJson, String nodeType_p, Map attr_p, Map param_p) throws Exception{
		List<JSONObject> list = new ArrayList<JSONObject>();
		String nodeType = rootJson.getString("nodeType");
		boolean equalProperty = false;
		if(nodeType.equals(nodeType_p)){
			equalProperty = true;
			JSONObject attr = rootJson.getJSONObject("attr");
			if(attr_p != null){
				for(Object key :attr_p.keySet()){
					Object attrvalue = attr.get(key);
					if( !(attrvalue != null && attrvalue.equals(attr_p.get(key))) ){
						equalProperty = true;
						break;
					}
				}
			}
			
			if(equalProperty && param_p != null){
				JSONObject param = rootJson.getJSONObject("param");
				for(Object key :param_p.keySet()){
					Object paramvalue = param.get(key);
					if( !(paramvalue != null && paramvalue.equals(param_p.get(key))) ){
						equalProperty = true;
						break;
					}
				}
			}
		}else{
			equalProperty = false;
		}
		
		if(equalProperty){
			list.add(rootJson);
		}
		
		JSONArray children = rootJson.getJSONArray("children");
		for (Object object : children) {
			JSONObject jsonObj = (JSONObject)object;
			List<JSONObject> retNodes = getNodes(jsonObj, nodeType_p, attr_p, param_p);
			if(retNodes.size()>0){
				list.addAll(retNodes);
			}
		}
		return list;
	}
}
