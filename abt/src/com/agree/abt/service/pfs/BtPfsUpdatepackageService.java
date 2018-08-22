package com.agree.abt.service.pfs;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.abt.model.pfs.BtPfsUpdatepackage;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.service.base.BaseService;
import com.agree.util.FileUtil;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BtPfsUpdatepackageService extends BaseService implements IBtPfsUpdatepackageService{	
	private static final Logger logger = LoggerFactory.getLogger(BtPfsUpdatepackageService.class);
	/**
	 * 查询版本为APK的所有信息
	 */
	public List<BtPfsUpdatepackage> query_all() throws Exception {
	    String hql = "from BtPfsUpdatepackage";
		return sqlDao_h.getRecordList(hql);			
	}
	
	/**
	 * 删除信息同事删除文件包
	 */
	public void deleteBtPfsUpdatepackage(String rcid, String packagename)
			throws Exception {
		String hql = "from BtPfsUpdatepackage where rcid ='"+rcid+"'";
		BtPfsUpdatepackage updatepackage = (BtPfsUpdatepackage) sqlDao_h.getRecord(hql);
		if(updatepackage!=null){
			String basePath = ApplicationConstants.getBasePath();
			String filepath = updatepackage.getPath()+packagename;
			FileUtil.deleteFile(basePath+filepath);
			Map params2 = new HashMap();
			params2.put("RCID", rcid);
			sqlDao_h.excuteHql("delete from BtPfsUpdatepackage where RCID=:RCID",params2);			
		}
	}
	/**
	 * 根据条件查询所有信息
	 * @param map
	 * @param pageInfo
	 * @return
	 * @throws Exception
	 */
	public List<?> queryRecordPage(Map<?, ?> map, Page pageInfo) throws Exception {
		HashMap paramaHashMap = new HashMap();
		paramaHashMap.putAll(map);
		String version = map.get("version").toString();
		String versionid = map.get("versionid").toString();
		String packagetype = map.get("packagetype").toString();
		String hql = "from BtPfsUpdatepackage where 1=1 ";
		if(("N-最新版本").equals(version)||("N-最新版本标识").equals(versionid)){
			if(("全部").equals(packagetype)&&("N-最新版本").equals(version)&&("全部").equals(versionid)
					||("全部").equals(packagetype)&&("全部").equals(version)&&("N-最新版本标识").equals(versionid)){
				paramaHashMap.remove("packagetype");
				paramaHashMap.remove("versionid");
				paramaHashMap.remove("version");
			    hql = "from BtPfsUpdatepackage where version in (select max(version) from BtPfsUpdatepackage) ";
				return sqlDao_h.queryPage(hql, paramaHashMap, pageInfo, false);
			}
			if(!("全部").equals(packagetype)&&("N-最新版本").equals(version)&&("全部").equals(versionid)
					||!("全部").equals(packagetype)&&("全部").equals(version)&&("N-最新版本标识").equals(versionid)){
				paramaHashMap.remove("version");
				paramaHashMap.remove("versionid");
				hql = "from BtPfsUpdatepackage where version in (select max(version) from BtPfsUpdatepackage where packagetype =:packagetype) and packagetype =:packagetype";
				return sqlDao_h.queryPage(hql, paramaHashMap, pageInfo, false);
			}else
			if(("N-最新版本").equals(version)&&("N-最新版本标识").equals(versionid)){
				paramaHashMap.remove("version");
				paramaHashMap.remove("versionid");
				paramaHashMap.put("packagetype", paramaHashMap.get("packagetype").toString());
				if(("全部").equals(packagetype)){
					paramaHashMap.remove("packagetype");
				    hql = "from BtPfsUpdatepackage where version in (select max(version) from BtPfsUpdatepackage) ";
					return sqlDao_h.queryPage(hql, paramaHashMap, pageInfo, false);
				}
				 hql = "from BtPfsUpdatepackage where version in (select max(version) from BtPfsUpdatepackage where packagetype =:packagetype) and packagetype =:packagetype";
				return sqlDao_h.queryPage(hql, paramaHashMap, pageInfo, false);
			}
			if(!("全部").equals(packagetype)&&("N-最新版本").equals(version)&&!("全部").equals(versionid)&&!("N-最新版本标识").equals(versionid)||
					!("全部").equals(packagetype)&&("N-最新版本标识").equals(versionid)&&!("全部").equals(version)&&!("N-最新版本").equals(versionid)){
				paramaHashMap.remove("packagetype");
				paramaHashMap.remove("versionid");
				version = "-1";
				paramaHashMap.put("version", Long.parseLong(version));
				hql += " and version =:version ";
				return sqlDao_h.queryPage(hql, paramaHashMap, pageInfo, false);	
			}
			
		}
		else{
			if (paramaHashMap.get("packagetype") != null && !paramaHashMap.get("packagetype").toString().equals("")&& !paramaHashMap.get("packagetype").toString().equals("全部")) {
				paramaHashMap.put("packagetype", paramaHashMap.get("packagetype").toString());
				hql += " and packagetype =:packagetype ";
			}else{
	            paramaHashMap.remove("packagetype");
	        }
			if(paramaHashMap.get("version") != null && !paramaHashMap.get("version").toString().equals("") && !paramaHashMap.get("version").toString().equals("全部")) {
				paramaHashMap.put("version", Long.parseLong(paramaHashMap.get("version").toString()));
				hql += " and version =:version ";
	        }else{
	            paramaHashMap.remove("version");
	        }
			if (paramaHashMap.get("versionid") != null && !paramaHashMap.get("versionid").toString().equals("")&& !paramaHashMap.get("versionid").toString().equals("全部")) {
				hql += " and versionid =:versionid ";
			}else{
	            paramaHashMap.remove("versionid");
	        }   
		}
		return sqlDao_h.queryPage(hql, paramaHashMap, pageInfo, false);
	}
	/**
	 * 根据rcid条件查询文件路径
	 */
	public String filePath(String rcid)
			throws Exception {
		String hql = "from BtPfsUpdatepackage where rcid ='"+rcid+"'";
		BtPfsUpdatepackage updatepackage = (BtPfsUpdatepackage) sqlDao_h.getRecord(hql);
		if(updatepackage!=null){
			String filePath = updatepackage.getPath();
			return filePath;
		}
		return null;
	}
    /**
     * 获得最新版本信息
     */
	public BtPfsUpdatepackage queryNewRecordPage(String packagename) throws Exception {
		String hql = "from BtPfsUpdatepackage where version in (select max(version) from BtPfsUpdatepackage where packagetype = '"+packagename+"') and packagetype = '"+packagename+"'";
		BtPfsUpdatepackage updatepackage = (BtPfsUpdatepackage) sqlDao_h.getRecord(hql);
		if(updatepackage!=null){
			return updatepackage;
		}
		return null;
	}

	/**
	 * 上传给更新包、添加数据库记录操作
	 * @param packageType
	 * @param versionid
	 * @param remark
	 * @param updatedDateTime
	 * @param packagename
	 * @param upload
	 * @return String
	 * @throws Exception
	 */
	public String addPackage(String packageType,String versionid,String remark,String updatedDateTime,String packagename,File upload) throws Exception {
	   String retdata = null;
	   if ((packagename = this.doUpload(packageType,versionid,upload)) != null) {
			Map map=new HashMap();
			map.put("packagetype", packageType);
			Long version = null;
			Object obj = sqlDao_h.getRecordBySql("select max(version) from BT_PFS_UPDATEPACKAGE where packagetype='"+packageType+"'", Long.class);//("select max(version) from BT_PFS_UPDATEPACKAGE where packagetype='"+packageType+"'");
			
			if ( null != obj )
			version = new Long(obj.toString());
			if( null == version )
				version=1L;
			else
				version++;
			BtPfsUpdatepackage save = new BtPfsUpdatepackage();
			save.setPackagetype(packageType);
			save.setVersionid(versionid);
			save.setPackagename(packagename);
			save.setPath("assets/download/");
			save.setRemark(remark);
			save.setUpdatedatetime(updatedDateTime);
			save.setVersion(version);
			
			save.setRcid(sqlDao_h.getNextId("BtPfsUpdatepackage"));
			sqlDao_h.saveRecord(save);
			retdata = "{'success':true, msg:'" + packagename + "'}";
			return retdata;  
			}
		else{
			retdata = "{'success':false, msg:'此次没有上传文件'}";
			return retdata; 
		}
		
	}
	
//	public ServiceReturn getVersion(String packageType) throws Exception {
//		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
//		Map map=new HashMap();
//		map.put("packagetype", packageType);
//		Long version = null;
//		Object obj = sqlDao_h.getRecordBySql("select max(version) from BT_PFS_UPDATEPACKAGE where packagetype='"+packageType+"'", Long.class);//("select max(version) from BT_PFS_UPDATEPACKAGE where packagetype='"+packageType+"'");
//		
//		if ( null != obj )
//			version = new Long(obj.toString());
//		
//		if( null == version )
//			version=1L;
//		else
//			version++;
//		
//		sRet.put(ServiceReturn.FIELD1, version);
//		sRet.put(ServiceReturn.FIELD2, "添加成功");
//		return sRet;
//	}
	
	/**
	 * 数据库查出最新version再插入到数据库并保证多线程同步操作
	 * @param packagetype
	 * @param upload
	 * @return
	 * @throws Exception
	 */
	
	private String doUpload(String packagetype,String versionId, File upload) throws Exception {
			Long version = null;
			Object obj = sqlDao_h.getRecordBySql("select max(version) from BT_PFS_UPDATEPACKAGE where packagetype='"+packagetype+"'", Long.class);//("select max(version) from BT_PFS_UPDATEPACKAGE where packagetype='"+packageType+"'");

			if ( null != obj )
				version = new Long(obj.toString());
			if( null == version )
					version=1L;
			  else
					version++;
			String uploadFileName;
			String newName;
			if(packagetype.equals("apk")){
				uploadFileName = "android." + version + ".v" + versionId + "." + packagetype;
				newName = "android.new.apk";
			}else{
				uploadFileName = "ios." + version + ".v" + versionId + "." + packagetype;
				newName = "ios.new.ipa";
			}
	
				if (upload == null) {
					return null;
				} else {
					String savePath = ApplicationConstants.getBasePath() + "assets"
							+ File.separator + ApplicationConstants.DOWNLOAD_FOLDER
							+ File.separator;
					FileUtil.mkDirectory(savePath);
					String filename = FileUtil.uploadForName(uploadFileName, savePath, upload);
					String newFileM = FileUtil.uploadForName(newName, savePath, upload);
					if (FileUtil.isFileExist(filename, savePath)){
						String newFileName = FileUtil.uploadForName(filename, savePath, upload);
						 //删除文件
						FileUtil.deleteFile(savePath+newName);
						FileUtil.deleteFile(savePath+newFileM);
						FileUtil.copyFile(savePath+newFileName,savePath+newName);
						FileUtil.deleteFile(savePath+newFileName);
						
					}
					else{
						FileUtil.uploadForName(uploadFileName, savePath, upload);
					}
					return filename;
				}
	}
	/**
	 * 更新上传包信息更新时间
	 */
	public ServiceReturn updateBtPfsUpdatepackage(String packagename,File upload,long rcid,String packagetype,long version,String versionid,String remark,String updateDateTime) throws Exception {
		synchronized (ApplicationConstants.PACKAGEUPDATE_LOCK) {
		String uploadFileName;
		String newNames;
		packagetype = packagetype.substring(packagetype.length() - 3).toLowerCase();
		if(packagetype.equals("apk")){
			uploadFileName = "android." + version + ".v" + versionid + "." + packagetype;
			newNames = "android.new.apk";
		}else{
			uploadFileName = "ios." + version + ".v" + versionid + "." + packagetype;
			newNames = "ios.new.ipa";
		}
		
		
			if (upload == null) {
				return null;
			} else {
				String savePath = ApplicationConstants.getBasePath() + "assets"
						+ File.separator + ApplicationConstants.DOWNLOAD_FOLDER
						+ File.separator;
				FileUtil.mkDirectory(savePath);		
				if (FileUtil.isFileExist(uploadFileName, savePath)){
					String newName = FileUtil.uploadForName(uploadFileName, savePath, upload);
					 //删除文件
					FileUtil.deleteFile(savePath+packagename);
					FileUtil.copyFile(savePath+newName,savePath+packagename);
					FileUtil.copyFile(savePath+newName,savePath+newNames);
					FileUtil.deleteFile(savePath+newName);
					
					
				}
				else{
					FileUtil.uploadForName(uploadFileName, savePath, upload);
				}
			}
		
	
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
	    Map params = new HashMap();
		params.put("RCID", rcid);
		params.put("VERSIONID", versionid);
		params.put("PACKAGENAME", uploadFileName);
		params.put("REMARK", remark);
		params.put("UPDATEDATETIME", updateDateTime);
		sqlDao_h.excuteHql("update BtPfsUpdatepackage set VERSIONID = :VERSIONID,PACKAGENAME = :PACKAGENAME,REMARK = :REMARK,UPDATEDATETIME = :UPDATEDATETIME where RCID=:RCID",params);
		sRet.put(ServiceReturn.FIELD2, "更新数据库成功");
		return sRet;
		}
	}
	/**
	 * 修改前回显返回对象
	 * @param rcid
	 * @return
	 * @throws Exception
	 */ 
	public BtPfsUpdatepackage queryBtpfsupdatepackage(long rcid)
	throws Exception {
		String hql = "from BtPfsUpdatepackage where rcid = '"+rcid+"')";
		BtPfsUpdatepackage updatepackage = (BtPfsUpdatepackage) sqlDao_h.getRecord(hql);
		if(updatepackage!=null){
			return updatepackage;
		}
		return null;
	}
	/**
	 * 根据rcid获得path
	 * @param rcid
	 * @return
	 * @throws Exception
	 */	 
	public String queryPath(String rcid) throws Exception {
		String hql = "from BtPfsUpdatepackage where rcid ='"+rcid+"'";
		BtPfsUpdatepackage updatepackage = (BtPfsUpdatepackage) sqlDao_h.getRecord(hql);
		String path = updatepackage.getPath();
		return path;
	}

	/**
	 * 更新数据时的上传文件
	 */
	public String doUpdateUpload(String packagename,String packagetype,Long version,File upload) throws Exception {
		packagetype = packagetype.substring(packagetype.length() - 3).toLowerCase();
		String uploadFileName = "com.agree." + packagetype  + "." + version + "."+ (packagetype.equals("mfp") ? "apk" : "zip");
		try {
			if (upload == null) {
				return null;
			} else {
				String savePath = ApplicationConstants.getBasePath() + "assets"
						+ File.separator + ApplicationConstants.DOWNLOAD_FOLDER
						+ File.separator;
				FileUtil.mkDirectory(savePath);		
				if (FileUtil.isFileExist(uploadFileName, savePath)){
					String newName = FileUtil.uploadForName(uploadFileName, savePath, upload);
					 //删除文件
					FileUtil.deleteFile(savePath+packagename);
					FileUtil.copyFile(savePath+newName,savePath+packagename);
					FileUtil.deleteFile(savePath+newName);
					
					
				}
				else{
					FileUtil.uploadForName(uploadFileName, savePath, upload);
				}
				
				return uploadFileName;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return null;
		} 
	}

	


}
