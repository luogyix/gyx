package com.agree.framework.communication;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.ProcessingInstruction;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

/** 
 * @ClassName: Dom4jparse 
 * @Description: 这里用dom4j解析xml报文，因为afa只支持两层循环，故这里也暂时只支持两层
 * @company agree   
 * @author haoruibing   
 * @date 2012-4-21 下午01:51:56 
 *  
 */ 
public class Dom4jparse {
	private File inputXml;
	private InputSource in;
	private  Message msg;
	@SuppressWarnings("rawtypes")
	private Map propertiesMap;
	private Log logger = LogFactory.getLog(Dom4jparse.class);	
	@SuppressWarnings("rawtypes")
	public Dom4jparse(File inputXml,Map map) {
		this.inputXml = inputXml;
		 msg=new Message();
		 propertiesMap=map;
	}
	
	@SuppressWarnings("rawtypes")
	public Dom4jparse(InputSource in,Map map,Message msg){
		this.in=in;
		 this.msg=msg;
		 this.propertiesMap=map;
		
	}

	public Document getDocument() {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			if(inputXml!=null){
				document = saxReader.read(inputXml);
			}else{
				document = saxReader.read(in);
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(),e);
		}
		return document;
	}

	public Element getRootElement() {
		return getDocument().getRootElement();
	}


	public Message traversalDocumentByVisitor() {
		getDocument().accept(new MyVisitor());
		return msg;
	}

	/**
	 * 定义自己的访问者类
	 */
	private  class MyVisitor extends VisitorSupport {
		
		int m=0;
		Map<String,String> temp=null;
		List<Map<String,String>> arrayList=null;
		String arrayPath="";
		/**
		 * 对于属性节点的处理
		 */
		public void visit(Attribute node) {
			
		}

		/**
		 * 对于处理指令节点的处理
		 */
		public void visit(ProcessingInstruction node) {
			logger.info("PI : " + node.getTarget() + " "
					+ node.getText());
		}

		/**
		 * 对于元素节点的处理
		 */
		@SuppressWarnings("rawtypes")
		public void visit(Element node) {
			String name = node.getName();
			if (node.isTextOnly()){//节点是否只包含文本内容，如果是，继续判断他是否在集合内（即temp是否存在），在的话就放到temp里
				String text = node.getText();
				if(node.getParent().getPath().equals(arrayPath)){
					temp.put(name, text);
				}else{
					String path = node.getParent().getPath().replaceAll("/Document/", "");
					path = path.replaceAll("ReqHdr/", "");
					if(!msg.isElementExist(path,name)){//判断该节点是否已经存在，如果存在则应该改变key的序号
						m=0;
						msg.put(path, name, text);
					}else{
						m++;
						msg.put(path, name+m, text);
					}
				}
			}
			else{//如果节点不止包含文本，那说明他还有子节点
				if(propertiesMap!=null){
					Iterator it=propertiesMap.keySet().iterator();
					while(it.hasNext()){
						String key=(String)it.next();
						if(node.getPath().equals(key)&&propertiesMap.get(key).equals("array")){//判断他是否是循环节点，是的话则新建一个map，并将其放入上级节点下
							if(!arrayPath.equals(key)){//判断该节点是否已经存在，如果存在则应该新建一个对象
								arrayList=new ArrayList<Map<String,String>>();
							}
							temp=new HashMap<String,String>();
							arrayPath=key;
							arrayList.add(temp);
							msg.put(node.getParent().getPath().replaceAll("/Document/", ""), node.getName(), arrayList);
							return;
						}
					}
				}
			}
		}
	}

//	public static void main(String[] argv) {
//		RecvMsgConf conf=new RecvMsgConf(new File("E:/bsms/resources/pack/upbs.100.001.01_recv.xml"));
//		Map propertiesMap=conf.traversalDocumentByVisitor();
//		Iterator it=propertiesMap.keySet().iterator();
//		Dom4jparse dom4jParser = new Dom4jparse(new File("e://return.txt"),propertiesMap);
//		dom4jParser.traversalDocumentByVisitor();
//		logger.info(dom4jParser.msg);
//	}
}
