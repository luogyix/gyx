<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'userinfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
		var pagereturn = ${actionresult};
		
		//alert('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=10012');
		var unitListStore = new Ext.data.JsonStore({
			fields : ['unitid','unitname','unitlevel'],
			data   : pagereturn.field1
		});
		
		var userStatusStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=10003',
			root   : 'field1', autoLoad:true
		});

		var userSexStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=10002',
			root   : 'field1', autoLoad:true
		});

		var userTypeStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=10004',
			root   : 'field1', autoLoad:true
		});

		var assignedRoleStore = new Ext.data.JsonStore({
			fields : ['roleid','rolename']
		});
		var systemRoleStore = new Ext.data.JsonStore({
			fields : ['roleid','rolename']
		});
		var rsM1 = new Ext.grid.RowSelectionModel({singleSelect: true});
		var rsM2 = new Ext.grid.RowSelectionModel({singleSelect: true});
		var columnModel1 = new Ext.grid.ColumnModel([{header:'角色名称',dataIndex:'rolename',width:250}]);
		var columnModel2 = new Ext.grid.ColumnModel([{header:'角色名称',dataIndex:'rolename',width:250}]);

		var logonUser = pagereturn.field2;
		
		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '密码重置', 120, 1,
				[
				{rowIndex:0, field:{xtype:'textfield', name:'username', fieldLabel:'<s:text name="admin.user.username"/>'}},
				{rowIndex:0, field:{xtype:'combo', name:'state1', hiddenName:'state', fieldLabel:'用户状态',
					store:userStatusStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{rowIndex:0, field:{xtype:'combo', name:'usertype1', hiddenName:'usertype', fieldLabel:'用户类型',
					store:userTypeStore, displayField:'dictValueDesc', valueField:'dictValue'}}
				],
				[
				{iconCls: "x-image-query", text:'<s:text name="common.button.query"/>'},
				{iconCls: "x-image-reset", text:'<s:text name="common.button.reset"/>'},
				{iconCls: "x-image-application_form_edit", text:'密码重置'}
				],
				onButtonClicked
			);
			conditionPanel.open();
			

			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/admin/systemuser_queryuser',
				['userid', 'usercode', 'username', 'usertype','sex','mailbox','telphone','cellphone','state', 'operatorcardid', 'unitid', 'unitname', 'sexdesc', 'statedesc', 'usertypedesc'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'<s:text name="admin.user.usernumber"/>',dataIndex:'userid',width:150},
				{header:'<s:text name="common.form.logonnumber"/>',dataIndex:'usercode',width:150},
				{header:'<s:text name="admin.user.username"/>',dataIndex:'username',width:150},
				{header:'用户类型',dataIndex:'usertype',width:150, translateField:'usertypedesc'},
				{header:'<s:text name="admin.user.usersex"/>',dataIndex:'sex',width:150, translateField:'sexdesc'},
				{header:'<s:text name="admin.user.useremail"/>',dataIndex:'mailbox',width:150},
				{header:'<s:text name="admin.user.userphone"/>',dataIndex:'telphone',width:150},
				{header:'<s:text name="admin.user.usermobilephone"/>',dataIndex:'cellphone',width:150},
				{header:'<s:text name="admin.user.userstatus"/>', dataIndex:'state',width:150, translateField:'statedesc'},
				{header:'<s:text name="admin.user.usercompany"/>',dataIndex:'unitname',width:150}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);

			var viewwindow = new SelfFormWindow('viewWindow', ' 查看用户信息', 600, 400, 200, 2,
					[
					{colIndex:0, field:{xtype : 'textfield', name:'userid',  fieldLabel:'<s:text name="admin.user.usernumber"/>',disabled:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'usercode',  fieldLabel:'<s:text name="common.form.logonnumber"/>',disabled:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'username',  fieldLabel:'<s:text name="admin.user.username"/>',disabled:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'usertypedesc',  fieldLabel:'用户类型',disabled:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'sexdesc',  fieldLabel:'<s:text name="admin.user.usersex"/>',disabled:true}},
					{colIndex:1, field:{xtype : 'textfield', name:'mailbox',  fieldLabel:'<s:text name="admin.user.useremail"/>',disabled:true}},
					{colIndex:1, field:{xtype : 'textfield', name:'telphone',  fieldLabel:'<s:text name="admin.user.userphone"/>',disabled:true}},
					{colIndex:1, field:{xtype : 'textfield', name:'cellphone',  fieldLabel:'<s:text name="admin.user.usermobilephone"/>',disabled:true}},
					{colIndex:1, field:{xtype : 'textfield', name:'statedesc',  fieldLabel:'<s:text name="admin.user.userstatus"/>',disabled:true}},
					{colIndex:1, field:{xtype : 'textfield', name:'unitname',  fieldLabel:'<s:text name="admin.user.usercompany"/>',disabled:true}}
					],
					[
						
						{text:'<s:text name="common.button.cancel"/>', handler: function(){
								viewwindow.close();
							}}
					]
				);

			pagequeryObj.pagingGrid.on('rowdblclick',function(){
    			var records = pagequeryObj.getSelectedRecords();
    			viewwindow.open();
    			viewwindow.updateFields(records[0]);
    		});
			
			function onButtonClicked(btn_index){
				switch(btn_index){
				case 0:
					var query_obj = conditionPanel.getFields();
					pagequeryObj.queryPage(query_obj);
				break;
				case 1:
					conditionPanel.reset();
				break;
				case 2:
					var submitdata = pagequeryObj.getSelectedObjects(['userid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定要重置密码吗?',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/admin/systemPassRedo_passwordRedo',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','重置密码成功,重置密码111111',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					}
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
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="recordAddWindow"></div>
	<div id="recordEditWindow"></div>
	<div id="roleAssignWindow"></div>
	<div id="viewWindow"></div>
  </body>
</html>
