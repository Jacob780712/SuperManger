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
		<title>商户储值卡修改</title>
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
		var odlLength = ${cardSize};
		function addTr(){
			var table = document.getElementById("store_table"); 
			var odlLength = table.rows.length;
			var row = table.insertRow(table.rows.length);
			var td1 = row.insertCell(0);
			td1.align="center";
			var index = table.rows.length-2;
			td1.innerHTML = index;
			var td2 = row.insertCell(1);
			td2.align="center";
			td2.innerHTML="<input type='text' id='ck_name"+index+"' name='ck_name"+index+"' required=\"required\"/>";
			
			var td3 = row.insertCell(2);
			td3.align="center";
			if("${mchant.sale_card_type}"=='0'){
				var cont = "<select id='ck_type"+index+"' name='ck_type"+index+"' ><option value='0'>买送</option></select>";
			}else{
				var cont = "<select id='ck_type"+index+"' name='ck_type"+index+"' ><option value='1'>买折</option></select>";
			}
			td3.innerHTML=cont;
			
			var td4 = row.insertCell(3);
			td4.align="center";
			td4.innerHTML="<input type='text' id='ck_quota"+index+"' name='ck_quota"+index+"' required=\"required\"/>";
			
			var td5 = row.insertCell(4);
			td5.align="center";
			if("${mchant.sale_card_type}"=='0'){
				td5.innerHTML="<input type='text' id='sales_amount"+index+"' name='sales_amount"+index+"' required=\"required\" onblur='setDiscount("+index+")'/>";
			}
			if("${mchant.sale_card_type}"=='1'){
				td5.innerHTML="<input type='text' id='sales_amount"+index+"' name='sales_amount"+index+"' required=\"required\"/>";
			}
			var td6 = row.insertCell(5);
			td6.align="center";
			td6.innerHTML="<input type='text' id='discount"+index+"' name='discount"+index+"' required=\"required\" />";
			var td8 = row.insertCell(6);
			td8.align="center";
			td8.innerHTML="<select id='status"+index+"' name='status"+index+"' ><option value='0'>上架</option><option value='1'>下架</option></select>";
			var td9 = row.insertCell(7);
			td9.align="center";
			td9.innerHTML = "<input type='button' value='删除' onclick='removeTr("+(table.rows.length-1)+",+"+odlLength+")'/>";
			var card_count = document.getElementById("card_count");
			card_count.value=parseInt(card_count.value)+1;
		}
		
		function removeTr(tr_id,odlLength){
			var table = document.getElementById("store_table"); 
			table.deleteRow(tr_id);
			var card_count = document.getElementById("card_count");
			card_count.value=card_count.value-1;
			var table2 = document.getElementById("store_table"); 
			for(var i=odlLength;i<table2.rows.length;i++){
				table2.rows[i].cells[0].innerHTML = i-1;
				table2.rows[i].cells[8].innerHTML = "<input type='button' value='删除' onclick='removeTr("+i+","+odlLength+")'/>";
			}
		}
	
		function setDiscount(id){
			var ck_quota = document.getElementById("ck_quota"+id).value;
			var sales_amount = document.getElementById("sales_amount"+id).value;
			if(ck_quota==""||sales_amount==""){
				alert("请填写卡面额或购买金额");
				return;
			}
			var ck_type = document.getElementById("ck_type"+id).value;
			if(ck_type==0){
				var dis = sales_amount*1/ck_quota*1;
				document.getElementById("discount"+id).value = dis.toFixed(2);
			}
		}
		</script>
	</head>
	<body>
		<div>
			<form action="excuteCardUpdate.jhtml" method="post">
				<input type="hidden" name="mchantNo" id="mchantNo" value="${mchant.mch_number}"/>
				<input type="hidden" name="mchantName" id="mchantName" value="${mchant.mch_name}"/>
				<input type="hidden" name="card_count" id="card_count" value="0"/>
				<input type="hidden" name="cardSize" id="cardSize" value="${cardSize}"/>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;商户储值卡修改
				</div>
				<table class="table_a marginb10" width="90%" id="store_table">
					<tr>
						<th align="left" colspan="9">
							<span style="float: left" >
								<strong>商户名称：${mchant.mch_name}&nbsp;&nbsp;&nbsp;商户编号：${mchant.mch_number}</strong>
							</span>
							<span style="float: right" >
								<input type="button" value="添加" onclick="addTr()"/>
							</span>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号:
						</td>
						<td align="center">
							卡种:
						</td>
						<td align="center">
							卡类型:
						</td>
						<td align="center">
							面额:
						</td>
						<td align="center">
							购买金额:
						</td>
						<td align="center">
							折扣（如0.7表示7折）:
						</td>
						<td align="center">
							状态:
						</td>
						<td align="center">
							操作：
						</td>
					</tr>
					<c:forEach var="item" items="${listCards}" varStatus="status">
						<tr>
							<td align="center">
							 	${status.index+1}
							 	<input type="hidden" name="cardId${status.index}" id="cardId${status.index}" value="${item.id}"/>
							</td>
							<td align="center">
								${item.ck_name}
							</td>
							<td align="center">
								<c:if test="${item.ck_type==0}">买送</c:if>
								<c:if test="${item.ck_type==1}">折扣</c:if>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.ck_quota/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.sales_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								${item.discount}
							</td>
							<td align="center">
								<select name="cardStatus${status.index}" id="cardStatus${status.index}">
									<c:if test="${item.status==0}">
										<option value="0" selected="selected">上架</option>
										<option value="1">下架</option>
									</c:if>
									<c:if test="${item.status==1}">
										<option value="0">上架</option>
										<option value="1"  selected="selected">下架</option>
									</c:if>
								</select>
							</td>
							<td align="center">
								
							</td>
						</tr>
					</c:forEach>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<td align="center" colspan="8">
							<input type="submit" value="提交"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="返回" onclick="javascript:history.back()"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>