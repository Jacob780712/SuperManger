// common.js
Date.prototype.isLeapYear = function() {
	return (0 == this.getYear() % 4 && ((this.getYear() % 100 != 0) || (this
			.getYear()
			% 400 == 0)));
}

function StringToDate(Str) {
	return new Date(Str.replace(/-/, "/"));
}



function getDateFromSplitStr(Str, splitStr) {
	return new Date(Str.replace(new RegExp(splitStr, "g"), "/"));
}

//定位RADIO的值
function changeRadio(radioObj,value){
	var length = radioObj.length;
	for(var i=0;i<length;i++){
		radioObj[i].checked = false;
		if(radioObj[i].value == value ){
			radioObj[i].checked = true;
			break;
		}
	}
}


function getDateFromStr(Str) {
	return new Date(Str.substring(0, 4) + "/" + Str.substring(4, 6) + "/"
			+ Str.substring(6, 8));
}

// ---------------------------------------------------
// 日期格式化
// 格式 YYYY/yyyy/YY/yy 表示年份
// MM/M 月份
// W/w 星期
// dd/DD/d/D 日期
// hh/HH/h/H 时间
// mm/m 分钟
// ss/SS/s/S 秒
// ---------------------------------------------------
Date.prototype.Format = function(formatStr) {
	var str = formatStr;
	var Week = ['日', '一', '二', '三', '四', '五', '六'];

	str = str.replace(/yyyy|YYYY/, this.getFullYear());
	str = str.replace(/yy|YY/, (this.getYear() % 100) > 9
			? (this.getYear() % 100).toString()
			: '0' + (this.getYear() % 100));

	str = str.replace(/MM/, this.getMonth() > 8 ? (this.getMonth() + 1)
			.toString() : '0' + (this.getMonth() + 1));
	str = str.replace(/M/g, this.getMonth() + 1);

	str = str.replace(/w|W/g, Week[this.getDay()]);

	str = str.replace(/dd|DD/, this.getDate() > 9
			? this.getDate().toString()
			: '0' + this.getDate());
	str = str.replace(/d|D/g, this.getDate());

	str = str.replace(/hh|HH/, this.getHours() > 9
			? this.getHours().toString()
			: '0' + this.getHours());
	str = str.replace(/h|H/g, this.getHours());
	str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes()
			.toString() : '0' + this.getMinutes());
	str = str.replace(/m/g, this.getMinutes());

	str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds()
			.toString() : '0' + this.getSeconds());
	str = str.replace(/s|S/g, this.getSeconds());

	return str;
}

// +---------------------------------------------------
// | 求两个时间的天数差 日期格式为 YYYY-MM-dd
// +---------------------------------------------------
function daysBetween(DateOne, DateTwo) {
	var OneMonth = DateOne.substring(5, DateOne.lastIndexOf('-'));
	var OneDay = DateOne
			.substring(DateOne.length, DateOne.lastIndexOf('-') + 1);
	var OneYear = DateOne.substring(0, DateOne.indexOf('-'));

	var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf('-'));
	var TwoDay = DateTwo
			.substring(DateTwo.length, DateTwo.lastIndexOf('-') + 1);
	var TwoYear = DateTwo.substring(0, DateTwo.indexOf('-'));

	var cha = ((Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) - Date
			.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) / 86400000);
	return Math.abs(cha);
}

// +---------------------------------------------------
// | 日期计算
// +---------------------------------------------------
Date.prototype.DateAdd = function(strInterval, Number) {
	var dtTmp = this;
	switch (strInterval) {
		case 's' :
			return new Date(Date.parse(dtTmp) + (1000 * Number));
		case 'n' :
			return new Date(Date.parse(dtTmp) + (60000 * Number));
		case 'h' :
			return new Date(Date.parse(dtTmp) + (3600000 * Number));
		case 'd' :
			return new Date(Date.parse(dtTmp) + (86400000 * Number));
		case 'w' :
			return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
		case 'q' :
			return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number
					* 3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(),
					dtTmp.getSeconds());
		case 'm' :
			return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number,
					dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(),
					dtTmp.getSeconds());
		case 'y' :
			return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(),
					dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(),
					dtTmp.getSeconds());
	}
}

// +---------------------------------------------------
// | 比较日期差 dtEnd 格式为日期型或者 有效日期格式字符串
// +---------------------------------------------------
Date.prototype.DateDiff = function(strInterval, dtEnd) {
	var dtStart = this;
	if (typeof dtEnd == 'string')// 如果是字符串转换为日期型
	{
		dtEnd = StringToDate(dtEnd);
	}
	switch (strInterval) {
		case 's' :
			return parseInt((dtEnd - dtStart) / 1000);
		case 'n' :
			return parseInt((dtEnd - dtStart) / 60000);
		case 'h' :
			return parseInt((dtEnd - dtStart) / 3600000);
		case 'd' :
			return parseInt((dtEnd - dtStart) / 86400000);
		case 'w' :
			return parseInt((dtEnd - dtStart) / (86400000 * 7));
		case 'm' :
			return (dtEnd.getMonth() + 1)
					+ ((dtEnd.getFullYear() - dtStart.getFullYear()) * 12)
					- (dtStart.getMonth() + 1);
		case 'y' :
			return dtEnd.getFullYear() - dtStart.getFullYear();
	}
}

// +---------------------------------------------------
// | 日期输出字符串，重载了系统的toString方法
// +---------------------------------------------------
Date.prototype.toString = function(showWeek) {
	var myDate = this;
	var str = myDate.toLocaleDateString();
	if (showWeek) {
		var Week = ['日', '一', '二', '三', '四', '五', '六'];
		str += ' 星期' + Week[myDate.getDay()];
	}
	return str;
}



function tongleSelect(checkname, tongleboxname) {
    var checkAll = document.getElementsByName(checkname);
    var tonglebox = document.getElementsByName(tongleboxname)[0];
    if (tonglebox.checked) {
        for (var i = 0; i < checkAll.length; i++) {
            checkAll[i].checked = true;
        }
    } else {
        for (var i = 0; i < checkAll.length; i++) {
            checkAll[i].checked = false;
        }
    }
}
function resetTongle(checkname, tongleboxname) {
    var flag = false;
    var checkAll = document.getElementsByName(checkname);
    for (var i = 0; i < checkAll.length; i++) {
        if (!checkAll[i].checked) {
            flag = true;
            break;
        }
    }
    if (flag) {
        document.getElementsByName(tongleboxname)[0].checked = false;
    } else {
        document.getElementsByName(tongleboxname)[0].checked = true;
    }
}
// newFunction
function validateInteger(field) {
    var bValid = true;
                	//var field = form[oInteger[x][0]];
    if (field.type == "text" || field.type == "textarea" || field.type == "select-one" || field.type == "radio") {
        var value = "";
						// get field's value
        if (field.type == "select-one") {
            var si = field.selectedIndex;
            if (si >= 0) {
                value = field.options[si].value;
            }
        } else {
            value = field.value;
        }
        if (value.length > 0) {
            if (!isAllDigits(value)) {
                bValid = false;
                field.focus();
            } else {
                var iValue = parseInt(value);
                if (isNaN(iValue) || !(iValue >= -2147483648 && iValue <= 2147483647)) {
                    if (i == 0) {
                        focusField = field;
                    }
                    bValid = false;
                }
            }
        }
    }
    return bValid;
}
function isAllDigits(argvalue) {
    argvalue = argvalue.toString();
    var validChars = "0123456789";
    var startFrom = 0;
    if (argvalue.substring(0, 2) == "0x") {
        validChars = "0123456789abcdefABCDEF";
        startFrom = 2;
    } else {
        if (argvalue.charAt(0) == "0") {
            validChars = "01234567";
            startFrom = 1;
        } else {
            if (argvalue.charAt(0) == "-") {
                startFrom = 1;
            }
        }
    }
    for (var n = startFrom; n < argvalue.length; n++) {
        if (validChars.indexOf(argvalue.substring(n, n + 1)) == -1) {
            return false;
        }
    }
    return true;
}

function showetype(type){
   if (type == 0){
     document.getElementById('0').style.display = "";
     document.getElementById('1').style.display = "none";
   } else{
     document.getElementById('1').style.display = "";
     document.getElementById('0').style.display = "none";
   }
}

function check(e)
{
	var k = window.event.keyCode;
	if (k < 46 || k > 57 ){
		alert("你输入的不是数字!")
		window.event.keyCode = 0 ;}
}
function login_tool(){
 if(location!=top.location ){
  top.location.href="login.jsp";
 }
}

var submitFlag=true;


function Append()
{   
    
    submitFlag=false;
	
    MoveOption(document.getElementById("allElement"), document.getElementById("selectElement"));
   
    submitFlag=true;
}



function Delete()
{   submitFlag=false;
    MoveOption(document.getElementById("selectElement"), document.getElementById("allElement"));
    submitFlag=true;
}


function MoveOption(oSrcSelect, oDestSelect)
{   
    var oOption, oNewOption;

	if(oSrcSelect==null || oDestSelect==null)
	{
		return;
	}
	for(var i=oSrcSelect.options.length-1; i>=0; i--)
	{
		oOption = oSrcSelect.options[i];
		if(oOption.selected)
		{
			oNewOption = new Option(oOption.text, oOption.value, false, false);
			oNewOption.__handset = oOption.__handset;
			oDestSelect.options[oDestSelect.options.length] = oNewOption;
			oSrcSelect.options[i] = null;
		}
	}
	
}

function allSelect() 
{
  List = document.forms[0].selectElement;
  for (i=0;i<List.length;i++)
  {
     List.options[i].selected = true;
  }
}

function checkAll(objAll)
{
	var isok=objAll.checked;
	//alert(document.all("chkIds"));
	
	if(document.getElementById("chkIds")!='[object]')
	{
		return ;
	}
	
	var len=document.getElementById("chkIds").length;
	//alert(len);
	if (len == undefined){
	  document.getElementById("chkIds").checked=isok;	
	  return; 
	}
	var objCheck=document.getElementById("chkIds");
	for(var i=0;i<len;i++)
	{
		objCheck[i].checked=isok;
	}
}

var oldTrClassName;
var pkId;

function onTrMoveIn(objTr)
	{
		pkId=objTr.id.replace("tr_","");
		oldTrClassName=objTr.className;
		objTr.className="list_trMoveIn";
		
	}
	
function onTrMoveOut(objTr)
	{
		var oldPkId=pkId;
		var oldTr=document.getElementById("tr_"+oldPkId);
		oldTr.className=oldTrClassName;
		
	}

String.prototype.trim  =  function()  
{  
  return  this.replace(/(^\s*)|(\s*$)/g,  "");  
} 


function getIEVersion()
{
	var str=navigator.appVersion;
	
	var beginIndex=str.indexOf("MSIE");

	var tmp=str.substring(beginIndex+4);

	var result=parseInt(tmp.split(";")[0].trim());

	return result;
	
}



/*
	js获取request参数
	name:参数名
*/
function GetQueryString(name)   
{   
     var     reg     =   new   RegExp("(^|&)"+     name     +"=([^&]*)(&|$)");   
     var     r     =     window.location.search.substr(1).match(reg);   
     if     (r!=null)   return     unescape(r[2]);   return   "";   
} 




/*
	打开模态窗口
	theURL:打开地址
	height:高度
	width:宽度
	isGotoByReturnValue：是否以窗体返回值进行跳转,否的话返回窗体返回值
*/
function openModalWindow(theURL,height,width,isGotoByReturnValue) { //v2.0

	 var returnValue=window.showModalDialog(theURL,window,'dialogHeight:'+height+'px;dialogWidth:'+width+'px;center:yes');
		 if(returnValue!=null&&
		returnValue!=undefined&& returnValue.trim()!=""&&isGotoByReturnValue==true)
		 {
		 	window.location.href=returnValue;
		 }else
		 {
		 	return returnValue;
		 }
}

	
	
	function checkEmailFormatForObj(emailObj)
	{
		if(!checkEmailFormat(emailObj.value.trim()))
		{
			emailObj.focus();
			return false;
		}
		
		return true;
	}
	
	
	function checkEmailFormat(email)
	{
			if(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(email)==false)
  			{
  				alert("您输入的是无效邮件地址！");
  				//$('email').focus();
        		return false;
  			}
  			
  			return true;
  			
	}



 		//验证手机号
      function checkMobileFormat(objMobile)
      {
      
      	if(objMobile!='[object]')
      	{
      		return false;
      	}
      
      	var reg=/(^13[0-9]\d{8}$)|(^15[0,7,8,9]\d{8}$)/g;
	
      	if(reg.test(objMobile.value.trim())==false)
      	{
      		alert("您输入的手机号无效！");
      		objMobile.focus();
      		return false;
      	}
		return true;
      }


	 //验证手机号和小灵通
      function checkMobileNoFormat(objMobile)
      {
      
      	if(objMobile!='[object]')
      	{
      		return false;
      	}
      
      	var reg=/(^13[0-9]\d{8}$)|(^15[0,7,8,9]\d{8}$)|(^[1-9]{2}\d{6}$)/g;
	
	
	    if(objMobile.value.trim() == "")
	    { 
	        alert("您输入的手机号为必填！");
	        objMobile.focus();
	        return false;
	      
	    }
      	if(reg.test(objMobile.value.trim())==false)
      	{
      		alert("您输入的手机号无效！");
      		objMobile.focus();
      		return false;
      	}
		return true;
      }	
		

		

   
      /*检查金额 */
      	function checkMoneyFormat(objMoney)
      	{
      	
      		if(objMoney!='[object]')
      		{
      			return false;
      		}
      
      	
      		var reg=/^\d+\.\d{2}$/;
      		
      		if(reg.test(objMoney.value.trim())==false)
      		{
      			alert("购买价格必须为带两位小数部分的正浮点数！");
				objMoney.focus();
				return false;		
      		}
      		
      		return true;
      	}

      	/*
      	 * 身份证合法性验证
      	 */
      	function checkIdcardObj(objIdCard) {

      		if (objIdCard != '[object]') {
      			return false;
      		}

      		var idcard = objIdCard.value;

      		if (checkIdcard(idcard) == false) {
      			objIdCard.focus();
      			return false;
      		}
      		return true;
      	}

      	/**
      	 * 身份证合法性验证
      	 */
      	function checkIdcard(idcard) {

      		return checkIdcardByFlag(idcard, true);
      	}

      	/**
      	 * 身份证合法性验证,如果isRtnBoolean为true，则返回布尔值,同时弹出错误提示框，如果为false，则返回错误字符串,为空表示正常
      	 */
      	function checkIdcardByFlag(idcard, isRtnBoolean) {
      		var Errors = new Array("验证通过！", "身份证号码位数不对！", "身份证号码出生日期超出范围或含有非法字符！",
      				"身份证号码校验错误！", "身份证地区非法！");
      		var area = {
      			11 : "北京",
      			12 : "天津",
      			13 : "河北",
      			14 : "山西",
      			15 : "内蒙古",
      			21 : "辽宁",
      			22 : "吉林",
      			23 : "黑龙江",
      			31 : "上海",
      			32 : "江苏",
      			33 : "浙江",
      			34 : "安徽",
      			35 : "福建",
      			36 : "江西",
      			37 : "山东",
      			41 : "河南",
      			42 : "湖北",
      			43 : "湖南",
      			44 : "广东",
      			45 : "广西",
      			46 : "海南",
      			50 : "重庆",
      			51 : "四川",
      			52 : "贵州",
      			53 : "云南",
      			54 : "西藏",
      			61 : "陕西",
      			62 : "甘肃",
      			63 : "青海",
      			64 : "宁夏",
      			65 : "新疆",
      			71 : "台湾",
      			81 : "香港",
      			82 : "澳门",
      			91 : "国外"
      		}
      		var idcard, Y, JYM;
      		var S, M;
      		var idcard_array = new Array();
      		idcard_array = idcard.split("");
      		// 地区检验
      		if (area[parseInt(idcard.substr(0, 2))] == null) {

      			if (isRtnBoolean) {
      				alert(Errors[4]);
      				return false;
      			} else {
      				return Errors[4];
      			}

      		}
      		// 身份号码位数及格式检验
      		switch (idcard.length) {
      			case 15 :
      				if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
      						|| ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
      								.substr(6, 2)) + 1900)
      								% 4 == 0)) {
      					ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
      				} else {
      					ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
      				}
      				if (ereg.test(idcard)) {

      					if (isRtnBoolean) {
      						return true;
      					} else {
      						return "";
      					}

      				} else {

      					if (isRtnBoolean) {
      						alert(Errors[2]);
      						// $('pcCard').focus();
      						return false;
      					} else {
      						return Errors[2];
      					}
      				}
      				break;
      			case 18 :
      				// 18位身份号码检测
      				// 出生日期的合法性检查
      				// 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
      				// 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
      				if (parseInt(idcard.substr(6, 4)) % 4 == 0
      						|| (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
      								.substr(6, 4))
      								% 4 == 0)) {
      					ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
      				} else {
      					ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
      				}
      				if (ereg.test(idcard)) {// 测试出生日期的合法性
      					// 计算校验位
      					S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10]))
      							* 7
      							+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
      							* 9
      							+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
      							* 10
      							+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
      							* 5
      							+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
      							* 8
      							+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
      							* 4
      							+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
      							* 2 + parseInt(idcard_array[7]) * 1
      							+ parseInt(idcard_array[8]) * 6
      							+ parseInt(idcard_array[9]) * 3;
      					Y = S % 11;
      					M = "F";
      					JYM = "10X98765432";
      					M = JYM.substr(Y, 1);// 判断校验位
      					if (M == idcard_array[17].toUpperCase()) {

      						if (isRtnBoolean) {
      							return true;
      						} else {
      							return "";
      						}

      					}// 检测ID的校验位
      					else {

      						if (isRtnBoolean) {
      							alert(Errors[3]);
      							return false;
      						} else {
      							return Errors[3];
      						}
      					}
      				} else {
      					if (isRtnBoolean) {
      						alert(Errors[2]);
      						return false;
      					} else {
      						return Errors[2];
      					}
      				}
      				break;
      			default :
      				if (isRtnBoolean) {
      					alert(Errors[1]);
      					return false;
      				} else {
      					return Errors[1];
      				}
      				break;
      		}
      	}

var REG_MOBILE = /^(13[0-9]\d{8})|(15[0,7,8,9]\d{8})$/;

// 验证手机号
function checkMobileFormat(objMobile) {

	return checkMobileFormatByRtnBoolean(objMobile, true);

}

// 验证手机号
function checkMobileFormatByRtnBoolean(objMobile, isRtnBoolean) {

	if (objMobile != '[object]') {
		if (isRtnBoolean) {
			alert("查无此控件");
			return false;
		} else {
			return "查无此控件";
		}

	}

	if (REG_MOBILE.test(objMobile.value.trim()) == false) {

		objMobile.focus();

		if (isRtnBoolean) {
			alert("您输入的手机号无效！");
			return false;
		} else {
			return "您输入的手机号无效！";
		}

	}

	if (isRtnBoolean) {
		return true;
	} else {
		return ""
	};
}


function checkAgeRange(idCard,begin,end)
      {
		var tValue=idCard;

		var birthYear="";
		var birthDate="";
		
      	if(tValue.length==15)
      	{
      		birthYear="19"+tValue.substring(6,8);
      		birthDate=tValue.substring(8,12)
      	}
      	else if(tValue.length==18)
      	{
      		birthYear=tValue.substring(6,10);
      		birthDate=tValue.substring(10,14);
      	}
      	else
      	{
      		alert("您输入的身份证位数不对！");
      		//$('pcCard').focus();
      		return false;
      		
      	}

      	birthYear=parseInt(birthYear);

      	var currentYear=(new Date()).getYear();
		
		var currentDate=setZero(((new Date()).getMonth()+1))+setZero(((new Date()).getDate()));

   	 	var age=currentYear-birthYear;
   	 	
   	 	if(age<begin||age>end)//首先判断年份
   	 	{
   	 		alert("您的年龄不再"+begin+"-"+end+"的范围内，不能投保！");
   	 		//$('pcCard').focus();
      		return false;
   	 	}
      	if((age==begin)&&(currentDate<birthDate))//如果刚好等于16，还得判断日期是否到了
      	{
      			alert("您的年龄尚未满"+begin+"岁，不能投保！");
      			//$('pcCard').focus();
      		return false;
      	}

		return true;
      }
		


  	   function checkAgeRangeObj(objIdCard,begin,end)
      {
      
      	if(objIdCard!='[object]')
      	{
      		return false;
      	}
      
      	
      	if(checkAgeRange(objIdCard.value.trim(),begin,end)==false)
      	{
      		objIdCard.focus();
      		return false;
      	}
      
     	return true;
      	
      
      }
      
      
      
      
      function setZero(tValue)
      {
      
      	tValue=tValue+"";
      	
      	if(tValue.length==1)
      	{
      		tValue="0"+tValue;
      	}
      	
      	return tValue;
      }
      
  	/*
		增加，提取，删除COOKIE的公共方法
	*/
	function getCookieVal(offset)
	//获得Cookie解码后的值
	{
		var endstr = document.cookie.indexOf (";", offset);
		if (endstr == -1)
		endstr = document.cookie.length;
		return unescape(document.cookie.substring(offset, endstr));
	}
	
	function setCookieByExpdate(name, value,date)
	//设定Cookie值
	{
		if (date!=''){
			var expdate = new Date(date);
		}else{
			var expdate = new Date();
		}
		var argv = setCookieByExpdate.arguments;
		var argc = setCookieByExpdate.arguments.length;
		var expires = (argc > 3) ? argv[3] : null;
		var path = (argc > 4) ? argv[4] : null;
		var domain = (argc > 5) ? argv[5] : null;
		var secure = (argc > 6) ? argv[6] : false;
		if(expires!=null) expdate.setTime(expdate.getTime() + ( expires * 1000 ));
		document.cookie = name + "=" + escape (value) +((expires == null) ? "" : ("; expires="+ expdate.toGMTString()))
			+((path == null) ? "" : ("; path=" + path)) +((domain == null) ? "" : ("; domain=" + domain))
			+((secure == true) ? "; secure" : "");
	}
	
	function delCookie(name)
	//删除Cookie
	{
		var exp = new Date();
		exp.setTime (exp.getTime() - 1);
		var cval = getCookie (name);
		document.cookie = name + "=" + cval + "; expires="+ exp.toGMTString();
	}
	
	function getCookie(name)
	//获得Cookie的原始值
	{
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		while (i < clen)
		{
			var j = i + alen;
			if (document.cookie.substring(i, j) == arg)
			return getCookieVal (j);
			i = document.cookie.indexOf(" ", i) + 1;
			if (i == 0) break;
		}
		return null;
	}
//转汉字
function convertCurrency(currencyDigits) {
// Constants:
if(currencyDigits=="")
{
	return "零元整";
}

var MAXIMUM_NUMBER = 99999999999.99;
// Predefine the radix characters and currency symbols for output:
var CN_ZERO = "零";
var CN_ONE = "壹";
var CN_TWO = "贰";
var CN_THREE = "叁";
var CN_FOUR = "肆";
var CN_FIVE = "伍";
var CN_SIX = "陆";
var CN_SEVEN = "柒";
var CN_EIGHT = "捌";
var CN_NINE = "玖";
var CN_TEN = "拾";
var CN_HUNDRED = "佰";
var CN_THOUSAND = "仟";
var CN_TEN_THOUSAND = "万";
var CN_HUNDRED_MILLION = "亿";
var CN_SYMBOL = "";
var CN_DOLLAR = "元";
var CN_TEN_CENT = "角";
var CN_CENT = "分";
var CN_INTEGER = "整";

// Variables:
var integral; // Represent integral part of digit number.
var decimal; // Represent decimal part of digit number.
var outputCharacters; // The output result.
var parts;
var digits, radices, bigRadices, decimals;
var zeroCount;
var i, p, d;
var quotient, modulus;

// Validate input string:
currencyDigits = currencyDigits.toString();
if (currencyDigits == "") {
alert("Empty input!");
return "";
}
if (currencyDigits.match(/[^,.\d]/) != null) {
alert("Invalid characters in the input string!");
return "";
}
if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
alert("Illegal format of digit number!");
return "";
}

// Normalize the format of input digits:
currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters.
currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning.
// Assert the number is not greater than the maximum number.
if (Number(currencyDigits) > MAXIMUM_NUMBER) {
alert("Too large a number to convert!");
return "";
}

// Process the coversion from currency digits to characters:
// Separate integral and decimal parts before processing coversion:
parts = currencyDigits.split(".");
if (parts.length > 1) {
integral = parts[0];
decimal = parts[1];
// Cut down redundant decimal digits that are after the second.
decimal = decimal.substr(0, 2);
}
else {
integral = parts[0];
decimal = "";
}
// Prepare the characters corresponding to the digits:
digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
decimals = new Array(CN_TEN_CENT, CN_CENT);
// Start processing:
outputCharacters = "";
// Process integral part if it is larger than 0:
if (Number(integral) > 0) {
zeroCount = 0;
for (i = 0; i < integral.length; i++) {
p = integral.length - i - 1;
d = integral.substr(i, 1);
quotient = p / 4;
modulus = p % 4;
if (d == "0") {
zeroCount++;
}
else {
if (zeroCount > 0)
{
outputCharacters += digits[0];
}
zeroCount = 0;
outputCharacters += digits[Number(d)] + radices[modulus];
}
if (modulus == 0 && zeroCount < 4) {
outputCharacters += bigRadices[quotient];
}
}
outputCharacters += CN_DOLLAR;
}
// Process decimal part if there is:
if (decimal != "") {
for (i = 0; i < decimal.length; i++) {
d = decimal.substr(i, 1);
if (d != "0") {
outputCharacters += digits[Number(d)] + decimals[i];
}
}
}
// Confirm and return the final output string:
if (outputCharacters == "") {
outputCharacters = CN_ZERO + CN_DOLLAR;
}
if (decimal == "") {
outputCharacters += CN_INTEGER;
}
outputCharacters = CN_SYMBOL + outputCharacters;
return outputCharacters;
}

//url链接路径进行验证

function IsURL(str_url){

var strRegex = "^((https|http|ftp|rtsp|mms)://)"
+ "(([0-9a-z_!~*’().&=+$%-]+: )?[0-9a-z_!~*’().&=+$%-]+@)?" //ftp的user@
+ "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
+ "|" // 允许IP和DOMAIN（域名）
+ "([0-9a-z_!~*’()-]+\.)*" // 域名- www.
+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
+ "[a-z]{2,6})" // first level domain- .com or .museum
+ "(:[0-9]{1,4})?" // 端口- :80

var re=new RegExp(strRegex);

if (re.test(str_url)){
return true;
}else{
return false;
}

}

/*
 * 限制文本域字段多少
 */
function checkTextAreaSize(obj,length)
{
	if(obj.value.length > length) 
		obj.value=obj.value.substr(0,length);
}
         /** 是否为空验证 */
function isNull( str ){

	if ( str == "" ) return true;
	
	var regu = "^[ ]+$";
	
	var re = new RegExp(regu);
	
	return re.test(str);

}
/**
*复制到剪贴板
*/
 function copyit(textit) {
	if (window.clipboardData) {
	window.clipboardData.setData("Text",textit);
	} else {
	var flashcopier = 'flashcopier';
	if(!document.getElementById(flashcopier)) {
	var divholder = document.createElement('div');
	divholder.id = flashcopier;
	document.body.appendChild(divholder);
	}
	document.getElementById(flashcopier).innerHTML = '';
	var divinfo = '<embed src="_clipboard.swf" FlashVars="clipboard='+encodeURIComponent(textit)+'" width="0" height="0" type="application/x-shockwave-flash"></embed>';
	document.getElementById(flashcopier).innerHTML = divinfo;
	}
}

    //add 随机数 为了更改url
  function GetRom()
  {
   	var now=new Date(); 

	return now.getSeconds(); 
   
  }
  
	function batch_doEx(tip,formName,elmentName,action)
	{ 
	   if (!atleaseOneCheckEx(elmentName))
	        {
	            alert('请至少选择一个' + tip + '！');
	            return;
	        }
	
	    if (confirm("确定要" + tip + "?"))
	    {
	        var form = document.getElementsByName(formName)[0];
	        form.action = action;
	        form.submit();
	    }
}
	//checkbox中至少有一项被选中
	function atleaseOneCheckEx(elmentName)
	{
    var items = document.getElementsByName(elmentName);
    if (items.length > 0) {
        for (var i = 0; i < items.length; i++)
        {
            if (items[i].checked == true)
            {
                return true;
            }
        }
    } else {
        if (items.checked == true) {
            return true;
        }
    }
    return false;
	}