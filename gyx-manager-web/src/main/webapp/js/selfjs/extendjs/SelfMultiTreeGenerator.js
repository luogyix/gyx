//重写ext treecheckNodeUi 提供完整的级联选择
Ext.tree.TreeCheckNodeUI = function(){//Ext.tree.TreeCheckNodeUI插件
    Ext.tree.TreeCheckNodeUI.superclass.constructor.apply(this, arguments);
};
            
Ext.extend(Ext.tree.TreeCheckNodeUI, Ext.tree.TreeNodeUI, {
    renderElements: function(n, a, targetNode, bulkRender){
    	
        this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : '';
        
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
    }
}); 
/**
 * 自定义树形结构的构造函数
 * @param: jsonArrayTree, 从服务器获取的节点数组，该节点数组应当已经进行了排序。
 * @param: jsonMeta, 树形结构的元数据，采用Json数据格式
 * @param: basePath, 点击树节点时，请求资源的基本路径
 */
SelfMultiTreeGenerator = function (jsonArrayTree, jsonMeta, iconCls, cls) {
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
	if(iconCls !== undefined){
		this.iconCls = iconCls;
	}
	if(cls !== undefined){
		this.cls = cls;
	}
};

/**
 * 生成自定义树形结构的成员函数
 * @param: whetherexpand, 是否展开树
 */
SelfMultiTreeGenerator.prototype.generate = function (whetherexpand) {
	var tree = null;
	var nodeArray = new Array();
	var old_node = null;
	for (var i = 0; i < this.jsonArrayTree.length; i = i + 1) {
		var currentNode = this.jsonArrayTree[i];
		var nodeconfig = {type:currentNode[this.jsonMeta.nodeType],id:currentNode[this.jsonMeta.nodeId].toString(), text:currentNode[this.jsonMeta.nodeName],etext:currentNode[this.jsonMeta.nodeNameEn], leaf:false, iconCls:(this.iconCls === undefined?'':this.iconCls[currentNode[this.jsonMeta.nodeType]]), cls:(this.cls === undefined?'':this.cls[currentNode[this.jsonMeta.nodeType]])};
		nodeconfig.qtip = nodeconfig.text;
		if(currentNode[this.jsonMeta.leafField] !== undefined && currentNode[this.jsonMeta.leafField] === true){
			nodeconfig.leaf = true;
		}
		if (i === 0) {//root
			nodeconfig.expanded = true;
			tree = new Ext.tree.TreeNode(nodeconfig);			
			old_node = tree;
			nodeArray.push(tree);
		} else {
			nodeconfig.expanded = whetherexpand;
			var node = null;
			node = new Ext.tree.TreeNode(nodeconfig);
			
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
	//this.tree = tree;
	return tree;
};

SelfMultiTreeGenerator.prototype.generateReturnArray = function(whetherexpand, num){
//	var tmpTree = this.generate(whetherexpand);
	var array = [];
	for ( var i = 0; i < num; i++) {
//		array.push(tmpTree);
		array.push(this.generate(whetherexpand));
	}
	return array;
};

/**
 * 更新自定义树形结构的内容
 * @param: jsonArrayTree 从服务器获取的节点数组，该节点数组应当已经进行了排序。
 */
SelfMultiTreeGenerator.prototype.reloadData = function (jsonArrayTree,whetherexpand) {
	this.jsonArrayTree = jsonArrayTree;
	this.generate(whetherexpand);
};
	
/**
 * private
 * 根据属性与值查找符合条件的某个节点
 * @param: rootNode, 父节点
 * @param: attribute, 属性的名称
 * @param: value, 属性的值
 */
SelfMultiTreeGenerator.prototype.findTreeNode = function(rootNode,attribute,value){
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
 * public static
 * 根据树形结构生成JsonArray
 * @param rootNode
 * @param jsonMeta
 * @return
 */
SelfMultiTreeGenerator.generateJsonArrayForTree = function (rootNode,jsonMeta){
	var array = new Array(); 
	SelfMultiTreeGenerator.tranversTreeForArray(rootNode,array,jsonMeta);
	return array;
};

/**
 * private static
 * 遍历整个树形结构，生成JsonArray
 */
SelfMultiTreeGenerator.tranversTreeForArray = function (node, array, jsonMeta){
	var item = {};
	if(node.ui.isChecked()){
		item[jsonMeta.nodeId] = node.id;
		array.push(item);
	}
	
	if(node.childNodes.length !== 0){
		for(var i=0;i<node.childNodes.length ;i=i+1){
			SelfMultiTreeGenerator.tranversTreeForArray(node.childNodes[i],array,jsonMeta);
		}
	}
};

