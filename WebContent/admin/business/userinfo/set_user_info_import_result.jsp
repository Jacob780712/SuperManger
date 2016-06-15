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
		<title>银行卡验证结果</title>
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
		
		function gotoPage(pageNo,callSubmit)
		{
		    document.getElementById("pageNo").value= pageNo;
			if(callSubmit==1)
			{
				frmPage.submit();		
			}
		}
	
		
		function showFmtControls(fmt) {
			WdatePicker({dateFmt:fmt});
		}
		
		function reject(cardNumber){
			var t = window.confirm("确定人工驳回该笔提现申请？");
			if(t){
				document.getElementById("cardNumber").value=cardNumber;
				frmPage.action="rejectBank.jhtml";
				frmPage.submit();
			}
		}
		
		function exportExl(){
			window.location="exportVerification.jhtml";
		}
		
		function importExl(){
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="cashOutList.jhtml" method="post">
			<input type="hidden" id="cardNumber" name="cardNumber"/>
			<div>
				<div align="left">
					您现在的位置：业务管理 &gt;&gt;银行卡验证结果
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>银行卡验证结果</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							姓名
						</td>
						<td align="center">
							手机号
						</td>
						<td align="center">
							身份证号
						</td>
						<td align="center">
							卡号
						</td>
						<td align="center">
							结果
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							${userName}
						</td>
						<td align="center">
							${mobliePhone}
						</td>
						<td align="center">
							${idCard}
						</td>
						<td align="center">
							${cardNumber}
						</td>
						<td align="center">
							<c:if test="${flag=='1'}">验证并设置信息成功</c:if>
							<c:if test="${flag=='0'}">银行卡验证失败</c:if>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>