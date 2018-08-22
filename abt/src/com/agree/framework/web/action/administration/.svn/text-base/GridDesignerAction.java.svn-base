/**   
 * @Title: GridDesignerAction.java 
 * @Package com.agree.framework.web.action.administration 
 * @Description 
 * @company agree   
 * @author haoruibing   
 * @date 2012-11-6 下午02:57:33 
 * @version V1.0   
 */

package com.agree.framework.web.action.administration;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.agree.framework.util.VelocityUtil;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.util.FileUtil;

/**
 * @ClassName: GridDesignerAction
 * @Description
 * @company agree
 * @author haoruibing
 * @date 2012-11-6 下午02:57:33
 * 
 */
@Controller
@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class GridDesignerAction extends BaseAction {
	private static final long serialVersionUID = 3426722660951267843L;
	private Log logger = LogFactory.getLog(GridDesignerAction.class);

	public String generatePage() throws Exception {
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(Map.class);
		List<Map> values = (List<Map>) JSONArray
				.toCollection(jsonArray, config);
		Map map = new HashMap();
		map.put("list", values);

		Map properties = values.get(0);
		values.remove(0);
		map.putAll(properties);

		int searchline = 0;
		int formline = 0;

		int serchnum = 0;
		int gridnum = 0;
		int formnum = 0;
		for (Map value : values) {
			int searchtemp = Integer.parseInt(value.get("search_lineno")
					.toString());
			if (searchtemp > searchline)
				searchline = searchtemp;

			int formtemp = Integer
					.parseInt(value.get("form_lineno").toString());
			if (formtemp > formline)
				formline = formtemp;

			if (value.get("showInSearch").toString().equals("true"))
				serchnum++;
			if (value.get("showInGrid").toString().equals("true"))
				gridnum++;
			if (value.get("showInForm").toString().equals("true"))
				formnum++;
		}
		map.put("searchline", searchline + 1);
		map.put("formline", formline + 1);
		map.put("serchnum", serchnum);
		map.put("gridnum", gridnum);
		map.put("formnum", formnum);

		String page = VelocityUtil.merge("page/gridTemplate.vm", map, "utf-8");

		HttpServletRequest request = ServletActionContext.getRequest();
		String usercode = super.getLogonUser(false).getUsercode();
		String filename = usercode + "_page.jsp";

		String path = request.getRealPath("/") + "temp/";
		File file = new File(path);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			logger.info("//不存在");
			file.mkdir();
		}
		FileUtil.writeFile(path + filename, new String(page.getBytes(), "gbk"));

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put("page", page);
		sRet.put("pagename", filename);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * @Title: savePage
	 * @Description: 保存页面
	 * @return
	 * @throws Exception
	 */
	public void downFile() throws Exception {
		String usercode = super.getLogonUser(false).getUsercode();

		HttpServletRequest request = ServletActionContext.getRequest();
		String filename = request.getRealPath("/") + "temp/" + usercode
				+ "_page.jsp";
		// 读取文件
		String file = FileUtil.readFile(filename);
		// 文件输出流
		HttpServletResponse response = ServletActionContext.getResponse();
		response.reset();
		response.setHeader("Content-Type", "application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ usercode + "_page.jsp\"");
		response.setCharacterEncoding("utf-8");
		OutputStream out = response.getOutputStream();
		out.write(file.getBytes("utf-8"));
		out.flush();
		out.close();

	}
}
