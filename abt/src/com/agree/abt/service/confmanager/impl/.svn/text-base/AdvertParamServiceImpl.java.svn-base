package com.agree.abt.service.confmanager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.model.confManager.BtTsfAdvert;
import com.agree.abt.service.confmanager.IAdvertParamService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;
import com.agree.util.FileUtil;
import com.agree.util.PropertiesUtils;

@SuppressWarnings({ "unchecked", "rawtypes","unused"})
public class AdvertParamServiceImpl extends BaseService implements IAdvertParamService {
	private static final Log loger=LogFactory.getLog(AdvertParamServiceImpl.class);//日志
	PropertiesUtils pro = new PropertiesUtils();
	/**
	 * String moduleid=(String) sqlDao_h.getRecord("select max(moduleid) from Module");
		module.setModuleid(String.valueOf((Integer.parseInt(moduleid)+1)));
		hql = hql+" and (un.unitlist like :unitid or un.unitname is null)";
		hql = hql+" from User us left join us.unit un";
	 */

	public List<Object> getAdvertParam(User user,Page pageinfo) throws Exception {
		List<Object> list = new ArrayList<Object>();
		String hql = "from BtTsfAdvert where branch =:branch";
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("branch", user.getUnit().getUnitid()); 
		list = sqlDao_h.queryPage(hql, map, pageinfo, false);
		if(list.size()==0){
			String[] unitlist = user.getUnit().getUnitlist().split("\\|");
			List<String> list1 = new ArrayList<String>();
			for (int i = 0; i < unitlist.length; i++) {
				list1.add(unitlist[i]);
			}
			hql ="from BtTsfAdvert where branch in (:branch)";
			map.put("branch", list1);
			list = sqlDao_h.queryPage(hql, map, pageinfo, false);

		}
		return list;
	}
	
	@Transactional
	public ServiceReturn addAdvertParam(String json) throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);//返回登录用户信息
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");		
		JSONObject jsonObj = JSONObject.fromObject(json);
		BtTsfAdvert paramCfg = (BtTsfAdvert)JSONObject.toBean(jsonObj, BtTsfAdvert.class);
		String photos_path = paramCfg.getPhotos_path().replace((CharSequence)"\n", (CharSequence)"");
		//拼接图片路径
		String[] photos_pathArray = photos_path.split(";");
		String newFilePath="";
		for(int i = 0; i<photos_pathArray.length; i++){
			int pathIndexOf = photos_pathArray[i].indexOf("adUpload/")+9;
			String startPath = photos_pathArray[i].substring(0, pathIndexOf);
			String endPath = photos_pathArray[i].substring(pathIndexOf, photos_pathArray[i].length());
			newFilePath = newFilePath + startPath + "AdvertisingPicture"+ "/" + "CardMachine"+ "/" +endPath + ";";
		}
		
		
		paramCfg.setPhotos_path(newFilePath);
		paramCfg.setPlay_time(paramCfg.getPlay_time().replace(":", ""));
		String hql = "select count(*)  from BtTsfAdvert where branch='"+paramCfg.getBranch()+"'";
//		map.put("branch", paramCfg.getBranch());
		
		String[] filenames=photos_path.split(";");
		String photos_md5="";
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator+ "adUpload"+ File.separator+ "AdvertisingPicture"+ File.separator+ "CardMachine"+ File.separator+ user.getUnitid();
		if(photos_path!=null&&!"".equals(photos_path)){
			if(filenames!=null&&filenames.length>0){
				for(int i=0;i<filenames.length;i++){
					/**
			        if ("999888".equals(user.getUnitid()) || "910020000".equals(user.getUnitid())) {
			            filePath1 = String.valueOf(path) + File.separator + "pub" + File.separator + filenames[i];
			        }
			        */
					String filename=filenames[i].substring(filenames[i].lastIndexOf("/")+1);
				    FileInputStream fis = new FileInputStream(path+File.separator+filename);
				    photos_md5 =photos_md5+DigestUtils.md5DigestAsHex(IOUtils.toByteArray(fis))+";";
				    IOUtils.closeQuietly(fis);
				}
			}
		}
		paramCfg.setPhotos_md5(photos_md5);
		//String filePath = String.valueOf(path) + File.separator + user.getUnitid() + File.separator + "000001.txt";
		/*
		 if ("999888".equals(user.getUnitid()) || "910020000".equals(user.getUnitid())) {
	            filePath = String.valueOf(path) + File.separator + "pub" + File.separator + "000001.txt";
	        }
	    */
		/**
		 * 一个机构号只能在数据库中创建一条广告数据,并且在此advert_id默认为“000001”
		 * 如果一个机构号可以在数据库中创建多个广告数据，则advert_id必须更改，待以后扩展
		 */
		Long count = sqlDao_h.getRecordCount(hql);
		if(count>0){
			throw new AppException("\u672c\u673a\u6784\u5df2\u5b58\u5728\u76f8\u5173\u914d\u7f6e,\u82e5\u60f3\u7ee7\u7eed\u6dfb\u52a0\uff0c\u8bf7\u5220\u9664\u539f\u6709\u914d\u7f6e");
		}
		String advert_id ="000001";
		paramCfg.setAdvert_id(advert_id);
		sqlDao_h.saveRecord(paramCfg);
		//String write ="play_time:"+paramCfg.getPlay_time()+System.getProperty("line.separator")+System.getProperty("line.separator")+"play_interval:" +paramCfg.getPlay_interval()+System.getProperty("line.separator")+"video_interval:"+paramCfg.getVideo_interval()+System.getProperty("line.separator")+"photos_name:"+paramCfg.getPhotos_path()+System.getProperty("line.separator")+paramCfg.getPhotos_md5();
		//writeFile(write,filePath);
		this.pro.close();
		return ret;
	}
	
	@Transactional
	public ServiceReturn deleteAdvertParam(String json) throws Exception {
        User user = (User)ServletActionContext.getRequest().getSession().getAttribute("logonuser");
        ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
        JSONArray jsonArray = JSONArray.fromObject((Object)json);
        JsonConfig config = new JsonConfig();
        config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
        config.setRootClass((Class)BtTsfAdvert.class);
        List<BtTsfAdvert> ids = (List)JSONArray.toCollection((JSONArray)jsonArray, (JsonConfig)config);
        for (BtTsfAdvert btQmGwqParamCfg : ids) {
            try {
                String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator + "adUpload"+File.separator+user.getUnitid();
                /*
                if ("999888".equals(user.getUnitid()) || "910020000".equals(user.getUnitid())) {
                    filePath = String.valueOf(path) + File.separator + "pub";
                }
                */
                Object[] param={btQmGwqParamCfg.getBranch()};
                this.sqlDao_h.excuteHql("delete from BtTsfAdvert where branch = ?", param);
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
	
	@Transactional
	public ServiceReturn editAdvertParam(String json)
			throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession()
		.getAttribute(ApplicationConstants.LOGONUSER);// 返回登录用户信息
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject jsonObj = JSONObject.fromObject(json);
		BtTsfAdvert paramCfg = (BtTsfAdvert)JSONObject.toBean(jsonObj, BtTsfAdvert.class);
		String photos_path = paramCfg.getPhotos_path().replace((CharSequence)"\n", (CharSequence)"");
		
		//拼接图片路径
		String[] photos_pathArray = photos_path.split(";");
		String newFilePath="";
		for(int i = 0; i<photos_pathArray.length; i++){
			int pathIndexOf = photos_pathArray[i].indexOf("adUpload/")+9;
			String startPath = photos_pathArray[i].substring(0, pathIndexOf);
			String endPath = photos_pathArray[i].substring(pathIndexOf, photos_pathArray[i].length());
			newFilePath = newFilePath + startPath + "AdvertisingPicture"+ "/" + "CardMachine"+ "/" +endPath + ";";
		}
		
		paramCfg.setPhotos_path(newFilePath);
		//String write ="play_time:"+paramCfg.getPlay_time()+"\n;play_interval:" +paramCfg.getPlay_interval()+"\n;video_interval:"+paramCfg.getVideo_interval()+"\n;photos_path:"+paramCfg.getPhotos_path()+"\n;photos_md5:"+paramCfg.getPhotos_md5();
		paramCfg.setPlay_time(paramCfg.getPlay_time().replace((CharSequence)":", (CharSequence)""));
		String[] filenames=photos_path.split(";");
		String photos_md5="";
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator+ "adUpload"+ File.separator+ "AdvertisingPicture"+ File.separator+ "CardMachine"+ File.separator+ user.getUnitid();
		if(filenames!=null&&filenames.length>0){
			for(int i=0;i<filenames.length;i++){
				String filename=filenames[i].substring(filenames[i].lastIndexOf("/")+1);
			    FileInputStream fis = new FileInputStream(path+File.separator+filename);
				String md5 = DigestUtils.md5DigestAsHex(IOUtils.toByteArray(fis));
				IOUtils.closeQuietly(fis);
				photos_md5=photos_md5+md5+";";
			}
		}
		paramCfg.setPhotos_md5(photos_md5);
		try {
			//String filePath = String.valueOf(path) + File.separator + user.getUnitid() + File.separator + "000001.txt";
			/**
            if ("999888".equals(user.getUnitid()) || "910020000".equals(user.getUnitid())) {
                filePath = String.valueOf(path) + File.separator + "pub" + File.separator + "000001.txt";
            }
            */
            this.sqlDao_h.updateRecord((Object)paramCfg);
            //this.writeFile(write, filePath);
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
	
	public String upLoadFile(String fileName, File upLoadFile) throws Exception {
		 	//Object buffer = null;
	        //User user = (User)ServletActionContext.getRequest().getSession().getAttribute("logonuser");
	        try {
	            FileInputStream stream = new FileInputStream(upLoadFile);
	            File outFile = new File(fileName);
	            File parent = outFile.getParentFile();
	            //判断此目录是否存在
	            if (!parent.exists()) {
	            	//不存在时创建
	                parent.mkdirs();//创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
	            }
	            loger.info("清理同名文件");
	            File[] files = parent.listFiles();
	            if(files!=null&&files.length>0){
		            for (File file : files = parent.listFiles()) {
		                if (file.getName().indexOf(outFile.getName().substring(0, outFile.getName().indexOf("."))) == -1) continue;
		                file.delete();
		            }
	            }
	            loger.info("开始上传文件");
	            FileOutputStream out = new FileOutputStream(outFile);
	            byte[] temp = new byte[1024];
	            int length = 0;
	            while ((length = stream.read(temp)) > 0) {
	                out.write(temp, 0, length);
	            }
	            loger.info("上传成功");
	            stream.close();
	            out.close();
	            this.pro.close();
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	}
	private boolean writeFile(String confinfo,String filePath) throws Exception{
		File file = new File(filePath);
		File parent=file.getParentFile();
		if(!parent.exists()){
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
	public boolean deleteFile(String filepath){
		boolean flag = false;
		File file = new File(filepath);
		if(file.isFile()&&file.exists()){
			file.delete();
			flag= true;
		}
		return flag;
	}

}
