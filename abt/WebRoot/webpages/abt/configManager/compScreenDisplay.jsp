<%@page import="com.agree.framework.struts2.webserver.ApplicationConstants"%>
<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
String brno = user.getUnitid();
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>综合屏信息配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
		//var pagereturn=${actionresult};
		var pagereturn={"result":true,"message":"","operaterResult":null};
		var conditionPanel = null;
		
		var styleStore=new Ext.data.SimpleStore({ 
			data:[["0","立即显示"],["1","从右向左移"],["2","从下向上移"],["3","从左向右展开"],["4","百叶窗从左向右展开"],["5","从上向下展开"],["6","从上向下移"],["7","闪烁显示"],["8","连续左移"]],
			fields : ['key','value']
		});
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示客户类型界面
 */		var priv=pagereturn.field2;
		function loadPage(){//就一直到最后了
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			//上边的工具条
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '综合屏信息配置', 120, 0,
				[
				],
				[
				{iconCls: "x-image-query", 			        id:'01',    text:'<s:text name="common.button.query"/>'},		//查询
				{iconCls: "x-image-application_form_add", 	id:'02',	text:'<s:text name="common.button.addrecord"/>'},	//添加
				{iconCls: "x-image-application_form_edit", 	id:'03',	text:'<s:text name="common.button.editrecord"/>'},	//修改
				{iconCls: "x-image-application_form_delete",id:'04', 	text:'<s:text name="common.button.deleterecord"/>'}//删除
				],
				onButtonClicked,priv
			);
			conditionPanel.open();
			
			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				clientWidth,clientHeight - conditionPanel.getHeight(),
				//在screenDisplayAction中有个queryScreenPage方法
				//!CompScreenDisplayAction 中的queryCompScreen4Page方法
				'<%=basePath%>confManager/compScreenDisplay_queryCompScreen4Page',
				['branch','comp_screen_id','default_flag','free_content','free_style','free_speed','call_content','call_style','call_speed','pause_content','pause_style','pause_speed','serve_content','serve_style','serve_speed'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'机构号',dataIndex:'branch',width:100},
				{header:'综合屏信息编号',dataIndex:'comp_screen_id',width:110},
				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
					return value=='0'?'非默认':'默认';}},
				{header:'空闲时显示内容',dataIndex:'free_content',width:180},
				{header:'空闲时显示效果',dataIndex:'free_style',width:180,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,styleStore,'key','value');//翻译字段方法  ？
       			 }},
				{header:'空闲时显示速度(秒)',dataIndex:'free_speed',width:180,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }},
				{header:'叫号时显示内容',dataIndex:'call_content',width:180},
				{header:'叫号时显示效果',dataIndex:'call_style',width:180,renderer:function(value){
					value = value.toString();
					return getDictValue(value,styleStore,'key','value');//翻译字段方法
       			 }},
				{header:'叫号时显示速度(秒)',dataIndex:'call_speed',width:180,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }},
				{header:'暂停时显示内容',dataIndex:'pause_content',width:180},
				{header:'暂停时显示效果',dataIndex:'pause_style',width:180,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,styleStore,'key','value');//翻译字段方法
       			 }},
				{header:'暂停时显示速度(秒)',dataIndex:'pause_speed',width:180,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }},
				{header:'正在办理时显示内容',dataIndex:'serve_content',width:180},
				{header:'正在办理时显示效果',dataIndex:'serve_style',width:180,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,styleStore,'key','value');//翻译字段方法
       			 }},
				{header:'正在办理时显示速度(秒)',dataIndex:'serve_speed',width:220,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }}
				],
				'<s:text name="common.pagequery.pagingtool"/>'//？
			);
			
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加综合屏信息', 600, 400, 160, 3,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'free_content',id:'free_content_add', 	fieldLabel:'空闲时显示内容', 	allowBlank : false, blankText:'请输入显示内容',maxLength : 100,	maxLengthText : '长度不能大于100位'	}},
				{colIndex:1, field:{xtype : 'combo',     name:'free_style',id:'free_style_add',   fieldLabel:'空闲时显示效果',	allowBlank : false, hiddenName:'free_style',value:0,editable:false,blankText : '请选择显示效果', store:styleStore, 	displayField:'value', valueField:'key'}},
				{colIndex:2, field:{xtype : 'textfield', name:'free_speed',id:'free_speed_add', 	fieldLabel:'空闲时显示速度(秒)', 	allowBlank : false, blankText:'请输入显示速度',maxLength : 5,	maxLengthText : '长度不能大于5位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'call_content',id:'call_content_add', 	fieldLabel:'叫号时显示内容', 	allowBlank : false, blankText:'请输入显示内容',maxLength : 100,	maxLengthText : '长度不能大于100位'	}},
				{colIndex:1, field:{xtype : 'combo',     name:'call_style',id:'call_style_add',   fieldLabel:'叫号时显示效果',	allowBlank : false, hiddenName:'call_style',value:0,editable:false,blankText : '请选择显示效果', store:styleStore, 	displayField:'value', valueField:'key'}},
				{colIndex:2, field:{xtype : 'textfield', name:'call_speed',id:'call_speed_add', 	fieldLabel:'叫号时显示速度(秒)', 	allowBlank : false, blankText:'请输入显示速度',maxLength : 5,	maxLengthText : '长度不能大于5位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'pause_content',id:'pause_content_add', 	fieldLabel:'暂停时显示内容', 	allowBlank : false, blankText:'请输入显示内容',maxLength : 100,	maxLengthText : '长度不能大于100位'	}},
				{colIndex:1, field:{xtype : 'combo',     name:'pause_style',id:'pause_style_add',   fieldLabel:'暂停时显示效果',	allowBlank : false, hiddenName:'pause_style',value:0,editable:false,blankText : '请选择显示效果', store:styleStore, 	displayField:'value', valueField:'key'}},
				{colIndex:2, field:{xtype : 'textfield', name:'pause_speed',id:'pause_speed_add', 	fieldLabel:'暂停时显示速度(秒)', 	allowBlank : false, blankText:'请输入显示速度',maxLength : 5,	maxLengthText : '长度不能大于5位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'serve_content',id:'serve_content_add', 	fieldLabel:'正在办理时显示内容', 	allowBlank : false, blankText:'请输入显示内容',maxLength : 100,	maxLengthText : '长度不能大于100位'	}},
				{colIndex:1, field:{xtype : 'combo',     name:'serve_style',id:'serve_style_add',   fieldLabel:'正在办理时显示效果',	allowBlank : false, hiddenName:'serve_style',value:0,editable:false,blankText : '请选择显示效果', store:styleStore, 	displayField:'value', valueField:'key'}},
				{colIndex:2, field:{xtype : 'textfield', name:'serve_speed',id:'serve_speed_add', 	fieldLabel:'正在办理时显示速度(秒)', 	allowBlank : false, blankText:'请输入显示速度',maxLength : 5,	maxLengthText : '长度不能大于5位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, hiddenLabel:true,field:{width:240,html:'<font color="red">注:     排队号:@@@@@,窗口:##</font>'}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'default_flag',id:'default_flag_add', 	boxLabel:'设为默认'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : screenDetails},
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑综合屏信息', 600, 400, 160, 3,
			   [
				{colIndex:0, field:{xtype : 'textfield', name:'branch', 	fieldLabel:'机构号',id:'branch', readOnly:true , hidden:true}},
				{colIndex:1, field:{xtype : 'textfield', name:'comp_screen_id', 	fieldLabel:'综合屏信息编号',id:'comp_screen_id', readOnly:true, hidden:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'free_content',id:'free_content_edit', 	fieldLabel:'空闲时显示内容', 	allowBlank : false, blankText:'请输入显示内容',maxLength : 100,	maxLengthText : '长度不能大于100位'	}},
				{colIndex:1, field:{xtype : 'combo',     name:'free_style', id:'free_style_edit',   fieldLabel:'空闲时显示效果',	allowBlank : false, hiddenName:'free_style',value:0,editable:false,blankText : '请选择显示效果', store:styleStore, 	displayField:'value', valueField:'key'}},
				{colIndex:2, field:{xtype : 'textfield', name:'free_speed',id:'free_speed_edit', 	fieldLabel:'空闲时显示速度(秒)', 	allowBlank : false, blankText:'请输入显示速度',maxLength : 5,	maxLengthText : '长度不能大于5位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'call_content',id:'call_content_edit', 	fieldLabel:'叫号时显示内容', 	allowBlank : false, blankText:'请输入显示内容',maxLength : 100,	maxLengthText : '长度不能大于100位'	}},
				{colIndex:1, field:{xtype : 'combo',     name:'call_style',id:'call_style_edit',   fieldLabel:'叫号时显示效果',	allowBlank : false, hiddenName:'call_style',value:0,editable:false,blankText : '请选择显示效果', store:styleStore, 	displayField:'value', valueField:'key'}},
				{colIndex:2, field:{xtype : 'textfield', name:'call_speed',id:'call_speed_edit', 	fieldLabel:'叫号时显示速度(秒)', 	allowBlank : false, blankText:'请输入显示速度',maxLength : 5,	maxLengthText : '长度不能大于5位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'pause_content',id:'pause_content_edit', 	fieldLabel:'暂停时显示内容', 	allowBlank : false, blankText:'请输入显示内容',maxLength : 100,	maxLengthText : '长度不能大于100位'	}},
				{colIndex:1, field:{xtype : 'combo',     name:'pause_style',id:'pause_style_edit',   fieldLabel:'暂停时显示效果',	allowBlank : false, hiddenName:'pause_style',value:0,editable:false,blankText : '请选择显示效果', store:styleStore, 	displayField:'value', valueField:'key'}},
				{colIndex:2, field:{xtype : 'textfield', name:'pause_speed',id:'pause_speed_edit', 	fieldLabel:'暂停时显示速度(秒)', 	allowBlank : false, blankText:'请输入显示速度',maxLength : 5,	maxLengthText : '长度不能大于5位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield', name:'serve_content',id:'serve_content_edit', 	fieldLabel:'正在办理时显示内容', 	allowBlank : false, blankText:'请输入显示内容',maxLength : 100,	maxLengthText : '长度不能大于100位'	}},
				{colIndex:1, field:{xtype : 'combo',     name:'serve_style',id:'serve_style_edit',   fieldLabel:'正在办理时显示效果',	allowBlank : false, hiddenName:'serve_style',value:0,editable:false,blankText : '请选择显示效果', store:styleStore, 	displayField:'value', valueField:'key'}},
				{colIndex:2, field:{xtype : 'textfield', name:'serve_speed',id:'serve_speed_edit', 	fieldLabel:'正在办理时显示速度(秒)', 	allowBlank : false, blankText:'请输入显示速度',maxLength : 5,	maxLengthText : '长度不能大于5位',regex:/^[0-9]*$/,regexText:'只能输入数字'}},
				{colIndex:0, hiddenLabel:true,field:{width:240,html:'<font color="red">注:     排队号:@@@@@,窗口:##</font>'}},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,  name:'default_flag',  id:'default_flag_edit', 	boxLabel:'设为默认'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : screenDetails},
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();}}
				]
			);
			//screenDetails  这个是上边用到的方法
            function screenDetails(){
        		var styleStore=new Ext.data.SimpleStore({ 
        			data:[["0","立即显示"],["1","从右向左移"],["2","从下向上移"],["3","从左向右展开"],["4","百叶窗从左向右展开"],["5","从上向下展开"],["6","从上向下移"],["7","闪烁显示"],["8","连续左移"]],
        			fields : ['key','value']
        		});
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['branch','comp_screen_id','default_flag','free_content','free_style','free_speed','call_content','call_style','call_speed','pause_content','pause_style','pause_speed','serve_content','serve_style','serve_speed']
			    });
			    
			    var detailData = [
					{header:'机构号',dataIndex:'branch',width:100},
				{header:'综合屏信息编号',dataIndex:'comp_screen_id',width:100},
				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
					return value=='1'?'默认':'非默认';}},
				{header:'空闲时显示内容',dataIndex:'free_content',width:180},
				{header:'空闲时显示效果',dataIndex:'free_style',width:180,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,styleStore,'value','key');//翻译字段方法
       			 }},
				{header:'空闲时显示速度(秒)',dataIndex:'free_speed',width:180,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }},
				{header:'叫号时显示内容',dataIndex:'call_content',width:180},
				{header:'叫号时显示效果',dataIndex:'call_style',width:180,renderer:function(value){
					value = value.toString();
					return getDictValue(value,styleStore,'value','key');//翻译字段方法
       			 }},
				{header:'叫号时显示速度(秒)',dataIndex:'call_speed',width:180,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }},
				{header:'暂停时显示内容',dataIndex:'pause_content',width:180},
				{header:'暂停时显示效果',dataIndex:'pause_style',width:180,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,styleStore,'value','key');//翻译字段方法
       			 }},
				{header:'暂停时显示速度(秒)',dataIndex:'pause_speed',width:180,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }},
				{header:'正在办理时显示内容',dataIndex:'serve_content',width:180},
				{header:'正在办理时显示效果',dataIndex:'serve_style',width:180,renderer:function(value){
					value = value.toString();
          		 	return getDictValue(value,styleStore,'value','key');//翻译字段方法
       			 }},
				{header:'正在办理时显示速度(秒)',dataIndex:'serve_speed',width:180,renderer:function(value){
					value = value.toString();
					return "0"==value?"无限大":value;//翻译字段方法
       			 }}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);//这个detailData是上边刚刚定义的呢个
			    var detailsWindow = new SelfFormWindowSetWidth('screenDetailsWindow','综合屏参数配置编号列表',600, 355, 555, 1, [555],[{
		    		colIndex:0,
		    		field:{
		    			xtype:'fieldset',
		    			title:'综合屏参数配置信息',
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
			    //这段？  是个双击事件
			    Ext.getCmp('paramGrid').on('rowdblclick',function(grid, rowindex,e){
	    			var record = grid.getStore().getAt(rowindex);
	    			detailsWindow.close();
	    			Ext.getCmp('free_content_add').setValue(record.data['free_content']);
	    			Ext.getCmp('free_content_edit').setValue(record.data['free_content']);
	    			Ext.getCmp('free_style_add').setValue(record.data['free_style']);
	    			Ext.getCmp('free_style_edit').setValue(record.data['free_style']);
	    			Ext.getCmp('free_speed_add').setValue(record.data['free_speed']);
	    			Ext.getCmp('free_speed_edit').setValue(record.data['free_speed']);
	    			Ext.getCmp('call_content_add').setValue(record.data['call_content']);
	    			Ext.getCmp('call_content_edit').setValue(record.data['call_content']);
	    			Ext.getCmp('call_style_add').setValue(record.data['call_style']);
	    			Ext.getCmp('call_style_edit').setValue(record.data['call_style']);
	    			Ext.getCmp('call_speed_add').setValue(record.data['call_speed']);
	    			Ext.getCmp('call_speed_edit').setValue(record.data['call_speed']);
	    			Ext.getCmp('pause_content_add').setValue(record.data['pause_content']);
	    			Ext.getCmp('pause_content_edit').setValue(record.data['pause_content']);
	    			Ext.getCmp('pause_style_add').setValue(record.data['pause_style']);
	    			Ext.getCmp('pause_style_edit').setValue(record.data['pause_style']);
	    			Ext.getCmp('pause_speed_add').setValue(record.data['pause_speed']);
	    			Ext.getCmp('pause_speed_edit').setValue(record.data['pause_speed']);
	    			Ext.getCmp('serve_content_add').setValue(record.data['serve_content']);
	    			Ext.getCmp('serve_content_edit').setValue(record.data['serve_content']);
	    			Ext.getCmp('serve_style_add').setValue(record.data['serve_style']);
	    			Ext.getCmp('serve_style_edit').setValue(record.data['serve_style']);
	    			Ext.getCmp('serve_speed_add').setValue(record.data['serve_speed']);
	    			Ext.getCmp('serve_speed_edit').setValue(record.data['serve_speed']);
	    			Ext.getCmp('default_flag_add').setValue(record.data['default_flag']);
	    			Ext.getCmp('default_flag_edit').setValue(record.data['default_flag']);
	    			//返回值
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '5';
			    requestAjax('<%=basePath%>/confManager/compScreenDisplay_queryCompScreen',submitDetail, function(sRet){
				      detailStore.loadData(sRet.field1);
				});
			}//screenDetails 方法
			 /**
			 * @Title:onButtonClicked
			 * @Description:触发"查询"，"重置"，"添加"，"修改"，"删除"的选择语句
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '4';
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
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['branch','comp_screen_id']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						 //无法删除非本行机构
					    for(var i=0;i<submitdata.length;i++){
					    	if(submitdata[i].branch!=<%="'"+brno+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法删除非本机构的配置');
					    		return;
					    	}
					    }
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
							//用这个接收action.java 中的return
								requestAjax('<%=basePath%>confManager/compScreenDisplay_delCompScreen',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>',sRet.resultmsg,function(id){
										var query_obj = conditionPanel.getFields();
										query_obj['query_rules'] = '4';
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
				createCheckBoxValue('default_flag',submitData);
				requestAjax('<%=basePath%>confManager/compScreenDisplay_editCompScreen',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '4';
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
				createCheckBoxValue('default_flag',submitData);
				requestAjax('<%=basePath%>confManager/compScreenDisplay_addCompScreen', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '4';
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
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="editWindow"></div>
	<div id="screenDetailsWindow"></div>
  </body>
</html>
