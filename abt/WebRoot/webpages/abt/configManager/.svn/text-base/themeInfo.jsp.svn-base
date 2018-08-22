<%@ page language="java"  pageEncoding="utf-8"%>
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
    
    <title>排队机主题配置</title>
    
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
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
	  var pagereturn=${actionresult};
	  
	  //保存字体名称的store
	var FontNamestore= new Ext.data.JsonStore({
		fields : ['dataValue','dataName'],
		url    : '<%=basePath%>/system/commonselect_getSelectCodes.action?item_id=fontName',
		root   : 'field1', autoLoad:true
	});
	//保存字体类型的store
	var FontTypestore= new Ext.data.JsonStore({
		fields : ['dataValue','dataName'],
		url    : '<%=basePath%>/system/commonselect_getSelectCodes.action?item_id=fontType',
		root   : 'field1', autoLoad:true
	});
	//保存字体大小的store
	var FontSizestore= new Ext.data.JsonStore({
		fields : ['dataValue','dataName'],
		url    : '<%=basePath%>/system/commonselect_getSelectCodes.action?item_id=fontSize',
		root   : 'field1', autoLoad:true
	});
	  
	  Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
	  Ext.QuickTips.init();
	  Ext.onReady(loadPage);
	  
	  function loadPage(){
	    var clientWidth = document.body.clientWidth;
	    var clientHeight = document.body.clientHeight;
	    var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '主题配置', 120, 0,
	      [],[
	        {iconCls: "x-image-query",text:'<s:text name="common.button.query"/>'},		//查询
	        {iconCls: "x-image-application_form_add",text:'<s:text name="common.button.addrecord"/>'},	//添加
	        {iconCls: "x-image-application_form_edit",text:'<s:text name="common.button.editrecord"/>'},	//修改
	        {iconCls: "x-image-application_form_delete",text:'<s:text name="common.button.deleterecord"/>'}//删除
	      ],onButtonClicked
	    );
	    conditionPanel.open();
	    
	    var pagequeryObj = new PageQuery(
	      true,'pageQueryTable',clientWidth,clientHeight - conditionPanel.getHeight(),
	      '<%=basePath%>/confManager/custType_queryCustType.action',
	      ['custtype_i','custtype_e','custtype_name','custtype_level','isvip','ticket_key'],
	      [
	        {header:'全选'},
	        {header:'复选框'},
	        {header:'内部客户类型',dataIndex:'custtype_i',width:100},
	        {header:'外部客户类型',dataIndex:'custtype_e',width:100},
	        {header:'客户类型名称',dataIndex:'custtype_name',width:100},
	        {header:'客户类型级别',dataIndex:'custtype_level',width:100},
	        {header:'是否VIP',dataIndex:'isvip',width:100},
	        {header:'小票键值',dataIndex:'ticket_key',width:100}
	      ],'<s:text name="common.pagequery.pagingtool"/>'
	    );
	    
	    var cp = new Ext.ColorPalette(); // 初始化时选中的颜色
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
	    
	    //当选择"添加"时，弹出的窗口
	    var addwindow = new SelfFormWindowSetWidth('addWindow','添加主题', 600, 350, 550, 1,[550],
	      [{colIndex:0,field:{xtype:'fieldset',layout:'column',title:'任务信息',
	        items:[{columnWidth: 1,layout:'form',
	          items:[{xtype:'textfield',name:'taskname', fieldLabel:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主题名称',anchor:'49%'}]},
	        {columnWidth: 1,layout:'form',items:[{html:'<font style="color:red">主题背景图片设置:</font>'}]},
	        {columnWidth: .85,layout:'form',labelWidth:140,
	          items:[{xtype:'textfield',name:'content', inputType:'file',allowBlank:false,blanckText:'选择附件路径',fieldLabel:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前背景图片',anchor:'98%'}]
	        },{columnWidth: .15,layout:'form',
	          items:[{xtype:'button',text:'预览',width:60}]
	        },{columnWidth: 1,layout:'form',items:[{html:'<font style="color:red">业务按钮设置:</font>'}]},
	        {columnWidth: .85,layout:'form',labelWidth:140,
	          items:[{xtype:'textfield',name:'content', inputType:'file',allowBlank:false,blanckText:'选择附件路径',fieldLabel:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前背景图片',anchor:'98%'}]
	        },{columnWidth: .15,layout:'form',
	          items:[{xtype:'button',text:'预览',width:60}]
	        },{columnWidth: .5,layout:'form',
	          items:[{xtype:'combo',name:'taskname', fieldLabel:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;字体名称',anchor:'98%',triggerAction : 'all',mode: 'local',store:FontNamestore,displayField:'dataName',valueField:'dataValue'}]
	        },{columnWidth: .5,layout:'form',
	          items:[{xtype:'combo',name:'taskname', fieldLabel:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;字形',anchor:'98%',triggerAction : 'all',mode: 'local',store:FontTypestore,displayField:'dataName',valueField:'dataValue'}]
	        },{columnWidth: .5,layout:'form',
	          items:[{xtype:'combo',name:'taskname', fieldLabel:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;字号',anchor:'98%',triggerAction : 'all',mode: 'local',store:FontSizestore,displayField:'dataName',valueField:'dataValue'}]
	        },{columnWidth: .5,layout:'form',
	          items:[{xtype:'textfield',name:'color',id:'color',fieldLabel:'字体颜色',style:'background:#000000',readOnly:true,listeners:{'focus': function(){showColor('color');}},width:20,height:20}]
	        },{columnWidth: 1,layout:'form',items:[{html:'<font style="color:red">显示时间:</font>'}]},
	        {columnWidth: .5,layout:'form',
	          items:[{xtype:'datefield',name:'taskname', fieldLabel:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始时间',anchor:'98%',format:'Ymd'}]
	        },{columnWidth: .5,layout:'form',
	          items:[{xtype:'datefield',name:'taskname', fieldLabel:'结束时间',anchor:'98%',format:'Ymd'}]
	        }]
	      }}],[
			{text:'<s:text name="复制上级机构配置"/>', 	handler : onaddclicked},
	        {text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
	        {text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
	      ],'left'
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
						//editwindow.open();
						//editwindow.updateFields(records[0]);
						break;
					case 3:
					    var submitdata = pagequeryObj.getSelectedObjects(['cardName','cardType']);
						if(submitdata === undefined){
							Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
							break;
						}
						
						//只能选择一条记录进行删除
						if(submitdata === undefined ||submitdata.length !== 1){
							Ext.MessageBox.alert('系统提示','请选择一条记录');
							break;
						}
						
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/confManager/cardBin_delCustType.action',submitdata,function(sRet){
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
				requestAjax('<%=basePath%>/confManager/cardBin_addCustType.action', submitData,
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
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="addWindow"></div>
  </body>
</html>
