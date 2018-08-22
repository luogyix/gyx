<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
	String template_id = (String)request.getSession().getAttribute("template");
	String brno;
	if(template_id!=null){
		brno = template_id;
	}else{
		brno = user.getUnitid();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>自助设备菜单管理</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
		-->
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="selfcss/common.css" />
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.js"></script>
		<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
		<script type="text/javascript" src="extjs/ux/CheckColumn.js"></script>
		<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
		
		<script type="text/javascript">
			Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
			Ext.QuickTips.init();
			var moduleId;//点击树时左键点击的节点ID
			var titles = "自助设备菜单配置";
			var items = [];
			var treeData = [{"menu_id":"root",	"menu_parent_id":"-1",	"menu_name_ch":"自助菜单",	"menu_action":"","menu_type":"0",	"menu_location":"1","menu_level":"1"}];
//----------------------------------------------------------------
			var tree = new Ext.tree.TreePanel({
				useArrows:true, rootVisible:false, anchor:'94%', enableDrag:true, enableDD:true
	    	});
			var queryFlag = false;
			//开始构建自助设备菜单 "树"
			var menuModules = treeData; 
			/*定义json串对应意义，按照需要定义
			 * menu_id 树节点ID
			 * parent_id 父节点ID
			 * node_name 树节点名称
			 * node_action 节点路径 url用来弹出新窗口用打的
			 * menu_type 菜单类型：0-系统；1-文件夹；2-页面；3-动作
			 * menu_location 排序标记
			 * menu_level 层级
			 */
			var jsonMeta = {
				nodeId:'menu_id',
				parentNodeId:'menu_parent_id',
				nodeName:'menu_name_ch',
				nodeNameEn:'menu_name_en',
				nodeHref:'menu_action',
				nodeTarget:'',
				leafField:'',
				nodeType:'menu_type',
				nodeOrder:'menu_location',
				nodeLevel:'menu_level'
			};
			//业务类型
			var businessStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>confManager/business_queryBusinessSmall?query_rules=4',
				autoLoad:true,
				root   : 'field1',
				fields:['bs_id','bs_name_ch']
			});
			//定义树生成器
			var treeGenerator = new SelfTreeGenerator(
				menuModules,
				jsonMeta,
				'<%=basePath%>',
				['x-image-house','x-image-package_tiny','',''], 
				['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']
			);
			//定义菜单树
			var SelfMenuTree = treeGenerator.generate(false,false,false,false);
			var activetreenode = null;
			var activenodedata = null;
			/*菜单类型*/
			var menutypeStore = new Ext.data.SimpleStore({
				data:[["0","直接使用"],["1","需授权使用"]],
				fields : ['key','value']
			});
			/*所属业务类别*/
			var busitypeStore = new Ext.data.SimpleStore({
				data:[["0","对公业务"],["1","个人业务"]],
				fields : ['key','value']
			});
			/*菜单状态*/
			var menustatusStore = new Ext.data.SimpleStore({
				data:[["0","可见"],["1","隐藏"]],
				fields : ['key','value']
			});
			/*菜单未生效时状态*/
			var disablestatusStore = new Ext.data.SimpleStore({
				data:[["0","不可见"],["1","加锁"],["2","置灰"]],
				fields : ['key','value']
			});
			/*删除标记*/
			var deleteFlagStore = new Ext.data.SimpleStore({
				data:[["0","正常菜单"],["1","已弃用菜单"]], 
				fields : ['key','value']
			});
			var menuStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>/confManager/selfMenu_querySelfDevMenuById',
				root   : 'field1',
				fields:['parameter_id','menu_id','menu_level','menu_location','menu_parent_id','menu_type','menu_name_ch','menu_name_en','bs_id','menutype','busitype','menustatus','disablestatus','deleteflag','menu_serv_time'],
    			listeners : {
    				load : function(store,records){
    					treeData = [];
    					for(var i = 0;i<store.data.length;i++){
    						var record = store.getAt(i).data;
    						var tree = {};
    						tree['menu_name_ch'] = record['menu_name_ch'];
    						tree['menu_name_en'] = record['menu_name_en'];//英文目录名
    						tree['menu_id'] = record['menu_id'];
    						tree['menu_type'] = record['menu_type'];
    						tree['menu_level'] = record['menu_level'];
    						tree['menu_location'] = record['menu_location'];
    						tree['menu_parent_id'] = record['menu_parent_id'];
    						tree['menu_action']="";
    						treeData.push(tree);
    					}
    					
    					menuModules = treeData; 
						treeGenerator = new SelfTreeGenerator(
							menuModules,jsonMeta,
							'<%=basePath%>',
							['x-image-house','x-image-package_tiny','',''], 
							['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']
						);
						SelfMenuTree = treeGenerator.generate(false,false,true,false);
						Ext.getCmp('viewTree').setRootNode(SelfMenuTree);
						queryFlag = true;
    				}
    			}
			});
			
			var systemParamStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>/confManager/systemParameter_querySystemParameter',
				root   : 'field1',
				fields:['parameter_id','parameter_name','branch','default_flag','parameter_flag']
			});
//----------------------------------------store--------------------------------------------------------------------------
			Ext.onReady(loadPage);
			function loadPage(){
				//取界面长宽
				var clientHeight = document.body.clientHeight;
				var clientWidth  = document.body.clientWidth;
				
//-------------------------------------------------------------------------编号弹出窗 开始				
				//系统参数弹出框form
				var systemParamForm = new Ext.FormPanel({
					labelWidth: 75,
					labelAlign : 'right',
					//bodyStyle:'padding:5px 5px 0',
					//defaults: {width: 210},
			        //defaultType: 'numberfield',
			        frame : false,
			        items: [{
		                xtype: 'grid',
		                //title: '参数列表',
		                height: 385,
		                //autoHeight : true, 
						name:'systemParamGrid',
						id:'systemParamGrid',
						store: systemParamStore,
						autoScroll :true,
						viewConfig: {forceFit: false},
						columns: [
						  	//new Ext.grid.RowNumberer({width: 30}),
						  	//'systemParam_id','systemParam_name','systemParam_note','branch','systemParam_flag'
				            {header: '参数编号', width: 80, dataIndex: 'parameter_flag', hidden:true},
				            {header: '参数编号', width: 80, dataIndex: 'parameter_id', hidden:true},
				            {header: '参数名称', width: 150, dataIndex: 'parameter_name', sortable:true},
				            {header: '参数来源', width: 100, dataIndex: 'branch', sortable:true},
				            {header: '是否默认', width: 100, dataIndex: 'default_flag', sortable:true,renderer:function(value){
								return value=='1'?'默认':'非默认';
								}
				            }
				        ],
				        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
						tbar: new Ext.Toolbar({
							id : 'tool',
							items: [{
				    			text : '<b>新建参数</b>',
				    			icon:'images/49.png',
				    			tooltip : '<b>提示</b><br/>创建新参数',
				    			handler : function(b,e){
				    				Ext.getCmp('parameter_name').setValue('');
				    				Ext.getCmp('default_flag').setValue(false);
				    				Ext.getCmp('operateMode').setValue('add');
				    				systemParam_add_window.setTitle('<span style="font-weight:normal">新建参数</span>');
				    				systemParam_add_window.show();
				    			}
				    		},'-',{
				    			text : '<b>修改参数</b>',
				    			icon:'images/49.png',
				    			tooltip : '<b>提示</b><br/>修改参数',
				    			handler : function(b,e){
				    				var record = Ext.getCmp('systemParamGrid').getSelectionModel().getSelected();
				    				if (Ext.isEmpty(record)) {
				    					Ext.Msg.alert('提示', '请先选中一个参数!');
				    					return;
				    				}
				    				var brno = '<%=brno%>';
				    				if(record.get('branch')!=brno){
				    					Ext.Msg.alert('提示', '无法修改非本机构的参数!');
				    					return;
				    				}
				    				systemParamAddForm.getForm().loadRecord(record);
				    				systemParam_add_window.setTitle('<span style="font-weight:normal">修改参数</span>');
				    				Ext.getCmp('operateMode').setValue('edit');
				    				systemParam_add_window.show();
				    			}
				    		},'-',{
				    			text : '<b>删除参数</b>',
				    			icon:'images/49.png',
				    			tooltip : '<b>提示</b><br/>删除所选参数',
				    			handler : function(b,e){
				    				var record = Ext.getCmp('systemParamGrid').getSelectionModel().getSelected();
				    				if (Ext.isEmpty(record)) {
				    					Ext.Msg.alert('提示', '请先选中要删除的参数!');
				    					return;
				    				}
				    				var brno = '<%=brno%>';
				    				if(record.get('branch')!=brno){
				    					Ext.Msg.alert('提示', '无法删除非本机构的参数!');
				    					return;
				    				}
				    				Ext.Msg.confirm('提示','确定要删除选中的参数吗?',function(btn, text){
										if (btn == 'yes') {
											var submitdata = {};
											submitdata['parameter_id'] = record.get('parameter_id');
											requestAjax('<%=basePath%>confManager/systemParameter_delSystemParameterById',submitdata,function(sRet){
												Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
													systemParamStore.load({params:{'branch':brno,'parameter_flag':'01'}});
												});
											});
										}
									});
				    			}
				    		},'-',{
				    			text : '<b>复制参数</b>',
				    			icon:'images/49.png',
				    			tooltip : '<b>提示</b><br/>复制已有参数至新参数项',
				    			handler : function(b,e){
				    				var record = Ext.getCmp('systemParamGrid').getSelectionModel().getSelected();
				    				if (Ext.isEmpty(record)) {
				    					Ext.Msg.alert('提示', '请先选中要复制的参数!');
				    					return;
				    				}
				    				systemParamAddForm.getForm().loadRecord(record);
				    				systemParam_add_window.setTitle('<span style="font-weight:normal">复制参数</span>');
				    				Ext.getCmp('operateMode').setValue('copy');
				    				systemParam_add_window.show();
				    			}
				    		}]
						})
		            }]
				});
				
				//弹出的属性框
				var systemParamWindow = new Ext.Window({
					layout : 'fit', // 设置窗口布局模式
					width: 400,
				    height : 300,
				    resizable: false,
				    id:'systemParamWindow',
				    draggable : true,
				    closeAction : 'hide',
				    closable : false, 
				    modal : true, //无法失去焦点
					title : '<span style="font-weight:normal">参数弹出窗</span>', // 窗口标题
					collapsible : false, // 是否可收缩
					titleCollapse : false,
					maximizable : false, // 设置是否可以最大化
					buttonAlign : 'right',
					border : false, // 边框线设置
					animCollapse : true,
					pageY : document.body.clientHeight/2-300/2, // 页面定位Y坐标
					pageX : document.body.clientWidth / 2 - 400 / 2, // 页面定位X坐标
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [systemParamForm], // 嵌入的表单面板
					buttons : [{
						text : '关闭', // 按钮文本
						handler : function() { // 按钮响应函数
							systemParamWindow.hide();
						}
					}]
				});	    	
				var timeStore = new Ext.data.JsonStore({
					fields : ['begin_time','end_time']
				});
		    	
		    	var timeData = [
	   				new Ext.grid.RowNumberer({header:'序号',width:33}),
	   				{xtype: 'gridcolumn', header:'复选框', align: 'center',hidden:true},
	   				{ xtype: 'gridcolumn',dataIndex: 'begin_time',header: '服务时间(从)',sortable:false,editor: {xtype: 'timefield',format:'H:i:s',increment:30,minValue: '08:00:00',maxValue: '18:00:00'}},
			      	{ xtype: 'gridcolumn',dataIndex: 'end_time',header: '服务时间(到)',sortable:false,editor: {xtype: 'timefield',format:'H:i:s',increment:30,minValue: '08:00:00',maxValue: '18:00:00'}}
	   			];
		    	var timeSM = new Ext.grid.CheckboxSelectionModel({ singleSelect: false });
		    	var timeCM = new Ext.grid.ColumnModel(timeData);
		    	
				var timeGrid = new Ext.grid.EditorGridPanel({
					id: 'timeGrid',
				    store: timeStore,
				    colModel: timeCM,
				    sm:timeSM,
				    stripeRows: true,
				    height:300,
				    frame:true,
				    viewConfig: {forceFit: true},
				    clicksToEdit:1,
				    tbar: [
				    	{id:'addBtn', text: '添加', disabled: false, iconCls: "x-image-add", 
				    		handler: function(){
								var length= timeStore.getCount();
	   							var recordType = timeStore.recordType;
	   							var num = timeStore.getCount();
	   							var record = new recordType({
	   								begin_time:'',end_time:''
								});
	   							timeGrid.stopEditing();
	   							timeStore.insert(length, record);
	   							timeGrid.getView().refresh();
	   							timeSM.selectRow(length);
	   							timeGrid.startEditing(timeStore.getCount()-1, 3);
				    		} 
				    	},'-',
				    	{id:'delBtn', text: '删除', disabled: false, iconCls: "x-image-delete", 
				    		handler: function(){
				    			if(!timeSM.hasSelection()){
									Ext.Msg.alert('<s:text name="common.info.title"/>' , '<s:text name="common.info.mustselectrecord"/>');
									return;
								}
								Ext.MessageBox.confirm('<s:text name="common.info.title"/>' , '<s:text name="common.info.suredelete"/>' , function(id){
									if(id=='yes'){
										var records = timeSM.getSelections();
										for(var i=0; i<records.length; i++){
											timeStore.remove(records[i]);
										}
									}
								});
				    		}
				    	}
				    ]
				});
				
				timeGrid.on('afteredit',function(e,a,b,c){
					var record = e.record;
					//e.originalValue;
					if(record.get('end_time')!=''&&record.get('begin_time')!=''){//判断都非空
						if (record.get('begin_time') >= record.get('end_time')) {
							Ext.MessageBox.alert('提示','起始时间必须小于结束时间',function(id){
								record.set(e.field,e.originalValue);
								return;
							});
						}
						//本条内条件皆通过,与已有数据进行比对
						for ( var i = 0; i < timeStore.getCount(); i++) {
							//e.row 所在行 一会循环要排除 e.column所在列 
							if(i==e.row){
								continue;
							}
							var r = timeStore.getAt(i);
							if(record.get('begin_time') < r.get('begin_time') && record.get('end_time') > r.get('begin_time')){
								Ext.MessageBox.alert('提示','['+record.get('begin_time')+'-'+record.get('end_time')+']与['+r.get('begin_time')+'-'+r.get('end_time')+']的时间段冲突,请重新修改',function(id){
									record.set(e.field,e.originalValue);
									return;
								});
							}else if(record.get('begin_time') >= r.get('begin_time') && record.get('begin_time')<r.get('end_time')){
								Ext.MessageBox.alert('提示','['+record.get('begin_time')+'-'+record.get('end_time')+']与['+r.get('begin_time')+'-'+r.get('end_time')+']的时间段冲突,请重新修改',function(id){
									record.set(e.field,e.originalValue);
									return;
								});
							}
						}
					}
				});
				var systemParamAddForm = new Ext.FormPanel({
					labelWidth: 60,
					labelAlign : 'right',
					bodyStyle:'padding:5px 5px 0',
			        defaultType: 'textfield',
			        frame : false,
			        items: [{
		                fieldLabel: '参数名称',
		                name:'parameter_name',
		                id:'parameter_name',
		                blankText:'请输入参数名称',
		                maxLength : 50,
		                maxLengthText : '长度不能大于50位',
		                allowBlank : false
		            },{
		                fieldLabel: '',
		                boxLabel: '默认',
		                xtype:'checkbox',
		                name:'default_flag',
		                id:'default_flag'
		            },{//隐藏区域
		                id:'parameter_id',
		                name:'parameter_id',
		                xtype:'hidden'
		            },{//隐藏区域
		                id:'parameter_flag',
		                name:'parameter_flag',
		                xtype:'hidden'
		            },{//隐藏区域
		                id:'branch',
		                name:'branch',
		                xtype:'hidden'
		            },{
						id : 'operateMode',
						name : 'operateMode',
						hidden : true
					}]
				});
				var systemParam_add_window = new Ext.Window({
					layout : 'fit', // 设置窗口布局模式
					width: 250,
				    height : 150,
				    resizable: false,
				    id:'systemParam_add_window',
				    draggable : true,
				    closeAction : 'hide',
				    closable : false, 
				    modal : true, //无法失去焦点
					title : '<span style="font-weight:normal">新建参数</span>', // 窗口标题
					collapsible : false, // 是否可收缩
					titleCollapse : false,
					maximizable : false, // 设置是否可以最大化
					buttonAlign : 'right',
					border : false, // 边框线设置
					animCollapse : true,
					pageY : document.body.clientHeight/2-150/2, // 页面定位Y坐标
					pageX : document.body.clientWidth / 2 - 250 / 2, // 页面定位X坐标
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [systemParamAddForm], // 嵌入的表单面板
					buttons : [{ // 窗口底部按钮配置
						text : '提交', // 按钮文本
						id:'btn_go',
						handler : function() { // 按钮响应函数
								if (!systemParamAddForm.form.isValid()) {
									return;
								}
								var array=systemParamAddForm.getForm().getFieldValues();
								array['parameter_flag'] = '01';
								var mode = Ext.getCmp('operateMode').getValue();
								if (mode == 'add'){
									//通知后台的方法.添加
									Ext.Ajax.request({
										url: '<%=basePath%>confManager/systemParameter_addSystemParameter',  
										params: {strJson:Ext.encode(array)},
										success:function(response,opt){
											var responseText = response.responseText;
											if(responseText.indexOf('false')!=-1){
												Ext.MessageBox.alert('提示', '此机构已存在默认的参数');
												return;
											}
											systemParam_add_window.hide();
											Ext.MessageBox.alert('提示', '添加参数成功');
											var brno = '<%=brno%>';
											systemParamStore.load({params:{'branch':brno,'parameter_flag':'01'}});
										}
									});
								}else if (mode == 'edit'){
									//通知后台的方法.修改
									Ext.Ajax.request({
										url: '<%=basePath%>confManager/systemParameter_editSystemParameter',  
										params: {strJson:Ext.encode(array)},
										success:function(response,opt){
											var responseText = response.responseText;
											if(responseText.indexOf('false')!=-1){
												Ext.MessageBox.alert('提示', '此机构已存在默认的参数');
												return;
											}
											systemParam_add_window.hide();
											Ext.MessageBox.alert('提示', '修改参数成功');
											var brno = '<%=brno%>';
											systemParamStore.load({params:{'branch':brno,'parameter_flag':'01'}});
										}
									});
								}
						}
					},{ // 窗口底部按钮配置
						text : '关闭', // 按钮文本
						handler : function() { // 按钮响应函数
							systemParam_add_window.hide();
						}
					}]
				});
				
//-------------------------------------------------------------------------排队机列表
				function onMenuClick(menu,menuitem,e){
					switch(menuitem.id){
						case "nodeaddmulu":
							//alert(activetreenode.attributes.text+'ID:'+activetreenode.attributes.id);//节点id
							//alert(activetreenode.parentNode.id);//父节点id
							//alert(activetreenode.attributes.type);//菜单类型：0-系统；1-文件夹；2-页面；
							//判断当前节点是不是文件夹，如果是，正常打开新增目录界面，如果是业务配置节点，则弹出提示窗告诉业务节点不可新增目录
							if(activetreenode.attributes.leaf){
								Ext.MessageBox.alert('<s:text name="common.info.title"/>','业务类节点不可新增目录');
								return;
							}
							changeLeaf('1');
						break;
						case "nodeaddbusi":
							if(activetreenode.attributes.leaf){
								Ext.MessageBox.alert('<s:text name="common.info.title"/>','业务类节点不可新增节点');
								return;
							}
							changeLeaf('3');
						break;
						case "nodedelete":
							//删除节点
							Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定删除节点?',function(id){
								if(id === 'yes'){
									if(activetreenode.hasChildNodes()){
										Ext.MessageBox.alert('消息提示','请先删除此节点的子节点');
										return;
									}
									var submitData = {};
		                  			var parent = activetreenode.parentNode;
		                  			submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
		                			submitData['menu_id'] = activenodedata.menu_id;//父节点menu_id
		                			submitData['parent_menu_id'] = parent.attributes.id=='root'?'':parent.attributes.id;//父节点menu_id
		                			
									requestAjax('<%=basePath%>confManager/selfMenu_deleteMenuLeafById', submitData,function(sRet){
										Ext.MessageBox.alert('<s:text name="common.info.title"/>','删除节点成功',function(id){
											menuStore.reload();
											Ext.getCmp('fieldset_add_type').removeAll(true);
	   	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
										});
									});
								}
							});
						break;
						case "nodemoveup":
							var submitData = {};
							var childNodes=activetreenode.parentNode.childNodes;
							//判断节点的位置
						    if(childNodes.length==1||activetreenode==activetreenode.parentNode.firstChild){
						    	Ext.MessageBox.alert('消息提示','该节点无法上移');
						    	return;
						    }
						  //找到节点的位置
						    for(var j=0;j<childNodes.length;j++){
						    	if(activetreenode==childNodes[j]){
						    		break;
						    	}
						    }
						    submitData['menu_id'] = activenodedata.menu_id;
						    submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();
						    submitData['menu_location'] = parseInt(activenodedata.menu_location)-1;
						    submitData['menu_id1'] = childNodes[j-1].id;
						    submitData['menu_location1'] = activenodedata.menu_location;
						    requestAjax('<%=basePath%>confManager/selfMenu_nodeMoveUpById', submitData,function(sRet){
								Ext.MessageBox.alert('<s:text name="common.info.title"/>','节点移动成功',function(id){
									menuStore.reload();
									Ext.getCmp('fieldset_add_type').removeAll(true);
	   						    	Ext.getCmp('fieldset_add_type').setTitle(titles);
								});
							});
						break;
						case "nodemovedown":
							var submitData = {};
							//判断节点的位置
							var childNodes=activetreenode.parentNode.childNodes;
						    if(childNodes.length==1||activetreenode==activetreenode.parentNode.lastChild){
						    	Ext.MessageBox.alert('消息提示','该节点无法下移');
						    	return;
						    }
						  //找到节点的位置
						    for(var j=0;j<childNodes.length;j++){
						    	if(activetreenode==childNodes[j]){
						    		break;
						    	}
						    }
						    submitData['menu_id'] = activenodedata.menu_id;
						    submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();
						    submitData['menu_location'] = parseInt(activenodedata.menu_location)+1;
						    submitData['menu_id1'] = childNodes[j+1].id;
						    submitData['menu_location1'] = activenodedata.menu_location;
						    requestAjax('<%=basePath%>confManager/selfMenu_nodeMoveDownById', submitData,function(sRet){
								Ext.MessageBox.alert('<s:text name="common.info.title"/>','节点移动成功',function(id){
									menuStore.reload();
									Ext.getCmp('fieldset_add_type').removeAll(true);
	   						    	Ext.getCmp('fieldset_add_type').setTitle(titles);
								});
							});
						break;
					}
				}
				//定义右键弹出的按钮
				var treecontextmenu = new Ext.menu.Menu({
		    		id:'treecontextmenu',
		    		items: [
						{id:'nodeaddmulu', text:'添加目录'},
						{id:'nodeaddbusi', text:'添加业务'},
						{id:'nodedelete', text:'删除节点'},
						{id:'nodemoveup',text:'上移'},
						{id:'nodemovedown',text:'下移'}
		    		]
				});
				//定义右键响应事件
				treecontextmenu.on('click',onMenuClick,this);
				//菜单弹出事件
				function onmenu(node,e){
					if(Ext.getCmp('systemParamCombo').getValue()==""){
						Ext.MessageBox.alert('消息提示','请先选定排队机');
						return;
					}
					//点击时判断是文件夹还是业务节点，根据不同的类型，禁用不同的下拉列表事件					 
					activetreenode = node;
					for(var i=0;i<menuModules.length;i++)
					{
						if(menuModules[i][jsonMeta.nodeId] === node.id){
							activenodedata = menuModules[i];
							break;
						}
					}
					
					if(activenodedata[jsonMeta.nodeType] === '0'){
						treecontextmenu.items.items[2].hide();
						treecontextmenu.items.items[3].hide();
						treecontextmenu.items.items[4].hide();
					}else{
						treecontextmenu.items.items[2].show();
						treecontextmenu.items.items[3].show();
						treecontextmenu.items.items[4].show();
					}
					if(activenodedata[jsonMeta.nodeType] === '2'){
						treecontextmenu.items.items[0].hide();
						treecontextmenu.items.items[1].hide();
					}else{
						treecontextmenu.items.items[0].show();
						treecontextmenu.items.items[1].show();
					}
					e.preventDefault();
		    		treecontextmenu.showAt(e.getXY());
				}
				
				var lastNode;//通过点击的节点取到index在要添加的位置之前的那个节点
				var lastNodeData;//通过点击的节点取到index在要添加的位置之前的那个节点的数据
				var parentNode;
				var parentNodeData;
				
				/**
				 * 提取上一index节点的数据
				 */
				function getLastIndexChild(checkedNode){
					if(checkedNode.hasChildNodes()){
						getLastIndexChild(checkedNode.lastChild);
					}else{
						lastNode = checkedNode;
						for(var i=0;i<menuModules.length;i++)
						{
							if(menuModules[i][jsonMeta.nodeId] === lastNode.id){
								lastNodeData = menuModules[i];
								break;
							}
						}
					}
				}
				function changeLeaf(type){
					parentNode = activetreenode;
					parentNodeData = activenodedata;
					if(type==1||type==2){
						if(type==1){
							Ext.getCmp('fieldset_add_type').setTitle('新增目录');
							getLastIndexChild(parentNode);
						}else if(type==2){
							Ext.getCmp('fieldset_add_type').setTitle('修改目录');
						}
						items = [{
							labelAlign: 'right',
							layout:'form',
					    	items:[{
					    		xtype:'textfield',
					    		name:'menu_name_ch',
					    		width:170,
					    		id:'menu_name_ch', 
					    		maxLength:50,
					    		maxLengthText : '长度不能大于50位',
					    		fieldLabel:'目录名称',
					    		emptyText: '请输入目录名称...'
					    	},{
					    		xtype:'textfield',
					    		name:'menu_name_en',
					    		width:170,
					    		id:'menu_name_en', 
					    		maxLength:50,
					    		maxLengthText : '长度不能大于50位',
					    		fieldLabel:'目录英文名称',
					    		emptyText: '请输入目录英文名称...'
					    	},{
	                    		xtype:'button',
	                    		text:'确定',
	                    		width:70,
	                    		handler:function(){
	                    			
	                    			if(!Ext.getCmp('menu_name_ch').isValid()){
	                    				Ext.MessageBox.alert('<s:text name="common.info.title"/>','目录名称过长,请重新输入')
                        				return;
	                    			}
	                    			if(!Ext.getCmp('menu_name_en').isValid()){
	                    				Ext.MessageBox.alert('<s:text name="common.info.title"/>','目录英文名称过长,请重新输入')
                        				return;
	                    			}
	                    			if(type == '1'){
	                        			//将信息发送到后台，并返回更新后的树集合
	                        			var submitData = {};
	                        			//lastNodeData activenodedata
	                        			submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
	                        			submitData['menu_parent_id'] = parentNodeData.menu_id;//父节点menu_id
	                        			submitData['menu_level'] = parseInt(parentNodeData.menu_level)+1//节点层级
	                        			if(parentNode.hasChildNodes()){
	                        				var index = menuStore.find('menu_id',parentNode.lastChild.id);
	            							var record = menuStore.getAt(index).data.menu_location;
	                        				submitData['menu_location'] = parseInt(record) + 1;//节点位置
	                        			}else{
	                        				submitData['menu_location'] = '1';
	                        			}
	                        			submitData['menu_type'] = '1';//节点类型
	                        			submitData['menu_name_ch'] = Ext.getCmp('menu_name_ch').getValue();//目录名称
	                        			submitData['menu_name_en'] = Ext.getCmp('menu_name_en').getValue();//目录名称
	                        			if(Ext.getCmp('menu_name_ch').getValue().trim()==''){
	                        				Ext.MessageBox.alert('<s:text name="common.info.title"/>','请输入目录名称')
	                        				return;
	                        			}
	                        			requestAjax('<%=basePath%>confManager/selfMenu_addMenuFolderById', submitData,function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','添加目录成功',function(id){
												menuStore.reload();
												Ext.getCmp('fieldset_add_type').removeAll(true);
	    	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
											});
										});
	                     			}else if(type == '2'){//编辑目录名称
	                     				var submitData = {};
	                        			//lastNodeData activenodedata
	                        			submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
	                        			submitData['menu_id'] = parentNodeData.menu_id;//父节点menu_id
	                        			submitData['menu_name_ch'] = Ext.getCmp('menu_name_ch').getValue();//父节点menu_id
	                        			submitData['menu_name_en'] = Ext.getCmp('menu_name_en').getValue();//目录名称
	                        			if(Ext.getCmp('menu_name_ch').getValue().trim()==''){
	                        				Ext.MessageBox.alert('<s:text name="common.info.title"/>','请输入目录名称')
	                        				return;
	                        			}
	                        			requestAjax('<%=basePath%>confManager/selfMenu_editMenuFolderById', submitData,function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','修改目录成功',function(id){
												menuStore.reload();
												Ext.getCmp('fieldset_add_type').removeAll(true);
	    	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
											});
										});
	                      			}
	                      		}
	                    	}]
						}];
						Ext.getCmp('fieldset_add_type').removeAll(true);
	                  	Ext.getCmp('fieldset_add_type').add(items);
	                  	
	                  	Ext.getCmp('fieldset_add_type').doLayout();
	                  	if(type == '2'){ 
	                  		Ext.getCmp('menu_name_ch').setValue(parentNode.attributes.text);
	                  		Ext.getCmp('menu_name_en').setValue(parentNode.attributes.etext);
	                  	}
					} else if(type==3||type==4){//添加修改业务
					  	Ext.getCmp('fieldset_add_type').removeAll(true);
					  	var resultStore = new Ext.data.JsonStore({
							fields : ['menu_name_ch','menu_name_en','bs_id','menu_type','busi_type','menu_status','menu_dis_status','delete_flag']
	                  	});
				    	var timeData = [
				    	   				new Ext.grid.RowNumberer({header:'序号',width:33}),
				    	   				{xtype: 'gridcolumn', header:'复选框', align: 'center',hidden:true},
				    	   				{ xtype: 'gridcolumn',dataIndex: 'begin_time',header: '服务时间(从)',sortable:false,editor: {xtype: 'timefield',format:'H:i:s',increment:30,minValue: '08:00:00',maxValue: '18:00:00'}},
				    			      	{ xtype: 'gridcolumn',dataIndex: 'end_time',header: '服务时间(到)',sortable:false,editor: {xtype: 'timefield',format:'H:i:s',increment:30,minValue: '08:00:00',maxValue: '18:00:00'}}
				    	   			];
				    		    	var timeSM = new Ext.grid.CheckboxSelectionModel({ singleSelect: false });
				    		    	var timeCM = new Ext.grid.ColumnModel(timeData);
				    		    	
				    				var timeGrid = new Ext.grid.EditorGridPanel({
				    					id: 'timeGrid',
				    				    store: timeStore,
				    				    colModel: timeCM,
				    				    sm:timeSM,
				    				    stripeRows: true,
				    				    height:300,
				    				    frame:true,
				    				    viewConfig: {forceFit: true},
				    				    clicksToEdit:1,
				    				    tbar: [
				    				    	{id:'addBtn', text: '添加', disabled: false, iconCls: "x-image-add", 
				    				    		handler: function(){
				    								var length= timeStore.getCount();
				    	   							var recordType = timeStore.recordType;
				    	   							var num = timeStore.getCount();
				    	   							var record = new recordType({
				    	   								begin_time:'',end_time:''
				    								});
				    	   							timeGrid.stopEditing();
				    	   							timeStore.insert(length, record);
				    	   							timeGrid.getView().refresh();
				    	   							timeSM.selectRow(length);
				    	   							timeGrid.startEditing(timeStore.getCount()-1, 3);
				    				    		} 
				    				    	},'-',
				    				    	{id:'delBtn', text: '删除', disabled: false, iconCls: "x-image-delete", 
				    				    		handler: function(){
				    				    			if(!timeSM.hasSelection()){
				    									Ext.Msg.alert('<s:text name="common.info.title"/>' , '<s:text name="common.info.mustselectrecord"/>');
				    									return;
				    								}
				    								Ext.MessageBox.confirm('<s:text name="common.info.title"/>' , '<s:text name="common.info.suredelete"/>' , function(id){
				    									if(id=='yes'){
				    										var records = timeSM.getSelections();
				    										for(var i=0; i<records.length; i++){
				    											timeStore.remove(records[i]);
				    										}
				    									}
				    								});
				    				    		}
				    				    	}
				    				    ]
				    				});
				    				
				    				timeGrid.on('afteredit',function(e,a,b,c){
				    					var record = e.record;
				    					//e.originalValue;
				    					if(record.get('end_time')!=''&&record.get('begin_time')!=''){//判断都非空
				    						if (record.get('begin_time') >= record.get('end_time')) {
				    							Ext.MessageBox.alert('提示','起始时间必须小于结束时间',function(id){
				    								record.set(e.field,e.originalValue);
				    								return;
				    							});
				    						}
				    						//本条内条件皆通过,与已有数据进行比对
				    						for ( var i = 0; i < timeStore.getCount(); i++) {
				    							//e.row 所在行 一会循环要排除 e.column所在列 
				    							if(i==e.row){
				    								continue;
				    							}
				    							var r = timeStore.getAt(i);
				    							if(record.get('begin_time') < r.get('begin_time') && record.get('end_time') > r.get('begin_time')){
				    								Ext.MessageBox.alert('提示','['+record.get('begin_time')+'-'+record.get('end_time')+']与['+r.get('begin_time')+'-'+r.get('end_time')+']的时间段冲突,请重新修改',function(id){
				    									record.set(e.field,e.originalValue);
				    									return;
				    								});
				    							}else if(record.get('begin_time') >= r.get('begin_time') && record.get('begin_time')<r.get('end_time')){
				    								Ext.MessageBox.alert('提示','['+record.get('begin_time')+'-'+record.get('end_time')+']与['+r.get('begin_time')+'-'+r.get('end_time')+']的时间段冲突,请重新修改',function(id){
				    									record.set(e.field,e.originalValue);
				    									return;
				    								});
				    							}
				    						}
				    					}
				    				});
					  	items = [{
					  		columnWidth:1/2,
						  	labelAlign: 'right',
						  	labelWidth:150,
						  	layout:'form',
						  	items:[{
						  		xtype:'textfield',
							  	name:'menu_name_ch', 
							  	fieldLabel:'菜单别名(中文)',
							  	
							  	emptyText: '请输入...',
							  	allowBlank:true,
							  	anchor:'70%',
							  	disabled: false,
							  	id:'menu_name_ch'
						  	}]
					  	},{
					  		columnWidth:1/2,
						  	labelAlign: 'right',
						  	labelWidth:150,
						  	layout:'form',
						  	items:[{
						  		xtype:'textfield',
							  	name:'menu_name_en', 
							  	fieldLabel:'菜单别名(英文)',
							  	emptyText: '请输入...',
							  	allowBlank:true,
							  	anchor:'70%',
							  	disabled: false,
							  	id:'menu_name_en'
						  	}]
					  	},{
						  	columnWidth:1/2,
						  	labelAlign: 'right',
						  	labelWidth:150,
						  	layout:'form',
						  	items:[{
						  		xtype:'combo',
							  	name:'bs_id', 
							  	fieldLabel:'业务类型',
							  	emptyText: '请选择...',
							  	anchor:'70%',
							  	editable:false,
							  	disabled: false,
							  	id:'businessCom',
							  	store:businessStore,
								displayField:'bs_name_ch',
								valueField:'bs_id',
								mode: 'local',
								triggerAction: 'all'
						  	}]
					  	},{
						  	columnWidth:1/2,
						  	labelAlign: 'right',
						  	labelWidth:150,
						  	layout:'form',
						  	items:[{
						  		xtype:'combo',
							  	name:'menutype', 
							  	fieldLabel:'菜单类型',
							  	emptyText: '请选择...',
							  	anchor:'70%',
							  	editable:false,
							  	disabled: false,
							  	id:'menutype',
							  	store:menutypeStore,
								displayField:'value',
								valueField:'key',
								mode: 'local',
								triggerAction: 'all'
						  	}]
					  	},{
						  	columnWidth:1/2,
						  	labelAlign: 'right',
						  	labelWidth:150,
						  	layout:'form',
						  	items:[{
						  		xtype:'combo',
							  	name:'busitype', 
							  	fieldLabel:'所属业务类别',
							  	emptyText: '请选择...',
							  	anchor:'70%',
							  	editable:false,
							  	disabled: false,
							  	id:'busitype',
							  	store:busitypeStore,
								displayField:'value',
								valueField:'key',
								mode: 'local',
								triggerAction: 'all'
						  	}]
					  	},{
						  	columnWidth:1/2,
						  	labelAlign: 'right',
						  	labelWidth:150,
						  	layout:'form',
						  	items:[{
						  		xtype:'combo',
							  	name:'menustatus', 
							  	fieldLabel:'菜单状态',
							  	emptyText: '请选择...',
							  	anchor:'70%',
							  	editable:false,
							  	disabled: false,
							  	id:'menustatus',
							  	store:menustatusStore,
								displayField:'value',
								valueField:'key',
								value:'0',
								mode: 'local',
								triggerAction: 'all'
						  	}]
					  	},{
						  	columnWidth:1/2,
						  	labelAlign: 'right',
						  	labelWidth:150,
						  	layout:'form',
						  	items:[{
						  		xtype:'combo',
							  	name:'disablestatus', 
							  	fieldLabel:'菜单未生效时状态',
							  	emptyText: '请选择...',
							  	anchor:'70%',
							  	editable:false,
							  	disabled: false,
							  	id:'disablestatus',
							  	store:disablestatusStore,
								displayField:'value',
								valueField:'key',
								value:'0',
								mode: 'local',
								triggerAction: 'all'
						  	}]
					  	},{
						  	columnWidth:1/2,
						  	labelAlign: 'right',
						  	labelWidth:150,
						  	layout:'form',
						  	items:[{
						  		xtype:'combo',
							  	name:'deleteflag', 
							  	fieldLabel:'删除标记',
							  	emptyText: '请选择...',
							  	anchor:'70%',
							  	editable:false,
							  	disabled: false,
							  	id:'deleteflag',
							  	store:deleteFlagStore,
								displayField:'value',
								valueField:'key',
								value:'0',
								mode: 'local',
								triggerAction: 'all'
						  	}]
					  	},{
						  	columnWidth:1,
						  	labelAlign: 'right',
						  	labelWidth:150,
						  	layout:'form',
						  	items:[{
					    		title:'菜单生效时间',
					    		xtype:'fieldset',
					    		collapsible:false,
					    		height:330,
					      		items:[timeGrid]
					    	}]
					  	},{
					  		  columnWidth:1,
					  		  layout:'form',
					  		  buttonAlign : 'center',
					    	  buttons:[{
					    		 text:'确定',
					    		 handler: function(){
					     			if(type=='3'){
					    				//添加业务
										Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确认提交?',function(id){
											if(id === 'yes'){
												var submitData = {};
												submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
			                        			submitData['menu_parent_id'] = parentNodeData.menu_id;//父节点menu_id
			                        			submitData['menu_level'] = parseInt(parentNodeData.menu_level)+1//节点层级
			                        			if(parentNode.hasChildNodes()){
			                        				var index = menuStore.find('menu_id',parentNode.lastChild.id);
			            							var record = menuStore.getAt(index).data.menu_location;
			                        				submitData['menu_location'] = parseInt(record) + 1;//节点位置
			                        			}else{
			                        				submitData['menu_location'] = '1';
			                        			}
			                        			submitData['menu_type'] = '2';//节点类型
			                        			if("" == Ext.getCmp('businessCom').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择业务');
			                        				return;
			                        			}
												submitData['bs_id'] = Ext.getCmp('businessCom').getValue();//业务编号
												if("" == Ext.getCmp('menu_name_ch').getValue()){
													submitData['menu_name_ch']=Ext.getCmp('businessCom').lastSelectionText;
												}else{
													submitData['menu_name_ch'] = Ext.getCmp('menu_name_ch').getValue();//
												}
												submitData['menu_name_en'] = Ext.getCmp('menu_name_en').getValue();//
												submitData['menutype'] = Ext.getCmp('menutype').getValue();//
												submitData['busitype'] = Ext.getCmp('busitype').getValue();
												submitData['menustatus'] = Ext.getCmp('menustatus').getValue();
												submitData['disablestatus'] = Ext.getCmp('disablestatus').getValue();
												submitData['deleteflag'] = Ext.getCmp('deleteflag').getValue();
			                        			
							    				var record = Ext.getCmp('timeGrid').getStore().data.items;
							    				var menu_serv_time = '';
												for ( var i = 0; i < timeStore.getCount(); i++) {
													var r = timeStore.getAt(i);
													menu_serv_time+=';' + r.get('begin_time')+'-'+r.get('end_time');
													if(r.get('begin_time')==''||r.get('end_time')==''){
														Ext.MessageBox.alert('提示','数据未填写完整,请修改');
														return;
													}
												}
												submitData['menu_serv_time']= menu_serv_time.substr(1);
												requestAjax('<%=basePath%>confManager/selfMenu_addMenuLeafById',submitData,function(sRet){
													Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="操作成功"/>',function(id){
														menuStore.reload();
														Ext.getCmp('fieldset_add_type').removeAll(true);
			    	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
													});
												});
											}
										});
					    			}else if(type=='4'){
					    				//修改业务
										Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确认提交?',function(id){
											if(id === 'yes'){
												var submitData = {};
												submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
			                        			submitData['menu_parent_id'] = parentNodeData.menu_id;//父节点menu_id
			                        			submitData['menu_id'] = parentNodeData.menu_id;
			                        			submitData['menu_level'] = parseInt(parentNodeData.menu_level)+1//节点层级
			                        			if(parentNode.hasChildNodes()){
			                        				var index = menuStore.find('menu_id',parentNode.lastChild.id);
			            							var record = menuStore.getAt(index).data.menu_location;
			                        				submitData['menu_location'] = parseInt(record) + 1;//节点位置
			                        			}else{
			                        				submitData['menu_location'] = '1';
			                        			}
			                        			submitData['menu_type'] = '1';//节点类型
			                        			if("" == Ext.getCmp('businessCom').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择业务');
			                        				return;
			                        			}
												submitData['bs_id'] = Ext.getCmp('businessCom').getValue();//业务编号
												if("" == Ext.getCmp('menu_name_ch').getValue()){
													submitData['menu_name_ch']=Ext.getCmp('businessCom').lastSelectionText;
												}else{
													submitData['menu_name_ch'] = Ext.getCmp('menu_name_ch').getValue();//
												}
												submitData['menu_name_en'] = Ext.getCmp('menu_name_en').getValue();//
												submitData['menutype'] = Ext.getCmp('menutype').getValue();//
												submitData['busitype'] = Ext.getCmp('busitype').getValue();
												submitData['menustatus'] = Ext.getCmp('menustatus').getValue();
												submitData['disablestatus'] = Ext.getCmp('disablestatus').getValue();
												submitData['deleteflag'] = Ext.getCmp('deleteflag').getValue();
			                        			
							    				var record = Ext.getCmp('timeGrid').getStore().data.items;
							    				var menu_serv_time = '';
												for ( var i = 0; i < timeStore.getCount(); i++) {
													var r = timeStore.getAt(i);
													menu_serv_time+=';' + r.get('begin_time')+'-'+r.get('end_time');
													if(r.get('begin_time')==''||r.get('end_time')==''){
														Ext.MessageBox.alert('提示','数据未填写完整,请修改');
														return;
													}
												}
												submitData['menu_serv_time']= menu_serv_time.substr(1);
												requestAjax('<%=basePath%>confManager/selfMenu_editMenuLeafById',submitData,function(sRet){
													Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="操作成功"/>',function(id){
														menuStore.reload();
														Ext.getCmp('fieldset_add_type').removeAll(true);
			    	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
													});
												});
											}
										});
										
					    			}
					    		 }
					    	  }]
						}];
						Ext.getCmp('fieldset_add_type').add(items);
	                  	Ext.getCmp('fieldset_add_type').doLayout();
	                  	var dataList = [];
					  	if(type=='3'){//添加业务
						  	resultStore.loadData(dataList);
						  	Ext.getCmp('fieldset_add_type').setTitle('添加业务');
		    			}else if(type=='4'){//修改业务
		    				Ext.getCmp('fieldset_add_type').setTitle('编辑业务');
		                  	//点击的是业务,需要进行查询此业务,之后进行修改
					  		var submitData = {};
					  		submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
                			submitData['menu_id'] = activenodedata.menu_id;//节点menu_id
					  		requestAjax('<%=basePath%>/confManager/selfMenu_queryMenuLeafById',submitData,function(sRet){
								var map = sRet.field1;
								var newArray = new Array();
								Ext.getCmp('businessCom').setValue(map[0]['bs_id']);
								Ext.getCmp('menu_name_en').setValue(map[0]['menu_name_en']);
								Ext.getCmp('menu_name_ch').setValue(map[0]['menu_name_ch']);
								Ext.getCmp('menutype').setValue(map[0]['menuparam'].menutype);
								Ext.getCmp('busitype').setValue(map[0]['menuparam'].busitype);
								Ext.getCmp('menustatus').setValue(map[0]['menuparam'].menustatus);
								Ext.getCmp('disablestatus').setValue(map[0]['menuparam'].disablestatus);
								Ext.getCmp('deleteflag').setValue(map[0]['menuparam'].deleteflag);
								var menu_serv_time= map[0]['menuparam'].menu_serv_time.split(';');
								for ( var i = 0; i < menu_serv_time.length; i++) {
									var serv_time = menu_serv_time[i];
									if(serv_time.length>0){
										var datas = {};
										datas['begin_time'] = serv_time.substr(0,8);
										datas['end_time'] = serv_time.substr(9,8);
										newArray.push(datas);
									}
								}
		                        timeStore.loadData(newArray);
								resultStore.loadData(dataList);
							});
		    			}
					}
				}
				
				/**
				 * 界面主体panel
				 *
				 */
				var panel = new Ext.Panel({
					layout : 'absolute',
					frame:true,
					height:clientHeight+40,
					applyTo :'panel',
					items:[{
						layout:'column', //整体数据
						frame:true,
						height:clientHeight,
						width:clientWidth,
			    		items:[{
			    			columnWidth:1/4, //左侧整体
			    			layout:'column', 
			    			bodyStyle:'padding:0 8 0 8', 
			    			labelAlign:'left',
			    		  	items:[{
			    		  		columnWidth:1, //左面上下整体 套层form
			    		  		layout:'form', 
			    		  		bodyStyle:'padding:0 8 0 8', 
			    		  		labelAlign:'left',
			    	   			items:[{
			    	   				width: (clientWidth*1/4)-30,//参数那一块
			    	   				height:60,
			    	   				xtype:'fieldset',
			    	   				layout:'column',
			    	   				title:'参数编号',
			    	   				items:[{
			    	   					columnWidth:0.75,
			    	   					labelAlign: 'right',
			    	   					labelWidth:45,
			    	   					layout:'form',
			    	   					items:[{
			    	   						xtype:'combo',
			    	   						name:'systemParamCombo', 
			    	   						hideLabel:true,
			    	   						fieldLabel:'参数名',
				    	   					emptyText: '请选择参数...',
				    	   					anchor:'99%',
				    	   					store:systemParamStore,
				    	   					displayField:'parameter_name',
				    	   					valueField:'parameter_id',
				    	   					mode: 'local',
				    	   					triggerAction: 'all',
				    	   					editable:false,
				    	   					id:'systemParamCombo',
				    	   					listeners:{
												'select': function(){
													var combo = Ext.getCmp('systemParamCombo');
													var submit = {};
								                	submit['parameter_id'] = combo.getValue();
								                	Ext.getCmp('fieldset_add_type').removeAll(true);
		    	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
		    	   						    		queryFlag = false;
								                	menuStore.load({params:submit});
												}
											}
			    	   					}]
			    	   				},{//编号的配置
			    	   					columnWidth:0.2,
			    	   					xtype:'button',
			    	   					text:'配置',
			    	   					width:50,
			    	   					handler : function(b,e){
			    	   						systemParamWindow.show();
						    			}
			    	   				}]
			    	   			}]
			    		  	},{
			    		  		columnWidth:1, 
			    		  		layout:'form', 
			    		  		bodyStyle:'padding:0 8 0 8', 
			    		  		labelAlign:'left',
			    	   			items:[{
			    	   				width: (clientWidth*1/4)-30,
			    	   				height:clientHeight - 90,
			    	   				xtype:'fieldset',
			    	   				layout:'column',
			    	   				title:'自助设备菜单管理',
			    	   				items:[{
			    	   					columnWidth:1,
			    	   					layout:'form',
			    	   					items:[{
			    	   						xtype:'treepanel',
			    	   						unstyled : true,
			    	   						root: SelfMenuTree,
			    	   						id:'viewTree',
			    	   						autoScroll:true,
			    	   						height:clientHeight - 160,
			    	   						listeners : {
			    	   							contextmenu:onmenu,
			    	   							click:function(node){
			    	   								//菜单树左键点击时候的判断,目录时候查询目录并打开修改目录页面,节点时候查询节点并打开修改节点页面
			    	   						  		activetreenode = node;
				    	   						  	for(var i=0;i<menuModules.length;i++)
				    	   							{
				    	   								if(menuModules[i][jsonMeta.nodeId] === node.id){
				    	   									activenodedata = menuModules[i];
				    	   									break;
				    	   								}
				    	   							}
			    	   						  		if(node.attributes.type == '0'){
			    	   						    		Ext.getCmp('fieldset_add_type').removeAll(true);
			    	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
			    	   						  		}else if(node.attributes.type == '1'){
			    	   						  			//点击的是目录,直接进入目录修改
			    	   						    		changeLeaf('2');
			    	   						  		}else if(node.attributes.type == '2'){
			    	   						  			changeLeaf('4');
			    	   						  		}
			    	   							}
			    	   						}
			    	   					}]
			    	   				}]
			    	   			}]
			    		  	}]
			    		},{
			    			columnWidth:3/4, 
			    			layout:'form', 
			    			bodyStyle:'padding:0 8 0 8', 
			    			labelAlign:'left',
			    		    items:[{
			    		    	width: (clientWidth*3/4)-47,
			    		    	height:clientHeight - 21,
			    		    	xtype:'fieldset',
			    		    	layout:'column',
			    		      	title:titles,
			    		      	hideMode :'visibility',
			    		      	hidden: false,
			    		      	id:'fieldset_add_type',
			    		      	items:items
			    	   		}]
			    		}]
			    	}]
				});
//-------------------------------------------------------------------------布局
				function buildLayout(){
					var viewport = new Ext.Viewport({
						layout: 'fit',
						items : [panel]
					});
				}
				buildLayout();
				
				//启动加载
				var brno = '<%=brno%>';
				systemParamStore.load({params:{'branch':brno,'parameter_flag':'01'}});
			}
		</script>
	</head>

	<body>
		<div id="panel"></div>
	</body>
</html>
