<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String template_id = (String)request.getSession().getAttribute("template");
String branch;
if(template_id!=null){
	branch = template_id;
}else{
	branch = user.getUnitid();
}

String hostIp = user.getHostip();
boolean machine_view_flag = (Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.MACHINEVIEWFLAG);
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>发卡机信息配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
		//发卡机编号
		var qmNumStore=new Ext.data.SimpleStore({ 
			data:[
			      ["1","1"],
			      ["2","2"],
			      ["3","3"],
			      ["4","4"],
			      ["5","5"],
			      ["6","6"],
			      ["7","7"],
			      ["8","8"],
			      ["9","9"],
			      ["10","10"]
			      ],
			fields : ['key','value']
		});
		
		//授权等级
		var accreditrankStore = new Ext.data.SimpleStore({
			data:[
			      ["1级以上","1"],
			      ["2级以上","2"],
			      ["5级以上","5"]
			      ],
			fields : ["acKey" , "acValue"]
		});
		
		var systemParamStore = new Ext.data.JsonStore({
			url    : '<%=basePath%>/confManager/systemParameter_querySystemParameter',
			root   : 'field1',
			fields:['parameter_id','parameter_name','branch','default_flag','parameter_flag']
		});
		
		/* var qmStatusStore=new Ext.data.SimpleStore({ 
			data:[["1","1-可用"],["0","0-不可用"]],
			fields : ['key','value']
		}); */
		
/**
 * @Title: loadPage 
 * @Description: 显示发卡机信息界面
 */	
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '发卡机信息配置', 120, 1,
				[
				{rowIndex:0, field:{xtype:'textfield', id:'branchParam',name:'branchParam',emptyText:'-请输入子机构号-',fieldLabel:'机构号'}}
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
				'<%=basePath%>/confManager/cardMachine_queryCardMachinePage',
				[
				 'machine_Code',//发卡机编号
				 'machine_name',//发卡机名称
				 'branch',//机构号
				 'accreditrank',//授权等级
				 'status',//授权等级
				 'begin_time',//投入运行时间
				 'addUser',//添加人
				 'addTime',//添加时间
				 'editUser',//修改人
				 'editTime',//修改时间
				 'site',//安装地址
				 'hardwareNum',//硬件号
				 'machine_ip',//ip地址
				 'machine_control',//管理员
				 'machine_confirmation',//审核员
				 'machineview_id'
				 ],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'发卡机编号',dataIndex:'machine_Code',width:106},
				{header:'发卡机名称',dataIndex:'machine_name',width:106},
				{header:'机构号',   dataIndex:'branch',width:106},
				{header:'授权等级',  dataIndex:'accreditrank',width:106},
				{header:'状态',  dataIndex:'status',width:106,renderer:function(value){return value=='1'?'可用':'不可用'}},
				{header:'投入运行时间',dataIndex:'begin_time',width:106},
				{header:'添加人',dataIndex:'addUser',width:106},
				{header:'添加时间',dataIndex:'addTime',width:106},
				{header:'修改人',dataIndex:'editUser',width:106},
				{header:'修改时间',dataIndex:'editTime',width:106},
				{header:'安装地址',dataIndex:'site',width:150,width:106},
				{header:'硬件序号',dataIndex:'hardwareNum',width:106},
				{header:'ip地址',dataIndex:'machine_ip',width:106},
				{header:'发卡机管理员',dataIndex:'machine_control',width:106},
				{header:'发卡机审核员',dataIndex:'machine_confirmation',width:106}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时,弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加发卡机信息', 300, 300, 140, 1,
				[
				{colIndex:0, field:{xtype : 'combo', name:'machine_Code', 	fieldLabel:'发卡机编号',editable:false, 	allowBlank : false, store:qmNumStore, 	displayField:'key', valueField:'key',listeners:{
		            select:function(combo,record,index){
		                //对应的处理函数
		                Ext.getCmp('machine_name_add').setValue(record.get('value')+'号发卡机');
		            }
		        }}},
		        {colIndex:0, field:{xtype : 'combo', name:'accreditrank', id:'accreditrank_add',fieldLabel:'授权等级',editable:false, 	allowBlank : false, store:accreditrankStore, 	displayField:"acKey", valueField:"acKey",listeners:{
		            select:function(combo,record,index){
		                //对应的处理函数
		                Ext.getCmp('accreditrank_add').setValue(record.get('acValue'));
		            }
		        }}},
				{colIndex:0, field:{xtype : 'textfield', name:'machine_name', 	fieldLabel:'发卡机名称',id:'machine_name_add', 	allowBlank : false, blankText:'请输入发卡机名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'site', 	fieldLabel:'安装地址',id:'site_add', 	allowBlank : false, blankText:'请输入安装地址',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'machine_control', 	fieldLabel:'管理员',id:'machine_control_add', 	allowBlank : false,emptyText:'-请输入柜员号-',blankText:'请输入柜员号',maxLength :10,	maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/, regexText:'只能输入数字',minLength:5}},
				{colIndex:0, field:{xtype : 'textfield', name:'machine_confirmation', 	fieldLabel:'审核员',id:'machine_confirmation_add', 	allowBlank : false,emptyText:'-请输入柜员号-',blankText:'请输入柜员号',maxLength :60,	maxLengthText : '长度不能大于60位',regex:/^[0-9]*$/, regexText:'只能输入数字',minLength:5}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'status', 	boxLabel:'启用',checked : true}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close()}}
				],'left',80
			);
			
			//选择"修改"时,弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑发卡机信息', 300, 340, 140, 1,
			   [
				{colIndex:0, field:{xtype : 'combo',	fieldLabel:'发卡机编号', readOnly:true,editable:false,name:'machine_Code', 	allowBlank : false, store:qmNumStore, 	displayField:'value', valueField:'key',listeners:{
		            select:function(combo,record,index){
		                //对应的处理函数
		                Ext.getCmp('machine_name_edit').setValue(record.get('value')+'号发卡机');
		            }
		        }}},
		        {colIndex:0, field:{xtype : 'combo', name:'accreditrank', id:'accreditrank_edit',fieldLabel:'授权等级',editable:false, 	allowBlank : false, store:accreditrankStore, 	displayField:"acKey", valueField:"acKey",listeners:{
		            select:function(combo,record,index){
		                //对应的处理函数
		                Ext.getCmp('accreditrank_edit').setValue(record.get('acValue'));
		            }
		        }}},
				{colIndex:0, field:{xtype : 'textfield', name:'machine_name', value:'',	fieldLabel:'发卡机名称', id:'machine_name_edit',	allowBlank : false, blankText:'请输入发卡机名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'site', value:'',	fieldLabel:'安装地址', id:'site_edit',	allowBlank : false, blankText:'请输入发卡机名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
				/* {colIndex:0, field:{xtype : 'textfield', name:'machine_control', 	fieldLabel:'管理员',id:'machine_control_edit', 	allowBlank : false, blankText:'请输入柜员号',maxLength :10,	maxLengthText : '长度不能大于10位'}},
				{colIndex:0, field:{xtype : 'textfield', name:'machine_confirmation', 	fieldLabel:'审核员',id:'machine_confirmation_edit', 	allowBlank : false, blankText:'请输入柜员号',maxLength :60,	maxLengthText : '长度不能大于60位'}}, */
				{colIndex:0, field:{xtype : 'textfield', name:'machine_control', 	fieldLabel:'管理员',id:'machine_control_edit', 	allowBlank : false,emptyText:'-请输入柜员号-',blankText:'请输入柜员号',maxLength :10,	maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/, regexText:'只能输入数字',minLength:5}},
				{colIndex:0, field:{xtype : 'textfield', name:'machine_confirmation', 	fieldLabel:'审核员',id:'machine_confirmation_edit', 	allowBlank : false,emptyText:'-请输入柜员号-',blankText:'请输入柜员号',maxLength :10,	maxLengthText : '长度不能大于10位',regex:/^[0-9]*$/, regexText:'只能输入数字',minLength:5}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'status', 	boxLabel:'启用'}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close()}}
				],'left',80
			);
			
			/**
			* @Title:onButtonClicked
			* @Description:触发"查询","重置","添加","修改","删除"的选择语句
			*/
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						var branchParamValue = Ext.get('branchParam').getValue();
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
						//无法修改非本行机构
						var branch = '<%=branch%>';
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=branch){
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
						if(records === undefined || records.length !== 1){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
						var branch = '<%=branch%>';
					    for(var i=0;i<records.length;i++){
					    	if(records[i].data.branch!=branch){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
					    		return;
					    	}
					    }
					    var submitdata = pagequeryObj.getSelectedObjects(['machine_Code','branch']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							return;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/confManager/cardMachine_delCardMachine',submitdata,function(sRet){
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
				requestAjax('<%=basePath%>/confManager/cardMachine_editCardMachine',submitData,function(sRet){
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
				createCheckBoxValue('status',submitData);
				requestAjax('<%=basePath%>/confManager/cardMachine_addCardMachine', submitData,
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
      		var branch = '<%=branch%>';
      		systemParamStore.load({params:{'branch':branch,'parameter_flag':'01'}});
      		
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="paramDetailsWindow"></div>
	<div id="systemParamWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>