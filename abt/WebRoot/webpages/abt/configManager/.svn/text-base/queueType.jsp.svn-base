<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String brno = user.getUnitid();
String bankLevel = user.getUnit().getBank_level();
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>队列配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		var query_rules;
		var systemUnits=pagereturn.field2;
		var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};
 		//构建机构树a
		var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree_a = treeGenerator_a.generate(false,false,false,false);
		var treepanel_a = new Ext.tree.TreePanel({
			height:220,
			width:200,
			bbar:[new Ext.form.TextField({
		        width:180,
		        emptyText:'快速检索',
		        enableKeyEvents: true,
			    listeners:{
					render: function(f){
		                this.filter = new QM.ux.TreeFilter(treepanel_a,{
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
			root:tree_a
		});
/**
 * @Title: loadPage 
 * @Description: 显示队列类型配置界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '队列配置', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,		fieldLabel:'机构号',passName: 'branch', tree:treepanel_a, width:200,value:<%="'" + user.getUnit().getUnitname() + "'"%>}} 
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
				'<%=basePath%>/confManager/queuetype_queryQueuePage',
				['branch','queuetype_id','queuetype_name','queuetype_prefix','queuetype_prefix_num','queuetype_level','queuetype_trans','status'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'队列编号',dataIndex:'queuetype_id',width:150},
				{header:'队列名称',dataIndex:'queuetype_name',width:150},
				{header:'队列前缀字母',dataIndex:'queuetype_prefix',width:100},
				{header:'队列前缀数字',dataIndex:'queuetype_prefix_num',width:100},
				{header:'队列优先级',dataIndex:'queuetype_level',width:100},
				{header:'队列调整启用状态',dataIndex:'queuetype_trans',width:120,renderer:function(value){return value=='1'?'1-启用':'0-未启用'}},
				{header:'队列状态',dataIndex:'status',width:100,renderer:function(value){return value=='1'?'1-启用':'0-未启用'}}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加队列', 542, 230, 140, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'queuetype_name',id:'queuetype_name_add', 	fieldLabel:'队列名称',	allowBlank : false,blankText:'请输入队列名称',maxLength : 50,maxLengthText : '长度不能大于50位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'queuetype_level',id:'queuetype_level_add', 	fieldLabel:'队列优先级',	allowBlank : true,blankText:'请输入优先级',regex:/^[0-9]*$/,regexText:'只能输入数字',maxLength : 5,maxLengthText : '长度不能大于5位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'queuetype_prefix',id:'queuetype_prefix_add', 	fieldLabel:'队列前缀字母',	allowBlank : true,blankText:'请输入队列前缀字母',maxLength : 1,maxLengthText : '长度不能大于1位',regex:/^[a-zA-Z]*$/,regexText:'只能输入英文'}},
				{colIndex:0, field:{xtype : 'textfield', name:'queuetype_prefix_num',id:'queuetype_prefix_num_add', 	fieldLabel:'队列前缀数字',	allowBlank : true,blankText:'请输入队列前缀数字',regex:/^[0-9]*$/,regexText:'只能输入数字',maxLength : 3,maxLengthText : '长度不能大于3位'}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'queuetype_trans',id:'queuetype_trans_add', 	boxLabel:'启用队列调整',checked:true}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'status',id:'status_add', 	boxLabel:'启用',checked:true}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : onSearchDialog},
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',90
			);
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑队列', 550, 230, 140, 2,
			   [
				{colIndex:0, field:{xtype : 'hidden', name:'queuetype_id', 	fieldLabel:'队列编号'}},
				{colIndex:0, field:{xtype : 'hidden', name:'branch', 	fieldLabel:'机构号'}},
				{colIndex:0, field:{xtype : 'textfield', name:'queuetype_name',id:'queuetype_name_edit', 	fieldLabel:'队列名称',	allowBlank : false,blankText:'请输入队列名称',maxLength : 50,maxLengthText : '长度不能大于50位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'queuetype_level',id:'queuetype_level_edit', 	fieldLabel:'队列优先级',	allowBlank : true,blankText:'请输入优先级',regex:/^[0-9]*$/,regexText:'只能输入数字',maxLength : 5,maxLengthText : '长度不能大于5位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'queuetype_prefix',id:'queuetype_prefix_edit', 	fieldLabel:'队列前缀字母',	allowBlank : true,blankText:'请输入队列前缀字母',maxLength : 1,maxLengthText : '长度不能大于1位',regex:/^[a-zA-Z]*$/,regexText:'只能输入英文'}},
				{colIndex:0, field:{xtype : 'textfield', name:'queuetype_prefix_num',id:'queuetype_prefix_num_edit', 	fieldLabel:'队列前缀数字',	allowBlank : true,blankText:'请输入队列前缀数字',regex:/^[0-9]*$/,regexText:'只能输入数字',maxLength : 3,maxLengthText : '长度不能大于3位'}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'queuetype_trans',id:'queuetype_trans_edit', 	boxLabel:'启用队列调整',checked:true}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'status',id:'status_edit', 	boxLabel:'启用'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : onSearchDialog},
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',90
			);
			/**
			 * @Title:onSearchDialog
			 * @Description:点击获取上级数据时的操作
			 */
			function onSearchDialog(){
				 var detailStore = new Ext.data.JsonStore({
				    	fields:['branch','queuetype_id','queuetype_name','queuetype_prefix','queuetype_prefix_num','queuetype_level','status']
				    });
				    var detailData = [
						{header:'机构号',dataIndex:'branch',width:100},
						{header:'队列编号',dataIndex:'queuetype_id',width:100},
						{header:'队列名称',dataIndex:'queuetype_name',width:100},
						{header:'队列前缀字母',dataIndex:'queuetype_prefix',width:100},
						{header:'队列前缀数字',dataIndex:'queuetype_prefix_num',width:100},
						{header:'队列优先级',dataIndex:'queuetype_level',width:100},
						{header:'队列调整启用状态',dataIndex:'queuetype_trans',width:120,renderer:function(value){return value=='1'?'1-启用':'0-未启用'}},
						{header:'队列状态',dataIndex:'status',width:100,renderer:function(value){return value=='1'?'1-启用':'0-未启用'}}
					];
				    var detailColModel=new Ext.grid.ColumnModel(detailData);
				    var detailsWindow = new SelfFormWindowSetWidth('queuetypeDetailsWindow','上级队列列表',600, 355, 555, 1, [555],[{
			    		colIndex:0,
			    		field:{
			    			xtype:'fieldset',
			    			title:'队列信息',
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
		    			Ext.getCmp('queuetype_name_add').setValue(record.data['queuetype_name']);
		    			Ext.getCmp('queuetype_level_add').setValue(record.data['queuetype_level']);
		    			Ext.getCmp('queuetype_prefix_add').setValue(record.data['queuetype_prefix']);
		    			Ext.getCmp('queuetype_prefix_num_add').setValue(record.data['queuetype_prefix_num']);
		    			Ext.getCmp('status_add').setValue(record.data['status']);
		    			Ext.getCmp('queuetype_name_edit').setValue(record.data['queuetype_name']);
		    			Ext.getCmp('queuetype_level_edit').setValue(record.data['queuetype_level']);
		    			Ext.getCmp('queuetype_prefix_edit').setValue(record.data['queuetype_prefix']);
		    			Ext.getCmp('queuetype_prefix_num_edit').setValue(record.data['queuetype_prefix_num']);
		    			Ext.getCmp('status_edit').setValue(record.data['status']);
		    			Ext.getCmp('queuetype_trans_add').setValue(record.data['queuetype_trans']);
		    			Ext.getCmp('queuetype_trans_edit').setValue(record.data['queuetype_trans']);
		    		});
				    
				    detailsWindow.open();
				    var submitDetail= {};
				    submitDetail['query_rules'] = '5';
				    requestAjax('<%=basePath%>/confManager/queuetype_queryQueue',submitDetail, function(sRet){
					      detailStore.loadData(sRet.field1);
					});
			};
			 /**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"，"重置"，"添加"，"修改"，"删除"的选择语句
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						var branch = query_obj['branch'];
						//这里判断查询方式:总行查6全部 ,默认本行的话查4本行及上级所有,指定的是本行范围内的其他行则是2-查询指定机构
						if(<%="'"+bankLevel+"'"%>=='1'){//此分支代表着登陆用户是总行用户
							if(branch!=<%="'"+brno+"'"%>){//此分支代表着是查询指定机构
								query_rules = '2';
							}else{
								query_rules = '6';
							}
						}else {//此分支代表着登陆用户不是总行用户
							if(branch!=<%="'"+brno+"'"%>){//此分支代表着是查询指定机构
								query_rules = '2';
							}else{
								query_rules = '4';
							}
						}
						query_obj['query_rules'] = query_rules;
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
						for(var i=0;i<records.length;i++){
					    	//机构数据判断,总行可配全部,其他行不可修改非本行机构
					    	if((records[i].data.branch!=<%="'"+brno+"'"%>)&&(<%="'"+bankLevel+"'"%>!='0')){
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
						var records = pagequeryObj.getSelectedRecords();
					    var submitdata = pagequeryObj.getSelectedObjects(['queuetype_id','branch']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						//无法删除非本行机构
					    for(var i=0;i<submitdata.length;i++){
					    	if((submitdata[i].branch!=<%="'"+brno+"'"%>)&&(<%="'"+bankLevel+"'"%>!='0')){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
					    		return;
					    	}
					    }
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/queuetype_delQueue',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										var branch = query_obj['branch'];
										//这里判断查询方式:总行查6全部 ,默认本行的话查4本行及上级所有,指定的是本行范围内的其他行则是2-查询指定机构
										if(<%="'"+bankLevel+"'"%>=='1'){//此分支代表着登陆用户是总行用户
											if(branch!=<%="'"+brno+"'"%>){//此分支代表着是查询指定机构
												query_rules = '2';
											}else{
												query_rules = '6';
											}
										}else {//此分支代表着登陆用户不是总行用户
											if(branch!=<%="'"+brno+"'"%>){//此分支代表着是查询指定机构
												query_rules = '2';
											}else{
												query_rules = '4';
											}
										}
										query_obj['query_rules'] = query_rules;
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
				submitData['queuetype_name'] = submitData['queuetype_name'].trim();
				createCheckBoxValue('queuetype_trans',submitData);
				createCheckBoxValue('status',submitData);
				submitData['branch'] = Ext.getCmp('unitname').getPassValue();
				if(submitData['queuetype_name']==''){
					Ext.MessageBox.alert('系统提示','请输入正确的队列名称');
					return;
				}
				requestAjax('<%=basePath%>confManager/queuetype_addQueue', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						var branch = query_obj['branch'];
						//这里判断查询方式:总行查6全部 ,默认本行的话查4本行及上级所有,指定的是本行范围内的其他行则是2-查询指定机构
						if(<%="'"+bankLevel+"'"%>=='1'){//此分支代表着登陆用户是总行用户
							if(branch!=<%="'"+brno+"'"%>){//此分支代表着是查询指定机构
								query_rules = '2';
							}else{
								query_rules = '6';
							}
						}else {//此分支代表着登陆用户不是总行用户
							if(branch!=<%="'"+brno+"'"%>){//此分支代表着是查询指定机构
								query_rules = '2';
							}else{
								query_rules = '4';
							}
						}
						query_obj['query_rules'] = query_rules;
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
				submitData['queuetype_name'] = submitData['queuetype_name'].trim();
				if(submitData['queuetype_name']==''){
					Ext.MessageBox.alert('系统提示','请输入正确的队列名称');
					return;
				}
				createCheckBoxValue('queuetype_trans',submitData);
				createCheckBoxValue('status',submitData);
				requestAjax('<%=basePath%>confManager/queuetype_editQueue',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						var branch = query_obj['branch'];
						//这里判断查询方式:总行查6全部 ,默认本行的话查4本行及上级所有,指定的是本行范围内的其他行则是2-查询指定机构
						if(<%="'"+bankLevel+"'"%>=='1'){//此分支代表着登陆用户是总行用户
							if(branch!=<%="'"+brno+"'"%>){//此分支代表着是查询指定机构
								query_rules = '2';
							}else{
								query_rules = '6';
							}
						}else {//此分支代表着登陆用户不是总行用户
							if(branch!=<%="'"+brno+"'"%>){//此分支代表着是查询指定机构
								query_rules = '2';
							}else{
								query_rules = '4';
							}
						}
						query_obj['query_rules'] = query_rules;
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
      		Ext.getCmp('unitname').setPassValue(<%="'"+user.getUnitid()+"'"%>);
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="queuetypeDetailsWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>
