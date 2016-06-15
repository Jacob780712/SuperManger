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
		<title>商户添加</title>
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
		<style type="text/css">
			.cityDiv{
				width: 400px;
				height: auto !important;
			}
		</style>
		<script type="text/javascript">
			function check(){
				var a_allot = document.getElementById("a_allot").value;
				var b_allot = document.getElementById("b_allot").value;
				var o_allot = document.getElementById("o_allot").value;
				if(parseInt(a_allot)+parseInt(b_allot)+parseInt(o_allot)!=100){
					alert("分配总和应该为100%,请重新添加！")
					return false;
				}else{
					return true;
				}
			}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="excuteAllotUpdate.jhtml" onsubmit="return check()" method="post">
			<input type="hidden" id="mchantNo" name="mchantNo" value="${mchant.mch_number}"/>
			<div>
				<div align="left">
					您现在的位置：商户添加&gt;&gt;B类消费折扣分配修改
				</div>
				
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="12">
							<span style="float: left" >
								<strong>
									商户名称:${mchant.mch_name}&nbsp;&nbsp;&nbsp;商户编号:${mchant.mch_number}
								</strong>
							</span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
					<tr>
						<th align="left" colspan="4">
							<strong>商户B类消费折扣分配详情</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							用户A分配(单位百分比)
						</td>
						<td align="center">
							用户B分配(单位百分比)
						</td>
						<td align="center">
							公司O分配(单位百分比)
						</td>
						<td align="center">
							状态 
						</td>
					</tr>
					<c:if test="${alloCount>0}">
						<c:forEach var="item" items="${listAllot}" varStatus="status">
							<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
								onMouseout='onTrMoveOut(this)'
								class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
								<td align="center">
								  	<input type="text" id="a_allot" name="a_allot" value="${item.a_allot}"/>
								</td>
								<td align="center">
								 	<input type="text" id="b_allot" name="b_allot" value="${item.b_allot}"/>
								</td>
								<td align="center">
									<input type="text" id="o_allot" name="o_allot" value="${item.o_allot}"/>
								</td>
								<td align="center">
									<c:if test="${item.status==0}">
								 		生效
								 	</c:if>
								 	<c:if test="${item.status==1}">
								 		失效
								 	</c:if>
								 	<c:if test="${item.status==9}">
								 		已删除
								 	</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${alloCount<=0}">
						<tr>
							<td align="center">
								<input type="hidden" id="allotFlag" name="allotFlag" value="empty"/>
							  	<input type="text" id="a_allot" name="a_allot" />
							</td>
							<td align="center">
							 	<input type="text" id="b_allot" name="b_allot" />
							</td>
							<td align="center">
								<input type="text" id="o_allot" name="o_allot" />
							</td>
							<td align="center">
								<select id="status" name="status">
									<option value="0">生效</option>
								</select>
							</td>
						</tr>
					</c:if>
					<tr>
						<td align="center" colspan="4">
							<input type="submit" value="提交" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>