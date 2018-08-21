// vim: ts=4:sw=4:nu:fdc=4:nospell
/**
 * Ext.ux.form.LovCombo, List of Values Combo
 *
 * @author    Ing. Jozef Sakáloš
 * @copyright (c) 2008, by Ing. Jozef Sakáloš
 * @date      16. April 2008
 * @version   $Id: Ext.ux.form.LovCombo.js 285 2008-06-06 09:22:20Z jozo $
 *
 * @license Ext.ux.form.LovCombo.js is licensed under the terms of the Open Source
 * LGPL 3.0 license. Commercial use is permitted to the extent that the 
 * code/component(s) do NOT become part of another Open Source or Commercially
 * licensed development library or toolkit without explicit permission.
 * 
 * License details: http://www.gnu.org/licenses/lgpl.html
 */
 
/*global Ext */

// add RegExp.escape if it has not been already added
if('function' !== typeof RegExp.escape) {
	RegExp.escape = function(s) {
		if('string' !== typeof s) {
			return s;
		}
		// Note: if pasting from forum, precede ]/\ with backslash manually
		return s.replace(/([.*+?^=!:${}()|[\]\/\\])/g, '\\$1');
	}; // eo function escape
}

// create namespace
Ext.ns('Ext.ux.form');
 
/**
 *
 * @class Ext.ux.form.LovCombo
 * @extends Ext.form.ComboBox
 */
Ext.ux.form.LovCombo = Ext.extend(Ext.form.ComboBox, {

	// {{{
    // configuration options
	/**
	 * @cfg {String} checkField name of field used to store checked state.
	 * It is automatically added to existing fields.
	 * Change it only if it collides with your normal field.
	 */
	 checkField:'checked'

	/**
	 * @cfg {String} separator separator to use between values and texts
	 */
    ,separator:','
	,assertValue:Ext.emptyFn
	/**
	 * @cfg {String/Array} tpl Template for items. 
	 * Change it only if you know what you are doing.
	 */
	// }}}
    // {{{
    ,initComponent:function() {
        
		// template with checkbox
		if(!this.tpl) {
			this.tpl = 
				 '<tpl for=".">'
				+'<div class="x-combo-list-item">'
				+'<img src="' + Ext.BLANK_IMAGE_URL + '" '
				+'class="ux-lovcombo-icon ux-lovcombo-icon-'
				+'{[values.' + this.checkField + '?"checked":"unchecked"' + ']}">'
				+'<div class="ux-lovcombo-item-text">{' + (this.displayField || 'text' )+ '}</div>'
				+'</div>'
				+'</tpl>'
			;
		}
 
        // call parent
        Ext.ux.form.LovCombo.superclass.initComponent.apply(this, arguments);

		// install internal event handlers
		this.on({
			 scope:this
			,beforequery:this.onBeforeQuery
			,blur:this.onRealBlur
		});

		// remove selection from input field
		this.onLoad = this.onLoad.createSequence(function() {
			if(this.el) {
				var v = this.el.dom.value;
				this.el.dom.value = '';
				this.el.dom.value = v;
			}
		});
 
    } // e/o function initComponent
    // }}}
	// {{{
    ,initList : function(){  
        if(!this.list){  
            var cls = 'x-combo-list';  
  
            this.list = new Ext.Layer({  
                parentEl: this.getListParent(),  
                shadow: this.shadow,  
                cls: [cls, this.listClass].join(' '),  
                constrain:false  
            });  
  
            var lw = this.listWidth || Math.max(this.wrap.getWidth(), this.minListWidth);  
            this.list.setSize(lw, 0);  
            this.list.swallowEvent('mousewheel');  
            this.assetHeight = 0;  
            if(this.syncFont !== false){  
                this.list.setStyle('font-size', this.el.getStyle('font-size'));  
            }  
            if(this.title){  
                this.header = this.list.createChild({cls:cls+'-hd', html: this.title});  
                this.assetHeight += this.header.getHeight();  
            }  
              
            if(this.showSelectAll){  
                this.selectall = this.list.createChild({  
                    cls:cls + 'item ux-combo-selectall-icon-unchecked ux-combo-selectall-icon',  
                    html: "选择全部"  
                });  
                this.selectall.on("click",function(el){  
                    if(this.selectall.hasClass("ux-combo-selectall-icon-checked")){  
                        this.selectall.replaceClass("ux-combo-selectall-icon-checked","ux-combo-selectall-icon-unchecked");  
                        this.deselectAll();  
                        this.selectall.dom.innerHTML="选择全部";
                    }else{  
                        this.selectall.replaceClass("ux-combo-selectall-icon-unchecked","ux-combo-selectall-icon-checked")  
                        this.selectAll();  
                        this.selectall.dom.innerHTML="取消全部";
                        var a;
                    }  
                },this);  
                this.assetHeight += this.selectall.getHeight();  
            }  
  
            this.innerList = this.list.createChild({cls:cls+'-inner'});  
            this.mon(this.innerList, 'mouseover', this.onViewOver, this);  
            this.mon(this.innerList, 'mousemove', this.onViewMove, this);  
            this.innerList.setWidth(lw - this.list.getFrameWidth('lr'));  
  
            if(this.pageSize){  
                this.footer = this.list.createChild({cls:cls+'-ft'});  
                this.pageTb = new Ext.PagingToolbar({  
                    store: this.store,  
                    pageSize: this.pageSize,  
                    renderTo:this.footer  
                });  
                this.assetHeight += this.footer.getHeight();  
            }  
  
            if(!this.tpl){  
                this.tpl = '<tpl for="."><div class="'+cls+'-item">{' + this.displayField + '}</div></tpl>';  
            }  
  
            this.view = new Ext.DataView({  
                applyTo: this.innerList,  
                tpl: this.tpl,  
                singleSelect: true,  
                selectedClass: this.selectedClass,  
                itemSelector: this.itemSelector || '.' + cls + '-item',  
                emptyText: this.listEmptyText  
            });  
  
            this.mon(this.view, 'click', this.onViewClick, this);  
  
            this.bindStore(this.store, true);  
  
            if(this.resizable){  
                this.resizer = new Ext.Resizable(this.list,  {  
                   pinned:true, handles:'se'  
                });  
                this.mon(this.resizer, 'resize', function(r, w, h){  
                    this.maxHeight = h-this.handleHeight-this.list.getFrameWidth('tb')-this.assetHeight;  
                    this.listWidth = w;  
                    this.innerList.setWidth(w - this.list.getFrameWidth('lr'));  
                    this.restrictHeight();  
                }, this);  
  
                this[this.pageSize?'footer':'innerList'].setStyle('margin-bottom', this.handleHeight+'px');  
            }  
        }  
    }
    
	/**
	 * Disables default tab key bahavior
	 * @private
	 */
	,initEvents:function() {
		Ext.ux.form.LovCombo.superclass.initEvents.apply(this, arguments);

		// disable default tab handling - does no good
		this.keyNav.tab = false;

	} // eo function initEvents
	// }}}
	// {{{
	/**
	 * clears value
	 */
	,clearValue:function() {
		this.value = '';
		this.setRawValue(this.value);
		this.store.clearFilter();
		this.store.each(function(r) {
			r.set(this.checkField, false);
		}, this);
		if(this.hiddenField) {
			this.hiddenField.value = '';
		}
		this.applyEmptyText();
	} // eo function clearValue
	// }}}
	// {{{
	/**
	 * @return {String} separator (plus space) separated list of selected displayFields
	 * @private
	 */
	,getCheckedDisplay:function() {
		var re = new RegExp(this.separator, "g");
		return this.getCheckedValue(this.displayField).replace(re, this.separator + ' ');
	} // eo function getCheckedDisplay
	// }}}
	// {{{
	/**
	 * @return {String} separator separated list of selected valueFields
	 * @private
	 */
	,getCheckedValue:function(field) {
		field = field || this.valueField;
		var c = [];

		// store may be filtered so get all records
		var snapshot = this.store.snapshot || this.store.data;

		snapshot.each(function(r) {
			if(r.get(this.checkField)) {
				c.push(r.get(field));
			}
		}, this);

		return c.join(this.separator);
	} // eo function getCheckedValue
	// }}}
	// {{{
	/**
	 * beforequery event handler - handles multiple selections
	 * @param {Object} qe query event
	 * @private
	 */
	,onBeforeQuery:function(qe) {
		qe.query = qe.query.replace(new RegExp(this.getCheckedDisplay() + '[ ' + this.separator + ']*'), '');
	} // eo function onBeforeQuery
	// }}}
	// {{{
	/**
	 * blur event handler - runs only when real blur event is fired
	 */
	,onRealBlur:function() {
		this.list.hide();
		var rv = this.getRawValue();
		var rva = rv.split(new RegExp(RegExp.escape(this.separator) + ' *'));
		var va = [];
		var snapshot = this.store.snapshot || this.store.data;

		// iterate through raw values and records and check/uncheck items
		Ext.each(rva, function(v) {
			snapshot.each(function(r) {
				if(v === r.get(this.displayField)) {
					va.push(r.get(this.valueField));
				}
			}, this);
		}, this);
		this.setValue(va.join(this.separator));
		this.store.clearFilter();
	} // eo function onRealBlur
	// }}}
	// {{{
	/**
	 * Combo's onSelect override
	 * @private
	 * @param {Ext.data.Record} record record that has been selected in the list
	 * @param {Number} index index of selected (clicked) record
	 */
	,onSelect:function(record, index) {
        if(this.fireEvent('beforeselect', this, record, index) !== false){

			// toggle checked field
			record.set(this.checkField, !record.get(this.checkField));

			// display full list
			if(this.store.isFiltered()) {
				this.doQuery(this.allQuery);
			}

			// set (update) value and fire event
			this.setValue(this.getCheckedValue());
            this.fireEvent('select', this, record, index);
        }
	} // eo function onSelect
	// }}}
	// {{{
	/**
	 * Sets the value of the LovCombo
	 * @param {Mixed} v value
	 */
	,setValue:function(v) {
		if(v) {
			v = '' + v;
			if(this.valueField) {
				this.store.clearFilter();
				this.store.each(function(r) {
					var checked = !(!v.match(
						 '(^|' + this.separator + ')' + RegExp.escape(r.get(this.valueField))
						+'(' + this.separator + '|$)'))
					;

					r.set(this.checkField, checked);
				}, this);
				this.value = this.getCheckedValue();
				this.setRawValue(this.getCheckedDisplay());
				if(this.hiddenField) {
					this.hiddenField.value = this.value;
				}
			}
			else {
				this.value = v;
				this.setRawValue(v);
				if(this.hiddenField) {
					this.hiddenField.value = v;
				}
			}
			if(this.el) {
				this.el.removeClass(this.emptyClass);
			}
		}
		else {
			this.clearValue();
		}
	} // eo function setValue
	// }}}
	// {{{
	/**
	 * Selects all items
	 */
	,selectAll:function() {
        this.store.each(function(record){
            // toggle checked field
            record.set(this.checkField, true);
        }, this);

        //display full list
        this.doQuery(this.allQuery);
        this.setValue(this.getCheckedValue());
    } // eo full selectAll
	// }}}
	// {{{
	/**
	 * Deselects all items. Synonym for clearValue
	 */
    ,deselectAll:function() {
		this.clearValue();
    } // eo full deselectAll 
	// }}}
	,beforeBlur:function(){
         this.setValue(this.getCheckedValue());
     }
     

}); // eo extend
 
// register xtype
Ext.reg('lovcombo', Ext.ux.form.LovCombo); 
 
// eof
