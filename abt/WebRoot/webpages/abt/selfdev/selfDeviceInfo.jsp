<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>			
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String branch = user.getUnitid();
String hostIp = user.getHostip();
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>自助设备信息</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagetoafaquery.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var	systemUnits=pagereturn.field1;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		var conditionPanel = null;
		var admin ='admin';
		var assessor ='assessor';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		var layingModeStore = new Ext.data.SimpleStore({ 
			data:[['0','0-在行'],['1','1-离行']],
			fields : ['dictValue','dictValueDesc']
		});
		//设备状态
		var deviceStatusStore = new Ext.data.SimpleStore({ 
			data:[['0','0-停用'],['1','1-启用'],['2','2-待激活'],['3','3-报废']],
			fields : ['dictValue','dictValueDesc']
		});
		//设备类型
		var deviceTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=devicetype',
			root   : 'field1',
			fields : ['dictValue','dictValueDesc'],
			autoLoad:true
		});
		var device_chk_typeStore=new Ext.data.SimpleStore({ 
			data:[["序列号校验","2"],["MAC校验","1"]],
			fields : ['key','value']
		});
/**
 * @Title: loadPage 
 * @Description: 显示自助设备信息界面
 */	
		function loadPage(){
	 	
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};
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
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '自助设备信息配置', 120, 1,
				[
				{rowIndex:0, field:{xtype:'combotree', 	name:'unitname',id:'unitname',  allowUnLeafClick:true,		fieldLabel:'机构号',passName: 'branch', tree:treepanel_a, width:202, value:<%="'" + user.getUnit().getUnitname() + "'"%>}}, 
				{rowIndex:0,field:{xtype:'textfield',name:'deviceadm_num',id:'deviceadm_num_query',fieldLabel:'发卡机柜员号',width:100,maxLength:4,maxLengthText:'长度不能大于4位'}}
				],
				[
				{iconCls: "x-image-query", 			        id:'01',    text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_edit",id:'04', text:'迁移'},
				{iconCls: "x-image-application_form_edit",id:'05', text:'初始化'},
				{iconCls: "x-image-application_form_edit",id:'06', text:'启用'},
				{iconCls: "x-image-application_form_edit",id:'07', text:'停用'},
				{iconCls: "x-image-application_form_edit",id:'08', text:'报废'}
				],
				onButtonClicked
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				'<%=basePath%>confManager/selfDevice_queryDeviceInfoConfPage',
				['device_num','device_name','branch','deviceadm_num','device_ip','device_mode','device_status','devicetype','laying_mode','device_param_id','buydate','installdate','card_admin',
				 'device_assessor','device_address','deviceadm_name','device_chk_type','device_chk_info'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'设备编号',dataIndex:'device_num',width:150},
				{header:'设备名称',dataIndex:'device_name',width:150},
				{header:'机构号',dataIndex:'branch',width:150},
				{header:'柜员号',dataIndex:'deviceadm_num',width:150},
				{header:'IP地址',dataIndex:'device_ip',width:180},
				{header:'在离行',dataIndex:'laying_mode',width:180},
				{header:'设备状态',dataIndex:'device_status',width:150,renderer:function(value){
          		 	return getDictValue(value,deviceStatusStore,'dictValue','dictValueDesc');}}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加设备信息', 600, 430, 180, 2,
				[
                {colIndex:0, field:{xtype : 'combotree', name:'branch',allowBlank : false,	fieldLabel:'机构号',	 passName: 'branch', tree:treepanel_a}},
                {colIndex:1, field:{xtype : 'textfield', name:'device_num', id:'device_num_add',fieldLabel:'设备编号',editable:false, 	allowBlank : false,readOnly:true}},
                {colIndex:0, field:{xtype : 'combo', name:'devicetype', hiddenName:'devicetype',	fieldLabel:'设备类型',id:'devicetype_add', editable:false, 	allowBlank : false, store:deviceTypeStore, 	displayField:'dictValueDesc', valueField:'dictValue'}},
                {colIndex:1, field:{xtype : 'textfield', name:'device_name', 	fieldLabel:'设备名称',id:'device_name_add', 	allowBlank : true, blankText:'请输入设备名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
                {colIndex:0, field:{xtype : 'textfield', name:'device_model', 	fieldLabel:'设备型号',id:'device_model_add',hidden: true, blankText:'请输入设备型号',maxLength :60,	maxLengthText : '长度不能大于60位'}},
                {colIndex:0,field:{xtype:'textfield',name:'device_chk_info',id:'device_chk_info_add',fieldLabel:'设备激活码'}},
                {colIndex:1,field:{xtype:'textfield',name:'deviceadm_num',id:'deviceadm_num_add',fieldLabel:'发卡机柜员号',regex:/^\d{4}$/,regexText:'请输入正确的4位数字'}},
                {colIndex:0,field:{xtype:'textfield',name:'device_ip',id:'device_ip_add',fieldLabel:'IP地址',regex: /((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))/,regexText:'请输入正确的ip地址',allowBlank : false}},
                {colIndex:1,field:{xtype:'combo',name:'laying_mode',id:'laying_mode_add',fieldLabel:'在离行',editable:false, hiddenName:'laying_mode',store:layingModeStore, displayField:'dictValueDesc', valueField:'dictValue',value:'0'}},
                {colIndex:0, field:{layout:'column',fieldLabel:'设备参数',items:[{xtype : 'textfield',readOnly:true,editable:true, name:'device_param_id',id:'device_param_id_add', 	fieldLabel:'设备参数',	allowBlank : true,width:'100'},
                                                         {xtype:'button',text:'选择',width:40,listeners:{"click":function(){paramDetails()}}}]}},
                {colIndex:1,field:{xtype:'datefield',name:'buydate',id:'buydate_add',fieldLabel:'购置日期',editable:false,format:'Y-m-d'}},
                {colIndex:0,field:{xtype:'datefield',name:'installdate',id:'installdate_add',fieldLabel:'安装日期',editable:false,format:'Y-m-d'}},
                {colIndex:1, field:{layout:'column',fieldLabel:'发卡机管理员',
                	items:[{xtype : 'textfield',readOnly:true,editable:true, name:'card_admin',id:'card_admin_add',fieldLabel:'发卡机管理员',	allowBlank : true},
                           {xtype:'button',text:'选择',listeners:{"click":function(){selectUser(admin)}}}]}}, 
                {colIndex:0, field:{layout:'column',fieldLabel:'审核员',
                          	items:[{xtype : 'textfield',readOnly:true,editable:true, name:'device_assessor',id:'device_assessor_add',fieldLabel:'审核员',	allowBlank : true},
                                      {xtype:'button',text:'选择',listeners:{"click":function(){selectUser(assessor)}}}]}} ,
                {colIndex:0, field:{xtype : 'textfield',name:'device_status', boxLabel:'启用',value:'2',hidden:true}},   
                {colIndex:0,field:{xtype:'textfield',name:'device_address',id:'device_address_add',fieldLabel:'装机地址',maxLength:60,maxLengthText:'长度不能大于60位',width:180}},
                {colIndex:0, field:{xtype : 'textfield', name:'deviceadm_name',hidden:true}},
                {colIndex:0, field:{xtype : 'combo', name:'device_chk_type',hiddenName:'device_chk_type',store:device_chk_typeStore,displayField:'key', valueField:'value',hidden:true}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				],'left',80
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑设备信息', 600, 460, 180, 2,
			    [
	                {colIndex:0, field:{xtype :'textfield', name:'branch',	fieldLabel:'机构号',	 passName: 'branch', tree:treepanel_a,readOnly:true}},
	                {colIndex:1, field:{xtype : 'textfield', name:'device_num', id:'device_num_edit',fieldLabel:'设备编号', readOnly:true}},
	                {colIndex:0, field:{xtype : 'combo', name:'devicetype', hiddenName:'devicetype',	fieldLabel:'设备类型',readOnly:true,  store:deviceTypeStore, 	displayField:'dictValueDesc', valueField:'dictValue'}},
	                {colIndex:1, field:{xtype : 'textfield', name:'device_name', 	fieldLabel:'设备名称',id:'device_name_edit', blankText:'请输入设备名称',maxLength :60,	maxLengthText : '长度不能大于60位'}},
	                {colIndex:0, field:{xtype : 'textfield', name:'device_model', 	fieldLabel:'设备型号',id:'device_model_edit',hidden: true, blankText:'请输入设备型号',maxLength :60,	maxLengthText : '长度不能大于60位'}},
	                {colIndex:1,field:{xtype:'textfield',name:'deviceadm_num',id:'deviceadm_num_edit',fieldLabel:'发卡机柜员号',maxLength:4,maxLengthText:'长度不能大于4位'}},
	                {colIndex:0,field:{xtype:'textfield',name:'device_ip',id:'device_ip_edit',fieldLabel:'IP地址'}},
	                {colIndex:0, field:{layout:'column',fieldLabel:'设备参数',items:[{xtype : 'textfield',readOnly:true,editable:true, name:'device_param_id',id:'device_param_id_edit', 	fieldLabel:'设备参数',	allowBlank : true},
	                            {xtype:'button',text:'选择',listeners:{"click":function(){paramDetails()}}}]}},
	                {colIndex:1,field:{xtype:'textfield',name:'device_chk_info',id:'device_chk_info_edit',fieldLabel:'设备激活码'}},
	                {colIndex:0,field:{xtype:'textfield',name:'device_address',id:'device_address_edit',fieldLabel:'装机地址',maxLength:60,maxLengthText:'长度不能大于60位'}},
	                {colIndex:1,field:{xtype:'datefield',name:'buydate',id:'buydate_edit',fieldLabel:'购置日期',editable:false,format:'Y-m-d'}},
	                {colIndex:0,field:{xtype:'datefield',name:'installdate',id:'installdate_edit',fieldLabel:'安装日期',editable:false,format:'Y-m-d'}},
	                {colIndex:1, field:{layout:'column',fieldLabel:'发卡机管理员',
	                	items:[{xtype : 'textfield',readOnly:true,editable:true, name:'card_admin',id:'card_admin_edit', 	fieldLabel:'发卡机管理员'},
	                           {xtype:'button',text:'选择',listeners:{"click":function(){selectUser(admin)}}}]}}, 
	                {colIndex:0, field:{layout:'column',fieldLabel:'审核员',
	                    items:[{xtype : 'textfield',readOnly:true,editable:true, name:'device_assessor',id:'device_assessor_edit', 	fieldLabel:'审核员'},
	                            {xtype:'button',text:'选择',listeners:{"click":function(){selectUser(assessor)}}}]}},
	                {colIndex:0,field:{xtype:'combo',name:'laying_mode',id:'laying_mode_edit',fieldLabel:'在离行',readOnly:true,store:layingModeStore, displayField:'dictValueDesc', valueField:'dictValue'}},
	                {colIndex:0, field:{xtype : 'textfield', name:'deviceadm_name',hidden:true}},
	                {colIndex:0, field:{xtype : 'combo', name:'device_chk_type', hiddenName:'device_chk_type',fieldLabel:'设备校验类型',store:device_chk_typeStore,displayField:'key', valueField:'value',hidden:true}}
				                
				],
				[
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				],'left',80
			);
			/*点击'迁移',弹出的窗口*/
			movewindow = new SelfFormWindow('moveWindow', '自助设备迁移', 600, 400, 170, 2,
				    [
		                {colIndex:0,field:{xtype:'textfield',name:'device_num',id:'device_num',fieldLabel:'设备编号',readOnly:true}},
		                {colIndex:1, field:{xtype : 'combo', name:'devicetype', hiddenName:'devicetype',	fieldLabel:'设备类型' ,store:deviceTypeStore, 	displayField:'dictValueDesc', valueField:'dictValue',readOnly:true}},
		                {colIndex:0,field:{xtype:'combo',name:'laying_mode',fieldLabel:'在离行',readOnly:true,store:layingModeStore, displayField:'dictValueDesc', valueField:'dictValue'}},
		                {colIndex:1,field:{xtype:'textfield',name:'device_chk_info',fieldLabel:'设备激活码',readOnly:true}},
		                {colIndex:0,field:{xtype:'textfield',name:'device_address',fieldLabel:'装机地址',maxLength:60,maxLengthText:'长度不能大于60位',readOnly:true}},
		                {colIndex:1,field:{xtype:'textfield',name:'buydate',fieldLabel:'购置日期',readOnly:true,format:'Y-m-d'}},
		                {colIndex:0, field:{xtype:'textfield',name:'branch',fieldLabel:'原机构',	passName: 'branch',readOnly:true}},
		                {colIndex:1, field:{xtype:'combotree',name:'branch_new', allowBlank : false,	fieldLabel:'新机构',	 passName: 'branch_new', tree:treepanel_a}},
		                {colIndex:0,field:{xtype:'textfield',name:'deviceadm_num',fieldLabel:'原柜员号',readOnly:true}},
		                {colIndex:1,field:{xtype:'textfield',name:'deviceadm_num_new',fieldLabel:'新柜员号', allowBlank : false,maxLength:4,maxLengthText:'长度不能大于4位'}},
			            {colIndex:0,field:{xtype:'textfield',name:'device_ip',fieldLabel:'原IP地址',readOnly:true}},
			            {colIndex:1,field:{xtype:'textfield',name:'device_ip_new',fieldLabel:'新IP地址', allowBlank : false,maxLength:15,maxLengthText:'长度不能大于15位',regex: /^((2[0-4]\d|25[0-5]|[1-9]?\d|1\d{2})\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,regexText:'请输入正确的ip地址'}},
		                {colIndex:0, field:{layout:'column',fieldLabel:'发卡机管理员',
		                	items:[{xtype : 'textfield',readOnly:true,editable:true, name:'card_admin',id:'card_admin_new',fieldLabel:'发卡机管理员',	allowBlank : true},
		                           {xtype:'button',text:'选择',listeners:{"click":function(){selectUser(admin)}}}]}}, 
		                {colIndex:1, field:{layout:'column',fieldLabel:'审核员',
		                           	items:[{xtype : 'textfield',readOnly:true,editable:true, name:'device_assessor',id:'device_assessor_new', 	fieldLabel:'审核员',	allowBlank : true},
		                                      {xtype:'button',text:'选择',listeners:{"click":function(){selectUser(assessor)}}}]}},
		   		        {colIndex:0,field:{xtype:'textfield',name:'installdate',fieldLabel:'安装日期',readOnly:true,format:'Y-m-d'}}        
					],
					[
						{text:'<s:text name="common.button.edit"/>', 	handler : onmoveclicked, 	formBind:true},
						{text:'<s:text name="common.button.cancel"/>', 	handler: function(){movewindow.close();}}
					],'left',80
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
						Ext.getCmp('device_num_add').setValue(getDeviceNum());
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
						editwindow.updateFields(records[0]);//回显用的
						break;
					case 3:
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
						movewindow.open();
						movewindow.updateFields(records[0]);//回显用的
						break;
					case 4:
						var records = pagequeryObj.getSelectedRecords();
						if(records === undefined || records.length !== 1){
							 Ext.MessageBox.alert('系统提示','请选择一条记录');
							   break;
						}
						var submitdata = records[0].data;
						if(submitdata['device_status']=='3'||submitdata['device_status']=='2'){
							 Ext.MessageBox.alert('系统提示','设备无法初始化');
							   break;
						}
						
						submitdata['device_status']='2';
						requestAjax('<%=basePath%>confManager/selfDevice_changeDeviceStatus',submitdata,function(sRet){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','设备成功初始化',function(id){
								var query_obj = conditionPanel.getFields();
								pagequeryObj.queryPage(query_obj);
							});
						});
						break;
					case 5:
						var records = pagequeryObj.getSelectedRecords();
						if(records === undefined || records.length !== 1){
							 Ext.MessageBox.alert('系统提示','请选择一条记录');
							   break;
						}
						var submitdata = records[0].data;
						if(submitdata['device_status']=='3'||submitdata['device_status']=='1'){
							 Ext.MessageBox.alert('系统提示','设备无法被启用');
							   break;
						}
						submitdata['device_status']='1';
						requestAjax('<%=basePath%>confManager/selfDevice_changeDeviceStatus',submitdata,function(sRet){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','设备成功启用',function(id){
								var query_obj = conditionPanel.getFields();
								pagequeryObj.queryPage(query_obj);
							});
						});
						break;
					case 6:
						var records = pagequeryObj.getSelectedRecords();
						if(records === undefined || records.length !== 1){
							 Ext.MessageBox.alert('系统提示','请选择一条记录');
							   break;
						}
						var submitdata = records[0].data;
						if(submitdata['device_status']=='3'||submitdata['device_status']=='0'){
							 Ext.MessageBox.alert('系统提示','设备无法进行停用');
							   break;
						}
						submitdata['device_status']='0';
						requestAjax('<%=basePath%>confManager/selfDevice_changeDeviceStatus',submitdata,function(sRet){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','设备成功停用',function(id){
								var query_obj = conditionPanel.getFields();
								pagequeryObj.queryPage(query_obj);
							});
						});
						break;
					case 7:
						var records = pagequeryObj.getSelectedRecords();
						if(records === undefined || records.length !== 1){
							 Ext.MessageBox.alert('系统提示','请选择一条记录');
							   break;
						}
						var submitdata = records[0].data;
						if(submitdata['device_status']=='3'){
							 Ext.MessageBox.alert('系统提示','设备无法进行报废');
							   break;
						}
						submitdata['device_status']='3';
						requestAjax('<%=basePath%>confManager/selfDevice_changeDeviceStatus',submitdata,function(sRet){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','设备报废成功',function(id){
								var query_obj = conditionPanel.getFields();
								pagequeryObj.queryPage(query_obj);
							});
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
				if(submitData['installdate']<submitData['buydate']){
					 Ext.MessageBox.alert('系统提示','安装日期不能小于购置日期！');
					 return;
				}
				requestAjax('<%=basePath%>confManager/selfDevice_editDeviceInfoConf',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
			}
			/**
			 * @Title:onButtonClicked
			 * @Description:点击迁移时的操作
			 */
			function onmoveclicked(){
				var submitData = movewindow.getFields();
				requestAjax('<%=basePath%>confManager/selfDevice_moveDeviceInfoConf',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','设备迁移成功',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					movewindow.close();
				});
			}
            /**
			* @Title:onaddclicked
			* @Description:添加数据函数
			*/
			function onaddclicked(){
				var submitData = addwindow.getFields();
				if(submitData['installdate']<submitData['buydate']){
					 Ext.MessageBox.alert('系统提示','安装日期不能小于购置日期！');
					 return;
				}
				requestAjax('<%=basePath%>confManager/selfDevice_addDeviceInfoConf', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
			}
            /*生成设备编号*/
			function getDeviceNum() {
				var date = new Date();
				var minute =  date.getMinutes();
                var second = date.getSeconds();
                if (minute <= 9) {
                    minute = "0" + minute;
               }
                if (second <= 9) {
                	second = "0" + second;
               }
                var num = "";
                for(var i=0;i<4;i++) 
                { 
                  num+=Math.floor(Math.random()*10); 
                } 

                var devicenum = minute+second+num;
                return devicenum;
            }
            /**
             *  设备审核员或管理员弹出窗
             */
             function selectUser(user){
            	var adminStore =  new Ext.data.JsonStore({
			    	fields:['userid','username','unitid']
			    }); 
            	var adminData = [
                    {header:'用户ID',dataIndex:'userid',width:100},
                    {header:'用户名',dataIndex:'username',width:100},
                    {header:'所属机构',dataIndex:'unitid',width:100}
       			];
            	var	title = '审核员列表';
            	if(user=='admin'){
            		title = '管理员列表';
            	}
            	var adminColModel=new Ext.grid.ColumnModel(adminData);
            	var adminWindow = new SelfFormWindowSetWidth('adminWindow',title,600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:title,
			   			layout:'column',
			   			items:[{
			       			columnWidth:1,
			       			items:[{
			       				xtype:'grid',
			       				id:'adminGrid',
			       				store:adminStore,
			       				cm:adminColModel,
			       				height:250,
			       				iconCls:'icon-grid',
			       				stripeRows : true/*,
			       				doLayout:function(){
			       					this.setSize(Ext.get(this.getEl().dom.parentNode).getSize(true));
			       				}*/
			       			}]
			   			}]
			   		}
			    }],[],'left',140);
            	 
			    Ext.getCmp('adminGrid').on('rowdblclick',function(grid, rowindex,e){
		  			var record = grid.getStore().getAt(rowindex);
		  			adminWindow.close();
		  			if(record.data['userid']!='默认'){
		  				if(user=='admin'){
		  					Ext.getCmp('card_admin_add').setValue(record.data['userid']);
			    			Ext.getCmp('card_admin_new').setValue(record.data['userid']);
			    			Ext.getCmp('card_admin_edit').setValue(record.data['userid']);
		  				}else{
		  					Ext.getCmp('device_assessor_new').setValue(record.data['userid']);
		  					Ext.getCmp('device_assessor_add').setValue(record.data['userid']);
		  					Ext.getCmp('device_assessor_edit').setValue(record.data['userid']);
		  				}
		  			}else{
		  				if(user=='admin'){
		  					Ext.getCmp('card_admin_add').setValue(record.data['userid']);
			    			Ext.getCmp('card_admin_new').setValue(record.data['userid']);
			    			Ext.getCmp('card_admin_edit').setValue(record.data['userid']);
		  				}else{
		  					Ext.getCmp('device_assessor_new').setValue(record.data['userid']);
		  					Ext.getCmp('device_assessor_add').setValue(record.data['userid']);
		  					Ext.getCmp('device_assessor_edit').setValue(record.data['userid']);
		  				}
		  			}
			  	});
			    
			    adminWindow.open();
			    var submitDetail= {};
			    requestAjax('<%=basePath%>/admin/systemuser_queryuser',submitDetail, function(sRet){
			    	adminStore.loadData(sRet.field1);
				});
            }
            /**
             *	参数弹出窗
             */
			function paramDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['dev_param_id','dev_param_name','parameter_id','param_type']
			    });
			    var detailData = [
					{header:'设备通用参数ID',dataIndex:'dev_param_id',width:100},
				{header:'设备通用参数名称',dataIndex:'dev_param_name',width:100},
				{header:'菜单编号',dataIndex:'parameter_id',width:100},
				{header:'设备通用参数类型',dataIndex:'param_type',width:100,renderer:function(value){
          		 	return getDictValue(value,deviceTypeStore,'dictValue','dictValueDesc');
      			 }}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('paramDetailsWindow','自助设备参数列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'自助设备参数信息',
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
		  			if(record.data['mob_param_id']!='默认'){
		  				Ext.getCmp('device_param_id_add').setValue(record.data['dev_param_id']);
		    			Ext.getCmp('device_param_id_edit').setValue(record.data['dev_param_id']);
		  			}else{
		  				Ext.getCmp('device_param_id_add').setValue('');
			  			Ext.getCmp('device_param_id_edit').setValue('');
		  			}
			  	});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    requestAjax('<%=basePath%>pfs/devParam_queryDevParam',submitDetail, function(sRet){
			    	detailStore.loadData(sRet.field1);
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
	<div id="moveWindow"></div>
	<div id="adminWindow"></div>
	<div id="paramDetailsWindow"></div>
	<div id="editWindow"></div>
  </body>
</html>
