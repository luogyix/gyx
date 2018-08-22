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

		<title>产品管理</title>

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
		var productClassStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>/confManager/productApp_queryProductClass',
				root   : 'field1',
				fields:['lableid','lablename','updtype']
			});
		var appListStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>/confManager/productApp_queryProductClass',
				root   : 'field1',
				fields:['lableid','lablename','updtype'],
				autoLoad:true,
				listeners:{
					'load':function(){
						for(var i=0;i<appListStore.getCount();i++){
							if(appListStore.getAt(i).data.lableid == "0"){
								appListStore.removeAt(i)
							}
							if(appListStore.getAt(i).data.lableid == "1"){
								appListStore.removeAt(i)
							}
						}
					}
				}
			});
		
		var appListproductStore = new Ext.data.SimpleStore({ 
			data:[["0","0"],["1","1"],["2","2"],["3","3"],["4","4"],["5","5"],["6","6"],["7","7"],["8","8"],["9","9"],["10","10"],["11","11"]],
			fields : ['productTypeID','productTypeName']
		});
		var updtypeStore= new Ext.data.SimpleStore({ 
			data:[["01","1-手动"],["02","2-自动"]],
			fields : ['updtypeID','updtypeName']
		});
		
		productClassStore.load({params:{'branch':'<%=branch%>'}});
		appListStore.load({params:{'branch':'<%=branch%>'}});
		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			//-------------------------------------------------------------------------编号弹出窗 开始				
				var systemParamForm = new Ext.FormPanel({
					labelWidth: 75,
					labelAlign : 'right',
					autoScroll:true, //内容溢出自动滚动  
					//bodyStyle:'padding:5px 5px 0',
					//defaults: {width: 210},
			        //defaultType: 'numberfield',
			        frame : false,
			        items: [{
		                xtype: 'grid',
		                //title: '类别列表',
		                height: 385,
		                //autoHeight : true, 
						name:'productClassGrid',
						id:'productClassGrid',
						store: productClassStore,
						autoScroll :true,
						viewConfig: {forceFit: false},
						columns: [
						  	new Ext.grid.RowNumberer({width:30}),
						  	//'systemParam_id','systemParam_name','systemParam_note','branch','systemParam_flag'
				            {header: '编号类别 ', width: 80, dataIndex: 'lableid',renderer:function(value){
								value = value.toString();
          					 	return getDictValue(value,productClassStore,'lableid','lableid');//翻译字段方法
       						 }},
				            {header: '类别名称', width: 150, dataIndex: 'lablename'},
				            {header: '维护方式', width: 100, dataIndex: 'updtype',renderer:function(value){
								return value=='01'?'手动':'自动';
								}}
				        ],
				        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
						tbar: new Ext.Toolbar({
							id : 'tool',
							items: [{
				    			text : '<b>新增类别</b>',
				    			icon:'images/49.png',
				    			tooltip : '<b>提示</b><br/>创建新参数',
				    			handler : function(b,e){
				    				productClassAddForm.getForm().reset();
				    				Ext.getCmp('operateMode').setValue('add');
				    				productClass_add_window.setTitle('<span style="font-weight:normal">新增类别</span>');
				    				productClass_add_window.show();
				    			}
				    		},{
				    			text : '<b>删除类别</b>',
				    			icon:'images/49.png',
				    			tooltip : '<b>提示</b><br/>删除所选参数',
				    			handler : function(b,e){
				    				var record = Ext.getCmp('productClassGrid').getSelectionModel().getSelected();
				    				if (Ext.isEmpty(record)) {
				    					Ext.Msg.alert('提示', '请先选中要删除的类别!');
				    					return;
				    				}
				    				Ext.Msg.confirm('提示','确定要删除选中的类别吗?',function(btn, text){
										if (btn == 'yes') {
											var submitdata = {};
											submitdata['lableid'] = record.get('lableid');
											if(submitdata['lableid']=="0"||submitdata['lableid']=="1" || submitdata['lableid']=="2"){
												Ext.Msg.alert('提示', '该产品类别不可删除！');
												return;
											}
											requestAjax('<%=basePath%>confManager/productApp_deleteProductClass',submitdata,function(sRet){
												Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
													productClassStore.load({params:{'branch':'<%=branch%>'}});
												});
											});
										}
									});
				    			}
				    		}]
						})
		            }]
				});
				
				//弹出的属性框
				var productClassWindow = new Ext.Window({
					layout : 'fit', // 设置窗口布局模式
					width: 400,
				    height : 300,
				    resizable: false,
				    id:'systemParamWindow',
				    draggable : true,
				    closeAction : 'hide',
				    closable : false, 
				    modal : true, //无法失去焦点
					title : '<span style="font-weight:normal">产品类别配置弹出窗</span>', // 窗口标题
					collapsible : false, // 是否可收缩
					titleCollapse : false,
					maximizable : false, // 设置是否可以最大化
					buttonAlign : 'right',
					border : false, // 边框线设置
					animCollapse : true,
					pageY : document.body.clientHeight/2-300/2, // 页面定位Y坐标
					pageX : document.body.clientWidth / 2 - 400 / 2, // 页面定位X坐标
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [systemParamForm], // 嵌入的表单面板
					buttons : [{
						text : '关闭', // 按钮文本
						handler : function() { // 按钮响应函数
							productClassWindow.hide();
						}
					}]
				});
				var productClassAddForm = new Ext.FormPanel({
					labelWidth: 60,
					labelAlign : 'right',
					bodyStyle:'padding:5px 5px 0',
			        defaultType: 'textfield',
			        frame : false,
			        items: [{
		                fieldLabel: '编号类别',
		                xtype:'combo',
		                name:'lableid',
		                forceSelection: true ,
		                id:'lableidNo',
		                store:appListproductStore, 
		                displayField:'productTypeName',
		                valueField:'productTypeID',
		                allowBlank : false,
		                blankText:'请选择编号类别',
		                hiddenName:'lableid', 
		                mode:'local',
		                triggerAction:'all',
		                lazyInit:false,
		                editable:false,
		                width:140
		            },{
		                fieldLabel: '类别名称',
		                name:'lablename',
		                id:'lablename',
		                blankText:'请输入类别名称',
		                maxLength : 50,
		                maxLengthText : '长度不能大于50位',
		                allowBlank : false,
		                width:140
		            },{
		            	forceSelection: true ,
		            	fieldLabel: '维护方式',
		                id:'updtypeNo',
		                name:'updtype',
		                xtype:'combo',
		                blankText:'请选择维护方式',
		                allowBlank : false,
		                hiddenName:'updtype',
		                mode:'local',
		                triggerAction:'all',
		                lazyInit:false,
		                store:updtypeStore, 
		                displayField:'updtypeName',
		                valueField:'updtypeID',
		                editable:false,
		                width:140
		            },{
						id : 'operateMode',
						name : 'operateMode',
						hidden : true
					}]
				});
				var productClass_add_window = new Ext.Window({
					layout : 'fit', // 设置窗口布局模式
					width: 250,
				    height : 150,
				    resizable: false,
				    id:'productClass_add_window',
				    draggable : true,
				    closeAction : 'hide',
				    closable : false, 
				    modal : true, //无法失去焦点
					title : '<span style="font-weight:normal">新增类别</span>', // 窗口标题
					collapsible : false, // 是否可收缩
					titleCollapse : false,
					maximizable : false, // 设置是否可以最大化
					buttonAlign : 'right',
					border : false, // 边框线设置
					animCollapse : true,
					pageY : document.body.clientHeight/2-150/2, // 页面定位Y坐标
					pageX : document.body.clientWidth / 2 - 250 / 2, // 页面定位X坐标
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [productClassAddForm], // 嵌入的表单面板
					buttons : [{ // 窗口底部按钮配置
						text : '提交', // 按钮文本
						id:'btn_go',
						handler : function() { // 按钮响应函数
								if (!productClassAddForm.form.isValid()) {
									return;
								}
								var array=productClassAddForm.getForm().getFieldValues();
								var mode = Ext.getCmp('operateMode').getValue();
								if (mode == 'add'){
									//通知后台的方法.添加
									Ext.Ajax.request({
										url: '<%=basePath%>confManager/productApp_addProductClass',  
										params: {strJson:Ext.encode(array)},
										success:function(response,opt){
											var responseText = response.responseText.toString().substring(response.responseText.toString().indexOf("<body>")+6,response.responseText.toString().indexOf("</body>"));
											if(responseText==''){
												Ext.MessageBox.alert('提示',responseText);
												return;
											}
											productClass_add_window.hide();
											Ext.MessageBox.alert('提示', '添加类别成功');
											productClassStore.load({params:{'branch':'<%=branch%>'}});
										}
									});
								}else if (mode == 'edit'){
									//通知后台的方法.修改
									Ext.Ajax.request({
										url: '<%=basePath%>confManager/systemParameter_editSystemParameter',  
										params: {strJson:Ext.encode(array)},
										success:function(response,opt){
											var responseText = response.responseText;
											if(responseText.indexOf('false')!=-1){
												Ext.MessageBox.alert('提示', '此机构已存在默认的参数');
												return;
											}
											productClass_add_window.hide();
											Ext.MessageBox.alert('提示', '修改类别成功');
											productClassStore.load({params:{'branch':'<%=branch%>','parameter_flag':'01'}});
										}
									});
								}else if(mode == 'copy'){
									//复制系统
									requestAjax('<%=basePath%>confManager/systemParameter_copySystemParameter',Ext.encode(array),function(sRet){
										Ext.MessageBox.alert('提示','复制成功',function(id){
											productClass_add_window.hide();
											productClassStore.load({params:{'branch':'<%=branch%>','parameter_flag':'01'}});
										});
									});
								}
						}
					},{ // 窗口底部按钮配置
						text : '关闭', // 按钮文本
						handler : function() { // 按钮响应函数
							productClass_add_window.hide();
						}
					}]
				});
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '产品管理', 120, 1,
				[
				{rowIndex:0, field:{xtype : 'combo', 		name:'LABLEID', allowBlank : true, editable:false,fieldLabel:'产品类别',	hiddenName:'LABLEID',  forceSelection: true , 	blankText : '', store:appListStore, displayField:'lablename', valueField:'lableid'	}}//用
				],
				[
				{iconCls: "x-image-query", 	 id:"01",	text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-user_add",  id:"02",		text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-user_edit", 	 id:"03",	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-user_delete",  id:"04",	text:'<s:text name="common.button.deleterecord"/>'},//删除
				{iconCls: "x-image-application_form_add",id:'05',text:'产品类别配置'}
				],
				onButtonClicked
			);
			conditionPanel.open();
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/configManager/productApp_queryProducts',
				['PDFVISON','PRODUCTID','LABLEID','PRODUCTNAME','CRTDATETIME','ISHOT','PDFNAME','DETAILSPIC1','DETAILSPIC2','DETAILSPIC3','PRODUCTDETAILS','PAYTYPE','CREATUSER','BRANCH','ALTDATETIME','PRODUCTTEXT','PRODUCTDATA','EXT3'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'版本号',	hidden:true,	dataIndex:'PDFVISON',		width:105},
				{header:'产品编号',		dataIndex:'PRODUCTID',		width:105},
				{header:'产品类别',			dataIndex:'LABLEID',	width:100,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,appListStore,'lableid','lablename');//翻译字段方法
       			 }},
				{header:'产品名称',			dataIndex:'PRODUCTNAME',	width:100},
				{header:'产品创建日期',			dataIndex:'CRTDATETIME',	width:100},
				{header:'重命名缩略图名称 ',	hidden:true,		dataIndex:'PDFNAME',	width:150},
				{header:'缩略图名称 ',			dataIndex:'DETAILSPIC3',	width:150},
				{header:'全部详情图片名称 ',			dataIndex:'DETAILSPIC1',	width:350},
				{header:'详情图片重命名后图片名称',	hidden:true,		dataIndex:'DETAILSPIC2',	width:150},
				{header:'产品详情',			dataIndex:'PRODUCTDETAILS',	width:150},
				{header:'版本标识 ',			dataIndex:'EXT3',	width:100},
				{header:'是否热销',dataIndex:'ISHOT',width:90,renderer:function(value){
					return value=='1'?'热销':'非热销';}},
				{header:'产品简介 ',			dataIndex:'PRODUCTTEXT',	width:200},
				{header:'产品特点 ',			dataIndex:'PRODUCTDATA',	width:200},
				{header:'维护用户',			dataIndex:'CREATUSER',width:100},
				{header:'维护机构',			dataIndex:'BRANCH',	width:100},
				{header:'维护日期',			dataIndex:'ALTDATETIME',	width:200}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			/*添加系统用户*/
			addwindow = new SelfFormWindow('recordAddWindow', '添加产品信息', 500, 420, 200, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', 	name:'BRANCH', id:'branch_add',	allowBlank : false, fieldLabel:'机构号',	blankText : '请输入机构号',		maxLength : 12,maxLengthText : '长度不能大于20位',readOnly:true	}},
				{colIndex:1, field:{xtype : 'combo', 		name:'LABLEID', id:'lableid_add',allowBlank : false, fieldLabel:'产品类别',	hiddenName:'LABLEID',editable:false,  forceSelection: true , 	blankText : '请选择产品类别', store:appListStore, displayField:'lablename', valueField:'lableid'	}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'PRODUCTID', id:'productid_add',	allowBlank : false, fieldLabel:'产品编号',	blankText : '请输入产品编号',	regex:/^\d{1,20}$/	,regexText:'请输入20位以内整数',maxLength : 20,maxLengthText : '长度不能大于20位',readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', 	name:'PRODUCTNAME', 		allowBlank : false, fieldLabel:'产品名称',	blankText : '请输入产品名称',		maxLength : 60,maxLengthText : '长度不能大于60位'	}},
				{colIndex:1, field:{xtype : 'textarea', 	name:'PRODUCTTEXT', 		allowBlank : true, fieldLabel:'产品简介',	blankText : '请输入产品简介',		maxLength : 1000,maxLengthText : '长度不能大于1000位'	}},
				{colIndex:0, field:{xtype : 'textarea', 	name:'PRODUCTDATA', 		allowBlank : true, fieldLabel:'产品特点',	blankText : '请输入产品特点',		maxLength : 1000,maxLengthText : '长度不能大于1000位'	}},
				{colIndex:0, field:{xtype : 'textfield',	name:'EXT3', 	fieldLabel:'版本标识',	allowBlank : true,blankText:'输入格式:n.n.n', regex:/^\d{1,3}\.\d{1,3}\.\d{1,3}$/,regexText:'输入格式:n.n.n',maxLength : 11,maxLengthText : '长度不能大于11位',blankText:'版本标识不能为空'}},
				{colIndex:1, field:{xtype : 'textfield', 	name:'PDFNAME', 	hidden:true, fieldLabel:'缩略图名称', 	blankText : '请上传图片',		maxLength : 200,maxLengthText : '长度不能大于200位',readOnly:true	 }},
				{colIndex:1, field:{xtype : 'textfield', 	name:'DETAILSPIC3',fieldLabel:'缩略图', 	blankText : '请上传图片',readOnly:true	 }},
				{colIndex:0, field:{xtype : 'textarea', 	name:'DETAILSPIC1', fieldLabel:'全部详情图片名称', 	blankText : '请上传图片',	height:50,	maxLength : 1200,maxLengthText : '1长度不能大于200位',readOnly:true	 }},
				{colIndex:0, field:{xtype : 'textfield', 	name:'DETAILSPIC2',	hidden:true, fieldLabel:'详情图片名称新',		maxLength : 1200,maxLengthText : '长度不能大于1200位',readOnly:true	 }},
				{colIndex:1, field:{xtype : 'textarea', 	name:'PRODUCTDETAILS', 		allowBlank : true, fieldLabel:'产品详情',	blankText : '请输入产品详情',width:200,	height:50,	maxLength : 1000,maxLengthText : '长度不能大于1000位'	}},
				{colIndex:0, field:{xtype : 'checkbox',		hideLabel : true,     name:'ISHOT',id:'ishot_add', boxLabel:'设为热销',hiddenName:'ISHOT'}}
				],
				[
					
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}},
					{text:'上传缩略图', formBind:true,handler: function(){
						var form = new Ext.form.FormPanel({  
							baseCls : 'x-plain',  
							labelWidth : 70,  
							fileUpload : true,  
							defaultType : 'textfield',  
							items : [{  
								xtype : 'textfield',  
						        fieldLabel : '选择图片',  
						        name : 'upload',  
						        id : 'upload',  
						        inputType : 'file',  
						        anchor : '95%' // anchor width by percentage  
							},
							{
								hidden:true,
							    xtype:'textfield', 
								name:'upload_productid',
						        anchor : '95%',
						        id:'upload_productid'
							},
							{
								hidden:true,
							    xtype:'textfield', 
								name:'upload_lableid',
						        anchor : '95%',
						        id:'upload_lableid'
							}
							]
						});
				        var window = new Ext.Window({  
				        	width :450,  
				    		height : 100,  
				            closeAction:'close',
				           	modal : true,//屏蔽其他组件,自动生成一个半透明的div  
				            buttonAlign : 'right',//按钮的对齐方式  
				            defaultButton : 0,//默认选择哪个按钮  
				            items:form,
				            buttons : [{  
				                text : '确定',  
				                handler : function() { 
				            	   Ext.getCmp('upload_productid').setValue(Ext.getCmp('productid_add').getValue());
				            	   Ext.getCmp('upload_lableid').setValue(Ext.getCmp('lableid_add').getValue());
							       if (form.form.isValid()) {  
							        if(Ext.getCmp('upload').getValue() == ''){  
							         Ext.Msg.alert('溫馨提示','请选择上传的图片..');  
							         return;  
							        }  
							        Ext.MessageBox.show({  
							           title : '请稍候....',  
							           msg : '图片正在上传中....',  
							           progressText : '',  
							           width : 300,  
							           progress : true,  
							           closable : false,  
							           animEl : 'loding'  
							        });  
							        form.getForm().submit({  
							            url : '<%=basePath%>/confManager/productApp_upLoadFile',  
							            method : 'POST',  
							            success : function(form, action) {  
									            var value=Ext.getCmp('upload').getValue();
									            var val=value.split("\\");
									           	var field=addwindow.getForm().findField('DETAILSPIC3');
												field.setValue(val[val.length-1]);//只有图片名称
												//field.setValue(value);显示全路径
											    Ext.Msg.alert('成功','恭喜！图片上传成功！');  
											    window.close();
							         	},  
								        failure : function(form, action) {  
								            Ext.Msg.alert('错误','图片上传失败，请重新操作！');  
								        }  
							        })  
							       }  
							     }  
				            },{  
				                text : '取消' , 
				                handler:function(){
				                	window.close();
				                }
				            }]  
				        });  
				        window.show();  
					}},
					{text:'上传详情图片', formBind:true,handler: function(){
						 var dialog = new Ext.ux.UploadDialog.Dialog({    
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
						             url: '<%=basePath%>/confManager/productApp_uploadDetailsPic',
						             post_var_name:'uploadFiles',  
						             base_params:{'upload_productid':Ext.getCmp('productid_add').getValue(),
						             			 'uploaddetails_lableid':Ext.getCmp('lableid_add').getValue()},  
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
							if(files.length > 5){
						 		Ext.Msg.alert('溫馨提示','上传图片最多为5张');  
						 		uploadflag = true;
							    return false;  
						 	}
						 });
						 dialog.on('fileadd', function(dialog,filename){
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
						 	addwindow.getForm().findField('DETAILSPIC1').setValue(savefilesName.toString().replace(/,/g,''));
						 	addwindow.getForm().findField('DETAILSPIC2').setValue(savefiles.toString()+",");
						 }); 
						 dialog.show(); 
					}}
				]
			);

		/**
			 * @createCheckBoxValue
			 * @Description:判断checkbox数据
			 */
            function createCheckBoxValue(check,data){
            	if(data[check]==undefined){
            		data[check] = '0';
				}
				if(data[check]=='on'){
            		data[check] = '1';
				}
				if(data[check]=='off'){
            		data[check] = '0';
				}
            }
            /*生成产品编号*/
			function getNewproductid() {
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

                var productid = date.getFullYear() + month + strDate+hour+minute+second+num;
                return productid;
            }
			/*主窗口按钮设置*/
			function onButtonClicked(btn_index){
				switch(btn_index){
				case 0://查询
					var query_obj = conditionPanel.getFields();
					//query_obj.unitlist = null;
					var value=query_obj["LABLEID"];
					if(null==value||value===undefined||value.length==0){
							Ext.MessageBox.alert('信息提示','请选择产品类型!');
							return;
						}
					pagequeryObj.queryPage(query_obj);
				break;
				case 1://添加
					var submitdata = pagequeryObj.getSelectedObjects(['func_id']);
					addwindow.open();
					Ext.getCmp('branch_add').setValue('<%=branch%>');
					Ext.getCmp('productid_add').setValue(getNewproductid());
				break;
				case 2://修改
					editwindow = new SelfFormWindow('recordEditWindow', '编辑功能', 500, 420, 200, 2,
						[
							{colIndex:0, field:{xtype : 'textfield', hidden:true,	name:'PDFVISON',fieldLabel:'版本号'}},
							{colIndex:0, field:{xtype : 'textfield', hidden:true,	name:'CRTDATETIME',fieldLabel:'产品创建日期'}},
							{colIndex:0, field:{xtype : 'textfield', 	name:'BRANCH',	allowBlank : false, fieldLabel:'机构号',	blankText : '请输入机构号',		maxLength : 12,maxLengthText : '长度不能大于20位',readOnly:true	}},
							{colIndex:1, field:{xtype : 'combo', 		name:'LABLEID', allowBlank : false, fieldLabel:'产品类别',	hiddenName:'LABLEID',  forceSelection: true , 	blankText : '', store:appListStore, displayField:'lablename', valueField:'lableid',readOnly:true	}},
							{colIndex:1, field:{xtype : 'textfield', 	name:'PRODUCTID',   	allowBlank : false, fieldLabel:'产品编号',	blankText : '请输入产品编号',		maxLength : 60,maxLengthText : '长度不能大于60位',readOnly:true	}},
							{colIndex:0, field:{xtype : 'textfield', 	name:'PRODUCTNAME', 		allowBlank : false, fieldLabel:'产品名称',	blankText : '请输入产品名称',		maxLength : 60,maxLengthText : '长度不能大于60位'	}},
							{colIndex:1, field:{xtype : 'textarea', 	name:'PRODUCTTEXT', fieldLabel:'产品简介',maxLength : 1000,maxLengthText : '长度不能大于1000位'	}},
							{colIndex:0, field:{xtype : 'textarea', 	name:'PRODUCTDATA', fieldLabel:'产品特点',maxLength : 1000,maxLengthText : '长度不能大于1000位'	}},
							{colIndex:0, field:{xtype : 'textfield',    name:'EXT3', 	fieldLabel:'版本标识',regex:/^\d{1,3}\.\d{1,3}\.\d{1,3}$/,regexText:'输入格式:n.n.n', maxLength : 11,maxLengthText : '长度不能大于11位'}},
							{colIndex:1, field:{xtype : 'textfield', 	name:'PDFNAME', 	hidden:true, fieldLabel:'缩略图名称',maxLength : 200,maxLengthText : '长度不能大于200位',readOnly:true	 }},
							{colIndex:1, field:{xtype : 'textfield', 	name:'DETAILSPIC3', 	 fieldLabel:'缩略图名称',maxLength : 200,maxLengthText : '长度不能大于200位',readOnly:true,emptyText:'建议上传缩略图，否则此处为空'	 }},
							{colIndex:0, field:{xtype : 'textarea', 	name:'DETAILSPIC1', 	 fieldLabel:'全部详情图片名称', height:50,	blankText : '请上传图片',emptyText:'建议上传详情图，否则此处为空',		maxLength : 200,maxLengthText : '长度不能大于200位',readOnly:true	 }},
							{colIndex:0, field:{xtype : 'textfield', 	name:'DETAILSPIC2',	 hidden:true,fieldLabel:'详情图片二',maxLength : 200,maxLengthText : '长度不能大于200位',readOnly:true	 }},
							{colIndex:1, field:{xtype : 'textarea', 	name:'PRODUCTDETAILS', fieldLabel:'产品详情',width:200,	height:50,	maxLength : 1000,maxLengthText : '长度不能大于1000位'	}},
							{colIndex:0, field:{xtype : 'checkbox',		hideLabel : true,     name:'ISHOT', boxLabel:'设为热销',hiddenName:'ISHOT'}}
						],
						[
							{text:'<s:text name="common.button.edit"/>', formBind:true,handler : oneditclicked},
							{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close();}},
							{text:'修改缩略图', formBind:true,handler: function(){
								var form = new Ext.form.FormPanel({  
								     baseCls : 'x-plain',  
								     labelWidth : 70,  
								     labelHeight: 150,  
								     fileUpload : true,  
								     defaultType : 'textfield',  
								     items : [{  
								        xtype : 'textfield',  
								        fieldLabel : '选择图片',  
								        name : 'upload',  
								        id : 'upload',  
								        inputType : 'file',  
								        anchor : '95%' // anchor width by percentage  
								     },
									{
										hidden:true,
									    xtype:'textfield', 
										name:'upload_productid',
								        anchor : '95%',
								        id:'upload_productid'
									},
									{
										hidden:true,
									    xtype:'textfield', 
										name:'upload_lableid',
								        anchor : '95%',
								        id:'upload_lableid'
									}
								     ]  
								});  
				        var window = new Ext.Window({  
				            //layout : 'fit',//设置window里面的布局  
				             width :450,  
				    		 height : 100,  
				            //关闭时执行隐藏命令,如果是close就不能再show出来了  
				            closeAction:'close',  
				            //draggable : false, //不允许窗口拖放  
				            //maximizable : true,//最大化  
				            //minimizable : true,//最小话  
				            //constrain : true,//防止窗口超出浏览器  
				            //constrainHeader : true,//只保证窗口顶部不超出浏览器  
				            //resizble : true,//是否可以改变大小  
				            //resizHandles : true,//设置窗口拖放的方式  
				           	modal : true,//屏蔽其他组件,自动生成一个半透明的div  
				            //animateTarget : 'target',//弹出和缩回的效果  
				            //plain : true,//对窗口进行美化,可以看到整体的边框  
				            buttonAlign : 'right',//按钮的对齐方式  
				            defaultButton : 0,//默认选择哪个按钮  
				            items:form,
				            buttons : [{  
				                text : '确定',  
				                handler : function() {  
				            	Ext.getCmp('upload_productid').setValue(editwindow.getForm().findField('PRODUCTID').getValue());
				            	Ext.getCmp('upload_lableid').setValue(editwindow.getForm().findField('LABLEID').getValue());
							       if (form.form.isValid()) {  
							        if(Ext.getCmp('upload').getValue() == ''){  
							         Ext.Msg.alert('溫馨提示','请选择上传的文件..');  
							         return;  
							        }  
							        Ext.MessageBox.show({  
							           title : '请稍候....',  
							           msg : '图片正在上传中....',  
							           progressText : '',  
							           width : 300,  
							           progress : true,  
							           closable : false,  
							           animEl : 'loding'  
							        });  
							        form.getForm().submit({  
							            url : '<%=basePath%>/confManager/productApp_upLoadFile',  
							            method : 'POST',  
							            success : function(form, action) {  
									            var value=Ext.getCmp('upload').getValue();
									            var val=value.split("\\");
									            var field=editwindow.getForm().findField('DETAILSPIC3');
												field.setValue(val[val.length-1]);
												//field.setValue(value);	
											    Ext.Msg.alert('成功','恭喜！图片修改成功！');  
											    window.close();
							         	},  
								        failure : function(form, action) {  
								            Ext.Msg.alert('错误','图片修改失败，请重新操作！');  
								        }  
							        })  
							       }  
							     }  
				            },{  
				                text : '取消' , 
				                handler:function(){
				                	window.close();
				                }
				            }]  
				        });  
				        window.show();  
					}},{text:'修改详情图片', formBind:true,handler: function(){
							var dialog = new Ext.ux.UploadDialog.Dialog({    
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
						             url: '<%=basePath%>/confManager/productApp_uploadDetailsPic',
						             post_var_name:'uploadFiles',  
						             base_params:{'upload_productid':editwindow.getForm().findField('PRODUCTID').getValue(),
						             			 'uploaddetails_lableid':editwindow.getForm().findField('LABLEID').getValue()},  
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
						 	if(files.length > 5){
						 		Ext.Msg.alert('溫馨提示','上传图片最多为5张');  
						 		uploadflag = true;
							    return false;  
						 	}
						 });
						 dialog.on('fileadd', function(dialog,filename){
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
						 	for (var i = 0; i < files.length; i++) { 
						 		var sn = files[i].substring(files[i].lastIndexOf("\\")+1)+'\n';
						 		savefilesName.push(sn);
						 		/* var sn = files[i]+'\n';
						 		savefilesName.push(sn); */
						 	}
						 	savefiles.push(data.filename);
						 	editwindow.getForm().findField('DETAILSPIC1').setValue(savefilesName.toString().replace(/,/g,''));
						 	editwindow.getForm().findField('DETAILSPIC2').setValue(savefiles.toString()+",");
						 }); 
						 dialog.show(); 
						 
					}}
						]
					);
					var records = pagequeryObj.getSelectedRecords();
					
					/* if(records !== undefined){
						records[0].data.DETAILSPIC3 = "";
						records[0].data.DETAILSPIC1 = "";
					} */
					
					if(records === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
						//无法修改非本行机构
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.BRANCH!=<%="'"+branch+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
					    		return;
					    	}
					    }
						//var recorddata = records[0];
						
						editwindow.open();
						//alert("unitid="+records[0].data.unitid);
						editwindow.updateFields(records[0]);
					}
				break;
				case 3://删除
					var submitdata = pagequeryObj.getSelectedObjects(['BRANCH','PRODUCTID','LABLEID','PDFNAME','DETAILSPIC1','DETAILSPIC2','DETAILSPIC3']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						//无法删除非本行机构
					    for(var i=0;i<submitdata.length;i++){
					    	if(submitdata[i].BRANCH!=<%="'"+branch+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
					    		return;
					    	}
					    }
						
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/confManager/productApp_deleteProducts',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					}
				break;
				case 4://产品类别配置
				     productClassWindow.show();
				}
			}

			function onaddclicked(){
				var submitData = addwindow.getFields();
					//submitData.createuser=null;
					//submitData.createdate=new Date();
					createCheckBoxValue('ISHOT',submitData);
					requestAjax('<%=basePath%>/confManager/productApp_addProduct', submitData,
					function(sRet){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
							var query_obj = conditionPanel.getFields();
							//query_obj.unitlist = null;
							pagequeryObj.queryPage(query_obj);
						});
						addwindow.close();
					});
				/*
				addwindow.getForm().submit({
					waitMsg:'正在导入请稍候',
					waitTitle:'提示',
					url:'<%=basePath%>/confManager/productApp_addProduct',
					method:'POST',
					success:function(form,action){
					},
					failure:function(form,action){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>',action.result.msg,function(){});
					}
				});
				*/
			}

			function oneditclicked(){
				var field=editwindow.getForm().findField('PDFNAME');
				//alert(field.getValue())
				var submitData = editwindow.getFields();
				createCheckBoxValue('ISHOT',submitData);
				requestAjax('<%=basePath%>/confManager/productApp_editProduct', submitData,
					function(sRet) {
						Ext.MessageBox.alert(
								'<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',
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
		}
		</script>

	</head>

	<body scroll="no">
		<div id="queryConditionPanel"></div>
		<div id="pageQueryTable"></div>
		<div id="recordAddWindow"></div>
		<div id="recordEditWindow"></div>
		<div id="roleAssignWindow"></div>
		<div id="unitname"></div>
		<div id="viewWindow"></div>
		<div id="hello"></div>
	</body>
</html>
