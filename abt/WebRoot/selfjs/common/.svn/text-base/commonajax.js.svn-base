/*添加提示等待效果

  	var d = document.createElement("div");
    d.innerHTML = '<div class="loading-indicator"><img src="/bsms/images/loading_page.gif" width="32" height="32" style="margin-right:8px;float:left;vertical-align:top;"/>正在加载中，请稍候... <br /><span id="loading-msg">Loading ...</span></div>';
    d.id = "loading";
    var body=document.getElementsByTagName('body')[0];
    if(body==undefined){
    	body=document.getElementsByTagName('head')[0];
    }
    body.appendChild(d);
    
	document.getElementById('loading-msg').innerHTML = '初始化系统组件...';
*/

Ext.Ajax.timeout=90000000;
var author_url;
var author_param;
var service_name;
var func;
/**
 * @param {} url
 * @param {} submitData
 * @param {} callbackfunc
 * @param {} maskflag
 * @param {} Mwindow 需要遮罩的弹窗
 */
function requestAjax(url, submitData, callbackfunc,maskflag,Mwindow){
	func = callbackfunc;
	var msg = ' <img  src="images/004.gif" style="vertical-align:middle"><font size=2><b>正在处理中，请稍候...</b></font></span></img>';   
	//var msg = ' <span id="loading-msg"><img  src="images/001.gif" /></span><br/><font color="#484891" text-algin:"center"  style="margin-left:40px;" size ="2" ><b>loading...</b></font>';   
	//var myMask = new Ext.LoadMask(Ext.getBody(), {msg:msg,removeMask:true,msgCls :'loading-module'});
	//var myMask = new Ext.LoadMask(this.workingTabPanel==undefined?this.workingTabPanel==null?Ext.getBody():this.workingTabPanel.getEl():this.workingTabPanel.getEl(), {msg:msg,removeMask:true,msgCls :'loading-module'});
	var myMask = new Ext.LoadMask(Mwindow == undefined ? Ext.getBody() : Mwindow.getEl(), {msg:msg,removeMask:true,msgCls :'loading-module'});
	if(maskflag!=false){
		myMask.show(); 
	}
	Ext.Ajax.request({
		url:url, 
		params:{requesttype: 'ajax'}, 
		jsonData:submitData,
		callback : function(options,success,response){
			myMask.hide();
			if(success){
				var rawObj = Ext.util.JSON.decode(response.responseText);
    			var jsonObj = Ext.util.JSON.decode(rawObj.actionresult);
    			if(jsonObj.author){//如果需要授权的话，弹出授权框
    				showDiv();
    				service_name=jsonObj.servicename;
    				author_url=jsonObj.field1;
					author_param=eval(jsonObj.field2);
    				creatAuthWin(callbackfunc,jsonObj);
    				
    			} else if(jsonObj.result){
    				callbackfunc(jsonObj);
    			}else{
    				var tishimessage = jsonObj.message;
        			Ext.Msg.show({
            				title: '错误提示',
             				msg: tishimessage,
             				buttons: Ext.Msg.OK,
             				icon: Ext.MessageBox.ERROR
         			});
    			}
			}
		}
	});
}
Ext.Ajax.timeout=90000000;//业务管理，批量调账需要等待   完成后重新刷新页面
function requestAjaxBatchAdjust(url, query_obj,pagequeryObj,myMask, callbackfunc){
	Ext.Ajax.request({url:url, params:{requesttype : 'ajax'}, jsonData:query_obj,
		callback : function(options,success,response){
			myMask.hide();
			if(success){
				var rawObj = Ext.util.JSON.decode(response.responseText);
    			var jsonObj = Ext.util.JSON.decode(rawObj.actionresult);
    			if(jsonObj.result){
    				callbackfunc(jsonObj);
    			}else{
    				var tishimessage = jsonObj.message;
        			var objectSign = tishimessage.substring(0,1);
        			var tsm = tishimessage.substring(1,tishimessage.length);
        			if(objectSign == 0){
        				Ext.MessageBox.alert('',tsm,function(id){
							var ss = window.location.href;
			                var arr = ss.split("/");
		                    parent.window.location.href(arr[0]+"/"+arr[1]+"/"+arr[2]+"/"+arr[3]);
						});
        			}else{
        				pagequeryObj.queryPage(query_obj);
        				Ext.MessageBox.alert('',tishimessage);
        			}
    			}
			}else{
				Ext.MessageBox.alert("提示!",'请求异常，请联系管理员!');
			}
		}
	});
}
Ext.Ajax.timeout=90000000;//(业务管理--调整部分，调整失败后有可能将状态设置为拒付，所以失败之后也需要刷新页面  lich)
function requestAjaxAdjust(url, submitData,pagequeryObj,query_obj, editwindow,callbackfunc){
	Ext.Ajax.request({url:url, params:{requesttype : 'ajax'}, jsonData:submitData,
		callback : function(options,success,response){
			if(success){
				var rawObj = Ext.util.JSON.decode(response.responseText);
    			var jsonObj = Ext.util.JSON.decode(rawObj.actionresult);
    			if(jsonObj.result){
    				callbackfunc(jsonObj);
    			}else{
    				if(editwindow != undefined){
    					editwindow.close();
    				}
    				var tishimessage = jsonObj.message;
        			var objectSign = tishimessage.substring(0,1);
        			var tsm = tishimessage.substring(1,tishimessage.length);
        			if(objectSign == 0){
        				Ext.MessageBox.alert('',tsm,function(id){
							var ss = window.location.href;
			                var arr = ss.split("/");
		                    parent.window.location.href(arr[0]+"/"+arr[1]+"/"+arr[2]+"/"+arr[3]);
						});
        			}else{
        				Ext.MessageBox.alert('',tishimessage,function(){
        					pagequeryObj.queryPage(query_obj);
        				});
        			}
    			}
			}else{
				Ext.MessageBox.alert("提示!",'请求异常，请联系管理员!');
			}
		}
	});
}
/*
Ext.override(Ext.data.Store, {
	sort : function(fieldName,dir){
		var f = this.fields.get(fieldName);
	    if(!f){
	        return false;
	    }
	    if(!dir){
	        if(this.sortInfo && this.sortInfo.field == f.name){ // toggle sort dir
	            dir = (this.sortToggle[f.name] || 'ASC').toggle('ASC', 'DESC');
	        }else{
	            dir = f.sortDir;
	        }
	    }
	    var st = (this.sortToggle) ? this.sortToggle[f.name] : null;
	    var si = (this.sortInfo) ? this.sortInfo : null;
	
	    this.sortToggle[f.name] = dir;
	    this.sortInfo = {field: f.name, direction: dir};
	    if(!this.remoteSort){
	        //this.applySort();
	        this.fireEvent('datachanged', this);
	    }else{
	        if (!this.load(this.lastOptions)) {
	            if (st) {
	                this.sortToggle[f.name] = st;
	            }
	            if (si) {
	                this.sortInfo = si;
	            }
	        }
	    }
	},
	loadRecords : function(o, options, success){
        if (this.isDestroyed === true) {
            return;
        }
        if(!o || success === false){
            if(success !== false){
                this.fireEvent('load', this, [], options);
            }
            if(options.callback){
                options.callback.call(options.scope || this, [], options, false, o);
            }
            return;
        }
        var r = o.records, t = o.totalRecords || r.length;
        if(!options || options.add !== true){
            if(this.pruneModifiedRecords){
                this.modified = [];
            }
            for(var i = 0, len = r.length; i < len; i++){
                r[i].join(this);
            }
            if(this.snapshot){
                this.data = this.snapshot;
                delete this.snapshot;
            }
            this.clearData();
            this.data.addAll(r);
            this.totalLength = t;
            //this.applySort();
            this.fireEvent('datachanged', this);
        }else{
            this.totalLength = Math.max(t, this.data.length+r.length);
            this.add(r);
        }
        this.fireEvent('load', this, r, options);
        if(options.callback){
            options.callback.call(options.scope || this, r, options, true);
        }
    }
});
*/
/**
 * 获得字典翻译值
 * @param {} fieldValue 该行字段值
 * @param {} store      翻译字段存储器
 * @param {} value      字典值
 * @param {} desc       字典翻译值
 * @return {}
 */
function getDictValue(fieldValue,store,value,desc){
	var map=store.getAt(store.findExact(value,fieldValue));
	if(map === undefined)
		return fieldValue;
	else
		return map.get(desc); 
}

function getDictValuebysplit(fieldValue,store,value,desc,split){
	var map=store.getAt(store.findExact(value,fieldValue));
	if(map === undefined)
		return fieldValue;
	else
		var arr=map.get(desc).split(split);
		return arr[1]; 
}
/**
 * 通过Extjs实现提示等待功能
 
Ext.override(Ext.data.Connection,{
    serail : 0,
    request : Ext.data.Connection.prototype.request.createSequence(function(){ 
        this.serail++;
        Ext.get('x-image-load_ing').show();
    }),
    handleResponse : Ext.data.Connection.prototype.handleResponse.createSequence(function(){ 
        if(this.serail==1)
            Ext.get('x-image-load_ing').hide();
        this.serail--;
    }),
    doFormUpload : Ext.data.Connection.prototype.doFormUpload.createSequence(function(){ 
        if(this.serail==1)
            Ext.get('x-image-load_ing').hide();
        this.serail--;
    }),
    handleFailure : Ext.data.Connection.prototype.handleFailure.createSequence(function(){ 
        Ext.DomHelper.overwrite(Ext.get('x-image-load_ing'),'加载出错,建议 <a onclick="window.location.reload();" href="#"><b>刷新本页</b></a> !')
    this.serail=this.serail-100;
    })
})
*/

/** 
 * 格式化日期 
 * <code> 
 * yyyy-------年 
 * MM---------月 
 * dd---------日 
 * hh---------时 
 * mm---------分 
 * 如：Util.formatDate(new Date() , 'yyyy-MM-dd mm:hh'); 
 * or Util.formateDate(new Date(), 'yyyy/MM/dd mm/hh'); 
 * </code> * @param {Date}date 需要格式化的日期对象 
 * @param {Object} style 样式 
 * @return 返回格式化后的当前时间 
 */  
formatDate = function(date, style){ 
      var y = date.getFullYear();  
      var Mon = "0" + (date.getMonth() + 1);  
      Mon = Mon.substring(Mon.length - 2); 
      var d = "0" + date.getDate(); 
      d = d.substring(d.length - 2);  
      var h = "0" + date.getHours();  
      h = h.substring(h.length - 2);  
      var m = "0" + date.getMinutes();  
      m = m.substring(m.length - 2);  
      return style.replace('yyyy', y).replace('MM', Mon).replace('dd', d).replace('hh', h).replace('mm', m); 
 } 

 var authorwindow=null;
 function creatAuthWin(callbackfunc,jsonObj){
 	if(authorwindow==null){
		 authorwindow = new SelfFormWindow('authorwindow', '授权窗口', 200, 200, 100, 1,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'author', 	  fieldLabel:'授权用户名',   allowBlank:false,blankText:'用户名'}},
				{colIndex:0, field:{xtype : 'textfield', name:'passwd', inputType:'password',	  fieldLabel:'密码',   allowBlank:false,blankText:'密码'}}
				],
				[
					{text:'提交', formBind: true, handler : function(){
						callbackfunc = func;
						var submitData = authorwindow.getFields();
						requestAjax(service_name+'/admin/systemuser_author',
						submitData,function(sRet){
							if(sRet.message&&sRet.message!=""){
								Ext.MessageBox.alert('授权失败',sRet.message);
							}else{
								authorwindow.close();
								requestAjax(author_url,author_param,function(sRet1){
										callbackfunc(sRet1);
								});
					}
				});
					}
					},
					{text:'取消', handler: function(){authorwindow.close();}}
				]
			);
 	}
 	authorwindow.open();
 }
function showDiv () {
    d = document.createElement("div");
    //d.innerHTML = "这是Div层的内容！";
    d.id = "authorwindow";
    d.width = 200;
    d.height = 200;
    document.getElementsByTagName('body')[0].appendChild(d);
}


/** 
	可以出现提示的输入框，用法：
	{xtype : 'uxtextfield', name:'recvbank',allowBlank : false,  fieldLabel:'接收行',
	regex:/^\d{3}$|^\d{12}$|0/, regexText:'只能输入0、3位的区域编码或者12位的行号',
	tooltip:{text:'只能输入0、3位的区域编码或者12位的行号',title:'提示'}}
 */  
Ext.ns('Ext.ux.form.TextField');  
Ext.ux.form.TextField = Ext.extend(Ext.form.TextField, {  
    tooltip : {},  
   onRender : function(ct, position) {  
        Ext.ux.form.TextField.superclass.onRender.call(this, ct, position);  
        if (this.tooltip.text)  
            new Ext.ToolTip({  
                target : this.id,  
                trackMouse : false,  
                draggable : true,  
               maxWidth : 200,  
                minWidth : 100,  
               title : '信息提示',  
                html : this.tooltip.text  
           });  
    }  
});  
Ext.reg('uxtextfield', Ext.ux.form.TextField);  

    //由指定的属性筛选记录
	function creatStoreFilter(store,key,value,anyMatch,caseSensitive){
				store.filter(key,value,false,false);
           		store.realSnapshot=store.snapshot;
           		store.snapshot=store.data;
			}
   //由外部函数进行筛选
    function creatStoreFilterByfun(store,fun){
                store.filterBy(fun);
                store.realSnapshot=store.snapshot;
                store.snapshot=store.data;
            }
    //清除筛选记录
    function clearStoreFilter(store){
                store.snapshot=store.realSnapshot;
                delete store.realSnapshot;
                store.clearFilter();
            }
			

	//去除提示等待效果
	function GOOGLEResize(){ 
 	//	Ext.get('loading').fadeOut({remove: true});
 		var body=parent. Ext.get('center-panel');
	 	if(body){
	 		body.unmask();
 		}
	} 
	var ua=navigator.userAgent.toLowerCase(); 
	var isStrict=document.compatMode=="CSS1Compat", 
	isOpera=ua.indexOf("opera")>-1, 
	isSafari=(/webkit|khtml/).test(ua), 
	isIE=ua.indexOf("msie")>-1, 
	isIE7=ua.indexOf("msie 7")>-1, 
	isGecko=!isSafari&&ua.indexOf("gecko")>-1, 
	isBorderBox=isIE&&!isStrict, 
	isWindows=(ua.indexOf("windows")!=-1||ua.indexOf("win32")!=-1), 
	isMac=(ua.indexOf("macintosh")!=-1||ua.indexOf("mac os x")!=-1), 
	isLinux=(ua.indexOf("linux")!=-1), 
	isSecure=window.location.href.toLowerCase().indexOf("https")===0; 
	if(isGecko || isOpera) { 
		document.addEventListener("DOMContentLoaded", GOOGLEResize, false); 
	}else if(isIE){ 
		document.write("<s"+'cript id="ie-deferred-loader" defer="defer" src="/'+'/:"></s'+"cript>"); 
	var defer = document.getElementById("ie-deferred-loader"); 
		defer.onreadystatechange = function(){ 
	if(this.readyState == "complete"){ 
		GOOGLEResize(); 
	} 

	}; 
	}else if(Ext.isSafari){ 
		docReadyProcId = setInterval(function(){ 
	var rs = document.readyState; 
	if(rs == "complete") { 
		GOOGLEResize(); 
	} 
	}, 10); 
	}else{ 
		window.onload=GOOGLEResize; 
	} 
	
/**
*格式化金额方法,用于有小数点金额
*/
function fmoney(s, n)  
{  
	if(s==null||s.toString()==''){
		return '';
	}
	if( isNaN(s) ){
		return s;
	}
   n = n > 0 && n <= 20 ? n : 2;  
   s = (parseFloat((s + "").replace(/[^\d\.-]/g, ""))).toFixed(n) + "";  
   var l = s.split(".")[0].split("").reverse(),  
   r = s.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
   return t.split("").reverse().join("") + "." + r;  
} 

/**
*格式化金额方法,用于没有小数点的金额格式化

function fmoney(s, n)  
{  
	if(s==''||s==null){
		return '';
	}
   n = n > 0 && n <= 20 ? n : 2;  
   s = (parseFloat((s + "").replace(/[^\d\.-]/g, ""))/100).toFixed(n) + "";  
   var l = s.split(".")[0].split("").reverse(),  
   r = s.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
   return t.split("").reverse().join("") + "." + r;  
} 
*/

function formatNumber(m){
	var n=m*100+'%';
	return n;
} 
//下面是格式化时间方法
function fdate(value){
	if(value==''||value==null){
		return '';
	}
	var str = value.toString();
	return str.substring(0,4) + '-' + str.substring(4,6) + '-' + str.substring(6,8);
}
function ftime(value){
	if(value==''||value==null){
		return '';
	}
	var str = value.toString();
	while(str.length < 6){
			str = '0' + str;
	}
	return str.substring(0,2) + ':' + str.substring(2,4) + ':' + str.substring(4,6);
}

function fDateTime(value){
	if(value==''||value==null){
		return '';
	}
	var str = value.toString();
	return str.substring(0,4) + '-' + str.substring(4,6) + '-' + str.substring(6,8) + ' ' + str.substring(8,10) + ':' + str.substring(10,12) + ':' + str.substring(12,14);
}

function fminute(value){
	if(value==''||value==null){
		return '';
	}
	return (value / 60).toString();
}

//Ext.util.CSS.swapStyleSheet('theme','extjs/resources/css/xtheme-calista.css');