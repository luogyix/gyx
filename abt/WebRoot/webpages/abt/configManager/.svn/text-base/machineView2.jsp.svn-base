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
		<title>排队机界面</title>
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
			var titles = "排队机界面配置";
			var items = [];
			var treeData = [{"node_id":"root",	"parent_id":"-1",	"node_name":"排队机",	"node_action":"",	"node_type":"0",	"node_location":"1",	"node_level":"1"}];
//----------------------------------------------------------------
			var queryFlag = false;
			//开始构建排队机 "树"
			var PaiDuiJiModules = treeData; //排队机数据
			//var PaiDuiJiModules = treeData; //排队机数据
			/*定义json串对应意义，按照需要定义
			 * node_id 树节点ID
			 * parent_id 父节点ID
			 * node_name 树节点名称
			 * node_action 节点路径 url用来弹出新窗口用打的
			 * node_type 菜单类型：0-系统；1-文件夹；2-页面；3-动作
			 * node_location 排序标记
			 * node_level 层级
			 */
			var jsonMeta = {
				nodeId:'node_id',
				parentNodeId:'parent_id',
				nodeName:'node_name',
				nodeNameEn:'dir_name_en',
				nodeHref:'node_action',
				nodeTarget:'',
				leafField:'',
				nodeType:'node_type',
				nodeIndex:'node_index',
				nodeOrder:'node_location',
				isReserve:'is_reserve',
				nodeLevel:'node_level'
			};
			//定义树生成器
			var treeGenerator = new SelfTreeGenerator(
				PaiDuiJiModules,
				jsonMeta,
				'<%=basePath%>',
				['x-image-house','x-image-package_tiny','',''], 
				['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']
			);
			//定义菜单树
			var PaiDuiJiTree = treeGenerator.generate(false,false,false,false);
			
			var activetreenode = null;
			var activenodedata = null;

//-----------------------------------------------------------------------------------------------------
    		var custTypeStore = new Ext.data.JsonStore({ 
    			url    : '<%=basePath%>confManager/custType_queryCustTypeSmall.action',
    			autoLoad:true,
    			root   : 'field1',
    			fields : ['custtype_i','custtype_name']
    		});
    		var queuetypeStore = new Ext.data.JsonStore({
    			url    : '<%=basePath%>confManager/queuetype_queryQueueTypeSmall?query_rules=4',
    			autoLoad:true,
    			root   : 'field1',
    			fields:['queuetype_id','queuetype_name']//,
    			//listeners : {
    			//	load : function(store,records){
    			//		var temp = store.data.items;
    			//		for(var i=0;i<temp.length;i++){
    			//			temp[i].data['queuetype_name'] = temp[i].data['queuetype_id'] + '-' + temp[i].data['queuetype_name'];
    			//		}
    			//		this.removeAll();
    			//		store.add(temp);
    			//	}
    			//}
    		});
    		
			var businessStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>confManager/business_queryBusinessSmall?query_rules=4',
				autoLoad:true,
				root   : 'field1',
				fields:['bs_id','bs_name_ch','note','card_flag'],
    			listeners : {
    				load : function(store,records){
    					var temp = store.data.items;
    					for(var i=0;i<temp.length;i++){
    						var note = temp[i].data['note'];
    						var bsNotes = note.split('@@');
    						temp[i].data['note'] = bsNotes[0];
    					}
    					this.removeAll();
    					store.add(temp);
    				}
    			}
			});
			
			var qfcStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>/confManager/machineView_queryViewById',
				root   : 'field1',
				fields:['qm_num','branch','node_id','node_level','node_location','child_node_order','node_name','parent_id','is_reserve','parameter_id',
				        'node_type','dir_name','dir_name_en','bs_id','is_show_msg','prompt_msg','node_index','need_reserv','card_visiable','need_card'],
    			listeners : {
    				load : function(store,records){
    					treeData = [];
    					for(var i = 0;i<store.data.length;i++){
    						
    						var record = store.getAt(i).data;
    						var tree = {};
    						if(record['node_type']=='2'){
    							var index = businessStore.find('bs_id',record['bs_id']);
    							var businessRecord = businessStore.getAt(index).data;
    							tree['node_name'] = businessRecord['bs_name_ch'];
    							if(record['is_reserve']){
    								tree['node_name'] = record['node_name']
    							}
    						}else{
    							tree['node_name'] = record['node_name'];
    						}
    						tree['dir_name_en'] = record['dir_name_en'];//英文目录名
    						tree['node_id'] = record['node_id'];
    						tree['node_type'] = record['node_type'];
    						tree['node_level'] = record['node_level'];
    						tree['node_location'] = record['node_location'];
    						tree['parent_id'] = record['parent_id'];
    						tree['node_index'] = record['node_index'];
    						treeData.push(tree);
    					}
    					
    					PaiDuiJiModules = treeData; 
						treeGenerator = new SelfTreeGenerator(
							PaiDuiJiModules,jsonMeta,
							'<%=basePath%>',
							['x-image-house','x-image-package_tiny','',''], 
							['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']
						);
						PaiDuiJiTree = treeGenerator.generate(false,false,true,false);
						Ext.getCmp('viewTree').setRootNode(PaiDuiJiTree);
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
            function getQueuetype(value, cellmeta, record, rowIndex, columnIndex, store){
            	if(value !== undefined)
             		value=value.toString();
             		var map=queuetypeStore.getAt(queuetypeStore.find('queuetype_id',value));
             		if(map === undefined)
             			return value;
             		else
             			return map.get('queuetype_name');
            }
            function getCustTypetype(value, cellmeta, record, rowIndex, columnIndex, store){
            	if(value !== undefined)
             		value=value.toString();
             		var map=custTypeStore.getAt(custTypeStore.find('custtype_i',value));
             		if(map === undefined)
             			return value;
             		else
             			return map.get('custtype_name');
            }
			
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
		                height: 240,
		                //autoHeight : true, 
						name:'systemParamGrid',
						id:'systemParamGrid',
						store: systemParamStore,
						autoScroll :true,
						viewConfig: {forceFit: true},
						columns: [
						  	new Ext.grid.RowNumberer({width: 30}),
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
				    				if(record.get('branch')!='<%=brno%>'){
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
				    				if(record.get('branch')!='<%=brno%>'){
				    					Ext.Msg.alert('提示', '无法删除非本机构的参数!');
				    					return;
				    				}
				    				Ext.Msg.confirm('提示','确定要删除选中的参数吗?',function(btn, text){
										if (btn == 'yes') {
											var submitdata = {};
											submitdata['parameter_id'] = record.get('parameter_id');
											requestAjax('<%=basePath%>confManager/systemParameter_delSystemParameterById',submitdata,function(sRet){
												Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
													systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'01'}});
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
<%--									Ext.Ajax.request({--%>
<%--										url: '<%=basePath%>confManager/systemParameter_addSystemParameter',  --%>
<%--										params: {strJson:Ext.encode(array)},--%>
<%--										success:function(response,opt){--%>
<%--											var responseText = response.responseText;--%>
<%--											if(responseText.indexOf('false')!=-1){--%>
<%--												Ext.MessageBox.alert('提示', '此机构已存在默认的参数或参数名重名');--%>
<%--												return;--%>
<%--											}--%>
<%--											systemParam_add_window.hide();--%>
<%--											Ext.MessageBox.alert('提示', '添加参数成功');--%>
<%--											systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'01'}});--%>
<%--										}--%>
<%--									});--%>
									requestAjax('<%=basePath%>confManager/systemParameter_addSystemParameter',Ext.encode(array),function(sRet){
										Ext.MessageBox.alert('提示','添加参数成功',function(id){
											systemParam_add_window.hide();
											systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'01'}});
										});
									});
								}else if (mode == 'edit'){
									//通知后台的方法.修改
<%--									Ext.Ajax.request({--%>
<%--										url: '<%=basePath%>confManager/systemParameter_editSystemParameter',  --%>
<%--										params: {strJson:Ext.encode(array)},--%>
<%--										success:function(response,opt){--%>
<%--											var responseText = response.responseText;--%>
<%--											if(responseText.indexOf('false')!=-1){--%>
<%--												Ext.MessageBox.alert('提示', '此机构已存在默认的参数或参数名重名');--%>
<%--												return;--%>
<%--											}--%>
<%--											systemParam_add_window.hide();--%>
<%--											Ext.MessageBox.alert('提示', '修改参数成功');--%>
<%--											systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'01'}});--%>
<%--										}--%>
<%--									});--%>
									requestAjax('<%=basePath%>confManager/systemParameter_editSystemParameter',Ext.encode(array),function(sRet){
										Ext.MessageBox.alert('提示','修改参数成功',function(id){
											systemParam_add_window.hide();
											systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'01'}});
										});
									});
								}else if(mode == 'copy'){
									//复制系统
									requestAjax('<%=basePath%>confManager/systemParameter_copySystemParameter',Ext.encode(array),function(sRet){
										Ext.MessageBox.alert('提示','复制成功',function(id){
											systemParam_add_window.hide();
											systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'01'}});
										});
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
					//添加判断是否所属机构
					var paramId = Ext.getCmp('systemParamCombo').getValue();
					var selectRecord = systemParamStore.getAt(systemParamStore.find('parameter_id',paramId));
					if(selectRecord.data['branch']!=<%="'" + brno + "'"%>){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的界面配置!');
						return;
					}
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
							//判断当前节点是不是文件夹，如果是，正常进入业务配置界面，如果是业务配置节点，则弹出提示窗告诉业务节点不可新增业务
							//changeLeaf('1');
							//P, G, Z, V, Z, P
							if(activetreenode.attributes.leaf){
								Ext.MessageBox.alert('<s:text name="common.info.title"/>','业务类节点不可新增节点');
								return;
							}
							changeLeaf('3');
						break;
						case "nodeaddtmaata":
							
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
		                  			//lastNodeData activenodedata
		                  			var child_node_order = "";
		                  			var parent = activetreenode.parentNode;
									if(parent.hasChildNodes()){
										var childNodes = parent.childNodes;
										for(var a=0;a<childNodes.length;a++){
											var child = childNodes[a];
											child_node_order = child_node_order + "," + child.attributes.id;
										}
										child_node_order = child_node_order.substr(1);
									}
		                  			
		                  			submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
		                			submitData['node_id'] = activenodedata.node_id;//父节点node_Id
		                			submitData['node_index'] = activenodedata.node_index;//节点索引
		                			submitData['parent_node_id'] = parent.attributes.id=='root'?'':parent.attributes.id;//父节点node_Id
		                			submitData['child_node_order'] = child_node_order;//child_node_order
		                			
									requestAjax('<%=basePath%>confManager/machineView_delLeafById', submitData,function(sRet){
										Ext.MessageBox.alert('<s:text name="common.info.title"/>','删除节点成功',function(id){
											qfcStore.reload();
											Ext.getCmp('fieldset_add_type').removeAll(true);
	   	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
	   	   						    		window.parent.queryMessage();
										});
									});
								}
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
						{id:'nodeaddtmaata', text:'添加预约激活'},
						{id:'nodedelete', text:'删除节点'}
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
					for(var i=0;i<PaiDuiJiModules.length;i++)
					{
						if(PaiDuiJiModules[i][jsonMeta.nodeId] === node.id){
							activenodedata = PaiDuiJiModules[i];
							break;
						}
					}
					
					if(activenodedata[jsonMeta.nodeType] === '0'){
						treecontextmenu.items.items[3].hide();
					}else{
						treecontextmenu.items.items[3].show();
					}
					if(activenodedata[jsonMeta.nodeType] === '2'){
						treecontextmenu.items.items[0].hide();
						treecontextmenu.items.items[1].hide();
						treecontextmenu.items.items[2].hide();
					}else{
						treecontextmenu.items.items[0].show();
						treecontextmenu.items.items[1].show();
						treecontextmenu.items.items[2].show();
					}
					treecontextmenu.items.items[2].hide();//预约激活没写,二期再考虑,由此屏蔽
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
						for(var i=0;i<PaiDuiJiModules.length;i++)
						{
							if(PaiDuiJiModules[i][jsonMeta.nodeId] === lastNode.id){
								lastNodeData = PaiDuiJiModules[i];
								break;
							}
						}
					}
				}
				
				/*
				 * 对节点进行操作
				 * type='1'代表是新增目录，type='2'代表编辑目录  3代表添加节点,4代表编辑节点
				 */
				function changeLeaf(type){
					if(!queryFlag){
						Ext.Msg.alert('提示','尚未查询到排队机界面数据,请重新选择排队机查询');
						return;
					}
					parentNode = activetreenode;
					parentNodeData = activenodedata;
					//拼var child_node_order字符串
					var child_node_order = "";
					if(parentNode.hasChildNodes()){
						var childNodes = parentNode.childNodes;
						for(var a=0;a<childNodes.length;a++){
							var child = childNodes[a];
							child_node_order = child_node_order + "," + child.attributes.id;
						}
						child_node_order = child_node_order.substr(1);
					}
					//拼var child_node_order字符串完成
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
					    		name:'dir_name',
					    		width:170,
					    		id:'dir_name', 
					    		maxLength:50,
					    		maxLengthText : '长度不能大于50位',
					    		fieldLabel:'目录名称',
					    		emptyText: '请输入目录名称...'
					    	},{
					    		xtype:'textfield',
					    		name:'dir_name_en',
					    		width:170,
					    		id:'dir_name_en', 
					    		maxLength:50,
					    		maxLengthText : '长度不能大于50位',
					    		fieldLabel:'目录英文名称',
					    		emptyText: '请输入目录英文名称...'
					    	},{
	                    		xtype:'button',
	                    		text:'确定',
	                    		width:70,
	                    		handler:function(){
	                    			//添加判断是否所属机构
	            					var paramId = Ext.getCmp('systemParamCombo').getValue();
	            					var selectRecord = systemParamStore.getAt(systemParamStore.find('parameter_id',paramId));
	            					if(selectRecord.data['branch']!=<%="'" + brno + "'"%>){
	            						Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的界面配置!');
	            						return;
	            					}
	                    			if(!Ext.getCmp('dir_name').isValid()){
	                    				Ext.MessageBox.alert('<s:text name="common.info.title"/>','目录名称过长,请重新输入')
                        				return;
	                    			}
	                    			if(!Ext.getCmp('dir_name_en').isValid()){
	                    				Ext.MessageBox.alert('<s:text name="common.info.title"/>','目录英文名称过长,请重新输入')
                        				return;
	                    			}
	                    			if(type == '1'){
	                        			//将信息发送到后台，并返回更新后的树集合
	                        			var submitData = {};
	                        			//lastNodeData activenodedata
	                        			submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
	                        			submitData['parent_node_id'] = parentNodeData.node_id;//父节点node_Id
	                        			submitData['node_level'] = parseInt(parentNodeData.node_level)+1//节点层级
	                        			if(parentNode.hasChildNodes()){
	                        				var index = qfcStore.find('node_id',parentNode.lastChild.id);
	            							var record = qfcStore.getAt(index).data.node_location;
	                        				submitData['node_location'] = parseInt(record) + 1;//节点位置
	                        			}else{
	                        				submitData['node_location'] = '1';
	                        			}
	                        			submitData['node_type'] = '0';//节点类型
	                        			submitData['dir_name'] = Ext.getCmp('dir_name').getValue();//目录名称
	                        			submitData['dir_name_en'] = Ext.getCmp('dir_name_en').getValue();//目录名称
	                        			submitData['node_index'] = parseInt(lastNodeData.node_index) + 1;//节点索引
	                        			if(Ext.getCmp('dir_name').getValue().trim()==''){
	                        				Ext.MessageBox.alert('<s:text name="common.info.title"/>','请输入目录名称')
	                        				return;
	                        			}
	                        			requestAjax('<%=basePath%>confManager/machineView_addFolderById', submitData,function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','添加目录成功',function(id){
												qfcStore.reload();
												Ext.getCmp('fieldset_add_type').removeAll(true);
	    	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
											});
										});
	                     			}else if(type == '2'){//编辑目录名称
	                     				var submitData = {};
	                        			//lastNodeData activenodedata
	                        			submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
	                        			submitData['node_id'] = parentNodeData.node_id;//父节点node_Id
	                        			submitData['dir_name'] = Ext.getCmp('dir_name').getValue();//父节点node_Id
	                        			submitData['dir_name_en'] = Ext.getCmp('dir_name_en').getValue();//目录名称
	                        			if(Ext.getCmp('dir_name').getValue().trim()==''){
	                        				Ext.MessageBox.alert('<s:text name="common.info.title"/>','请输入目录名称')
	                        				return;
	                        			}
	                        			requestAjax('<%=basePath%>confManager/machineView_editFolderById', submitData,function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','修改目录成功',function(id){
												qfcStore.reload();
												Ext.getCmp('fieldset_add_type').removeAll(true);
	    	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
											});
										});
	                      			}
	                      		}
	                    	}]
						}];
						
						if(Ext.getCmp('custtypeQueuetypeBind')){
	                    	Ext.getCmp('custtypeQueuetypeBind').removeAll(true);
	                	}
	                  	Ext.getCmp('fieldset_add_type').removeAll(true);
	                  	Ext.getCmp('fieldset_add_type').add(items);
	                  	
	                  	Ext.getCmp('fieldset_add_type').doLayout();
	                  	if(type == '2'){ 
	                  		Ext.getCmp('dir_name').setValue(parentNode.attributes.text);
	                  		Ext.getCmp('dir_name_en').setValue(parentNode.attributes.etext);
	                  	}
					} else if(type==3||type==4){//添加修改业务
						if(Ext.getCmp('custtypeQueuetypeBind')){//删除绑定的数据,然后再次添加
							Ext.getCmp('custtypeQueuetypeBind').removeAll(true);
						}
					  	Ext.getCmp('fieldset_add_type').removeAll(true);

						/*
						 * bound 绑定字段 (绑定:true，未绑定:false)
						 * customtype	客户类型
						 * queue 队列
						 * waitarea 等待区域
						 * nocardcustomtype 无卡时客户类型 (list中只能有一个true，其他都为false)
						 */
					  	var resultStore = new Ext.data.JsonStore({
							fields : ['bound','custtype_i','queuetype_id','wait_area','prompt_msg','nocard_default','isreservqidflag','reserv_queuetype_id']
	                  	});
	                  	var mydata = [
	                    	new Ext.grid.RowNumberer({header:'选择',width:33}),
							{xtype: 'checkcolumn',dataIndex: 'bound',header: '绑定',sortable: true,width:50,align: 'center'},
							{xtype: 'gridcolumn',dataIndex: 'custtype_i',header: '客户类型',sortable: true,width:120,editable:false,align: 'left',renderer: getCustTypetype,editor: { xtype: 'combo',store: custTypeStore,displayField:'custtype_name',valueField:'custtype_i',mode: 'local',triggerAction: 'all',resizable:'true',typeAhead:'true'}},
							{xtype: 'gridcolumn',dataIndex: 'queuetype_id',header: '队列',sortable: true,align: 'left',width:120,renderer: getQueuetype,editor: { xtype: 'combo',store: queuetypeStore,displayField:'queuetype_name',valueField:'queuetype_id',mode: 'local',triggerAction: 'all',resizable:'true',typeAhead:'true',emptyText : "请选择队列",allowBlank : false,editable:false}},
							{xtype: 'checkcolumn',dataIndex: 'isreservqidflag',header: '预约标识',sortable: true,width:80,align: 'center'},
							{xtype: 'gridcolumn',dataIndex: 'reserv_queuetype_id',header: '预约队列',sortable: true,align: 'left',width:120,renderer: getQueuetype,editor: { xtype: 'combo',store: queuetypeStore,displayField:'queuetype_name',valueField:'queuetype_id',mode: 'local',triggerAction: 'all',resizable:'true',typeAhead:'true',emptyText : "请选择预约队列",allowBlank : false,editable:false}},
							{xtype: 'gridcolumn',dataIndex: 'wait_area',header: '自定义客户等待区域',sortable: true,width:150,editor: { xtype: 'textfield' ,maxLength:80}},
							{xtype: 'gridcolumn',dataIndex: 'prompt_msg',header: '小票提示信息',sortable: true,width:150,editor: { xtype: 'textfield' ,maxLength:80}},
					  		{xtype: 'checkcolumn',dataIndex: 'nocard_default',header: '无卡时选择客户类型',singleSelect: true,sortable: true,width:150,align: 'left'}
						];
	                  	var sm = new Ext.grid.CheckboxSelectionModel({  
	                  	    dataIndex: "nocard_default",
	                  	  	header: '无卡时选择客户类型',
	                  	    singleSelect: true  
	                  	});  
					  	var regionColModel=new Ext.grid.ColumnModel(mydata);
					  								
					  	items = [{
						  	columnWidth:1/3,
						  	labelAlign: 'right',
						  	labelWidth:80,
						  	layout:'form',
						  	items:[{
							  	xtype:'combo',
							  	name:'businessCom', 
							  	fieldLabel:'选择业务',
							  	emptyText: '请选择业务...',
							  	anchor:'95%',
							  	editable:false,
							  	listWidth:139,
							  	disabled: false,
							  	id:'businessCom',
							  	store:businessStore,
								displayField:'bs_name_ch',
								valueField:'bs_id',
								mode: 'local',
								triggerAction: 'all',
								listeners:{
									'select': function(store,record){
										var need_card = record.data['card_flag'];
										var note = record.data['note'].split('@@')[0];
										if(need_card=='2'){
											Ext.getCmp('need_card').setValue(true);
										}else{
											Ext.getCmp('need_card').setValue(false);
										}
										if(note!=''||note!=undefined||note!=null){
											Ext.getCmp('is_show_msg').setValue(true);
											Ext.getCmp('prompt_msg').setValue(note);
										}else{
											Ext.getCmp('is_show_msg').setValue(false);
											Ext.getCmp('prompt_msg').setValue('');
										}
									}
								}
							}]
						},{
							columnWidth:2/3,
							labelWidth: 20,
							layout:'form',
					    	items:[{
					    		xtype: 'fieldset',
					    		hideLabels: true,
					    		height:100,
					      		items:[
					      		{
					      			boxLabel: '勾选此项，代表预约业务取号时需要输入预约信息',
					          		name: 'need_reserv', 
					          		id: 'need_reserv', 
					          		inputValue: '1',
					          		xtype: 'checkbox',
					          		disabled: false
					      		},{
					      			boxLabel: '仅刷卡可见,仅控制默认界面不显示该业务，勾选此项则下面不需要配置无卡',
					          		name: 'card_visiable', 
					          		id: 'card_visiable', 
					          		inputValue: '1',
					          		xtype: 'checkbox',
					          		disabled: false
					          	},{
					          		boxLabel: '必须刷卡，勾选此项则下面不需要配置无卡',
					          		name: 'need_card', 
					          		id: 'need_card', 
					          		inputValue: '2',
					          		xtype: 'checkbox',
					          		disabled: false
					          	}]
					    	}]
					  	},{
					  		columnWidth:1,
					  		labelAlign: 'right',
					  		layout:'form',
					    	items:[{
					    		xtype:'checkbox',
					    		name:'is_show_msg',
					      		id:'is_show_msg',
					    		height:25,
					      		boxLabel:'选中后提示信息',
					      		inputValue:'bepsbat',
					      		checked: false,
					      		disabled: false,
					      		listeners:{
					        		'check':function(){
							        	if(this.checked){
							            	Ext.getCmp('prompt_msg').enable();
							          	}else{
						           	 		Ext.getCmp('prompt_msg').disable();
						           	 		//Ext.getCmp('prompt_msg').setValue('');
							          	}
					        		}
					      		}
					    	}]
					  	},{
					  		columnWidth:1,
					  		labelAlign: 'right',
					  		layout:'form',
					    	items:[{
					      		xtype:'textfield',
					      		name:'prompt_msg',
					      		id:'prompt_msg',
					      		fieldLabel:'提示信息',
					      		emptyText: '提示信息...',
					      		maxLength:60,
					    		maxLengthText : '长度不能大于60位',
					      		anchor:'100%',
					      		disabled: true
					    	}]
					  	},{
					  		columnWidth:1,
					  		layout:'form',
					    	items:[{
					    		xtype: 'fieldset',
					    		title:'客户类型和队列关系绑定',
					    		height:clientHeight-280,
					    		hideLabels: true,
					    		id:'custtypeQueuetypeBind'
					    	}]
					  	},{
					  		columnWidth:1,
					  		layout:'form',
					  		buttonAlign : 'center',
					    	buttons:[{
					    		text:'确定',
					    		handler: function(){
					    			//添加判断是否所属机构
									var paramId = Ext.getCmp('systemParamCombo').getValue();
									var selectRecord = systemParamStore.getAt(systemParamStore.find('parameter_id',paramId));
									if(selectRecord.data['branch']!=<%="'" + brno + "'"%>){
										Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的界面配置!');
										return;
									}
					    			if(type=='3'){
					    				//添加业务
										Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确认提交?',function(id){
											if(id === 'yes'){
												var submitData = {};
												submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
			                        			submitData['parent_node_id'] = parentNodeData.node_id;//父节点node_Id
			                        			submitData['node_level'] = parseInt(parentNodeData.node_level)+1//节点层级
			                        			if(parentNode.hasChildNodes()){
			                        				var index = qfcStore.find('node_id',parentNode.lastChild.id);
			            							var record = qfcStore.getAt(index).data.node_location;
			                        				submitData['node_location'] = parseInt(record) + 1;//节点位置
			                        			}else{
			                        				submitData['node_location'] = '1';
			                        			}
			                        			submitData['node_type'] = '1';//节点类型
			                        			if("" == Ext.getCmp('businessCom').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择业务');
			                        				return;
			                        			}
												submitData['bs_id'] = Ext.getCmp('businessCom').getValue();//业务编号
												submitData['is_show_msg'] = Ext.getCmp('is_show_msg').getValue();//业务是否显示提示信息
												if(Ext.getCmp('is_show_msg').getValue()){
													submitData['prompt_msg'] = Ext.getCmp('prompt_msg').getValue();//业务提示信息
												}else{
													submitData['prompt_msg'] = "";//业务提示信息
												}
												if(submitData['prompt_msg'].length>60){
													Ext.MessageBox.alert('提示信息','提示信息文字超长,请修改后提交');
													return;
												}
												submitData['need_reserv'] = Ext.getCmp('need_reserv').getValue();//预约
												submitData['card_visiable'] = Ext.getCmp('card_visiable').getValue();//仅刷卡可见
												submitData['need_card'] = Ext.getCmp('need_card').getValue();//必须刷卡
			                        			submitData['node_index'] = parseInt(lastNodeData.node_index) + 1;//节点索引
			                        			
							    				var record = Ext.getCmp('cqGrid').getStore().data.items;
												var listData = [];
												var num = 0;
												var flag = false;
												for(var i = 0;i<record.length;i++){
													if(record[i].data['bound']){
														num +=1;
														var data = {};
														data['custtype_i'] = record[i].data['custtype_i'];
														data['queuetype_id'] = record[i].data['queuetype_id'];
														data['wait_area'] = record[i].data['wait_area'];
														data['prompt_msg'] = record[i].data['prompt_msg'];
														data['nocard_default'] = record[i].data['nocard_default'];
														data['isreservqidflag'] = record[i].data['isreservqidflag'];
														if(record[i].data['isreservqidflag']){
															//这里判断勾选了预约就要选择预约的队列
															if(record[i].data['reserv_queuetype_id']==undefined||record[i].data['reserv_queuetype_id']==null||record[i].data['reserv_queuetype_id']==''){
																Ext.MessageBox.alert('提示信息','请为已选定预约的客户类型选择队列!');
																return;
															}
														}
														data['reserv_queuetype_id'] = record[i].data['reserv_queuetype_id'];
														listData.push(data);

														if(record[i].data['nocard_default']){
															flag = true;
														}
													}
												}
												if(!(submitData['need_card'])&&!(submitData['card_visiable'])){
													if(!flag){
														Ext.MessageBox.alert('提示信息','请选择无卡时的客户类型');
														return;
													}
												}else{
													if(flag){
														Ext.MessageBox.alert('提示信息','配置刷卡后不需要配置无卡时的客户类型,请修改');
														return;
													}
												}
												if(num==0){
													Ext.MessageBox.alert('提示信息','请绑定客户类型与队列关系!');
													return;
												}
												submitData['listData'] = listData;
												requestAjax('<%=basePath%>confManager/machineView_addLeafById',submitData,function(sRet){
													Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="操作成功"/>',function(id){
														qfcStore.reload();
														window.parent.queryMessage();
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
			                        			if("" == Ext.getCmp('businessCom').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择业务');
			                        				return;
			                        			}
												submitData['bs_id'] = Ext.getCmp('businessCom').getValue();//业务编号
												submitData['is_show_msg'] = Ext.getCmp('is_show_msg').getValue();//业务是否显示提示信息
												if(Ext.getCmp('is_show_msg').getValue()){
													submitData['prompt_msg'] = Ext.getCmp('prompt_msg').getValue();//业务提示信息
												}else{
													submitData['prompt_msg'] = "";//业务提示信息
												}
												if(submitData['prompt_msg'].length>60){
													Ext.MessageBox.alert('提示信息','提示信息文字超长,请修改后提交');
													return;
												}
												submitData['need_reserv'] = Ext.getCmp('need_reserv').getValue();//预约
												submitData['card_visiable'] = Ext.getCmp('card_visiable').getValue();//仅刷卡可见
												submitData['need_card'] = Ext.getCmp('need_card').getValue();//必须刷卡
												submitData['node_id'] = parentNodeData.node_id;//父节点node_Id
							    				var record = Ext.getCmp('cqGrid').getStore().data.items;
												var listData = [];
												var num = 0;
												var flag = false;
												for(var i = 0;i<record.length;i++){
													if(record[i].data['bound']){
														num +=1;
														var data = {};
														data['custtype_i'] = record[i].data['custtype_i'];
														data['queuetype_id'] = record[i].data['queuetype_id'];
														data['wait_area'] = record[i].data['wait_area'];
														data['prompt_msg'] = record[i].data['prompt_msg'];
														data['nocard_default'] = record[i].data['nocard_default'];
														if(record[i].data['nocard_default']){
															flag = true;
														}
														data['isreservqidflag'] = record[i].data['isreservqidflag'];
														if(record[i].data['isreservqidflag']){
															//这里判断勾选了预约就要选择预约的队列
															if(record[i].data['reserv_queuetype_id']==undefined||record[i].data['reserv_queuetype_id']==null||record[i].data['reserv_queuetype_id']==''){
																Ext.MessageBox.alert('提示信息','请为已选定预约的客户类型选择队列!');
																return;
															}
														}
														data['reserv_queuetype_id'] = record[i].data['reserv_queuetype_id'];
														listData.push(data);
													}
												}
												if(!(submitData['need_card'])&&!(submitData['card_visiable'])){
													if(!flag){
														Ext.MessageBox.alert('提示信息','请选择无卡时的客户类型');
														return;
													}
												}else{
													if(flag){
														Ext.MessageBox.alert('提示信息','配置刷卡后不需要配置无卡时的客户类型,请修改');
														return;
													}
												}
												submitData['listData'] = listData;
												if(num==0){
													Ext.MessageBox.alert('提示信息','请绑定客户类型与队列关系!');
													return;
												}
												requestAjax('<%=basePath%>confManager/machineView_editLeafById',submitData,function(sRet){
													Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="操作成功"/>',function(id){
														qfcStore.reload();
														window.parent.queryMessage();
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
					  	//客户类型与队列关系绑定的grid
					  	var custTypeQueueBindGrid = [{
					  		xtype: 'editorgrid',
					  		name:'cqGrid',
					  		id:'cqGrid',
					  		store: resultStore,
					  		width: 567,
					    	height: clientHeight-290,
					    	viewConfig: {forceFit: false},
					        doLayout: function() {
					          	this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true));
					        },
					        cm: regionColModel,
					        sm:sm,
					        clicksToEdit: 1 //点击几次可修改
						}];
					  	Ext.getCmp('fieldset_add_type').add(items);
	                  	Ext.getCmp('fieldset_add_type').doLayout();
	                  	
	                  	Ext.getCmp('custtypeQueuetypeBind').add(custTypeQueueBindGrid);
	                  	Ext.getCmp('custtypeQueuetypeBind').doLayout();
	                  	
	                  	var custtypes = custTypeStore.data.items;
	                  	var dataList = [];
	                  	for(var i=0;i<custtypes.length;i++){
	                  		var tempData = {
	                  			'custtype_i':custtypes[i].data['custtype_i']//这里可以添加未来的客户类型是否预约标识
	                  		};
	                  		dataList.push(tempData);
	                  	}
					  	if(type=='3'){//添加业务
						  	resultStore.loadData(dataList);
						  	Ext.getCmp('fieldset_add_type').setTitle('添加业务');
						  	getLastIndexChild(parentNode);
		    			}else if(type=='4'){//修改业务
		    				Ext.getCmp('fieldset_add_type').setTitle('编辑业务');
		                  	//点击的是业务,需要进行查询此业务,之后进行修改
					  		var submitData = {};
					  		submitData['parameter_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
                			submitData['node_id'] = activenodedata.node_id;//父节点node_Id
					  		requestAjax('<%=basePath%>/confManager/machineView_queryLeafByViewNoAndId',submitData,function(sRet){
								//这次操作结束后才进行这步操作
								var map = sRet.leafMap;
								Ext.getCmp('businessCom').setValue(map['bs_id']);//业务编号
								Ext.getCmp('is_show_msg').setValue(map['is_show_msg']);//业务是否显示提示信息
								Ext.getCmp('prompt_msg').setValue(map['prompt_msg']);//业务提示信息
								Ext.getCmp('need_reserv').setValue(map['need_reserv']);//预约
								Ext.getCmp('card_visiable').setValue(map['card_visiable']);//仅刷卡可见
								Ext.getCmp('need_card').setValue(map['need_card']);//必须刷卡
                       			
								//这里要与生成的全部数据进行比对添加...好难
								var array = sRet.custtype_list;
								
								for(var j = 0;j<array.length;j++){
									for(var i = 0;i<dataList.length;i++){
										if(array[j]['custtype_i'] == dataList[i]['custtype_i']){
											//这两条数据是一个
											dataList[i]['bound'] = array[j]['bound'];
											dataList[i]['queuetype_id'] = array[j]['queuetype_id'];
											dataList[i]['wait_area'] = array[j]['wait_area'];
											dataList[i]['prompt_msg'] = array[j]['prompt_msg'];
											dataList[i]['nocard_default'] = array[j]['nocard_default'];
											dataList[i]['isreservqidflag'] = array[j]['isreservqidflag'];
											dataList[i]['reserv_queuetype_id'] = array[j]['reserv_queuetype_id'];
											break;
										}
									}
		    					}
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
								                	qfcStore.load({params:submit});
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
			    	   				title:'排队机界面设置',
			    	   				items:[{
			    	   					columnWidth:1,
			    	   					layout:'form',
			    	   					items:[{
			    	   						xtype:'treepanel',
			    	   						unstyled : true,
			    	   						root: PaiDuiJiTree,
			    	   						id:'viewTree',
			    	   						autoScroll:true,
			    	   						height:clientHeight - 130,
			    	   						listeners : {
			    	   							contextmenu:onmenu,
			    	   							click:function(node){
			    	   								//菜单树左键点击时候的判断,目录时候查询目录并打开修改目录页面,节点时候查询节点并打开修改节点页面
			    	   						  		activetreenode = node;
				    	   						  	for(var i=0;i<PaiDuiJiModules.length;i++)
				    	   							{
				    	   								if(PaiDuiJiModules[i][jsonMeta.nodeId] === node.id){
				    	   									activenodedata = PaiDuiJiModules[i];
				    	   									break;
				    	   								}
				    	   							}
			    	   						  		if(node.attributes.type == '0'){
			    	   						  			//根节点,清空右侧的界面
			    	   						    		if(Ext.getCmp('custtypeQueuetypeBind')){
			    	   						      			Ext.getCmp('custtypeQueuetypeBind').removeAll(true);
		    	   						    			}
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
				systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'01'}});
			}
		</script>
	</head>

	<body>
		<div id="panel"></div>
	</body>
</html>