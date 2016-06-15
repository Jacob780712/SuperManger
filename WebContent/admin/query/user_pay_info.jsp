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
		<title>用户买单信息</title>
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
		
		function addBlance(){
			var amount=prompt("请输入充值金额,单位元","");
			if(amount==null){
				return;
			}
			if(amount<0||amount==''){
				alert("金额格式不正确")
			}
			else{
				window.location.href="addCoAccountBlance.jhtml?amount="+amount;
			}
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="userPayInfo.jhtml" method="post">
			<input type="hidden" id="user_id" name="user_id" value="${param.user_id}"/>
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;用户买单信息
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="8">
							<strong>
								查询配置
							</strong>
						</th>
						<th align="left" colspan="3">
							<strong>
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</strong>
						</th>
					</tr>
					<tr height="10">
						<td align="left">
							时间范围:
							<input size="8" value="${param.beginTime}" onclick="showFmtControls('yyyy-MM-dd')" readonly="readonly" id="beginTime"  name="beginTime" value="${param.beginTime }"  type="text" class="input_line" style="width:100px;" />&nbsp;
							到&nbsp;<input id="endTime" value="${param.endTime}"  readonly="readonly" name="endTime" type="text" value="${param.endTime }"   class="input_line" style="width:100px;" onclick="showFmtControls('yyyy-MM-dd')" size="8"/>
						</td>
						<td align="left">
							<input type="submit" value="查询"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>用户买单明细</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							日期
						</td>
						<td align="center">
							商户名称
						</td>
						<td align="center">
							门店名称
						</td>
						<td align="center">
							订单金额
						</td>
						<td align="center">
							实扣储值卡金额
						</td>
						<td align="center">
							实扣本人储值卡金额
						</td>
						<td align="center">
							实扣他人储值卡金额
						</td>
						<td align="center">
							账户余额支付金额
						</td>
						<td align="center">
							微信支付金额
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
								${item.mch_name}
							</td>
							<td align="center">
								${item.branchName}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.business_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.svc_pay_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.ownCardAmount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${(item.svc_pay_amount-item.ownCardAmount)/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.account_pay_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.pay_amount/100}" pattern="##0.00"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>