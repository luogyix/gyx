<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>客户类型配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		//var custTypeStore = new Ext.data.JsonStore({ 
		//	url    : '<%=basePath%>/confManager/custType_queryCustTypeSmall.action',
		//	autoLoad:true,
		//	root   : 'field1',
		//	fields : ['custtype_i','custtype_e']
		//});
		
		var isVipStore=new Ext.data.SimpleStore({ 
			data:[["1","是"],["0","否"]],
			fields : ['key','value']
		});
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示客户类型界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '客户类型配置', 120, 0,
				[
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
				'<%=basePath%>/confManager/custType_queryCustTypePage',
				['custtype_i','custtype_e','custtype_name','custtype_level','isvip','custtype_waittime'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'内部客户类型',dataIndex:'custtype_i',width:100},
				{header:'外部客户类型',dataIndex:'custtype_e',width:300},
				{header:'客户类型名称',dataIndex:'custtype_name',width:100},
				{header:'是否VIP',dataIndex:'isvip',width:(window.screen.width)-775,renderer:function(value){if(value=='1'){return '是'}else{return '否'}}}
				//{header:'客户等待时间达标阀值',dataIndex:'custtype_waittime',width:150}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加客户类型', 600, 300, 110, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'custtype_name', 	fieldLabel:'客户类型名称', 	allowBlank : false, blankText:'请输入客户类型名称',maxLength : 30,	maxLengthText : '长度不能大于30位'	}},
				//{colIndex:0, field:{xtype : 'numberfield',     name:'custtype_waittime',   fieldLabel:'客户等待时间达标阀值',	allowBlank : false, blankText:'请输入客户等待时间达标阀值',allowDecimals : false,allowNegative : false,maxValue : 99999999,maxText : '长度不可超过8位'}},//,regex:/^((1002001004\_85000[2-7]{1}|1002001004\_859999|1002001005\_),)*$/,regexText:'输入数据非法'
				{colIndex:0, field:{xtype : 'textfield',     name:'custtype_i',   fieldLabel:'内部客户类型',	allowBlank : false, hiddenName:'custtype_i',blankText : '请输入内部客户类型',maxLength : 2,	maxLengthText : '长度不能大于2位',regex:/^[^\u4e00-\u9fa5]*$/, regexText:'不能输入中文'}},
				{colIndex:0, field:{xtype : 'textfield',     name:'custtype_e',   fieldLabel:'外部客户类型',	allowBlank : true, blankText:'请输入外部客户类型',maxLength : 1000,	maxLengthText : '长度不能大于1000位',regex:/^[^\u4e00-\u9fa5]*$/, regexText:'不能输入中文'}},//,regex:/^((1002001004\_85000[2-7]{1}|1002001004\_859999|1002001005\_),)*$/,regexText:'输入数据非法'
				
				{colIndex:1, field:{xtype : 'checkbox', name:'isvip', 	boxLabel:'VIP',hideLabel:true}},
				{colIndex:0, hiddenLabel:true,field:{width:240,html:'<font color="red">注:配置多个外部客户类型,由","分割</font>'}},
				{colIndex:1, hiddenLabel:true,field:{width:240,html:'<br><br><font color="blue">外部客户类型列表:</font><br><font color="black">&nbsp;&nbsp;0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;普通客户</font><br><font color="black">&nbsp;&nbsp;5&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;黄金客户</font><br><font color="black">&nbsp;&nbsp;6&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铂金客户</font><br><font color="black">&nbsp;&nbsp;7&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;钻石客户</font>'}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',150
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑客户类型', 600, 300, 110, 2,
			   [
				{colIndex:0, field:{xtype : 'textfield', name:'custtype_name', 	fieldLabel:'客户类型名称', 	allowBlank : false, blankText:'请输入客户类型名称',maxLength : 30,	maxLengthText : '长度不能大于30位'	}},
				{colIndex:0, field:{xtype : 'textfield',name:'custtype_i_old',id:'custtype_i_old',hidden:true}},
				{colIndex:0, field:{xtype : 'textfield',name:'custtype_e_old',id:'custtype_e_old',hidden:true}},
				//{colIndex:0, field:{xtype : 'numberfield',     name:'custtype_waittime',   fieldLabel:'客户等待时间达标阀值',	allowBlank : false, blankText:'请输入客户等待时间达标阀值',allowDecimals : false,allowNegative : false,maxValue : 99999999,maxText : '长度不可超过8位'}},//,regex:/^((1002001004\_85000[2-7]{1}|1002001004\_859999|1002001005\_),)*$/,regexText:'输入数据非法'
				{colIndex:0, field:{xtype : 'textfield',     name:'custtype_i',   fieldLabel:'内部客户类型',	allowBlank : false, hiddenName:'custtype_i',blankText : '请输入内部客户类型',maxLength : 2,	maxLengthText : '长度不能大于2位',regex:/^[^\u4e00-\u9fa5]*$/, regexText:'不能输入中文'}},
				{colIndex:0, field:{xtype : 'textfield',     name:'custtype_e',   fieldLabel:'外部客户类型',	allowBlank : true, blankText:'请输入外部客户类型',maxLength : 1000,	maxLengthText : '长度不能大于1000位',regex:/^[^\u4e00-\u9fa5]*$/, regexText:'不能输入中文'}},
				{colIndex:1, field:{xtype : 'checkbox', name:'isvip', 	boxLabel:'VIP',hideLabel:true}},
				{colIndex:0, hiddenLabel:true,field:{width:240,html:'<font color="red">注:配置多个外部客户类型,由","分割</font>'}},
				{colIndex:1, hiddenLabel:true,field:{width:240,html:'<br><br><font color="blue">外部客户类型列表:</font><br><font color="black">&nbsp;&nbsp;0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;普通客户</font><br><font color="black">&nbsp;&nbsp;5&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;黄金客户</font><br><font color="black">&nbsp;&nbsp;6&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铂金客户</font><br><font color="black">&nbsp;&nbsp;7&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;钻石客户</font>'}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',150
			);
			
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
						editwindow.open();
						editwindow.updateFields(records[0]);
						Ext.getCmp('custtype_i_old').setValue(records[0].data['custtype_i']);
						Ext.getCmp('custtype_e_old').setValue(records[0].data['custtype_e']);
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['custtype_i','custtype_e']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>confManager/custType_delCustType',submitdata,function(sRet){
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
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				if(submitData['custtype_i'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入内部客户类型');
					return;
				}
				if(submitData['custtype_name'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入客户类型名称');
					return;
				}
				var custtype_e = submitData['custtype_e'];
				//1002001004_850002,1002001004_850003,1002001004_850004
				var array= new Array();
				array = custtype_e.split(',');
				for (i=0;i<array.length ;i++ )    
			    {
					for(j=i+1;j<array.length;j++){
						//遍历全部外部客户类型
						//alert(array[i] + " " + array[j]);
						if(array[i] == array[j]){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','外部客户类型输入了重复数据');
							return;
						}
					}
			    } 
				createCheckBoxValue('isvip',submitData);
				//pagequeryObj pagingGrid
				requestAjax('<%=basePath%>/confManager/custType_addCustType', submitData,
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
				var submitData = editwindow.getFields();
				if(submitData['custtype_i'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入内部客户类型');
					return;
				}
				if(submitData['custtype_name'].trim()==''){
					Ext.MessageBox.alert('系统提示','请输入客户类型名称');
					return;
				}
				var custtype_e = submitData['custtype_e'];
				//1002001004_850002,1002001004_850003,1002001004_850004
				var array= new Array();
				array = custtype_e.split(',');
				for (i=0;i<array.length ;i++ )    
			    {
					for(j=i+1;j<array.length;j++){
						//遍历全部外部客户类型
						//alert(array[i] + " " + array[j]);
						if(array[i] == array[j]){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','外部客户类型输入了重复数据');
							return;
						}
					}
			    } 
				createCheckBoxValue('isvip',submitData);
				requestAjax('<%=basePath%>/confManager/custType_editCustType',submitData,function(sRet){
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
