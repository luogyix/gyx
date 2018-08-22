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

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>    
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>业务配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
	    var query_rules;
		var bsTypeStore=new Ext.data.SimpleStore({ 
				data:[["0-个人业务","0"],["1-对公业务","1"]],
				fields : ['key','value']
		});
		var stateStore=new Ext.data.SimpleStore({ 
				data:[["1-可用","1"],["0-不可用","0"]],
				fields : ['key','value']
		});
		
		var cardFlagStore=new Ext.data.SimpleStore({ 
				data:[["0-不刷","0"],["1-可刷可不刷","1"],["2-必刷","2"]],
				fields : ['key','value']
		});
		var branchLevelStore=new Ext.data.SimpleStore({ 
				data:[["0-总行虚拟机构","0"],["1-总行","1"],["2-分行","2"],["3-支行","3"],["4-网点","4"]],
				fields : ['key','value']
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示业务配置界面
 */
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
		
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '业务配置', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,		fieldLabel:'机构号',passName: 'branch', tree:treepanel_a, width:202, value:<%="'" + user.getUnit().getUnitname() + "'"%>}} 
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
				'<%=basePath%>confManager/business_queryBusinessPage',
				['bs_id','branch','bs_name_ch','bs_name_en','bs_type','branch_level','card_flag','status','bs_signstatus','note','bs_prefix'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'业务编号',     dataIndex:'bs_id',width:150},
				{header:'机构号',     dataIndex:'branch',width:150},
				{header:'业务名称(中文)',dataIndex:'bs_name_ch',width:150},
				{header:'业务名称(英文)',dataIndex:'bs_name_en',width:150},
				//{header:'业务前缀',dataIndex:'bs_prefix',width:150},
				{header:'所属业务类别',dataIndex:'bs_type',width:150,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,bsTypeStore,'value','key');//翻译字段方法
       			 }},
				{header:'机构类型',dataIndex:'branch_level',width:150,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,branchLevelStore,'value','key');//翻译字段方法
       			 }},
				{header:'刷卡标志',dataIndex:'card_flag',width:150,hidden:true,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,cardFlagStore,'value','key');//翻译字段方法
       			 }},
				{header:'业务状态',dataIndex:'status',width:150,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,stateStore,'value','key');//翻译字段方法
       			 }},
				{header:'业务签到状态',dataIndex:'bs_signstatus',width:150,hidden:true,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,stateStore,'value','key');//翻译字段方法
       			 }},
				{header:'小票提示',dataIndex:'note',width:590}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加业务信息', 600, 350, 150, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'bs_name_ch',id:'bs_name_ch_add', 	 fieldLabel:'业务名称(中文)',allowBlank : false,regex:/^[\u4E00-\u9FA5]/,regexText:'只能输入汉字',maxLength : 60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'bs_name_en',id:'bs_name_en_add', 	 fieldLabel:'业务名称(英文)',allowBlank : true,maxLength : 60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'bs_prefix',id:'bs_prefix_add', 	 fieldLabel:'业务前缀',hidden:true}},
				{colIndex:1, field:{xtype : 'combo',     name:'branch_level',id:'branch_level_add',    fieldLabel:'机构类型',value:<%="'"+bankLevel+"'"%>,editable:false,	readOnly:true,    allowBlank : false,hiddenName:'branch_level',store:branchLevelStore,displayField:"key",valueField:"value"}},
				{colIndex:1, field:{xtype : 'combo',     name:'bs_type',id:'bs_type_add', 	 fieldLabel:'所属业务类别',editable:false,value:0,    allowBlank : false,hiddenName:'bs_type',store:bsTypeStore,displayField:"key",valueField:"value"}},
				{colIndex:1, field:{xtype : 'combo',     name:'card_flag',id:'card_flag_add',    fieldLabel:'刷卡标志',	value:'1',editable:false,    allowBlank : false,hiddenName:'card_flag',store:cardFlagStore,displayField:"key",valueField:"value"}},
				{colIndex:1, field:{xtype : 'checkbox',  name:'status',id:'status_add',hideLabel:true, 	boxLabel:'启用',checked:true}},
				{colIndex:0, field:{xtype : 'textarea',  name:'note',id:'note_add', 		 fieldLabel:'小票提示',maxLength : 100,	maxLengthText : '长度不能大于100位'}},
				{colIndex:0, field:{width:250,html:'<font style="color:#red">小票提示信息配置格式:@@小票提示</font>'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : details},
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑业务信息', 600, 450, 150, 2,
				[
					{colIndex:0, field:{xtype : 'textfield', name:'bs_id', 	 fieldLabel:'业务编号',allowBlank : false,readOnly:true}},
					{colIndex:1, field:{xtype : 'textfield', name:'branch', 	 fieldLabel:'机构号',allowBlank : false,readOnly:true}},
					{colIndex:0, field:{xtype : 'textfield',     name:'bs_signstatus',    fieldLabel:'业务签到状态',	readOnly:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'bs_name_ch',id:'bs_name_ch_edit', 	 fieldLabel:'业务名称(中文)',allowBlank : false,regex:/^[\u4E00-\u9FA5]/,regexText:'只能输入汉字开头',maxLength : 60,	maxLengthText : '长度不能大于60位'}},
					{colIndex:0, field:{xtype : 'textfield', name:'bs_name_en', id:'bs_name_en_edit', 	 fieldLabel:'业务名称(英文)',allowBlank : true,maxLength : 60,	maxLengthText : '长度不能大于60位'}},
					{colIndex:0, field:{xtype : 'textfield', name:'bs_prefix',id:'bs_prefix_edit', 	 fieldLabel:'业务前缀',hidden:true}},
					{colIndex:1, field:{xtype : 'combo',     name:'branch_level',id:'branch_level_edit',    fieldLabel:'机构类型',editable:false,readOnly:true,	    allowBlank : false,hiddenName:'branch_level',store:branchLevelStore,displayField:"key",valueField:"value"}},
					{colIndex:1, field:{xtype : 'combo',     name:'bs_type',id:'bs_type_edit', 	 fieldLabel:'所属业务类别',editable:false,    allowBlank : false,hiddenName:'bs_type',store:bsTypeStore,displayField:"key",valueField:"value"}},
					{colIndex:1, field:{xtype : 'combo',     name:'card_flag', id:'card_flag_edit',    fieldLabel:'刷卡标志',	editable:false,    allowBlank : false,hiddenName:'card_flag',store:cardFlagStore,displayField:"key",valueField:"value"}},
					{colIndex:1, field:{xtype : 'checkbox',  name:'status',id:'status_edit',hideLabel:true, 	boxLabel:'启用'}},
					{colIndex:0, field:{xtype : 'textarea',  name:'note', id:'note_edit', 		 fieldLabel:'小票提示',maxLength : 100,	maxLengthText : '长度不能大于100位'}},
					{colIndex:0, field:{width:250,html:'<font style="color:#red">小票提示信息配置格式:@@小票提示</font>'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : details},
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				]
			);
			/**
			 * 弹出窗
			 */
			function details(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['bs_id','bs_prefix','branch','bs_name_ch','bs_name_en','bs_type','branch_level','card_flag','status','bs_signstatus','note']
			    });
			    var detailData = [
      				{header:'业务编号',     dataIndex:'bs_id',width:150},
					{header:'机构号',     dataIndex:'branch',width:150},
					{header:'业务名称(中文)',dataIndex:'bs_name_ch',width:150},
					{header:'业务名称(英文)',dataIndex:'bs_name_en',width:150},
	       			{header:'业务前缀',dataIndex:'bs_prefix',width:150,hidden:true},
					{header:'所属业务类别',dataIndex:'bs_type',width:150,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,bsTypeStore,'value','key');//翻译字段方法
	       			 }},
					{header:'机构类型',dataIndex:'branch_level',width:150,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,branchLevelStore,'value','key');//翻译字段方法
	       			 }},
					{header:'刷卡标志',dataIndex:'card_flag',width:150,hidden:true,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,cardFlagStore,'value','key');//翻译字段方法
	       			 }},
					{header:'业务状态',dataIndex:'status',width:150,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,stateStore,'value','key');//翻译字段方法
	       			 }},
					{header:'业务签到状态',dataIndex:'bs_signstatus',width:150,hidden:true,renderer:function(value){
						value = value.toString();
	          		 	return getDictValue(value,stateStore,'value','key');//翻译字段方法
	       			 }},
					{header:'小票提示',dataIndex:'note',width:150}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('detailsWindow','上级机构业务配置列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'业务列表',
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
			  			Ext.getCmp('bs_name_ch_edit').setValue(record.data['bs_name_ch']);
			  			Ext.getCmp('bs_name_en_edit').setValue(record.data['bs_name_en']);
			  			Ext.getCmp('bs_type_edit').setValue(record.data['bs_type']);
			  			//Ext.getCmp('branch_level_edit').setValue(record.data['branch_level']);
			  			Ext.getCmp('card_flag_edit').setValue(record.data['card_flag']);
			  			Ext.getCmp('status_edit').setValue(record.data['status']);
			  			Ext.getCmp('note_edit').setValue(record.data['note']);
			  			Ext.getCmp('bs_name_ch_add').setValue(record.data['bs_name_ch']);
			  			Ext.getCmp('bs_name_en_add').setValue(record.data['bs_name_en']);
			  			Ext.getCmp('bs_type_add').setValue(record.data['bs_type']);
			  			//Ext.getCmp('branch_level_add').setValue(record.data['branch_level']);
			  			Ext.getCmp('card_flag_add').setValue(record.data['card_flag']);
			  			Ext.getCmp('status_add').setValue(record.data['status']);
			  			Ext.getCmp('note_add').setValue(record.data['note']);
			  			Ext.getCmp('bs_prefix_add').setValue(record.data['bs_prefix']);
			  			Ext.getCmp('bs_prefix_edit').setValue(record.data['bs_prefix']);
			  		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '5';
			    requestAjax('<%=basePath%>/confManager/business_queryBusiness',submitDetail, function(sRet){
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
					    var submitdata = pagequeryObj.getSelectedObjects(['bs_id','branch']);
					    //请选择机构
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
								requestAjax('<%=basePath%>confManager/business_delBusiness',submitdata,function(sRet){
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
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				createCheckBoxValue('status',submitData);
				requestAjax('<%=basePath%>confManager/business_editBusiness',submitData,function(sRet){
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
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				createCheckBoxValue('status',submitData);
				submitData['branch'] = Ext.getCmp('unitname').getPassValue();
				requestAjax('<%=basePath%>confManager/business_addBusiness', submitData,
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
	<div id="editWindow"></div>
	<div id="detailsWindow"></div>
  </body>
</html>
