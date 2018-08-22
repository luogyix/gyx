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
	 
	 <title>主题配置</title>
	 
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
	<script type="text/javascript" src="selfjs/extendjs/ComboBoxCheckTree.js"></script>
	<script type="text/javascript">
		Ext.QuickTips.init();
		/**
		 * 翻译字段用store
		 */
		var modelStore=new Ext.data.SimpleStore({ 
			data:[["1-模板1","1"],["2-模板2","2"],["3-模板3","3"],["4-模板4","4"],["5-提取上级配置","5"]],
			fields : ['value','key']
		});
		
		Ext.onReady(function(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			
			var fileForm = new Ext.form.FormPanel({
				title : "测试上传",
				renderTo : "fileUpload",
				fileUpload : true,
				layout : "form",
				id : "fileUploadForm",
				items : [{
					id : 'imgName',
					name : 'imgName',
					fieldLabel : '主题名',
					xtype : 'textfield'
				},{
					id : 'upload',
					name : 'upload',
					inputType : "file",
					fieldLabel : '上传缩略图',
					xtype : 'textfield',
					anchor : '40%'
				},{
					xtype : 'box',
					id : 'browseImage',
					fieldLabel : "预览图片",
					autoEl : {
						width : 300,
						height : 350,
						tag : 'img',
						// type : 'image',
						src : Ext.BLANK_IMAGE_URL,
						style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',
						complete : 'off',
						id : 'imageBrowse'
					}
				}],
				listeners : {
					'render' : function(f) {
						this.form.findField('upload').on('render', function() {
							//通過change事件
							Ext.get('upload').on('change',function(field, newValue, oldValue) {
								var url = 'file://' + Ext.get('upload').dom.value;
						  		// alert("url = " + url);
								if (img_reg.test(url)) {
									if (Ext.isIE) {
										var image = Ext.get('imageBrowse').dom;
							  			image.src = Ext.BLANK_IMAGE_URL;// 覆盖原来的图片
							  			image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url;
							 		}else {// 支持FF
							  			Ext.get('imageBrowse').dom.src = window.URL.createObjectURL(Ext.get('upload').dom.files[0]);
							 		}
								}
							}, this);
						}, this);
					}
				},
				buttons : [{
					text : "提交",
					name : "submit",
					handler : submit
				}]
			});
			
			var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/;
		});
		
		//上传图片
		function submit() {
			Ext.getCmp("fileUploadForm").getForm().submit({
				url : "<%=basePath%>confManager/themeManager_saveFile",
				params: {'imgName':Ext.getCmp('imgName').getValue()},
				method : "POST",
				success : function(form, action) {
					alert("success");
				},
				failure : function(form, action) {
					alert("failure");
				}
			});
		}
	</script>
  </head>
  <body>
  	<div id="fileUpload"></div>
  </body>
</html>
