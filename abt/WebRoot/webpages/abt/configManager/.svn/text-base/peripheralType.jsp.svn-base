<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'wgqJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	<script type="text/javascript" src=extjs/adapter/ext/ext-base.js></script>
	<script type="text/javascript" src=extjs/ext-all.js></script>	
	<script type="text/javascript" src=extjs/ext-lang-zh_CN.js></script>	
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
    <script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
    <script type="text/javascript">

        var pagereturn=${actionresult};
		var conditionPanel = null;
		
		var devptStore = new Ext.data.JsonStore({
		fields : ['dictValue','dictValueDesc'],
		url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
		root   : 'field1', autoLoad:true
		});
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示客户类型界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '设备外设类型配置', 120, 0,
				[
				],
				[
				{iconCls: "x-image-query", 			        id:'01',    text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/confManager/peripheralType_queryPeripheralType',
				['p_type_key','p_type_value','p_type_devicetype'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'外设类型ID',dataIndex:'p_type_key',width:80},
				{header:'外设类型名',dataIndex:'p_type_value',width:160},
				{header:'外设所属设备类型', 		dataIndex:'p_type_devicetype',		width:150, renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,devptStore,'dictValue','dictValueDesc');//翻译字段方法
       			 }}
				//{header:'外设所属设备类型',dataIndex:'p_type_devicetype',width:150}
				//{header:'预留1',dataIndex:'exp1',width:50}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口
			var addwindow = new SelfFormWindow('addWindow', '外设类型', 600, 300, 110, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'p_type_key', 	fieldLabel:'外设类型ID', 	allowBlank : false, blankText:'请输入外设类型ID',maxLength : 30,	maxLengthText : '长度不能大于30位'	}},
		        {colIndex:0, field:{xtype : 'textfield',     name:'p_type_value',   fieldLabel:'外设类型名',	allowBlank : false, hiddenName:'p_type_value',blankText : '请输入外设类型名',maxLength : 30,	maxLengthText : '长度不能大于30位'}},
		        //{colIndex:1, hiddenLabel:true,field:{width:240,html:'<br><br><font color="blue">设备类型列表:</font><br><font color="black">&nbsp;&nbsp;03&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;排队机</font><br><font color="black">&nbsp;&nbsp;04&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手持</font><br><font color="black">&nbsp;&nbsp;05&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自助</font><br><font color="black">&nbsp;&nbsp;09&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填单机</font>'}},
				{colIndex:0, field:{xtype : 'combo',     name:'p_type_devicetype', hiddenName:'p_type_devicetype' , fieldLabel:'外设所属设备类型',editable:false,store:devptStore, allowBlank : false,displayField:'dictValueDesc', valueField:'dictValue'  }}
				
				//{colIndex:0, field:{xtype : 'textfield', name:'exp1', 	fieldLabel:'预留1', 	allowBlank : true, blankText:'预留1',maxLength : 30,	maxLengthText : '长度不能大于30位'}},
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',150
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑外设类型', 600, 300, 110, 2,
			   [
				{colIndex:0, field:{xtype : 'textfield', name:'p_type_key', 	fieldLabel:'外设类型ID', 	readOnly:true}},
				//{colIndex:1, hiddenLabel:true,field:{width:240,html:'<br><br><font color="blue">设备类型列表:</font><br><font color="black">&nbsp;&nbsp;03&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;排队机</font><br><font color="black">&nbsp;&nbsp;04&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手持</font><br><font color="black">&nbsp;&nbsp;05&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自助</font><br><font color="black">&nbsp;&nbsp;09&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填单机</font>'}},
				{colIndex:0, field:{xtype : 'textfield', name:'p_type_value', fieldLabel:'外设类型名',blankText : '请输入外设类型名',maxLength : 30,	maxLengthText : '长度不能大于30位'  }},
				{colIndex:0, field:{xtype : 'combo',     name:'p_type_devicetype',   fieldLabel:'外设所属设备类型', hiddenName:'p_type_devicetype',readOnly:false,store:devptStore, allowBlank : false,displayField:'dictValueDesc', valueField:'dictValue'}}
				//{colIndex:0, field:{xtype : 'textfield', name:'exp1', 	fieldLabel:'预留1', 	allowBlank : true, blankText:'预留1',maxLength : 30,	maxLengthText : '长度不能大于30位'}}				
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',150
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
						editwindow.updateFields(records[0]);
						//Ext.getCmp('p_type_value_old').setValue(records[0].data['p_type_value']);
						//Ext.getCmp('p_type_devicetype_old').setValue(records[0].data['p_type_devicetype']);
					
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['p_type_key','p_type_value']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/peripheralType_delPeripheralType',submitdata,function(sRet){
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
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				if(submitData['p_type_key'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入外设类型ID');
					return;
				}
				if(submitData['p_type_value'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入外设类型名');
					return;
				}
		
		
				//pagequeryObj pagingGrid
				requestAjax('<%=basePath%>/confManager/peripheralType_addPeripheralType', submitData,
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
				if(submitData['p_type_value'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入外设类型');
					return;
				}
				var p_type_devicetype = submitData['p_type_devicetype'];
				requestAjax('<%=basePath%>/confManager/peripheralType_editPeripheralType',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
						
					});
					editwindow.close();
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
