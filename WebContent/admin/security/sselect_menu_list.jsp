<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>无标题文档</title>
		<script language="javascript" src="../scripts/common.js"></script>
		<link href="../themes/default/css.css" rel="stylesheet" type="text/css" />
		<LINK href="../themes/admin/user_userinfo.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css rel=stylesheet>
		<LINK href="../css/claim/common.css" type="text/css" rel=stylesheet>
		<script src="../scripts/jQuery/jquery-1.4.4.min.js" type="text/javascript"></script>
<style type="text/css">
.align_turn {
	  background:#B4E4BE
}
</style>
	
	<script type="text/javascript">
	jQuery = $;
	$(document).ready(function(){
	   var viewId = $('#modu_1').attr('viewId');
       $('tr[id^=modu_]').hide();	
       $('td[id^=moduGroup_]').removeAttr('class');
       $('#moduGroup_'+viewId).attr('class','align_turn');
       $('input[id^=allcheck_]').attr('style','display: none');
       $('#allcheck_'+viewId).removeAttr('style');
       $('tr[id^=modu_]').each(function(){
	   		if($(this).attr('viewId')==viewId)
	   			$(this).show();
	   });	
     });
	
	function changetab(obj){
	   $('tr[id^=modu_]').hide();
	   $('td[id^=moduGroup_]').removeAttr('class');
       $('#moduGroup_'+obj).attr('class','align_turn');
       $('input[id^=allcheck_]').attr('style','display:none');
       $('#allcheck_'+obj).removeAttr('style');
	   $('tr[id^=modu_]').each(function(){
	   		if($(this).attr('viewId')==obj)
	   			$(this).show();
	   });
	   
	}
	</script>

	</head>

	<body>
		<div class="right2">
			<div id="basicDiv">
				<div align="left">
					您现在的位置：用户管理 &gt;&gt; 组管理&gt;&gt;角色配置(${role.name })
				</div>

				<form name="role"
					action="roleMgtAction!saveRoleAuth.jhtml"
					method="post">
					<input type="hidden" name="id" id="id" value="${roleId}"/>
					<tr>
						<th align="left">
							<strong>权限选项</strong>
						</th>
					  </tr>
					<table class="table_a marginb10">
					<tr class="align_Center" >
					   <c:forEach var="itemModuGroup" items="${moduGroupList}">
								<td onclick="changetab(${itemModuGroup.moduGroupId})" id='moduGroup_${itemModuGroup.moduGroupId}'  style="cursor:hand;cursor:pointer;">
												<b>${itemModuGroup.moduGroupName}</b>
								</td>
					   </c:forEach>
					</tr>
                    </table>
                    <table class="table_a marginb10">
					<c:forEach var="item" items="${modus}" varStatus="status">
						<tr id='modu_${status.count}' viewId='${item.moduGroupId}'>
								<td width="10%">
									<b>${item.name}</b>
								</td>
						     
						       <td type="float:left;">
								<c:forEach var="itemAuth" items="${item.auths}" varStatus="statusA">
									<input id ="auths${item.moduGroupId}_${statusA.count}" name="auths" type="checkbox"
										value="${itemAuth.authId}" 
										<c:if test="${itemAuth.contained}">checked</c:if>>${itemAuth.authName}&nbsp;&nbsp;
								 </c:forEach>
							   </td>
						    
						</tr>
				    
					</c:forEach>
					
					</table>
					
					    <div class="menu">
					    <ul>
					    <li>
						    <input type="submit" name="Submit" value="确　定">
						    &nbsp;
						    <input type="button" name="Submit" value="返　回" onclick="history.back();">
						    <c:forEach var="itemModuGroup" items="${moduGroupList}">
						    <input id="allcheck_${itemModuGroup.moduGroupId}" name="allcheck_${itemModuGroup.moduGroupId}" type="checkbox"
							    onclick="tongleSelectId('auths${itemModuGroup.moduGroupId}','allcheck_${itemModuGroup.moduGroupId}')">
					       </c:forEach>
					       全选
					    </li>
					    </ul>
					    </div>
					
					
				</form>
			</div>
		</div>
	</body>
</html>

								