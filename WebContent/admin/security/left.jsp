<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<HTML>
	<HEAD>
		<TITLE></TITLE>
		<LINK href="${pageContext.request.contextPath}/themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="${pageContext.request.contextPath}/themes/admin/common.css" type=text/css
			rel=stylesheet>
		<LINK href="${pageContext.request.contextPath}/themes/admin/userhome.css" type=text/css
			rel=stylesheet>
		<script src="${pageContext.request.contextPath}/scripts/jQuery/jquery-1.2.6.js"
			type="text/javascript"></script>
		<style>
TABLE TR TD {
	padding-left: 20px;
}
  .adian{
color: #5c5c5c;
}
.adin{
color:#999999;
}
	a.d:hover{color:#AC938F;text-decoration: underline}
	.hrs{
		color:#ffffff;
		width:180px;
	}
	
</style>
		<base target="main">
	</HEAD>


	<SCRIPT language="javascript">

function onClickMenuItem(url)
{
	if(url=="")
	{	
	}
	else
	{
		window.parent.right.location=url;
	}		
}


</SCRIPT>

	<script language="javascript">
			function shrinkMenu(menuId){
				if(document.getElementById('menu_'+menuId).style.display == 'none'){
					document.getElementById('menu_'+menuId).style.display = '';
					document.getElementById('menu_'+menuId+'_img').src='${pageContext.request.contextPath}/images/admin/tree_close.gif';
				}
				else{ 
					document.getElementById('menu_'+menuId).style.display = 'none';
					document.getElementById('menu_'+menuId+'_img').src='${pageContext.request.contextPath}/images/admin/tree_open.gif';
				}
			}
		    window.onbeforeunload = function() {   
				var menuary= jQuery("#menuAry>UL");
				var len = menuary.length;
				var cookisValue = "";
				for(var i=0; i < len; i++)
				{
				 var obj = menuary[i];
				 if(obj.style.display == "none")
				 {
				  cookisValue = cookisValue+i+"|";
				 }
				}
				jQuery.cookie("menus",cookisValue,{ expires: 7});
		    }
		    
		    function setMenusDisplay()
		    {
		     var menus = jQuery.cookie("menus");
		     if(menus == null)
		     	return ;
		     var ary = menus.split("|");
			 var len = ary.length;
			 for(var i=0; i < len-1; i++)
			 {
			   	var index = parseInt(ary[i])+1;
			   	var obj = 	document.getElementById('menu_'+index);
			   	if(obj !=null){
			   		document.getElementById('menu_'+index).style.display = 'none';
					document.getElementById('menu_'+index+'_img').src='${pageContext.request.contextPath}/images/admin/tree_open.gif';
				}
			 }
			 }
			 //logout remove seesion
		    
		    //add 随机数 为了更改url
		    function GetRom()
		    {
		     	var now=new Date(); 

    			return now.getSeconds(); 
		     
		    }
		
		function getElementObj(event){
			var e = event || window.event;
			return e.srcElement || e.target;
		}
		  
		$(document).ready(function() {
			
			$("#menuAry").click(function(event){
				var elem = getElementObj(event);
				if($(elem).parent().attr("class")=='files'){
					;
				}else if($(elem).parent().parent().is("li")){
					toLoadPage(elem);
				}
				return false;
			});
		
			var toLoadPage = function(object){
			
				var loadhref = $(object).parent().attr("href");
				
				if(loadhref.indexOf('?')>0){
					loadhref +='&oneShow='+ encodeURI(encodeURI($(object).parent().parent().parent().prev().text()))+'&twoShow='+encodeURI(encodeURI($(object).text().substring($(object).children('em').text().length,$(object).text().length)));
				}else{
					loadhref +='?oneShow='+ encodeURI(encodeURI($(object).parent().parent().parent().prev().text()))+'&twoShow='+encodeURI(encodeURI($(object).text().substring($(object).children('em').text().length,$(object).text().length)));
				}
			
				$('.left_box1 > UL > LI > a').each(function(){
					if($(this).attr("href")==$(object).parent().attr("href")){
						$(this).find('span').addClass('adian');
						$(this).find('span').removeClass('adin');
					}else{
						$(this).find('span').removeClass('adian');
						$(this).find('span').addClass('adin');
					}
				});
				window.parent.main.location.href=loadhref;
				
			}
		});
		</script>


	<body style="background-color:#EFEFEF;">
		<DIV class=Main>
			<DIV class=left id="menuLeft">
				
				<DIV class=left_box1 id="menuAry" style="font-size:16px;">
					<!-- 循环赋值 menu -->
					<c:if test="${flag=='no'}">
						<c:forEach var="menuItem" items="${menuList}"
							varStatus="status">
							<UL id="menu_${menuItem.menuId}">
								<c:forEach var="menuItemBiz" items="${menuItem.subMenuList}"
									varStatus="statusBiz">
									<LI style="white-space:nowrap;">
										<A class="d" href="${pageContext.request.contextPath}/admin/admin/${menuItemBiz.url }">
										<span class="adin">${menuItemBiz.name }</span></A>
									</LI>
									<hr class="hrs">
								</c:forEach>
							</UL>
						</c:forEach>
					</c:if>
					<c:if test="${flag!='no'}">
						<c:forEach var="menuItem" items="${menuList}"
							end="0" varStatus="status">
							<UL id="menu_${menuItem.menuId}">
								<c:forEach var="menuItemBiz" items="${menuItem.subMenuList}"
									varStatus="statusBiz">
									<LI style="white-space:nowrap;">
										<A class="d" href="${pageContext.request.contextPath}/admin/admin/${menuItemBiz.url }">
										<span class="adin">${menuItemBiz.name }</span></A>
									</LI>
									<hr class="hrs">
								</c:forEach>
							</UL>
						</c:forEach>
					</c:if>
				</DIV>
			</DIV>
		</DIV>

	</BODY>
</HTML>
