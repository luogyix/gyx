<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%@ page import="com.agree.framework.web.form.administration.User"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>周边网点配置</title>
    
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
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator2.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
 		var systemUnits=pagereturn.field2;
 		var systemAllUnits=pagereturn.field3;
 		var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};
 		//构建机构树-查询条件
		var treeGenerator_sel = new SelfTreeGenerator(systemUnits,jsonMeta,'',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree_sel = treeGenerator_sel.generate(false,false,false,false);
		var treepanel_sel = new Ext.tree.TreePanel({
			height:220,
			width:200,
			bbar:[new Ext.form.TextField({
		        width:180,
		        emptyText:'快速检索',
		        enableKeyEvents: true,
			    listeners:{
					render: function(f){
		                this.filter = new QM.ux.TreeFilter(treepanel_sel,{
							clearAction : 'expand'
						});//初始化TreeFilter 
					},
		            keyup: {//添加键盘点击监听
		                fn:function(t,e){
		                  t.filter.filter(t.getValue());
		                },
		                buffer: 350
		            }
				}
		    }),{xtype:'button',text:''}],
			rootVisible : true, 
			root:tree_sel
		});
 		//构建机构树b
		var tree_b = treeGenerator_sel.generate(false,false,false,false);
		var treepanel_b = new Ext.tree.TreePanel({
			height:220,
			width:200,
			bbar:[new Ext.form.TextField({
		        width:180,
		        emptyText:'快速检索',
		        enableKeyEvents: true,
			    listeners:{
					render: function(f){
		                this.filter = new QM.ux.TreeFilter(treepanel_b,{
							clearAction : 'expand'
						});//初始化TreeFilter 
					},
		            keyup: {//添加键盘点击监听
		                fn:function(t,e){
		                  t.filter.filter(t.getValue());
		                },
		                buffer: 350
		            }
				}
		    }),{xtype:'button',text:''}],
			rootVisible : true, 
			root:tree_b
		});
		var treeGenerator = new SelfTreeGenerator(systemAllUnits,jsonMeta,'',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree = treeGenerator.generate(false,false,true,false);
		var tree2 = treeGenerator.generate(false,false,true,false);
		//构建机构树c
		var tree_c = treeGenerator_sel.generate(false,false,false,false);
		var treepanel_c = new Ext.tree.TreePanel({
			width:200,
			rootVisible : true, 
			height:220,
			root:tree_c,
			bbar:[new Ext.form.TextField({
				width:180,
		        emptyText:'快速检索',
		        enableKeyEvents: true,
			    listeners:{
					render: function(f){
		                this.filter = new QM.ux.TreeFilter(treepanel_c,{
							clearAction : 'expand'
						});//初始化TreeFilter 
					},
		            keyup: {//添加键盘点击监听
		                fn:function(t,e){
		                  t.filter.filter(t.getValue());
		                },
		                buffer: 350
		            }
				}
		    }),{xtype:'button',text:''}]
		});
		var assignedBranchStore = new Ext.data.SimpleStore({
			fields : ['unitid','unitname']
		});
		var rsM1 = new Ext.grid.RowSelectionModel({singleSelect: true});
		var columnModel1 = new Ext.grid.ColumnModel([
			{header:'机构号',dataIndex:'unitid',width:60},
			{header:'机构名',dataIndex:'unitname',width:150},
			{
                xtype: 'actioncolumn',
                width: 30,
                items: [
					{
						icon:'<%=basePath%>images/12.png',
						tooltip: '删除',
						handler: function(grid, rowIndex, colIndex) {
	                        //var rec = assignedBranchStore.getAt(rowIndex);
	                        assignedBranchStore.removeAt(rowIndex);
	                    }
					}
                ]
            }
		]);
		
		var rsM2 = new Ext.grid.RowSelectionModel({singleSelect: true});
		var columnModel2 = new Ext.grid.ColumnModel([
			{header:'机构号',dataIndex:'unitid',width:60},
			{header:'机构名',dataIndex:'unitname',width:150},
			{
                xtype: 'actioncolumn',
                width: 30,
                items: [
					{
						icon:'<%=basePath%>images/12.png',
						tooltip: '删除',
						handler: function(grid, rowIndex, colIndex) {
	                        //var rec = assignedBranchStore.getAt(rowIndex);
	                        assignedBranchStore.removeAt(rowIndex);
	                    }
					}
                ]
            }
		]);
		
		var UnitRecord = Ext.data.Record.create(['unitid','unitname']);
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		/**
		 * @Title: loadPage 
		 * @Description: 显示业务配置界面
		 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '周边网点配置', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,		fieldLabel:'机构号',passName: 'branch', tree:treepanel_sel, width:202, value:<%="'" + user.getUnit().getUnitname() + "'"%>}} 
				],
				[
				{iconCls: "x-image-query", 			            text:'<s:text name="common.button.query"/>'},		//查询
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
				'<%=basePath%>confManager/aroundBranch_queryAroundBranch',
				['branch','branch_neighbous','note','branch_temp'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',     dataIndex:'branch',width:100},
				{header:'机构名',     dataIndex:'branch',width:150,renderer:function(value,b,record){
					value = value.toString();
					for(var i=0;i<systemAllUnits.length;i++){
						var branch = systemAllUnits[i];
						if(branch['unitid'] == value){
							return branch['unitname'];
						}
					}
					return '';
				}},
				{header:'周边网点号',     dataIndex:'branch_neighbous',width:200},
				{header:'周边网点名称',dataIndex:'branch_neighbous',width:400,renderer:function(value,b,record){
					value = value.toString();
					var brnos = value.split('|');
					var fin = '';
					for(var j=0;j<brnos.length;j++){
						for(var i=0;i<systemAllUnits.length;i++){
							var branch = systemAllUnits[i];
							if(branch['unitid'] == brnos[j]){
								fin += '  |  ' + branch['unitname'];
								break;
							}
						}
					}
					return fin.substr(5);
				}},
				{header:'备注',dataIndex:'note',width:250}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加周边网点信息', 600, 400, 200, 2,
				[
				{colIndex:0, field:{xtype:'grid', title:'周边网点信息', width:270, height:180, cm:columnModel1, sm:rsM1, store:assignedBranchStore, stripeRows:true,listeners:{
					rowdblclick:function(grid, rowindex, e){
		    			var record = grid.getStore().getAt(rowindex);
		    			grid.getStore().remove(record);
		    		}
				}}},
				//{colIndex:1, field:{xtype : 'combotree', allowBlank : true, fieldLabel:'周边网点机构号', tree:treepanel_c}},
				{colIndex:1, field:{xtype : 'treepanel',root:tree,title:'机构信息',autoScroll:true, rootVisible:true, height:300, width:270,listeners:{
					click:function(node){
						var index = assignedBranchStore.find('unitid',node.attributes.id);
						if(index==-1){
							var record = new UnitRecord({
						    	unitid:node.attributes.id,
						    	unitname:node.attributes.text
						    });
							var records = [record];
							assignedBranchStore.add(records);
						}
					}
				}}},
				{colIndex:0, field:{xtype : 'textarea', name:'note', fieldLabel:'备注', width:270, maxLength : 300,	 maxLengthText : '长度不能大于300位'}},
				{colIndex:0, field:{xtype : 'combotree', name:'branch',id:'branch_add', allowBlank : false,	fieldLabel:'所属机构', passName: 'branch', tree:treepanel_b}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			//当选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '修改周边网点信息', 600, 400, 200, 2,[
				{colIndex:0, field:{xtype:'grid', title:'周边网点信息', width:270, height:180, cm:columnModel2, sm:rsM2, store:assignedBranchStore, stripeRows:true,listeners:{
					rowdblclick:function(grid, rowindex, e){
		    			var record = grid.getStore().getAt(rowindex);
		    			grid.getStore().remove(record);
		    		}
				}}},
				{colIndex:1, field:{xtype : 'treepanel',root:tree2,title:'机构信息',autoScroll:true, rootVisible:true, height:300, width:270,listeners:{
					click:function(node){
						var index = assignedBranchStore.find('unitid',node.attributes.id);
						if(index==-1){
							var record = new UnitRecord({
						    	unitid:node.attributes.id,
						    	unitname:node.attributes.text
						    });
							var records = [record];
							assignedBranchStore.add(records);
						}
					}
				}}},
				{colIndex:0, field:{xtype : 'textarea', name:'note',id:'note_edit', fieldLabel:'备注', width:270, maxLength : 300,	 maxLengthText : '长度不能大于300位'}},
				{colIndex:0, field:{xtype : 'combotree', name:'branch',id:'branch_edit', allowBlank : false,	fieldLabel:'所属机构', passName: 'branch', tree:treepanel_c,readOnly:true}}
			],
			[
				{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
				{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close();}}
			]);
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
						assignedBranchStore.removeAll();
						addwindow.open();
						break;
					case 2:
						var records = pagequeryObj.getSelectedRecords();
						if(records === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
						}else if(records.length !== 1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
						}else{
							assignedBranchStore.removeAll();
							editwindow.open();
							//editwindow.updateFields(records[0]);
							//数据处理
							for(var i=0;i<systemAllUnits.length;i++){
								var branch = systemAllUnits[i];
								if(branch['unitid'] == records[0].data.branch){
									Ext.getCmp('branch_edit').setValue(branch['unitname']);
									Ext.getCmp('branch_edit').setPassValue(branch['unitid']);
									break;
								}
							}
							Ext.getCmp('note_edit').setValue(records[0].data.note);
							var data = records[0].data.branch_neighbous;
							var datas = data.split('|');
							var array = [];
							for ( var int = 0; int < datas.length; int++) {
								for(var i=0;i<systemAllUnits.length;i++){
									var branch = systemAllUnits[i];
									if(branch['unitid'] == datas[int]){
										var record = new UnitRecord({
									    	unitid:branch['unitid'],
									    	unitname:branch['unitname']
									    });
										array.push(record);
										break;
									}
								}
								
							}
							assignedBranchStore.add(array);
						}
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['branch']);
					    //请选择机构
					    if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
					    
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/aroundBranch_delAroundBranchById',submitdata,function(sRet){
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
				var num = assignedBranchStore.getCount();
				var str = '';
				if(num>0){
					for(var i=0;i<num;i++){
						if(str == ''){
							str = assignedBranchStore.getAt(i).get('unitid');
						}else{
							str += '|' + assignedBranchStore.getAt(i).get('unitid');
						}
					}
				}
				submitData['branch_neighbous'] = str;
				requestAjax('<%=basePath%>confManager/aroundBranch_addAroundBranch', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
			}
            /**
			* @Title:oneditclicked
			* @Description:修改数据函数
			*/
			function oneditclicked(){
				var submitData = editwindow.getFields();
				var num = assignedBranchStore.getCount();
				var str = '';
				if(num>0){
					for(var i=0;i<num;i++){
						if(str == ''){
							str = assignedBranchStore.getAt(i).get('unitid');
						}else{
							str += '|' + assignedBranchStore.getAt(i).get('unitid');
						}
					}
				}
				submitData['branch_neighbous'] = str;
				requestAjax('<%=basePath%>confManager/aroundBranch_editAroundBranch', submitData,function(sRet){
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
					items : [
						conditionPanel.toolbarwindow,
						pagequeryObj.pagingGrid
					]
				});
			}
      		buildLayout();
      		Ext.getCmp('unitname').setPassValue(<%="'"+user.getUnitid()+"'"%>);
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