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
		<title>商户开通购卡详情</title>
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
		<script type="text/javascript">
			function rePay(mchantNo){
				window.location.href="mchantRepay.jhtml?mchantNo="+mchantNo;
			}
		</script>
	</head>
	<body>
		<div>
			<div align="left">
				您现在的位置：商户管理 &gt;&gt;商户开通购卡详情
			</div>
			<table class="table_a marginb10" width="90%">
				<tr>
					<th align="left" colspan="8">
						商户名称:${mchant.mch_name}&nbsp;&nbsp;&nbsp;商户编号:${mchant.mch_number}
					</th>
				</tr>
				<tr class="align_Center">
					<td align="center">
						卡种名称:
					</td>
					<td align="center">
						购买张数:
					</td>
					<td align="center">
						购买金额:
					</td>
					<td align="center">
						状态:
					</td>
					<td align="center">
						操作:
					</td>
				</tr>
				<tr>
					<td align="center">
					 	${openPurchase.ck_name}
					</td>
					<td align="center">
					 	${openPurchase.card_count}
					</td>
					<td align="center">
					 	<fmt:formatNumber value="${openPurchase.sales_amount/100}" pattern="##0.00"/>
					</td>
					<td align="center">
						<c:if test="${openPurchase.status==0}">失败</c:if>
					 	<c:if test="${openPurchase.status==1}">成功</c:if>
					</td>
					<td align="center">
					 	<c:if test="${openPurchase.status==0}">
					 		<input type="button" value="重新购买开通" onclick="rePay('${mchant.mch_number}')"/>
					 	</c:if>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="5">
						<input type="button" value="返回" onclick="javascript:history.back()"/>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>