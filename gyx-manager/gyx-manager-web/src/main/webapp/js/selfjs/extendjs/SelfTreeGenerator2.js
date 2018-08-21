//重写ext treecheckNodeUi 提供完整的级联选择
Ext.tree.TreeCheckNodeUI = function(){   //Ext.tree.TreeCheckNodeUI插件
                //'multiple':多选; 'single':单选; 'cascade':级联多选 
                this.checkModel = 'multiple';
                
                //only leaf can checked 
                this.onlyLeafCheckable = false;
               
                Ext.tree.TreeCheckNodeUI.superclass.constructor.apply(this, arguments);
            };
            
            
 Ext.extend(Ext.tree.TreeCheckNodeUI, Ext.tree.TreeNodeUI, {
            
                renderElements: function(n, a, targetNode, bulkRender){
                    var tree = n.getOwnerTree();
                    this.checkModel = tree.checkModel || this.checkModel;
                    this.onlyLeafCheckable = tree.onlyLeafCheckable || false;
                    
                    // add some indent caching, this helps performance when rendering a large tree 
                    this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : '';
                    
                    var cb = (!this.onlyLeafCheckable || a.leaf);
                    var href = a.href ? a.href : Ext.isGecko ? "" : "#";
                    var buf = ['<li class="x-tree-node"><div ext:tree-node-id="', n.id, '" class="x-tree-node-el x-tree-node-leaf x-unselectable ', a.cls, '" unselectable="on">', '<span class="x-tree-node-indent">', this.indentMarkup, "</span>", '<img src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow" />', '<img src="', a.icon || this.emptyIcon, '" class="x-tree-node-icon', (a.icon ? " x-tree-node-inline-icon" : ""), (a.iconCls ? " " + a.iconCls : ""), '" unselectable="on" />', cb ? ('<input class="x-tree-node-cb" type="checkbox" ' + (a.checked ? 'checked="checked" />' : '/>')) : '', '<a hidefocus="on" class="x-tree-node-anchor" href="', href, '" tabIndex="1" ', a.hrefTarget ? ' target="' + a.hrefTarget + '"' : "", '><span unselectable="on">', n.text, "</span></a></div>", '<ul class="x-tree-node-ct" style="display:none;"></ul>', "</li>"].join('');
                    
                    var nel;
                    if (bulkRender !== true && n.nextSibling && (nel = n.nextSibling.ui.getEl())) {
                        this.wrap = Ext.DomHelper.insertHtml("beforeBegin", nel, buf);
                    }
                    else {
                        this.wrap = Ext.DomHelper.insertHtml("beforeEnd", targetNode, buf);
                    }
                    
                    this.elNode = this.wrap.childNodes[0];
                    this.ctNode = this.wrap.childNodes[1];
                    var cs = this.elNode.childNodes;
                    this.indentNode = cs[0];
                    this.ecNode = cs[1];
                    this.iconNode = cs[2];
                    var index = 3;
                    if (cb) {
                        this.checkbox = cs[3];
                        Ext.fly(this.checkbox).on('click', this.check.createDelegate(this, [null]));
                        index++;
                    }
                    this.anchor = cs[index];
                    this.textNode = cs[index].firstChild;
                },
               
                // private 
                check: function(checked){
                	
                    var n = this.node;
                    var tree = n.getOwnerTree();
                    this.checkModel = tree.checkModel || this.checkModel;
                    
                    if (checked === null) {
                        checked = this.checkbox.checked;
                    }
                    else {
                        this.checkbox.checked = checked;
                    }
                    
                    n.attributes.checked = checked;
                    tree.fireEvent('check', n, checked);
                    
                    if (!this.onlyLeafCheckable && this.checkModel == 'cascade') {
                        var parentNode = n.parentNode;
                        if (parentNode !== null) {
                            this.parentCheck(parentNode, checked);
                        }
                        if (!n.expanded && !n.childrenRendered) {
                            n.expand(false, false, this.childCheck);
                        }
                        else {
                            this.childCheck(n);
                        }
                    }
                    else 
                        if (this.checkModel == 'single') {
                            var checkedNodes = tree.getChecked();
                            for (var i = 0; i < checkedNodes.length; i++) {
                                var node = checkedNodes[i];
                                if (node.id != n.id) {
                                    node.getUI().checkbox.checked = false;
                                    node.attributes.checked = false;
                                    tree.fireEvent('check', node, false);
                                }
                            }
                        }
                    
                },
                
                // private 
                childCheck: function(node){
                    var a = node.attributes;
                    if (!a.leaf) {
                        var cs = node.childNodes;
                        var csui;
                        for (var i = 0; i < cs.length; i++) {
                            csui = cs[i].getUI();
                            if (csui.checkbox.checked ^ a.checked&&cs[i].disabled!=true) 
                                csui.check(a.checked);
                        }
                    }
                },
                
                // private 
                parentCheck: function(node, checked){
                	
                    var checkbox = node.getUI().checkbox;
                    if (typeof checkbox == 'undefined') 
                        return;
                    if (!(checked ^ checkbox.checked)) 
                        return;
                    if (!checked && this.childHasChecked(node)) 
                        return;
                    if(node.disabled==true){
                    	return;
                    }
                    	
                    checkbox.checked = checked;
                    node.attributes.checked = checked;
                    
                    node.getOwnerTree().fireEvent('check', node, checked);
                    
                    var parentNode = node.parentNode;
                    if (parentNode !== null) {
                        this.parentCheck(parentNode, checked);
                    }
                },
                
                // private 
                childHasChecked: function(node){
                    var childNodes = node.childNodes;
                    if (childNodes || childNodes.length > 0) {
                        for (var i = 0; i < childNodes.length; i++) {
                            if (childNodes[i].getUI().checkbox.checked) 
                                return true;
                        }
                    }
                    return false;
                },
                
                toggleCheck: function(value){
                    var cb = this.checkbox;
                    if (cb) {
                        var checked = (value === undefined ? !cb.checked : value);
                        this.check(checked);
                    }
                }
            }); 
/**
 * 自定义树形结构的构造函数
 * @param: jsonArrayTree, 从服务器获取的节点数组，该节点数组应当已经进行了排序。
 * @param: jsonMeta, 树形结构的元数据，采用Json数据格式
 * @param: basePath, 点击树节点时，请求资源的基本路径
 */
SelfTreeGenerator = function (jsonArrayTree, jsonMeta, basePath, iconCls, cls, method) {
	if (jsonMeta === null) {
		/**
		 * nodeId : 节点编号字段名
		 * parentNodeId : 父节点编号字段名
		 * nodeName : 节点名称字段名
		 * nodeHref : 节点连接地址字段名
		 * leafField : 判断是否为叶节点的字段名
		 */
		this.jsonMeta = {nodeId:"", parentNodeId:"", nodeName:"", nodeHref:"", leafField:""};
	} else {
		this.jsonMeta = jsonMeta;
	}
	this.jsonArrayTree = jsonArrayTree;
	this.basePath = basePath;
	if(iconCls !== undefined){
		this.iconCls = iconCls;
	}
	if(cls !== undefined){
		this.cls = cls;
	}
	if(method !== undefined){
		this.method = method;
	}
};

/**
 * 生成自定义树形结构的成员函数
 * @param: whehtervisible, 是否进行可见性检查
 * @param: whethercheck, 树形结构的节点前是否显示复选框
 * @param: checked, 当whethercheck为true时，该参数表示生成树形结构时全部勾选所有的复选框，如果wherthercheck为false，则该参数没有作用
 * @param: whetherexpand, 是否展开树
 * @param: whetherhref, 判断树形结构的节点是否能够请求其它的服务器资源
 * @param: hrefTarget, 当whetherhref为true时，表示树形结构节点的href；当whetherhref为false时，此参数没有作用
 * @param: hrefStartLevel, 表示从第几层节点开始放置href 
 * 当whethercheck为ture是使用级联选择的TreeCheckNodeUI，去除了原来的tree.on("checkchange")
 */
SelfTreeGenerator.prototype.generate = function (whethercheck, checked, whetherexpand, whetherhref, hrefTarget) {
	var tree = null;
	var nodeArray = new Array();
	var old_node = null;
	var number=1;
	for (var i = 0; i < this.jsonArrayTree.length; i = i + 1) {
		var currentNode = this.jsonArrayTree[i];
		var img=currentNode[this.jsonMeta.nodeImage];
		/*if(img!=undefined){
			var nodeconfig = {type:currentNode[this.jsonMeta.nodeType],id:currentNode[this.jsonMeta.nodeId].toString(), text:currentNode[this.jsonMeta.nodeName], leaf:false, iconCls:(img), cls:(this.cls === undefined?'':this.cls[currentNode[this.jsonMeta.nodeType]])};
		}else{
			var nodeconfig = {type:currentNode[this.jsonMeta.nodeType],id:currentNode[this.jsonMeta.nodeId].toString(), text:currentNode[this.jsonMeta.nodeName], leaf:false, iconCls:(this.iconCls === undefined?'':this.iconCls[currentNode[this.jsonMeta.nodeType]]), cls:(this.cls === undefined?'':this.cls[currentNode[this.jsonMeta.nodeType]])};
		}*/
		if(img!=undefined){
			var nodeconfig = {type:currentNode[this.jsonMeta.nodeType],id:currentNode[this.jsonMeta.nodeId].toString(), text:currentNode[this.jsonMeta.nodeName],etext:currentNode[this.jsonMeta.nodeNameEn], leaf:false, iconCls:(img), cls:(this.cls === undefined?'':this.cls[currentNode[this.jsonMeta.nodeType]]),projectRcid:currentNode[this.jsonMeta.projectRcid],projectName:currentNode[this.jsonMeta.projectName],procaseRcid:currentNode[this.jsonMeta.procaseRcid],areaRcid:currentNode[this.jsonMeta.areaRcid],phaseRcid:currentNode[this.jsonMeta.phaseRcid], asynchronousTag:currentNode[this.jsonMeta.asynchronousTag]};
		}else{
			var nodeconfig = {type:currentNode[this.jsonMeta.nodeType],id:currentNode[this.jsonMeta.nodeId].toString(), text:currentNode[this.jsonMeta.nodeName],etext:currentNode[this.jsonMeta.nodeNameEn], leaf:false, iconCls:(this.iconCls === undefined?'':this.iconCls[currentNode[this.jsonMeta.nodeType]]), cls:(this.cls === undefined?'':this.cls[currentNode[this.jsonMeta.nodeType]]),projectRcid:currentNode[this.jsonMeta.projectRcid],projectName:currentNode[this.jsonMeta.projectName],procaseRcid:currentNode[this.jsonMeta.procaseRcid],areaRcid:currentNode[this.jsonMeta.areaRcid], phaseRcid:currentNode[this.jsonMeta.phaseRcid], menus:currentNode[this.jsonMeta.menus], asynchronousTag:currentNode[this.jsonMeta.asynchronousTag] };
		}
		if (i === 0) {
			nodeconfig.expanded = true;
			if(whethercheck){
				nodeconfig.uiProvider = Ext.tree.TreeCheckNodeUI;
				nodeconfig.checked = checked;
			}
			if(whetherhref && currentNode[this.jsonMeta.nodeHref] !== ''){
				//nodeconfig.href = this.basePath + currentNode[this.jsonMeta.nodeHref];
				nodeconfig.url = this.basePath + currentNode[this.jsonMeta.nodeHref];
				nodeconfig.hrefTarget = hrefTarget;
			}
			if(currentNode[this.jsonMeta.leafField] !== undefined && currentNode[this.jsonMeta.leafField] === true){
				nodeconfig.leaf = true;
			}
			tree = new Ext.tree.TreeNode(nodeconfig);			
			old_node = tree;
			nodeArray.push(tree);
		} else {
			nodeconfig.expanded = whetherexpand;
			var node = null;
			if(whethercheck){
				nodeconfig.uiProvider = Ext.tree.TreeCheckNodeUI;
				nodeconfig.checked = checked;
			}
			if(whetherhref && currentNode[this.jsonMeta.nodeHref] !== ''){
				//nodeconfig.href = this.basePath + currentNode[this.jsonMeta.nodeHref];
				nodeconfig.url = this.basePath + currentNode[this.jsonMeta.nodeHref];
				nodeconfig.hrefTarget = hrefTarget;
			}
			if(currentNode[this.jsonMeta.leafField] !== undefined && currentNode[this.jsonMeta.leafField] === true){
				nodeconfig.leaf = true;
			}
			node = new Ext.tree.TreeNode(nodeconfig);
			
			if(this.method !== undefined){
				if(nodeconfig.url !== undefined){	
					var n = node;
					node.on('click',function(n){
						var c = Ext.getCmp('center-panel');
	                 	//if(c.getComponent("tab_welcome")){
	        			//	c.remove(c.getComponent("tab_welcome"));
	        			//}
	                	var tab = c.getComponent("tab_"+n.attributes.id);
	    			    if(tab){ 
	    			    	c.setActiveTab(tab);
	    			    } //判断是否已经打开该面板
	    			    else{
	    			    	var	htmlurl = '<iframe name="iftab_'+n.attributes.id+'" scrolling="auto" frameborder="0" width="100%" height="100%" src="'+n.attributes.url+ (n.attributes.url.indexOf('?')!=-1?'&':'?')+'areaRcid='+n.attributes.areaRcid+'&projectRcid='+n.attributes.projectRcid+'&procaseRcid='+n.attributes.procaseRcid+'&phaseRcid='+n.attributes.phaseRcid+'&asynchronousTag='+n.attributes.asynchronousTag+'&nodeId='+n.attributes.id+'"></iframe>';
	    			    	var tab = c.add({
	  	    					'id':'tab_'+n.attributes.id,
	  	    					'title':n.attributes.text,
	  	    					tabWidth :120,
	  	    					tabTip :n.attributes.text,
	  	    					closable:true,  //通过html载入目标页
	  	    					autoDestroy:true,
	  	    					listeners: {'destroy':function(){
	  	    						//回收资源
	  	    					}},
	  	    					//html:'<iframe name="iftab_'+n.attributes.id+'" scrolling="auto" frameborder="0" width="100%" height="100%" src="'+n.attributes.url+'"></iframe>'
	  	    					html: htmlurl //通过html载入目标页
	  	    				});
	  	    			    c.setActiveTab(tab);
	  	    			    //为等待加载页面加等待提示
		    			    var msg = ' <span id="loading-msg"><img  src="images/000.gif" /></span><br><font size=2 ><b>页面加载中,请稍后...</b></font>';   
						  	var body =  Ext.get('center-panel');   
							body.mask(msg,'loading-module');   
	    			    }
				//	  	Ext.fly('loading-msg').center();    
					},this);
				}
			}
			
			if (currentNode[this.jsonMeta.parentNodeId].toString() === old_node.id) {
				old_node.appendChild(node);
			} else {
				var parentNode = null;
				for (var j = 0; j < nodeArray.length; j=j+1) {
					if (nodeArray[j].id.toString() === currentNode[this.jsonMeta.parentNodeId].toString()) {
						parentNode = nodeArray[j];
						break;
					}
				}
				parentNode.appendChild(node);
				old_node = parentNode;
			}
			nodeArray.push(node);
		}
	}
	
	this.tree = tree;
	return tree;
};

//cust 移植方法
SelfTreeGenerator.prototype.generateConf = function (conf) {
	var whethercheck = conf.whethercheck;
	var checked = conf.checked;
	var whetherexpand = conf.whetherexpand;
	var whetherhref = conf.whetherhref;
	var hrefTarget = conf.hrefTarget;
	var project = conf.project || {};
	var tree = null;
	var nodeArray = new Array();
	var old_node = null;
	var number=1;
	var _this = this;
	for (var i = 0; i < this.jsonArrayTree.length; i = i + 1) {
		var currentNode = this.jsonArrayTree[i];
		var img=currentNode[this.jsonMeta.nodeImage];
		if(img!=undefined){
			var nodeconfig = {type:currentNode[this.jsonMeta.nodeType],id:currentNode[this.jsonMeta.nodeId].toString(), text:currentNode[this.jsonMeta.nodeName], leaf:false, iconCls:(img), cls:(this.cls === undefined?'':this.cls[currentNode[this.jsonMeta.nodeType]]),projectRcid:project.rcid,areaRcid:project.arcid, asynchronousTag:project.asynchronousTag};
		}else{
			var nodeconfig = {type:currentNode[this.jsonMeta.nodeType],id:currentNode[this.jsonMeta.nodeId].toString(), text:currentNode[this.jsonMeta.nodeName], leaf:false, iconCls:(this.iconCls === undefined?'':this.iconCls[currentNode[this.jsonMeta.nodeType]]), cls:(this.cls === undefined?'':this.cls[currentNode[this.jsonMeta.nodeType]]), projectRcid:project.rcid,areaRcid:project.arcid, asynchronousTag:project.asynchronousTag };
		}
		nodeconfig.qtip = nodeconfig.text;
		if (i === 0) {
			nodeconfig.expanded = true;
			if(whethercheck){
				selfCheckModel = Ext.isEmpty(_this.checkModel)?'cascade':_this.checkModel;
				nodeconfig.uiProvider = Ext.tree.TreeCheckNodeUI;
				nodeconfig.checked = checked;
			}
			if(whetherhref && currentNode[this.jsonMeta.nodeHref] !== ''){
				//nodeconfig.href = this.basePath + currentNode[this.jsonMeta.nodeHref];
				nodeconfig.url = this.basePath + currentNode[this.jsonMeta.nodeHref];
				nodeconfig.hrefTarget = hrefTarget;
			}
			if(currentNode[this.jsonMeta.leafField] !== undefined && currentNode[this.jsonMeta.leafField] === true){
				nodeconfig.leaf = true;
			}
			
			tree = new Ext.tree.TreeNode(nodeconfig);			
			old_node = tree;
			nodeArray.push(tree);
		} else {
			nodeconfig.expanded = whetherexpand;
			var node = null;
			if(whethercheck){
				selfCheckModel = Ext.isEmpty(_this.checkModel)?'cascade':_this.checkModel;
				nodeconfig.uiProvider = Ext.tree.TreeCheckNodeUI;
				nodeconfig.checked = checked;
			}
			if(whetherhref && currentNode[this.jsonMeta.nodeHref] !== ''){
				//nodeconfig.href = this.basePath + currentNode[this.jsonMeta.nodeHref];
				nodeconfig.url = this.basePath + currentNode[this.jsonMeta.nodeHref];
				nodeconfig.hrefTarget = hrefTarget;
			}
			if(currentNode[this.jsonMeta.leafField] !== undefined && currentNode[this.jsonMeta.leafField] === true){
				nodeconfig.leaf = true;
			}
			node = new Ext.tree.TreeNode(nodeconfig);
			
			if(this.method !== undefined){
				if(nodeconfig.url !== undefined){
					var n = node;
					node.on('click',function(n){
						var c = Ext.getCmp('center-panel');
	                 	//if(c.getComponent("tab_welcome")){
	        			//	c.remove(c.getComponent("tab_welcome"));
	        			//}
	                	var tab = c.getComponent("tab_"+n.attributes.id);
	    			    if(tab){
	    			    	c.setActiveTab(tab);
	    			    } //判断是否已经打开该面板
	    			    else{
	    			    	var tab = c.add({
	    			    		id:'tab_'+n.attributes.id,
	    			    		//title:'<P><MARQUEE direction="left" behavior="scroll" scrollamount=4 scrolldelay=0 onmouseover=this.stop() onmouseout=this.start()>'+n.attributes.text+'</MARQUEE></P>',
	    			    		title:n.attributes.text,
	    			    		tabTip : n.attributes.text,
	    			    		closable:true,
	    			    		autoDestroy:true,				
	    			    		listeners: {'destroy':function(){
	    			    			//回收资源
	    			    		}},
	    			    		html: '<iframe name="iftab_'+n.attributes.id+'" scrolling="auto" frameborder="0" width="100%" height="100%" src="'+n.attributes.url+ (n.attributes.url.indexOf('?')!=-1?'&':'?')+'areaRcid='+n.attributes.areaRcid+'&projectRcid='+n.attributes.projectRcid+'&asynchronousTag='+n.attributes.asynchronousTag+'&nodeId='+n.attributes.id+'"></iframe>'
	    			    	});
	    			    	c.setActiveTab(tab);
	    			    	//为等待加载页面加等待提示
	    			    	var msg = ' <span id="loading-msg"><img width ="180" src="images/000.gif" /></span><br /><font style="margin-left:20px;" color="#484891" size ="2">正在加载页面，请稍候...</font>';   
	    			    	var body =  Ext.get('center-panel');
	    			    	body.mask(msg,'loading-module'); 
	    			    	setTimeout("Ext.get('center-panel').unmask();",1000*30);
	    			    }
					},this);
				}
			}
			
			if (currentNode[this.jsonMeta.parentNodeId].toString() === old_node.id) {
				old_node.appendChild(node);
			} else {
				var parentNode = null;
				for (var j = 0; j < nodeArray.length; j=j+1) {
					if (nodeArray[j].id.toString() === currentNode[this.jsonMeta.parentNodeId].toString()) {
						parentNode = nodeArray[j];
						break;
					}
				}
				parentNode.appendChild(node);
				old_node = parentNode;
			}
			nodeArray.push(node);
		}
	}
	
	this.tree = tree;
	return tree;	
}


/**
 * 更新自定义树形结构的内容
 * @param: jsonArrayTree 从服务器获取的节点数组，该节点数组应当已经进行了排序。
 */
SelfTreeGenerator.prototype.reloadData = function (jsonArrayTree,lastVisibleLevel, whethercheck, checked, whetherhref, hrefTarget, hrefStartLevel) {
	this.jsonArrayTree = jsonArrayTree;
	this.generate(lastVisibleLevel, whethercheck, checked, whetherhref, hrefTarget, hrefStartLevel);
};

/**
 * 根据数据自动勾选树形结构的节点
 * @param: jsonArrayTreeForCheck,对象数组形式的节点数据
 */
SelfTreeGenerator.prototype.autoCheckTreeNode = function(jsonArrayTreeForCheck){
	
	for(var i=0;i<jsonArrayTreeForCheck.length;i=i+1){
		var currentCheckNode = jsonArrayTreeForCheck[i];
		//alert(currentCheckNode[this.jsonMeta.nodeId]);
		var node = this.findTreeNode(this.tree,'id',currentCheckNode[this.jsonMeta.nodeId]);
		//只有叶子节点时启动toggleCheck事件
		if(node !== null&&node.leaf==true){
			
			if(node){
				node.ui.toggleCheck(true);
			}
			
		}
	}
	
};

SelfTreeGenerator.prototype.disableTreeNodeByLevel = function(level){
	this.tranverseAndDisable(this.tree,level);
};

SelfTreeGenerator.prototype.tranverseAndDisable = function(node, level){
	for(var i=0;i<this.jsonArrayTree.length;i=i+1){
		var node = this.findTreeNode(this.tree, 'id', this.jsonArrayTree[i][this.jsonMeta.nodeId]);
		if(this.jsonArrayTree[i].modulelevel < level){
			node.disable();
		}else{
			node.enable();
		}
	}
};



/**
 * private
 * 根据属性与值查找符合条件的某个节点
 * @param: rootNode, 父节点
 * @param: attribute, 属性的名称
 * @param: value, 属性的值
 */
SelfTreeGenerator.prototype.findTreeNode = function(rootNode,attribute,value){
	if(rootNode[attribute] === value){
		return rootNode;
	}
		
	if(rootNode.childNodes.length !== 0){
		for(var i=0;i<rootNode.childNodes.length ;i=i+1){
			var tmp_node = this.findTreeNode(rootNode.childNodes[i],attribute,value);
			if(tmp_node !== null)
				return tmp_node;
		}
		return null;
	}else{
		return null;
	}
};

/**
 * 事件处理函数，当勾选或取消节点前的复选框时触发
 * @param node
 * @param status
 * @return
 */

 
SelfTreeGenerator.prototype.onCheckChanged = function (node, status) {
	if (status){ //勾选复选框
		//勾选所有父节点
	this.checkAllParents(node);
	}
	else//取消勾选复选框
	{
		//取消勾选所有子节点
	this.uncheckAllChildren(node);
	}
};

/**
 * private
 * 勾选该节点的所有父节点，该函数为递归函数
 * @param node
 * @return
 */
SelfTreeGenerator.prototype.checkAllParents = function (node) {
	if (node.parentNode === null) {
		return;
	} else {
		if (!node.parentNode.ui.isChecked()) {
			node.parentNode.ui.toggleCheck(true);
			this.checkAllParents(node.parentNode);
		}
	}
};

/**
 * private
 * 勾选某个节点下的所有子节点，该函数为递归函数
 * @param node
 * @return
 */
SelfTreeGenerator.prototype.checkAllChildren = function (node) {
	var allChildren = node.childNodes;
	if (allChildren.length !== 0) {
		for (var i = 0; i < allChildren.length; i = i + 1) {
			if (!allChildren[i].ui.isChecked()) {
				allChildren[i].ui.toggleCheck(true);
				this.checkAllChildren(allChildren[i]);
			}
		}
	}
};

/**
 * private
 * 取消选择某个节点的所有子节点，该函数为递归函数
 * @param node
 * @return
 */
SelfTreeGenerator.prototype.uncheckAllChildren = function (node) {
	var allChildren = node.childNodes;
	if (allChildren !== null) {
		for (var i = 0; i < allChildren.length; i = i + 1) {
			if (allChildren[i].ui.isChecked()) {
				allChildren[i].ui.toggleCheck(false);
				this.uncheckAllChildren(allChildren[i]);
			}
		}
	}
};


/**
 * public static
 * 根据树形结构生成JsonArray
 * @param rootNode
 * @param jsonMeta
 * @return
 */
SelfTreeGenerator.generateJsonArrayForTree = function (rootNode,jsonMeta){
	var array = new Array(); 
	SelfTreeGenerator.tranversTreeForArray(rootNode,array,jsonMeta);
	return array;
};

/**
 * private static
 * 遍历整个树形结构，生成JsonArray
 */
SelfTreeGenerator.tranversTreeForArray = function (node, array, jsonMeta){
	var item = {};
	if(node.ui.isChecked()){
		item[jsonMeta.nodeId] = node.id;
		array.push(item);
	}
	
	if(node.childNodes.length !== 0){
		for(var i=0;i<node.childNodes.length ;i=i+1){
			SelfTreeGenerator.tranversTreeForArray(node.childNodes[i],array,jsonMeta);
		}
	}
};

