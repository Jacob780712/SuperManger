var regexEnum =
{
	intege:"^-?[1-9]\\d*$",					//整数
	intege1:"^[1-9]\\d*$",					//正整数
	intege2:"^-[1-9]\\d*$",					//负整数
	num:"^([+-]?)\\d*\\.?\\d+$",			//数字
	num1:"^[1-9]\\d*|0$",					//正数（正整数 + 0）
	num2:"^-[1-9]\\d*|0$",					//负数（负整数 + 0）
	decmal:"^([+-]?)\\d*\\.\\d+$",			//浮点数
	decmal1:"^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$",　　	//正浮点数
	decmal2:"^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$",　 //负浮点数
	decmal3:"^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$",　 //浮点数
	decmal4:"^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$",　　 //非负浮点数（正浮点数 + 0）
	decmal5:"^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$",　　//非正浮点数（负浮点数 + 0）

	email:"^\\w+((-\\w+)|(\\.\\w+))*\\@$", //邮件
	color:"^[a-fA-F0-9]{6}$",				//颜色
	url:"^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$",	//url
	chinese:"^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$",					//仅中文
	ascii:"^[\\x00-\\xFF]+$",				//仅ACSII字符
	zipcode:"^\\d{6}$",						//邮编
	mobile:"^(13|15)[0-9]{9}$",				//手机
	ip4:"^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$",	//ip地址
	notempty:"^\\S+$",						//非空
	picture:"(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$",	//图片
	rar:"(.*)\\.(rar|zip|7zip|tgz)$",								//压缩文件
	date:"^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$",					//日期
	qq:"^[1-9]*[1-9][0-9]*$",				//QQ号码
	tel:"^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$",	//电话号码的函数(包括验证国内区号,国际区号,分机号)
	username:"^\\w+$",						//用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
	letter:"^[A-Za-z]+$",					//字母
	letter_u:"^[A-Z]+$",					//大写字母
	letter_l:"^[a-z]+$",					//小写字母
	idcard:"^[1-9]([0-9]{14}|[0-9]{17})$",	//身份证
	emailNew:"^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\-]?)*[a-zA-Z0-9][\.][a-zA-Z0-9]{2,3}$"//邮件扩展
	//[0-9a-zA-Z]+([0-9a-zA-Z]|_|\.|-)+[0-9a-zA-Z]+@(([0-9a-zA-Z]+\.)|([0-9a-zA-Z]+-))+[0-9a-zA-Z]+$
}

var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"} 

function isCardID(sId){
	
	var iSum=0 ;
	var info="" ;
	if(!/^\d{17}(\d|x)$/i.test(sId)) return "身份证格式错误"; 
	sId=sId.replace(/x$/i,"a"); 
	if(aCity[parseInt(sId.substr(0,2))]==null) return "你的身份证地区非法"; 
	sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
	var d=new Date(sBirthday.replace(/-/g,"/")) ;
	if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "身份证上的出生日期非法"; 
	for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
	if(iSum%11!=1) return "身份证号非法"; 
	return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女") 
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

         /** 是否为空验证 */
function isNull( str ){

	if ( str == "" ) return true;
	
	var regu = "^[ ]+$";
	
	var re = new RegExp(regu);
	
	return re.test(str);

}

function isCardType(sId){
	if (jQuery("#idcardType").val() == 0){
	    var re = checkIdcardByFlag(sId,false);
	    if(isNull(re))
	    	return true;
		return "";
	}else{
		if(isNull(sId)){
			return "证件号码不能为空!";
		}else{
			return true;
		}
	}

}

function isCardTypeEx(sId){
	if (jQuery("#idcardType").val() == 0){
	    var re = checkIdcardByFlag(sId,false);
	    if(isNull(re))
	    	return true;
		return "请填写正确证件号码，仅作为理赔时的凭证。";
	}else{
		if(isNull(sId)){
			return "证件号码不能为空!";
		}else{
			return true;
		}
	}

}


function isCardTypePinAn(sId){

	if (jQuery("#idcardType").val() == 0){
	    var re = checkIdcardByFlag(sId,false);
	    
	    if(isNull(re)){
	    	if(sId.substring(17,18)=='x'){
    			return 'X为大写';
    		}
	    	if(checkBirthday(sId)){
	    		return true;
	    	}else{
	    		return "被保险人的年龄限制为18-44周岁";
	    	}
	    }
		return "";
	}else{
		if(isNull(sId)){
			return "证件号码不能为空!";
		}else{
			return true;
		}
	}

}

function isCardTypeEx2(sId){

	if (jQuery("#idcardType").val() == 0){
	    var re = checkIdcardByFlag(sId,false);
	    
	    if(isNull(re)){
	    	if(sId.substring(17,18)=='x'){
    			return 'X为大写';
    		}else{
    			return true;
    		}
	    }
		return "请填写正确证件号码，仅作为理赔时的凭证。";
	}else{
		if(isNull(sId)){
			return "证件号码不能为空!";
		}else{
			return true;
		}
	}

}


function checkDate(inputDate){
	var   sysDate   =   new   Date(); 
	var   y   =   sysDate.getYear(); 
	var   m   =   sysDate.getMonth()+1; 
	var   d   =   sysDate.getDate(); 

	if(m <=9)   m= "0"+m; 
	if(d <=9)   d= "0"+d; 
	var sysDateStr = y+ "-"+m+ "-"+d;
	
	//出行日期限制在一年以内

	sysDate.setFullYear(sysDate.getFullYear() + 1); 
	sysDate.setDate(sysDate.getDate() - 1); 
	
	var   y1   =   sysDate.getFullYear(); 
	var   m1   =   sysDate.getMonth()+1; 
	var   d1   =   sysDate.getDate(); 

	if(m1 <=9)   m1= "0"+m1; 
	if(d1 <=9)   d1= "0"+d1; 
	var nextYearDate = y1+ "-"+m1+ "-"+d1;
	
	if (inputDate < sysDateStr){
		return("日期不能小于当天日期");
	}else if (inputDate> nextYearDate){
		return("日期不能超过一年");
	}else{
		return true;
	}
}


function checkDateMobile(){
	var   sysDate   =   new   Date(); 
	var   y   =   sysDate.getYear(); 
	var   m   =   sysDate.getMonth()+1; 
	var   d   =   sysDate.getDate(); 

	if(m <=9)   m= "0"+m; 
	if(d <=9)   d= "0"+d; 
	var sysDateStr = y+ "-"+m+ "-"+d;
	if (jQuery("#buyDate").val() !=  sysDateStr){
		return("激活时间与购机时间不符");
	}else{
		return true;
	}
}




//短时间，形如 (13:04:06)
function isTime(str)
{
	var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
	if (a == null) {return false}
	if (a[1]>24 || a[3]>60 || a[4]>60)
	{
		return false;
	}
	return true;
}

//短日期，形如 (2003-12-05)
function isDate(str)
{
	var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	if(r==null)return false; 
	var d= new Date(r[1], r[3]-1, r[4]); 
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
}

//长时间，形如 (2003-12-05 13:04:06)
function isDateTime(str)
{
	var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
	var r = str.match(reg); 
	if(r==null) return false; 
	var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
}


//验证手机号
function checkallMobileFormat(sId)
{
		if(isNull(sId)){
			return "手机号不能为空";
		}else{
			var reg=/(^13[0-9]\d{8}$)|(^15[0-35-9]\d{8}$)|(18[025-9]\d{8}$)/g;
		
	      	if(reg.test(sId)==false)
	      	{
	      		return "手机号格式不正确";
	      	}
		}
      	
		return true;
}

function checkallMobileFormatajax(sId){
	if(isNull(sId)){
		return "手机号不能为空";
	}else{
		var reg=/(^13[0-9]\d{8}$)|(^15[0-35-9]\d{8}$)|18[025-9]\d{8}$/g;
		
	   if(reg.test(sId)==false)
	    {
	      	return "手机号格式不正确";
	     }
	}
	
	var temp = '';
		
	jQuery.ajax({
	 url: 'ajaxGetActcode.jhtml',
	 type: 'POST',
	// dataType: 'html',
	 timeout: 20000,
	 data:'activecode='+sId,
	 error: function(){alert("服务器没有返回数据，可能服务器忙，请重试");},
	 onerror : "该手机号未登记!",
	 onwait : "正在校验手机号，请稍候...",
	 async: false,
	 success: function(html){
	 	if(html.indexOf('0')==0){
	 		temp = html.split('#')[1];
	 	}else if(html.indexOf('1')==0){
	 		var array = html.split('#');
	 		jQuery('#actCode').val(array[1]);
	 		jQuery('#orderid').val(array[2]);
	 	}
	 }
	 
	});
	

	if(!isNull(temp)){
		return '该手机号未登记或已激活!';
	}else{
		return true;
	}
}
 
 
function checkBirthday(card)
{
	
    var len = card.length;
    //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
    if(len == '15')
    {
        var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/; 
        var arr_data = card.match(re_fifteen);
        var year = arr_data[2];
        var month = arr_data[3];
        var day = arr_data[4];
        var birthday = new Date('19'+year+'/'+month+'/'+day);
        return verifyBirthday('19'+year,month,day,birthday);
    }
    //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
    if(len == '18')
    {
        var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
        var arr_data = card.match(re_eighteen);
        var year = arr_data[2];
        var month = arr_data[3];
        var day = arr_data[4];
        var birthday = new Date(year+'/'+month+'/'+day);
        return verifyBirthday(year,month,day,birthday);
    }
    return false;
};
 
 function verifyBirthday(year,month,day,birthday)
{
    var now = new Date();
    var now_year = now.getFullYear();
    //年月日是否合理
    if(birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day)
    {
        //判断年份的范围（3岁到100岁之间)
        var time = now_year - year;
        if(time >= 18 && time <= 44)
        {
            return true;
        }
        return false;
    }
    return false;
};


function checkusernamevalue(sId){

	//var reg=/[^\a-zA-Z\u4E00-\u9FA5]/g;
	var reg=/[^\u4E00-\u9FA5]/g;
	if(!reg.test(sId)){
		return true;
	}else{
		return "姓名只能为中文!";
	}
}

//验证手机号
function checknoliantongMobileFormat(sId)
{
		if(isNull(sId)){
			return "手机号不能为空";
		}else{
			var reg=/(^13[4-9]\d{8}$)|(^15[0-27-9]\d{8}$)|(18[27-8]\d{8}$)/g;
		
	      	if(reg.test(sId)==false)
	      	{
	      		return "只支持移动用户";
	      	}
		}
      	
		return true;
}



