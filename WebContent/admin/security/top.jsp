<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>
		<LINK href="../../themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="../../themes/admin/common.css" type=text/css
			rel=stylesheet>
		<LINK href="../../themes/admin/userhome.css" type=text/css
			rel=stylesheet>
		<script src="../../scripts/jQuery/jquery-1.4.4.min.js"
			type="text/javascript"></script>
		<LINK href="../../css/common.css" type="text/css" rel=stylesheet>
		<style>
TABLE TR TD {
	padding-left: 20px;
}
html { overflow-x:hidden; overflow-y:hidden; }
/* 所有class为menu的div中的ul中的a样式(包括尚未点击的和点击过的样式) */
.menudefault
{
    background-color: #AC938F; /* 背景色 */
    border: 1px #AC938F solid; /* 边框 */
    color: #dde4ec; /* 文字颜色 */
    display: block; /* 此元素将显示为块级元素，此元素前后会带有换行符 */
    line-height: 1.35em; /* 行高 */
    padding: 4px 20px; /* 内部填充的距离 */
    text-decoration: none; /* 不显示超链接下划线 */
    white-space: nowrap; /* 对于文本内的空白处，不会换行，文本会在在同一行上继续，直到遇到 <br> 标签为止。 */
}

.menuactive
{
    background-color: #EFEFEF; /* 背景色 */
    color: #465c71; /* 文字颜色 */
    text-decoration: none; /* 不显示超链接下划线 */
}
</style>

	</head>
	
	<script type="text/javascript">

	function ajaxaction(){
				 para="?ddd="+GetRom();
				jQuery.ajax({
				 url: '../keepSessionAction.jhtml',
				 type: 'POST',
				 timeout: 20000,
				 data:para,
				 error: function(html){
				 	alert(html);
				 },
				 success: function(html){
				 }
				});
	}
		
	function GetRom()
  {
   	var now=new Date(); 

	return now.getSeconds(); 
	}
	

			$(document).ready(function() {
			setInterval("ajaxaction()",1000*60);
			//setTimeout("ajaxaction()",1000*60*10);
			
				var accordion_main = $('#topMain > div > ul > li > a');
				
				accordion_main.bind('click', function(event) {
				// Disable header links
				// Show and hide the tabs on click
					if ($(this).attr('class') != 'menuactive'){
						$(this).next().stop(true,true).slideToggle('normal');
						accordion_main.removeClass('menuactive');
						$(this).addClass('menuactive');
					}
				});
			accordion_main.first().addClass('menuactive').next().slideDown('normal');
			accordion_main.first().click();
			});
	</script>
	<body class="topBody">
			<div class="top_main" id="topMain">
				     <c:if test ="${! empty sessionScope.loginUser.loginid}">
						<div class="top_main_top">
							<div class="top_main_top_left"><br/>
							<span style="FILTER: mask(color=#E1E4EC)shadow(color=#8C96B5,direction=135)chroma(color=#E1E4EC)" >
							
							</div>
							<div class="top_main_top_right">
						     &nbsp;&nbsp; 
							</div>
						</div>
						<div class="menu" style="font-size:17px;">
						 <ul>
						 	<c:forEach var="menuList" items="${moduGroupList}">
							   <c:forEach var="menuItem" items="${menuList.menuList}">
							      <li>
							      <a target="leftFrame" class="menudefault" href="../queryMenuAction.jhtml?menuId=${menuItem.menuId }">
							      	${menuItem.name }
							      </a></li>
							   </c:forEach>
						   </c:forEach>
						</ul>
						</div>
					</c:if>
					<div class="top_main_bom">
						${sessionScope.loginUser.name } 您好,欢迎您&nbsp;&nbsp;&nbsp;
						<img src="../../images/admin/top_18.gif" align="absmiddle" />&nbsp; 
							<a href="../logOutAction!logOut.jhtml" target="_parent" class="top_white" >退出</a>
					</div>
					</div>
					<iframe id="hidframe" src="" height="0" width="0">
	</body>
</html>
