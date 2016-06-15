<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="../css/style.css" rel="stylesheet" type="text/css">
</head>

<SCRIPT language=javascript>

function closeThis()
{

}

function gotoPage(url)
{
	window.location.href = url;
	
}

</SCRIPT>


<body>

<br>
<table width="85%" border="0" align="center" cellpadding="0" cellspacing="1" >

<tr></tr>
<tr></tr>

  <tr align="center">
	<td height="25"><strong>
	    ${message }
	</strong></td>
 </tr>

<tr></tr>
<tr></tr>

  <tr align="center">
   <td height="25">

        <c:if test="${ not empty goUrl}">	
			<input type="button" name="button1" value="返回" onclick="javascript:gotoPage('${goUrl }')">
		</else>
		   	<input type="button" name="button1" value="返回" onclick="javascript:history.back(-1)">
		</c:if>   	
   </td>
 </tr>

</table>

</body>

</html>