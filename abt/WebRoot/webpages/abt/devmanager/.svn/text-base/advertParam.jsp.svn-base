<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@page
	import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	User user = (User) request.getSession().getAttribute(
			ApplicationConstants.LOGONUSER);
	String branch = user.getUnitid();
	
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>广告参数管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="selfcss/common.css" />
		<link rel="stylesheet" type="text/css" href="extjs/UploadDialog/css/Ext.ux.UploadDialog.css" />
		
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.js"></script>
		<script type="text/javascript"  src="extjs/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
		<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
		
		<script type="text/javascript" charset="utf-8" src="extjs/UploadDialog/Ext.ux.UploadDialog.packed.js"></script>
		<script type="text/javascript" charset="utf-8" src="extjs/UploadDialog/locale/ru.utf-8_zh.js"></script>
		<script type="text/javascript">
		var addwindow;
		var editwindow;
		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		var deviceTypeStore = new Ext.data.SimpleStore({
			data:[['04','手持设备广告'],['10','填单机广告'],['11','发卡机广告']],
			fields:['divice_type','divice_type_desc']
		})
		
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '自助填单机参数设置', 120, 0,
				[],
				[
				{iconCls: "x-image-query", 	 id:"01",	text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-user_add",  id:"02",		text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-user_edit", 	 id:"03",	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-user_delete",  id:"04",	text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked
			);
			conditionPanel.open();
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/confManager/adParam_queryParam',
				['branch','device_type','device_id','param_id','param_name','param_key','param_value','create_date','note'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',	dataIndex:'branch',width:100},
				{header:'设备类型',dataIndex:'device_type',	width:100,renderer:function(value){
          		 	return getDictValue(value,deviceTypeStore,'divice_type','divice_type_desc')
          		 	}
				},
				{header:'参数ID',			dataIndex:'param_id',	width:50 ,hidden:true},
				{header:'广告轮播时间（秒）',			dataIndex:'param_value',	width:150},
				{header:'创建日期',			dataIndex:'create_date',	width:100},
				{header:'备注',			dataIndex:'note',	width:200}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			/*添加系统用户*/
			addwindow = new SelfFormWindow('recordAddWindow', '添加参数', 300, 300, 260, 1,
				[
				{colIndex:0,field: {xtype:'combo',name:'device_type',fieldLabel:'设备类型',editable:false,forceSelection:true,allowBlank:false,hiddenName:'device_type',store:deviceTypeStore,displayField:'divice_type_desc',valueField:'divice_type'}},
				{colIndex:0, field:{xtype : 'textfield', 	name:'create_date', id:'create_date_add', fieldLabel:'创建日期',hidden:true}},
				{colIndex:0, field:{xtype : 'textfield', 	name:'param_id', id:'param_id_add', fieldLabel:'参数ID',hidden:true	}},
				{colIndex:0, field:{xtype : 'textfield', 	name:'param_name', id:'param_name_add',	allowBlank : true, fieldLabel:'参数名称',	blankText : '请输入参数名称',		maxLength : 50,maxLengthText : '长度不能大于50位',hidden:true}},
				{colIndex:0, field:{xtype : 'numberfield', 	name:'param_value', id:'param_value_add',fieldLabel:'广告轮播时间（秒）',allowBlank:false,maxLength : 3,maxLengthText : '长度不能大于3位'}},
				{colIndex:0, field:{xtype : 'textarea', 	name:'note', id:'note_add', fieldLabel:'备注',maxLength : 256,maxLengthText : '长度不能大于256位'}}
				],
				[
					
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
 			/*主窗口按钮设置*/
			function onButtonClicked(btn_index){
				switch(btn_index){
				case 0://查询
					var query_obj = conditionPanel.getFields();
					pagequeryObj.queryPage(query_obj);
				break;
				case 1://添加
					addwindow.open();
					Ext.getCmp('create_date_add').setValue(getDate_id());
				break;
				case 2://修改
					editwindow = new SelfFormWindow('recordEditWindow', '修改参数', 300, 300, 260, 1,
						[	
						 	{colIndex:0,field: {xtype: 'textfield', name:'branch',fieldLabel:'机构号',hidden:true}},
							{colIndex:0,field: {xtype:'combo',name:'device_type',fieldLabel:'设备类型',readOnly:true,editable:false,forceSelection:true,allowBlank:false,hiddenName:'device_type',store:deviceTypeStore,displayField:'divice_type_desc',valueField:'divice_type'}},
							{colIndex:0, field:{xtype : 'textfield',name:'param_id', fieldLabel:'参数ID',hidden:true	}},
							//{colIndex:0, field:{xtype : 'textfield',name:'param_name',	allowBlank : false, fieldLabel:'参数名称',	blankText : '请输入参数名称',		maxLength : 50,maxLengthText : '长度不能大于50位',hidden:true}},
							//{colIndex:0, field:{xtype : 'textfield',name:'param_key',allowBlank : false, fieldLabel:'KEY',	blankText : '请输入参数KEY',		maxLength : 20,maxLengthText : '长度不能大于20位'}},
							{colIndex:0, field:{xtype : 'numberfield',name:'param_value',fieldLabel:'广告轮播时间（秒）',allowBlank:false,maxLength : 3,maxLengthText : '长度不能大于3位'}},
							{colIndex:0, field:{xtype : 'textarea',name:'note',fieldLabel:'备注',maxLength : 256,maxLengthText : '长度不能大于256位'}}
						],
						[
							{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
							{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close();}}
						]
					);
					var records = pagequeryObj.getSelectedRecords();
					
					if(records === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
						//无法修改非本行机构
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=<%="'"+branch+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
					    		return;
					    	}
					    }
						editwindow.open();
						editwindow.updateFields(records[0]);
					}
				break;
				case 3://删除
					var submitdata = pagequeryObj.getSelectedObjects(['param_id']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						//无法删除非本行机构
					  <%--  for(var i=0;i<submitdata.length;i++){
					    	if(submitdata[i].branch!=<%="'"+branch+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
					    		return;
					    	}
					    } --%>
						
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/confManager/adParam_deleteAdvertParam',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
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
			function onaddclicked(){
				var submitData = addwindow.getFields();
					requestAjax('<%=basePath%>/confManager/adParam_addAdvertParam', submitData,
					function(sRet){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
							var query_obj = conditionPanel.getFields();
							pagequeryObj.queryPage(query_obj);
						});
						addwindow.close();
					});
			}
			function oneditclicked(){
				var submitData = editwindow.getFields();
				requestAjax('<%=basePath%>/confManager/adParam_editAdvertParam', submitData,
					function(sRet) {
						Ext.MessageBox.alert(
								'<s:text name="common.info.title"/>',
								'<s:text name="common.info.editsuccess"/>',
								function(id) {
									var query_obj = conditionPanel.getFields();
									pagequeryObj.queryPage(query_obj);
								});
						editwindow.close();
					});
			}
			
			
			var date = new Date();
			
			//设置创建日期
			function getDate_id(){
				var month = date.getMonth()+1;
				var strDate = date.getDate();
				if(month <=9){
					month = "0" + month;
				}
				if(strDate <= 9){
					strDate = "0"+strDate;
				}
				return date.getFullYear()+month+strDate;
			}
			
			
			function buildLayout() {
				var viewport = new Ext.Viewport(
					{
						layout : "border",
						items : [ conditionPanel.toolbarwindow,pagequeryObj.pagingGrid ]
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
		<div id="hello"></div>
	</body>
</html>
