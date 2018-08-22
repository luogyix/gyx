package com.agree.framework.natp;

import java.util.Map;


public interface INatp {
	@SuppressWarnings("rawtypes")
	Map exchange() throws Exception;

    int getRecordCount(String fieldName);

    int init(int natpVersion, String transCode, String templateCode,String reservedCode);

    void pack(String fieldName, String value) throws Exception;


    void pack(String[] fieldNames, String[] values) throws Exception;
    
    String[] unpack(String fieldName) throws Exception;
    public void reInit();
}
