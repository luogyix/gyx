ComboBoxTree = Ext.extend(Ext.form.ComboBox, {   

    /** 
     * -------------------------------------   
     * 作为隐藏域的name属性 
     * ------------------------------------- 
     */ 
    passName : 'id',   
    

    /** 
     * -------------------------------------   
     * 是否允许非叶子结点的单击事件 
     *   
     * @default false   
     * ------------------------------------- 
     */ 
    allowUnLeafClick : true,   

    /** 
     * ---------------------   
     * 树渲染的模板tpl   
     * --------------------- 
     */ 
    // tpl: '<div id="treeTpl"></div>', //html代码   
    /** 
     * ----------------------- 
     * 树显示的高度，默认为180 
     * ----------------------- 
     */ 
    treeHeight : 180,   

    store : new Ext.data.SimpleStore({   
        fields : [],   
        data : [[]]   
    }),   
       
    //Default   
    editable : false, // 禁止手写及联想功能   
    mode : 'local',   
    triggerAction : 'all',   
	resizable: true,
	typeAhead: true,
    selectedClass : '', 
    onSelect : Ext.emptyFn,   
    emptyText : this.emptyText,   
    grow:true,
    growMax:500,

    /** 
     * 清空值 
     */ 
    clearValue : function() {   
        if (this.passField) {   
            this.passField.value = '';   
        }   

        this.setRawValue('');   
            },   
       
    /** 
     * 设置传值 
     * @param passvalue 
     */ 
    setPassValue: function(passvalue){   
        if (this.passField){ 
            this.passField.value = passvalue;   
        }else{
			 this.passField.value=this.value;
		}
    },   
    
    /** 
     * 取值 
     * @param passvalue 
     */ 
    getPassValue: function(){   
    	if (this.passField){ 
    		return this.passField.value;
    	}else{
    		return this.value;
    	}
    },   

    /** 
     * --------------------------------------   
     * 下拉树被点击事件添加一处理方法 
     * @param node 
     * -------------------------------------- 
     */ 
    onTreeSelected : function(node) {   

    },   

    /** 
     * ----------------------------------   
     * 树的单击事件处理 
     * @param node,event 
     * ---------------------------------- 
     */ 
    treeClk : function(node, e) {   
      //  if (!node.isLeaf() && !this.allowUnLeafClick) {   
       //     e.stopEvent();// 非叶子节点则不触发   
      //      return;   
      //  }   
        this.setValue(node.text);// 设置option值  
       // this.setValue(node.text);
        this.collapse();// 隐藏option列表   

        if (this.passField)   
            this.passField.value = node.id;// 以树的节点ID传递   

        // 选中树节点后的触发事件   
        this.fireEvent('treeselected', node);   

    },   

    /** 
     * 初始化 
     * Init 
     */ 
    initComponent : function() {   
        ComboBoxTree.superclass.initComponent.call(this);   
        this.tree.autoScroll = true;   
        this.treeHeight=this.tree.height;   
        this.tree.containerScroll = true;   
        this.tplId = Ext.id();   
       // this.width=;
        // overflow:auto"   
        this.tpl = '<tpl for="."><div id="' + this.tplId + '" style="height:' + this.treeHeight   
                + 'px";overflow:hidden;"></div></tpl>';   

        /** 
         * -----------------------   
         * 添加treeselected事件， 
         * 选中树节点会激发这个事 
         * 件， 参数为树的节点 
         * ------------------------ 
         */ 
        this.addEvents('treeselected');   
        // this.on('treeselected',this.onTreeSelected,this);   
    },   

    /** 
     * ------------------ 
     * 事件监听器   
     * Listener 
     * ------------------ 
     */ 
    
    
    listeners : {   
        'expand':{   
            fn : function() {   
                if (!this.tree.rendered && this.tplId) {   
                    this.tree.render(this.tplId);   
                }   
                this.tree.show();   
            },   
            single : true 
        },   

        'render' : {   
            fn : function() {   

                this.tree.on('click', this.treeClk, this);   

                /** 
                 * -------------------------------------------   
                 * 创建隐藏输入域<input /> 
                 * 并将其dom传给passField   
                 * ------------------------------------------ 
                 */ 
                if (this.passName) {   
                    this.passField = this.getEl().insertSibling({   
                        tag : 'input',   
                        type : 'hidden',   
                        name : this.translateName==undefined?this.passName:this.translateName,   
                        id : this.passId || Ext.id()   
                    }, 'before', true)   
                }   
				
                this.passField.value = this.passValue !== undefined   
                        ? this.passValue   
                        : (this.value !== undefined ? this.value : '');   

                this.el.dom.removeAttribute('name');   
            }   
        },   
        'beforedestroy' : {   
            fn : function(cmp) {   
                this.purgeListeners();   
                this.tree.purgeListeners();   
            }   
        },
        'expandnode':{
        	 fn : function(node) {   
        		alert(node);
        	 }
        }
    }   

});   
// 修复ExtJS3.3中自动关闭下来树的Bug   
Ext.override(Ext.form.ComboBox, {   
      onViewClick : function(doFocus) {   
        var index = this.view.getSelectedIndexes()[0], s = this.store, r = s.getAt(index);   
        if (r) {   
          this.onSelect(r, index);   
        } else if (s.getCount() === 0) {   
          this.collapse();   
        }   
//        if (doFocus !== false) {   
//          this.el.focus();   
//        }   
      }   
    });  


/** 
* ---------------------------------   
* 将ComboBoxTree注册为Ext的组件,以便使用 
* Ext的延迟渲染机制，xtype:'combotree'   
* --------------------------------- 
*/ 
Ext.reg('combotree', ComboBoxTree);
