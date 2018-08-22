package com.agree.framework.communication.natp;

import java.util.Map;

public interface INatp
{
    @SuppressWarnings("rawtypes")
	Map exchange(String serverName) throws Exception;

    int getRecordCount(String fieldName);

    int init(int natpVersion, String transCode, String templateCode,
            String reservedCode);

    void pack(String fieldName, String value) throws Exception;


    void pack(String[] fieldNames, String[] values) throws Exception;


    String[] unpack(String fieldName) throws Exception;

    String unpack(String fieldName, int iPos) throws Exception;

    
    void downloadFile(String serviceName,String remoteFileName,String localFileName)throws Exception;
    
    

    void downloadFile(String serviceName,String remotePathId,String remoteFileName,String localPathId,String localFileName)throws Exception;
    
	void downloadFile(String serviceName, String remoteFileName, String localPathId,String localFileName) throws Exception;

    
    
    void uploadFile(String serviceName,String remoteFileName,String localFileName)throws Exception;
    
   
   void uploadFile(String serviceName,String remotePathId,String remoteFileName,String localPathId,String localFileName)throws Exception;
   
    void uploadFile(String serviceName,String remotePathId,String remoteFileName,String localFileAbsolutePath)throws Exception;
 
   void uploadFileByBytes(String serviceName,String remoteFileName,String localFileName,byte[] content)throws Exception;
  
   void uploadFileByBytes(String serviceName,String remotePathId,String remoteFileName,String localFileName,byte[] content)throws Exception;
   
    
//   String getABPlatformParentPath() throws Exception;
//   String getABPlatformAbsolutePath()throws Exception;
//   String getFileTransferConfigProperty(String key)throws Exception;
}