<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute("logonuser");
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>    
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>设备注册信息查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.js"></script>
	<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagetoafaquery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	
	<script type="text/javascript">
		var conditionPanel = null;
		var devicetypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
			root   : 'field1',
			fields : ['dictValue','dictValueDesc'],
			autoLoad:true
		});
		var deviceonlineStore = new Ext.data.SimpleStore({ 
			data:[['0','0-已离线'],['1','1-在线']],
			fields : ['dictValue','dictValueDesc']
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示预约信息查询界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '设备注册信息查询', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combo',name:'devicetype',fieldLabel:'设备类型', forceSelection : true, hiddenName:'devicetype', store:devicetypeStore, 	displayField:'dictValueDesc',valueField:'dictValue'}}
				],
				[
				{iconCls: "x-image-query",text:'<s:text name="common.button.query"/>'},//查询
				{iconCls: "x-image-reset", 			text:'<s:text name="common.button.reset"/>'}//重置
				],
				onButtonClicked
			);
			
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/confManager/deviceRegist_queryDeviceRegistInfo',
				[
					'oid','devicetype','deviceip','deviceport','channelcode_rsp','deviceonline',
					'datacondition','deviceaddition'
				],[
					{header:'全选'},
					{header:'复选框'},
					{header:'设备唯一标识',dataIndex:'oid',width:100},
					{header:'设备类型',dataIndex:'devicetype',width:100},
					{header:'设备IP',dataIndex:'deviceip',width:100},
					{header:'设备PORT',dataIndex:'deviceport',width:100},
					{header:'渠道号',dataIndex:'channelcode_rsp',width:100},
					{header:'设备在线标识',dataIndex:'deviceonline',width:100, renderer:function(value){
					      return getDictValue(value.toString(),deviceonlineStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},
					{header:'过滤条件',dataIndex:'datacondition',width:100},
					{header:'设备附加信息',dataIndex:'deviceaddition',width:100}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			 /**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						pagequeryObj.queryPage(conditionPanel.getFields());
					break;
					case 1:
						conditionPanel.reset();
					break;
				}
			}
			 
			/**
			* @Title:buildLayout
			* @Description:创建布局函数
			*/
			function buildLayout(){
				var viewport = new Ext.Viewport({
				    layout : "border",
					items : [conditionPanel.toolbarwindow,pagequeryObj.pagingGrid]
				});
			}
      		buildLayout();
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
  </body>
</html>
