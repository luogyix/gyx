 Moneyfield = Ext.extend(Ext.form.NumberField, {  

	decimalPrecision: 2,
    allowDecimals: true,
    cls: 'numReadOnly',
    FormatComma: true,
    /** 
     * -------------------------------------   
     * 作为隐藏域的name属性 
     * ------------------------------------- 
     */ 
    passName : 'passName', 
    
    initEvents: function () {
        Ext.form.NumberField.superclass.initEvents.call(this);
        var allowed = this.baseChars + '';
        if (this.allowDecimals) {
            allowed += this.decimalSeparator;
        }
        if (this.FormatComma) {
            allowed += ",";
        }
        if (this.allowNegative) {
            allowed += "-";
        }
        this.stripCharsRe = new RegExp('[^' + allowed + ']', 'gi');
        var keyPress = function (e) {
            var k = e.getKey();
            if (!Ext.isIE && (e.isSpecialKey() || k == e.BACKSPACE || k == e.DELETE)) {
                return;
            }
            var c = e.getCharCode();
            if (allowed.indexOf(String.fromCharCode(c)) === -1) {
                e.stopEvent();
            }
        };
        this.el.on("keypress", keyPress, this);
        
        var focus = function (e) {
	        var v = this.parseValue(this.getRawValue());
	
	        if (String(v).trim().length > 0) {
	            this.setValueNoCommas(this.fixPrecision(v));
	
	        }
        };
        this.el.on("focus", focus, this);
    },
    
    initComponent : function() {   
    	Moneyfield.superclass.initComponent.call(this);   
    },
    
    // private
    validateValue: function (value) {
    	var tmpvalue = this.removeCommas(String(value));
        if (!Ext.form.NumberField.superclass.validateValue.call(this, tmpvalue)) {
            return false;
        }
//								        if (!Ext.form.NumberField.superclass.validateValue
//												.call(this, value)) {
//								            return false;
//								        }
        if (value.length < 1) { // if it's blank
            // and textfield
            // didn't flag
            // it then it's
            // valid
            return true;
        }
        if (this.FormatComma) {
            value = this.removeCommas(String(value));
        }
        value = String(value).replace(this.decimalSeparator, ".");
        if (isNaN(value)) {
            this.markInvalid(String.format(this.nanText, value));
            return false;
           
        }
        var num = this.parseValue(value);
        if (num < this.minValue) {
            this.markInvalid(String.format(this.minText,this.minValue));
            return false;
        }
        if (num > this.maxValue) {
            this.markInvalid(String.format(this.maxText,this.maxValue));
            return false;
        }
        return true;
    },
    fixPrecision: function (value) {
        var nan = isNaN(value);
        if (!this.allowDecimals || this.decimalPrecision == -1 || nan || !value) {
            return nan ? '' : value;
        }
        return parseFloat(parseFloat(value).toFixed(this.decimalPrecision));
    },

    setValue: function (v) {
        v = typeof v == 'number' ? v : (String(this.removeCommas(v)).replace(this.decimalSeparator, "."));
        v = isNaN(v) ? '' : String(v).replace(".", this.decimalSeparator);
        if (String(v).length > 0)
            v = parseFloat(v).toFixed(this.decimalPrecision);
        // if(this.FormatComma)
        // v=this.formatCommaStyle(v);
        Ext.form.NumberField.superclass.setValue.call(this, v);
        if (this.FormatComma&& String(v).length > 0) {
            v = this.addCommas(v);
            Ext.form.NumberField.superclass.setRawValue.call(this, v);            
        }        
        var temp=v;
        temp=this.removeCommas(temp);
        this.passField.value=temp;
    },
    
    setValueNoCommas: function (v) {
        v = typeof v == 'number' ? v : (String(this.removeCommas(v)).replace(this.decimalSeparator, "."));
        v = isNaN(v) ? '' : String(v).replace(".", this.decimalSeparator);
        if (String(v).length > 0)
            v = parseFloat(v).toFixed(this.decimalPrecision);
        // if(this.FormatComma)
        // v=this.formatCommaStyle(v);
        Ext.form.NumberField.superclass.setValue.call(this, v);
        if (this.FormatComma&& String(v).length > 0) {
            v = this.removeCommas(v);
            Ext.form.NumberField.superclass.setRawValue.call(this, v);            
        }        
        var temp=v;
        temp=this.removeCommas(temp);
        this.passField.value=temp;
    },
            
    getValue: function(){    	
    		var temp=Ext.form.NumberField.superclass.getValue.call(this);
    		temp=this.removeCommas(temp);
    		return temp;
    	
    },
    
    parseValue: function (value) {
        if (this.FormatComma)
            value = this.removeCommas(String(value));
        value = parseFloat(String(value).replace(this.decimalSeparator,"."));

        return isNaN(value) ? '' : value;
    },
    beforeBlur: function () {
        var v = this.parseValue(this.getRawValue());
        if (String(v).trim().length > 0) {
            this.setValue(this.fixPrecision(v));
        }else{
       	  this.setValue('');
        }
        
    }
	,
    addCommas: function (nStr) {
        nStr += '';
        if (nStr.length == 0)
            return '';
        x = nStr.split('.');
        x1 = x[0];
        x2 = x.length > 1 ? '.' + x[1] : '';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx,'$1' + ',' + '$2');
        }
        return x1 + x2;

    },
    removeCommas: function (nStr) {
        nStr = nStr + '';
        var r = /(\,)/;
        while (r.test(nStr)) {
            nStr = nStr.replace(r, '');
        }
        return nStr;

    },
    listeners : {
    	'render' : {   
            fn : function() {   
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
                        name : this.passName,   
                        id : this.id  
                    }, 'before', true)   
                }   			                                  
                this.el.dom.removeAttribute('name');   
            }   
        }
    }

}); 
Ext.reg('moneyfield', Moneyfield);