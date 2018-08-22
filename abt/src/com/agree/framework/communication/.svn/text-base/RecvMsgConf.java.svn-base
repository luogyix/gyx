package com.agree.framework.communication;

import java.io.File;
import java.util.HashMap;
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



/** 
 * @ClassName: RecvMsgConf 
 * @Description: 解析返回报文的配置文件解析器
 * @company agree   
 * @author haoruibing   
 * @date 2012-4-21 下午01:45:31 
 *  
 */ 
public class RecvMsgConf {
	private File inputXml;
	private  Map<String,String> map;
	private Log logger = LogFactory.getLog(RecvMsgConf.class);	
	public RecvMsgConf(File inputXml) {
		this.inputXml = inputXml;
		this.map=new HashMap<String,String>();
	}

	public Document getDocument() {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(inputXml);
		} catch (DocumentException e) {
			logger.error(e.getMessage(),e);
		}
		return document;
	}

	public Element getRootElement() {
		return getDocument().getRootElement();
	}


	public Map<String,String> traversalDocumentByVisitor() {
		getDocument().accept(new MyVisitor());
		return map;
	}

	/**
	 * 定义自己的访问者类
	 */
	private  class MyVisitor extends VisitorSupport {
		
		/**
		 * 对于属性节点，打印属性的名字和值
		 */
		public void visit(Attribute node) {
			map.put(node.getParent().getPath(), node.getName());
		}

		/**
		 * 对于处理指令节点，打印处理指令目标和数据
		 */
		public void visit(ProcessingInstruction node) {
			logger.info("PI : " + node.getTarget() + " "
					+ node.getText());
		}
		/**
		 * 对于元素节点，判断是否只包含文本内容，如是，则打印标记的名字和 元素的内容。如果不是，则只打印标记的名字
		 */
		public void visit(Element node) {
			 if (node.isTextOnly()){
			}
			else{
			}
		}
	}
}
