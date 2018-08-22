<%@page import="com.agree.framework.web.form.administration.User" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page
	import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User) request.getSession().getAttribute(
		ApplicationConstants.LOGONUSER);
String branch = user.getUnitid();
String filename;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>广告管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	<link rel="stylesheet" type="text/css" href="extjs/UploadDialog/css/Ext.ux.UploadDialog.css" />
	
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
	var addwindow;
	var editwindow;
	var file_limit=pagereturn.file_limit.split(',');
	var sub_imagefile_num=pagereturn.sub_imagefile_num;
	var branch_imagefile_num=pagereturn.branch_imagefile_num;
	var conditionPanel = null;
	Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.onReady(loadPage);
	
	function loadPage(){
		var clientWidth = document.body.clientWidth;
		var clientHeight = document.body.clientHeight;
		var timeStore = new Ext.data.JsonStore({
			fields : ['begin_time','end_time']
		});
    	
    	var timeData = [
				new Ext.grid.RowNumberer({header:'序号',width:33}),
				{xtype: 'gridcolumn', header:'复选框', align: 'center',hidden:true},
				{ xtype: 'gridcolumn',dataIndex: 'begin_time',header: '播放时间(从)',sortable:false,editor: {xtype: 'timefield',format:'H:i:s',increment:30,minValue: '08:00:00',maxValue: '18:00:00'}},
	      		{ xtype: 'gridcolumn',dataIndex: 'end_time',header: '播放时间(到)',sortable:false,editor: {xtype: 'timefield',format:'H:i:s',increment:30,minValue: '08:00:00',maxValue: '18:00:00'}}
			];
    	var timeSM = new Ext.grid.CheckboxSelectionModel({ singleSelect: false });
    	var timeCM = new Ext.grid.ColumnModel(timeData);
    	
		var timeGrid = new Ext.grid.EditorGridPanel({
			id: 'timeGrid',
		    store: timeStore,
		    colModel: timeCM,
		    sm:timeSM,
		    stripeRows: true,
		    height:240,
		    frame:true,
		    viewConfig: {forceFit: true},
		    clicksToEdit:1,
		    tbar: [
		    	{id:'addBtn', text: '添加', disabled: false, iconCls: "x-image-add", 
		    		handler: function(){
						var length= timeStore.getCount();
							var recordType = timeStore.recordType;
							var num = timeStore.getCount();
							var record = new recordType({
								begin_time:'',end_time:''
				});
							timeGrid.stopEditing();
							timeStore.insert(length, record);
							timeGrid.getView().refresh();
							timeSM.selectRow(length);
							timeGrid.startEditing(timeStore.getCount()-1, 3);
		    		} 
		    	},'-',
		    	{id:'delBtn', text: '删除', disabled: false, iconCls: "x-image-delete", 
		    		handler: function(){
		    			if(!timeSM.hasSelection()){
							Ext.Msg.alert('<s:text name="common.info.title"/>' , '<s:text name="common.info.mustselectrecord"/>');
							return;
						}
				 		Ext.MessageBox.confirm('<s:text name="common.info.title"/>' , '<s:text name="common.info.suredelete"/>' , function(id){
							if(id=='yes'){
								var records = timeSM.getSelections();
								for(var i=0; i<records.length; i++){
									timeStore.remove(records[i]);
								}
							}
						});
		    		}
		    	}
		    ]
		});
		
		timeGrid.on('afteredit',function(e,a,b,c){
			var record = e.record;
			//e.originalValue;
			if(record.get('end_time')!=''&&record.get('begin_time')!=''){//判断都非空
				if (record.get('begin_time') >= record.get('end_time')) {
					Ext.MessageBox.alert('提示','起始时间必须小于结束时间',function(id){
						record.set(e.field,e.originalValue);
						return;
					});
				}
				//本条内条件皆通过,与已有数据进行比对
				for ( var i = 0; i < timeStore.getCount(); i++) {
					//e.row 所在行 一会循环要排除 e.column所在列 
					if(i==e.row){
						continue;
					}
					var r = timeStore.getAt(i);
					//r.get('begin_time') r.get('end_time') a b
					//record.get('begin_time')  record.get('end_time') s e
					if(record.get('begin_time') < r.get('begin_time') && record.get('end_time') > r.get('begin_time')){
						Ext.MessageBox.alert('提示','['+record.get('begin_time')+'-'+record.get('end_time')+']与['+r.get('begin_time')+'-'+r.get('end_time')+']的时间段冲突,请重新修改',function(id){
							record.set(e.field,e.originalValue);
							return;
						});
					}else if(record.get('begin_time') >= r.get('begin_time') && record.get('begin_time')<r.get('end_time')){
						Ext.MessageBox.alert('提示','['+record.get('begin_time')+'-'+record.get('end_time')+']与['+r.get('begin_time')+'-'+r.get('end_time')+']的时间段冲突,请重新修改',function(id){
							record.set(e.field,e.originalValue);
							return;
						});
					}
				}
			}
		});
		
		var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '广告管理', 120, 0,
			[
			],
			[
			{iconCls: "x-image-query", 	 id:"01",		text:'<s:text name="common.button.query"/>'},		//查询
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
			'<%=basePath%>/confManager/advertInfoAction_queryAdvertParam',
			['advert_id','branch','play_time','play_interval','photos_path','video_interval','photos_md5'],
			[
			{header:'全选'},
			{header:'复选框'},
			{header:'广告编号',		dataIndex:'advert_id',		width:125},
			{header:'机构号',			dataIndex:'branch',			width:125},
			{header:'播放时间',		dataIndex:'play_time',		width:200,hidden:true,renderer:function(value){
				var values=value.toString().split(';');
				var time='';
				for(var i=0;i<values.length;i++){
					time+=";"+values[i].substr(0,2)+":"+values[i].substr(2,2)+":"+values[i].substr(4,5)+":"+values[i].substr(9,2)+":"+values[i].substr(11,2);
				}
				return time.substr(1);
			}},
			{header:'图片播放间隔',	dataIndex:'play_interval',	width:125},
			{header:'视频播放间隔',	dataIndex:'video_interval',hidden:true,	width:125},
			{header:'图片的MD5值',	dataIndex:'photos_md5',	width:210, hidden:true},
			{header:'播放内容 ',		dataIndex:'photos_path',	width:500,renderer:function(value,metadata,record,index,colIndex,store){
				var values = value.toString().split(';');
				var v = '';
				for(var i=0;i<values.length;i++){
					if(values[i].indexOf("jpg")!=-1||values[i].indexOf("JPG")!=-1||values[i].indexOf("png")!=-1||values[i].indexOf("PNG")!=-1||values[i].indexOf("jpeg")!=-1||values[i].indexOf("JPEG")!=-1||values[i].indexOf("GIF")!=-1||values[i].indexOf("gif")!=-1||values[i].indexOf("bmp")!=-1||values[i].indexOf("BMP")!=-1){
					 	v = v + "&nbsp;&nbsp;&nbsp;<img height='200' width='200' src=\"confManager/advertInfoAction_getCustImg.action?filename="+values[i]+"&&branch="+record.data['branch']+"\"/>";							
					}else if(value[i]!=""){
						v=v+values[i]+" ";
					}
				}
      		 	return v;
      		 }}
			],
			'<s:text name="common.pagequery.pagingtool"/>'
		);
		/*添加新广告信息*/
		addwindow = new SelfFormWindow('recordAddWindow', '添加新广告',350, 380, 200, 1,
			[
			{colIndex:0, field:{xtype : 'textfield', 	name:'branch', id:'branch_add',	allowBlank : false, fieldLabel:'机构号',	blankText : '请输入机构号',		maxLength : 12,maxLengthText : '长度不能大于20位',readOnly:true	}},
			{colIndex:0, field:{xtype : 'textfield', 	name:'play_interval', id:'play_interval_add',	allowBlank : false, fieldLabel:'图片播放间隔(秒)',	blankText : '请输入图片播放间隔',	regex:/^[0-9]*$/	,regexText:'请输入20位以内整数',maxLength : 20,maxLengthText : '长度不能大于20位'}},
			{colIndex:0, field:{xtype : 'textfield', hidden:true,	name:'video_interval', id:'video_interval_add',	 fieldLabel:'视频播放间隔(秒)',	regex:/^[0-9]*$/	,regexText:'请输入20位以内整数',maxLength : 20,maxLengthText : '长度不能大于20位'}},
			{colIndex:0, field:{layout:'column',hidden:true,fieldLabel:'播放时间(秒)',items:[{xtype : 'textfield',editable:false, name:'play_time',id:'play_time_add', 	fieldLabel:'播放时间',	width:'160',readOnly:true},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){selectTime("play_time_add");}}}]}},
			{colIndex:0, field:{xtype : 'textarea', id:'photos_name_add',name:'photos_path',	 fieldLabel:'播放内容详情',  allowBlank : false,	blankText : '请上传自助广告播放内容',	height:130,	width:290,maxLength : 2000,maxLengthText : '长度不能大于1200位',readOnly:true}}
			],
			[
				
				{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
				{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}},
				{text:'上传详情图片', handler: function(){
					 var dialog = new Ext.ux.UploadDialog.Dialog({    
					              autoCreate: true,    
					              closable: true,    
					              collapsible: false,    
					              draggable: false,    
					              minWidth: 400,          
					              minHeight: 200,    
					              width: 400,    
					              height: 350,    
					             permitted_extensions:file_limit,    
					             proxyDrag: false,    
					             resizable: false,    
					             constraintoviewport: true,    
					             title: '文件上传',    
					             url: '<%=basePath%>/confManager/advertInfoAction_uploadDetailsPic',
					             post_var_name:'uploadFiles',  
					             base_params:{'upload_fileNames':Ext.getCmp('photos_name_add').getValue()},  
					             reset_on_hide: false,    
					             allow_close_on_upload: true    
				});
					
					 var files = []; //本地文件名称数组
					 var savefiles=[];//上传重命名文件名称数组
					 var uploadflag = false;
					 var param = dialog.getBaseParams();
					 param['prephotosnum']=0;
					 dialog.on('filetest', function(dialog,filename){
					 	if(uploadflag){ 
					 		dialog.resetQueue();
							dialog.fireResetQueueEvent();
							uploadflag = false;
							files = [];
							savefiles=[];
					 	}						 	
					 });
					 dialog.on('resetqueue', function(dialog,filename){
					 	files = [];
					 	savefiles=[];
					 });
					 
					 dialog.on('fileadd', function(dialog,filename){
						 var param = dialog.getBaseParams();
						 var photos_num= Ext.getCmp('photos_name_add').getValue().split(';');
						 if(photos_num.length!=0){
							 param['prephotosnum'] = photos_num.length-1;
							 if("999888"===<%="'"+branch+"'"%> || "910020000"===<%="'"+branch+"'"%>){
								 	if(files.length+photos_num.length > parseInt(branch_imagefile_num)){
								 		Ext.Msg.alert('溫馨提示','上传文件数最多为'+branch_imagefile_num);
								 	}
							 	}else{
							 		if(files.length+photos_num.length > parseInt(sub_imagefile_num)){
								 		Ext.Msg.alert('溫馨提示','上传文件数最多为'+sub_imagefile_num);
								 	}
							 	}
						 }else{
							 if("999888"===<%="'"+branch+"'"%> || "910020000"===<%="'"+branch+"'"%>){
								 	if(files.length >= parseInt(branch_imagefile_num)){
								 		Ext.Msg.alert('溫馨提示','上传文件数最多为'+branch_imagefile_num);
								 	}
							 	}else{
							 		if(files.length >= parseInt(sub_imagefile_num)){
								 		Ext.Msg.alert('溫馨提示','上传文件数最多为'+sub_imagefile_num);
								 	}
							 	}
						 }
						 
						filename = filename.substring(filename.lastIndexOf("\\")+1);
					 	files.push(filename);
					 	var param = dialog.getBaseParams();
					 	param['upload_fileNames'] = files;
					 	dialog.setBaseParams(param);
					 });
					 dialog.on('beforefileuploadstart', function(dialog,filename){
						 var param = dialog.getBaseParams();
						 	if("999888"===<%="'"+branch+"'"%> || "910020000"===<%="'"+branch+"'"%>){
							 	if(files.length+param['prephotosnum'] > parseInt(branch_imagefile_num)){
							 		Ext.Msg.alert('溫馨提示','上传文件数最多为'+branch_imagefile_num);  
							 		uploadflag = false;
								    return false;  
							 	}
						 	}else{
						 		if(files.length+param['prephotosnum'] > parseInt(sub_imagefile_num)){
							 		Ext.Msg.alert('溫馨提示','上传文件数最多为'+sub_imagefile_num);  
							 		uploadflag = false;
								    return false;  
							 	}
						 	}
						 	
						 });
					 dialog.on('fileremove', function(dialog,filename){
					 	filename = filename.substring(filename.lastIndexOf("\\")+1);
					 	var index = -1;
					 	for (var i = 0; i < files.length; i++) {  
					        if (files[i] == filename){
					        	 index=i;
					        	 break;
					        }
					    }  
					 	index != -1 && files.splice(index, 1);
					 	var param = dialog.getBaseParams();
					 	param['upload_fileNames'] = files;
					 	dialog.setBaseParams(param);
					 }); 
					 
					  dialog.on('uploadsuccess', function(dialog,filename,data,record){
						    uploadflag = true;
						    var savefilesName = []//存放原文件名称的 
						 	var sn = data.filename+";";
						 	savefiles.push(sn);
						 	var param = dialog.getBaseParams();
						 	Ext.getCmp('photos_name_add').setValue(Ext.getCmp('photos_name_add').getValue()+savefiles.toString());
						 	savefiles=[];
					 }); 
					 dialog.show(); 
				}}
			]
		);
				editwindow = new SelfFormWindow('recordEditWindow', '编辑广告信息', 350, 380, 200, 1,
					[
						{colIndex:0, field:{xtype : 'textfield', 	name:'advert_id',id:'advert_id_edit',	allowBlank : false, fieldLabel:'广告编号',	 blankText : '请输入广告ID',		maxLength : 12,maxLengthText : '长度不能大于20位',readOnly:true	}},
						{colIndex:0, field:{xtype : 'textfield', 	name:'branch',id:'branch_edit',	allowBlank : false, fieldLabel:'机构号',	blankText : '请输入机构号',		maxLength : 12,maxLengthText : '长度不能大于20位',readOnly:true	}},
						{colIndex:0, field:{xtype : 'textfield', 	name:'play_interval', id:'play_interval_edit',	allowBlank : false, fieldLabel:'图片播放间隔(秒)',	blankText : '请输入图片播放间隔',	regex:/^\d{1,4}$/	,regexText:'请输入3位以内整数',maxLength : 3,maxLengthText : '长度不能大于3位'}},
						{colIndex:0, field:{xtype : 'textfield', hidden:true,	name:'video_interval', id:'video_interval_edit',	 fieldLabel:'视频播放间隔(秒)',	regex:/^\d{1,4}$/	,regexText:'请输入3位以内整数',maxLength : 3,maxLengthText : '长度不能大于3位'}},
						{colIndex:0, field:{layout:'column',hidden:true,fieldLabel:'播放时间(秒)',items:[{xtype : 'textfield',editable:false, name:'play_time',id:'play_time_edit', 	fieldLabel:'播放时间',	width:'160',readOnly:true},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){selectTime("play_time_edit");}}}]}},
						{colIndex:0, field:{xtype : 'textarea', 	name:'photos_path',	 id:'photos_name_edit',	 fieldLabel:'播放内容详情', allowBlank : false,	blankText : '请上传自助广告播放内容',	height:130,width:290, maxLength : 2000,maxLengthText : '长度不能大于400位',readOnly:true}}
					],
					[
						{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
						{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close();}},
						{text:'修改详情图片', handler: function(){
							Ext.getCmp('photos_name_edit').setValue('');
						var dialog = new Ext.ux.UploadDialog.Dialog({    
					              autoCreate: true,    
					              closable: true,    
					              collapsible: false,    
					              draggable: false,    
					              minWidth: 400,          
					              minHeight: 200,    
					              width: 400,    
					              height: 350,    
					             permitted_extensions:file_limit,    
					             proxyDrag: false,    
					             resizable: false,    
					             constraintoviewport: true,    
					             title: '文件上传',    
					             url: '<%=basePath%>/confManager/advertInfoAction_uploadDetailsPic',
					             post_var_name:'uploadFiles',  
					             base_params:{'upload_fileNames':Ext.getCmp('photos_name_edit').getValue()},  
					             reset_on_hide: false,    
					             allow_close_on_upload: true    
					           });
					 var files = []; //本地文件名称数组
					 var savefiles=[];//上传重命名文件名称数组
					 var uploadflag = false;
					 var photos_num= Ext.getCmp('photos_name_edit').getValue().split(';');
					 var param = dialog.getBaseParams();
					 param['prephotosnum'] = photos_num.length-1;
					 dialog.on('filetest', function(dialog,filename){
					 	if(uploadflag){
					 		dialog.resetQueue();
							dialog.fireResetQueueEvent();
							uploadflag = false;
							files = [];
							savefiles=[];
					 	}
					 		
					 });
					  dialog.on('resetqueue', function(dialog,filename){
					 	files = [];
					 	savefiles=[];
					 });
					dialog.on('beforefileuploadstart', function(dialog,filename){
					 	if("999888"===<%="'"+branch+"'"%> || "910020000"===<%="'"+branch+"'"%>){
						 	if(photos_num.length+files.length > parseInt(branch_imagefile_num)+1){
						 		Ext.Msg.alert('溫馨提示','上传文件数最多为'+branch_imagefile_num);  
						 		uploadflag = false;
							    return false;  
						 	}
					 	}else{
					 		if(photos_num.length+files.length > parseInt(sub_imagefile_num)+1){
						 		Ext.Msg.alert('溫馨提示','上传文件数最多为'+sub_imagefile_num);  
						 		uploadflag = false;
							    return false;  
						 	}
					 	}
					 	
					 });
					 dialog.on('fileadd', function(dialog,filename){
					 	if("999888"===<%="'"+branch+"'"%> || "910020000"===<%="'"+branch+"'"%>){
						 	if(files.length > parseInt(branch_imagefile_num)-photos_num.length){
						 		Ext.Msg.alert('溫馨提示','上传文件数最多为'+branch_imagefile_num);
						 	}
					 	}else{
					 		if(files.length > parseInt(sub_imagefile_num)-photos_num.length){
						 		Ext.Msg.alert('溫馨提示','上传文件数最多为'+sub_imagefile_num);
						 	}
					 	}
					 	filename = filename.substring(filename.lastIndexOf("\\")+1);
					 	files.push(filename);
					 	var param = dialog.getBaseParams();
					 	param['upload_fileNames'] = files;
					 	dialog.setBaseParams(param);
					 });
					 dialog.on('fileremove', function(dialog,filename){
					 	filename = filename.substring(filename.lastIndexOf("\\")+1);
					 	var index = -1;
					 	for (var i = 0; i < files.length; i++) {  
					        if (files[i] == filename){
					        	 index=i;
					        	 break;
					        }
					    }  
					 	index != -1 && files.splice(index, 1);
					 	var param = dialog.getBaseParams();
					 	param['upload_fileNames'] = files;
					 	dialog.setBaseParams(param);
					 }); 
					  dialog.on('uploadsuccess', function(dialog,filename,data,record){
						uploadflag = true;
						var param = dialog.getBaseParams();
						var savefilesName = [];//存放原文件名称的 
					 	var sn = data.filename+";";
					 	savefiles.push(sn);
					 	Ext.getCmp('photos_name_edit').setValue(Ext.getCmp('photos_name_edit').getValue()+savefiles.toString());
					 	savefiles.pop(sn);
					 }); 
					 dialog.show(); 
					 
				}}
					]
				);
				var timeWindow = new SelfFormWindow('timeWindow', '播放时间配置', 500, 355, 460, 1,
						[
				 			{colIndex:0,field:{xtype:'hidden',id:'row'}},
							{colIndex:0,field:{
								xtype:'fieldset', layout:'column', items:[
                       				timeGrid
                       			]
							}}
						],
						[
							{xtype: 'button' , text:'保存' , id:'btnSave' , handler:function(){
								Ext.MessageBox.confirm('<s:text name="common.info.title"/>' , '保存此配置?' , function(id){
									if(id=='yes'){
										var serv_time_list = '';
										for ( var i = 0; i < timeStore.getCount(); i++) {
										var r = timeStore.getAt(i);
										serv_time_list+=';' + r.get('begin_time')+'-'+r.get('end_time');
										if(r.get('begin_time')==''||r.get('end_time')==''){
											Ext.MessageBox.alert('提示','数据未填写完整,请修改');
											return;
										}
									}
									Ext.getCmp('play_time_add').setValue(serv_time_list.substr(1));
									Ext.getCmp('play_time_edit').setValue(serv_time_list.substr(1));
									timeWindow.close();
								}
								});
							}},
							{xtype: 'button' , text:'取消' , id:'btnClose', handler:function(){timeWindow.close();}}
						]
					);
				/*function download(record){
			    var fileStore = new Ext.data.JsonStore({
			    	fields:['filename']
			    });
			    var fileData = [
      				{xtype:'checkcolumn',header:'复选框', dataIndex: 'monday',sortable:false,width:60,align:'center'},		    	
      				{header:'文件名称',     dataIndex:'filename',width:150},
      				{header:'文件机构',     dataIndex:'branch',width:150}
				];
			    var fileColModel=new Ext.grid.ColumnModel(fileData);
			    var fileWindow = new SelfFormWindowSetWidth('fileWindow','上级机构业务配置列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'文件列表',
			   			layout:'column',
			   			items:[{
			       			columnWidth:1,
			       			items:[{
			       				xtype:'grid',
			       				id:'fileGrid',
			       				store:fileStore,
			       				cm:fileColModel,
			       				height:250,
			       				iconCls:'icon-grid',
			       				stripeRows : true,
			       				doLayout:function(){
			       					this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true));
			       				}
			       			}]
			   			}]
			   		}
			    }],[],'left',140);
			    fileWindow.open();
			    var filenames=record[0].get('photos_name').split(';');
				var branch =record[0].get('branch'); 
				fileStore.removeAll();
                   var newArray = new Array();
				for (var i = 0; i < filenames.length; i++) {
					var filename = filenames[i];
					var datas={};
					if(filename.length>0){
						datas['filename'] = filename;
						datas['branch'] = branch;
						newArray.push(datas);						
					}
				}
				fileStore.loadData(newArray);
			} */
				function selectTime(grid, rowIndex, colIndex){
                    timeStore.removeAll();
                    var newArray = new Array();
                    var play_time_list = Ext.getCmp(grid).getValue();
                   var serv_time_list = play_time_list.split(';');
                     for ( var i = 0; i < serv_time_list.length; i++) {
						var serv_time = serv_time_list[i];
						if(serv_time.length>0){
							var datas={};
							if (serv_time.indexOf(':')==-1) {
								var begin_time = serv_time.substr(0,6);
								var end_time = serv_time.substr(7);
								datas['begin_time'] = begin_time.substr(0,2)+':'+begin_time.substr(2,2)+':'+begin_time.substr(4,2);
								datas['end_time'] = end_time.substr(0,2)+':'+end_time.substr(2,2)+':'+end_time.substr(4,2);
							} else {
								datas['begin_time'] = serv_time.substr(0,8);
								datas['end_time'] = serv_time.substr(9,8);
							}								
							newArray.push(datas);
						}
					}
                    timeStore.loadData(newArray);
                    row = rowIndex;
                    timeWindow.open();
			
		}
		/*主窗口按钮设置*/
		function onButtonClicked(btn_index){
			switch(btn_index){
			case 0://查询
				var query_obj = conditionPanel.getFields();
				pagequeryObj.queryPage(query_obj);
			break;
			case 1://添加
				var records = pagequeryObj.getStore().data.items;
				for(var i=0;i<records.length;i++){
					if(records[i].data.branch==<%="'"+branch+"'"%>){
				    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','本机构已存在相关配置,若想继续添加，请删除原有配置!');
				    		return;
				    }
				}
				addwindow.open();
				Ext.getCmp('branch_add').setValue('<%=branch%>');
			break;
			case 2://修改
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
					//var recorddata = records[0];
					editwindow.open();
					var values= records[0].data.play_time.split(";");
					var time='';
					for(var i=0;i<values.length;i++){
						if(values[i].indexOf(":")==-1){
							time+=";"+values[i].substr(0,2)+":"+values[i].substr(2,2)+":"+values[i].substr(4,5)+":"+values[i].substr(9,2)+":"+values[i].substr(11,2);
						}else{
							time+=";"+values[i];
						}
					}
					records[0].data.play_time=time.substr(1);
					//alert("unitid="+records[0].data.unitid); 
					editwindow.updateFields(records[0]);
				}
			break;
			case 3://删除
				var submitdata = pagequeryObj.getSelectedObjects(['branch','advert_id']);
				if(submitdata === undefined){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
				}else{
					//无法删除非本行机构
				    for(var i=0;i<submitdata.length;i++){
				    	if(submitdata[i].branch!=<%="'"+branch+"'"%>){
				    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
				    		return;
				    	}
				    }
					
					Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
						if(id === 'yes'){
							requestAjax('<%=basePath%>/confManager/advertInfoAction_deleteAdvertParam',submitdata,function(sRet){
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
				requestAjax('<%=basePath%>/confManager/advertInfoAction_addAdvertParam', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						//query_obj.unitlist = null;
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
		}
		function oneditclicked(){
			var submitData = editwindow.getFields();
			requestAjax('<%=basePath%>/confManager/advertInfoAction_editAdvertParam', submitData,
				function(sRet) {
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',
						function(id) {
								var query_obj = conditionPanel.getFields();
								pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
			});
		}
		
		function buildLayout() {
			var viewport = new Ext.Viewport(
				{
					layout : "border",
					items : [ conditionPanel.toolbarwindow,pagequeryObj.pagingGrid ]
				});
		}
		buildLayout();
		var query_obj = conditionPanel.getFields();
		pagequeryObj.queryPage(query_obj);
	}
	</script>
  </head>
  
  <body scroll="no">
		<div id="queryConditionPanel"></div>
		<div id="pageQueryTable"></div>
		<div id="conditionPanel"></div>
		<div id="timeWindow"></div>
		<div id="fileWindow"></div>
		<div id="recordAddWindow"></div>
		<div id="recordEditWindow"></div>
  </body>
</html>
