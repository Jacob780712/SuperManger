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
		<title>商户门店列表</title>
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
		
			//门店详情
			function storeDetail(mchantNo,branchId){
				window.location.href="storeDetail.jhtml?mchantNo="+mchantNo+"&branchId="+branchId;
			}
			
			//门店修改
			function storeUpdatel(mchantNo,branchId){
				window.location.href="storeUpdate.jhtml?mchantNo="+mchantNo+"&branchId="+branchId;
			}
			
			//门店删除
			function storeDel(status,mchantNo,branchId){
				if(status=="0"){
					alert("线上门店不允许删除");
				}else{
					window.location.href="storeDelete.jhtml?mchantNo="+mchantNo+"&branchId="+branchId;
				}
			}
			
			function mchantShipList(mchNumber,branchId){
				window.location.href="mchantShipList.jhtml?mchNumber="+mchNumber+"&branchId="+branchId;
			} 
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="addStore.jhtml"  method="post">
		<input type="hidden" id="mchantNo" name="mchantNo" value="${mchantNo}"/>
		<div>
			<div align="left">
				您现在的位置：商户管理 &gt;&gt;商户门店列表
			</div>
			<table class="table_a marginb10" width="90%">
				<tr>
					<th align="left" colspan="5">
						<strong>商户门店信息</strong>
					</th>
					<th align="right" colspan="5">
						<strong>
							<input type="submit" value="添加"/>
						</strong>
					</th>
				</tr>
				<tr class="align_Center">
					<td align="center">
						序号:
					</td>
					<td align="center">
						门店id:
					</td>
					<td align="center">
						门店名称:
					</td>
					<td align="center">
						状态:
					</td>
					<td align="center">
						联系方式:
					</td>
					<td align="center">
						城市:
					</td>
					<td align="center">
						地址:
					</td>
					<td align="center">
						经度:
					</td>
					<td align="center">
						纬度:
					</td>
					<td align="center">
						操作:
					</td>
				</tr>
				<c:forEach var="item" items="${listBranch}" varStatus="status">
					<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
						onMouseout='onTrMoveOut(this)'
						class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
						<td align="center">
						 	${status.index+1}
						</td>
						<td align="center">
						 	${item.id}
						</td>
						<td align="center">
						 	${item.branch_name}
						</td>
						<td align="center">
							<c:if test="${item.status=='0'}">开通</c:if>
						 	<c:if test="${item.status=='1'}">关闭</c:if>
						 	<c:if test="${item.status=='9'}">已删除</c:if>
						</td>
						<td align="center">
						 	${item.telephone}
						</td>
						<td align="center">
						 	${item.city_name}
						</td>
						<td align="center">
						 	${item.branch_addr}
						</td>
						<td align="center">
						 	${item.longitude}
						</td>
						<td align="center">
						 	${item.latitude}
						</td>
						<td align="center">
							<input type="button" value="到店礼遇" onclick="mchantShipList('${mchantNo}','${item.id}')"/>
							&nbsp;&nbsp;&nbsp;&nbsp;
						 	<input type="button" value="详情" onclick="storeDetail('${mchantNo}','${item.id}')"/>
						 	<c:if test="${item.status!='9'}">
							 	&nbsp;&nbsp;&nbsp;&nbsp;
							 	<input type="button" value="修改" onclick="storeUpdatel('${mchantNo}','${item.id}')"/>
							 	&nbsp;&nbsp;&nbsp;&nbsp;
							 	<input type="button" value="删除" onclick="storeDel('${item.status}','${mchantNo}','${item.id}')"/>
							 	&nbsp;&nbsp;&nbsp;&nbsp;
						 	</c:if>
						</td>
 					</tr>
				</c:forEach>
				<tr>
					<td align="center" colspan="10">
						<input type="button" value="返回" onclick="javascript:history.back()"/>
					</td>
				</tr>
			</table>
		</div>
		</form>
	</body>
</html>