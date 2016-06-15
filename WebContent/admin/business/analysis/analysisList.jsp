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
		<title>收益分析列表</title>
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
			if(flag=='3'){
				document.getElementById("nianhua").value= "年化收益 ↓";
			}
			if(flag=='33'){
				document.getElementById("nianhua").value= "年化收益 ↑";
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
		
		function detail(mchName,mchNumber){
			document.getElementById("detailmchName").value=mchName;
			document.getElementById("mchNumber").value=mchNumber;
			frmPage.action="investmentAnalysisDetail.jhtml";
			frmPage.submit();
		}
		</script>
	</head>
	<body onload="init()">
		<form name="frmPage" id="queryFrom" action="investmentAnalysis.jhtml" method="post">
			<input type="hidden" id="orderFlag" name="orderFlag" value="${param.orderFlag}"/>
			<input type="hidden" id="detailmchName" name="detailmchName"/>
			<input type="hidden" id="mchNumber" name="mchNumber"/>
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;储值收益分析
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="12">
							<strong>查询配置</strong>
						</th>
					</tr>
					<tr height="10">
						<td align="left">
							商户名称:
							<input type="text" id="mchName" name="mchName" value="${param.mchName}"/>
						</td>
						<td align="left">
							卡种状态:
							<select id="status" name="status">
								<c:if test="${param.status==null||param.status=='all'}">
									<option value='all' selected="selected">全部</option>
									<option value='0'>上架</option>
									<option value='1'>下架</option>
								</c:if>
								<c:if test="${param.status=='0'}">
									<option value='all'>全部</option>
									<option value='0' selected="selected">上架</option>
									<option value='1'>下架</option>
								</c:if>
								<c:if test="${param.status=='1'}">
									<option value='all'>全部</option>
									<option value='0'>上架</option>
									<option value='1' selected="selected">下架</option>
								</c:if>
							</select>
						</td>
						<td align="left">
							<input type="submit" value="查询"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="12">
							<strong>查询结果列表</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center" onclick="sort(0)">
							<input type="button" id="mchNameSort" name="mchNameSort" value="商户名称" class="butttt"/>
						</td>
						<td align="center">
							卡种
						</td>
						<td align="center">
							储值卡状态
						</td>
						<td align="center">
							可用张数
						</td>
						<td align="center">
							卡面额
						</td>
						<td align="center">
							购卡金额
						</td>
						<td align="center">
							返现收益
						</td>
						<td align="center" onclick="sort(1)">
							<input type="button" id="zhouqi" name="zhouqi" value="返现周期" class="butttt"/>
						</td>
						<td align="center" onclick="sort(2)">
							<input type="button" id="yuehua" name="yuehua" value="月化收益" class="butttt"/>
						</td>
						<td align="center" onclick="sort(3)">
							<input type="button" id="nianhua" name="nianhua" value="年化收益" class="butttt"/>
						</td>
						<td align="center">
							详情
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
								${item.mchName}
							</td>
							<td align="center">
								${item.ck_name}
							</td>
							<td align="center">
								<c:if test="${item.status=='0'}">上架</c:if>
								<c:if test="${item.status=='1'}">下架</c:if>
							</td>
							<td align="center">
								${item.ck_count}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.ck_quota/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.sales_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.shouyi/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.zhouqi}" pattern="##0.0"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.yuehua}" pattern="##0.0"/>%
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.nianhua}" pattern="##0.0"/>%
							</td>
							<td align="center">
								<input type="button" value="详情" onclick="detail('${item.mchName}','${item.mchNumber}')"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>