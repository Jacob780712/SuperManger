<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String tailStr = "";
	byte[] tailByteArray = (byte[])request.getAttribute("tailByteArray");	
	String fileLen = (String)request.getAttribute("fileLen");
	
	if(tailByteArray!=null)
		tailStr = new String(tailByteArray);
		
	if(fileLen==null)
		fileLen="";
%>	

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
		<LINK href="../themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css
			rel=stylesheet>
</head>


<SCRIPT language=javascript>


</SCRIPT>


<body>

<form action="tailFileAction.jhtml" method="post">


<table width="100%" border="0" cellpadding="0" cellspacing="0">

<tr>
<td height="40" style="PADDING-TOP: 8px;PADDING-left:8px">
<table width="800" height="100%" class="filterTable" align="center">  
  <tr> 
	<td width="55" class="searchFilterDataLabel"> 
	    大小
	</td>
	<td width="150" class="searchFilterDataField">
		<input type="text" name="lastTailFileLen" size="8" value="${lastTailFileLen }"/>(<%=fileLen%>)
	</td>
	
	<td width="55" class="searchFilterDataLabel"> 
	    文件
	</td>
	<td width="300" class="searchFilterDataField">
		<input type="text" name="lastTailFilePath" size="80" value="${lastTailFilePath }"/>
	</td>	
	
	<td width="55" class="searchFilterDataField">
     <INPUT type="submit" name="Submit" value="查看"  class="buttonface">			     
    </td>
        
  </tr>  
    
</table>
</td>
</tr>
</form>

<tr>
<td>
	&nbsp;
</td>
</tr>

<tr>
<td width="80%" align="center">
	<textarea rows="28" cols="160" class="textarea"><%=tailStr%></textarea>
</td>
</tr>

</table>




</body>
</html>
