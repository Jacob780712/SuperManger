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
		<title>商户账号修改</title>
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
		var accountSize = ${accountSize};
		function addAccount(){
			var table = document.getElementById("account_table"); 
			var row = table.insertRow(table.rows.length);
			var td1 = row.insertCell(0);
			td1.align="center";
			var index = table.rows.length-2;
			td1.innerHTML = index
			var td2 = row.insertCell(1);
			td2.align="center";
			td2.innerHTML="<input type='text' id='account_number"+index+"' required=\"required\" name='account_number"+index+"' />";
			var td3 = row.insertCell(2);
			td3.align="center";
			var td4 = row.insertCell(3);
			td4.align="center";
			td4.innerHTML="<select name='authority"+index+"' id='authority"+index+"'><option value='0'>所有权限</option>"+
			  			  "<option value='1'>交易验证</option></select>";
			var td5 = row.insertCell(4);
			td5.align="center";
			td5.innerHTML="<input type='text' id='remark"+index+"' required=\"required\" name='remark"+index+"' />";
			var td6 = row.insertCell(5);
			td6.align="center";
			td6.innerHTML = "<input type='button' value='删除' onclick='removeTr("+(table.rows.length-1)+")'/>";
			var mchaccount_count = document.getElementById("mchaccount_count");
			mchaccount_count.value=table.rows.length-2-accountSize;
		}
		function removeTr(tr_id){
			var table = document.getElementById("account_table"); 
			table.deleteRow(tr_id);
			var mchaccount_count = document.getElementById("mchaccount_count");
			mchaccount_count.value=mchaccount_count.value-1;
			var table2 = document.getElementById("account_table"); 
			for(var i=2;i<table2.rows.length;i++){
				table2.rows[i].cells[0].innerHTML = i-1;
				table2.rows[i].cells[5].innerHTML = "<input type='button' value='删除' onclick='removeTr("+i+")'/>";
			}
		}
		
		function closeAccount(id,mchantNo){
			frmPage.action="updateMchAccountStatus.jhtml?status=1&mchantNo="+mchantNo+"&accountId="+id;
			frmPage.submit();
			
		}
		function openAccount(id,mchantNo){
			frmPage.action="updateMchAccountStatus.jhtml?status=0&mchantNo="+mchantNo+"&accountId="+id;
			frmPage.submit();
			
		}
		</script>
	</head>
	<body>
		<div>
			<form action="excuteAccountUpdate.jhtml" name="frmPage" method="post">
				<input type="hidden" name="mchantNo" id="mchantNo" value="${mchant.mch_number}"/>
				<input type="hidden" name="mchaccount_count" id="mchaccount_count" value="0"/>
				<input type="hidden" name="mchaccount_count_old" id="mchaccount_count_old" value="${accountSize}"/>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;商户账号修改
				</div>
				<table class="table_a marginb10" width="90%" id="account_table">
					<tr>
						<th align="left" colspan="9">
							<span style="float: left" >
								<strong>商户名称：${mchant.mch_name}&nbsp;&nbsp;&nbsp;商户编号：${mchant.mch_number}</strong>
							</span>
							<span style="float: right" >
								<input type="button" value="添加" onclick="addAccount()"/>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号:
						</td>
						<td align="center">
							账号:
						</td>
						<td align="center">
							状态:
						</td>
						<td align="center">
							权限:
						</td>
						<td align="center">
							备注:
						</td>
						<td align="center">
							操作:
						</td>
					</tr>
					<c:forEach var="item" items="${listMchantAccount}" varStatus="status">
						<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
							onMouseout='onTrMoveOut(this)'
							class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
							<td align="center">
							 	${status.index+1}
							</td>
							<td align="center">
							 	${item.account_number}
							</td>
							<td align="center">
							 	<c:if test="${item.status==0}">开通</c:if>
							 	<c:if test="${item.status==1}">关闭</c:if>
							 	<c:if test="${item.status==2}">未改密</c:if>
							</td>
							<td align="center">
							 	<c:if test="${item.authority==0}">
							 		所有权限
							 	</c:if>
							 	<c:if test="${item.authority==1}">
							 		交易验证
							 	</c:if>
							</td>
							<td align="center">
							 	${item.remark}
							</td>
							<td align="center">
								<c:if test="${item.status==0}">
									<input type="button" value="关闭" onclick="closeAccount('${item.id}','${mchant.mch_number}')"/>
								</c:if>
								<c:if test="${item.status==1}">
									<input type="button" value="开通" onclick="openAccount('${item.id}','${mchant.mch_number}')"/>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<td align="center" colspan="8">
							<input type="submit" value="提交"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="返回" onclick="javascript:history.back()"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>