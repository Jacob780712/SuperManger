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
		<title>门店到店礼遇列表</title>
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
		
		function checkForm() {
	    	document.getElementById("queryFrom").submit();
		}
		
		function showFmtControls(fmt) {
			WdatePicker({dateFmt:fmt});
		}
		
		function updateShip(mchNumber,branchId){
			queryFrom.action="membershipUpdate.jhtml?mchNumber="+mchNumber+"&branchId="+branchId;
			queryFrom.submit();
		}
	
		
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="membershipList.jhtml" method="post">
			<input type="hidden" id="goodsId" name="goodsId"/>
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;门店到店礼遇列表
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="2">
							<strong>查询结果列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								商户名称:${mchName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								商户编号:${mchNumber}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								门店名称:${branch.branch_name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							</strong>
						</th>
						<th align="right" colspan="2">
							<input type="button" onclick="updateShip('${mchNumber}','${branch.id}')" value="修改" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" onclick="javascript:history.back()" value="返回" />
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							礼遇编号
						</td>
						<td align="center">
							礼遇名称
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
								${item.goods_number}
							</td>
							<td align="center">
								${item.goods_name}
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>