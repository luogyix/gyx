package com.agree.framework.communication.natp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

 /**
 * <DL><DT>
 * ������.
 * </DT><p><DD>
 * ��ϸ����
 * </DD></DL><p>
 * 
 * <DL><DT><B>ʹ�÷���</B></DT><p><DD>
 * ʹ�÷���˵��
 * </DD></DL><p>
 * 
 * @author 
 * @author ��ͬ�Ƽ�
 * @version $Revision: 1.1 $ $Date: 2011/03/16 01:53:03 $ 
 */
public class ServerMapping {
	
	private static Properties serverProp=new Properties();
	
//	public static final Log logger=LogFactory.getLog(ServerMapping.class);
	private static Properties natpFileServiceConfigProp=new Properties();
	
	static File natpFileServiceConfigFile=null;
	static long fileLastModified=-1;
	

	
//	private static boolean isDebug(){
//		if (debugMode == null) {
//			String debugModeStr = Platform.getPreferencesService().getString(DEBUGQUALIFIER, DEBUGMODE, "false", null);
//			debugMode = new Boolean("true".equals(debugModeStr));
//		}
//		return debugMode.booleanValue();
//	}
	
	
	public static String getNatpFileServiceServer(String serviceName)throws IOException{
		return getFileTransferConfigProperty(serviceName);
	}
	
	public static String getFileTransferConfigProperty(String key)throws IOException{
		return getNatpFileServiceConfigProp().getProperty(key);
	}
	private synchronized static  Properties getNatpFileServiceConfigProp()throws IOException{
		/*if(!isDebug() && natpFileServiceConfigProp.size()>0){
			return natpFileServiceConfigProp;
		}
		
		if(natpFileServiceConfigProp.size()>0){
			natpFileServiceConfigProp.clear();
		}
		*/
		if(natpFileServiceConfigFile==null)
		{
//			checkFile(INatpConstants.NATP_FILE_TRANSFER_CONFIG_FILE,INatpConstants.NATP_FILE_TRANSFER_CONFIG_PATH+INatpConstants.NATP_FILE_TRANSFER_CONFIG_FILE,Platform.getBundle(INatpConstants.PLANPLUGINID));
//			String tmpFileName= Platform.getConfigurationLocation().getURL().getFile().toString().concat(INatpConstants.NATP_FILE_TRANSFER_CONFIG_FILE);
//			natpFileServiceConfigFile=new File(tmpFileName);
			
		}
		boolean  isChanged=false;
		if(natpFileServiceConfigFile!=null)
		{
			
			isChanged=checkFileLastModified();
			if(!isChanged)
			{
				return natpFileServiceConfigProp;
			}else
			{
//				if(logger.isInfoEnabled())
//				{
//					logger.info("Natp�ļ����������ļ��Ѿ��޸�.");
//				}
			}
			
		}
		InputStream in=null;
		try {
//			checkFile(INatpConstants.NATP_FILE_TRANSFER_CONFIG_FILE,INatpConstants.NATP_FILE_TRANSFER_CONFIG_PATH+INatpConstants.NATP_FILE_TRANSFER_CONFIG_FILE,Platform.getBundle(INatpConstants.PLANPLUGINID));
//			in=new URL(Platform.getConfigurationLocation().getURL(),INatpConstants.NATP_FILE_TRANSFER_CONFIG_FILE).openStream();
			natpFileServiceConfigProp.load(in);
			if(isChanged)
			{
//				if(logger.isInfoEnabled())
//				{
//					logger.info("��ǰnatp�ļ��������ò���Ϊ:"+natpFileServiceConfigProp);
//				}
			}
			
		} catch (IOException e) {
			throw e;
		} finally {
			if(in!=null)
				in.close();
		}
			
		return natpFileServiceConfigProp;
	}
	
	public static String getServer(String serverName) throws IOException {
		return getServerProp().getProperty(serverName);
		
	}
	
	private static Properties getServerProp() throws IOException{
//		if(!isDebug() && serverProp.size()>0)
//			return serverProp;
		
		if(serverProp.size()>0)
			serverProp.clear();
		
		

//			in=new URL(Platform.getConfigurationLocation().getURL(),INatpConstants.SERVERRCONFIGFILE).openStream();
//			serverProp.load(in);
//		} catch (IOException e) {
//			throw e;
//		} finally {
//			if(in!=null)
//				in.close();
//		}
			
		return serverProp;
	}
	
	
	public static ProfileReader getServerProfile()throws IOException{
		
		ProfileReader reader=new ProfileReader();
		InputStream in=null;
		try {
//			checkFile(INatpConstants.SERVERRCONFIGFILE,INatpConstants.SERVERCONFIGFILEPATH+INatpConstants.SERVERRCONFIGFILE,Platform.getBundle(INatpConstants.PLANPLUGINID));
			
//			in=new URL(Platform.getConfigurationLocation().getURL(),INatpConstants.SERVERRCONFIGFILE).openStream();
			reader.load(in);
		} catch (IOException e) {
			throw e;
		} finally {
			if(in!=null)
				in.close();
		}
		return reader;
	}

//	protected static void checkFile(String configFile, String entryPath,Bundle bundle) throws IOException {
//		String conf = Platform.getConfigurationLocation().getURL().getFile().toString().concat(configFile);
//		if(!(new File(conf).exists())) {
//			URL url = bundle.getEntry(entryPath);
//			cp(url,conf);
//		}
//	}

	static boolean checkFileLastModified()
	{
		
		
		boolean fileExists;
		try 
		{
			fileExists = natpFileServiceConfigFile.exists();
		} catch(SecurityException  e) 
		{
//			if(logger.isErrorEnabled())
//			{
//				logger.error("natp�ļ�����ͨѶ�����ļ�������.");
//			}
			fileExists=false;
		}
		if(fileExists)
		{
			long currLastModified=natpFileServiceConfigFile.lastModified();
			if(currLastModified>fileLastModified)
			{
				fileLastModified=currLastModified;
				return true;
			}else
			{
				return false;
			}
		}else
		{
			return false;
		}
		
	}

}
