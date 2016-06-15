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
		<title>提现管理</title>
		<script language="javascript" src="<%=basePath%>scripts/common.js"></script>
		<script language="javascript" src="<%=basePath%>scripts/Windows.js"></script>
		<link href="<%=basePath%>themes/admin/css.css" rel="stylesheet" type="text/css" />
		<LINK href="<%=basePath%>themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="<%=basePath%>themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="<%=basePath%>themes/admin/userhome.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="../scripts/jQuery/jquery-1.4.4.min.js"></script>
		<script src="<%=basePath%>scripts/jQuery/formValidator.js" type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>scripts/jQuery/jquery.loadmask.js"></script>
		<link href="<%=basePath%>themes/admin/jquery.loadmask.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom"  action="<%=basePath %>admin/cstCashOutFinish.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：提现结果表
				</div>
				
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="10">
							<span style="float: left"><strong>提现完成列表</strong></span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							批次号
						</td>
						<td align="center">
							姓名
						</td>
						<td align="center">
							手机号
						</td>
						<td align="center">
							卡号
						</td>
						<td align="center">
							提现金额
						</td>
						<td align="center">
							申请时间
						</td>
						<td align="center">
							状态
						</td>
						<td align="center">
							备注
						</td>
						<td align="center">
							提现id
						</td>
					</tr>
					<c:forEach var="item" items="${resList}" varStatus="status">
						<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
							onMouseout='onTrMoveOut(this)'
							class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
							<td align="center">
								${status.index+1}
							</td>
							<td align="center">
								${item.batch_number}
							</td>
							<td align="center">
								${item.full_name}
							</td>
							<td align="center">
								${item.mobile_phone}
							</td>
							<td align="center">
								${item.card_number}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								 <fmt:formatDate value="${item.create_date}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center">
								<c:if test="${item.status==0}">处理中</c:if>
								<c:if test="${item.status==1}">成功</c:if>
								<c:if test="${item.status==2}">失败</c:if>
							</td>
							<td align="center">
								${item.remark}
							</td>
							<td align="center">
								${item.id}
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</form>
	</body>
</html>