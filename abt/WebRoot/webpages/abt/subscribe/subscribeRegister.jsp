<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>预约登记</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="selfcss/common.css" />
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.js"></script>
		<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/MultiSelect.js"></script>
		<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		var systemUnits=pagereturn.field1;
		var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};
		var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree_a = treeGenerator_a.generate(false,false,false,false);
		var treepanel_a = new Ext.tree.TreePanel({
		  	rootVisible : true,
		  	height:280,
		  	root:tree_a
		});
		
		var custinfoTypeStore = new Ext.data.ArrayStore({ 
			data:[["0","0-身份证"],["1","1-银行卡卡号"],["2","2-客户号"],["3","3-护照号码"]],
			fields : ['key','value']
		});
		var reservZoneStore = new Ext.data.ArrayStore({ 
			data:[["0","0-北京"],["1","1-上海"]],
			fields : ['key','value']
		});
		
		var businessStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>confManager/business_queryBusinessSmall?query_rules=4',
			autoLoad:true,
			root   : 'field1',
			fields:['bs_id','bs_name_ch']
		});
		
		var reservTimeStore = new Ext.data.ArrayStore({ 
			data:[["08:00:00-09:00:00","08:00:00-09:00:00"],["09:00:00-10:00:00","09:00:00-10:00:00"],
			      ["10:00:00-11:00:00","10:00:00-11:00:00"],["14:00:00-15:00:00","14:00:00-15:00:00"],
			      ["15:00:00-16:00:00","15:00:00-16:00:00"],["16:00:00-17:00:00","16:00:00-17:00:00"],
			      ["17:00:00-18:00:00","17:00:00-18:00:00"],["18:00:00-19:00:00","18:00:00-19:00:00"],
			      ["19:00:00-20:00:00","19:00:00-20:00:00"],["20:00:00-21:00:00","20:00:00-21:00:00"],
			      ["21:00:00-22:00:00","21:00:00-22:00:00"],["22:00:00-23:00:00","22:00:00-23:00:00"]],
			fields : ['key','value']
		});
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);

	/**
	 * @Title: loadPage 
	 * @Description: 显示机构参数配置界面
	 */
	function loadPage() {
		var clientWidth = document.body.clientWidth;
		var clientHeight = document.body.clientHeight;

		var form = new Ext.FormPanel({
			labelWidth:80,
			frame:true,
			title:'预约登记',
			bodyStyle:'padding5px 5px 0',
			width:350,
			id:'form',
			monitorValid :true,
			defaults:{width:250},
			defaultType:'textfield',
			items:[{
				xtype : 'combotree', 
				name:'branchCom',
				id:'branchCom',
				allowBlank : false,
				fieldLabel:'选定机构',
				tree: treepanel_a,
				allowUnLeafClick:false
			},{
				xtype:'datefield',
				fieldLabel:'预约开始日期',
				name:'reserv_begin_date',
				id:'reserv_begin_date',
				format:'Y-m-d',
				//value:new Date(new Date().setDate(new Date().getDate() + 1)),
				allowBlank : false
			},{
				xtype:'datefield',
				fieldLabel:'预约结束日期',
				name:'reserv_end_date',
				id:'reserv_end_date',
				format:'Y-m-d',
				//value:new Date(new Date().setDate(new Date().getDate() + 1)),
				allowBlank : false
			}/*,{
				xtype:'combo',
				fieldLabel:'预约时间',
				name:'reserv_time',
				id:'reserv_time',
				triggerAction : 'all',
				mode: 'local',
				editable:false, 
				store:reservTimeStore, 	
				displayField:'value', 
				valueField:'value',
				allowBlank : false
			}*/,{
				fieldLabel:'预约开始时间',
				name:'reserv_begin_time',
				id:'reserv_begin_time',
				xtype: 'timefield',
				format:'H:i:s',
				increment:30,
				minValue: '08:00:00',
				maxValue: '18:00:00'
			},{
				fieldLabel:'预约结束时间',
				name:'reserv_end_time',
				id:'reserv_end_time',
				xtype: 'timefield',
				format:'H:i:s',
				increment:30,
				minValue: '08:00:00',
				maxValue: '18:00:00'
			},{
				xtype:'combo',
				//xtype:'multiSelect',
				fieldLabel:'选定业务',
				name:'reserv_bs_id',
				id:'reserv_bs_id',
				triggerAction : 'all',
				mode: 'local',
				editable:false, 
				store:businessStore, 	
				displayField:'bs_name_ch', 
				valueField:'bs_id',
				allowBlank : true
			},{
				xtype:'combo',
				//xtype:'multiSelect',
				fieldLabel:'预约区域',
				name:'reserv_zone',
				id:'reserv_zone',
				triggerAction : 'all',
				mode: 'local',
				editable:false, 
				store:reservZoneStore, 	
				displayField:'value', 
				valueField:'key',
				allowBlank : false
			},{
				xtype:'combo',
				fieldLabel:'证件类型',
				name:'custinfo_type',
				id:'custinfo_type',
				triggerAction : 'all',
				mode: 'local',
				editable:false, 
				store:custinfoTypeStore, 	
				displayField:'value', 
				valueField:'key',
				value:0,
				allowBlank : false
			},{
				fieldLabel:'客户姓名',
				name:'custinfo_name',
				id:'custinfo_name',
				allowBlank : false
			},{
				xtype:'numberfield',
				fieldLabel:'卡号/账号',
				name:'account',
				id:'account',
				allowBlank : false
			},{
				xtype:'numberfield',
				fieldLabel:'证件号码',
				name:'custinfo_num',
				id:'custinfo_num',
				allowBlank : false
			},{
				xtype:'numberfield',
				fieldLabel: '预约人手机号',
				id: 'phone_no',
				name: 'phone_no',
				allowBlank : false
			},{
				xtype: 'radiogroup',
				fieldLabel: '短信通知客户',
				id: 'sms_customer',
				name: 'sms_customer',
				items:[
					{boxLabel:'是',name:'sms_customer',inputValue:1,checked:true},       
					{boxLabel:'否',name:'sms_customer',inputValue:0}       
				]
			},{
				fieldLabel: '预约号',
				hidden:true,
				id: 'reserv_id',
				name: 'reserv_id',
				readOnly:true
			}],
			buttons:[
				{text:'提交',formBind:true, handler : onSubmit},
				{text:'重置', handler : reset}
			]
		});
		
		form.render(document.body);
		
		function onSubmit(){
			var submitData = {};
			submitData['reserv_bs_id'] = Ext.getCmp('reserv_bs_id').getValue();
			submitData['reserv_begin_date'] = Ext.getCmp('reserv_begin_date').getValue();
			submitData['reserv_end_date'] = Ext.getCmp('reserv_end_date').getValue();
			submitData['reserv_begin_time'] = Ext.getCmp('reserv_begin_time').getValue();
			submitData['reserv_end_time'] = Ext.getCmp('reserv_end_time').getValue();
			submitData['reserv_zone'] = Ext.getCmp('reserv_zone').getValue();
			submitData['branch'] = Ext.getCmp('branchCom').passField.value;
			submitData['custinfo_type'] = Ext.getCmp('custinfo_type').getValue();
			submitData['custinfo_num'] = Ext.getCmp('custinfo_num').getValue();
			submitData['phone_no'] = Ext.getCmp('phone_no').getValue();
			submitData['account'] = Ext.getCmp('account').getValue();
			submitData['sms_customer'] = Ext.getCmp('sms_customer').getValue().inputValue;
			submitData['custinfo_name'] = Ext.getCmp('custinfo_name').getValue();
			
			requestAjax('<%=basePath%>subscribe/subscribe_submit', submitData,function(sRet){
				Ext.MessageBox.alert('预约成功','预约号:'+sRet.reserv_id,function(id){
					Ext.getCmp('reserv_id').show();
					Ext.getCmp('reserv_id').setValue(sRet.reserv_id);
				});
			});
		}
		
		function reset(){
			Ext.getCmp('branchCom').setPassValue('');
			Ext.getCmp('branchCom').setValue('');
			Ext.getCmp('reserv_begin_date').setValue('');
			Ext.getCmp('reserv_end_date').setValue('');
			Ext.getCmp('reserv_bs_id').setValue('');
			Ext.getCmp('reserv_begin_time').setValue('');
			Ext.getCmp('reserv_end_time').setValue('');
			Ext.getCmp('custinfo_type').setValue('');
			Ext.getCmp('custinfo_num').setValue('');
			Ext.getCmp('phone_no').setValue('');
			Ext.getCmp('sms_customer').setValue('');
			Ext.getCmp('custinfo_name').setValue('');
		}
	}
</script>
	</head>
	<body scroll="yes">
		<div id="panel"></div>
	</body>
</html>
