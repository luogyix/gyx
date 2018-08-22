/**   
 * @Title: MessagePacker.java 
 * @Package com.agree.bsms.communication.codec 
 * @Description: TODO 
 * @company agree   
 * @author haoruibing   
 * @date 2011-10-11 下午05:36:55 
 * @version V1.0   
 */ 

package com.agree.framework.communication;

import java.io.File;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.agree.framework.exception.AppException;
import com.agree.framework.util.VelocityUtil;
import com.agree.util.Constants;
import com.agree.util.StringUtil;

/** 
 * @ClassName: MessagePacker 
 * @Description: afa通讯工具类
 * @company agree   
 * @author haoruibing   
 * @date 2011-10-11 下午05:36:55  update by haoruibing  at 2012/4/21
 * @detail:之前的sax解析方式只支持一层，现在用dom4j方式解析，增加两个类：RecvMsgConf和Dom4jparse
 * 现在能够支持两层循环，考虑到afa也只支持两层就已经足够了
 *  
 */

public class AFAMessagePacker implements Packager{
	private static final Logger logger = LoggerFactory.getLogger(AFAMessagePacker.class);

	  /* (non-Javadoc)
	 * <p>Title: unpack</p> 
	 * <p>Description: 解析报文,暂时不解析安全信息域</p> 
	 * @param msg
	 * @return 
	 * @see com.agree.framework.communication.Packer#unpack(java.lang.String) 
	 */ 
	@SuppressWarnings("rawtypes")
	public Message unpack(String msg)
	  {
		AFAHeaderPackager headerPacker=new AFAHeaderPackager();
		Message msg1=headerPacker.unpack(msg);
		//解析安全信息域
		
	    try {
	    	int index=msg.indexOf("<?xml");
	      InputSource inputSource = new InputSource(new StringReader(msg.substring(index)));

	      
	      String servicecode=msg1.getString(Constants.HEADER, "M_ServiceCode");
	      String path=AFAMessagePacker.class.getResource("/resources/pack").getFile();
	      logger.info("返回服务码"+servicecode.trim());
	      File file=new File(path+"/"+servicecode.trim()+"_recv.xml");   
	      if(file.exists()){   																				//如果返回报文的配置文件存在，按照配置文件解析
	    	  RecvMsgConf conf=new RecvMsgConf(file);
	    	  Map propertiesMap=conf.traversalDocumentByVisitor();
	    	  Dom4jparse dom4jParser = new Dom4jparse(inputSource,propertiesMap,msg1);
	    	  dom4jParser.traversalDocumentByVisitor();
	      }else{																								//否则认为该报文只有一层，无需配置文件直接解析
	    	//  saxParser.parse(inputSource, new PackReader(msg1));//sax解析方式，只支持一层
	    	  Dom4jparse dom4jParser = new Dom4jparse(inputSource,null,msg1);
	    	  dom4jParser.traversalDocumentByVisitor();
	      }
	      
	    } catch (Exception e) {
	      throw new AppException("解析失败", e);
	    }
	    
	    return msg1;
	  }

	  /* (non-Javadoc)
	 * <p>Title: pack</p> 
	 * <p>Description: 根据message对象组装报文</p> 
	 * @param msg
	 * @return 
	 * @see com.agree.framework.communication.Packer#pack(com.agree.framework.communication.Message) 
	 */ 
	public String pack(Message msg)
	  {
	    if (msg.getString("packFile") == null) {
	      return packWithoutFormatFile(msg);
	    }
	    return packWithFormatFile(msg, msg.getString("packFile"));
	  }
/**
 * 
 * @Title: packWithoutFormatFile 
 * @Description: 不根据格式文件组装信息
 * @param @param msg
 * @param @return    参数 
 * @return byte[]    返回类型 
 * @throws
 */
	  @SuppressWarnings("rawtypes")
	private String packWithoutFormatFile(Message msg)
	  {
	    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GB18030\"?><Document>");
	    String key;
	    if (msg.findAllKeysByCategory("BankCommHeader").size() > 0) {
	      sb.append("<ReqHdr><BankCommHeader>");
	      for (Iterator itr = msg.findAllKeysByCategory("BankCommHeader").iterator(); itr.hasNext(); ) {
	        key = (String)itr.next();
	        if (key == null) {
	          continue;
	        }
	        String value = (String)msg.get("BankCommHeader", key);
	        sb.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
	      }
	      sb.append("</BankCommHeader></ReqHdr>");
	    }

	    sb.append("<Content>");
	    for (Iterator itr = msg.findAllKeysByCategory(Constants.DEFAULT).iterator(); itr.hasNext(); ) {
	      key = itr.next().toString();
	      if (key.startsWith("//")) continue; if (msg.get(key) == null) {
	        continue;
	      }
	      if (msg.get(key) == null) {
	        logger.error("Error Key:" + key);
	      }
	      StringBuffer value = new StringBuffer(msg.get(key).toString());

	      replaceAll(value, "&", "&amp;");
	      replaceAll(value, "<", "&lt;");
	      replaceAll(value, ">", "&gt;");
	      replaceAll(value, "\"", "&quot;");

	      sb.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
	    }
	    sb.append("</Content>");

	    sb.append("</Document>");

	    return sb.toString();
	  }

	  /** 
	 * @Title: packWithFormatFile 
	 * @Description: 按照格式文件组装报文
	 * @param @param msg
	 * @param @param packFile
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	  private String packWithFormatFile(Message msg, String packFile)
	  {
		 AFAHeaderPackager headerPacker=new AFAHeaderPackager();
		 AFASecurityPackager security=new AFASecurityPackager();
	    String xml=headerPacker.pack(msg)+security.pack(msg)+VelocityUtil.merge(packFile,msg);
	    byte[] bytes=null;
		try {
			bytes = xml.getBytes("GB2312");
			xml=StringUtil.fillZeroLeft(String.valueOf(bytes.length), 8)+xml;
		} catch (UnsupportedEncodingException e) {
			logger.error("不支持字符集GB2312",e);
		}
	    return xml;
	  }
	
	  /** 
	 * @Title: replaceAll 
	 * @Description: 替换方法
	 * @param @param sb 传入字符串，
	 * @param @param findText 被替换字符
	 * @param @param replacement    替换字符
	 * @return void    返回类型 
	 * @throws 
	 */ 
	private void replaceAll(StringBuffer sb, String findText, String replacement)
	  {
	    int pos = sb.indexOf(findText);
	    if (pos >= 0)
	      sb.replace(pos, pos + findText.length(), replacement);
	  }
	  /**
	   * 
	   * @ClassName: PackReader 
	   * @Description: 解析报文
	   * @company agree   
	   * @author haoruibing   
	   * @date 2011-10-11 下午05:44:34 
	   *
	   
	  private class PackReader extends DefaultHandler { 
		private boolean isBankHead = false;
		private boolean isCNAPHead = false;
	    private String key = null;
	    private String category=null;
	    private boolean isContent = false;
	    private boolean isReturn = false;
	    private Message msg;
	    
	    private Map numMap=new HashMap();//用于记录循环节点出现次数

	    PackReader(Message msg) { 
	    	this.msg = msg;
	    }

	    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	      if (qName.equals(Constants.BANKCOMMHEADER)) {
	        this.isBankHead = true;
	      } else if (qName.equals(Constants.CNAPSHEADER)) {
	        this.isCNAPHead = true;
	      }else if (qName.equals(Constants.CONTENT)) {
	    	  this.isContent = true;
		  }else if (qName.equals(Constants.RSPHDR)) {
	    	  this.isReturn = true;
		  }
	      this.key = qName;
	    }

	    public void characters(char[] ch, int start, int length) throws SAXException
	    {
	      String value = new String(ch, start, length);
	      value=StringUtil.trim(value);
	      if ((this.isBankHead) && (this.key != null)) {
	        this.msg.put(Constants.BANKCOMMHEADER, this.key, value);
	        this.key = null;
	      } else if((this.isCNAPHead) && (this.key != null)){
	    	  this.msg.put(Constants.CNAPSHEADER, this.key, value);
		      this.key = null; 
	      }else if((this.isReturn) && (this.key != null)){
	    	  this.msg.put(Constants.RSPHDR, this.key, value);
		      this.key = null; 
	      }
	      else if((this.isContent) && (this.key != null)){ 
	    	  this.msg.put(Constants.CONTENT, this.key, value);
		      this.key = null; 
	      }
	    }

	    public void endElement(String uri, String localName, String qName) throws SAXException {
	      if (qName.equals(Constants.BANKCOMMHEADER)){
	        this.isBankHead = false;
	      }else if(qName.equals(Constants.CNAPSHEADER)){
	    	  this.isCNAPHead=false;
	      } else if(qName.equals(Constants.RSPHDR)){
	    	  this.isReturn=false;
	      } else if(qName.equals(Constants.CONTENT)){
	    	  this.isContent=false;
	      }
	    }
	  }
	  */
//	  public static void main(String[] args) {
//		  String filePath="e:\\upbs.sfm.0001.01.xml";
//		  File file=new File(filePath);
//		  BufferedReader reader=null;
//		  String tempstr="";
//		  String xml="";
//		  try{
//			  reader=new BufferedReader(new FileReader(file));
//			  while((tempstr=reader.readLine())!=null){
//				  xml+=tempstr;
//			  }
//			  System.out.println(xml);
//		  }catch(Exception e){
//			  logger.error(e.getMessage(),e);
//		  }
//		  AFAMessagePacker packer=new AFAMessagePacker();
//		  Message msg=packer.unpack(xml);
//		  System.out.println(msg.get("Content","contacts1"));
//	  }

}
