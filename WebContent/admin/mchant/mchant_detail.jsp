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
		<title>商户详情</title>
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
		<script type="text/javascript">
			function seePic(url){
				window.location.href="mchantSeepic.jhtml?imgUrl="+url;
			}
			
			function openPuerLook(){
				frmPage.action="mchOpenPuerDetail.jhtml";
				frmPage.submit();
			}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="mchantStoreDetail.jhtml" method="post">
			<input type="hidden" name="mchantNo" id="mchantNo" value="${mchant.mch_number}"/>
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;商户详情
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="2">
							<strong>商户基本信息</strong>
						</th>
					</tr>
					<tr>
						<td align="center">
							商户名称:
						</td>
						<td align="center">
							${mchant.mch_name}
						</td>
					</tr>
					<tr>
						<td align="center">
							商户编号:
						</td>
						<td align="center">
							${mchant.mch_number}
						</td>
					</tr>
					<tr>
						<td align="center">
							使用说明:
						</td>
						<td align="center">
							${mchant.use_note}
						</td>
					</tr>
					<tr>
						<td align="center">
							储值卡类型:
						</td>
						<td align="center">
							<c:if test="${mchant.sale_card_type=='0'}">买送卡</c:if>
							<c:if test="${mchant.sale_card_type=='1'}">买折卡</c:if>
						</td>
					</tr>
					<tr>
						<td align="center">
							折扣说明:
						</td>
						<td align="center">
							${mchant.discount_note}
						</td>
					</tr>
					<tr>
						<td align="center">
							普通用户买单折扣:
						</td>
						<td align="center">
							${mchant.mch_min_discount}
						</td>
					</tr>
					<tr>
						<td align="center">
							人均消费（单位元）:
						</td>
						<td align="center">
							<c:if test="${mchant.capita_consumption==null}">未设置</c:if>
							<c:if test="${mchant.capita_consumption!=null}">
								<fmt:formatNumber value="${mchant.capita_consumption/100}" pattern="##0.00"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td align="center">
							最大折扣:
						</td>
						<td align="center">
							${mchant.mch_max_discount}
						</td>
					</tr>
					<tr>
						<td align="center">
							结算手续费:
						</td>
						<td align="center">
							${mchant.sale_poundage}
						</td>
					</tr>
					<tr>
						<td align="center">
							城市标签:
						</td>
						<td align="center">
							<c:forEach var="item" items="${listCity}" varStatus="status">
								${item.city_name } 
							</c:forEach>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户状态:
						</td>
						<td align="center">
							<strong>
								<c:if test="${mchant.status==0}">
									开通
								</c:if>
								<c:if test="${mchant.status==1}">
									关闭
								</c:if>
								<c:if test="${mchant.status==9}">
									已删除
								</c:if>
							</strong>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							售卡权限:
						</td>
						<td align="center">
							<strong>
								<c:if test="${mchant.sale_card_status==0}">
									开通
								</c:if>
								<c:if test="${mchant.sale_card_status==1}">
									关闭
								</c:if>
							</strong>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							首次开通是否购卡:
						</td>
						<td align="center">
							<strong>
								<c:if test="${mchant.type_open==0}">
									购卡&nbsp;&nbsp;<input type="button" value="查看购卡情况" onclick="openPuerLook()"/>
								</c:if>
								<c:if test="${mchant.type_open==1}">
									不购卡
								</c:if>
								<c:if test="${mchant.type_open==2}">
									商户开通时未进行开通购卡步骤
								</c:if>
							</strong>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户关键字:
						</td>
						<td align="center">
							${mchant.mch_key_word}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户图片:
						</td>
						<td align="center">
							<input type="button" value="查看" onclick="seePic('${mchPic}')"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户logo:
						</td>
						<td align="center">
							<input type="button" value="查看" onclick="seePic('${mchant.pic_url_logo}')"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							营业时间:
						</td>
						<td align="center">
							${mchant.mch_time_sale}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							所属商圈:
						</td>
						<td align="center">
							${mchant.mch_biz_districts}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							储值卡使用范围:（哪类商品无法使用）
						</td>
						<td align="center">
							${mchant.mch_card_range}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户简介:
						</td>
						<td align="center">
							${mchant.mch_more}
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
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
					<c:forEach var="item" items="${listAllot}" varStatus="status">
						<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
							onMouseout='onTrMoveOut(this)'
							class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
							<td align="center">
							 	${item.a_allot}
							</td>
							<td align="center">
							 	${item.b_allot}
							</td>
							<td align="center">
								${item.o_allot}
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
				</table>
				
			
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="4">
							<strong>商户工商信息</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户工商注册名:
						</td>
						<td align="center">
							${mchant.mch_indus_name}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户营业期限:
						</td>
						<td align="center">
							${mchant.mch_oper_per}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户法人:
						</td>
						<td align="center">
							${mchant.mch_leg_person}
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="4">
							<strong>商户账户</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							账号:
						</td>
						<td align="center">
							权限:
						</td>
						<td align="center">
							状态:
						</td>
						<td align="center">
							备注:
						</td>
					</tr>
					<c:forEach var="item" items="${listMchantAccount}" varStatus="status">
						<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
							onMouseout='onTrMoveOut(this)'
							class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
							<td align="center">
							 	${item.account_number}
							</td>
							<td align="center">
							 	<c:if test="${item.authority==0}">
							 		所有权限
							 	</c:if>
							 	<c:if test="${item.authority==1}">
							 		交易验证
							 	</c:if>
							</td>
							<td align="center">
								<c:if test="${item.status==0}">开通</c:if>
							 	<c:if test="${item.status==1}">关闭</c:if>
							 	<c:if test="${item.status==2}">未改密</c:if>
							</td>
							<td align="center">
							 	${item.remark}
							</td>
						</tr>
					</c:forEach>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="8">
							<strong>商户储值卡</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号:
						</td>
						<td align="center">
							卡类型:
						</td>
						<td align="center">
							卡种:
						</td>
						<td align="center">
							状态:
						</td>
						<td align="center">
							面额（元）:
						</td>
						<td align="center">
							折扣:
						</td>
						<td align="center">
							购买金额（元）:
						</td>
					</tr>
					<c:forEach var="item" items="${listCards}" varStatus="status">
						<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
							onMouseout='onTrMoveOut(this)'
							class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
							<td align="center">
							 	${status.index+1}
							</td>
							<td align="center">
							 	<c:if test="${item.ck_type==0}">
							 		买送
							 	</c:if>
							 	<c:if test="${item.ck_type==1}">
							 		折扣
							 	</c:if>
							</td>
							<td align="center">
							 	${item.ck_name}
							</td>
							<td align="center">
								<c:if test="${item.status==0}">上架</c:if>
							 	<c:if test="${item.status==1}">下架</c:if>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.ck_quota/100}" pattern="##0.00"/>
							</td>
							<td align="center">
							 	${item.discount}
							</td>
							<td align="center">
							 	<fmt:formatNumber value="${item.sales_amount/100}" pattern="##0.00"/>
							</td>
					    </tr>
					</c:forEach>
				</table>
				<table class="table_a marginb10" width="90%">
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
						 	${stmConfig.mch_account_card}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							账户名称:
						</td>
						<td align="center">
							${stmConfig.mch_account_name}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							开户行:
						</td>
						<td align="center">
							${stmConfig.mch_bank_open_bank}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							支行行号:
						</td>
						<td align="center">
							${stmConfig.mch_bank_open_bank_no}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							开户地址:
						</td>
						<td align="center">
							${stmConfig.mch_bank_open_addr}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							账户类型:
						</td>
						<td align="center">
							<c:if test="${stmConfig.mch_account_type==0}">
								个人账户
							</c:if>
							<c:if test="${stmConfig.mch_account_type==1}">
								企业账户
							</c:if>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							联系人:
						</td>
						<td align="center">
							${stmConfig.mch_account_person}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							联系电话:
						</td>
						<td align="center">
							${stmConfig.mch_account_phone}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							邮箱:
						</td>
						<td align="center">
							${stmConfig.mch_email}
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							结算类型:
						</td>
						<td align="center">
							<c:if test="${stmConfig.mch_setment_type==1}">全额结算</c:if>
							<c:if test="${stmConfig.mch_setment_type==2}">非全额结算</c:if>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							结算周期:
						</td>
						<td align="center">
							<c:if test="${stmConfig.mch_settlement_period==1}">每月1次</c:if>
							<c:if test="${stmConfig.mch_settlement_period==2}">每月2次</c:if>
							<c:if test="${stmConfig.mch_settlement_period==3}">每月3次</c:if>
							<c:if test="${stmConfig.mch_settlement_period==4}">每周2结算</c:if>
							<c:if test="${stmConfig.mch_settlement_period==5}">每周2、5结算</c:if>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							商户垫款限额:
						</td>
						<td align="center">
							<fmt:formatNumber value="${stmConfig.mch_advance_quota/100}" pattern="##0.00"/>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="10">
							<input type="button" value="返回" onclick="javascript:history.back()"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>