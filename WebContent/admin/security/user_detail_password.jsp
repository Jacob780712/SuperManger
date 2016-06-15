<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>修改密码</title>
		<LINK href="../themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css
			rel=stylesheet>
		<script src="${pageContext.request.contextPath}/scripts/jQuery/jquery-1.4.4.min.js"
			type="text/javascript"></script>
<script type="text/javascript">	
    
function checkPwd()
{
     //var passwd = document.getElementById('password').value.trim();
    		var passwd = $('#password').val();
            var repeatpswd = $('#newPass').val();
            var repeatpswd1 = $('#newPass1').val();
            if($.trim(passwd) == '' || $.trim(repeatpswd) == '' ||$.trim(repeatpswd1) == '')
            {
              alert('原密码|新密码|确定密码 为必填项！');
              if ($.trim(passwd) == '') {
               
                $("password").focus();
                return false;
             } 
             if ($.trim(repeatpswd) == '') {
              
                $("newPass").focus();
                return false;
                } 
             if ($.trim(repeatpswd1) == '') {
              
                $("newPass1").focus();
                return false;
              } 
             }
            if ($.trim(repeatpswd) != $.trim(repeatpswd1)) {
                alert('新密码必须和确认密码相同！');
                return false;
            } 
            return true;
}
</script>
</head>
<body>

<form name="frmPage" id="frmPage" action="passWordMgtAction!save.jhtml" method="post" onsubmit="return checkPwd();">
<div class="right2">
<div align="left">
	您现在的位置：系统管理 &gt;&gt; 修改密码
</div>
<input type="hidden" style="text" name="userId" id="userId" value="${userId }"/>
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
			<th align="left">
				<strong>密码维护</strong>
			</th>
		</tr>
		<tr class="EFF_OS_CON" id="dgxx" style="">
			<td style="padding: 0pt;">
				<table frame="void" width="100%" bordercolor="#deedf9"
					border="1" class="Table">
					<tbody>
						<tr>
							<td style="border: 0pt none;" class="table_wrap">
								<table frame="void" width="100%" bordercolor="#deedf9"
									border="1" class="Table">
									<tbody>
								        <TR>
								          <TD height="30" align=right class="cjwt" width="15%" >原密码:</TD>
								          <TD height="30" align="left">
								            <input name="password" type="password" class="unnamed1" size="30" id="password" maxlength="16" id="password" onblur="">&nbsp;&nbsp;<span class="cjbt">*</span>&nbsp;<span id="loginView"/>
								          </TD>
								        </TR>
								        <TR>
								          <TD height="30" align=right class="cjwt" width="15%">新密码:</TD>
								          <TD height="30" align="left">
								            <input name="newPass" type="password" class="unnamed1" size="30" maxlength="16" id="newPass" onblur="">&nbsp;&nbsp;<span class="cjbt">*</span>
								          </TD>
								        </TR>
								        <TR>
								          <TD height="30" align=right class="cjwt" width="15%">确认密码:</TD>
								          <TD height="30" align="left">
								            <input name="newPass1" type="password" class="unnamed1" size="30" maxlength="16" id="newPass1"  onblur="">&nbsp;&nbsp;<span class="cjbt">*</span>
								          </TD>
								        </TR>
								        <TR>
								            <TD height="30" colSpan=2 align=left><div style="margin-left: 150px;"><span class="statusBar">
								                <input type="submit" name="Submit" value="确　定" id="btn">
								                　<input type="reset" name="Submit" value="重　填">
								            </span></div></TD>
								        </TR>
									  </tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>
</form>
</body>
</html>