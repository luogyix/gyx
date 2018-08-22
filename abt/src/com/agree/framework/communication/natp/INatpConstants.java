package com.agree.framework.communication.natp;

 /**
 * <DL><DT>
 * INatpConstants常量.
 * </DT><p><DD>
 * 详细介绍
 * </DD></DL><p>
 * 
 * <DL><DT><B>使用范例</B></DT><p><DD>
 * 使用范例说明
 * </DD></DL><p>
 * 
 * @author 
 * @author 赞同科技
 * @version $Revision: 1.1 $ $Date: 2011/03/16 01:53:03 $ 
 */
public interface INatpConstants {
	String SERVERRCONFIGFILE="server.properties";
	
	String SERVERCONFIGFILEPATH="/config/";
	
	String PLANPLUGINID="cn.com.agree.ab.trade.ext.natp.impl";
	int    SERVERCONFIGFILE_PARAM_LENGHT=4;
	String GROUP_SERVER_CONNECTOR="_";
	
	String NATP_FILE_TRANSFER_CONFIG_PATH="/config/";
	String NATP_FILE_TRANSFER_CONFIG_FILE="natpfileservice.properties";
}
