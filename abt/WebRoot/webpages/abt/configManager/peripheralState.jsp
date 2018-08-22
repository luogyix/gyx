<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
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
    
    <title>My JSP 'peripheralStateJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	<script type="text/javascript" src=extjs/adapter/ext/ext-base.js></script>
	<script type="text/javascript" src=extjs/ext-all.js></script>	
	<script type="text/javascript" src=extjs/ext-lang-zh_CN.js></script>	
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
    <script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
    <script type="text/javascript">

        var pagereturn=${actionresult};
		var conditionPanel = null;
	
		var typeStore = new Ext.data.JsonStore({
		url    : '<%=basePath%>/confManager/peripheralType_queryPeripheralTypeSmall',
		autoLoad:true,
		root   : 'field1',
		id     :'p_type_key',
		fields:['p_type_key','p_type_value']
	    });
		
		var applyTypeStore = new Ext.data.JsonStore({
			fields : ['p_type_key']
			//fields : ['p_type_key','p_type_values']
		});

		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示客户类型界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '设备外设配置', 120, 0,
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
				'<%=basePath%>/confManager/peripheralState_queryPeripheralState',
				['p_state_key','p_state_value','p_type_key'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'外设状态ID',dataIndex:'p_state_key',width:80, align: 'center'},
				{header:'外设状态名',dataIndex:'p_state_value',width:160, align: 'center'},
				//{header:'关联外设类型',dataIndex:'p_type_key',width:360}
				{header:'关联外设类型',dataIndex:'p_type_key',width:360, align: 'center',
				renderer:function(value){      //翻译字段方法
				value = value.toString();
				var typeArr = value.split(',');
				var type_val = '';
				for(var i = 0;i<typeArr.length;i++){
					type_val += ',' + typeStore.getById(typeArr[i]).get('p_type_value');
				}
				if(typeArr.length > 0){
				    type_val = type_val.toString();
					return type_val.substring(1, type_val.length);
					//return type_val;
				}
				}}
				
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			
			var typeComb = new Ext.form.ComboBox({
				editable : false,
				mode : 'local',
				valueField : 'p_type_key',
				displayField : 'p_type_value',
				triggerAction : 'all',
				store : typeStore
			});
			
			//add grid
		    var typeData = [
   				new Ext.grid.RowNumberer({header:'序号',width:33}),
   				{xtype:'gridcolumn', header:'复选框', align: 'center',width:20,hidden:true},
		      	{xtype:'gridcolumn',dataIndex: 'p_type_key',header: '外设类型名称',sortable:false,editor:typeComb,width:150,
		      	renderer:function(value){                   //翻译字段方法
		      		if(value!=''){
		      	    	value = value.toString();
						var type_val = '';
						type_val = typeStore.getById(value).get('p_type_value');
						return type_val
						}
					}
		      	}
   			];
	    	var typeSM = new Ext.grid.CheckboxSelectionModel({ singleSelect: true });
	    	var typeCM = new Ext.grid.ColumnModel(typeData);
	    	
			var typeGrid = new Ext.grid.EditorGridPanel({
				id: 'typeGrid',
			    store: applyTypeStore,
			    colModel: typeCM,
			    sm:typeSM,
			    stripeRows: true,
			    height:160,
			    frame:true,
			    viewConfig: {forceFit: true},
			    clicksToEdit:1,
			    tbar: [
			    	{id:'addBtn', text: '添加', disabled: false, iconCls: "x-image-add", 
			    		handler: function(){
							var length= applyTypeStore.getCount();
   							var recordType = applyTypeStore.recordType;
   							var record = new recordType({
   								//p_type_key:'',p_type_value:''
   								p_type_key:''
							});
   							typeGrid.stopEditing();
   							applyTypeStore.insert(length, record);
   							typeGrid.getView().refresh();
   							typeSM.selectRow(length);
   							typeGrid.startEditing(applyTypeStore.getCount()-1, 3);
			    		} 
			    	},'-',
			    	{id:'delBtn', text: '删除', disabled: false, iconCls: "x-image-delete", 
			    		handler: function(){
			    			if(!typeSM.hasSelection()){
								Ext.Msg.alert('<s:text name="common.info.title"/>' , '<s:text name="common.info.mustselectrecord"/>');
								return;
							}
							Ext.MessageBox.confirm('<s:text name="common.info.title"/>' , '<s:text name="common.info.suredelete"/>' , function(id){
								if(id=='yes'){
									var records = typeSM.getSelections();
									for(var i=0; i<records.length; i++){
										applyTypeStore.remove(records[i]);
									}
								}
							});
			    		}
			    	}
			    ]
			});
			
			typeGrid.on('afteredit',function(e,a,b,c){
				var record = e.record;
				if(record.get('p_type_key')!=''){//判断非空,与已有数据进行对比
					for ( var i = 0; i < applyTypeStore.getCount(); i++) {
					//e.row 所在行 一会循环要排除 e.column所在列 
						if(i==e.row){
							continue;
						}
						var r = applyTypeStore.getAt(i);
						if(record.get('p_type_key') == r.get('p_type_key') ){
							Ext.MessageBox.alert('提示','外设类型冲突,请重新修改',function(id){
								record.set(e.field,e.originalValue);
								return;
							});
						}
					}
				}
			});
			//edite grid
			var tsm = new Ext.grid.CheckboxSelectionModel({ singleSelect: true });
			var typeGridEdition = new Ext.grid.EditorGridPanel({
				id: 'typeGridEdition',
			    store: applyTypeStore,
			    height:160,
			    colModel: new Ext.grid.ColumnModel([
   					new Ext.grid.RowNumberer({header:'序号',width:33}),
   					{xtype:'gridcolumn', header:'复选框', align: 'center',width:20,hidden:true},
		      		{xtype:'gridcolumn',header: '外设类型名称',sortable:false,dataIndex: 'p_type_key',
			      	editor:new Ext.form.ComboBox({
						mode : 'local',
						valueField : 'p_type_key',
						displayField : 'p_type_value',
						triggerAction : 'all',
						store : typeStore
				    }),
				    renderer:function(value){             //翻译字段方法
		      			if(value!=''){
		      	    		value = value.toString();
							var type_val = '';
							type_val = typeStore.getById(value).get('p_type_value');
							return type_val
							}
						}
					}
   			]),
			    sm:tsm,
			    stripeRows: true,
			    height:240,
			    frame:true,
			    viewConfig: {forceFit: true},
			    clicksToEdit:1,
			    tbar: [
			    	{id:'addBtned', text: '添加', disabled: false, iconCls: "x-image-add", 
			    		handler: function(){
							var length= applyTypeStore.getCount();
   							var recordType = applyTypeStore.recordType;
   							var record = new recordType({
   								//p_type_key:'',p_type_value:''
   								p_type_key:''
							});
   							typeGridEdition.stopEditing();
   							applyTypeStore.insert(length, record);
   							typeGridEdition.getView().refresh();
   							tsm.selectRow(length);
   							typeGridEdition.startEditing(applyTypeStore.getCount()-1, 3);
			    		} 
			    	},'-',
			    	{id:'delBtned', text: '删除', disabled: false, iconCls: "x-image-delete", 
			    		handler: function(){
			    			if(!tsm.hasSelection()){
								Ext.Msg.alert('<s:text name="common.info.title"/>' , '<s:text name="common.info.mustselectrecord"/>');
								return;
							}
							Ext.MessageBox.confirm('<s:text name="common.info.title"/>' , '<s:text name="common.info.suredelete"/>' , function(id){
								if(id=='yes'){
									var records = tsm.getSelections();
									for(var i=0; i<records.length; i++){
										applyTypeStore.remove(records[i]);
									}
								}
							});
			    		}
			    	}
			    ]
			});
			
			
			typeGridEdition.on('afteredit',function(e,q,w,r){
				var record = e.record;
				if(record.get('p_type_key')!=''){//判断非空
					for ( var i = 0; i < applyTypeStore.getCount(); i++) {
					//e.row 所在行 一会循环要排除 e.column所在列 
						if(i==e.row){
							continue;
						}
						var r = applyTypeStore.getAt(i);
						if(record.get('p_type_key') == r.get('p_type_key') ){
							Ext.MessageBox.alert('提示','外设类型冲突,请重新修改',function(id){
								record.set(e.field,e.originalValue);
								return;
							});
						}
					}
				}
			});
			
			
			//当选择"添加"时，弹出的窗口
			var addwindow = new SelfFormWindow('addWindow', '外设状态', 330, 330, 110, 1,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'p_state_key',   fieldLabel:'外设状态ID',allowBlank : false, blankText:'请输入外设类型ID',maxLength : 30,	maxLengthText : '长度不能大于30位'	}},
				//{colIndex:0, field:{xtype : 'textfield', name:'p_state_key',   fieldLabel:'外设状态ID',allowBlank : false,renderer:function(ID){var myDate = new Date();
				//ID = myDate + Math.random()*100;
				//ID = ID.toString().subString(0,8);
				//return ID;}}},
		        {colIndex:0, field:{xtype : 'textfield', name:'p_state_value', fieldLabel:'外设状态名',	 allowBlank : false,blankText : '请输入外设类型名',maxLength : 30,maxLengthText : '长度不能大于30位'}},
		        {colIndex:0, field:{xtype: 'fieldset', width:280,layout:'column', items:[typeGrid]}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',150
			);
			
			//选择"修改"时，弹出的窗口
			var editwindow = new SelfFormWindow('editWindow', '编辑外设状态', 330, 330, 110, 1,
			   [
				{colIndex:0, field:{xtype : 'textfield', name:'p_state_key', 	fieldLabel:'外设状态ID', 	readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'p_state_value', fieldLabel:'外设状态名',blankText : '请输入外设状态名',maxLength : 30,	maxLengthText : '长度不能大于30位'  }},
				{colIndex:0, field:{xtype: 'fieldset',width:280, layout:'column', items:[typeGridEdition]}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',150
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
						applyTypeStore.removeAll();
						break;
					case 2:
					    var isEdit = true;
					    var records = pagequeryObj.getSelectedRecords();
						if(records === undefined || records.length !== 1){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
						var edit = pagequeryObj.getSelectedRecords();
						for(var i=0;i<edit.length;i++){
							if(records[i].get('edit') == '1'){
								Ext.MessageBox.alert('系统提示','该记录不能修改');
								isEdit = false;
							}
						}
						if(!isEdit){
							break;
						}
						applyTypeStore.removeAll();
						editwindow.open();
						editwindow.updateFields(records[0]);
						var typestr = records[0].get('p_type_key');
						var temp = typestr.split(',');
						var typeArr = new Array();
						for(var i = 0;i<temp.length;i++){
							if(temp.length>0){
								var datas = {};
								datas['p_type_key'] = temp[i];
								//datas['p_type_value'] 
								typeArr.push(datas);
							}
						}
					    applyTypeStore.loadData(typeArr);					
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['p_state_key','p_state_value','p_type_key']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/peripheralState_delPeripheralState',submitdata,function(sRet){
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
				var type_list = '';
				for(var i = 0;i<applyTypeStore.getCount();i++){
					var temp = applyTypeStore.getAt(i);
					type_list += temp.get('p_type_key') + ';';
					//type_list += temp.get('p_type_value') + ';';
				}
				submitData['p_type_key'] = type_list;
				if(submitData['p_state_key'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入外设状态ID');
					return;
				}
				if(submitData['p_state_value'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入外设状态名');
					return;
				}
/*				if(submitData['p_type_key'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入外设状态名');
					return;
				}*/
				requestAjax('<%=basePath%>/confManager/peripheralState_addPeripheralState', submitData,
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
				var type_list = '';
				for(var i = 0;i<applyTypeStore.getCount();i++){
					var temp = applyTypeStore.getAt(i);
					type_list += temp.get('p_type_key') + ';';
				}
				submitData['p_type_key'] = type_list;
				if(submitData['p_state_value'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入外设状态');
					return;
				}
/*				if(submitData['p_type_key'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入外设类型');
					return;
				}*/

				requestAjax('<%=basePath%>/confManager/peripheralState_editPeripheralState',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
					var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
						
					});
					editwindow.close();
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
