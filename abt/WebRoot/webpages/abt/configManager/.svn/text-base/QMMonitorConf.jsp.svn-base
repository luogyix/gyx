<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String brno = user.getUnitid();
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>各队列监控配置</title>
    
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
<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		var queuetypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%><%=basePath%>/confManager/queuetype_queryQueueTypeSmall?query_rules=4',
			autoLoad:true,
			root   : 'field1',
			fields : ['queuetype_id','queuetype_name']
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示预约业务界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '队列监控参数配置', 120, 0,
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
				'<%=basePath%>confManager/QMMonitorConf_queryQMMonitorConfPage',
				['branch','queuetype_id','waitnum_threshold','waittime_threshold','notify_threshold','show_notify_threshold'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'队列类型',dataIndex:'queuetype_id',width:100,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,queuetypeStore,'queuetype_id','queuetype_name');//翻译字段方法
       			 }},
				{header:'人数预警阈值',dataIndex:'waitnum_threshold',width:150},
				{header:'等待时间预警阈值',dataIndex:'waittime_threshold',width:150},
				{header:'队列到号提前通知人数阈值',dataIndex:'notify_threshold',width:150},
				{header:'队列到号通知启用人数阈值',dataIndex:'show_notify_threshold',width:150}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加队列监控参数', 300, 400, 200, 1,
				[
				{colIndex:0, field:{xtype : 'combo', name:'queuetype_id', 	fieldLabel:'选择队列',hiddenName : 'queuetype_id' ,	allowBlank : false, blankText:'请选择队列',editable:false, store:queuetypeStore, 	displayField:'queuetype_name', valueField:'queuetype_id'}},
				{colIndex:0, field:{xtype : 'textfield', name:'waitnum_threshold', value:'0',	fieldLabel:'人数预警阈值(0为不限制)',	allowBlank : false, blankText:'请输入人数预警阈值',maxLength : 10,maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'waittime_threshold',value:'0', 	fieldLabel:'等待时间预警阈值(0为不限制)',	allowBlank : false, blankText:'请输入等待时间预警阈值',maxLength : 10,maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'notify_threshold',value:'0', 	fieldLabel:'队列到号提前通知人数阈值(0为不限制)',	allowBlank : false, blankText:'请输入队列到号提前通知人数阈值',maxLength : 10,maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'show_notify_threshold',value:'0', 	fieldLabel:'队列到号通知启用人数阈值(0为不限制)',	allowBlank : false,blankText:'请输入队列到号通知启用人数阈值',maxLength : 10,maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/,regexText:'只能输入数字'}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑队列监控参数', 300, 400, 200, 1,
			   [
				{colIndex:0, field:{xtype : 'textfield', name:'queuetype_id_old', id:'queuetype_id_old',hidden:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'branch', 	fieldLabel:'机构号',readOnly:true}},
				{colIndex:0, field:{xtype : 'combo', name:'queuetype_id', 	fieldLabel:'选择队列',hiddenName : 'queuetype_id', 	allowBlank : false, blankText:'请选择队列',editable:false, store:queuetypeStore, 	displayField:'queuetype_name', valueField:'queuetype_id'}},
				{colIndex:0, field:{xtype : 'textfield', name:'waitnum_threshold', 	fieldLabel:'人数预警阈值(0为不限制)',	allowBlank : false, blankText:'请输入人数预警阈值',maxLength : 10,maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'waittime_threshold', 	fieldLabel:'等待时间预警阈值(0为不限制)',	allowBlank : false, blankText:'请输入等待时间预警阈值',maxLength : 10,maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'notify_threshold', 	fieldLabel:'队列队列到号提前通知人数阈值(0为不限制)',	allowBlank : false, blankText:'请输入队列到号提前通知人数阈值',maxLength : 10,maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'show_notify_threshold', 	fieldLabel:'队列到号通知启用人数阈值(0为不限制)',	allowBlank : false,blankText:'请输入队列到号通知启用人数阈值',maxLength : 10,maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/,regexText:'只能输入数字'}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				]
			);
			
			/**
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				requestAjax('<%=basePath%>confManager/QMMonitorConf_editQMMonitorConf',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
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
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=<%="'"+brno+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
					    		return;
					    	}
					    }
						if(!isEdit){
							break;
						}
						editwindow.open();
						editwindow.updateFields(records[0]);
						Ext.getCmp('queuetype_id_old').setValue(records[0].data['queuetype_id']);
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['queuetype_id','branch']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						 //无法删除非本行机构
					    for(var i=0;i<submitdata.length;i++){
					    	if(submitdata[i].branch!=<%="'"+brno+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
					    		return;
					    	}
					    }
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/QMMonitorConf_delQMMonitorConf',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					    break;
				}
			}
			 
            /**
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				requestAjax('<%=basePath%>confManager/QMMonitorConf_addQMMonitorConf', submitData,
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
	<div id="editWindow"></div>
  </body>
</html>
