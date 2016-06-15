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
		<title>储值卡使用详情</title>
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
		
		function cardList(user_id){
			frmPage.action="bankCardList.jhtml?user_id="+user_id;
			frmPage.submit();
		}		
		
		function userDetail(fullName,mobile,mchName,ck_name,cardNumber){
			document.getElementById("fullName").value= fullName;
			document.getElementById("mobile").value= mobile;
			document.getElementById("mchName").value= mchName;
			document.getElementById("ck_name").value= ck_name;
			document.getElementById("cardNumber").value= cardNumber;
			frmPage.action="cardUserDetail.jhtml";
			frmPage.submit();
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="prepaidCardDetails.jhtml" method="post">
			<input type="hidden" id="fullName" name="fullName"/>
			<input type="hidden" id="mobile" name="mobile"/>
			<input type="hidden" id="mchName" name="mchName"/>
			<input type="hidden" id="ck_name" name="ck_name"/>
			<input type="hidden" id="cardNumber" name="cardNumber"/>
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;储值卡使用详情
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="6">
							<span style="float: left" >
								<strong>
									<strong>储值卡使用详情</strong>
								</strong>
							</span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
					<tr>
						<td colspan="6">
							${fullName}&nbsp;${mobile}在${mchName}的${ck_name} ${cardNumber}
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							日期时间
						</td> 
						<td align="center">
							交易类型
						</td>
						<td align="center">
							交易金额
						</td>
						<td align="center">
							储值卡余额
						</td>
						<td align="center">
							储值用户返现
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
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center">
								<c:if test="${item.type=='0'}">自用</c:if>
								<c:if test="${item.type=='1'}">他人使用</c:if>
								<c:if test="${item.type=='2'}">购卡</c:if>
								<c:if test="${item.type=='3'}">充值</c:if>
							</td>
							<td align="center">
								<c:if test="${item.type=='0'}">
									<fmt:formatNumber value="${item.spend_amount/100}" pattern="##0.00"/>
								</c:if>
								<c:if test="${item.type=='1'}">
									<fmt:formatNumber value="${item.spend_amount/100}" pattern="##0.00"/>
								</c:if>
								<c:if test="${item.type=='2'}">
									<fmt:formatNumber value="${sales_amount/100}" pattern="##0.00"/>
								</c:if>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.svc_balance/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<c:if test="${item.type=='1'}">
									<fmt:formatNumber value="${item.actual_amount/100}" pattern="##0.00"/>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>