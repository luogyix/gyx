<%@ page language="java"  pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>数据字典管理</title>
    
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
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		function getState(value){
			if(value == 0){
				return "0-启用";
			}
			else if(value == 1){
				return "1-禁用";
			}
		}
		function getEditState(value){
   		    if (value == 0) {
        		return "0-可编辑";
    		} 
    		else if (value == 1){
        		return "1-不可编辑";
     	    }
		}
		var stateStore=new Ext.data.SimpleStore({ 
				data:[["0-启用","0"],["1-禁用","1"]],
				fields : ['state','value']
		});
		var editStore=new Ext.data.SimpleStore({ 
				data:[["0-可编辑","0"],["1-不可编辑","1"]],
				fields : ['editable','value']
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示数据字典维护界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '数据字典管理', 120, 1,
				[
                {rowIndex:0, field:{xtype:'textfield',  name:'dicttype',    fieldLabel:'字段名称'}},
				{rowIndex:0, field:{xtype:'textfield', 	name:'dicttypedesc', 	fieldLabel:'字段描述'}}
				],
				[
				{iconCls: "x-image-query", 			text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-reset", 			text:'<s:text name="common.button.reset"/>'},		//重置
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
				'<%=basePath%>/admin/systemdict_queryDict',
				['item','dicttype','dicttypedesc','dictvalue','dictvaluedesc','enable','editable','remark'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'字典ID',dataIndex:'item',width:70,sortable:true},
				{header:'字典名称',dataIndex:'dicttype',width:150,sortable:true},
				{header:'字段描述',dataIndex:'dicttypedesc',width:150},
				{header:'代码',dataIndex:'dictvalue',width:150},
				{header:'代码描述',dataIndex:'dictvaluedesc',width:150},
				{header:'状态',dataIndex:'enable',width:60,renderer:getState},
				{header:'是否可编辑',dataIndex:'editable',width:80,renderer:getEditState},
				{header:'备注',dataIndex:'remark',width:150}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加字典', 600, 350, 200, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'dicttype', 		fieldLabel:'字典名称', 	allowBlank : false,blankText:'请输入字典名称',maxLength : 20,	maxLengthText : '长度不能大于20位'	}},
				{colIndex:1, field:{xtype : 'textfield', name:'dicttypedesc', 	fieldLabel:'字段描述'}},
				{colIndex:0, field:{xtype : 'textfield', name:'dictvalue',		fieldLabel:'代码',       allowBlank : false,blankText:'请输入代码'}},
				{colIndex:1, field:{xtype : 'textfield', name:'dictvaluedesc', 	fieldLabel:'代码描述',	maxLength : 20,maxLengthText : '长度不能大于20位'	}},
				{colIndex:0, field:{xtype : 'combo',     name:'enable', 		fieldLabel:'状态',		allowBlank : false,blankText:'请选择状态',hiddenName:'enable',store:stateStore,displayField:"state",valueField:"value",editable:false}},
				{colIndex:1, field:{xtype : 'combo', 	 name:'editable', 		fieldLabel:'是否可编辑',	allowBlank : false,blankText:'请选择是否可编辑',hiddenName:'editable',store:editStore,displayField:"editable",valueField:"value",editable:false}},
				{colIndex:0, field:{xtype : 'textfield', name:'remark', 		fieldLabel:'备注'}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑字典', 600, 400, 200, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'dicttype', 		fieldLabel:'字典名称', 	allowBlank : false,blankText:'请输入字典名称',maxLength : 20,	maxLengthText : '长度不能大于20位'	}},
				{colIndex:1, field:{xtype : 'textfield', name:'dicttypedesc', 	fieldLabel:'字段描述'}},
				{colIndex:0, field:{xtype : 'textfield', name:'dictvalue',		fieldLabel:'代码',       allowBlank : false,blankText:'请输入代码'}},
				{colIndex:1, field:{xtype : 'textfield', name:'dictvaluedesc', 	fieldLabel:'代码描述',	maxLength : 20,maxLengthText : '长度不能大于20位'	}},
				{colIndex:0, field:{xtype : 'combo',     name:'enable', 		fieldLabel:'状态',		allowBlank : false,blankText:'请选择状态',hiddenName:'enable',store:stateStore,displayField:"state",valueField:"value",editable:false}},
				{colIndex:1, field:{xtype : 'combo', 	 name:'editable', 		fieldLabel:'是否可编辑',	allowBlank : false,blankText:'请选择是否可编辑',hiddenName:'editable',store:editStore,displayField:"editable",valueField:"value",editable:false}},
				{colIndex:0, field:{xtype : 'textfield', name:'remark', 		fieldLabel:'备注'}},
				{colIndex:1, field:{xtype : 'hidden', 		name:'item'	}}
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
				var dicttype = submitData["dicttype"].trim();
				var dictvalue = submitData["dictvalue"].trim();
				submitData["dicttype"] = dicttype;
				submitData["dictvalue"] = dictvalue;
				requestAjax('<%=basePath%>/admin/systemdict_editDict',submitData,function(sRet){
					if(sRet.message!=""){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>',sRet.message);
						return;
					}
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
						conditionPanel.reset();
						break;
					case 2:
						addwindow.open();
						break;
					case 3:
					    var isEdit = true;
					    var records = pagequeryObj.getSelectedRecords();
						if(records === undefined ){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						if(records.length !== 1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
							break;
						}
						var editable = pagequeryObj.getSelectedRecords();
						for(var i=0;i<editable.length;i++){
							if(records[i].get('editable') == '1'){
								Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>该记录不能修改！</s:param></s:text >');
								isEdit = false;
							}
						}
						if(!isEdit){
							break;
						}
						editwindow.open();
						editwindow.updateFields(records[0]);
						break;
					case 4:
					    var submitdata = pagequeryObj.getSelectedObjects(['item']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
					    var isEdit = true;
						var deletable = pagequeryObj.getSelectedRecords();
						for(var i=0;i<deletable.length;i++){
							if(deletable[i].get('editable') == '1'){
								Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commonwarn"><s:param>不可编辑项目不能删除！</s:param></s:text >');
								isEdit = false;
							}
						}
						if(!isEdit){
							break;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/admin/systemdict_delDict',submitdata,function(sRet){
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
				var dicttype = submitData["dicttype"].trim();
				var dictvalue = submitData["dictvalue"].trim();
				submitData["dicttype"] = dicttype;
				submitData["dictvalue"] = dictvalue;
				requestAjax('<%=basePath%>/admin/systemdict_addDict', submitData,
				function(sRet){
					if(sRet.message!=""){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>',sRet.message);
						return;
					}
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
