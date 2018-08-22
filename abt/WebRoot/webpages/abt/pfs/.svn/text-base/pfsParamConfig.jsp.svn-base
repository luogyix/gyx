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
    
    <title>填单机参数配置</title>
    
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
		var pagereturn=${actionresult};
		var conditionPanel = null;
		
		var labelTextFontStore=new Ext.data.ArrayStore({ 
			data:[
				["新宋体","新宋体"],
				["仿宋","仿宋"],
				["幼圆","幼圆"],
				["黑体","黑体"],
				["宋体","宋体"],
				["隶书","隶书"],
				["楷体","楷体"],
				["微软雅黑","微软雅黑"],
				["华文新魏","华文新魏"],
				["华文细黑","华文细黑"],
				["华文宋体","华文宋体"],
				["华文楷体","华文楷体"],
				["华文行楷","华文行楷"],
				["华文仿宋","华文仿宋"],
				["华文彩云","华文彩云"],
				["方正姚体","方正姚体"],
				["宋体-方正超大字符集","宋体-方正超大字符集"]
			],
			fields : ['key','value']
		});
		
		var labelTextSizeStore=new Ext.data.ArrayStore({ 
			data:[
				["44","初号"],
				["36","小初"],
				["26","一号"],
				["24","小一"],
				["22","二号"],
				["18","小二"],
				["16","三号"],
				["15","小三"],
				["14","四号"],
				["12","小四"],
				["10.5","五号"],
				["9","小五"],
				["7.5","六号"],
				["6.5","小六"],
				["5.5","七号"],
				["5","八号"],
				["8","8"],
				["9","9"],
				["10","10"],
				["11","11"],
				["12","12"],
				["14","14"],
				["16","16"],
				["18","18"],
				["20","20"],
				["22","22"],
				["24","24"],
				["26","26"],
				["28","28"],
				["36","36"],
				["48","48"],
				["72","72"],
				["73","73"],
				["74","74"],
				["75","75"],
				["76","76"],
				["77","77"],
				["78","78"],
				["79","79"],
				["80","80"],
				["81","81"],
				["82","82"],
				["83","83"],
				["84","84"],
				["85","85"],
				["86","86"],
				["87","87"],
				["88","88"],
				["89","89"],
				["90","90"],
				["91","91"],
				["92","92"],
				["93","93"],
				["94","94"],
				["95","95"],
				["96","96"]
			],
			fields : ['key','value']
		});
		
		var labelTextStyleStore=new Ext.data.ArrayStore({ 
			data:[
				["0","0-常规"],
				["1","1-粗体"],
				["2","2-斜体"]
			],
			fields : ['key','value']
		});
		
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		
/**
 * @Title: loadPage 
 * @Description: 显示填单机参数维护界面
 */		
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			//------------------------------------字体颜色配置-----------------------------------------------
			var cp = new Ext.ColorPalette(); // 初始化时选中的颜色
			var fontCol = '';
			var colorWin=new Ext.Window({//调色板的窗口布局
				border:false,
				closeAction:'hide',
				closable:false,
				resizable:false,
				width:100,
				height:100,
				items:[cp]
			})
			cp.on('select',function(palette, selColor){//selColor颜色的值
				fontCol=selColor;//把颜色的值保存起来，传给后台处理
				var color='#'+selColor;
				
				Ext.getDom(colorShowId).style.background=color;
				colorWin.hide();
			})
		    function showColor(id){
		      colorShowId=id;
		      var colorText=Ext.getCmp(id);
		      colorWin.x=colorText.getPosition()[0]+colorText.getWidth();
		      colorWin.y=colorText.getPosition()[1];
		      colorWin.show();
			}
			//----------------------------------字体颜色配置-------------------------------------------------
			var conditionPanel = new SelfToolBarPanel('pfsConditionPanel', '填单机参数配置', 120, 0,
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
				'<%=basePath%>pfs/paramConfig_pfsParamPage',
				['mob_param_id','default_flag','branch',
				 'timeing_shutdown','timeing_timeout','mob_pwd',//'doublescreen_id',
				 'label_content','label_text_font','label_text_style','label_text_size','label_text_color'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'填单机参数ID',dataIndex:'mob_param_id',width:100},
				{header:'是否默认标识',dataIndex:'default_flag',width:90,renderer:function(value){
					return value=='1'?'默认':'非默认';}},
				{header:'机构号',dataIndex:'branch',width:100},
				
				{header:'定时关机时间',dataIndex:'timeing_shutdown',width:90,hidden:true},
				{header:'页面超时返回时间',dataIndex:'timeing_timeout',width:110},
				{header:'手持设置界面密码',dataIndex:'mob_pwd',width:100},
				
				{header:'标签内容',dataIndex:'label_content',width:120},
				{header:'标签字体名称',dataIndex:'label_text_font',width:100,renderer:function(value){
					value = value.toString();
					return getDictValue(value,labelTextFontStore,'key','value');//翻译字段方法
       			 }},
				{header:'标签字体类型',dataIndex:'label_text_style',width:100,renderer:function(value){
					value = value.toString();
					return getDictValue(value,labelTextStyleStore,'key','value');//翻译字段方法
       			 }},
				{header:'标签字体大小',dataIndex:'label_text_size',width:80},
				{header:'标签字体颜色',dataIndex:'label_text_color',width:100}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加预填单机参数配置', 400, 370, 180, 1,
				[
					{colIndex:0, field:{xtype : 'textfield',width:200, name:'timeing_shutdown', id:'timeing_shutdown_add', 	fieldLabel:'定时关机时间',	allowBlank : false,blankText:'请输入定时关机时间(例19:12)',maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^([0]|(([0-1][0-9])|([2][0-3])):([0-5][0-9]))$/,regexText:'请输入正确的时间(例19:12或0)',emptyText:'输入格式: hh:mm或0',value:'0',hidden:true}},
					{colIndex:0, field:{xtype : 'textfield',width:200, name:'timeing_timeout', id:'timeing_timeout_add', 	fieldLabel:'页面超时锁屏时间',	allowBlank : false,blankText:'请输入页面超时锁屏时间',maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^[0-9]*$/,regexText:'密码只能输入数字'}},
					{colIndex:0, field:{xtype : 'textfield',width:200, name:'mob_pwd', id:'mob_pwd_add', 	fieldLabel:'手持设置界面密码',	allowBlank : false,blankText:'请输入手持设置界面密码',maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^[0-9]*$/,regexText:'密码只能输入数字'}},
					{
						colIndex:0, 
						hideLabel:true,
						field:
						{
							width:330,
							autoHeight:true,
							xtype:'fieldset',
							labelWidth:100,
							title:'填单机界面提示标签',
							items:[
								{xtype : 'textfield',width:200, name:'label_content',id:'label_content_add', 	fieldLabel:'标签内容',maxLength : 80,maxLengthText : '长度不能大于80位'},
								{xtype : 'combo',width:200, name:'label_text_font',id:'label_text_font_add',triggerAction : 'all',mode: 'local',	fieldLabel:'标签字体名称',hiddenName : 'label_text_font',editable:false, store:labelTextFontStore, 	displayField:'value', valueField:'key'},
								{xtype : 'combo',width:200, name:'label_text_style',id:'label_text_style_add',triggerAction : 'all',mode: 'local', 	fieldLabel:'标签字体类型',hiddenName : 'label_text_style' ,editable:false, store:labelTextStyleStore,	displayField:'value', valueField:'key'},
								{xtype : 'combo',width:200, name:'label_text_size',id:'label_text_size_add',triggerAction : 'all',mode: 'local', 	fieldLabel:'标签字体大小',hiddenName : 'label_text_size' ,editable:false, store:labelTextSizeStore,	displayField:'value', valueField:'key'},
								{xtype:'textfield',width:200,name:'label_text_color',id:'label_text_color_add',fieldLabel:'字体颜色',style:'background:#000000',readOnly:true,listeners:{'focus': function(){showColor('label_text_color_add');}},width:20,height:20}
							]
						}
					},
					{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,     name:'default_flag',id:'default_flag_add', boxLabel:'设为默认',hiddenName:'default_flag'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : searchDetails},
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();colorWin.hide();}}
				],'left',110
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑填单机参数信息', 400, 370, 180, 1,
			   [
				{colIndex:0, field:{xtype : 'hidden',width:200, name:'mob_param_id', 	fieldLabel:'填单机参数ID', 	     readOnly : true}},
				{colIndex:0, field:{xtype : 'hidden',width:200, name:'branch', 	fieldLabel:'机构号',	readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield',width:200, name:'timeing_shutdown',id:'timeing_shutdown_edit', 	fieldLabel:'定时关机时间',	allowBlank : false,blankText:'请输入定时关机时间(例19:12)',maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^([0]|(([0-1][0-9])|([2][0-3])):([0-5][0-9]))$/,regexText:'请输入正确的时间(例19:12或0)',emptyText:'输入格式: hh:mm或0',hidden:true}},
				{colIndex:0, field:{xtype : 'textfield',width:200, name:'timeing_timeout', id:'timeing_timeout_edit', 	fieldLabel:'页面超时锁屏时间',	allowBlank : false,blankText:'请输入页面超时锁屏时间',maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^[0-9]*$/,regexText:'密码只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield',width:200, name:'mob_pwd',id:'mob_pwd_edit', 	fieldLabel:'手持设置界面密码',	allowBlank : false,blankText:'请输入手持设置界面密码',maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^[0-9]*$/,regexText:'密码只能输入数字'}},
				{
					colIndex:0,
					hideLabel:true,
					field:
					{
						width:330,
						autoHeight:true,
						xtype:'fieldset',
						labelWidth:100,
						title:'填单机界面提示标签',
						items:[
							{xtype : 'textfield',width:200, name:'label_content',id:'label_content_edit', 	fieldLabel:'标签内容',maxLength : 80,maxLengthText : '长度不能大于80位'},
							{xtype : 'combo',width:200, name:'label_text_font',id:'label_text_font_edit',triggerAction : 'all',mode: 'local',	fieldLabel:'标签字体名称',hiddenName : 'label_text_font',editable:false, store:labelTextFontStore, 	displayField:'value', valueField:'key'},
							{xtype : 'combo',width:200, name:'label_text_style',id:'label_text_style_edit',triggerAction : 'all',mode: 'local', 	fieldLabel:'标签字体类型',hiddenName : 'label_text_style',editable:false, store:labelTextStyleStore,	displayField:'value', valueField:'key'},
							{xtype : 'combo',width:200, name:'label_text_size',id:'label_text_size_edit',triggerAction : 'all',mode: 'local', 	fieldLabel:'标签字体大小',hiddenName : 'label_text_size',editable:false, store:labelTextSizeStore,	displayField:'value', valueField:'key'},
							{xtype:'textfield',width:200,name:'label_text_color',id:'label_text_color_edit',fieldLabel:'字体颜色',style:'background:#000000',readOnly:true,listeners:{'focus': function(){showColor('label_text_color_edit');}},width:20,height:20}
						]
					}
				},
				{colIndex:0, field:{xtype : 'checkbox',hideLabel : true,     name:'default_flag',id:'default_flag_edit', boxLabel:'设置为默认',hiddenName:'default_flag'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : searchDetails},
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();colorWin.hide();}}
				],'left',110
			);
			
			/**
			 * 上级机构参数弹出窗
			 */
			function searchDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['mob_param_id','branch','default_flag',
							 'timeing_shutdown','timeing_timeout','mob_pwd',
							 'label_content','label_text_font','label_text_style','label_text_size','label_text_color']
			    });
			    var detailData = [
					{header:'填单机参数ID',dataIndex:'mob_param_id',width:100},
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
						return value=='1'?'默认':'非默认';}},
					{header:'定时关机时间',dataIndex:'timeing_shutdown',width:100},
					{header:'页面超时返回时间',dataIndex:'timeing_timeout',width:100},
					{header:'填单机设置密码',dataIndex:'mob_pwd',width:100},
					{header:'标签内容',dataIndex:'label_content',width:200},
					{header:'标签字体名称',dataIndex:'label_text_font',width:100,renderer:function(value){
						value = value.toString();
						return getDictValue(value,labelTextFontStore,'key','value');//翻译字段方法
	       			 }},
					{header:'标签字体类型',dataIndex:'label_text_style',width:100,renderer:function(value){
						value = value.toString();
						return getDictValue(value,labelTextStyleStore,'key','value');//翻译字段方法
	       			 }},
					{header:'标签字体大小',dataIndex:'label_text_size',width:100},
					{header:'标签字体颜色',dataIndex:'label_text_color',width:100}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('searchDetailsWindow','上级机构填单机参数列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'上级填单机参数信息',
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
			  			
			  			Ext.getCmp('timeing_shutdown_add').setValue(record.data['timeing_shutdown']);
			  			Ext.getCmp('timeing_shutdown_edit').setValue(record.data['timeing_shutdown']);
			  			/*Ext.getCmp('timeing_timeout_add').setValue(record.data['timeing_timeout']);
			  			Ext.getCmp('timeing_timeout_edit').setValue(record.data['timeing_timeout']);*/
			  			Ext.getCmp('timeing_timeout_add').setValue(record.data['timeing_timeout']);
			  			Ext.getCmp('timeing_timeout_edit').setValue(record.data['timeing_timeout']);
			  			Ext.getCmp('mob_pwd_add').setValue(record.data['mob_pwd']);
			  			Ext.getCmp('mob_pwd_edit').setValue(record.data['mob_pwd']);
			  			Ext.getCmp('default_flag_add').setValue(record.data['default_flag']);
			  			Ext.getCmp('default_flag_edit').setValue(record.data['default_flag']);
			  			
			  			
			  			Ext.getCmp('label_content_add').setValue(record.data['label_content']);
			  			Ext.getCmp('label_content_edit').setValue(record.data['label_content']);
			  			Ext.getCmp('label_text_size_add').setValue(record.data['label_text_size']);
			  			Ext.getCmp('label_text_size_edit').setValue(record.data['label_text_size']);
			  			Ext.getCmp('label_text_style_add').setValue(record.data['label_text_style']);
			  			Ext.getCmp('label_text_style_edit').setValue(record.data['label_text_style']);
			  			Ext.getCmp('label_text_font_add').setValue(record.data['label_text_font']);
			  			Ext.getCmp('label_text_font_edit').setValue(record.data['label_text_font']);
			  			
			  			fontCol=record.data['label_text_color'];//把颜色的值保存起来，传给后台处理
			  			if(fontCol!=''){
			  				var color='#'+fontCol;
				  			Ext.getDom('label_text_color_add').style.background=color;
				  			Ext.getDom('label_text_color_edit').style.background=color;
			  			}
			  			
			  		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '4';
			    requestAjax('<%=basePath%>pfs/paramConfig_queryPfsParam.action',submitDetail, function(sRet){
			    	detailStore.loadData(sRet.field1);
				});
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
			 * @Description:触发"查询"，"重置"，"添加"，"修改"，"删除"的选择语句
			 */
			function onButtonClicked(btn_index){
				switch(btn_index){
					case 0:
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '3';//查询本机构及上级所有
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
					    	if(records[i].data.branch!=<%="'"+brno+"'"%>){
					    		Ext.MessageBox.alert('<s:text name="common.info.title"/>','无法修改非本机构的配置');
					    		return;
					    	}
					    }
						editwindow.open();
						fontCol=records[0].data['label_text_color'];//把颜色的值保存起来，传给后台处理
						records[0].data['label_text_color'] = '';
			  			if(fontCol!=''){
			  				var color='#'+fontCol;
				  			Ext.getDom('label_text_color_edit').style.background=color;
			  			}
						editwindow.updateFields(records[0]);
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['mob_param_id','branch']);
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
								requestAjax('<%=basePath%>pfs/paramConfig_delPfsParam',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										query_obj['query_rules'] = '3';
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
				submitData['label_text_color'] = fontCol;
				if(submitData['label_content']==''){
					submitData['label_text_color'] = '';
					submitData['label_text_size'] = '';
					submitData['label_text_style'] = '';
					submitData['label_text_font'] = '';
				}
				createCheckBoxValue('default_flag',submitData);
				requestAjax('<%=basePath%>pfs/paramConfig_addPfsParam', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '3';
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
					colorWin.hide();
				});
			}
			/**
			 * @Title:onButtonClicked
			 * @Description:点击修改时的操作
			 */
			function oneditclicked(){
				var submitData = editwindow.getFields();
				submitData['label_text_color'] = fontCol;
				if(submitData['label_content']==''){
					submitData['label_text_color'] = '';
					submitData['label_text_size'] = '';
					submitData['label_text_style'] = '';
					submitData['label_text_font'] = '';
				}
				createCheckBoxValue('default_flag',submitData);
				requestAjax('<%=basePath%>pfs/paramConfig_editPfsParam',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '3';
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
					colorWin.hide();
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
  
  <body>
	<div id="pfsConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="editWindow"></div>
	<div id="searchDetailsWindow"></div>
  </body>
</html>
