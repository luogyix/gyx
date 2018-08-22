<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String brno = user.getUnitid();
%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>    
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>预填单业务配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		
		//业务类型
		var businessStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>confManager/business_queryBusinessSmall?query_rules=4',
			autoLoad:true,
			root   : 'field1',
			fields:['bs_id','bs_name_ch','card_flag'],
			listeners : {
				load : function(store,records){
					var temp = store.data.items;
					/* for(var i=0;i<temp.length;i++){
						var note = temp[i].data['note'];
						var bsNotes = note.split('@@');
						temp[i].data['note'] = bsNotes[0];
					} */
					this.removeAll();
					store.add(temp);
				}
			}
		});
		
		
		
	   
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
				data:[["1-总行","1"],["2-分行","2"],["3-支行","3"],["4-网点","4"]],
				fields : ['key','value']
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		
 		var systemUnits=pagereturn.field2;
 		var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};
 		//构建机构树a
		var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree_a = treeGenerator_a.generate(false,false,false,false);
		var treepanel_a = new Ext.tree.TreePanel({
		  	rootVisible : true,
		  	height:220,
		  	width:200,
		  	root:tree_a
		});
		//构建机构树b
		var treeGenerator_b = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var tree_b = treeGenerator_b.generate(false,false,false,false);
		var treepanel_b = new Ext.tree.TreePanel({
			height:220,
			width:200,
			rootVisible : true, 
			root:tree_b
		});
		
		/**
		 * @Title: loadPage 
		 * @Description: 显示业务配置界面
		 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '业务配置', 120, 0,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitnameid',  allowUnLeafClick:true,		fieldLabel:'机构号',passName: 'unitid', tree:treepanel_a, width:200,value:<%="'" + user.getUnit().getUnitname() + "'"%>}} 
				],
				[
				{iconCls: "x-image-query", 			            text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-application_form_edit", 		text:'<s:text name="common.button.editrecord"/>'}	//修改
				],
				onButtonClicked
			);
			conditionPanel.open();
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>pfs/pfsTrade_queryTradePage',
				['trade_id','branch','trade_name_ch','trade_name_en','trade_type','branch_level','status','note'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'业务编号',     dataIndex:'trade_id',width:150},
				{header:'机构号',     dataIndex:'branch',width:150},
				{header:'业务名称(中文)',dataIndex:'trade_name_ch',width:150},
				{header:'业务名称(英文)',dataIndex:'trade_name_en',width:150},
				{header:'所属业务类别',dataIndex:'trade_type',width:150,renderer:function(value){
          		 	return getDictValue(value,bsTypeStore,'value','key');//翻译字段方法
       			 }},
				{header:'机构类型',dataIndex:'branch_level',width:150,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,branchLevelStore,'value','key');//翻译字段方法
       			 }},
				{header:'业务状态',dataIndex:'status',width:150,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,stateStore,'value','key');//翻译字段方法
       			 }},
				{header:'备注',dataIndex:'note',width:350,renderer:function(value){
					value = value.toString();
					return getDictValue(value,businessStore,'bs_id','bs_name_ch');
				}}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑业务信息', 500, 280, 200, 2,
				[
					{colIndex:0, field:{xtype : 'textfield', name:'trade_id', 	 fieldLabel:'业务编号', allowBlank : false,readOnly:true}},
					{colIndex:1, field:{xtype : 'textfield', name:'branch', 	 fieldLabel:'机构号', allowBlank : false,readOnly:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'trade_name_ch', id:'trade_name_ch', 	 fieldLabel:'业务名称(中文)',allowBlank : false,regex:/^[\u4E00-\u9FA5]/,regexText:'只能输入汉字开头',maxLength : 60,	maxLengthText : '长度不能大于60位'}},
					{colIndex:0, field:{xtype : 'textfield', name:'trade_name_en', id:'trade_name_en', 	 fieldLabel:'业务名称(英文)',allowBlank : true,maxLength : 60,	maxLengthText : '长度不能大于60位'}},
					{colIndex:1, field:{xtype : 'combo',     name:'trade_type', id:'sel_trade_type', 	 fieldLabel:'所属业务类别',editable:false,    allowBlank : false,hiddenName:'trade_type',store:bsTypeStore,displayField:"key",valueField:"value"}},
					{colIndex:1, field:{xtype : 'combo',     name:'note', id:'notea', 	 fieldLabel:'所属业务',editable:false,    allowBlank : false,hiddenName:'note',store:businessStore,displayField:"bs_name_ch",valueField:"bs_id"}},
					{colIndex:1, field:{xtype : 'checkbox',  name:'status', id:'status', hideLabel:true, 	boxLabel:'启用'}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				]
			);
			
			 /**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"，"重置"，"添加"，"修改"，"删除"的选择语句
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '3';
						pagequeryObj.queryPage(query_obj);
						break;
					case 1:
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
				requestAjax('<%=basePath%>pfs/pfsTrade_editTrade',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '3';
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
	<div id="editWindow"></div>
	<div id="detailsWindow"></div>
  </body>
</html>
