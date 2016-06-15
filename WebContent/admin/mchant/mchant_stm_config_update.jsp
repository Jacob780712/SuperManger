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
		<title>商户修改</title>
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
		<form name="frmPage" id="queryFrom" action="excuteAstmConfigUpdate.jhtml" onsubmit="return check()" method="post">
			<input type="hidden" id="mchantNo" name="mchantNo" value="${mchant.mch_number}"/>
			<div>
				<div align="left">
					您现在的位置：商户添加&gt;&gt;商户结算配置修改
				</div>
				
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="10">
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
						<th align="left" colspan="10">
							<strong>商户结算配置</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							结算银行账号:
						</td>
						<td align="center">
						 	<input type="text" id="mch_account_card" name="mch_account_card" value="${stmConfig.mch_account_card}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">	
						<td align="center">
							账户名称:
						</td>
						<td align="center">
							<input type="text" id="mch_account_name" name="mch_account_name" value="${stmConfig.mch_account_name}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							开户行:
						</td>
						<td align="center">
							<input type="text" id="mch_bank_open_bank" name="mch_bank_open_bank" value="${stmConfig.mch_bank_open_bank}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							支行行号:
						</td>
						<td align="center">
							<input type="text" id="mch_bank_open_bank_no" name="mch_bank_open_bank_no" value="${stmConfig.mch_bank_open_bank_no}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							开户地址:
						</td>
						<td align="center">
							<input type="text" id="mch_bank_open_addr" name="mch_bank_open_addr" value="${stmConfig.mch_bank_open_addr}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							账户类型:
						</td>
						<td align="center">
							<select id="mch_account_type" name="mch_account_type">
								<c:if test="${stmConfig.mch_account_type==null}">
									<option value="0">个人账户</option>
									<option value="1">企业账户</option>
								</c:if>
								<c:if test="${stmConfig.mch_account_type==0}">
									<option value="0" selected="selected">个人账户</option>
									<option value="1">企业账户</option>
								</c:if>
								<c:if test="${stmConfig.mch_account_type==1}">
									<option value="0" selected="selected">个人账户</option>
									<option value="1">企业账户</option>
								</c:if>
							</select>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							联系人:
						</td>
						<td align="center">
							<input type="text" id="mch_account_person" name="mch_account_person" value="${stmConfig.mch_account_person}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							联系电话:
						</td>
						<td align="center">
							<input type="text" id="mch_account_phone" name="mch_account_phone" value="${stmConfig.mch_account_phone}" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							邮箱:
						</td>
						<td align="center">
							<input type="text" id="mch_email" name="mch_email" value="${stmConfig.mch_email}"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							结算类型:
						</td>
						<td align="center">
							<c:if test="${stmConfig.mch_setment_type==null}">
								<select name="mch_setment_type" id="mch_setment_type">
									<option value="1">全额结算</option>
									<option value="2">非全额结算</option>
								</select>
							</c:if>
							<c:if test="${stmConfig.mch_setment_type==1}">
								<select name="mch_setment_type" id="mch_setment_type">
									<option value="1" selected="selected">全额结算</option>
									<option value="2">非全额结算</option>
								</select>
							</c:if>
							<c:if test="${stmConfig.mch_setment_type==2}">
								<select name="mch_setment_type" id="mch_setment_type">
									<option value="1">全额结算</option>
									<option value="2" selected="selected">非全额结算</option>
								</select>
							</c:if>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							结算周期:
						</td>
						<td align="center">
							<select id="mch_settlement_period" name="mch_settlement_period">
								<c:if test="${stmConfig.mch_settlement_period==null}">
									<option value="1">每月1次</option>
									<option value="2">每月2次</option>
									<option value="3">每月3次</option>
									<option value="3">每月3次</option>
									<option value="4">每周2结算</option>
									<option value="5">每周2、5结算</option>
								</c:if>
								<c:if test="${stmConfig.mch_settlement_period==1}">
									<option value="1" selected="selected">每月1次</option>
									<option value="2">每月2次</option>
									<option value="3">每月3次</option>
									<option value="4">每周2结算</option>
									<option value="5">每周2、5结算</option>
								</c:if>
								<c:if test="${stmConfig.mch_settlement_period==2}">
									<option value="1">每月1次</option>
									<option value="2" selected="selected">每月2次</option>
									<option value="3">每月3次</option>
									<option value="4">每周2结算</option>
									<option value="5">每周2、5结算</option>
								</c:if>
								<c:if test="${stmConfig.mch_settlement_period==3}">
									<option value="1">每月1次</option>
									<option value="2">每月2次</option>
									<option value="3" selected="selected">每月3次</option>
									<option value="4">每周2结算</option>
									<option value="5">每周2、5结算</option>
								</c:if>
								<c:if test="${stmConfig.mch_settlement_period==4}">
									<option value="1">每月1次</option>
									<option value="2">每月2次</option>
									<option value="3">每月3次</option>
									<option value="4" selected="selected">每周2结算</option>
									<option value="5">每周2、5结算</option>
								</c:if>
								<c:if test="${stmConfig.mch_settlement_period==5}">
									<option value="1">每月1次</option>
									<option value="2">每月2次</option>
									<option value="3">每月3次</option>
									<option value="4">每周2结算</option>
									<option value="5" selected="selected">每周2、5结算</option>
								</c:if>
							</select>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户垫款限额:
						</td>
						<td align="center">
							<input type="text" id="mch_advance_quota" name="mch_advance_quota" value="<fmt:formatNumber value="${stmConfig.mch_advance_quota/100}" pattern="##0.00"/>" required="required"/>
						</td>
					</tr>
				    <tr>
						<td align="center" colspan="2">
							<input type="submit" value="提交" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>