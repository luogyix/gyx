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
    
    <title>My JSP 'devInfoPeripheral.jsp' starting page</title>
    
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
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		//var devInfoConditionPanel = null;
		//var peripheralConditionPanel = null;
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		//设备类型
		var devicetypeStore = new Ext.data.JsonStore({
		fields : ['dictValue','dictValueDesc'],
		url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
		root   : 'field1', autoLoad:true
		});
		//设备校验类型
		var device_chk_typeStore=new Ext.data.SimpleStore({ 
			data:[["序列号校验","2"],["MAC校验","1"]],
			fields : ['key','value']
		});
		//关联排队机号
//		var qm_numStore = new Ext.data.JsonStore({
//			fields:['qm_num','qm_name'],
//			data:['','']
//		});
		//设备编号
		var deviceNumStore = new Ext.data.JsonStore({
			url : '<%=basePath%>configManager/selfDevice_queryDeviceInfo',
			root:'field1',
			fields:['device_num']
		});
		//外设类型
//		var peripheralTypeStore=new Ext.data.JsonStore({
//			url:'<%=basePath%>configManager/devicePeripheral_queryPeripheralType',
//			root:'field1',
//			fields:['p_type_key','p_type_value']
//		});
		//外设类型
		var typeStore =new Ext.data.JsonStore({
			url:'<%=basePath%>configManager/peripheralType_queryPeripheralTypeSmall',
			root:'field1',
			fields:['p_type_key','p_type_value'],
		    autoLoad:true
		});
		//外设状态
		var peripheralStatusStore =new Ext.data.JsonStore({
			url:'<%=basePath%>configManager/devicePeripheral_queryPeripheralState',
			root:'field2',
			fields:['p_state_key','p_state_value'],
			autoLoad:true
		});
//		var peripheralStateStore = new Ext.data.JsonStore({
//			url:'<%=basePath%>configManager/peripheralState_queryPeripheralState',
//			root:'field1',
//			fields:['p_state_key','p_state_value'],
//			autoLoad:true
//		});
		var peripheralMoniterStore=new Ext.data.SimpleStore({ 
			data:[["1","1-启用"],["0","0-不启用"]],
			fields : ['key','value']
		});
		
//		var peripheralStore = new Ext.data.JsonStore({
//			url:'<%=basePath%>configManager/devicePeripheral_queryDevicePeripheralStatePage',
//			root:'field1',
//			fields:['branch','devicetype','device_num','peripheral_num','peripheral_name','peripheral_type','peripheral_monitor','peripheral_state']
//		});

		
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var devInfoConditionPanel = new SelfToolBarPanel('queryConditionPanel', '设备信息配置', 120,1,
				[
				{rowIndex:0, field:{xtype:'combo', name:'devicetype',fieldLabel:'设备类型',editable:false,hiddenName:'devicetype',displayField:'dictValueDesc', valueField:'dictValue',store: devicetypeStore,emptyText:'请选择'}}
				],
				[
				{iconCls: "x-image-query", id:'01',  text:'<s:text name="common.button.query"/>'}		//查询
				],
				devInfoOnButtonClicked
			);
			devInfoConditionPanel.open(); 

			var devInfoPagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth/3*2,
				clientHeight - devInfoConditionPanel.getHeight(),
				'<%=basePath%>confManager/deviceMsg_queryDeviceInfoPage',
				['device_num','device_name','branch','devicetype','device_model','deviceadm_num','deviceadm_name','device_ip','device_oid','device_status','device_param_id',
				 'device_chk_type','device_chk_info','qm_num','qm_ip'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'设备编号',dataIndex:'device_num',width:100},
				{header:'设备名称',dataIndex:'device_name',width:100},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'设备类型',dataIndex:'devicetype',width:100,renderer:function(value){
          		 	return getDictValue(value,devicetypeStore,'dictValue','dictValueDesc');
       			 }},
				{header:'设备型号',dataIndex:'device_model',width:100},
				{header:'设备管理柜员号',dataIndex:'deviceadm_num',width:120},
				{header:'设备主管姓名',dataIndex:'deviceadm_name',width:100},
				{header:'设备IP',dataIndex:'device_ip',width:180},
				{header:'设备唯一标识',dataIndex:'device_oid',width:180},
				{header:'设备状态',dataIndex:'device_status',width:100,renderer:function(value){return value=='1'?'可用':'不可用'}},
				{header:'设备参数',dataIndex:'device_param_id',width:200},
				{header:'设备校验类型',dataIndex:'device_chk_type',width:200,renderer:function(value){
          		 	return getDictValue(value,device_chk_typeStore,'value','key');
       			 }},
       			{header:'设备校验信息',dataIndex:'device_chk_info',width:200},
				{header:'关联排队机号',dataIndex:'qm_num',width:200},
				{header:'关联排队机IP',dataIndex:'qm_ip',width:200}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			devInfoPagequeryObj.setEvent('rowclick',function(paginggrid,index,c,d){
			    //var submitdata = devInfoPagequeryObj.getSelectedObjects(['branch','devicetype','device_num']);
			    var record = paginggrid.getStore().getAt(index);
			    var bra = record.get('branch');
			    var devtype = record.get('devicetype');
			    var devnum = record.get('device_num');
			    var submitdata = {};
			    submitdata['branch'] = bra;
			    submitdata['devicetype'] = devtype;
			    submitdata['device_num'] = devnum;
				requestAjax('<%=basePath%>confManager/devInfoPeripheral_queryPeripheralInfo',submitdata,function(sRet){				
					var store = peripheralPagequeryObj.getStore();
					store.removeAll();
					store.loadData(sRet.field1);
				})
			});
			
			function queryPeripheralCon(){
				var records = devInfoPagequeryObj.getSelectedRecords();
				if(records === undefined || records.length !== 1){
						Ext.MessageBox.alert('系统提示','请选择一条记录');

				}else{
				var record  = records[0];
			    var bran = record.get('branch');
			    var devtype = record.get('devicetype');
			    var devnum = record.get('device_num');
			    var submitdata = {};
			    submitdata['branch'] = bran;
			    submitdata['devicetype'] = devtype;
			    submitdata['device_num'] = devnum;
			    requestAjax('<%=basePath%>confManager/devInfoPeripheral_queryPeripheralInfo',submitdata,function(sRet){				
					var store = peripheralPagequeryObj.getStore();
					store.removeAll();
					store.loadData(sRet.field1);
				})
			    }
			}
			
			
			var peripheralConditionPanel = new SelfToolBarPanel('ConditionPanel', '设备对应外设状态配置', 120, 0,
				[
				],
				[
				{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'} //删除
				],
				peripheralOnButtonClicked
			);
			peripheralConditionPanel.open();  
			
			
			var peripheralPagequeryObj = new PageQuery(
				true,'QueryTable',
				clientWidth/3,
				//clientHeight - peripheralConditionPanel.getHeight(),
				clientHeight - devInfoConditionPanel.getHeight(),
				null,
				['device_num','device_name','branch','devicetype','peripheral_type','peripheral_monitor','peripheral_state','peripheral_num','peripheral_name'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'设备类型',dataIndex:'devicetype',width:100,renderer:function(value){
          		 	return getDictValue(value,devicetypeStore,'dictValue','dictValueDesc');
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
                {colIndex:0, field:{xtype : 'textfield', name:'branch',id:'bra',allowBlank : false,fieldLabel:'机构号',readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'devicetype',id:'devt', hiddenName:'devicetype',	fieldLabel:'设备类型', editable:false, 	allowBlank : false,readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'device_num',id:'devn',fieldLabel:'设备编号',editable:false, 	allowBlank : false,readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'peripheral_num', 	fieldLabel:'外设编号',id:'peripheral_num_add', 	allowBlank : false, blankText:'请输入外设编号',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'peripheral_name', 	fieldLabel:'外设自定义名称',id:'peripheral_name_add', 	allowBlank : true, blankText:'请输入外设自定义名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'peripheral_monitor', 	boxLabel:'启用',checked : false}},
				{colIndex:0, field:{xtype : 'combo', name:'peripheral_type',id:'peripheral_type_add', hiddenName:'peripheral_type',	fieldLabel:'外设类型',editable:false, 	allowBlank : false, displayField:'p_type_value', valueField:'p_type_key',store: typeStore
					,listeners:{
						'select': function(){
							Ext.getCmp('peripheral_state_add').setValue("");
							peripheralStatusStore.removeAll();
							var submit = {};
		                	submit['p_type_key'] = Ext.getCmp('peripheral_type_add').getValue();
		                	peripheralStatusStore.load({params:submit});
						}
					}		
				}},
				{colIndex:0, field:{xtype : 'combo', name:'peripheral_state', hiddenName:'peripheral_state',id:'peripheral_state_add',fieldLabel:'外设状态',editable:false, 	allowBlank : false, store:peripheralStatusStore, 	displayField:'p_state_value', valueField:'p_state_key'}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',80
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑设备外设状态', 420, 460, 160, 1,
			   [
                {colIndex:0, field:{xtype : 'textfield', name:'branch',allowBlank : false,	fieldLabel:'机构号', readOnly:true}}, 
				{colIndex:0, field:{xtype : 'textfield', name:'devicetype', hiddenName:'devicetype',	fieldLabel:'设备类型',id:'devicetype_edit', editable:false, 	allowBlank : false,readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'device_num', 	fieldLabel:'设备编号',editable:false, 	allowBlank : false,readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'peripheral_num', 	fieldLabel:'外设编号',id:'peripheral_num_edit', 	allowBlank : false, blankText:'请输入外设编号',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'peripheral_name', 	fieldLabel:'外设自定义名称',id:'peripheral_name_edit', 	allowBlank : true, blankText:'请输入外设自定义名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'peripheral_monitor', 	boxLabel:'启用',checked : true}},
				{colIndex:0, field:{xtype : 'combo', name:'peripheral_type', hiddenName:'peripheral_type',	fieldLabel:'外设类型',editable:false, 	allowBlank : false, store:typeStore,readOnly:true, 	displayField:'p_type_value', valueField:'p_type_key'}},
				{colIndex:0, field:{xtype : 'combo', name:'peripheral_state', hiddenName:'peripheral_state',id:'peripheral_state_edit',fieldLabel:'外设状态',editable:true,readOnly:true, allowBlank : false, store:peripheralStatusStore, 	displayField:'p_state_value', valueField:'p_state_key'}}
            	],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',80
			);
			//触发“查询”按钮
			function devInfoOnButtonClicked(btn_index){
					var query_obj = devInfoConditionPanel.getFields();
					devInfoPagequeryObj.queryPage(query_obj);
					var sto = peripheralPagequeryObj.getStore();
					sto.removeAll();
			}
			/**
			* @Title:peripheralOnButtonClicked
			* @Description:触发"添加"，"修改"，"删除"的选择语句
			*/
			function peripheralOnButtonClicked(btn_index){
				switch(btn_index){
					//case 0:
					//	var query_obj = conditionPanel.getFields();
					//	pagequeryObj.queryPage(query_obj);
					//	break;
					case 0:
						//var deviceGrid = devInfoPagequeryObj.getGrid();
						var records = devInfoPagequeryObj.getSelectedRecords();
						if(records === undefined || records.length !== 1){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
						//var record = deviceGrid.sm.getSelections();
							var record  = records[0];
						    var bran = record.get('branch');
						    var devtype = record.get('devicetype');
						    var devnum = record.get('device_num');						    
							addwindow.open();
							Ext.getCmp('bra').setValue(bran);
						    Ext.getCmp('devt').setValue(devtype);
						    Ext.getCmp('devn').setValue(devnum);
							break;
						
					case 1:
					    var isEdit = true;
					    var records = peripheralPagequeryObj.getSelectedRecords();
						if(records === undefined || records.length !== 1){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
						var editable = peripheralPagequeryObj.getSelectedRecords();
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
					case 2:
					    var submitdata = peripheralPagequeryObj.getSelectedObjects(['device_num','branch','devicetype','peripheral_type']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/devicePeripheral_delDevicePeripheralState',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										//var query_obj = peripheralPagequeryObj.getFields();
										//peripheralPagequeryObj.queryPage(query_obj);
										//var records = devInfoPagequeryObj.getSelectedRecords();
										//var store = peripheralPagequeryObj.getStore();
										//store.removeAll();
										//store.loadData(sRet.field1);
										queryPeripheralCon();
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
						//var query_obj = peripheralConditionPanel.getFields();
						//peripheralPagequeryObj.queryPage(query_obj);
						queryPeripheralCon();
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
						//var query_obj = peripheralConditionPanel.getFields();
						//peripheralPagequeryObj.queryPage(query_obj);
						queryPeripheralCon();
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
				    layout : "column",
					items : [
						devInfoConditionPanel.toolbarwindow,
						devInfoPagequeryObj.pagingGrid,
						peripheralConditionPanel.toolbarwindow,
						peripheralPagequeryObj.pagingGrid]
				});
			}
			
      		buildLayout();
		}
	</script>
  </head>
  
  <body scroll="no">
  	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
  	<div id="ConditionPanel"></div>
	<div id="QueryTable"></div>
	<div id="addWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>
