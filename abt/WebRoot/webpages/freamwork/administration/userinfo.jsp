<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute("logonuser");
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户信息配置</title>
    
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
		var addwindow;
		var editwindow;
		var systemUnits = pagereturn.field1;
		var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'',nodeLevel:'unitlevel',nodeType:'unitlevel'};
		//构建机构树a
		var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree_a = treeGenerator_a.generate(false,false,false,false);
		var treepanel_a = new Ext.tree.TreePanel({
			width:200,
			height:220,
			rootVisible : true, 
			root:tree_a,
			bbar:[new Ext.form.TextField({
				width:180,
		        emptyText:'快速检索',
		        enableKeyEvents: true,
			    listeners:{
					render: function(f){
		                this.filter = new QM.ux.TreeFilter(treepanel_a,{
							clearAction : 'expand'
						});//初始化TreeFilter 
					},
		            keyup: {//添加键盘点击监听
		                fn:function(t,e){
		                  t.filter.filter(t.getValue());
		                },
		                buffer: 350
		            }
				}
		    }),{xtype:'button',text:''}]
		});
		//构建机构树b
		var treeGenerator_b = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree_b = treeGenerator_b.generate(false,false,false,false);
		var treepanel_b = new Ext.tree.TreePanel({
			width:200,
			height:220,
			rootVisible : true, 
			root:tree_b,
			bbar:[new Ext.form.TextField({
				width:180,
		        emptyText:'快速检索',
		        enableKeyEvents: true,
			    listeners:{
					render: function(f){
		                this.filter = new QM.ux.TreeFilter(treepanel_b,{
							clearAction : 'expand'
						});//初始化TreeFilter 
					},
		            keyup: {//添加键盘点击监听
		                fn:function(t,e){
		                  t.filter.filter(t.getValue());
		                },
		                buffer: 350
		            }
				}
		    }),{xtype:'button',text:''}]
		});
		//构建机构树c
		var treeGenerator_c = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree_c = treeGenerator_c.generate(false,false,false,false);
		var treepanel_c = new Ext.tree.TreePanel({
			width:200,
			rootVisible : true, 
			height:220,
			root:tree_c,
			bbar:[new Ext.form.TextField({
				width:180,
		        emptyText:'快速检索',
		        enableKeyEvents: true,
			    listeners:{
					render: function(f){
		                this.filter = new QM.ux.TreeFilter(treepanel_c,{
							clearAction : 'expand'
						});//初始化TreeFilter 
					},
		            keyup: {//添加键盘点击监听
		                fn:function(t,e){
		                  t.filter.filter(t.getValue());
		                },
		                buffer: 350
		            }
				}
		    }),{xtype:'button',text:''}]
		});
		
		//
		var unitListStore = new Ext.data.JsonStore({
			fields : ['unitid','unitname','unitlevel'],
			data   : systemUnits
		});
		var userauthStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=userauth',
			root   : 'field1', autoLoad:true
		});
		/*用户状态*/
		var userStatusStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=userstatus',
			root   : 'field1', autoLoad:true
		});
		/*用户性别*/
		var userSexStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=sex',
			root   : 'field1', autoLoad:true
		});
		/*用户类型*/
		//var userTypeStore = new Ext.data.JsonStore({
		//	fields : ['dictValue','dictValueDesc'],
		//	url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=usertype',
		//	root   : 'field1', autoLoad:true
		//});
		/*已分配的角色*/
		var assignedRoleStore = new Ext.data.JsonStore({
			fields : ['roleid','rolename']
		});
		/*未分配的角色*/
		var systemRoleStore = new Ext.data.JsonStore({
			fields : ['roleid','rolename']
		});
		var quartersStore=new Ext.data.SimpleStore({ 
			data:[['1','1-审核岗'],['2','2-管理岗'],['3','3-维护岗']],
			fields : ['dictValue','dictValueDesc']
		});
		var rsM1 = new Ext.grid.RowSelectionModel({singleSelect: true});
		var rsM2 = new Ext.grid.RowSelectionModel({singleSelect: true});
		
		var columnModel1 = new Ext.grid.ColumnModel([{header:'<s:text name="admin.role.rolename"/>',dataIndex:'rolename',width:250}]);
		var columnModel2 = new Ext.grid.ColumnModel([{header:'<s:text name="admin.role.rolename"/>',dataIndex:'rolename',width:250}]);

		var logonUser = pagereturn.field2;//
		
		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '系统用户配置', 120, 1,
				[
				{rowIndex:0, field:{xtype:'textfield', 	name:'username', 	emptyText:'--请输入用户名称/账号--',fieldLabel:'用户'}},//用户名称
				//{rowIndex:0, field:{xtype:'textfield', 	name:'usercode', 	emptyText:'----登陆账号----',fieldLabel:'登陆账号'}},//登陆账号
				{rowIndex:0, field:{xtype:'combo', 		name:'state1', 		emptyText:'----<s:text name="admin.user.userstatus"/>----',fieldLabel:'<s:text name="admin.user.userstatus"/>', 	hiddenName:'state', shadow:false, editable:false,	store:userStatusStore, 	displayField:'dictValueDesc', valueField:'dictValue'}},//用户状态
				//{rowIndex:0, field:{xtype:'combo', 		name:'usertype1', 	emptyText:'----<s:text name="admin.user.usertype"/>----',fieldLabel:'<s:text name="admin.user.usertype"/>', 		hiddenName:'usertype',editable:false, 	store:userTypeStore, 	displayField:'dictValueDesc', valueField:'dictValue'}},//用户类型
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',	emptyText:'----<s:text name="admin.user.userunit"/>----',fieldLabel:'<s:text name="admin.user.userunit"/>',		passName: 'unitid', tree:treepanel_b,value:<%="'" + user.getUnit().getUnitname()+ "'"%>, width:200,id:'unitnameid'}} //机构
				],
				[
				{iconCls: "x-image-query", 			text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-reset", 			text:'<s:text name="common.button.reset"/>'},		//重置
				{iconCls: "x-image-user_add", 		text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-user_edit", 		text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-user_delete", 	text:'<s:text name="common.button.deleterecord"/>'},//删除
				{iconCls: "x-image-database_go", 	text:'<s:text name="admin.role.assignrole"/>'},	//角色分配
				{iconCls: "x-image-query", 	text:'<s:text name="admin.user.resetpassword"/>'},//密码重置
				{iconCls: "x-image-application_form_add",text:'导出Excel'}, //导出excel
				{iconCls: "x-image-auth", 	text:'分配自助岗位'},//分配自助岗位
				{iconCls: "x-image-notauth", 	text:'收回自助岗位'},//收回自助岗位
				{iconCls: "x-image-auth", 	text:'<s:text name="admin.user.assignauthrole"/>'},//授权用户
				{iconCls: "x-image-notauth", 	text:'<s:text name="admin.user.unassignauthrole"/>'}//取消授权用户
				],
				onButtonClicked
			);
			conditionPanel.open();
			

			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/admin/systemuser_queryuser',
				['userid', 'usercode', 'username', 'usertype','role_name','state','unitid' ,'unitname','unitid','sex','mailbox','telphone','cellphone','tellerno','infoex1','infoex2','infoex3'],
				[
				{header:'全选'},
				{header:'复选框'},
				//{header:'<s:text name="admin.user.usernumber"/>',		dataIndex:'userid',		width:150},
				{header:'<s:text name="common.form.logonnumber"/>',		dataIndex:'usercode',	width:150},
				//{header:'<s:text name="admin.user.tellerno"/>',			dataIndex:'tellerno',	width:150},
				{header:'<s:text name="admin.user.username"/>',			dataIndex:'username',	width:150},
				{header:'所属机构机构号',		dataIndex:'unitid',	width:150},
				{header:'<s:text name="admin.user.usercompany"/>',		dataIndex:'unitname',	width:150,sortable:false},
				{header:'分配角色',			dataIndex:'role_name',	width:150 ,sortable:false},
				{header:'自助岗位',dataIndex:'infoex3',width:100,renderer:function(value){
					if(value==''||value==null){
						return '0-无岗位';
					}
          		 	return getDictValue(value,quartersStore,'dictValue','dictValueDesc');//翻译字段方法
       			 }},
				//{header:'<s:text name="admin.user.usertype"/>',			dataIndex:'usertype',	width:80, renderer:function(value){
				//	if(value==null){
				//		return '无';
				//	}
				//	value = value.toString();
          		// 	return getDictValue(value,userTypeStore,'dictValue','dictValueDesc');//翻译字段方法
       			//}},
       			{header:'<s:text name="admin.user.authroleflag"/>',	dataIndex:'infoex1',	width:100, renderer:function(value){
          		 	return getDictValue(value,userauthStore,'dictValue','dictValueDesc');//翻译字段方法
       			}},
				{header:'<s:text name="admin.user.userstatus"/>', 		dataIndex:'state',		width:55, renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,userStatusStore,'dictValue','dictValueDesc');//翻译字段方法
       			 }},
				{header:'<s:text name="admin.user.usersex"/>',			dataIndex:'sex',		width:55, renderer:function(value){
					if(value==null || value == "0"){
						return '未知';
					}
					value = value.toString();
          		 	return getDictValue(value,userSexStore,'dictValue','dictValueDesc');//翻译字段方法
       			 }},
				{header:'<s:text name="admin.user.userphone"/>',		dataIndex:'telphone',	width:100},
				{header:'<s:text name="admin.user.usermobilephone"/>',	dataIndex:'cellphone',	width:150},
				{header:'<s:text name="admin.user.useremail"/>',		dataIndex:'mailbox',	width:380}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			/*添加系统用户*/
			addwindow = new SelfFormWindow('recordAddWindow', '添加系统用户', 600, 350, 200, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', 	name:'usercode', 		allowBlank : false, fieldLabel:'<s:text name="common.form.logonnumber"/>', 	blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="common.form.logonnumber"/></s:param></s:text>',	maxLength : 9,	maxLengthText : '长度不能大于9位'}},
				{colIndex:0, field:{xtype : 'combotree', 	name:'unitname',		allowBlank : false,	fieldLabel:'<s:text name="admin.user.userunit"/>',	passName: 'unitid',tree:treepanel_b}},
				//{colIndex:0, field:{xtype : 'combo', 		name:'usertype', 		allowBlank : false, fieldLabel:'<s:text name="admin.user.usertype"/>',	hiddenName:'usertype',editable:false ,value:2, 	blankText : '', store:userTypeStore, displayField:'dictValueDesc', valueField:'dictValue'	}},
				{colIndex:0, field:{xtype : 'textfield', 	name:'username', 		allowBlank : false, fieldLabel:'<s:text name="admin.user.username"/>',	blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.username"/></s:param></s:text>',		maxLength : 20,maxLengthText : '长度不能大于20位'	}},
				{colIndex:0, field:{xtype : 'combo', 		name:'sex1', 			allowBlank : true, fieldLabel:'<s:text name="admin.user.usersex"/>', 	hiddenName:'sex',editable:false ,value:'1', blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.usersex"/></s:param></s:text>', 		store:userSexStore, 	displayField:'dictValueDesc', valueField:'dictValue'	}},
				//{colIndex:0, field:{xtype : 'textfield', 	name:'tellerno', 		allowBlank : true, fieldLabel:'<s:text name="admin.user.tellerno"/>', 	blankText : '<s:text name="common.info.mustwrite"><s:param>交易柜员号</s:param></s:text>',	maxLength : 20,	maxLengthText : '长度不能大于20位'	,regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'password', 			allowBlank : false, fieldLabel:'<s:text name="common.form.logonpassword"/>',	blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="common.form.logonpassword"/></s:param></s:text>',	maxLength : 32,maxLengthText : '长度不能大于32位', 	value:pagereturn.field6,	readOnly:true }},
				{colIndex:1, field:{xtype : 'combo', 		name:'state1', 			allowBlank : false, fieldLabel:'<s:text name="admin.user.userstatus"/>', 	hiddenName:'state',editable:false ,value:1, blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.userstatus"/></s:param></s:text>', 	store:userStatusStore, 	displayField:'dictValueDesc', valueField:'dictValue'	}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'mailbox',  		fieldLabel:'<s:text name="admin.user.useremail"/>',			blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.useremail"/></s:param></s:text>', vtype : 'email', 	maxLength : 30,maxLengthText : '长度不能大于30位'	}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'telphone', 		fieldLabel:'<s:text name="admin.user.userphone"/>',			blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.userphone"/></s:param></s:text>',  maxLength : 20,maxLengthText : '长度不能大于20位'	}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'cellphone', 		fieldLabel:'<s:text name="admin.user.usermobilephone"/>',	blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.usermobilephone"/></s:param></s:text>', 			maxLength : 20,maxLengthText : '长度不能大于20位',	regex:/^[0-9]*$/, regexText:'只能输入数字'	}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			editwindow = new SelfFormWindow('recordEditWindow', '编辑系统用户', 600, 350, 200, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', 	name:'usercode', 		fieldLabel:'<s:text name="common.form.logonnumber"/>',		readOnly:true }},
				{colIndex:0, field:{xtype : 'combotree', 	name:'unitname',		fieldLabel:'<s:text name="admin.user.userunit"/>',	passName:'unitid',	allowBlank : false,	tree:treepanel_c}},
				//{colIndex:0, field:{xtype : 'combo', 		name:'usertype1', 		fieldLabel:'<s:text name="admin.user.usertype"/>', 			hiddenName:'usertype', 	allowBlank : false,editable:false , blankText : '', store:userTypeStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{colIndex:0, field:{xtype : 'textfield', 	name:'username', 		fieldLabel:'<s:text name="admin.user.username"/>',allowBlank : false,	blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.username"/></s:param></s:text>',		maxLength : 20,maxLengthText : '长度不能大于20位'	}},
				{colIndex:0, field:{xtype : 'combo', 		name:'sex1', 			fieldLabel:'<s:text name="admin.user.usersex"/>', 			hiddenName:'sex', 		allowBlank : true,editable:false , blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.usersex"/></s:param></s:text>', store:userSexStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				
				//{colIndex:0, field:{xtype : 'textfield', 	name:'tellerno', 		fieldLabel:'<s:text name="admin.user.tellerno"/>',allowBlank : true,			blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.tellerno"/></s:param></s:text>',  maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:1, field:{xtype : 'combo', 		name:'state1', 			fieldLabel:'<s:text name="admin.user.userstatus"/>', 		hiddenName:'state', allowBlank : false,editable:false , blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.userstatus"/></s:param></s:text>', store:userStatusStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'mailbox',  		fieldLabel:'<s:text name="admin.user.useremail"/>',			blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.useremail"/></s:param></s:text>', vtype : 'email', maxLength : 30,maxLengthText : '长度不能大于30位'}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'cellphone', 		fieldLabel:'<s:text name="admin.user.usermobilephone"/>',	blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.usermobilephone"/></s:param></s:text>', maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^[0-9]*$/, regexText:'只能输入数字'}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'telphone', 		fieldLabel:'<s:text name="admin.user.userphone"/>',			blankText : '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.user.userphone"/></s:param></s:text>',  maxLength : 20,maxLengthText : '长度不能大于20位'}},
				{colIndex:1, field:{xtype : 'hidden', 		name:'userid'}}
				
				],
				[
					{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close();}}
				]
			);
			var quartersWindow = new SelfFormWindow('quartersWindow', '分配岗位', 200, 120, 90, 1,
					[
					{colIndex:0, field:{xtype : 'combo', 		name:'infoex3', 			allowBlank : false, forceSelection:true, fieldLabel:'岗位', 	hiddenName:'infoex3', blankText : '请选择岗位', 		store:quartersStore, 	displayField:'dictValueDesc', valueField:'dictValue'}},
					{colIndex:0, field:{xtype : 'hidden', 	name:'userid', id:'userid_quarters'}}
					],
					[
						{text:'提交', formBind:true, handler : onQuartersclicked},
						{text:'<s:text name="common.button.cancel"/>', handler: function(){quartersWindow.close();}}
					],'left',50
				);
			var assignwindow = new SelfFormWindow('roleAssignWindow', '分配用户角色', 600, 400, 200, 2,
				[
				 {colIndex:0, field:{xtype:'grid', id:'rolepanel1', title:'已分配的角色', width:260, height:300, cm:columnModel1, sm:rsM1, store:assignedRoleStore, stripeRows:true}},
				 {colIndex:0, field:{html:'<font style="color:#FF0000;font-size:15px">注:双击添加或删除角色</font>'}},
				 {colIndex:1, field:{xtype:'grid', id:'rolepanel2', title:'未分配的角色', width:260, height:300, cm:columnModel2, sm:rsM2, store:systemRoleStore, stripeRows:true}}
				],
				[
					{text:'<s:text name="common.button.submit"/>', handler : onassignclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){assignwindow.close();}}
				]
			);
			Ext.getCmp('rolepanel1').on('rowdblclick',function(grid, rowindex,e){
				
    			var record = grid.getStore().getAt(rowindex);
    			var records = [record];
    			Ext.getCmp('rolepanel2').getStore().add(records);
    			grid.getStore().remove(record);
    		});

			Ext.getCmp('rolepanel2').on('rowdblclick',function(grid, rowindex, e){
				var rolepanel1 = Ext.getCmp('rolepanel1');
    			var record = grid.getStore().getAt(rowindex);
    			var records = [record];
    			Ext.getCmp('rolepanel1').getStore().add(records);
    			grid.getStore().remove(record);
    		});

			/*主窗口按钮设置*/
			function onButtonClicked(btn_index){
				
				switch(btn_index){
				case 0://查询
					var query_obj = conditionPanel.getFields();
					//query_obj.unitlist = null;
					tree_a = treeGenerator_a.generate(false,false,false,false);
					pagequeryObj.queryPage(query_obj);
				break;
				case 1://重置
					Ext.getCmp('unitnameid').clearValue();
					conditionPanel.reset();
					Ext.getCmp('unitnameid').setPassValue(<%="'"+user.getUnit().getUnitid()+"'"%>);
					Ext.getCmp('unitnameid').setValue(<%="'" + user.getUnit().getUnitname() + "'"%>);
					//Ext.getCmp('unitnameid').setValue(<%="'" + user.getUnit().getUnitname() + "'"%>);
				break;
				case 2://添加
					tree_b = treeGenerator_b.generate(false,false,false,false);
					addwindow.open();
				break;
				case 3://修改
					tree_c = treeGenerator_c.generate(false,false,false,false);
					var records = pagequeryObj.getSelectedRecords();
					if(records === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
						//var recorddata = records[0];
						editwindow.open();
						//alert("unitid="+records[0].data.unitid);
						if(records[0].data.sex==null){
							records[0].data.sex=2;
						}
						editwindow.updateFields(records[0]);
					}
				break;
				case 4://删除
					var submitdata = pagequeryObj.getSelectedObjects(['userid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						for(var i=0;i<submitdata.length;i++){
						if(<%="'"+user.getUserid()+"'"%>==submitdata[i]['userid']){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除自己');
							return;
						}
					}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/admin/systemuser_deleteuser',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					}
				break;
				case 5://角色分配
					var submitdata = pagequeryObj.getSelectedObjects(['userid','unitid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(submitdata.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
						requestAjax('<%=basePath%>/admin/systemuser_getSystemUserRoles',submitdata[0],function(sRet){
							assignwindow.open();
							assignedRoleStore.loadData(sRet.field2);
							systemRoleStore.loadData(sRet.field1);
						});
					}
					
				break;
				case 6://密码重置
					var submitdata = pagequeryObj.getSelectedObjects(['userid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定重置选中用户的密码吗？默认为“888888”',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/admin/systemuser_resetuserspasswd',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					}
				break;
				case 7://导出用户excel
					if(pagequeryObj.selfPagingTool.totalNum == -1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.queryneeded"/>');
					}else if(pagequeryObj.selfPagingTool.totalNum == 0 ){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commoninfo"><s:param>'+'查询记录数为0，无数据需要下载！'+'</s:param></s:text>');
					}/*else if(pagequeryObj.selfPagingTool.totalNum > 50000){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.recordsoverlimit"/>');
					}*/else{
						requestAjax('<%=basePath%>common_exportXls','',function(sRet){
							if(sRet.res){
								Ext.MessageBox.alert("导出失败",sRet.message);
							}else{
								/* var query_obj = conditionPanel.getFields();
								var a = document.forms[4].querycondition_str;
								var b = document.forms[4];
								var c = document;
								document.forms[4].querycondition_str.value = Ext.util.JSON.encode(query_obj);
								document.forms[4].submit(); */
								
								var query_obj = conditionPanel.getFields();
								var myForm = document.getElementById("formId");
								var myInputs = myForm.getElementsByTagName("input");
								myInputs[0].value = Ext.util.JSON.encode(query_obj);
								myForm.submit();
							}
						});
					}
				break;
				case 8://赋予用户自助权限
					/* var userid_temp = submitdata[0]['userid'];
					alert("获得usercode："+userid_temp); */
					var submitdata = pagequeryObj.getSelectedObjects(['userid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(submitdata.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
						//获得userid
						var userid = submitdata[0]["userid"];
						//弹出岗位下拉窗
						quartersWindow.open();
						Ext.getCmp('userid_quarters').setValue(userid);
					}
				break;
				case 9://取消用户自助权限
					var submitdata = pagequeryObj.getSelectedObjects(['userid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定取消用户岗位?',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/admin/systemuser_cancelQuarters',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					}
				break;
				case 10://赋予用户授权权限
					var submitdata = pagequeryObj.getSelectedObjects(['userid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定赋予用户授权权限?',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/admin/systemuser_authuser',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>',function(id){
										var query_obj = conditionPanel.getFields();
										//query_obj.unitlist = null;
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					}
				break;
				case 11://取消用户的授权权限
					var submitdata = pagequeryObj.getSelectedObjects(['userid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定取消用户授权权限?',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/admin/systemuser_cancelAuth',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>',function(id){
										var query_obj = conditionPanel.getFields();
										//query_obj.unitlist = null;
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					}
				break;
				}
			}

			function onaddclicked(){
				var submitData = addwindow.getFields();
				//submitData.createuser=null;
				//submitData.createdate=new Date();
				
				if(submitData['usercode'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入登陆账号');
					return;
				}
				if(submitData['username'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入姓名');
					return;
				}
				requestAjax('<%=basePath%>/admin/systemuser_adduser', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						//query_obj.unitlist = null;
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
			}

			function oneditclicked(){
				var submitData = editwindow.getFields();
				//submitData.lastmoduser=null;
				//submitData.lastmoddate=new Date();

				if(submitData['usercode'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入登陆账号');
					return;
				}
				if(submitData['username'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入姓名');
					return;
				}
				requestAjax('<%=basePath%>/admin/systemuser_edituser',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						//query_obj.unitlist = null;
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
			}
			
			/*弹出窗口中的分配角色按钮*/
			function onassignclicked(){
				var roles = new Array();
				assignedRoleStore.each(function(record){
					var role = {roleid:record.get('roleid')};
					roles.push(role);
				});
				//if(roles.length>1){
				//	Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法赋予用户多个角色');
				//	return;
				//}
				var submitdata = pagequeryObj.getSelectedObjects(['userid','unitid']);
				var submitData = submitdata[0];
				submitData['roles'] = roles;
				requestAjax('<%=basePath%>/admin/systemuser_assignUserRole',submitData,function(sRet){
					
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','操作成功',function(id){
						var query_obj = conditionPanel.getFields();
						//query_obj.unitlist = null;
						pagequeryObj.queryPage(query_obj);
					});
					assignwindow.close();
				});
			}
			
			function onQuartersclicked(){
				var submitData = quartersWindow.getFields();
				requestAjax('<%=basePath%>/admin/systemuser_assignQuarters', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','分配岗位成功',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					quartersWindow.close();
				});
			}
			
			function buildLayout(){
				var viewport = new Ext.Viewport({
				    layout : "border",
					items : [conditionPanel.toolbarwindow,pagequeryObj.pagingGrid]
				});
			}
      buildLayout();
      Ext.getCmp('unitnameid').setPassValue(<%="'"+user.getUnit().getUnitid()+"'"%>);
      
		} 
	</script>

  </head>
  
  <body>
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="recordAddWindow"></div>
	<div id="recordEditWindow"></div>
	<div id="roleAssignWindow"></div>
	<div id="quartersWindow"></div>
	<div id="unitname"></div>
	<div id="viewWindow"></div>
	
	<!-- <s:form action="abt/admin/systemuser_exportExcel" target="REPORTRESULTFRAME">
		<s:hidden name="querycondition_str"></s:hidden>
	</s:form>
	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/> -->
	
	<form id="formId" name="queueUser_exportExcel" action="/abt/admin/systemuser_exportExcel" target="REPORTRESULTFRAME" method="post">
		<input type="hidden" name="querycondition_str" value="" id="inputId"/>
	</form>
	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
	
  </body>
</html>