<%@ page language="java" import="java.util.*,com.agree.framework.web.form.administration.*;" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=(User)(request.getSession().getAttribute("logonuser"));
Long usertype=user.getUsertype();
String username=user.getUsername();
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'loginfo.jsp' starting page</title>
    
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
    <script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
    <script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
    <script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
    <style type="text/css">
        .x-grid3-cell-inner{white-space:normal !important;}
        .x-grid3-cell{vertical-align: middle !important;}
    </style>  
    <script type="text/javascript">
        Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
        Ext.QuickTips.init();
        var results = ${actionresult};
        var systemUnits = results.field2;//部门数据
        var unitListStore = new Ext.data.JsonStore({
            fields : ['unitid','unitname','unitlevel'],
            data   : systemUnits
        });
        
        //定义json串对应意义，按照需要定义
        var jsonMeta = {nodeId:'unitid',parentNodeId:'parentunitid',nodeName:'unitname',nodeHref:'',nodeTarget:'',leafField:'',nodeLevel:'unitlevel',nodeType:'unitlevel'};
        var treeGenerator1 = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
        var unitTree1 = treeGenerator1.generate(false,false,false,false);
        var unitTreePanl = new Ext.tree.TreePanel({
                 rootVisible : true, 
                 root:unitTree1 
        });
        
         var treeGenerator2 = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
        var unitTree2 = treeGenerator1.generate(false,false,false,false);
        var unitTreePanl2 = new Ext.tree.TreePanel({
                 rootVisible : true, 
                 root:unitTree2 
        });
        
        var treeGenerator3 = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-chart_organisation','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
        var unitTree3 = treeGenerator1.generate(false,false,false,false);
        var unitTreePanl3 = new Ext.tree.TreePanel({
                 rootVisible : true, 
                 root:unitTree3 
        });
        
        Ext.onReady(loadPage);
        //装载操作记录查询页面
        function loadPage(){
            var clientWidth = document.body.clientWidth;
            var clientHeight = document.body.clientHeight;
            var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '', 120, 1,
                [
                {rowIndex:0, field:{xtype:'combotree',  name:'unitname',    emptyText:'----<s:text name="admin.user.userunit"/>----',fieldLabel:'部门',     passName: 'unitid', tree: unitTreePanl3, width:300}} //部门
                ],
                [
                {iconCls: "x-image-query", text:'<s:text name="common.button.query"/>'},
                {iconCls: "x-image-reset", text:'<s:text name="common.button.reset"/>'},
                {iconCls: "x-image-application_form_add",       text:'<s:text name="common.button.addrecord"/>'},   //添加
                {iconCls: "x-image-application_form_edit",      text:'<s:text name="common.button.editrecord"/>'},  //修改
                {iconCls: "x-image-application_form_delete",    text:'<s:text name="common.button.deleterecord"/>'}//删除
                ],
                onButtonClicked
            );
            conditionPanel.open();
            var pagequeryObj = new PageQuery(
                true,'pageQueryTable',
                clientWidth,clientHeight - conditionPanel.getHeight(),
                '<%=basePath%>admin/sysnotice_query',
                ['unitid','unitname','msg','id'],
                [
                {header:'全选'},
                {header:'复选框'},
                {header:'通知部门',dataIndex:'unitname'},
                {header:'通知信息',dataIndex:'msg',width:980}
                ],
                '<s:text name="common.pagequery.pagingtool"/>'
            );
          
           var addwindow = new SelfFormWindow('addWindow', '添加', 600, 350, 550, 1,
                [
                {colIndex:0, field:{xtype : 'combotree', name:'unitname',allowBlank : false,fieldLabel:'通知部门',passName:'unitid',tree:unitTreePanl,width:300}},
                {colIndex:0, field:{xtype : 'textarea', name:'msg',   fieldLabel:'通知信息'}}
                ],
                [
                    {text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
                    {text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
                ]
            );
            function onaddclicked(){
                var submitData = addwindow.getFields();
                requestAjax('<%=basePath%>admin/sysnotice_addNotice', submitData,
                function(sRet){
                    Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
                        var query_obj = conditionPanel.getFields();
                        pagequeryObj.queryPage(query_obj);
                    });
                    addwindow.close();
                });
            }
            var editwindow = new SelfFormWindow('editWindow', '修改', 600, 350, 550, 1,
                [
                {colIndex:0, field:{xtype : 'combotree', name:'unitname',id:'unit',allowBlank : false,fieldLabel:'部门',passName:'unitid',tree: unitTreePanl2,valueField:'unitid',displayField:'unitname',width:300}},
                {colIndex:0, field:{xtype : 'textarea', name:'msg',   fieldLabel:'通知信息'}},
                {colIndex:0, field:{xtype : 'hidden', name:'id'}}
                ],
                [
                    {text:'<s:text name="common.button.edit"/>',    handler : oneditclicked,    formBind:true},
                    {text:'<s:text name="common.button.cancel"/>',  handler: function(){editwindow.close();}}
                ]
            );
            function oneditclicked(){
                var submitData = editwindow.getFields();
                requestAjax('<%=basePath%>admin/sysnotice_updateNotice',submitData,function(sRet){
                    Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
                        var query_obj = conditionPanel.getFields();
                        pagequeryObj.queryPage(query_obj);
                    });
                    editwindow.close();
                });
            }
            
           //触发"查询"、"重置"按钮
            function onButtonClicked(btn_index){
                switch(btn_index){
                case 0:
                    var query_obj = conditionPanel.getFields();
                    pagequeryObj.queryPage(query_obj);
                    break;
                case 1:
                    conditionPanel.reset();
                    break;
                case 2:
                    addwindow.open();
                    break;
                case 3:
                        var records = pagequeryObj.getSelectedRecords();
                        if(records === undefined ){
                            Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
                            break;
                        }
                        if(records.length !== 1){
                            Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
                            break;
                        }
                        editwindow.open();
                        records[0].data.unitid=parseInt(records[0].data.unitid);
                        editwindow.updateFields(records[0]);
                        break;
                 case 4:
                        var submitdata = pagequeryObj.getSelectedObjects(['id']);
                        if(submitdata === undefined){
                            Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
                            break;
                        }
                        Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
                            if(id === 'yes'){
                                requestAjax('<%=basePath%>admin/sysnotice_delNotice',submitdata,function(sRet){
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
