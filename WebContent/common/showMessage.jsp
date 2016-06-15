<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
      <script language="javascript">
		 <c:if test="${not empty message}">
		 	alert("${message}");
		 </c:if>
      </script>