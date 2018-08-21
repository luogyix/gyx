'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var defaultTypes = ['aui-text-input', 'aui-number-input', 'aui-admin-input', 'aui-text-area', 'aui-checkbox', 'aui-radio', 'aui-currency-input', 'input', 'textarea'];

var FocusManager = {
  formFields: [],

  getFormFields: function getFormFields(component) {
    var _this = this;

    var formField = {};
    if (component && component.$options && component.$options.name) {
      var componentsName = component.$options.name;
      if (this.types.indexOf(componentsName) > -1 && !component.$props.disabled && !component.$el.dataset.outfocus) {
        formField = {
          element: component,
          elementTop: component.$el.getBoundingClientRect().top + component.$el.scrollTop + parseInt(window.getComputedStyle(component.$el).height) / 2,
          elementLeft: component.$el.getBoundingClientRect().left + component.$el.scrollLeft + parseInt(window.getComputedStyle(component.$el).width) / 2
        };
        this.formFields.push(formField);
      } else if (component.$children && component.$children.length > 0) {
        component.$children.forEach(function (child) {
          _this.getFormFields(child);
        });

        if (component && component.$el && component.$el.children) {
          var child = component.$el.children;
          for (var i = 0; i < child.length; i++) {
            for (var j = 0; j < this.types.length; j++) {
              if (this.types[j].toLowerCase() === child[i].tagName.toLowerCase()) {
                if (!child[i].disabled && !child[i].dataset.outfocus) {
                  formField = {
                    element: child[i],
                    elementTop: child[i].getBoundingClientRect().top + child[i].scrollTop + parseInt(window.getComputedStyle(child[i]).height) / 2,
                    elementLeft: child[i].getBoundingClientRect().left + child[i].scrollLeft + parseInt(window.getComputedStyle(child[i]).width) / 2
                  };
                  this.formFields.push(formField);
                }
              }
            }
            this.getFormFields(child[i]);
          }
        }
      }
    }
  },

  sortFormFields: function sortFormFields() {
    var formFieldTemp = {};
    for (var i = 0; i < this.formFields.length; i++) {
      for (var j = 0; j < this.formFields.length - 1 - i; j++) {
        if (this.formFields[j].elementTop > this.formFields[j + 1].elementTop) {
          formFieldTemp = this.formFields[j + 1];
          this.formFields[j + 1] = this.formFields[j];
          this.formFields[j] = formFieldTemp;
        } else if (this.formFields[j].elementTop === this.formFields[j + 1].elementTop) {
          if (this.formFields[j].elementLeft > this.formFields[j + 1].elementLeft) {
            formFieldTemp = this.formFields[j + 1];
            this.formFields[j + 1] = this.formFields[j];
            this.formFields[j] = formFieldTemp;
          }
        }
      }
    }
  },
  attach: function attach(root, options) {
    var _this2 = this;

    if (options && options.types && options.types.length > 0) {
      this.types = options.types;
    } else {
      this.types = defaultTypes;
    }
    if (!this.formFields) {
      this.formFields = [];
    }
    this.getFormFields(root);
    this.sortFormFields();
    var inputFocusIndex = 0;

    var _loop = function _loop(i) {
      _this2.indexHandler = function () {
        inputFocusIndex = i;
      };
      var forField = _this2.formFields[i].element;
      if (forField) {
        if (forField.$el) {
          forField.$el.addEventListener('click', _this2.indexHandler, false);
        } else {
          forField.addEventListener('click', _this2.indexHandler, false);
        }
      }
    };

    for (var i in this.formFields) {
      _loop(i);
    }
    this.formFields.forEach(function (item, index) {
      if (item.element) {
        if (item.element.$el) {
          item = item.element.$el.firstChild;
          _this2.formFields[index] = item;
        } else {
          item = item.element;
          _this2.formFields[index] = item;
        }
      }
    });

    this.keyHandler = function (ev) {
      var event = ev || window.event;

      if (event.keyCode === 13) {
        event.preventDefault();
        ++inputFocusIndex;
        if (!_this2.formFields || _this2.formFields.length <= 0) {
          return;
        }
        if (_this2.formFields[inputFocusIndex - 1]) {
          if (document.activeElement === _this2.formFields[_this2.formFields.length - 1]) {
            _this2.formFields[inputFocusIndex - 1].blur();
          } else {
            if (_this2.formFields[inputFocusIndex]) {
              _this2.formFields[inputFocusIndex].focus();
            }
          }
        }
      }
    };
    document.addEventListener('keydown', this.keyHandler, false);
  },
  dettach: function dettach() {
    document.removeEventListener('keydown', this.keyHandler, false);

    if (!this.formFields) {
      return;
    }

    for (var i in this.formFields) {
      var formField = this.formFields[i];
      formField.removeEventListener('click', this.indexHandler, false);
    }

    this.formFields = null;
  }
};

exports.default = FocusManager;