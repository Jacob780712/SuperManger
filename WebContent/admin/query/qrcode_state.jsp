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
		<title>渠道统计</title>
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
		
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="qrStatList.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt; 渠道统计
				</div>
				<table class="table_a marginb10" width="90%">
					<tr class="align_Center">
						<td align="center">
							渠道总数：${total.channelCou}
						</td>
						<td align="center">
							关注总数：${total.folCou}
						</td>
						<td align="center">
							注册总数：${total.regCou}
						</td>
						<td align="center">
							交易总人数：${total.billUCou}
						</td>
						<td align="center">
							2次交易以上总人数：${total.twoBillCount}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							购卡总人数：${total.uCarCou}
						</td>
						<td align="center">
							2次购卡以上总人数：${total.twoCardCount}
						</td>
						<td align="center">
							交易总数：${total.billCou}
						</td>
						<td align="center">
							交易总金额：${total.billTotAmount}
						</td>
						<td align="center">
							购卡总张数：${total.cardCou}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							购卡总面额：${total.cardTotQuo}
						</td>
						<td align="center">
							购卡总金额：${total.cardTotPur}
						</td>
						<td align="center">
							账户支付总额：${total.accountTotAmount}
						</td>
						<td align="center">
							账户返现金额：${total.oRetAmount}
						</td>
						<td align="center">
							移动支付总额：${total.wcAmount}
						</td>
					</tr>
				</table>
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
							渠道名称：<input type="text" id="channel" name="channel" value="${param.channel}"/>
						</td>
						<td align="left">
							<input type="submit" value="查询"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="16">
							<strong>每日售卡列表</strong>
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
							渠道名称
						</td>
						<td align="center">
							新增关注数
						</td>
						<td align="center">
							注册人数
						</td>
						<td align="center">
							交易人数
						</td>
						<td align="center">
							首次交易人数
						</td>
						<td align="center">
							交易笔数
						</td>
						<td align="center">
							购卡人数
						</td>
						<td align="center">
							首次购卡人数
						</td>
						<td align="center">
							购卡数量
						</td>
						<td align="center">
							购卡面额
						</td>
						<td align="center">
							购卡金额
						</td>
						<td align="center">
							账户支付金额
						</td>
						<td align="center">
							账户返现金额
						</td>
						<td align="center">
							移动支付金额
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
								<fmt:formatDate value="${item.date}" pattern="yyyy-MM-dd" />
							</td>
							<td align="center">
								${item.channelName}
							</td>
							<td align="center">
								${item.folCou}
							</td>
							<td align="center">
								${item.regCou}
							</td>
							<td align="center">
								${item.billUCou}
							</td>
							<td align="center">
								${item.fOPBCount}
							</td>
							<td align="center">
								${item.billCou}
							</td>
							<td align="center">
								${item.uCarCou}
							</td>
							<td align="center">
								${item.fOPCCount}
							</td>
							<td align="center">
								${item.cardCou}
							</td>
							<td align="center">
								${item.cardTotQuo}
							</td>
							<td align="center">
								${item.cardTotPur}
							</td>
							<td align="center">
								${item.accountTotAmount}
							</td>
							<td align="center">
								${item.oRetAmount}
							</td>
							<td align="center">
								${item.wcAmount}
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>