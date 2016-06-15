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
		<title>设置用户信息</title>
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
		
		function importPage(){
			if(checkForm()){
				frmPage.action="importUserBankCard.jhtml";
				frmPage.submit();
			}
			
		}
		
		function checkForm(){
			  var userName = document.getElementById("userName").value;
			  var mobliePhone = document.getElementById("mobliePhone").value;
			  var cardNumber = document.getElementById("cardNumber").value;
			  var idCard = document.getElementById("idCard").value;
			  if(userName==""){
				  alert("用户姓名不能为空");
				  return false;
			  }
			  if(mobliePhone==""){
				  alert("用户手机号不能为空");
				  return false;
			  }
			  if(cardNumber==""){
				  alert("用户银行卡号不能为空");
				  return false;
			  }
			  if(idCard==""){
				  alert("用户身份证号不能为空");
				  return false;
			  }
			  return true;
		}
		
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="setUserInfo.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;设置用户信息
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>用户信息</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							用户姓名
						</td>
						<td align="center">
							<input type="text" id="userName" name="userName" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							用户手机号
						</td>
						<td align="center">
							<input type="text" id="mobliePhone" name="mobliePhone" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							用户银行卡号
						</td>
						<td align="center">
							<input type="text" id="cardNumber" name="cardNumber" style="width:300px;" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							用户身份证号
						</td>
						<td align="center">
							<input type="text" id="idCard" name="idCard" style="width:300px;" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center" colspan="2">
							<input type="submit" value="导出文件"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" onclick="importPage()" value="导入文件"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>