<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<title>交易详情</title>
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
		<style type="text/css">
			.hrCla{
				color: #AACDED;
				height: 1px;
			}
		</style>
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
			
			function detail(orderNumber){
				frmPage.action="detailBpayBill.jhtml?orderNumber="+orderNumber;
				frmPage.submit();
			}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="listBpayBill.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;交易详情
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="8">
							<span style="float: left" >
								<strong>
									<strong>订单编号:${orderNumber}</strong>
								</strong>
							</span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							储值卡号
						</td> 
						<td align="center">
							储值卡扣除金额
						</td>
						<td align="center">
							储值卡余额
						</td>
						<td align="center">
							储值用户手机号
						</td>
						<td align="center">
							储值用户返现金额
						</td>
					</tr>
					<c:forEach var="item" items="${list}" varStatus="status">
						<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
							onMouseout='onTrMoveOut(this)'
							class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
							<td align="center">
								${status.index+1}
							</td>
							<td align="center">
								<c:if test="${item.svc_number!=null}">${item.svc_number}</c:if>
								<c:if test="${item.co_svc_number!=null}">${item.co_svc_number}</c:if>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.spend_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.svc_balance/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<c:if test="${item.mobile_phone!=null}">${item.mobile_phone}</c:if>
								<c:if test="${item.co_tel!=null}">${item.co_tel}</c:if>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.actual_amount/100}" pattern="##0.00"/>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</form>
	</body>
</html>