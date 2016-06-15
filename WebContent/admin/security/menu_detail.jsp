<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>菜单管理</title>
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
					alert("菜单名称为必填项！");
					return false;
				}
				document.forms.menu.action="menuMgtAction!save.jhtml";
				document.forms.menu.submit();
			}
		</script>
	</head>

	<body>
		<div class="right2">
			<div align="center">
				<div align="left" class="kjj">
					<c:if test="${menu.menuId == null}">
					添加菜单
				</c:if>
					<c:if test="${menu.menuId != null}">
					修改菜单信息
				</c:if>
				</div>
				<!-- update/add 模块信息 -->
				<form method="post" action="" name="menu" id="menu">
				    <input type="hidden" name="menuId" value="${menu.menuId }">
				    <input type="hidden" name="pageNo" id="pageNo" value="${pageNo }"/>
				    <input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
					<table class="table_a word_break">
                            <tr>
							<td width="5%" >
								菜单名称
							</td>
							<td width="43%" >
								<input type="text" name="name" id="name"
								    value="${menu.name}" />
								<font color="red">( *菜单名称必填)</font>
							</td>
							</tr>
							<tr>
							<td width="5%" >
								URL
							</td>
							<td width="43%" >
								<input type="text" name="url"
								    value="${menu.url }"
									style="font-size: 9pt; width: 40%" />
							</td>
						</tr>
						<tr>
							<td width="5%" >
								菜单位置
							</td>
							<td width="43%" >
								<input type="text" name="target"
								    value="${menu.target }" />
							</td>
						</tr>
						<tr>
							<td width="5%" >
								菜单SEQ
							</td>
							<td width="43%" >
								<input type="text" name="seq"
								    value="${menu.seq }" />
								<font color="red">( *double类型数据)</font>
							</td>
							
						</tr>
						<tr>
							<td width="5%" >
								父菜单ID
							</td>
							<td width="43%" >
								<select name="parentId" style="WIDTH: 100pt">
								  <option value="-1">根菜单</option>
								   <c:forEach items="${menuParentList}" var="menuBiz">
									<option
										value='${menuBiz.menuId }'
										<c:if test="${menu.parentId==menuBiz.menuId}"> selected </c:if>>
										${menuBiz.name }
									</option>
								</c:forEach>
								 </select>
							</td>
						</tr>
						<tr>
							<td width="5%" >
								图片路径
							</td>
							<td width="43%" >
								<input type="text" name="imgPath"
								    value="${menu.imgPath }"
									style="font-size: 9pt; width: 40%" />
							</td>
						</tr>
						<tr>
							<td width="5%" >
								菜单描述
							</td>
							<td width="43%" style="word-break : break-all">
								<input type="text" name="remark"
								    value="${menu.remark }"
									style="font-size: 9pt; width: 40%" />
							</td>
						</tr>
						<tr align_Center>
							<td style="margin-left: 150px;" width="10%" colspan="4">
								<input type="button" name="Submit" onClick="saveItem()" value="确  定" />
								<input name="back" type="button"
									onClick="window.location ='listMenuAction!list.jhtml?s=1&pageNo=${pageNo }&pageSize=${pageSize }'"
									value="返回">
							</td>
						</tr>
					</table>
				</form>
			</div>
	</body>
</html>
							