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
    
    <title>凭证管理</title>
    
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
	//凭证类型
	var vouchertypeStore= new Ext.data.SimpleStore({ 
		data:[['0','0-卡'],['1','1-ukey'],['2','2-令牌']],
		fields:['key','value']
	});
	//凭证状态
	var voucherstatusStore= new Ext.data.SimpleStore({ 
		data:[['0','0-停用'],['1','1-启用']],
		fields:['key','value']
	});
	Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.onReady(loadPage);
	
	function loadPage(){
		var clientWidth = document.body.clientWidth;
		var clientHeight = document.body.clientHeight;
		
		var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '凭证管理', 120, 0,
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
			'<%=basePath%>/dataAnalysis/voucherManager_queryVoucherInfo',
			['vouchercode','vouchertype','vouchercodename','voucherstatus','manufacturertype','voucherpicture','picturemd5','voucherpicturepath'],
			[
			{header:'全选'},
			{header:'复选框'},
			{header:'凭证号码',		dataIndex:'vouchercode',		width:100},
			{header:'凭证类型',dataIndex:'vouchertype',width:100,renderer:function(value){
					      return getDictValue(value,vouchertypeStore,'key','value');//翻译字段方法
			}},
			{header:'凭证名称',	dataIndex:'vouchercodename',	width:100},
			{header:'凭证状态',	dataIndex:'voucherstatus',	width:100,renderer:function(value){
						return getDictValue(value,voucherstatusStore,'key','value');//翻译字段方法
			}},
			{header:'厂商类型',	dataIndex:'manufacturertype',	width:100},
			{header:'凭证图片名称', dataIndex:'voucherpicture', width:100,hidden:true},
			{header:'图片的MD5值',dataIndex:'picturemd5', width:100,hidden:true},
			{header:'凭证图片',		dataIndex:'voucherpicturepath',height:50,width:500,renderer:function(value,metadata,record,index,colIndex,store){
				if(value!=null&&value!=""&&value!=undefined){
					var values = value.toString().split(';');
					var v = '';
					for(var i=0;i<values.length;i++){
						if(values[i].indexOf("jpg")!=-1||values[i].indexOf("JPG")!=-1||values[i].indexOf("png")!=-1||values[i].indexOf("PNG")!=-1||values[i].indexOf("jpeg")!=-1||values[i].indexOf("JPEG")!=-1||values[i].indexOf("GIF")!=-1||values[i].indexOf("gif")!=-1||values[i].indexOf("bmp")!=-1||values[i].indexOf("BMP")!=-1){
						 	v = v + "&nbsp;&nbsp;&nbsp;<img height='200' width='200' src=\"dataAnalysis/voucherManager_getCustImg.action?filename="+values[i]+"&&branch="+record.data['branch']+"\"/>";							
						}else if(value[i]!=""){	
							v=v+values[i]+" ";
						}
					}
	      		 	return v;
	      		 	}
      		 }}
			],
			'<s:text name="common.pagequery.pagingtool"/>'
		);
		/*添加新凭证信息*/
		addwindow = new SelfFormWindow('recordAddWindow', '添加凭证',350, 380, 200, 1,
			[
			{colIndex:0, field:{xtype : 'textfield', 	name:'vouchercode',allowBlank : false,regex:/^[0-9]*$/,regexText:'请输入20位以内数字', fieldLabel:'凭证号码',	blankText : '请输入凭证号码',	maxLength : 20,maxLengthText : '长度不能大于20位'}},
			{colIndex:0, field:{xtype : 'combo', 	name:'vouchertype', id:'vouchertype_add', hiddenName:'vouchertype',	allowBlank : false, fieldLabel:'凭证类型',	blankText : '请选择凭证类型',editable:false,store:vouchertypeStore,displayField:"value",valueField:"key",listeners:{
				select:function(combo,record,index){
					//对应的处理函数
					if(record.get('key')=='1'||record.get('key')=='2'){
						if(Ext.getCmp('manufacturertype_add').allowBlank==true){
							var txtLabel = Ext.getCmp('manufacturertype_add').getEl().parent().parent().first(); 
		               	 	txtLabel.dom.innerHTML = Ext.getCmp('manufacturertype_add').fieldLabel + '<font color=red>*</font>:';
		               	 }
		                Ext.getCmp('manufacturertype_add').allowBlank=false;
		                if(Ext.getCmp('voucherpicture_add').allowBlank==false){
							var txtLabel = Ext.getCmp('voucherpicture_add').getEl().parent().parent().first(); 
		                	txtLabel.dom.innerHTML = Ext.getCmp('voucherpicture_add').fieldLabel + ':';
						}
						Ext.getCmp('voucherpicture_add').allowBlank=true;
					}else if(record.get('key')=='0'){
						if( Ext.getCmp('voucherpicture_add').allowBlank==true){
							var txtLabel = Ext.getCmp('voucherpicture_add').getEl().parent().parent().first(); 
		                	txtLabel.dom.innerHTML = Ext.getCmp('voucherpicture_add').fieldLabel + '<font color=red>*</font>:';
						}
		                Ext.getCmp('voucherpicture_add').allowBlank=false;
		                if(Ext.getCmp('manufacturertype_add').allowBlank==false){
							var txtLabel = Ext.getCmp('manufacturertype_add').getEl().parent().parent().first(); 
		               	 	txtLabel.dom.innerHTML = Ext.getCmp('manufacturertype_add').fieldLabel + ':';
						}
						Ext.getCmp('manufacturertype_add').allowBlank=true;
					}
				}
			}}},
			{colIndex:0, field:{xtype : 'textfield', 	name:'vouchercodename', id:'vouchercodename_add',	allowBlank : false, fieldLabel:'凭证名称',	blankText : '请输入凭证名称',	maxLength : 256,maxLengthText : '长度不能大于256位'}},
			{colIndex:0, field:{xtype : 'combo', 	name:'voucherstatus', id:'voucherstatus_add', hiddenName:'voucherstatus',	allowBlank : false, fieldLabel:'凭证状态',	blankText : '请选择凭证状态',	editable:false, maxLength : 8,maxLengthText : '长度不能大于8位',store:voucherstatusStore,displayField:"value",valueField:"key"}},
			{colIndex:0, field:{xtype : 'textfield', 	name:'manufacturertype', id:'manufacturertype_add',	 fieldLabel:'厂商类型',allowBlank : true,	blankText : '请输入厂商类型',	maxLength : 256,maxLengthText : '长度不能大于256位'}},
			//{colIndex:0, field:{xtype:'textfield', name:'voucherpicturepath',id:'voucherpicturepath_add',fieldLabel:'图片地址',allowBlank: true, blankText : '请输入图片地址'}},
			{colIndex:0, field:{xtype : 'textarea', id:'voucherpicture_add',name:'voucherpicture',	 fieldLabel:'凭证图片',  allowBlank : true,	blankText : '请上传凭证图片',	height:50,	width:290,maxLength : 256,maxLengthText : '长度不能大于256位',readOnly:true}}
			],
			[
				
				{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
				{text:'<s:text name="common.button.cancel"/>', handler: onAddConcle},
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
					             url: '<%=basePath%>/dataAnalysis/voucherManager_uploadDetailsPic',
					             post_var_name:'uploadFiles',  
					             base_params:{'upload_fileNames':Ext.getCmp('voucherpicture_add').getValue()},  
					             reset_on_hide: false,    
					             allow_close_on_upload: true    
				});
					
					 var files = []; //本地文件名称数组
					 var savefiles=[];//上传重命名文件名称数组
					 var uploadflag = false;
					 
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
						 	if(files.length > 1){
							 		Ext.Msg.alert('溫馨提示','最多只能上传一张图片');
							 		uploadflag = true;
								    return false;  
							}
						 	
						 });
					 
					 dialog.on('fileadd', function(dialog,filename){
						 /* if(files.length >= 1){
							 	Ext.Msg.alert('溫馨提示','最多只能上传一张图片');
							 	uploadflag = true;
								return false;  
						} */
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
						    var savefilesName = []//存放原文件名称的 
						 	var sn = data.filename;
						 	savefiles.push(sn);
						 	var param = dialog.getBaseParams();
						 	Ext.getCmp('voucherpicture_add').setValue(Ext.getCmp('voucherpicture_add').getValue()+savefiles.toString());
						 	savefiles=[];
					 }); 
					 dialog.show(); 
				}}
			]
		);
				editwindow = new SelfFormWindow('recordEditWindow', '编辑凭证信息', 350, 380, 200, 1,
					[
						{colIndex:0, field:{xtype : 'textfield', 	name:'vouchercode',	 fieldLabel:'凭证号码',readOnly:true,editable:false,		maxLength : 20,maxLengthText : '长度不能大于20位'	}},
						{colIndex:0, field:{xtype : 'combo', 	name:'vouchertype', id:'vouchertype_edit',hiddenName:'vouchertype',	allowBlank : false, fieldLabel:'凭证类型',	blankText : '请选择凭证类型',editable:false,store:vouchertypeStore,displayField:"value",valueField:"key",listeners:{
							select:function(combo,record,index){
							//对应的处理函数
							if(record.get('key')=='1'||record.get('key')=='2'){
								if(Ext.getCmp('manufacturertype_edit').allowBlank==true){
									var txtLabel = Ext.getCmp('manufacturertype_edit').getEl().parent().parent().first(); 
				               	 	txtLabel.dom.innerHTML = Ext.getCmp('manufacturertype_edit').fieldLabel + '<font color=red>*</font>:';
				               	 }
				                Ext.getCmp('manufacturertype_edit').allowBlank=false;
				                if(Ext.getCmp('voucherpicture_edit').allowBlank==false){
									var txtLabel = Ext.getCmp('voucherpicture_edit').getEl().parent().parent().first(); 
				                	txtLabel.dom.innerHTML = Ext.getCmp('voucherpicture_edit').fieldLabel + ':';
								}
								Ext.getCmp('voucherpicture_edit').allowBlank=true;
							}else if(record.get('key')=='0'){
								if( Ext.getCmp('voucherpicture_edit').allowBlank==true){
									var txtLabel = Ext.getCmp('voucherpicture_edit').getEl().parent().parent().first(); 
				                	txtLabel.dom.innerHTML = Ext.getCmp('voucherpicture_edit').fieldLabel + '<font color=red>*</font>:';
								}
				                Ext.getCmp('voucherpicture_edit').allowBlank=false;
				                if(Ext.getCmp('manufacturertype_edit').allowBlank==false){
									var txtLabel = Ext.getCmp('manufacturertype_edit').getEl().parent().parent().first(); 
				               	 	txtLabel.dom.innerHTML = Ext.getCmp('manufacturertype_edit').fieldLabel + ':';
								}
								Ext.getCmp('manufacturertype_edit').allowBlank=true;
							}
							}
						}}},
						{colIndex:0, field:{xtype : 'textfield', 	name:'vouchercodename', id:'vouchercodename_edit',	allowBlank : false, fieldLabel:'凭证名称',	blankText : '请输入凭证名称',	maxLength : 256,maxLengthText : '长度不能大于256位'}},
						{colIndex:0, field:{xtype : 'combo', 		name:'voucherstatus',   id:'voucherstatus_edit', hiddenName:'voucherstatus',	allowBlank : false, fieldLabel:'凭证状态',	blankText : '请选择凭证状态',	maxLength : 8,maxLengthText : '长度不能大于8位', editable:false, store:voucherstatusStore,displayField:"value",valueField:"key"}},
						{colIndex:0, field:{xtype : 'textfield', 	name:'manufacturertype', id:'manufacturertype_edit',	allowBlank : true, fieldLabel:'厂商类型',	blankText : '请输入厂商类型',	maxLength : 256,maxLengthText : '长度不能大于256位'}},
						{colIndex:0, field:{xtype : 'textfield', 	name:'deletedPic', id:'deletedPic_edit', fieldLabel:'待删除图片',hidden:true}},
						{colIndex:0, field:{xtype : 'textarea', id:'voucherpicture_edit',name:'voucherpicturepath',	 fieldLabel:'凭证图片',  allowBlank : true,	blankText : '请上传凭证图片',	height:60,	width:290,maxLength : 256,maxLengthText : '长度不能大于256位',readOnly:true}}
					],
					[
						{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
						{text:'<s:text name="common.button.cancel"/>', handler: onEditConcle},
						{text:'修改详情图片', handler: function(){
							
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
					             url: '<%=basePath%>/dataAnalysis/voucherManager_uploadDetailsPic',
					             post_var_name:'uploadFiles',  
					             base_params:{'upload_fileNames':Ext.getCmp('voucherpicture_edit').getValue()},  
					             reset_on_hide: false,    
					             allow_close_on_upload: true    
					           });
					 var files = []; //本地文件名称数组
					 var savefiles=[];//上传重命名文件名称数组
					 var uploadflag = false;
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
					 	if(files.length > 1){
							 		Ext.Msg.alert('溫馨提示','最多只能存在一张图片');
							 		uploadflag = true;
								    return false;  
						}
					 });
					 dialog.on('fileadd', function(dialog,filename){
					 	/* if(photos_num-1+files.length >= 1){
							 	Ext.Msg.alert('溫馨提示','最多只能存在一张图片');
								return ;  
						} */
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
					 	var sn = data.filename;
					 	savefiles.push(sn);
					 	//Ext.getCmp('voucherpicture_edit').setValue('');
					 	Ext.getCmp('voucherpicture_edit').setValue(savefiles.toString());
					 	savefiles.pop(sn);
					 }); 
					 dialog.show(); 
					 
				}}
					]
				);
				
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
				addwindow.open();
				var txtLabel = Ext.getCmp('manufacturertype_add').getEl().parent().parent().first(); 
		        txtLabel.dom.innerHTML = Ext.getCmp('manufacturertype_add').fieldLabel + ':';
				var txtLabel1 = Ext.getCmp('voucherpicture_add').getEl().parent().parent().first(); 
		        txtLabel1.dom.innerHTML = Ext.getCmp('voucherpicture_add').fieldLabel + ':';
				Ext.getCmp('manufacturertype_add').allowBlank=true;
				Ext.getCmp('voucherpicture_add').allowBlank=true;
			break;
			case 2://修改
				var records = pagequeryObj.getSelectedRecords();
				if(records === undefined){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
				}else if(records.length !== 1){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
				}else{
				
					
					editwindow.open();
					var txtLabel = Ext.getCmp('manufacturertype_edit').getEl().parent().parent().first(); 
			        txtLabel.dom.innerHTML = Ext.getCmp('manufacturertype_edit').fieldLabel + ':';
					var txtLabel1 = Ext.getCmp('voucherpicture_edit').getEl().parent().parent().first(); 
			        txtLabel1.dom.innerHTML = Ext.getCmp('voucherpicture_edit').fieldLabel + ':';
					Ext.getCmp('manufacturertype_edit').allowBlank=true;
					Ext.getCmp('voucherpicture_edit').allowBlank=true;
					editwindow.updateFields(records[0]);
					var deletedPic = Ext.getCmp('voucherpicture_edit').getValue();
					Ext.getCmp('deletedPic_edit').setValue(deletedPic);
				}
			break;
			case 3://删除
				var submitdata = pagequeryObj.getSelectedObjects(['vouchercode','voucherpicture']);
				if(submitdata === undefined){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
				}else{
					
					Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
						if(id === 'yes'){
							requestAjax('<%=basePath%>/dataAnalysis/voucherManager_deleteVoucherInfo',submitdata,function(sRet){
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
				requestAjax('<%=basePath%>/dataAnalysis/voucherManager_addVoucherInfo', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						//query_obj.unitlist = null;
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
		}
		
		function onEditConcle(){
			var submitData = editwindow.getFields();
			requestAjax('<%=basePath%>/confManager/dataAnalysis/voucherManager_editConcle', submitData,
				function(sRet) {
					editwindow.close();
			});
		}
		
		function onAddConcle(){
			var submitData = addwindow.getFields();
			requestAjax('<%=basePath%>/confManager/dataAnalysis/voucherManager_addConcle', submitData,
				function(sRet) {
					addwindow.close();
			});
		}
		
		function oneditclicked(){
			var submitData = editwindow.getFields();
			requestAjax('<%=basePath%>/confManager/dataAnalysis/voucherManager_editVoucherInfo', submitData,
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
		<div id="fileWindow"></div>
		<div id="recordAddWindow"></div>
		<div id="recordEditWindow"></div>
  </body>
</html>
