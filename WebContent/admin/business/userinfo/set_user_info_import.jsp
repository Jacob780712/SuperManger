<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>数据上传</title>
		<script language="javascript" src="../scripts/common.js"></script>
		<link href="../themes/default/css.css" rel="stylesheet" type="text/css" />
		<link href="../themes/admin/user_userinfo.css" type=text/css rel=stylesheet>
		<link href="../themes/admin/common.css" type=text/css rel=stylesheet>
		<link href="../themes/admin/userhome.css" type=text/css rel=stylesheet>
		<link href="../themes/admin/css.css" type=text/css rel=stylesheet>
		<script language="javascript" src="../scripts/jQuery/jquery-1.4.4.min.js"></script>
		<script  type="text/javascript">
		function CheckForm()
		{
		  var fileName = jQuery("#upload").val();
		  if(fileName=="")
		  {
		   alert("请选择文件");
		   return false;
		  }
		  
			var index = fileName.lastIndexOf(".");
			if(index < 0) {
				alert("上传的文件格式不正确，请选择97-2003Excel文件(*.xls)！");
				return false;
			} else {
				var ext = fileName.substring(index + 1, fileName.length);
				if(ext != "xls" && ext != "xlsx") {
				 alert("上传的文件格式不正确，请选择97-2003Excel文件(*.xls)！");
				 return false;
				}
			}
			  
		  jQuery("#subbtn").attr({"disabled":"disabled"});
		  jQuery("#viewProces").show();
		  return true;
		}		
		</script>
	</head>
	<body  id="global_body">
		<br/>
		<table class="table_a marginb10" >
			<tr>
				<th align="left">
					<strong>上传付款结果数据</strong>
				</th>
			</tr>
		</table><br/><br/>
		<div align="left">
			<form action="importResultUserBankCard.jhtml" method="post" name="frmPage"   enctype ="multipart/form-data" onsubmit="return CheckForm();">
				<input type="hidden" id="userName" name="userName" value="${userName}" />
				<input type="hidden" id="mobliePhone" name="mobliePhone" value="${mobliePhone}" />
				<input type="hidden" id="cardNumber" name="cardNumber" value="${cardNumber}" />
				<input type="hidden" id="idCard" name="idCard" value="${idCard}" />
				<table  class="table_a marginb10">
					<tr class="align_Center">
						<th width="15%">
							<strong>文件</strong>
						</th>
						<td  width="85%" align="left"><input type="file" id="upload" name="upload" />
						</td>
					</tr>
					
					<tr class="align_Center">
						<td colspan="2">
							<input type="submit" name="sub" id="subbtn" value="上传" /> <span id="viewProces" style="display:none;color:red;">正在上传请稍等......</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>

</html>