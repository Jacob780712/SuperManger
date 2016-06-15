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
		<title>公司账户收支详情</title>
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
		<form name="frmPage" id="queryFrom" action="coAccountDetail.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;公司账户收支详情
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="12">
							<strong>查询配置
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							日期:${date}
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							类型:
							<c:if test="${type=='0'}">收入</c:if>
							<c:if test="${type=='1'}">支出</c:if>
							</strong>
						</th>
						<th align="right" colspan="12">
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
					
				</table>
			
				<table class="table_a marginb10" id="tab" width="90%">
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							时间
						</td>
						<td align="center">
							商户名称
						</td>
						<td align="center">
							金额
						</td>
						<td align="center">
							动态余额
						</td>
					</tr>
					<c:forEach var="item" items="${page.result}" varStatus="status">
						<tr>
							<td align="center">
								${status.index+1}
							</td>
							<td align="center">
								<fmt:formatDate value="${item.create_date}"
									pattern="HH:mm:ss" />
							</td>
							<td align="center">
								<c:if test="${type=='0'}">
									${item.shouruMchName}
								</c:if>
								<c:if test="${type=='1'}">
									${item.buyCardMchName}
								</c:if>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.account_balance/100}" pattern="##0.00"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>