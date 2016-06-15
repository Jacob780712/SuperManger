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
		<title>城市列表</title>
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
			.ddiv{
				display: none;
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
		
		function closeCity(cityId,cityName){
			var t = window.confirm("确定关闭"+cityName+"?");
			if(t){
				document.getElementById("cityId").value= cityId;
				frmPage.action="changeCity.jhtml?flag=close";
				frmPage.submit();
			}
		}
		
		function openCity(cityId,cityName){
			var t = window.confirm("确定开通"+cityName+"?");
			if(t){
				document.getElementById("cityId").value= cityId;
				frmPage.action="changeCity.jhtml?flag=open";
				frmPage.submit();
			}
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="cityList.jhtml" method="post">
			<input type="hidden" id="cityId" name="cityId" />
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;城市列表
				</div>
				<table class="table_a marginb10" width="90%">
				<th align="left" colspan="12">
					<strong>查询配置
					</strong>
				</th>
					<tr height="10">
					<td align="left">
						<strong>城市名称:</strong>
						<input type="text" id="cityName" name="cityName" value="${param.cityName}"/>
					</td>
					<td align="left">
						<input type="submit" value="查 询" />
					</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<th align="left" colspan="7">
						<strong>查询结果列表</strong>
					</th>
					<tr class="align_Center">
						<td align="center">
							<strong>序号</strong>
						</td>
						<td align="center">
							<strong>城市名称</strong>
						</td>
						<td align="center">
							<strong>状态</strong>
						</td>
						<td align="center">
							<strong>操作</strong>
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
								${item.city_name}
							</td>
							<td align="center">
								<c:if test="${item.status==0}" >开通</c:if>
							 	<c:if test="${item.status==1}" >关闭</c:if>
							</td>
							<td align="center">
								<c:if test="${item.status==0}" >
									<input type="button" value="关闭" onclick="closeCity('${item.city_id}','${item.city_name}')"/>
								</c:if>
								<c:if test="${item.status==1}" >
									<input type="button" value="开通" onclick="openCity('${item.city_id}','${item.city_name}')"/>
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