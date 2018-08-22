<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String brno = user.getUnitid();
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>自助设备参数配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		var deviceTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
			root   : 'field1',
			fields : ['dictValue','dictValueDesc'],
			autoLoad:true
		});
		var parameterIdStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>confManager/selfMenu_queryMenuParameterId',
			root   : 'field1',
			fields : ['parameter_id'],
			autoLoad:true
		});
		parameterIdStore.fields;
/**
 * @Title: loadPage 
 * @Description: 显示排队机参数维护界面
 */		
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '自助设备参数配置', 120, 0,
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
				'<%=basePath%>confManager/devParam_queryDevParamPage',
				['dev_param_id','dev_param_name','parameter_id','param_type'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'设备通用参数ID',dataIndex:'dev_param_id',width:100},
				{header:'设备通用参数名称',dataIndex:'dev_param_name',width:100},
				{header:'菜单编号',dataIndex:'parameter_id',width:100},
				{header:'设备通用参数类型',dataIndex:'param_type',width:100,renderer:function(value){
          		 	return getDictValue(value,deviceTypeStore,'dictValue','dictValueDesc');
      			 }}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加自助设备参数', 610, 420, 140, 2,
				[
					{colIndex:0, field:{xtype : 'textfield', name:'dev_param_name', id:'dev_param_name_add', 	fieldLabel:'设备通用参数名称',	allowBlank : false,blankText:'请输入设备通用参数名称',maxLength : 60,maxLengthText : '长度不能大于60位'}},
					{colIndex:0, field:{xtype : 'combo', name:'parameter_id', id:'parameter_id_add', hiddenName:'parameter_id',fieldLabel:'菜单编号',editable:false, 	allowBlank : false, store:parameterIdStore, displayField:'parameter_id', valueField:'parameter_id'}},
					{colIndex:0, field:{xtype : 'combo', name:'param_type', id:'param_type_add', hiddenName:'param_type',	fieldLabel:'设备通用参数类型',editable:false, 	allowBlank : false, store:deviceTypeStore, 	displayField:'dictValueDesc', valueField:'dictValue'}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',110
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑自助设备参数', 610, 420, 140, 2,
			   [	
			    	{colIndex:0,field:{xtype:'textfield',name:'dev_param_id',readOnly:true,fieldLabel:'设备通用参数ID'}},
					{colIndex:0, field:{xtype : 'textfield', name:'dev_param_name', id:'dev_param_name_edit', 	fieldLabel:'设备通用参数名称',	allowBlank : false,blankText:'请输入设备通用参数名称',maxLength : 60,maxLengthText : '长度不能大于60位'}},
					{colIndex:0, field:{xtype : 'combo', name:'parameter_id', id:'parameter_id_edit', 	fieldLabel:'菜单编号',editable:false, 	allowBlank : false, store:deviceTypeStore, 	displayField:'parameter_id', valueField:'parameter_id'}},
					{colIndex:0, field:{xtype : 'combo', name:'param_type', id:'param_type_edit',hiddenName:'param_type', fieldLabel:'设备通用参数类型',editable:false, 	allowBlank : false, store:deviceTypeStore, 	displayField:'dictValueDesc', valueField:'dictValue'}}			],
				
				
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();colorWin.hide();}}
				],'left',110
			);
			
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
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['dev_param_id']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/devParam_delDevParam',submitdata,function(sRet){
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
				submitData['branch'] = <%=user.getUnitid()%>;
				requestAjax('<%=basePath%>confManager/devParam_addDevParam', submitData,
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
				requestAjax('<%=basePath%>confManager/devParam_editDevParam',submitData,function(sRet){
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
  
  <body>
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>
