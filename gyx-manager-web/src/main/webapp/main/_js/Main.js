/* 基版徽商，变动部分：帮助、公告、便签、客户框架、柜员权限控制、消息、事件
 * lnnx客户现场硬件环境基础底、废弃定时调度时时刷新客户端消息机制、关联任务机制废弃
 * 客户框架涉及7后台接口180063-69
 *   --gl
 */
//import getEnumNameByValue from "./enum.js";
var tradeInfo;
var customInfo;
$(window).on('load', function () {
	console.log("window加载");

/*
 * 增加日历、用于内部服务布局
 */
calendar();

function calendar() {

var calendarElement = document.getElementById('calendar');
var todayTimeElement = document.getElementById('todayTime');
var todayDateElement = document.getElementById('todayDate');
var selectYearElement = document.getElementById('selectYear');
var selectMonthElement = document.getElementById('selectMonth');
var showTableElement = document.getElementById('showTable');
var prevMonthElement = document.getElementById('prevMonth');
var nextMonthElement = document.getElementById('nextMonth');

calendarElement.style.display = 'block';

/*
   * 获取今天的时间
   * */
var today = new Date();

//设置日历显示的年月
var showYear = today.getFullYear();
var showMonth = today.getMonth();

//持续更新当前时间
updateTime();

/***********gyx************************************/
//监听局域网连接
//LANState();
/***********************************************/

//显示当前的年月日星期
todayDateElement.innerHTML = getDate(today);

//动态生成选择年的select
for (var i = 1970; i < 2020; i++) {
    var option = document.createElement('option');
    option.value = i;
    option.innerHTML = i;
    if (i == showYear) {
      option.selected = true;
    }
    selectYearElement.appendChild(option);
}
//动态生成选择月的select
for (var i = 1; i < 13; i++) {
    var option = document.createElement('option');
    option.value = i - 1;
    option.innerHTML = i;
    if (i == showMonth + 1) {
      option.selected = true;
    }
    selectMonthElement.appendChild(option);
}

//初始化显示table
showTable();

//选择年
selectYearElement.onchange = function() {
    showYear = this.value;
    showTable();
    showOption();
}

//选择月
selectMonthElement.onchange = function() {
    showMonth = Number(this.value);
    showTable();
    showOption();
}

//上一个月
prevMonthElement.onclick = function() {
    showMonth--;
    showTable();
    showOption();
}

//下一个月
nextMonthElement.onclick = function() {
    showMonth++;
    showTable();
    showOption();
}


/*
* 实时更新当前时间
* */
function updateTime() {
    var timer = null;
    //每个500毫秒获取当前的时间，并把当前的时间格式化显示到指定位置
    var today = new Date();
    todayTimeElement.innerHTML = getTime(today);
    timer = setInterval(function() {
      var today = new Date();
      todayTimeElement.innerHTML = getTime(today);
    }, 500);
}

/*****************gyx 2018-05-25/11:08 增加，当客户端与局域网连接状态发生改变时，修改vost文字***************************************************************************/
/*
*	持续检查网络状态
**
function LANState(){
	window.setInterval(function(){
		if(!navigator.onLine){
		 document.getElementById("VTconnect1").innerHTML("VOST连接失败");
		}
	},1000);
	
}
/
/******************************结束**********************************************************/

function showTable() {
    showTableElement.tBodies[0].innerHTML = '';
    //根据当前需要显示的年和月来创建日历
    //创建一个要显示的年月的下一个的日期对象
    var date1 = new Date(showYear, showMonth + 1, 1, 0, 0, 0);
    //对下一个月的1号0时0分0秒的时间 - 1得到要显示的年月的最后一天的时间
    var date2 = new Date(date1.getTime() - 1);
    //得到要显示的年月的总天数
    var showMonthDays = date2.getDate();
    //获取要显示的年月的1日的星期,从0开始的星期
    date2.setDate(1);
    //showMonthWeek表示这个月的1日的星期，也可以作为表格中前面空白的单元格的个数
    var showMonthWeek = date2.getDay();

    var cells = 7;
    var rows = Math.ceil((showMonthDays + showMonthWeek) / cells);

    //通过上面计算出来的行和列生成表格
    //没生成一行就生成7列
    //行的循环
    for (var i = 0; i < rows; i++) {

      var tr = document.createElement('tr');

      //列的循环
      for (var j = 0; j < cells; j++) {

        var td = document.createElement('td');

        var v = i * cells + j - (showMonthWeek - 1);

        //根据这个月的日期控制显示的数字
        //td.innerHTML = v;
        if (v > 0 && v <= showMonthDays) {

          //高亮显示今天的日期
          if (today.getFullYear() == showYear && today.getMonth() == showMonth && today.getDate() == v) {
            td.className = 'today';
          }

          td.innerHTML = v;
        } else {
          td.innerHTML = '';
        }

        tr.appendChild(td);

      }

      showTableElement.tBodies[0].appendChild(tr);

    }
}

function showOption() {

    var date = new Date(showYear, showMonth);
    var sy = date.getFullYear();
    var sm = date.getMonth();
    console.log(showYear, showMonth)

    var options = selectYearElement.getElementsByTagName('option');
    for (var i = 0; i < options.length; i++) {
      if (options[i].value == sy) {
        options[i].selected = true;
      }
    }

    var options = selectMonthElement.getElementsByTagName('option');
    for (var i = 0; i < options.length; i++) {
      if (options[i].value == sm) {
        options[i].selected = true;
      }
    }
}
}

/*
   * 获取指定时间的时分秒
   * */
function getTime(d) {
return [
    addZero(d.getHours()),
    addZero(d.getMinutes()),
    addZero(d.getSeconds())
].join(':');
}

/*
* 获取指定时间的年月日和星期
* */
function getDate(d) {
return d.getFullYear() + '年' + addZero(d.getMonth() + 1) + '月' + addZero(d.getDate()) + '日 星期' + getWeek(d.getDay());
}

/*
* 给数字加前导0
* */
function addZero(v) {
if (v < 10) {
    return '0' + v;
} else {
    return '' + v;
}
}

/*
* 把数字星期转换成汉字星期
* */
function getWeek(n) {
return '日一二三四五六'.split('')[n];
}


//界面基础事件封装改动较少	
	$('.ct-dropdown>a').click(function () {
		hideDown();
		hideUp();
		dropdown();
		hideDropdownTooltip(event);
	});

	$('.ct-dropup>a').click(function () {
		hideDown();
		hideUp();
		dropup();
		hideDropupTooltip(event);
	});

	$('.ct-dropdown>a').mouseover(function () {
		showDropdownTooltip(event);
	});

	$('.ct-dropdown>a').mouseleave(function () {
		hideDropdownTooltip(event);
	});

	$('.ct-dropup>a').mouseover(function () {
		showDropupTooltip(event);
	});

	$('.ct-dropup>a').mouseleave(function () {
		hideDropupTooltip(event);
	});

	$('#sidebar>ul>li>a').click(function () {
		var isCollapse = $('#sidebar').attr('class').indexOf('ct-sidebar') < 0;
		if (!isCollapse) {
			$('#sidebar').removeClass('ct-sidebar');
			$('#sidebar').addClass('ct-collapseSidebar');

			var sidebarText = $('.ct-sidebarText');
			sidebarText.removeClass('ct-sidebarText');
			sidebarText.addClass('ct-collapseSidebarText');

			var content = $('.ct-content');
			content.removeClass('ct-content');
			content.addClass('ct-content_sidebarCollapse');

			var commonMenu = $('.ct-commonMenu');
			commonMenu.removeClass('ct-commonMenu');
			commonMenu.addClass('ct-commonMenu-collapse');

			var menucontainer = $('.ct-commonMenu-container');
			menucontainer.removeClass('ct-commonMenu-container');
			menucontainer.addClass('ct-commonMenu-container-collapse');
		} else {
			$('#sidebar').removeClass('ct-collapseSidebar');
			$('#sidebar').addClass('ct-sidebar');

			var sidebarText = $('.ct-collapseSidebarText');
			sidebarText.removeClass('ct-collapseSidebarText');
			sidebarText.addClass('ct-sidebarText');

			var content = $('.ct-content_sidebarCollapse');
			content.removeClass('ct-content_sidebarCollapse');
			content.addClass('ct-content');

			var commonMenu = $('.ct-commonMenu-collapse');
			commonMenu.removeClass('ct-commonMenu-collapse');
			commonMenu.addClass('ct-commonMenu');

			var menucontainer = $('.ct-commonMenu-container-collapse');
			menucontainer.removeClass('ct-commonMenu-container-collapse');
			menucontainer.addClass('ct-commonMenu-container');
		}
	});

	$('.sidebarItem').click(function (event) {
		$('.ct-selectedBar').removeClass('ct-show');
		$('.ct-selectedBar').addClass('ct-hide');
		$('.sidebarItem').removeClass('ct-sidebarSelected');
		$('.sidebarItem').addClass('ct-sidebarUnselect');

		var sourceElement = event.target;
		var data = sourceElement.getAttribute('data');

		if (data == null || data == '') {
			var el = sourceElement.parentElement;
			data = el.getAttribute('data');
			$(el).removeClass('ct-sidebarUnselect');
			$(el).addClass('ct-sidebarSelected');

			var bar = sourceElement.parentElement.getElementsByClassName('ct-selectedBar');
			$(bar[0]).removeClass('ct-hide');
			$(bar[0]).addClass('ct-show');
		} else {
			$(sourceElement).removeClass('ct-sidebarUnselect');
			$(sourceElement).addClass('ct-sidebarSelected');
			var bar = sourceElement.getElementsByClassName('ct-selectedBar');
			$(bar[0]).removeClass('ct-hide');
			$(bar[0]).addClass('ct-show');
		}

		var contents = $('#ct-content').children();
		for (var i = 0; i < contents.length; i++) {
			var c = contents[i];
			$(c).removeClass('ct-display');
			$(c).addClass('ct-displayNone');
		}

		$('#' + data).removeClass('ct-displayNone');
		$('#' + data).addClass('ct-display');

		var appid = "001";
		if ("ct-content-custom" === data) {
			appid = "001";
		    console.log("点击客户服务");
		    switchPCTab(0);
		} else {
			appid = "002";
		}

		var eventData = {
			"eventType": "personal",
			"operation": "switchService",
			"appid": appid
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#menu_close').click(function () {
		menuClose();
	});
	
	$('#commonMenu').click(function () {
		console.log("调用常用菜单维护lfc");
		initFavoriteTran();
		
		hideDown();
		hideUp();
		console.log("常用菜单点击");
//		initFavoriteMenu(sessiondata);
		var isCollapse = $('#sidebar').attr('class').indexOf('ct-sidebar') < 0;
		var menuContainer;
		if (!isCollapse) {
			menuContainer = $('.ct-commonMenu-container');
		} else {
			menuContainer = $('.ct-commonMenu-container-collapse');
		}
		if (!menuContainer) {
			return;
		}
		menuContainer.removeClass('ct-hide');
		menuContainer.addClass('ct-show');
	});

	$('#ct-personInfo').click(function (event) {
		hideDown();
		var eventData = {
			'eventType': 'personal',
			'operation': 'personalInfo'
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#ct-help').click(function (event) {
		hideDown();
		var eventData = {
			'eventType': 'personal',
			'operation': 'help'
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#ct-setting').click(function (event) {
		hideDown();
		var eventData = {
			'eventType': 'personal',
			'operation': 'setting'
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#ct-lock').click(function (event) {
		hideDown();
		var eventData = {
			'eventType': 'personal',
			'operation': 'lock'
		};
		window.sendAdoreMessage(eventData, null);
	});

/*
 * 签到签退lnnx无日终签退概念、柜员状态存在碰库等特殊状态新增逻辑  --gl
 */
	$('#ct-signout').click(function (event) {
		hideDown();
		var eventData = {
			'eventType': 'personal',
			'operation': 'signout'
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#signin').click(function (event) {
		if(document.getElementById('signina').textContent=='柜员签到'){
		var eventData = {
			'eventType': 'signIn',
			'operation': ''
		};
		window.sendAdoreMessage(eventData, null);
		}else if(document.getElementById('signina').textContent=='柜员签退'){
			var eventData = {
			'eventType': 'personal',
			'operation': 'signOut'
		};
		window.sendAdoreMessage(eventData, null);
		}else if(document.getElementById('signina').textContent=='现金碰库'){
			document.getElementById("signInfo").style.display='block';
			console.log("刷新柜员");
		}
	});
	
	 $('#ct-tellerIn').click(function (event) {
		var eventData = {
			'eventType': 'signIn',
			'operation': ''
		};
		window.sendAdoreMessage(eventData, null);
	});
	
	 $('#ct-tellerOut').click(function (event) {
		var eventData = {
			'eventType': 'personal',
			'operation': 'signOut'
		};
		window.sendAdoreMessage(eventData, null);
	});

    $('#tranHelp').click(function (event) {
		var eventData = {
			'eventType': 'tranHelp',
			'operation': ''
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#ct-device').click(function (event) {
		hideDown();
		$('#ct-deviceStatus').removeClass('ct-displayNone');
		$('#ct-deviceStatus').addClass('ct-display');
		refreshDeviceStatus();
	});

	$('#ct-service').click(function (event) {
		hideDown();
		$('#ct-serviceInfo').removeClass('ct-displayNone');
		$('#ct-serviceInfo').addClass('ct-display');
		refreshService();
	});

	$('#ct-todoTaskButton').click(function (event) {
		var eventData = {
			'eventType': 'task',
			'operation': 'getTaskData',
			'taskType': '1'
		};
		window.sendAdoreMessage(eventData, function (data) {
			var jsonData = jQuery.parseJSON(data);
			var taskData = jsonData.data;
			var num = jsonData.num;
//			console.log(taskData);
			setTodoTaskNum(num);
			var taskElement = $('#ct-todoTaskData');
			setTaskData(taskElement, taskData);
		});
	});

	$('#ct-handlingTaskButton').click(function (event) {
		var eventData = {
			'eventType': 'task',
			'operation': 'getTaskData',
			'taskType': '2'
		};
		window.sendAdoreMessage(eventData, function (data) {
			var jsonData = jQuery.parseJSON(data);
			var taskData = jsonData.data;
			var num = jsonData.num;
//			console.log(taskData);
			setHandlingTaskNum(num);
			var taskElement = $('#ct-handlingTaskData');
			setTaskData(taskElement, taskData);
		});
	});

	$('#ct-finishedTaskButton').click(function (event) {
		var eventData = {
			'eventType': 'task',
			'operation': 'getTaskData',
			'taskType': '3'
		};
		window.sendAdoreMessage(eventData, function (data) {
			var jsonData = jQuery.parseJSON(data);
			var taskData = jsonData.data;
			var num = jsonData.num;
			console.log(taskData);
			setFinishedTaskNum(num);
			var taskElement = $('#ct-finishedTaskData');
			setTaskData(taskElement, taskData);
		});
	});

	$('#ct-interruptTaskButton').click(function (event) {
		var eventData = {
			'eventType': 'task',
			'operation': 'getTaskData',
			'taskType': '4'
		};
		window.sendAdoreMessage(eventData, function (data) {
			var jsonData = jQuery.parseJSON(data);
			var taskData = jsonData.data;
			var num = jsonData.num;

			setInterruptTaskNum(num);
			var taskElement = $('#ct-interruptTaskData');
			setTaskData(taskElement, taskData);
		});
	});
	$('#ct-printTaskButton').click(function (event) {
		var eventData = {
			'eventType': 'task',
			'operation': 'getTaskData',
			'taskType': '5'
		};
		window.sendAdoreMessage(eventData, function (data) {
			var jsonData = jQuery.parseJSON(data);
			var taskData = jsonData.data;
			var num = jsonData.num;

			setPrintTaskNum(num);
			var taskElement = $('#ct-printTaskData');
			setTaskData(taskElement, taskData);
		});
	});

	$('#ct-depositCalc').click(function (event) {
		calc(event, 'depositCalc');
	});

	$('#ct-loanCalc').click(function (event) {
		calc(event, 'loanCalc');
	});

	$('#ct-financialCalc').click(function (event) {
		calc(event, 'financialCalc');
	});

	$('#ct-commonCalc').click(function (event) {
		calc(event, 'commonCalc');
	});

	$(document).click(function (e) {
		var e = e || window.event;
		var elem = e.target || e.srcElement;
		while (elem) {
			if (elem.className && (elem.className == 'ct-dropdown' || elem.className == 'ct-dropup' || elem.className == 'ct-commonMenu' || elem.className == 'ct-commonMenu-collapse' || elem.className == 'ct-dropdown-menu' || elem.className == 'ct-dropup-menu' || elem.className == 'ct-commonMenu-container' || elem.className == 'ct-commonMenu-container-collapse')) {
				return;
			}
			elem = elem.parentElement;
		}

		hideDown();
		hideUp();
		menuClose();
	});

    
    $('#remoteControl').click(function () {
		if(document.getElementById('remoteName').textContent=='启动远程控制台'){
			console.log("启动远程控制台");
			StartRemoteControl();
		var eventData = {
			'eventType': 'remoteControl',
			'operation': ''
		};
		window.sendAdoreMessage(eventData, null);
		}else if(document.getElementById('remoteName').textContent=='停止远程控制台'){
			StopRemoteControl();
			console.log("停止远程控制台");
			var eventData = {
			'eventType': 'stopRemoteControl',
			'operation': ''
		};
		window.sendAdoreMessage(eventData, null);
	    }
	});
    
	$('#ct-ssoButton').click(function () {
		var eventData = {
			'eventType': 'sso',
			'operation': 'sso'
		};
		window.sendAdoreMessage(eventData, null);
	});
	$("#ct-runrisk").click(function(){
		var eventData = {
			'eventType':'risk',
			'operation': 'risk'
		};
		window.sendAdoreMessage(eventData,null);
	})
	var flag = false;
	$('.ct-softCallButton').click(function (event) {
		var type = $(event.target).attr('type');
		if (!type) {
			type = $(event.target.parentElement).attr('type');
		}
		var _this_ = event.target;
		if(_this_.tagName=='IMG'){
			$(_this_).css('background-color','#CCC');
		} 
		if(!flag){
			flag = true;
			var eventData = {
			'eventType': 'softCall',
			'operation': type
			};
			sendAdoreMessage(eventData,null);	
		}
		setTimeout(function() {
			flag = false;
			if(_this_.tagName == 'IMG') {
				$(_this_).css('background-color','#ECB1AC');
			} 
		},5000);
	});

	$('#ct-finishedTrades').click(function () {
		var eventData = {
			'eventType': 'busi',
			'operation': 'moreTrades'
		};
		sendAdoreMessage(eventData, null);
	});

	$('#ct-serviceCustoms').click(function () {
		var eventData = {
			'eventType': 'busi',
			'operation': 'moreCustoms'
		};
		sendAdoreMessage(eventData, null);
	});

	$('#hotKeyList').click(function (event) {
		hideUp();
		var eventSource = event.target;
		var id = eventSource.id;
		if (!id) {
			id = eventSource.parentElement.id
		}
//		alert(id);
		var eventData = {
			'eventType': 'hotKey',
			'operation': id
//			'data':id
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#serviceEndButton').click(function () {
//		initCustomInfo();
//		switchSoftCallAndCustInfo(0);
//		var data = {
//			'CstNum': '',
//			'CtfTp': '',
//			'CtfNum': '',
//			'CstNm': '',
//			'CstTp': '',
//			'SerialNum': '',
//			'Dt': '',
//			'Cgy': '',
//			'CstRtg': '',
//			'AmtOfMny': '',
//			'AmtOfMny1': '',
//			'AmtOfMny2': ''
//		};
//		setCustomData(data);
//		switchCustomTab(0);
//		
		var eventData = {
			'eventType': 'custom',
			'operation': 'serviceEnd'
		};
		window.sendAdoreMessage(eventData, null);
//		$('.adore-expandableText').setFocus();
        
	});

    $('#cash').click(function () {
		var eventData = {
			'eventType': 'cash',
			'operation': ''
		};
		window.sendAdoreMessage(eventData, null);
	});


	$('#customQueryButton').click(function () {
		var certNumValue = $('#certNum').val();
	    if(certNumValue == "" || certNumValue.length < 1){
	    	$('#certNum').focus();
	    	return;
	    }
        var eventData = {
			'eventType': 'custom',
			'operation': 'customQuery',
			'certNum': $('#certNum').val(),
			'custType': '1',
			'inputMode': $('#customMode1').val(),
			'queryType':'1'
		};
		window.sendAdoreMessage(eventData, null);
/**************************高艺祥2018-05-05/15:20 增加 *****************************************/	
        document.getElementById('customMode1').value="0";	
		document.getElementById("certNum").value = "";
/**************************End*****************************************/		
        //$('#certNum').empty();
        console.log("个人查询");
//      }
	});
	
	
        $('#customQueryButton1').click(function() {
        console.log("个人客户查询");
        if($('#accountInput').val()=='' || $('#accountInput').val()==null) {
       $('#accountInput').focus();
        }else{
        var eventData = {
        'eventType': 'custom',
        'operation': 'customQuery',
        'certNum': $('#accountInput').val(),
        'certType': $('#voucherType').val(),
        'custType': '1',
        'selectMode': $('#selectMode').val().toString(),
        'inputMode': $('#inputType2').val()
        };
        window.sendAdoreMessage(eventData, null);
       }
      });	
	

	
	

	
	
	
	
	
	
	
	$('#companyQueryButton').click(function () {
		console.log("对公查询");
		 var eventData = {
			'eventType': 'custom',
			'operation': 'customQuery',
			'certNum': $('#customNumInput').val(),
			'custType': '2',
			'inputMode': '0'
		};
		console.log( $('#certNum').val());
		window.sendAdoreMessage(eventData, null);
		$('#certNum').empty();
	});
	
	$('#companyQueryButton1').click(function () {
		console.log("对公查询");
		 var eventData = {
			'eventType': 'custom',
			'operation': 'customQuery',
			'certNum': $('#customNumInput1').val(),
			'selectMode': "0",
			'custType': '2'
		};
		console.log( $('#certNum').val());
		window.sendAdoreMessage(eventData, null);
		$('#certNum').empty();
	});

	$('#accountNoButton').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'customNumQuery',
			'customType': $('#inputCustomType2').val(),
			'customNum': $('#customNum').val()
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#accountQueryButton').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'accountQuery',
			'voucherType': $('#voucherType').val(),
			'inputType': $('#inputType').val(),
			'account': $('#accountInput').val()
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#customType').blur(function () {
		var value = $(this).val();
		var certType;
		if (value == '1') {
			certType = certTypePersonal;
		} else {
			certType = certTypeCompany;
		}
		var certTypeSelect = $('#certType');
		certTypeSelect.val("");
		certTypeSelect.empty();
		var placeHolder = $('<option>', {
			'selected': 'true',
			'disabled': 'true'
		});
		placeHolder.html('请选择证件类型');
		certTypeSelect.append(placeHolder);
		for (var o in certType) {
			var option = $('<option>', {
				'value': o
			});
			option.html(o + '-' + certType[o]);
			certTypeSelect.append(option);
		}
	});

	$('#submitButton').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'submit'
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('.adore-expandableText').addFinishListener(function (key) {
		var item;
		for (var i = 0; i < tradeInfo.length; i++) {
			if (tradeInfo[i].TRANCODE == key) {
				item = tradeInfo[i].TRANCODE + "@" + tradeInfo[i].TRANNAME + "@" + tradeInfo[i].TRANPATH;
				break;
			}
		}
		// var eventData = {
		// 	'eventType': 'menu',
		// 	'operation': 'openTrade',
		// 	'menuData': item
		// };
		// window.sendAdoreMessage(eventData, null);
		openTrade(item);
	});

	$('#cutomModifyButton').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'modifyCustomInfo'
		};
		window.sendAdoreMessage(eventData, null);
	});
	
	$('#cutomModifyButton1').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'modifyCustomInfo'
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#customInfoDetail').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'modifyCustomInfo'
		};
		window.sendAdoreMessage(eventData, null);
	});
	
	$('#resident').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'resident'
		};
		window.sendAdoreMessage(eventData, null);
	});
	
	$('#resident1').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'resident'
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#idCheck').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'idCheck',
/*********************高艺祥2018.05.17/11:23 增加对私联网核查时传入参数增加 证件类型 *********************/			
			'inputMode':$('#ct-show-pcftTp').html().split("-")[0]
/*********************结束 ********************************************************************/			
		};
		window.sendAdoreMessage(eventData, null);
	});
	
	$('#idCheck1').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'idCheck',
/*********************高艺祥2018.06.04/11:23 修改对公联网核查时传入证件类型为法人证件类型 *********************/			
			'inputMode':$('#ct-show-gcsttp').html().split("-")[0]
/************************结束**************************************************************************/			
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#agentInfo').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'agentInfo'
		};
		window.sendAdoreMessage(eventData, null);
	});
	
	$('#agentInfo1').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'agentInfo'
		};
		window.sendAdoreMessage(eventData, null);
	});
/********************************高艺祥2018-05-12/14:13 增加*******************************************/	
	$('#checkSeal').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'checkSeal'
		};
		window.sendAdoreMessage(eventData, null);
	});
/********************************结束*******************************************/
    $('#vouNoInfo').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'vouNoInfo'
		};
		window.sendAdoreMessage(eventData, null);
	});
	
	//对公
	$('#vouNoInfo1').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'vouNoInfo'
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#customView').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'customView'
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#operator').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'operatorInfo'
		};
		window.sendAdoreMessage(eventData, null);
	});

	$('#companyInfo').click(function () {
		var eventData = {
			'eventType': 'custom',
			'operation': 'modifyCustomInfo'
		};
		window.sendAdoreMessage(eventData, null);
	});

	//由于一体化页面与交易是相互独立的页面，在一体化页面中无法监听到交易的鼠标事件，所以由平台处理发送交易点击事件到一体化页面，用于处理弹出菜单隐藏
	window.addEventListener("message", function (e) {
		if (e.data && e.data.type && "mousedown" === e.data.type && e.data.widgetId 
			&& e.data.widgetId.indexOf("ChatTool") == -1) {
			hideDown();
			hideUp();
			menuClose();
		}
	});

	$('.adore-expandableText').setPlaceholder('请输入交易码');
	showTime();
	initTaskNum();
	initTellerInfo();
	initBusiInfo();
	initWorkTask();
	// initSoftCall();
	$('.adore-expandableText').setFocus();
});

//客户框架枚举定义     --gl 11.9

//4.4 180063 64接口新增账户性质字段
var acctPry = {
	"00": "待核准账户",
	"01": "基本存款账户",
	"02": "一般存款账户",
	"03": "专用存款账户",
	"04": "临时存款账户",
	"05": "验资账户",          
    "99": "其它帐户" 
};

//4.9 180063 接口新增居民识别字段
var chkResident = {
	"0": "仅为中国税收居民",
	"1": "仅为非居民",
	"2": "既是中国又是其他国家（地区）的税收居民"
};

var gcheckResident = {
	"0": "非居民不是消极非金融机构",
	"1": "非居民消极非金融机构",
	"2": "居民消极非金融机构(控制人为非居民)",
	"3": "居民消极非金融机构(控制人为居民)",
	"4": "居民不是消极非金融机构",
	"5": "豁免机构"
};

//输入方式
var inputMode = {
	"0": "手工输入",
	"1": "读身份证",
	"2": "磁条输入",          
    "6": "IC卡输入" 
};

var accNoType = {
	"1": "存单",
	"2": "多功能存折",          
    "3": "卡",                  
    "5": "活期一本通存折",           
    "6": "证实书",             
    "7": "定期一本通存折",
    "9": "无介质"    
};

var certType = {
	"01": "身份证",
	"02": "军官证",          
    "03": "户口簿",          
    "04": "警官证",           
    "05": "士兵证",           
    "06": "护照",             
    "07": "港澳台居民通行证", 
    "08": "台湾同胞来往内地通行证",
    "10": "外国人永久居留证", 
    "11": "临时身份证",  
    "49": "其他(个人)",     
    "52": "营业执照",         
    "53": "登记证书",         
    "54": "批文",             
    "55": "开户证明"         
};

//对公证件类型
var gcertType = {   
    "52": "营业执照",         
    "53": "登记证书",         
    "54": "批文",             
    "55": "开户证明",
    "56": "统一信用代码",
    "99": "其他(单位)"         
};

var serverLevel= {
    "0":"普通",        
    "1":"本行员工",    
    "2":"潜在用户",    
    "3":"VIP客户",    
    "4":"黄金客户",    
    "5":"白金客户",    
    "8":"淘汰客户",   
    "9":"黑名单客户"  
};

var custStatus = {
	"00": "正常",
	"01": "注销",
	"02": "已合并"
};

//账户状态
var acctStatus = {
	"0": "正常",
	"1": "销户"
};

//账号状态
var ctpStatus = {
	"0": "正常",
	"1": "挂失"
};

//客户类型
var custType ={
    "110": "本行员工",                  
    "111": "普通客户",                  
    "201": "国有企业",                  
    "211": "股份公司",                  
    "212": "有限公司",                  
    "213": "集体企业",                  
    "214": "三资企业",                  
    "220": "合伙企业",                  
    "221": "个人独资企业",              
    "222": "其他企业",                    
    "230": "机关和实行预算管理事业单位",  
    "231": "非预算管理的事业单位",        
    "252": "民办非企业组织",              
    "253": "外地常设机构",                
    "254": "单位外设的独立核算的",        
    "255": "有字号的个体工商户",          
    "256": "居民委员位、村民委员",        
    "257": "外国驻华机构",                
    "258": "其他组织",                    
    "390": "金融同业-银行",               
    "391": "金融同业-其他",               
    "240": "团级（含）以上军队、武警部队",
    "250": "社会团体",                    
    "251": "宗教组织"                    
};

//国别
var nationCode = {
    "004": "阿富汗伊斯兰国",      
    "008": "阿尔巴尼亚共和国",    
    "010": "南极洲",              
    "012": "阿尔及利亚民主人民共",
    "016": "美属萨摩亚",          
    "020": "安道尔公国",          
    "024": "安哥拉共和国",        
    "028": "安提瓜和巴布达",      
    "031": "阿塞拜疆共和国",      
    "032": "阿根廷共和国",        
    "036": "澳大利亚联邦",        
    "040": "奥地利共和国",        
    "044": "巴哈马联邦",          
    "048": "巴林国",              
    "050": "孟加拉人民共和国",    
    "051": "亚美尼亚共和国",      
    "052": "巴巴多斯",            
    "056": "比利时王国",          
    "060": "百慕大群岛",          
    "064": "不丹王国",            
    "068": "玻利维亚共和国",      
    "070": "波斯尼亚和黑赛哥维纳",
    "072": "博茨瓦纳共和国",      
    "074": "布维岛",              
    "076": "巴西联邦共和国",      
    "084": "伯利兹",              
    "086": "英属印度洋领土",      
    "090": "所罗门群岛",          
    "092": "BVI",                 
    "096": "文莱达鲁萨兰国",      
    "100": "保加利亚共和国",      
    "104": "缅甸联邦",            
    "108": "布隆迪共和国",        
    "112": "白俄罗斯共和国",      
    "116": "柬埔寨王国",          
    "120": "喀麦隆共和国",        
    "124": "加拿大",              
    "132": "佛得角共和国",        
    "136": "开曼群岛",            
    "140": "中非共和国",          
    "144": "斯里兰卡民主社会主义",
    "148": "乍得共和国",          
    "152": "智利共和国",          
    "156": "中国",                
    "158": "中国台湾",            
    "162": "圣诞岛",              
    "166": "科科斯（基林）群岛",  
    "170": "哥伦比亚共和国",      
    "174": "科摩罗伊斯兰联邦共和",
    "175": "马约特",              
    "178": "刚果共和国",          
    "180": "扎伊尔共和国",        
    "184": "库克群岛",            
    "188": "哥斯达黎加共和国",    
    "191": "克罗地亚共和国",      
    "192": "古巴共和国",          
    "196": "塞浦路斯共和国",      
    "203": "捷克共和国",          
    "204": "贝宁共和国",          
    "208": "丹麦王国",            
    "212": "多米尼克联邦",        
    "214": "多米尼加共和国",      
    "218": "厄瓜多尔共和国",      
    "222": "萨尔多瓦共和国",      
    "226": "赤道几内亚共和国",    
    "231": "埃塞俄比亚",          
    "232": "厄立特里亚国",        
    "233": "爱沙尼亚共和国",      
    "234": "法罗群岛",            
    "238": "马尔维纳斯群岛",      
    "239": "南乔治岛和南桑德韦奇",
    "242": "斐济共和国",              
    "246": "芬兰共和国",        
    "250": "法兰西共和国",      
    "254": "法属圭亚那",        
    "258": "法属波利尼西亚",    
    "260": "法属南部领土",      
    "262": "吉布提共和国",      
    "266": "加蓬共和国",        
    "268": "格鲁吉亚共和国",    
    "270": "冈比亚共和国",      
    "276": "德意志联邦共和国",  
    "288": "加纳共和国",        
    "292": "直布罗陀",          
    "296": "基里巴斯共和国",    
    "300": "希腊共和国",        
    "304": "格陵兰",            
    "308": "格林纳达",          
    "312": "瓜德罗普",          
    "316": "关岛",              
    "320": "危地马拉共和国",    
    "324": "几内亚共和国",      
    "328": "圭亚那合作共和国",  
    "332": "海地共和国",        
    "334": "赫德岛和麦克唐纳岛",
    "336": "梵蒂冈城国",        
    "340": "洪都拉斯共和国",    
    "344": "香港",              
    "348": "匈牙利共和国",      
    "352": "冰岛共和国",        
    "356": "印度共和国",        
    "360": "印度尼西亚共和国",  
    "364": "伊朗伊斯兰共和国",  
    "368": "伊拉克共和国",      
    "372": "爱尔兰",            
    "374": "巴勒斯坦国",        
    "376": "以色列国",          
    "380": "意大利共和国",      
    "384": "科特迪瓦共和国",    
    "388": "牙买加",            
    "392": "日本国",            
    "398": "哈萨克斯坦共和国",    
    "400": "约旦哈希姆王国",      
    "404": "肯尼亚共和国",        
    "408": "朝鲜民主主义人民共和",
    "410": "韩国",                
    "414": "科威特国",            
    "417": "吉尔吉斯共和国",      
    "418": "老挝人民民主共和国",  
    "422": "黎巴嫩共和国",        
    "426": "莱索托王国",          
    "428": "拉托维亚共和国",      
    "430": "利比里亚共和国",      
    "434": "大阿拉伯利比亚人民社",
    "438": "列支敦士登公国",      
    "440": "立陶宛共和国",        
    "442": "卢森堡大公国",        
    "446": "澳门",                
    "450": "马达加斯加共和国",    
    "454": "马拉维共和国",        
    "458": "马来西亚",            
    "462": "马尔代夫共和国",      
    "470": "马尔他共和国",        
    "474": "马提尼克",            
    "478": "马里塔尼亚伊斯兰共和",
    "480": "毛里求斯共和国",      
    "484": "墨西哥合众国",        
    "492": "摩纳哥公国",          
    "496": "蒙古国",              
    "498": "摩尔多瓦共和国",      
    "500": "蒙特塞拉特",          
    "504": "摩洛哥王国",          
    "508": "莫桑比克共和国",      
    "512": "阿曼苏丹王国",        
    "516": "纳米比亚共和国",        
    "520": "瑙鲁共和国",            
    "524": "尼泊尔王国",            
    "528": "荷兰王国",              
    "530": "荷属安的列斯",          
    "533": "阿鲁巴",                
    "540": "新喀里多尼亚",        
    "548": "瓦努阿图共和国",      
    "554": "新西兰",              
    "558": "尼加拉瓜共和国",      
    "562": "尼日尔共和国",        
    "566": "尼日利亚联邦共和国",  
    "570": "纽埃",                
    "574": "诺福克岛",            
    "578": "挪威王国",            
    "580": "北马里亚纳自由联邦",  
    "581": "美属太平洋各群岛",    
    "583": "密克罗尼西亚联邦",    
    "584": "马绍尔群岛共和国",    
    "585": "贝劳共和国",          
    "586": "巴基斯坦伊斯兰共和国",
    "591": "巴拿马共和国",        
    "598": "巴布亚新几内亚独立国",
    "600": "巴拉圭共和国",        
    "604": "秘鲁共和国",          
    "608": "菲律宾共和国",          
    "612": "皮特凯恩岛",            
    "616": "波兰共和国",            
    "620": "葡萄牙共和国",          
    "624": "几内亚比绍",            
    "626": "东帝汶",                
    "630": "波多黎各自由联邦",      
    "634": "卡塔尔国",              
    "638": "留尼汪",                
    "642": "罗马尼亚",              
    "643": "俄罗斯联邦",            
    "646": "卢旺达共和国",          
    "654": "圣浩勒拿",              
    "659": "圣基茨和尼维斯联邦",    
    "660": "安圭拉",                
    "662": "圣卢西亚",              
    "666": "圣皮埃尔和密克隆",      
    "670": "圣文森特合格林纳丁斯",  
    "674": "圣马力诺共和国",        
    "678": "圣多美和普林西比民主",  
    "682": "沙特阿拉伯王国",        
    "686": "塞内加尔共和国",      
    "690": "塞舌尔共和国",        
    "694": "塞拉利昂共和国",      
    "702": "新加坡共和国",        
    "703": "斯洛伐克共和国",      
    "704": "越南社会主义共和国",  
    "705": "斯罗文尼亚共和国",    
    "706": "索马里共和国",        
    "710": "南非共和国",          
    "716": "津巴布韦共和国",      
    "724": "西班牙",              
    "732": "西撒哈拉",            
    "736": "苏丹共和国",          
    "740": "苏里南共和国",        
    "744": "斯瓦尔巴群岛",        
    "748": "斯威士兰王国",        
    "752": "瑞典王国",            
    "756": "瑞士联邦",            
    "760": "阿拉伯叙利亚共和国",  
    "762": "塔吉克斯坦共和国",    
    "764": "泰国",                
    "768": "多哥共和国",          
    "772": "托克劳",              
    "776": "汤加王国",            
    "780": "特立尼达和多巴哥共和",
    "784": "阿拉伯联合酋长国",    
    "788": "突尼斯共和国",        
    "792": "土耳其共和国",        
    "795": "土库曼斯坦",          
    "796": "特克斯和凯科斯群岛",    
    "798": "图瓦卢",                
    "800": "乌干达共和国",          
    "804": "乌克兰",                
    "807": "马其顿共和国",          
    "818": "阿拉伯埃及共和国",      
    "826": "英国",                  
    "834": "坦桑尼亚联合共和国",    
    "840": "美国",                  
    "850": "美属维尔京群岛",        
    "854": "布基纳法索",        
    "858": "乌拉圭东岸共和国",  
    "860": "乌兹别克斯坦共和国",
    "862": "委内瑞拉共和国",    
    "876": "瓦利斯合富图纳群岛",
    "882": "西萨摩亚独立国",    
    "887": "也门共和国",        
    "891": "南斯拉夫联盟共和国",
    "894": "赞比亚共和国",      
    "900": "国家和地区不明",    
    "901": "中国一般贸易区",    
    "902": "中国保税区",        
    "903": "中国加工区",        
    "904": "中国钻石交易所",    
    "Z00": "中国一般贸易区",    
    "Z01": "中国保税区",        
    "Z02": "中国加工区",        
    "Z03": "中国钻石交易所"    
};


function menuClose() {
	var sidebar = $('#sidebar');
	if (!sidebar) {
		return;
	}
	var isCollapse = sidebar.attr('class').indexOf('ct-sidebar') < 0;
	var menuContainer;
	if (!isCollapse) {
		menuContainer = $('.ct-commonMenu-container');
	} else {
		menuContainer = $('.ct-commonMenu-container-collapse');
	}
	if (!menuContainer) {
		return;
	}
	menuContainer.removeClass('ct-show');
	menuContainer.addClass('ct-hide');
}

function calc(event, type) {
	hideUp();
	var eventData = {
		'eventType': 'calc',
		'operation': type
	};
	window.sendAdoreMessage(eventData, null);
}

function setTaskData(taskElement, data) {
	taskElement.empty();
	var innerHtml = '';
	
	for (var i = 0; i < data.length; i++) {
		var dataItem = data[i];
//		console.log(dataItem);
		innerHtml += '<li data="' + dataItem + '">';
		innerHtml += '<div class="ct-icon-content">';
		innerHtml += '<i class="fa fa-plus"></i>';
		innerHtml += '</div>';
		var htmlData = '';
		var taskType = dataItem[9];
		if (dataItem[9] == '1') {
			taskType += '-集中作业';
		} else if (dataItem[9] == 'A2') {
			taskType += '-远程授权';
		} else if (dataItem[9] == 'A3') {
			taskType += '-异终端授权';
		} else if (dataItem[9] == 'C') {
			taskType += '-复核';
		} else {
			taskType += '-其他';
		}
		var tradeName = ' ';
		if (dataItem[3] && dataItem[3] != '' && dataItem[3] != null && dataItem[3] != 'null') {
			tradeName = dataItem[3];
		}
		var tradeCode = ' ';
		if (dataItem[2] && dataItem[2] != '' && dataItem[2] != null && dataItem[2] != 'null') {
			tradeCode = dataItem[2];
			
		}
		var endReason = ' ';
		if(dataItem[13]){
			endReason = dataItem[13];
		}
		htmlData += taskType + ' ' + dataItem[0] + ' ' + dataItem[8] + ' ' + tradeCode + '-' + tradeName + ' ' + endReason;
//		console.log(htmlData);
		innerHtml += '<span class="ct-dropdown-content-span">' + htmlData + '</span>';
		innerHtml += '</li>';
	}
	taskElement.html(innerHtml);
	if (taskElement.attr('id') == 'ct-todoTaskData' || taskElement.attr('id') == 'ct-handlingTaskData' || taskElement.attr('id') == 'ct-printTaskData') {
		var taskItem = taskElement.find('li');
		taskItem.click(function (event) {
			hideDown();
			var eventSource = event.target;
			var item = eventSource;
			if (eventSource.tagName != 'LI') {
				if (eventSource.tagName == 'I') {
					item = eventSource.parentElement.parentElement;
				} else {
					item = eventSource.parentElement;
				}
			}
			var data = $(item).attr('data');
			var eventData = {
				'eventType': 'task',
				'operation': 'handleTask',
				'data': data
			};
			window.sendAdoreMessage(eventData, function (returnData) {
				alert(returnData);
			});
		
		});
	}
}

function dropdown() {
	var srcElement = event.srcElement;
	var content = null;
	if (srcElement.tagName == 'A') {
		content = srcElement.parentElement.getElementsByClassName('ct-dropdown-menu')[0];
	} else {
		content = srcElement.parentElement.parentElement.getElementsByClassName('ct-dropdown-menu')[0];
	}
	if (!content) {
		return;
	}
	var classStr = content.className;
	if (classStr.indexOf('ct-show') >= 0) {
		hideDown();
	} else {
		showDown();
	}
}

function showDown() {
	var content = null;
	var srcElement = event.srcElement;
	// var eventData = {
	// 	eventType: "showBrowser",
	// 	operation: "",
	// 	visible: true
	// };

	// window.sendAdoreMessage(eventData, null);
	if (srcElement.tagName == 'A') {
		content = srcElement.parentElement.getElementsByClassName('ct-dropdown-menu')[0];
	} else {
		content = srcElement.parentElement.parentElement.getElementsByClassName('ct-dropdown-menu')[0];
	}
	if (!content) {
		return;
	}
	$(content).removeClass('ct-hide');
	$(content).addClass('ct-show');
	showOverlay(content);
}

function hideDown() {
	var downMenu = $('.ct-dropdown-menu');
	var eventData = {
		eventType: "showBrowser",
		operation: "",
		visible: false
	};

	window.sendAdoreMessage(eventData, null);
	for (var i = 0; i < downMenu.length; i++) {
		var menu = downMenu.get(i);
		$(menu).removeClass('ct-show');
		$(menu).addClass('ct-hide');
		hideOverlay(menu);
	}
}

function dropup() {
	var content = null;
	if (event.srcElement.tagName == 'A') {
		content = event.srcElement.parentElement.getElementsByClassName('ct-dropup-menu')[0];
	} else {
		content = event.srcElement.parentElement.parentElement.getElementsByClassName('ct-dropup-menu')[0];
	}
	if (!content) {
		return;
	}
	var classStr = content.className;
	if (classStr.indexOf('ct-show') >= 0) {
		hideUp();
	} else {
		showUp();
	}
}

function showUp() {
	var content = null;
	if (event.srcElement.tagName == 'A') {
		content = event.srcElement.parentElement.getElementsByClassName('ct-dropup-menu')[0];
	} else {
		content = event.srcElement.parentElement.parentElement.getElementsByClassName('ct-dropup-menu')[0];
	}
	if (!content) {
		return;
	}
	var eventData = {
		eventType: "showBrowser",
		operation: "",
		visible: true
	};

	window.sendAdoreMessage(eventData, null);
	$(content).removeClass('ct-hide');
	$(content).addClass('ct-show');
	showOverlay(content);
}

function hideUp() {
	var downMenu = $('.ct-dropup-menu');
	var eventData = {
		eventType: "showBrowser",
		operation: "",
		visible: false
	};

	window.sendAdoreMessage(eventData, null);
	for (var i = 0; i < downMenu.length; i++) {
		var menu = downMenu.get(i);
		$(menu).removeClass('ct-show');
		$(menu).addClass('ct-hide');
		hideOverlay(menu);
	}
}

function openContent(fileName) {
	$.get(fileName, function (data) {
		$('#ct-content').html(data);
	});
}

function checkTime(i) { //补位处理
	if (i < 10) {
		i = "0" + i; //当秒分小于10时，在左边补0；
	}
	return i;
}

function showDropdownTooltip(event) {
	var toolTip = null;
	if (event.srcElement.tagName == 'A') {
		toolTip = event.srcElement.parentElement.getElementsByClassName('ct-dropdown-tooltip');
	} else {
		toolTip = event.srcElement.parentElement.parentElement.getElementsByClassName('ct-dropdown-tooltip');
	}
	if (!toolTip[0]) {
		return;
	}
	$(toolTip[0]).removeClass('ct-hide');
	$(toolTip[0]).addClass('ct-show');
}

function hideDropdownTooltip(event) {
	var toolTip = null;
	if (event.srcElement.tagName == 'A') {
		toolTip = event.srcElement.parentElement.getElementsByClassName('ct-dropdown-tooltip');
	} else {
		toolTip = event.srcElement.parentElement.parentElement.getElementsByClassName('ct-dropdown-tooltip');
	}
	if (!toolTip[0]) {
		return;
	}
	$(toolTip[0]).removeClass('ct-show');
	$(toolTip[0]).addClass('ct-hide');
}

function showDropupTooltip(event) {
	var toolTip = null;
	if (event.srcElement.tagName == 'A') {
		toolTip = event.srcElement.parentElement.getElementsByClassName('ct-dropup-tooltip');
	} else {
		toolTip = event.srcElement.parentElement.parentElement.getElementsByClassName('ct-dropup-tooltip');
	}
	if (!toolTip[0]) {
		return;
	}
	$(toolTip[0]).removeClass('ct-hide');
	$(toolTip[0]).addClass('ct-show');
}

function hideDropupTooltip(event) {
	var toolTip = null;
	if (event.srcElement.tagName == 'A') {
		toolTip = event.srcElement.parentElement.getElementsByClassName('ct-dropup-tooltip');
	} else {
		toolTip = event.srcElement.parentElement.parentElement.getElementsByClassName('ct-dropup-tooltip');
	}
	if (!toolTip[0]) {
		return;
	}
	$(toolTip[0]).removeClass('ct-show');
	$(toolTip[0]).addClass('ct-hide');
}

function showTime() {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth() + 1; //js获取的月份是从0开始；
	var day = now.getDate();
	var h = now.getHours();
	var m = now.getMinutes();
	var s = now.getSeconds();
	m = checkTime(m);
	s = checkTime(s);
	setTimeout(showTime, 500);
	$("#showTime .t").html("  " + h + ":" + m + ":" + s);
}

function initTaskNum() {
	var eventData = {
		'eventType': 'task',
		'operation': 'getTaskNum'
	};
	window.sendAdoreMessage(eventData, function (data) {
		var jsonData = jQuery.parseJSON(data);
		setTodoTaskNum(jsonData.num1);
		setHandlingTaskNum(jsonData.num2);
		setFinishedTaskNum(jsonData.num3);
		setInterruptTaskNum(jsonData.num4);
		setPrintTaskNum(jsonData.num5);
	});
}

function setTodoTaskNum(num) {
	var todoTaskNum = $("#ct-todoTaskNum");
	todoTaskNum.empty();
	todoTaskNum.html(num);
	if (parseInt(num)) {
		todoTaskNum.removeClass("ct-displayNone");
	}else{
		todoTaskNum.addClass("ct-displayNone");
	}
}
function setPrintTaskNum(num) {
	var PrintTaskNum = $("#ct-printTaskNum");
	PrintTaskNum.empty();
	PrintTaskNum.html(num);
	if (parseInt(num)) {
		PrintTaskNum.removeClass("ct-displayNone");
	}else{
		PrintTaskNum.addClass("ct-displayNone");
	}
}
function setHandlingTaskNum(num) {
	var handlingTaskNum = $("#ct-handlingTaskNum");
	handlingTaskNum.empty();
	handlingTaskNum.html(num);
	if (parseInt(num)) {
		handlingTaskNum.removeClass("ct-displayNone");
	}else{
		handlingTaskNum.addClass("ct-displayNone");
	}
}

function setFinishedTaskNum(num) {
	var finishedTaskNum = $("#ct-finishedTaskNum");
	finishedTaskNum.empty();
	finishedTaskNum.html(num);
	if (parseInt(num)) {
		finishedTaskNum.removeClass("ct-displayNone");
	}else{
		finishedTaskNum.addClass("ct-displayNone");
	}
}

function setInterruptTaskNum(num) {
	var interruptTaskNum = $("#ct-interruptTaskNum");
	interruptTaskNum.empty();
	interruptTaskNum.html(num);
	if (parseInt(num)) {
		interruptTaskNum.removeClass("ct-displayNone");
	}else{
		interruptTaskNum.addClass("ct-displayNone");
	}
}

function initTellerInfo() {
	var eventData = {
		'eventType': 'personal',
		'operation': 'tellerInfo'
	};
	window.sendAdoreMessage(eventData, function (data) {
		var jsonData = jQuery.parseJSON(data);
		var tellerInfo = $("#ct-tellerInfo");
		var tellerTlr = $("#tellerTlr");
		var brahNum = $("#tellerBrahNum");
		var showTime = $("#showTime .s")
		tellerInfo.empty();
		tellerTlr.empty();
		brahNum.empty();
		tellerInfo.html(jsonData.tellerName + "-" + jsonData.tellerNo);
		tellerTlr.html(jsonData.brahNm);
		brahNum.html(jsonData.brahNum);
		showTime.html(jsonData.acgDt);
	});
}

function initBusiInfo() {
	var eventData = {
		'eventType': 'busi',
		'operation': 'busiInfo'
	};
	window.sendAdoreMessage(eventData, function (data) {
		var jsonData = jQuery.parseJSON(data);
		setBusiData(jsonData);
	});
}

//调用常用交易查询用于快捷菜单时时更新--gl,这里仅使用data.eventType,operation并不对应
function initFavoriteTran() {
	console.log("刷新快捷菜单调用lfc");
	var eventData = {
		'eventType': 'favoriteTran',
		'operation': ''
	};
	
	window.sendAdoreMessage(eventData, function (data) {
		console.log(data);
	});
}	

function setBusiData(data) {
	if (data.Num3) {
		$('#ct-busiNum-div').html(data.Num3);
		$('#ct-custom-busiNum').html(data.Num3);
	}
	if (data.Num) {
		$('#ct-custNum-div').html(data.Num);
		$('#ct-custom-custNum').html(data.Num);
	}
	if (data.Flow1) {
		$('#ct-custom-flow').html(data.Flow1);
	}
}

function initFavoriteMenu(data) {
	sessiondata=data;
	closeLoading();
	console.log("初始化刷新快捷菜单");
	console.log(sessiondata);
	var menuContainer = $('#ct-commonMenu-Data');
	menuContainer.empty();
	for (var i = 0; i < data.length; i++) {
		var taga = $('<a>', {
			'class': 'ct-menu-item',
			'menudata': data[i][0] + '@' + data[i][1] + '@' + data[i][4]
		});
		var tagimg = $('<img>', {
			'src': '../../workspace/FCBank/resources/image/' + data[i][3]
		});
		var tagspan = $('<span>');
		tagspan.html(data[i][1]);
		taga.append(tagimg);
		taga.append(tagspan);
		menuContainer.append(taga);
		if (i >= 23) {
			break;
		}
	}

	var taga = $('<a>', {
		'class': 'ct-menu-item',
		'menudata': '102213@常用交易维护@/FCBank/trade/Trade/PB/AU/t010513/T010513.tad'
	});
	var tagimg = $('<img>', {
		'src': '_img/menu.png'
	});
	var tagspan = $('<span>');
	tagspan.html('常用交易维护');
	taga.append(tagimg);
	taga.append(tagspan);
	menuContainer.append(taga);

	$('.ct-menu-item').click(function (event) {
		var menuItem;
		if (event.target != 'A') {
			menuItem = event.target.parentElement;
		} else {
			menuItem = event.target;
		}
		var data = $(menuItem).attr('menuData');
		
		openTrade(data);
	});
}

function setWorkTask(data) {
	var tasklist = $('#todo-tasklist');
	tasklist.empty();
	for (var i = 0; i < data.length; i++) {
		var tagItem = $('<div>', {
			'class': 'todo-tasklist-item todo-tasklist-item-border-green',
			'taskId': data[i].TaskId
		});

		var tagTitle = $('<div>', {
			'class': 'todo-tasklist-item-title'
		});
		tagTitle.html(data[i].TaskName);
		tagItem.append(tagTitle);

		var tagText = $('<div>', {
			'class': 'todo-tasklist-item-text'
		});
		tagText.html(data[i].TaskContent);
		tagItem.append(tagText);

		var tagStatus = $('<div>', {
			'class': 'todo-tasklist-controls todo-pull-left'
		});

		var tagDate = $('<span>', {
			'class': 'todo-tasklist-date'
		});
		var tagIcon = $('<i>', {
			'class': 'fa fa-calendar'
		});
		tagDate.append(tagIcon);
		tagDate.append(data[i].TaskTime);
		tagStatus.append(tagDate);

		var prio = '';
		var className = '';

		if (data[i].PriorOption == 1) {
			prio = '高';
			className = 'todo-tasklist-badge-red todo-badge badge-roundless';
		} else if (data[i].PriorOption == 2) {
			prio = '中';
			className = 'todo-tasklist-badge-yellow todo-badge badge-roundless';
		} else if (data[i].PriorOption == 3) {
			prio = '低';
			className = 'todo-tasklist-badge-green todo-badge badge-roundless';
		}

		var tagPrio = $('<span>', {
			'class': className
		});
		tagPrio.html(prio);
		tagStatus.append(tagPrio);
		tagItem.append(tagStatus);

		tasklist.append(tagItem);
	}

	$('.todo-tasklist-item').click(function (event) {
		var taskId = $(event.target).attr("taskId");
		if (!taskId) {
			var elem = $(event.target);
			while (elem.parent()) {
				elem = elem.parent();
				taskId = elem.attr('taskId');
				if (taskId) {
					break;
				}
			}
		}
		var eventData = {
			'eventType': 'workTask',
			'operation': 'handleTask',
			'taskId': taskId
		};
		window.sendAdoreMessage(eventData, null);
	});
}

function initWorkTask() {
	var eventData = {
		'eventType': 'workTask',
		'operation': 'initWorkTask'
	};
	window.sendAdoreMessage(eventData, function (data) {
		var tasklist = $('#todo-tasklist');
		tasklist.empty();
		var jsonData = jQuery.parseJSON(data);
		setWorkTask(jsonData);
	});
}

function customViewSwitch(switchIndex) {
	switchView(0);
	switch (switchIndex) {
		case 0:
			switchSoftCallAndCustInfo(0);
			break;
		case 1:
			switchSoftCallAndCustInfo(1);
			switchCustInfo(0);
			break;
		case 2:
			switchSoftCallAndCustInfo(1);
			switchCustInfo(1);
			switchCustomTab(0);
			break;
		case 3:
			switchSoftCallAndCustInfo(1);
			switchCustInfo(0);
			switchCustomTab(1);
			break;
		default:
			break;
	}
}

function switchInternalViewTab(switchIndex) {
	var home = $('#home');
	var allMenu = $('#multipanelcomposite_manage');
	if (switchIndex == 0) {
		var homeTab = $('.ct-tab>li>a[value="home"]');
		homeTab.click();
	} else if (switchIndex == 1) {
		var allMenuTab = $('.ct-tab>li>a[value="multipanelcomposite_manage"]');
		allMenuTab.click();
	}

}

function switchView(switchIndex) {
	if (switchIndex == 0) {
		var customView = $('.sidebarItem[data="ct-content-custom"]');
		customView.click();
	} else if (switchIndex == 1) {
		var internalView = $('.sidebarItem[data="ct-content-internal"]')
		internalView.click();
	}
}

function switchSoftCallAndCustInfo(switchIndex) {
	var customViews = $('.ct-customView>div');
	customViews.each(function (index) {
		$(this).removeClass('ct-display');
		$(this).addClass('ct-displayNone');

		if (switchIndex == index) {
			$(this).removeClass('ct-displayNone');
			$(this).addClass('ct-display');
		}
	});
}

function switchCustInfo(switchIndex) {
	var customInfo = $('#ct-custom-info-div>div');
	customInfo.each(function (index) {
		if (this.id != 'ct-custom-button') {
			$(this).removeClass('ct-display');
			$(this).addClass('ct-displayNone');
		}

		if (switchIndex == index) {
			$(this).removeClass('ct-displayNone');
			$(this).addClass('ct-display');
		}
	});
}

function switchCustomShowInfo(switchIndex) {
	var customShowInfo = $('#ct-custom-info>div');
	customShowInfo.each(function (index) {
		if (switchIndex == index) {
			$(this).removeClass('ct-displayNone');
			$(this).addClass('ct-display');
		} else {
			$(this).removeClass('ct-display');
			$(this).addClass('ct-displayNone');
		}
	});
}

//个人对公换页处理--gl 
$('#ct-custom-info-div>div>div>ul>li>a').click(function (event) {
	    if('ct-custom-personal'==this.getAttribute("value")){
	    	console.log("进入个人");
	    	
	    	$("#ct-custom-personal").removeClass('ct-displayNone');
 			$("#ct-custom-personal").addClass('ct-display');
 			$("#ct-custom-company").removeClass('ct-display');
 			$("#ct-custom-company").addClass('ct-displayNone');
 			document.getElementById("customMode1").focus();
	    }else{
	    	console.log("进入对公");
	    	
	    	$("#ct-custom-personal").removeClass('ct-display');
	    	$("#ct-custom-personal").addClass('ct-displayNone');
	    	$("#ct-custom-company").removeClass('ct-displayNone');
	    	$("#ct-custom-company").addClass('ct-display');
	    	document.getElementById("customNumInput").focus();
	    }
	});

//点击客户服务界面时，个人初始化显示处理--gl
function switchPCTab(switchIndex) {
	console.log('切换tab页');
	document.getElementById("customMode1").focus();
	var tabItem;
	switch (switchIndex) {
		case 0:
			tabItem = getElementByAttributeValue(".ct-tab>li>a", "value", "ct-custom-personal");
			break;
		case 1:
			tabItem = getElementByAttributeValue(".ct-tab>li>a", "value", "ct-custom-company");
			break;
	}
	if (tabItem) {
		tabItem.click();
//		switchPCInfo(switchIndex);
	}
}

function switchCustomTab(switchIndex) {
	var tabItem;
	switch (switchIndex) {
		case 0:
			tabItem = getElementByAttributeValue(".ct-tab>li>a", "value", "ct-custom-info");
			break;
		case 1:
			tabItem = getElementByAttributeValue(".ct-tab>li>a", "value", "ct-account-info");
			break;
		case 2:
			tabItem = getElementByAttributeValue(".ct-tab>li>a", "value", "ct-financial-info");
			break;
	}
	if (tabItem) {
		tabItem.click();
	}
}

function switchSoftCallTab(switchIndex) {
	if (switchIndex == 0) {
		$('#bindWindow').removeClass('ct-displayNone');
		$('#bindWindow').addClass('ct-display');

		$('#softCallFunc').addClass('ct-displayNone');
		$('#softCallFunc').removeClass('ct-display');
	} else if (switchIndex == 1) {
		$('#bindWindow').removeClass('ct-display');
		$('#bindWindow').addClass('ct-displayNone');

		$('#softCallFunc').addClass('ct-display');
		$('#softCallFunc').removeClass('ct-displayNone');
	}
}

function initSoftCall() {
	setSoftCallEnable(2, false);
	setSoftCallEnable(5, false);
}

function setSoftCallEnable(index, enable) {
	var elem = getElementByAttributeValue('.ct-softCallButton', 'type', index);
	setButtonEnable(elem, enable);
}

function setElementEnable(targetElement, enable) {
	targetElement.attr('disabled', enable);
	if (!enable) {
		targetElement.addClass('ct-disabled');
	} else {
		targetElement.removeClass('ct-disabled');
	}
}

function setButtonEnable(targetElement, enable) {
	targetElement.children().each(function (index) {
		if (this.tagName == 'IMG') {
			if (!enable) {
				$(this).addClass('ct-disabled-backgroundcolor');
			} else {
				$(this).removeClass('ct-disabled-backgroundcolor');
			}
		} else {
			if (!enable) {
				$(this).addClass('ct-disabled-color');
			} else {
				$(this).removeClass('ct-disabled-color');
			}
		}
	});

	setElementEnable(targetElement, enable);
}

function getElementByAttributeValue(selector, attributeName, attributeValue) {
	var element;
	$(selector).each(function (index) {
		var value = $(this).attr(attributeName);
		if (value == attributeValue) {
			element = $(this);
			return;
		}
	});
	return element;
}

function initCustomData(data) {
//	switchSoftCallAndCustInfo(1);
//	switchCustInfo(1);
//	switchCustomTab(0);
//	setSoftCallData(data);
	setCustomData(data);
}

function setSoftCallData(data) {
	if (data.QueueNo) {
		$('#ct-queueNo').html(data.QueueNo);
	}

	if (data.WaitNum) {
		$('#ct-waitNum').html(data.WaitNum);
	}
}

function setCustomData(data) {
	console.log(data);
	var cstTp = '1';
	if (data.CstTp) {
		cstTp = data.CstTp;
	}
	if (cstTp == '1') {
		$('#customMode1').val('');
		$('#certNum').val('');
		$('#selectMode').val('');
		$('#voucherType').val('');
		$('#inputType2').val('');
		$('#accountInput').val('');
		document.getElementById('voucherType').disabled=false;
    	document.getElementById("inputType2").disabled=false;
		console.log("打开个人客户界面");
		var cstNum = '';
		if (data.CstNum) {
			cstNum = data.CstNum;
			console.log(cstNum);
		}
		$('#ct-show-cstNum').html(cstNum);

		var ctfTp = '';
		if (data.CtfTp) {
			ctfTp = data.CtfTp;
			ctfTp = ctfTp+"-"+certType[ctfTp];
		}
		$('#ct-show-pcftTp').html(ctfTp);

		var ctfNum = '';
		if (data.CtfNum) {
			ctfNum = data.CtfNum;
		}
		$('#ct-show-pctfNum').html(ctfNum);

        var bank = '';
		if (data.BrcCode) {
			bank = data.BrcCode;
		}
		$('#ct-show-bank').html(bank);
		
		var cstNm = '';
		if (data.CstNm) {
			cstNm = data.CstNm;
		}
		$('#ct-show-pcstNm').html(cstNm);
        
        var pcstMaNm = '';
		if (data.CustMngNo) {
			pcstMaNm = data.CustMngNo;
		}
		$('#ct-show-pcstMaNm').html(pcstMaNm);
        
		var phone = '';
		if (data.SerialNum) {
			phone = data.SerialNum;
		}
		$('#ct-show-phone').html(phone);
        
        var IDEDate = '';
		if (data.CstIDEDate){
			IDEDate = data.CstIDEDate;
		}
		$('#ct-show-pcstdt').html(IDEDate);
        
		var invalidDate = '';
		if (data.CtfEDate){
			invalidDate = data.CtfEDate;
		}
		$('#ct-show-pcstdt2').html(invalidDate);
        
        var pcstLevel = '';
		if (data.GlCustomClass1) {
			pcstLevel = data.GlCustomClass1;
			pcstLevel = pcstLevel + '-'+ custType[pcstLevel];
		}			
		$('#ct-show-pcstLevel').html(pcstLevel);
        
		var cgy = '';
		if (data.Cgy) {
			cgy = data.Cgy;
			cgy = cgy+'-'+serverLevel[cgy];
		}
			
		$('#ct-show-cgy').html(cgy);

//		var ctcAdr = '';
//		if (data.CtcAdr) {
//			ctcAdr = data.CtcAdr;
//		}
//		$('#ct-show-ctcAdr').html(ctcAdr);
        
        var checkResident = '';
		if (data.checkResident) {
			checkResident = data.checkResident;
			checkResident = checkResident+'-'+chkResident[checkResident];
		}
		$('#ct-show-checkResident').html(checkResident);
        
        var vouType = '';
		if (data.cstAccType1) {
			vouType = data.cstAccType1
			vouType = data.cstAccType1 + '-' + accNoType[vouType];
		}
		$('#ct-show-cstAccType1').html(vouType);
		
        var vouNo = '';
		if (data.vouNo) {
			vouNo = data.vouNo;
		}
		$('#ct-show-vouNo').html(vouNo);
        
		var checkStatus = '已录入';
/*************************gyx，联网核查相关***20180531/20:27**********************************************************/		
		if (!data.ChkRslt) {
			checkStatus = '未录入';
			$('#ct-show-checkStatus').html(checkStatus);
	        $('#idCheck').removeClass('chkCutomModifyButton');
     	    $('#idCheck').addClass('cutomModifyButton');	
     	    document.getElementById("IdPhoto").src = "_img/证件头像.bmp";
     	    document.getElementById("IdCheckPhoto").src = "_img/证件头像.bmp";		
		}else{
			$('#idCheck').addClass('chkCutomModifyButton');
     	    $('#idCheck').removeClass('cutomModifyButton');
		}
/**************************end************************************************************/		
		$('#ct-show-checkStatus').html(checkStatus);
		
		var Bal = '';
		if (data.Bal) {
			Bal = data.Bal;
		}
		$('#ct-show-Bal').html(Bal);
		
		var Stat = '';
		if (data.Stat=='1') {
			Stat = data.Stat;
			Stat = Stat+'-'+ctpStatus[Stat];
		}
		$('#ct-show-Stat').html(Stat);
		
		var InputMode='';
        if (data.InputMode){
        	InputMode = data.InputMode;
			InputMode = InputMode+'-'+inputMode[InputMode];
        }
        $('#ct-show-Mode').html(InputMode);
        
        if (data.tradeFlag=='1'){
        	console.log("交易更新");
        }else{
        	$('#ct-show-checkStatus').val('');
	        $('#ct-show-checkStatus').html('未录入');
	        $('#idCheck').removeClass('chkCutomModifyButton');
     	    $('#idCheck').addClass('cutomModifyButton');
     	    document.getElementById("IdPhoto").src = "_img/证件头像.bmp";
     	    document.getElementById("IdCheckPhoto").src = "_img/证件头像.bmp";
        
            $('#ct-show-agentStatus').val('');
	        $('#ct-show-agentStatus').html('未录入');
	        $('#agentInfo').removeClass('chkCutomModifyButton');
     	    $('#agentInfo').addClass('cutomModifyButton');
     	    
        }
        
        showCustomerView();
        var datestr=invalidDate.replace(/-/g,'/');
        var iddatestr=IDEDate.replace(/-/g,'/');
        var showTime = document.getElementById('chkDate').textContent;
        var chkdatestr=showTime.replace(/-/g,'/');
	    var now = new Date(chkdatestr);//过期判断
//	    var earlyday=now.setDate(now.getDate() +30);//提前30天
	    var ctfDate= new Date(datestr);
	    var idEDate= new Date(iddatestr);
	    if(iddatestr){
	    if(now>idEDate){
	    	document.getElementById('ct-show-pcstdt').style.color="red";
            alert("证件已过期");	
            console.log("对私身份证过期");
        }else if((now.setDate(now.getDate() +30))>idEDate){
	    	document.getElementById('ct-show-pcstdt').style.color="red";
            alert("证件即将过期");	
        }else{}
        }
	    if(now>ctfDate){
	    	document.getElementById('ct-show-pcstdt2').style.color="red";
            alert("系统证件已过期");
            console.log("对私过期");	
        }else if((now.setDate(now.getDate() +30))>ctfDate){
	    	document.getElementById('ct-show-pcstdt2').style.color="red";
            alert("系统证件即将过期");	
        }else{}
	       
//		switchCustomShowInfo(0);
// 对公赋值按照html界面顺序
	} else {
		$('#customNumInput').val('');
		$('#customNumInput1').val('');
		console.log("打开对公客户界面");
		var cstNum = '';
		if (data.CstNum) {
			cstNum = data.CstNum;
			console.log(cstNum);
		}
		$('#ct-show-gcstNum').html(cstNum);
        
        var cstNm = '';
		if (data.CstNm) {
			cstNm = data.CstNm;
		}
		$('#ct-show-gcstNm').html(cstNm);
		
		var bank = '';
		if (data.BrcCode) {
			bank = data.BrcCode;
		}
		$('#ct-show-gbank').html(bank);
		
		//4.4账户状态=>账户性质新增字段
		var AccStat = '';
		if (data.AccStat) {
			AccStat = data.AccStat;
			AccStat = AccStat + '-' + acctPry[AccStat];
		}			
		$('#ct-show-gctq').html(AccStat);
		
		var gcstNaty = '';
		if (data.NationCode) {
			gcstNaty = data.NationCode;
			gcstNaty = gcstNaty+'-'+nationCode[gcstNaty];
		}
			
		$('#ct-show-gcstNaty').html(gcstNaty);
		
		var cgy = '';
		if (data.Cgy) {
			cgy = data.Cgy;
			cgy = cgy+'-'+serverLevel[cgy];
		}
			
		$('#ct-show-gcstRtg').html(cgy);
		
		var CtfTp = '';
		if (data.CtfTp) {
			CtfTp = data.CtfTp;
			CtfTp = CtfTp+'-'+gcertType[CtfTp];
		}
			
		$('#ct-show-gCtfTp').html(CtfTp);
		
		var CtfNum = '';
		if (data.CtfNum) {
			CtfNum = data.CtfNum;
		}
			
		$('#ct-show-gctfNum').html(CtfNum);
				
		var invalidDate = '';
		if (data.CtfEDate){
			invalidDate = data.CtfEDate;
			console.log(invalidDate);
		}
		$('#ct-show-gcodedate').html(invalidDate);
	    //法人
		var juidman = '';
		if (data.JurPerNm) {
			juidman = data.JurPerNm;
		}
		$('#ct-show-juidman').html(juidman);
		var ctfTp = '';
		if (data.JPerType) {
			ctfTp=data.JPerType;
			ctfTp= ctfTp + '-' + certType[ctfTp];
		}			
//		ctfTp = getCertTypeFullName(cstTp, ctfTp);
		$('#ct-show-gcsttp').html(ctfTp);
<!-- *************************高艺祥 2018-05-03/22:49增加***************************** -->		
		//alert(JSON.stringify(data));
		//证件有效期值
		$('#ct-show-gcstdate').html(data.AcceptanceDate);
<!-- *************************end***************************** -->		
		//证件号码
		var ctfNum = '';
		if (data.JPerCode) {
			ctfNum = data.JPerCode;
		}
		$('#ct-show-gcstno').html(ctfNum);
		
		var checkResident = '';
		if (data.checkResident) {
			checkResident = data.checkResident;
			checkResident = checkResident+'-'+gcheckResident[checkResident];
		}
		$('#ct-show-checkResident1').html(checkResident);
		
		//对公新增介质区 3.19
		
        var vouNo = '';
		if (data.vouNo) {
			vouNo = data.vouNo;
		}
		$('#ct-show-vouNo1').html(vouNo);
        
		var checkStatus = '未核查';
		if (data.ChkRslt) {
			checkStatus = data.ChkRslt;
		}
		$('#ct-show-checkStatus1').html(checkStatus);
		
		var Bal = '';
		if (data.Bal) {
			Bal = data.Bal;
		}
		$('#ct-show-Bal1').html(Bal);
		
		var Stat = '';
		if (data.Stat=='1') {
			Stat = data.Stat;
			Stat = Stat+'-'+ctpStatus[Stat];
		}
		$('#ct-show-Stat1').html(Stat);
		 if (data.tradeFlag=='1'){
        	console.log("交易更新");
        }else{
        	$('#ct-show-checkStatus').val('');
	        $('#ct-show-checkStatus').html('未录入');
	        $('#idCheck1').removeClass('chkCutomModifyButton');
     	    $('#idCheck1').addClass('cutomModifyButton');
        
            $('#ct-show-agentStatus1').val('');
	        $('#ct-show-agentStatus1').html('未录入');
	        $('#agentInfo1').removeClass('chkCutomModifyButton');
     	    $('#agentInfo1').addClass('cutomModifyButton');
        }
		showCompanyView();
		var datestr=invalidDate.replace(/-/g,'/');
		var showTime = document.getElementById('chkDate').textContent;
        var chkdatestr=showTime.replace(/-/g,'/');
	    var now = new Date(chkdatestr);//过期判断
//	    var earlyday=now.setDate(now.getDate() +30);//提前30天
	    var ctfDate= new Date(datestr);
	    if(now==ctfDate||now>ctfDate){
	    	document.getElementById('ct-show-gcodedate').style.color="red";
            alert("系统证件已过期");	
            console.log("对公过期");
        }else if((now.setDate(now.getDate() +30))>ctfDate){
	    	document.getElementById('ct-show-gcodedate').style.color="red";
            alert("系统证件即将过期");	
        }else{}
/*******************高艺祥 2018-05-03/23:57 增加*********************************************************/
		var nowDateInt = parseInt(showTime.replace(/-/g,''));
		var acceptanceDateInt= parseInt(data.AcceptanceDate.replace(/-/g, ''));
		if(nowDateInt==acceptanceDateInt||nowDateInt>acceptanceDateInt){
	    	document.getElementById('ct-show-gcstdate').style.color="red";
            alert("法人证件已过期");	
            console.log("法人证件过期");
        }else if(nowDateInt+30>acceptanceDateInt){
	    	document.getElementById('ct-show-gcstdate').style.color="red";
            alert("法人证件即将过期");	
        }else{}
/**************End*****************************************************************************************/
//		switchCustomShowInfo(1);
	}
}

function initAccountList(data) {
	var accountList = $('#ct-account-list');
	accountList.empty();
	for (var i = 0; i < data.length; i++) {
		var tagContainer = $('<div>');
		tagContainer.css("margin-top", "10px");
		var tagDiv = $('<div>', {
			'class': 'ct-input-div'
		});
		var tagLabel = $('<label>');
		tagLabel.html('账    号:');
		tagDiv.append(tagLabel);

		var tagValue = $('<a>', {
			"class": "ct-account"
		});
		tagValue.html(data[i].SerialNum1);
		tagDiv.append(tagValue);
		tagContainer.append(tagDiv);

		tagDiv = $('<div>', {
			'class': 'ct-input-div'
		});
		tagLabel = $('<label>');
		tagLabel.html('账户分类:');
		tagDiv.append(tagLabel);

		tagValue = $('<label>');
		var contStyle = data[i].Cl;
			switch(contStyle){
				case '1':
				contStyle = 'I 类户';
				break;
				case '2':
				contStyle = 'II 类户';
				break;
				case '3':
				contStyle = 'III 类户';
				break;
			} 
		tagValue.html(contStyle);
		tagDiv.append(tagValue);
		tagContainer.append(tagDiv);

		tagDiv = $('<div>', {
			'class': 'ct-input-div'
		});
		tagLabel = $('<label>');
		tagLabel.html('账户类型:');
		tagDiv.append(tagLabel);
		var countType = data[i].Tp1;
		switch(countType){
				case '1':
				countType = '活期';
				break;
				case '2':
				countType = '定期';
				break;
				case '3':
				countType = '活期保证金';
				break;
				case '4':
				countType = '定期保证金';
				break;
			} 
		tagValue = $('<label>');
		tagValue.html(countType);

		tagDiv.append(tagValue);
		tagContainer.append(tagDiv);

		tagDiv = $('<div>', {
			'class': 'ct-input-div'
		});
		
		tagLabel = $('<label>');
		tagLabel.html('余额:');
		tagDiv.append(tagLabel);

		tagValue = $('<label>');
		tagValue.html(data[i].AmtOfMny);
		tagDiv.append(tagValue);
		tagContainer.append(tagDiv);

		if (data[i].MedmTp) {
			tagDiv = $('<div>', {
				'class': 'ct-input-div'
			});
			tagLabel = $('<label>');
			tagLabel.html('介质:');
			tagDiv.append(tagLabel);

			tagValue = $('<label>', {
				'class': 'ct-mediaType'
			});
			var MedmTp = data[i].MedmTp;
			switch(MedmTp){
				case '0':
				MedmTp = '无凭证';
				case '1':
				MedmTp = '主卡';
				break;
				case '2':
				MedmTp = '联名卡';
				break;
				case '3':
				MedmTp = '附属卡（副卡）';
				break;
				case '4':
				MedmTp = '虚拟卡';
				break;
				case '5':
				MedmTp = '活期存折';
				break;
				case '6':
				MedmTp = '定期一本通';
				break;
				case '7':
				MedmTp = '定期存单';
				break;
				case '8':
				MedmTp = '定期存折';
				break;
				case '9':
				MedmTp = '存款证实书';
				break;
			} 
			tagValue.html(MedmTp);

			tagDiv.append(tagValue);
			tagContainer.append(tagDiv);
		}
		accountList.append(tagContainer);
	}

	$('.ct-account').click(function (event) {
		var eventSource = event.target;
		var account = $(eventSource).text();
		var mediaTypeSource = $(eventSource.parentElement.parentElement).find('.ct-mediaType');
		var accountStyle = $(eventSource.parentElement.parentElement.parentElement).find('.ct-account');
		var mediaType = mediaTypeSource.text();
		if (mediaType) {
			mediaType = mediaType.split('-')[0];
		}
		for (var index = 0; index < accountStyle.length; index++) {
				var element = accountStyle[index];
				$(element).css('color','#00AAFF');
			}
		$(eventSource).css('color','#E81C1C');
		$('#currentAccount').html(account);

		var eventData = {
			'eventType': 'custom',
			'operation': 'switchAccount',
			'account': account,
			'mediaType': mediaType
		};
		window.sendAdoreMessage(eventData, null);
	});
}

function setSubmitButtonText(text) {
	$('#submitButton').val(text);
}

function setSubmitButtonEnable(enable) {
	$('#submitButton').attr('disabled', !enable);
}

function initExpandableText(data) {
	console.log("交易搜索框初始化");
	tradeInfo = data;
	var items = new Array();
	for (var i = 0; i < data.length; i++) {
		items[i] = data[i].TRANCODE + " " + data[i].TRANNAME;
	}
    console.log(items);
	$('.adore-expandableText').setItems(items);
}

//证件类型枚举转换  4.9新增 gl  打印拼头要求增加
function getCertTypeFullName(data) {
	var certTypeFullName;
	var cstCertType;
	if (data.cstType == '1') {
		cstCertType = certType;
	} else {
		cstCertType = gcertType;
	}
	if(data.certType){
//      cstCertType = data.certType + "-" + cstCertType[data.certType];
        cstCertType = cstCertType[data.certType];
	}else{
		console.log("enumjson串缺失关键字");
	}
	if(cstCertType==null||cstCertType==""){
		cstCertType="";
	}
	var eventData = {
		'eventType': 'enum',
		'operation': 'certTypeConvert',
		'fullname': cstCertType
	};
    console.log(cstCertType);
	window.sendAdoreMessage(eventData, null);

}

//clearAcctNoData  清理账号信息  4.9 新增需求清理账号信息
function clearAcctNoData(data) {
	if(data.CstTp){
		if(data.CstTp=='1'){
			$('#ct-show-cstAccType1').html('');
			$('#ct-show-vouNo').html('');
			$('#ct-show-Bal').html('');
			$('#ct-show-Stat').html('');
		}else{
			$('#ct-show-vouNo1').html('');
			$('#ct-show-gctq').html('');
			$('#ct-show-Bal1').html('');
			$('#ct-show-Stat1').html('');
		}
	}
}

function initCustomInfo() {
	$('#customType').val('');
	$('#certType').val('');
	$('#certNum').val('');
	$('#customNumInput').val('');
	$('#voucherType').val('');
	$('#inputType').val('');
	$('#accountInput').val('');
	$('#inputCustomType2').val('');
	$('#customNum').val('');
	$('#currentAccount').html('');
}

function initCustom(data){
	switchCustInfo(0);
	switchSoftCallAndCustInfo(1);
	setSoftCallData(data);
	if(data){
		var customType = data.cstTp;
		if(customType == '01'){
			$('#customType').val('1');
			$('#inputCustomType2').val('1');
		}else{
			$('#customType').val('2');
			$('#inputCustomType2').val('2');
		}
		var certType = data.ctfTp;
		if(certType == '01'){
			$('#certType').val('10101');

		}else{
			$('#certType').val('10102');
		}
		var certNum = data.cstNum;
		if(certNum){
			$('#certNum').val(certNum);
		}else{
			$('#certNum').val('');
		}
		var customNumInput = data.cstNm;
		if(customNumInput){
			$('#customNumInput').val(customNumInput);
		}else{
			$('#customNumInput').val('');
		}
		var voucherType = data.voucherType;
		if(voucherType){
			$('#voucherType').val(voucherType);
		}else{
			$('#voucherType').val('1');
		}
		var inputType = data.inputType;
		if(inputType){
			$('#inputType').val(inputType);
		}else{
			$('#inputType').val('3');
		}
		var accountInput =  data.accountInput;
		if(accountInput){
			$('#accountInput').val(accountInput);
		}else{
			$('#accountInput').val('');
		}
		var customNum = data.customNum;
		if(customNum){
			$('#customNum').val(customNum);
		}else{
			$('customNum').val('');
		}
	}
}
function initBar(data) {
	var myChart = echarts.init(document.getElementById('chart'));
	myChart.setOption({

		title: {
			text: '',
			subtext: '',
			x: 'center'
		},
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		color:['#36B98A','#1B8E9D','#0D6DB2'],
		calculable: true,
		series: [{
			name: '账户信息',
			type: 'pie',
			radius: '40%',
			center: ['50%', '40%'],
			
			data: [{
				value: 10000,
				name: '活期',
				color:'#36B98A'
			},
			{
				value: 20000,
				name: '定期',
				color:'#1B8E9D'
			},
			{
				value: 50000,
				name: '理财',
				color:'#0D6DB2'
			}
			]
		}]

	});
}

//平台新增加载中插件，lfc流程增加主题url动态图。暂废--4.2
function closeLoading() {
//  document.getElementById('loading').style.visibility='hidden';
}


function closeDiv(container) {
	$('#' + container).removeClass('ct-display');
	$('#' + container).addClass('ct-displayNone');
}

function getfocus() {
	$(".input-field input").focus();
}

function removeRecall(){
	$("#cthotkeystop").removeClass("ct-callagin");
	$("#cthotkeytrans").removeClass("ct-callagin");
}
function addRecall(){
	$("#cthotkeystop").addClass("ct-callagin");
	$("#cthotkeytrans").addClass("ct-callagin");
}

function setCustomView(index){
	if (index == 0) {
		var customView = $('.sidebarItem[data="ct-content-custom"]');
		var internalView = $('.sidebarItem[data="ct-content-internal"]');
		customView.addClass('ct-displayNone');
		internalView.addClass('ct-displayNone');
	} else if (index == 1) {
		var internalView = $('.ct-tab>li>a[value="multipanelcomposite_manage"]');
		internalView.css('display','none');
	}
}

function showOverlay(floatPanel){
	var view = document.getElementById('ct-content-internal');
	var multiTradeComposite = document.getElementById('multipanelcomposite_manage');
	if(view.className.indexOf('ct-displayNone') >= 0){
		multiTradeComposite = document.getElementById('multipanelcomposite_custom');
	}
	var x = $(floatPanel).offset().left;
	var width = $(floatPanel).width();
	var height = $(floatPanel).height();
	var frameX = $(multiTradeComposite).offset().left;
	var frameY = $(multiTradeComposite).offset().top;
	window.sendAdoreMessage({'type':'showOverlay', 'id': multiTradeComposite.id,'overlayId':'overlay','x':x,'y':y,'width':width,'height':height,'frameX':frameX,'frameY':frameY},null);
}

function hideOverlay(floatPanel){
	var view = document.getElementById('ct-content-internal');
	var multiTradeComposite = document.getElementById('multipanelcomposite_manage');
	if(view.className.indexOf('ct-displayNone') >= 0){
		multiTradeComposite = document.getElementById('multipanelcomposite_custom');
	}
	window.sendAdoreMessage({'type':'hideOverlay', 'id':multiTradeComposite.id,'overlayId':'overlay'},null);
}

function openTrade(tradeData) {
	var eventData = {
		'eventType': 'menu',
		'operation': 'openTrade',
		'menuData': tradeData
	};
	window.sendAdoreMessage(eventData, null);
}

//个人账号进入页面切换
function showCustomerView() {
    $('.ct-customInputInfo').removeClass('ct-display');
    $('.ct-customInputInfo').addClass('ct-displayNone');
    $('.ct-customShowInfo').removeClass('ct-displayNone');
    $('.ct-customShowInfo').addClass('ct-display');
    $('#ct-custom-info').removeClass('ct-displayNone');
    $('#ct-custom-info').addClass('ct-display');
    $('#ct-financial-info').removeClass('ct-display');
    $('#ct-financial-info').addClass('ct-displayNone');
}

//对公账号进入页面切换
function showCompanyView(){
        $('.ct-customInputInfo').removeClass('ct-display');
        $('.ct-customInputInfo').addClass('ct-displayNone');
        $('.ct-customShowInfo').removeClass('ct-displayNone');
        $('.ct-customShowInfo').addClass('ct-display');
        $('#ct-custom-info').removeClass('ct-display');
        $('#ct-custom-info').addClass('ct-displayNone');
        $('#ct-financial-info').removeClass('ct-displayNone');
        $('#ct-financial-info').addClass('ct-display');
}

//客户信息查询方式改变事件	
function ChangeSelectMode(){
	console.log("下拉改变测试");
    if($('#selectMode').val()==0){	
    	document.getElementById('voucherType').disabled=true;  	
    	document.getElementById("inputType2").options[1].selected=true;
    	document.getElementById("inputType2").disabled=true;
    	document.getElementById('voucherType').options[0].selected=true;
    	document.getElementById('accountInput').focus();
    }else{
    	document.getElementById('voucherType').disabled=false;
    	document.getElementById('voucherType').options[1].selected=true;
    	document.getElementById("inputType2").disabled=false;
    	document.getElementById("inputType2").options[2].selected=true;
    }
}	

//证件种类改变事件
function ChangeVoucherType(){
    if($('#voucherType').val()=="01"){	
    	document.getElementById("inputType2").disabled=false;
    	document.getElementById("inputType2").options[2].selected=true;
    	if($('#inputType2').val()=="1"){	
	        var eventData = {
				'eventType': 'custom',
				'operation': 'selectInput2',
				'inputMode':'1'
			};
			window.sendAdoreMessage(eventData, null);
		}
/*********************高艺祥 2018-05-12/14:54新增**********************/  		
    }else if($('#voucherType').val()=="10"){
    	document.getElementById("inputType2").disabled=false;
    	document.getElementById("inputType2").options[2].selected=true;
    	if($('#inputType2').val()=="1"){	
	        var eventData = {
			'eventType': 'custom',
		    'operation': 'selectInput2',
			'inputMode':'10'
			};
			window.sendAdoreMessage(eventData, null);
		}
/********************************结束******************************/ 		
    }else{
    	document.getElementById("inputType2").options[1].selected=true;
    	document.getElementById("inputType2").disabled=true;
    }
}	

//选择输入方式判断是否调用外设
function ChangeCustomMode1(){
    $('#certNum').val("");
}
//对私截至信息账号点击事件
function certNumOnFocus(){
	if($('#customMode1').val()=="2"){	
        var eventData = {
		'eventType': 'custom',
	    'operation': 'selectInput',
		'inputMode':'2'
	    };
	    window.sendAdoreMessage(eventData, null);
    }else if($('#customMode1').val()=="6"){
    	var eventData = {
		'eventType': 'custom',
	    'operation': 'selectInput',
		'inputMode':'6'
	    };
	    window.sendAdoreMessage(eventData, null);
    }
}
	
//选择输入方式判断是否调用外设
function ChangeInputType2(){
    if($('#inputType2').val()=="1"){	
        var eventData = {
		'eventType': 'custom',
	    'operation': 'selectInput2',
/*********************高艺祥 2018-05-12/14:54修改***原'inputMode':'1'*******************/   
		'inputMode':$('#voucherType').val()=="10"?"10":"1"
/*********************结束**********************/		
	};
	window.sendAdoreMessage(eventData, null);
    }else {
    	console.log("手工录入");
        $('#accountInput').val('');
        document.getElementById("IdPhoto").src = "_img/证件头像.bmp";
    }
}

//刷卡读出卡号赋值
function getCertNumFocus(data) {
      if (data) {
        if (data.AccNum) {
          $('#certNum').val(data.AccNum);
          console.log("账号" + data.AccNum);
        } else {
          $('#certNum').val('');
        }
        if (data.Mode) {
          $('#customMode1').val(data.Mode);
          console.log("输入方式" + data.Mode);
        } else {
          $('#customMode1').val('');
          console.log("输入方式为空");
        }
        document.getElementById("customQueryButton").focus();
	}
}
	
function getCustomMode1Focus() {
	$('#customMode1').val('0');
	document.getElementById("certNum").disabled=false;
	document.getElementById("customMode1").focus();
}

//刷IC卡磁条卡，刷卡正确，通讯失败后清空卡号、输入方式置空
function initCustomInput() {
	$('#customMode1').val('');
	$('#certNum').val('');
	document.getElementById("certNum").disabled=false;
	document.getElementById("customMode1").focus();
}
	
//刷身份证读出卡号赋值
function setIDInfo(data) {
      if (data) {
        if (data.CtfNum) {
          $('#accountInput').val(data.CtfNum);
          console.log("身份证号" + data.CtfNum);
        }else{
          $('#accountInput').val('');
        }
        if (data.IDPhoto) {
          document.getElementById("IdPhoto").src = 'data:image/png;base64,'+data.IDPhoto;
        }
        document.getElementById("customQueryButton1").focus();
      }
}

function getinputType2Focus() {
	$('#inputType2').val('');
	document.getElementById("inputType2").focus();
}

function initIDInfo() {
    $('#accountInput').val('');
    document.getElementById("IdPhoto").src = "_img/证件头像.bmp";
}	
	
function initagentInfo(data) {
	if(data.AgntMark == "1"){
		$('#ct-show-agentStatus').val('');
		$('#ct-show-agentStatus').html('已录入');
		$('#ct-show-agentStatus1').val('');
		$('#ct-show-agentStatus1').html('已录入');
		$('#agentInfo').removeClass('cutomModifyButton');
		$('#agentInfo').addClass('chkCutomModifyButton');
		$('#agentInfo1').removeClass('cutomModifyButton');
		$('#agentInfo1').addClass('chkCutomModifyButton');
	}else{
		$('#ct-show-agentStatus').val('');
		$('#ct-show-agentStatus').html('未录入');
		$('#agentInfo').addClass('cutomModifyButton');
		$('#agentInfo').removeClass('chkCutomModifyButton');
	}
	  
//	document.getElementById('accountInput').style.background-color="red";
}

/****************高艺祥2018-06-04/14:13 增加**更新验印标志*****************************************/	
function updateCheckSeal(data){
	//改变验印标志label标签的标签体
	var arr = data.checkSealFlag.split(":");
	$('#checkSealLabel').html(arr[1].split("-")[1]);
	if(data.checkSealFlag == "0" || data.checkSealFlag == "1"){
		$('#checkSeal').addClass('chkCutomModifyButton');
		$('#checkSeal').removeClass('cutomModifyButton');
	}else{
		$('#checkSeal').addClass('cutomModifyButton');
		$('#checkSeal').removeClass('chkCutomModifyButton');
	}
}
/********************************结束*******************************************/
	
function chkIDInfo(data) {
	$('#ct-show-checkStatus').val('');
	$('#ct-show-checkStatus').html('已录入');
	$('#idCheck').removeClass('cutomModifyButton');
	$('#idCheck').addClass('chkCutomModifyButton');
	$('#ct-show-checkStatus1').val('');
	$('#ct-show-checkStatus1').html('已录入');
	$('#idCheck1').removeClass('cutomModifyButton');
	$('#idCheck1').addClass('chkCutomModifyButton');
	if (data) {
        if (data.IDChkPhoto) {
          document.getElementById("IdCheckPhoto").src = 'data:image/png;base64,'+data.IDChkPhoto;
        }
        if (data.IDPhoto) {
          document.getElementById("IdPhoto").src = 'data:image/png;base64,'+data.IDPhoto;
        }
        if(data.endDate){
          $('#ct-show-pcstdt').html(data.endDate);
        }
        	
      }  
//	document.getElementById('accountInput').style.background-color="red";
}

//服务结束切换界面	
function ServiceEnd() {
	$('.ct-customInputInfo').addClass('ct-display');
    $('.ct-customInputInfo').removeClass('ct-displayNone');
    $('.ct-customShowInfo').addClass('ct-displayNone');
    $('.ct-customShowInfo').removeClass('ct-display');
/*********************************gyx 2018.06.04/20:21 退出框架后将验印状态还原*******************************/
			$('#checkSealLabel').html("未验印");
/**************************************结束****************************************************************/	    
/**********************gyx 2018.06.04/20:07 退出框架时还原验印标志标签底色 ********************************/     
    $('#checkSeal').addClass('cutomModifyButton');
	$('#checkSeal').removeClass('chkCutomModifyButton');
/****************************结束************************************************************************/	
    document.getElementById("customMode1").focus();
}	

//初始化账号信息
function InitAccountInfo(data) {
	console.log(data);
	if(data.CstTp=="1") {
      var vouNo = '';
      if (data.vouNo) {
        vouNo = data.vouNo;
      }
      $('#ct-show-vouNo').html(vouNo);
      var vouType = '';
      if (data.vouType) {
        vouType = data.vouType;
        vouType = vouType + '-' + accNoType[vouType];
      }
      $('#ct-show-cstAccType1').html(vouType);
      var Bal = '';
      if (data.Bal) {
        Bal = data.Bal;
      }
      $('#ct-show-Bal').html(Bal);

      var Stat = '';
      if (data.Stat=='1') {
        Stat = data.Stat;
        Stat = Stat + '-' + ctpStatus[Stat];
      }
      $('#ct-show-Stat').html(Stat);
      var Mode = '';
      if (data.Mode) {
        Mode = data.Mode;
        Mode = Mode + '-' + inputMode[Mode];
      }
      $('#ct-show-Mode').html(Mode);
    }else if(data.CstTp=="2") {
      var vouNo = '';
      if (data.vouNo) {
        vouNo = data.vouNo;
      }
      $('#ct-show-vouNo1').html(vouNo);
      var AcctPry = '';
      if (data.AcctPry) {
        AcctPry = data.AcctPry;
        AcctPry = AcctPry + '-' + acctPry[AcctPry];
      }
      $('#ct-show-gctq').html(AcctPry);
      var Bal = '';
      if (data.Bal) {
        Bal = data.Bal;
      }
      $('#ct-show-Bal1').html(Bal);

      var Stat = '';
      //介质状态正常不显示，挂失显示
      if (data.Stat=='1') {
        Stat = data.Stat;
        Stat = Stat + '-' + ctpStatus[Stat];
      }
      $('#ct-show-Stat1').html(Stat);
    }else{} 
}

//VT已连接
          function SignIn1(){
 	           document.getElementById("VTconnect1").textContent='VOST连接成功';
                            }
//VT未连接
          function SignIn2(){
 	           document.getElementById("VTconnect1").textContent='VOST连接失败';
                            }  
//VTdiv隐藏                        
          function SignInzhao(){
 	            $('#VTconnect').hide();
                            }                          
//VTclick                         	   
	   $('#VTconnect').click(function () {
		if(document.getElementById('VTconnect1').textContent=='VOST连接成功'){
		var eventData = {
			'eventType': 'custom',
			'operation': 'chenggong5'
		};
		window.sendAdoreMessage(eventData, null);
		}else{
			var eventData = {
			'eventType': 'custom',
			'operation': 'vtwei'
		};
		window.sendAdoreMessage(eventData, null);
		}
	});	
	
//柜员签到	
 function SignIn(){
 	$('#signina').html('柜员签退');
 	$('#sign-ico').removeClass("fa-sign-in");
 	$('#sign-ico').addClass("fa-sign-out");
 	document.getElementById("signInfo").style.display='none';
 	console.log('柜员签到');
 }
 
 //柜员签退
 function SignOut(){
 	$('#signina').html('柜员签到');
 	$('#sign-ico').removeClass("fa-sign-out");
 	$('#sign-ico').addClass("fa-sign-in");
    document.getElementById("signInfo").style.display='none';
 	console.log('柜员签退');
 }	
 
  //现金碰库
 function SignInit(){
 	$('#signina').html('现金碰库');
   	document.getElementById("signInfo").style.display='block';
 	console.log('现金碰库');
 }	

 //消息推送显示--gl
function initWorkMessage(data) {
	    console.log('开始跑屏推送');
	    console.log(data);
	    $("#noticeMessage").html(data);
        $('#marquee').removeClass('ct-displayNone');
	    $('#marquee').addClass('ct-display');
	    setTimeout(function() {
			$('#marquee').removeClass('ct-display');
	        $('#marquee').addClass('ct-displayNone');
		},30000);
}

  //公告推送显示--zk
function initWorkMessag(data) {
	    console.log('开始跑屏推送');
	    console.log(data);
	    $("#noticeMessage").html(data);
        $('#marquee').removeClass('ct-displayNone');
	    $('#marquee').addClass('ct-display');
	    setTimeout(function() {
			$('#marquee').removeClass('ct-display');
	        $('#marquee').addClass('ct-displayNone');
		},100000);
}

 function CustomQueryButton_Entry(){
 	  document.getElementById("selectMode").focus();
 	}
 function SelectMode_Entry(){
 	document.getElementById("voucherType").focus();
 }
 
//停止远程控制台
 function StopRemoteControl(){ 	
 	$('#remoteIco').removeClass("fa-pause");
 	$('#remoteIco').addClass("fa-play");
    $('#remoteName').html("启动远程控制台");
 }
 
 function StartRemoteControl(){	
 	$('#remoteIco').removeClass("fa-play");
 	$('#remoteIco').addClass("fa-pause");
    $('#remoteName').html("停止远程控制台");
 }	

//显示
  function ShowRemoteControl(){
  	RefreshRemoteTasks(); 
 	$('#remoteControl').removeClass("ct-displayNone");
 	$('#remoteControl').addClass("ct-display");
 	$('#ct-remote-task').removeClass("ct-displayNone");
 	$('#ct-remote-task').addClass("ct-display");
 	$('.ct-customInputInfo').removeClass("ct-display");
 	$('.ct-customInputInfo').addClass("ct-displayNone");
 	$('#signin').removeClass("ct-display");
 	$('#signin').addClass("ct-displayNone");
 	$('#cash').removeClass("ct-display");
 	$('#cash').addClass("ct-displayNone");
 }	
 
 //G12、G13不显示远程授权控制台
  function ShowRemoteControl2(){
  	RefreshRemoteTasks(); 
 	$('#ct-remote-task').removeClass("ct-displayNone");
 	$('#ct-remote-task').addClass("ct-display");
 	$('.ct-customInputInfo').removeClass("ct-display");
 	$('.ct-customInputInfo').addClass("ct-displayNone");
 	$('#signin').removeClass("ct-display");
 	$('#signin').addClass("ct-displayNone");
 	$('#cash').removeClass("ct-display");
 	$('#cash').addClass("ct-displayNone");
 }

/* 1.功能需求： 外围系统显示  收到信息显示对应系统菜单
 * 2.约定数据类型："012-外围系统1@013-外围系统2@..."
 * 3.逻辑说明：初始化清空对应div元素块
 *            解析数据
 *            拼接对应界面
 *            注册点击外围系统事件
 *   测试1024*768 页面效果最多放16个 跳出循环
 *    --gl 4.12  新增功能
 */
  function ShowOutSystem(data){
  	if(data.menus){
  		var menulist=data.menus.split("@");
  		console.log(menulist);
  	    var OutSystem=$('#OutSystemList');
  	    OutSystem.empty();
  	    for (var i = 0; i < menulist.length; i++) {
  	    var menunames=menulist[i].split("-");
		var taga = $('<a>', {
			'menuid': menunames[0],
			'class': "ct-runingrisk"
		});
		var tagimg = $('<img>', {
			'src': '_img/trade/icon_out_system' + i+'.png',
			'style': "height:70px;width:70px;"
		});
		var tagspan = $('<span>');
		tagspan.html(menunames[1]);
		taga.append(tagimg);
		taga.append(tagspan);
		OutSystem.append(taga);
		if (i >= 16) {
			break;
		}
	  }
  	    $('.ct-runingrisk').click(function (event) {
		var menuItem;
		if (event.target != 'A') {
			menuItem = event.target.parentElement;
		} else {
			menuItem = event.target;
		}
		var data = $(menuItem).attr('menuid');
		
		OpenOutSystem(data);
	});
  	}
 }

function OpenOutSystem(data) {
	var eventData = {
		'eventType': 'hotKey',
		'operation': 'openOutSystem',
		'menuid': data
	};
	window.sendAdoreMessage(eventData, null);
}

/*
 * 1.公告比较时间戳
 */ 	

//新建客户号成功选择登录界面赋值
 function SelectLogin(data){	
    if(data.CstTp=="1"){ 	
	    $("#ct-custom-personal").removeClass('ct-displayNone');
 	    $("#ct-custom-personal").addClass('ct-display');
 	    $("#ct-custom-company").removeClass('ct-display');
 	    $("#ct-custom-company").addClass('ct-displayNone');
 	    document.getElementById("selectMode").options[1].selected=true;
 	    document.getElementById("voucherType").options[0].selected=true;
    	document.getElementById("voucherType").disabled=true;
 	    document.getElementById("inputType2").options[1].selected=true;
    	document.getElementById("inputType2").disabled=true;
    	$('#accountInput').val(data.CstId);
//  	document.getElementById("customQueryButton1").focus();
	 }else {   	
	    $("#ct-custom-personal").removeClass('ct-display');
	    $("#ct-custom-personal").addClass('ct-displayNone');
	    $("#ct-custom-company").removeClass('ct-displayNone');
	    $("#ct-custom-company").addClass('ct-display');
	    $('#customNumInput1').val(data.CstId);
	 }
 }

//keypress数字控制 --gl 4.16 	
 function onlyNum() { 
    if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39)) 
    if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105))) 
    event.returnValue=false;
} 

/*
 * 授权任务刷新 --gl 4.18
 *   调用接口查询返回刷新任务列表
 */
function RefreshRemoteTasks() {
    var eventData = {
		'eventType': 'RefreshRemoteTasks',
		'operation': ''
	};
	window.sendAdoreMessage(eventData, null);
}

//刷新授权任务界面
function InitRemoteTasks(data) {
	console.log(data);

      var BMSNUM = '';
      if (data.BMSNUM) {
        BMSNUM = data.BMSNUM;
      }
      $('#ct-show-totalTasks').html(BMSNUM);
      var IssueNum1 = '';
      if (data.IssueNum1) {
        IssueNum1 = data.IssueNum1;
      }
      $('#ct-show-passTasks').html(IssueNum1);

      var IssueNum2 = '';
      if (data.IssueNum2) {
        IssueNum2 = data.IssueNum2;
      }
      $('#ct-show-refuseTaseks').html(IssueNum2);
      var IssueNum = '';
      if (data.IssueNum) {
        IssueNum = data.IssueNum;
      }
      $('#ct-show-endTasks').html(IssueNum);
      var Num = '';
      if (data.Num) {
        Num = data.Num;
      }
      $('#ct-show-nopassTasks').html(Num);

}

 function focusNextInput(thisInput) {  
     var inputs = document.getElementsByTagName("input","select");
            
        for(var i = 0;i<inputs.length;i++){   
            // 如果是最后一个，则焦点回到第一个   
            if(i==(inputs.length-1)){   
                inputs[0].focus(); break;   
            }else if(thisInput == inputs[i]){   
                inputs[i+1].focus(); break;   
            }   
        }   
} 


