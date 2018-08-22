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
    
    <title>设备类型配置</title>
    
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
		var deviceTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
			root   : 'field1',
			fields : ['dictValue','dictValueDesc'],
			autoLoad:true
		});
		var connectTypeStore = new Ext.data.SimpleStore({ 
			data:[['0','0-间联'],['1','1-直连']],
			fields : ['dictValue','dictValueDesc']
		});
		var msgModeStore = new Ext.data.SimpleStore({ 
			data:[['0','0-SOCKET'],['1','1-MQ']],
			fields : ['dictValue','dictValueDesc']
		});
		var deviceClassStore = new Ext.data.SimpleStore({ 
			data:[['0','0-系统内设备'],['1','1-系统外设备']],
			fields : ['dictValue','dictValueDesc']
		});
		var deviceStatusStore = new Ext.data.SimpleStore({ 
			data:[['0','0-初始'],['1','1-生效'],['2','2-故障'],['3','3-废弃']],
			fields : ['dictValue','dictValueDesc']
		});
		var msgRecvTypeStore = new Ext.data.SimpleStore({ 
			data:[['1','1-按设备ID逐笔接收'],['2','2-按设备类型整合接收']],
			fields : ['dictValue','dictValueDesc']
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '设备类型配置', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combo',name:'devicetype',fieldLabel:'设备类型', forceSelection : true, hiddenName:'devicetype', store:deviceTypeStore, 	displayField:'dictValueDesc',valueField:'dictValue'}}
				],
				[
				{iconCls: "x-image-query",text:'<s:text name="common.button.query"/>'},//查询
				{iconCls: "x-image-reset",text:'<s:text name="common.button.reset"/>'},//重置
				{iconCls: "x-image-application_form_add",  id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked
			);
			
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/confManager/deviceManager_queryDeviceManager',
				[
					'devicetype','connecttype','relaydeviceip','relaydeviceport','msgprotocol','msgmode','deviceclass','serviceno',
					'servicecode','devicestatus','msgrecvtype'
				],[
					{header:'全选'},
					{header:'复选框'},
					{header:'设备类型',dataIndex:'devicetype',width:100, renderer:function(value){
					      return getDictValue(value.toString(),deviceTypeStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},
					{header:'联接类型',dataIndex:'connecttype',width:100, renderer:function(value){
					      return getDictValue(value.toString(),connectTypeStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},
					{header:'中转设备IP',dataIndex:'relaydeviceip',width:100},
					{header:'中转设备PORT',dataIndex:'relaydeviceport',width:100},
					{header:'报文协议',dataIndex:'msgprotocol',width:100},
					{header:'通讯方式',dataIndex:'msgmode',width:100, renderer:function(value){
					      return getDictValue(value.toString(),msgModeStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},
					{header:'设备类型',dataIndex:'deviceclass',width:100, renderer:function(value){
					      return getDictValue(value.toString(),deviceClassStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},
					{header:'服务方系统编',dataIndex:'serviceno',width:100},
					{header:'服务代码',dataIndex:'servicecode',width:100},
					{header:'设备状态',dataIndex:'devicestatus',width:100, renderer:function(value){
					      return getDictValue(value.toString(),deviceStatusStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},
					{header:'消息接收类型',dataIndex:'msgrecvtype',width:200, renderer:function(value){
					      return getDictValue(value.toString(),msgRecvTypeStore,'dictValue','dictValueDesc');//翻译字段方法
				    }}
					//{header:'设备在线标识',dataIndex:'deviceonline',width:100, renderer:function(value){
					//      return getDictValue(value.toString(),deviceonlineStore,'dictValue','dictValueDesc');//翻译字段方法
				    //}},
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
					case 2:
						addwindow.open();
						break;
					case 3:
					    var isEdit = true;
					    var records = pagequeryObj.getSelectedRecords();
						if(records === undefined || records.length !== 1){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							isEdit = false;
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
						editwindow.updateFields(records[0]);
						Ext.getCmp('devicetype_old').setValue(records[0].data['devicetype']);
						break;
					case 4:
					    var submitdata = pagequeryObj.getSelectedObjects(['devicetype']);
					    if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/deviceManager_delDeviceManager',submitdata,function(sRet){
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
			
			addwindow = new SelfFormWindow('addWindow', '添加设备', 500, 400, 160, 2,
				[
				{colIndex:0,field:{xtype:'combo',name:'devicetype',fieldLabel:'设备类型',forceSelection:true,allowBlank:false,hiddenName:'devicetype',store:deviceTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'connecttype',fieldLabel:'联接类型',forceSelection:true,allowBlank:false,hiddenName:'connecttype',store:connectTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'msgmode',fieldLabel:'通讯方式',forceSelection:true,allowBlank:false,hiddenName:'msgmode',store:msgModeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'deviceclass',fieldLabel:'设备类型',forceSelection:true,allowBlank:false,hiddenName:'deviceclass',store:deviceClassStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'devicestatus',fieldLabel:'设备状态',forceSelection:true,allowBlank:false,hiddenName:'devicestatus',store:deviceStatusStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'msgrecvtype',fieldLabel:'消息接收类型',forceSelection:true,allowBlank:false,hiddenName:'msgrecvtype',store:msgRecvTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:1,field:{xtype:'textfield',name:'relaydeviceip',fieldLabel:'中转设备IP',allowBlank:true,blankText:'请输入中转设备IP',maxLength:512,	maxLengthText:'长度不能大于512位'}},
				{colIndex:1,field:{xtype:'textfield',name:'relaydeviceport',fieldLabel:'中转设备PORT',allowBlank:true,blankText:'请输入中转设备PORT',maxLength:256,	maxLengthText:'长度不能大于256位'}},
				{colIndex:1,field:{xtype:'textfield',name:'msgprotocol',fieldLabel:'报文协议',allowBlank:false,blankText:'请输入报文协议',maxLength:128,	maxLengthText:'长度不能大于128位'}},
				{colIndex:1,field:{xtype:'textfield',name:'serviceno',fieldLabel:'服务方系统编号',allowBlank:false,blankText:'请输入服务方系统编号',maxLength:255,	maxLengthText:'长度不能大于255位'}},
				{colIndex:1,field:{xtype:'textfield',name:'servicecode',fieldLabel:'服务代码',allowBlank:false,blankText:'请输入服务代码',maxLength:100,	maxLengthText:'长度不能大于100位'}}
				],
				[
					{text:'<s:text name="common.button.add"/>',   formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
		
			editwindow = new SelfFormWindow('editWindow', '编辑设备', 500, 400, 160, 2,
				[
				{colIndex:0, field:{xtype :'textfield', id:'devicetype_old', name:'devicetype_old',hidden:true}},
				{colIndex:0,field:{xtype:'combo',name:'devicetype',fieldLabel:'设备类型',forceSelection:true,allowBlank:false,hiddenName:'devicetype',store:deviceTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'connecttype',fieldLabel:'联接类型',forceSelection:true,allowBlank:false,hiddenName:'connecttype',store:connectTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'msgmode',fieldLabel:'通讯方式',forceSelection:true,allowBlank:false,hiddenName:'msgmode',store:msgModeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'deviceclass',fieldLabel:'设备类型',forceSelection:true,allowBlank:false,hiddenName:'deviceclass',store:deviceClassStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'devicestatus',fieldLabel:'设备状态',forceSelection:true,allowBlank:false,hiddenName:'devicestatus',store:deviceStatusStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'msgrecvtype',fieldLabel:'消息接收类型',forceSelection:true,allowBlank:false,hiddenName:'msgrecvtype',store:msgRecvTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:1,field:{xtype:'textfield',name:'relaydeviceip',fieldLabel:'中转设备IP',allowBlank:true,blankText:'请输入中转设备IP',maxLength:512,	maxLengthText:'长度不能大于512位'}},
				{colIndex:1,field:{xtype:'textfield',name:'relaydeviceport',fieldLabel:'中转设备PORT',allowBlank:true,blankText:'请输入中转设备PORT',maxLength:256,	maxLengthText:'长度不能大于256位'}},
				{colIndex:1,field:{xtype:'textfield',name:'msgprotocol',fieldLabel:'报文协议',allowBlank:false,blankText:'请输入报文协议',maxLength:128,	maxLengthText:'长度不能大于128位'}},
				{colIndex:1,field:{xtype:'textfield',name:'serviceno',fieldLabel:'服务方系统编号',allowBlank:false,blankText:'请输入服务方系统编号',maxLength:255,	maxLengthText:'长度不能大于255位'}},
				{colIndex:1,field:{xtype:'textfield',name:'servicecode',fieldLabel:'服务代码',allowBlank:false,blankText:'请输入服务代码',maxLength:100,	maxLengthText:'长度不能大于100位'}}],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				]
			);
			
			/**
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				requestAjax('<%=basePath%>confManager/deviceManager_addDeviceManager', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
			}
			/**
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				
				requestAjax('<%=basePath%>confManager/deviceManager_editDeviceManager',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
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
	<div id="addWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>
