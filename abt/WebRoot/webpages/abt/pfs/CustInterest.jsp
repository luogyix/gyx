<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute("logonuser");
String template_id = (String)request.getSession().getAttribute("template");
String branch;
if(template_id!=null){
	branch = template_id;
}else{
	branch = user.getUnitid();
}

%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>利率管理</title>
    
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
	<script type="text/javascript" src="selfjs/pagequery/pagetoafaquery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
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
		
		var systemParamStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>pfs/custInterest_queryAddRequirement',
			root   : 'field1',
			fields:['intds','vlddat','int'],
			autoLoad:true
		});
		
		var ccyStore = new Ext.data.SimpleStore({ 
			data:[
			      ["01","01-人民币"]
			],
			fields : ['dictValue','dictValueDesc']
		});
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		
		
		
		
/**
 * @Title: loadPage 
 * @Description: 显示客户信息处理界面
 */	
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var branch = <%="'"+user.getUnit().getUnitid()+"'"%>;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '利率管理', 120, 1,
				[
					//{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,		fieldLabel:'机构号',passName: 'branch', tree:treepanel_a, width:202, value:<%="'" + user.getUnit().getUnitname() + "'"%>}}，
					{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,fieldLabel:'机构号',passName:'branch',tree:treepanel_a, width:200,value:<%="'" + user.getUnit().getUnitname()+ "'"%>}} //部门
				],
				[
					{iconCls: "x-image-query", 			        id:'01',    text:'<s:text name="common.button.query"/>'},		//查询
					{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
					{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
					{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'},//删除
					{iconCls: "x-image-application_form_add",id:'06',text:'导出Excel'} //导出excel
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>pfs/custInterest_queryBisfmintBisPage',
				[
				 	'branch',//机构号
				 	'intds',//利率说明
				 	'ccy',//货币代码
				 	'int',//利率
				 	'vlddat',//生效日期
				 	'amount',//涨幅
				 	'ex_interest',//执行利率
				 	'add_time',//添加时间
				 	'date_time'//修改时间
				],
				[
					{header:'全选'},
					{header:'复选框'},
					{header:'机构号',dataIndex:'branch',width:140},
					{header:'利率说明',dataIndex:'intds',width:170},
					{header:'货币代码',dataIndex:'ccy',width:140,renderer:function(value){
						return getDictValue(value,ccyStore,'dictValue','dictValueDesc');
	       			}},
					{header:'利率',dataIndex:'int',width:140},
					{header:'生效日期',dataIndex:'vlddat',width:150},
					{header:'涨幅',dataIndex:'amount',width:140},
					{header:'执行利率',dataIndex:'ex_interest',width:140},
					{header:'添加时间',dataIndex:'add_time',width:140},
					{header:'修改时间',dataIndex:'date_time',width:140}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加利率', 280, 360, 200, 1,
				[
					{colIndex:0, field:{xtype : 'combo', 	  fieldLabel:'利率说明',name:'intds', 	 allowBlank : false,editable:false,store:systemParamStore, displayField:'intds', valueField:'intds',listeners:{
			            select:function(combo,record,index){
			            	//根据利率说明获得设置生效日期
			            	Ext.getCmp('vlddat_add').setValue(record.get('vlddat'));
			            	//设置利率
			            	Ext.getCmp('int_add').setValue(record.get('int'));
			        }}}},
					{colIndex:0, field:{xtype : 'combo', 	  fieldLabel:'货币类型',name:'ccy',    	 allowBlank : false,editable:false,displayField:'dictValueDesc',valueField:'dictValue',store: ccyStore}},
					{colIndex:0, field:{xtype : 'textfield',  fieldLabel:'生效日期',name:'vlddat', 	 allowBlank : true ,readOnly:true, id:'vlddat_add'}},
					{colIndex:0, field:{xtype : 'textfield',  fieldLabel:'利率',   name:'int',        id:'int_add',   	  allowBlank : true, decimalPrecision : 4, readOnly:true }},
					{colIndex:0, field:{xtype : 'numberfield',fieldLabel:'涨幅',   name:'amount',     id:'amount_add',	  allowBlank : false,decimalPrecision : 4, blankText:'请输入(精确到小数点后4位)',maxLength:8,maxlengthText:'最大长度为8',maxValue:999.9999,maxText:'格式为：xxx.xxxx',value:0.0000}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '修改执行利率',280, 360, 200, 1,
				[
					{colIndex:0, field:{xtype : 'combo', 	  fieldLabel:'利率说明',name:'intds', 	 id:'intds_edit',      allowBlank : false,editable:false,readOnly:true,store:systemParamStore, displayField:'intds', valueField:'intds'}},
					{colIndex:0, field:{xtype : 'combo', 	  fieldLabel:'货币类型',name:'ccy',    	 allowBlank : false,  editable:false,displayField:'dictValueDesc',valueField:'dictValue',store: ccyStore}},
					{colIndex:0, field:{xtype : 'textfield',  fieldLabel:'生效日期',name:'vlddat', 	 allowBlank : true ,  readOnly:true, id:'vlddat_edit'}},
					{colIndex:0, field:{xtype : 'textfield',  fieldLabel:'利率',   name:'int',        id:'int_edit',   	  allowBlank : true, decimalPrecision : 4, readOnly:true }},
					{colIndex:0, field:{xtype : 'numberfield',fieldLabel:'涨幅',   name:'amount',     id:'amount_edit',	  allowBlank : false,decimalPrecision : 4, blankText:'请输入(精确到小数点后4位)',maxLength:8,maxlengthText:'最大长度为8',maxValue:999.9999,maxText:'格式为：xxx.xxxx',value:0.0000}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				]
			);
			
		   /**
			* @Title:onButtonClicked
			* @Description:触发"查询"，"添加"，"修改"，"删除"的选择语句
			*/
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
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
						if(!isEdit){
							break;
						}
						//无法修改非本行机构
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=<%="'"+branch+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
					    		return;
					    	}
					    }
						editwindow.open();
						editwindow.updateFields(records[0]);
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['branch','intds']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}
						 //无法删除非本行机构
					    for(var i=0;i<submitdata.length;i++){
					    	if(submitdata[i].branch!=<%="'"+branch+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
					    		return;
					    	}
					    }
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>pfs/custInterest_delBisfmintBisPage',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					    break;
					case 4:
						var query_obj = conditionPanel.getFields();
						var branch=query_obj["branch"];
						if(null==branch||branch===undefined||branch.length==0){
							Ext.MessageBox.alert('信息提示','请选择想查询的机构!');
							return;
						}if(pagequeryObj.selfPagingTool.totalNum == -1){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.queryneeded"/>');
						}else if(pagequeryObj.selfPagingTool.totalNum == 0 ){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.commoninfo"><s:param>'+'查询记录数为0，无数据需要下载！'+'</s:param></s:text>');
						}else if(pagequeryObj.selfPagingTool.totalNum > 100000){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.download.recordsoverlimit"/>');
						}else{
							requestAjax('<%=basePath%>common_exportXls','',function(sRet){
								if(sRet.res){
									Ext.MessageBox.alert("导出失败",sRet.message);
								}else{
									var query_obj = conditionPanel.getFields();
									var myForm = document.getElementById("formId");
									var myInputs = myForm.getElementsByTagName("input");
									myInputs[0].value = Ext.util.JSON.encode(query_obj);
									myForm.submit();
								}
							});
						}
					break;
					    
				}
			}
			
			
			/**
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				requestAjax('<%=basePath%>pfs/custInterest_editBisfmintBisPage',submitData,
						function(sRet){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
							var query_obj = conditionPanel.getFields();
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
				requestAjax('<%=basePath%>pfs/custInterest_addBisfmintBisPage', submitData, 
					function(sRet){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
							var query_obj = conditionPanel.getFields();
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
      		Ext.getCmp('unitname').setPassValue(branch);
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="editWindow"></div>
	
	<form id="formId" name="queueNum_exportExcel" action="/abt/pfs/custInterest_exportExcel" target="REPORTRESULTFRAME" method="post">
		<input type="hidden" name="querycondition_str" value="" id="inputId"/>
	</form>

	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
  </body>
</html>
