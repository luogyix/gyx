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

		<title>自助填单机参数设置</title>

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
			data:[['02','填单机']],
			fields:['divice_type','divice_type_desc']
		})
		
		var queuetype_list = new Ext.data.SimpleStore({ 
			fields : ['qlKey','qlValue'],
			data:[
			      ["先填单","1"],
			      ["先取号","2"],
			      ["任意方式","0"]
			      ]
		});
		
		//收费标准
		var accreditrankStore = new Ext.data.SimpleStore({
			data:[["免费","0"],["包月","1"],["包年","2"],["用户可选","1,2"]],
			fields : ["acKey" , "acValue"]
		});
		
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
				'<%=basePath%>/dataAnalysis/formFill_queryParam?flag=0',
				[
				 'branch',
				 'param_id',
				 'messageoutlay',
				 'create_date',
				 'explain',
				 'queuetype',
				 'pro_save_hint',
				 'pro_drow_hint',
				 'trapro_drow_hint',
				 'pfs_password',
				 'shutdown_time'
				 ],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100 },
				{header:'排队方式',	dataIndex:'queuetype',width:100,renderer:function(value){
					if(value == "0"){
						return "任意方式";
					}else if(value != "0"){
						return value=='1'?'先填单':'先取号';
					}
					
				}},
				{header:'参数ID',dataIndex:'param_id',width:100 },
				{header:'填单机管理密码',dataIndex:'pfs_password',width:100},
				{header:'收费标准',	dataIndex:'messageOutlay',width:117,renderer:function(value){
					if(value == "0"){
						return "免费";
					}else if(value == "1"){
						return "包月";
					}
					return value == "2" ? "包年" : "用户可选";
				}},
				{header:'关机时间',dataIndex:'shutdown_time',width:80},
				{header:'创建日期',dataIndex:'create_date',width:150},
				{header:'备注',dataIndex:'explain',width:200},
				{header:'省内存款提示信息',dataIndex:'pro_save_hint',width:200},
				{header:'省内取款提示信息',dataIndex:'pro_drow_hint',width:200},
				{header:'农信银跨省取款提示信息',dataIndex:'trapro_drow_hint',width:200}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			/*添加参数*/
			addwindow = new SelfFormWindow('recordAddWindow', '添加参数', 530, 450, 200, 2,
				[
				{colIndex:0, field:{xtype : 'textarea', 	name:'explain', id:'explain_add', fieldLabel:'备注',height:140,maxLength : 256,maxLengthText : '长度不能大于256位'}},
				{colIndex:0, field:{xtype : 'numberfield', 	name:'pfs_password', id:'pfs_password_add',	allowBlank : false, fieldLabel:'填单机管理密码',	blankText : '请添加管理密码',	minLength:6,minLengthText:'请输入6位数字',	maxLength : 6,maxLengthText : '请输入6位数字'}},
				{colIndex:0, field:{xtype : 'combo',        name:'queuetype', 	id:'queuetype_add',allowBlank : false,fieldLabel:'排队方式' ,editable:false, store:queuetype_list, 	displayField:'qlKey', valueField:'qlValue'}},
				{colIndex:0, field:{xtype: 'timefield',fieldLabel:'关机时间',	format:'H:i:s',name:'shutdown_time',increment:30,editable:false,minValue: '06:00:00',maxValue: '22:00:00',allowBlank:false}},
				{colIndex:1, field:{xtype : 'combo', 	 name:'messageoutlay',  allowBlank : false,fieldLabel:'短信通知费用',editable:false, 	 store:accreditrankStore, 	displayField:'acKey', valueField:'acValue'}},
				{colIndex:1, field:{xtype : 'textarea', 	name:'pro_save_hint', height:70,	allowBlank : false, fieldLabel:'省内存款提示信息',	blankText : '请添加管理密码',	  maxLength : 200,maxLengthText : '最大长度为100位中文'}},
				{colIndex:1, field:{xtype : 'textarea', 	name:'pro_drow_hint', height:70,	allowBlank : false, fieldLabel:'省内取款提示信息',	blankText : '请添加管理密码',	  maxLength : 200,maxLengthText : '最大长度为100位中文'}},
				{colIndex:1, field:{xtype : 'textarea', 	name:'trapro_drow_hint',height:70, allowBlank : false, fieldLabel:'农信银跨省取款提示信息',	blankText : '请添加管理密码',maxLength : 200,maxLengthText : '最大长度为100位中文'}}
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
					//Ext.getCmp('create_date_add').setValue(getDate_id());
				break;
				case 2://修改
					editwindow = new SelfFormWindow('recordEditWindow', '修改参数',530, 450, 200, 2,
						[
							/* {colIndex:0, field:{xtype : 'textfield', 	name:'device_id', id:'device_id_add', fieldLabel:'设备ID',hidden:true}}, */
							//{colIndex:0, field:{xtype : 'textfield', 	name:'create_date', fieldLabel:'创建日期',hidden:true	}},
							{colIndex:0, field:{xtype : 'textfield', 	name:'param_id',  fieldLabel:'参数ID',hidden:true	}},
							{colIndex:0, field:{xtype : 'textarea', 	name:'explain',  fieldLabel:'备注',height:140,maxLength : 256,maxLengthText : '长度不能大于256位'}},
							{colIndex:0, field:{xtype : 'combo',        name:'queuetype', 	fieldLabel:'排队方式' ,editable:false, store:queuetype_list, 	displayField:'qlKey', valueField:'qlValue'}},
							{colIndex:0, field:{xtype : 'numberfield', 	name:'pfs_password', 	allowBlank : false, fieldLabel:'填单机管理密码',	blankText : '请添加管理密码',	minLength:6,minLengthText:'请输入6位数字',	maxLength : 6,maxLengthText : '请输入6位数字'}},
							{colIndex:0, field:{xtype: 'timefield',fieldLabel:'关机时间',	format:'H:i:s',name:'shutdown_time',increment:30,editable:false,minValue: '06:00:00',maxValue: '22:00:00',allowBlank:false}},
							{colIndex:1, field:{xtype : 'combo', 	 name:'messageoutlay',  allowBlank : false,fieldLabel:'短信通知费用',editable:false, 	 store:accreditrankStore, 	displayField:'acKey', valueField:'acValue'}},
							{colIndex:1, field:{xtype : 'textarea', 	name:'pro_save_hint', height:70,	allowBlank : false, fieldLabel:'省内存款提示信息',	blankText : '请添加管理密码',	  maxLength : 200,maxLengthText : '最大长度为100位中文'}},
							{colIndex:1, field:{xtype : 'textarea', 	name:'pro_drow_hint', height:70,	allowBlank : false, fieldLabel:'省内取款提示信息',	blankText : '请添加管理密码',	  maxLength : 200,maxLengthText : '最大长度为100位中文'}},
							{colIndex:1, field:{xtype : 'textarea', 	name:'trapro_drow_hint',height:70, allowBlank : false, fieldLabel:'农信银跨省取款提示信息',	blankText : '请添加管理密码',maxLength : 200,maxLengthText : '最大长度为100位中文'}}
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
								requestAjax('<%=basePath%>/dataAnalysis/formFill_deleteAdvertParam',submitdata,function(sRet){
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
					requestAjax('<%=basePath%>/dataAnalysis/formFill_addAdvertParam', submitData,
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
				requestAjax('<%=basePath%>/dataAnalysis/formFill_editAdvertParam', submitData,
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
			/* function getDate_id(){
				var month = date.getMonth()+1;
				var strDate = date.getDate();
				if(month <=9){
					month = "0" + month;
				}
				if(strDate <= 9){
					strDate = "0"+strDate;
				}
				return date.getFullYear()+month+strDate;
			} */
			
			
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
