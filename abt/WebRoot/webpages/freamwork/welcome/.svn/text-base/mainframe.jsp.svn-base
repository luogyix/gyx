<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	com.agree.framework.web.form.administration.User user = (com.agree.framework.web.form.administration.User) (request
			.getSession().getAttribute("logonuser"));
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String username = null;
	String unidName = null;
	String tellerNo = null;
	String unitId = null;
	String unitCheckCode = null;
	String logontime = null;
	String usercode = null;
	String date = null;
	String password = null;
	
	if (user != null) {
		username = user.getUsername();
		tellerNo = user.getTellerno();
		unidName = user.getUnit().getUnitname();
		unitCheckCode = user.getUnit().getCheckcode();
		unitId = user.getUnit().getUnitid().toString();
		logontime = user.getLogintime();
		usercode = user.getUsercode();
		date = logontime;
		password = user.getPasswd();
	}
	
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>网点管理平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		
		<link rel="shortcut icon" href="<%=basePath%>images/application/favicon.ico">
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-patch.css"  />
		<link rel="stylesheet" type="text/css" href="selfcss/common.css" />
		<link rel="stylesheet" type="text/css" href="selfcss/notice.css" />
		<link rel="stylesheet" type="text/css" href="selfcss/master.css" />
		
		<!-- <style type="text/css">
		BODY {
			FONT-SIZE: 12px;
			FONT-FAMILY: Tahoma
		}
		
		BODY {
			PADDING-RIGHT: 0px;
			PADDING-LEFT: 0px;
			PADDING-BOTTOM: 0px;
			MARGIN: 0px;
			PADDING-TOP: 0px
		}
		
		.guide {
			PADDING-RIGHT: 0.5em;
			PADDING-LEFT: 0.5em;
			PADDING-BOTTOM: 0.5em;
			PADDING-TOP: 0.5em;
			background-image: url('<%=basePath%>images/application/toptitle.jpg');
			background-repeat: no-repeat;
			background-position: left;
		}
		
		.guide {
			text-align: right;
		}
		
		.guide 	a {
			text-decoration: none;
			padding: 1px 2px 1px 2px;
			margin: 0px 0px;
			border: 1px solid #FFFFFF;
		}
		
		.s1 {
			font-size: 9pt;
			COLOR: #008000
		}
		
		.s2 {
			font-size: 8pt;
			COLOR: #fa891b
		}
		</style>
		<style type="text/css">
		
       		.ext-ie .x-tree .x-panel-body {position: relative;}  

			.ext-ie .x-tree .x-tree-root-ct {position: absolute;} 
			.x-panel-header {
				    color:#e7f1fb;
					font-weight:bold; 
				    font-size: 12px;
				    font-family: tahoma,arial,verdana,sans-serif;
				    border-color:#99bbe8;
				    background-image: url(extjs/resources/images/default/panel/white-top-bottom1.gif)!important;
					background-repeat:repeat-x;
				}
				.x-panel-header {
				    overflow:hidden;
				    zoom:1;
				    padding:5px 3px 4px 5px;
				    border:1px solid;
				    line-height: 15px;
				    background: transparent repeat-x 0 -1px;
				}
				.x-panel-header {
				    overflow:hidden;
				    zoom:1;
				}
				.x-accordion-hd {
					color:#153e7c;
				    font-weight:normal;
				    background-image: url(extjs/resources/images/default/panel/light-hd1.gif);
					background-repeat:repeat-x;
				}
				.x-accordion-hd {
					padding-top:4px;
					padding-bottom:3px;
					border-top:0 none;
				    background: transparent repeat-x 0 -72px;
				}
     	</style> -->
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.js"></script>
		<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="selfjs/common/TabCloseMenu.js"></script>
		<script type="text/javascript" src="extjs/ux/LockingGridView.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/Notification.js"></script>
		<script type="text/javascript" src="selfjs/common/ajax-pushlet-client.js"></script>
		<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
		<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
		<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
		<script type="text/javascript">
			var pswd = '<%=password%>';
			var pagereturn = ${actionresult};//获取服务器 输出的数据 actionresult
		    if('<%=username%>'==null){
                 alert("对不起，您的会话已经失效，请重新登录！");
                 //获取当前页面的地址，并重定向到指定的页面
                 window.location.href='<%=basePath%>webpages/freamwork/welcome/Exits.jsp?si=1';
            }
            var branch =pagereturn.field3;//所属机构id
		    var message = document.getElementById('message');
		    var task_flag = true;
		    var template_model_flag = false;
		    var task = {
	        	run: function(){
	        		if(task_flag){
	        			document.getElementById('msg_icon').src='images/icon-1-1-1.png';
	        			task_flag = false;
	        		}else{
	        			document.getElementById('msg_icon').src='images/icon-1.png';
	        			task_flag = true;
	        		}
	        	},
	        	interval: 1000
	        };
		    var dataStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>confManager/errorMessage_loadMessage',
				root: 'field1',
				//dataStore.load({params:temp}); temp = {}; temp[''] = Ext.getCmp('').getValue();
				fields : ['message'],
				listeners : {  
					load : function(store,records){//这两个参数 没被初始化 value=undefined.
						//Ext.getCmp('default_flag').setValue(store.getAt(0).data['default_flag']);
						//Ext.getCmp('def_show_notify_threshold').setValue(store.getAt(0).data['def_show_notify_threshold']);
						
						if(records.length==0){
							//message.innerHTML = "";
							Ext.TaskMgr.stop(task);
							document.getElementById('msg_icon').src='images/icon-1.png';
							//document.getElementById('message').src='images/icon-1-1.png';
						}else{
							//message.innerHTML = "<font color='red'>消息数:("+ records.length +")</font>";
							//time触发闪动
							Ext.TaskMgr.start(task);
							//document.getElementById('message').src='images/icon-1-1-1.png';
						}
					}
				}
		    });
		    var templateStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>confManager/template_queryTemplateInfo4ComboBox',
				root: 'field1',
				//dataStore.load({params:temp}); temp = {}; temp[''] = Ext.getCmp('').getValue();
				fields : ['template_id','template_name','template_note','branch','template_flag'],
				listeners : {
					load : function(store,records){
						//alert('加载成功');
						//store.add({'template_id':null,'template_name':'常规配置模式','branch':<%=unitId%>});
					}
				}
		    });
		    
			var workingTabPanel;
			
			/**
			 * 点击修改密码触发函数
			 */
			function updateUserInit() {
				
				var tab = workingTabPanel.add({
					'id':'tra_xiugaimima',
					'title':'修改密码',
					closable:true, 
					//通过html载入目标页，访问action，获得修改密码 页面
					html:'<iframe name="iftra_xiugaimima" scrolling="auto" frameborder="0" width="100%" height="100%" src="<%=basePath%>/admin/systemUserPass_loadPage"></iframe>'
				});
				workingTabPanel.setActiveTab(tab);
			}
			//打开模板窗口
			//点击右上角的 正常模式 图片 时弹出打开模版窗口
			function openTemplateWindow() {
				Ext.getCmp('template_window').show();
			}
			
			//点击 退出系统图片 时 返回登录页面
			function UserExit(){
				requestAjax('<%=basePath%>/BasePackage/exitSystem_exit',{"name":"exit"} , function(sRet){
			   	 
				}); 
				window.location.href = './welcome';
			}
			var messageWindow;
			/**
			 * 进行变更上面的状态操作
			 */
			function queryMessage(){
				//store操作
				dataStore.load();
			}
			function showWindow(){
				var mydata = [
					{xtype: 'gridcolumn',dataIndex: 'message',header: '<div style="text-align:center">消息内容</div>',sortable: false}
				];
				messageWindow = new SelfFormWindowSetWidth('messageWindow','错误配置列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			layout:'column',
			   			items:[{
			       			columnWidth:1,
			       			items:[{
			       				xtype: 'grid',
			    				name:'messageGrid',
			    				id:'messageGrid',
			    				store: dataStore,
			    				viewConfig: {forceFit: true},
			    			    cm: new Ext.grid.ColumnModel(mydata),
			       				height:240,
			       				iconCls:'icon-grid',
			       				stripeRows : true,
			       				autoScroll:true,
			       				doLayout:function(){
			       					this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true));
			       				}
			       			}]
			   			}]
			   		}
			    }],[
					{text:'返回', 	handler: function(){messageWindow.close();}}
			    ]);
			    
				//这里要进行操作,执行另一个js
				messageWindow.open();
				queryMessage();
			}
			
			function onmouseover_template(){
				if(template_model_flag){
					document.getElementById('template_icon').src='images/icon-4-5.png';
				}else{
					document.getElementById('template_icon').src='images/icon-4-4.png';
				}
			}
			function onmouseout_template(){
				if(template_model_flag){
					document.getElementById('template_icon').src='images/icon-4-2.png';
				}else{
					document.getElementById('template_icon').src='images/icon-4.png';
				}
			}
			//消息按钮焦点离开时候的触发方法
			function onmouseout_message(){
				if(dataStore.getCount()==0){
					Ext.TaskMgr.stop(task);
					document.getElementById('msg_icon').src='images/icon-1.png';
				}else{
					Ext.TaskMgr.start(task);
				}
			}
			
			function showNotice(autoClose,prompt,title){
				if(autoClose){
					new Ext.ux.Notification({
										listeners: {
											'beforeshow': function(){
												//Sound.enable();
												//Sound.play('sound/notify.wav');
												//Sound.disable();
											}
										}
									}).showMessage(title,prompt,true); 
				
				}else{
					new Ext.ux.Notification({
										listeners: {
											'beforerender': function(){
												//Sound.enable();
												//Sound.play('notify.wav');
												//Sound.disable();
											}
										}
									}).showMessage(title,prompt,false); 
			
				}
			}
			
			
			
			Ext.onReady(function(){
				Ext.QuickTips.init(); 
				var result_user = Ext.util.JSON.decode('${actionresult}');
				Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
				var clientWidth = document.body.clientWidth;
				var treeData = result_user.field1;
				//var jsonMeta = {nodeId:'moduleid',parentNodeId:'parentmoduleid',nodeName:'modulename',nodeType:'moduletype', nodeHref:'moduleaction',leafField:'isleaf',nodeImage:'moduleImg'};
				var jsonMeta = {nodeId:'moduleid',parentNodeId:'parentmoduleid',nodeName:'modulename',nodeType:'moduletype', nodeHref:'moduleaction',leafField:'isleaf',nodeImage:'moduleImg',areaRcid:'areaRcid',asynchronousTag:'asynchronousTag'};
				var isPrivateMethod = true;
				
				var treeGenerator = new SelfTreeGenerator(treeData,jsonMeta,'<%=basePath%>',['','x-image-none',''],['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle'],isPrivateMethod);
				var tree = treeGenerator.generate(false,false,false,true,'WORKINGFRAME');
			//	var username = result_user.field1.username;
				var data= new Date();
				data= data.format('Y-m-d H:i:s');
			
				//弹出的属性框的form
				var templateForm = new Ext.FormPanel({
					labelWidth: 75,
					labelAlign : 'right',
					//bodyStyle:'padding:5px 5px 0',
					//defaults: {width: 210},
			        //defaultType: 'numberfield',
			        frame : false,
			        items: [{
		                xtype: 'grid',
		                //title: '模板列表',
		                height: 385,
		                //autoHeight : true, 
						name:'templateGrid',
						id:'templateGrid',
						store: templateStore,
						autoScroll :true,
						viewConfig: {forceFit: false},
						columns: [
						  	new Ext.grid.RowNumberer({width: 30}),
						  	//'template_id','template_name','template_note','branch','template_flag'
				            {header: '模板编号', width: 80, dataIndex: 'template_id', hidden:true},
				            {header: '模板名称', width: 100, dataIndex: 'template_name', sortable:true},
				            {header: '模板来源', width: 100, dataIndex: 'branch', sortable:true},
				            {header: '模板备注', width: 220, dataIndex: 'template_note', sortable:true},
				            {header: '模板影响模块', width: 350, dataIndex: 'template_flag', sortable:true,renderer:function(value){
				            	var values = value.split(',');
				            	var str = '';
				            	for ( var i = 0; i < values.length; i++) {
									switch (values[i]) {
									case 'qm':
										str += '排队机信息,';
										break;
									case 'window':
										str += '窗口,';
										break;
									case 'machineView':
										str += '排队机界面,';
										break;
									case 'queueingRules':
										str += '叫号规则,';
										break;
									case 'branchParam':
										str += '机构参数,';
										break;
									case 'managerCallNum':
										str += '大堂经理优先队列配置,';
										break;
									}
								}
				            	if(str.lastIndexOf(',')===str.length-1){
				            		str = str.substring(0,str.length-1);
				            	}
				      		 	return str;
				 			}}
				        ],
				        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
						tbar: new Ext.Toolbar({
							id : 'tool',
							items: [{
				    			text : '<b>切换模板状态</b>',
				    			icon:'images/49.png',
				    			id:'btn_template',
				    			tooltip : '<b>提示</b><br/>开关模板状态',
				    			handler : function(b,e){
				    				if(b.pressed){//true,这里要走一个方法
				    					Ext.Ajax.request({
											url: '<%=basePath%>confManager/template_configTemplateModel',  
											params: {'template_id':'normal','template_flag':''},
											success:function(response,opt){
												template_window.hide();
												Ext.Msg.alert('提示',response.responseText);
												b.toggle();//改变按键状态,改变文字
												template_model_flag = false;
												document.getElementById('template_icon').src='images/icon-4.png';
												document.getElementById('message').innerHTML = '';
												var tree = treeGenerator.generate(false, false, false, true, 'WORKINGFRAME');
												Ext.getCmp('menuTree').setRootNode(tree);
												var item0 = Ext.getCmp('center-panel').getItem('tab_welcome');
												Ext.getCmp('center-panel').removeAll();
												item0 = {
													id:'tab_welcome',
						                        	title: '欢迎',
						                        	closable:false,
						                        	html : '<iframe src="<%=basePath%>/webpages/freamwork/welcome/welcome1.jsp" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>',
									    			autoScroll:true
									    		};
												Ext.getCmp('center-panel').add(item0);
												Ext.getCmp('center-panel').setActiveTab('tab_welcome');
											}
										});
				    				}else{
				    					var record = Ext.getCmp('templateGrid').getSelectionModel().getSelected();
					    				if (Ext.isEmpty(record)) {
					    					Ext.Msg.alert('提示', '请先选中一个模板!');
					    					return;
					    				}
					    				Ext.Ajax.request({
											url: '<%=basePath%>confManager/template_configTemplateModel',  
											params: {'template_id':record.data.template_id,'template_flag':record.data.template_flag,'template_name':record.data.template_name},
											success:function(response,opt){
												template_window.hide();
												Ext.Msg.alert('提示',response.responseText);
												template_model_flag = true;
												document.getElementById('template_icon').src='images/icon-4-2.png';
												document.getElementById('message').innerHTML = "<font color='white'>当前使用模板:"+ record.data.template_name +"</font>";
												b.toggle();//改变按键状态,改变文字
												//workingTabPanel.items.each(function(item){
							                    //    if(item.closable){
							                    //    	workingTabPanel.remove(item);
							                    //    }
							                    //});
												var temp1 = ['qm','window','machineView','queueingRules','managerCallNum','branchParam'];
												var temp2 = ['排队机信息配置','窗口信息配置','排队机界面配置','叫号规则配置','大堂经理取号配置','机构参数配置'];
												var temp = '业务管理台,排队机设备管理,                                       ';
												for ( var j = 0; j < temp1.length; j++) {
													if(record.data.template_flag.indexOf(temp1[j])!=-1){
														temp += temp2[j]+",";
													}
												}
												var treeData2 = new Array();
												for ( var i = 0; i < treeData.length; i++) {
													var modulename = treeData[i].modulename;
													if(temp.indexOf(modulename+',')!=-1){
														//删掉
														treeData2.push(treeData[i]);
													}
												}
												var treeGenerator2 = new SelfTreeGenerator(treeData2,jsonMeta,'<%=basePath%>',['','x-image-none',''],['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle'],isPrivateMethod);
												var tree2 = treeGenerator2.generate(false, false, false, true, 'WORKINGFRAME');
												Ext.getCmp('menuTree').setRootNode(tree2);
												var item0 = Ext.getCmp('center-panel').getItem('tab_welcome');
												Ext.getCmp('center-panel').removeAll();
												item0 = {
													id:'tab_welcome',
						                        	title: '欢迎',
						                        	closable:false,
						                        	html : '<iframe src="<%=basePath%>/webpages/freamwork/welcome/welcome1.jsp" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>',
									    			autoScroll:true
									    		};
												Ext.getCmp('center-panel').add(item0);
												Ext.getCmp('center-panel').setActiveTab('tab_welcome');
											}
										});
				    				}
				    			}
				    		},'-',{
				    			text : '<b>查看模板</b>',
				    			icon:'images/49.png',
				    			tooltip : '<b>提示</b><br/>查看模板详情',
				    			handler : function(b,e){
				    				var record = Ext.getCmp('templateGrid').getSelectionModel().getSelected();
				    				if (Ext.isEmpty(record)) {
				    					Ext.Msg.alert('提示', '请先选中一个模板!');
				    					return;
				    				}
				    				templateAddForm.getForm().loadRecord(record);
				    				//这里拆分状态值,放入checkbox
			    					Ext.getCmp('check_qm').setValue(true);
			    					Ext.getCmp('check_window').setValue(true);
				    				if(record.data.template_flag.indexOf('machineView')!=-1){
				    					Ext.getCmp('check_machineView').setValue(true);
				    				}
				    				if(record.data.template_flag.indexOf('queueingRules')!=-1){
				    					Ext.getCmp('check_queueingRules').setValue(true);
				    				}
				    				if(record.data.template_flag.indexOf('managerCallNum')!=-1){
				    					Ext.getCmp('check_managerCallNum').setValue(true);
				    				}
				    				if(record.data.template_flag.indexOf('branchParam')!=-1){
				    					Ext.getCmp('check_branchParam').setValue(true);
				    				}
				    				template_add_window.setTitle('<span style="font-weight:normal">查看模板</span>');
				    				//Ext.getCmp('btn_go').enable();
				    				Ext.getCmp('btn_go').hide();
				    				template_add_window.show();
				    			}
				    		},'-',{
				    			text : '<b>新建模板</b>',
				    			icon:'images/49.png',
				    			tooltip : '<b>提示</b><br/>创建新模板',
				    			handler : function(b,e){
				    				templateAddForm.getForm().reset();
				    				template_add_window.show();
				    				//Ext.getCmp('template_name').readOnly = false;
				    				//Ext.getCmp('template_note').readOnly = false;
				    				Ext.getCmp('template_name').setValue('');
				    				Ext.getCmp('template_note').setValue('');
				    				Ext.getCmp('check_qm').setValue(true);
				    				Ext.getCmp('check_window').setValue(true);
				    				Ext.getCmp('check_machineView').setValue(false);
				    				Ext.getCmp('check_queueingRules').setValue(false);
				    				Ext.getCmp('check_managerCallNum').setValue(false);
				    				Ext.getCmp('check_branchParam').setValue(false);
				    				template_add_window.setTitle('<span style="font-weight:normal">新建模板</span>');
				    				Ext.getCmp('btn_go').show();
				    			}
				    		},'-',{
				    			text : '<b>删除模板</b>',
				    			icon:'images/49.png',
				    			tooltip : '<b>提示</b><br/>删除所选模板',
				    			handler : function(b,e){
				    				var record = Ext.getCmp('templateGrid').getSelectionModel().getSelected();
				    				if (Ext.isEmpty(record)) {
				    					Ext.Msg.alert('提示', '请先选中要删除的模板!');
				    					return;
				    				}
				    				if(record.get('branch')!=branch){
				    					Ext.Msg.alert('提示', '无法删除非本机构的模板!'+"用户登录的机构"+branch+";  要删除数据的机构号"+record.get('branch')+";");
				    					return;
				    				}
				    				Ext.Msg.confirm('提示','确定要删除选中的模板吗?',function(btn, text){
										if (btn == 'yes') {
											var submitdata = {};
											submitdata['template_id'] = record.get('template_id');
											requestAjax('<%=basePath%>confManager/template_delTemplateById',submitdata,function(sRet){
												Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
													templateStore.load({params:{'branch':branch}});
												});
											});
										}
									});
				    			}
				    		},'-',{
				    			text : '<b>应用模板</b>',
				    			icon:'images/49.png',
				    			tooltip : '<b>提示</b><br/>为本机构应用所选模板',
				    			handler : function(b,e){
				    				var record = Ext.getCmp('templateGrid').getSelectionModel().getSelected();
				    				if (Ext.isEmpty(record)) {
				    					Ext.Msg.alert('提示', '请先选中要应用的模板!');
				    					return;
				    				}
				    				Ext.Msg.confirm('提示','确定要应用此模板吗?此操作将清空本机构相关配置',function(btn, text){
										if (btn == 'yes') {
											//Ext.Ajax.request({
						    				//	url: '<%=basePath%>confManager/template_selTemplateById',  
						    				//	params: {template_id:record.get('template_id')},
						    				//	success:function(response,opt){
						    				//		var data = Ext.decode(response.responseText);
						    				//		Ext.Msg.alert('提示', '应用模板成功!');
						    				//	},
						    				//	callback:function(options,success,response){
						    				//		var data = Ext.decode(response.responseText);
						    				//		alert(data);
						    				//	}
						    				//});
											record.data['chkflag'] = '1';
											requestAjax('<%=basePath%>confManager/template_selTemplateById',record.data,function(sRet){
												if(!sRet.operaterResult){
													Ext.Msg.confirm('提示',sRet.message + "，<font color='#FF0000'>确定要强制应用此模板吗?</font>",function(btn, text){
														if (btn == 'yes') {
															record.data['chkflag'] = '0';
															requestAjax('<%=basePath%>confManager/template_selTemplateById',record.data,function(sRet){
																Ext.MessageBox.alert('提示','应用模板成功');
															});
														}
													});
												}else{
													Ext.MessageBox.alert('提示','应用模板成功');
												}
											});
										}
									});
				    			}
				    		}]
						})
		            }]
				});
				
				//Ext.getCmp('templateGrid').on("rowdblclick", function(grid, rowIndex){ 
				//    var record = grid.getStore().getAt(rowIndex);  // Get the Record
    			//	Ext.Ajax.request({
				//		url: '<!%=basePath%>confManager/template_configTemplateModel',  
				//		params: {'template_id':record.data.template_id,'template_flag':record.data.template_flag,'template_name':record.data.template_name},
				//		success:function(response,opt){
				//			template_window.hide();
				//			Ext.Msg.alert('提示',response.responseText);
				//			template_model_flag = true;
				//			document.getElementById('template_icon').src='images/icon-4-2.png';
				//			document.getElementById('message').innerHTML = "<font color='white'>当前使用模板:"+ record.data.template_name +"</font>";
				//			Ext.getCmp('btn_template').toggle();//改变按键状态,改变文字
				//			workingTabPanel.items.each(function(item){
		        //                if(item.closable){
		        //                	workingTabPanel.remove(item);
		        //                }
		         //           });
				//		}
				//	});
				//});
				
				//弹出的属性框
				var template_window = new Ext.Window({
					layout : 'fit', // 设置窗口布局模式
					width: 650,
				    height : 450,
				    resizable: false,
				    id:'template_window',
				    draggable : true,
				    closeAction : 'hide',
				    closable : false, 
				    modal : true, //无法失去焦点
					title : '<span style="font-weight:normal">模板弹出窗</span>', // 窗口标题
					collapsible : false, // 是否可收缩
					titleCollapse : false,
					maximizable : false, // 设置是否可以最大化
					buttonAlign : 'right',
					border : false, // 边框线设置
					animCollapse : true,
					pageY : document.body.clientHeight/2-450/2, // 页面定位Y坐标
					pageX : document.body.clientWidth / 2 - 650 / 2, // 页面定位X坐标
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [templateForm], // 嵌入的表单面板
					buttons : [{
						text : '关闭', // 按钮文本
						handler : function() { // 按钮响应函数
							template_window.hide();
						}
					}]
				});
				var templateAddForm = new Ext.FormPanel({
					labelWidth: 60,
					labelAlign : 'right',
					bodyStyle:'padding:5px 5px 0',
					defaults: {width: 300},
			        defaultType: 'textfield',
			        frame : false,
			        items: [{
		                fieldLabel: '模板名称',
		                name:'template_name',
		                id:'template_name',
		                blankText:'请输入模板名称',
		                maxLength : 50,
		                maxLengthText : '长度不能大于50位'
		            },{
		                fieldLabel: '模板备注',
		                xtype:'textarea',
		                name:'template_note',
		                id:'template_note',
		                blankText:'请输入模板备注',
		                maxLength : 50,
		                maxLengthText : '长度不能大于50位'
		            },{
		                fieldLabel: '影响模块',
		                xtype:'fieldset',
		                layout:'form',
		                items:[{
		                	layout: 'column',
							height:30,
							items:[{
									xtype: 'checkbox',
									columnWidth:.6,
									boxLabel: '排队机信息配置',
									id: 'check_qm',
									name: 'check_qm',
									checked: true,
									disabled:true,
									hideLabel:true
								},{
									xtype: 'checkbox',
									columnWidth:.4,
									boxLabel: '窗口信息配置',
									id: 'check_window',
									name: 'check_window',
									checked: true,
									disabled:true,
									hideLabel:true
								}
							]
						},{
		                	layout: 'column',
							height:30,
							items:[{
									columnWidth:.6,
									xtype: 'checkbox',
									boxLabel: '排队机界面配置',
									id: 'check_machineView',
									name: 'check_machineView',
									hideLabel:true
								},{
									xtype: 'checkbox',
									columnWidth:.4,
									boxLabel: '叫号规则配置',
									id: 'check_queueingRules',
									name: 'check_queueingRules',
									hideLabel:true
								}
							]
						},{
		                	layout: 'column',
							height:30,
							items:[{
									columnWidth:.6,
									xtype: 'checkbox',
									boxLabel: '大堂经理优先队列配置',
									id: 'check_managerCallNum',
									name: 'check_managerCallNum',
									hideLabel:true
								},{
									xtype: 'checkbox',
									columnWidth:.4,
									boxLabel: '机构参数配置',
									id: 'check_branchParam',
									name: 'check_branchParam',
									hideLabel:true
								}
							]
						}]
		            }]
				});
				var template_add_window = new Ext.Window({
					layout : 'fit', // 设置窗口布局模式
					width: 400,
				    height : 275,
				    resizable: false,
				    id:'template_add_window',
				    draggable : true,
				    closeAction : 'hide',
				    closable : false, 
				    modal : true, //无法失去焦点
					title : '<span style="font-weight:normal">新建模板</span>', // 窗口标题
					collapsible : false, // 是否可收缩
					titleCollapse : false,
					maximizable : false, // 设置是否可以最大化
					buttonAlign : 'right',
					border : false, // 边框线设置
					animCollapse : true,
					pageY : document.body.clientHeight/2-275/2, // 页面定位Y坐标
					pageX : document.body.clientWidth / 2 - 400 / 2, // 页面定位X坐标
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [templateAddForm], // 嵌入的表单面板
					buttons : [{ // 窗口底部按钮配置
						text : '提交', // 按钮文本
						id:'btn_go',
						handler : function() { // 按钮响应函数
								if(Ext.getCmp('template_name').getValue()==''){
									Ext.Msg.alert('提示','模板名称不能为空');
									return;
								}
								var array = new Array();
								array.push(Ext.getCmp('template_name').getValue());
								array.push(Ext.getCmp('template_note').getValue());
								//将checkbox合并
								var check_flag = 'qm,window';
								if(Ext.getCmp('check_machineView').getValue()){
									check_flag += ',machineView';
								}
								if(Ext.getCmp('check_queueingRules').getValue()){
									check_flag += ',queueingRules';
								}
								if(Ext.getCmp('check_managerCallNum').getValue()){
									check_flag += ',managerCallNum';
								}
								if(Ext.getCmp('check_branchParam').getValue()){
									check_flag += ',branchParam';
								}
								array.push(check_flag);
						    	//通知后台的方法.
							    Ext.Ajax.request({
									url: '<%=basePath%>confManager/template_addTemplateInfo',  
									params: {strJson:Ext.encode(array)},
									success:function(response,opt){
										template_add_window.hide();
										Ext.MessageBox.alert('提示', '添加模板成功');
										templateStore.load({params:{'branch':branch}});
									}
								});
						}
					},{ // 窗口底部按钮配置
						text : '关闭', // 按钮文本
						handler : function() { // 按钮响应函数
							template_add_window.hide();
						}
					}]
				});
				
				/**
				 * 修改用户信息
				 */
				function updateUser() {
					if (!userFormPanel.form.isValid()) {
						return;
					}
					password1 = Ext.getCmp('password1').getValue();
					password = Ext.getCmp('password').getValue();
					if (password1 != password) {
						Ext.Msg.alert('提示', '两次输入的密码不匹配,请重新输入!');
						Ext.getCmp('password').setValue('');
						Ext.getCmp('password1').setValue('');
						return;
					}
					userFormPanel.form.submit({
						url : 'index.ered?reqCode=updateUserInfo',
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							Ext.MessageBox.alert('提示', '登录帐户信息修改成功');
						},
						failure : function(form, action) {
							var msg = action.result.msg;
							Ext.MessageBox.alert('提示', '人员数据保存失败');
						}
					});
				}
				
				/**
				*	北面板
				*/
				var headerPanel =  new Ext.Panel({
                     region:'north',
                     contentEl:'north', 
                     border:false});
                var clientWidth = document.body.clientWidth;
                
                /*
                *菜单面板
                */
			    var westPanel = new Ext.Panel({
			    	region : 'west' , 
			    	collapsible:true , 
			    	title  : '导航区' , 
			    	width : 210,
					split : true , 
					layout:'accordion', 
					margins:'0 0 0 5',
					autoScroll:false, 
					items:[{
						xtype:'treepanel', 
						id:'menuTree',
						title:'<s:text name="welcome.menuareatitle"/>',
						tbar:[
							' ',
						    new Ext.form.TextField({
								width:140,
        						emptyText:'快速检索菜单',
        						enableKeyEvents: true,
        						id:'treetbar',
	    						listeners:{
									render: function(f){
                						this.filter = new QM.ux.TreeFilter(Ext.getCmp('menuTree'),{
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
    						}),' ', ' ',
							{
               	 				iconCls: 'icon-expand-all',
								tooltip: '全部展开',
                				handler: function(){ tree.expand(true); },
                				scope: this
            				}, '-', {
               					iconCls: 'icon-collapse-all',
                				tooltip: '全部收起',
                				handler: function(){ tree.collapse(true); },
                				scope: this
            				}
            			],autoScroll:true, 
            			root: tree, 
            			rootVisible:false,
            			listeners:{
                			//添加键盘点击监听
     						click: function(tab){
         							//alert("走到这儿了!密码为："+pswd);
         							var passwordCharAt1 = pswd.charAt(0);
         							if(passwordCharAt1==pswd.charAt(1)&&passwordCharAt1==pswd.charAt(2)&&passwordCharAt1==pswd.charAt(3)&&passwordCharAt1==pswd.charAt(4)&&passwordCharAt1==pswd.charAt(5)){
         								Ext.MessageBox.alert("系统提示：","您的密码为原始密码，请修改密码！");
         							}
         					}
     					}
           			}]
				});
				
				//工作区面板
			    var workingPanel = new Ext.Panel({
			    	region : 'center' , 
			  //  	html : '<iframe src="<%=basePath%>/webpages/freamwork/welcome/welcome1.jsp" name="WORKINGFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>',
			    	split : true, margins:'0 5 0 0'
			    });
			    
				//底部面板
			    var footerPanel = new Ext.Panel({
			    	region: 'south' , collapsible: false , contentEl:'south',
       				split: true , height: 23,
       				tbar:{height:20,
       					items:[
       					    {xtype:'tbspacer', width:(clientWidth - 200)/2},
							{xtype:'tbtext', text:'ABT@'+ new Date().getFullYear()}
              				]
			    	}
			    });
			    
			    workingTabPanel = new Ext.TabPanel({
                    region:'center',
                   // renderTo:'tabs',
                    id:'center-panel',
                    deferredRender:false,
                    defaults: {autoScroll:true},
                    plugins: new Ext.ux.TabCloseMenu(),
                    resizeTabs:true, // turn on tab resizing
        			minTabWidth: 115,
        			tabWidth:135,
        			enableTabScroll:true,
                    border:false,
                    activeTab:0,
                    frame:true,
                    items:[{
                        //contentEl:'welcome',
                        id:'tab_welcome',
                        title: '欢迎',
                        closable:false,
                        html : '<iframe src="<%=basePath%>/webpages/freamwork/welcome/welcome1.jsp" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>',
			    		autoScroll:true
                    }],
                    listeners:{
                    	'beforeremove': function(tdemo,item,event) { 
	                    	if(item.id!="tab_welcome"){   
	                    		var a=document.getElementsByName('if'+item.id); 
	                    		var node=a[0];
		                    	node.src="about:blank";    
		                    	var spaceNode=[node];
	    							 lookforchildNode(node,spaceNode);	
	    							for(i=0;i<spaceNode.length;i++){		
	    									node.parentNode.removeChild(spaceNode[i]);	
	    							}
	    							if(Ext.isIE){
	    								CollectGarbage();
	    							}              
	                        }           
                     }
                    }
                });
				
				var viewport = new Ext.Viewport({
					enableTabScroll : true,
					layout : "border",
					items : [
						headerPanel,
						westPanel,
						workingTabPanel,
						footerPanel
					]
				});
				
				dataStore.load();
				templateStore.load({params:{'branch':branch}});
				//showNotice(true);					
			});
			var si = 0;
			function onUserLogoutClicked(){
				si = 2;
		    	window.location.href='<%=basePath%>webpages/freamwork/welcome/Exits.jsp?si='+si;
	    	}
			function onUserReLogClicked(){
				si = 1;
	           window.location.href='<%=basePath%>webpages/freamwork/welcome/Exits.jsp?si='+si;
			}
			function onbeforeunload_handler(){   
		        var n = window.event.screenX - window.screenLeft;    
		              var b = n > document.documentElement.scrollWidth-20;    
		              if(b && window.event.clientY < 0 || window.event.altKey)    
		              {    
		                     window.location.href='<%=basePath%>webpages/freamwork/welcome/Exits.jsp?si='+si;
		              }    
		  
		    }
		 function onunload_handler(){   
			 window.location.href='<%=basePath%>webpages/freamwork/welcome/Exits.jsp?si='+si;   
		    }     
		 window.onunload = function(){
		 	
		 }

			
			
			
			//var configButton = new Ext.Button({
			//	text : '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
				//iconCls : 'x-image-config2Icon',
				//iconAlign : 'left',
				//scale : 'small',
				//width : 50,
				//height : 10,
			//	tooltip : '<span style="font-size:12px">个人设置</span>',
				//pressed : true,
			//	arrowAlign : 'right',
			//	renderTo : 'configDiv',
			//	handler : function() {
			//			updateUserInit();
			//	}
			//});

			//var closeButton = new Ext.Button({
				//text : '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
				//iconCls : 'x-image-relogin',
				//iconAlign : 'left',
				//scale : 'small',
				//width : 30,
				//height : 10,
			//	tooltip : '<span style="font-size:12px">切换用户,安全退出系统</span>',
				//pressed : true,
			//	arrowAlign : 'right',
			//	renderTo : 'closeDiv',
			//	handler : function() {
			//		window.location.href = './welcome';
			//	}
			//});
		</script>
		<script language="JavaScript"> 
		//	function myrefresh(){
		//		//zrequestAjax('<%=basePath%>/nxpsappamange/nxpspaymentstatus_StatusManage','',function(sRet){
		//			alert('1');
		//			showNotice(true);	
		//		//});
		//	}
		//	setTimeout('myrefresh()',2000); //指定10秒刷新一次
		</script>
	</head>

	<body onbeforeunload="onbeforeunload_handler()";onunload="onunload_handler()";>
		<div id="messagewindow"></div>
			<div id="north" class="top_bg">
				<div class="top_left">
					<div class="msg">
						<div class="info">
							姓名：<%=username%>&nbsp;||&nbsp;账号：<%=usercode%>&nbsp;||&nbsp;所属机构：<%=unidName%>&nbsp;||&nbsp;机构号：<%=unitId%>&nbsp;||&nbsp;机构校验码：<%=unitCheckCode%>&nbsp;||&nbsp;登录时间：<%=date%>
						</div>
						<div class="tool">
							<font id="message"></font>
							&nbsp;
							<img id="template_icon" src="images/icon-4.png" onClick="javascript:openTemplateWindow();return false;" href="#" onmouseover="javascript:onmouseover_template();return false;" onmouseout="javascript:onmouseout_template();return false;"
								align="absmiddle" />
							&nbsp;
							<img id="msg_icon" src="images/icon-1.png" onClick="javascript:showWindow();return false;" href="#" onmouseover="Ext.TaskMgr.stop(task);this.src='images/icon-1-1.png';" onmouseout="javascript:onmouseout_message();return false;"
								align="absmiddle" >
							</img>
							&nbsp;
							<img src="images/icon-2.png" onClick="javascript:updateUserInit();return false;" href="#" onmouseover="this.src='images/icon-2-1.png'" onmouseout="this.src='images/icon-2.png'"
								align="absmiddle" />
							&nbsp;
							<img src="images/icon-3.png" onClick="javascript:UserExit();return false;" href="#" onmouseover="this.src='images/icon-3-1.png'" onmouseout="this.src='images/icon-3.png'"
								align="absmiddle"/>
							&nbsp;
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="messageWindow"></div>
		<div id="south"></div>
		<div id="center"></div>
		<form name="form1" />
		<div id="loading-msg" align="center">
			<img src="images/000.gif" />
		</div>
	</body>
</html>
