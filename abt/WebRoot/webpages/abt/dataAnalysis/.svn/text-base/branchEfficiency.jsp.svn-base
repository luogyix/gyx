<%@page import="com.agree.framework.web.form.administration.User" isELIgnored="false"%>
<%@page
	import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@ page language="java"  pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User) request.getSession().getAttribute(
		ApplicationConstants.LOGONUSER);
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>   
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'dictinfo.jsp' starting page</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		var ReportTypeStore= new Ext.data.JsonStore({
			fields : ['dataValue','dataName'],
			url    : '<%=basePath%>/system/commonselect_getSelectCodes.action?item_id=report',
			root   : 'field1', autoLoad:true
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		var queueBusiness= new Ext.data.JsonStore({
			fields : ['dataValue','dataName'],
			url    : '<%=basePath%>/system/commonselect_getSelectCodes.action?item_id=queueBusiness',
			root   : 'field1', autoLoad:true
		});
/**
 * @Title: loadPage 
 * @Description: 显示数据字典维护界面
 */
 		var	systemUnits=pagereturn.field1;
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var jsonMeta = {nodeId:'brno',parentNodeId:'supervbrno',nodeName:'branchname',nodeHref:'',nodeTarget:'',leafField:'',nodeLevel:'type',nodeType:'type'};

			var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
			var tree_a = treeGenerator_a.generate(false,false,false,false);
			var treepanel_a = new Ext.tree.TreePanel({
					 rootVisible : true, 
					 root:tree_a 
			});
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '柜员办理效率', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname', allowUnLeafClick:true,		fieldLabel:'机构号',		passName: 'branch', tree:treepanel_a, width:300}} ,//部门
				{rowIndex:0, field:{xtype:'combo', 	    name:'queueType', 	fieldLabel:'业务类型',value:'', editable:false, hiddenName:'queueType',blankText : '', store:queueBusiness, 	displayField:'dataName', valueField:'dataValue'}},				
				{rowIndex:0, field:{xtype:'combo',   	name:'reportType', 	fieldLabel:'报表类型',value:'日报表',editable:false,allowBlank : false, hiddenName:'reportType',forceSelection: true,blankText : '', store:ReportTypeStore, 	displayField:'dataName', valueField:'dataValue'}},
				{rowIndex:0, field:{xtype:'datefield', 	name:'startDate',   format:'Y-m-d',value:new Date(),allowBlank : false,	fieldLabel:'开始时间'}},
				{rowIndex:0, field:{xtype:'datefield', 	name:'endDate',     format:'Y-m-d',value:new Date(),allowBlank : false,	fieldLabel:'结束时间'}}
				],
				[
				{iconCls: "x-image-query", 			     id:'01',text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-reset", 			text:'<s:text name="common.button.reset"/>',id:'08'},		//重置
				{iconCls: "x-image-application_form_add",id:'06',text:'导出Excel'},//导出excel
				{iconCls: "x-image-application_form_add",id:'07',text:'导出Pdf'}//导出excel
				],
				onButtonClicked,priv
			);
			conditionPanel.open();
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/dataAnalysis/branchEfficiency_queryeEfficiency.action',
				['BRANCH','BRANCH_NAME','DATE','PEOPLE_COUNT','AVERAGE_WAITING_TIME','AVERAGE_SERVICE_TIME','AVERAGE_USE_TIME','TIME_EFFICIENCY','AVG_PEOPLE_TELLER','TELLER_COUNT'],
				[
					{header:'全选'},
					{header:'复选框'},
					{header:'机构号',dataIndex:'BRANCH',width:70},
					{header:'机构名',dataIndex:'BRANCH_NAME',width:100},
					{header:'日期',dataIndex:'DATE',width:100,sortable:true},				
					{header:'有效服务人数',dataIndex:'PEOPLE_COUNT',width:100,sortable:true},
					{header:'服务柜员数',dataIndex:'TELLER_COUNT',width:100,sortable:true},
					{header:'柜员平均服务人数',dataIndex:'AVG_PEOPLE_TELLER',width:100,sortable:true},
					{header:'平均等待时间',dataIndex:'AVERAGE_WAITING_TIME',width:100,sortable:true},
					{header:'平均服务时间',dataIndex:'AVERAGE_SERVICE_TIME',width:100,sortable:true},
					{header:'平均客户用时',dataIndex:'AVERAGE_USE_TIME',width:100,sortable:true},
					{header:'有效时间率',dataIndex:'TIME_EFFICIENCY',width:100,sortable:true}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			
			
			 /**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"，"重置"，"添加"，"修改"，"删除"的选择语句
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
					var query_obj = conditionPanel.getFields();
						var value=query_obj["reportType"];
						var startdate=query_obj["startDate"];
						var endate=query_obj["endDate"] ;
						var queueType=query_obj["queueType"];
						if(null==queueType||queueType===undefined||queueType.length==0){
							Ext.MessageBox.alert('信息提示','请选择业务类型!');
							return;
						}
						if(null==value||value===undefined||value.length==0){
							Ext.MessageBox.alert('信息提示','请选择报表类型!');
							return;
						}
						if(startdate=="undefined"||startdate.indexOf("-")==-1||startdate.length==0||startdate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的起始日期!');
									return;
						}
						if(endate=="undefined"||endate.indexOf("-")==-1||endate.length==0||endate.length!=10){
							Ext.MessageBox.alert('信息提示','请输入正确的结束日期!');
									return;
						}
						if(startdate>endate){
							Ext.MessageBox.alert('信息提示','起始日期不能晚于结束日期!');
									return;
						}
						pagequeryObj.queryPage(query_obj);
						break;
					case 1:
						conditionPanel.reset();
						Ext.getCmp('unitname').setPassValue(<%="'"+user.getUnitid()+"'"%>);
						Ext.getCmp('unitname').setValue(<%="'" + user.getUnit().getUnitname() + "'"%>);
						break;
					case 2:
						var colm=pagequeryObj.getStore().getCount();
						if(colm==0){
							Ext.MessageBox.alert('系统提示','请查询后生成报表');
						}else{
							window.location.href='<%=basePath%>dataAnalysis/branchEfficiency_exportExcel.action';
						}
						break;
						
					case 3:
						var colm=pagequeryObj.getStore().getCount();
						if(colm==0){
							Ext.MessageBox.alert('系统提示','请查询后生成报表');
						}else{
							window.location.href='<%=basePath%>dataAnalysis/branchEfficiency_exportPdf.action';
						}
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
	<div id="addWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>
