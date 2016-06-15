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
    <title>新增二维码</title>
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
        function createQrCode(){
        	var qrHref = $("#qrHref").val();
            var channelName = $("#channel").val();
            var remark = $("#remark").val();
            $("#btn_create").attr("disabled","disabled");

            $.ajax({
                type:"POST",
                url:"<%=basePath%>admin/addQrCode.jhtml",
                data:{channel:channelName,remark:remark,qrHref:qrHref},
                dataType:"json",
                success:function(data){
                    if(data.qrPicUrl == null){
                        alert("生成二维码失败！请联系管理员！");
                        return;
                    }
                    $("#qrUrl").val(data.qrPicUrl);
                    $("#channel").attr("readonly","readobly");
                    $("#remark").attr("readonly","readobly");
                    $("#qrImg").attr("src",data.qrPicUrl);
                    $("#qrContent").val(data.qrContent);
                    $("#qrJson").val(data);
                },
                error:function(erro){
                    alert(erro);
                    console.log(erro);
                }
            });
        }
        
    </script>
</head>
<body>

    <div>
        <div align="left">
            您现在的位置：商户管理 &gt;&gt;新增二维码信息
        </div>

        <div>
            <strong>二维码信息</strong>
			<input type="hidden" name="qrJson" id="qrJson"/>
            <table class="table_a marginb10" width="90%">
                <th align="left" colspan="2">
                    <strong>二维码信息配置:</strong>
                </th>
                <th>
                    <input type="button" value="返回" onclick="javascript:history.back()"/>
                </th>
                <tr height="10">
                    <td align="left">
                        <strong>渠道名称:</strong>
                        <input type="text" name="channel" id="channel" />
                    </td>
                    <td align="left" colspan="2">
                        <strong>备注:</strong>
                        <input id="remark" name="remark" />
                    </td>
                </tr>
                <tr height="10">
                 	<td align="left" colspan="3">
                    	<strong>扫码跳转地址:</strong>
                        <input type="text" style="width:800px;" id="qrHref" name="qrHref" required="required"/>
                        <br/>
                        	例如:http://wx.ihuapay.cn/weChat/mch.htm?mchNumber=xxx&branchId=xx<br/>
                        	mchNumber商户编号，branchId门店id
                    </td>
                 </tr>
                <tr>
                    <td align="left">
                        <strong>二维码信息:</strong>
                        <textarea id="qrUrl" name="qrUrl" cols="4" rows="4" readonly="readonly" style="height: 80px; width: 290px;"></textarea>
                    </td>
                    <td align="left">
                        <strong align="center">二维码预览:</strong>
                        <img id="qrImg" src="" width="80" height="80" align="center"/>
                    </td>
                    <td>
                        <button id="btn_create" onclick="createQrCode()" >生 成</button>
                    </td>
                </tr>
                <tr>
                    <td align="left" colspan="3">
                        <strong>二维码内容:</strong>
                        <input type="text" style="width:400px;" id="qrContent" readonly="readonly"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>