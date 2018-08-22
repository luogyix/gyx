package com.agree.abt.service.pfs;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.agree.abt.model.pfs.BtPfsUpdatepackage;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;

public interface IBtPfsUpdatepackageService {
	
	/**
	 * 根据条件查询所有信息
	 * @param map
	 * @param pageInfo
	 * @return
	 * @throws Exception
	 */
	public List<?> queryRecordPage(Map<?, ?> map, Page pageInfo) throws Exception;
	
	 /**
	  * 根据rcid条件查询文件路径
	  * @param rcid
	  * @return
	  * @throws Exception
	  */
	public String filePath(String rcid) throws Exception;
	
	/**
	 * 查询版本的所有信息
	 * @return
	 * @throws Exception
	 */
	public List<BtPfsUpdatepackage> query_all() throws Exception;

	/**
	 * 删除信息同事删除文件包
	 * @param rcid
	 * @param packagename
	 * @throws Exception
	 */	 
	public void deleteBtPfsUpdatepackage(String rcid,String packagename) throws Exception;

	/**
	 * 获得最新版本信息
	 * @param packagename
	 * @return
	 * @throws Exception
	 */     
	public BtPfsUpdatepackage queryNewRecordPage(String packagename) throws Exception;
	
	/**
	 * 添加数据上传版本并插入数据库
	 * @param packageType
	 * @param versionid
	 * @param remark
	 * @param updatedDateTime
	 * @param packagename
	 * @param upload
	 * @return
	 * @throws Exception
	 */
	public String addPackage(String packageType,String versionid,String remark,String updatedDateTime,String packagename,File upload) throws Exception;
	
	
	/**
	 * 修改前回显返回对象
	 * @param rcid
	 * @return
	 * @throws Exception
	 */ 
	public BtPfsUpdatepackage queryBtpfsupdatepackage(long rcid) throws Exception;
	
	/**
	 * 更新数据时的上传文件更新数据库的数据
	 * @param rcid
	 * @param uploadFileName
	 * @param packagetype
	 * @param version
	 * @param versionid
	 * @param remark
	 * @param updateDateTime
	 * @return
	 * @throws Exception
	 */	 
	public ServiceReturn updateBtPfsUpdatepackage(String packagename,File upload,long rcid,String packagetype,long version,String versionid,String remark,String updateDateTime) throws Exception;
	
	/**
	 * 根据rcid获得path
	 * @param rcid
	 * @return
	 * @throws Exception
	 */	 
	public String queryPath(String rcid) throws Exception;
}
