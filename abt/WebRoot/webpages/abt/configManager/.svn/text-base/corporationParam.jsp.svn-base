<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
	String template_id = (String)request.getSession().getAttribute("template");
	String brno;
	if(template_id!=null){
		brno = template_id;
	}else{
		brno = user.getUnitid();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>法人参数配置</title>
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
		<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
		<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
		<script type="text/javascript" src="extjs/ux/CheckColumn.js"></script>
		
		<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		var reservTimeWindow = null;
		var reservBusinessWindow = null;
		
	    var dataStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>confManager/corporationParam_loadConf',
			root: 'field1',
			//dataStore.load({params:temp}); temp = {}; temp[''] = Ext.getCmp('').getValue();
			fields : ['param_key','param_value'],
			listeners : {  
				load : function(store,records){
						for(var i = 0;i<3;i++){
							var param_key = store.getAt(i).data['param_key'];
							var param_value = store.getAt(i).data['param_value'];
							if(param_key=="accreditrank"){
								Ext.getCmp('accreditrank-'+param_value).setValue(true);
							}
							if(param_key=="messageOutlay-card"){
								Ext.getCmp('messageOutlay-card'+param_value).setValue(true);
							}
							if(param_key=="messageOutlay-pfs"){
								Ext.getCmp('messageOutlay-pfs'+param_value).setValue(true);
							}
							
						} 
						//Ext.getCmp('default_flag').setValue(store.getAt(0).data['default_flag']);
						if(store.getAt(0).data['queue_applytime']!=''){
							Ext.getCmp('queue_applytime').setValue(store.getAt(0).data['queue_applytime']);
						}else{
							Ext.getCmp('queue_applytime').setValue(10);
						}
						
						panelName.getForm().findField(store.getAt(0).data['param_key']).setValue(store.getAt(0).data['param_value']);
						alert(store.getAt(0).data['param_key']);
						Ext.getCmp('def_show_notify_threshold').setValue(store.getAt(0).data['def_show_notify_threshold']);
					
				}
			}
	    });
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
/**
 * @Title: loadPage 
 * @Description: 显示发卡机参数界面
 */	
			function loadPage(){
				//取界面长宽
				var clientHeight = document.body.clientHeight;
				var clientWidth  = document.body.clientWidth;
				
				/**
				 * 界面主体panel
				 *
				 */
				var panel = new Ext.Panel({
					//layout : 'absolute',
					frame:true,
					height:clientHeight+40,
					buttonAlign :'left',
					applyTo :'panel',
					layout:'form',
					items:[{
						layout:'form', //整体数据
						frame:true,
						height:clientHeight,
						width:clientWidth,
			    		items:[{
			    			columnWidth:.5, //左侧整体
			    			layout:'form', 
			    			bodyStyle:'padding:0 8 0 8', 
			    			labelAlign:'left',
			    		  	items:[{
			    		  		columnWidth:1, //左面上下整体 套层form
			    		  		layout:'form', 
			    		  		bodyStyle:'padding:0 8 0 8', 
			    		  		labelAlign:'left',
			    	   			items:[{
			    	   				width: (clientWidth*2/4)-30,//参数那一块
			    	   				height:60,
			    	   				xtype:'fieldset',
			    	   				layout:'column',
			    	   				title:'发卡机短信通知收费标准',
			    	   				items:[{
			    	   					columnWidth:0.75,
			    	   					labelAlign: 'right',
			    	   					labelWidth:45,
			    	   					layout:'column',
			    	   					items:[
												{
													xtype:'radio',
													id:'messageOutlay-card0',
													name:'messageOutlay-card',
													boxLabel:'免费'
												},
												{
													xtype:'radio',
													id:'messageOutlay-card1',
													name:'messageOutlay-card',
													boxLabel:'包月'
												},
												{
													xtype:'radio',
													id:'messageOutlay-card2',
													name:'messageOutlay-card',
													boxLabel:'包年'
												},
												{
													xtype:'radio',
													id:'messageOutlay-card3',
													name:'messageOutlay-card',
													boxLabel:'用户可选'
												}
			    	   					]
			    	   				}]
			    	   			}]
			    		  	},{
			    		  		columnWidth:1, 
			    		  		layout:'form', 
			    		  		bodyStyle:'padding:0 8 0 8', 
			    		  		labelAlign:'left',
			    	   			items:[{
			    	   				width: (clientWidth*2/4)-30,
			    	   				height:clientHeight - 90,
			    	   				xtype:'fieldset',
			    	   				layout:'column',
			    	   				title:'填单机短信通知收费标准',
			    	   				height:60,
			    	   				hideMode :'visibility',
				    		      	hidden: false,
			    	   				items:[
			    	   						{
			    	   							xtype:'radio',
			    	   							id:'messageOutlay-pfs0',
			    						    	name:'messageOutlay-pfs',
			    						    	boxLabel:'免费'
			    	   						},
			    	   						{
			    	   							xtype:'radio',
			    	   							id:'messageOutlay-pfs1',
			    						    	name:'messageOutlay-pfs',
			    						    	boxLabel:'包月'
			    	   						},
			    	   						{
			    	   							xtype:'radio',
			    	   							id:'messageOutlay-pfs2',
			    						    	name:'messageOutlay-pfs',
			    						    	boxLabel:'包年'
			    	   						},
			    	   						{
			    	   							xtype:'radio',
			    	   							id:'messageOutlay-pfs3',
			    						    	name:'messageOutlay-pfs',
			    						    	boxLabel:'用户可选'
			    	   						}
			    	   				]
			    		  		}]
			    		  	},{
			    		  		columnWidth:1, 
			    		  		layout:'form', 
			    		  		bodyStyle:'padding:0 8 0 8', 
			    		  		labelAlign:'left',
			    	   			items:[{
			    	   				width: (clientWidth*2/4)-30,
			    	   				height:clientHeight - 90,
			    	   				xtype:'fieldset',
			    	   				layout:'column',
			    	   				title:'发卡机授权等级',
			    	   				height:60,
			    	   				hideMode :'visibility',
				    		      	hidden: false,
			    	   				id:'accreditrank',
			    	   				items:[
			    	   						{
			    	   							xtype:'radio',
			    	   							id:'accreditrank-1',
		    						    	   	name:'accreditrank',
		    						    	   	boxLabel:'一级以上'
			    	   						},
			    	   						{
			    	   							xtype:'radio',
			    	   							id:'accreditrank-2',
		    						    	   	name:'accreditrank',
		    						    	   	boxLabel:'二级以上'
			    	   						},
			    	   						{
			    	   							xtype:'radio',
			    	   							id:'accreditrank-5',
		    						    	   	name:'accreditrank',
		    						    	   	boxLabel:'五级以上'
			    	   						}
			    	   				]
			    		  		}]
			    		  	}]
			    		}]
			    	}],buttons: [{
						text: '保存', 
						formBind: true,
						handler : function() { // 按钮响应函数
							onSaveClicked();
						}
					}]
				});
				dataStore.load({'params':{'query_rules':'0'}});
				/**
				* @Title:onSaveClicked
				* @Description:保存修改事件
				*/
				function onSaveClicked(){
					var submitData = {};
					//授权等级
					if(Ext.getCmp('accreditrank-1').getValue()){
						submitData['accreditrank'] = '1';
					}
					if(Ext.getCmp('accreditrank-2').getValue()){
						submitData['accreditrank'] = '2';
					}
					if(Ext.getCmp('accreditrank-5').getValue()){
						submitData['accreditrank'] = '5';
					}
					//发卡机短信通知费用
					if(Ext.getCmp('messageOutlay-card0').getValue()){
						submitData['messageOutlay-card'] = '0'
					}
					if(Ext.getCmp('messageOutlay-card1').getValue()){
						submitData['messageOutlay-card'] = '1'
					}
					if(Ext.getCmp('messageOutlay-card2').getValue()){
						submitData['messageOutlay-card'] = '2'
					}
					if(Ext.getCmp('messageOutlay-card3').getValue()){
						submitData['messageOutlay-card'] = '3'
					}
					//填单机短信收费标准
					if(Ext.getCmp('messageOutlay-pfs0').getValue()){
						submitData['messageOutlay-pfs'] = '0'
					}
					if(Ext.getCmp('messageOutlay-pfs1').getValue()){
						submitData['messageOutlay-pfs'] = '1'
					}
					if(Ext.getCmp('messageOutlay-pfs2').getValue()){
						submitData['messageOutlay-pfs'] = '2'
					}
					if(Ext.getCmp('messageOutlay-pfs3').getValue()){
						submitData['messageOutlay-pfs'] = '3'
					}
					
					requestAjax('<%=basePath%>confManager/corporationParam_saveParam', submitData,function(sRet){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','保存法人配置成功',function(id){
							dataStore.load({'params':{'query_rules':'0'}});
						});
					});
				}
				
//-------------------------------------------------------------------------布局
				function buildLayout(){
					var viewport = new Ext.Viewport({
						layout: 'fit',
						items : [panel]
					});
				}
				buildLayout();
				
				//启动加载
				systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'10'}});
			}
		</script>
	</head>

	<body>
		<div id="panel"></div>
	</body>
</html>