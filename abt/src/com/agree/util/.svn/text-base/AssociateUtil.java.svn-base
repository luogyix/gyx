package com.agree.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ognl.ASTChain;
import ognl.ASTConst;
import ognl.ASTProperty;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;

/**
 * 关联关系util
 */
@SuppressWarnings( { "rawtypes", "unused", "unchecked" })
public class AssociateUtil {

	private static Log logger = LogFactory.getLog(FileUtil.class);
	public static Map ognlExpressContext = new HashMap();


	/**
	 * 根据用例json递归获取关联字段及数据
	 * 
	 * @param jsonObject
	 *            用例json
	 * @param reqMsgRelKeyValue
	 *            请求关联字段及数据
	 * @return
	 */
	public static JSONObject getMessageKeyValue(JSONObject jsonObject,
			JSONObject reqMsgRelKeyValue) {
		if (jsonObject.getString("nodeType").equals("Field")
				&& reqMsgRelKeyValue.get(jsonObject.getJSONObject("attr")
						.getString("fieldName")) != null) {
			reqMsgRelKeyValue.put(jsonObject.getJSONObject("attr").getString(
					"fieldName"), jsonObject.getJSONObject("attr").get(
					"fieldValue"));
		}

		JSONArray children = (JSONArray) jsonObject.get("children");
		for (int i = 0, j = children.size(); i < j; i++) {
			getMessageKeyValue((JSONObject) children.get(i), reqMsgRelKeyValue);
		}

		return reqMsgRelKeyValue;
	}

	/**
	 * 自动回执根据来报报文 原始数据 替换回执用例报文数据
	 * 
	 * @param jsonObject
	 *            回执用例片段json
	 * @param resKeyValue
	 *            回执用例要替换的字段及值
	 * @return msg
	 */
	public static JSONObject setResMsgKeyValue(JSONObject jsonObject,
			JSONObject resKeyValue) {
		if (jsonObject.getString("nodeType").equals("Field")
				&& resKeyValue.get(jsonObject.getJSONObject("attr").getString(
						"fieldName")) != null) {
			jsonObject.getJSONObject("attr").put(
					"fieldValue",
					resKeyValue.get(jsonObject.getJSONObject("attr").getString(
							"fieldName")));
		}

		JSONArray children = (JSONArray) jsonObject.get("children");
		for (int i = 0, j = children.size(); i < j; i++) {
			setResMsgKeyValue((JSONObject) children.get(i), resKeyValue);
		}

		return jsonObject;
	}

	/**
	 * @Description 从拆出来的报文Map中根据字段名称获得字段值
	 * @param fieldName
	 *            字段名称
	 * @param msgMap
	 *            报文拆包后的Map
	 * @return
	 * @throws Exception
	 */
	// public static Object getFieldValueByNameFromMsgMap(String fieldName, Map
	// msgMap) throws Exception{
	// Object fieldValue = null;
	// if(msgMap.containsKey(fieldName) &&
	// !Map.class.isInstance(msgMap.get(fieldName))){
	// fieldValue = msgMap.get(fieldName);
	// }else{
	// Iterator keys = msgMap.keySet().iterator();
	// while(keys.hasNext()) {
	// String key = (String) keys.next();
	// if(Map.class.isInstance(msgMap.get(key))){
	// Map map= (Map)msgMap.get(key);
	// getFieldValueByNameFromMsgMap(fieldName, map);
	// }
	// }
	// }
	// return fieldValue;
	// }
	public static Object getFieldValueByNameFromMsgMap(String fieldName,
			Map msgMap) throws Exception {
		OgnlContext ognlContext = (OgnlContext) Ognl.createDefaultContext(msgMap);

		Object o = Ognl.getValue(getOgnlExpress(fieldName),
				ognlContext, msgMap);
		return o;
	}
	static public Object getOgnlExpress(String express) throws OgnlException {
		if(express.contains("-")){
			String[] ss = express.split("\\.");
			ASTChain aChain = new ASTChain(ss.length-1);
			for (int i = 0; i < ss.length; i++) {
				ASTConst ast  = new ASTConst(0);
				ASTProperty asp = new ASTProperty(0);
				ast.jjtSetParent(asp);
				asp.jjtAddChild(ast, 0);
				ast.setValue(ss[i]);
				
				aChain.jjtAddChild(asp, i);
			}
			return aChain;
		}
		
		Object o = ognlExpressContext.get(express);
		if (o == null) {
			o = Ognl.parseExpression(express);
			ognlExpressContext.put(express, o);
		}
		return o;
	}
	/**
	 * @Description 从用例json中根据字段名称获得字段值
	 * @param fieldName
	 *            字段名称
	 * @param tcContent
	 *            用例片段json
	 * @return
	 * @throws Exception
	 */
	public static Object getFieldValueByNameFromTCContent(
			List<JSONObject> tcContent, String fieldName) throws Exception {
		Object fieldValue = null;
		for (JSONObject tcpartJson : tcContent) {
			fieldValue = getFieldValueFromJson(tcpartJson, "fieldName",
					fieldName);
			if (fieldValue != null)
				break;
		}
		return fieldValue;
	}

	/**
	 * @Description 从用例json中根据字段唯一的标识uuid获得字段值
	 * @param uuid
	 *            字段唯一标识
	 * @param tcContent
	 *            用例片段json
	 * @return
	 * @throws Exception
	 */
	public static Object getFieldValueByUUIDFromTCContent(
			List<JSONObject> tcContent, String uuid) throws Exception {
		Object fieldValue = null;
		for (JSONObject tcpartJson : tcContent) {
			fieldValue = getFieldValueFromJson(tcpartJson, "UUID", uuid);
			if (fieldValue != null)
				break;
		}
		return fieldValue;
	}

	/**
	 * @Description 从用例片段json根据参数名称（节点属性中的）获得字段值
	 * @param paramkey
	 *            参数名称
	 * @param paramvalue
	 *            参数值
	 * @param tcpartJson
	 *            用例片段json
	 * @return
	 * @throws Exception
	 */
	private static Object getFieldValueFromJson(JSONObject tcpartJson,
			String paramkey, String paramvalue) throws Exception {
		Object fieldValue = null;
		if (tcpartJson.getString("nodeType").equals("Field")
				&& paramvalue.equals(tcpartJson.getJSONObject("attr")
						.getString(paramkey))) {
			fieldValue = tcpartJson.getJSONObject("attr").get("fieldValue");
		} else {
			JSONArray children = (JSONArray) tcpartJson.get("children");
			for (int i = 0, j = children.size(); i < j; i++) {
				getFieldValueFromJson((JSONObject) children.get(i), paramkey,
						paramvalue);
			}
		}
		return fieldValue;
	}

	/**
	 * 将用例文件jsonArray转换为map, 键值(mapkey, fieldValue)
	 * 
	 * @param mapkey
	 * @param tcContent
	 * @return
	 */
	public static Map<String, Object> createMsgFieldvalueMap(String mapkey,
			List<JSONObject> tcContent) {
		Map fieldMap = new HashMap<String, Object>();
		for (JSONObject tcpartJson : tcContent) {
			fieldMap = createMsgpartFieldvalueMap(mapkey, tcpartJson, fieldMap);
		}
		return fieldMap;
	}

	/**
	 * 将用例jsonArray里的一片段json转换为map, 键值(mapkey, fieldValue)
	 * 
	 * @param mapkey
	 * @param tcpartJson
	 * @param fieldMap
	 * @return
	 */
	private static Map<String, Object> createMsgpartFieldvalueMap(
			String mapkey, JSONObject tcpartJson, Map<String, Object> fieldMap) {
		JSONObject attr = tcpartJson.getJSONObject("attr");
		if (tcpartJson.getString("nodeType").equals("Field")) {
			fieldMap.put(attr.getString(mapkey), attr.get("fieldValue"));
		} else {
			JSONArray children = (JSONArray) tcpartJson.get("children");
			for (int i = 0, j = children.size(); i < j; i++) {
				JSONObject child = (JSONObject) children.get(i);
				// 去除多余的分支
				if (!(child.get("pvtParam") != null
						&& ((JSONObject) child.get("pvtParam")).get("hidden") != null && "true"
						.equals(((JSONObject) child.get("pvtParam")).get(
								"hidden").toString()))) {
					createMsgpartFieldvalueMap(mapkey, child, fieldMap);
				}
			}
		}
		return fieldMap;
	}
}
