<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>编辑用户组</title>
		<script language="javascript" src="../scripts/common.js"></script>
		<link href="../themes/default/css.css" rel="stylesheet"
			type="text/css" />
		<LINK href="../themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="../scripts/jQuery/jquery-1.2.6.js"></script>
		<script type="text/javascript" src="../scripts/jQuery/jquery.validate.js"></script>
		<script type="text/javascript">
			function saveItem(){
				if ($("#name").val() == null || $("#name").val() == ""){
					alert("角色名称为必填项！");
					return false;
				}
				document.forms.role.action = "roleMgtAction!save.jhtml";
				document.forms.role.submit();
			}
			
		</script>
	</head>
	
	

	<body>
		<form name="role" id="role"
			action=""
			method="post" >

			<input type="hidden" name="roleId" id="roleId" value="${role.roleId}"/>
			<div class="right2">
				<div align="left">
					您现在的位置：用户管理 &gt;&gt; 组管理&gt;&gt;
				</div>
				<table border='0'>
					<tr height="10">
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table class="table_a marginb10">
					<tbody>
						<tr>
							<th align="left" colspan="2">
								<strong>组信息</strong>
							</th>
						</tr>

						<tr>
							<td width="15%" align="right">
								角色名称
							</td>
							<td align="left">
								<input name="name" type="text" size="26" maxlength="25"
									id="name" value="${role.name}">
								&nbsp;
								<span style="color: #FF0000">( * 请输入用户组名称) </span>
							</td>
						</tr>

						<tr>
							<td width="15%" align="right">
								角色描述
							</td>
							<td align="left">
								<TEXTAREA name="remark" cols="50" rows="6" onkeyup="checkTextAreaSize(this,30);" id="remark">${role.remark}</TEXTAREA>
							</td>
						</tr>

						<tr>
							<td height="33" colspan="2" align="left" valign="bottom">
								&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="button" name="Submit" onClick="saveItem()" value="确　定">
								&nbsp;
								<input type="reset" name="Submit" value="重　填">
								&nbsp;
								<input type="button" name="Submit" value="返　回"
									onclick="history.back();">
							</td>
						</tr>
					</tbody>
				</table>
			</div>

		</form>
	</body>
</html>