$(window).on('load', function() {
	$('.ct-tab>li>a').click(function(event) {
		var tabContent = getTarget(event.target.parentElement.parentElement.parentElement.parentElement, 'ct-tabContent');
		var contents = $(tabContent).children();
		
		for(var i = 0; i < contents.length; i++) {
			var c = contents[i];
			$(c).removeClass('ct-display');
			$(c).addClass("ct-displayNone");
		}

		var sourceElement = event.target;
		var contents = $(getTarget(event.target.parentElement.parentElement.parentElement, 'ct-tab')).children();
		for(var i = 0; i < contents.length; i++) {
			var c = contents[i];
			$(c).removeClass('ct-tab-seleted');
			$(c).addClass("ct-tab-unseleted");
		}
		sourceElement.parentElement.className = "ct-tab-seleted";
		var value = sourceElement.getAttribute('value');
		$('#' + value).removeClass('ct-displayNone');
		$('#' + value).addClass('ct-display');
	});

	$('.ct-portab-tab-item>a').click(function(event) {
		var contents = $('.ct-protab-content').children();
		for(var i = 0; i < contents.length; i++) {
			var c = contents[i];
			c.className = "ct-displayNone";
		}

		var sourceElement = event.target;
		var contents = $('.ct-portab-tabs').children();
		for(var i = 0; i < contents.length; i++) {
			var c = contents[i];
			c.className = "ct-portab-tab-item";
		}
		sourceElement.parentElement.className = "ct-portab-tab-item ct-portab-tab-item-selected";
		var value = sourceElement.getAttribute('value');
		$('#' + value).removeClass('ct-displayNone');
		$('#' + value).addClass('ct-display');
	});

	refreshNotice();
	refreshNote();
});

function getTarget(element, className) {
	var ecs = $(element).children();
	var target;
	ecs.each(function(index){
		if($(this).attr('class') == className){
			target = $(this);
			return;
		}
	});
	return target;
}

/**
 * 初始化公告
 * @param {Object} jsonData 公告数据
 */
function initNotice(jsonData) {
	var noteList = document.getElementById('myNoticeList');
	noteList.innerHTML = '';
	var innerHtml = '';
	for(var i = 0; i < jsonData.length; i++) {
		innerHtml += '<div>';
		innerHtml += '<img src="./_img/notice.png">';
		innerHtml += '<div>'
		var data = JSON.stringify(jsonData[i]);
		console.log(data);
		innerHtml += '<p data=' + data + ' class="ct-message_p" onclick="readNotice(event);">' + jsonData[i].description_cn + '</p>';
		innerHtml += '<span>' + jsonData[i].moddate + '</span>';
		innerHtml += '</div>'
		innerHtml += '<i class="ct-noticeArrow"></i>';
		innerHtml += '</div>';
	}
	noteList.innerHTML = innerHtml;
}


//公告更新清理div有问题，待修改--gl
/*
 * 刷新公告查询出当日公告根据显示时间处理等待任务
 * 1.刷新按钮推送消息进行数据库通讯查询公告信息
 * 2.得到返回公告显示信息，显示到界面框
 * 3.返回日期时间比较处理定时任务 
 *    tips：由于jquery-2.1.3.min.js经测试无substr，substring，slice方法。
 *       将时间字符串hhmmss当成数组截取char插入: 转换标准时间加减毫秒值处理
 *    --gl 4.14 lnnx
 */
function refreshNotice() {
	var eventData = {
		'eventType': 'notice',
		'operation': 'refreshNotice'
	};
	var flag=false;
	console.log("推送公告");
	window.sendAdoreMessage(eventData, function(data) {
		$("#myNoticeList").innerHTML='';
//      var noteList = document.getElementById('myNoticeList');
//	    noteList.innerHTML = '';
		console.log($("#myNoticeList").empty());
		var noticeData = jQuery.parseJSON(data);
		initNotice(noticeData);
		console.log("刷新响应： "+noticeData);
		console.log(noticeData.length);
		var flag=false;
		if(noticeData.length > 0){
			console.log('刷flag');
			flag=true;
		}
		var hhmmss;
		var sendDate;
		var sendtime;
		var nowtime=(new Date()).getTime();
		var chkDate = document.getElementById('chkDate').textContent;
        var chkdatestr=chkDate.replace(/-/g,'');
        console.log("会计日期=="+chkdatestr);
		for(var i = 0; i < noticeData.length; i++){
			hhmmss=noticeData[i].RELEASETIME;
		    hhmmss=hhmmss[0]+hhmmss[1]+':'+hhmmss[2]+hhmmss[3]+':'+hhmmss[4]+hhmmss[5];
		    sendDate=noticeData[i].RELEASEDATE;
		    if(sendDate==chkdatestr){
		        sendtime=(new Date(chkdatestr+hhmmss)).getTime();
		        if(nowtime>sendtime){
		            $("#noticeMessage").html(noticeData[i].description_cn);
                    $('#marquee').removeClass('ct-displayNone');
	                $('#marquee').addClass('ct-display');
	                setTimeout(function() {
			            $('#marquee').removeClass('ct-display');
	                    $('#marquee').addClass('ct-displayNone');
		            },(nowtime-sendtime));
		        }
		    }
		}
//		date = Date.parse((new Date()));   精确到秒000ms级，不准确不好对应延时参数，适合做时分秒倒计时样式
//      timestamp =(new Date()).valueOf(); 精确到ms级,对应cef机制，本函数不容易让开发人员适应
        newDate = (new Date()).getTime(); 
//      console.log(date);
//      console.log(timestamp);
        console.log(newDate);
		
		console.log('刷新公告完成');
//	    if( flag== true ){
//	    console.log('开始跑屏推送');
//	    console.log(noticeData[0].description_cn);
//	    console.log(noticeData[0].releasetime);
//	    $("#noticeMessage").html(noticeData[0].description_cn);
//      $('#marquee').removeClass('ct-displayNone');
//	    $('#marquee').addClass('ct-display');
//	    setTimeout(function() {
//			$('#marquee').removeClass('ct-display');
//	        $('#marquee').addClass('ct-displayNone');
//		},3000);
//	}
	});
	
}


/**
 * 初始化便签--拼接日期
 * @param {Object} jsonData
 */
function initNote(noteData) {
	var jsonData = noteData.Result;
	var noteList = document.getElementById("myNoteListItem");
	var innerHtml = '';
	/** 初始化时间轴标题 **/
	innerHtml += '<li class="ct-clear">';
	innerHtml += '<div class="ct-timeAxisTitleText"></div>';
	innerHtml += '<div class="ct-timeAxisStartDot"></div>';
	innerHtml += '<div class="ct-timeAxisText"></div>';
	innerHtml += '</li>';
	innerHtml += '<li class="ct-clear">';
	innerHtml += '<div class="ct-timeAxisLineLeft"></div>';
	innerHtml += '<div class="ct-timeAxisLineCenter"></div>';
	innerHtml += '<div class="ct-timeAxisText"></div>';
	innerHtml += '</li>';
/*    
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth() + 1; //js获取的月份是从0开始；
	var day = now.getDate();
	var curDate = ''+year + checkMonth(month) + checkMonth(day);
*/
	console.log(noteData.nowDate);
	/** 初始化数据 **/
	var flag = false;
	for(var i = 0; i < jsonData.length; i++) {
//当日期为每日每天每周时，便签时期默认赋值为99999999用作判断生成便签内容
	if(jsonData[i].Dt==''){
		jsonData[i].Dt='99999999';
	}
//jquery-2.1.3.min.js经测试无substr，substring，slice方法。
//	if(jsonData[i].Time.length==6){
//	   var a=jsonData[i].Time.
//	   console.log(a);
//	   var b=b.slice(0,2);
//	   var c=jsonData[i].Time.slice(3,2);
//	   jsonData[i].Time=a+':'+b+':'+c;
//	   console.log(jsonData[i].Time);
//	}
		if(jsonData[i].Dt >= noteData.nowDate) {
			flag = true;
			innerHtml += '<li class="ct-clear">';
			innerHtml += '<div class="ct-timeAxisTextLeft">' + jsonData[i].Co + '</div>';
			innerHtml += '<div class="ct-timeAxisTextCenter"></div>';
			innerHtml += '<div class="ct-timeAxisText">' + jsonData[i].Time + '</div>';
			innerHtml += '</li>';
			innerHtml += '<li class="ct-clear">';
			innerHtml += '<div class="ct-timeAxisLineLeft"></div>';
			innerHtml += '<div class="ct-timeAxisLineCenter"></div>';
			innerHtml += '<div class="ct-timeAxisText">' + jsonData[i].Cntnt + '</div>';
			innerHtml += '</li>';
			if(i >= 4) {
				break;
			}
		}
	}
	if(flag) {
		noteList.innerHTML = innerHtml;
	}
		console.log(noteList);
}

function checkMonth(month) {
	if(month < 10) {
		month = "0" + month;
	}
	return month;
}

/**
 * 刷新便签
 */
function refreshNote() {
	console.log("刷新便签");
	var eventData = {
		'eventType': 'note',
		'operation': 'refreshNote'
	};
	window.sendAdoreMessage(eventData, function(data) {
		$('#myNoteListItem').empty();
		console.log(data);
		var noteData = jQuery.parseJSON(data);
		initNote(noteData);
	});
}

function refreshService() {
	var eventData = {
		'eventType': 'service',
		'operation': 'refreshService'
	};
	window.sendAdoreMessage(eventData, function(data) {
		$('#myServiceListItem').empty();
		var serviceData = jQuery.parseJSON(data);
		initService(serviceData);
	});
}

/**
 * 初始化服务信息
 * @param {Object} jsonData 服务信息数据
 */
function initService(jsonData) {
	var serviceItem = document.getElementById('myServiceListItem');
	serviceItem.innerHTML = '';
	var innerHtml = '';
	innerHtml += '<li>';
	innerHtml += '<span>ABS节点：</span>';
	innerHtml += '<em>' + jsonData.CELLNAME + '</em>';
	innerHtml += '</li>';
	innerHtml += '<li>';
	innerHtml += '<span>ABS IP地址：</span>';
	innerHtml += '<em>' + jsonData.SERVERIP + '</em>';
	innerHtml += '</li>';
	var status = '';
	if('1' == jsonData.NETSTATUS) {
		status = 'ct-netStatusNormal';
	} else {
		status = 'ct-netStatusError';
	}
	innerHtml += '<li>';
	innerHtml += '<span class="ct-netStatus">ABC连接状态：</span>';
	innerHtml += '<div id="ct-netStatusDiv" class="' + status + '"></div>';
	innerHtml += '</li>';
	innerHtml += '<li class="ct-clear">';
	innerHtml += '<span>上次登录时间：</span>';
	innerHtml += '<em>' + jsonData.LOGINTIME + '</em>';
	innerHtml += '</li>';
	serviceItem.innerHTML = innerHtml;
}

/**
 * 初始化外设状态
 * @param {Object} jsonData 外设状态信息
 */

function initDeviceStatus(jsonData) {
	var deviceList = document.getElementById("deviceListItem");
	deviceList.innerHTML = '';
	var innerHtml = '';
	for(var propertyName in jsonData) {
		innerHtml += '<li style="clear: both;">';
		innerHtml += '<span class="ct-deviceName">' + propertyName + ':</span>';
		var status = 'ct-deviceStatusError';
		if('0' == jsonData[propertyName] || '-28' == jsonData[propertyName] || '-19' == jsonData[propertyName]) {
			status = 'ct-devicestatusNormal';
		}
		innerHtml += '<div class="' + status + '"></div>';
	}
	deviceList.innerHTML = innerHtml;
}

function refreshDeviceStatus() {
	var eventData = {
		'eventType': 'device',
		'operation': 'refreshDeviceStatus'
	};
	window.sendAdoreMessage(eventData, function(data) {
		$('#deviceListItem').empty();
		var deviceStatusData = jQuery.parseJSON(data);
		initDeviceStatus(deviceStatusData);
	});
}

//增加公告后及时刷新--gl
function moreNotice(event) {
	var eventData = {
		'eventType': 'notice',
		'operation': 'moreNotice'
	};
	window.sendAdoreMessage(eventData, null);
//	console.log("开始刷新公告");
//	$('#myNoticeList').empty();
//	refreshNotice();
}
function moreTask(event) {
	var eventData = {
		'eventType': 'workTask',
		'operation': 'moreTask'
	};
	window.sendAdoreMessage(eventData, null);
}
function readNotice(event) {
	var noticeData = event.srcElement.getAttribute("data");
	var eventData = {
		'eventType': 'notice',
		'operation': 'readNotice',
		'noticeData': noticeData
	};
	window.sendAdoreMessage(eventData, null);
}

function moreNote(event) {
	var eventData = {
		'eventType': 'note',
		'operation': 'moreNote'
	};
	window.sendAdoreMessage(eventData, null);
}

function addNote(event) {
	var eventData = {
		'eventType': 'note',
		'operation': 'addNote'
	};
	window.sendAdoreMessage(eventData, null);
}

function refreshServiceStatus(data) {
	var status = data.status;
	var statusDiv = $('#ct-netStatusDiv');
	if(status == 'Disconnection') {
		statusDiv.removeClass('ct-netStatusNormal');
		statusDiv.addClass('ct-netStatusError');
	} else {
		statusDiv.removeClass('ct-netStatusError');
		statusDiv.addClass('ct-netStatusNormal');
	}
}
	
