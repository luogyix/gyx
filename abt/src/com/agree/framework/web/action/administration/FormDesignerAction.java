/**   
 * @Title: GridDesignerAction.java 
 * @Package com.agree.framework.web.action.administration 
 * @Description: TODO 
 * @company agree   
 * @author haoruibing   
 * @date 2012-11-6 下午02:57:33 
 * @version V1.0   
 */

package com.agree.framework.web.action.administration;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.agree.framework.util.VelocityUtil;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.util.FileUtil;

/**
 * @ClassName: GridDesignerAction
 * @Description: TODO
 * @company agree
 * @author haoruibing
 * @date 2012-11-6 下午02:57:33
 * 
 */
@Controller
@SuppressWarnings({"deprecation","unchecked","rawtypes"})
public class FormDesignerAction extends BaseAction {

	private static final long serialVersionUID = -909565824367110470L;

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
		map.put("listsize", values.size());

		Map properties = values.get(0);
		values.remove(0);
		map.putAll(properties);

		String page = VelocityUtil.merge("page/formTemplate.vm", map, "utf-8");

		HttpServletRequest request = ServletActionContext.getRequest();
		String usercode = super.getLogonUser(false).getUsercode();
		String filename = usercode + "_formpage.jsp";
		FileUtil.writeFile(request.getRealPath("/") + "temp/" + filename,
				new String(page.getBytes(), "gbk"));

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
				+ "_formpage.jsp";
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
