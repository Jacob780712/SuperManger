<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title></title>
		<link href="${pageContext.request.contextPath}/themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<link href="${pageContext.request.contextPath}/themes/admin/common.css" type=text/css
			rel=stylesheet>
		<link href="${pageContext.request.contextPath}/themes/admin/userhome.css" type=text/css
			rel=stylesheet>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/claim/common.css" type="text/css"  />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/claim/style.css" type="text/css"  />
		<script src="${pageContext.request.contextPath}/scripts/jQuery/jquery-1.2.6.js"
			type="text/javascript"></script>
		<script type="text/javascript"  src="${pageContext.request.contextPath}/js/jquery/jquery.cookie.js"></script>
			
		<style>
TABLE TR TD {
	padding-left: 20px;
.footer a{color:#999;text-decoration:none}
#wrapper-250{width:100%;height:100%;}
}
</style>
<base target="main">
	</head>


	<script type="text/javascript">
		
		//获取原生的dom对象
		function getElementObj(event){
			var e = event || window.event;
			return e.srcElement || e.target;
		}

		$(document).ready(function() {
			var accordion_head = $('.accordion > li > a');

			var accordion_body = $('.accordion li > .sub-menu');
			$("#wrapper-250").click(function(event){
				var elem = getElementObj(event);
				if($(elem).parent().attr("class")=='files'){
					showModuleMenu(elem);
				}else if($(elem).parent().is("li")){
					toLoadPage(elem);
				}
				return false;
			});
		
		
		
		var toLoadPage = function(object){
			var loadhref = $(object).attr("href");
			if(loadhref.indexOf('?')>0){
				loadhref +='&oneShow='+ encodeURI(encodeURI($(object).parent().parent().prev().text()))+'&twoShow='+encodeURI(encodeURI($(object).text().substring($(object).children('em').text().length,$(object).text().length)));
			}else{
				loadhref +='?oneShow='+ encodeURI(encodeURI($(object).parent().parent().prev().text()))+'&twoShow='+encodeURI(encodeURI($(object).text().substring($(object).children('em').text().length,$(object).text().length)));
			}
			
			
			window.parent.main.location.href=loadhref;
		}
		
		var showModuleMenu = function(object){
			if ($(object).attr('class') != 'active'){
					accordion_body.slideUp('normal');
					$(object).next().stop(true,true).slideToggle('normal');
					accordion_head.removeClass('active');
					$(object).addClass('active');
					$.cookie('the_cookie', object.id); // 设置cookie
			}
		}
		
			var cookievalue= $.cookie('the_cookie');
			if( cookievalue){
				$("#"+cookievalue).click();
			}else{
				accordion_head.first().addClass('active').next().slideDown('normal');
			}
		});
		  
	</script>


	<body class="leftBODY">
		<div id="wrapper-250">
			<ul class="accordion">
					<!-- 循环赋值 menu -->
					<c:forEach var="menuItem" items="${menuList}"
						varStatus="status">
						 <li  class="files"><a id="report${status.index+1 }" href="#" >${menuItem.name }</a>
						<ul id="menu_${menuItem.menuId}" style="display: none;" class="sub-menu">
							<c:forEach var="menuItemBiz" items="${menuItem.subMenuList}"
								varStatus="statusBiz">
								<li>
									<a href="../admin/${menuItemBiz.url }"><em>${statusBiz.index+1 }</em>${menuItemBiz.name }</a>
								</li>
							</c:forEach>
						</ul>
					</c:forEach>
				</ul>
		</div>
	</body>
</html>
