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
	String bank_level = user.getUnit().getBank_level();
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>广告图片管理</title>

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
		var addwindow;
		var editwindow;
		var file_limit=pagereturn.file_limit.split(',');
		var sub_imagefile_num=pagereturn.sub_imagefile_num;
		var branch_imagefile_num=pagereturn.branch_imagefile_num;
		<%-- var deviceTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
			root   : 'field1',
			fields : ['dictValue','dictValueDesc'],
			autoLoad:true
		}); --%>
		
		/*
		//设备类型
		var deviceTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>/confManager/advert_queryDeviceType?item_id=devicetype',
			root   : 'field1',
			fields : ['dictValue','dictValueDesc'],
			autoLoad:true
		});
		*/
		
		var deviceTypeStore = new Ext.data.SimpleStore({
			data:[['04','手持设备'],['10','填单机'],['11','发卡机']],
			fields:['divice_type','divice_type_desc']
		})
		
		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '广告图片管理', 120, 1,
				[
					{rowIndex:0, field:{xtype:'combo',name:'device_type',editable:false,fieldLabel:'设备类型', forceSelection:true, hiddenName:'device_type', store:deviceTypeStore, 	displayField:'divice_type_desc',valueField:'divice_type',value:''}}
				],
				[
				{iconCls: 'x-image-query', 	 id:'01',	text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: 'x-image-user_add',  id:'02',		text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: 'x-image-user_delete',  id:'03',	text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked
			);
			conditionPanel.open();
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/confManager/advert_queryAdvertList',
				['branch','device_type','content_id','content','creator','create_date','explain','content'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',	dataIndex:'branch',width:100},
				{header:'设备类型',	dataIndex:'device_type',width:170, renderer:function(value){
				      return getDictValue(value.toString(),deviceTypeStore,'divice_type','divice_type_desc');//翻译字段方法
			    }},
				{header:'图片编号',	dataIndex:'content_id',		width:170},
				{header:'广告详情',		dataIndex:'content',		width:240,renderer:function(value,metadata){
					value = value.toString();
					metadata.attr = 'ext:qtitle="" ext:qtip="<img width=\'512\' height=\'384\' src=\''+ value +'\'/>"';
					value = "<img width='64' height='48' src=\'" + value + "\'/>";
          		 	return value;
       			 }},
       			{header:'创建人',dataIndex:'creator',width:150},
       			{header:'创建日期',dataIndex:'create_date',width:150},
				{header:'图片说明',		dataIndex:'explain',		width:180}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			/*添加系统用户*/
			addwindow = new SelfFormWindow('imageAddWindow', '添加广告图片', 600, 290, 240, 2,
				[
					{colIndex:0, field:{xtype : 'textfield', 	name:'branch', id:'branch_add',	fieldLabel:'机构号',readOnly:true}},
					//{colIndex:0, field:{xtype : 'textfield', 	name:'content_id', id:'content_id_add',	fieldLabel:'图片编号',readOnly:true}},
				 	{colIndex:0,field: {xtype:'combo',name:'device_type',id:'device_type_add',fieldLabel:'设备类型',forceSelection:true,allowBlank:false,hiddenName:'device_type',store:deviceTypeStore,displayField:'divice_type_desc',valueField:'divice_type'}},
					{colIndex:0, field:{xtype : 'textarea', 	name:'explain', id:'explain_add',allowBlank:false,blankText:'请尽量全面的描述广告信息，字数限制在256内',fieldLabel:'广告备注',maxLength : 256,maxLengthText : '长度不能大于256位'}},
					//{colIndex:0, field:{xtype : 'textfield', 	name : 'content',  id : 'content_add', inputType : 'file', allowBlank : true ,fieldLabel:'选择图片上传', 	 blankText : '请选择图片',editable:false}},
					{colIndex:1, field:{xtype : 'textarea', fieldLabel:'广告详情图片', name:'content', id:'content_add',blankText:'请上传图片', readOnly:true,height:165,	width:260}}
				],
				[
					
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}},
					{text:'上传详情图片',formBind:true, handler: function(){
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
						             url: '<%=basePath%>/confManager/advert_uploadAdvertisingPicture',
						             post_var_name:'uploadFiles',  
						             base_params:{'upload_fileNames':Ext.getCmp('content_add').getValue()},  
						             reset_on_hide: false,    
						             allow_close_on_upload: true    
					});
						
						 var files = []; //本地文件名称数组
						 var savefiles=[];//上传重命名文件名称数组
						 var uploadflag = false;
						 var param = dialog.getBaseParams();
						 param['prephotosnum']=0;
						 param['device_type']=Ext.getCmp('device_type_add').getValue();
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
							 var photos_num= Ext.getCmp('content_add').getValue().split(';');
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
							 	var imgpath = savefiles.toString();
							 	Ext.getCmp('content_add').setValue(Ext.getCmp('content_add').getValue()+savefiles.toString());
							 	savefiles=[];
						 }); 
						 dialog.show(); 
					}}
				],'top',240,true
			);

			/*主窗口按钮设置*/
			function onButtonClicked(btn_index){
				switch(btn_index){
				case 0://查询
					var query_obj = conditionPanel.getFields();
					 var dt = query_obj["device_type"];
					if(dt==null||""==dt){
						Ext.Msg.alert("系统提示","请选择设备类型！");
						return ;
					} 
					pagequeryObj.queryPage(query_obj);
				break;
				case 1://添加
					addwindow.open();
					Ext.getCmp('branch_add').setValue('<%=branch%>');
					//Ext.getCmp('content_id_add').setValue(getNewContentId());
				break;
				case 2://删除
					var records = pagequeryObj.getSelectedRecords();
					var submitdata = pagequeryObj.getSelectedObjects(['content_id','branch','content','device_type']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						//无法删除非本行机构
						var branch = '<%=branch%>';
					   /* for(var i=0;i<submitdata.length;i++){
					    	if(submitdata[i].branch!=<%=branch%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的设备图片');
					    		return;
					    	}
					    }*/
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=branch){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
					    		return;
					    	}
					    }
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/confManager/advert_delAdvertImage',submitdata,function(sRet){
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
			
			/*
			*添加广告
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				if(submitData["content"] == "" || submitData["content"] == null || submitData["content"] == undefined){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','请添加广告图片');
					return;
				}
				requestAjax('<%=basePath%>/confManager/advert_addAdvertisingPicture', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						//query_obj.unitlist = null;
						query_obj["device_type"]=submitData["device_type"];
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
			}
			
			 /*生成产品编号
			function getNewContentId() {
    			var date = new Date();
                var month = date.getMonth() + 1;
                var strDate = date.getDate();
                var hour = date.getHours();
                var minute =  date.getMinutes();
                var second = date.getSeconds()
                var num = "";
                if (month <= 9) {
                     month = "0" + month;
                }
                if (strDate <= 9) {
                     strDate = "0" + strDate;
                }
                if (hour <= 9) {
                    hour = "0" + hour;
               }
                if (minute <= 9) {
                    minute = "0" + minute;
               }
                if (second <= 9) {
                	second = "0" + second;
               }
                for(var i=0;i<6;i++) 
                { 
                  num+=Math.floor(Math.random()*10); 
                } 

                var content_id = date.getFullYear() + month + strDate+hour+minute+second+num;
                return content_id;
            }
			 */
			
			function buildLayout() {
				var viewport = new Ext.Viewport(
					{
						layout : 'border',
						items : [ 
					          conditionPanel.toolbarwindow,
					          pagequeryObj.pagingGrid 
						]
					});
			}
			buildLayout();	
		}
		</script>

	</head>

	<body scroll="no">
		<div id="queryConditionPanel"></div>
		<div id="pageQueryTable"></div>
		<div id="imageAddWindow"></div>
	</body>
</html>
