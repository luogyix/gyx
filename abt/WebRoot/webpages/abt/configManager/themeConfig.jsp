<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String brno = user.getUnitid();
String bankLevel = user.getUnit().getBank_level();
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>    
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>排队机主题配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	
	<script type="text/javascript">
		var conditionPanel = null;
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
/**
 * @Title: loadPage 
 * @Description: 显示业务配置界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '排队机主题配置', 120, 0,
				[
				],
				[
				{iconCls: "x-image-query", 			            text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-application_form_add", 		text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", 		text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_delete", 	text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked
			);
			conditionPanel.open();
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>confManager/themeManager_queryTheme4Page',
				['theme_id','theme_name','theme_imgsrc_icon','theme_imgsrc_list','theme_note','branch','default_flag'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'主题编号',     dataIndex:'theme_id',width:100},
				{header:'所属机构',     dataIndex:'branch',width:100},
				{header:'主题名称',     dataIndex:'theme_name',width:150},
				{header:'主题图标',dataIndex:'theme_imgsrc_icon',width:150,renderer:function(value,metadata){
					value = value.toString();
					metadata.attr = 'ext:qtitle="" ext:qtip="<img width=\'512\' height=\'384\' src=\'<%=basePath%>'+ value +'\'/>"';
					value = "<img width='64' height='48' src=\'<%=basePath%>" + value + "\'/>";
          		 	return value;
       			 }},
				{header:'主题内容图片',dataIndex:'theme_imgsrc_list',width:350,renderer:function(value,metadata,record,index,colIndex,store){
					var values = value.toString().split(';');
					var v = '';
					for(var i=0;i<values.length;i++){
						v = v + "&nbsp;&nbsp;&nbsp;<img height='64' width='48' src=\'<%=basePath%>" + values[i] + "\'/>";
					}
          		 	return v;
       			 }},
				{header:'主题说明',dataIndex:'theme_note',width:250},
				{header:'默认标志',dataIndex:'default_flag',width:100,renderer:function(value){
          		 	return value==1?'0-默认':'1-非默认';//翻译字段方法
       			 }}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口'theme_id','theme_name','theme_imgsrc_icon','theme_imgsrc_list','theme_note','default_flag'
			addwindow = new SelfFormWindow('addWindow', '添加主题', 500, 350, 150, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'theme_name', fieldLabel:'主题名称',allowBlank : false,maxLength : 60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'theme_imgsrc_icon', fieldLabel:'主题图标路径',allowBlank : false,maxLength : 100,maxLengthText : '长度不能大于100位'}},
				{colIndex:0, field:{xtype : 'checkbox',  name:'default_flag',hideLabel:true, boxLabel:'默认'}},
				{colIndex:1, field:{xtype : 'textarea', name:'theme_imgsrc_list', fieldLabel:'主题内容图片路径',allowBlank : false,maxLength : 500,maxLengthText : '长度不能大于500位'}},
				{colIndex:1, field:{width:250,html:'<font style="color:#FF0000">备注:多个图片路径请用";"分割</font>'}},
				{colIndex:1, field:{xtype : 'textarea', name:'theme_note', fieldLabel:'主题说明',allowBlank : false,maxLength : 500,maxLengthText : '长度不能大于500位'}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑主题', 500, 450, 150, 2,
				[
					{colIndex:0, field:{xtype : 'hidden', name:'theme_id'}},
					{colIndex:0, field:{xtype : 'hidden', name:'branch'}},
					{colIndex:0, field:{xtype : 'textfield', name:'theme_name', fieldLabel:'主题名称',allowBlank : false,maxLength : 60,	maxLengthText : '长度不能大于60位'}},
					{colIndex:0, field:{xtype : 'textfield', name:'theme_imgsrc_icon', fieldLabel:'主题图标路径',allowBlank : false,maxLength : 100,maxLengthText : '长度不能大于100位'}},
					{colIndex:0, field:{xtype : 'checkbox',  name:'default_flag',hideLabel:true, boxLabel:'默认'}},
					{colIndex:1, field:{xtype : 'textarea', name:'theme_imgsrc_list', fieldLabel:'主题内容图片路径',allowBlank : false,maxLength : 500,maxLengthText : '长度不能大于500位'}},
					{colIndex:1, field:{width:250,html:'<font style="color:#FF0000">备注:多个图片路径请用";"分割</font>'}},
					{colIndex:1, field:{xtype : 'textarea', name:'theme_note', fieldLabel:'主题说明',allowBlank : false,maxLength : 500,maxLengthText : '长度不能大于500位'}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				]
			);
			
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
						if(!isEdit){
							break;
						}
						editwindow.open();
						editwindow.updateFields(records[0]);
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['theme_id','branch']);
					    //请选择机构
					    if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
					    
					    //无法删除非本行机构
					    for(var i=0;i<submitdata.length;i++){
					    	if((submitdata[i].branch!=<%="'"+brno+"'"%>)&&(<%="'"+bankLevel+"'"%>!='0')){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
					    		return;
					    	}
					    }
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/themeManager_delTheme',submitdata,function(sRet){
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
				createCheckBoxValue('default_flag',submitData);
				requestAjax('<%=basePath%>confManager/themeManager_editTheme',submitData,function(sRet){
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
				createCheckBoxValue('default_flag',submitData);
				requestAjax('<%=basePath%>confManager/themeManager_addTheme', submitData,
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
