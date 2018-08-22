<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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

		<title>叫号规则配置</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="selfcss/common.css" />
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.js"></script>
		<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
		<script type="text/javascript" src="extjs/ux/CheckColumn.js"></script>
		<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
		<script type="text/javascript">
	var pagereturn=${actionresult};
	
	var bussinessStore = new Ext.data.JsonStore({
		url    : '<%=basePath%>/confManager/business_queryBusinessSmall?query_rules=4',
		autoLoad:true,
		root   : 'field1',
		fields:['bs_id','bs_name_ch']
	});
	  
	var queuetypeStore = new Ext.data.JsonStore({
		url    : '<%=basePath%>/confManager/queuetype_queryQueueTypeSmall?query_rules=4',
		autoLoad:true,
		root   : 'field1',
		fields:['queuetype_id','queuetype_name','queuetype_level','queuetype_prefix','queuetype_prefix_num']
	});
	
	var winBsStore = new Ext.data.JsonStore({ 
		fields : ['bs_id','bs_name_ch','bs_level','shareqm_flag']
	});
	
	var winQmStore = new Ext.data.JsonStore({
		fields : ['queuetype_id','queuetype_name','queuetype_level']
	});
	var systemParamStore = new Ext.data.JsonStore({
		url    : '<%=basePath%>/confManager/systemParameter_querySystemParameter',
		root   : 'field1',
		fields:['parameter_id','parameter_name','branch','default_flag','parameter_flag']
	});
	Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.onReady(loadPage);
	
	function loadPage() {
		var clientWidth = document.body.clientWidth;
		var clientHeight = document.body.clientHeight;

		//-------------------------------------------------------------------------编号弹出窗 开始				
		//系统参数弹出框form
		var systemParamForm = new Ext.FormPanel({
			labelWidth: 75,
			labelAlign : 'right',
			//bodyStyle:'padding:5px 5px 0',
			//defaults: {width: 210},
	        //defaultType: 'numberfield',
	        frame : false,
	        items: [{
	            xtype: 'grid',
	            //title: '参数列表',
	            height: 240,
	            //autoHeight : true, 
				name:'systemParamGrid',
				id:'systemParamGrid',
				store: systemParamStore,
				autoScroll :true,
				viewConfig: {forceFit: true},
				columns: [
				  	new Ext.grid.RowNumberer({width: 30}),
				  	//'systemParam_id','systemParam_name','systemParam_note','branch','systemParam_flag'
		            {header: '参数编号', width: 80, dataIndex: 'parameter_flag', hidden:true},
		            {header: '参数编号', width: 80, dataIndex: 'parameter_id', hidden:true},
		            {header: '参数名称', width: 150, dataIndex: 'parameter_name', sortable:true},
		            {header: '参数来源', width: 100, dataIndex: 'branch', sortable:true},
		            {header: '是否默认', width: 100, dataIndex: 'default_flag', sortable:true,renderer:function(value){
						return value=='1'?'默认':'非默认';
						}
		            }
		        ],
		        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
				tbar: new Ext.Toolbar({
					id : 'tool',
					items: [{
		    			text : '<b>新建参数</b>',
		    			icon:'images/49.png',
		    			tooltip : '<b>提示</b><br/>创建新参数',
		    			handler : function(b,e){
		    				Ext.getCmp('parameter_name').setValue('');
		    				Ext.getCmp('default_flag').setValue(false);
		    				Ext.getCmp('operateMode').setValue('add');
		    				systemParam_add_window.setTitle('<span style="font-weight:normal">新建参数</span>');
		    				systemParam_add_window.show();
		    			}
		    		},'-',{
		    			text : '<b>修改参数</b>',
		    			icon:'images/49.png',
		    			tooltip : '<b>提示</b><br/>修改参数',
		    			handler : function(b,e){
		    				var record = Ext.getCmp('systemParamGrid').getSelectionModel().getSelected();
		    				if (Ext.isEmpty(record)) {
		    					Ext.Msg.alert('提示', '请先选中一个参数!');
		    					return;
		    				}
		    				var brno = '<%=brno%>';
		    				if(record.get('branch')!=brno){
		    					Ext.Msg.alert('提示', '无法修改非本机构的参数!');
		    					return;
		    				}
		    				systemParamAddForm.getForm().loadRecord(record);
		    				systemParam_add_window.setTitle('<span style="font-weight:normal">修改参数</span>');
		    				Ext.getCmp('operateMode').setValue('edit');
		    				systemParam_add_window.show();
		    			}
		    		},'-',{
		    			text : '<b>删除参数</b>',
		    			icon:'images/49.png',
		    			tooltip : '<b>提示</b><br/>删除所选参数',
		    			handler : function(b,e){
		    				var record = Ext.getCmp('systemParamGrid').getSelectionModel().getSelected();
		    				if (Ext.isEmpty(record)) {
		    					Ext.Msg.alert('提示', '请先选中要删除的参数!');
		    					return;
		    				}
		    				var brno = '<%=brno%>';
		    				if(record.get('branch')!=brno){
		    					Ext.Msg.alert('提示', '无法删除非本机构的参数!');
		    					return;
		    				}
		    				Ext.Msg.confirm('提示','确定要删除选中的参数吗?',function(btn, text){
								if (btn == 'yes') {
									var submitdata = {};
									submitdata['parameter_id'] = record.get('parameter_id');
									requestAjax('<%=basePath%>confManager/systemParameter_delSystemParameterById',submitdata,function(sRet){
										Ext.MessageBox.alert('提示','删除成功',function(id){
											systemParamStore.load({params:{'branch':brno,'parameter_flag':'02'}});
										});
									});
								}
							});
		    			}
		    		},'-',{
		    			text : '<b>复制参数</b>',
		    			icon:'images/49.png',
		    			tooltip : '<b>提示</b><br/>复制已有参数至新参数项',
		    			handler : function(b,e){
		    				var record = Ext.getCmp('systemParamGrid').getSelectionModel().getSelected();
		    				if (Ext.isEmpty(record)) {
		    					Ext.Msg.alert('提示', '请先选中要复制的参数!');
		    					return;
		    				}
		    				systemParamAddForm.getForm().loadRecord(record);
		    				systemParam_add_window.setTitle('<span style="font-weight:normal">复制参数</span>');
		    				Ext.getCmp('operateMode').setValue('copy');
		    				systemParam_add_window.show();
		    			}
		    		}]
				})
	        }]
		});
		
		//弹出的属性框
		var systemParamWindow = new Ext.Window({
			layout : 'fit', // 设置窗口布局模式
			width: 400,
		    height : 300,
		    resizable: false,
		    id:'systemParamWindow',
		    draggable : true,
		    closeAction : 'hide',
		    closable : false, 
		    modal : true, //无法失去焦点
			title : '<span style="font-weight:normal">参数弹出窗</span>', // 窗口标题
			collapsible : false, // 是否可收缩
			titleCollapse : false,
			maximizable : false, // 设置是否可以最大化
			buttonAlign : 'right',
			border : false, // 边框线设置
			animCollapse : true,
			pageY : clientHeight/2 - 300/2, // 页面定位Y坐标
			pageX : clientWidth / 2 - 400 / 2, // 页面定位X坐标
			animateTarget : Ext.getBody(),
			constrain : true,
			items : [systemParamForm], // 嵌入的表单面板
			buttons : [{
				text : '关闭', // 按钮文本
				handler : function() { // 按钮响应函数
					systemParamWindow.hide();
				}
			}]
		});
		var systemParamAddForm = new Ext.FormPanel({
			labelWidth: 60,
			labelAlign : 'right',
			bodyStyle:'padding:5px 5px 0',
	        defaultType: 'textfield',
	        frame : false,
	        items: [{
	            fieldLabel: '参数名称',
	            name:'parameter_name',
	            id:'parameter_name',
	            blankText:'请输入参数名称',
	            maxLength : 50,
	            maxLengthText : '长度不能大于50位',
	            allowBlank : false
	        },{
	            fieldLabel: '',
	            boxLabel: '默认',
	            xtype:'checkbox',
	            name:'default_flag',
	            id:'default_flag'
	        },{//隐藏区域
	            id:'parameter_id',
	            name:'parameter_id',
	            xtype:'hidden'
	        },{//隐藏区域
	            id:'parameter_flag',
	            name:'parameter_flag',
	            xtype:'hidden'
	        },{//隐藏区域
	            id:'branch',
	            name:'branch',
	            xtype:'hidden'
	        },{
				id : 'operateMode',
				name : 'operateMode',
				hidden : true
			}]
		});
		var systemParam_add_window = new Ext.Window({
			layout : 'fit', // 设置窗口布局模式
			width: 250,
		    height : 150,
		    resizable: false,
		    id:'systemParam_add_window',
		    draggable : true,
		    closeAction : 'hide',
		    closable : false, 
		    modal : true, //无法失去焦点
			title : '<span style="font-weight:normal">新建参数</span>', // 窗口标题
			collapsible : false, // 是否可收缩
			titleCollapse : false,
			maximizable : false, // 设置是否可以最大化
			buttonAlign : 'right',
			border : false, // 边框线设置
			animCollapse : true,
			pageY : clientHeight/2-150/2, // 页面定位Y坐标
			pageX : clientWidth/2 - 250 / 2, // 页面定位X坐标
			animateTarget : Ext.getBody(),
			constrain : true,
			items : [systemParamAddForm], // 嵌入的表单面板
			buttons : [{ // 窗口底部按钮配置
				text : '提交', // 按钮文本
				id:'btn_go',
				handler : function() { // 按钮响应函数
						if (!systemParamAddForm.form.isValid()) {
							return;
						}
						var array=systemParamAddForm.getForm().getFieldValues();
						array['parameter_flag'] = '02';
						var mode = Ext.getCmp('operateMode').getValue();
						if (mode == 'add'){
							//通知后台的方法.添加
							Ext.Ajax.request({
								url: '<%=basePath%>confManager/systemParameter_addSystemParameter',  
								params: {strJson:Ext.encode(array)},
								success:function(response,opt){
									var responseText = response.responseText;
									if(responseText.indexOf('false')!=-1){
										Ext.MessageBox.alert('提示', '此机构已存在默认的参数');
										return;
									}
									systemParam_add_window.hide();
									Ext.MessageBox.alert('提示', '添加参数成功');
									var brno = '<%=brno%>';
									systemParamStore.load({params:{'branch':brno,'parameter_flag':'02'}});
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
									systemParam_add_window.hide();
									Ext.MessageBox.alert('提示', '修改参数成功');
									var brno = '<%=brno%>';
									systemParamStore.load({params:{'branch':brno,'parameter_flag':'02'}});
								}
							});
						}else if(mode == 'copy'){
							//复制系统
							requestAjax('<%=basePath%>confManager/systemParameter_copySystemParameter',Ext.encode(array),function(sRet){
								Ext.MessageBox.alert('提示','复制成功',function(id){
									systemParam_add_window.hide();
									var brno = '<%=brno%>';
									systemParamStore.load({params:{'branch':brno,'parameter_flag':'02'}});
								});
							});
						}
				}
			},{ // 窗口底部按钮配置
				text : '关闭', // 按钮文本
				handler : function() { // 按钮响应函数
					systemParam_add_window.hide();
				}
			}]
		});
	//-------------------------------------------------------------------------编号弹出窗 开始	
		var busiData = [ new Ext.grid.RowNumberer({
			width : 33
		}), {
			xtype : 'gridcolumn',
			dataIndex : 'bs_id',
			header : '业务编号',
			sortable : true
		}, {
			xtype : 'gridcolumn',
			dataIndex : 'bs_name_ch',
			header : '业务名称',
			sortable : true
		},{
			xtype : 'gridcolumn',
			dataIndex : 'bs_level',
			header : '优先级',
			value:0,
			sortable : true
		},{
			xtype : 'gridcolumn',
			dataIndex : 'shareqm_flag',
			header : '共享排队机标识',
			renderer:function(value){
				if(value=='1'){
					return '共享';
				}else{
					return '不共享';
				}
			},
			sortable : true
		}];
		
		var busiColModel = new Ext.grid.ColumnModel(busiData);
		
		var busiwinData = [ new Ext.grid.RowNumberer({
			width : 33
		}), {
			xtype : 'gridcolumn',
			dataIndex : 'queuetype_id',
			header : '队列编号',
			sortable : true
		}, {
			xtype : 'gridcolumn',
			dataIndex : 'queuetype_name',
			header : '队列名称',
			sortable : true
		}, {
			xtype : 'gridcolumn',
			dataIndex : 'queuetype_level',
			header : '优先级',
			sortable : true
		} ];
		var busiwinColModel = new Ext.grid.ColumnModel(busiwinData);
		
			
		var queryFlag = false;
		var bsWindow;
		var queueWindow;
		var panel = new Ext.Panel({
			layout : 'absolute',
			frame : true,
			applyTo : 'panel',
			height : clientHeight + 40,
			modal : true,
			closable : true,
			items : [ {
				layout : 'column',
				frame : true,
				height : clientHeight,
				width : clientWidth,
				items : [{
					columnWidth : 1,
					layout : 'form',
					bodyStyle : 'padding:5 8 0 8',
					labelAlign : 'left',
					items : [ {
						width : clientWidth - 27,
						height : 60,
						xtype : 'fieldset',
						layout : 'column',
						title : '参数编号',
						items : [ {
							columnWidth : .5,
							labelAlign : 'right',
							labelWidth : 80,
							layout : 'form',
							items : [ {//排队机
								xtype:'combo',
    	   						name:'systemParamCombo', 
    	   						fieldLabel:'参数名',
	    	   					emptyText: '请选择参数...',
	    	   					anchor:'90%',
	    	   					store:systemParamStore,
	    	   					displayField:'parameter_name',
	    	   					valueField:'parameter_id',
	    	   					mode: 'local',
	    	   					triggerAction: 'all',
	    	   					editable:false,
	    	   					id:'systemParamCombo',
	    	   					listeners:{
									'select': function(){
										queryFlag = false;
										winBsStore.removeAll();
										winQmStore.removeAll();
									}
								}
							}]
						},{
							columnWidth : .2,
							layout : 'form',
							items : [ {//查询按钮
								text:'参数配置',
								width : 80,
								xtype : 'button',
								handler : function() {
									systemParamWindow.show();
								}
							}]
						},{
							columnWidth : .2,
							layout : 'form',
							items : [ {//查询按钮
								text : '查询',
								width : 80,
								xtype : 'button',
								handler : function() {
									queryParams();
								}
							}]
						}]
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					bodyStyle : 'padding:5 8 0 8',
					items : [ {
						width : clientWidth - 27,
						xtype : 'fieldset',
						layout : 'column',
						title : '规则配置',
						id : 'gzpz',
						items : [{
							columnWidth : 1 / 2,
							layout : 'form',
							items : [ {
								xtype : 'fieldset',
								layout : 'form',
								anchor : '99%',
								title : '窗口可办业务',
								items : [ {
									xtype : 'grid',
									name : 'winbsgrid',
									id : 'winbsgrid',
									store : winBsStore,
									width : (clientWidth / 2) - 50,
									height : (clientHeight / 2) + 30,
									viewConfig : {
										forceFit : true
									},
									stripeRows : true,
									doLayout : function() {
										this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true))
									},
									cm : busiColModel,
									clicksToEdit : 1,
									tbar : [{
										text : '<span style="font-size:15px;">配置</span>',
										width:80,
										height:25,
										iconCls:'x-image-system1',
										handler : function() {
											if (queryFlag == false) {
												Ext.Msg.alert('警告','请先选择一个编号');
												return;
											} else {
												businessDetails();
											}
										}
									}]
								} ]
							} ]
						},
						{
							columnWidth : 1 / 2,
							layout : 'form',
							items : [ {
								xtype : 'fieldset',
								layout : 'form',
								anchor : '99%',
								title : '窗口服务队列',
								items : [ {
									xtype : 'editorgrid',
									name : 'winqmgrid',
									id : 'winqmgrid',
									store : winQmStore,
									width : (clientWidth / 2) - 50,
									height : (clientHeight / 2) + 30,
									viewConfig : {
										forceFit : true
									},
									stripeRows : true,
									doLayout : function() {
										this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true))
									},
									cm : busiwinColModel,
									clicksToEdit : 1,
									tbar : [{
										text : '<span style="font-size:15px;">配置</span>',
										width:80,
										height:25,
										iconCls:'x-image-system1',
										handler : function() {
											if (queryFlag == false) {
												Ext.Msg.alert('警告','请先选择一个编号');
												return;
											} else {
												//客户类型与队列关系绑定的grid
												queueDetails();
											}
										}
									}]
								}]
							} ]
						} ]
					} ]
				} ]
			} ]
		});

        /**
		* 查询数据
		*/
		function queryParams(){
			var parameter_id = Ext.getCmp('systemParamCombo').getValue();
			if(parameter_id == ''){
				Ext.MessageBox.alert('<s:text name="common.info.title"/>','请先选择一个编号');
				return;
			}
			submitdata = {'parameter_id':parameter_id};
			requestAjax('<%=basePath%>confManager/queueingRules_queryQueueingRulesById',submitdata,function(sRet){
				//这次操作结束后才进行这步操作
				queryFlag = true;
				winBsStore.loadData(sRet.win_bs);
				winQmStore.loadData(sRet.win_qm);
			});
		}
        
		function businessDetails(){
			var mydata = [new Ext.grid.RowNumberer({header:'选择',width:33}),
				{xtype: 'checkcolumn',dataIndex: 'bound',header: '绑定',sortable: true,width:60,align: 'center'},
				{xtype: 'gridcolumn',dataIndex: 'bs_id',header: '业务编号',sortable: true,width:100,align: 'center'},
				{xtype: 'gridcolumn',dataIndex: 'bs_name_ch',header: '业务名称',sortable: true,width:100,align: 'center'},
				{xtype: 'gridcolumn',dataIndex: 'bs_level',header: '业务优先级',sortable: true,width:100,align: 'center',editor: { xtype: 'numberfield',maxValue:'99999'}},
				{xtype: 'checkcolumn',dataIndex: 'shareqm_flag',header: '共享叫号机标识',sortable: true,width:120,align: 'center'}
			];
			var resultStore = new Ext.data.JsonStore({
				fields : ['bound','bs_id','bs_name_ch','bs_level','shareqm_flag']
          	});
			bsWindow = new SelfFormWindowSetWidth('busiWindow','业务列表',600, 355, 555, 1, [555],[{
		   		colIndex:0,
		   		field:{
		   			xtype:'fieldset',
		   			layout:'column',
		   			items:[{
		       			columnWidth:1,
		       			items:[{
		       				xtype: 'editorgrid',
		    				name:'bsGrid',
		    				id:'bsGrid',
		    				store: resultStore,
		    				viewConfig: {forceFit: false},
		    			    cm: new Ext.grid.ColumnModel(mydata),
		    			    sm:new Ext.grid.CheckboxSelectionModel({  
		    			      	    dataIndex: "bound",
		    			      	  	header: '绑定',
		    			      	    singleSelect: false  
		    			      	}),
		       				height:240,
		       				iconCls:'icon-grid',
		       				stripeRows : true,
		       				doLayout:function(){
		       					this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true));
		       				},
		       				clicksToEdit: 1 //点击几次可修改
		       			}]
		   			}]
		   		}
		    }],[
		        {text:'保存配置', 	handler : onBSClicked, 	formBind:true},
				{text:'返回', 	handler: function(){bsWindow.close();}}
		    ]);
		    
		    var dataList = [];
		    var business = bussinessStore.data.items;
          	for(var i=0;i<business.length;i++){
          		var tempData = {
          			'bs_id':business[i].data['bs_id'],
          			'bs_name_ch':business[i].data['bs_name_ch'],
				'bs_level':business[i].data['bs_level'],
          			'shareqm_flag':false
          		};
          		dataList.push(tempData);
          	}
		  	//这里要与生成的全部数据进行比对添加...好难
			var array = winBsStore.data.items;
			
			for(var j = 0;j<array.length;j++){
				for(var i = 0;i<dataList.length;i++){
					if(array[j].data['bs_id'] == dataList[i]['bs_id']){
						//这两条数据是一个
						dataList[i]['bound'] = true;
						dataList[i]['bs_level']=array[j].data['bs_level'];
						dataList[i]['shareqm_flag'] = array[j].data['shareqm_flag']==1?true:false;
						break;
					}
				}
			}
			resultStore.loadData(dataList);
			bsWindow.open();
		}
		function onBSClicked(){
			var record = Ext.getCmp('bsGrid').getStore().data.items;
			var submitData = {};
			var listData = [];
			for(var i = 0;i<record.length;i++){
				if(record[i].data['bound']){
					var data = {};
					data['bs_id'] = record[i].data['bs_id'];
					if(record[i].data['bs_level'] == ''&&record[i].data['bs_level'] != 0){
						Ext.MessageBox.alert('系统提示',record[i].data['bs_name_ch']+'的优先级不可为空')
						return;
					}
					data['bs_level'] = record[i].data['bs_level'];
					data['shareqm_flag'] = record[i].data['shareqm_flag'];
					listData.push(data);
				}
			}
			submitData['listData'] = listData;
			var parameter_id = Ext.getCmp('systemParamCombo').getValue();
			submitData['parameter_id'] = parameter_id;
			
			Ext.MessageBox.confirm('提示','确定提交已选配置',function(id){
					if(id == 'yes'){
						requestAjax('<%=basePath%>confManager/queueingRules_addWinBusinessById', submitData,function(sRet){
							bsWindow.close();
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','配置成功',function(id){
								window.parent.queryMessage();
								//查询数据
								requestAjax('<%=basePath%>confManager/queueingRules_queryQueueingRulesById',submitdata,function(sRet){
									queryParams();
								});
							});
							//busiWindow.close();
						});
					}
				}
			);
		}
		
		function queueDetails(){
			var mydata = [new Ext.grid.RowNumberer({header:'选择',width:33}),
				{xtype: 'checkcolumn',dataIndex: 'bound',header: '绑定',sortable: true,width:60,align: 'center'},
				{xtype: 'gridcolumn',dataIndex: 'queuetype_id',header: '队列编号',sortable: true,width:100,align: 'center'},
				{xtype: 'gridcolumn',dataIndex: 'queuetype_name',header: '队列名称',sortable: true,width:100,align: 'center'},
				{xtype: 'gridcolumn',dataIndex: 'queuetype_prefix',header: '队列前缀字母',sortable: true,width:100,align: 'center'},
				{xtype: 'gridcolumn',dataIndex: 'queuetype_prefix_num',header: '队列前缀数字',sortable: true,width:100,align: 'center'},
				{xtype: 'gridcolumn',dataIndex: 'queuetype_level',header: '队列优先级',sortable: true,width:100,align: 'center',editor: { xtype: 'numberfield',maxValue:'99999'}}
			];
			//,'queuetype_prefix','queuetype_prefix_num'
			var resultStore = new Ext.data.JsonStore({
				fields : ['bound','queuetype_id','queuetype_name','queuetype_level','queuetype_prefix','queuetype_prefix_num']
          	});
		    queueWindow = new SelfFormWindowSetWidth('winQueueWindow','队列列表',600, 355, 555, 1, [555],[{
		   		colIndex:0,
		   		field:{
		   			xtype:'fieldset',
		   			layout:'column',
		   			items:[{
		       			columnWidth:1,
		       			items:[{
		       				xtype: 'editorgrid',
		    				name:'qmGrid',
		    				id:'qmGrid',
		    				store: resultStore,
		    				viewConfig: {forceFit: false},
		    			    cm: new Ext.grid.ColumnModel(mydata),
		    			    sm:new Ext.grid.CheckboxSelectionModel({  
		    			      	    dataIndex: "bound",
		    			      	  	header: '绑定',
		    			      	    singleSelect: false  
		    			      	}),
		       				height:240,
		       				iconCls:'icon-grid',
		       				stripeRows : true,
		       				doLayout:function(){
		       					this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true));
		       				},
		       				clicksToEdit: 1 //点击几次可修改
		       			}]
		   			}]
		   		}
		    }],[
		        {text:'保存配置', 	handler : onQMClicked, 	formBind:true},
				{text:'返回', 	handler: function(){queueWindow.close();}}
		    ]);
		    
		    var dataList = [];
		    var queue = queuetypeStore.data.items;
          	for(var i=0;i<queue.length;i++){
          		var tempData = {
          			'queuetype_id':queue[i].data['queuetype_id'],
          			'queuetype_name':queue[i].data['queuetype_name'],
          			'queuetype_prefix':queue[i].data['queuetype_prefix'],
          			'queuetype_prefix_num':queue[i].data['queuetype_prefix_num'],
          			'queuetype_level':queue[i].data['queuetype_level']
          		};
          		dataList.push(tempData);
          	}
		  	//这里要与生成的全部数据进行比对添加...好难
			var array = winQmStore.data.items;
			
			for(var j = 0;j<array.length;j++){
				for(var i = 0;i<dataList.length;i++){
					if(array[j].data['queuetype_id'] == dataList[i]['queuetype_id']){
						//这两条数据是一个
						dataList[i]['bound'] = true;
						dataList[i]['queuetype_level'] = array[j].data['queuetype_level'];
						break;
					}
				}
			}
			resultStore.loadData(dataList);
		    queueWindow.open();
		}
		function onQMClicked(){
			Ext.MessageBox.confirm('提示','确定提交已选配置',
				function(id){
					if(id == 'yes'){
						var record = Ext.getCmp('qmGrid').getStore().data.items;
						var submitData = {};
						var listData = [];
						for(var i = 0;i<record.length;i++){
							if(record[i].data['bound']){
								var data = {};
								data['queuetype_id'] = record[i].data['queuetype_id'];
								if(record[i].data['queuetype_level'] == ''){
									Ext.MessageBox.alert('系统提示',record[i].data['queuetype_name']+'的优先级不可为空')
									return;
								}
								data['queuetype_level'] = record[i].data['queuetype_level'];
								listData.push(data);
							}
						}
						submitData['listData'] = listData;
						var parameter_id = Ext.getCmp('systemParamCombo').getValue();
						submitData['parameter_id'] = parameter_id;
						requestAjax('<%=basePath%>confManager/queueingRules_addWinQueueById', submitData,function(sRet){
							queueWindow.close();
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','配置成功',function(id){
								window.parent.queryMessage();
								//查询数据
								requestAjax('<%=basePath%>confManager/queueingRules_queryQueueingRulesById',submitdata,function(sRet){
									queryParams();
								});
							});
							//busiWindow.close();
						});
					}
				}
			);
		}
		//---------------------------------建立页面--------------------------------------------------
		function buildLayout() {
			var viewport = new Ext.Viewport({
				layout : 'absolute',
				items : [ panel ]
			});
		}
		;
		buildLayout();
		
		//启动加载
		var brno = '<%=brno%>';
		systemParamStore.load({params:{'branch':brno,'parameter_flag':'02'}});
	}
</script>
	</head>

	<body scroll="no">
		<div id="panel"></div>
		<div id="busiWindow"></div>
		<div id="winQueueWindow"></div>
	</body>
</html>
