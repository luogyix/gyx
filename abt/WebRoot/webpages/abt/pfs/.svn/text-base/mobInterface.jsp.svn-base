<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
	String brno = user.getUnitid();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>填单机界面</title>
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
			var titles = "填单机界面配置";
			var items = [];
			var treeData = [{"node_id":"root",	"parent_id":"-1",	"node_name":"填单机",	"node_action":"",	"node_type":"0",	"node_location":"1",	"node_level":"1"}];
//----------------------------------------------------------------
			var queryFlag = false;
			//开始构建填单机 "树"
			var PaiDuiJiModules = treeData; //填单机数据
			//var PaiDuiJiModules = treeData; //填单机数据
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
				nodeHref:'node_action',
				nodeTarget:'',
				leafField:'',
				nodeType:'node_type',
				nodeIndex:'node_index',
				nodeOrder:'node_location',
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
    	
    		  		
			var tradeStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>pfs/pfsTrade_queryTradeSmall?query_rules=3',
				autoLoad:true,
				root   : 'field1',
				fields:['trade_id','trade_name_ch','note'],
    			listeners : {
    				load : function(store,records){
    					var temp = store.data.items;
    					for(var i=0;i<temp.length;i++){
    						var note = temp[i].data['note'];
    						var tradeNotes = note.split('@@');
    						temp[i].data['note'] = tradeNotes[0];
    					}
    					this.removeAll();
    					store.add(temp);
    				}
    			}
			});
			
			var qfcStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>pfs/pfsMobinterface_queryViewByMobNum',
				root   : 'field1',
				fields:['mobdevice_num','branch','node_id','node_level','node_location','child_node_order','node_name','parent_id',
				        'node_type','dir_name','trade_id','is_show_msg','prompt_msg','node_index'],
    			listeners : {
    				load : function(store,records){
    					treeData = [];
    					for(var i = 0;i<store.data.length;i++){
    						
    						var record = store.getAt(i).data;
    						var tree = {};
    						if(record['node_type']=='2'){
    							var index = tradeStore.find('trade_id',record['trade_id']);
    							var businessRecord = tradeStore.getAt(index).data;
    							tree['node_name'] = businessRecord['trade_name_ch'];
    						}else{
    							tree['node_name'] = record['node_name'];
    						}
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
			
			var MobInfoStore = new Ext.data.JsonStore({
				url    : '<%=basePath%>pfs/pfsMobinterface_queryQMInfoSmall.action',
				autoLoad:true,
				root   : 'field1',
				fields:['mobdevice_num','mobdevice_name']
			});
			

			Ext.onReady(loadPage);
			function loadPage(){
				//取界面长宽
				var clientHeight = document.body.clientHeight;
				var clientWidth  = document.body.clientWidth;
//-------------------------------------------------------------------------填单机列表
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
		                  			
		                  			submitData['mobdevice_num'] = Ext.getCmp('mobNumCombo').getValue();//填单机编号
		                			submitData['node_id'] = activenodedata.node_id;//父节点node_Id
		                			submitData['node_index'] = activenodedata.node_index;//节点索引
		                			submitData['parent_node_id'] = parent.attributes.id=='root'?'':parent.attributes.id;//父节点node_Id
		                			submitData['child_node_order'] = child_node_order;//child_node_order
		                			
									requestAjax('<%=basePath%>pfs/pfsMobinterface_delLeafByViewNo', submitData,function(sRet){
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
					if(Ext.getCmp('mobNumCombo').getValue()==""){
						Ext.MessageBox.alert('消息提示','请先选定填单机');
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
						Ext.Msg.alert('提示','尚未查询到填单机界面数据,请重新选择填单机查询');
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
						
						items = [
							{columnWidth:1,
					  	     height:50,
					  	    labelAlign: 'right',
					  		layout:'form',
					    	items:[{
					    	}]
					  		},{
							columnWidth:1/2,
							labelAlign: 'right',
							layout:'form',
							height:30,
					    	items:[{
					    		xtype:'textfield',
					    		name:'dir_name',
					    		id:'dir_name', 
					    		maxLength:20,
					    		maxLengthText : '长度不能大于20位',
					    		fieldLabel:'目录名称',
					    		emptyText: '请输入目录名称...',
					    		anchor:'98%'
					    	}]
						},{
							columnWidth:1/2,
							labelAlign: 'right',
							layout:'form',
	                    	items:[{
	                    		xtype:'button',
	                    		text:'确定',
	                    		width:70,
	                    		handler:function(){
	                    			
	                    			if(!Ext.getCmp('dir_name').isValid()){
	                    				Ext.MessageBox.alert('<s:text name="common.info.title"/>','目录名称过长,请重新输入')
                        				return;
	                    			}
	                    			if(type == '1'){
	                        			//将信息发送到后台，并返回更新后的树集合
	                        			var submitData = {};
	                        			//lastNodeData activenodedata
	                        			submitData['mobdevice_num'] = Ext.getCmp('mobNumCombo').getValue();//填单机编号
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
	                        			submitData['node_index'] = parseInt(lastNodeData.node_index) + 1;//节点索引
	                        			if(Ext.getCmp('dir_name').getValue().trim()==''){
	                        				Ext.MessageBox.alert('<s:text name="common.info.title"/>','请输入目录名称')
	                        				return;
	                        			}
	                        			requestAjax('<%=basePath%>pfs/pfsMobinterface_addFolder', submitData,function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','添加目录成功',function(id){
												qfcStore.reload();
												Ext.getCmp('fieldset_add_type').removeAll(true);
	    	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
											});
										});
	                     			}else if(type == '2'){//编辑目录名称
	                     				var submitData = {};
	                        			//lastNodeData activenodedata
	                        			submitData['mobdevice_num'] = Ext.getCmp('mobNumCombo').getValue();//填单机编号
	                        			submitData['node_id'] = parentNodeData.node_id;//父节点node_Id
	                        			submitData['dir_name'] = Ext.getCmp('dir_name').getValue();//父节点node_Id
	                        			if(Ext.getCmp('dir_name').getValue().trim()==''){
	                        				Ext.MessageBox.alert('<s:text name="common.info.title"/>','请输入目录名称')
	                        				return;
	                        			}
	                        			requestAjax('<%=basePath%>pfs/pfsMobinterface_editFolder', submitData,function(sRet){
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
						
	                  	Ext.getCmp('fieldset_add_type').removeAll(true);
	                  	Ext.getCmp('fieldset_add_type').add(items);
	                  	
	                  	Ext.getCmp('fieldset_add_type').doLayout();
	                  	if(type == '2'){ Ext.getCmp('dir_name').setValue(parentNode.attributes.text);}
					} else if(type==3||type==4){//添加修改业务
					  	Ext.getCmp('fieldset_add_type').removeAll(true);

					  	items = [
					  		{columnWidth:0.4,
					  	     height:40,
					  	   labelWidth:80,
					  	    labelAlign: 'right',
					  	    layout:'form',
					    	items:[		
					    		{
					    		height:22,
					  	        allowBlank : false,
					      		xtype:'textfield',
					      		name:'menu_name',
					      		id:'menu_name',
					      		fieldLabel:'菜单名称',
					      		emptyText: '请输入菜单名称...',
					      		maxLength:15,
					    		maxLengthText : '长度不能大于15位',
					    		anchor:'100%'
					    	}]
					  		},{columnWidth:1,
					  	     height:1,
					  	    labelAlign: 'right',
					  		layout:'form',
					    	items:[{
					    	}]
					  		},{
						  	columnWidth:0.4,
						  	 height:50,
						  	labelWidth:80,
						  	labelAlign: 'right',
						  	layout:'form',
						  	items:[{
							  	xtype:'combo',
							  	name:'businessCom', 
							  	fieldLabel:'选择业务',
							  	emptyText: '请选择业务...',
							  	anchor:'98%',
							  	editable:false,
							  	disabled: false,
							  	id:'businessCom',
							  	store:tradeStore,
								displayField:'trade_name_ch',
								valueField:'trade_id',
								mode: 'local',
								triggerAction: 'all',
								listeners:{
									'select': function(store,record){

											Ext.getCmp('is_show_msg').setValue(false);
											Ext.getCmp('prompt_msg').setValue('');
										
									}
								}
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
					  		labelWidth:80,
					  		layout:'form',
					    	items:[{
					    		height:170,
					      		xtype:'textarea',
					      		name:'prompt_msg',
					      		id:'prompt_msg',
					      		fieldLabel:'提示信息',
					      		emptyText: '提示信息...',
					      		maxLength:15,
					    		maxLengthText : '长度不能大于15位',
					    		anchor:'100%',
					      		disabled: true
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
												submitData['mobdevice_num'] = Ext.getCmp('mobNumCombo').getValue();//填单机编号
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
												submitData['trade_id'] = Ext.getCmp('businessCom').getValue();//业务编号
												submitData['is_show_msg'] = Ext.getCmp('is_show_msg').getValue();//业务是否显示提示信息
												submitData['menu_name'] = Ext.getCmp('menu_name').getValue();//菜单名称
												if(Ext.getCmp('is_show_msg').getValue()){
													submitData['prompt_msg'] = Ext.getCmp('prompt_msg').getValue();//业务提示信息
												}else{
													submitData['prompt_msg'] = "";//业务提示信息
												}
												if(submitData['prompt_msg'].length>60){
													Ext.MessageBox.alert('提示信息','提示信息文字超长,请修改后提交');
													return;
												}
												if(submitData['menu_name'].length>20){
													Ext.MessageBox.alert('提示信息','菜单名称文字超长,请修改后提交');
													return;
												}
												if(submitData['menu_name'].trim() == ""){
													Ext.MessageBox.alert('提示信息','请输入菜单名称');
													return;
												}
			                        			submitData['node_index'] = parseInt(lastNodeData.node_index) + 1;//节点索引	
			                        			
												requestAjax('<%=basePath%>pfs/pfsMobinterface_addLeaf',submitData,function(sRet){
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
												submitData['mobdevice_num'] = Ext.getCmp('mobNumCombo').getValue();//填单机编号
			                        			if("" == Ext.getCmp('businessCom').getValue()){
			                        				Ext.MessageBox.alert('提示信息','请选择业务');
			                        				return;
			                        			}
												submitData['trade_id'] = Ext.getCmp('businessCom').getValue();//业务编号
												submitData['is_show_msg'] = Ext.getCmp('is_show_msg').getValue();//业务是否显示提示信息
												submitData['menu_name'] = Ext.getCmp('menu_name').getValue();//菜单名称
												if(Ext.getCmp('is_show_msg').getValue()){
													submitData['prompt_msg'] = Ext.getCmp('prompt_msg').getValue();//业务提示信息
												}else{
													submitData['prompt_msg'] = "";//业务提示信息
												}
												if(submitData['prompt_msg'].length>60){
													Ext.MessageBox.alert('提示信息','提示信息文字超长,请修改后提交');
													return;
												}
												if(submitData['menu_name'].length>20){
													Ext.MessageBox.alert('提示信息','菜单名称文字超长,请修改后提交');
													return;
												}
												if(submitData['menu_name'].trim() == ""){
													Ext.MessageBox.alert('提示信息','请输入菜单名称');
													return;
												}
												submitData['node_id'] = parentNodeData.node_id;//父节点node_Id
												
												requestAjax('<%=basePath%>pfs/pfsMobinterface_editLeaf',submitData,function(sRet){
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
					  	Ext.getCmp('fieldset_add_type').add(items);
	                  	Ext.getCmp('fieldset_add_type').doLayout();
	                  	
	 
					  	if(type=='3'){//添加业务
						  	Ext.getCmp('fieldset_add_type').setTitle('添加业务');
						  	getLastIndexChild(parentNode);
		    			}else if(type=='4'){//修改业务
		    				Ext.getCmp('fieldset_add_type').setTitle('编辑业务');
		                  	//点击的是业务,需要进行查询此业务,之后进行修改
					  		var submitData = {};
					  		submitData['mobdevice_num'] = Ext.getCmp('mobNumCombo').getValue();//填单机编号
                			submitData['node_id'] = activenodedata.node_id;//父节点node_Id
					  		requestAjax('<%=basePath%>pfs/pfsMobinterface_queryLeafByViewNo',submitData,function(sRet){
								//这次操作结束后才进行这步操作
								var map = sRet.leafMap;
								Ext.getCmp('businessCom').setValue(map['trade_id']);//业务编号
								Ext.getCmp('is_show_msg').setValue(map['is_show_msg']);//业务是否显示提示信息
								Ext.getCmp('prompt_msg').setValue(map['prompt_msg']);//业务提示信息
								Ext.getCmp('menu_name').setValue(map['menu_name']);//业务菜单提示信息
                       			
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
					autoScroll:true,
					applyTo :'panel',
					items:[{
						layout:'column', 
						frame:true,
						height:clientHeight,
						width:clientWidth,
			    		items:[{
			    			columnWidth:1/4, 
			    			layout:'column', 
			    			bodyStyle:'padding:0 8 0 8', 
			    			labelAlign:'left',
			    		  	items:[{
			    		  		columnWidth:1, 
			    		  		layout:'form', 
			    		  		bodyStyle:'padding:0 8 0 8', 
			    		  		labelAlign:'left',
			    	   			items:[{
			    	   				width: (clientWidth*1/4)-27,
			    	   				height:70,
			    	   				xtype:'fieldset',
			    	   				layout:'column',
			    	   				title:'选择填单机',
			    	   				items:[{
			    	   					columnWidth:1,
			    	   					labelAlign: 'right',
			    	   					labelWidth:45,
			    	   					layout:'form',
			    	   					items:[{
			    	   						xtype:'combo',
			    	   						name:'mobNumCombo', 
			    	   						fieldLabel:'填单机',
				    	   					emptyText: '请选择填单机...',
				    	   					anchor:'100%',
				    	   					store:MobInfoStore,
				    	   					displayField:'mobdevice_name',
				    	   					valueField:'mobdevice_num',
				    	   					mode: 'local',
				    	   					triggerAction: 'all',
				    	   					editable:false,
				    	   					id:'mobNumCombo',
				    	   					listeners:{
												'select': function(){
													var combo = Ext.getCmp('mobNumCombo');
													var submit = {};
								                	submit['mobdevice_num'] = combo.getValue();//TODO 这里修改!!!!!!回调函数
								                	Ext.getCmp('fieldset_add_type').removeAll(true);
		    	   						    		Ext.getCmp('fieldset_add_type').setTitle(titles);
		    	   						    		queryFlag = false;
								                	qfcStore.load({params:submit});
												}
											}
			    	   					}]
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
			    	   				title:'填单机界面设置',
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
			}
		</script>
	</head>

	<body>
		<div id="panel"></div>
	</body>
</html>
