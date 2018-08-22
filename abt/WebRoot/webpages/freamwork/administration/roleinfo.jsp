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
    
    <title>角色信息管理</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
		var pagereturn = ${actionresult};
		//定义一些公用属性
		var addwindow;
		var treeGenerator;
		var tree;
		var treeGenerator1;
		var tree1;
		//部门集合
		var unitListStore = new Ext.data.JsonStore({
			storeId : 'unitListStore',
			fields : ['unitid','unitname','unitlevel'],
			data   : pagereturn.field1
		});
		//角色状态
		var roleStatusStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=isabled',
			root   : 'field1', 
			autoLoad:true 
		});
		//角色类型
		var roleTypeStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=roletype',
			root   : 'field1', 
			autoLoad:true 
		});
		//角色标识
		var defaultStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=default_flag',
			root   : 'field1', 
			autoLoad:true 
		});
		var roleLevelStore=new Ext.data.SimpleStore({ 
			data:[['1','1 - 一级'],['2','2 - 二级'],['3','3 - 三级'],['4','4 - 四级'],['5','5 - 五级'],
				  ['6','6 - 六级'],['7','7 - 七级'],['8','8 - 八级'],['9','9 - 九级']],
			fields : ['key','value']
		});
		
		//角色状态显示模式
//		function getRoleStatus(value, cellmeta, record, rowIndex, columnIndex, store){
//			//value=value.toString();
//			var map=roleStatusStore.getAt(roleStatusStore.findExact('dictValue',value));
//			if(map === undefined)
//				return value;
//			else
//				return map.get('dictValueDesc'); 
//		}
		
		var logonUser = pagereturn.field3;//登录用户信息

		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		
		var jsonMeta = {nodeId:'moduleid',parentNodeId:'parentmoduleid',nodeName:'modulename',nodeHref:'',leafField:'isleaf',nodeType:'moduletype',nodeOrder:'moduleorder',nodeLevel:'modulelevel'};
		
		Ext.onReady(loadPage);
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var editwindow;
			
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '角色', 120, 1,
				[
				{rowIndex:0, field:{xtype:'textfield', name:'rolename', fieldLabel:'<s:text name="admin.role.rolename"/>',emptyText:'----<s:text name="admin.role.rolename"/>----'}},
				{rowIndex:0, field:{xtype:'combo', name:'enabled1', hiddenName:'enabled', fieldLabel:'<s:text name="admin.role.rolestatus"/>',emptyText:'----<s:text name="admin.role.rolestatus"/>----', editable:false,store:roleStatusStore, displayField:'dictValueDesc', valueField:'dictValue'}}
				],
				[
				{iconCls: "x-image-query", text:'<s:text name="common.button.query"/>'},//查询
				{iconCls: "x-image-reset", text:'<s:text name="common.button.reset"/>'},//重置
				{iconCls: "x-image-application_form_add", text:'<s:text name="common.button.addrecord"/>'},//添加
				{iconCls: "x-image-application_form_edit", text:'<s:text name="common.button.editrecord"/>'},//修改
				{iconCls: "x-image-application_form_delete", text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked
			);
			conditionPanel.open();

			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/admin/systemrole_queryrole',
				['roleid', 'rolename','roledescribe','default_flag','enabled','remark1','remark2','remark3','roletype','rolelevel'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'<s:text name="admin.role.rolename"/>',			dataIndex:'rolename',		width:150},
				{header:'<s:text name="admin.role.roledescription"/>',	dataIndex:'roledescribe',	width:150},
				{header:'<s:text name="admin.role.rolestatus"/>',		dataIndex:'enabled',		width:150, renderer:function(value){
          		 	return getDictValue(value.toString(),roleStatusStore,'dictValue','dictValueDesc');//翻译字段方法
       			 }},
				{header:'角色类型',		dataIndex:'roletype',		width:150, renderer:function(value){
          		 	return getDictValue(value,roleTypeStore,'dictValue','dictValueDesc');//翻译字段方法
       			 }},
       			{header:'角色标识',	dataIndex:'default_flag',	width:150,hidden:true},
       			{header:'角色级别',	dataIndex:'rolelevel',	width:90, renderer:function(value){
          		 	//return getDictValue(value.toString(),roleStatusStore,'dictValue','dictValueDesc');//翻译字段方法
          		 	if(value==''||value==null){
          		 		return 'X - 无级别';
          		 	}else if(value=='0'){
          		 		return '0 - 最高级';
          		 	}
          		 	return getDictValue(value,roleLevelStore,'key','value');//翻译字段方法
      			 }}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			treeGenerator = new SelfTreeGenerator(pagereturn.field4,jsonMeta,'<%=basePath%>',['x-image-house','','x-image-leaf','x-image-query'], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
					tree = treeGenerator.generate(true,false,true,false);
					
			treeGenerator.uncheckAllChildren(tree);
			
			treeGenerator1 = new SelfTreeGenerator(pagereturn.field4,jsonMeta,'<%=basePath%>',['x-image-house','','x-image-leaf','x-image-query'], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
			tree1 = treeGenerator1.generate(true,false,true,false);
			
				editwindow = new SelfFormWindow('recordEditWindow', '<s:text name="admin.role.editsystemroleinfo" />', 600, 400, 200, 2,
						[
						{colIndex:0, field:{xtype : 'hidden', 		name:'roleid'}},
						{colIndex:0, field:{xtype : 'textfield', 	name:'rolename', 		fieldLabel:'<s:text name="admin.role.rolename"/>',			allowBlank:false, blankText: '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.role.rolename"/></s:param></s:text>', maxLength : 15,maxLengthText : '长度不能大于15位'}},
						{colIndex:0, field:{xtype : 'textfield', 	name:'roledescribe', 	fieldLabel:'<s:text name="admin.role.roledescription"/>',	maxLength : 150,maxLengthText : '长度不能大于150位'}},
						{colIndex:0, field:{xtype : 'combo', 		name:'enabled1', 		fieldLabel:'<s:text name="admin.role.rolestatus"/>',		hiddenName:'enabled',allowBlank : false, forceSelection:true ,blankText: '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.role.rolestatus"/></s:param></s:text>',store:roleStatusStore, displayField:'dictValueDesc', valueField:'dictValue'}},
						{colIndex:0, field:{xtype : 'combo', 		name:'roletype', 		fieldLabel:'角色类型',		hiddenName:'roletype',allowBlank : false, forceSelection:true ,blankText: '<s:text name="common.info.mustwrite"><s:param>角色类型</s:param></s:text>',store:roleTypeStore, displayField:'dictValueDesc', valueField:'dictValue'}},
						{colIndex:0, field:{xtype : 'combo', 		name:'rolelevel', 		fieldLabel:'角色级别', id:'rolelevelId', hiddenName:'rolelevel', allowBlank : false, forceSelection:true, blankText: '请选择角色级别',store:roleLevelStore, displayField:'value', valueField:'key'}},
						//{colIndex:0, field:{xtype : 'combo', 	name:'default_flag', 	fieldLabel:'角色标识',hiddenName:'default_flag',editable:false ,blankText: '角色标识',store:defaultStore, displayField:'dictValueDesc', valueField:'dictValue',hidden:true}},						
						{colIndex:1, field:{xtype : 'treepanel', 	root:tree1, 			title:'<s:text name="admin.role.rolemodule"/>', 			autoScroll:true, rootVisible:true, height:300, width:270}}
						],
						[
							{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
							{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close();}}
						]
					);
			
			//按钮操作
			function onButtonClicked(index){
				switch(index){
				case 0://查询
					var query_obj = conditionPanel.getFields();
					pagequeryObj.queryPage(query_obj);
					break;
				case 1://重置
					conditionPanel.reset();
					break;
				case 2://添加
					if(!addwindow){
					addwindow = new SelfFormWindow('recordAddWindow', '<s:text name="admin.role.addsystemroleinfo" />', 600, 400, 200, 2,
						[
						{colIndex:0, field:{xtype : 'textfield', 	name:'rolename', 		fieldLabel:'<s:text name="admin.role.rolename"/>',allowBlank:false, blankText: '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.role.rolename"/></s:param></s:text>', maxLength : 15,maxLengthText : '长度不能大于15位'}},
						{colIndex:0, field:{xtype : 'textfield', 	name:'roledescribe', 	fieldLabel:'<s:text name="admin.role.roledescription"/>',maxLength : 150,maxLengthText : '长度不能大于150位'}},
						{colIndex:0, field:{xtype : 'combo', 		name:'enabled1', 		fieldLabel:'<s:text name="admin.role.rolestatus"/>', value:'1', hiddenName:'enabled', allowBlank : false, forceSelection:true, blankText: '<s:text name="common.info.mustwrite"><s:param><s:text name="admin.role.rolestatus"/></s:param></s:text>',store:roleStatusStore, displayField:'dictValueDesc', valueField:'dictValue'}},
						{colIndex:0, field:{xtype : 'combo', 		name:'roletype', 		fieldLabel:'角色类型',value:'1',hiddenName:'roletype', allowBlank : false, forceSelection:true, blankText: '<s:text name="common.info.mustwrite"><s:param>角色类型</s:param></s:text>',store:roleTypeStore, displayField:'dictValueDesc', valueField:'dictValue'}},
						{colIndex:0, field:{xtype : 'combo', 		name:'rolelevel', 		fieldLabel:'角色级别',value:'1', hiddenName:'rolelevel', allowBlank : false, forceSelection:true, blankText: '请选择角色级别',store:roleLevelStore, displayField:'value', valueField:'key'}},
						//{colIndex:0, field:{xtype : 'combo', 	name:'default_flag', 	fieldLabel:'角色标识',hiddenName:'default_flag',editable:false ,blankText: '角色标识',store:defaultStore, displayField:'dictValueDesc', valueField:'dictValue'}},
						{colIndex:1, field:{xtype : 'treepanel', 	root:tree, 				title:'<s:text name="admin.role.rolemodule"/>', id:'moduleTreeadd',  autoScroll:true, rootVisible:true, height:300, width:270}}
						],
						[
							{text:'<s:text name="common.button.add"/>', 	handler : onaddclicked, formBind:true},
							{text:'<s:text name="common.button.cancel"/>', 	handler: function(){addwindow.close();}}
						]
					);}
                    treeGenerator.uncheckAllChildren(tree);
					addwindow.open();
					break;
				case 3://修改
					var records = pagequeryObj.getSelectedObjects(['roleid', 'rolename','roledescribe','enabled','roletype','default_flag']);
					if(records === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
						treeGenerator1.uncheckAllChildren(tree1);
						var record = pagequeryObj.getSelectedRecords();
						var recorddata = records[0];
						//无法修改比自己角色级别高或相等的角色.
						var rolelevel = record[0].get('rolelevel');
						var roles = logonUser.roles;
						var flag = false;
						for ( var i = 0; i < roles.length; i++) {
							var level = roles[i]['rolelevel'];
							if(level == 0&&level != ''){
								flag = true;
								break;//0级永远允许修改任何角色
							} else if(level!=''){
								if(level<rolelevel){
									flag = true;
									break;
								}
							}
						}
						if(!flag){
							Ext.MessageBox.alert('错误提示','无法修改超过或等于用户级别的角色,请确认');
							return;
						}
						requestAjax('<%=basePath%>/admin/systemrole_getSystemRoleModule',recorddata,function(sRet){
							if(rolelevel==0){
								Ext.getCmp('rolelevelId').hide();
							}else{
								Ext.getCmp('rolelevelId').show();
							}
							editwindow.open();
							editwindow.updateFields(record[0]);
							treeGenerator1.autoCheckTreeNode(sRet.field1);
						});
					}
					break;
				case 4://删除
					var submitdata = pagequeryObj.getSelectedObjects(['roleid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/admin/systemrole_deleterole',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					}
					//无法删除比自己角色级别高或相等的角色.
					var rolelevels = pagequeryObj.getSelectedObjects(['rolelevel']);
					for ( var j = 0; j < rolelevels.length; j++) {
						var rolelevel = rolelevels[j]['rolelevel'];
						if(rolelevel==0){
							Ext.MessageBox.alert('错误提示','无法删除最高级别的角色,请确认.');
							return;
						}
						if(rolelevel!=null){
							var roles = logonUser.roles;
							var flag = false;
							for ( var i = 0; i < roles.length; i++) {
								var level = roles[i]['rolelevel'];
								if(level == 0&&level != ''){//0级永远允许删除非0的任何角色
									flag = true;
									break;
								} else if(level!=''){//用户的角色级别非空
									if(level<rolelevel){//可以删除低级角色级别
										flag = true;
										break;
									}
								}
							}
							if(!flag){
								Ext.MessageBox.alert('错误提示','无法删除超过或等于用户级别的角色,请确认.');
								return;
							}
						}
					}
					break;
				}
			}
			//添加按钮设置
			function onaddclicked(){
				var submitData = addwindow.getFields();
				if(submitData['rolename'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入角色名称');
					return;
				}
				//无法添加比自己角色级别高或相等的角色.
				var roles = logonUser.roles;
				var flag = false;
				for ( var i = 0; i < roles.length; i++) {
					var level = roles[i]['rolelevel'];
					if(level == 0&&level != ''){//0级永远允许添加任何角色
						flag = true;
						break;
					} else if(level!=''){//目标非空
						if(level<submitData.rolelevel){//可以添加低级角色级别
							flag = true;
							break;
						}
					}
				}
				if(!flag){
					Ext.MessageBox.alert('错误提示','无法赋予超过或等于用户级别的角色级别,请确认.');
					return;
				}
				var rolename = submitData['rolename'].trim();
				submitData['rolename']= rolename;
				var jsonMeta = {nodeId:'moduleid',parentNodeId:'parentmoduleid',nodeName:'modulename',nodeHref:'',nodeTarget:'',nodeType:'moduletype'};
				submitData['modules'] = SelfTreeGenerator.generateJsonArrayForTree(tree,jsonMeta);
				requestAjax('<%=basePath%>/admin/systemrole_addrole',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
			}
			//修改按钮设置
			function oneditclicked(){
				var submitData = editwindow.getFields();
				if(submitData['rolename'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入角色名称');
					return;
				}
				//无法修改比自己角色级别高或相等的角色.
				var roles = logonUser.roles;
				var flag = false;
				for ( var i = 0; i < roles.length; i++) {
					var level = roles[i]['rolelevel'];
					if(level == 0&&level != ''){//0级永远修改添加任何角色
						flag = true;
						break;
					} else if(level!=''){//目标非空
						if(level<submitData.rolelevel){//可以修改低级角色级别
							flag = true;
							break;
						}
					}
				}
				if(!flag){
					Ext.MessageBox.alert('错误提示','无法赋予超过或等于用户级别的角色级别,请确认.');
					return;
				}
				var rolename = submitData['rolename'].trim();
				submitData['rolename']= rolename;
				var jsonMeta = {nodeId:'moduleid',parentNodeId:'parentmoduleid',nodeName:'modulename',nodeHref:'',nodeTarget:'',nodeType:'moduletype'};
				submitData['modules'] = SelfTreeGenerator.generateJsonArrayForTree(tree1,jsonMeta);
				requestAjax('<%=basePath%>/admin/systemrole_editrole',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
			}
			//页面布局设置
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
	<div id="viewWindow"></div>
  </body>
</html>