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
			.cardDiv{
				display: none;
			}
		</style>
		<script type="text/javascript">
		
			function changeAmout(amount){
				document.getElementById("sales_amount").value=amount;
			}
			
			function setAmount(){
				var ck_id = document.getElementById("ck_id").value;
				if(ck_id==-1){
					alert("请选择卡种");
					return;
				}
				var sales_amount = document.getElementById("sales_amount").value;
				var card_count = document.getElementById("card_count").value;
				var cardAmount = (sales_amount*1)*(card_count*1)/100;
				document.getElementById("card_amount").value = cardAmount;
			}
			
			function checkT(){
				var card_count = document.getElementById("card_count").value;
				var ck_id = document.getElementById("ck_id").value;
				if(ck_id==-1){
					alert("请选择卡种");
					return false;
				}
				if(card_count==""||card_count==null){
					alert("请输入购卡张数");
					return false;
				}
					return true;
				
			}
			
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="updateMchantStatus.jhtml" method="post" onsubmit="return checkT()">
			<input type="hidden" id="mchantNo" name="mchantNo" value="${mchantNo}"/>
			<input type="hidden" id="mchantName" name="mchantName" value="${mchantName}"/>
			<input type="hidden" id="sales_amount" name="sales_amount" />
			<div>
				<div align="left">
					您现在的位置：商户添加&gt;&gt;修改商户状态
				</div>
				<table class="table_a marginb10" id="store_table">
					<tr>
						<th align="left" colspan="3">
							<span style="float: left" >
								<strong>商户名称：${mch_name}&nbsp;&nbsp;&nbsp;商户编号：${mchantNo}</strong>
							</span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
					<tr>
						<td align="center">
							卡种:
							<select id="ck_id" name="ck_id">
								<option value="-1">请选择卡种</option>
								<c:forEach var="item" items="${listCards}" varStatus="status">
									<option onclick="changeAmout('${item.sales_amount}')" value="${item.id}">${item.ck_name}</option>
								</c:forEach>
							</select>
						</td>
						<td align="center">
							&nbsp;&nbsp;&nbsp;
							张数:
							<input type="text" id="card_count" min="0" name="card_count" required="required" onblur="setAmount()"/>
						</td>
						<td align="center">
							&nbsp;&nbsp;&nbsp;
							金额（元）:
							<input type="text" id="card_amount"  name="card_amount" required="required"/>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="3">
							<input type="submit" value="购卡开通"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>