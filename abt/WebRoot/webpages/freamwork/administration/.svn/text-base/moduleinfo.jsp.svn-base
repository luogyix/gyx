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
    
    <title>操作菜单管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css" />
	<style type="text/css">
       		.ext-ie .x-tree .x-panel-body {position: relative;}  
			.ext-ie .x-tree .x-tree-root-ct {position: absolute;} 
     </style>  
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<style type="text/css">
        .x-grid3-cell-inner{white-space:normal !important;}
        .x-grid3-cell{vertical-align: middle !important;}
    </style>  
	<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		var parentType;
		var results = ${actionresult};
		var systemModules = results.field1;//系统菜单数据
		var systemtreepanel;
		var addmoduletype,editmoduletype;		
		var pagequeryObj;
		//菜单类型存储器
		var menuTypeStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=moduletype',
			root   : 'field1', autoLoad:true
		});
		//日志标志存储器
		var logflagListStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=islogrecord',
			root   : 'field1', autoLoad:true
		});
		//授权表示存储器
		//var authflagStore = new Ext.data.JsonStore({
		//	fields : ['dictValue','dictValueDesc'],
		//	url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=authflag',
		//	root   : 'field1', autoLoad:true
		//});
		
		//开始构建菜单树
		//定义json串对应意义，按照需要定义
		var jsonMeta = {nodeId:'moduleid',parentNodeId:'parentmoduleid',nodeName:'modulename',nodeHref:'moduleaction',nodeTarget:'',leafField:'',nodeType:'moduletype',nodeOrder:'moduleorder',nodeLevel:'modulelevel'};
		//定义树生成器
		var treeGenerator = new SelfTreeGenerator(systemModules,jsonMeta,'<%=basePath%>',['x-image-house','x-image-folder','x-image-leaf','x-image-query'], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		//定义菜单树
		var systemModuleTree = treeGenerator.generate(false,false,false,false);
		
		var addwindow = null;
		var editwindow = null;
		//定义数据字段
		var ModuleRecord = Ext.data.Record.create([
			'moduleid', 'modulename', 'moduleaction', 'moduleorder', 'modulelevel', 'parentmoduleid', 'moduletype', 'logflag','authflag','remark1','remark2','remark3']);
		//定义右键弹出的按钮
		var treecontextmenu = new Ext.menu.Menu({
    		id:'treecontextmenu',
    		items: [
				{id:'nodeadd', text:'<s:text name="admin.module.addnode"/>'},
				{id:'nodeedit', text:'<s:text name="admin.module.editnode"/>'},
				{id:'nodedel', text:'<s:text name="admin.module.delnode"/>'}
    		]
		});
		//定义右键响应事件
		treecontextmenu.on('click',onMenuClick,this);
		
		var activetreenode = null;
		var activenodedata = null;
		//菜单弹出事件
		function onmenu(node,e)
		{
			activetreenode = node;
			for(var i=0;i<systemModules.length;i++)
			{
				if(systemModules[i][jsonMeta.nodeId] === node.id){
					activenodedata = systemModules[i];
					break;
				}
			}
			
			if(activenodedata[jsonMeta.nodeType] === 4)
				treecontextmenu.items.items[0].hide();
			else
				treecontextmenu.items.items[0].show();
			e.preventDefault();
    		treecontextmenu.showAt(e.getXY());
		}
		
		//菜单点击事件
		function onMenuClick(menu,menuitem,e)
		{
			switch(menuitem.id)
			{
				case "nodeadd"://如果是添加按钮
					parentType=this.activenodedata['moduletype'];
					if(parentType==3){//如果父节点是按钮，不能添加了
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>按钮下不能添加节点了！</s:param></s:text >');
							return;
					};
					var privilegeType=0;
					if(parentType==2){
						privilegeType=1;
					}
					var record = new ModuleRecord(
					    {
					    	parentmoduleid:this.activenodedata[jsonMeta.nodeId],
					    	modulelevel:this.activenodedata[jsonMeta.nodeLevel]+1,
					    	privilegeType:privilegeType
					    }
					);
					
					addwindow.open();
					addwindow.updateFields(record);
				break;
				case "nodeedit"://修改按钮
					var privilegetype=0;
					if(this.activenodedata[jsonMeta.nodeType]==3){
						var privilegetype=1;
					}
					var record = new ModuleRecord(
					    {
					    	moduleid:this.activenodedata[jsonMeta.nodeId],
					    	modulename:this.activenodedata[jsonMeta.nodeName],
					    	moduleaction:this.activenodedata[jsonMeta.nodeHref],
					    	moduleorder:this.activenodedata[jsonMeta.nodeOrder],
					    	modulelevel:this.activenodedata[jsonMeta.nodeLevel],
					    	parentmoduleid:this.activenodedata[jsonMeta.parentNodeId],
					    	moduletype:this.activenodedata[jsonMeta.nodeType],
					    	logflag:this.activenodedata['logflag'],
					    	authflag:this.activenodedata['authflag'],
					    	privilegetype:privilegetype
					    }
					);
					editwindow.open();
					editwindow.updateFields(record);
				break;
				case "nodedel"://删除按钮
					Ext.MessageBox.confirm('<s:text name="common.info.title"/>','删除当前模块后，该模块的子模块也将删除,确定要删除吗？',
						function(id){
							if(id == 'yes'){
								var submitData = {};
								var nid=activenodedata[jsonMeta.nodeId];
								submitData[jsonMeta.nodeId] = nid;
								if(submitData[jsonMeta.nodeId]==0){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>抱歉，根节点不能删除！</s:param></s:text >');
									return;
								}
								requestAjax('<%=basePath%>admin/systemmodule_deleteModule', submitData, function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(id){
										systemtreepanel.getNodeById(nid).remove();
										//查询一下节点
										var modulename=Ext.getCmp('modulename1').getValue();
										var query_obj= {"start":1,"limit":20,"modulename":modulename};
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						}
					);
				break;
			}
		}
		//开始加载页面
		Ext.onReady(loadPage);
		function loadPage()
		{
			//取界面长宽
			var clientHeight = document.body.clientHeight;
			var clientWidth  = document.body.clientWidth;
			//定义树的宽度
			var treeWidth=220;
			//定义菜单树面板
			systemtreepanel = new Ext.tree.TreePanel({
				id : 'devmoduletree', applyTo:'modulepanel', title:'<s:text name="admin.module.moduletitle"/>', 
				autoScroll:true, root: systemModuleTree,  
				height :clientHeight-25, autoWidth : true, listeners : {contextmenu : onmenu}});
			//定义点击事件
			systemtreepanel.on('click', function(node) {
				var moduleId = node.attributes.id;
				var query_obj= {"start":1,"limit":20,"moduleid":moduleId};
				pagequeryObj.queryPage(query_obj);
			});
		//定义添加菜单窗口
			addwindow = new SelfFormWindow('addwindow', '<s:text name="admin.module.adddlgtitle"/>', 250, 350, 200, 1,
				[
				{colIndex:0, field:{xtype : 'textfield', fieldLabel : '<s:text name="admin.module.modulename"/>', name:'modulename',maxLength : 20,maxLengthText : '长度不能大于20位',allowBlank : false, blankText : '请填写名称！'}},
				{colIndex:0, field:{xtype : 'textfield', fieldLabel : '<s:text name="admin.module.moduleaction"/>', name : 'moduleaction',maxLength : 350,maxLengthText : '长度不能大于350位',regex:/^[^\u4e00-\u9fa5]*$/, regexText:'不能输入中文'}},
				{colIndex:0, field:{xtype : 'textfield', fieldLabel : '<s:text name="admin.module.moduleorder"/>', name : 'moduleorder',maxLength : 10,maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/, regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'combo', name : 'moduletype', hiddenName:'moduletype',id : 'addmoduletype',editable :false,
					fieldLabel : '<s:text name="admin.module.moduletype"/>', store:menuTypeStore, displayField:'dictValueDesc', valueField:'dictValue',allowBlank : false, blankText : '请选择菜单类型！'}},
				{colIndex:0, field:{xtype : 'combo', name : 'logflag1', hiddenName:'logflag',
					fieldLabel : '<s:text name="admin.module.logflag"/>', store:logflagListStore, displayField:'dictValueDesc', valueField:'dictValue',editable :false,allowBlank : false, blankText : '请选择是否记录日志！'}},
				//{colIndex:0, field:{xtype : 'combo', name : 'authflag', id :'addauthflag',editable :false,
				//	fieldLabel : '是否需要授权', store: authflagStore, hiddenName:'authflag',displayField:'dictValueDesc', valueField:'dictValue'}},
				{colIndex:0, field:{xtype : 'hidden', name : 'privilegeType'}},
				{colIndex:0, field:{xtype : 'hidden', name : 'modulelevel'}},
				{colIndex:0, field:{xtype : 'hidden', name : 'parentmoduleid'}}
				],
				[
					{text:'<s:text name="common.button.add"/>',formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			
			//addmoduletype=Ext.getCmp('addmoduletype');					
			//addmoduletype.on('select',funSetAddAuthType,this);
			//function funSetAddAuthType(combo, record, index){
			//	var temp=addmoduletype.value;
			//	if (temp!="" && temp =="3") {//菜单类型是"按钮",必须授权
			//		Ext.getCmp('addauthflag').readOnly =false;
			//	} else {
			//		Ext.getCmp('addauthflag').setValue("");
			//		Ext.getCmp('addauthflag').readOnly =true;
			//	}							
			//}		
			
		//定义添加按钮函数
			function onaddclicked(){
				var submitData = addwindow.getFields();
				var type=submitData.moduletype;
				var parentNodeId=submitData.parentmoduleid;
				
				if(submitData['modulename'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入菜单名称');
					return;
				}
				
				if(parentType==1||parentType==0){//如果父节点是根节点或文件夹,那么添加节点必须是文件夹或功能模块
					if(type!=2&&type!=1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>根节点或文件夹下只能添加文件夹或功能模块！</s:param></s:text >');
						return;
					}
				}else if(parentType==2){//如果父节点是模块，节点必须是按钮
					if(type!=3){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>功能下只能添加按钮！</s:param></s:text >');
						return;
					}
				}
				
				//var temp=Ext.getCmp('addmoduletype').value;
				//if (temp!="" && temp =="3") {//菜单类型是"按钮",必须授权
				//	if (Ext.getCmp("addauthflag").value=="") {
				//		Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>请选择授权标识！</s:param></s:text >');
				//		return;
				//	}
				//}
				var moduleaction = submitData["moduleaction"].trim();
				submitData["moduleaction"] = moduleaction;
				requestAjax('<%=basePath%>admin/systemmodule_addModule', submitData, function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(ret){
						systemModules = sRet.field1;
						treeGenerator = new SelfTreeGenerator(systemModules,jsonMeta,'<%=basePath%>',['x-image-house','x-image-folder','x-image-leaf','x-image-query'], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
						systemModuleTree = treeGenerator.generate(false,false,false,false);
						var nodepath= systemtreepanel.getNodeById (parentNodeId).getPath();
						systemtreepanel.setRootNode(systemModuleTree);
						systemtreepanel.expandPath(nodepath);// 保持父节点展开状态
						
					});
					addwindow.close();
				});
			}
		//定义修改菜单窗口
			editwindow = new SelfFormWindow('editwindow', '<s:text name="admin.module.editdlgtitle"/>', 250, 400, 200, 1,
				[
				{colIndex:0, field:{xtype : 'textfield', fieldLabel : '<s:text name="admin.module.moduleid"/>', readOnly : true, name:'moduleid'}},
				{colIndex:0, field:{xtype : 'textfield', fieldLabel : '<s:text name="admin.module.modulename"/>', name:'modulename',maxLength : 20,maxLengthText : '长度不能大于20位',allowBlank : false, blankText : '请填写名称！'}},
				{colIndex:0, field:{xtype : 'textfield', fieldLabel : '<s:text name="admin.module.moduleaction"/>', name:'moduleaction',maxLength : 350,maxLengthText : '长度不能大于350位',regex:/^[^\u4e00-\u9fa5]*$/, regexText:'不能输入中文'}},
				{colIndex:0, field:{xtype : 'textfield', fieldLabel : '<s:text name="admin.module.moduleorder"/>', name: 'moduleorder',maxLength : 10,maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/, regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'combo', name : 'moduletype', hiddenName:'moduletype',id : 'editmoduletype',editable :false,readOnly:true,
					fieldLabel : '<s:text name="admin.module.moduletype"/>', store:menuTypeStore, displayField:'dictValueDesc', valueField:'dictValue',allowBlank : false, blankText : '请选择菜单类型！'}},
				{colIndex:0, field:{xtype : 'combo', name : 'logflag',
					fieldLabel : '<s:text name="admin.module.logflag"/>', store:logflagListStore, displayField:'dictValueDesc', valueField:'dictValue',editable :false,allowBlank : false, blankText : '请选择是否记录日志！'}},
				//{colIndex:0, field:{xtype : 'combo', name : 'authflag',id : 'editauthflag',editable :false,
				//	fieldLabel : '是否需要授权', store: authflagStore,hiddenName:'authflag', displayField:'dictValueDesc', valueField:'dictValue'}},
				{colIndex:0, field:{xtype : 'hidden', name : 'parentmoduleid'}}
				],
				[
					{text : '<s:text name="common.button.edit"/>',formBind:true, handler: oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', handler:function(){editwindow.close()}}
				]
			);
		//定义修改按钮函数
			function oneditclicked(){
				var submitData = editwindow.getFields();
				var parentNodeId=submitData.parentmoduleid;
				if(submitData['modulename'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入菜单名称');
					return;
				}
				//var temp=Ext.getCmp('addmoduletype').value;
				//if (temp!="" && temp =="3") {//菜单类型是"按钮",必须授权
				//	if (Ext.getCmp("addauthflag").value=="") {
				//		Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>请选择授权标识！</s:param></s:text >');
				//		return;
				//	}
				//}
				var moduleaction = submitData["moduleaction"].trim();
				submitData["moduleaction"] = moduleaction;
				requestAjax('<%=basePath%>admin/systemmodule_editModule', submitData, function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(id){
						//更新菜单树
						systemModules = sRet.field1;
						treeGenerator = new SelfTreeGenerator(systemModules,jsonMeta,'<%=basePath%>',['x-image-house','x-image-folder','x-image-leaf','x-image-query'], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
						systemModuleTree = treeGenerator.generate(false,false,false,false);
						var nodepath= systemtreepanel.getNodeById (parentNodeId).getPath();
						systemtreepanel.setRootNode(systemModuleTree);
						systemtreepanel.expandPath(nodepath);// 保持父节点展开状态
						
						var moduleId = submitData['moduleid'];
						var query_obj= {"start":1,"limit":20,"moduleid":moduleId};
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
			}
		   
			//*************************************************列表定义**********************************************
			var columnSortFunc;
			//头工具栏
			var tbar=new Ext.Toolbar({
				id:'tbar',
				autoDestroy:true,
				items :[
				 new Ext.form.TextField({
					id : 'modulename1',
					name : 'modulename1',
					emptyText : '请输入菜单名称',
					enableKeyEvents : true,
					listeners : {
						specialkey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								queryModule();
							}
						}
					},
					width : 130
				}),{
					text : '查询',
					iconCls : 'x-image-query',
					handler : function() {
						queryModule();
					}
				},'-',
				{
				text : '添加子节点',
					iconCls : 'x-image-application_form_add',
					handler : function() {
						var record ={};
						var data=pagequeryObj.getSelectedRecords();
						if(data === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
						}else if(data.length !== 1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
						}else{
							parentType=data[0].data.moduletype;
							if(parentType==3){//如果父节点是按钮，不能添加了
								Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>按钮下不能添加节点了！</s:param></s:text >');
								return;
							};
							var privilegeType=0;
							if(parentType==2){//如果父节点是模块的话，添加的一定是按钮，他的权限属性为1
								privilegeType=1;
							}
							record['parentmoduleid']=data[0].data.moduleid;
							record['modulelevel']=data[0].data.modulelevel+1;
							record['privilegeType']=privilegeType;
							addwindow.open();
							addwindow.getForm().setValues(record);
						}
					}
				},'-',
				 {
					text : '修改',
					iconCls : 'x-image-application_form_edit',
					handler : function() {
						var record =pagequeryObj.getSelectedRecords();
						if(record === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
						}else if(record.length !== 1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
						}else{
							editwindow.open();																				
							editwindow.updateFields(record[0]);
							//var temp=Ext.getCmp('editmoduletype').value;
							//if (temp==3) {//菜单类型是"按钮",必须授权
							//	Ext.getCmp("editauthflag").readOnly=false;
							//}else {
							//	Ext.getCmp("editauthflag").setValue("");
							//	Ext.getCmp("editauthflag").readOnly=true;
							//}
						}
					}
				}, '-', {
					text : '删除',
					iconCls : 'x-image-application_form_delete',
					handler : function() {
						var record =pagequeryObj.getSelectedRecords();
						if(record === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}else if(record.length !== 1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
							return;
						}
						if(record[0].data.moduleid==0){
							Ext.MessageBox.alert("警告","根节点不能删除！");
							return;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','删除当前模块后，该模块的子模块也将删除,确定要删除吗？',
						function(id){
							if(id == 'yes'){
								var submitData = {};
								var nid=record[0].data.moduleid;
								submitData[jsonMeta.nodeId] = nid;
								
								requestAjax('<%=basePath%>admin/systemmodule_deleteModule', submitData, function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(id){
										systemtreepanel.getNodeById(nid).remove();
										//查询一下节点
										queryModule();
									});
								});
							}
						}
					);
					}
				}
				  
				 ]
			});
			//查询函数
			function queryModule(){
				var modulename=Ext.getCmp('modulename1').getValue();
				var query_obj= {"start":1,"limit":20,"modulename":modulename};
				pagequeryObj.queryPage(query_obj);
			}
			//列表面板
			pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth-treeWidth,clientHeight,
				'<%=basePath%>/admin/systemmodule_queryModules',
				['moduleid', 'modulename', 'parentmoduleid', 'moduletype','moduleaction','moduleorder','modulelevel','logflag','privilegeType', 'moduleImg', 'createuser', 'createdate', 'lastmoduser', 'lastmoddate','authflag','remark1','remark2','remark3'],
				[
				new Ext.grid.RowNumberer({header:'',width:50}),
				{header:'复选框'},
				{header:'<s:text name="admin.module.moduleid"/>',dataIndex:'moduleid',width:110},
				{header:'<s:text name="admin.module.modulename"/>',dataIndex:'modulename',width:180},
				{header:'父菜单号',dataIndex:'parentmoduleid',width:100},
				{header:'<s:text name="admin.module.moduletype"/>',dataIndex:'moduletype',width:100,renderer:function(value){
					if(value==0){
						return "系统根节点";
					}
          		  	return getDictValue(value.toString(),menuTypeStore,'dictValue','dictValueDesc');//翻译字段方法
       			 }},
				{header:'<s:text name="admin.module.moduleorder"/>',dataIndex:'moduleorder',width:100},
				{header:'级次',dataIndex:'modulelevel',width:100},
				{header:'<s:text name="admin.module.logflag"/>',dataIndex:'logflag',width:150,renderer:function(value){
          		 	return getDictValue(value.toString(),logflagListStore,'dictValue','dictValueDesc');//翻译字段方法
       			}},
       			//{header:'是否需要授权',dataIndex:'authflag',width:120,renderer:function(value){
          		// 	return getDictValue(value,authflagStore,'dictValue','dictValueDesc');//翻译字段方法
       			//}},
       			{header:'<s:text name="admin.module.moduleaction"/>',dataIndex:'moduleaction',width:575}//,
       			  
				//{header:'菜单图片', dataIndex:'moduleImg',width:100,renderer:function(value){
				//	if(value==undefined||value==''){
				//		return '';
				//	}else{
				//		if(value.indexOf('x-image-')!=-1){
				//			value=value.substring(8,value.length)+".png";
				//		}
				//		var pic="<img src='<%=basePath%>images/application/"+value+"'/><span>"+value+"</span>";
				//		return pic;
				//	}
				//}}
			//	{header:'创建用户',dataIndex:'createuser',width:100},
			//	{header:'创建时间',dataIndex:'createdate',width:80},
			//	{header:'最后修改用户',dataIndex:'lastmoduser',width:100},
			//	{header:'最后修改时间',dataIndex:'lastmoddate',width:80},
				],
				'<s:text name="common.pagequery.pagingtool"/>',
				columnSortFunc,
				tbar
			);
			//定义页面布局
			var viewport = new Ext.Viewport({
				    layout : "border",
				    items : [ {
					title : '<span style="font-weight:normal">系统菜单</span>',
					iconCls : 'x-image-expand-all',
					collapsible : true,
					width :  treeWidth ,
					hight :  clientHeight,
					minSize : 100,
					maxSize : clientWidth,
					split : true,
					region : 'west',
					items : [ systemtreepanel]
				}, {
					region : 'center',
					layout : 'fit',
					items : [ pagequeryObj.pagingGrid ]
				} ]
					
			});
				
		}
	</script>

  </head>
  
  <body scroll="no">
  	<div id="modulepanel"></div>
  	<div id="addwindow"></div>
  	<div id="editwindow"></div>
  	<div id="pageQueryTable"></div>
  </body>
</html>
