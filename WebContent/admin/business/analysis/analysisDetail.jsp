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
		<title>储值情况汇总</title>
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
			.butttt{
			border:0px;width:100%;height:100%;background-color:#ffffff;
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
		
		function init(){
			var s = "${param.orderFlag}";
			var flag = s.trim();
			if(flag=='0'||flag.length<1){
				document.getElementById("mchNameSort").value= "商户名称 ↓";
			}
			if(flag=='00'){
				document.getElementById("mchNameSort").value= "商户名称 ↑";
			}
			if(flag=='1'){
				document.getElementById("zhouqi").value= "返现周期↓";
			}
			if(flag=='11'){
				document.getElementById("zhouqi").value= "返现周期 ↑";
			}
			if(flag=='2'){
				document.getElementById("yuehua").value= "月化收益 ↓";
			}
			if(flag=='22'){
				document.getElementById("yuehua").value= "月化收益 ↑";
			}
		}
		
		function sort(flag){
			var oldFlag = document.getElementById("orderFlag").value;
			if(flag==oldFlag&&oldFlag.length==1){
				document.getElementById("orderFlag").value= oldFlag+oldFlag;
			}else{
				document.getElementById("orderFlag").value= flag;
			}
			
			frmPage.submit();
		}
		
		function detail(mchName){
			document.getElementById("detailMchName").value=mchName;
			frmPage.action="investmentAnalysisDetail.jhtml";
			frmPage.submit();
		}
		</script>
	</head>
	<body onload="init()">
		<form name="frmPage" id="queryFrom" action="investmentAnalysis.jhtml" method="post">
			<input type="hidden" id="orderFlag" name="orderFlag" value="${param.orderFlag}"/>
			<input type="hidden" id="detailMchName" name="detailMchName"/>
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;储值情况汇总
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>${mchName} 商户的储值情况汇总</strong>
						</th>
						<th align="right" colspan="11">
							<input type="button" value="返回" onclick="javascript:history.back()"/>
						</th>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<td align="center">
							平均客单价
						</td>
						<td align="center">
							平均日单量
						</td>
						<td align="center">
							平均月流水
						</td>
						<td align="center">
							总交易流水
						</td>
					</tr>
					<tr>
						<td align="center">
							<fmt:formatNumber value="${bo.kedan/100}" pattern="##0.00"/>
						</td>
						<td align="center">
							<fmt:formatNumber value="${bo.ridan/100}" pattern="##0.00"/>
						</td>
						<td align="center">
							<fmt:formatNumber value="${bo.yueliu/100}" pattern="##0.00"/>
						</td>
						<td align="center">
							<fmt:formatNumber value="${bo.allAmout/100}" pattern="##0.00"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>&nbsp;</strong>
						</th>
					</tr>
					<tr>
						<td align="center">
							储值总张数
						</td>
						<td align="center">
							储值总面额
						</td>
						<td align="center">
							储值总金额
						</td>
						<td align="center">
							未用完储值卡
						</td>
						<td align="center">
							储值余额
						</td>
						<td align="center">
							返现总金额
						</td>
					</tr>
					<tr>
						<td align="center">
							${bo.zhangshu}
						</td>
						<td align="center">
							<fmt:formatNumber value="${bo.miane/100}" pattern="##0.00"/>
						</td>
						<td align="center">
							<fmt:formatNumber value="${bo.saleAmount/100}" pattern="##0.00"/>
						</td>
						<td align="center">
							${bo.freeZhangshu}
						</td>
						<td align="center">
							<fmt:formatNumber value="${bo.balance/100}" pattern="##0.00"/>
						</td>
						<td align="center">
							<fmt:formatNumber value="${bo.acturlAmout/100}" pattern="##0.00"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>储值情况汇总</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							卡种
						</td>
						<td align="center">
							卡面额
						</td>
						<td align="center">
							购卡金额
						</td>
						<td align="center">
							总张数
						</td>
						<td align="center">
							总面额
						</td>
						<td align="center">
							总购买金额
						</td>
						<td align="center">
							储值余额
						</td>
						<td align="center">
							累计返现
						</td>
					</tr>
					<c:forEach var="item" items="${listTotalResult}" varStatus="status">
						<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
							onMouseout='onTrMoveOut(this)'
							class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
							<td align="center">
								${status.index+1}
							</td>
							<td align="center">
								${item.ck_name}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.miane/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.saleAmount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								${item.zhangshu}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.allMiane/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.allSaleAmount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.balance/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.fanxian/100}" pattern="##0.0"/>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</form>
	</body>
</html>