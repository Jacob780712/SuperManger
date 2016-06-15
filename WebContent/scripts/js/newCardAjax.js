
function createXMLHttpRequest() {
	if (window.XMLHttpRequest) { //Mozilla 浏览器
		XMLHttpReq = new XMLHttpRequest();
	} else {
		if (window.ActiveXObject) { // IE浏览器
			try {
				XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch (e) {
				try {
					XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch (e) {
				}
			}
		}
	}
}

//代理商
function sendReq_agentCode() {
	var agentCode = document.getElementById("agentCode").value;
			
	//1:初始化
	createXMLHttpRequest();
			
	//2:设置属性
	XMLHttpReq.onreadystatechange = myDeal_agentCode;
	//3:发出请求
	XMLHttpReq.open("GET", "../manage/getComboTabAction.jhtml?agentCode=" + agentCode, true);
	XMLHttpReq.setRequestHeader("If-Modified-Since","0");
	XMLHttpReq.send(null);
}
function myDeal_agentCode() {
	if (XMLHttpReq.readyState == 4) {
		//1:接收服务端返回的数据
		var ret = XMLHttpReq.responseText;
		//2:处理
		var obj = document.getElementById("comboCode");
		for (var i = obj.options.length - 1; i >= 0; i--) {
			obj.remove(i);
		}
		var ops = ret.split("|");
		for (var i = 0; i < ops.length; i++) {
			var op = ops[i];
			var ss = op.split(",");
			var oOption = document.createElement("OPTION");
			obj.options.add(oOption);
			oOption.text = ss[1];
			oOption.value = ss[0];
		}
	}
}
//套餐
function sendReq_comboCode(type) {
	var rand = Math.random();
	var comboCode = document.getElementById("comboCode").value;
	var agentCode = document.getElementById("agentCode").value;
	var applyCount = document.getElementById("applyCount").value;
	var endfixStr = document.getElementById("endfixStr").value;
	if (endfixStr.length != 2){
	 endfixStr = "88";
	}
	
	//1:初始化
	createXMLHttpRequest();
	//2:设置属性
	if (type == "add") {
		XMLHttpReq.onreadystatechange = myDeal_comboCode;
	} else {
		XMLHttpReq.onreadystatechange = myDeal_comboCodeEdit;
	}
	//3:发出请求
	XMLHttpReq.open("GET", "../admin/card.jhtml?method=getCardCountByCode&applyCount=" + applyCount + "&agentCode=" + agentCode + "&comboCode=" + comboCode + "&endfixStr=" + endfixStr+ "&rand=" + rand, true);
	XMLHttpReq.setRequestHeader("If-Modified-Since","0");
	XMLHttpReq.send(null);
	
}
function myDeal_comboCode() {
	if (XMLHttpReq.readyState == 4) {
		//1:接收服务端返回的数据
		var ret = XMLHttpReq.responseText;
		//alert("ret="+ret)
		var arrs = ret.split("|");
		if (arrs != null && arrs.length>0){
			var arr1 = arrs[0];
			var arr = arr1.split(",");
			if (arr != null && arr.length>0){
				//本套餐已用卡号量
				var objCardNum = document.getElementById("card_num");
				objCardNum.value = arr[0];
			}
		}
		if(arrs != null && arrs.length >1){
			arr1 = arrs[1];
			arr = arr1.split(",");
			if(arr != null && arr.length == 2){
				//起始卡号描述
				var objBeginNoDesc = document.getElementById("beginNoDesc");
				objBeginNoDesc.value = arr[0];
						
				//结束卡号描述
				var objEndNoDesc = document.getElementById("endNoDesc");
				objEndNoDesc.value = arr[1];
			}
		}
		
		if(arrs != null && arrs.length > 2){
			var arr1 = arrs[2];
			if(arr != null && arr.length == 2){
				var arr = arr1.split(",");
				//起始卡号
				var objBeginNo = document.getElementById("beginNo");
				objBeginNo.value = arr[0];
						
				//结束卡号
				var objEndNo = document.getElementById("endNo");
				objEndNo.value = arr[1];
			}
		}
	}
}
function myDeal_comboCodeEdit() {
	if (XMLHttpReq.readyState == 4) {
		//1:接收服务端返回的数据
		var ret = XMLHttpReq.responseText;
		//alert ("ret="+ret);
		var arrs = ret.split("|");
		var arr1 = arrs[0];
		var arr = arr1.split(",");
		//本套餐已用卡号量
		var objCardNum = document.getElementById("card_num");
		objCardNum.value = arr[0];
		//批次号
		//var objBatchNo = document.getElementById("batchNo");
		//objBatchNo.value = arr[1];
		arr1 = arrs[1];
		arr = arr1.split(",");
		//起始卡号描述
		var objBeginNoDesc = document.getElementById("beginNoDesc");
		objBeginNoDesc.value = arr[0];
				
		//结束卡号描述
		var objEndNoDesc = document.getElementById("endNoDesc");
		objEndNoDesc.value = arr[1];
		var arr1 = arrs[2];
		var arr = arr1.split(",");
		//起始卡号
		var objBeginNo = document.getElementById("beginNo");
		objBeginNo.value = arr[0];
				
		//结束卡号
		var objEndNo = document.getElementById("endNo");
		objEndNo.value = arr[1];
	}
}
		
//申请数量
function sendReq_applyCount() {
	var rand = Math.random();
	var comboCode = document.getElementById("comboCode").value;
	var agentCode = document.getElementById("agentCode").value;
	var applyCount = document.getElementById("applyCount").value;
	var endfixStr = document.getElementById("endfixStr").value;
	if (endfixStr.length != 2){
	 endfixStr = "88";
	}
			
	//1:初始化
	createXMLHttpRequest();
			
	//2:设置属性
	XMLHttpReq.onreadystatechange = myDeal_applyCount;
	//3:发出请求
	XMLHttpReq.open("GET", "../admin/card.jhtml?method=getCardBathNo&applyCount=" + applyCount + "&agentCode=" + agentCode + "&comboCode=" + comboCode + "&endfixStr=" + endfixStr + "&rand=" + rand, true);
	XMLHttpReq.setRequestHeader("If-Modified-Since","0");
	XMLHttpReq.send(null);
}
function myDeal_applyCount() {
	if (XMLHttpReq.readyState == 4) {
		//1:接收服务端返回的数据
		var ret = XMLHttpReq.responseText;
		var arrs = ret.split("|");
		if (arrs != null && arrs.length > 0){
			var carddesc = arrs[0];
			var arr = carddesc.split(",");
			if (arr != null && arr.length == 2){
				//起始批号描述
				var objCardNum = document.getElementById("beginNoDesc");
				objCardNum.value = arr[0];
				//结束批号描述
				var objBatchNo = document.getElementById("endNoDesc");
				objBatchNo.value = arr[1];
			}
		}
		if (arrs != null && arrs.length > 1){
			var cardno = arrs[1];
			var arr = cardno.split(",");
			if (arr != null && arrs.length==2){
				//起始批号
				var objCardNum = document.getElementById("beginNo");
				objCardNum.value = arr[0];
				//结束批号
				var objBatchNo = document.getElementById("endNo");
				objBatchNo.value = arr[1];
			}
		}
	}
}
//edit.jsp  填充时间
function fillTime(){
	var endDate =  $('#endDate').val();
	if (endDate != null || endDate != ""){
		$('#endDate').val(endDate+" 23:59:59")
	}
}	
//list.jsp  删除按钮
function radion_do(entityName, action,formName){ 
	if (confirm("确定要" + entityName + "?")){
        var form = eval("document.forms."+formName);
        form.action = action;
        form.submit();
	}
}
//radion中至少有一项被选中
function atleaseOneCheck(){
	var items = document.getElementsByName('radIds');
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
			  
//提示按钮
function mySubmitPass(method,id,status,entityName,formName){
	var flag;
	flag = confirm("确定要"+entityName+"通过吗?");
	if(flag){
		$('#agree').attr({disabled:"true"});
		$('#refuse').attr({disabled:"true"});
		var form = eval("document.forms."+formName);
		form.action = "card.jhtml?method="+method+"&id="+id+"&status="+status;
		form.submit();
	}
	
}
function mySubmitRefuse(method,id,status,entityName,formName){
	var info = $.trim($('#remark_refuse').val());
	if (info == null || info == ""){
		alert("受理意见必须填写！");
		$('#remark_refuse').focus();
	}else{
		var flag;
		flag = confirm("确定要拒绝"+entityName+"吗?");
		if(flag){
			var form = eval("document.forms."+formName);
			form.action = "card.jhtml?method="+method+"&id="+id+"&status="+status;
			form.submit();
	
		}
	}
	
}
//尾数变化触发事件
function changeEndfixStr(){
	var descBegin =  $('#beginNoDesc').val();
	var descEnd =  $('#endNoDesc').val();
	var endfix =  $('#endfixStr').val();
	if (descBegin == "" || descEnd == "" || endfix.length != 2){
		return;
	}
	$('#beginNoDesc').val(descBegin.substr(0,descBegin.length-2)+endfix);
	$('#endNoDesc').val(descEnd.substr(0,descEnd.length-2)+endfix);
}

//新增，修改  保存
function saveItem(status,formName,operType){
	var form = eval("document.forms."+formName);
	form.action = "card.jhtml?method="+operType+"&status="+status;
}
//查询
function onQuery(formName,method){
	var form = eval("document.forms."+formName);
	form.action = "../admin/card.jhtml?method="+method;
	form.submit();
}
//edit.jsp onload 加载函数
function init(){
	var status =  $('#status').val();
	var appflag = $('#appflag').val();
	if(status != "100"){
		$('#delete').attr({disabled:"true"})
	}
	else if (status == "100" && appflag == "1")
	{
		$('#delete').attr({disabled:"true"})
	}
	
	if(status != "100" && status != "-1" && status != "-2" && status != "-3"){
		$('#batchNo').attr({disabled:"true"});
		$('#agentName').attr({disabled:"true"});
		$('#comboName').attr({disabled:"true"});
		$('#card_num').attr({disabled:"true"});
		$('#applyCount').attr({disabled:"true"});
		$('#endfixStr').attr({disabled:"true"}); 
		$('#feeTypeCode').attr({disabled:"true"}); 
		$('#endDate').attr({disabled:"true"}); 
		$('#beginNoDesc').attr({disabled:"true"}); 
		$('#endNoDesc').attr({disabled:"true"}); 
		$('#remark').attr({disabled:"true"}); 
		$('#batchSummary').attr({disabled:"true"}); 
		$('#save').attr({disabled:"true"})
		$('#commit').attr({disabled:"true"})
		
	}
}



