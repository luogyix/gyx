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
    
    <title>卡BIN配置</title>
    
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
	<script type="text/javascript" src="selfjs/pagequery/pagetoafaquery.js"></script>
	<script type="text/javascript">
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		/**
		 * 翻译字段用store
		 */
		var trackNoStore=new Ext.data.SimpleStore({ 
			data:[["是","1"],["否","0"]],
			fields : ['key','value']
		});
		//内部客户类型
		var custTypeStore = new Ext.data.JsonStore({ 
			url    : '<%=basePath%>/confManager/custType_queryCustTypeSmall.action',
			autoLoad:true,
			root   : 'field1',
			fields : ['custtype_i','custtype_name'],
			listeners : {
				load : function(store,records){
					var temp = store.data.items;
					for(var i=0;i<temp.length;i++){
						temp[i].data['custtype_name'] = temp[i].data['custtype_i'] + '-' + temp[i].data['custtype_name'];
					}
					this.removeAll();
					store.add(temp);
				}
			}
		});
		
		
		
/**
 * @Title: loadPage 
 * @Description: 显示卡bin维护界面
 */
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '卡bin', 120, 0,
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
				'<%=basePath%>/confManager/cardBin_queryCardPage',
				[
				 'branch',//机构号
				 'check_id',//卡bin类型id
				 'card_name',//卡名称
				 'custtype_i',//内部客户类型
				 'bin_check_no',//卡Bin验证数据
				 'iscustdiscern',//是否进行客户识别
				 'ordertime',//指定时间
				 'isuseing',//是否启用(0:禁用;1:启用)
				 'remarks'//备注
				 ],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'ID',dataIndex:'check_id',width:100},
				{header:'卡名称',dataIndex:'card_name',width:100},
				{header:'内部客户类型',dataIndex:'custtype_i',width:100,renderer:function(value){
          		 	return getDictValue(value,custTypeStore,'custtype_i','custtype_name');//翻译字段方法
       			}},
				{header:'卡Bin验证数据',dataIndex:'bin_check_no',width:70},
				{header:'是否进行客户识别',dataIndex:'iscustdiscern',width:150,renderer:function(value){
          		 	if(value == "0"){
          		 		return "否";
          		 	}
          		 	return "是";
       			}},
				{header:'制定时间',dataIndex:'ordertime',width:100},
				{header:'是否启用',dataIndex:'isuseing',width:100,renderer:function(value){
					if(value == "0"){
						return "否";
					}else{
						return "是";
					}
					
				}},
				{header:'备注',dataIndex:'remarks',width:100}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加卡Bin', 500, 300, 200, 2,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'card_name', 	fieldLabel:'卡名称', 	     allowBlank : false, blankText:'请输入卡名称',maxLength : 20,	maxLengthText : '长度不能大于20位'	}},
				{colIndex:1, field:{xtype : 'combo', name:'custtype_i', 	fieldLabel:'内部客户类型',	allowBlank : false,hiddenName:'custtype_i',blankText:'请选择内部客户类型',store:custTypeStore,displayField:"custtype_name",valueField:"custtype_i",editable:false}},
				{colIndex:0, field:{xtype : 'textfield', name:'bin_check_no', 	fieldLabel:'卡Bin验证数据',	allowBlank : false,blankText:'请输入验证数据',maxLength:19,maxLengthText:'长度不能大于19位'}},
				{colIndex:1, field:{xtype : 'combo',     name:'iscustdiscern', 	 fieldLabel:'是否进行客户识别',	    allowBlank : false,hiddenName:'iscustdiscern',store:trackNoStore,displayField:"key",valueField:"value",editable:false}},
				{colIndex:0, field:{xtype : 'textarea', 	name:'remarks', id:'remarks_add', fieldLabel:'备注',allowBlank : false,height:70,maxLength : 60,maxLengthText : '长度不能大于60位'}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'isuseing', 	boxLabel:'启用',checked : true}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑卡bin', 600, 400, 200, 2,
			   [
				{colIndex:0, field:{xtype : 'textfield', name:'check_id',hidden:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'card_name', 	fieldLabel:'卡名称', 	     allowBlank : false, blankText:'请输入卡名称',maxLength : 20,	maxLengthText : '长度不能大于20位'	}},
				{colIndex:1, field:{xtype : 'combo', name:'custtype_i', 	fieldLabel:'内部客户类型',	allowBlank : false,hiddenName:'custtype_i',blankText:'请选择内部客户类型',store:custTypeStore,displayField:"custtype_name",valueField:"custtype_i",editable:false}},
				{colIndex:0, field:{xtype : 'textfield', name:'bin_check_no', 	fieldLabel:'卡Bin验证数据',	allowBlank : false,blankText:'请输入验证数据',maxLength:19,maxLengthText:'长度不能大于19位'}},
				{colIndex:1, field:{xtype : 'combo',     name:'iscustdiscern', 	 fieldLabel:'是否进行客户识别',	    allowBlank : false,hiddenName:'iscustdiscern',store:trackNoStore,displayField:"key",valueField:"value",editable:false}},
				{colIndex:0, field:{xtype : 'textarea', 	name:'remarks', id:'remarks_edit', fieldLabel:'备注',height:70,maxLength : 60,allowBlank : false,maxLengthText : '长度不能大于60位'}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,  name:'isuseing', 	boxLabel:'启用',checked : true}}
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
						Ext.getCmp('card_name_old').setValue(records[0].data['card_name']);
						Ext.getCmp('custtype_i_old').setValue(records[0].data['custtype_i']);
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['branch','check_id']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/confManager/cardBin_delCard.action',submitdata,function(sRet){
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
				requestAjax('<%=basePath%>/confManager/cardBin_addCard.action', submitData,
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
				requestAjax('<%=basePath%>/confManager/cardBin_editCard.action',submitData,function(sRet){
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
