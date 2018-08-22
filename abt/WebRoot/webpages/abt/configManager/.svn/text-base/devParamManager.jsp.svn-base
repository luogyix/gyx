<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute("logonuser");
String brno = user.getUnitid();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>设备参数管理</title>
    
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
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var addwindow;
		var editwindow;
		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		//是否默认标识
		var flagStore=new Ext.data.SimpleStore({ 
			data:[['0','非默认'],['1','默认']],
			fields : ['key','value']
		});
		//定时关机时间
		var timeStore = new Ext.data.SimpleStore({
			data:[['09:00','0'],['10:00','1'],['11:00','2'],['12:00','3'],['13:00','4'],['14:00','5'],['15:00','6'],['16:00','7'],['17:00','8'],['18:00','9'],['19:00','10'],['20:00','11'],['21:00','12'],['22:00','13'],['23:00','14'],['24:00','15']],
			fields : ['key','value']
		});
		
		var branch =<%="'" + user.getUnit().getUnitid() + "'"%>;
		/**
		*@Title: loadPage 
	 	*@Description: 版本管理界面
		*/
		function loadPage(){
	 		var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '设备参数管理', 120, 0,
					[
					],
					[
					{iconCls: "x-image-query",	text:'<s:text name="common.button.query"/>'},//查询
					{iconCls: "x-image-application_form_add", 		text:'<s:text name="common.button.addrecord"/>'},//添加
					{iconCls: "x-image-application_form_edit", 		text:'<s:text name="common.button.editrecord"/>'},	//修改
					{iconCls: "x-image-application_form_delete", 	text:'<s:text name="common.button.deleterecord"/>'}//删除
					],
					onButtonClicked
				);
		    conditionPanel.open();
		    var pagequeryObj = new PageQuery(
					true,'pageQueryTable',
					clientWidth,clientHeight - conditionPanel.getHeight(),
					'<%=basePath%>/confManager/selfDevParamAction_queryDevParamPage',
					[ 'parameter_id','branch','default_flag','time_pageout','time_mainshow','time_shutdown'], 
					[ 
					{header : '全选'}, 
					{header : '复选框'}, 
					{header : '设备参数编号',dataIndex : 'parameter_id',width : 150}, 
					{header : '机构号',dataIndex : 'branch',width : 150}, 
					{header : '是否默认标识',dataIndex : 'default_flag',width : 150,renderer:function(value){
						if(value==''||value==null){
							return '';
						}
		          		return value=="0"?"非默认":"默认";//翻译字段方法
					}}, 
					{header : '定时关机时间',dataIndex : 'time_shutdown',width : 150,renderer:function(value){
						var time=value.substr(0,2)+":"+value.substr(2,2);
						return time;
					}},
					{header : '界面超时时间',dataIndex : 'time_pageout',width : 150},
					{header : '主界面显示时间',dataIndex : 'time_mainshow',width : 150}
					], 
					'<s:text name="common.pagequery.pagingtool"/>'
			);
		    
		  	//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加设备参数信息', 350, 380, 200, 1,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'branch', 	    id:'branch_add',   fieldLabel:'机构号', 	  value:<%="'" + user.getUnit().getUnitid() + "'"%>,   allowBlank : false, blankText:'请输入机构号',maxLength : 30,	maxLengthText : '长度不能大于30位',readOnly:true}},
				{colIndex:0, field:{xtype : 'combo',     name:'time_shutdown',  id:'time_shutdown_add',   fieldLabel:'定时关机时间',hiddenName:"time_shutdown", allowBlank : false,blankText:'请选择',editable:false,store:timeStore,displayField:"key",valueField:"key"}},
				{colIndex:0, field:{xtype : 'textfield', name:'time_pageout', 	id:'time_pageout_add',   fieldLabel:'界面超时时间(秒)',	allowBlank : false,blankText:'请输入界面超时时间',maxLength : 5,maxLengthText : '长度不能大于5位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'time_mainshow', 	id:'time_mainshow_add',   fieldLabel:'主界面显示时间(秒)',	allowBlank : false,blankText:'请输入主界面显示时间',regex:/^[0-9]*$/,regexText:'只能输入数字',maxLength : 5,maxLengthText : '长度不能大于5位'}},
				{colIndex:0, field:{xtype : 'checkbox',  hideLabel : true,      id:'default_flag_add',   name:'default_flag', 	boxLabel:'默认标识',checked : false}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : searchDetails},
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
		  	
			//当选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '修改设备参数信息', 350, 410, 200, 1,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'parameter_id', 	id:'parameter_id_edit',   fieldLabel:'设备参数编号', 	 allowBlank : false, blankText:'请输入设备参数ID',maxLength : 30,	maxLengthText : '长度不能大于30位',readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'branch', 	    id:'branch_edit',   fieldLabel:'机构号', 	  value:<%="'" + user.getUnit().getUnitid() + "'"%>,   allowBlank : false, blankText:'请输入机构号',maxLength : 30,	maxLengthText : '长度不能大于30位',readOnly:true}},
				{colIndex:0, field:{xtype : 'combo',      name:'time_shutdown', id:'time_shutdown_edit',   fieldLabel:'定时关机时间',hiddenName:"time_shutdown",  allowBlank : false,blankText:'请选择', editable:false,store:timeStore,displayField:"key",valueField:"key"}},
				{colIndex:0, field:{xtype : 'textfield', name:'time_pageout', 	id:'time_pageout_edit',   fieldLabel:'界面超时时间(秒)',	allowBlank : false,blankText:'请输入界面超时时间',maxLength : 5,maxLengthText : '长度不能大于5位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'time_mainshow', 	id:'time_mainshow_edit',   fieldLabel:'主界面显示时间(秒)',	allowBlank : false,blankText:'请输入主界面显示时间',regex:/^[0-9]*$/,regexText:'只能输入数字',maxLength : 5,maxLengthText : '长度不能大于5位'}},
				{colIndex:0, field:{xtype : 'checkbox',  hideLabel : true,      id:'default_flag_edit',   name:'default_flag', 	boxLabel:'默认标识',checked : false}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : searchDetails},
					{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close();}}
				]
			);
			
			/**
			 * 上级机构参数弹出窗
			 */
			function searchDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['parameter_id','default_flag','branch','time_shutdown','time_pageout','time_mainshow']
			    });
			    var detailData = [
				{header:'自助设备参数编号',dataIndex:'parameter_id',width:100},
				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
					return value=='1'?'默认':'非默认';}},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'定时关机时间',dataIndex:'time_shutdown',width:100,renderer:function(value){
					return value.substr(0,2)+":"+value.substr(2,2);
				}},
				{header:'界面超时时间(秒)',dataIndex:'time_pageout',width:100},
				{header:'主界面显示时间(秒)',dataIndex:'time_mainshow',width:100}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('searchDetailsWindow','上级机构排队机参数列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'上级排队机参数信息',
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
		  			var time= record.data['time_shutdown'];
					var time_shutdown=time.substr(0,2)+":"+time.substr(2,2);
		  			Ext.getCmp('time_shutdown_add').setValue(time_shutdown);
		  			Ext.getCmp('time_shutdown_edit').setValue(time_shutdown);
		  			Ext.getCmp('time_pageout_add').setValue(record.data['time_pageout']);
		  			Ext.getCmp('time_pageout_edit').setValue(record.data['time_pageout']);
		  			Ext.getCmp('default_flag_add').setValue(record.data['default_flag']);
		  			Ext.getCmp('default_flag_edit').setValue(record.data['default_flag']);
		  			Ext.getCmp('time_mainshow_add').setValue(record.data['time_mainshow']);
		  			Ext.getCmp('time_mainshow_edit').setValue(record.data['time_mainshow']);
		  		});
		    
		    detailsWindow.open();
		    var submitDetail= {};
		    submitDetail['query_rules'] = '5';
		    requestAjax('<%=basePath%>/confManager/selfDevParamAction_queryDevParam.action',submitDetail, function(sRet){
		    	detailStore.loadData(sRet.field1);
			});
		}
		    
		    /**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"
			 */
			function onButtonClicked(btn_index) {
				switch (btn_index) {
				case 0:
					var query_obj = conditionPanel.getFields();
					query_obj['query_rules'] = '5';
					pagequeryObj.queryPage(query_obj);
					break;
				case 1://添加
					addwindow.open();
					break;
				case 2://修改
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
					//无法修改非本行机构
				    for(var i=0;i<records.length;i++){
				    	if(records[i].data.branch!=<%="'"+brno+"'"%>){
				    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
				    		return;
				    	}
				    }
					editwindow.open();
					var time= records[0].data.time_shutdown;
					var time_shutdown=time.substr(0,2)+":"+time.substr(2,2);
					records[0].data.time_shutdown=time_shutdown;
					editwindow.updateFields(records[0]);
					break;
				case 3:
				    var submitdata = pagequeryObj.getSelectedObjects(['parameter_id','branch']);
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
							requestAjax('<%=basePath%>/confManager/selfDevParamAction_delDevParam.action',submitdata,function(sRet){
								Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
									var query_obj = conditionPanel.getFields();
									query_obj['query_rules'] = '5';
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
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				createCheckBoxValue('default_flag',submitData);
				var time_shutdown=submitData['time_shutdown'];
				submitData['time_shutdown']=time_shutdown.substr(0,2)+time_shutdown.substr(3,2);
				requestAjax('<%=basePath%>/confManager/selfDevParamAction_addDevParam.action', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '5';
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
				createCheckBoxValue('default_flag',submitData);
				var time_shutdown=submitData['time_shutdown'];
				submitData['time_shutdown']=time_shutdown.substr(0,2)+time_shutdown.substr(3,2);
				requestAjax('<%=basePath%>/confManager/selfDevParamAction_editDevParam.action',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '5';
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
			}
			 
		    
		    /**
			 * @Title:buildLayout
			 * @Description:创建布局函数
			 */
			function buildLayout() {
				var viewport = new Ext.Viewport({
					layout : "border",
					items : [ conditionPanel.toolbarwindow,
							pagequeryObj.pagingGrid ]
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
	<div id="searchDetailsWindow"></div>
	
  </body>
</html>
