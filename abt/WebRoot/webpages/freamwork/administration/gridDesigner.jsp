<%@ page language="java"  pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String sessionid = session.getId();
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
    <link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
    <link rel="stylesheet" type="text/css" href="extjs/ux/css/ColumnHeaderGroup.css" />
    <script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="extjs/ext-all.gzjs"></script>
    <script type="text/javascript" src="selfjs/common/commonajax.js"></script>
    <script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
    <script type="text/javascript" src="extjs/ux/ColumnHeaderGroup.js"></script>
    <script type="text/javascript" src="selfjs/common/util.js"></script>
     <script type="text/javascript" src="extjs/ux/CheckColumn.js"></script>
     <script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
    
    <script type="text/javascript">
        var page;
        var viewWindow;
        var xtypeStore=new Ext.data.SimpleStore(
                { 
                data:[  ['textfield','文本框' ],
                        [  'textarea',  '多行文本框' ],
                        [  'datefield',  '日期控件' ],
                        [  'combo',  '下拉框' ],
                        [  'numberfield', '数字输入框'],
                        [  'hidden',  '隐藏控件']
                ],
                fields:["value","text"]});   
        Ext.QuickTips.init();
        Ext.onReady(loadPage);
        function loadPage(){
             // create the data store
             var store = new Ext.data.ArrayStore({
                fields: [
                   {name: 'colname'},{name: 'display'},{name: 'iscolnull',type: 'bool'},
                   {name: 'showInGrid',type: 'bool'},{name: 'colWidth'},{name: 'showInSearch',type: 'bool'},{name: 'search_lineno',type: 'bool'},
                   {name: 'showInForm',type: 'bool'},{name: 'xtype'},{name: 'lableWidth'},{name: 'xtypeWidth'},{name: 'spaceWidth'},{name: 'form_showInNewline',type: 'bool'},{name: 'isGroup',type: 'bool'}
                ]
            });
             var group = new Ext.ux.grid.ColumnHeaderGroup({
                rows: [[
                        {header: '基本信息', colspan: 4, align: 'center'},
                        {header: '列表页设置', colspan: 4, align: 'center'},
                        {header: '表单页设置', colspan: 6, align: 'center'}
                ]]
            });
            var sm=new Ext.grid.CheckboxSelectionModel({singleSelect: false});
            var  columns= [
                    new Ext.grid.RowNumberer({header:'',width:30}),
                    { header:'复选框', align: 'center'},
                    {header: "字段名", width: 60, sortable: true, dataIndex: 'colname',editor :{ xtype:'textfield'}},
                    {header: "显示名", width: 100, sortable: true,  dataIndex: 'display',editor :{ xtype:'textfield'}},
                 //   {header: "是否为空", xtype: 'checkcolumn',width: 60, sortable: true,  dataIndex: 'iscolnull'},
                    {header: "列表显示", width: 60, xtype: 'checkcolumn',sortable: true, dataIndex: 'showInGrid'},
                    {header: "列表宽度", width: 60, sortable: true,  dataIndex: 'colWidth',editor :{ xtype:'numberfield'}},
                    {header: "搜索显示", width: 60, xtype: 'checkcolumn',sortable: true, dataIndex: 'showInSearch'},
                    {header: "所在行", width: 100, sortable: true,  dataIndex: 'search_lineno',editor :{ xtype:'numberfield'}},
                    {header: "表单显示", width: 60, xtype: 'checkcolumn',sortable: true,  dataIndex: 'showInForm'},
                    {header: "控件类型", width: 100, sortable: true, dataIndex: 'xtype',editor: {xtype : 'combo', name:'xtype', hiddenName:'xtype',allowBlank : false, store: xtypeStore,displayField:"text",valueField:"value",triggerAction: 'all', mode:'local'},renderer: function(value){
                        return getDictValue(value,xtypeStore,'value','text');//翻译字段方法
                    }},
                    {header: "最大长度", width: 60, sortable: true,  dataIndex: 'maxLength',editor :{ xtype:'numberfield'}},
                    {header: "是否只读", width: 60, xtype: 'checkcolumn',sortable: true,  dataIndex: 'readOnly'},
                    {header: "是否可空", width: 60, xtype: 'checkcolumn',sortable: true,  dataIndex: 'allowBlank'},
                    {header: "所在行", width: 60, sortable: true,  dataIndex: 'form_lineno',editor :{ xtype:'numberfield'}},
                    {header: "是否分组", width: 60, xtype: 'checkcolumn',hidden:true,sortable: true,  dataIndex: 'isGroup'}//暂时没有实现分组
                ];
            columns[1] = sm;
            var regionColModel=new Ext.grid.ColumnModel({defaults:{sortable: true,align: 'center'},columns:columns});
            var grid = new Ext.grid.EditorGridPanel ({
                region : 'center',
                store: store,
                sm: sm,
                cm:regionColModel,
                frame:true,
                iconCls: 'icon-grid',
                clicksToEdit: 1,
                tbar  : [ {
                    text:'添加',
                    iconCls: 'x-image-add',
                    handler : function(){
                         var Plant = store.recordType;
                         var length=store.getCount();
                         var p = new Plant({colname:'name',display:'显示名称',xtype:"textfield",showInSearch : true,search_lineno : 0,colWidth:100,
                                        form_lineno:0,readOnly:false, showInForm : true,form_showInNewline : false,isGroup : false,showInGrid : true,allowBlank:true});
                         grid.stopEditing();
                         store.insert(length, p);
                         grid.getView().refresh();
                         grid.startEditing(0, 0);
                    }
                },{
                    text:'删除',
                    iconCls: 'x-image-delete',
                    handler : function(){
                        var sm = grid.getSelectionModel();
                        var cells = sm.getSelections();
                        for(var i=0;i<=cells.length;i++){
                            store.remove(cells[i]);
                        }
                        grid.getView().refresh();
                    }
                },{
                    text:'上移',
                    iconCls: 'x-image-up',
                    handler : function(){
                        var sm = grid.getSelectionModel();
	                    var row=sm.getSelected();
                         if(row){
	                        var rowIndex=store.indexOf(row);
	                        if(rowIndex == 0) {  
	                           return;  
	                        }  
	                        store.removeAt(rowIndex);   
	                        store.insert(rowIndex - 1, row);   
	                        sm.selectRow(rowIndex - 1);   
	                        grid.getView().refresh();  
                        }
                    }
                    },{
                    text:'下移',
                    iconCls: 'x-image-down',
                    handler : function(){
                       var sm = grid.getSelectionModel();
	                   var row=sm.getSelected();
                       if(row){
	                      var rowIndex=store.indexOf(row);
	                      if(rowIndex < grid.getStore().getCount() - 1){  
	                        grid.getStore().removeAt(rowIndex);   
	                        grid.getStore().insert(rowIndex + 1, row);   
	                        grid.getSelectionModel().selectRow(rowIndex + 1);   
	                        grid.getView().refresh();         
	                       }  
                       }
                    }
                },{
                    text:'预览',
                    iconCls: 'x-image-control_play_blue',
                    handler : function(){
                        var submitdata = new Array();
                        
                        var properties=eastPanel.get('propertyGrid').getSource();
                        submitdata.push(properties);
                        for(var i = 0; i < store.getCount(); i++){
                            var record=store.getAt(i);
                            var data={};
                            data['colname']=record.get('colname');
                            data['display']=record.get('display');
                            data['search_lineno']=record.get('search_lineno');
                            data['showInGrid']=record.get('showInGrid');
                            data['colWidth']=record.get('colWidth');
                            data['showInSearch']=record.get('showInSearch');
                            data['search_showInNewline']=record.get('search_showInNewline');
                            data['showInForm']=record.get('showInForm');
                            data['xtype']=record.get('xtype');
                            data['form_lineno']=record.get('form_lineno');
                            data['maxLength']=record.get('maxLength');
                            data['allowBlank']=record.get('allowBlank');
                            data['readOnly']=record.get('readOnly');
                            data['isGroup']=record.get('isGroup');
                            submitdata.push(data);
                        }
                        requestAjax('<%=basePath%>baseinfo/gridDesigner_generatePage',submitdata,function(sRet){
                            var c = parent.Ext.getCmp('center-panel');
                            var pagename=sRet.pagename;
                            pagename="<%=basePath%>temp/"+pagename;
	                        var id='tab_'+'<%=sessionid%>';
                            if(parent.Ext.getCmp(id)){
                               c.remove(id);
                            }
	                        var tab = c.add({
	                                    'id':id,
	                                    name:'viewtemp',
	                                    'title':'预览效果',
	                                    closable:true,  //通过html载入目标页
	                                    autoDestroy:true,
	                                    html:'<iframe name="if'+id+'" scrolling="auto" frameborder="0" width="100%" height="100%" src='+pagename+'></iframe>'
	                        });
                           
	                        c.setActiveTab(tab);
                        });
                        
                    }
                },{
                    text:'导出',
                    iconCls: 'x-image-print',
                    handler : function(){
                        var submitdata = new Array();
                        
                         var properties=eastPanel.get('propertyGrid').getSource();
                        submitdata.push(properties);
                        
                        for(var i = 0; i < store.getCount(); i++){
                            var record=store.getAt(i);
                            var data={};
                            data['colname']=record.get('colname');
                            data['display']=record.get('display');
                            data['search_lineno']=record.get('search_lineno');
                            data['showInGrid']=record.get('showInGrid');
                            data['colWidth']=record.get('colWidth');
                            data['showInSearch']=record.get('showInSearch');
                            data['search_showInNewline']=record.get('search_showInNewline');
                            data['showInForm']=record.get('showInForm');
                            data['xtype']=record.get('xtype');
                            data['form_lineno']=record.get('form_lineno');
                            data['maxLength']=record.get('maxLength');
                            data['allowBlank']=record.get('allowBlank');
                            data['readOnly']=record.get('readOnly');
                            data['isGroup']=record.get('isGroup');
                            submitdata.push(data);
                        }
                        requestAjax('<%=basePath%>baseinfo/gridDesigner_generatePage',submitdata,function(sRet){
                            messageWindow.open();
                            page=sRet.page;
                            Ext.getCmp('message').setValue(page);
                        });
                    }
                }],
                viewConfig: {
                    forceFit: true
                },
                plugins: group
            });
            
             
            
            
            var messageWindow= new SelfFormWindow('outputwindow', '预览', 600, 400, 200, 1,
            [
                {colIndex:0, field:{xtype : 'textarea', name:'msg',width:550,height:280,id:'message'}}
                ],
                [
                    {text:'保存', formBind:true, handler : save},
                    {text:'关闭', handler: function(){messageWindow.close();}}
                ]
            );
            
            function save(){
	                   // downFileForm.downFileName.value="generatePage.jsp";
	                    downFileForm.submit();                  
            }
            
            var eastPanel=new Ext.Panel({
                region: 'east',
                title: '属性配置',
                collapsible: true,
                split: true,
                width: 225, // give east and west regions a width
                minSize: 175,
                maxSize: 400,
                margins: '0 5 0 0',
                layout: 'fit', // specify layout manager for items
                items: 
                    new Ext.grid.PropertyGrid({
		                autoHeight: true,
                        id:'propertyGrid',
		                propertyNames: {
                            queryurl:'查询url',
                            whetherpage:'是否分页',
		                    height: '表单高度',
		                    width: '表单宽度',
                            labelAlign:'标签位置',
                            labelWidth:'标签宽度',
                            needAddWindow:'需要添加表单',
                            needUpdateWindow:'需要修改表单',
                            needDel:'需要删除按钮'
		                },
		                source: {
                            queryurl:'<%=basePath%>',
                            whetherpage:true,
		                    height: 400,
		                    width: 600,
                            labelAlign:'top',
                            labelWidth:100,
                            needAddWindow:true,
                            needUpdateWindow:true,
                            needDel:true
		                },
		                viewConfig : {
		                    forceFit: true,
		                    scrollOffset: 2 // the grid will never have scrollbars
		                }
		            })
            });
            
            
            
            
            function buildLayout(){
                var viewport = new Ext.Viewport({
                    layout : "border",
                    items : [grid,eastPanel]
                });
            }
            buildLayout();
      
        } 
    </script>

  </head>
  
  <body scroll="no">
    <div id="queryConditionPanel"></div>
    <div id="pageQueryTable"></div>
    <div id="inputwindow"></div>
    <div id="outputwindow"></div>
    <div id="prop-grid"></div>
    <s:form name="downFileForm" method="get" action="/temp/gridDesigner_downFile" target="REPORTRESULTFRAME"/>
  </body>
</html>
