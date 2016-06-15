<%@ page contentType="text/html; charset=UTF-8" %>


<%@ page import="cn.common.security.vo.*" %>
<%@ page import="java.util.*" %>

<%
	java.util.List	appProfileOptionList = (java.util.List)request.getAttribute("appProfileOptionList");
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
		<LINK href="../themes/admin/user_userinfo.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css rel=stylesheet>
		<script src="../scripts/jQuery/jquery-1.2.6.js" type="text/javascript"></script>
</head>


<SCRIPT language=javascript>

function checkForm()
{	
	return true;
}

</SCRIPT>

<body>

<form action="saveAppProfileOption.jhtml" onsubmit="return checkForm()">


<br>
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="detailTable">

        <c:if test="${not empty message}" >
         <tr>
            <td colspan="2"><div align="center">${message }
            </div>
            </td>
         </tr>
        </c:if>

  <tr align="center"> 
    <td height="25" colspan="2" class='detailFormTitle'>系统参数</td>
  </tr>
<%
String appProfileOptionNames = "";
for(int i=0;appProfileOptionList!=null&&i<appProfileOptionList.size();i++)
{
		AppProfileOption appProfileOption = (AppProfileOption)appProfileOptionList.get(i);
		String optionName = appProfileOption.getOptionName();
		String optionValue = appProfileOption.getOptionDefaultValue();
		
		if(optionValue==null) optionValue = "";
    	if(optionName!=null&&optionName.equals("apple.act.limit.date")){
		if(appProfileOptionNames.length()>0)
			appProfileOptionNames=appProfileOptionNames+";";
		appProfileOptionNames=appProfileOptionNames+optionName;
	 %>	
  <tr> 
    <td height="20" align="right" class="detailFormRowLabel" width="50%">激活时间限制</td>
    <td height="20" class="detailFormRowField"  width="50%"> 
			<input type="text" name="<%=optionName%>" value="<%=optionValue %>">
    </td>
  </tr>    
	<%}else if(optionName!=null&&optionName.equals("apple.model")){
		if(appProfileOptionNames.length()>0)
			appProfileOptionNames=appProfileOptionNames+";";
		appProfileOptionNames=appProfileOptionNames+optionName;	
	 %>	
 <tr><td colspan="2" height="20px"></td></tr>
  <tr> 
    <td height="20" align="right" class="detailFormRowLabel" >港澳销售型号</td>
    <td height="20" class="detailFormRowField" > 
       <input type="text" size="30" name="<%=optionName%>" value="<%=optionValue %>">
    </td>
  </tr> 
	<%}%>			
<%}%>
 <tr>
   <td colspan="2" height="30">
   <div align="center">
     <br/>
     <input name="save" type="submit" class="buttonface" value="保存">&nbsp;              
      		  			  
   </div>
   </td>
             
   </tr>  
</table>


<html:hidden property="cmd"/>  
<input type="hidden" name="appProfileOptionNames" value="<%=appProfileOptionNames%>">			

</html>

</body>
</html>
