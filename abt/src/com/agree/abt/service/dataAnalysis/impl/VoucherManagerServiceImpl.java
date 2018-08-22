package com.agree.abt.service.dataAnalysis.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.model.dataAnalysis.BtBusVoucherInfo;
import com.agree.abt.service.dataAnalysis.IVoucherManagerService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;
import com.agree.util.PropertiesUtils;

public class VoucherManagerServiceImpl extends BaseService implements IVoucherManagerService {
	private static final Log loger=LogFactory.getLog(VoucherManagerServiceImpl.class);//日志
	PropertiesUtils pro = new PropertiesUtils();
	/**
	 * String moduleid=(String) sqlDao_h.getRecord("select max(moduleid) from Module");
		module.setModuleid(String.valueOf((Integer.parseInt(moduleid)+1)));
		hql = hql+" and (un.unitlist like :unitid or un.unitname is null)";
		hql = hql+" from User us left join us.unit un";
	 */

	@SuppressWarnings("rawtypes")
	public List<Object> getVoucherInfo(User user,Page pageinfo) throws Exception {
		List<Object> list = new ArrayList<Object>();
		String hql = "from BtBusVoucherInfo";
		HashMap map = new HashMap();
		list = sqlDao_h.queryPage(hql, map, pageinfo, false);
		return list;
	}
	
	@Transactional
	public ServiceReturn addVoucherInfo(String json) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");		
		JSONObject jsonObj = JSONObject.fromObject(json);
		BtBusVoucherInfo paramCfg = (BtBusVoucherInfo)JSONObject.toBean(jsonObj, BtBusVoucherInfo.class);
		String voucherpicture = paramCfg.getVoucherpicture().replace((CharSequence)"\n", (CharSequence)"");
		String picName = voucherpicture.substring(voucherpicture.lastIndexOf("/")+1);
		
				
		paramCfg.setVoucherpicture(picName);
		//paramCfg.setVouchercode(UUID.randomUUID().toString().substring(0, 8));
		paramCfg.setVoucherpicturepath(voucherpicture);
		String[] filenames = voucherpicture.split(",");
		String picturemd5 = "";
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator + "adUpload"+ File.separator+"Voucher";
		if(voucherpicture!= null&&!"".equals(voucherpicture))
		{
			if(filenames!= null&& filenames.length > 0)
			{
				for(int i=0;i<filenames.length;i++) 
				{
					String filename=filenames[i].substring(filenames[i].lastIndexOf("/")+1,voucherpicture.length());
					FileInputStream fis = new FileInputStream(path+File.separator+filename);
					picturemd5 =picturemd5+DigestUtils.md5DigestAsHex(IOUtils.toByteArray(fis));
					IOUtils.closeQuietly(fis);
				}
			}
		}
		paramCfg.setPicturemd5(picturemd5);
		sqlDao_h.saveRecord(paramCfg);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public ServiceReturn deleteVoucherInfo(String json) throws Exception {
        ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
        JSONArray jsonArray = JSONArray.fromObject((Object)json);
        JsonConfig config = new JsonConfig();
        config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
        config.setRootClass(BtBusVoucherInfo.class);
        List<BtBusVoucherInfo> ids = (List<BtBusVoucherInfo>)JSONArray.toCollection((JSONArray)jsonArray, (JsonConfig)config);
        for (BtBusVoucherInfo btQmGwqParamCfg : ids) {
            try {
                String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator + "adUpload"+File.separator+"Voucher"+File.separator+btQmGwqParamCfg.getVoucherpicture();
                Object[] param={btQmGwqParamCfg.getVouchercode()};
                this.sqlDao_h.excuteHql("delete from BtBusVoucherInfo where vouchercode = ?", param);
                this.deleteFile(path);
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
	public ServiceReturn editVoucherInfo(String json)
			throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject jsonObj = JSONObject.fromObject(json);
		String deletePic = (String)jsonObj.get("deletedPic");
		BtBusVoucherInfo paramCfg = (BtBusVoucherInfo)JSONObject.toBean(jsonObj, BtBusVoucherInfo.class);
		String voucherpicturepath = paramCfg.getVoucherpicturepath().replace((CharSequence)"\n", (CharSequence)"");
		
		String picName = voucherpicturepath.substring(voucherpicturepath.lastIndexOf("/")+1);
		
		paramCfg.setVoucherpicturepath(voucherpicturepath);
		paramCfg.setVoucherpicture(picName);
		
		String picturemd5 = "";
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator + "adUpload"+ File.separator+"Voucher";
		if(voucherpicturepath!= null&&!"".equals(voucherpicturepath))
		{
			String filename=voucherpicturepath.substring(voucherpicturepath.lastIndexOf("/")+1);
			FileInputStream fis = new FileInputStream(path+File.separator+filename);
			picturemd5 =picturemd5+DigestUtils.md5DigestAsHex(IOUtils.toByteArray(fis));
			IOUtils.closeQuietly(fis);
		}
		paramCfg.setPicturemd5(picturemd5);
		
		try {
            this.sqlDao_h.updateRecord((Object)paramCfg);
		} catch (Exception e) {
			ret.setSuccess(false);
			ret.setOperaterResult(false);
			ret.setErrmsg("\u4fee\u6539\u5931\u8d25");
			e.printStackTrace();
			return ret;
		}
		if(!deletePic.equals(voucherpicturepath)){
			String deletePicName = deletePic.substring(deletePic.lastIndexOf("/")+1);
			this.deleteFile(path+File.separator+deletePicName);
		}
		this.pro.close();
		return ret;
	}
	
	public String upLoadFile(String fileName, File upLoadFile) throws Exception {
	        try {
	            FileInputStream stream = new FileInputStream(upLoadFile);
	            File outFile = new File(fileName);
	            File parent = outFile.getParentFile();
	            if (!parent.exists()) {
	                parent.mkdirs();
	            }
	            VoucherManagerServiceImpl.loger.info((Object)"\u6e05\u7406\u540c\u540d\u6587\u4ef6!");
	            File[] files = parent.listFiles();
	            if(files!=null&&files.length>0){
		            for (File file : files = parent.listFiles()) {
		                if (file.getName().indexOf(outFile.getName().substring(0, outFile.getName().indexOf("."))) == -1) continue;
		                file.delete();
		            }
	            }
	            VoucherManagerServiceImpl.loger.info((Object)("\u5f00\u59cb\u4e0a\u4f20\u6587\u4ef6" + fileName));
	            FileOutputStream out = new FileOutputStream(outFile);
	            byte[] temp = new byte[1024];
	            int length = 0;
	            while ((length = stream.read(temp)) > 0) {
	                out.write(temp, 0, length);
	            }
	            VoucherManagerServiceImpl.loger.info((Object)("\u4e0a\u4f20\u6587\u4ef6\u6210\u529f" + fileName));
	            stream.close();
	            out.close();
	            this.pro.close();
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	}
	/*private boolean writeFile(String confinfo,String filePath) throws Exception{
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
	}*/
	public boolean deleteFile(String filepath){
		boolean flag = false;
		File file = new File(filepath);
		if(file.isFile()&&file.exists()){
			file.delete();
			flag= true;
		}
		return flag;
	}

	
	public ServiceReturn editConcle(String json) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject jsonObj = JSONObject.fromObject(json);
		String forDeletePic = (String)jsonObj.get("deletedPic");
		String voucherpicturepath = (String)jsonObj.get("voucherpicturepath");
        String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator + "adUpload"+File.separator+"Voucher";
        if(!forDeletePic.equals(voucherpicturepath)){
        	String picName = voucherpicturepath.substring(voucherpicturepath.lastIndexOf('/')+1);
        	this.deleteFile(path+File.separator+picName);
        }
        return ret;
	}

	public ServiceReturn addConcle(String jsonString) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		String voucherpicture = (String)jsonObj.get("voucherpicture");
        if(!voucherpicture.equals("")&&voucherpicture!=null){
        	String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+ File.separator + "adUpload"+File.separator+"Voucher";
        	String picName = voucherpicture.substring(voucherpicture.lastIndexOf('/')+1);
        	this.deleteFile(path+File.separator+picName);
        }
        return ret;
	}

}
