<%@page import="com.agree.framework.web.form.administration.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)request.getSession().getAttribute("logonuser");
%>
 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>窗口服务状态监控</title>
    
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
	
	<script type="text/javascript" src="selfjs/common/ajax-pushlet-client.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindowSetWidth.js"></script>	
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript">
	  var winservicestateStore;
	  var winservicestateData;
		//配置推送事件
	      PL._init();
	      PL.joinListen('/winServiceState/Monitor');
	      function onData(event){
	        var result = event.get("result");
	        var obj=eval("("+result+")");
	        winservicestateStore.loadData(obj.list);
	     }
	  var pagereturn=${actionresult};
	  var systemUnits=pagereturn.field1;
	  var branch = <%="'"+user.getUnit().getUnitid()+"'"%>;
	  var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'isleaf',nodeLevel:'unitlevel',nodeType:'unitlevel'};
	  var treeGenerator_a = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
	  var tree_a = treeGenerator_a.generate(false,false,false,false);
	  var treepanel_a = new Ext.tree.TreePanel({
			height:280,
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
	  
	  //叫号规则
	  var yttrStore = new Ext.data .ArrayStore({
	    fields:['dictValue','dictValueDesc'],
	    data:[['1','1-静态规则'],['2','2-动态规则']]
	  });
	  //窗口状态
	  var winstateStore = new Ext.data .ArrayStore({
	    fields:['dictValue','dictValueDesc'],
	    data:[['1','1-可用'],['0','0-不可用']]
	  });
	  //柜台服务状态
	  var tcssStore = new Ext.data .ArrayStore({
	    fields:['dictValue','dictValueDesc'],
	    data:[['0','0-离线'],['1','1-空闲'],['2','2-正在办理'],['3','3-暂停服务']]
	  });
	  
	  Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
	  Ext.QuickTips.init();
	  var pagequeryObj;
	  Ext.onReady(loadPage);
	  
	  winservicestateStore = new Ext.data.JsonStore({
	    fields : [
'branch','win_num','qm_num','ip','win_oid','call_rule','status','screen_id',
'softcall_id','assess_id','win_service_status','teller_num','teller_name'
        ]
	  });	
	  winservicestateData = [
	    new Ext.grid.RowNumberer({header:''}),
	    {header:'机构号',			dataIndex:'branch',		width:120},
	    {header:'窗口号',			dataIndex:'win_num',		width:100},
	    {header:'排队机编号',		dataIndex:'qm_num',		width:100},
	    {header:'终端IP',		dataIndex:'ip',		width:150},
	    {header:'叫号规则',		dataIndex:'call_rule',		width:100, renderer:function(value){
	      return getDictValue(value.toString(),yttrStore,'dictValue','dictValueDesc');//翻译字段方法
	    }},
	    {header:'窗口状态',		dataIndex:'status',	width:100, renderer:function(value){
	      return getDictValue(value.toString(),winstateStore,'dictValue','dictValueDesc');//翻译字段方法
	    }},
	    {header:'柜台服务状态',	dataIndex:'win_service_status',		width:100, renderer:function(value){
	      return getDictValue(value.toString(),tcssStore,'dictValue','dictValueDesc');//翻译字段方法
	    }},
	    {header:'柜员号',			dataIndex:'teller_num',		width:100},
	    {header:'柜员名称',		dataIndex:'teller_name',	width:(window.screen.width)-1105}
	  ];
	  var winservicestateColModel=new Ext.grid.ColumnModel(winservicestateData);
	  
	  function loadPage(){
	    var clientWidth = document.body.clientWidth;
	    var clientHeight = document.body.clientHeight;
	    var panel = new Ext.Panel({
	      layout : 'absolute',frame:true,applyTo :'panel',height:clientHeight+44,modal: true,closable:true,
	      items:[          
	          
	          {xtype:'grid',width:clientWidth,height:clientHeight-10,
	            cm: winservicestateColModel,store: winservicestateStore, stripeRows: true,
	            items:[{layout:'column',items:[
{columnWidth:3/10,labelWidth:65,layout:'form',items:[{xtype:'combotree',name:'branch',fieldLabel:'&nbsp;监控机构',id:'query_branch',anchor:'98%',allowBlank : false,fieldLabel:'监控机构',passName:'branch',tree: treepanel_a,value:<%="'" + user.getUnit().getUnitname().replace("珠海华润银行","").replace("股份有限公司","") + "'"%>, allowUnLeafClick:false}]},
{columnWidth:1/5,layout:'form',items:[{xtype:'button',text:'查询',handler:function(){
  var queryData = {};
  queryData['branch'] = Ext.getCmp('query_branch').passField.value;
  requestAjax('<%=basePath%>/dataAnalysis/winServiceState_queryBusiness',queryData,function(sRet){
    winservicestateStore.loadData(sRet.field1);
  });
},anchor:'30%'}]}
                ]}]/*,
	            tbar: [{text: '查询',disabled :false,handler : function(){
	                requestAjax('<%=basePath%>/dataAnalysis/winServiceState_runPushlet','',function(sRet){
	                  
	                });
	              }},'-',{text: '启动',id:'runpushlet',disabled :true,handler : function(){
	                requestAjax('<%=basePath%>/dataAnalysis/winServiceState_runPushlet','',function(sRet){
	                  Ext.MessageBox.alert('系统提示','启动成功',function(id){
	                    Ext.getCmp('runpushlet').disable();
	                    Ext.getCmp('stoppushlet').enable();
	                  });
	                });
	              }},'-',{text:'停止',id:'stoppushlet',disabled :true,handler:function(){
	                requestAjax('<%=basePath%>/dataAnalysis/winServiceState_stopPushlet','',function(sRet){
	                  Ext.MessageBox.alert('系统提示','停止成功',function(id){
	                    Ext.getCmp('runpushlet').enable();
	                    Ext.getCmp('stoppushlet').disable();
	                  });
	                });
	              }}
	            ]*/
	          }
	      
	      ]
	    });
//---------------------------------------------------------------
	    function buildLayout(){
	      var viewport = new Ext.Viewport({
	        layout: 'absolute',
	        items : [panel]
	      });
	    };
	    buildLayout();
	    Ext.getCmp('query_branch').setPassValue(branch);
	  }
	  
	</script>
  </head>
  <body scroll="no">
    <div id="panel"></div>
  </body>
</html>