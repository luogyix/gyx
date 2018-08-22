<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>机构参数配置</title>
    
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
		/**
		 * 翻译字段用store
		 */
		var busiStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/subscribeBusiness_loadReservBusiness',
			root   : 'field1',
			fields:['bs_id','bs_name_ch','status','t9_10','t10_11','t11_12','t12_13','t13_14','t14_15','t15_16','t16_17']
		});
	    var dataStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>confManager/branchParam_loadConf',
			root: 'field1',
			//dataStore.load({params:temp}); temp = {}; temp[''] = Ext.getCmp('').getValue();
			fields : ['queue_applytime','ticket_fstip','branch','default_flag','reserv_status','reserv_advance_status','reserv_max_break','reserv_advance_before','reserv_max_time','reserv_maxdays_before','reserv_minmin_before','reserv_min_active','reserv_max_active','reserv_prompt','customer_visit',
			          'negative_monitor','negative_noti_flag','remaind_flag','def_waitnum_threshold','def_waittime_threshold','def_notify_threshold','def_show_notify_threshold','wake_sleeptime'],
			listeners : {  
				load : function(store,records){
					if(store.data.items!=''){
						//Ext.getCmp('default_flag').setValue(store.getAt(0).data['default_flag']);
						if(store.getAt(0).data['queue_applytime']!=''){
							Ext.getCmp('queue_applytime').setValue(store.getAt(0).data['queue_applytime']);
						}else{
							Ext.getCmp('queue_applytime').setValue(10);
						}
						
						Ext.getCmp('ticket_fstip').setValue(store.getAt(0).data['ticket_fstip']);
						Ext.getCmp('reserv_status').setValue(store.getAt(0).data['reserv_status'].toString().substring(0,1));
						//启用预约提前通知
						Ext.getCmp('reserv_advance_status').setValue(store.getAt(0).data['reserv_advance_status']);
						Ext.getCmp('reserv_max_break').setValue(store.getAt(0).data['reserv_max_break'].toString().substring(0,1));
						//预约提前通知分钟
						Ext.getCmp('reserv_advance_before').setValue(store.getAt(0).data['reserv_advance_before']);
						Ext.getCmp('reserv_max_time').setValue(store.getAt(0).data['reserv_max_time']);
						Ext.getCmp('reserv_maxdays_before').setValue(store.getAt(0).data['reserv_maxdays_before']);
						Ext.getCmp('reserv_minmin_before').setValue(store.getAt(0).data['reserv_minmin_before']);
						Ext.getCmp('reserv_min_active').setValue(store.getAt(0).data['reserv_min_active']);
						Ext.getCmp('reserv_max_active').setValue(store.getAt(0).data['reserv_max_active']);
						Ext.getCmp('reserv_prompt').setValue(store.getAt(0).data['reserv_prompt']);
						Ext.getCmp('negative_monitor').setValue(store.getAt(0).data['negative_monitor']);
						Ext.getCmp('remaind_flag').setValue(store.getAt(0).data['remaind_flag']);
						Ext.getCmp('customer_visit').setValue(store.getAt(0).data['customer_visit']);
						Ext.getCmp('negative_noti_flag').setValue(store.getAt(0).data['negative_noti_flag']);
						Ext.getCmp('def_waitnum_threshold').setValue(store.getAt(0).data['def_waitnum_threshold']);
						Ext.getCmp('def_waittime_threshold').setValue(store.getAt(0).data['def_waittime_threshold']);
						Ext.getCmp('wake_sleeptime').setValue(store.getAt(0).data['wake_sleeptime']);
						Ext.getCmp('def_notify_threshold').setValue(store.getAt(0).data['def_notify_threshold']);
						Ext.getCmp('def_show_notify_threshold').setValue(store.getAt(0).data['def_show_notify_threshold']);
					}
				}
			}
	    });
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示机构参数配置界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			/*
			{
					xtype:'fieldset',
					title:'基础配置',
					width:580,
					height: 60,
					items:[{
						xtype:'checkbox',
						boxLabel:'设为默认',
						hideLabel:true,
						id:'default_flag',
						name:'default_flag'
					}]
				},
			*/
			var form = new Ext.FormPanel({
				labelWidth: 200, // label settings here cascade unless overridden
				autoScroll:true,
				url:'提交保存时候的配置',
				frame:true,
				monitorValid :true,
				title: '机构参数配置',
				width: clientWidth,
				height: clientHeight,
				buttonAlign :'left',
				defaults: {width: 270},
				layout:'form',
				items: [{
					xtype:"fieldset",
					title:"基础配置",
					layout:"form",
					width:500,
					height:130,
					items:[{
						xtype: 'textfield',
						fieldLabel:'按时长计算队列的时间阈值(分)',
						width:50, 
						id: 'queue_applytime',
						name: 'queue_applytime',
						hidden:true,
						maxLength:20,
						maxLengthText:'长度不能大于20位',
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						listeners:{
							render:function(obj){
								var font = document.createElement('font');
								font.setAttribute('color','black');
								font.setAttribute('size','2');
								var redStar = document.createTextNode('  注:0为不启用此时间阈值');
								font.appendChild(redStar);
								obj.el.dom.parentNode.appendChild(font);
							}						
						}
					},{
						xtype: 'textarea',
						fieldLabel:'小票友情提示',
						width:270,
						id: 'ticket_fstip',
						name: 'ticket_fstip',
						maxLength:600,
						maxLengthText:'长度不能大于600位'
					}]
				},{
					xtype:"fieldset",
					title:"监控配置",
					layout:"form",
					width:500,
					height:225,
					items:[{
						layout: 'column',
						height:30,
						items:[
							{
								xtype: 'checkbox',
								columnWidth:.35,
								boxLabel: '启用差评监控',
								id: 'negative_monitor',
								name: 'negative_monitor',
								hideLabel:true
							},{
								xtype: 'checkbox',
								columnWidth:.35,	
								boxLabel: '启用差评通知',
								id: 'negative_noti_flag',
								name: 'negative_noti_flag',
								hideLabel:true
							},{
								xtype:'button',
								text:'各队列监控配置',
								handler : function() { // 按钮响应函数
									window.open('<%=basePath%>/webpages/abt/configManager/QMMonitorConf_loadPage.action','队列监控配置','height=500,width=800');
								}
							}
						]
					},{
						layout: 'column',
						height:30,
						items:[{
								xtype: 'checkbox',
								columnWidth:.283,	
								boxLabel: '启用客户到号通知',
								id: 'remaind_flag',
								name: 'remaind_flag',
								hideLabel:true
							},{
								xtype: 'checkbox',
								columnWidth:.30,	
								boxLabel: '启用客户到访通知',
								id: 'customer_visit',
								name: 'customer_visit',
								hideLabel:true
							}
						]
					},{
						fieldLabel: '默认人数预警阈值',
						xtype:'textfield',
						width: 50,
						id: 'def_waitnum_threshold',
						name: 'def_waitnum_threshold',
						maxLength : 10,
						maxLengthText : '长度不能大于10',
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						allowBlank:false,
						listeners:{
							render:function(obj){
								var font = document.createElement('font');
								font.setAttribute('color','black');
								var redStar = document.createTextNode('人');
								font.appendChild(redStar);
								obj.el.dom.parentNode.appendChild(font);
							}						
						}
					},{
						fieldLabel: '默认等待时间预警阈值',
						xtype:'textfield',
						width: 50,
						id: 'def_waittime_threshold',
						name: 'def_waittime_threshold',
						maxLength : 10,
						maxLengthText : '长度不能大于10',
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						allowBlank:false,
						listeners:{
							render:function(obj){
								var font = document.createElement('font');
								font.setAttribute('color','black');
								font.setAttribute('size','2');
								var redStar = document.createTextNode('分');
								font.appendChild(redStar);
								obj.el.dom.parentNode.appendChild(font);
							}						
						}
					},{
						fieldLabel: '默认队列到号提前通知人数阈值',
						xtype:'textfield',
						width: 50,
						id: 'def_notify_threshold',
						name: 'def_notify_threshold',
						maxLength : 10,
						maxLengthText : '长度不能大于10',
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						allowBlank:false,
						listeners:{
							render:function(obj){
								var font = document.createElement('font');
								font.setAttribute('color','black');
								font.setAttribute('size','2');
								var redStar = document.createTextNode('人');
								font.appendChild(redStar);
								obj.el.dom.parentNode.appendChild(font);
							}						
						}
					},{
						fieldLabel: '默认队列到号通知启用人数阈值',
						xtype:'textfield',
						width: 50,
						id: 'def_show_notify_threshold',
						name: 'def_show_notify_threshold',
						maxLength : 10,
						maxLengthText : '长度不能大于10',
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						allowBlank:false,
						listeners:{
							render:function(obj){
								var font = document.createElement('font');
								font.setAttribute('color','black');
								font.setAttribute('size','2');
								var redStar = document.createTextNode('人');
								font.appendChild(redStar);
								obj.el.dom.parentNode.appendChild(font);
							}						
						}
					},{
						fieldLabel: '唤醒休眠状态时间阈值',
						xtype:'textfield',
						width: 50,
						id: 'wake_sleeptime',
						name: 'wake_sleeptime',
						maxLength : 10,
						maxLengthText : '长度不能大于10',
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						allowBlank:false,
						listeners:{
							render:function(obj){
								var font = document.createElement('font');
								font.setAttribute('color','black');
								font.setAttribute('size','2');
								var redStar = document.createTextNode('分');
								font.appendChild(redStar);
								obj.el.dom.parentNode.appendChild(font);
							}						
						}
					}]
				},{
					xtype:'spacer',
					width:100
				},{
					xtype:'fieldset',
					title:'预约配置',
					width:500,
/*****************************隐藏此组件***************************************************/
					hidden:true,
/*****************************2017-01-10 19:57/高艺祥修改***************************************************/					
					height: 275,
					layout:'form',
					items:[{
						layout:'column',
						height:30,
						items:[{
								xtype: 'checkbox',
								boxLabel: '启用预约',
								columnWidth:0.3,
								height:30,
								id: 'reserv_status',
								name: 'reserv_status',
								hideLabel:true
							},{
								xtype: 'checkbox',
								boxLabel: '启用预约提前通知',
								columnWidth:0.5,
								height:30,
								id: 'reserv_advance_status',
								name: 'reserv_advance_status',
								hideLabel:true
							},{
								width:150,
								xtype:'button',
								text:'预约业务配置',
// 								hidden:true,
								handler : function() { // 按钮响应函数
									//reservBusinessWindow.open();
									busWindow.show();
									busiStore.load();
								}
							},{
								xtype:'button',
								width:40,
								hidden:true,
								hideMode : 'visibility'
							},{
								width:150,
								xtype:'button',
								text:'预约时间段配置',
								handler : function() { // 按钮响应函数
									//window.open('<%=basePath%>confManager/subscribeTime_loadPage','预约时间段配置','height=500,width=800');
									reservTimeDetails();
								} 
							}
						]
					},{
					fieldLabel: '预约提前通知分钟',
						xtype:'textfield',
						width: 270,
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						id: 'reserv_advance_before',
						name: 'reserv_advance_before',
						allowBlank:true
					},{
						fieldLabel: '客户爽约次数上限',
						xtype:'textfield',
						width: 270,
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						id: 'reserv_max_break',
						name: 'reserv_max_break',
						allowBlank:true
					},{
						fieldLabel: '客户单日预约最大次数',
						xtype:'textfield',
						width: 270,
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						id: 'reserv_max_time',
						name: 'reserv_max_time',
						allowBlank:true
					},{
						fieldLabel: '最大可预约天数',
						xtype:'textfield',
						width: 270,
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						id: 'reserv_maxdays_before',
						name: 'reserv_maxdays_before',
						allowBlank:true
					},{
						fieldLabel: '最小可预约提前分钟数',
						xtype:'textfield',
						width: 270,
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						id: 'reserv_minmin_before',
						name: 'reserv_minmin_before',
						allowBlank:true
					},{
						fieldLabel: '可提前激活分钟数',
						xtype:'textfield',
						width: 270,
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						id: 'reserv_max_active',
						name: 'reserv_max_active',
						allowBlank:true
					},{
						fieldLabel: '可延后激活分钟数',
						xtype:'textfield',
						width: 270,
						regex:/^[0-9]*$/,
						regexText:'只能输入数字',
						id: 'reserv_min_active',
						name: 'reserv_min_active',
						allowBlank:true
					},{
						fieldLabel: '网点预约提示',
						xtype: 'textarea',
						name: 'reserv_prompt',
						maxLength : 100,
						maxLengthText : '长度不能大于100',
						id: 'reserv_prompt',
						width: 270,
						height:50
					}]
				}
				],
				buttons: [{
					text: '保存', 
					formBind: true,
					handler : function() { // 按钮响应函数
						onSaveClicked();
					}
				},{
					text: '复制上级配置',
					handler : function() { // 按钮响应函数
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','是否提取上级配置',function(id){
				    		if(id === 'yes'){
				    			dataStore.load({'params':{'query_rules':'3'}});
				    		}
				    	});
					} 
				}]
			});
				
			var viewport = new Ext.Viewport({
				layout:'fit',
				frame:true,
				items:[form]
			});
			
            /**
			* @Title:onSaveClicked
			* @Description:保存修改事件
			*/
			function onSaveClicked(){
				var submitData = {};
				//if(Ext.getCmp('default_flag').getValue()){
				//	submitData['default_flag'] = '0';
				//}else{
				//	submitData['default_flag'] = '1';
				//}
				if(Ext.getCmp('reserv_status').getValue()){
					submitData['reserv_status'] = '1';
				}else{
					submitData['reserv_status'] = '0';
				}
				if(Ext.getCmp('reserv_advance_status').getValue()){
					submitData['reserv_advance_status'] = '1';
				}else{
					submitData['reserv_advance_status'] = '0';
				}
				if(Ext.getCmp('queue_applytime').getValue()!=''||Ext.getCmp('queue_applytime').getValue()==0){
					submitData['queue_applytime'] = Ext.getCmp('queue_applytime').getValue();
				}else{
					submitData['queue_applytime'] = 10;
				}
				
				if(Ext.getCmp('reserv_advance_before').getValue()!=''){
					submitData['reserv_advance_before'] = Ext.getCmp('reserv_advance_before').getValue();
				}else{
					submitData['reserv_advance_before'] = 0;
				}
				if(Ext.getCmp('reserv_max_break').getValue()!=''){
					submitData['reserv_max_break'] = Ext.getCmp('reserv_max_break').getValue().toString().substring(0,1);
				}else{
					submitData['reserv_max_break'] = 0;
				}
				if(Ext.getCmp('reserv_max_time').getValue()!=''){
					submitData['reserv_max_time'] = Ext.getCmp('reserv_max_time').getValue();
				}else{
					submitData['reserv_max_time'] = 0;
				}
				if(Ext.getCmp('reserv_maxdays_before').getValue()!=''){
					submitData['reserv_maxdays_before'] = Ext.getCmp('reserv_maxdays_before').getValue();
				}else{
					submitData['reserv_maxdays_before'] = 0;
				}
				if(Ext.getCmp('reserv_minmin_before').getValue()!=''){
					submitData['reserv_minmin_before'] = Ext.getCmp('reserv_minmin_before').getValue();
				}else{
					submitData['reserv_minmin_before'] = 0;
				}
				if(Ext.getCmp('reserv_min_active').getValue()!=''){
					submitData['reserv_min_active'] = Ext.getCmp('reserv_min_active').getValue();
				}else{
					submitData['reserv_min_active'] = 0;
				}
				if(Ext.getCmp('reserv_max_active').getValue()!=''){
					submitData['reserv_max_active'] = Ext.getCmp('reserv_max_active').getValue();
				}else{
					submitData['reserv_max_active'] = 0;
				}
				if(Ext.getCmp('reserv_prompt').getValue()!=''){
					submitData['reserv_prompt'] = Ext.getCmp('reserv_prompt').getValue();
				}else{
					submitData['reserv_prompt'] = 0;
				}
				submitData['ticket_fstip'] = Ext.getCmp('ticket_fstip').getValue();
				if(Ext.getCmp('negative_monitor').getValue()){
					submitData['negative_monitor'] = '1';
				}else{
					submitData['negative_monitor'] = '0';
				}
				if(Ext.getCmp('remaind_flag').getValue()){
					submitData['remaind_flag'] = '1';
				}else{
					submitData['remaind_flag'] = '0';
				}
				if(Ext.getCmp('customer_visit').getValue()){
					submitData['customer_visit'] = '1';
				}else{
					submitData['customer_visit'] = '0';
				}
				if(Ext.getCmp('negative_noti_flag').getValue()){
					submitData['negative_noti_flag'] = '1';
				}else{
					submitData['negative_noti_flag'] = '0';
				}
				submitData['def_waitnum_threshold'] = Ext.getCmp('def_waitnum_threshold').getValue();
				submitData['def_waittime_threshold'] = Ext.getCmp('def_waittime_threshold').getValue();
				submitData['def_notify_threshold'] = Ext.getCmp('def_notify_threshold').getValue();
				submitData['def_show_notify_threshold'] = Ext.getCmp('def_show_notify_threshold').getValue();
				submitData['wake_sleeptime'] = Ext.getCmp('wake_sleeptime').getValue();
				
				requestAjax('<%=basePath%>confManager/branchParam_saveConf', submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','保存机构信息成功',function(id){
						dataStore.load({'params':{'query_rules':'0'}});
					});
				});
			}
			dataStore.load({'params':{'query_rules':'0'}});
			
			/*预约办理业务字段名*/
			var busiData = [
    	      	{ xtype: 'gridcolumn',dataIndex: 'bs_id',header: '业务编号',sortable:true,width:100,hidden:true,align:'center'},
    	      	{ xtype: 'gridcolumn',dataIndex: 'bs_name_ch',header: '业务名称',sortable:true,width:130,align:'center'},
    	      	{ xtype: 'checkcolumn',dataIndex: 'status',header: '启用',sortable:true,width:65,align:'center'},
    	      	{ xtype: 'checkcolumn',dataIndex: 't9_10',header: '9-10点',sortable:true,width:80,align:'center'},
    	      	{ xtype: 'checkcolumn',dataIndex: 't10_11',header: '10-11点',sortable:true,width:80,align:'center'},
    	      	{ xtype: 'checkcolumn',dataIndex: 't11_12',header: '11-12点',sortable:true,width:80,align:'center'},
    	      	{ xtype: 'checkcolumn',dataIndex: 't12_13',header: '12-13点',sortable:true,width:80,align:'center'},
    	      	{ xtype: 'checkcolumn',dataIndex: 't13_14',header: '13-14点',sortable:true,width:80,align:'center'},
    	      	{ xtype: 'checkcolumn',dataIndex: 't14_15',header: '14-15点',sortable:true,width:80,align:'center'},
    	      	{ xtype: 'checkcolumn',dataIndex: 't15_16',header: '15-16点',sortable:true,width:80,align:'center'},
    	      	{ xtype: 'checkcolumn',dataIndex: 't16_17',header: '16-17点',sortable:true,width:80,align:'center'}
    		];
    		var busiColModel=new Ext.grid.ColumnModel(busiData);
    		//预约办理业务窗口
			var busWindow = new Ext.Window({
				layout : 'fit', // 设置窗口布局模式
				width: 650,
			    height : 330,
			    resizable: false,
			    draggable : true,
			    closeAction : 'hide',
			    modal : true, //无法失去焦点
				title : '<span style="font-weight:normal">预约可办理业务配置</span>', // 窗口标题
				collapsible : false, // 是否可收缩
				titleCollapse : true,
				maximizable : false, // 设置是否可以最大化
				buttonAlign : 'right',
				border : false, // 边框线设置
				pageY : 20, // 页面定位Y坐标
				pageX : document.body.clientWidth / 2 - 400, // 页面定位X坐标
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [{
					xtype: 'editorgrid',
					name:'busGrid',
					id:'busGrid',
					store: busiStore,
					viewConfig: {forceFit: true},
		  			cm: busiColModel,
		  			clicksToEdit: 1
				}], // 嵌入的表单面板
				buttons : [{ // 窗口底部按钮配置
					text : '确定', // 按钮文本
					handler : function(a,b,c) { // 按钮响应函数
						var submitData = {};
						var listData = [];
						var datas = Ext.getCmp('busGrid').store.data.items;
						for ( var i = 0; i < datas.length; i++) {
							listData.push(datas[i].data);
						}
						submitData['listData'] = listData;
						//-----------------------------------------------------判断结束
						requestAjax('<%=basePath%>/confManager/subscribeBusiness_saveReservBusiness', submitData,function(sRet){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','配置业务成功',function(id){
								busWindow.hide();
							});
						});
					}
				},{ // 窗口底部按钮配置
					text : '取消', // 按钮文本
					handler : function() { // 按钮响应函数
						busWindow.hide();
					}
				}]
			});
			
			reservBusinessWindow = new SelfFormWindowSetWidth('reservBusinessWindow','预约可办理业务配置',540, 290, 500, 1, [500],[{
		   		colIndex:0,
		   		field:{
	       			xtype:'fieldset',
	       			layout:'form',
	       			height:'260',
	       			items:[]
		   		}
		    }],[
		        {text:'保存配置', 	handler : onReservTimeClicked, 	formBind:true},
				{text:'返回', 	handler: function(){reservBusinessWindow.close();}}
		    ]);
			
			reservTimeWindow = new SelfFormWindowSetWidth('reservTimeWindow','预约可办理时段配置',340, 290, 300, 1, [300],[{
		   		colIndex:0,
		   		field:{
	       			xtype:'fieldset',
	       			layout:'form',
	       			items:[{
	       				layout:'column',
	       				items:[
      				    	{columnWidth:0.9,xtype:'checkbox',id:'check_09_10',boxLabel:'09:00-10:00　　　最大可预约人数:',hideLabel:true},
	       					{xtype:'numberfield',width:'35',id:'txt_09_10',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}
	       				]
	       			},{
	       				layout:'column',
	       				items:[
      				    	{columnWidth:0.9,xtype:'checkbox',id:'check_10_11',boxLabel:'10:00-11:00　　　最大可预约人数:',hideLabel:true},
	       					{xtype:'numberfield',width:'35',id:'txt_10_11',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}
	       				]
	       			},{
	       				layout:'column',
	       				items:[
      				    	{columnWidth:0.9,xtype:'checkbox',id:'check_11_12',boxLabel:'11:00-12:00　　　最大可预约人数:',hideLabel:true},
	       					{xtype:'numberfield',width:'35',id:'txt_11_12',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}
	       				]
	       			},{
	       				layout:'column',
	       				items:[
      				    	{columnWidth:0.9,xtype:'checkbox',id:'check_12_13',boxLabel:'12:00-13:00　　　最大可预约人数:',hideLabel:true},
	       					{xtype:'numberfield',width:'35',id:'txt_12_13',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}
	       				]
	       			},{
	       				layout:'column',
	       				items:[
      				    	{columnWidth:0.9,xtype:'checkbox',id:'check_13_14',boxLabel:'13:00-14:00　　　最大可预约人数:',hideLabel:true},
	       					{xtype:'numberfield',width:'35',id:'txt_13_14',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}
	       				]
	       			},{
	       				layout:'column',
	       				items:[
      				    	{columnWidth:0.9,xtype:'checkbox',id:'check_14_15',boxLabel:'14:00-15:00　　　最大可预约人数:',hideLabel:true},
	       					{xtype:'numberfield',width:'35',id:'txt_14_15',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}
	       				]
	       			},{
	       				layout:'column',
	       				items:[
      				    	{columnWidth:0.9,xtype:'checkbox',id:'check_15_16',boxLabel:'15:00-16:00　　　最大可预约人数:',hideLabel:true},
	       					{xtype:'numberfield',width:'35',id:'txt_15_16',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}
	       				]
	       			},{
	       				layout:'column',
	       				items:[
      				    	{columnWidth:0.9,xtype:'checkbox',id:'check_16_17',boxLabel:'16:00-17:00　　　最大可预约人数:',hideLabel:true},
	       					{xtype:'numberfield',width:'35',id:'txt_16_17',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}
	       				]
	       			}/*,{layout:'column',
	       				items:[{columnWidth:0.4,xtype:'checkbox',id:'check_10_11',boxLabel:'10:00-11:00',hideLabel:true},
	       				       {columnWidth:0.6,xtype:'form',items:[{xtype:'numberfield',width:'35',id:'txt_10_11',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}]
	       				}]
	       			},{layout:'column',
	       				items:[{columnWidth:0.4,xtype:'checkbox',id:'check_11_12',boxLabel:'11:00-12:00',hideLabel:true},
	       				       {columnWidth:0.6,xtype:'form',items:[{xtype:'numberfield',width:'35',id:'txt_11_12',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}]
	       				}]
	       			},{layout:'column',
	       				items:[{columnWidth:0.4,xtype:'checkbox',id:'check_12_13',boxLabel:'12:00-13:00',hideLabel:true},
	       				       {columnWidth:0.6,xtype:'form',items:[{xtype:'numberfield',width:'35',id:'txt_12_13',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}]
	       				}]
	       			},{layout:'column',
	       				items:[{columnWidth:0.4,xtype:'checkbox',id:'check_13_14',boxLabel:'13:00-14:00',hideLabel:true},
	       				       {columnWidth:0.6,xtype:'form',items:[{xtype:'numberfield',width:'35',id:'txt_13_14',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}]
	       				}]
	       			},{layout:'column',
	       				items:[{columnWidth:0.4,xtype:'checkbox',id:'check_14_15',boxLabel:'14:00-15:00',hideLabel:true},
	       				       {columnWidth:0.6,xtype:'form',items:[{xtype:'numberfield',width:'35',id:'txt_14_15',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}]
	       				}]
	       			},{layout:'column',
	       				items:[{columnWidth:0.4,xtype:'checkbox',id:'check_15_16',boxLabel:'15:00-16:00',hideLabel:true},
	       				       {columnWidth:0.6,xtype:'form',items:[{xtype:'numberfield',width:'35',id:'txt_15_16',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}]
	       				}]
	       			},{layout:'column',
	       				items:[{columnWidth:0.4,xtype:'checkbox',id:'check_16_17',boxLabel:'16:00-17:00',hideLabel:true},
	       				       {columnWidth:0.6,xtype:'form',items:[{xtype:'numberfield',width:'35',id:'txt_16_17',fieldLabel: '最大可预约人数',allowDecimals : false,allowNegative : false,maxValue : 99,maxText : '最大可预约99人'}]
	       				}]
	       			}*/]
		   		}
		    }],[
		        {text:'保存配置', 	handler : onReservTimeClicked, 	formBind:true},
				{text:'返回', 	handler: function(){reservTimeWindow.close();}}
		    ]);
			
			function reservTimeDetails(){
				//查询预约可办理时段
				requestAjax('<%=basePath%>/confManager/subscribeTime_loadReservTime', '',function(sRet){
					var array = sRet.field1;
					//判断哪个时段,则把哪个时段的配置上
					for ( var i = 0; i < array.length; i++) {
						var data = array[i];
						switch (data['reserv_begin_time']) {
						case '09':
							Ext.getCmp('check_09_10').setValue(true);
							Ext.getCmp('txt_09_10').setValue(data['reserv_max_num']);
							break;
						case '10':
							Ext.getCmp('check_10_11').setValue(true);
							Ext.getCmp('txt_10_11').setValue(data['reserv_max_num']);
							break;
						case '11':
							Ext.getCmp('check_11_12').setValue(true);
							Ext.getCmp('txt_11_12').setValue(data['reserv_max_num']);
							break;
						case '12':
							Ext.getCmp('check_12_13').setValue(true);
							Ext.getCmp('txt_12_13').setValue(data['reserv_max_num']);
							break;
						case '13':
							Ext.getCmp('check_13_14').setValue(true);
							Ext.getCmp('txt_13_14').setValue(data['reserv_max_num']);
							break;
						case '14':
							Ext.getCmp('check_14_15').setValue(true);
							Ext.getCmp('txt_14_15').setValue(data['reserv_max_num']);
							break;
						case '15':
							Ext.getCmp('check_15_16').setValue(true);
							Ext.getCmp('txt_15_16').setValue(data['reserv_max_num']);
							break;
						case '16':
							Ext.getCmp('check_16_17').setValue(true);
							Ext.getCmp('txt_16_17').setValue(data['reserv_max_num']);
							break;
						default:
							break;
						}
					}
				});
				reservTimeWindow.open();
			}
			function onReservTimeClicked(){
				var submitData = {};
				var listData = [];
				//从9-17点判断------------------------------------------------
				var check_09_10 = Ext.getCmp('check_09_10').getValue();
				var txt_09_10 = Ext.getCmp('txt_09_10').getValue();
				if(check_09_10){
					if(''==txt_09_10){
						Ext.Msg.alert('提示','9-10点区间的预约人数未填写');
						return;
					}
					var data = {};
					data['reserv_begin_time'] = '090000';
					data['reserv_end_time'] = '100000';
					data['reserv_max_num'] = txt_09_10;
					listData.push(data);
				}
				var check_10_11 = Ext.getCmp('check_10_11').getValue();
				var txt_10_11 = Ext.getCmp('txt_10_11').getValue();
				if(check_10_11){
					if(''==txt_10_11){
						Ext.Msg.alert('提示','10-11点区间的预约人数未填写');
						return;
					}
					var data = {};
					data['reserv_begin_time'] = '100000';
					data['reserv_end_time'] = '110000';
					data['reserv_max_num'] = txt_10_11;
					listData.push(data);
				}
				var check_11_12 = Ext.getCmp('check_11_12').getValue();
				var txt_11_12 = Ext.getCmp('txt_11_12').getValue();
				if(check_11_12){
					if(''==txt_11_12){
						Ext.Msg.alert('提示','11-12点区间的预约人数未填写');
						return;
					}
					var data = {};
					data['reserv_begin_time'] = '110000';
					data['reserv_end_time'] = '120000';
					data['reserv_max_num'] = txt_11_12;
					listData.push(data);
				}
				var check_12_13 = Ext.getCmp('check_12_13').getValue();
				var txt_12_13 = Ext.getCmp('txt_12_13').getValue();
				if(check_12_13){
					if(''==txt_12_13){
						Ext.Msg.alert('提示','12-13点区间的预约人数未填写');
						return;
					}
					var data = {};
					data['reserv_begin_time'] = '120000';
					data['reserv_end_time'] = '130000';
					data['reserv_max_num'] = txt_12_13;
					listData.push(data);
				}
				var check_13_14 = Ext.getCmp('check_13_14').getValue();
				var txt_13_14 = Ext.getCmp('txt_13_14').getValue();
				if(check_13_14){
					if(''==txt_13_14){
						Ext.Msg.alert('提示','13-14点区间的预约人数未填写');
						return;
					}
					var data = {};
					data['reserv_begin_time'] = '130000';
					data['reserv_end_time'] = '140000';
					data['reserv_max_num'] = txt_13_14;
					listData.push(data);
				}
				var check_14_15 = Ext.getCmp('check_14_15').getValue();
				var txt_14_15 = Ext.getCmp('txt_14_15').getValue();
				if(check_14_15){
					if(''==txt_14_15){
						Ext.Msg.alert('提示','14-15点区间的预约人数未填写');
						return;
					}
					var data = {};
					data['reserv_begin_time'] = '140000';
					data['reserv_end_time'] = '150000';
					data['reserv_max_num'] = txt_14_15;
					listData.push(data);
				}
				var check_15_16 = Ext.getCmp('check_15_16').getValue();
				var txt_15_16 = Ext.getCmp('txt_15_16').getValue();
				if(check_15_16){
					if(''==txt_15_16){
						Ext.Msg.alert('提示','15-16点区间的预约人数未填写');
						return;
					}
					var data = {};
					data['reserv_begin_time'] = '150000';
					data['reserv_end_time'] = '160000';
					data['reserv_max_num'] = txt_15_16;
					listData.push(data);
				}
				var check_16_17 = Ext.getCmp('check_16_17').getValue();
				var txt_16_17 = Ext.getCmp('txt_16_17').getValue();
				if(check_16_17){
					if(''==txt_16_17){
						Ext.Msg.alert('提示','16-17点区间的预约人数未填写');
						return;
					}
					var data = {};
					data['reserv_begin_time'] = '160000';
					data['reserv_end_time'] = '170000';
					data['reserv_max_num'] = txt_16_17;
					listData.push(data);
				}
				//-----------------------------------------------------判断结束
				submitData['listData'] = listData;
				requestAjax('<%=basePath%>/confManager/subscribeTime_saveReservTime', submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','配置时段成功',function(id){
						reservTimeWindow.close();
					});
				});
			}
		} 
	</script>
  </head>
  <body>
  	<div id="reservBusinessWindow"></div>
  	<div id="reservTimeWindow"></div>
  </body>
</html>
