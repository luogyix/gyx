/**
 * 根据路径打开一个新的tab
 * @param {} path 打开页面路径
 * @param {} title 打开页面的标题
 * 使用方法：例子openTab('<%=basePath%>/report/hvps_loadPage?flag=0','报表查询');
 */
function openTab(path,title){
	var c = this.parent.Ext.getCmp('center-panel');
				 		
	                 	if(c.getComponent("tab_welcome")){
	        				c.remove(c.getComponent("tab_welcome"));
	        			};
	        			
	        			var url='<iframe name=\"iftab_'+path+'\" scrolling=\"auto\" frameborder=\"0\" width=\"100%\" height=\"100%\" src=\"'+path+'\"></iframe>';;
	    			    var tab = c.add({
	    					'id':'tab_'+path,
	    					'title':title,
	    					closable:true,  //通过html载入目标页
	    					html:url
	    				 });
	    			     c.setActiveTab(tab);
}