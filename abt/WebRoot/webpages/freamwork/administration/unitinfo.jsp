<%@ page language="java" pageEncoding="utf-8"%>
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
    
    <title>机构信息管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css" />
	
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
	<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	
	<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		
		var results = ${actionresult};
		var systemUnits = results.field1;//机构数据
	
		
		//开始构建菜单树
		//定义json串对应意义，按照需要定义
		var jsonMeta = {
				nodeId:'unitid',
				parentNodeId:'parentunitid',
				nodeName:'unitname',
				nodeHref:'',
				nodeTarget:'',
				leafField:'',
				nodeLevel:'unitlevel',
				nodeType:'unitlevel',
				parentName:'parentName'};//hml 自己添加的parentName:'parentName'
		
		//定义页面显示树
		var treeGenerator = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var unitTree = treeGenerator.generate(false,false,false,false);
				
		//定义修改窗口显示机构数
		var treeGenerator1 = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var unitTree1 = treeGenerator1.generate(false,false,false,false);
		var unitTreePanl = new Ext.tree.TreePanel({
			height:220,
			width:160,
			bbar:[new Ext.form.TextField({
		        width:180,
		        emptyText:'快速检索',
		        enableKeyEvents: true,
			    listeners:{
					render: function(f){
		                this.filter = new QM.ux.TreeFilter(unitTreePanl,{
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
		    }),{xtype:'button',text:''}],
			rootVisible : true, 
			root:unitTree1
		});
		
		var systemtreepanel;				
		var addwindow = null;
		var editwindow = null;
		var activetreenode = null;
		var activenodedata = null;
		var curNodeId;
		
		//定义数据字段
		var UnitRecord = Ext.data.Record.create(['unitid','unitname','checkcode','unitlevel','parentunitid','parentName','createuser','createdate','lastmoduser','lastmoddate','address','longitude','latitude','bank_level','near_station','bank_tel','manager_name','manager_phone','manager_tel','city','note']);
				
		//开始加载页面
		Ext.onReady(loadPage);
		function loadPage(){
			
			//定义右键弹出的按钮
			var treecontextmenu = new Ext.menu.Menu({
	    		id:'treecontextmenu',
	    		items: [
					{id:'nodeadd', text:'<s:text name="common.button.addrecord"/>'},
					{id:'nodeedit', text:'<s:text name="common.button.editrecord"/>'},
					{id:'nodedel', text:'<s:text name="common.button.deleterecord"/>'}
	    		]
			});
			//业务机构类型
			var branchTypeStore = new Ext.data.JsonStore({
				fields : ['dictValue','dictValueDesc'],
				data   : [
				          {'dictValue':'1', 'dictValueDesc':'1-省联社'},
				          {'dictValue':'2', 'dictValueDesc':'2-市联社 '},
				          {'dictValue':'3', 'dictValueDesc':'3-区县联社'},
				          {'dictValue':'4', 'dictValueDesc':'4-信用社'},
				          {'dictValue':'5', 'dictValueDesc':'5-分社/储蓄所'}
				          ]
			});
			var defaultStore = new Ext.data.JsonStore({
				fields : ['dictValue','dictValueDesc'],
				data   : [{'dictValue':'1', 'dictValueDesc':'1-创建'},{'dictValue':'2', 'dictValueDesc':'2-不创建'}]
			});
			//定义右键响应事件
			treecontextmenu.on('click',onMenuClick,this);
			
			//菜单弹出事件
			function onmenu(node,e){	
				activetreenode = node;
				for(var i=0;i<systemUnits.length;i++){
					if(systemUnits[i][jsonMeta.nodeId] == node.id){
						activenodedata = systemUnits[i];
						break;
					}
				}
				e.preventDefault();
	    		treecontextmenu.showAt(e.getXY());
			}
			
			//菜单点击事件
			function onMenuClick(menu,menuitem,e){				
				curNodeId=""+this.activenodedata[jsonMeta.nodeId];		//当前操作节点
				switch(menuitem.id){			
					case "nodeadd"://如果是添加按钮
						var record = new UnitRecord({
						    	parentName:this.activenodedata[jsonMeta.nodeName],
						    	parentunitid:this.activenodedata[jsonMeta.nodeId],
						    	unitlist:this.activenodedata['unitlist'],
						    	unitlevel:this.activenodedata[jsonMeta.nodeLevel]
						    }
						);
						addwindow.open();
						addwindow.updateFields(record);
					break;
					case "nodeedit"://修改按钮
						var record = new UnitRecord({
						    	unitid:this.activenodedata[jsonMeta.nodeId],
						    	unitname:this.activenodedata[jsonMeta.nodeName],
						    	near_station:this.activenodedata['near_station'],
						    	unitlevel:this.activenodedata[jsonMeta.nodeLevel],
						    	unitlist:this.activenodedata['unitlist'],
						    	parentName:this.activenodedata['parentName'],//  将[jsonMeta.nodeParentName] 改为 [parentName]
						    	bank_level: this.activenodedata['bank_level'],
						    	address: this.activenodedata['address'],
						    	bank_tel: this.activenodedata['bank_tel'],
						    	city: this.activenodedata['city'],
						    	checkcode: this.activenodedata['checkcode'],
						    	manager_name: this.activenodedata['manager_name'],
						    	manager_phone: this.activenodedata['manager_phone'],
						    	manager_tel: this.activenodedata['manager_tel'],
						    	longitude: this.activenodedata['longitude'],
						    	latitude: this.activenodedata['latitude'],
						    	note: this.activenodedata['note'],
						    	manager_tel: this.activenodedata['manager_tel'],
						    	unitid_old: this.activenodedata[jsonMeta.nodeId],
						    	parentunitid:this.activenodedata[jsonMeta.parentNodeId]
						    }
						);
						editwindow.open();
						if (this.activenodedata[jsonMeta.parentNodeId]==-1) {		//根节点，不能修改上级机构
								Ext.getCmp("parentid").disable();	
								Ext.getCmp("parentid").allowBlank=true;
						}
						editwindow.updateFields(record);
					break;
					case "nodedel"://删除按钮
						if (activenodedata[jsonMeta.parentNodeId]==''||activenodedata[jsonMeta.parentNodeId]==-1) {
							return Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>总行信息不能删除！</s:param></s:text >');
						}
						//判断此机构是否有下级机构
						if(activetreenode.hasChildNodes()){
							Ext.MessageBox.confirm('<s:text name="common.info.title"/>','该操作将删除本机构及相关下级机构,请确认是否删除',
								function(id){
									if(id == 'yes'){
										var submitData = new Array();
										var data = {};
										data[jsonMeta.nodeId] = activenodedata[jsonMeta.nodeId];
										data[jsonMeta.parentNodeId] = activenodedata[jsonMeta.parentNodeId];
										submitData.push(data);
										requestAjax('<%=basePath%>admin/systemunit_delUnit', submitData, function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(id){
												for(var i=0;i<submitData.length;i++){
													systemtreepanel.getNodeById(submitData[i].unitid).remove();	
													var nodepath= systemtreepanel.getRootNode().getPath();
													systemtreepanel.expandPath(nodepath);
													systemtreepanel.selectPath(nodepath);
													curNodeId = '';
													queryUnit();
												}
											});
										});
									}
								}
							);
						}else{
							Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',
								function(id){
									if(id == 'yes'){
										var submitData = new Array();
										var data = {};
										data[jsonMeta.nodeId] = activenodedata[jsonMeta.nodeId];
										data[jsonMeta.parentNodeId] = activenodedata[jsonMeta.parentNodeId];
										submitData.push(data);
										requestAjax('<%=basePath%>admin/systemunit_delUnit', submitData, function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(id){
												for(var i=0;i<submitData.length;i++){
													systemtreepanel.getNodeById(submitData[i].unitid).remove();	
													var nodepath= systemtreepanel.getRootNode().getPath();
													systemtreepanel.expandPath(nodepath);
													systemtreepanel.selectPath(nodepath);
													curNodeId = '';
													queryUnit();
												}
											});
										});
									}
								}
							);
						}
					break;
				}
			}
			
			//取界面长宽
			var clientHeight = document.body.clientHeight;
			var clientWidth  = document.body.clientWidth;
			//定义树的宽度
			var treeWidth=260;
			//定义菜单树面板
			systemtreepanel = new Ext.tree.TreePanel({
				id : 'devmoduletree', applyTo:'modulepanel', title:'机构', 
				autoScroll:true, root:unitTree,   
				height :clientHeight-26, width:treeWidth, listeners : {contextmenu : onmenu}});
			//定义点击事件
			systemtreepanel.on('click', function(node) {
				var unitid = node.attributes.id;
				curNodeId=unitid;
				Ext.getCmp('unitname1').setValue('');
				var nodepath= systemtreepanel.getNodeById(unitid).getPath();
				systemtreepanel.expandPath(nodepath); 
				
				//var query_obj= {"start":1,"limit":20,"unitid":unitid};
				//pagequeryObj.queryPage(query_obj);
				queryUnit();
			});
		//定义添加菜单窗口
			addwindow = new SelfFormWindow('addwindow', '添加机构', 700, 350, 160, 3,
				[
				{colIndex:0, field:{xtype : 'combo', 		name:'bank_level', 		fieldLabel:'机构类型',	readOnly:false, allowBlank : false, editable:false, hiddenName:'bank_level', blankText : '请选择机构类型', store:branchTypeStore, displayField:'dictValueDesc', valueField:'dictValue'}},						
				{colIndex:1, field:{xtype : 'textfield', 	name:'unitid',  		fieldLabel:'机构号',allowBlank:false, blankText : '请输入机构号',  maxLength : 9,maxLengthText : '长度不能大于9位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:2, field:{xtype : 'textfield', name:'unitname',  fieldLabel:'机构名称',allowBlank : false,blankText:'请输入机构名称',maxLength : 60,	maxLengthText : '长度不能大于60位'	}},
				{colIndex:0, field:{xtype : 'textfield', name:'parentName',fieldLabel:'所属机构',readOnly:true}},//下面有隐藏上级机构列表
				//{colIndex:1, field:{xtype : 'textfield', name:'unitorder', allowBlank : false,fieldLabel:'排序标志',blankText:'请输入排序标志',  maxLength : 4,maxLengthText : '长度不能大于4位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'address',  fieldLabel:'机构地址', 	allowBlank : true,  maxLength : 100,maxLengthText : '长度不能大于100位'}},
				{colIndex:2, field:{xtype : 'textfield', 	name:'city', fieldLabel:'所在城市',	allowBlank : true,  maxLength : 40,maxLengthText : '长度不能大于40位'}},
				{colIndex:0, field:{xtype : 'textfield', 	name:'manager_name', fieldLabel:'负责人',	allowBlank : true,blankText:'请输入负责人',  maxLength : 30,maxLengthText : '长度不能大于30位'}},
				{colIndex:1, field:{xtype : 'numberfield', 	name:'manager_phone', fieldLabel:'负责人手机',	allowBlank : true,  maxLength : 11,maxLengthText : '长度不能大于11位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:2, field:{xtype : 'textfield', 	name:'manager_tel', fieldLabel:'负责人电话',	allowBlank : true,  maxLength : 15,maxLengthText : '长度不能大于15位',regex:/^\d*\-\d*$/,regexText:'只能输入数字和-'}},				
				{colIndex:0, field:{xtype : 'textfield', 	name:'bank_tel', fieldLabel:'网点电话',	allowBlank : true,blankText:'请输入网点电话',  maxLength : 15,maxLengthText : '长度不能大于15位',regex:/^\d*\-\d*$/,regexText:'只能输入数字和-'}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'near_station', fieldLabel:'附近车站',	allowBlank : true,  maxLength : 120,maxLengthText : '长度不能大于120位'}},
				{colIndex:2, field:{xtype : 'textfield', 	name:'note', fieldLabel:'备注',	allowBlank : true,  maxLength : 100,maxLengthText : '长度不能大于100位',hidden:true}},
				{colIndex:2, field:{xtype : 'combo', 		name:'default', id:'default_add',		fieldLabel:'是否创建用户',	readOnly:false, allowBlank : false, editable:false, hiddenName:'default',store:defaultStore, displayField:'dictValueDesc', valueField:'dictValue',value:'1'}},
				{colIndex:0, field:{xtype : 'numberfield', 	name:'longitude', fieldLabel:'经度',	allowBlank : true,  allowDecimals : true,allowNegative : false, decimalPrecision:10,maxValue : 180,maxText : '经度不能大于180'}},
				{colIndex:1, field:{xtype : 'numberfield', 	name:'latitude', fieldLabel:'纬度',	allowBlank : true,  allowDecimals : true,allowNegative : false, decimalPrecision:10,maxValue : 90,maxText : '纬度不能大于90'}},
				{colIndex:0, field:{xtype : 'hidden',    name:'unitlevel'}},
				{colIndex:0, field:{xtype : 'hidden',    name:'unitlist'}},
				{colIndex:0, field:{xtype : 'hidden',    name:'parentunitid'}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			//定义添加按钮函数
			function onaddclicked(){
				var submitData = addwindow.getFields();
				var parentNodeId=submitData.parentunitid;
				/* if(submitData['default']=='2'){
					Ext.MessageBox.confirm('<s:text name="common.info.title"/>','由于选择不创建用户，创建失败，确定要关闭添加窗口吗？',
						function(id){
							if(id == 'yes'){
								addwindow.close();
								return;
							}else{
								return;
							}
					});
					return;
				} */
				if(submitData['unitname'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入机构名称');
					return;
				}
				requestAjax('<%=basePath%>admin/systemunit_addUnit', submitData, function(sRet){
					addwindow.close();
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(ret){
						//重构显示树
						systemUnits=sRet.field1;
						treeGenerator = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
						unitTree = treeGenerator.generate(false,false,false,false);
						var nodepath= systemtreepanel.getNodeById(parentNodeId).getPath();
						systemtreepanel.setRootNode(unitTree);
						systemtreepanel.expandPath(nodepath);
						systemtreepanel.selectPath(nodepath);
						//重构修改窗口机构树
						treeGenerator1 = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);						
						unitTree1 = treeGenerator1.generate(false,false,false,false);
						unitTreePanl.setRootNode(unitTree1);
						queryUnit();
					});	
				});
			}
			//定义修改菜单窗口
			editwindow = new SelfFormWindow('editwindow', '修改机构信息', 700, 350, 160, 3,
				[
				{colIndex:0, field:{xtype : 'combo', 		name:'bank_level',id:'bankLevelCom', 		fieldLabel:'机构类型',editable:false,	readOnly:true, allowBlank : false , hiddenName:'bank_level', blankText : '请选择机构类型', store:branchTypeStore, displayField:'dictValueDesc', valueField:'dictValue'}},						
				{colIndex:1, field:{xtype : 'textfield', 	name:'unitid',fieldLabel:'机构号',readOnly:true}},
				{colIndex:2, field:{xtype : 'textfield', name:'unitname',  fieldLabel:'机构名称',allowBlank : false,blankText:'请输入机构名称',maxLength : 60,readOnly:true,	maxLengthText : '长度不能大于60位'	}},
				{colIndex:0, field:{xtype : 'combotree', name:'parentName',id:'parentid',allowBlank : false,fieldLabel:'所属机构',passName:'parentunitid',tree: unitTreePanl,	readOnly:true}},
				//{colIndex:1, field:{xtype : 'textfield', name:'unitorder', allowBlank : false,fieldLabel:'排序标志',blankText:'排序标志',  maxLength : 4,maxLengthText : '长度不能大于4位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'address',  fieldLabel:'机构地址', 	allowBlank : true,  maxLength : 100,maxLengthText : '长度不能大于100位'}},
				{colIndex:2, field:{xtype : 'textfield', 	name:'city', fieldLabel:'所在城市',	allowBlank : true,  maxLength : 40,maxLengthText : '长度不能大于40位'}},
				{colIndex:0, field:{xtype : 'textfield', 	name:'manager_name', fieldLabel:'负责人',	allowBlank : true,blankText:'请输入负责人',  maxLength : 30,maxLengthText : '长度不能大于30位'}},
				{colIndex:1, field:{xtype : 'numberfield', 	name:'manager_phone', fieldLabel:'负责人手机',	allowBlank : true,  maxLength : 11,maxLengthText : '长度不能大于11位'}},
				{colIndex:2, field:{xtype : 'textfield', 	name:'manager_tel', fieldLabel:'负责人电话',	allowBlank : true,  maxLength : 15,maxLengthText : '长度不能大于15位',regex:/^\d*\-\d*$/,regexText:'只能输入数字和-'}},				
				{colIndex:0, field:{xtype : 'textfield', 	name:'bank_tel', fieldLabel:'网点电话',	allowBlank : true,blankText:'请输入网点电话',  maxLength : 15,maxLengthText : '长度不能大于15位',regex:/^\d*\-\d*$/,regexText:'只能输入数字和-'}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'near_station', fieldLabel:'附近车站',	allowBlank : true,  maxLength : 120,maxLengthText : '长度不能大于120位'}},
				{colIndex:2, field:{xtype : 'textfield', 	name:'note', fieldLabel:'备注',	allowBlank : true,  maxLength : 100,maxLengthText : '长度不能大于100位',hidden:true}},
				{colIndex:0, field:{xtype : 'numberfield', 	name:'longitude', fieldLabel:'经度',	allowBlank : true,  allowDecimals : true,allowNegative : false, decimalPrecision:10,maxValue : 180,maxText : '经度不能大于180'}},
				{colIndex:1, field:{xtype : 'numberfield', 	name:'latitude', fieldLabel:'纬度',	allowBlank : true,  allowDecimals : true,allowNegative : false, decimalPrecision:10,maxValue : 90,maxText : '纬度不能大于90'}},
				{colIndex:0, field:{xtype : 'hidden',    name:'unitid_old' , id:'unitid_old'}},
				{colIndex:0, field:{xtype : 'hidden',    name:'checkcode'}},
				{colIndex:0, field:{xtype : 'hidden',    name:'unitlevel'}},
				{colIndex:0, field:{xtype : 'hidden',    name:'unitlist'}}
				],
				[
					{text : '<s:text name="common.button.edit"/>',formBind:true, handler:oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', handler:function(){editwindow.close()}}
				]
			);
		//定义修改按钮函数
			function oneditclicked(){
				var submitData = editwindow.getFields();
				var parentNodeId=submitData.parentunitid;
				if(submitData['unitname'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入机构名称');
					return;
				}
				requestAjax('<%=basePath%>admin/systemunit_editUnit', submitData, function(sRet){
					editwindow.close();
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(id){
						//重构显示树
						systemUnits=sRet.field1;
						treeGenerator = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
						unitTree = treeGenerator.generate(false,false,false,false);
						var nodepath= systemtreepanel.getNodeById(parentNodeId).getPath();
						systemtreepanel.setRootNode(unitTree);
						systemtreepanel.expandPath(nodepath);
						systemtreepanel.selectPath(nodepath);
						curNodeId = parentNodeId;
						//重构修改窗口机构树
						treeGenerator1 = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);						
						unitTree1 = treeGenerator1.generate(false,false,false,false);
						unitTreePanl.setRootNode(unitTree1);
						queryUnit();
					});
					
				});
			}
		   
			//*************************************************列表定义**********************************************
			var columnSortFunc;
			//头工具栏
			var tbar=new Ext.Toolbar({
				id:'tbar',
				items :[
				 new Ext.form.TextField({
					id : 'unitname1',
					name : 'unitname1',
					emptyText : '请输入机构号/机构名称',
					enableKeyEvents : true,
					listeners : {
						specialkey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								queryUnit();
							}
						}
					},
					width : 150
				}), {
					text : '查询',
					iconCls : 'x-image-query',
					handler : function() {
						queryUnit();
					}
				}, '-', {
				text : '添加子机构',
					iconCls : 'x-image-application_form_add',
					handler : function() {
						var record ={};
						var data=pagequeryObj.getSelectedRecords();
						if(data === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}else if(data.length>1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
							return;
						}else{
							record['parentunitid']=data[0].data.unitid;
							record['unitlevel']=parseInt(data[0].data.unitlevel);
							record['unitlist']=data[0].data.unitlist;
							record['parentName']=data[0].data.unitname;
							addwindow.open();
							addwindow.getForm().setValues(record);
						}
					}
				}, '-', {
					text : '修改',
					iconCls : 'x-image-application_form_edit',
					handler : function() {
						var records =pagequeryObj.getSelectedRecords();
						if(records === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}else if(records.length !== 1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
							return;
						}else{
							editwindow.open();
							if (records[0].data.parentunitid==-1) {		//根节点，不能修改上级机构
								Ext.getCmp("bankLevelCom").disable();	
								Ext.getCmp("bankLevelCom").allowBlank=true;
								Ext.getCmp("parentid").disable();	
								Ext.getCmp("parentid").allowBlank=true;
							}else {
								Ext.getCmp("parentid").enable();
								Ext.getCmp("parentid").allowBlank=false;
								Ext.getCmp("parentid").blankText='请选择上级机构';
								Ext.getCmp("bankLevelCom").enable();	
								Ext.getCmp("bankLevelCom").allowBlank=false;
							}
							editwindow.updateFields(records[0]);
							Ext.getCmp("unitid_old").setValue(records[0].data['unitid']);
						}
					}
				}, '-', {
					text : '删除',
					iconCls : 'x-image-application_form_delete',
					handler : function() {
						var submitdata = pagequeryObj.getSelectedObjects(['unitid','parentunitid']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}
						if(submitdata.length !== 1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
							return;
						}
						for(var i=0;i<submitdata.length;i++){
							if (submitdata[i].parentunitid==-1) {
								return Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>总行信息不能删除！</s:param></s:text >');
							}			
							if (submitdata[i].parentunitid=='') {
								return Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>总行信息不能删除！</s:param></s:text >');
							}
						}
						//判断此机构是否有下级机构
						var delNode = systemtreepanel.getNodeById(submitdata[0].unitid);
						if(delNode==null){
							Ext.MessageBox.alert('系统提示','非总行及总行子机构请在左侧机构信息查找该机构进行删除！');
							return;
						}
						if(delNode.hasChildNodes()){
							Ext.MessageBox.confirm('<s:text name="common.info.title"/>','该操作将删除本机构及相关下级机构,请确认是否删除',
								function(id){
									if(id == 'yes'){
										requestAjax('<%=basePath%>admin/systemunit_delUnit', submitdata, function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(id){
												for(var i=0;i<submitdata.length;i++){
													if(systemtreepanel.getNodeById(submitdata[i].unitid)!=undefined){
														systemtreepanel.getNodeById(submitdata[i].unitid).remove();
													}
													systemUnits=sRet.field1;
													//重构修改窗口机构树
													treeGenerator1 = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);						
													unitTree1 = treeGenerator1.generate(false,false,false,false);
													unitTreePanl.setRootNode(unitTree1);
													var nodepath= systemtreepanel.getRootNode().getPath();
													systemtreepanel.expandPath(nodepath);
													systemtreepanel.selectPath(nodepath);
													curNodeId = '';
													queryUnit();
												}
											});
										});
									}
								}
							);
						}else{
							Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',
								function(id){
									if(id == 'yes'){
										requestAjax('<%=basePath%>admin/systemunit_delUnit', submitdata, function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(id){
												for(var i=0;i<submitdata.length;i++){
													if(systemtreepanel.getNodeById(submitdata[i].unitid)!=undefined){
														systemtreepanel.getNodeById(submitdata[i].unitid).remove();
													}
													systemUnits=sRet.field1;
													//重构修改窗口机构树
													treeGenerator1 = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);						
													unitTree1 = treeGenerator1.generate(false,false,false,false);
													unitTreePanl.setRootNode(unitTree1);
													var nodepath= systemtreepanel.getRootNode().getPath();
													systemtreepanel.expandPath(nodepath);
													systemtreepanel.selectPath(nodepath);
													curNodeId = '';
													queryUnit();
												}
											});
										});
									}
								}
							);
						}
					}
				}
				]
			});
			//查询函数
			function queryUnit(){
				var unitname=Ext.getCmp('unitname1').getValue();
				var query_obj= {"start":1,"limit":20,"unitname":unitname};
				if(curNodeId!=null&&curNodeId!=""){
					query_obj["unitid"]=""+curNodeId;
				}
				pagequeryObj.queryPage(query_obj);
			}
			//列表面板
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth-treeWidth,clientHeight,
				'<%=basePath%>/admin/systemunit_queryUnit',
				['unitid','unitname','unitlevel','parentunitid','checkcode','parentName','createuser','createdate','lastmoduser','lastmoddate','address','longitude','latitude','bank_level','near_station','bank_tel','manager_name','manager_phone','manager_tel','city','note','unitlist'],
				[
				new Ext.grid.RowNumberer(
				{header:'',width:30}),
				{header:'复选框'},
				{header:'机构号',dataIndex:'unitid',width:100},
				{header:'机构名称',dataIndex:'unitname',width:150,renderer:function(value){
					if(""==value){
						return "无";
					}else{
						return value.replace('珠海华润银行','').replace('股份有限公司','');//翻译字段方法
					}
	    		}},
				{header:'机构类型',		dataIndex:'bank_level',width:70,	renderer:function(value){
			   		return getDictValue(value,branchTypeStore,'dictValue','dictValueDesc');//翻译字段方法
	    		}},
				//{header:'交易机构号',dataIndex:'brno',width:150},
				//{header:'管理机构号',dataIndex:'mngbrno',width:150},
				{header:'上级机构',dataIndex:'parentName',width:150,sortable:false,renderer:function(value){
					if(""==value){
						return "无";
					}else{
						return value.replace('珠海华润银行','').replace('股份有限公司','');//翻译字段方法
					}
       			 }},
				{header:'上级机构号',dataIndex:'parentunitid',width:80},
				{header:'机构专属校验码',dataIndex:'checkcode',width:100},
				//{header:'排序号',dataIndex:'unitorder',width:50},
				{header:'GPS经度',	dataIndex:'longitude',		width:100},
				{header:'GPS纬度',	dataIndex:'latitude',		width:100},
				{header:'机构地址',dataIndex:'address',	width:180},
				{header:'附近车站',dataIndex:'near_station',	width:100},
				{header:'所在城市',dataIndex:'city',	width:100},
				{header:'负责人',dataIndex:'manager_name',	width:100},
				{header:'负责人电话',dataIndex:'manager_tel',	width:100},
				{header:'负责人手机',dataIndex:'manager_phone',	width:100},
				{header:'网点电话',dataIndex:'bank_tel',	width:100},
				{header:'备注',dataIndex:'note',	width:150}
				],
				'<s:text name="common.pagequery.pagingtool"/>',
				columnSortFunc,
				tbar
			);
			//定义页面布局
			var viewport = new Ext.Viewport({
				    layout : "border",
				    items : [ 
				    	{	title : '<span style="font-weight:normal">机构树</span>',
							iconCls : 'x-image-expand-all',
							collapsible : true,
							width : treeWidth,
							minSize : 160,
							maxSize : treeWidth,
							split : true,
							region : 'west',
							autoScroll : false,
							items : [ systemtreepanel ]
						}, 
						{	region : 'center',
							layout : 'fit',
							items : [ pagequeryObj.pagingGrid ]
						}
					]	
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
