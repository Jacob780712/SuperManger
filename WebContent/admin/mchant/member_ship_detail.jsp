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
		<title>礼遇详情</title>
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
		<script type="text/javascript" src="../scripts/zgjson/province.js"></script>
		<script type="text/javascript" src="../scripts/zgjson/city.js"></script>
		<script type="text/javascript" src="../scripts/zgjson/District.js"></script>
		<script type="text/javascript">
			function seePic(url){
				window.location.href="mchantSeepic.jhtml?imgUrl="+url;
			}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="addGoodsSubmit.jhtml" method="post" onsubmit="return check()"
			enctype="multipart/form-data">
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;礼遇详情
				</div>
				
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="3">
							<span style="float: left" >
								<strong>礼遇基本信息</strong>
							</span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							礼遇编号
						</td>
						<td align="center">
							${goods.goods_number}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							礼遇名称
						</td>
						<td align="center">
							${goods.goods_name}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							礼遇介绍
						</td>
						<td align="center">
							${goods.goods_introd}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							图片
						</td>
						<td align="center">
							<input type="button" value="查看" onclick="seePic('${goods.goods_pic}')"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							原价
						</td>
						<td align="center">
							<fmt:formatNumber value="${goods.orig_price/100}" pattern="##0.00"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							销售价格
						</td>
						<td align="center">
							<fmt:formatNumber value="${goods.sale_price/100}" pattern="##0.00"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>