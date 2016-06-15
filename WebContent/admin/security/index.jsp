<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="application.name"/></title>
</head>
<frameset rows="85,*" cols="*" frameborder="no" border="2" framespacing="0">
	<frame src="security/top.jsp" name="topFrame" id="topFrame" />
	<frameset rows="*" cols="170,7,*" id=frameset1>
		<frame src="security/left.jsp" scrolling=auto   id="leftFrame" name="leftFrame">
		<frame src="security/flag.html" name="flag" scrolling="no" noresize="noresize" id="flag" title="flag" />
		<frame src="main.jsp" name="main" id="main">
	</frameset>
</frameset>
<noframes><body>

</body></noframes>
</html>
