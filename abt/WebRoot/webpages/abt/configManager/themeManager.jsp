<%@page import="com.agree.abt.model.confManager.ThemeInfo"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
HttpServletRequest req = ServletActionContext.getRequest();
List<ThemeInfo> list = (List<ThemeInfo>)req.getAttribute("themeInfo");
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
		
		<link rel="stylesheet" type="text/css" href="selfcss/thememanager/site.css"/>
		
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.js"></script>
		<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
		
		<script type="text/javascript" src="selfjs/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="selfjs/jquery/jq.Slide.js"></script>
		
		<script type="text/javascript">
			Ext.QuickTips.init();
			Ext.onReady(function(){
				var myBorderPanel = new Ext.Panel({
					frame: false,
					region : 'west' , 
			    	collapsible:false , 
			    	width : 180,
					split : false , 
					layout:'accordion', 
					margins:'0 0 0 5',
					autoScroll:true,
					//renderTo: document.body,
					//width: document.body.clientWidth,
					//height: document.body.clientHeight,
					//layout: 'border',
				    contentEl: 'menu',
				    autoScroll:true,
				    split: true,
				    //width:document.body.clientWidth,
				    title: '主题列表',
				    margins: '0 0 0 0'
				});
				
				var mainPanel = new Ext.Panel({
					region: 'center',
					layout:'fit',
					frame: true,
				    autoScroll:true,
				    contentEl: 'myframe',
				    split: true,
				    title: '主题详细展示',
				    margins:'0 5 0 0'
				});
		    	//这个panel的添加纯粹是为了避免viewport为border布局时在IE7下页面内容消失的问题
		    	var panel = new Ext.Panel({
					layout: 'border',
					items: [myBorderPanel,mainPanel]
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
				//window.open('','主题展示',params); 
				/**/
				var tempForm = document.createElement('form');  
	            tempForm.id='tempForm1';
	            tempForm.method='post';
	            tempForm.action=url;  
	            tempForm.target='myframe';  
	             
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
		</script>
	</head>
	<body>
		<div id="menu" class="menu">
			<ul>
				<%
				for(int i=0;i<list.size();i++){
					ThemeInfo theme = list.get(i);
					String id = theme.getTheme_id();
					String name = theme.getTheme_name();
					String imgsrc_icon = theme.getTheme_imgsrc_icon();
					String imgsrc_list = theme.getTheme_imgsrc_list();
					String note = theme.getTheme_note();
				%>
				<li>
					<a style="text-align: center;" target="_blank" onclick="cilckTheme('<%=id%>','<%=name%>','<%=imgsrc_icon%>','<%=imgsrc_list%>','<%=note%>')">
						<div class="name"><%=name %></div>
						<IMG alt="<%=name %>" src="<%=imgsrc_icon %>" width="100" height="100" />
						<div class="note"></div>
					</a>
				</li>
				<!-- <li>
					<a style="text-align: center;" target="_blank" onclick="cilckTheme('<%=id%>','<%=name%>','<%=imgsrc_icon%>','<%=imgsrc_list%>','<%=note%>')">
						<IMG alt="<%=note %>" src="<%=imgsrc_icon %>" width="200" height="200" />
						<div class="name"><%=name %></div>
						<div class="note"><%=note %></div>
					</a>
				</li> -->
				<%
				}
				%>
			</ul>
		</div>
		
		<iframe id="myframe" name="myframe" frameborder="0" width="100%" height="100%">
		</iframe>
	</body>
</html>
