<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String template_id = (String)request.getSession().getAttribute("template");
String brno;
if(template_id!=null){
	brno = template_id;
}else{
	brno = user.getUnitid();
}

boolean queue_rules_flag = (Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.QUEUERULESFLAG);
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>窗口配置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.js"></script>
	<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		/**
		 * 翻译字段用store
		 */
		var callRuleStore=new Ext.data.SimpleStore({ 
			data:[["1-静态规则","1"],["2-动态规则","2"]],
			fields : ['value','key']
		});
		var apprflagStore=new Ext.data.SimpleStore({ 
			data:[["1-硬叫号评价器","1"],["2-柜面评价器","2"]],
			fields : ['value','key']
		});
		var winServiceStatusStore=new Ext.data.SimpleStore({ 
			data:[["0-离线","0"],["1-空闲","1"],["2-正在办理","2"],["3-暂停服务","3"]],
			fields : ['value','key']
		});
		var winNumStore=new Ext.data.SimpleStore({ 
			data:[["1","1"],["2","2"],["3","3"],["4","4"],["5","5"],["6","6"],["7","7"],["8","8"],["9","9"],["10","10"],["11","11"],["12","12"],["13","13"],["14","14"],["15","15"],["16","16"],["17","17"],["18","18"],["19","19"],["20","20"]],
			fields : ['key','value']
		});
		var systemParamStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/systemParameter_querySystemParameter',
			root   : 'field1',
			fields:['parameter_id','parameter_name','branch','default_flag','parameter_flag']
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示窗口维护界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '窗口配置', 120, 0,
				[
				],
				[
				{iconCls: "x-image-query", 			        id:'01',    text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>confManager/window_queryWindowPage',
				['branch','win_num','qm_num','ip','win_oid','call_rule','status','screen_id','softcall_id','access_id','win_service_status','apprflag','parameter_id'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:150},
				{header:'窗口号',dataIndex:'win_num',width:150},
				{header:'排队机编号',dataIndex:'qm_num',width:150},
				{header:'终端IP',dataIndex:'ip',width:150},
				{header:'设备唯一标识',dataIndex:'win_oid',width:150},
				{header:'叫号规则',dataIndex:'call_rule',width:150,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,callRuleStore,'key','value');//翻译字段方法
       			 }
				},
				{header:'所用叫号规则',dataIndex:'parameter_id',width:150,hidden:<%=!queue_rules_flag%>,renderer:function(value){
          		 	return getDictValue(value,systemParamStore,'parameter_id','parameter_name');//翻译字段方法
       			}},
				{header:'评价器标识',dataIndex:'apprflag',width:150,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,apprflagStore,'key','value');//翻译字段方法
       			 }
				},
				{header:'状态',dataIndex:'status',width:150,renderer:function(value){return value=='1'?'1-可用':'0-不可用'}},
				{header:'条屏样式编号',dataIndex:'screen_id',width:150},
				{header:'软叫号配置编号',dataIndex:'softcall_id',width:150},
				{header:'柜台服务状态',dataIndex:'win_service_status',width:150,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,winServiceStatusStore,'key','value');//翻译字段方法
       			 }}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加窗口信息', 300, 300, 140, 1,
				[
				//{colIndex:0, field:{xtype : 'checkboxgroup', name:'win_num1', hiddenName:'win_num1',fieldLabel:'窗口号',columns: 10,items: [
				//	{boxLabel: '1', name: '1'},
				//	{boxLabel: '2', name: '2', checked: true},
				//	{boxLabel: '3', name: '3'}]
				//}},
				{colIndex:0, field:{xtype : 'combo', name:'win_num',id:'win_num_add', hiddenName:'win_num', 	fieldLabel:'窗口号',allowBlank : false, store:winNumStore, editable:true,	displayField:'value', valueField:'key',regex:/^[0-9]*$/,regexText:'窗口号只能输入数字',maxLength:3,maxLengthText:'窗口号最大长度3位'}},
				//{colIndex:0, field:{xtype : 'textfield', name:'qm_num', 	fieldLabel:'排队机编号',	allowBlank : false,blankText:'请选择排队机编号',maxLength : 20,maxLengthText : '长度不能大于20位'}},
				//{colIndex:0, field:{xtype : 'textfield', name:'ip',id:'ip_add', 	fieldLabel:'窗口IP',	allowBlank : false,blankText:'请输入窗口IP',maxLength : 40,maxLengthText : '长度不能大于40位',regex:/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/,regexText:'请输入正确的IP'}},
				{colIndex:0, field:{xtype : 'combo', name:'apprflag',id:'apprflag_add', hiddenName:'apprflag', 	fieldLabel:'评价器标识',allowBlank : false, store:apprflagStore, editable:false,	displayField:'value', valueField:'key'}},
				{colIndex:0, field:{xtype : 'combo', name:'parameter_id', hiddenName:'parameter_id', fieldLabel:'所用叫号规则',editable:false, store:systemParamStore, 	displayField:'parameter_name', valueField:'parameter_id',hidden:<%=!queue_rules_flag%>}},
				{colIndex:0, field:{xtype : 'combo', name:'call_rule',id:'call_rule_add', hiddenName:'call_rule', 	fieldLabel:'叫号规则类型',allowBlank : false, store:callRuleStore, editable:false,	displayField:'value', valueField:'key',value:1}},
				{colIndex:0, field:{layout:'column',fieldLabel:'排队机编号<font style="color:red">*</font>',items:[{xtype : 'textfield', name:'qm_num',id:'qm_num_add',readOnly:true, 	fieldLabel:'排队机编号',	allowBlank : false,blankText:'请输入排队机编号',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){queueMachineDetails();}}}]}},
				{colIndex:0, field:{layout:'column',fieldLabel:'软叫号配置编号',items:[{xtype : 'textfield', name:'softcall_id',id:'softcall_id_add',readOnly:true, 	fieldLabel:'排队机编号',	allowBlank : true,blankText:'请选择软叫号配置编号',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){softCallDetails();}}}]}},
				{colIndex:0, field:{layout:'column',fieldLabel:'条屏样式编号',items:[{xtype : 'textfield', name:'screen_id',id:'screen_id_add',readOnly:true, 	fieldLabel:'条屏样式编号',	allowBlank : true,blankText:'请选择条屏样式编号',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){screenDetails();}}}]}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'status',id:'status_add', 	boxLabel:'启用窗口',checked:true}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : windowDetails},
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left','100'
			);
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑窗口信息', 300, 300, 140, 1,
			   [
				{colIndex:0, field:{xtype : 'textfield', name:'branch', 	fieldLabel:'机构号',hidden:true}},
				//{colIndex:0, field:{xtype : 'combo',     name:'win_service_status', fieldLabel:'柜台服务状态',readOnly:true,hiddenName:'win_service_status',blankText : '', store:winServiceStatusStore, 	displayField:'value', valueField:'key'}},
				//{colIndex:0, field:{xtype : 'textfield', name:'win_oid', 	fieldLabel:'ABC OID',readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'qm_num_old',id:'qm_num_old', 	fieldLabel:'老排队机编号',hidden:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'win_num_old',id:'win_num_old',	fieldLabel:'老窗口号',hidden:true}},
				//{colIndex:0, field:{xtype : 'textfield', name:'ip',id:'ip_edit', fieldLabel:'窗口IP',readOnly:true,	allowBlank : true,blankText:'请输入窗口IP',maxLength : 40,maxLengthText : '长度不能大于40位',regex:/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/,regexText:'请输入正确的IP'}},
				{colIndex:0, field:{xtype : 'combo', name:'win_num',id:'win_num_edit', hiddenName:'win_num', 	fieldLabel:'窗口号',allowBlank : false,editable:true,readOnly:true, store:winNumStore, 	displayField:'value', valueField:'key',regex:/^[0-9]*$/,regexText:'窗口号只能输入数字',maxLength:3,maxLengthText:'窗口号最大长度3位'}},
				{colIndex:0, field:{xtype : 'textfield',fieldLabel:'排队机编号', name:'qm_num',id:'qm_num_edit',readOnly:true, 	fieldLabel:'排队机编号',	allowBlank : false,blankText:'请输入排队机编号'}},
				{colIndex:0, field:{xtype : 'combo', name:'apprflag',id:'apprflag_edit', hiddenName:'apprflag', 	fieldLabel:'评价器标识',allowBlank : false, store:apprflagStore, editable:false,	displayField:'value', valueField:'key'}},
				{colIndex:0, field:{xtype : 'combo', name:'parameter_id', hiddenName:'parameter_id', fieldLabel:'所用叫号规则',editable:false, store:systemParamStore, 	displayField:'parameter_name', valueField:'parameter_id',hidden:<%=!queue_rules_flag%>}},
				{colIndex:0, field:{xtype : 'combo', name:'call_rule',id:'call_rule_edit', hiddenName:'call_rule', 	fieldLabel:'叫号规则',allowBlank : false, store:callRuleStore, editable:false,	displayField:'value', valueField:'key'}},
				{colIndex:0, field:{layout:'column',fieldLabel:'软叫号配置编号',items:[{xtype : 'textfield', name:'softcall_id',id:'softcall_id_edit',readOnly:true, 	fieldLabel:'排队机编号',	allowBlank : true,blankText:'请选择软叫号配置编号',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){softCallDetails();}}}]}},
				{colIndex:0, field:{layout:'column',fieldLabel:'条屏样式编号',items:[{xtype : 'textfield', name:'screen_id',id:'screen_id_edit',readOnly:true, 	fieldLabel:'条屏样式编号',	allowBlank : true,blankText:'请选择条屏样式编号',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){screenDetails();}}}]}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'status',id:'status_edit', 	boxLabel:'启用'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : windowDetails},
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left','100'
			);
			
			//上级窗口
            function windowDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['branch','win_num','qm_num','ip','win_oid','call_rule','status','screen_id','softcall_id','access_id','win_service_status','apprflag']
			    });
			    var detailData = [
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'窗口号',dataIndex:'win_num',width:100},
					{header:'排队机编号',dataIndex:'qm_num',width:100},
					{header:'终端IP',dataIndex:'ip',width:100},
					{header:'ABC OID',dataIndex:'win_oid',width:100},
					{header:'叫号规则',dataIndex:'call_rule',width:100,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,callRuleStore,'key','value');//翻译字段方法
	       			 }
					},
					{header:'评价器标识',dataIndex:'apprflag',width:100,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,apprflagStore,'key','value');//翻译字段方法
	       			 }
					},
					{header:'状态',dataIndex:'status',width:100,renderer:function(value){return value=='1'?'1-可用':'0-不可用'}},
					{header:'条屏样式编号',dataIndex:'screen_id',width:100},
					{header:'软叫号配置编号',dataIndex:'softcall_id',width:100},
					{header:'柜台服务状态',dataIndex:'win_service_status',width:100,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,winServiceStatusStore,'key','value');//翻译字段方法
	       			 }}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('windowDetailsWindow','窗口列表',600, 355, 555, 1, [555],[{
		    		colIndex:0,
		    		field:{
		    			xtype:'fieldset',
		    			title:'窗口信息',
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
	    			Ext.getCmp('win_num_add').setValue(record.data['win_num']);
	    			Ext.getCmp('win_num_edit').setValue(record.data['win_num']);
	    			//Ext.getCmp('ip_add').setValue(record.data['ip']);
	    			//Ext.getCmp('ip_edit').setValue(record.data['ip']);
	    			Ext.getCmp('qm_num_add').setValue(record.data['qm_num']);
	    			Ext.getCmp('qm_num_edit').setValue(record.data['qm_num']);
	    			Ext.getCmp('softcall_id_add').setValue(record.data['softcall_id']);
	    			Ext.getCmp('softcall_id_edit').setValue(record.data['softcall_id']);
	    			Ext.getCmp('screen_id_add').setValue(record.data['screen_id']);
	    			Ext.getCmp('screen_id_edit').setValue(record.data['screen_id']);
	    			Ext.getCmp('status_add').setValue(record.data['status']);
	    			Ext.getCmp('status_edit').setValue(record.data['status']);
	    			Ext.getCmp('apprflag_add').setValue(record.data['apprflag']);
	    			Ext.getCmp('apprflag_edit').setValue(record.data['apprflag']);
	    			Ext.getCmp('call_rule_add').setValue(record.data['call_rule']);
	    			Ext.getCmp('call_rule_edit').setValue(record.data['call_rule']);
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '5';
			    requestAjax('<%=basePath%>/confManager/window_queryWindow',submitDetail, function(sRet){
				      detailStore.loadData(sRet.field1);
				});
			}
			//排队机编号
            function queueMachineDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['qm_num','qm_name','branch','qm_ip','status','qm_param_id']
			    });
			    var detailData = [
					{header:'排队机编号',dataIndex:'qm_num',width:100},
					{header:'排队机名称',dataIndex:'qm_name',width:100},
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'排队机IP',dataIndex:'qm_ip',width:180},
					{header:'排队机状态',dataIndex:'status',width:100,renderer:function(value){return value=='1'?'1-可用':'0-不可用'}},
					{header:'排队机参数',dataIndex:'qm_param_id',width:100}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('qmDetailsWindow','排队机编号列表',600, 355, 555, 1, [555],[{
		    		colIndex:0,
		    		field:{
		    			xtype:'fieldset',
		    			title:'排队机信息',
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
	    			Ext.getCmp('qm_num_add').setValue(record.data['qm_num']);
	    			Ext.getCmp('qm_num_edit').setValue(record.data['qm_num']);
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '0';
			    requestAjax('<%=basePath%>/confManager/qm_queryQMInfo',submitDetail, function(sRet){
				      detailStore.loadData(sRet.field1);
				});
			}
            
			//软叫号配置编号
            function softCallDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['branch','softcall_id','default_flag','status','recall_limit','call_next_limit']
			    });
			    var detailData = [
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'软叫号配置编号',dataIndex:'softcall_id',width:100},
					{header:'是否默认标志',dataIndex:'default_flag',width:100,renderer:function(value){
    					if(value!=""){
    						return value=='1'?'默认':'非默认';
    					}else{
    						return value;
    					}
    				}},
					{header:'软叫号启用状态',dataIndex:'status',width:100,renderer:function(value){
						if(value!=""){
							return value=='1'?'可用':'不可用';
    					}else{
    						return value;
    					}
					}},
					{header:'重呼限制时间',dataIndex:'recall_limit',width:100},
					{header:'顺呼限制时间',dataIndex:'call_next_limit',width:100}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('softCallDetailsWindow','软叫号配置编号列表',600, 355, 555, 1, [555],[{
		    		colIndex:0,
		    		field:{
		    			xtype:'fieldset',
		    			title:'软叫号配置信息',
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
	    			if(record.data['softcall_id']!='默认'){
		  				Ext.getCmp('softcall_id_add').setValue(record.data['softcall_id']);
			  			Ext.getCmp('softcall_id_edit').setValue(record.data['softcall_id']);
		  			}else{
		  				Ext.getCmp('softcall_id_add').setValue('');
			  			Ext.getCmp('softcall_id_edit').setValue('');
		  			}
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '4';
			    requestAjax('<%=basePath%>/confManager/softCall_querySoftCall',submitDetail, function(sRet){
			    	sRet.field1.unshift({'softcall_id':'默认'});
			    	detailStore.loadData(sRet.field1);
				});
			}
            
			//screenDetails
            function screenDetails(){
        		var styleStore=new Ext.data.SimpleStore({ 
        			data:[["0","立即显示"],["1","从右向左移"],["2","从下向上移"],["3","从左向右展开"],["4","百叶窗从左向右展开"],["5","从上向下展开"],["6","从上向下移"],["7","闪烁显示"],["8","连续左移"]],
        			fields : ['key','value']
        		});
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['branch','screen_id','default_flag','free_content','free_style','free_speed','call_content','call_style','call_speed','pause_content','pause_style','pause_speed','serve_content','serve_style','serve_speed']
			    });
			    var detailData = [
					{header:'机构号',dataIndex:'branch',width:100},
				{header:'条屏信息编号',dataIndex:'screen_id',width:100},
				{header:'是否默认标志',dataIndex:'default_flag',width:100,renderer:function(value){
					if(value!=""){
						return value=='1'?'默认':'非默认';
					}else{
						return value;
					}
				}},
				{header:'窗口空闲时显示内容',dataIndex:'free_content',width:180},
				{header:'窗口空闲时显示效果',dataIndex:'free_style',width:180,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,styleStore,'value','key');//翻译字段方法
       			 }},
				{header:'窗口空闲时切换时间(秒)',dataIndex:'free_speed',width:180,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }},
				{header:'窗口叫号时显示内容',dataIndex:'call_content',width:180},
				{header:'窗口叫号时显示效果',dataIndex:'call_style',width:180,renderer:function(value){
					value = value.toString();
					return getDictValue(value,styleStore,'value','key');//翻译字段方法
       			 }},
				{header:'窗口叫号时切换时间(秒)',dataIndex:'call_speed',width:180,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }},
				{header:'窗口暂停时显示内容',dataIndex:'pause_content',width:180},
				{header:'窗口暂停时显示效果',dataIndex:'pause_style',width:180,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,styleStore,'value','key');//翻译字段方法
       			 }},
				{header:'窗口暂停时切换时间(秒)',dataIndex:'pause_speed',width:180,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }}/*,
				{header:'窗口正在办理时显示内容',dataIndex:'serve_content',width:180},
				{header:'窗口正在办理时显示效果',dataIndex:'serve_style',width:180,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,styleStore,'value','key');//翻译字段方法
       			 }},
				{header:'窗口正在办理时切换时间(秒)',dataIndex:'serve_speed',width:180,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }} */
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('screenDetailsWindow','条屏参数配置编号列表',600, 355, 555, 1, [555],[{
		    		colIndex:0,
		    		field:{
		    			xtype:'fieldset',
		    			title:'条屏参数配置信息',
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
	    			if(record.data['screen_id']!='默认'){
		  				Ext.getCmp('screen_id_add').setValue(record.data['screen_id']);
			  			Ext.getCmp('screen_id_edit').setValue(record.data['screen_id']);
		  			}else{
		  				Ext.getCmp('screen_id_add').setValue('');
			  			Ext.getCmp('screen_id_edit').setValue('');
		  			}
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '4';
			    requestAjax('<%=basePath%>/confManager/screenDisplay_queryScreen',submitDetail, function(sRet){
			    	sRet.field1.unshift({'screen_id':'默认'});  
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
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '2';
						pagequeryObj.queryPage(query_obj);
						break;
					case 1:
						addwindow.open();
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
						//无法修改非本行机构
						var brno = <%="'"+brno+"'"%>;
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=brno){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
					    		return;
					    	}
					    }
						if(!isEdit){
							break;
						}
						editwindow.open();
						editwindow.updateFields(records[0]);
						Ext.getCmp('qm_num_old').setValue(records[0].data['qm_num']);
						Ext.getCmp('win_num_old').setValue(records[0].data['win_num']);
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['branch','win_num','qm_num']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						 //无法删除非本行机构
						 var brno = <%="'"+brno+"'"%>;
					    for(var i=0;i<submitdata.length;i++){
					    	if(submitdata[i].branch!=brno){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
					    		return;
					    	}
					    }
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/window_delWindow',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										query_obj['query_rules'] = '2';
										pagequeryObj.queryPage(query_obj);
									});
									window.parent.queryMessage();
								});
							}
						});
					    break;
				}
			};
			/**
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				createCheckBoxValue('status',submitData);
				requestAjax('<%=basePath%>confManager/window_editWindow',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '2';
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
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
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				createCheckBoxValue('status',submitData);
				requestAjax('<%=basePath%>confManager/window_addWindow', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '2';
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
      		
      		var brno = '<%=brno%>';
      		systemParamStore.load({params:{'branch':brno,'parameter_flag':'02'}});
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="editWindow"></div>
	<div id="qmDetailsWindow"></div>
	<div id="screenDetailsWindow"></div>
	<div id="softCallDetailsWindow"></div>
	<div id="windowDetailsWindow"></div>
  </body>
</html>
