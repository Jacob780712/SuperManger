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
		<title>商户添加</title>
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
		function addCityTags(){
			
		}
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
			if(province==0||city==0){
				alert("请选择省市");
				return;
			}
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
		function check(){
			if(document.getElementById("cityString").value==""){
				alert("请选择城市标签！")
				return false;
			}else{
				return true;
			}
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="savemchant.jhtml" method="post" onsubmit="return check()"
			enctype="multipart/form-data">
			<input type="hidden" id="mchantNo" name="mchantNo" value="${mchantNo}"/>
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;新增商户信息
				</div>
				
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="3">
							<span style="float: left" >
								<strong>商户基本信息</strong>
							</span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户名称
						</td>
						<td align="left" colspan="2">
							<input type="text" id="mch_name" name="mch_name" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户类型
						</td>
						<td align="left" colspan="2">
							<select id="mch_group_id" name="mch_group_id">
								<c:forEach var="item" items="${mchGroupList}" varStatus="status">
									<option value="${item.mch_group_id}">${item.group_name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							城市标签
						</td>
						<td align="center" width="40%">
							<div class="cityDiv" id="city_symbol"></div>
							<input type="hidden" id="cityString" name="cityString" readonly="readonly" required="required"/>
							<input type="hidden" id="cityNameString" name="cityNameString" readonly="readonly" required="required"/>
						</td>
						<td align="center">
							省份
							<select id="selProvince" name="mchProvice">
								<option value="0">--请选择省份--</option>
							</select>
							城市
							<select id="selCity" name="mchCity">
								<option value="0">--请选择城市--</option>
							</select>
							<input type="button" value="确定" onclick="addCitys()"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							使用说明（换行用p标签)
						</td>
						<td align="center" colspan="4">
							<input style="width:80%;" type="text" id="use_note" name="use_note" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							储值卡类型
						</td>
						<td align="center" colspan="4">
							<select id="sale_card_type" name="sale_card_type">
								<option value="0">买送卡</option>
								<option value="1">买折卡</option>
							</select>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							人均消费金额（单位元）
						</td>
						<td align="center" colspan="4">
							<input style="width:50%;" type="text" id="capita_consumption" name="capita_consumption" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							折扣说明（如：买单最高立减18%,买储值卡最高享30%折扣）
						</td>
						<td align="center" colspan="4">
							<input style="width:50%;" type="text" id="discount_note" name="discount_note" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							普通用户买单折扣（如90%，填写90）
						</td>
						<td align="center" colspan="4">
							<input style="width:10%;" type="text" id="mch_min_discount" name="mch_min_discount" required="required"/>%
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							最大折扣（如30%）
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_max_discount" name="mch_max_discount" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							结算手续费（如0.1）
						</td>
						<td align="center" colspan="4">
							<input type="text" id="sale_poundage" name="sale_poundage" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户关键词
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_key_word" name="mch_key_word" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户图片
						</td>
						<td align="center" colspan="4">
							<input type="file" id="pic_url" name="pic_url" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户logo
						</td>
						<td align="center" colspan="4">
							<input type="file" id="pic_url_logo" name="pic_url_logo"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							营业时间（如 09:00-18:00）
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_time_sale" name="mch_time_sale" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							所属商圈
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_biz_districts" name="mch_biz_districts" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							储值卡使用范围（哪类商品无法使用）
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_card_range" name="mch_card_range" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户简介
						</td>
						<td align="center" colspan="4">
							<input type="text" id="mch_more" name="mch_more" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							售卡权限
						</td>
						<td align="center" colspan="4">
							<input type="radio" value="0" name="sale_card_status" id="sale_card_status" checked="checked"/>开通
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="1" name="sale_card_status" id="sale_card_status"/>关闭
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="2">
							<span style="float: left" >
								<strong>商户工商信息</strong>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户工商注册名
						</td>
						<td align="center">
							<input type="text" id="mch_indus_name" name="mch_indus_name" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							营业期限
						</td>
						<td align="center">
							<input type="text" id="mch_oper_per" name="mch_oper_per" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户法人
						</td>
						<td align="center">
							<input type="text" id="mch_leg_person" name="mch_leg_person" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center" colspan="2">
							<input type="submit" value="下一步"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>