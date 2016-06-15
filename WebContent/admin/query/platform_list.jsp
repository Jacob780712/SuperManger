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
		<title>平台收入每日统计</title>
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
		
		function exportData(){
			frmPage.action="listPlatformExport.jhtml";
			frmPage.submit();
			frmPage.action="listPlatform.jhtml";
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="listPlatform.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;平台收入每日统计
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>查询配置
							</strong>
						</th>
					</tr>
					<tr height="10">
						<td align="left">
							时间范围:
							<input size="8" onclick="showFmtControls('yyyy-MM-dd')" readonly="readonly" id="beginTime"  name="beginTime" value="${param.beginTime }"  type="text" class="input_line" style="width:100px;" />&nbsp;
							到&nbsp;<input id="endTime" readonly="readonly" name="endTime" type="text" value="${param.endTime }"   class="input_line" style="width:100px;" onclick="showFmtControls('yyyy-MM-dd')" size="8"/>
						</td>
						<td align="left">
							<input type="submit" value="查询"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="导出数据" onclick="exportData()"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<td align="center">
							微信手续费总额:<fmt:formatNumber value="${PlatFormRE.weixinAmount/100}" pattern="##0.00"/></td>
						<td align="center">
							提现手续费总额:<fmt:formatNumber value="${PlatFormRE.tixianAmount/100}" pattern="##0.00"/></td>
						<td align="center">
							总支出:<fmt:formatNumber value="${PlatFormRE.zhichu/100}" pattern="##0.00"/></td>
						<td align="center">
							商户储值佣金总额:<fmt:formatNumber value="${PlatFormRE.machantAmount/100}" pattern="##0.00"/></td>
						<td align="center">
							平台返现总额:<fmt:formatNumber value="${PlatFormRE.platAmount/100}" pattern="##0.00"/></td>
						<td align="center">
							总收入:<fmt:formatNumber value="${PlatFormRE.shouru/100}" pattern="##0.00"/></td>
						<td align="center">
							净收入总额:<fmt:formatNumber value="${PlatFormRE.all/100}" pattern="##0.00"/></td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>平台收入每日统计</strong>
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
							微信手续费
						</td>
						<td align="center">
							提现手续费
						</td>
						<td align="center">
							总支出
						</td>
						<td align="center">
							商户储值佣金
						</td>
						<td align="center">
							平台返现
						</td>
						<td align="center">
							总收入
						</td>
						<td align="center">
							当日净收入
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
								${item.date}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.weixinAmount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.tixianAmount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.zhichu/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.machantAmount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.platAmount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.shouru/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.all/100}" pattern="##0.00"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>