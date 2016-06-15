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
		<title>售卡详情</title>
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
		
		function cardSaleDetail(date){
			frmPage.action="saleCardTotal.jhtml?detailDate="+date;
			frmPage.submit();
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="saleCardTotal.jhtml?detailDate=1" method="post">
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;售卡详情
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="7">
						<span style="float: left" >
								<strong>
									<strong>查询配置</strong>
								</strong>
							</span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
					<tr height="10">
						<td align="left">
							商户名称:
							<input type="text" name="mch_name" id="mch_name" value="${param.mch_name}"/>
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
							商户名称
						</td> 
						<td align="center">
							商户编号
						</td> 
						<td align="center">
							卡种类型
						</td>
						<td align="center">
							卡种名称
						</td>
						<td align="center">
							售卡张数
						</td>
						<td align="center">
							售卡金额
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
								${item.mch_name}
							</td>
							<td align="center">
								${item.mchantNo}
							</td>
							<td align="center">
								<c:if test="${item.ck_type==0}">买送</c:if>
								<c:if test="${item.ck_type==1}">折扣</c:if>
							</td>
							<td align="center">
								${item.ck_name}
							</td>
							<td align="center">
								${item.allCount}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.amout/100}" pattern="##0.00"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>