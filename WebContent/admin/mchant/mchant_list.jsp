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
		<title>商户列表</title>
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
		//查看商户详情
		function mchantDetial(mchantNo){
			frmPage.action="mchantDetail.jhtml?mchantNo="+mchantNo;
			frmPage.submit();
		}
		//商户删除
		function mchantDel(mchantNo){
			var flag = window.confirm("是否删除该商户？");
			if(flag){
				frmPage.action="mchantDel.jhtml?mchantNo="+mchantNo;
				frmPage.submit();
			}
		}
		//商户修改
		function mchantUpdate(mchantNo){
			frmPage.action="mchantUpdate.jhtml?mchantNo="+mchantNo;
			frmPage.submit();
		} 
		
		function checkThis(id){
			var s=  document.getElementById("ddiv_"+id).style.display;
			if(s=="none"||s==""){
				document.getElementById("ddiv_"+id).style.display="block";
			}
			else{
				document.getElementById("ddiv_"+id).style.display="none";
			}
		}
		
		function closeThis(id){
			document.getElementById("ddiv_"+id).style.display="none";
		}
		
		function updateInfo(flag,mchantNo){
			if(flag=="store"){
				//修改门店信息
				frmPage.action="storeUpdate.jhtml?mchantNo="+mchantNo;
				frmPage.submit();
			}
			if(flag=="account"){
				//修改商户账号		
				frmPage.action="accountUpdate.jhtml?mchantNo="+mchantNo;
				frmPage.submit();
			}
			if(flag=="card"){
				//修改储值卡信息
				frmPage.action="cardUpdate.jhtml?mchantNo="+mchantNo;
				frmPage.submit();
			}	
			if(flag=="allot"){
				//修改储值卡信息
				frmPage.action="allotUpdate.jhtml?mchantNo="+mchantNo;
				frmPage.submit();
			}
			if(flag=="stmConfig"){
				//修改结算信息
				frmPage.action="astmConfigUpdate.jhtml?mchantNo="+mchantNo;
				frmPage.submit();
			}
		}
		
		function mchantShipList(mchNumber){
			frmPage.action="mchantShipList.jhtml?mchNumber="+mchNumber;
			frmPage.submit();
		} 
		
		function mchStore(mchNumber){
			frmPage.action="mchantStoreDetail.jhtml?mchantNo="+mchNumber;
			frmPage.submit();
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="mchant_list.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;商户列表
				</div>
				<table class="table_a marginb10" width="90%">
				<th align="left" colspan="12">
					<strong>查询配置
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户总数:${count}
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已开通商户:${openCount}
					</strong>
				</th>
					<tr height="10">
					<td align="left">
						<strong>商户名称:</strong>
						<input type="text" id="mchantName" name="mchantName" value="${param.mchantName}"/>
					</td>
					<td align="left">
						<strong>商户编号:</strong>
					 	<input  type="text" id="mchantNo" name="mchantNo"  value="${param.mchantNo}"/>
					</td>
					<td align="left">
						<strong>商户状态:</strong>
						<select id="status" name="status">
							<c:if test="${param.status==8||param.status==null}">
								<option value="8" selected="selected">所有</option>
								<option value="0">开通</option>
								<option value="1">关闭</option>
								<option value="9">已删除</option>
							</c:if>
							<c:if test="${param.status==0}">
								<option value="8">所有</option>
								<option value="0" selected="selected">开通</option>
								<option value="1">关闭</option>
								<option value="9">已删除</option>
							</c:if>
							<c:if test="${param.status==1}">
								<option value="8">所有</option>
								<option value="0">开通</option>
								<option value="1" selected="selected">关闭</option>
								<option value="9">已删除</option>
							</c:if>
							<c:if test="${param.status==9}">
								<option value="8">所有</option>
								<option value="0">开通</option>
								<option value="1">关闭</option>
								<option value="9" selected="selected">已删除</option>
							</c:if>
						</select>					
						<input type="button" onclick="checkForm()" value="查 询" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="addmchant()" value="新 增" />
					</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<th align="left" colspan="7">
						<strong>查询结果列表</strong>
					</th>
					<tr class="align_Center">
						<td width="4%">
							<strong>序号</strong>
						</td>
						<td width="7%">
							<strong>商户代码</strong>
						</td>
						<td width="4%">
							<strong>商户名称</strong>
						</td>
						<td width="10%">
							<strong>当前状态</strong>
						</td>
						<td width="10%">
							<strong>录入日期</strong>
						</td>
						<td width="10%">
							<strong>操作</strong>
						</td>
						
					</tr>
					<c:forEach var="item" items="${page.result}" varStatus="status">
						<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
							onMouseout='onTrMoveOut(this)'
							class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
							<td align="center">
								${status.index+1}
							</td>
							<td align="center">
								${item.mch_number}
							</td>
							<td align="center">
								${item.mch_name}
							</td>
							<td align="center">
								<c:if test="${item.status==0}" >开通</c:if>
							 	<c:if test="${item.status==1}" >关闭</c:if>
							 	<c:if test="${item.status==9}" >已删除</c:if>
							</td>
							<td align="center">
								<fmt:formatDate value="${item.create_date}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center">
								<input type="button" value="门店列表" onclick="mchStore('${item.mch_number}')"/>
								<input type="button" value="详情" onclick="mchantDetial('${item.mch_number}')"/>
								&nbsp;&nbsp;
								<c:if test="${item.status!=9}" >
									<input type="button" value="删除" onclick="mchantDel('${item.mch_number}')"/>
									<input type="button" value="修改" onclick="checkThis(${status.index+1})" />
									<div class="ddiv" id="ddiv_${status.index+1}">
										<input type="button" value="修改基本信息" onclick="mchantUpdate('${item.mch_number}')"/><br/>
										<input type="button" value="修改商户账号" onclick="updateInfo('account','${item.mch_number}')"/><br/>
										<input type="button" value="修改储值卡" onclick="updateInfo('card','${item.mch_number}')"/><br/>
										<input type="button" value="修改B类消费分配" onclick="updateInfo('allot','${item.mch_number}')"/><br/>
										<input type="button" value="修改结算配置" onclick="updateInfo('stmConfig','${item.mch_number}')"/><br/>
									</div>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>