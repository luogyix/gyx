<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String template_id = (String)request.getSession().getAttribute("template");
String branch;
if(template_id!=null){
	branch = template_id;
}else{
	branch = user.getUnitid();
}
String hostIp = user.getHostip();
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>设备信息配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagetoafaquery.js"></script>
	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		//排队机编号
		var qmNumStore=new Ext.data.SimpleStore({ 
			data:[["1","1"],["2","2"],["3","3"],["4","4"],["5","5"],["6","6"],["7","7"],["8","8"],["9","9"],["10","10"]],
			fields : ['key','value']
		});
		//排队机状态
		var qmStatusStore=new Ext.data.SimpleStore({ 
			data:[["1","1-可用"],["0","0-不可用"]],
			fields : ['key','value']
		});
		
		
		var labelTextStyleStore=new Ext.data.ArrayStore({ 
			data:[
				["0","0-常规"],
				["1","1-粗体"],
				["2","2-斜体"]
			],
			fields : ['key','value']
		});
		
		
		/*
			,
			listeners : {  
				load : function(store,records){
					var a = store.data.items;
					this.removeAll();
					var rec = new (store.recordType)();
					//写入数据
					rec.set('custtype_i', '默认');
					rec.set('custtype_e', '默认');
					store.add(rec);
					store.add(a);
				}
			}
		*/
		
		
		//设备类型
		/**<%-- var deviceTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
			root   : 'field1',
			fields : ['dictValue','dictValueDesc'],
			autoLoad:true --%>
		});
		*/
		
		//设备界面参数
		var systemParamStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/systemParameter_querySystemParameter',
			root   : 'field1',
			fields:['parameter_id','parameter_name','branch','default_flag','parameter_flag'],
			autoLoad:true
		});
		
		
		//设备类型
		var deviceTypeStore = new Ext.data.SimpleStore({ 
			data:[
			      ["04","手持设备"],
			      ["10","填单机"],
			      ["11","自助设备"]
			],
			fields : ['dictValue','dictValueDesc']
		});
		
		var initParam = new Ext.data.SimpleStore({ 
			data:[
			      ["00","请选择设备类型"]
			],
			fields : ['parameter_id','parameter_name']
		});
		
		
		//设备校验类型
		var device_chk_typeStore=new Ext.data.SimpleStore({ 
			data:[["序列号校验","2"],["MAC校验","1"]],
			fields : ['key','value']
		});
		//关联排队机号
		var qm_numStore = new Ext.data.JsonStore({
			fields:['qm_num','qm_name'],
			data:['','']
		});
		
		//qm_numStore.load({params:{'branch':'<%=branch%>'}});
/**
 * @Title: loadPage 
 * @Description: 显示填单机信息界面
 */	
		function loadPage(){
			
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '填单机信息配置', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combo', name:'devicetype',fieldLabel:'设备类型',forceSelection:true,hiddenName:'devicetype',displayField:'dictValueDesc',valueField:'dictValue',store: deviceTypeStore}}
				],
				[
				{iconCls: "x-image-query", 			        id:'01',    text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'}, //删除
				{iconCls: "x-image-reset",id:'05', text:'<s:text name="common.button.reset"/>'}//重置
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>confManager/deviceMsg_queryDeviceInfoPage',
				['device_num','device_name','branch','devicetype','device_model','deviceadm_num','deviceadm_name','terminalnum','device_ip','device_oid','device_status','device_param_id',
				 'device_chk_type','propertynum','device_chk_info','qm_num','qm_ip','check_code','interface_id','location'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'设备编号',dataIndex:'device_num',width:100},
				{header:'设备名称',dataIndex:'device_name',width:100},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'设备类型',dataIndex:'devicetype',width:100,renderer:function(value){
					return getDictValue(value,deviceTypeStore,'dictValue','dictValueDesc');
       			}},
       			{header:'设备界面',dataIndex:'interface_id',width:100,renderer:function(value){
          		 	return getDictValue(value,systemParamStore,'parameter_id','parameter_name');//翻译字段方法
       			}},
				{header:'设备型号',dataIndex:'device_model',width:100},
				{header:'虚拟柜员号',dataIndex:'deviceadm_num',width:120},
				{header:'设备主管姓名',dataIndex:'deviceadm_name',width:100},
				{header:'终端号',dataIndex:'terminalnum',width:80},
				{header:'设备IP',dataIndex:'device_ip',width:180},
				{header:'资产编号',dataIndex:'propertynum',width:180},
				{header:'设备地址',dataIndex:'location',width:180},
				{header:'设备唯一标识',dataIndex:'device_oid',width:180},
				{header:'设备状态',dataIndex:'device_status',width:100,renderer:function(value){return value=='1'?'可用':'不可用'}},
				{header:'设备参数',dataIndex:'device_param_id',width:200},
				{header:'设备校验类型',dataIndex:'device_chk_type',width:200,renderer:function(value){
          		 	return getDictValue(value,device_chk_typeStore,'value','key');
       			}},
				{header:'关联排队机',dataIndex:'qm_num',width:200},
				{header:'关联排队机IP',dataIndex:'qm_ip',width:200},
				{header:'设备校验信息',dataIndex:'device_chk_info',width:200},
				{header:'自助激活校验码',dataIndex:'check_code',width:200}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加设备信息', 550, 390, 100, 2,
				[
				{colIndex:0, field:{xtype : 'combo', name:'device_num', 	fieldLabel:'设备编号',editable:false, 	allowBlank : false, store:qmNumStore, 	displayField:'value', valueField:'key',listeners:{
		            select:function(combo,record,index){
		                //对应的处理函数
		                Ext.getCmp('device_name_add').setValue(record.get('value')+'号设备');
		            }
		        }}},
				{colIndex:0, field:{xtype : 'textfield', name:'device_name', 	fieldLabel:'设备名称',id:'device_name_add', 	allowBlank : false, blankText:'请输入设备名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'combo', name:'devicetype', hiddenName:'devicetype',	fieldLabel:'设备类型',id:'devicetype_add', editable:false, 	allowBlank : false, store:deviceTypeStore,displayField:'dictValueDesc',valueField:'dictValue',listeners:{
		            select:function(combo,record,index){
		            	if(record.get('dictValue') == '04'){
		            		Ext.getCmp('terminalnum_add').hide();//隐藏终端号组件
		            		Ext.getCmp('interface_id_add').hide();//隐藏设备界面组件
		            		Ext.getCmp('device_param').hide();//隐藏设备参数组件
		            		Ext.getCmp('terminalnum_add').allowBlank = true;
		            		Ext.getCmp('interface_id_add').allowBlank = true;
		            	}else if(record.get('dictValue') == '10'){
		            		systemParamStore.reload({params:{'branch':'<%=branch%>','parameter_flag':'10'}});
		            		Ext.getCmp('terminalnum_add').hide();//隐藏终端号组件
		            		Ext.getCmp('terminalnum_add').allowBlank = true;
		            		Ext.getCmp('interface_id_add').show();//展示设备界面组件
		            		Ext.getCmp('device_param').show();//展示设备参数组件
			            		
			            }else if(record.get('dictValue') == '11'){
			            	systemParamStore.reload({params:{'branch':'<%=branch%>','parameter_flag':'11'}});
		            		Ext.getCmp('terminalnum_add').show();//展示终端号组件
		            		Ext.getCmp('interface_id_add').show();//展示设备界面组件
		            		Ext.getCmp('device_param').show();//展示设备参数组件
			            }
		        }}}},
				{colIndex:0, field:{xtype : 'textfield', name:'device_model', 	fieldLabel:'设备型号',id:'device_model_add', 	allowBlank : true, blankText:'请输入设备型号',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'deviceadm_num', 	fieldLabel:'虚拟柜员号',id:'deviceadm_num_add', 	allowBlank : false, blankText:'请输入设备管理柜员号',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'deviceadm_name', fieldLabel:'设备主管姓名', id:'deviceadm_name_add', 	allowBlank : false, blankText:'请输入设备管理柜员名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'terminalnum',id:'terminalnum_add', fieldLabel:'终端号', 	allowBlank : false, blankText:'请输入终端号',maxLength :20,	maxLengthText : '长度不能大于20位'}},
				{colIndex:0, field:{xtype : 'combo',     name:'interface_id',  hiddenName:'interface_id', fieldLabel:'设备界面',     id:'interface_id_add',      editable:false, 	allowBlank : true, hidden:true,  store:systemParamStore, displayField:'parameter_name', valueField:'parameter_id'}},
				{colIndex:0, field:{xtype : 'textarea', name:'location',id:'location_add', fieldLabel:'设备地址', 	allowBlank : false, blankText:'请输入设备地址',maxLength :256,	maxLengthText : '长度不能大于256位'}},
				{colIndex:1, field:{layout:'column',fieldLabel:'设备参数',id:'device_param',items:[{xtype : 'textfield',readOnly:true,editable:true, name:'device_param_id',id:'device_param_id_add', 	fieldLabel:'设备参数',	allowBlank : true},
					{xtype:'button',text:'选择',width:40,listeners:{"click":function(){
						if(Ext.getCmp('devicetype_add').getValue()=='11'){
							paramDetails();	
						}else if(Ext.getCmp('devicetype_add').getValue()=='10'){
							formDetails();
						}
				}}}]}},
				{colIndex:1, field:{xtype : 'combo', name:'device_chk_type', hiddenName:'device_chk_type',	fieldLabel:'设备校验类型',editable:false, 	allowBlank : false, store:device_chk_typeStore, 	displayField:'key', valueField:'value', value:'2'}},
				{colIndex:1, field:{xtype : 'combo', name:'qm_num', hiddenName:'qm_num',id:'qm_num_add_id',fieldLabel:'关联排队机',editable:true, forceSelection:true, store:qm_numStore, 	displayField:'qm_name', valueField:'qm_num'}},
				{colIndex:1, field:{xtype : 'textfield', name:'qm_ip', 	fieldLabel:'关联排队机IP',id:'qm_ip_add', 	allowBlank : true, blankText:'请输入关联排队机IP',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:1, field:{xtype : 'textarea', name:'device_chk_info', 	fieldLabel:'设备校验信息',id:'device_chk_info_add', 	allowBlank : true, blankText:'请输入设备校验信息',maxLength :1000, emptyText:'校验信息若不输入，则在设备第一次初始化时注册',maxLengthText : '长度不能大于1000位'}},
				{colIndex:1, field:{xtype : 'textfield', name:'propertynum', 	fieldLabel:'资产编号',id:'propertynum_add', 	allowBlank : false , blankText:'请输入资产编号',maxLength :10,	maxLengthText : '长度不能大于10位'}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'device_status', 	boxLabel:'启用',checked : true}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',100
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑设备信息',550, 360, 100, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', id:'branch', name:'branch', 	fieldLabel:'机构号',readOnly:true,hidden:true}},
				{colIndex:0, field:{xtype : 'combo',	fieldLabel:'设备编号', readOnly:true,editable:false,name:'device_num', 	allowBlank : false, store:qmNumStore, 	displayField:'value', valueField:'key',listeners:{
		            select:function(combo,record,index){
		                //对应的处理函数
		                Ext.getCmp('device_name_edit').setValue(record.get('value')+'号设备');
		            }
		        }}},
		        {colIndex:0, field:{xtype : 'combo', name:'devicetype',  hiddenName:'devicetype',	id:'devicetype_edit',fieldLabel:'设备类型',editable:false, readOnly:true,allowBlank : false, store:deviceTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0, field:{xtype : 'textfield', name:'device_name', value:'',	fieldLabel:'设备名称', id:'device_name_edit',	allowBlank : false, blankText:'请输入设备名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'device_model', value:'',	fieldLabel:'设备型号', id:'devicemodel_edit',	allowBlank : true, blankText:'请输入设备型号',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				//{colIndex:0, field:{xtype : 'textfield', name:'deviceadm_num', value:'',	fieldLabel:'虚拟柜员号', id:'deviceadm_num_edit',	allowBlank : false, blankText:'请输入设备管理柜员号',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : "textfield", name:'deviceadm_num', fieldLabel:'虚拟柜员号',id:'deviceadm_num_edit', allowBlank : false, blankText:'请输入设备管理柜员号',maxLength:60, maxLengthText:'长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'deviceadm_name', value:'',	fieldLabel:'设备主管姓名', id:'deviceadm_name_edit',	allowBlank : true, blankText:'请输入设备主管姓名',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'terminalnum', fieldLabel:'终端号',id:'terminalnum_edit',maxLength :20,	maxLengthText : '长度不能大于20位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'device_oid', fieldLabel:'设备唯一标识', id:'device_oid_edit',hidden:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'device_ip', fieldLabel:'设备IP', id:'device_ip_edit',hidden:true}},
				{colIndex:0, field:{xtype : 'combo', name:'interface_id', hiddenName:'interface_id', fieldLabel:'设备界面', id:'interface_id_edit', editable:false,  allowBlank : true, hidden:true, store:systemParamStore,displayField:'parameter_name',valueField:'parameter_id'}},
				{colIndex:0, field:{xtype : 'textarea', name:'location',id:'location_edit', fieldLabel:'设备地址', 	allowBlank : false, blankText:'请输入设备地址',maxLength :256,	maxLengthText : '长度不能大于256位'}},
				{colIndex:0, field:{xtype : 'combo',     name:'interface_id',  hiddenName:'interface_id', fieldLabel:'设备界面',     id:'interface_id_edit',      editable:false, 	allowBlank : true, hidden:true,  store:systemParamStore, displayField:'parameter_name', valueField:'parameter_id'}},
				{colIndex:1, field:{layout:'column',fieldLabel:'设备参数',id:'device_param_edit',items:[{xtype : 'textfield',readOnly:true,editable:true, name:'device_param_id',id:'device_param_id_edit', 	fieldLabel:'设备参数',	allowBlank : true},
 					{xtype:'button',text:'选择',width:40,listeners:{"click":function(){
 						if(Ext.getCmp('devicetype_edit').getValue()=='11'){
 							Ext.getCmp('device_param_edit').show();//展示设备参数组件
 							paramDetails();	
 						}else if(Ext.getCmp('devicetype_edit').getValue()=='10'){
 							Ext.getCmp('device_param_edit').show();//展示设备参数组件
 							formDetails();
 						}
 				}}}]}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'device_status', 	boxLabel:'启用'}},
				{colIndex:1, field:{xtype : 'combo', name:'device_chk_type', hiddenName:'device_chk_type',	fieldLabel:'设备校验类型',editable:false, 	allowBlank : false, store:device_chk_typeStore, 	displayField:'key', valueField:'value'}},
				{colIndex:1, field:{xtype : 'combo', name:'qm_num', hiddenName:'qm_num',id:'qm_num_edit_id',fieldLabel:'关联排队机',editable:true, forceSelection:true, store:qm_numStore, 	displayField:'qm_name', valueField:'qm_num'}},
				{colIndex:1, field:{xtype : 'textfield', name:'qm_ip', 	fieldLabel:'关联排队机IP',id:'qm_ip_edit', 	allowBlank : true, blankText:'请输入关联排队机IP',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:1, field:{xtype : 'textarea', name:'device_chk_info', 	fieldLabel:'设备校验信息',id:'device_chk_info_edit', 	allowBlank : true, blankText:'请输入设备校验信息',maxLength :1000,emptyText:'校验信息若不输入，则在设备第一次初始化时注册',	maxLengthText : '长度不能大于1000位'}},
				{colIndex:1, field:{xtype : 'textfield', name:'propertynum', 	fieldLabel:'资产编号',id:'propertynum_edit', 	allowBlank : false , blankText:'请输入资产编号',maxLength :10,	maxLengthText : '长度不能大于10位'}},
				{colIndex:1, field:{xtype : 'hidden', name:'check_code'}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',100
			);
			
			/**
			 * 自助参数弹出窗
			 */
			function paramDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['parameter_id','branch','default_flag','time_pageout','time_addshow','time_mainshow']
			    });
			    var detailData = [
					{header:'设备参数ID',dataIndex:'parameter_id',width:100},
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
						if(value!=""){
							return value=='1' ? '默认':'非默认';
						}else{
							return value;
						}
					}},
					{header:'定时关机时间',dataIndex:'time_shutdown',width:100},
					{header:'界面超时时间',dataIndex:'time_pageout',width:100},
					//{header:'广告播放时间',dataIndex:'time_addshow',width:100},
					{header:'主界面显示时间',dataIndex:'time_mainshow',width:100}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('paramDetailsWindow','设备参数列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'设备参数信息',
			   			layout:'column',
			   			items:[{
			       			columnWidth:1,
			       			items:[{
			       				xtype:'grid',
			       				id:'paramGrid',
			       				store:detailStore,
			       				cm:detailColModel,
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
			    
			    Ext.getCmp('paramGrid').on('rowdblclick',function(grid, rowindex,e){
		  			var record = grid.getStore().getAt(rowindex);
		  			detailsWindow.close();
		  			Ext.getCmp('device_param_id_add').setValue(record.data['parameter_id']);
		    		Ext.getCmp('device_param_id_edit').setValue(record.data['parameter_id']);
			  	});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '5';
			    requestAjax('<%=basePath%>confManager/selfDevParamAction_queryDevParam.action',submitDetail, function(sRet){
			    	detailStore.loadData(sRet.field1);
				});
			}
			/**
			 * 填单机参数弹出窗
			 */
			function formDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:[
							'branch',
							'param_id',
							'messageoutlay',
							'create_date',
							'explain',
							'queuetype',
							'pfs_password',
							'shutdown_time'
					]
			    });
			    var detailData = [
					{header:'机构号',dataIndex:'branch',width:100 },
				{header:'排队方式',	dataIndex:'queuetype',width:100,renderer:function(value){
					if(value == "0"){
						return "任意方式";
					}else if(value != "0"){
						return value=='1'?'先填单':'先取号';
					}
					
				}},
				{header:'参数ID',dataIndex:'param_id',width:100 },
				{header:'填单机管理密码',dataIndex:'pfs_password',width:100},
				{header:'收费标准',	dataIndex:'messageOutlay',width:117,renderer:function(value){
					if(value == "0"){
						return "免费";
					}else if(value == "1"){
						return "包月";
					}
					return value == "2" ? "包年" : "用户可选";
				}},
				{header:'关机时间',dataIndex:'shutdown_time',width:80},
				{header:'创建日期',dataIndex:'create_date',width:150},
				{header:'备注',dataIndex:'explain',width:200}
				
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('paramDetailsWindow','设备参数列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'设备参数信息',
			   			layout:'column',
			   			items:[{
			       			columnWidth:1,
			       			items:[{
			       				xtype:'grid',
			       				id:'paramGrid',
			       				store:detailStore,
			       				cm:detailColModel,
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
			    
			    Ext.getCmp('paramGrid').on('rowdblclick',function(grid, rowindex,e){
		  			var record = grid.getStore().getAt(rowindex);
		  			detailsWindow.close();
		  			Ext.getCmp('device_param_id_add').setValue(record.data['param_id']);
		    		Ext.getCmp('device_param_id_edit').setValue(record.data['param_id']);
			  	});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '5';
			    requestAjax('<%=basePath%>/dataAnalysis/formFill_queryParamAll',submitDetail, function(sRet){
			    	detailStore.loadData(sRet.field1);
				});
			}
			/**
			* @Title:onButtonClicked
			* @Description:触发"查询"，"重置"，"添加"，"修改"，"删除"的选择语句
			*/
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						systemParamStore.load({params:{'branch':'<%=branch%>','parameter_flag':'11'}});
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
						break;
					case 1:
						var submitData = conditionPanel.getFields();
						requestAjax('<%=basePath%>/confManager/qm_queryQMInfoPage',submitData, function(sRet){
							if(sRet.field1.length>0){
								qm_numStore.removeAll();
								for (var i = 0; i < sRet.field1.length; i++) { 
									var item = (sRet.field1)[i];
									var data ={qm_name:item.qm_name,qm_num:item.qm_num};
									var p = new qm_numStore.recordType(data);
									qm_numStore.insert(0,p);
								}
							}
						});
						addwindow.open();
						Ext.getCmp('interface_id_add').allowBlank=true;
						Ext.getCmp('interface_id_add').hide();
						Ext.getCmp('device_param').hide();
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
						if(!isEdit){
							break;
						}
						var submitData = conditionPanel.getFields();
						requestAjax('<%=basePath%>/confManager/qm_queryQMInfoPage',submitData, function(sRet){
							if(sRet.field1.length>0){
								qm_numStore.removeAll();
								for (var i = 0; i < sRet.field1.length; i++) { 
									var item = (sRet.field1)[i];
									var data ={qm_name:item.qm_name,qm_num:item.qm_num};
									var p = new qm_numStore.recordType(data);
									qm_numStore.insert(0,p);
								}
							}
						});
						if(records[0].get('devicetype')==10||records[0].get('devicetype')==11){
							// 	allowBlank : false, blankText:'请输入终端号',
							if(records[0].get('devicetype')==11){
								Ext.getCmp('terminalnum_edit').show();
								Ext.getCmp('terminalnum_edit').allowBlank=false;
								Ext.getCmp('terminalnum_edit').blankText='请输入终端号';
							}else{
								Ext.getCmp('terminalnum_edit').hide();
								Ext.getCmp('terminalnum_edit').allowBlank=true;
							}
		                	if(Ext.getCmp('deviceadm_num_edit').allowBlank == false){
		                		var txtLabel = Ext.getCmp('deviceadm_num_edit').getEl().parent().parent().first(); 
		                		txtLabel.dom.innerHTML = Ext.getCmp('deviceadm_num_edit').fieldLabel + '<font color=red>*</font>:';
		                	}
		                	Ext.getCmp('deviceadm_num_edit').allowBlank = true;
		                	if(Ext.getCmp('deviceadm_name_edit').allowBlank == true){
		                		var txtLabel = Ext.getCmp('deviceadm_name_edit').getEl().parent().parent().first(); 
		                		txtLabel.dom.innerHTML = Ext.getCmp('deviceadm_name_edit').fieldLabel + '<font color=red>*</font>:';
		                	}
		                	Ext.getCmp('deviceadm_name_edit').allowBlank = false;
		                	Ext.getCmp('interface_id_edit').hide();
		                	Ext.getCmp('interface_id_edit').allowBlank=true;
		                	if(records[0].get('devicetype')==11){
		                		systemParamStore.load({params:{'branch':'<%=branch%>','parameter_flag':'11'}});
		                		Ext.getCmp('interface_id_edit').show();
		                		//Ext.getCmp('interface_id_edit').allowBlank=false;
		                		//var txtLabel=Ext.getCmp('interface_id_edit').getEl().parent().parent().parent().first();
		                		//txtLabel.dom.innerHTML = Ext.getCmp('interface_id_edit').fieldLabel + '<font color=red>*</font>:';
		                	}
		                }else{
		                	Ext.getCmp('terminalnum_edit').hide();
							Ext.getCmp('terminalnum_edit').allowBlank=true;
		                	if(Ext.getCmp('deviceadm_num_edit').allowBlank == true){
		                		var txtLabel = Ext.getCmp('deviceadm_num_edit').getEl().parent().parent().first(); 
		                		txtLabel.dom.innerHTML = Ext.getCmp('deviceadm_num_edit').fieldLabel + ':';
		                	}
		                	Ext.getCmp('deviceadm_num_edit').allowBlank = false;
		                	if(Ext.getCmp('deviceadm_name_edit').allowBlank == false){
		                		var txtLabel = Ext.getCmp('deviceadm_name_edit').getEl().parent().parent().first(); 
		                		txtLabel.dom.innerHTML = Ext.getCmp('deviceadm_name_edit').fieldLabel + ':';
		                	}
		                	Ext.getCmp('deviceadm_name_edit').allowBlank = true;
		                	Ext.getCmp('interface_id_edit').hide();
		                }
						
						editwindow.open();
						editwindow.updateFields(records[0]);//回显用的
						Ext.getCmp("qm_num_edit_id").setValue(Ext.getCmp("qm_num_edit_id").getValue());
						if(Ext.getCmp('devicetype_edit').getValue()=='04'){
 							Ext.getCmp('device_param_edit').hide();//隐藏设备参数组件
 							Ext.getCmp('interface_id_edit').hide();//隐藏设备界面选择组件
 						}else if(Ext.getCmp('devicetype_edit').getValue()=='10'){
 							systemParamStore.reload({params:{'branch':'<%=branch%>','parameter_flag':'10'}});
 							Ext.getCmp('device_param_edit').show();//展示设备参数组件
 							Ext.getCmp('interface_id_edit').show();//展示设备界面选择组件
 						}else if(Ext.getCmp('devicetype_edit').getValue()=='11'){
 							systemParamStore.reload({params:{'branch':'<%=branch%>','parameter_flag':'11'}});
 							Ext.getCmp('device_param_edit').show();//展示设备参数组件
 							Ext.getCmp('interface_id_edit').show();//展示设备界面选择组件
 						}
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['device_num','branch','devicetype']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/deviceMsg_delDeviceInfo',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					    break;
					case 4:
						conditionPanel.reset();
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
				createCheckBoxValue('device_status',submitData);
				requestAjax('<%=basePath%>confManager/deviceMsg_editDeviceInfo',submitData,function(sRet){
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
						createCheckBoxValue('device_status',submitData);
						requestAjax('<%=basePath%>confManager/deviceMsg_addDeviceInfo', submitData,
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
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="paramDetailsWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>
