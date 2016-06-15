<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>选择角色</title>
		<script language="javascript" src="../scripts/common.js"></script>
		<link href="../themes/default/css.css" rel="stylesheet" type="text/css" />
		<LINK href="../themes/admin/user_userinfo.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css rel=stylesheet>

</head>
<body>
<form name="frmPage" id="frmPage"  action="userMgtAction!saveUserRole.jhtml?userId=${user.userId}" method="post" onsubmit="javascript:allSelect();return true;">
<input type="hidden" name="id" id="id"/>
	<div class="right2">
		<div align="left">
			您现在的位置：用户管理 &gt;&gt; 用户组配置
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
			<th align="left" colspan="4">
				<strong>选择角色</strong>
			</th>
			</tr>
			<tr>
			      <TD height="30" align="right"  width="15%">登录ID</TD>
		          <TD height="30" align="left" colspan="3">
		            <input name="loginid" type="text" size="26" maxlength="50" class="add_midd_form1" readonly="true" id="loginid" value="${user.loginid}">&nbsp;&nbsp;&nbsp;&nbsp;默认用户组不允许删除
		          </TD>
			</tr>
            <TR>
	          <TD  align="right"  width="15%">角色</TD>
	          <TD  align="left" width="15%">
                        <select name="allElement" style="width:100%; height:300" size="10"  multiple="true" ondblclick="Append();" id="allElement">
							<c:forEach var="item" items="${roleSysList}">
			                   <option value="${item.roleId}">${item.name}</option>
			                </c:forEach>
						</select>
					</td>
					<td width="15%" align="left">
									<input name="Submit" type="button" class="add_midd_bott1"  value="添加&gt;&gt;" onclick="Append();" /><br/>
									<input name="Submit2" type="button" class="add_midd_bott1"  value="&lt;&lt;删除" onclick="Delete();" />
                    </td>
					<td align="left">
                        <select name="selectElement"  style="width:30%; height:300" size="10" multiple="true"  ondblclick="Delete();" id="selectElement">
							<c:forEach var="item" items="${roleUserList}">
			                   <option value="${item.roleId}">${item.name}</option>
			                </c:forEach>
						</select>
					</td>
        	</TR>
	        <TR>
	            <TD height="30" colSpan=4 align=middle><div align="center"><span class="statusBar">
	                <input type="submit" name="Submit" value="确　定">
	                　<input type="button" name="Submit" value="返　回" onclick="history.back();">
	            </span></div></TD>
	          </TR>
		</tbody>
		</table>
   </div>
</form>
</body>
</html>