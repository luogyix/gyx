<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	User user = (User)request.getSession().getAttribute("logonuser");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<base href="<%=basePath%>">
	
	<title>定时任务管理</title>
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
	<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	
	<script type="text/javascript">
		var pagereturn = ${actionresult};
		var trades = pagereturn.field1;
		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '定时任务管理', 120, 0,
				[],
				[
				{iconCls: "x-image-query", 			text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-user_edit", 		text:'<s:text name="common.button.editrecord"/>'}	//修改
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			
			//意思是之前已经加载了页面，此功能是分布详细页面信息信息
			
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/admin/timing_manage',
				[
				 'templatecode',
				 'transcode',
				 'status',
				 'skunumber', 
				 'exec_start_datetime',
				 'exec_end_datetime',
				 'exec_status',
				 'success_times',
				 'trade_name'
				 ],
				[
					{header:'全选'},
					{header:'复选框'},
					{header:'模板代码',dataIndex:'templatecode',width:100},
					{header:'交易代码', dataIndex:'transcode',width:100},
					{header:'交易名称', dataIndex:'trade_name',width:100},
					{header:'启用状态',	dataIndex:'status',width:100},
					{header:'集群服务器数量',	dataIndex:'skunumber',width:100},
					{header:'执行状态',	dataIndex:'exec_status',width:100},
					{header:'开始执行时间',	dataIndex:'exec_start_datetime',width:100},
					{header:'执行完成时间',	dataIndex:'exec_end_datetime',width:100},
					{header:'可执行次数',	dataIndex:'success_times',width:100}
				],
				'<s:text name="common.pagequery.pagingtool"/>'//分页信息
			);
			
			
			//修改窗口
			editwindow = new SelfFormWindow('editWindow', '修改定时任务信息', 280, 320, 140, 1,
			   [
				{colIndex:0, field:{xtype : 'textfield', id:'templatecode', name:'templatecode', 	fieldLabel:'模板代码',readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', id:'transcode', name:'transcode', 	fieldLabel:'交易代码',readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield',fieldLabel:'交易名称', id:'trade_name', name:'trade_name', readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield',fieldLabel:'启用状态', id:'status', name:'status', readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield',fieldLabel:'集群服务器数量',  id:'skunumber', name:'skunumber',maxLength:2,maxLengthText:"长度不能超过2位数",regex:/^[1-9]|([1-9][0-9])$/,regexText:"只能输入数字1~99",allowBlank:false}},
				{colIndex:0, field:{xtype :'textfield', id:'exec_date', name:'exec_date', 	fieldLabel:'执行日期',readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'exec_status', value:'',	fieldLabel:'执行状态', id:'exec_status',readOnly:true}},
				{colIndex:0, field:{xtype : 'numberfield', name:'success_times',id:'success_times',fieldLabel:'可执行次数',maxLength:2,maxLengthText:"长度不能超过2位数",allowBlank:false}}
			   ],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked,formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',100
			);
			
			/**
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				requestAjax('<%=basePath%>/admin/timing_editTradeStatus',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
			}
			
			
			//pagequeryObj.open();
			
			/*主窗口按钮设置*/
			function onButtonClicked(btn_index){
				switch(btn_index){
				 case 0://查询
					var query_obj = conditionPanel.getFields();
					pagequeryObj.queryPage(query_obj);
				break; 
				case 1://修改
					    var records = pagequeryObj.getSelectedRecords();
						if(records === undefined || records.length === 0){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
						if(records.length>1){
							Ext.MessageBox.alert('系统提示','只能选择一条记录');
							break;
						}
						editwindow.open();
						editwindow.updateFields(records[0]);
						break;
				}
			}
			function buildLayout(){
				var viewport = new Ext.Viewport({
				    layout : "border",
					items : [conditionPanel.toolbarwindow,pagequeryObj.pagingGrid]
				});
			}
     		buildLayout();
     		<%-- Ext.getCmp('unitnameid').setPassValue(<%="'"+user.getUnit().getUnitid()+"'"%>); --%>
		}
	</script>
	</head>
	<body>
		<div id="queryConditionPanel"></div>
		<div id="pageQueryTable"></div>
		<div id="editWindow"></div>
		<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
		 
		<!-- <form><table><tr><td>helloWorld</td></tr></table></form>-->
	</body>
</html>