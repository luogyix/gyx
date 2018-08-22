<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String branch = user.getUnitid();
String hostIp = user.getHostip();
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>设备对应外设配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagetoafaquery.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var	systemUnits=pagereturn.field1;
		var conditionPanel = null;	
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		/*设备类型*/
		var deviceTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
			root   : 'field1',
			fields : ['dictValue','dictValueDesc'],
			autoLoad:true
		});
		/*设备编号*/
		var deviceNumStore = new Ext.data.JsonStore({
			url : '<%=basePath%>configManager/selfDevice_queryDeviceInfo',
			root:'field1',
			fields:['device_num']
		});
		/*外设类型*/
		var peripheralTypeStore=new Ext.data.JsonStore({
			url:'<%=basePath%>configManager/devicePeripheral_queryPeripheralType',
			root:'field1',
			fields:['p_type_key','p_type_value']
		});
		/*外设类型*/
		var typeStore =new Ext.data.JsonStore({
			url:'<%=basePath%>configManager/peripheralType_queryPeripheralTypeSmall',
			root:'field1',
			fields:['p_type_key','p_type_value'],
		    autoLoad:true
		});
		/*外设状态*/
		var peripheralStatusStore =new Ext.data.JsonStore({
			url:'<%=basePath%>configManager/devicePeripheral_queryPeripheralState',
			root:'field2',
			fields:['p_status_key','p_status_value']
		});
		var peripheralMoniterStore=new Ext.data.SimpleStore({ 
			data:[["1","1-启用"],["0","0-不启用"]],
			fields : ['key','value']
		});
        function loadPage(){
        	var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};
			var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
			var tree_a = treeGenerator_a.generate(false,false,false,false);
			var treepanel_a = new Ext.tree.TreePanel({
				height:220,
				width:200,
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
			    }),{xtype:'button',text:''}],
				rootVisible : true, 
				root:tree_a
			});
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '设备对应外设状态配置', 120, 0,
				[
				],
				[
				{iconCls: "x-image-query", 			        id:'01',    text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'} //删除
				],
				onButtonClicked
			);
			conditionPanel.open();  
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>confManager/devicePeripheral_queryDevicePeripheralStatePage',
				['device_num','device_name','branch','devicetype','peripheral_type','peripheral_monitor','peripheral_state','peripheral_num','peripheral_name'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'设备类型',dataIndex:'devicetype',width:100,renderer:function(value){
          		 	return getDictValue(value,deviceTypeStore,'dictValue','dictValueDesc');
       			 }},
       			{header:'设备编号',dataIndex:'device_num',width:100},
				{header:'外设编号',dataIndex:'peripheral_num',width:100},
				{header:'外设自定义名称',dataIndex:'peripheral_name',width:120},
				{header:'外设类型',dataIndex:'peripheral_type',width:100,renderer:function(value){
          		 	return getDictValue(value,typeStore,'p_type_key','p_type_value');
      			 }},
				{header:'外设监控开关',dataIndex:'peripheral_monitor',width:180,
					renderer:function(value){
	          		 	return getDictValue(value,peripheralMoniterStore,'key','value');	
					}
				},
				{header:'外设状态',dataIndex:'peripheral_state',width:180}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加设备外设状态', 420, 460, 170, 1,
				[
                {colIndex:0, field:{xtype : 'combotree', name:'branch',allowBlank : false,fieldLabel:'机构号',	 passName: 'branch', tree:treepanel_a}},
				{colIndex:0, field:{xtype : 'combo', name:'devicetype', hiddenName:'devicetype',	fieldLabel:'设备类型',id:'device_type_add', editable:false, 	allowBlank : false, store:deviceTypeStore, 	displayField:'dictValueDesc', valueField:'dictValue',
					listeners:{
					'select': function(){
						var combo = Ext.getCmp('device_type_add');
						var combo1 = addwindow.getFields();
						Ext.getCmp('device_num_add').setValue("");
						Ext.getCmp('peripheral_type_add').setValue("");
						peripheralTypeStore.removeAll();
						deviceNumStore.removeAll();
						var submit = {};
	                	submit['devicetype'] = combo.getValue();
	                	submit['branch']=combo1['branch'];
	                	deviceNumStore.load({params:submit});
	                	peripheralTypeStore.load({params:submit});
					}
					}	
                }},
				{colIndex:0, field:{xtype : 'combo', name:'device_num',id:'device_num_add',fieldLabel:'设备编号',editable:false, 	allowBlank : false, store:deviceNumStore, 	displayField:'device_num', valueField:'device_num'
		        }},
				{colIndex:0, field:{xtype : 'textfield', name:'peripheral_num', 	fieldLabel:'外设编号',id:'peripheral_num_add', 	allowBlank : false, blankText:'请输入外设编号',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'peripheral_name', 	fieldLabel:'外设自定义名称',id:'peripheral_name_add', 	allowBlank : true, blankText:'请输入外设自定义名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'peripheral_monitor', 	boxLabel:'启用',checked : false}},
				{colIndex:0, field:{xtype : 'combo', name:'peripheral_type',id:'peripheral_type_add', hiddenName:'peripheral_type',	fieldLabel:'外设类型',editable:false, 	allowBlank : false, store:peripheralTypeStore, 	displayField:'p_type_value', valueField:'p_type_key',
					listeners:{
						'select': function(){
							Ext.getCmp('peripheral_state_add').setValue("");
							peripheralStatusStore.removeAll();
							var submit = {};
		                	submit['p_type_key'] = Ext.getCmp('peripheral_type_add').getValue();
		                	peripheralStatusStore.load({params:submit});
						}
					}	
				}},
				{colIndex:0, field:{xtype : 'combo', name:'peripheral_state', hiddenName:'peripheral_state',id:'peripheral_state_add',fieldLabel:'外设状态',editable:false, 	allowBlank : false, store:peripheralStatusStore, 	displayField:'p_status_value', valueField:'p_status_key'}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',80
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑设备外设状态', 420, 460, 160, 1,
			   [
                {colIndex:0, field:{xtype : 'combotree', name:'branch',allowBlank : false,	fieldLabel:'机构号',	 passName: 'branch', tree:treepanel_a,readOnly:true}}, 
				{colIndex:0, field:{xtype : 'combo', name:'devicetype', hiddenName:'devicetype',	fieldLabel:'设备类型',id:'devicetype_edit', editable:false, 	allowBlank : false, store:deviceTypeStore, 	displayField:'dictValueDesc', valueField:'dictValue',readOnly:true}},
				{colIndex:0, field:{xtype : 'combo', name:'device_num', 	fieldLabel:'设备编号',editable:false, 	allowBlank : false, store:deviceNumStore, 	displayField:'device_num',readOnly:true, valueField:'device_num'
		        }},
				{colIndex:0, field:{xtype : 'textfield', name:'peripheral_num', 	fieldLabel:'外设编号',id:'peripheral_num_edit', 	allowBlank : false, blankText:'请输入外设编号',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'peripheral_name', 	fieldLabel:'外设自定义名称',id:'peripheral_name_edit', 	allowBlank : true, blankText:'请输入外设自定义名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'peripheral_monitor', 	boxLabel:'启用',checked : true}},
				{colIndex:0, field:{xtype : 'combo', name:'peripheral_type', hiddenName:'peripheral_type',	fieldLabel:'外设类型',editable:false, 	allowBlank : false, store:peripheralTypeStore,readOnly:true, 	displayField:'p_type_value', valueField:'p_type_key'}},
				{colIndex:0, field:{xtype : 'combo', name:'peripheral_state', hiddenName:'peripheral_state',id:'peripheral_state_edit',fieldLabel:'外设状态',editable:true,readOnly:true, allowBlank : false, store:peripheralStatusStore, 	displayField:'p_status_value', valueField:'p_status_key'}}
            	],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',80
			);
			/**
			* @Title:onButtonClicked
			* @Description:触发"查询"，"重置"，"添加"，"修改"，"删除"的选择语句
			*/
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
						break;
					case 1:
						addwindow.open();
						break;
					case 2:
					    var isEdit = true;
					    var records = pagequeryObj.getSelectedRecords();
						if(records === undefined || records.length !== 1){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
						var editable = pagequeryObj.getSelectedRecords();
						for(var i=0;i<editable.length;i++){
							if(records[i].get('editable') == '1'){
								Ext.MessageBox.alert('系统提示','该记录不能修改');
								isEdit = false;
							}
						}
						if(!isEdit){
							break;
						}
						editwindow.open();
						editwindow.updateFields(records[0]);//回显用的
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['device_num','branch','devicetype','peripheral_type']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/devicePeripheral_delDevicePeripheralState',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					    break;
				}
			}
			
			/**
			 * @createCheckBoxValue
			 * @Description:判断checkbox数据
			 */
            function createCheckBoxValue(check,data){
            	if(data[check]==undefined){
            		data[check] = 'off';
				}
            }
			
			/**
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				createCheckBoxValue('peripheral_monitor',submitData);
				requestAjax('<%=basePath%>confManager/devicePeripheral_editDevicePeripheralState',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
			}
            /**
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				createCheckBoxValue('peripheral_monitor',submitData);
				requestAjax('<%=basePath%>confManager/devicePeripheral_addDevicePeripheralState', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
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

