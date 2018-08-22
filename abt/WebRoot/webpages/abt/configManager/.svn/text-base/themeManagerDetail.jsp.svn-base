<%@page import="java.net.URLDecoder"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
HttpServletRequest req = ServletActionContext.getRequest();

String id = req.getParameter("id");
String name = URLDecoder.decode(req.getParameter("name"),"utf-8");
String note = URLDecoder.decode(req.getParameter("note"),"utf-8");
String[] imgs =  req.getParameter("list").split(";");
%>

 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
	 
		<title>主题展示</title>
	 
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
		
		<script type="text/javascript" src="selfjs/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="selfjs/jquery/jq.Slide.js"></script>
		<style type="text/css">
			/* base */
			ul,li{padding:0;margin:0;}
			fieldset,img{border:0}
			
			/* slide-box */
			#slide-box{width:690px;position:relative;height:472px;}
			#slide-box .corner{clear:both;border-top:#333 1px solid;display:block;overflow:hidden;height:0;margin:0 1px;}
			#slide-box .slide-content{background:#333;padding:10px;}
			#slide-box .JQ-slide-content{position:absolute;}
			#slide-box .JQ-slide-nav a{display:block;z-index:99;width:37px;color:#b4b4b4;position:absolute;top:205px;height:65px;text-decoration:none;}
			#slide-box .JQ-slide-nav span{display:block;background:#4b4b4b;font:700 53px arial;width:37px;cursor:pointer;height:63px;text-align:center;}
			#slide-box .JQ-slide-nav .corner{border-color:#4b4b4b;}
			#slide-box .JQ-slide-nav .prev{left:-10px;}
			#slide-box .JQ-slide-nav .next{right:-10px;}
			#slide-box .wrap{overflow:hidden;width:670px;height:450px;position:relative;}
			#slide-box ul{width:10000px;}
			#slide-box li{float:left;width:340px;height:450px;}
			#slide-box li img{width:330px;}<!-- height:450px; -->
			#slide-box .JQ-slide-nav a:hover,#slide-box .JQ-slide-nav a:hover span{color:#f43d1e;}
			
			p{text-indent:2em}
		</style>
		
		<script type="text/javascript">
			Ext.QuickTips.init();
			Ext.onReady(function(){
				var myBorderPanel = new Ext.TabPanel({
					region: 'center',
					deferredRender: false,//是否延迟加载
				    //width:document.body.clientWidth,
				    //activeTab: 0,
				    //frame:true,
				    activeTab: 0, //激活第一页为当前页
				    defaults:{autoScroll : true },
				    listeners : {  
						'beforetabchange' : function(panel,click){
						}
					},
				    items:[
				        {contentEl:'nextView', title: '主题图片展示'},
				        {contentEl:'nextView2', title: '详细介绍'}
				    ]
				});
		    	//这个panel的添加纯粹是为了避免viewport为border布局时在IE7下页面内容消失的问题
		    	var panel = new Ext.Panel({
					layout: 'border',
					items: [myBorderPanel]
		    	});
		    	var viewport = new Ext.Viewport({
	        			layout : 'fit',
	        			items : [
							panel
	        			]
	        		}
	        	);
			});
			function cilckTheme(id,name,icon,list,note){
			}
			
			$(function(){
				$("#imgs").Slide({
					effect : "scroolLoop",
					autoPlay:false,
			    	speed : "fast",
					timer : 3000,
					steps:2
				});
			});
		</script>
	</head>
	<body>
		<div id="nextView">
			<div id="slide-box">
				<b class="corner"></b>
				<div class="slide-content" id="imgs">
					<div class="wrap">
						<ul class="JQ-slide-content">
						<%
						for(int i=0;i<imgs.length;i++){
						%>
							<li><a target="_blank"><IMG src="<%=imgs[i] %>" width="330"  /></a></li><!-- height="450" -->
						<%
						}
						%>
						</ul>
					</div>
					<div class="JQ-slide-nav">
						<a class="prev">
							<b class="corner"></b>
							<span>&#8249;</span>
							<B class="corner"></B>
						</a>
						<a class="next">
							<b class="corner"></b>
							<span>&#8250;</span>
							<B class="corner"></B>
						</a>
					</div>
				</div>
				<b class="corner"></b>
			</div>
		</div>
		<div id="nextView2">
			<h1 align="center" style="padding: 20px;font-size: 50px;"><%=name %></h1>
			<p><%=note %></p>
		</div>
	</body>
</html>
