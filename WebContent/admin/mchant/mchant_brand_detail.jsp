<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<title>品牌信息详情</title>
		<script language="javascript" src="<%=basePath%>scripts/common.js"></script>
		<script language="javascript" src="<%=basePath%>scripts/Windows.js"></script>
		<link href="<%=basePath%>themes/admin/css.css" rel="stylesheet" type="text/css" />
		<LINK href="<%=basePath%>themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="<%=basePath%>themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="<%=basePath%>themes/admin/userhome.css" type=text/css rel=stylesheet>
		<script src="<%=basePath%>scripts/jQuery/jquery-1.2.6.js" type="text/javascript"></script>
		<script src="<%=basePath%>scripts/jQuery/formValidator.js" type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>scripts/jQuery/jquery.loadmask.js"></script>
		<link href="<%=basePath%>themes/admin/jquery.loadmask.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="../scripts/dateNew/My97DatePicker/date.js"></script>
		<script language="javascript" src="../scripts/dateNew/My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
			.ddiv{
				display: none;
			}
		</style>
		<script type="text/javascript">
		
		function gotoPage(pageNo,callSubmit)
		{
		    document.getElementById("pageNo").value= pageNo;
			if(callSubmit==1)
			{
				frmPage.submit();		
			}
		}
	
		function addmchant(){
			window.location.href="addmchant.jhtml";
		}
		
		function checkForm() {
	    	document.getElementById("queryFrom").submit();
		}
		
		function showFmtControls(fmt) {
			WdatePicker({dateFmt:fmt});
		}
	
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="addBrandCommit.jhtml" method="post" enctype="multipart/form-data">
			<input type="hidden" value="${mchantNo}" id="mchantNo" name="mchantNo"/>
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;品牌信息详情
				</div>
				<table class="table_a marginb10" width="90%">
					<tr class="align_Center">
						<td align="center">
							品牌信息:
						</td>
						<td align="center">
							<textarea>${MchantBrand.brand_info}</textarea>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							品牌信息内容:
						</td>
						<td align="center">
							<textarea>${MchantBrand.input_info}</textarea>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center" colspan="2">
							<input type="button" value="返回" onclick="javascript:history.back()"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>