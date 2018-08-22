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
    
    <title>排队机参数配置</title>
    
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
		
		function submitTheme(id,name,note,list){
			var iWidth=705; //模态窗口宽度
			var iHeight=535;//模态窗口高度
			var iTop=(window.screen.height-iHeight)/2-50;
			var iLeft=(window.screen.width-iWidth)/2;
			var url = '<%=basePath%>webpages/abt/configManager/themeManagerDetail.jsp';
			var param = '?id=' + encodeURI(id) + '&name=' + encodeURI(encodeURI(name)) + '&list=' 
					+ encodeURI(encodeURI(list)) + '&note=' + encodeURI(encodeURI(note));
			var params = 'Scrollbars=yes,Toolbar=no,Location=no,Direction=no,Resizeable=no,Width='
						 + iWidth + ',Height=' + iHeight + ',top=' + iTop + ',left=' + iLeft;
			//window.open(url+param,name,params);
			window.open('','主题展示',params); 
			/**/
			var tempForm = document.createElement('form');  
            tempForm.id='tempForm1';
            tempForm.method='post';
            tempForm.action=url;  
            tempForm.target='主题展示';  
             
            var hideInput = document.createElement('input');  
            hideInput.type='hidden';  
            hideInput.name= 'id'
            hideInput.value= id;
            tempForm.appendChild(hideInput);  
             
            var hideInput = document.createElement('input');  
            hideInput.type='hidden';  
            hideInput.name= 'name'
            hideInput.value= name;
            tempForm.appendChild(hideInput);  
             
            var hideInput = document.createElement('input');  
            hideInput.type='hidden';  
            hideInput.name= 'list'
            hideInput.value= list;
            tempForm.appendChild(hideInput);  
             
            var hideInput = document.createElement('input');  
            hideInput.type='hidden';  
            hideInput.name= 'note'
            hideInput.value= note.trim();
            tempForm.appendChild(hideInput);  
    
            tempForm.appendChild(hideInput);  
            document.body.appendChild(tempForm);  
            tempForm.submit();
            document.body.removeChild(tempForm);
		}
/**
 * @Title: loadPage 
 * @Description: 显示排队机参数维护界面
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
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '排队机参数配置', 120, 0,
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
				'<%=basePath%>confManager/machineParam_queryQMParamPage',
				['qm_param_id','default_flag','branch','bs_servicetime_status','ticket_style_id','theme_id','theme_status',
				 'timeing_shutdown','cfg_pwd','show_clock','call_voice','isprecontract',//'doublescreen_id',
				 'show_bs_waitnum','label_content','label_text_font','label_text_style','label_text_size','label_text_color'],
				[
				{header:'全选'},
				{header:'复选框'},
				{header:'排队机参数编号',dataIndex:'qm_param_id',width:100},
				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
					return value=='1'?'默认':'非默认';}},
				{header:'机构号',dataIndex:'branch',width:100},
				//{header:'二层屏编号',dataIndex:'doublescreen_id',width:100},
				{header:'小票模板编号',dataIndex:'ticket_style_id',width:100},
				{header:'主题编号',dataIndex:'theme_id',width:100},
				//{header:'是否启用主题',dataIndex:'theme_status',width:100},
				{header:'定时关机时间',dataIndex:'timeing_shutdown',width:100},
				{header:'排队机设置密码',dataIndex:'cfg_pwd',width:100},
				{header:'是否启用业务服务时间',dataIndex:'bs_servicetime_status',width:150,renderer:function(value){return value=='1'?'是':'否'}},
				{header:'是否显示时钟',dataIndex:'show_clock',width:100,renderer:function(value){return value=='1'?'是':'否'}},
				{header:'语音呼叫设置',dataIndex:'call_voice',width:150},
				//{header:'是否短信通知客户',dataIndex:'sms_customer',width:150,renderer:function(value){return value=='1'?'是':'否'}},
				//{header:'是否支持预约',dataIndex:'isprecontract',width:100},
				{header:'是否显示业务等待人数',dataIndex:'show_bs_waitnum',width:200,renderer:function(value){return value=='1'?'是':'否'}},
				
				
				
				{header:'标签内容',dataIndex:'label_content',width:200},
				{header:'标签字体大小',dataIndex:'label_text_size',width:100},
				{header:'标签字体颜色',dataIndex:'label_text_color',width:100},
				{header:'标签字体名称',dataIndex:'label_text_font',width:100,renderer:function(value){
					value = value.toString();
					return getDictValue(value,labelTextFontStore,'key','value');//翻译字段方法
       			 }},
				{header:'标签字体类型',dataIndex:'label_text_style',width:140,renderer:function(value){
					value = value.toString();
					return getDictValue(value,labelTextStyleStore,'key','value');//翻译字段方法
       			 }} 
       				], 
				'<s:text name="common.pagequery.pagingtool"/>'
			);
			//当选择"添加"时，弹出的窗口
			addwindow = new SelfFormWindow('addWindow', '添加排队机参数信息', 610, 420, 140, 2,
				[
					//{colIndex:0, field:{layout:'column',fieldLabel:'二层屏编号',items:[{xtype : 'textfield', name:'doublescreen_id', id:'doublescreen_id_add',	fieldLabel:'二层屏编号',readOnly:true,allowBlank : true,blankText:'请选择二层屏编号',maxLength : 20,emptyText:'请选择二层屏信息',maxLengthText : '长度不能大于20位',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){screenDetails();}}}]}},
					{colIndex:0, field:{layout:'column',fieldLabel:'小票模板编号',items:[{xtype : 'textfield', name:'ticket_style_id', id:'ticket_style_id_add',	fieldLabel:'小票模板编号',readOnly:true,allowBlank : true,blankText:'请选择小票模板编号',maxLength : 20,emptyText:'请选择小票模板',maxLengthText : '长度不能大于20位',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){ticketDetails();}}}]}},
					{colIndex:0, field:{layout:'column',fieldLabel:'主题编号',items:[{xtype : 'textfield', name:'theme_id',id:'theme_id_add', 	fieldLabel:'主题编号',readOnly:true,blankText:'请选择主题编号',emptyText:'请选择主题编号',maxLength : 20,maxLengthText : '长度不能大于20位',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){themeDetails();}}}]}},
					{colIndex:0, field:{xtype : 'textfield', name:'timeing_shutdown', id:'timeing_shutdown_add', 	fieldLabel:'定时关机时间',	allowBlank : false,blankText:'请输入定时关机时间(例19:12)',maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^([0]|(([0-1][0-9])|([2][0-3])):([0-5][0-9]))$/,regexText:'请输入正确的时间(例19:12或0)',emptyText:'输入格式: hh:mm或0'}},
					{colIndex:0, field:{xtype : 'textfield', name:'cfg_pwd', id:'cfg_pwd_add', 	fieldLabel:'排队机设置密码',	allowBlank : false,blankText:'请输入排队机密码',value:'1',maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^[0-9]*$/,regexText:'密码只能输入数字'}},
					{colIndex:0, field:{xtype : 'textfield', name:'call_voice', id:'call_voice_add', fieldLabel:'语音呼叫设置',allowBlank : false,blankText : '请输入语音呼叫设置',regex:/^[0-2]*$/,regexText:'请输入正确的设置参数',maxLength : 10,maxLengthText : '长度不能大于10位'}},
					{colIndex:0, field:{width:250,html:'<font style="color:red">预设值:0-普通话,1-英语,2-粤语<br>(例:00为2次普通话,021为普通话+粤语+英语)</font>'}},
					
					
					{
						colIndex:0, 
						hideLabel:true,
						field:
						{
							width:255,
							autoHeight:true,
							xtype:'fieldset',
							labelWidth:100,
							title:'排队机界面提示标签',
							items:[
								{xtype : 'textfield',width:125, name:'label_content',id:'label_content_add', 	fieldLabel:'标签内容',maxLength : 80,maxLengthText : '长度不能大于80位'},
								{xtype : 'combo',width:125, name:'label_text_font',id:'label_text_font_add',triggerAction : 'all',mode: 'local',	fieldLabel:'标签字体名称',hiddenName : 'label_text_font',editable:false, store:labelTextFontStore, 	displayField:'value', valueField:'key'},
								{xtype : 'combo',width:125, name:'label_text_style',id:'label_text_style_add',triggerAction : 'all',mode: 'local', 	fieldLabel:'标签字体类型',hiddenName : 'label_text_style' ,editable:false, store:labelTextStyleStore,	displayField:'value', valueField:'key'},
								{xtype : 'combo',width:125, name:'label_text_size',id:'label_text_size_add',triggerAction : 'all',mode: 'local', 	fieldLabel:'标签字体大小',hiddenName : 'label_text_size' ,editable:false, store:labelTextSizeStore,	displayField:'value', valueField:'key'},
								{xtype:'textfield',width:125,name:'label_text_color1',id:'label_text_color_add',fieldLabel:'字体颜色',style:'background:#000000',readOnly:true,listeners:{'focus': function(){showColor('label_text_color_add');}},width:20,height:20}
							]
						}
					}, 
					
					
					{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'default_flag',id:'default_flag_add', boxLabel:'设为默认',hiddenName:'default_flag'}},
					{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'show_bs_waitnum',id:'show_bs_waitnum_add', boxLabel:'显示业务等待人数',hiddenName:'show_bs_waitnum',checked : true}},
					//{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'theme_status', boxLabel:'启用主题',hiddenName:'theme_status'}},
					{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'bs_servicetime_status',id:'bs_servicetime_status_add', boxLabel:'启用业务服务时间',checked : true,hiddenName:'bs_servicetime_status'}},
					{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'show_clock',id:'show_clock_add', boxLabel:'显示时钟',hiddenName:'show_clock',checked : true}}
					//{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'sms_customer',id:'sms_customer_add', boxLabel:'短信通知客户',hiddenName:'sms_customer'}}
					//{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'isprecontract', boxLabel:'支持预约',hiddenName:'isprecontract'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : searchDetails},
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();colorWin.hide();}}
				],'left',110
			);
			
			//选择"修改"时，弹出的窗口
			editwindow = new SelfFormWindow('editWindow', '编辑排队机参数信息', 610, 420, 140, 2,
			   [
				{colIndex:0, field:{xtype : 'hidden', name:'qm_param_id', 	fieldLabel:'排队机参数编号', 	     readOnly : true}},
				{colIndex:0, field:{xtype : 'hidden', name:'branch', 	fieldLabel:'机构号',	readOnly:true}},
				//{colIndex:0, field:{layout:'column',fieldLabel:'二层屏编号',items:[{xtype : 'textfield', name:'doublescreen_id', id:'doublescreen_id_edit',	fieldLabel:'二层屏编号',readOnly:true,allowBlank : true,blankText:'请选择二层屏编号',maxLength : 20,emptyText:'请选择二层屏信息',maxLengthText : '长度不能大于20位',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){screenDetails();}}}]}},
				{colIndex:0, field:{layout:'column',fieldLabel:'小票模板编号',items:[{xtype : 'textfield', name:'ticket_style_id', id:'ticket_style_id_edit',	fieldLabel:'小票模板编号',readOnly:true,allowBlank : true,blankText:'请输入小票模板编号',maxLength : 20,emptyText:'请选择小票模板',maxLengthText : '长度不能大于20位',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){ticketDetails();}}}]}},
				{colIndex:0, field:{layout:'column',fieldLabel:'主题编号',items:[{xtype : 'textfield', name:'theme_id',id:'theme_id_edit', 	fieldLabel:'主题编号',readOnly:true,blankText:'请选择主题编号',emptyText:'请选择主题编号',maxLength : 20,maxLengthText : '长度不能大于20位',width:'100'},{xtype:'button',text:'选择',width:40,listeners:{"click":function(){themeDetails();}}}]}},
				{colIndex:0, field:{xtype : 'textfield', name:'timeing_shutdown',id:'timeing_shutdown_edit', 	fieldLabel:'定时关机时间',	allowBlank : false,blankText:'请输入定时关机时间(例19:12)',maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^([0]|(([0-1][0-9])|([2][0-3])):([0-5][0-9]))$/,regexText:'请输入正确的时间(例19:12或0)',emptyText:'输入格式: hh:mm或0'}},
				{colIndex:0, field:{xtype : 'textfield', name:'cfg_pwd',id:'cfg_pwd_edit', 	fieldLabel:'排队机设置密码',	allowBlank : false,blankText:'请输入排队机密码',maxLength : 20,maxLengthText : '长度不能大于20位',regex:/^[0-9]*$/,regexText:'密码只能输入数字'}},
				{colIndex:0, field:{xtype : 'textfield',     name:'call_voice',id:'call_voice_edit', fieldLabel:'语音呼叫设置',allowBlank : false,blankText : '请输入语音呼叫设置',regex:/^[0-2]*$/,regexText:'请输入正确的设置参数',maxLength : 10,maxLengthText : '长度不能大于10位'}},
				{colIndex:0, field:{width:250,html:'<font style="color:red">预设值:0-普通话,1-英语,2-粤语<br>(例:00为2次普通话,021为普通话+粤语+英语)</font>'}},
				
				
				{
					colIndex:0,
					hideLabel:true,
					field:
					{
						width:255,
						autoHeight:true,
						xtype:'fieldset',
						labelWidth:100,
						title:'排队机界面提示标签',
						items:[
							{xtype : 'textfield',width:125, name:'label_content',id:'label_content_edit', 	fieldLabel:'标签内容',maxLength : 80,maxLengthText : '长度不能大于80位'},
							{xtype : 'combo',width:125, name:'label_text_font',id:'label_text_font_edit',triggerAction : 'all',mode: 'local',	fieldLabel:'标签字体名称',hiddenName : 'label_text_font',editable:false, store:labelTextFontStore, 	displayField:'value', valueField:'key'},
							{xtype : 'combo',width:125, name:'label_text_style',id:'label_text_style_edit',triggerAction : 'all',mode: 'local', 	fieldLabel:'标签字体类型',hiddenName : 'label_text_style',editable:false, store:labelTextStyleStore,	displayField:'value', valueField:'key'},
							{xtype : 'combo',width:125, name:'label_text_size',id:'label_text_size_edit',triggerAction : 'all',mode: 'local', 	fieldLabel:'标签字体大小',hiddenName : 'label_text_size',editable:false, store:labelTextSizeStore,	displayField:'value', valueField:'key'},
							{xtype:'textfield',width:125,name:'label_text_color1',id:'label_text_color_edit',fieldLabel:'字体颜色',style:'background:#000000',readOnly:true,listeners:{'focus': function(){showColor('label_text_color_edit');}},width:20,height:20}
						]
					}
				},
				 
				
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'default_flag',id:'default_flag_edit', boxLabel:'设为默认',hiddenName:'default_flag'}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'show_bs_waitnum',id:'show_bs_waitnum_edit', boxLabel:'显示业务等待人数',hiddenName:'show_bs_waitnum'}},
				//{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'theme_status', boxLabel:'启用主题',hiddenName:'theme_status'}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'bs_servicetime_status',id:'bs_servicetime_status_edit', boxLabel:'启用业务服务时间',hiddenName:'bs_servicetime_status'}},
				{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'show_clock',id:'show_clock_edit', boxLabel:'显示时钟',hiddenName:'show_clock'}}
				//{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'sms_customer',id:'sms_customer_edit', boxLabel:'短信通知客户',hiddenName:'sms_customer'}}
				//{colIndex:1, field:{xtype : 'checkbox',hideLabel : true,     name:'isprecontract', boxLabel:'支持预约',hiddenName:'isprecontract'}}
				],
				[
					{text:'<s:text name="复制上级机构配置"/>', 	handler : searchDetails},
					{text:'<s:text name="common.button.edit"/>', 	handler : oneditclicked, 	formBind:true},
					{text:'<s:text name="common.button.cancel"/>', 	handler: function(){editwindow.close();colorWin.hide();}}
				],'left',110
			);
			/**
			 * 主题弹出窗
			 */
			function themeDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['theme_id','theme_name','theme_note','theme_imgsrc_list','branch','default_flag']
			    });
			    var detailData = [
    				{header:'主题编号',dataIndex:'theme_id',width:100},
    				{header:'主题名称',dataIndex:'theme_name',width:120},
    				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
    					if(value!=""){
    						return value=='0'?'默认':'非默认';
    					}else{
    						return value;
    					}
    				}},
    				{header:'所属机构',dataIndex:'branch',width:80},
    				{header:'主题预览',dataIndex:'',width:100,align:'center',renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
    					if(record.data['theme_id']=='默认'){
    						return '';
    					}
    					return "<A href='javascript:void(0)' onclick=\"submitTheme('"+record.data['theme_id']+"','"+record.data['theme_name']+"','"+record.data['theme_note']+"','"+record.data['theme_imgsrc_list']+"');return false;\">主题预览</A>";
    				}}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('themeDetailsWindow','主题列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'主题信息',
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
			  			if(record.data['theme_id']!='默认'){
			  				Ext.getCmp('theme_id_add').setValue(record.data['theme_id']);
				  			Ext.getCmp('theme_id_edit').setValue(record.data['theme_id']);
			  			}else{
			  				Ext.getCmp('theme_id_add').setValue('');
				  			Ext.getCmp('theme_id_edit').setValue('');
			  			}
			  		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    requestAjax('<%=basePath%>/confManager/themeManager_queryTheme',submitDetail, function(sRet){
			    	sRet.field1.unshift({'theme_id':'默认'});
			    	detailStore.loadData(sRet.field1);
				});
			}
			
			/**
			 * 小票模板弹出窗
			 */
			function ticketDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['branch','ticket_style_id','default_flag','style_content']
			    });
			    var detailData = [
      				{header:'机构号',dataIndex:'branch',width:100},
    				{header:'小票模板编号',dataIndex:'ticket_style_id',width:100},
    				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
    					if(value!=""){
    						return value=='1'?'默认':'非默认';
    					}else{
    						return value;
    					}
    				}},
    				{header:'模板内容',dataIndex:'style_content',width:500}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('ticketDetailsWindow','小票模板编号列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'小票模板信息',
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
			  			if(record.data['ticket_style_id']!='默认'){
			  				Ext.getCmp('ticket_style_id_add').setValue(record.data['ticket_style_id']);
				  			Ext.getCmp('ticket_style_id_edit').setValue(record.data['ticket_style_id']);
			  			}else{
			  				Ext.getCmp('ticket_style_id_add').setValue('');
				  			Ext.getCmp('ticket_style_id_edit').setValue('');
			  			}
			  		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '4';
			    requestAjax('<%=basePath%>/confManager/ticket_queryTicket',submitDetail, function(sRet){
			    	sRet.field1.unshift({'ticket_style_id':'默认'});
			    	detailStore.loadData(sRet.field1);
				});
			}
			
			function screenDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['branch','doublescreen_id','default_flag','content']
			    });
			    var detailData = [
					{header:'机构号',dataIndex:'branch',width:100},
					{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
						return value=='1'?'默认':'非默认';}},
					{header:'二层屏编号',dataIndex:'doublescreen_id',width:100},
					{header:'二层屏显示内容',dataIndex:'content',width:600}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('screenDetailsWindow','二层屏参数配置编号列表',600, 355, 555, 1, [555],[{
		    		colIndex:0,
		    		field:{
		    			xtype:'fieldset',
		    			title:'二层屏参数配置信息',
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
	    			Ext.getCmp('doublescreen_id_add').setValue(record.data['doublescreen_id']);
	    			Ext.getCmp('doublescreen_id_edit').setValue(record.data['doublescreen_id']);
	    			//返回值
	    		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '4';
			    requestAjax('<%=basePath%>/confManager/doubleScreenDisplay_queryScreen',submitDetail, function(sRet){
			    	sRet.field1.unshift({'qm_param_id':'默认'});
			    	detailStore.loadData(sRet.field1);
				});
			}
			
			/**
			 * 上级机构参数弹出窗
			 */
			function searchDetails(){
			    var detailStore = new Ext.data.JsonStore({
			    	fields:['qm_param_id','default_flag','branch','bs_servicetime_status','ticket_style_id','theme_id','theme_status',
							 'timeing_shutdown','cfg_pwd','show_clock','call_voice','isprecontract',//'doublescreen_id',
							 'show_bs_waitnum','label_content','label_text_font','label_text_style','label_text_size','label_text_color']
			    });
			    var detailData = [
					{header:'排队机参数编号',dataIndex:'qm_param_id',width:100},
				{header:'是否默认标识',dataIndex:'default_flag',width:100,renderer:function(value){
					return value=='1'?'默认':'非默认';}},
				{header:'机构号',dataIndex:'branch',width:100},
				//{header:'二层屏编号',dataIndex:'doublescreen_id',width:100},
				{header:'小票模板编号',dataIndex:'ticket_style_id',width:100},
				{header:'主题编号',dataIndex:'theme_id',width:100},
				//{header:'是否启用主题',dataIndex:'theme_status',width:100},
				{header:'定时关机时间',dataIndex:'timeing_shutdown',width:100},
				{header:'排队机设置密码',dataIndex:'cfg_pwd',width:100},
				{header:'是否启用业务服务时间',dataIndex:'bs_servicetime_status',width:150,renderer:function(value){return value=='1'?'是':'否'}},
				{header:'是否显示时钟',dataIndex:'show_clock',width:100,renderer:function(value){return value=='1'?'是':'否'}},
				{header:'语音呼叫设置',dataIndex:'call_voice',width:150},
				//{header:'是否短信通知客户',dataIndex:'sms_customer',width:150,renderer:function(value){return value=='1'?'是':'否'}},
				//{header:'是否支持预约',dataIndex:'isprecontract',width:100},
				{header:'是否显示业务等待人数',dataIndex:'show_bs_waitnum',width:200,renderer:function(value){return value=='1'?'是':'否'}},
				{header:'标签内容',dataIndex:'label_content',width:200},
				{header:'标签字体大小',dataIndex:'label_text_size',width:100},
				{header:'标签字体颜色',dataIndex:'label_text_color',width:100},
				{header:'标签字体名称',dataIndex:'label_text_font',width:100,renderer:function(value){
					value = value.toString();
					return getDictValue(value,labelTextFontStore,'key','value');//翻译字段方法
       			 }},
				{header:'标签字体类型',dataIndex:'label_text_style',width:100,renderer:function(value){
					value = value.toString();
					return getDictValue(value,labelTextStyleStore,'key','value');//翻译字段方法
       			 }}
				];
			    var detailColModel=new Ext.grid.ColumnModel(detailData);
			    var detailsWindow = new SelfFormWindowSetWidth('searchDetailsWindow','上级机构排队机参数列表',600, 355, 555, 1, [555],[{
			   		colIndex:0,
			   		field:{
			   			xtype:'fieldset',
			   			title:'上级排队机参数信息',
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
			  			Ext.getCmp('ticket_style_id_add').setValue(record.data['ticket_style_id']);
			  			Ext.getCmp('ticket_style_id_edit').setValue(record.data['ticket_style_id']);
			  			Ext.getCmp('timeing_shutdown_add').setValue(record.data['timeing_shutdown']);
			  			Ext.getCmp('timeing_shutdown_edit').setValue(record.data['timeing_shutdown']);
			  			Ext.getCmp('cfg_pwd_add').setValue(record.data['cfg_pwd']);
			  			Ext.getCmp('cfg_pwd_edit').setValue(record.data['cfg_pwd']);
			  			Ext.getCmp('call_voice_add').setValue(record.data['call_voice']);
			  			Ext.getCmp('call_voice_edit').setValue(record.data['call_voice']);
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
			  			
			  			Ext.getCmp('show_bs_waitnum_add').setValue(record.data['show_bs_waitnum']);
			  			Ext.getCmp('show_bs_waitnum_edit').setValue(record.data['show_bs_waitnum']);
			  			Ext.getCmp('bs_servicetime_status_add').setValue(record.data['bs_servicetime_status']);
			  			Ext.getCmp('bs_servicetime_status_edit').setValue(record.data['bs_servicetime_status']);
			  			Ext.getCmp('show_clock_add').setValue(record.data['show_clock']);
			  			Ext.getCmp('show_clock_edit').setValue(record.data['show_clock']);
			  			//Ext.getCmp('sms_customer_add').setValue(record.data['sms_customer']);
			  			//Ext.getCmp('sms_customer_edit').setValue(record.data['sms_customer']);
			  		});
			    
			    detailsWindow.open();
			    var submitDetail= {};
			    submitDetail['query_rules'] = '5';
			    requestAjax('<%=basePath%>/confManager/machineParam_queryQMParam.action',submitDetail, function(sRet){
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
					    var submitdata = pagequeryObj.getSelectedObjects(['qm_param_id','branch']);
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
								requestAjax('<%=basePath%>confManager/machineParam_delQMParam',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
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
				createCheckBoxValue('show_bs_waitnum',submitData);
				createCheckBoxValue('theme_status',submitData);
				createCheckBoxValue('bs_servicetime_status',submitData);
				createCheckBoxValue('show_clock',submitData);
				createCheckBoxValue('sms_customer',submitData);
				createCheckBoxValue('isprecontract',submitData);
				requestAjax('<%=basePath%>confManager/machineParam_addQMParam', submitData,
				function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '4';
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
				createCheckBoxValue('show_bs_waitnum',submitData);
				createCheckBoxValue('theme_status',submitData);
				createCheckBoxValue('bs_servicetime_status',submitData);
				createCheckBoxValue('show_clock',submitData);
				createCheckBoxValue('sms_customer',submitData);
				createCheckBoxValue('isprecontract',submitData);
				requestAjax('<%=basePath%>confManager/machineParam_editQMParam',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						query_obj['query_rules'] = '4';
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
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
	<div id="editWindow"></div>
	<div id="screenDetailsWindow"></div>
	<div id="ticketDetailsWindow"></div>
	<div id="themeDetailsWindow"></div>
	<div id="searchDetailsWindow"></div>
  </body>
</html>