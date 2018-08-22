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
    
    <title>费率管理</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	
	<script type="text/javascript" src="selfjs/common/TreeFilter.js"></script>
	<script type="text/javascript" src="selfjs/common/PinyinFilter.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagetoafaquery.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		var q;
		
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
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
/**
 * @Title: loadPage 
 * @Description: 显示客户信息处理界面
 */	
 		function loadPage(){
	 
	        var importWindow;
	        
		 	var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var branch = <%="'"+user.getUnit().getUnitid()+"'"%>;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '费率管理', 120, 1,
				[
					{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,fieldLabel:'机构号',passName:'branch',tree:treepanel_a, width:200,value:<%="'" + user.getUnit().getUnitname()+ "'"%>}}, //部门
					{rowIndex:0, field:{xtype:'textfield', 	name:'produce_name',fieldLabel:'产品名称',value:'', width:100}}				
				],
				[
					{iconCls: "x-image-query", 			        id:'01',    text:'<s:text name="common.button.query"/>'},		//查询
					{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
					{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
					{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'},//删除
					{iconCls: "x-image-reset",  id:'05',text:'<s:text name="common.button.reset"/>'},//重置
					{iconCls: "x-image-Excel",id:'06',text:'导出Excel'},//导出excel
					{iconCls: "x-image-Excel", id:'07',	text:'导入数据'}//导入Excel
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				//当选择"查询"时，弹出的窗口
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>pfs/custRate_queryCustRatePage',
				[
				 	'branch',//机构号
				 	'produce_name',//产品名称 
				 	'charge_project',//收费项目
				 	'charge_criterion',//收费标准
				 	'charge_reason',//收费依据CHARGE_REASON
				 	'create_date',//创建日期
				 	'update_time',//修改日期
				 	'cust_note'//备注
				 	
				],
				[
					{header:'全选'},
					{header:'复选框'},
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'产品名称 ',dataIndex:'produce_name',width:100},
					{header:'收费项目',dataIndex:'charge_project',width:200},
					{header:'收费标准',dataIndex:'charge_criterion',width:300},
					{header:'收费依据',dataIndex:'charge_reason',width:500},
					{header:'创建日期',dataIndex:'create_date',width:100},
					{header:'修改日期',dataIndex:'update_time',width:100},
					{header:'备注',dataIndex:'cust_note',width:100}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加费率', 280, 420, 200, 1,
				[
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '产品名称', name: 'produce_name', allowBlank: false, blankText: '请输入产品名称',maxLength : 32,maxLengthText : '长度不能大于32位'}},
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '收费项目', name: 'charge_project', allowBlank: false, blankText: '请输入收费项目',maxLength : 100,maxLengthText : '长度不能大于100位'}},
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '收费标准', name:'charge_criterion', allowBlank: false, blankText:'请输入收费标准',maxLength : 100,maxLengthText : '长度不能大于100位'}},
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '收费依据', name:'charge_reason', allowBlank: false,blankText:'请输入收费依据',maxLength : 100,maxLengthText : '长度不能大于100位'}},
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '创建时间', name:'create_date',allowBlank:true, hidden:true}},
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '备注', name:'cust_note', allowBlank:true}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '修改费率', 320, 220, 140, 1,
				[
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '产品名称', name: 'produce_name', allowBlank: false, blankText:'请输入产品名称',maxLength : 32,maxLengthText : '长度不能大于32位'}},
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '收费项目', name: 'charge_project', allowBlank: false, blankText:'请输入收费项目',maxLength : 100,maxLengthText : '长度不能大于100位'}},
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '收费标准', name: 'charge_criterion', allowBlank: false, blankText: '请输入收费标准',maxLength : 100,maxLengthText : '长度不能大于100位'}},
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '收费依据', name:'charge_reason', allowBlank: false,blankText:'请输入收费依据',maxLength : 100,maxLengthText : '长度不能大于100位'}},
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '修改时间', name: 'update_time', allowBlank:true,hidden:true}},
					{colIndex:0, field:{xtype : 'textfield', fieldLabel: '备注', name:'cust_note',allowBlank:true}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',80
			);
			
		   /**
			* @Title:onButtonClicked
			* @Description:触发"重置"""查询"，"添加"，"修改"，"删除"的选择语句
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
						q=records[0];
						editwindow.updateFields(records[0]);
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['branch','produce_name','charge_project','charge_criterion']);
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
								requestAjax('<%=basePath%>pfs/custRate_deleteCusRate',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
						break;
					case 4:
						conditionPanel.reset();
						break;
					case 5:
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
					case 6:
						 importWindow = new SelfFormWindow('importWindow', '导入机构费率信息', 300, 165, 210, 1,
							[
								{colIndex:0, field:{xtype : 'textfield', name : 'filename', allowBlank : false,  fieldLabel : '文件名称', blankText : '请上传文件', maxLength : 200,maxLengthText : '长度不能大于200位',readOnly:true	 }},
								{colIndex:0, field:{xtype : 'textfield', name : 'produce_name', allowBlank : true, fieldLabel : '产品名称', hidden : true }},
								{colIndex:0, field:{xtype : 'textfield', name : 'charge_project', allowBlank : true, fieldLabel : '收费项目', hidden : true }},
								{colIndex:0, field:{xtype : 'textfield', name : 'charge_criterion', allowBlank : true, fieldLabel : '收费标准', hidden : true }},
								{colIndex:0, field:{xtype : 'textfield', name : 'charge_reason', allowBlank : true, fieldLabel : '收费依据', hidden : true}},								
								{colIndex:0, field:{xtype : 'textfield', name : 'cust_note', allBlank:true, fieldLabel : '备注', hidden : true}}
							],
							[
								{text:'上传文件', handler: function(){
									var form = new Ext.form.FormPanel({
										baseCls : 'x-plain',  
										labelWidth : 70,    
										fileUpload : true,  
										defaultType : 'textfield',  
										items : [{  
											xtype : 'textfield',  
									        fieldLabel : '选择文件',  
									        name : 'upload',  
									        id : 'upload',  
									        inputType : 'file',  
									        anchor : '95%'
										}]
									});
								    var window = new Ext.Window({
								    	width :450,  
										height : 100,  
								        //关闭时执行隐藏命令,如果是close就不能再show出来了  
								        closeAction:'close',
								        draggable : false, //不允许窗口拖放  
								       	modal : true,//屏蔽其他组件,自动生成一个半透明的div  
								        buttonAlign : 'right',//按钮的对齐方式  
								        defaultButton : 0,//默认选择哪个按钮  
								        items:form,
								        buttons : [{  
								            text : '确定',  
								            handler : function() {  
										       if (form.form.isValid()) {  
										        if(Ext.getCmp('upload').getValue() == ''){  
										         Ext.Msg.alert('溫馨提示','请选择上传的文件..');  
										         return;  
										        }  
										        Ext.MessageBox.show({  
										           title : '请稍候....',  
										           msg : '文件正在上传中....',  
										           progressText : '',  
										           width : 300,  
										           progress : true,  
										           closable : false,  
										           animEl : 'loding'  
										        });  
										        form.getForm().submit({  
										            url : '<%=basePath%>/pfs/custRate_upLoadFile',  
										            method : 'POST',  
										            success : function(form, action) {
										            	Ext.MessageBox.hide();//让进度条消失  
										            	var response = eval('(' + action.response.responseText + ')');
										            	if(response['success'] == 'true'){//成功
										            		var value = Ext.getCmp('upload').getValue();
												            var val = value.split("\\");
												           	var field = importWindow.getForm().findField('filename');
															field.setValue(val[val.length-1]);
															window.close();
										            	}
													    Ext.Msg.alert('提示',response['result']);  
										         	},  
											        failure : function(form, action) {  
											            Ext.Msg.alert('错误','文件上传失败，请重新操作！');  
											        } 
										        }); 
										       }  
										     }  
								        },{  
								            text : '取消' , 
								            handler:function(){
								            	window.close();
								            }
								        }]  
								    });  
								    window.show();  
								}},
								{text:'导入',   formBind:true, handler :  function(){
									var submitData = importWindow.getFields();
									requestAjax('<%=basePath%>/pfs/custRate_importCustRate', submitData,
									function(sRet){
										Ext.MessageBox.alert('<s:text name="common.info.title"/>','导入成功',function(id){
											var query_obj = conditionPanel.getFields();
											pagequeryObj.queryPage(query_obj);
										});
										importWindow.close();
									});
								}},
								{text:'<s:text name="common.button.cancel"/>', handler: function(){importWindow.close();}}
							]
						);
						 importWindow.open();	
						 break;
				}
			}
		   
		   /**
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
			    requestAjax('<%=basePath%>pfs/custRate_addCustRate', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
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
				 console.log(q);
				var submitData = editwindow.getFields();
				submitData['old']=q.data;
				requestAjax('<%=basePath%>pfs/custRate_editCustRate',submitData,function(sRet){
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
	<div id="importWindow"></div>
  <form id="formId" name="custRate_exportExcel" action="/abt/pfs/custRate_exportExcel" target="REPORTRESULTFRAME" method="post">
		<input type="hidden" name="querycondition_str" value="" id="inputId"/>
	</form>
	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
  </body>
</html>
