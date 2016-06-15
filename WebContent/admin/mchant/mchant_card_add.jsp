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
		<title>储值卡添加</title>
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
		<script type="text/javascript">
			function addAccount(){
				var table = document.getElementById("account_table"); 
				var row = table.insertRow(table.rows.length);
				var td1 = row.insertCell(0);
				td1.align="center";
				var index = table.rows.length-2;
				td1.innerHTML = index;
				var td3 = row.insertCell(1);
				td3.align="center";
				td3.innerHTML="<input type='text' id='account_number"+index+"' required=\"required\" name='account_number"+index+"' />";
				var td4 = row.insertCell(2);
				td4.align="center";
				td4.innerHTML="<select name='authority"+index+"' id='authority"+index+"'><option value='0'>所有权限</option>"+
				  			  "<option value='1'>交易验证</option></select>";
				var td5 = row.insertCell(3);
				td5.align="center";
				td5.innerHTML="<input type='text' id='remark"+index+"' required=\"required\" name='remark"+index+"' />";
				var td6 = row.insertCell(4);
				td6.align="center";
				td6.innerHTML = "<input type='button' value='删除' onclick='removeTr("+(table.rows.length-1)+",\"account_table\")'/>";
				var mchaccount_count = document.getElementById("mchaccount_count");
				mchaccount_count.value=table.rows.length-2;
			}
			
			function addCard(){
				var table = document.getElementById("card_table"); 
				var row = table.insertRow(table.rows.length);
				var td1 = row.insertCell(0);
				td1.align="center";
				var index = table.rows.length-2;
				td1.innerHTML = index;
				var td2 = row.insertCell(1);
				td2.align="center";
				if("${sale_card_type}"=="0"){
					td2.innerHTML="<select id='ck_type"+index+"' name='ck_type"+index+"'>"+
							  "<option value='0'>买送</option></select>";
				}
				if("${sale_card_type}"=="1"){
					td2.innerHTML="<select id='ck_type"+index+"' name='ck_type"+index+"'>"+
							  "<option value='1'>买折</option></select>";
				}
				var td3 = row.insertCell(2);
				td3.align="center";
				td3.innerHTML="<input type='text' id='ck_name"+index+"' required=\"required\" name='ck_name"+index+"' />";
				
				var td4 = row.insertCell(3);
				td4.align="center";
				td4.innerHTML="<input type='text' id='ck_quota"+index+"' required=\"required\" name='ck_quota"+index+"' />";
				var td5 = row.insertCell(4);
				td5.align="center";
				if("${sale_card_type}"=="0"){
					td5.innerHTML="<input type='text' id='sales_amount"+index+"' required=\"required\" onblur='setDiscount("+index+")' name='sales_amount"+index+"' />";
				}
				if("${sale_card_type}"=="1"){
					td5.innerHTML="<input type='text' id='sales_amount"+index+"' required=\"required\" name='sales_amount"+index+"' />";
				}
				var td6 = row.insertCell(5);
				td6.align="center";
				td6.innerHTML="<input type='text' id='discount"+index+"' required=\"required\" name='discount"+index+"' />";
				var td7 = row.insertCell(6);
				td7.align="center";
				td7.innerHTML = "<input type='button' value='删除' onclick='removeTr("+(table.rows.length-1)+",\"card_table\")'/>";
				var mchcard_count = document.getElementById("mchcard_count");
				mchcard_count.value=table.rows.length-2;
			}
			
			function setDiscount(id){
				var ck_quota = document.getElementById("ck_quota"+id).value;
				var sales_amount = document.getElementById("sales_amount"+id).value;
				if(ck_quota==""||sales_amount==""){
					alert("请填写卡面额或购买金额");
					return;
				}
				var ck_type = document.getElementById("ck_type"+id).value;
				if(ck_type==0){
					var dis = sales_amount*1/ck_quota*1;
					document.getElementById("discount"+id).value = dis.toFixed(2);
				}
			}
			
			function removeTr(tr_id,table_id){
				var table = document.getElementById(table_id); 
				table.deleteRow(tr_id);
				var table2 = document.getElementById(table_id); 
				for(var i=2;i<table2.rows.length;i++){
					table2.rows[i].cells[0].innerHTML = i-1;
					if(table_id=="account_table"){
						table2.rows[i].cells[5].innerHTML = "<input type='button' value='删除' onclick='removeTr("+i+",\"account_table\")'/>";
					}else{
						table2.rows[i].cells[7].innerHTML = "<input type='button' value='删除' onclick='removeTr("+i+",\"card_table\")'/>";
					}
				}
			}
			
			function checkMch(){
				var table1 = document.getElementById("account_table"); 
				if(table1.rows.length<3){
					alert("请添加商户账号！")
					return false;
				}
				
				var table2 = document.getElementById("card_table"); 
				if(table2.rows.length<3){
					alert("请添加商户储值卡类型")
					return false;
				}
				return true;
			}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="savemchantCard.jhtml" onsubmit="return checkMch()" method="post">
			<input type="hidden" id="mchantNo" name="mchantNo" value="${mchantNo}"/>
			<input type="hidden" id="mch_name" name="mch_name" value="${mch_name}"/>
			<input type="hidden" id="mchaccount_count" name="mchaccount_count"/>
			<input type="hidden" id="mchcard_count" name="mchcard_count"/>
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;储值卡添加
				</div>
				
				<table class="table_a marginb10">
					<tr>
						<th align="left" colspan="12">
							<span style="float: left" >
								<strong>商户名称：${mch_name}&nbsp;&nbsp;&nbsp;商户编号：${mchantNo}</strong>
							</span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()" />
							</span>
						</th>
					</tr>
				</table>
				<table class="table_a marginb10" id="account_table">
					<tr>
						<th align="left" colspan="6">
							<span style="float: left" >
								商户账号添加
							</span>
							<span style="float: right">
								<input type="button" value="添加" onclick="addAccount()"/>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							账号
						</td>
						<td align="center">
							权限
						</td>
						<td align="center">
							备注
						</td>
						<td align="center">
							操作
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" id="card_table">
					<tr>
						<th align="left" colspan="8">
							<span style="float: left" >
								商户储值卡添加
							</span>
							<span style="float: right">
								<input type="button" value="添加" onclick="addCard()"/>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							卡类型
						</td>
						<td align="center">
							卡种
						</td>
						<td align="center">
							面额
						</td>
						<td align="center">
							购买金额
						</td>
						<td align="center">
							折扣（如0.7表示7折）
						</td>
						<td align="center">
							操作
						</td>
					</tr>
				</table>
				<table class="table_a marginb10">
					<tr>
						<td align="center" colspan="12">
							<input type="submit" value="下一步" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>