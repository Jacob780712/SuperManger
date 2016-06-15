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
		<title>账户余额调整</title>
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
		
		function check(){
			var amount = document.getElementById("amount").value;
			if(!isNaN(amount)){
				var t = window.confirm("确定提交修改？");
				if(t){
					frmPage.submit();
				}
			}else{
				alert("金额请输入数字！")
			}
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="rechangeSubmit.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：业务管理 &gt;&gt;用户账户余额调整
				</div>
				<table class="table_a marginb10" width="90%">
					<tr class="align_Center">
						<td align="center">
							手机号
						</td>
						<td align="center">
							<input type="text" id="mobile" name="mobile" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							金额
						</td>
						<td align="center">
							<input type="text" id="amount" name="amount" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							类型
						</td>
						<td align="center">
							<select id="type" name="type">
								<option value="1">系统退款</option>
								<option value="2">系统扣款</option>
							</select>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							备注
						</td>
						<td align="center">
							<textarea id="remark" name="remark" required="required"></textarea>
						</td>
					</tr>
					<tr class="align_Center">
						<td colspan="2" align="center">
							<input type="button" value="提交" onclick="check()"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>