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
		<title>通知管理</title>
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
			function check(){
				var msg = document.getElementById("msg").value;
				if(msg.length.length<1){
					alert("请输入短信内容");
					return false;
				}
				return true;
			}
		</script>
	</head>
	<body>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		<form name="frmPage" id="queryFrom" onsubmit="return check()" action="messageSend.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：通知管理 &gt;&gt;发送短信
				</div>
				<table class="table_a marginb10" width="90%">
				<th align="left" colspan="12">
					<strong>发送配置</strong>

				</th>
					<tr height="10">
					<td align="left">
						<strong>手机号码:</strong>
						<input type="text" id="mobile" name="mobile" />
					</td>
					<td align="left">
						<strong>手短信内容:</strong>
						<textarea id="msg" name="msg"></textarea>
					</td>
					<td align="left">
						<strong>备注:</strong>
						<textarea id="desc" name="desc" ></textarea>
					</td>
					<td>
						<input type="submit" value="发送" />
					</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>