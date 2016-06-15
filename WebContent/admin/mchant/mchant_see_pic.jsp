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
		<title>商户图片详情</title>
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
	</head>
	<body>
		<div>
			<div align="left">
				您现在的位置：商户管理 &gt;&gt;商户图片详情
			</div>
			<table class="table_a marginb10" width="90%">
				<tr>
					<th align="left" colspan="12">
						<span style="float: left" >
							<strong>商户名称：${mchName}&nbsp;&nbsp;&nbsp;商户编号：${mchantNo}</strong>
						</span>
						<span style="float: right">
							<input type="button" value="返回" onclick="javascript:history.back()"/>
						</span>
					</th>
				</tr>
				<tr>
					<td align="center">
						<c:if test="${imgName=='mch_pic_url'}">
							商户图片
						</c:if>
						<c:if test="${imgName=='business_license_url'}">
							营业执照
						</c:if>
						<c:if test="${imgName=='tax_certificate'}">
							税务登记证
						</c:if>
						<c:if test="${imgName=='organization_code_url'}">
							组织机构代码证
						</c:if>
						<img src="../../SuperManger/mchantImages\v1451987294779mch.png" ></img>
					</td>
				</tr>
			</table> 
		</div>
	</body>
</html>