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
		<title>商户品牌信息列表</title>
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
	
		function addmchant(){
			window.location.href="addmchant.jhtml";
		}
		
		function checkForm() {
	    	document.getElementById("queryFrom").submit();
		}
		
		function showFmtControls(fmt) {
			WdatePicker({dateFmt:fmt});
		}
		//新增品牌信息
		function add(mchNumber){
			window.location.href="addBrand.jhtml?mchantNo="+mchNumber;
		}
		//修改品牌信息
		function see(mchNumber){
			window.location.href="brandDetail.jhtml?mchantNo="+mchNumber;		
		}
		//删除品牌信息
		function del(mchNumber){
			window.location.href="delBrand.jhtml?mchantNo="+mchNumber;
		}
	
		
	
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="mchantBrandList.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;商户品牌信息列表
				</div>
				<table class="table_a marginb10" width="90%">
				<th align="left" colspan="12">
					<strong>查询配置</strong>
				</th>
					<tr height="10">
					<td align="left">
						<strong>商户名称:</strong>
						<input type="text" id="mchantName" name="mchantName" value="${param.mchantName}"/>
					</td>
					<td align="left">
						<strong>商户编号:</strong>
					 	<input  type="text" id="mchantNo" name="mchantNo"  value="${param.mchantNo}"/>
					</td>
					<td align="left">
						<input type="button" onclick="checkForm()" value="查 询" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<th align="left" colspan="7">
						<strong>查询结果列表</strong>
					</th>
					<tr class="align_Center">
						<td width="4%">
							<strong>序号</strong>
						</td>
						<td width="7%">
							<strong>商户代码</strong>
						</td>
						<td width="4%">
							<strong>商户名称</strong>
						</td>
						<td width="10%">
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
								${item.mch_number}
							</td>
							<td align="center">
								${item.mch_name}
							</td>
							<td align="center">
								<c:if test="${item.brandId>0}">
									<input type="button" value="删除" onclick="del('${item.mch_number}')"/>
									<input type="button" value="查看" onclick="see('${item.mch_number}')"/>
								</c:if>
								<c:if test="${item.brandId==null}">
									<input type="button" value="添加" onclick="add('${item.mch_number}')"/>
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