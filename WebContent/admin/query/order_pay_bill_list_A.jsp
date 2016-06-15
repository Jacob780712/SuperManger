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
		<title>储值用户交易明细</title>
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
				color: #BFB7B6;
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
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="listApayBill.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;储值用户交易
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>查询配置</strong>
						</th>
					</tr>
					<tr height="10">
						<td align="left">
							时间范围:
							<input size="8" value="${param.beginTime}" onclick="showFmtControls('yyyy-MM-dd')" readonly="readonly" id="beginTime"  name="beginTime" value="${param.beginTime }"  type="text" class="input_line" style="width:100px;" />&nbsp;
							到&nbsp;<input id="endTime" value="${param.endTime}"  readonly="readonly" name="endTime" type="text" value="${param.endTime }"   class="input_line" style="width:100px;" onclick="showFmtControls('yyyy-MM-dd')" size="8"/>
						</td>
						<td align="left">
							商户名称:
							<input type="text" id="mch_name" name="mch_name" value="${param.mch_name}"/>
						</td>
						<td align="left">
							手机号:
							<input type="text" id="mobile_phone" name="mobile_phone" value="${param.mobile_phone}"/>
						</td>
						<td align="left">
							<input type="submit" value="查询"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							日期
						</td> 
						<td align="center">
							时间
						</td> 
						<td align="center">
							订单号
						</td>
						<td align="center">
							手机号
						</td>
						<td align="center">
							商户名称
						</td>
						<td align="center">
							订单金额
						</td>
						<td align="center">
							储值卡卡号
						</td>
						<td align="center">
							储值卡支付金额
						</td>
						<td align="center">
							储值卡余额
						</td>
						<td align="center">
							账户余额支付金额
						</td>
					</tr>
					<c:forEach var="item" items="${page.result}" varStatus="status">
						<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
							onMouseout='onTrMoveOut(this)'
							class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
							<td align="center">
								${status.index+1}
							</td>
							<td align="center">
								<fmt:formatDate value="${item.create_date}"
									pattern="yyyy-MM-dd" />
							</td>
							<td align="center">
								<fmt:formatDate value="${item.create_date}"
									pattern="HH:mm:ss" />
							</td>
							<td align="center">
								${item.order_number}
							</td>
							<td align="center">
								${item.mobile_phone}
							</td>
							<td align="center">
								${item.mch_name}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.business_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<c:forEach var="item2" items="${item.cardList}" varStatus="status2">
									<c:if test="${item2.svc_number!=null}">${item2.svc_number}</c:if>
									<c:if test="${item2.co_svc_number!=null}">${item2.co_svc_number}</c:if>
									<c:if test="${status2.index<item.listSize-1}">
										<hr class="hrCla">
									</c:if>
								</c:forEach>
							</td>
							<td align="center">
								<c:forEach var="item2" items="${item.cardList}" varStatus="status2">
									<fmt:formatNumber value="${item2.spend_amount/100}" pattern="##0.00"/>
									<c:if test="${status2.index<item.listSize-1}">
										<hr class="hrCla">
									</c:if>
								</c:forEach>
							</td>
							<td align="center">
								<c:forEach var="item2" items="${item.cardList}" varStatus="status2">
									<fmt:formatNumber value="${item2.svc_balance/100}" pattern="##0.00"/>
									<c:if test="${status2.index<item.listSize-1}">
										<hr class="hrCla">
									</c:if>
								</c:forEach>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.account_pay_amount/100}" pattern="##0.00"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>