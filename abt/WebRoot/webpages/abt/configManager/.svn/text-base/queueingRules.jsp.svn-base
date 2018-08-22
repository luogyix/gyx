<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
		<link rel="stylesheet" type="text/css" href="extjs/ux/css/LockingGridView.css" />
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.js"></script>
		<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="extjs/ux/LockingGridView.js"></script>
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
	  
	var QMInfoStore = new Ext.data.JsonStore({
		url    : '<%=basePath%>/confManager/qm_queryQMInfoSmall',
		autoLoad:true,
		root   : 'field1',
		fields:['qm_num','qm_name']
	});
	
	var windowStore = new Ext.data.JsonStore({ 
		url    : '<%=basePath%>/confManager/queueingRules_queryWindowByQMNum.action',
		root   : 'field1',
		fields : ['win_num','win_name']
	});
	
	var winBsStore = new Ext.data.JsonStore({ 
		fields : ['bs_id','bs_name_ch','bs_level','shareqm_flag']
	});
	
	var winQmStore = new Ext.data.JsonStore({
		fields : ['queuetype_id','queuetype_name','queuetype_level']
	});
	  
	Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.onReady(loadPage);
	function loadPage() {
		var clientWidth = document.body.clientWidth;
		var clientHeight = document.body.clientHeight;
		
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
						title : '条件窗口',
						items : [ {
							columnWidth : .3,
							labelAlign : 'right',
							labelWidth : 80,
							layout : 'form',
							items : [ {//排队机
								xtype : 'combo',
								name : 'qmCombo',
								id:'qmCombo',
								fieldLabel : '排队机',
								emptyText : '请选择排队机...',
								anchor : '100%',
								editable:false,
								store : QMInfoStore,
								displayField : 'qm_name',
								valueField : 'qm_num',
								mode : 'local',
								triggerAction : 'all',
								listeners:{
									'select': function(){
										//alert(Ext.getCmp('qm').getRawValue());//获取显示数据
										var qm_num = Ext.getCmp('qmCombo').getValue();//获取实际value
										windowStore.load({params:{value:qm_num}});
									},
									'change': function(){
										queryFlag = false;
										Ext.getCmp('winCombo').setValue('');
										winBsStore.removeAll();
										winQmStore.removeAll();
									}
								}
							}]
						}, {
							columnWidth : .35,
							labelAlign : 'right',
							labelWidth : 80,
							layout : 'form',
							items : [ {//窗口
								xtype : 'combo',
								name : 'winCombo',
								id : 'winCombo',
								fieldLabel : '窗口',
								emptyText : '请选择窗口...',
								editable:false,
								anchor : '100%',
								store : windowStore,
								displayField : 'win_name',
								valueField : 'win_num',
								mode : 'local',
								triggerAction : 'all',
								anchor : '90%',
								listeners:{
									'select': function(){
										//alert(Ext.getCmp('qm').getRawValue());//获取显示数据
										queryFlag = false;
										winBsStore.removeAll();
										winQmStore.removeAll();
									}
								}
							} ]
						}, {
							columnWidth : .35,
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
												Ext.Msg.alert('警告','请先选择一个窗口');
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
												Ext.Msg.alert('警告','请先选择一个窗口');
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
			var qm_num = Ext.getCmp('qmCombo').getValue();
			var win_num = Ext.getCmp('winCombo').getValue();
			if(qm_num == ''){
				Ext.MessageBox.alert('<s:text name="common.info.title"/>','请选择一个排队机');
				return;
			}
			if(win_num == ''){
				Ext.MessageBox.alert('<s:text name="common.info.title"/>','请选择一个窗口');
				return;
			}
			submitdata = {'qm_num':qm_num,'win_num':win_num};
			requestAjax('<%=basePath%>/confManager/queueingRules_queryQueueingRules',submitdata,function(sRet){
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
		    				viewConfig: {forceFit: true},
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
  			submitData['qm_num'] = Ext.getCmp('qmCombo').getValue();
			submitData['win_num'] = Ext.getCmp('winCombo').getValue();
			
			Ext.MessageBox.confirm('提示','确定提交已选配置',function(id){
					if(id == 'yes'){
						requestAjax('<%=basePath%>/confManager/queueingRules_addWinBusiness', submitData,function(sRet){
							bsWindow.close();
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','配置成功',function(id){
								window.parent.queryMessage();
								//查询数据
								requestAjax('<%=basePath%>/confManager/queueingRules_queryQueueingRules.action',submitdata,function(sRet){
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
		    				viewConfig: {forceFit: true},
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
			  			submitData['qm_num'] = Ext.getCmp('qmCombo').getValue();
						submitData['win_num'] = Ext.getCmp('winCombo').getValue();
						requestAjax('<%=basePath%>/confManager/queueingRules_addWinQueue', submitData,function(sRet){
							queueWindow.close();
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','配置成功',function(id){
								window.parent.queryMessage();
								//查询数据
								requestAjax('<%=basePath%>/confManager/queueingRules_queryQueueingRules.action',submitdata,function(sRet){
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
	}
</script>
	</head>

	<body scroll="no">
		<div id="panel"></div>
		<div id="busiWindow"></div>
		<div id="winQueueWindow"></div>
	</body>
</html>
