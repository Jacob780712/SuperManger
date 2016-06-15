<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" isErrorPage="true" %>
<%@ page import="java.lang.Exception"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<link href="${pageContext.request.contextPath}/themes/admin/userhome.css"
			rel="stylesheet" type="text/css">
	</head>
	<body>
		<table width="80%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="detailTable">
			<tr>
				<td colspan="4" class='listTableTitleTd'>
				
						<div align="center">
							<font color="#FF0000" size="2"><b>系统处理过程中发生了一个错误，信息如下：</b>
							</font>
						</div>
			
				</td>
			</tr>
			<tr>
				<td height="100" valign=top>
					<div align="center">
		
						<span style="color: red; font: 10pt bold"><s:property
								value="exception.message" /> </span><br>
						<span style="color: red; font: 10pt bold"><s:property
								value="message" /> </span>
								
								<% 
						if (exception == null){
						 exception = (Exception)request.getAttribute("exception");
						 exception.printStackTrace();
						 }
						%>
						<%=exception.getMessage() %>
					</div>
				</td>
			</tr>
			<tr>
				<td valign=top>
					<div align="center" style="font: 10pt">
						请您先核对输入，如果再次出现该错误，请联系管理员 谢谢。
					</div>
					<br>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input type="button" onclick="javascript:history.back()" value="返回"/>
				</td>
			</tr>
		</table>
	</body>
</html>