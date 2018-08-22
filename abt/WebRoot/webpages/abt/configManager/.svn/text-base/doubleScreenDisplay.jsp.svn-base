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
    
    <title>二层屏信息配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		var styleStore=new Ext.data.SimpleStore({ 
			data:[["0","立即显示"],["1","从右向左移"],["2","从下向上移"],["3","从左向右展开"],["4","百叶窗从左向右展开"],["5","从上向下展开"],["6","从上向下移"],["7","闪烁显示"],["8","连续左移"]],
			fields : ['key','value']
		});
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示客户类型界面
 */		var priv=pagereturn.field2;
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '二层屏配置', 120, 0,
				[
				],
				[
				{iconCls: "x-image-query", 			        id:'01',    text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked,priv
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>confManager/doubleScreenDisplay_queryScreenPage',
				['branch','doublescreen_id','default_flag','content'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
					return value=='1'?'默认':'非默认';}},
				{header:'二层屏编号',dataIndex:'doublescreen_id',width:100},
				{header:'二层屏显示内容',dataIndex:'content',width:600}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加二层屏信息', 400, 200, 250, 1,
				[
				{colIndex:0, field:{xtype : 'textarea', name:'content',id:'content_add', 	fieldLabel:'二层屏内容', 	allowBlank : false, blankText:'请输入显示内容',maxLength : 1000,	maxLengthText : '长度不能大于1000位'	}},
				//{colIndex:1, field:{xtype : 'combo',     name:'free_style',id:'free_style_add',   fieldLabel:'显示效果',	allowBlank : false, hiddenName:'free_style',value:0,editable:false,blankText : '请选择显示效果', store:styleStore, 	displayField:'value', valueField:'key'}},
				//{colIndex:0, hiddenLabel:true,field:{width:240,html:'<font color="red">注:     排队号:@@@@@,窗口:##</font>'}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'default_flag',id:'default_flag_add', 	boxLabel:'设为默认'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : screenDetails},
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',80
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑二层屏信息', 400, 200, 250, 1,
			   [
				{colIndex:0, field:{xtype : 'textfield', name:'branch', 	fieldLabel:'机构号',id:'branch', readOnly:true , hidden:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'doublescreen_id', 	fieldLabel:'二层屏信息编号',id:'doublescreen_id', readOnly:true, hidden:true}},
				{colIndex:0, field:{xtype : 'textarea', name:'content',id:'free_content_edit', 	fieldLabel:'显示内容', 	allowBlank : false, blankText:'请输入显示内容',maxLength : 1000,	maxLengthText : '长度不能大于1000位'	}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'default_flag',  id:'default_flag_edit', 	boxLabel:'设为默认'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : screenDetails},
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',80
			);
			//screenDetails
            function screenDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['branch','doublescreen_id','default_flag','content']
			    });
			    var detailData = [
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
						return value=='1'?'默认':'非默认';}},
					{header:'二层屏编号',dataIndex:'doublescreen_id',width:100},
					{header:'二层屏显示内容',dataIndex:'content',width:600}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('screenDetailsWindow','二层屏参数配置编号列表',600, 355, 555, 1, [555],[{
		    		colIndex:0,
		    		field:{
		    			xtype:'fieldset',
		    			title:'二层屏参数配置信息',
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
	    			Ext.getCmp('content_add').setValue(record.data['content']);
	    			Ext.getCmp('content_edit').setValue(record.data['content']);
	    			Ext.getCmp('default_flag_add').setValue(record.data['default_flag']);
	    			Ext.getCmp('default_flag_edit').setValue(record.data['default_flag']);
	    			//返回值
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '5';
			    requestAjax('<%=basePath%>/confManager/doubleScreenDisplay_queryScreen',submitDetail, function(sRet){
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
					    var submitdata = pagequeryObj.getSelectedObjects(['branch','doublescreen_id']);
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
								requestAjax('<%=basePath%>confManager/doubleScreenDisplay_delScreen',submitdata,function(sRet){
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
				requestAjax('<%=basePath%>confManager/doubleScreenDisplay_editScreen',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '4';
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
				requestAjax('<%=basePath%>confManager/doubleScreenDisplay_addScreen', submitData,
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
  
  <body>
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="editWindow"></div>
	<div id="screenDetailsWindow"></div>
  </body>
</html>
