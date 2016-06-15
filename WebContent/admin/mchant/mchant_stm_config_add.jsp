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
		
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="savemchantStmConfig.jhtml" method="post">
			<input type="hidden" id="mchantNo" name="mchantNo" value="${mchantNo}"/>
			<input type="hidden" id="mch_name" name="mch_name" value="${mch_name}"/>
			<div>
				<div align="left">
					您现在的位置：商户添加&gt;&gt;商户结算信息配置
				</div>
				
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="12">
							<span style="float: left" >
								<strong>
									商户名称:${mch_name}&nbsp;&nbsp;&nbsp;商户编号:${mchantNo}
								</strong>
							</span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							银行账号
						</td>
						<td align="center">
							<input type="text" id="mch_account_card" name="mch_account_card" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							账户名称
						</td>
						<td align="center">
							<input type="text" id="mch_account_name" name="mch_account_name" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							开户行
						</td>
						<td align="center">
							<input type="text" id="mch_bank_open_bank" name="mch_bank_open_bank" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							支行行号
						</td>
						<td align="center">
							<input type="text" id="mch_bank_open_bank_no" name="mch_bank_open_bank_no" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							开户地址
						</td>
						<td align="center">
							<input type="text" id="mch_bank_open_addr" name="mch_bank_open_addr" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							账号类型
						</td>
						<td align="center">
							<select id="mch_account_type" name="mch_account_type">
								<option value="0">个人账户</option>
								<option value="1">企业账户</option>
							</select>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							联系人
						</td>
						<td align="center">
							<input type="text" id="mch_account_person" name="mch_account_person" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							联系电话
						</td>
						<td align="center">
							<input type="text" id="mch_account_phone" name="mch_account_phone" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							邮箱
						</td>
						<td align="center">
							<input type="text" id="mch_email" name="mch_email" required="required"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							结算类型
						</td>
						<td align="center">
							<select id="mch_setment_type" name="mch_setment_type">
								<option value="1">全额结算</option>
								<option value="2">非全额结算</option>
							</select>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							结算周期类型
						</td>
						<td align="center">
							<select id="mch_settlement_period" name="mch_settlement_period">
								<option value="1">每月1次</option>
								<option value="2">每月2次</option>
								<option value="3">每月3次</option>
								<option value="4">每周2结算</option>
								<option value="5">每周2、5结算</option>
							</select>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							垫款限额(元)
						</td>
						<td align="center">
							<input type="text" id="mch_advance_quota" name="mch_advance_quota" required="required"/>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="submit" value="开通"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>