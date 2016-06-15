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
		<title>礼遇添加</title>
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
			function check(){
				var sale_price = document.getElementById("sale_price").value;
				if(sale_price>99.99||sale_price.length>5){
					alert("商品金额不能大于99.99元或金额位数不能大于5位");
					return false;
				}else{
					return true;
				}
			}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="addGoodsSubmit.jhtml" method="post" onsubmit="return check()"
			enctype="multipart/form-data">
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;礼遇添加
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
							礼遇名称
						</td>
						<td align="center" colspan="2">
							<input type="text" id="goods_name" name="goods_name" style="width:20%;" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							礼遇介绍
						</td>
						<td align="center" colspan="2">
							<input type="text" id="goods_introd" name="goods_introd" style="width:60%;" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							图片
						</td>
						<td align="center" colspan="2">
							<input type="file" id="goods_pic" name="goods_pic" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							原价
						</td>
						<td align="center" colspan="2">
							<input type="text" id="orig_price" name="orig_price" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							销售价格
						</td>
						<td align="center" colspan="2">
							<input type="text" id="sale_price" name="sale_price" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center" colspan="2">
							<input type="submit" value="添加" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>