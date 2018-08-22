<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute("logonuser");
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>    
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>消息推送规则配置</title>
    
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
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagetoafaquery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	
	<script type="text/javascript">
		var conditionPanel = null;
		var deviceTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
			root   : 'field1',
			fields : ['dictValue','dictValueDesc'],
			autoLoad:true
		});
		var pushTypeStore = new Ext.data.SimpleStore({ 
			data:[['Real','Real-实时'],['Time','Time-定时']],
			fields : ['dictValue','dictValueDesc']
		});
		var outModeStore = new Ext.data.SimpleStore({ 
			data:[['0','0-不过时'],['1','1-过时']],
			fields : ['dictValue','dictValueDesc']
		});
		var msgTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>confManager/msgManager_queryMsgManager4Combo',
			autoLoad:true,
			root   : 'field1',
			fields : ['msgtype','msgname']
		});
		var pushModeStore = new Ext.data.SimpleStore({ 
			data:[['0','0-定点推送'],['1','1-全局推送']],
			fields : ['dictValue','dictValueDesc']
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '消息推送规则配置', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combo',name:'devicetype',fieldLabel:'设备类型', editable:false, hiddenName:'devicetype', store:deviceTypeStore, 	displayField:'dictValueDesc',valueField:'dictValue'}},
				{rowIndex:0, field:{xtype:'textfield',name:'msgtype',fieldLabel:'消息类型'}}
				],
				[
				{iconCls: "x-image-query",text:'<s:text name="common.button.query"/>'},//查询
				{iconCls: "x-image-reset",text:'<s:text name="common.button.reset"/>'},//重置
				{iconCls: "x-image-application_form_add",  id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked
			);
			
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>/confManager/msgPushRule_queryMsgPushRule',
				[
					'msgtype','devicetype','pushtype','delaytime','retrytime','retrynum','outmode','msgaddition',
					'pushmode','pushcondition','conditiondesc'
				],[
					{header:'全选'},
					{header:'复选框'},
					{header:'消息类型',dataIndex:'msgtype',width:150, renderer:function(value){
					      return getDictValue(value.toString(),msgTypeStore,'msgtype','msgname');//翻译字段方法
				    }},                 
					{header:'设备类型',dataIndex:'devicetype',width:150, renderer:function(value){
					      return getDictValue(value.toString(),deviceTypeStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},              
					{header:'推送类型',dataIndex:'pushtype',width:100, renderer:function(value){
					      return getDictValue(value.toString(),pushTypeStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},                
					{header:'延迟间隔',dataIndex:'delaytime',width:100},               
					{header:'重试间隔',dataIndex:'retrytime',width:100},               
					{header:'失败重试次数',dataIndex:'retrynum',width:100},            
					{header:'过时策略',dataIndex:'outmode',width:100, renderer:function(value){
					      return getDictValue(value.toString(),outModeStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},                 
					{header:'消息附加信息',dataIndex:'msgaddition',width:100},         
					{header:'推送模式',dataIndex:'pushmode',width:100, renderer:function(value){
					      return getDictValue(value.toString(),pushModeStore,'dictValue','dictValueDesc');//翻译字段方法
				    }},
					{header:'推送条件表达式',dataIndex:'pushcondition',width:500},     
					{header:'推送条件描述',dataIndex:'conditiondesc',width:100}
					//{header:'设备在线标识',dataIndex:'deviceonline',width:100, renderer:function(value){
					//      return getDictValue(value.toString(),deviceonlineStore,'dictValue','dictValueDesc');//翻译字段方法
				    //}},
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			 /**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						pagequeryObj.queryPage(conditionPanel.getFields());
					break;
					case 1:
						conditionPanel.reset();
					break;
					case 2:
						addwindow.open();
						break;
					case 3:
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
						editwindow.open();
						editwindow.updateFields(records[0]);
						Ext.getCmp('msgtype_old').setValue(records[0].data['msgtype']);
						Ext.getCmp('devicetype_old').setValue(records[0].data['devicetype']);
						break;
					case 4:
					    var submitdata = pagequeryObj.getSelectedObjects(['msgtype','devicetype']);
					    if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/msgPushRule_delMsgPushRule',submitdata,function(sRet){
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
			
			addwindow = new SelfFormWindow('addWindow', '添加消息推送规则', 460, 420, 160, 2,
				[//msgTypeStore,'msgtype','msgname'
				{colIndex:0,field:{xtype:'combo',name:'devicetype',fieldLabel:'设备类型',forceSelection:true,allowBlank:false,hiddenName:'devicetype',store:deviceTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'msgtype',fieldLabel:'消息类型',forceSelection:true,allowBlank:false,hiddenName:'msgtype',store:msgTypeStore,displayField:'msgname',valueField:'msgtype'}},
				{colIndex:0,field:{xtype:'combo',name:'pushtype',fieldLabel:'推送类型',forceSelection:true,allowBlank:false,hiddenName:'pushtype',store:pushTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'outmode',fieldLabel:'过时策略',forceSelection:true,hiddenName:'outmode',store:outModeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:0,field:{xtype:'combo',name:'pushmode',fieldLabel:'推送模式',forceSelection:true,hiddenName:'pushmode',store:pushModeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
				{colIndex:1,field:{xtype:'textfield',name:'delaytime',fieldLabel:'延迟间隔',blankText:'请输入延迟间隔',maxLength:20,	maxLengthText:'长度不能大于20位'}},
				{colIndex:1,field:{xtype:'textfield',name:'retrytime',fieldLabel:'重试间隔',blankText:'请输入重试间隔',maxLength:20,	maxLengthText:'长度不能大于20位'}},
				{colIndex:1,field:{xtype:'numberfield',name:'retrynum',fieldLabel:'失败重试次数',blankText:'请输入失败重试次数',allowDecimals : false,allowNegative : false,maxValue : 9999999,maxText : '最大可重试9999999次'}},
				{colIndex:1,field:{xtype:'textfield',name:'msgaddition',fieldLabel:'消息附加信息',blankText:'请输入消息附加信息',maxLength:1000,	maxLengthText:'长度不能大于1000位'}},
				{colIndex:0,field:{xtype:'textarea',name:'pushcondition',fieldLabel:'推送条件表达式',blankText:'请输入推送条件表达式',maxLength:1000,	maxLengthText:'长度不能大于1000位'}},
				{colIndex:1,field:{xtype:'textfield',name:'conditiondesc',fieldLabel:'推送条件描述',blankText:'请输入推送条件描述',maxLength:1000,	maxLengthText:'长度不能大于1000位'}}
				],
				[
					{text:'<s:text name="common.button.add"/>',   formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
		
			editwindow = new SelfFormWindow('editWindow', '编辑消息推送规则', 460, 420, 160, 2,
				[
					{colIndex:0,field:{xtype :'textfield', id:'devicetype_old', name:'devicetype_old',hidden:true}},
					{colIndex:0,field:{xtype :'textfield', id:'msgtype_old', name:'msgtype_old',hidden:true}},
					{colIndex:0,field:{xtype:'combo',name:'devicetype',fieldLabel:'设备类型',forceSelection:true,allowBlank:false,hiddenName:'devicetype',store:deviceTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
					{colIndex:0,field:{xtype:'combo',name:'msgtype',fieldLabel:'消息类型',forceSelection:true,allowBlank:false,hiddenName:'msgtype',store:msgTypeStore,displayField:'msgname',valueField:'msgtype'}},
					{colIndex:0,field:{xtype:'combo',name:'pushtype',fieldLabel:'推送类型',forceSelection:true,allowBlank:false,hiddenName:'pushtype',store:pushTypeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
					{colIndex:0,field:{xtype:'combo',name:'outmode',fieldLabel:'过时策略',forceSelection:true,hiddenName:'outmode',store:outModeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
					{colIndex:0,field:{xtype:'combo',name:'pushmode',fieldLabel:'推送模式',forceSelection:true,hiddenName:'pushmode',store:pushModeStore,displayField:'dictValueDesc',valueField:'dictValue'}},
					{colIndex:1,field:{xtype:'textfield',name:'delaytime',fieldLabel:'延迟间隔',blankText:'请输入延迟间隔',maxLength:20,	maxLengthText:'长度不能大于20位'}},
					{colIndex:1,field:{xtype:'textfield',name:'retrytime',fieldLabel:'重试间隔',blankText:'请输入重试间隔',maxLength:20,	maxLengthText:'长度不能大于20位'}},
					{colIndex:1,field:{xtype:'numberfield',name:'retrynum',fieldLabel:'失败重试次数',blankText:'请输入失败重试次数',allowDecimals : false,allowNegative : false,maxValue : 9999999,maxText : '最大可重试9999999次'}},
					{colIndex:1,field:{xtype:'textfield',name:'msgaddition',fieldLabel:'消息附加信息',blankText:'请输入消息附加信息',maxLength:1000,	maxLengthText:'长度不能大于1000位'}},
					{colIndex:0,field:{xtype:'textarea',name:'pushcondition',fieldLabel:'推送条件表达式',blankText:'请输入推送条件表达式',maxLength:1000,	maxLengthText:'长度不能大于1000位'}},
					{colIndex:1,field:{xtype:'textfield',name:'conditiondesc',fieldLabel:'推送条件描述',blankText:'请输入推送条件描述',maxLength:1000,	maxLengthText:'长度不能大于1000位'}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				]
			);
			
			/**
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				requestAjax('<%=basePath%>confManager/msgPushRule_addMsgPushRule', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
			}
			/**
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				
				requestAjax('<%=basePath%>confManager/msgPushRule_editMsgPushRule',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
			}
			
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
