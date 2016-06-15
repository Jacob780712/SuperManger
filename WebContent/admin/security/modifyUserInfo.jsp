<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>个人资料</title>
	<script language="javascript" src="../scripts/common.js"></script>
	<link href="../themes/default/css.css" rel="stylesheet" type="text/css" />
	<LINK href="../themes/admin/user_userinfo.css" type=text/css rel=stylesheet>
	<LINK href="../themes/admin/common.css" type=text/css rel=stylesheet>
	<LINK href="../themes/admin/userhome.css" type=text/css rel=stylesheet>
	<link href="../scripts/jQuery/css/validator.css" type=text/css rel=stylesheet></link>
	<script type="text/javascript" src="../scripts/jQuery/jquery-1.2.6.js"></script>
	<script type="text/javascript" src="../scripts/jQuery/jquery.validate.js"></script>
	<script type="text/javascript" src="../scripts/jQuery/formValidator.js" ></script>
	<script type="text/javascript" src="../scripts/jQuery/formValidatorRegex.js" ></script>
<script type="text/javascript">
		 jQuery(document).ready(function(){
				jQuery.formValidator.initConfig({formid:"userForm", onerror:function(msg){}});//初始化配置组3
				validateExtr();
			});
		function validateExtr(){
					//name
					jQuery("#name").formValidator({empty:false,onshow:"请输入用户姓名",onfocus:"请输入用户姓名",oncorrect:"恭喜你,输入完成"})
								.inputValidator({min:1,onerror:"用户姓名必填"});
					jQuery("#email").formValidator({empty:false,onshow:"请输入接收系统消息的邮箱地址",onfocus:"请输入接收系统消息的邮箱地址",oncorrect:"恭喜你,输入完成"})
								.inputValidator({min:1,onerror:"邮箱地址必填"}).regexValidator({regexp:"emailNew",datatype:"enum",onerror:"你输入的邮箱格式不正确"});
					jQuery("#mobile").formValidator({empty:false,onshow:"请输入手机号码",onfocus:"请输入手机号码",oncorrect:"恭喜你,输入完成"})
								.inputValidator({min:1,onerror:"手机号码必填"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"你输入的手机号码格式不正确"});
		}	
		function sumitInfo(){
			    if(!jQuery.formValidator.pageIsValid('1')){
					return false;
				}
				document.forms.userForm.action = "userMgtAction!save.jhtml";
				document.forms.userForm.submit();
			}
			
			
</script>
</head>
<body>
<form name="userForm" id="userForm" action="" method="post" >
<input type="hidden" name="userId" id="userId" value="${user.userId}"/>
			<div>
					<div align="left">
						您现在的位置：用户管理 &gt;&gt; 用户资料修改
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
								<th align="left">
									<strong>资料维护</strong>
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
													          <TD height="30" align="right" width="15%" class="cjwt">登录ID:</TD>
													          <TD height="30" align="left">
													             ${user.loginid}
													          </TD>
													        </TR>
													        <TR>
													          <TD height="30" align="right"  width="20%">姓名:</TD>
													          <TD height="30" align="left">
													            <input name="name" type="text" class="unnamed1" size="30" maxlength="25" id="name" value="${user.name}"><span id="nameTip"></span>
													          </TD>
													        </TR>
													        <TR>
													          <TD height="30" align="right"  width="20%">邮箱:</TD>
													          <TD height="30" align="left">
													             <input name="email" type="text" class="unnamed1" size="30" maxlength="50" id="email" value="${user.email}"><span id="emailTip"></span>
													          </TD>
													        </TR>
													        <TR>
													          <TD height="30" align="right"  width="20%">手机号码:</TD>
													          <TD height="30" align="left">
													             <input name="mobile" type="text" class="unnamed1" size="30" id="mobile" value="${user.mobile}"><span id="mobileTip"></span>&nbsp;&nbsp;
													          </TD>
													        </TR>
													        <TR>
													            <TD height="30" colSpan=2 align=middle><div align="center"><span class="statusBar">
													                <input type="button" name="Submit" value="确　定" onclick="sumitInfo()">
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