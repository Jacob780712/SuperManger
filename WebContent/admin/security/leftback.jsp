<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
response.setHeader("Pragma", "no-cache"); // HTTP 1.0
response.setDateHeader("Expires", 0); // prevents caching at the proxy

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title></title>
	    <link rel="stylesheet" href="<%=basePath%>css/claim/common.css" type="text/css"  />
        <link rel="stylesheet" href="<%=basePath%>css/claim/style.css" type="text/css"  />
		<script src="<%=basePath%>scripts/jQuery/jquery-1.4.4.min.js"
			type="text/javascript"></script>
		<script type="text/javascript"  src="<%=basePath%>js/jquery/jquery.cookie.js"></script>
			
		<style>
TABLE TR TD {
	padding-left: 20px;
.footer a{color:#999;text-decoration:none}
#wrapper-250{width:100%;height:100%;}
}

.accordionlia {
	display: block;
	position: relative;
	min-width: 110px;
	padding: 0 10px 0 40px;
	height: 32px;

	color: #2044A2;
	font: bold 12px/32px Arial, sans-serif;
	text-decoration: none;
	text-shadow: 0px 1px 0px rgba(0,0,0, .35);
	
	background: #6c6e74;
	background: -moz-linear-gradient(top,  #D5D4FF 0%, #B4B6FF 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#D5D4FF), color-stop(100%,#B4B6FF));
	background: -webkit-linear-gradient(top,  #D5D4FF 0%,#B4B6FF 100%);
	background: -o-linear-gradient(top,  #D5D4FF 0%,#B4B6FF 100%);
	background: -ms-linear-gradient(top,  #D5D4FF 0%,#B4B6FF 100%);
	background: linear-gradient(top,  #D5D4FF 0%,#B4B6FF 100%);
	-pie-background:linear-gradient(top,  #D5D4FF 0%,#B4B6FF 100%);
   
	-webkit-box-shadow: inset 0px 1px 0px 0px rgba(255,255,255, .1), 0px 1px 0px 0px rgba(0,0,0, .1);
	-moz-box-shadow: inset 0px 1px 0px 0px rgba(255,255,255, .1), 0px 1px 0px 0px rgba(0,0,0, .1);
	box-shadow: inset 0px 1px 0px 0px rgba(255,255,255, .1), 0px 1px 0px 0px rgba(0,0,0, .1);
	
	behavior: url(css/PIE.htc);
}
</style>
<base target="main">
	</head>
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
										<a href="<%=basePath%>admin/admin/${menuItemBiz.url }"><em>${statusBiz.index+1 }</em>${menuItemBiz.name }</a>
									</li>
								</c:forEach>
							</ul>
						 </li>
					</c:forEach>
				</ul>
		</div>
	</body>
	<script type="text/javascript">
	var accordion_head = $('.accordion > li > a');
	accordion_head.each(function(){
		$(this).addClass('accordionlia');
	});


			var accordion_body = $('.accordion li > .sub-menu');
		
		//获取原生的dom对象
		function getElementObj(event){
			var e = event || window.event;
			return e.srcElement || e.target;
		}

		$(document).ready(function() {
			
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
			if(cookievalue){
				$("#"+cookievalue).click();
			}else{
				accordion_head.first().addClass('active').next().slideDown('normal');
			}
		});
		  
	</script>
	
</html>
