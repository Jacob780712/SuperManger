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
		<title>门店添加</title>
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
		<form name="frmPage" id="queryFrom" action="savemchantStore.jhtml"  method="post">
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;门店详情
				</div>
				
				<table class="table_a marginb10" id="store_table">
					<tr>
						<th align="left" colspan="12">
							<span style="float: left" >
								<strong>商户名称：${mchant.mch_name}&nbsp;&nbsp;&nbsp;商户编号：${mchant.mch_number}</strong>
							</span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center" style="width:50%;">
							门店名称
						</td>
						<td align="center" style="width:50%;">
							${branch.branch_name}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							城市
						</td>
						<td align="center">
							${branch.city_name}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							门店图片
						</td>
						<td align="center">
							<input type="button" value="查看图片" onclick="seePic('${mchpicUrl}')"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							门店地址
						</td>
						<td align="center">
							${branch.branch_addr}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							所属商圈（如果有多个，请用逗号分隔）
						</td>
						<td align="center">
							${branch.biz_districts}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							使用须知（请使用p标签，包含储值卡使用范围、营业时间等；如果不添加使用须知，则默认使用须知为该商户的使用须知）
						</td>
						<td align="center">
							${branch.use_note}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							联系方式
						</td>
						<td align="center">
							${branch.telephone}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							经度
						</td>
						<td align="center">
							${branch.longitude}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							纬度
						</td>
						<td align="center">
							${branch.latitude}
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" id="account_table">
					<tr>
						<th align="left" colspan="6">
							<span style="float: left" >
								门店账号添加
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							账号
						</td>
						<td align="center">
							权限
						</td>
						<td align="center">
							状态
						</td>
						<td align="center">
							备注
						</td>
					</tr>
					<c:forEach var="item" items="${listAccount}" varStatus="status">
						<tr>
							<td align="center">
								${status.index+1}
							</td>
							<td align="center">
								${item.account_number}
							</td>
							<td align="center">
								<c:if test="${item.authority=='0'}">所有权限</c:if>
								<c:if test="${item.authority=='1'}">交易验证</c:if>
							</td>
							<td align="center">
								<c:if test="${item.status=='0'}">开通</c:if>
								<c:if test="${item.status=='1'}">关闭</c:if>
								<c:if test="${item.status=='2'}">未改密</c:if>
							</td>
							<td align="center">
								${item.remark}
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</form>
	</body>
</html>
