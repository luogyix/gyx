/**
 * 用来方便的关闭Tab，可以关闭一个或多个
 */
Ext.ux.TabCloseMenu = function(){
    var tabs, menu, ctxItem;
    this.init = function(tp){
        tabs = tp;
        tabs.on('contextmenu', onContextMenu);
        tabs.on('destroy', onDestroy);
    }
 
    
    
     function onDestroy(){
        Ext.destroy(this.menu);
        delete this.menu;
        delete this.tabs;
        delete this.active;    
    }
    
    function onContextMenu(ts, item, e){
        if(!menu){ 
            menu = new Ext.menu.Menu([{
                id: tabs.id + '-close',
                text: '关闭当前标签页',
                iconCls:'x-image-applicationIcon', 
                scope:this,
                handler : function(){
                    tabs.remove(ctxItem);
                    menu.hide();
                }
            },{
                id: tabs.id + '-close-others',
                text: '关闭其它标签页',
                iconCls:'x-image-application_doubleIcon', 
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable && item != ctxItem){
                            tabs.remove(item);
                            menu.hide();
                        }
                    });
                }
            },{
                id: tabs.id + '-close-all',
                text: '关闭全部标签页',
                iconCls:'x-image-application_cascadeIcon', 
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable){
                            tabs.remove(item);
                            menu.hide();
                        }
                    });
                }
            },'-',{
                id: tabs.id + '-cancel',
                text: '取消',
                iconCls:'x-image-tbar_synchronizeIcon', 
                handler : function(){
                	menu.hide();
                }
            }]);
        }
        ctxItem = item;
        var items = menu.items;
        items.get(tabs.id + '-close').setDisabled(!item.closable);
        var disableOthers = true;
        tabs.items.each(function(){
            if(this != item && this.closable){
                disableOthers = false;
                return false;
            }
        });
        items.get(tabs.id + '-close-others').setDisabled(disableOthers);
        var disableAll = true;
        tabs.items.each(function(){
            if(this.closable){
                disableAll = false;
                return false;
            }
        });
        items.get(tabs.id + '-close-all').setDisabled(disableAll);
        e.stopEvent();
        menu.showAt(e.getPoint());
    }
};

//遍历dom节点，将该节点node和其子节点都放到spaceNodes中
		function lookforchildNode(node,spaceNodes) {  
			        var nodeList = node.childNodes;  
			        for(var i=0; i<nodeList.length; i++) { 
			            var n = nodeList[i];            
			            spaceNodes[spaceNodes.length] = n;    
			            lookforchildNode(n,spaceNodes);
			        }  
		}  