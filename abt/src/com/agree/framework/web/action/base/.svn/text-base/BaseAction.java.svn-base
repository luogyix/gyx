package com.agree.framework.web.action.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Dictionary;
import com.agree.framework.web.form.administration.Unit;
import com.agree.framework.web.form.administration.User;
import com.agree.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @ClassName: BaseAction 
 * @Description: TODO
 * @company agree   
 * @author haoruibing   
 * @date 2011-8-18 下午03:49:10 
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(BaseAction.class);
	
	public static final String AJAX_SUCCESS = "AJAX_SUCCESS";
	public static final String JSONP_SUCCESS = "JSONP_SUCCESS";
	public static final String SYSTEMUNITTREELIST = "systemunittreelist";
	private Page page;
	
	
	private String actionresult;
	private String actiondetail;

	public String getActiondetail() {
		return actiondetail;
	}

	public String getActionresult() {
		return actionresult;
	}
	
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public ServletContext getContext() {
		// return ApplicationConstants.getContext();
		return ContextLoader.getCurrentWebApplicationContext()
				.getServletContext();
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	public static JSONObject getInputJsonObject() throws Exception {
		return JSONObject.fromObject(getJsonString());
	}
	

	/**
	 * @Title: getInputJsonArray
	 * @Description: 得到页面传递值
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getInputJsonArray() throws Exception {
		return JSONArray.fromObject(getJsonString());
	}
	
	public void setActiondetail(String actiondetail) {
		this.actiondetail = actiondetail;
		ServletActionContext.getRequest().setAttribute("actiondetail", actiondetail);
	}
	public void setActionresult(String actionresult) {
		ServletActionContext.getRequest().setAttribute("result", actionresult);
		this.actionresult = actionresult;
	}
	
	
	public static  JSONObject convertServiceReturnToJson(ServiceReturn ret) throws Exception {
		JSONObject jsonobj = new JSONObject();
		
		if (ret.getSuccess()) {//返回正确
			jsonobj.accumulate(ServiceReturn.RESULT, ret.getSuccess());
			jsonobj.accumulate(ServiceReturn.MESSAGE, ret.getErrmsg());
			jsonobj.accumulate(ServiceReturn.OPERATERRESULT, ret.getOperaterResult());
			
			Set<Entry<String,Object>> entrySet = ret.getResultMap().entrySet();
			for(Entry<String,Object> entry : entrySet){
				Object value = entry.getValue();
				String key = entry.getKey();
				
				if(Iterable.class.isInstance(value)){
					JSONArray array = JSONArray.fromObject(value);
					jsonobj.accumulate(key, array);
				}else if(String.class.isInstance(value)){
					jsonobj.accumulate(key, value);
				}else if(Long.class.isInstance(value)){
					jsonobj.accumulate(key, value);
				}else{
					JSONObject obj = JSONObject.fromObject(value);
					jsonobj.accumulate(key, obj);
				}
			}
		} else {//返回错误
			jsonobj.accumulate(ServiceReturn.RESULT, ret.getSuccess());
			jsonobj.accumulate(ServiceReturn.MESSAGE, ret.getErrmsg());
			jsonobj.accumulate(ServiceReturn.OPERATERRESULT, ret.getOperaterResult());
			jsonobj.accumulate("results", 100);
		}
		return jsonobj;
	}
	
	/** 
	 * @Title: getJsonString 
	 * @Description: 得到页面传递值
	 * @return
	 * @throws Exception    
	 */ 
	public static  String getJsonString() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Object result=  request.getAttribute("actiondetail");
		if(result==null){
			try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
			BufferedReader reader = null;
			StringBuffer sb = new StringBuffer();
			try {
				reader = request.getReader();
				String str = null;
				while ((str = reader.readLine()) != null) {
					sb.append(str);
				}
			} catch (IOException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
			
			return sb.toString();
			
		}else{
			return result.toString();
		}
	}
	
	/** 
	 * @Title: getJsonStringBeforMethod 
	 * @Description: 在方法前得到页面传递的参数
	 * @return
	 * @throws Exception    
	 */ 
	public static  String getJsonStringBeforMethod() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = request.getReader();
			String str = "";
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}catch (java.lang.IllegalStateException e){
			logger.info("IllegalStateException",e);
		}
		
		return sb.toString();
	}
	
	protected static  User getLogonUser(boolean isBasic) throws Exception{
		User attribute = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		if(!isBasic){
			return attribute;
		}else{
			User logonUser = attribute;
			User user = new User();
			user.setUnit(new Unit(logonUser.getUnit()));
			user.setUserid(logonUser.getUserid());
			user.setUsername(logonUser.getUsername());
			user.setUsertype(logonUser.getUsertype());
			return user;
		}
	}
	
	/**
	 * 根据用户所在机构获取机构树
	 * @return
	 * @throws Exception
	 */
	public static  List<Unit> getUnitTreeList() throws Exception{
		
		User logonUser = getLogonUser(false);
		
		if(logonUser==null){
			throw new AppException("用户已失效,请重新登录");
		}
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		
		//Unit current_tree_node = unit_tree.findNodeById(logonUser.getUnit().getUnitid());
		//根据用户信息获得机构的id
		String unitId = logonUser.getUnit().getUnitid();
		
		//根据用户的机构id 获得机构数据
		Unit current_tree_node = unit_tree.findNodeById(unitId);
		
		//获得机构树
		List<Unit> unit_list = current_tree_node.getUnitTreeList(0);
		
		return unit_list;
	}
	/**
	 * 获取完整的机构树
	 * @return
	 * @throws Exception
	 */
	public static List<Unit> getAllUnitTreeList() throws Exception{
		User logonUser = getLogonUser(false);
		
		if(logonUser==null){
			throw new AppException("用户已失效,请重新登录");
		}
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		//Unit current_tree_node = unit_tree.findNodeById("999888");
		List<Unit> unit_list = unit_tree.getUnitTreeList(0);
		return unit_list;
		
	}
	public static  List<Unit> getUnitListAtLevel(int level) throws Exception{
		User logonUser = getLogonUser(false);
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		Unit current_tree_node = unit_tree.findNodeById(logonUser.getUnit().getUnitid());
		List<Unit> unit_list = current_tree_node.getDescendantAtLevel(level);
		List<Unit> out_list = new ArrayList<Unit>();
		if(unit_list == null){
			return out_list;
		}
		for(Unit unit : unit_list){
			Unit temp = new Unit(unit);
			out_list.add(temp);
		}
		return out_list;
	}
	

	public Page getPage() {
		return page;
	}
	
	public Page getPage(JSONObject jsonObj) throws Exception {
		page = new Page();
		String limit = "";
		String start = "";		
		String pageflag = "";
		try {
			start = jsonObj.getString("start");
		} catch (JSONException e) {	}
		try { 
			limit = jsonObj.getString("limit");
		} catch (JSONException e) {	}
		try {
			pageflag = jsonObj.getString("pageflag");
		} catch (JSONException e) {	}
		
		if(StringUtil.isNotBlank(limit)){
			int l = Integer.parseInt(limit);
			page.setLimit(l);
		}
		if(StringUtil.isNotBlank(start)){
			int s = Integer.parseInt(start);
			page.setStart(s);
		}
		if(StringUtil.isNotBlank(pageflag)){
			int f = Integer.parseInt(pageflag);
			page.setPageflag(f);
		}
		if(jsonObj.containsKey("sortString")){
			String sort=jsonObj.getString("sortString");
			if(StringUtil.isNotBlank(sort))
			page.setSortString(sort);
		}
		return page;
	}
	
	public Page getPage(JSONObject jsonObj,String str) throws Exception {
		page = new Page();
		String limit = jsonObj.getString("limit");
		String start = jsonObj.getString("start");
		String pageflag = jsonObj.getString("pageflag");
		
		if(StringUtil.isNotBlank(limit)){
			int l = Integer.parseInt(limit);
			page.setLimit(l);
		}
		if(StringUtil.isNotBlank(start)){
			int s = Integer.parseInt(start);
			page.setStart(s);
		}
		if(StringUtil.isNotBlank(pageflag)){
			int f = Integer.parseInt(pageflag);
			page.setPageflag(f);
		}
		if(jsonObj.containsKey("sortString")){
			String sort=jsonObj.getString("sortString");
			if(StringUtil.isNotBlank(sort))
				page.setSortString(sort);
		}
		return page;
	}
	
	public void setPage(Page page) {
		this.page = page;
	}
	
	/** 
	 * @Title: setActionResult 
	 * @param @param pageInfo
	 * @param @param list
	 * @param @param ret
	 * @param @throws Exception    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	
	protected void setActionResult(Page pageInfo, List list, ServiceReturn ret)
			throws Exception {
		ret.put(ServiceReturn.FIELD1, list);							//FIELD1放置业务数据
		ret.put(ServiceReturn.FIELD2, pageInfo);						//FIELD2放置分页信息
		JSONObject retObj = convertServiceReturnToJson(ret);
		String json = retObj.toString();
		this.setActionresult(json);
	}
	/**
	 * 整合导出报表于该类中
	 * @param path          模板路径
	 * @param objs 			数据集合
	 * @param formClass		文件名称
	 * @param map           参数集合
	 * @throws Exception
	 */
	public Boolean doExcel(String path,List objs,Map map,String filename) throws Exception{
		Date dte = new Date();
		DateFormat dfm = new SimpleDateFormat("yyyyMMdd");// 设置显示格式
		String formatDate = dfm.format(dte);
		HttpServletResponse response = ServletActionContext.getResponse();
	//	HttpServletRequest request = ServletActionContext.getRequest();
	    response.reset();
	    response.setContentType("application/vnd.ms-excel");
	    //使用excel_名字加时间作为导出excel的名字
	    //这里进行了改造,文件名重新更改了下
	//	String fname=ExcelUtils.encodeChineseDownloadFileName(request, filename);
	//	response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".xls\"");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + new String( filename.getBytes
					("gb2312"), "ISO8859-1" ) + formatDate + ".xls\"");
		
	    InputStream in = null;
	    OutputStream out = null;
	    HSSFWorkbook workbook;
	    try 
	    {
	        //路径必须使用utf-8解码 
	    	String newpath="";
		   	newpath=java.net.URLDecoder.decode(this.getClass().getClassLoader().getResource("/resources/excelmodel").getPath(),"utf-8");
		   	in =  new FileInputStream((newpath+path));
	        Map beans = new HashMap();   
	        beans.put("listdata", objs); 
	        beans.put("reporttime", ""+formatDate); 
	        if(map!=null)
	        beans.putAll(map);
	        XLSTransformer transformer = new XLSTransformer();   
	        workbook=transformer.transformXLS(in, beans);  
	        out = response.getOutputStream();
	        workbook.write(out);
	        out.flush();
	        return true;
	    } catch(IOException e)
	    {
	    	throw e;
	    }
	    catch(ParsePropertyException e)
	    {
	    	throw e;
	    }
	    catch(Exception e)
	    {
	    	logger.error(e.getMessage(),e);
	    }
	    finally 
	    {
	        if (in != null) 
	        {
	            try 
	            {
					in.close();
				} catch (IOException e) 
				{
					throw e;
				}
	        }
	        if (out != null) 
	        {
	            try 
	            {
	            	out.close();
				} catch (IOException e) 
				{
					throw e;
				}
	        }
	    }
		return false;
	}
	/**
	 * 动态列
	 * @param path
	 * @param objs
	 * @param formClass
	 * @param cols
	 * @param colNames
	 * @return
	 * @throws Exception
	 */
	public Boolean doExcel(String path,List objs,Map map,List cols,List colNames,String filename) throws Exception{
		Date dte = new Date();
		DateFormat dfm = new SimpleDateFormat("yyyyMMdd");// 设置显示格式
		String formatDate = dfm.format(dte);
	//	HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.reset();
	    response.setContentType("application/vnd.ms-excel");
	    
	    //这里进行了改造,文件名重新更改了下
	//	String fname=ExcelUtils.encodeChineseDownloadFileName(request, filename + formatDate);
	//	response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".xls\"");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + new String( filename.getBytes
				("gb2312"), "ISO8859-1" ) + formatDate + ".xls\"");
	    InputStream in = null;
	    OutputStream out = null;
	    HSSFWorkbook workbook;
	    try 
	    {
	        //路径必须使用utf-8解码 
	    	String newpath="";
		   	newpath=java.net.URLDecoder.decode(this.getClass().getClassLoader().getResource("/resources/excelmodel").getPath(),"utf-8");
		   	in =  new FileInputStream((newpath+path));
	        Map beans = new HashMap();   
	        beans.put("listdata", objs); 
	        beans.put("reporttime", "报表生成时间："+formatDate); 
	        if(map!=null)
	        beans.putAll(map);
	        if(cols!=null){
	        	beans.put("cols", cols);
	        }
	        if(colNames != null){
	        	beans.put("colNames", colNames);
	        }
	        XLSTransformer transformer = new XLSTransformer();   
	        workbook=transformer.transformXLS(in, beans);  
	        out = response.getOutputStream();
	        workbook.write(out);
	        out.flush();
	        return true;
	    } catch(IOException e)
	    {
	    	logger.error(e.getMessage(),e);
	    }
	    catch(ParsePropertyException e)
	    {
	    	logger.error(e.getMessage(),e);
	    }
	    finally 
	    {
	        if (in != null) 
	        {
	            try 
	            {
					in.close();
				} catch (IOException e) 
				{
					logger.error(e.getMessage(),e);
				}
	        }
	        if (out != null) 
	        {
	            try 
	            {
	            	out.close();
				} catch (IOException e) 
				{
					logger.error(e.getMessage(),e);
				}
	        }
	    }
		return false;
	}
	/**
	 * 将excel文件写入服务器端
	 * @param path
	 * @param filename
	 * @param objs
	 * @param formClass
	 * @return
	 * @throws Exception
	 */
	public Boolean doExceltoServer(String path,List objs,Map map,String filename) throws Exception{
		Date dte = new Date();
		DateFormat dfm = new SimpleDateFormat("yyyyMMdd");// 设置显示格式
		String formatDate = dfm.format(dte);
	    InputStream in = null;
	    OutputStream out = null;
	    HSSFWorkbook workbook;
	    try 
	    {
	        //路径必须使用utf-8解码 
	    	String newpath="";
		   	newpath=java.net.URLDecoder.decode(this.getClass().getClassLoader().getResource("/resources/excelmodel").getPath(),"utf-8");
		   	in =  new FileInputStream((newpath+path));
	        Map beans = new HashMap();   
	        beans.put("listdata", objs); 
	        beans.put("reporttime", "报表生成时间："+formatDate); 
	        if(map!=null)
	        beans.putAll(map);
	        XLSTransformer transformer = new XLSTransformer();   
	        workbook=transformer.transformXLS(in, beans);  
	        out = new FileOutputStream(new File(newpath+"/"+filename+".xls"));
	        
	        workbook.write(out);
	        out.flush();
	        return true;
	    } catch(IOException e)
	    {
	    	logger.error(e.getMessage(),e);
	    }
	    catch(ParsePropertyException e)
	    {
	    	logger.error(e.getMessage(),e);
	    }
	    finally 
	    {
	        if (in != null) 
	        {
	            try 
	            {
					in.close();
				} catch (IOException e) 
				{
					logger.error(e.getMessage(),e);
				}
	        }
	        if (out != null) 
	        {
	            try 
	            {
	            	out.close();
				} catch (IOException e) 
				{
					logger.error(e.getMessage(),e);
				}
	        }
	    }
		return false;
	}

	/** 
	 * @Title: getMyUnits 
	 * @Description: 获得用户的下属部门ID集合
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return List    返回类型 
	 * @throws 
	 */ 
	public List getMyUnits() throws Exception{
		List myUnits=new ArrayList();
		//获得当前用户
		User user=getLogonUser(true);
		//获得全部的unit集合
		List<Unit> units=(List<Unit>) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITS);
		String userUnitId=String.valueOf(user.getUnit().getUnitid());//获得用户部门id
		for(Unit unit:units){
			String unitlist=unit.getUnitlist();
			String[] strs=unitlist.split("\\|");//获得部门上级菜单数组
			for(int i=0;i<strs.length;i++){
				if(strs[i].equals(userUnitId)){//如果该部门上级部门里包含该用户的部门则要将该部门放到集合中
					myUnits.add(unit.getUnitid());
					break;
				}
			}
		}
		return myUnits;
	}

	/** 
	 * @Title: setDictValue 
	 * @Description:       为返回集合翻译字段,
	 *  * @param object    准备翻译的对象
	 * @param key          准备翻译的字段名
	 * @param itemId       对应字段的字典id
	 * @param dictList     应用产品字典集合
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */ 
	public void setDictValue(Object object,String key,String itemId,Map listmap) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String value=null;
		List<Dictionary> dictList=(List<Dictionary>) listmap.get(itemId);
		if(dictList==null){
			return;
		}
		for(Dictionary entry : dictList){
			String code = entry.getDictValue();
			//取出该字段的值
			String getMethodName = "get" + key.substring(0, 1).toUpperCase() + key.substring( 1 );   
			String v=null;
			Map map=null;
			if(object instanceof Map){
				map=(Map)object;
				Object map_value = map.get(key);
				if(map_value!=null){
					v=map_value.toString();
				}else{
					v="";
				}
			}else{
				Method gm = object.getClass().getDeclaredMethod(getMethodName); 
				Object invokeValue = gm.invoke(object);
				if(invokeValue!=null){
					v= invokeValue.toString(); 
				}else{
					v="";
				}
			}
		     //如果code和该字段值相等，取出其字典值，并赋予他
			if(code.equals(v)){
				String[] values = entry.getDictValueDesc().split("-");
				value=values.length>1?values[1]:"";
				if(object instanceof Map){
					//如果传入的对象为Map
					map.put(key, value);
				}else{
					//如果传入的对象为业务对象
				     String setMethodName = "set" + key.substring(0, 1).toUpperCase() + key.substring( 1 );    
				     Method sm = object.getClass().getDeclaredMethod(setMethodName,String.class); 
				     sm.invoke(object,value); 
				}
			}
		}
	}

	public Unit getUnitByID(String unitID) {
		if (unitID == null || "".equals(unitID)) {
			return null;
		}
		List<Unit> units = (List<Unit>) ServletActionContext
				.getServletContext().getAttribute(
						ApplicationConstants.SYSTEMUNITS);
		Unit unit = null;
		for (Unit curUnit : units) {
			if (unitID.equals(curUnit.getUnitid())) {
				unit = curUnit;
				break;
			}
		}
		return unit;
	}
	
	public JSONObject getParamString(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, String[]> map = request.getParameterMap();
		Iterator<String> it = map.keySet().iterator();
		JSONObject json = new JSONObject();
		while(it.hasNext()){
			String key = it.next();
			if(!key.equals("jsoncallback")&&!key.equals("_")){
				json.accumulate(key, request.getParameter(key));
			}
		}
		return json;
	}
}

