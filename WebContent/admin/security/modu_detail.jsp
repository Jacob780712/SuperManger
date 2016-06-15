<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>模块管理</title>
		<LINK href="../themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css
			rel=stylesheet>
		<script type="text/javascript">
			function saveItem(){
			    var name = document.getElementById("name");
				if (name.value == null || name.value == ""){
					alert("模块名称为必填项！");
					return false;
				}
				document.forms.modu.action = "moduMgtAction!save.jhtml";
				document.forms.modu.submit();
			}
			
		</script>
	</head>

	<body>
		<div class="right2">
			<div align="center">
				<div align="left" class="kjj">
					<c:if test="${modu.moduId == null}">
					添加模块
				</c:if>
					<c:if test="${modu.moduId!=null }">
					修改模块信息
				</c:if>
				</div>
				<!-- 添加模块 -->

				<!-- 修改模块信息 -->
				<form name="modu" id="modu" method="post" action="">
				   <input type="hidden" name="moduId" value="${modu.moduId }">
					<table class="table_a marginb10">
                            <tr>
							  <td width="5%" >
								模块名称
							  </td>
							  <td width="45%" >
								<input type="text" name="name" id="name"
								    value="${modu.name }" />
								<font color="red">( *模块名称必填)</font>
							  </td>
							</tr>
							<tr>
							<td width="5%" >
								所属模块组
							</td>
							<td width="43%" >
								<select name="moduGroupId" style="WIDTH: 100pt">
								  <option value="0">无</option>
								   <c:forEach items="${moduGroupList}" var="moduGroup">
									<option
										value='${moduGroup.moduGroupId }'
										<c:if test="${modu.moduGroupId==moduGroup.moduGroupId}"> selected </c:if>>
										${moduGroup.moduGroupName }
									</option>
								</c:forEach>
								 </select>
							</td>
						</tr>
							<tr>
							  <td width="5%" >
								模块说明
							  </td>
							  <td width="45%" >
								<input type="text" name="remark"
								    value="${modu.remark }"
									style="font-size: 9pt; width: 40%" />
							  </td>
						</tr>
						<tr align_Center>
							<td style="margin-left: 150px;" width="10%" colspan="4">
								<input type="button" name="Submit" onClick="saveItem()" value="确  定" />
								<input name="back" type="button"
									onClick="window.location ='listModuAction!list.jhtml?s=1'"
									value="返回">
							</td>
						</tr>
					</table>
				</form>
	</body>
</html>
