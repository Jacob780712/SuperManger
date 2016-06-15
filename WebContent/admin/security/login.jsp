<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>这儿有卡业务管理平台</title>
	<link href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
	<LINK href="../../scripts/jQuery/css/validator.css" type=text/css rel=stylesheet>
	<style>
		*{margin:0;padding: 0;}
		body{background-color:#AC938F;font-family:"宋体";}
		.loginBox{width:420px;height:280px;padding:0 20px;border:1px solid #fff; color:#000; margin-top:40px; border-radius:8px;background: white;box-shadow:0 0 15px #222; background: -moz-linear-gradient(top, #fff, #efefef 8%);background: -webkit-gradient(linear, 0 0, 0 100%, from(#f6f6f6), to(#f4f4f4));font:11px/1.5em 'Microsoft YaHei' ;position: absolute;left:50%;top:50%;margin-left:-210px;margin-top:-165px;}
		.loginBox h2{height:45px;font-size:20px;font-weight:normal;}
		.loginBox .left{border-right:1px solid #ccc;height:100%;padding-right: 20px; }
		.regBtn{margin-top:21px;}

	</style>

	<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="../../scripts/jQuery/formValidator.js" type="text/javascript"></script>

	<script type="text/javascript">
		function checkLoginForm()
		{
			if(!jQuery.formValidator.pageIsValid('login')){
				return false;
			}
			return true;
		}

		function initPage(){
			document.getElementById("userId").focus();
		}
		function ChangeVerify()
		{
			document.getElementById('imgOne').src="${pageContext.request.contextPath}/code.jpg?data="+GetRom();
		}
		//add 随机数 为了更改url
		function GetRom()
		{
			var now=new Date();

			return now.getSeconds();

		}

		jQuery(document).ready(function(){
			jQuery.formValidator.initConfig({validatorgroup:"login", onerror:function(msg){}});
			//避免与子窗体冒泡，所以改用id
			jQuery("input:text[id=userLoginId]").formValidator({validatorgroup:"login",onshow:"请输入用户名",onfocus:"请输入用户名",oncorrect:"输入完成"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能有空符号"},onerror:"用户名必填"});
			jQuery("input:password[id=passwordLogin]").formValidator({validatorgroup:"login",onshow:"请输入密码",onfocus:"请输入密码",oncorrect:"输入完成"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能有空符号"},onerror:"密码必填"});

		});

	</script>
</head>
<body>
<div class="container">
	<form class="form-horizontal" action="../loginAction.jhtml" method="POST" id="loginForm" name="loginForm" onsubmit="return checkLoginForm()">
		<div class="loginBox row">
			<h2 class="text-center">这儿有卡业务管理后台</h2>
			<div class="form-group has-success">
				<label  class="col-sm-2 col-md-2 control-label">用户名</label>
				<div class="col-sm-10 col-md-10">
					<input type="text" class="form-control" id="userLoginId" name="userId" placeholder="用户名" value="">
				</div>
			</div>
			<div class="form-group has-success">
				<label class="col-sm-2 col-md-2 control-label">密码</label>
				<div class="col-sm-10 col-md-10">
					<input type="password" class="form-control" id="passwordLogin" name="password" placeholder="密码">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-10" style="color: #990033;"></div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-10 col-md-10">
					<button class="btn btn-info" type="submit" style="width:35%">登 录</button>
				</div>
			</div>
	</form>
</div>
</body>
</html>
