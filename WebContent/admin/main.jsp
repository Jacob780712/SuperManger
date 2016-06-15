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
		<title>客户管理</title>
	</head>
	<body style="background-color:#EFEFEF;">
	    <div align="center" style="margin-top: 20%;font-size: 3em">
	    
	       欢迎使用这儿有卡业务管理系统
	    </div>
	</body>
</html>