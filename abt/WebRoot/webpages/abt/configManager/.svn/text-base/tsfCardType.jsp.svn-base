<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute("logonuser");
String template_id = (String)request.getSession().getAttribute("template");
String branch;
if(template_id!=null){
	branch = template_id;
}else{
	branch = user.getUnitid();
}

%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>发卡机参数配置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="extjs/UploadDialog/css/Ext.ux.UploadDialog.css" />
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.gzjs"></script>
	<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	
	<script type="text/javascript" charset="utf-8" src="extjs/UploadDialog/Ext.ux.UploadDialog.packed.js"></script>
	<script type="text/javascript" charset="utf-8" src="extjs/UploadDialog/locale/ru.utf-8_zh.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		
		//收费标准
		var messageOutlayStore = new Ext.data.SimpleStore({
			data:[
			      ["免费","0"],
			      ["包月","1"],
			      ["包年","2"],
			      ["用户可选","1,2"]
			      ],
			fields : ["moKey" , "moValue"]
		});
		
		var cardProduct = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/cardType_queryCardProduct',
			autoLoad:true,
			root   : 'field1',
			fields:['pdpc','rm4']
		});
		
		//授权等级
		var accreditrankStore = new Ext.data.SimpleStore({
			data:[
			      ["1级以上","1"],
			      ["2级以上","2"],
			      ["5级以上","5"]
			      ],
			fields : ['acKey' , 'acValue']
		});
		
		
		/***************************************************/
		var systemUnits = pagereturn.field1;
		var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'',nodeLevel:'unitlevel',nodeType:'unitlevel'};
		//构建机构树a
		var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree_a = treeGenerator_a.generate(false,false,false,false);
		var treepanel_a = new Ext.tree.TreePanel({
			rootVisible : true, 
			height:220,
			root:tree_a,
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
		    }),{xtype:'button',text:''}]
		});
		/***************************************************/
		
		var systemParamStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/systemParameter_querySystemParameter',
			root   : 'field1',
			fields:['parameter_id','parameter_name','branch','default_flag','parameter_flag']
		});
		
/**
 * @Title: loadPage 
 * @Description: 显示发卡机参数界面
 */		
		function loadPage(){
	 		var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '发卡机参数配置', 120, 1,
				[
					{rowIndex:0, field:{xtype:'combotree', 	name:'branch',	emptyText:'----<s:text name="admin.user.userunit"/>----',fieldLabel:'<s:text name="admin.user.userunit"/>',		passName: 'branchParam', tree:treepanel_a,value:<%="'" + user.getUnit().getUnitname()+ "'"%>, width:200,id:'branch'}} //机构
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
				'<%=basePath%>/confManager/cardType_queryCardTypePage',
				[
				 'cardTypeId',//卡种id
				 'cardTypeName',//卡种名称
				 'branch',//机构号
				 'producecode',//产品代码
				 'vouchertype',//凭证类型
				 'cardTypeDesc',//卡种描述
				 'accreditrank',//授权等级
				 'cardtypeimgface',//卡种正面图片
				 'cardtypeimgback',//卡种背面图片
				 'messageOutlay',//收费标准
				 'status',
				 ],
				[
				{header:'全选'},
				{header:'复选框'},
				//{header:'卡种id',		dataIndex:'cardTypeId',   width:150},
				{header:'卡种名称', 	dataIndex:'cardTypeName', width:120},
				{header:'卡种正面图片',	dataIndex:'cardtypeimgface', width:125,renderer:function(value,metadata){
					value = value.toString();
					metadata.attr = 'ext:qtitle="" ext:qtip="<img width=\'512\' height=\'384\' src=\''+ value +'\'/>"';
					value = "<img width='64' height='48' src=\'" + value + "\'/>";
          		 	return value;
       			 }},
				{header:'卡种背面图片',	dataIndex:'cardtypeimgback', width:125,renderer:function(value,metadata){
					value = value.toString();
					metadata.attr = 'ext:qtitle="" ext:qtip="<img width=\'512\' height=\'384\' src=\''+ value +'\'/>"';
					value = "<img width='64' height='48' src=\'" + value + "\'/>";
          		 	return value;
       			 }},
       			{header:'卡种介绍',  	dataIndex:'cardTypeDesc', width:150},
				{header:'机构号',  	dataIndex:'branch',		  width:120},
				{header:'授权等级',  	dataIndex:'accreditrank', width:120,renderer:function(value){
          		 	return getDictValue(value,accreditrankStore,'acValue','acKey');
       			}},
				{header:'产品代码',  	dataIndex:'producecode',	width:120,renderer:function(value){
          		 	return getDictValue(value,cardProduct,'pdpc','rm4');//翻译字段方法
       			}},
				{header:'凭证类型',  	dataIndex:'vouchertype',	width:120},
       			{header:'状态',  dataIndex:'status',width:125,renderer:function(value){
       				return value=='1'?'可用':'不可用'
       						
       			}},
				{header:'收费标准',	dataIndex:'messageOutlay',width:132,renderer:function(value){
					if(value=='0'){
						return '免费';
					}
					if(value=='1'){
						return '包月';
					}
					return value=='2' ? '包年':'用户可选';
				}}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			
			//当选择"添加"时,弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加发卡机参数', 580, 370, 200, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'cardTypeName', 	allowBlank : false,fieldLabel:'卡种名称',blankText:'请输入卡种名称',maxLength :40,	maxLengthText : '长度不能大于40位'}},
				//{colIndex:0, field:{xtype : 'textfield', name:'cardTypeFace', 	id:'cardTypeFace_add',hidden:true,hideLabel:true}},
				//{colIndex:0, field:{xtype : 'textfield', name:'cardTypeBack', 	id:'cardTypeBack_add',hidden:true,hideLabel:true}},
		        {colIndex:0, field:{xtype : 'combo', 	 name:'messageOutlay',  allowBlank : false,fieldLabel:'短信通知费用',editable:false, 	 store:messageOutlayStore, 	displayField:"moKey", valueField:"moValue"}},
		        {colIndex:0, field:{xtype : 'textarea',  name:'cardTypeDesc', 	allowBlank : false,fieldLabel:'卡种介绍',blankText:'请输入卡种介绍',height:140, maxLength :500,maxLengthText : '长度不能大于500位'}},
		        {colIndex:1, field:{xtype : 'textfield', fieldLabel:'卡种正面图', name:'upload_img_face',id:'upload_img_face_add', blankText:'请上传图片', readOnly:true}},
		        {colIndex:1, field:{xtype : 'textfield', fieldLabel:'卡种背面图', name:'upload_img_back',id:'upload_img_back_add', blankText:'请上传图片', readOnly:true}},
		        {colIndex:1, field:{xtype : 'combo',     name:'producecode',  hiddenName:'producecode', fieldLabel:'产品代码',     id:'producecode_add',      editable:false, 	allowBlank : false,store:cardProduct, displayField:'rm4', valueField:'pdpc'}},
		        {colIndex:1, field:{xtype : 'textfield', name:'vouchertype', 	allowBlank : false,fieldLabel:'凭证类型',blankText:'请输入凭证类型',maxLength :20,	maxLengthText : '长度不能大于20位'}},
		        {colIndex:1, field:{xtype : 'combo', name:'accreditrank', id:'accreditrank_add',fieldLabel:'授权等级',editable:false, 	allowBlank : false, store:accreditrankStore, displayField:'acKey', valueField:'acValue', value:'2'}},
		        {colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'status', 	boxLabel:'启用',checked : true}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close()}},
					{text:'上传卡种正面图', formBind:true,handler: function(){
						var dialogFace = new Ext.ux.UploadDialog.Dialog({
							 autoCreate: true,    
				             closable: true,    
				             collapsible: false,    
				             draggable: false,    
				             minWidth: 400,          
				             minHeight: 200,    
				             width: 400,    
				             height: 350,    
				             permitted_extensions:['JPG','jpg','PNG','png','jpeg','JPEG','GIF','gif'],    
				             proxyDrag: false,    
				             resizable: false,    
				             constraintoviewport: true,    
				             title: '文件上传',    
				             url: '<%=basePath%>/confManager/cardType_uploadImgFace',
				             post_var_name:'uploadFiles' ,
				             reset_on_hide: false,    
				             allow_close_on_upload: true
						});	
						
						var files = []; //本地文件名称数组
						 var savefiles=[];//上传重命名文件名称数组
						 var uploadflag = false;
						 dialogFace.on('filetest', function(dialogFace,filename){
						 	if(uploadflag){
						 		dialogFace.resetQueue();
								dialogFace.fireResetQueueEvent();
								uploadflag = false;
								files = [];
								savefiles=[];
						 	}
						 	
						 });
						 dialogFace.on('resetqueue', function(dialogFace,filename){
						 	files = [];
						 	savefiles=[];
						 });
						//上传图片事件 
						dialogFace.on('beforefileuploadstart', function(dialogFace,filename){
							if(files.length > 1){
						 		Ext.Msg.alert('溫馨提示','只能上传一张图片');  
						 		uploadflag = true;
							    return false;  
						 	}
						 });
						//添加图片事件
						dialogFace.on('fileadd', function(){
							this.grid_panel.getBottomToolbar();  
					        if(this.grid_panel.getStore().getCount() > 1){ 
					        	Ext.Msg.alert('溫馨提示','只能上传一张图片');
					            this.grid_panel.getSelectionModel().selectLastRow();  
					            var selections = this.grid_panel.getSelectionModel().getSelections();  
					            this.fsa.postEvent('remove-files', selections);           
					        }  
						 });
						//去除路径,得到文件名称.并添加到files数组中.
						 dialogFace.on('fileadd', function(dialogFace,filename){
							filename = filename.substring(filename.lastIndexOf("\\")+1);
						 	files.push(filename);
						 	var param = dialogFace.getBaseParams();
						 	param['upload_fileNames'] = files;
						 	dialogFace.setBaseParams(param);
						 });
						 //删除文件
						 dialogFace.on('fileremove', function(dialogFace,filename){
						 	filename = filename.substring(filename.lastIndexOf("\\")+1);
						 	var index = -1;
						 	for (var i = 0; i < files.length; i++) {  
						        if (files[i] == filename){
						        	 index=i;
						        	 break;
						        }
						    }  
						 	index != -1 && files.splice(index, 1);
						 	var param = dialogFace.getBaseParams();
						 	param['upload_fileNames'] = files;
						 	dialogFace.setBaseParams(param);
						 }); 
						 
						  dialogFace.on('uploadsuccess', function(dialogFace,filename,data,record){
							 uploadflag = true;
							 var savefilesName = [];//存放原文件名称的
						 	 for (var i = 0; i < files.length; i++) { 
						 		var s = files[i].substring(files[i].lastIndexOf("\\")+1)+'\n';
						 		savefilesName.push(s);
						 		/* var sn = files[i]+'\n';
						 		var vn = filename+'\n';
						 		savefilesName.push(sn);
						 		savefilesName.push(vn); */
						 	}
						 	savefiles.push(data.filename);
						 	//addwindow.getForm().findField('DETAILSPIC1').setValue(savefilesName.toString().replace(/,/g,''));
						 	addwindow.getForm().findField('upload_img_face').setValue(savefilesName.toString().replace(/,/g,''));
						 }); 
						dialogFace.show(); 
					}},
					{text:'上传卡种背面图', formBind:true,handler: function(){
						var dialogBack = new Ext.ux.UploadDialog.Dialog({
							 autoCreate: true,    
				             closable: true,    
				             collapsible: false,    
				             draggable: false,    
				             minWidth: 400,          
				             minHeight: 200,    
				             width: 400,    
				             height: 350,    
				             permitted_extensions:['JPG','jpg','PNG','png','jpeg','JPEG','GIF','gif'],    
				             proxyDrag: false,    
				             resizable: false,    
				             constraintoviewport: true,    
				             title: '文件上传',    
				             url: '<%=basePath%>/confManager/cardType_uploadImgBack',
				             post_var_name:'uploadFiles' ,
				             reset_on_hide: false,    
				             allow_close_on_upload: true
						});	
						
						var files = []; //本地文件名称数组
						 var savefiles=[];//上传重命名文件名称数组
						 var uploadflag = false;
						 dialogBack.on('filetest', function(dialogBack,filename){
						 	if(uploadflag){
						 		dialogBack.resetQueue();
								dialogBack.fireResetQueueEvent();
								uploadflag = false;
								files = [];
								savefiles=[];
						 	}
						 	
						 });
						 dialogBack.on('resetqueue', function(dialogBack,filename){
						 	files = [];
						 	savefiles=[];
						 });
						dialogBack.on('beforefileuploadstart', function(dialogBack,filename){
							if(files.length > 1){
						 		Ext.Msg.alert('溫馨提示','只能上传一张图片');  
						 		uploadflag = true;
							    return false;  
						 	}
						 });
						//添加图片事件
						dialogBack.on('fileadd', function(){
							this.grid_panel.getBottomToolbar();  
					        if(this.grid_panel.getStore().getCount() > 1){ 
					        	Ext.Msg.alert('溫馨提示','只能上传一张图片');
					            this.grid_panel.getSelectionModel().selectLastRow();  
					            var selections = this.grid_panel.getSelectionModel().getSelections();  
					            this.fsa.postEvent('remove-files', selections);           
					        }  
						 });
						//去除路径,得到文件名称.并添加到files数组中.
						 dialogBack.on('fileadd', function(dialogBack,filename){
							filename = filename.substring(filename.lastIndexOf("\\")+1);
						 	files.push(filename);
						 	var param = dialogBack.getBaseParams();
						 	param['upload_fileNames'] = files;
						 	dialogBack.setBaseParams(param);
						 });
						 //删除文件
						 dialogBack.on('fileremove', function(dialogBack,filename){
						 	filename = filename.substring(filename.lastIndexOf("\\")+1);
						 	var index = -1;
						 	for (var i = 0; i < files.length; i++) {  
						        if (files[i] == filename){
						        	 index=i;
						        	 break;
						        }
						    }  
						 	index != -1 && files.splice(index, 1);
						 	var param = dialogBack.getBaseParams();
						 	param['upload_fileNames'] = files;
						 	dialogBack.setBaseParams(param);
						 }); 
						 
						  dialogBack.on('uploadsuccess', function(dialogBack,filename,data,record){
							 uploadflag = true;
							 var savefilesName = [];//存放原文件名称的
						 	 for (var i = 0; i < files.length; i++) { 
						 		var s = files[i].substring(files[i].lastIndexOf("\\")+1)+'\n';
						 		savefilesName.push(s);
						 		/* var sn = files[i]+'\n';
						 		var vn = filename+'\n';
						 		savefilesName.push(sn);
						 		savefilesName.push(vn); */
						 	}
						 	savefiles.push(data.filename);
						 	//addwindow.getForm().findField('DETAILSPIC1').setValue(savefilesName.toString().replace(/,/g,''));
						 	addwindow.getForm().findField('upload_img_back').setValue(savefilesName.toString().replace(/,/g,''));
						 }); 
						dialogBack.show(); 
					}}
				]
			);
			
			
			//选择"修改"时,弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '添加发卡机参数', 580, 370, 200, 2,
					[
						{colIndex:0, field:{xtype : 'textfield', name:'cardTypeName', 	allowBlank : false,fieldLabel:'卡种名称',blankText:'请输入卡种名称',maxLength :40,	maxLengthText : '长度不能大于40位'}},
						//{colIndex:0, field:{xtype : 'textfield', name:'cardTypeFace', 	id:'cardTypeFace_add',hidden:true,hideLabel:true}},
						//{colIndex:0, field:{xtype : 'textfield', name:'cardTypeBack', 	id:'cardTypeBack_add',hidden:true,hideLabel:true}},
				        {colIndex:0, field:{xtype : 'combo', 	 name:'messageOutlay',  allowBlank : false,fieldLabel:'短信通知费用',editable:false, 	 store:messageOutlayStore, 	displayField:"moKey", valueField:"moValue"}},
				        {colIndex:0, field:{xtype : 'textarea',  name:'cardTypeDesc', 	allowBlank : false,fieldLabel:'卡种介绍',blankText:'请输入卡种介绍',height:140, maxLength :500,maxLengthText : '长度不能大于500位'}},
				        {colIndex:1, field:{xtype : 'textfield', fieldLabel:'卡种正面图', name:'upload_img_face', blankText:'请上传图片', readOnly:true}},
				        {colIndex:1, field:{xtype : 'textfield', fieldLabel:'卡种背面图', name:'upload_img_back', blankText:'请上传图片', readOnly:true}},
				        {colIndex:1, field:{xtype : 'combo',     name:'producecode',  hiddenName:'producecode', fieldLabel:'产品代码',     id:'producecode_edit',      editable:false, 	allowBlank : false,store:cardProduct, displayField:'rm4', valueField:'pdpc'}},
				        {colIndex:1, field:{xtype : 'textfield', name:'vouchertype', 	allowBlank : false,fieldLabel:'凭证类型',blankText:'请输入凭证类型',maxLength :20,	maxLengthText : '长度不能大于20位'}},
				        {colIndex:1, field:{xtype : 'combo', name:'accreditrank', id:'accreditrank_edit',fieldLabel:'授权等级',editable:false, 	allowBlank : false, store:accreditrankStore, displayField:'acKey', valueField:'acValue'}},
				        {colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'status', 	boxLabel:'启用',checked : true}}
						],
						[
							{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked, 	formBind:true},
							{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close()}},
							{text:'上传卡种正面图', formBind:true,handler: function(){
								var dialogFace = new Ext.ux.UploadDialog.Dialog({
									 autoCreate: true,    
						             closable: true,    
						             collapsible: false,    
						             draggable: false,    
						             minWidth: 400,          
						             minHeight: 200,    
						             width: 400,    
						             height: 350,    
						             permitted_extensions:['JPG','jpg','PNG','png','jpeg','JPEG','GIF','gif'],    
						             proxyDrag: false,    
						             resizable: false,    
						             constraintoviewport: true,    
						             title: '文件上传',    
						             url: '<%=basePath%>/confManager/cardType_uploadImgFace',
						             post_var_name:'uploadFiles' ,
						             reset_on_hide: false,    
						             allow_close_on_upload: true
								});	
								
								var files = []; //本地文件名称数组
								 var savefiles=[];//上传重命名文件名称数组
								 var uploadflag = false;
								 dialogFace.on('filetest', function(dialogFace,filename){
								 	if(uploadflag){
								 		dialogFace.resetQueue();
										dialogFace.fireResetQueueEvent();
										uploadflag = false;
										files = [];
										savefiles=[];
								 	}
								 	
								 });
								 dialogFace.on('resetqueue', function(dialogFace,filename){
								 	files = [];
								 	savefiles=[];
								 });
								//上传图片事件 
								dialogFace.on('beforefileuploadstart', function(dialogFace,filename){
									if(files.length > 1){
								 		Ext.Msg.alert('溫馨提示','只能上传一张图片');  
								 		uploadflag = true;
									    return false;  
								 	}
								 });
								//添加图片事件
								dialogFace.on('fileadd', function(){
									this.grid_panel.getBottomToolbar();  
							        if(this.grid_panel.getStore().getCount() > 1){ 
							        	Ext.Msg.alert('溫馨提示','只能上传一张图片');
							            this.grid_panel.getSelectionModel().selectLastRow();  
							            var selections = this.grid_panel.getSelectionModel().getSelections();  
							            this.fsa.postEvent('remove-files', selections);           
							        }  
								 });
								//去除路径,得到文件名称.并添加到files数组中.
								 dialogFace.on('fileadd', function(dialogFace,filename){
									filename = filename.substring(filename.lastIndexOf("\\")+1);
								 	files.push(filename);
								 	var param = dialogFace.getBaseParams();
								 	param['upload_fileNames'] = files;
								 	dialogFace.setBaseParams(param);
								 });
								 //删除文件
								 dialogFace.on('fileremove', function(dialogFace,filename){
								 	filename = filename.substring(filename.lastIndexOf("\\")+1);
								 	var index = -1;
								 	for (var i = 0; i < files.length; i++) {  
								        if (files[i] == filename){
								        	 index=i;
								        	 break;
								        }
								    }  
								 	index != -1 && files.splice(index, 1);
								 	var param = dialogFace.getBaseParams();
								 	param['upload_fileNames'] = files;
								 	dialogFace.setBaseParams(param);
								 }); 
								 
								  dialogFace.on('uploadsuccess', function(dialogFace,filename,data,record){
									 uploadflag = true;
									 var savefilesName = [];//存放原文件名称的
								 	 for (var i = 0; i < files.length; i++) { 
								 		var s = files[i].substring(files[i].lastIndexOf("\\")+1)+'\n';
								 		savefilesName.push(s);
								 		/* var sn = files[i]+'\n';
								 		var vn = filename+'\n';
								 		savefilesName.push(sn);
								 		savefilesName.push(vn); */
								 	}
								 	savefiles.push(data.filename);
								 	//addwindow.getForm().findField('DETAILSPIC1').setValue(savefilesName.toString().replace(/,/g,''));
								 	editwindow.getForm().findField('upload_img_face').setValue(savefilesName.toString().replace(/,/g,''));
								 }); 
								dialogFace.show(); 
							}},
							{text:'上传卡种背面图', formBind:true,handler: function(){
								var dialogBack = new Ext.ux.UploadDialog.Dialog({
									 autoCreate: true,    
						             closable: true,    
						             collapsible: false,    
						             draggable: false,    
						             minWidth: 400,          
						             minHeight: 200,    
						             width: 400,    
						             height: 350,    
						             permitted_extensions:['JPG','jpg','PNG','png','jpeg','JPEG','GIF','gif'],    
						             proxyDrag: false,    
						             resizable: false,    
						             constraintoviewport: true,    
						             title: '文件上传',    
						             url: '<%=basePath%>/confManager/cardType_uploadImgBack',
						             post_var_name:'uploadFiles' ,
						             reset_on_hide: false,    
						             allow_close_on_upload: true
								});	
								
								var files = []; //本地文件名称数组
								 var savefiles=[];//上传重命名文件名称数组
								 var uploadflag = false;
								 dialogBack.on('filetest', function(dialogBack,filename){
								 	if(uploadflag){
								 		dialogBack.resetQueue();
										dialogBack.fireResetQueueEvent();
										uploadflag = false;
										files = [];
										savefiles=[];
								 	}
								 	
								 });
								 dialogBack.on('resetqueue', function(dialogBack,filename){
								 	files = [];
								 	savefiles=[];
								 });
								dialogBack.on('beforefileuploadstart', function(dialogBack,filename){
									if(files.length > 1){
								 		Ext.Msg.alert('溫馨提示','只能上传一张图片');  
								 		uploadflag = true;
									    return false;  
								 	}
								 });
								//添加图片事件
								dialogBack.on('fileadd', function(){
									this.grid_panel.getBottomToolbar();  
							        if(this.grid_panel.getStore().getCount() > 1){ 
							        	Ext.Msg.alert('溫馨提示','只能上传一张图片');
							            this.grid_panel.getSelectionModel().selectLastRow();  
							            var selections = this.grid_panel.getSelectionModel().getSelections();  
							            this.fsa.postEvent('remove-files', selections);           
							        }  
								 });
								//去除路径,得到文件名称.并添加到files数组中.
								 dialogBack.on('fileadd', function(dialogBack,filename){
									filename = filename.substring(filename.lastIndexOf("\\")+1);
								 	files.push(filename);
								 	var param = dialogBack.getBaseParams();
								 	param['upload_fileNames'] = files;
								 	dialogBack.setBaseParams(param);
								 });
								 //删除文件
								 dialogBack.on('fileremove', function(dialogBack,filename){
								 	filename = filename.substring(filename.lastIndexOf("\\")+1);
								 	var index = -1;
								 	for (var i = 0; i < files.length; i++) {  
								        if (files[i] == filename){
								        	 index=i;
								        	 break;
								        }
								    }  
								 	index != -1 && files.splice(index, 1);
								 	var param = dialogBack.getBaseParams();
								 	param['upload_fileNames'] = files;
								 	dialogBack.setBaseParams(param);
								 }); 
								 
								  dialogBack.on('uploadsuccess', function(dialogBack,filename,data,record){
									 uploadflag = true;
									 var savefilesName = [];//存放原文件名称的
								 	 for (var i = 0; i < files.length; i++) { 
								 		var s = files[i].substring(files[i].lastIndexOf("\\")+1)+'\n';
								 		savefilesName.push(s);
								 		/* var sn = files[i]+'\n';
								 		var vn = filename+'\n';
								 		savefilesName.push(sn);
								 		savefilesName.push(vn); */
								 	}
								 	savefiles.push(data.filename);
								 	editwindow.getForm().findField('upload_img_back').setValue(savefilesName.toString().replace(/,/g,''));
								 }); 
								dialogBack.show(); 
							}}
						]
			);
			
		    
			/**
			* @Title:onButtonClicked
			* @Description:触发"查询","重置","添加","修改","删除"的选择语句
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
						//无法修改非本行机构
						var branch = '<%=branch%>';
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=branch){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
					    		return;
					    	}
					    }
						if(!isEdit){
							break;
						}
						editwindow.open();
						editwindow.updateFields(records[0]);
						break;
					case 3:
						var records = pagequeryObj.getSelectedRecords();
						/*得到用户选择的数据条数并验证
						if(records === undefined || records.length !== 1){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
						*/
						var branch = '<%=branch%>';
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=branch){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
					    		return;
					    	}
					    }
					    var submitdata = pagequeryObj.getSelectedObjects(['cardTypeId','branch','cardtypeimgface','cardtypeimgback']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/confManager/cardType_delCardType',submitdata,function(sRet){
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
				submitData.cardTypeId = pagequeryObj.getSelectedObjects(['cardTypeId'])
				createCheckBoxValue('status',submitData);
				requestAjax('<%=basePath%>/confManager/cardType_editCardType',submitData,function(sRet){
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
				createCheckBoxValue('status',submitData);
				requestAjax('<%=basePath%>/confManager/cardType_addCardParam', submitData,
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
      		var branch = '<%=branch%>';
      		systemParamStore.load({params:{'branch':branch,'parameter_flag':'01'}});
      		
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="paramDetailsWindow"></div>
	<div id="systemParamWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>