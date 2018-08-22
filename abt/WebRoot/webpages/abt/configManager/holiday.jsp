<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String brno = user.getUnitid();
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>   
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'dictinfo.jsp' starting page</title>
    
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
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		function submitTheme(id,name,note,list){
			var iWidth=705; //模态窗口宽度
			var iHeight=535;//模态窗口高度
			var iTop=(window.screen.height-iHeight)/2-50;
			var iLeft=(window.screen.width-iWidth)/2;
			var url = '<%=basePath%>webpages/abt/configManager/themeManagerDetail.jsp';
			var param = '?id=' + encodeURI(id) + '&name=' + encodeURI(encodeURI(name)) + '&list=' 
					+ encodeURI(encodeURI(list)) + '&note=' + encodeURI(encodeURI(note));
			var params = 'Scrollbars=yes,Toolbar=no,Location=no,Direction=no,Resizeable=no,Width='
						 + iWidth + ',Height=' + iHeight + ',top=' + iTop + ',left=' + iLeft;
			//window.open(url+param,name,params);
			window.open('','主题展示',params); 
			/**/
			var tempForm = document.createElement('form');  
            tempForm.id='tempForm1';
            tempForm.method='post';
            tempForm.action=url;  
            tempForm.target='主题展示';  
             
            var hideInput = document.createElement('input');  
            hideInput.type='hidden';  
            hideInput.name= 'id'
            hideInput.value= id;
            tempForm.appendChild(hideInput);  
             
            var hideInput = document.createElement('input');  
            hideInput.type='hidden';  
            hideInput.name= 'name'
            hideInput.value= name;
            tempForm.appendChild(hideInput);  
             
            var hideInput = document.createElement('input');  
            hideInput.type='hidden';  
            hideInput.name= 'list'
            hideInput.value= list;
            tempForm.appendChild(hideInput);  
             
            var hideInput = document.createElement('input');  
            hideInput.type='hidden';  
            hideInput.name= 'note'
            hideInput.value= note.trim();
            tempForm.appendChild(hideInput);  
    
            tempForm.appendChild(hideInput);  
            document.body.appendChild(tempForm);  
            tempForm.submit();
            document.body.removeChild(tempForm);
		}
/**
 * @Title: loadPage 
 * @Description: 显示节假日 维护界面
 */
 		var priv=pagereturn.field2;
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '节假日配置', 120, 0,
				[],
				[
				{iconCls: "x-image-query", 			       id:'01',     text:'<s:text name="common.button.query"/>'},		//查询
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
				'<%=basePath%>confManager/holiday_queryHolidayPage',
				['holiday_id','branch','holiday_name','start_date','end_date','note','theme_id'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'节假日编号',dataIndex:'holiday_id',width:150},
				{header:'机构号',dataIndex:'branch',width:150},
				{header:'节假日名称',dataIndex:'holiday_name',width:150},
				{header:'节假日主题编号',dataIndex:'theme_id',width:100},
				{header:'开始时间' ,dataIndex:'start_date',width:150},
				{header:'结束时间' ,dataIndex:'end_date',width:150},
				{header:'备注',dataIndex:'note',width:(window.screen.width)-1125}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加节假日', 350, 380, 200, 1,
				[
				//{colIndex:0, field:{xtype :'textfield', name:'holiday_id',fieldLabel:'节假日编号',allowBlank : false,blankText:'请输入节假日编号',maxLength : 8,	maxLengthText : '长度不能大于8位',regex:/^[0-9a-zA-Z]*$/,regexText:'只能输入英文数字'}},
				{colIndex:0, field:{xtype :'textfield',id:'holiday_name_add', name:'holiday_name',fieldLabel:'节假日名称',allowBlank : false,blankText:'请输入节假日名称',maxLength : 60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype:'datefield',id:'start_date_add',  name:'start_date', fieldLabel:'开始日期(含当天)',format:'Y-m-d',allowBlank : false,blankText:'请选择开始时间',editable:false}},
				{colIndex:0, field:{xtype:'datefield',id:'end_date_add',  name:'end_date',   fieldLabel:'结束日期(含当天)',format:'Y-m-d',allowBlank : false,blankText:'请选择结束时间',editable:false}},
				{colIndex:0, field:{layout:'column',fieldLabel:'主题编号',items:[{xtype : 'textfield', name:'theme_id',id:'theme_id_add', 	fieldLabel:'主题编号',readOnly:true,blankText:'请选择主题编号',emptyText:'请选择主题编号',maxLength : 20,maxLengthText : '长度不能大于20位',width:'160'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){themeDetails();}}}]}},
				{colIndex:0, field:{xtype :'textarea',id:'note_add', name:'note',fieldLabel:'备注',maxLength : 100,	maxLengthText : '长度不能大于100位'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : details},
					{text:'<s:text name="common.button.add"/>',   formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑节假日', 350, 380, 200, 1,
				[
				{colIndex:0, field:{xtype :'textfield', name:'holiday_id',hidden:true}},
				{colIndex:0, field:{xtype :'textfield', name:'branch',hidden:true}},
				{colIndex:0, field:{xtype :'textfield',id:'holiday_name_edit', name:'holiday_name',fieldLabel:'节假日名称',allowBlank : false,blankText:'请输入节假日名称',maxLength : 60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype:'datefield',id:'start_date_edit',  name:'start_date', fieldLabel:'开始日期(含当天)',format:'Y-m-d',allowBlank : false,blankText:'请选择开始时间',editable:false}},
				{colIndex:0, field:{xtype:'datefield',id:'end_date_edit',  name:'end_date',   fieldLabel:'结束日期(含当天)',format:'Y-m-d',allowBlank : false,blankText:'请选择结束时间',editable:false}},
				{colIndex:0, field:{layout:'column',fieldLabel:'主题编号',items:[{xtype : 'textfield', name:'theme_id',id:'theme_id_edit', 	fieldLabel:'主题编号',readOnly:true,blankText:'请选择主题编号',emptyText:'请选择主题编号',maxLength : 20,maxLengthText : '长度不能大于20位',width:'160'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){themeDetails();}}}]}},
				{colIndex:0, field:{xtype :'textarea',id:'note_edit', name:'note',fieldLabel:'备注',maxLength : 100,	maxLengthText : '长度不能大于100位'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : details},
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				]
			);
			/**
			 * 主题弹出窗
			 */
			function themeDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['theme_id','theme_name','theme_note','theme_imgsrc_list','branch','default_flag']
			    });
			    var detailData = [
					{header:'主题预览',dataIndex:'',width:100,align:'center',renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
						if(record.data['theme_id']=='默认'){
							return '';
						}
						return "<A href='javascript:void(0)' onclick=\"submitTheme('"+record.data['theme_id']+"','"+record.data['theme_name']+"','"+record.data['theme_note']+"','"+record.data['theme_imgsrc_list']+"');return false;\">主题预览</A>";
					}},
    				{header:'主题编号',dataIndex:'theme_id',width:100},
    				{header:'主题名称',dataIndex:'theme_name',width:120},
    				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
    					if(value!=""){
    						return value=='0'?'默认':'非默认';
    					}else{
    						return value;
    					}
    				}},
    				{header:'所属机构',dataIndex:'branch',width:80}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('themeDetailsWindow','主题列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'主题信息',
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
			  			if(record.data['theme_id']!='默认'){
			  				Ext.getCmp('theme_id_add').setValue(record.data['theme_id']);
				  			Ext.getCmp('theme_id_edit').setValue(record.data['theme_id']);
			  			}else{
			  				Ext.getCmp('theme_id_add').setValue('');
				  			Ext.getCmp('theme_id_edit').setValue('');
			  			}
			  		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    requestAjax('<%=basePath%>/confManager/themeManager_queryTheme',submitDetail, function(sRet){
			    	sRet.field1.unshift({'theme_id':'默认'});
			    	detailStore.loadData(sRet.field1);
				});
			}
			/**
			 * 弹出窗
			 */
			function details(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['holiday_id','branch','holiday_name','start_date','end_date','note']
			    });
			    var detailData = [
					{header:'节假日编号',dataIndex:'holiday_id',width:150},
					{header:'机构号',dataIndex:'branch',width:150},
					{header:'节假日名称',dataIndex:'holiday_name',width:150},
					{header:'开始时间' ,dataIndex:'start_date',width:150},
					{header:'结束时间' ,dataIndex:'end_date',width:150},
					{header:'备注',dataIndex:'note',width:150}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('detailsWindow','上级节假日列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
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
			  			Ext.getCmp('holiday_name_add').setValue(record.data['holiday_name']);
			  			Ext.getCmp('start_date_add').setValue(record.data['start_date']);
			  			Ext.getCmp('end_date_add').setValue(record.data['end_date']);
			  			Ext.getCmp('note_add').setValue(record.data['note']);
			  			
			  			Ext.getCmp('holiday_name_edit').setValue(record.data['holiday_name']);
			  			Ext.getCmp('start_date_edit').setValue(record.data['start_date']);
			  			Ext.getCmp('end_date_edit').setValue(record.data['end_date']);
			  			Ext.getCmp('note_edit').setValue(record.data['note']);
			  		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '5';
			    requestAjax('<%=basePath%>/confManager/holiday_queryHoliday',submitDetail, function(sRet){
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
						query_obj['query_rules'] = '1';
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
					    var submitdata = pagequeryObj.getSelectedObjects(['holiday_id','branch']);
					    if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/holiday_delHoliday',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										query_obj['query_rules'] = '1';
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
				requestAjax('<%=basePath%>confManager/holiday_addHoliday', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '1';
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
				
				requestAjax('<%=basePath%>confManager/holiday_editHoliday',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '1';
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
	<div id="detailsWindow"></div>
	<div id="themeDetailsWindow"></div>
  </body>
</html>
