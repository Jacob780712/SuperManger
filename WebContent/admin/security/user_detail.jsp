<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>用户管理</title>
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
			jQuery = $;
			 jQuery(document).ready(function(){
					jQuery.formValidator.initConfig({formid:"userForm", onerror:function(msg){}});//初始化配置组3
					validateExtr();
				});
	
		   function validateExtr(){
					jQuery("#loginid").formValidator({empty:false,onshow:"请输入登录名",onfocus:"请输入登录名",oncorrect:"恭喜你,输入完成"})
								.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"登录名两边不能有空格"},onerror:"登录名必填"}).regexValidator({regexp:"^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$",onerror:"内容不能包含特殊字符"}).ajaxValidator({
					    type : "get",
						url : "userMgtAction!ajaxValidateLoignid.jhtml",
					    para : "loginid=" + jQuery("#loginid").val(),
						datatype : "html",
						success : function(data){
	  					    if( data == "0" )
	  					    {
	  					       return true;
	  					    }
				            else if( data == "1" )
							{
				                return false;
							}
				            else
							{
				                return false;
							}
							
						},
						buttons: $("#button"),
						error: function(){alert("服务器没有返回数据，可能服务器忙，请重试");},
						onerror : "用户名已被使用!",
						onwait : "正在校验用户名，请稍候..."
					});
					jQuery("#password").formValidator({empty:false,onshow:"请输入密码",onfocus:"请输入密码",oncorrect:"恭喜你,输入完成"})
								.inputValidator({min:1,onerror:"密码必填"});
					jQuery("#password1").formValidator({empty:false,onshow:"请输入确认密码",onfocus:"请输入确认密码",oncorrect:"恭喜你,输入完成"})
								.inputValidator({min:1,onerror:"确认密码必填"}).functionValidator({fun:vP});
					//name
					jQuery("#name").formValidator({empty:false,onshow:"请输入用户姓名",onfocus:"请输入用户姓名",oncorrect:"恭喜你,输入完成"})
								.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户名两边不能有空格"},onerror:"用户姓名必填"});
					/**
					jQuery("#email").formValidator({empty:false,onshow:"请输入接收系统消息的邮箱地址",onfocus:"请输入接收系统消息的邮箱地址",oncorrect:"恭喜你,输入完成"})
								.inputValidator({min:1,onerror:"邮箱地址必填"}).regexValidator({regexp:"emailNew",datatype:"enum",onerror:"你输入的邮箱格式不正确"});
					jQuery("#mobile").formValidator({empty:false,onshow:"请输入手机号码",onfocus:"请输入手机号码",oncorrect:"恭喜你,输入完成"})
								.inputValidator({min:11,max:11,onerror:"手机号码必须是11位的,请确认"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"你输入的手机号码格式不正确"});
								*/
					
				}
		  function vP()
		  {
		    if(jQuery("#password").val()==jQuery("#password1").val())
		      return true;
		    else
		     return "两次密码不一致";
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
		<div id="LoadingStatus"
			style="float: right; width: 130px; display: none;">
			<img src="../scripts/jQuery/images/loading.gif" />
			正在验证...
		</div>
		<div id="formDiv">
		<form name="userForm" id="userForm" method="post">
			<jodd:form bean="user" scope="request">
					<table class="table_a marginb10">
						<tbody>
							<tr>
								<th align="left" colspan="2">
									<strong>后台用户信息</strong>
								</th>
							</tr>
							<TR>
								<TD height="30" align="right" class="cjwt" width="15%">
									登录ID
								</TD>
								<TD align="left">
									<input name="loginid" id="loginid" type="text"	class="unnamed1" size="30"  maxlength="25">
									<span id="loginidTip"></span>
								</TD>
							</TR>
							<TR>
								<TD height="30" align="right" class="cjwt">
									密码
								</TD>
								<TD align="left">
									<input name="password" type="password"
										class="unnamed1" size="30" maxlength="16"
										id="password">
									<span id="passwordTip"></span>
								</TD>
							</TR>
							<TR>
								<TD height="30" align="right" class="cjwt">
								 	确认密码
								</TD>
								<TD align="left">
									<input name="password1" type="password" class="unnamed1"
										size="30" maxlength="16" id="password1">
									<span id="password1Tip"></span>
								</TD>
							</TR>
							<TR>
								<TD height="30" align="right" class="cjwt">
									姓名
								</TD>
								<TD align="left">
									<input name="name" type="text" class="unnamed1"
										size="30" maxlength="25" id="name">
									<span id="nameTip"></span>
								</TD>
							</TR>
							
							<TR>
								<TD height="30" align="right" class="cjwt">
									邮箱地址 
								</TD>
								<TD align="left">
									<input name="email" type="text" class="unnamed1"
										size="30" id="email">
									<span id="emailTip"></span>
								</TD>
							</TR>
							<TR>
								<TD height="30" align="right" class="cjwt">
									手机
								</TD>
								<TD align="left">
									<input name="mobile" type="text" class="unnamed1"
										size="30" id="mobile">
										<span id="mobileTip"></span>
								</TD>
							</TR>
							<TR>
								<TD height="30" align="right" class="cjwt">
									 用户说明
								</TD>
								<TD align="left">
									<input name="remark" type="text" class="unnamed1"
										size="100" maxlength="125" id="remark">
								</TD>
							</TR>
							<TR>
								<TD height="30" colSpan=2 align=middle>
									<div style="margin-left: 150px;">
										<span class="statusBar"> <input type="button"
												id="btn" name="Submit" value="确　定" onclick="sumitInfo()">
											&nbsp;&nbsp; </span>
									</div>
									<br>
								</TD>
							</TR>
							</tbody>
						</table>
			</jodd:form>
		</form>
		</div>
	</body>
</html>
