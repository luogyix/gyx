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
		<title>填单菜单管理</title>
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
			var titles = "设备菜单配置";
			var items=[];
			var menuitems=[];
			var treeData = [{"node_id":"root",	"parent_id":"-1", "child_id":"",	"node_name_ch":"自助菜单", "node_type":"0",	"node_location":"1", "node_level":"1","metadata_id":""}];
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
				nodeId:'node_id',
				child_id:'child_id',
				parentNodeId:'parent_id',
				nodeName:'node_name_ch',
				//nodeNameEn:'menu_name_en',
				//nodeHref:'menu_action',
				nodeTarget:'',
				leafField:'',
				nodeType:'node_type',
				node_location:'node_location',
				node_level:'node_level',
				metadata_id:'metadata_id'
			};
			//菜单标志
			var trade_flagStore=new Ext.data.SimpleStore({ 
				data:[['0','管理菜单'],['1','业务菜单']],
				fields : ['key','value']
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
			//菜单所属角色
			var quartersStore=new Ext.data.SimpleStore({ 
				data:[['1','1-审核岗'],['2','2-管理岗'],['3','3-维护岗']],
				fields : ['dictValue','dictValueDesc']
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
			//应用类型01-排队机 04-手持厅堂 09-手持填单 10-填单机 11-自助机
			var applytypeStore=new Ext.data.SimpleStore({
				data:[["01","01-排队机"],["04","04-手持厅堂"],["09","09-手持填单"],["10","10-填单机"],["11","11-自助机"]], 
				fields : ['key','value']
			});
			
			//界面编号
			var interfaceTypeStore = new Ext.data.JsonStore({ 
				url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=interfacetype',
				root   : 'field1',
				fields : ['dictValue','dictValueDesc'],
				autoLoad:true,
				listeners : {  
					load : function(store,records){
						//var rec = new (store.recordType)();
						//写入数据
						//rec.set('dictValue', '');
						//rec.set('dictValueDesc', '---请选择---');
						//store.insert(0,rec);
					}
				}
			});
			
			/*
			 * metadata_id 元数据id
			 * menu_code	菜单码
			 * menu_name 菜单名称
			 * trade_flag 菜单标志,0-管理菜单 1-业务菜单
			 * menu_description 菜单描述
			 * remind 提示信息
			 * menu_role 菜单所属角色 1-审核岗,2-管理岗,3-维护岗
			 * busitype  所属业务类别,0-对公 1-个人
			 * menutype  是否授权,0-直接使用,1-需授权使用
			 * menustatus 菜单状态,0-可见 1-隐藏
			 * menu_serv_time 菜单生效时间段
			 * deleteflag    删除标志,0-正常菜单,1-已弃用菜单
			 * disablestatus 正常菜单未生效时状态,0-不可见、1-加锁、2-置灰
			 */
		  	var resultStore = new Ext.data.JsonStore({
		  		fields : ['menu_code','menu_name','metadata_id','interface_id','trade_flag','menu_description','menu_role','busitype','menutype','menustatus','menu_serv_time','deleteflag','disablestatus']
          	});
			
			//复制菜单中的选项
			var metadataMenuStore=new Ext.data.JsonStore({
				url    : '<%=basePath%>/confManager/selfMenuInfoAction_queryAllMetadataMenuToList?device_type=10',
				root   : 'field1',
				fields:['menu_code','menu_name','metadata_id','interface_id','trade_flag','menu_description','menu_role','busitype','menutype','menustatus','menu_serv_time','deleteflag','disablestatus','node_location'],
				autoLoad:true,
				listeners : {  
					load : function(store,records){
						//var rec = new (store.recordType)();
						//写入数据
						//rec.set('dictValue', '');
						//rec.set('dictValueDesc', '---请选择---');
						//store.insert(0,rec);
					}
				}
			});
			
			var menuStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>/confManager/selfMenuInfoAction_queryAllMenuToList',
				root   : 'field1',
				fields:['node_id','child_id','node_location','parent_id','node_type','node_name_ch','node_level','metadata_id'],
    			listeners : {
    				load : function(store,records){
    					treeData = [];
    					for(var i = 0;i<store.data.length;i++){
    						var record = store.getAt(i).data;
    						var tree1 = {};
    						tree1['node_id']=record['node_id'];
    						tree1['node_name_ch'] = record['node_name_ch'];
    						tree1['node_type'] = record['node_type'];
    						tree1['node_level'] = record['node_level'];
    						tree1['node_location'] = record['node_location'];
    						tree1['parent_id'] = record['parent_id'];
    						tree1['metadata_id'] = record['metadata_id'];
    						tree1['child_id']=record['child_id'];
    						treeData.push(tree1);
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
			
			//业务编号
			var tradeStore=new Ext.data.JsonStore({
				url: '<%=basePath%>/pfs/pfsTrade_queryTradeSmall',
				root   : 'field1',
				fields:['trade_id','trade_name_ch']
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
					id:'systemParamForm',
					name:'systemParamForm',
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
				            {header: '参数编号', width: 80, dataIndex: 'parameter_id', hidden:true},
				            {header: '参数名称', width: 150, dataIndex: 'parameter_name', sortable:true},
				            {header: '界面类型', width: 80, dataIndex: 'parameter_flag',sortable:true,renderer:function(value){
				            	if(value=="01"){
				            		return "排队机";
				            	}else if(value=="04"){
				            		return "手持厅堂";
				            	}else if(value=="09"){
				            		return "手持填单";
				            	}else if(value=="10"){
				            		return "填单机";
				            	}else if(value=="11"){
				            		return "自助机";
				            	}else{
				            		return value;
				            	}
				            }},
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
				    				Ext.getCmp('parameter_flag').setValue('');
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
											requestAjax('<%=basePath%>confManager/systemParameter_delSystemParameterByParameterId',submitdata,function(sRet){
												Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
													systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'10'}});
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
		    	
				//服务时间
				var timeStore = new Ext.data.JsonStore({
					fields : ['begin_time','end_time']
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
		                anchor:'99%',
		                maxLength : 50,
		                maxLengthText : '长度不能大于50位',
		                allowBlank : false
		            },{//参数标志
		            	fieldLabel: '参数标志',
		                id:'parameter_flag',
		                name:'parameter_flag',
		                xtype:'combo',
		                anchor:'99%',
		                blankText:'请选择...',
		                store:applytypeStore,
		                displayField:'value',
	   					valueField:'key',
	   					maxLength : 50,
		                maxLengthText : '长度不能大于50位',
	   					mode: 'local',
	   					triggerAction: 'all',
	   					editable:false
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
				    height : 200,
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
								//array['parameter_flag'] = '01';
								var mode = Ext.getCmp('operateMode').getValue();
								if (mode == 'add'){
									//通知后台的方法.添加
									
									requestAjax('<%=basePath%>confManager/systemParameter_addSystemParameter',Ext.encode(array),function(sRet){
										Ext.MessageBox.alert('提示','添加参数成功',function(id){
											systemParam_add_window.hide();
											systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'10'}});
										});
									});
								}else if (mode == 'edit'){
									//通知后台的方法.修改
									requestAjax('<%=basePath%>confManager/systemParameter_editSystemParameter',Ext.encode(array),function(sRet){
										/*
										if(!sRet[success]){
											Ext.MessageBox.alert("消息提示",sRet[errmsg]);
											return;
										}
										*/
										Ext.MessageBox.alert('提示','修改参数成功',function(id){
											systemParam_add_window.hide();
											systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'10'}});
										});
									});
								}else if(mode == 'copy'){
									//通知后台的方法.复制
									requestAjax('<%=basePath%>confManager/systemParameter_copysystemParameter',Ext.encode(array),function(sRet){
										Ext.MessageBox.alert('提示','复制成功',function(id){
											systemParam_add_window.hide();
											systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'10'}});
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
				
//-------------------------------------------------------------------------自助机列表
				function onMenuClick(menu,menuitem,e){
					switch(menuitem.id){
						case "nodeaddmulu":
							//alert(activetreenode.attributes.text+'ID:'+activetreenode.attributes.id);//节点id
							//alert(activetreenode.parentNode.id);//父节点id
							//alert(activetreenode.attributes.type);//菜单类型：0-系统；1-文件夹；2-业务；
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
		                  			submitData['interface_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
		                			submitData['node_id'] = activenodedata.node_id;//
		                			submitData['parent_id'] = parent.attributes.id=='root'?'root':parent.attributes.id;//父节点menu_id
		                			submitData['metadata_id']=activenodedata.metadata_id;
		                			
									requestAjax('<%=basePath%>confManager/selfMenuInfoAction_deleteMenuLeaf', submitData,function(sRet){
										Ext.MessageBox.alert('<s:text name="common.info.title"/>','删除节点成功',function(id){
											menuStore.reload();
											metadataMenuStore.reload();
											Ext.getCmp('fieldset_add_type').removeAll(true);
	   	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
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
						{id:'nodedelete',  text:'删除节点'}
		    		]
				});
				//定义右键响应事件
				treecontextmenu.on('click',onMenuClick,this);
				//菜单弹出事件
				function onmenu(node,e){
					if(Ext.getCmp('systemParamCombo').getValue()==""){
						Ext.MessageBox.alert('消息提示','请先选定自助机');
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
						treecontextmenu.items.items[0].show();
						treecontextmenu.items.items[2].hide();
					}else if(activenodedata[jsonMeta.nodeType] === '1'){
						treecontextmenu.items.items[2].show();
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

				/*
				 * 对节点进行操作
				 * type='1'代表是新增目录，type='2'代表编辑目录  3代表添加节点,4代表编辑节点
				 */
				function changeLeaf(type){
					parentNode = activetreenode;
					parentNodeData = activenodedata;
					
					if(type=='1'||type=='2'){
						Ext.getCmp('fieldset_add_type').removeAll(true);
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
				    				    frame:true,
				    				    height:300,
				    				    viewConfig: {forceFit: true,autoFil:true},
				    				    clicksToEdit:1,
				    				    tbar: [
				    				    	{id:'addBtn', text: '添加', disabled: false, iconCls: "x-image-add", 
				    				    		handler: function(){
				    								var length= timeStore.getCount();
				    								if(length<1){
					    	   							var recordType = timeStore.recordType;
					    	   							var record = new recordType({
					    	   								begin_time:'',end_time:''
					    								});
					    	   							timeGrid.stopEditing();
					    	   							timeStore.insert(length, record);
					    	   							timeGrid.getView().refresh();
					    	   							timeSM.selectRow(length);
					    	   							timeGrid.startEditing(timeStore.getCount()-1, 3);
				    								}
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
									  	columnWidth:1,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'metadatamenu', 
										  	fieldLabel:'复制菜单',
										  	emptyText: '请选择...',
										  	anchor:'55%',
										  	editable:false,
										  	disabled: false,
										  	id:'metadatamenu',
										  	store:metadataMenuStore,
											displayField:'menu_name',
											valueField:'metadata_id',
											mode: 'local',
											triggerAction: 'all',
											listeners:{
												'select': function(){
													var index = metadataMenuStore.find('metadata_id',Ext.getCmp('metadatamenu').getValue());
													var record = metadataMenuStore.getAt(index).data;
													Ext.getCmp('node_location').setValue(record['node_location']);
													if(type=='1'){
														Ext.getCmp('menu_code').setValue(record['menu_code']);
														Ext.getCmp('node_name_ch').setValue(record['menu_name']);
													}
													
													Ext.getCmp('trade_flag').setValue(record['trade_flag']);
													/*
													if("0"==Ext.getCmp('trade_flag').getValue()){
														Ext.getCmp('menu_role').show();
													}else{
														Ext.getCmp('menu_role').hide();
													}
													*/
													if(record['menu_role'].indexOf("1") != -1){
														Ext.getCmp('menu_role1').setValue(true);
													}else{
														Ext.getCmp('menu_role1').setValue(false);
													}
													if(record['menu_role'].indexOf("2") != -1){
														Ext.getCmp('menu_role2').setValue(true);
													}else{
														Ext.getCmp('menu_role2').setValue(false);
													}
													if(record['menu_role'].indexOf("3") != -1){
														Ext.getCmp('menu_role3').setValue(true);
													}else{
														Ext.getCmp('menu_role3').setValue(false);
													}
													Ext.getCmp('menutype').setValue(record['menutype']);
													Ext.getCmp('busitype').setValue(record['busitype']);
													Ext.getCmp('menustatus').setValue(record['menustatus']);
													Ext.getCmp('disablestatus').setValue(record['disablestatus']);
													Ext.getCmp('deleteflag').setValue(record['deleteflag']);
													Ext.getCmp('menu_description').setValue(record['menu_description']);
													var menu_serv_time= record['menu_serv_time'].split(';');
													var newArray = new Array();
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
												}
											}
									  	}]
								  	},{
										columnWidth:1/2,
										labelAlign: 'right',
										layout:'form',
										labelWidth:120,
								    	items:[{
								    		xtype:'textfield',
								    		name:'node_name_ch',
								    		id:'node_name_ch', 
								    		maxLength:50,
								    		maxLengthText : '长度不能大于50位',
								    		anchor:'95%',
								    		disabled: false,
								    		allowBlank:false,
								    		fieldLabel:'目录名称',
								    		emptyText: '请输入目录名称...'
								    	}]
							    	},{
								  		columnWidth:1/2,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'textfield',
										  	name:'menu_code', 
										  	fieldLabel:'目录码',
										  	maxLength : 10,
										  	maxLengthText : '长度不能大于10位',
										  	emptyText: '请输入目录码...',
										  	allowBlank:false,
										  	readOnly:false,
										  	anchor:'95%',
										  	disabled: false,
										  	id:'menu_code'
									  	}]
								  	},{
									  	columnWidth:1/2,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'textfield',
										  	name:'node_location', 
										  	fieldLabel:'目录顺序号',
										  	emptyText: '请输入...',
										  	maxLength : 10,
										  	maxLengthText : '长度不能大于10位',
										  	regex:/^[0-9]*$/, 
										  	disabled: false,
										  	regexText:'只能输入数字',
										  	anchor:'95%',
										  	id:'node_location'
									  	}]
								  	},{
								  		columnWidth:1/2,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'trade_flag', 
										  	fieldLabel:'目录标志',
										  	emptyText: '请选择...',
										  	editable:false,
										  	disabled: false,
										  	store:trade_flagStore,
										  	anchor:'95%',
										  	displayField:'value',
										  	valueField:'key',
										  	id:'trade_flag',
										  	mode: 'local',
										  	triggerAction: 'all',
										  	listeners:{
												'select': function(){
													//if("0"==Ext.getCmp('trade_flag').getValue()){
													//	Ext.getCmp('menu_role').show();
													///}
												}
											}
									  	}]
								  	},{
									  	columnWidth:1/2,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'menutype', 
										  	fieldLabel:'目录类型',
										  	emptyText: '请选择...',
										  	anchor:'95%',
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
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'busitype', 
										  	fieldLabel:'所属业务类别',
										  	emptyText: '请选择...',
										  	anchor:'95%',
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
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'menustatus', 
										  	fieldLabel:'目录状态',
										  	emptyText: '请选择...',
										  	anchor:'95%',
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
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'disablestatus', 
										  	fieldLabel:'目录未生效时状态',
										  	emptyText: '请选择...',
										  	anchor:'95%',
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
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'deleteflag', 
										  	fieldLabel:'删除标记',
										  	emptyText: '请选择...',
										  	anchor:'95%',
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
									  	columnWidth:1/2,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'tradeId', 
										  	fieldLabel:'业务编号(非必选)',
										  	emptyText: '请选择...',
										  	anchor:'95%',
										  	editable:false,
										  	disabled: false,
										  	id:'tradeId',
										  	store:tradeStore,
											displayField:'trade_name_ch',
											valueField:'trade_id',
											hidden:false,
											mode: 'local',
											triggerAction: 'all',
											listeners:{
												'select': function(){
													Ext.getCmp('menu_code').setValue(Ext.getCmp('tradeId').getValue());
												}
											}
									  	}]
								  	},{
									  	columnWidth:1,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'column',
									  	fieldLabel:'目录所属角色',
									  	items:[{
									  		columnWidth:1/2,
									  		labelAlign: 'right',
									  		labelWidth:120,
									  		layout:'form',
									  		items:[{
									  			xtype : 'checkbox',
											  	name:'menu_role1', 
											  	hideLabel : false,
											  	fieldLabel:'目录所属角色',
											  	id:'menu_role1',
											  	anchor:'95%',
											  	boxLabel:'审核岗',
											  	checked : false
									  		}]
									  	},{
									  			columnWidth:1/4,
										  		layout:'form',
										  		items:[{
										  			xtype : 'checkbox',
												  	name:'menu_role2', 
												  	hideLabel : true,
												  	id:'menu_role2',
												  	boxLabel:'管理岗'
										  		}]
									  		},{
									  			columnWidth:1/4,
										  		layout:'form',
										  		items:[{
										  			xtype : 'checkbox',
												  	name:'menu_role3', 
												  	hideLabel : true,
												  	id:'menu_role3',
												  	boxLabel:'维护岗'
										  		}]
									  		}]
								  	},{
									  	columnWidth:1,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'textarea',
										  	name:'menu_description', 
										  	fieldLabel:'目录描述',
										  	emptyText: '请输入...',
										  	allowBlank:true,
										  	disabled: false,
										  	height:70,
										  	//autoHeight:true,
										  	anchor:'95%',
										  	id:'menu_description'
									  	}]
								  	},{
									  	columnWidth:1,
									  	labelAlign: 'right',
									  	labelWidth:150,
									  	layout:'form',
									  	items:[{
								    		title:'目录生效时间',
								    		xtype:'fieldset',
								    		collapsible:false,
								    		height:180,
								    		//autoHeight:true,
								      		items:[timeGrid]
								    	}]
								  	},{
					  		  	columnWidth:1,
					  		  	layout:'form',
					  		  	buttonAlign : 'center',
					    	  	buttons:[{
					    		text:'确定',
					    		formBind:true,
					    		handler: function(){
					    			if(type=='1'){
					    				//添加目录
										Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确认提交?',function(id){
											if(id === 'yes'){
												var submitData = {};
												submitData['parent_id'] = parentNodeData.node_id;//父节点menu_id
			                        			submitData['node_level'] = parseInt(parentNodeData.node_level)+1//节点层级
			                        			submitData['node_type']='1';//菜单类型0-系统 1-目录 2-业务
			                        			if("" == Ext.getCmp('node_name_ch').getValue()){
			                        				Ext.MessageBox.alert('提示信息','<s:text name="common.info.addmenuname"/>');
			                        				return;
			                        			}else{
			                        				submitData['node_name_ch']=Ext.getCmp('node_name_ch').getValue();
			                        				submitData['menu_name']=Ext.getCmp('node_name_ch').getValue();
			                        			}
			                        			
			                        			if(""==Ext.getCmp('menu_code').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请输入目录码');
			                        				return;
			                        			}else{
			                        				submitData['menu_code']=Ext.getCmp('menu_code').getValue();
			                        			}
			                        			
			                        			if("" == Ext.getCmp('node_location').getValue()){
			                        				if(parentNode.hasChildNodes()){
				                        				var index = menuStore.find('node_id',parentNode.lastChild.id);
				            							var record = menuStore.getAt(index).data.node_location;
				                        				submitData['node_location'] = parseInt(record) + 1;//节点位置
				                        			}else{
				                        				submitData['node_location'] = '1';
				                        			}
			                        			}else{
			                        				var pattern=/^[0-9]*$/;
			                        				flag=pattern.test(Ext.getCmp('node_location').getValue())
			                        				if(!flag){
			                        					Ext.MessageBox.alert('提示信息','您输入的目录顺序号不是数字');
			                        					return;
			                        				}
			                        				submitData['node_location']=Ext.getCmp('node_location').getValue();
			                        			}
			                        			if(""==Ext.getCmp('trade_flag').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择目录标志（管理菜单或业务菜单）');
			                        				return;
			                        			}else{
			                        				submitData['trade_flag']=Ext.getCmp('trade_flag').getValue();
			                        			}
			                        			
			                        			
			                        			if(""==Ext.getCmp('menutype').getValue()){
			                        				submitData['menutype'] = '1';//菜单类型0-直接使用、1-需授权使用
			                        			}else{
			                        				submitData['menutype']=Ext.getCmp('menutype').getValue();
			                        			}
			                        			
			                        			if(""==Ext.getCmp('busitype').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择目录所属业务类别');
			                        				return;
			                        			}else{
			                        				submitData['busitype']=Ext.getCmp('busitype').getValue();
			                        			}
			                        			var role='';
			                        			if(Ext.getCmp('menu_role1').getValue()==true){
			                        				role=role+'1';
			                        			}
			                        			if(Ext.getCmp('menu_role2').getValue()==true){
			                        				if(role!=null&&role!=''){
			                        					role=role+';2';
			                        				}else{
			                        					role=role+'2';
			                        				}
			                        				
			                        			}
			                        			if(Ext.getCmp('menu_role3').getValue()==true){
			                        				if(role!=null&&role!=''){
			                        					role=role+';3';
			                        				}else{
			                        					role=role+'3';
			                        				}
			                        			}
			                        			submitData['menu_role'] = role;
												submitData['menustatus'] = Ext.getCmp('menustatus').getValue();
												submitData['disablestatus'] = Ext.getCmp('disablestatus').getValue();
												submitData['deleteflag'] = Ext.getCmp('deleteflag').getValue();
												submitData['menu_description'] = Ext.getCmp('menu_description').getValue();
												submitData['interface_id']=Ext.getCmp('systemParamCombo').getValue();
												var index = systemParamStore.find("parameter_id",Ext.getCmp('systemParamCombo').getValue());
												submitData['interface_name'] =systemParamStore.getAt(index).data.parameter_name;
			                        			
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
												//添加目录
												submitData['menu_serv_time']= menu_serv_time.substr(1);
												submitData['device_type']= "10";
												requestAjax('<%=basePath%>confManager/selfMenuInfoAction_addMenu',submitData,function(sRet){
													Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="操作成功"/>',function(id){
														menuStore.reload();
														var index = systemParamStore.find("parameter_id",Ext.getCmp('systemParamCombo').getValue());
														var parameter_flag=systemParamStore.getAt(index).data.parameter_flag;
														Ext.getCmp('fieldset_add_type').removeAll(true);
														if(parameter_flag=='01'){
															Ext.getCmp('fieldset_add_type').setTitle("排队机"+titles);
														}else if(parameter_flag=='10'){
															Ext.getCmp('fieldset_add_type').setTitle("填单机"+titles);
														}else if(parameter_flag=='11'){
															Ext.getCmp('fieldset_add_type').setTitle("自助机"+titles);
														}
															
													});
												});
											}
										});
					    			}else if(type=='2'){
					    				//修改目录
										Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确认提交?',function(id){
											if(id === 'yes'){
												var submitData = {};
												submitData['parent_id'] = activenodedata.parent_id;//父节点menu_id
			                        			submitData['node_level'] = activenodedata.node_level//节点层级
			                        			submitData['node_type']='1';//菜单类型0-系统 1-目录 2-业务
			                        			submitData['node_id']=activenodedata.node_id;
			                        			if("" == Ext.getCmp('node_name_ch').getValue()){
			                        				Ext.MessageBox.alert('提示信息','<s:text name="common.info.addmenuname"/>');
			                        				return;
			                        			}else{
			                        				submitData['node_name_ch']=Ext.getCmp('node_name_ch').getValue();
			                        				submitData['menu_name']=Ext.getCmp('node_name_ch').getValue();
			                        				
			                        			}
			                        			
			                        			if(""==Ext.getCmp('menu_code').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请输入目录码');
			                        				return;
			                        			}else{
			                        				submitData['menu_code']=Ext.getCmp('menu_code').getValue();
			                        				
			                        			}
			                        			
			                        			if("" == Ext.getCmp('node_location').getValue()){
			                        				if(parentNode.hasChildNodes()){
				                        				var index = menuStore.find('node_id',parentNode.lastChild.id);
				            							var record = menuStore.getAt(index).data.node_location;
				                        				submitData['node_location'] = parseInt(record) + 1;//节点位置
				                        			}else{
				                        				submitData['node_location'] = '1';
				                        			}
			                        			}else{
			                        				var pattern=/^[0-9]*$/;
			                        				flag=pattern.test(Ext.getCmp('node_location').getValue())
			                        				if(!flag){
			                        					Ext.MessageBox.alert('提示信息','您输入的目录顺序号不是数字');
			                        					return;
			                        				}
			                        				submitData['node_location']=Ext.getCmp('node_location').getValue();
			                        			}
			                        			if(""==Ext.getCmp('trade_flag').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择目录标志（管理菜单或业务菜单）');
			                        				return;
			                        			}else{
			                        				submitData['trade_flag']=Ext.getCmp('trade_flag').getValue();
			                        			}
			                        			
			                        			if(""==Ext.getCmp('menutype').getValue()){
			                        				submitData['menutype'] = '1';//菜单类型0-直接使用、1-需授权使用
			                        			}else{
			                        				submitData['menutype']=Ext.getCmp('menutype').getValue();
			                        			}
			                        			
			                        			if(""==Ext.getCmp('busitype').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择目录所属业务类别');
			                        				return;
			                        			}else{
			                        				submitData['busitype']=Ext.getCmp('busitype').getValue();
			                        			}
			                        			submitData['metadata_id']=activenodedata.metadata_id;
			                        			submitData['metadatamenu']=Ext.getCmp('metadatamenu').getValue();
			                        			var role='';
			                        			if(Ext.getCmp('menu_role1').getValue()==true){
			                        				role=role+'1';
			                        			}
			                        			if(Ext.getCmp('menu_role2').getValue()==true){
			                        				if(role!=null&&role!=''){
			                        					role=role+';2';
			                        				}else{
			                        					role=role+'2';
			                        				}
			                        				
			                        			}
			                        			if(Ext.getCmp('menu_role3').getValue()==true){
			                        				if(role!=null&&role!=''){
			                        					role=role+';3';
			                        				}else{
			                        					role=role+'3';
			                        				}
			                        			}
			                        			submitData['menu_role'] = role;
												submitData['menustatus'] = Ext.getCmp('menustatus').getValue();
												submitData['disablestatus'] = Ext.getCmp('disablestatus').getValue();
												submitData['deleteflag'] = Ext.getCmp('deleteflag').getValue();
												submitData['menu_description'] = Ext.getCmp('menu_description').getValue();
												submitData['parameter_id']=Ext.getCmp('systemParamCombo').getValue();
												submitData['interface_id']=Ext.getCmp('systemParamCombo').getValue();
												var index = systemParamStore.find("parameter_id",Ext.getCmp('systemParamCombo').getValue());
												submitData['interface_name'] =systemParamStore.getAt(index).data.parameter_name;
			                        			
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
												submitData['device_type'] = '10';
												requestAjax('<%=basePath%>confManager/selfMenuInfoAction_editMenuLeaf',submitData,function(sRet){
													
													Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="操作成功"/>',function(id){
														menuStore.reload();
														var index = systemParamStore.find("parameter_id",Ext.getCmp('systemParamCombo').getValue());
														var parameter_flag=systemParamStore.getAt(index).data.parameter_flag;
														Ext.getCmp('fieldset_add_type').removeAll(true);
														if(parameter_flag=='01'){
															Ext.getCmp('fieldset_add_type').setTitle("排队机"+titles);
														}else if(parameter_flag=='10'){
															Ext.getCmp('fieldset_add_type').setTitle("填单机"+titles);
														}else if(parameter_flag=='11'){
															Ext.getCmp('fieldset_add_type').setTitle("自助机"+titles);
														}else{
															Ext.getCmp('fieldset_add_type').setTitle(titles);
														}
													});
												});
											}
										});
										
					    			}
					    		 }
					    	  }]
						}];
						Ext.getCmp('fieldset_add_type').add(items);
						metadataMenuStore.reload();
						tradeStore.load({params:{'query_rules':'3'}});
				        Ext.getCmp('fieldset_add_type').doLayout();
				        var dataList = [];
					    if(type=='1'){//添加目录
						//resultStore.loadData(dataList);
							Ext.getCmp('fieldset_add_type').setTitle('添加目录');
					    }else if(type=='2'){//修改目录
					    	Ext.getCmp('fieldset_add_type').setTitle('编辑目录');
					        //点击的是业务,需要进行查询此业务,之后进行修改
						    var submitData = {};
						    submitData['interface_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
			                submitData['node_id'] = activenodedata.node_id;//节点menu_id
			                submitData['metadata_id'] = activenodedata.metadata_id;//元数据id
						    requestAjax('<%=basePath%>confManager/selfMenuInfoAction_queryMenuLeafById',submitData,function(sRet){
							var map = sRet.field1;
							var newArray = new Array();
							Ext.getCmp('node_location').setValue(map[0]['node_location']);
							Ext.getCmp('menu_code').setValue(map[0]['menu_code']);
							Ext.getCmp('menu_code').getEl().dom.readOnly=true;
							Ext.getCmp('menu_code').getEl().dom.disabled=true;
							Ext.getCmp('menu_code').getEl().dom.style.background='#E6E6E6';
							Ext.getCmp('menu_code').getEl().dom.style.color='#000000';
							Ext.getCmp('tradeId').hide();
							Ext.getCmp('node_name_ch').setValue(map[0]['menu_name']);
							//Ext.getCmp('node_name_ch').getEl().dom.readOnly=true;
							Ext.getCmp('trade_flag').setValue(map[0]['trade_flag']);
							/*
							if("0"==Ext.getCmp('trade_flag').getValue()){
								Ext.getCmp('menu_role').show();
							}
							*/
							if(map[0]['menu_role'].indexOf("1") != -1){
								Ext.getCmp('menu_role1').setValue(true);
							}else{
								Ext.getCmp('menu_role1').setValue(false);
							}
							if(map[0]['menu_role'].indexOf("2") != -1){
								Ext.getCmp('menu_role2').setValue(true);
							}else{
								Ext.getCmp('menu_role2').setValue(false);
							}
							if(map[0]['menu_role'].indexOf("3") != -1){
								Ext.getCmp('menu_role3').setValue(true);
							}else{
								Ext.getCmp('menu_role3').setValue(false);
							}
							Ext.getCmp('menutype').setValue(map[0]['menutype']);
							Ext.getCmp('busitype').setValue(map[0]['busitype']);
							Ext.getCmp('menustatus').setValue(map[0]['menustatus']);
							Ext.getCmp('disablestatus').setValue(map[0]['disablestatus']);
							Ext.getCmp('deleteflag').setValue(map[0]['deleteflag']);
							Ext.getCmp('menu_description').setValue(map[0]['menu_description']);
							var menu_serv_time= map[0]['menu_serv_time'].split(';');
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
							//resultStore.loadData(dataList);
						});
					   }
					} else if(type=='3'||type=='4'){//添加修改业务
					  	Ext.getCmp('fieldset_add_type').removeAll(true);
						/*
					  	var resultStore = new Ext.data.JsonStore({
							fields : ['interface_id','node_name_ch','node_location','menu_code','trade_flag','menu_serv_time','menu_role','menutype','busitype','menustatus','disablestatus','deleteflag','menu_description']
	                  	});
						*/
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
				    				    frame:true,
				    				    height:300,
				    				    viewConfig: {forceFit: true,autoFil:true},
				    				    clicksToEdit:1,
				    				    tbar: [
				    				    	{id:'addBtn', text: '添加', disabled: false, iconCls: "x-image-add", 
				    				    		handler: function(){
				    								var length= timeStore.getCount();
				    								if(length<1){
					    	   							var recordType = timeStore.recordType;
					    	   							var record = new recordType({
					    	   								begin_time:'',end_time:''
					    								});
					    	   							timeGrid.stopEditing();
					    	   							timeStore.insert(length, record);
					    	   							timeGrid.getView().refresh();
					    	   							timeSM.selectRow(length);
					    	   							timeGrid.startEditing(timeStore.getCount()-1, 3);
				    								}
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
									  	columnWidth:1,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'metadatamenu', 
										  	fieldLabel:'复制菜单',
										  	emptyText: '请选择...',
										  	anchor:'55%',
										  	editable:false,
										  	disabled: false,
										  	id:'metadatamenu',
										  	store:metadataMenuStore,
											displayField:'menu_name',
											valueField:'metadata_id',
											mode: 'local',
											triggerAction: 'all',
											listeners:{
												'select': function(){
													var index = metadataMenuStore.find('metadata_id',Ext.getCmp('metadatamenu').getValue());
													var record = metadataMenuStore.getAt(index).data;
													Ext.getCmp('node_location').setValue(record['node_location']);
													if(type=='3'){
														Ext.getCmp('menu_code').setValue(record['menu_code']);
														Ext.getCmp('node_name_ch').setValue(record['menu_name']);
													}
													
													Ext.getCmp('trade_flag').setValue(record['trade_flag']);
													/*
													if("0"==Ext.getCmp('trade_flag').getValue()){
														Ext.getCmp('menu_role').show();
													}else{
														Ext.getCmp('menu_role').hide();
													}
													*/
													if(record['menu_role'].indexOf("1") != -1){
														Ext.getCmp('menu_role1').setValue(true);
													}else{
														Ext.getCmp('menu_role1').setValue(false);
													}
													if(record['menu_role'].indexOf("2") != -1){
														Ext.getCmp('menu_role2').setValue(true);
													}else{
														Ext.getCmp('menu_role2').setValue(false);
													}
													if(record['menu_role'].indexOf("3") != -1){
														Ext.getCmp('menu_role3').setValue(true);
													}else{
														Ext.getCmp('menu_role3').setValue(false);
													}
													Ext.getCmp('menutype').setValue(record['menutype']);
													Ext.getCmp('busitype').setValue(record['busitype']);
													Ext.getCmp('menustatus').setValue(record['menustatus']);
													Ext.getCmp('disablestatus').setValue(record['disablestatus']);
													Ext.getCmp('deleteflag').setValue(record['deleteflag']);
													Ext.getCmp('menu_description').setValue(record['menu_description']);
													var menu_serv_time= record['menu_serv_time'].split(';');
													var newArray = new Array();
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
												}
											}
									  	}]
								  	},{
										columnWidth:1/2,
										labelAlign: 'right',
										layout:'form',
										labelWidth:120,
								    	items:[{
								    		xtype:'textfield',
								    		name:'node_name_ch',
								    		id:'node_name_ch', 
								    		maxLength:50,
								    		maxLengthText : '长度不能大于50位',
								    		anchor:'95%',
								    		disabled: false,
								    		allowBlank:false,
								    		fieldLabel:'菜单名称',
								    		emptyText: '请输入菜单名称...'
								    	}]
							    	},{
								  		columnWidth:1/2,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'textfield',
										  	name:'menu_code', 
										  	fieldLabel:'菜单码',
										  	maxLength : 10,
										  	maxLengthText : '长度不能大于10位',
										  	emptyText: '请输入菜单码...',
										  	allowBlank:false,
										  	readOnly:false,
										  	anchor:'95%',
										  	disabled: false,
										  	id:'menu_code'
									  	}]
								  	},{
									  	columnWidth:1/2,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'textfield',
										  	name:'node_location', 
										  	fieldLabel:'菜单顺序号',
										  	emptyText: '请输入...',
										  	maxLength : 10,
										  	maxLengthText : '长度不能大于10位',
										  	regex:/^[0-9]*$/, 
										  	disabled: false,
										  	regexText:'只能输入数字',
										  	anchor:'95%',
										  	id:'node_location'
									  	}]
								  	},{
								  		columnWidth:1/2,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'trade_flag', 
										  	fieldLabel:'菜单标志',
										  	emptyText: '请选择...',
										  	editable:false,
										  	disabled: false,
										  	store:trade_flagStore,
										  	anchor:'95%',
										  	displayField:'value',
										  	valueField:'key',
										  	id:'trade_flag',
										  	mode: 'local',
										  	triggerAction: 'all',
										  	listeners:{
												'select': function(){
													/*
													if("0"==Ext.getCmp('trade_flag').getValue()){
														Ext.getCmp('menu_role').show();
													}else{
														Ext.getCmp('menu_role').hide();
													}
													*/
												}
											}
									  	}]
								  	},{
									  	columnWidth:1/2,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'menutype', 
										  	fieldLabel:'菜单类型',
										  	emptyText: '请选择...',
										  	anchor:'95%',
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
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'busitype', 
										  	fieldLabel:'所属业务类别',
										  	emptyText: '请选择...',
										  	anchor:'95%',
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
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'menustatus', 
										  	fieldLabel:'菜单状态',
										  	emptyText: '请选择...',
										  	anchor:'95%',
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
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'disablestatus', 
										  	fieldLabel:'菜单未生效时状态',
										  	emptyText: '请选择...',
										  	anchor:'95%',
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
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'deleteflag', 
										  	fieldLabel:'删除标记',
										  	emptyText: '请选择...',
										  	anchor:'95%',
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
									  	columnWidth:1/2,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'combo',
										  	name:'tradeId', 
										  	fieldLabel:'业务编号(非必选)',
										  	emptyText: '请选择...',
										  	anchor:'95%',
										  	editable:false,
										  	disabled: false,
										  	hidden:false,
										  	id:'tradeId',
										  	store:tradeStore,
											displayField:'trade_name_ch',
											valueField:'trade_id',
											mode: 'local',
											triggerAction: 'all',
											listeners:{
												'select': function(){
													Ext.getCmp('menu_code').setValue(Ext.getCmp('tradeId').getValue());
												}
											}
									  	}]
								  	},{
									  	columnWidth:1,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'column',
									  	fieldLabel:'菜单所属角色',
									  	items:[{
									  		columnWidth:1/2,
									  		labelAlign: 'right',
									  		labelWidth:120,
									  		layout:'form',
									  		items:[{
									  			xtype : 'checkbox',
											  	name:'menu_role1', 
											  	hideLabel : false,
											  	fieldLabel:'菜单所属角色',
											  	id:'menu_role1',
											  	anchor:'95%',
											  	boxLabel:'审核岗',
											  	checked : false
									  		}]
									  	},{
									  			columnWidth:1/4,
										  		layout:'form',
										  		items:[{
										  			xtype : 'checkbox',
												  	name:'menu_role2', 
												  	hideLabel : true,
												  	id:'menu_role2',
												  	boxLabel:'管理岗'
										  		}]
									  		},{
									  			columnWidth:1/4,
										  		layout:'form',
										  		items:[{
										  			xtype : 'checkbox',
												  	name:'menu_role3', 
												  	hideLabel : true,
												  	id:'menu_role3',
												  	boxLabel:'维护岗'
										  		}]
									  		}]
								  	},{
									  	columnWidth:1,
									  	labelAlign: 'right',
									  	labelWidth:120,
									  	layout:'form',
									  	items:[{
									  		xtype:'textarea',
										  	name:'menu_description', 
										  	fieldLabel:'菜单描述',
										  	emptyText: '请输入...',
										  	allowBlank:true,
										  	disabled: false,
										  	height:70,
										  	anchor:'95%',
										  	id:'menu_description'
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
								    		height:180,
								      		items:[timeGrid]
								    	}]
								  	},{
					  		  	columnWidth:1,
					  		  	layout:'form',
					  		  	buttonAlign : 'center',
					    	  	buttons:[{
					    		text:'确定',
					    		formBind:true,
					    		handler: function(){
					     			if(type=='3'){
					    				//添加业务
										Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确认提交?',function(id){
											if(id === 'yes'){
												var submitData = {};
												submitData['parent_id'] = parentNodeData.node_id;//父节点menu_id
			                        			submitData['node_level'] = parseInt(parentNodeData.node_level)+1//节点层级
			                        			submitData['node_type']='2';//菜单类型0-系统 1-目录 2-业务
			                        			if("" == Ext.getCmp('node_name_ch').getValue()){
			                        				Ext.MessageBox.alert('提示信息','<s:text name="common.info.addmenuname"/>');
			                        				return;
			                        			}else{
			                        				submitData['node_name_ch']=Ext.getCmp('node_name_ch').getValue();
			                        				submitData['menu_name']=Ext.getCmp('node_name_ch').getValue();
			                        			}
			                        			
			                        			if(""==Ext.getCmp('menu_code').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请输入菜单码');
			                        				return;
			                        			}else{
			                        				submitData['menu_code']=Ext.getCmp('menu_code').getValue();
			                        			}
			                        			
			                        			if("" == Ext.getCmp('node_location').getValue()){
			                        				if(parentNode.hasChildNodes()){
				                        				var index = menuStore.find('node_id',parentNode.lastChild.id);
				            							var record = menuStore.getAt(index).data.node_location;
				                        				submitData['node_location'] = parseInt(record) + 1;//节点位置
				                        			}else{
				                        				submitData['node_location'] = '1';
				                        			}
			                        			}else{
			                        				var pattern=/^[0-9]*$/;
			                        				flag=pattern.test(Ext.getCmp('node_location').getValue())
			                        				if(!flag){
			                        					Ext.MessageBox.alert('提示信息','您输入的菜单顺序号不是数字');
			                        					return;
			                        				}
			                        				submitData['node_location']=Ext.getCmp('node_location').getValue();
			                        			}
			                        			if(""==Ext.getCmp('trade_flag').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择菜单标志（管理菜单或业务菜单）');
			                        				return;
			                        			}else{
			                        				submitData['trade_flag']=Ext.getCmp('trade_flag').getValue();
			                        			}
			                        			
			                        			
			                        			if(""==Ext.getCmp('menutype').getValue()){
			                        				submitData['menutype'] = '1';//菜单类型0-直接使用、1-需授权使用
			                        			}else{
			                        				submitData['menutype']=Ext.getCmp('menutype').getValue();
			                        			}
			                        			
			                        			if(""==Ext.getCmp('busitype').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择菜单所属业务类别');
			                        				return;
			                        			}else{
			                        				submitData['busitype']=Ext.getCmp('busitype').getValue();
			                        			}
			                        			var role='';
			                        			if(Ext.getCmp('menu_role1').getValue()==true){
			                        				role=role+'1';
			                        			}
			                        			if(Ext.getCmp('menu_role2').getValue()==true){
			                        				if(role!=null&&role!=''){
			                        					role=role+';2';
			                        				}else{
			                        					role=role+'2';
			                        				}
			                        				
			                        			}
			                        			if(Ext.getCmp('menu_role3').getValue()==true){
			                        				if(role!=null&&role!=''){
			                        					role=role+';3';
			                        				}else{
			                        					role=role+'3';
			                        				}
			                        			}
			                        			submitData['menu_role'] = role;
												submitData['menustatus'] = Ext.getCmp('menustatus').getValue();
												submitData['disablestatus'] = Ext.getCmp('disablestatus').getValue();
												submitData['deleteflag'] = Ext.getCmp('deleteflag').getValue();
												submitData['menu_description'] = Ext.getCmp('menu_description').getValue();
												submitData['interface_id']=Ext.getCmp('systemParamCombo').getValue();
												var index = systemParamStore.find("parameter_id",Ext.getCmp('systemParamCombo').getValue());
												submitData['interface_name'] =systemParamStore.getAt(index).data.parameter_name;
			                        			
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
												//添加业务节点
												submitData['menu_serv_time']= menu_serv_time.substr(1);
												submitData['device_type']= "10";
												requestAjax('<%=basePath%>confManager/selfMenuInfoAction_addMenu',submitData,function(sRet){
													Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="操作成功"/>',function(id){
														menuStore.reload();
														var index = systemParamStore.find("parameter_id",Ext.getCmp('systemParamCombo').getValue());
														var parameter_flag=systemParamStore.getAt(index).data.parameter_flag;
														Ext.getCmp('fieldset_add_type').removeAll(true);
														if(parameter_flag=='01'){
															Ext.getCmp('fieldset_add_type').setTitle("排队机"+titles);
														}else if(parameter_flag=='10'){
															Ext.getCmp('fieldset_add_type').setTitle("填单机"+titles);
														}else if(parameter_flag=='11'){
															Ext.getCmp('fieldset_add_type').setTitle("自助机"+titles);
														}
															
													});
												});
											}
										});
					    			}else if(type=='4'){
					    				//修改业务
										Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确认提交?',function(id){
											if(id === 'yes'){
												var submitData = {};
												submitData['parent_id'] = activenodedata.parent_id;//父节点menu_id
			                        			submitData['node_level'] = activenodedata.node_level//节点层级
			                        			submitData['node_type']='2';//菜单类型0-系统 1-目录 2-业务
			                        			submitData['node_id']=activenodedata.node_id;
			                        			if("" == Ext.getCmp('node_name_ch').getValue()){
			                        				Ext.MessageBox.alert('提示信息','<s:text name="common.info.addmenuname"/>');
			                        				return;
			                        			}else{
			                        				submitData['node_name_ch']=Ext.getCmp('node_name_ch').getValue();
			                        				submitData['menu_name']=Ext.getCmp('node_name_ch').getValue();
			                        			}
			                        			
			                        			if(""==Ext.getCmp('menu_code').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请输入菜单码');
			                        				return;
			                        			}else{
			                        				submitData['menu_code']=Ext.getCmp('menu_code').getValue();
			                        				Ext.getCmp('menu_code').getEl().dom.readOnly=false;
			                        			}
			                        			
			                        			if("" == Ext.getCmp('node_location').getValue()){
			                        				if(parentNode.hasChildNodes()){
				                        				var index = menuStore.find('node_id',parentNode.lastChild.id);
				            							var record = menuStore.getAt(index).data.node_location;
				                        				submitData['node_location'] = parseInt(record) + 1;//节点位置
				                        			}else{
				                        				submitData['node_location'] = '1';
				                        			}
			                        			}else{
			                        				var pattern=/^[0-9]*$/;
			                        				flag=pattern.test(Ext.getCmp('node_location').getValue())
			                        				if(!flag){
			                        					Ext.MessageBox.alert('提示信息','您输入的菜单顺序号不是数字');
			                        					return;
			                        				}
			                        				submitData['node_location']=Ext.getCmp('node_location').getValue();
			                        			}
			                        			if(""==Ext.getCmp('trade_flag').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择菜单标志（管理菜单或业务菜单）');
			                        				return;
			                        			}else{
			                        				submitData['trade_flag']=Ext.getCmp('trade_flag').getValue();
			                        			}
			                        			
			                        			if(""==Ext.getCmp('menutype').getValue()){
			                        				submitData['menutype'] = '1';//菜单类型0-直接使用、1-需授权使用
			                        			}else{
			                        				submitData['menutype']=Ext.getCmp('menutype').getValue();
			                        			}
			                        			
			                        			if(""==Ext.getCmp('busitype').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择菜单所属业务类别');
			                        				return;
			                        			}else{
			                        				submitData['busitype']=Ext.getCmp('busitype').getValue();
			                        			}
			                        			submitData['metadata_id']=activenodedata.metadata_id;
			                        			submitData['metadatamenu']=Ext.getCmp('metadatamenu').getValue();
			                        			var role='';
			                        			if(Ext.getCmp('menu_role1').getValue()==true){
			                        				role=role+'1';
			                        			}
			                        			if(Ext.getCmp('menu_role2').getValue()==true){
			                        				if(role!=null&&role!=''){
			                        					role=role+';2';
			                        				}else{
			                        					role=role+'2';
			                        				}
			                        				
			                        			}
			                        			if(Ext.getCmp('menu_role3').getValue()==true){
			                        				if(role!=null&&role!=''){
			                        					role=role+';3';
			                        				}else{
			                        					role=role+'3';
			                        				}
			                        			}
			                        			submitData['menu_role'] = role;
												submitData['menustatus'] = Ext.getCmp('menustatus').getValue();
												submitData['disablestatus'] = Ext.getCmp('disablestatus').getValue();
												submitData['deleteflag'] = Ext.getCmp('deleteflag').getValue();
												submitData['menu_description'] = Ext.getCmp('menu_description').getValue();
												submitData['parameter_id']=Ext.getCmp('systemParamCombo').getValue();
												submitData['interface_id']=Ext.getCmp('systemParamCombo').getValue();
												var index = systemParamStore.find("parameter_id",Ext.getCmp('systemParamCombo').getValue());
												submitData['interface_name'] =systemParamStore.getAt(index).data.parameter_name;
			                        			
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
												submitData['device_type']= "10";
												requestAjax('<%=basePath%>confManager/selfMenuInfoAction_editMenuLeaf',submitData,function(sRet){
													
													Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="操作成功"/>',function(id){
														menuStore.reload();
														var index = systemParamStore.find("parameter_id",Ext.getCmp('systemParamCombo').getValue());
														var parameter_flag=systemParamStore.getAt(index).data.parameter_flag;
														Ext.getCmp('fieldset_add_type').removeAll(true);
														if(parameter_flag=='01'){
															Ext.getCmp('fieldset_add_type').setTitle("排队机"+titles);
														}else if(parameter_flag=='10'){
															Ext.getCmp('fieldset_add_type').setTitle("填单机"+titles);
														}else if(parameter_flag=='11'){
															Ext.getCmp('fieldset_add_type').setTitle("自助机"+titles);
														}else{
															Ext.getCmp('fieldset_add_type').setTitle(titles);
														}
													});
												});
											}
										});
										
					    			}
					    		 }
					    	  }]
						}];
						Ext.getCmp('fieldset_add_type').add(items);
						metadataMenuStore.reload();
						tradeStore.load({params:{'query_rules':'3'}});
	                  	Ext.getCmp('fieldset_add_type').doLayout();
	                  	var dataList = [];
					  	if(type=='3'){//添加业务
						  	//resultStore.loadData(dataList);
						  	Ext.getCmp('fieldset_add_type').setTitle('添加业务');
		    			}else if(type=='4'){//修改业务
		    				Ext.getCmp('fieldset_add_type').setTitle('编辑业务');
		                  	//点击的是业务,需要进行查询此业务,之后进行修改
					  		var submitData = {};
					  		submitData['interface_id'] = Ext.getCmp('systemParamCombo').getValue();//参数编号
                			submitData['node_id'] = activenodedata.node_id;//节点menu_id
                			submitData['metadata_id'] = activenodedata.metadata_id;//元数据id
					  		requestAjax('<%=basePath%>confManager/selfMenuInfoAction_queryMenuLeafById',submitData,function(sRet){
								var map = sRet.field1;
								var newArray = new Array();
								Ext.getCmp('node_location').setValue(map[0]['node_location']);
								Ext.getCmp('menu_code').setValue(map[0]['menu_code']);
								Ext.getCmp('menu_code').getEl().dom.readOnly=true;
								Ext.getCmp('menu_code').getEl().dom.disabled=true;
								Ext.getCmp('menu_code').getEl().dom.style.color='#000000';
								Ext.getCmp('menu_code').getEl().dom.style.background='#E6E6E6';
								Ext.getCmp('tradeId').hide();
								Ext.getCmp('node_name_ch').setValue(map[0]['menu_name']);
								//Ext.getCmp('node_name_ch').getEl().dom.readOnly=true;
								Ext.getCmp('trade_flag').setValue(map[0]['trade_flag']);
								/*
								if("0"==Ext.getCmp('trade_flag').getValue()){
									Ext.getCmp('menu_role').show();
								}
								*/
								if(map[0]['menu_role'].indexOf("1") != -1){
									Ext.getCmp('menu_role1').setValue(true);
								}else{
									Ext.getCmp('menu_role1').setValue(false);
								}
								if(map[0]['menu_role'].indexOf("2") != -1){
									Ext.getCmp('menu_role2').setValue(true);
								}else{
									Ext.getCmp('menu_role2').setValue(false);
								}
								if(map[0]['menu_role'].indexOf("3") != -1){
									Ext.getCmp('menu_role3').setValue(true);
								}else{
									Ext.getCmp('menu_role3').setValue(false);
								}
								
								Ext.getCmp('menutype').setValue(map[0]['menutype']);
								Ext.getCmp('busitype').setValue(map[0]['busitype']);
								Ext.getCmp('menustatus').setValue(map[0]['menustatus']);
								Ext.getCmp('disablestatus').setValue(map[0]['disablestatus']);
								Ext.getCmp('deleteflag').setValue(map[0]['deleteflag']);
								Ext.getCmp('menu_description').setValue(map[0]['menu_description']);
								var menu_serv_time= map[0]['menu_serv_time'].split(';');
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
								//resultStore.loadData(dataList);
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
								                	submit['interface_id'] = combo.getValue();
								                	//submit['interface_name'] = Ext.get('systemParamCombo').dom.value;
								                	var index = systemParamStore.find("parameter_id",Ext.getCmp('systemParamCombo').getValue());
								                	submit['interface_name'] =systemParamStore.getAt(index).data.parameter_name;
								                	Ext.getCmp('fieldset_add_type').removeAll(true);
								                	//Ext.getCmp('fieldset_menu').removeAll(true);
								                	var index = systemParamStore.find('parameter_id',combo.getValue());
								                	var parameter_flag = systemParamStore.getAt(index).data.parameter_flag;
								                	if(parameter_flag=='01'){
														Ext.getCmp('fieldset_add_type').setTitle("排队机"+titles);
													}else if(parameter_flag=='10'){
														Ext.getCmp('fieldset_add_type').setTitle("填单机"+titles);
													}else if(parameter_flag=='11'){
														Ext.getCmp('fieldset_add_type').setTitle("自助机"+titles);
													}else{
														Ext.getCmp('fieldset_add_type').setTitle(titles);
													}
		    	   						    		queryFlag = false;
								                	menuStore.load({params:submit});
								                	
								                	//Ext.getCmp('fieldset_menu').add(menuitems);
		    	   				                  	//Ext.getCmp('fieldset_menu').doLayout();
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
			    	   				title:'菜单管理',
			    	   				hideMode :'visibility',
				    		      	hidden: false,
			    	   				id:'fieldset_menu',
			    	   				items:[{
			    	   					columnWidth:1,
			    	   					layout:'form',
			    	   					items:[{
			    	   						xtype:'treepanel',
			    	   						unstyled : true,
			    	   						root: SelfMenuTree,
			    	   						id:'viewTree',
			    	   						autoScroll:true,
			    	   						height:clientHeight - 130,
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
			    	   						  		}else if(node.attributes.type == '1'){
			    	   						  			//点击的是目录,直接进入目录修改
			    	   						    		changeLeaf('2');
			    	   						  		}else if(node.attributes.type == '2'){
			    	   						  			//点击业务，直接进入修改业务界面
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
			    		      	autoScroll:true,
			    		      	hideMode :'visibility',
			    		      	hidden: false,
			    		      	id:'fieldset_add_type',
			    		      	items:[{
								  	columnWidth:1,
								  	labelAlign: 'right',
								  	labelWidth:150,
								  	layout:'form',
								  	items:items
							  	}]
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
				systemParamStore.load({params:{'branch':'<%=brno%>','parameter_flag':'10'}});
			}
		</script>
	</head>

	<body>
		<div id="panel"></div>
	</body>
</html>