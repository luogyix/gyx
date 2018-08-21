(function () {
    var TemplateNormal = "<div class=\"input-field\"><input type=\"text\" ondragstart=\"return false;\"><label></label><i class=\"mdi-action-search adore-expandabletext-button-search\"></i></div>";

    var Template1 = "<ul class=\"adore_dropDown dropdown-content\" style=\"opacity:1;\"></ul>";

    var Template2 = "<li><a></a></li>";

    var Template_DropDownControl = "<div class=\"adore-dropDown-control\"><div class=\"to-pre\"><i class=\"fa mdi-hardware-keyboard-arrow-left\"></i></div><div class=\"to-next\"><i class=\"fa mdi-hardware-keyboard-arrow-right\"></i></div><div class=\"page-index\"></div></div>";
    $(document).ready(function () {
        var $expandableText = $(document).find(".adore-expandableText");
        if ($expandableText.length > 0) {
            for (var i = 0; i < $expandableText.length; i++) {
                (function (i) {
                    var id = $expandableText[i].id;
                    if (id) {
                        $expandableText[i].expandableTextId = id;
                    } else {
                        $expandableText[i].expandableTextId = "adore-expandableText" + adoreUUID(8, 16);
                    }
                    if (!$expandableText[i].datas) {
                        $expandableText[i].datas = [];
                    }
                    $expandableText[i].maxShowItems = 10;
                    $expandableText[i].currentIndex = -1;
                    $expandableText[i].scrollIndexs = [];
                    $expandableText[i].currentPage = 0;
                    $expandableText[i].$thisNode = $(TemplateNormal).attr("id", $expandableText[i].expandableTextId);
                    $expandableText[i].$thisNode.appendTo($expandableText[i]);
                    $expandableText[i].$textNode = $expandableText[i].$thisNode.find("input");
                    $expandableText[i].$searchNode = $expandableText[i].$thisNode.find("i");
                    $expandableText[i].$thisNode.find("label").attr("for", $expandableText[i].expandableTextId);
                    $expandableText[i].$textNode.on("input", function (e) {
                        handleInput(e, $expandableText[i].$textNode.val(), $expandableText[i]);
                    });
                    
                    var input = $expandableText[i].$textNode.get(0);
                    input.addEventListener("keydown", function(e) {
                    	handleKeyDown(e, $expandableText[i]);
                    	e.stopPropagation();
                    }, true);
                    
//                  $expandableText[i].$textNode.on("keydown", function (e) {
//                      handleKeyDown(e, $expandableText[i]);
//                  });
                    $expandableText[i].$textNode.on("blur", function (e) {
                        handleBlur(e, $expandableText[i]);
                    });
                    //add listener
                    $expandableText[i].$searchNode.on("click", function (e) {
                        //own.$textCode.focus();
                        if (isDropDownShow($expandableText[i])) {
                            showDropDown(e, "", false, $expandableText[i]);
                        } else {
                            showDropDown(e, $expandableText[i].$textNode.val(), true, $expandableText[i]);
                        }
                    });
                })(i);
            }
        }
    });

    function isDropDownShow(element) {
        var $dropDownNode = $("#" + element.expandableTextId + "_dropDown");
        if ($dropDownNode) {
            var dis = $dropDownNode.css("display");
            if (dis) {
                if (dis === "block") return true;
            }
        }
        return false;
    }


    function handleInput(e, value, element) {
        showDropDown(e, value, false, element);
    }

    function handleKeyDown(e, element) {
        var dropDown = element.$dropDown;
        if (!dropDown) {
            return;
        }
        var dis = dropDown.css("display");
        // 还未弹出dropDown，忽略
        // if (!dis || dis === "none") {
        //     // if (e.keyCode === Keys.DOWN_ARROW) {
        //     //     this.showDropDown(e, "", true);
        //     // }
        //     return;
        // }
        var items = dropDown.find("li");
        if (items.length === 0) {
            return;
        }

        //上
         if (e.keyCode === 38) {
            if (dropDown.css("display") === "block") {
                if (element.currentIndex === -1) {
                    element.currentIndex = items.length - 1;
                } else {
                    element.currentIndex--;
                }
                items.css("background-color", "#FFFFFF");
                if (element.currentIndex < 0) {
                    element.currentIndex = -1;
                } else {
                    $(items[element.currentIndex]).css("background-color", "#BEBEBE");
                    element.$textNode.val($(items[element.currentIndex]).find("a").text().split(" ")[0]);
                }
                refreshScroll(dropDown, element.currentIndex, element);
                e.preventDefault();
                return;
            }
        }
         	
        //
//      else if (e.keyCode == 39) {
////          $('.adore-dropDown-control>div').children
////document.getElementsByClassName("to-next").
////          document.getElementsByClassName("to-next").mouseup();
//
//          var maxPage = getShowPages(element.datas, element.$textNode.val(), element.maxShowItems);
//              if (element.currentPage >= maxPage - 1) {
//                  return;
//              }
//              element.currentPage++;
//              var $expandableText = $(document).find(".adore-expandableText");
//              $expandableText[i].$thisNode = $(TemplateNormal).attr("id", $expandableText[element.currentPage].expandableTextId).find("input");
//              $expandableText[i].$textNode = $expandableText[i].$thisNode.find("input");
//              var showTextList = getShowItems(element.datas, $(document).find(".adore-expandableText")[element.currentPage].$textNode.val(), element.maxShowItems, element.currentPage);
//              regenerateList(showTextList, element.currentPage + 1, maxPage, element);
//              
////          document.getElementsByClassName("to-next").mousedown();
////          alert(document.getElementsByClassName("to-next").mousedown());
////          alert(element+'');
////          alert(element.$dropDownControl.children(".to-next")+'');
////      	element.$dropDownControl.children(".to-next").mousedown();
//      }
        // 下
        else if (e.keyCode === 40) {
            if (dropDown.css("display") === "block") {
                element.currentIndex++;
                items.css("background-color", "#FFFFFF");
                if (element.currentIndex >= items.length) {
                    element.currentIndex = -1;
                } else {
                    $(items[element.currentIndex]).css("background-color", "#BEBEBE");
                    element.$textNode.val($(items[element.currentIndex]).find("a").text().split(" ")[0]);
                }
                refreshScroll(dropDown, element.currentIndex, element);
                e.preventDefault();
                return;
            }
            else {
                var textCode = element.$textNode.val();
                if (textCode !== null) {
                    handleInput(e, textCode, element);
                }
            }
        }
        // enter
        else if (e.keyCode === 13 || e.keyCode === 108) {
            dropDown.css("display", "none");
            element.$dropDownControl.css("display", "none");
            hideOverlay(element.$dropDownControl);
             hideOnelay(element.$dropDown);
            if (element.currentIndex >= 0 && element.currentIndex < items.length) {
                var text = $(items[element.currentIndex]).find("a").text();
                var val = text.split(" ")[0];
                element.$textNode.val("");
                if (element.finishListener) {
                    element.finishListener(val);
                }
            }
            else if (element.currentIndex === -1 && dropDown.css("display", "block")) { //出现了下拉列表，但是没有选中
                e.preventDefault();
                e.stopPropagation();
                element.$dropDownControl.css("display", "none");
                element.$dropDown.css("display", "none");
                 hideOverlay(element.$dropDownControl);
                 hideOnelay(element.$dropDown);
                var val = element.$textNode.val();
                element.$textNode.val("");
                if (element.finishListener) {
                    element.finishListener(val);
                }
            }
        }
    }

    function handleBlur(e, element) {
        var mouseOn = $("#" + element.expandableTextId + "_dropDown").attr("mouseOn");
        if (mouseOn === "false") {
            $("#" + element.expandableTextId + "_dropDown").css("display", "none");
            element.$dropDownControl.css("display", "none");
        }
    }

    function refreshScroll($dropDown, currentIndex, element) {
        if (currentIndex === -1) {
            return;
        }
        var count = $dropDown.find("li").length - 1;
        var needRefresh = false;
        /**判断当前下标对应的li是否已经不在可视滚动区域内,若不在，需要刷新滚动条的位置**/
        if (currentIndex < element.scrollIndexs[0]) {
            // 6 10 currentIndex:5
            var endIndex = currentIndex + element.maxShowItems - 1;
            endIndex = endIndex > count ? count : endIndex;
            element.scrollIndexs = [currentIndex, endIndex];
            needRefresh = true;
        } else if (currentIndex > element.scrollIndexs[1]) {
            var beginIndex = currentIndex - element.maxShowItems + 1;
            beginIndex = beginIndex < 0 ? 0 : beginIndex;
            element.scrollIndexs = [beginIndex, currentIndex];
            needRefresh = true;
        }
        if (needRefresh) {
            var h = $dropDown.find("li").height();
            $dropDown[0].scrollTop = h * element.scrollIndexs[0];
        }
        //更新选中的项的值模糊查询的页码数
        var maxPage = getShowPages(element.datas, element.$textNode.val(), element.maxShowItems);
        element.$dropDownControl.children(".page-index").on("mousedown mouseup", function (e) {
            e.preventDefault();
            e.stopPropagation();
        }).html(1 + "/" + maxPage + "页");
        element.currentPage = 0;
    }

    function showDropDown(e, value, showAll, element) {
        element.currentPage = 0;
        var height = element.$textNode.height();
        element.scrollIndexs = [0, element.maxShowItems - 1];//重置滚动区域的可视下标[beginIndex,endIndex]
        //height = Number(height.replace("px", ""));
        var pos = getPosition(element.$textNode[0]);
        var tmpHeight = height + pos[0] + 14;
        //把原来dropDown remove掉
        if (element.$dropDown) {
            element.$dropDown.remove();
            hideOnelay(element.$dropDown)
        }
        if (element.$dropDownControl) {
            element.$dropDownControl.remove();
             hideOverlay(element.$dropDownControl);
             
        }

        // 添加 dropDown
        element.$dropDown = $(Template1).attr("id", element.expandableTextId + "_dropDown").on("mousedown mouseup", function (e) {
            e.preventDefault();
        });
        element.$dropDown.appendTo(document.body);

        //设置位置
        element.$dropDown.css("top", tmpHeight);
        element.$dropDown.css("left", pos[1]);
        element.$dropDown.css("background-color", "#FFFFFF");
        element.currentIndex = -1;
        //添加列表
        var $liNode;
        if (element.datas) {
            if (showAll === true && value === "") {
                for (var i = 0; i < element.datas.length; i++) {
                    $liNode = $(Template2);
                    $liNode.find("a").text(element.datas[i]);
                    $liNode.appendTo(element.$dropDown);
                }
            }
            else if (value !== "") {
            	var dataItems = getShowItems(element.datas, value, element.maxShowItems, element.currentPage);
                for (var data in dataItems) {
                    var $liNode = $(Template2).appendTo(element.$dropDown);
                    $liNode.find("a").on("mouseup", function (e) {
                        var value = $(this).text();
                        var newValue = value.split(" ")[0];
                        element.$textNode.val("");
                        e.preventDefault();
                        e.stopPropagation();
                        element.$dropDownControl.css("display", "none");
                        element.$dropDown.css("display", "none");
                        if (element.finishListener) {
                            element.finishListener(newValue);
                        }
                    }).text(dataItems[data]);
                }
            }
        }
        var len = element.$dropDown.find("li").length;
        var inputNode = element.$textNode;
        var tmpId = element.expandableTextId;

        if (len > 0) {
            // // 为每个列表添加click事件
            // this.$dropDown.find("li").find("a").on("click", function (e) {
            //     let value = $(this).text();
            //     let newValue = value.split(" ")[0];
            //     $("#" + tmpId + "_input").val(newValue);
            //     $("#" + tmpId + "_dropDown").css("display", "none");
            //     // dispatch modify event
            //     EventHub.dispatchEvent(MessageType.WIDGET, { id: tmpId, text: newValue, evtType: Event.MODIFY });
            //     // dispatch click event
            //     EventHub.dispatchEvent(MessageType.WIDGET, { id: tmpId, evtType: Event.CLICK });
            // });
            /**
             * 添加mouseover和mouseout事件，解决为input添加blur事件后不能选中选项的问题
             * */

            //弹出dropDown
            element.$dropDown.css("display", "block");
            // showOverlay(element.$dropDown)    
            // // 若超出最大显示条数，设置最大高度
            // if (len > this.maxShowItems) {
            //     let h = $(this.$dropDown.find("li")[0]).height();
            //     let maxHeight = h * this.maxShowItems;
            //     this.$dropDown.css("height", maxHeight);
            // }

            element.$dropDownControl = $(Template_DropDownControl).css({
                display: "block",
                top: element.$dropDown.offset().top + element.$dropDown.height(),
                left: element.$dropDown.offset().left,
                width: element.$dropDown.width()
            }).appendTo(document.body);
             
            var $toPreButton = element.$dropDownControl.children(".to-pre");
            var $toNextButton = element.$dropDownControl.children(".to-next");
            var $pageIndexSpan = element.$dropDownControl.children(".page-index");
            showOverlay( element.$dropDownControl);
            OneOverlay( element.$dropDown);
            //点击上一页的按钮事件绑定
            $toPreButton.on("mousedown", function (e) {
                e.preventDefault();
                e.stopPropagation();
            }).on("mouseup", function (e) {
                e.preventDefault();
                e.stopPropagation();
                if (element.currentPage === 0) {
                    return;
                }
                element.currentPage--;
                var showTextList = getShowItems(element.datas, value, element.maxShowItems, element.currentPage);
                var maxPage = getShowPages(element.datas, element.$textNode.val(), element.maxShowItems);
                regenerateList(showTextList, element.currentPage + 1, maxPage, element);
            });
            //点击下一页的按钮事件绑定
            $toNextButton.on("mousedown", function (e) {
                e.preventDefault();
                e.stopPropagation();
            }).on("mouseup", function (e) {
                e.preventDefault();
                e.stopPropagation();
                var maxPage = getShowPages(element.datas, element.$textNode.val(), element.maxShowItems);
                if (element.currentPage >= maxPage - 1) {
                    return;
                }
                element.currentPage++;
                var showTextList = getShowItems(element.datas, value, element.maxShowItems, element.currentPage);
                regenerateList(showTextList, element.currentPage + 1, maxPage, element);
            });
            //设置页码
            var maxPage = getShowPages(element.datas, element.$textNode.val(), element.maxShowItems);
            $pageIndexSpan.on("mousedown mouseup", function (e) {
                e.preventDefault();
                e.stopPropagation();
            }).html(1 + "/" + maxPage + "页");

            $(document).bind('mouseup.' + element.expandableTextId + "_dropDown", function (e) {
                if ((!element.$dropDown.is(e.target) && !inputNode.is(e.target)) && !element.$searchNode.is(e.target) || element.$dropDown.css("display") === "none") {
                    element.$dropDown.css("display", "none");
                    element.$dropDownControl.css("display", "none");
                    $(document).unbind('mouseup.' + tmpId + "_dropDown");
                    hideOverlay(element.$dropDownControl);
                    hideOnelay(element.$dropDown)
                }
            });
        } else {
            element.$dropDown.css("display", "none");
            hideOverlay(element.$dropDownControl);
            hideOnelay(element.$dropDown)
        }
    }

    function regenerateList(showData, pageIndex, maxPageIndex, element) {
        var height = element.$textNode.height();
        element.scrollIndexs = [0, element.maxShowItems - 1];
        var pos = getPosition(element.$textNode[0]);
        var tmpHeight = height + pos[0] + 14;

        //如果之前的dropDown列表块存在,则先移除
        if (element.$dropDown) {
            element.$dropDown.remove();
            
             hideOnelay(element.$dropDown);
        }
        //设置dropDown的属性
        element.$dropDown = $(Template1).css({
            "top": tmpHeight,
            "left": pos[1],
            "background-color": "#FFFFFF"
        }).on("mousedown mouseon", function (e) {
            e.preventDefault();
            e.stopPropagation();
        }).appendTo(document.body);
        
        //在列表中添加数据
        for (var data in showData) {
            var $liNode = $(Template2).appendTo(element.$dropDown);
            $liNode.find("a").on("mouseup", function (e) {
                var value = $(this).text();
                var newValue = value.split(" ")[0];
                element.$textNode.val(newValue);
                e.preventDefault();
                e.stopPropagation();
                element.$dropDownControl.css("display", "none");
                element.$dropDown.css("display", "none");
            }).text(showData[data]);
        }
        //显示下拉列表
        element.$dropDown.css("display", "block");
        //重置控制模块的位置
        element.$dropDownControl.css({
            display: "block",
            top: element.$dropDown.offset().top + element.$dropDown.height(),
            left: element.$dropDown.offset().left,
            width: element.$dropDown.width()
        });
        element.$dropDownControl.children(".page-index").text(pageIndex + "/" + maxPageIndex + "页");
    }

    function getPosition(element) {
        var pos = [0, 0];
        if (element && element.offsetParent) {
            while (element && element.offsetParent) {
                pos[0] += element.offsetTop;
                pos[1] += element.offsetLeft;
                element = element.offsetParent;
            }
        }
        return pos;
    }

    function getShowPages(datas, input, showItemsNum) {
        var filterData = getFilterData(datas, input);
        var div = Math.floor(filterData.length / showItemsNum);
        var rem = filterData.length % showItemsNum;
        if (rem !== 0) {
            return div + 1;
        }
        return div;
    }

    function getShowItems(datas, input, showItemsNum, currentIndex) {
        var filterData = getFilterData(datas, input);
        var resultMap = getFilterDataItems(filterData, showItemsNum);
        if (resultMap[currentIndex] !== null) {
            return resultMap[currentIndex];
        }
        return null;
    }

    function getFilterData(datas, input) {
        var result = [];
        var inputLowerCase = input.toLocaleLowerCase();
        for (var i = 0, j = 0; i < datas.length; i++) {
            if (Filter(datas[i].toLocaleLowerCase(), inputLowerCase)) {
                result[j] = datas[i];
                j++;
            }
        }
        return result;
    }

    function getFilterDataItems(datas, showitemsNum) {
        var resultMap = {};
        var length = datas.length;
        var temp = [];
        var j = 0;
        var index = 0;
        for (var i = 0; i < length; i++) {
            if (j < showitemsNum) {
                temp[j] = datas[i];
                j++;
            } else {
                resultMap[index] = temp;
                index++;
                j = 0;
                temp = [];
                temp[j] = datas[i];
                j++;
            }
        }
        resultMap[index] = temp;
        return resultMap;
    }

    function Filter(item, input) {
        input = input.trim().toLowerCase();
        //这里匹配了，就无需再做下面的操作了，这样的话效率高一些。
        //下同
        if (item.indexOf(input) > -1) {
            return true;
        }
        var firstKeys = getFirstKeys(item);
        if (firstKeys.indexOf(input) > -1) {
            return true;
        }
        var totalKeys = getTotalKeys(item);
        if (totalKeys.indexOf(input) > -1) {
            return true;
        }
        return false;
    }

    function getFirstKeys(item) {
        var result = "";
        for (var i = 0; i < item.length; i++) {
            var c = item[i];
            var value = PinYinDict.get(c);
            if (value === undefined) {
                result = result + c;
            } else {
                if (value.length > 0) {
                    // 只获取第一个字符
                    result = result + value.charAt(0);
                }
            }
        }
        return result;
    }

    function getTotalKeys(item) {
        var result = "";
        for (var i = 0; i < item.length; i++) {
            var c = item[i];
            var value = PinYinDict.get(c);
            if (value === undefined) {
                result = result + c;
            } else {
                result = result + value;
            }
        }
        return result;
    }

    function adoreUUID(len, radix) {
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var uuid = [], i;
        radix = radix || chars.length;
        if (len) {
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
        } else {
            var r;
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }
        return uuid.join('');
    }

    $.fn.extend({
        setText: function (text) {
            //判断是否交易码搜索框
            if (!this.hasClass("adore-expandableText"))
                return;
            for (var i = 0; i < this.length; i++) {
                if (this[i].$textNode) {
                    this[i].$textNode.val(text);
                }
            }
        },
        setName: function (name) {
            //判断是否交易码搜索框
            if (!this.hasClass("adore-expandableText"))
                return;
            for (var i = 0; i < this.length; i++) {
                if (this[i].$thisNode) {
                    this[i].$thisNode.find("label").text(name);
                }
            }
        },
        setItems: function (items) {
            //判断是否交易码搜索框
            if (!this.hasClass("adore-expandableText"))
                return;
            for (var i = 0; i < this.length; i++) {
                this[i].datas = items;
            }
        },
        setFocus: function () {
            //判断是否交易码搜索框
            if (!this.hasClass("adore-expandableText"))
                return;
            for (var i = 0; i < this.length; i++) {
                var $dropDownNode = $("#" + this[i].expandableTextId + "_dropDown");
                if ($dropDownNode.css("display") !== "block" && document.activeElement !== this[i].$textNode[0]) {
                    this[i].$textNode.focus();
                }
            }
        },
        setEnabled: function (enabled) {
            //判断是否交易码搜索框
            if (!this.hasClass("adore-expandableText"))
                return;
            for (var i = 0; i < this.length; i++) {
                this[i].enabled = enabled;
                if (this[i].enabled) {
                    this[i].$thisNode.find("input").removeAttr("disabled");
                } else {
                    this[i].$thisNode.find("input").attr("disabled", "disabled");
                }
            }
        },
        setPlaceholder: function (placeholder) {
            //判断是否交易码搜索框
            if (!this.hasClass("adore-expandableText"))
                return;
            for (var i = 0; i < this.length; i++) {
                this[i].$thisNode.find("input").attr("placeholder", placeholder);
            }
        },
        addFinishListener: function (listener) {
            //判断是否交易码搜索框
            if (!this.hasClass("adore-expandableText"))
                return;
            for (var i = 0; i < this.length; i++) {
                this[i].finishListener = listener;
            }
        }

    });
})();


function showOverlay(floatPanel){
	
}
function OneOverlay(floatPanel){
	
}
function hideOverlay(floatPanel){
	
}
function hideOnelay(floatPanel){
	
}
