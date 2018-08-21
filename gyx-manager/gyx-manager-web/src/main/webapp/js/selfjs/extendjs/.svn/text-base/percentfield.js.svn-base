
Ext.namespace("Ext.ux");
Ext.ux.CustomNumberField = Ext.extend(Ext.form.NumberField, {fieldClass:"x-form-field x-form-customnumber-field", prefixChar:"", suffixChar:"", numberDelim:",", delimLength:3, alwaysShowCents:true, onRender:function () {
	Ext.ux.CustomNumberField.superclass.onRender.apply(this, arguments);
	var name = this.name || this.el.dom.name;
	this.hiddenField = this.el.insertSibling({tag:"input", type:"hidden", name:name, value:this.parseValue(this.value)});
	this.hiddenName = name;
	this.el.dom.removeAttribute("name");
	this.el.on({keyup:{scope:this, fn:this.updateHidden}, blur:{scope:this, fn:this.updateHidden}}, Ext.isIE ? "after" : "before");
	this.setValue = this.setValue.createSequence(this.updateHidden);
}, initEvents:function () {
	Ext.ux.CustomNumberField.superclass.initEvents.call(this);
	var allowed = this.baseChars + "";
	var stripBeforeParse = [];
	if (this.allowDecimals) {
		allowed += this.decimalSeparator;
	}
	if (this.allowNegative) {
		allowed += "-";
	}
	if (this.prefixChar) {
		allowed += this.prefixChar;
		stripBeforeParse.push(Ext.escapeRe(this.prefixChar));
	}
	if (this.suffixChar) {
		allowed += this.suffixChar;
		stripBeforeParse.push(Ext.escapeRe(this.suffixChar));
	}
	if (this.numberDelim) {
		allowed += this.numberDelim;
		stripBeforeParse.push(Ext.escapeRe(this.numberDelim));
	}
	this.maskRe = new RegExp("[" + Ext.escapeRe(allowed) + "]");
	this.stripBeforeParseRe = new RegExp("[" + stripBeforeParse.join("|") + "]", "g");
}, updateHidden:function () {
	this.hiddenField.dom.value = this.parseValue(accDiv(this.getValue(),100));
}, getErrors:function () {
	var errors = Ext.form.NumberField.superclass.getErrors.apply(this, arguments);
	return errors;
}, setValue:function (v) {
	v = this.formatValue(this.parseValue(v));
	Ext.form.NumberField.superclass.setValue.call(this, v);
}, parseValue:function (value) {
	value = String(value).replace(this.stripBeforeParseRe, "");
	value = Ext.ux.CustomNumberField.superclass.parseValue.call(this, value);
	return value;
}, formatValue:function (value) {
	if (Ext.isEmpty(value)) {
		return "";
	}
	value = String(Ext.ux.CustomNumberField.superclass.fixPrecision.call(this, value));
	var vSplit = value.split(".");
	var cents = (vSplit[1]) ? "." + vSplit[1] : "";
	if (this.alwaysShowCents && cents == "") {
		cents = ".00";
	}
	if (this.numberDelim && this.delimLength) {
		var numbers = vSplit[0].split("");
		var sNumbers = [];
		var c = 0;
		while (numbers.length > 0) {
			c++;
			if (c > this.delimLength) {
				c = 1;
			}
			sNumbers.unshift(numbers.pop());
			if (c == this.delimLength && numbers.length > 0) {
				sNumbers.unshift(this.numberDelim);
			}
		}
		value = sNumbers.join("") + cents;
	} else {
		value = vSplit[0] + cents;
	}
	if (this.prefixChar) {
		value = this.prefixChar + String(value);
	}
	if (this.suffixChar) {
		value = String(value) + this.suffixChar;
	}
	return value;
}});
Ext.reg("custnumberfield", Ext.ux.CustomNumberField);
/*
Ext.ux.MoneyField = Ext.extend(Ext.ux.CustomNumberField, {
currencyChar: '￥',
initComponent: function(){
Ext.apply(this,{
prefixChar: this.currencyChar  
});
Ext.ux.MoneyField.superclass.initComponent.apply(this,arguments);
}
});

Ext.reg('moneyfield', Ext.ux.MoneyField);
*/
Ext.ux.PercentField = Ext.extend(Ext.ux.CustomNumberField, {percentChar:"%", initComponent:function () {
	Ext.apply(this, {suffixChar:this.percentChar});
	Ext.ux.PercentField.superclass.initComponent.apply(this, arguments);
}});
Ext.reg("percentfield", Ext.ux.PercentField);

