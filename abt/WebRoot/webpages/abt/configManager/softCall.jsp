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
    
    <title>软叫号配置</title>
    
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
		
/**
 * @Title: loadPage 
 * @Description: 显示软叫号维护界面
 */	
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '软叫号配置', 120, 0,
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
				'<%=basePath%>confManager/softCall_querySoftCallPage',
				['branch','softcall_id','default_flag','status','recall_limit','call_next_limit'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'软叫号配置编号',dataIndex:'softcall_id',width:100},
				{header:'是否默认标志',dataIndex:'default_flag',width:100,renderer:function(value){return value=='1'?'默认':'非默认'}},
				{header:'软叫号启用状态',dataIndex:'status',width:100,renderer:function(value){return value=='1'?'可用':'不可用'}},
				{header:'重呼限制时间',dataIndex:'recall_limit',width:100},
				{header:'顺呼限制时间',dataIndex:'call_next_limit',width:100}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加软叫号信息', 530, 220, 120, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'recall_limit',id:'recall_limit_add', 	fieldLabel:'重呼限制时间(秒)',emptyText:'输入限制时间(秒)',	allowBlank : false,blankText:'请输入重呼限制时间',regex:/^[0-9]*$/,regexText:'只能输入数字',maxLength : 10,maxLengthText : '长度不能大于10位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'call_next_limit',id:'call_next_limit_add', 	fieldLabel:'顺呼限制时间(秒)',emptyText:'输入限制时间(秒)',	allowBlank : false,blankText:'请输入顺呼限制时间',regex:/^[0-9]*$/,regexText:'只能输入数字',maxLength : 10,maxLengthText : '长度不能大于10位'}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'status',id:'status_add', 	boxLabel:'启用' ,checked : true}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'default_flag',id:'default_flag_add', 	boxLabel:'设为默认'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : softCallDetails},
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',110
			);
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑软叫号信息', 530, 220, 120, 2,
			   [
				{colIndex:0, field:{xtype : 'textfield', name:'branch', 	fieldLabel:'机构号',readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'softcall_id', 	fieldLabel:'软叫号配置编号',readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'recall_limit',id:'recall_limit_edit', 	fieldLabel:'重呼限制时间(秒)',emptyText:'输入限制时间(秒)',	allowBlank : false,blankText:'请输入重呼限制时间',regex:/^[0-9]*$/,regexText:'只能输入数字',maxLength : 10,maxLengthText : '长度不能大于10位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'call_next_limit', id:'call_next_limit_edit', 	fieldLabel:'顺呼限制时间(秒)',emptyText:'输入限制时间(秒)',	allowBlank : false,blankText:'请输入顺呼限制时间',regex:/^[0-9]*$/,regexText:'只能输入数字',maxLength : 10,maxLengthText : '长度不能大于10位'}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'status',id:'status_edit', 	boxLabel:'启用' }},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'default_flag',id:'default_flag_edit', 	boxLabel:'设为默认'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : softCallDetails},
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',110
			);
			 /**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"，"重置"，"添加"，"修改"，"删除"的选择语句
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '4';
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
						
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['branch','softcall_id']);
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
								requestAjax('<%=basePath%>confManager/softCall_delSoftCall',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										query_obj['query_rules'] = '4';
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					    break;
				}
			}
			//软叫号配置编号
            function softCallDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['branch','softcall_id','default_flag','status','recall_limit','call_next_limit']
			    });
			    var detailData = [
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'软叫号配置编号',dataIndex:'softcall_id',width:100},
					{header:'是否默认标志',dataIndex:'default_flag',width:100,renderer:function(value){return value=='1'?'默认':'非默认'}},
					{header:'软叫号启用状态',dataIndex:'status',width:100,renderer:function(value){return value=='1'?'可用':'不可用'}},
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
	    			Ext.getCmp('recall_limit_add').setValue(record.data['recall_limit']);
	    			Ext.getCmp('recall_limit_edit').setValue(record.data['recall_limit']);
	    			Ext.getCmp('call_next_limit_add').setValue(record.data['call_next_limit']);
	    			Ext.getCmp('call_next_limit_edit').setValue(record.data['call_next_limit']);
	    			Ext.getCmp('status_add').setValue(record.data['status']);
	    			Ext.getCmp('status_edit').setValue(record.data['status']);
	    			Ext.getCmp('default_flag_add').setValue(record.data['default_flag']);
	    			Ext.getCmp('default_flag_edit').setValue(record.data['default_flag']);
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '5';
			    requestAjax('<%=basePath%>/confManager/softCall_querySoftCall',submitDetail, function(sRet){
				      detailStore.loadData(sRet.field1);
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
				createCheckBoxValue('default_flag',submitData);
				requestAjax('<%=basePath%>confManager/softCall_addSoftCall', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '4';
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
				createCheckBoxValue('status',submitData);
				createCheckBoxValue('default_flag',submitData);
				requestAjax('<%=basePath%>confManager/softCall_editSoftCall',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '4';
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
	<div id="softCallDetailsWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>
