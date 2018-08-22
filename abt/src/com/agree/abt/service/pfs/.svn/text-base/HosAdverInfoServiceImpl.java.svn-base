package com.agree.abt.service.pfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.model.confManager.BtTsfAdvert;
import com.agree.abt.model.pfs.BtHosAdvert;
import com.agree.abt.service.confmanager.impl.AdvertParamServiceImpl;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;
import com.agree.util.DateUtil;
import com.agree.util.FileUtil;
import com.agree.util.PropertiesUtils;

@SuppressWarnings({ "unchecked", "rawtypes","unused"})
public class HosAdverInfoServiceImpl  extends BaseService implements IHosAdverInfoService
{
	private static final Log loger=LogFactory.getLog(AdvertParamServiceImpl.class);//日志
	PropertiesUtils pro = new PropertiesUtils();
	HttpServletRequest request = ServletActionContext.getRequest();
	/**
	 * 查询广告信息
	 */
	public List<Object> queryHosAdvertParam(User user, Page pageinfo)throws Exception 
	{
		List<Object> list = new ArrayList<Object>();
		String hql = "from BtHosAdvert where branch =:branch";
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("branch", user.getUnit().getUnitid()); 
		list = sqlDao_h.queryPage(hql, map, pageinfo, false);
		if(list.size()==0)
		{
			String[] unitlist = user.getUnit().getUnitlist().split("\\|");
			List<String> list1 = new ArrayList<String>();
			for (int i = 0; i < unitlist.length; i++) {
				list1.add(unitlist[i]);
			}
			//查询上级机构
			hql ="from BtHosAdvert where branch in (:branch)";
			map.put("branch", list1);
			list = sqlDao_h.queryPage(hql, map, pageinfo, false);

		}
		return list;
	}
	/**
	 * 添加广告信息
	 *
	 */
	public ServiceReturn addHosAdvertParam(String json) throws Exception 
	{
		//返回登录用户信息
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");		
		//得到页面传递值
		JSONObject jsonObj = JSONObject.fromObject(json);
		//手持广告实体类
		BtHosAdvert paramCfg = (BtHosAdvert)JSONObject.toBean(jsonObj, BtHosAdvert.class);
		//得到图片路径
		String photo_path = paramCfg.getPhoto_path().replace((CharSequence)"\n", (CharSequence)"");
		//图片路径
		paramCfg.setPhoto_path(photo_path);
		//得到当前系统时间
		String create_time = DateUtil.getDateTime();
		//添加时间
		paramCfg.setCreate_time(create_time);
		//图片数量
		String photo_num = Integer.toString(photo_path.split(";").length);
		paramCfg.setPhoto_num(photo_num);
		//图片名称
		String advert_rotation = "";
		String[] photo_name = photo_path.split(";");
		for(int i = 0;i < photo_name.length; i++)
		{
			String str = photo_name[i];
			advert_rotation += str.substring(str.lastIndexOf("/")+1)+",";
		}
		paramCfg.setAdvert_rotation(advert_rotation);
		//轮播时间
		paramCfg.setAdvert_space(paramCfg.getAdvert_space());
		//查询该机构下的记录
		String hql = "select count(*) from BtHosAdvert where branch='"+paramCfg.getBranch()+"'";
		Long count = sqlDao_h.getRecordCount(hql);
		if(count > 0)
		{
			throw new Exception("该机构下手持设备已存在广告，请删除后再添加");
		}
		sqlDao_h.saveRecord(paramCfg);
		this.pro.close();	
		return ret;
	}
	/**
	 * 修改广告信息
	 */
	public ServiceReturn editHosAdvertParam(String json) throws Exception 
	{
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);// 返回登录用户信息
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject jsonObj = JSONObject.fromObject(json);
		//手持广告信息实体类
		BtHosAdvert paramCfg = (BtHosAdvert)JSONObject.toBean(jsonObj, BtHosAdvert.class);
		//得到图片路径
		String photo_path = paramCfg.getPhoto_path().replace((CharSequence)"\n", (CharSequence)"");
		//图片路径
		paramCfg.setPhoto_path(photo_path);
		//得到当前系统时间
		String ament_time = DateUtil.getDateTime();
		//修改时间
		paramCfg.setAment_time(ament_time);
		//图片数量
		String photo_num = Integer.toString(photo_path.split(";").length);
		paramCfg.setPhoto_num(photo_num);
		//图片名称
		String advert_rotation = "";
		String[] photo_name = photo_path.split(";");
		for(int i = 0;i < photo_name.length; i++)
		{
			String str = photo_name[i];
			advert_rotation += str.substring(str.lastIndexOf("/")+1)+",";
		}
		paramCfg.setAdvert_rotation(advert_rotation);
		//轮播时间
		paramCfg.setAdvert_space(paramCfg.getAdvert_space());
		try {
            this.sqlDao_h.updateRecord((Object)paramCfg);
		} catch (Exception e) {
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("\u4fee\u6539\u5931\u8d25");
			e.printStackTrace();
			return ret;
		}
		this.pro.close();
		return ret;
	}
	/**
	 * 删除广告信息
	 */
	public ServiceReturn deleteHosAdvertParam(String json) throws Exception 
	{
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("logonuser");
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONArray jsonArray = JSONArray.fromObject((Object)json);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass((Class)BtHosAdvert.class);
		List<BtHosAdvert> ids = (List)JSONArray.toCollection((JSONArray)jsonArray, (JsonConfig)config);
		for (BtHosAdvert btQmGwqParamCfg : ids) {
		    try {
		        String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator + "adUpload"+File.separator+user.getUnitid();
		        Object[] param={btQmGwqParamCfg.getBranch()};
		        this.sqlDao_h.excuteHql("delete from BtHosAdvert where branch = ?", param);
		        this.deleteFile(path);
		        FileUtil.deleteDir((String)path);
		        FileUtil.deleteDir((String)path.replace((CharSequence)("/ad"), (CharSequence)("/video")));
		        continue;
		    }
		    catch (Exception e) {
		        ret.setSuccess(Boolean.valueOf(false));
		        ret.setOperaterResult(false);
		        ret.setErrmsg("\u5220\u9664\u5931\u8d25");
		        e.printStackTrace();
		        return ret;
		    }
		}
		this.pro.close();
		return ret;
	}

	//上传文件
	public String upLoadFile(String fileName, File upLoadFile) throws Exception 
	{
	 try {
            FileInputStream stream = new FileInputStream(upLoadFile);
            File outFile = new File(fileName);
            File parent = outFile.getParentFile();
            //判断此目录是否存在
            if (!parent.exists()) 
            {
            	//不存在时创建
                parent.mkdirs();//创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
            }
            HosAdverInfoServiceImpl.loger.info((Object)"\u6e05\u7406\u540c\u540d\u6587\u4ef6!");
            File[] files = parent.listFiles();
            if(files!=null&&files.length>0){
	            for (File file : files = parent.listFiles()) 
	            {
	                if (file.getName().indexOf(outFile.getName().substring(0, outFile.getName().indexOf("."))) == -1) continue;
	                file.delete();
	            }
            }
            HosAdverInfoServiceImpl.loger.info((Object)("\u5f00\u59cb\u4e0a\u4f20\u6587\u4ef6" + fileName));
            FileOutputStream out = new FileOutputStream(outFile);
            byte[] temp = new byte[1024];
            int length = 0;
            while ((length = stream.read(temp)) > 0) 
            {
                out.write(temp, 0, length);
            }
            HosAdverInfoServiceImpl.loger.info((Object)("\u4e0a\u4f20\u6587\u4ef6\u6210\u529f" + fileName));
            stream.close();
            out.close();
            this.pro.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	//读取文件
	private boolean writeFile(String confinfo,String filePath) throws Exception
	{
		File file = new File(filePath);
		File parent=file.getParentFile();
		if(!parent.exists())
		{
			parent.mkdirs();
		}
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.print(confinfo);
			ps.close();
  		} catch (Exception e) {
			return false;
		}
		return true;
	}

	//删除文件
	private boolean deleteFile(String filepath) 
	{
		boolean flag = false;
		File file = new File(filepath);
		if(file.isFile()&&file.exists()){
			file.delete();
			flag= true;
		}
		return flag;
	}
}
