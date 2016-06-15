<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<title>商户修改</title>
		<script language="javascript" src="<%=basePath%>scripts/common.js"></script>
		<script language="javascript" src="<%=basePath%>scripts/Windows.js"></script>
		<link href="<%=basePath%>themes/admin/css.css" rel="stylesheet" type="text/css" />
		<LINK href="<%=basePath%>themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="<%=basePath%>themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="<%=basePath%>themes/admin/userhome.css" type=text/css rel=stylesheet>
		<script src="<%=basePath%>scripts/jQuery/jquery-1.2.6.js" type="text/javascript"></script>
		<script src="<%=basePath%>scripts/jQuery/formValidator.js" type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>scripts/jQuery/jquery.loadmask.js"></script>
		<link href="<%=basePath%>themes/admin/jquery.loadmask.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="../scripts/dateNew/My97DatePicker/date.js"></script>
		<script language="javascript" src="../scripts/dateNew/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../scripts/zgjson/province.js"></script>
		<script type="text/javascript" src="../scripts/zgjson/city.js"></script>
		<script type="text/javascript" src="../scripts/zgjson/District.js"></script>
		<style type="text/css">
			.cityDiv{
				width: 400px;
				height: auto !important;
			}
		</style>
		<script type="text/javascript">
		$(function () {
            $.each(province, function (k, p) { 
                var option = "<option value='" + p.ProID + "'>" + p.ProName + "</option>";
                $("#selProvince").append(option);
           });
         
       		 $("#selProvince").change(function () {
            var selValue = $(this).val(); 
            $("#selCity option:gt(0)").remove();
			 $("#selDistrict option:gt(0)").remove(); 
             
            $.each(city, function (k, p) { 
                if (p.ProID == selValue) {
                    var option = "<option value='" + p.CityID + "'>" + p.CityName + "</option>";
                    $("#selCity").append(option);
                }
            });
        });
         
        $("#selCity").change(function () {
            var selValue = $(this).val();
                $("#selDistrict option:gt(0)").remove(); 
                $.each(District, function (k, p) {
                	  if(p.DisName!='市辖区'){
                    if (p.CityID == selValue) {
                        var option = "<option value='" + p.Id + "'>" + p.DisName + "</option>";
                        $("#selDistrict").append(option);
                    }
                  }
                }); 
        	}); 
        });
		
		function addCitys(){
			var provinceCtr = document.getElementById("selProvince");
			var province = provinceCtr.value;
			var provinceTxt = provinceCtr.options[provinceCtr.options.selectedIndex].text;
			var cityCtr = document.getElementById("selCity");
			var city = cityCtr.value;
			var cityTxt = cityCtr.options[cityCtr.options.selectedIndex].text;
			var city_symbol = document.getElementById("city_symbol");//显示的省市
			var cityNameString = document.getElementById("cityNameString")//城市名称
			var cityString = document.getElementById("cityString");//隐藏的省市id
			if(isExitsCity(cityString.value,city)){
				alert("该城市已存在，请勿重复添加！");
			}
			else{
				var but = "<input type='button' value='"+cityTxt+"' id='"+
					province+"-"+city+"' onclick='removess(\""+city+"\",\""+province+"-"+city+"\",\""+city+"-"+cityTxt+"\")'/>";
				if(city_symbol.innerHTML==""){
					city_symbol.innerHTML = but;
					cityNameString.value = city+"-"+cityTxt;
				}else{
					city_symbol.innerHTML = city_symbol.innerHTML+but;
					cityNameString.value = cityNameString.value + "," + city+"-"+cityTxt;
				}
				if(cityString.value==""){
					cityString.value = city;
				}else{
					cityString.value = cityString.value+","+city;
				}
			}
		}
		function isExitsCity(oldCitys,newCity){
			if(oldCitys.indexOf(newCity)>-1){
				return true;
			}else{
				return false;
			}
		}
		
		function removess(cityId,butId,cityTx){
			var t = window.confirm("是否删除该城市？");
			if(t){
				var inputCtr = document.getElementById(butId);
				inputCtr.remove();
				var cityString = document.getElementById("cityString").value;
				document.getElementById("cityString").value = cityString.replace(cityId,"");
				var tmpStr = document.getElementById("cityString").value;
				if(tmpStr.substr(tmpStr.length-1,1)==","){
					document.getElementById("cityString").value = tmpStr.substr(0,tmpStr.length-1);
					
				}
				var tmpStr2 = document.getElementById("cityString").value;
				if(tmpStr2.substr(0,1)==","){
					document.getElementById("cityString").value = tmpStr2.substr(1,tmpStr2.length-1);
					
				}
				var cityNameString = document.getElementById("cityNameString").value;
				document.getElementById("cityNameString").value = cityNameString.replace(cityTx,"");
				
				var tmpStrName = document.getElementById("cityNameString").value
				if(tmpStrName.substr(tmpStrName.length-1,1)==","){
					document.getElementById("cityNameString").value = tmpStrName.substr(0,tmpStrName.length-1);
					
				}
				var tmpStrName2 = document.getElementById("cityNameString").value
				if(tmpStrName2.substr(0,1)==","){
					document.getElementById("cityNameString").value = tmpStrName2.substr(1,tmpStrName2.length-1);
					
				}
			}
		}
		
		function seePic(url){
			window.location.href="mchantSeepic.jhtml?imgUrl="+url;
		}
		
		function init(){
			var cityList = ${Cityjson};
			var centent = "";
			var cityName = "";
			for(var i=0;i<cityList.length;i++){
				if(i==cityList.length-1){
					centent = centent + cityList[i].city_id;
					cityName = cityName + cityList[i].city_id+"-"+cityList[i].city_name;
				}else{
					centent = centent + cityList[i].city_id+",";
					cityName = cityName + cityList[i].city_id+"-"+cityList[i].city_name+","
				}
				
			}
			document.getElementById("cityString").value = centent;
			document.getElementById("cityNameString").value = cityName;
		}
		
		
		function updateInfo(flag){
			if(flag=="store"){
				//修改门店信息
				frmPage.action="storeUpdate.jhtml";
				frmPage.submit();
			}
			if(flag=="account"){
				//修改商户账号		
				frmPage.action="accountUpdate.jhtml";
				frmPage.submit();
			}
			if(flag=="card"){
				//修改储值卡信息
				frmPage.action="cardUpdate.jhtml";
				frmPage.submit();
			}	
			if(flag=="allot"){
				//修改储值卡信息
				frmPage.action="allotUpdate.jhtml";
				frmPage.submit();
			}
			if(flag=="stmConfig"){
				//修改储值卡信息
				frmPage.action="astmConfigUpdate.jhtml";
				frmPage.submit();
			}
		}
		</script>
	</head>
	<body onload="init()">
		<form name="frmPage" id="queryFrom" action="excuteMchantUpdate.jhtml" method="post" 
			enctype="multipart/form-data">
			<input type="hidden" name="mchant_number" id="mchant_number" value="${mchant.mch_number}"/>
			<input type="hidden" name="id" id="id" value="${mchant.id}"/>
			<input type="hidden" name="oldCity" id="oldCity" value='${Cityjson}' />
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;商户修改
				</div>
				
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="12">
							<span style="float: left" >
								<strong>商户名称：${mchant.mch_name}&nbsp;&nbsp;&nbsp;商户编号：${mchant.mch_number}</strong>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户名称
						</td>
						<td align="left">
							<input type="text" id="mch_name" name="mch_name" value="${mchant.mch_name}" required="required"/>
						</td>
						<td align="center">
							商户类型
						</td>
						<td align="center">
							<select id="mch_group_id" name="mch_group_id">
								<c:forEach var="item" items="${mchGroupList}" varStatus="status">
									<c:if test="${item.mch_group_id==mchant.mch_group_id}">
										<option value="${item.mch_group_id}" selected="selected">${item.group_name}</option>
									</c:if>
									<c:if test="${item.mch_group_id!=mchant.mch_group_id}">
										<option value="${item.mch_group_id}">${item.group_name}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							城市标签
						</td>
						<td align="center" width="40%">
							<div class="cityDiv" id="city_symbol">
								<c:forEach var="item" items="${listCity}" varStatus="status">
									<input type="button" value="${item.city_name}"/>&nbsp;
								</c:forEach>
							</div>
							<input type="hidden" id="cityString" name="cityString" readonly="readonly" required="required"/>
							<input type="hidden" id="cityNameString" name="cityNameString" readonly="readonly" required="required"/>
						</td>
						<td align="center">
							省份
							<select id="selProvince" name="mchProvice">
								<option value="0">--请选择省份--</option>
							</select>
						</td>
						<td align="center">
							城市
							<select id="selCity" name="mchCity">
								<option value="0">--请选择城市--</option>
							</select>
						</td>
						<td align="center">
							<input type="button" value="确定" onclick="addCitys()"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							使用说明
						</td>
						<td align="center" colspan="4">
							<input style="width: 80%" type="text" id="use_note" name="use_note" value="${mchant.use_note}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							储值卡类型
						</td>
						<td align="center" colspan="4">
							<c:if test="${mchant.sale_card_type=='0'}">买送卡</c:if>
							<c:if test="${mchant.sale_card_type=='1'}">买折卡</c:if>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							人均消费（单位元）
						</td>
						<td align="center" colspan="4">
							<input style="width: 50%" type="text" id="capita_consumption" name="capita_consumption" value="${mchant.capita_consumption}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							折扣说明
						</td>
						<td align="center" colspan="4">
							<input style="width: 50%" type="text" id="discount_note" name="discount_note" value="${mchant.discount_note}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							普通用户买单折扣（如90%，填写90）
						</td>
						<td align="center" colspan="4">
							<input style="width:10%;" type="text" id="mch_min_discount" name="mch_min_discount" value="${mchant.mch_min_discount}" required="required"/>%
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							最大折扣（如30%，填写30%）
						</td>
						<td align="center" colspan="4">
							<input style="width:10%;" type="text" id="mch_max_discount" name="mch_max_discount" value="${mchant.mch_max_discount}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							结算手续费（如0.1）
						</td>
						<td align="center" colspan="4">
							<input type="text" id="sale_poundage" name="sale_poundage" value="${mchant.sale_poundage}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户关键字
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_key_word" name="mch_key_word" value="${mchant.mch_key_word}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户图片
						</td>
						<td align="center" colspan="4">
							<input type="button" value="查看" onclick="seePic('${mchPic}')"/>&nbsp;&nbsp;
							请点击“浏览”进行修改<input type="file" id="pic_url" name="pic_url" />
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户logo
						</td>
						<td align="center" colspan="4">
							<input type="button" value="查看" onclick="seePic('${mchant.pic_url_logo}')"/>&nbsp;&nbsp;
							请点击“浏览”进行修改<input type="file" id="pic_url_logo" name="pic_url_logo" />
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户营业时间
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_time_sale" name="mch_time_sale" value="${mchant.mch_time_sale}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							所属商圈
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_biz_districts" name="mch_biz_districts" value="${mchant.mch_biz_districts}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							储值卡使用范围（哪类商品无法使用）
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_card_range" name="mch_card_range" value="${mchant.mch_card_range}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户简介
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_more" name="mch_more" value="${mchant.mch_more}"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户状态
						</td>
						<td align="center" colspan="4">
							<select name="status" id=" status">
								<c:if test="${mchant.status==0}">
									<option value="0" selected="selected">开通</option>
									<option value="1">关闭</option>
								</c:if>
								<c:if test="${mchant.status==1}">
									<option value="0">开通</option>
									<option value="1" selected="selected">关闭</option>
								</c:if>
							</select>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							售卡权限
						</td>
						<td align="center" colspan="4">
							<select name="sale_card_status" id=" sale_card_status">
								<c:if test="${mchant.sale_card_status==0}">
									<option value="0" selected="selected">开通</option>
									<option value="1">关闭</option>
								</c:if>
								<c:if test="${mchant.sale_card_status==1}">
									<option value="0">开通</option>
									<option value="1" selected="selected">关闭</option>
								</c:if>
							</select>
						</td>
					</tr>
					<tr>
						<th align="left" colspan="12">
							<span style="float: left" >
								<strong>商户工商信息</strong>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户工商注册名
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_indus_name" name="mch_indus_name" value="${mchant.mch_indus_name}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户营业期限
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_oper_per" name="mch_oper_per" value="${mchant.mch_oper_per}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户法人
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_leg_person" name="mch_leg_person" value="${mchant.mch_leg_person}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center" colspan="5">
							<input type="submit" value="提交"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>