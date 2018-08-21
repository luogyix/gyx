(function () {
    function AdoreMap() {
        this.map = {};
    }

    AdoreMap.prototype = {
        put: function (key, value) {
            this.map[key] = value;
        },
        get: function (key) {
            if (this.map.hasOwnProperty(key)) {
                return this.map[key];
            }
            return null;
        },
        remove: function (key) {
            if (this.map.hasOwnProperty(key)) {
                var value = this.map[key];
                delete this.map[key];
                return value;
            }
            return null;
        },
        removeAll: function () {
            this.map = {};
        },
        keySet: function () {
            var _keys = [];
            for (var i in this.map) {
                _keys.push(i);
            }
            return _keys;
        }
    };
    var adoreMap = new AdoreMap();

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

    function AdoreRequest() {
        this.id = adoreUUID(8, 16);
        adoreMap.put(this.id, this);
    };
    AdoreRequest.prototype.send = function (data) {
        var data_ = { requestId: this.id, data: data };
        window.sendAdoreMessage(data_);
    };
    /**
     @type {string}
     @const
     */
    Object.defineProperty(AdoreRequest, "onCallback", {
        set: function (value) {
        }
    });

    //发送消息给adore引擎，让平台把消息转发给应用
    window.sendAdoreMessage = function (message, callback) {
        if (callback) {
            var adoreRequest = new AdoreRequest();
            adoreRequest.onCallback = callback;
            adoreRequest.send(message);
        } else {
            top.postMessage(message, "*");
        }
    }

    function getOffset(element, offS) {
        if (!element || element === document.body) {
            return;
        }
        offS.left += element.offsetLeft;
        offS.top += element.offsetTop;
        getOffset(element.parentElement, offS);
    }

    function offset(element) {
        var offS = { left: 0, top: 0 };
        getOffset(element, offS);
        return offS;
    }

    /**
    * 一体化页面域响应top更新ACefOverlay的请求(内嵌遮挡overlay)
    * 
    * @param requestData:{ overlayId: overlayId, top: top, left: left, width: width, height: height};
    */
    function handleRequestRefreshACefOverlay(requestData) {
        if (!requestData) {
            return;
        }
        var aIframes = document.getElementsByTagName("iframe");
        var aIframeData = [];
        for (var i = 0; i < aIframes.length; i++) {
            if (aIframes[i].id && aIframes[i].id.indexOf("AdoreIFrame") === 0) {
                //var offS = offset(aIframes[i]);
                var offS = $(aIframes[i]).offset();
                aIframeData.push({ id: aIframes[i].id, top: offS.top, left: offS.left });
            }
        }
        if (aIframeData.length !== 0) {
            var message = { "adoreType": "handleRequestRefreshACefOverlay", "requestData": requestData, "aIframeData": aIframeData };
            top.postMessage(message, "*");
        }
    }


    //处理adore引擎的请求
    window.addEventListener('message', function (event) {
        if (!(event && event.data)) {
            return;
        }
        if (event.data.type === "response" && event.data.requestId) {
            var request = adoreMap.remove(event.data.requestId);
            if (request) {
                if (request.onCallback) {
                    var responseText = event.data.responseText;
                    request.onCallback(responseText);
                }
            }
        } else if (event.data.type === "executeScript" && event.data.script) {
            try {
                setTimeout(event.data.script, 0);
            } catch (e) {
                console.error(e);
            }
        } else if (event.data.type === "refreshACefOverlay") {
            handleRequestRefreshACefOverlay(event.data.requestData);
        }
    }, false);
})();
