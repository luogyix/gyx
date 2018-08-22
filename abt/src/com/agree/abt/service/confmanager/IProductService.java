package com.agree.abt.service.confmanager;

import java.io.File;
import java.util.List;
import java.util.Map;
/**
 * 产品管理处理接口
 * @author duml
 * */
@SuppressWarnings({ "rawtypes" })
public interface IProductService {
	/**
	 * 查询产品
	 * */
	public List<Map> getProducts(String jsonObject)throws Exception;
	/**
	 * 添加产品
	 * */
	public String addProduct(String json)throws Exception;
	
	/**
	 * 删除产品
	 * */
	public String deleteProduct(String json)throws Exception;
	
	/**
	 * 编辑产品
	 * */
	public void editProduct(String json)throws Exception;
	/**
	 * 查询产品类别
	 * */
	public List<Map> getProductClass(String jsonObject)throws Exception;
	/**
	 * 添加产品类别
	 * */
	public String addProductClass(String json)throws Exception;
	/**
	 * 编辑产品类别
	 * */
	public String editProductClass(String json)throws Exception;
	/**
	 * 删除产品类别
	 * */
	public String deleteProductClass(String json)throws Exception;
	/**
	 * 上传文件
	 * @param fileName 文件名
	 * @param upLoadFile 上传文件
	 * @return
	 * @throws Exception
	 */
	public String upLoadFile(String fileName,File upLoadFile)throws Exception;
}
