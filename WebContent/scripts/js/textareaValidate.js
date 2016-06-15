$(function() {
   	//匹配包含给定属性的元素，keyup在按键释放时发生
      $("textarea[maxlength]").keyup(function() {
          var area = $(this);
          //parseInt 方法返回与保存在 numString 中的数字值相等的整数。如果 numString 的前缀不能解释为整数，则返回 NaN（而不是数字）。
          var max = parseInt(area.attr("maxlength"), 10); //获取maxlength的值 转化为10进制，将输入到textarea的文本长度
          //这个判断可知max得到的是不是数字，设定的大小是多少
          if (max > 0) {
              if (area.val().length > max) { //textarea的文本长度大于maxlength 
                  area.val(area.val().substr(0, max)); //截断textarea的文本重新赋值 
              }
          }
      });
      $("textarea[maxlength]").blur(function() {
          var area = $(this);
          var max = parseInt(area.attr("maxlength"), 10); //获取maxlength的值 
          if (max > 0) {
              if (area.val().length > max) { //textarea的文本长度大于maxlength 
                  area.val(area.val().substr(0, max)); //截断textarea的文本重新赋值 
              }
          }
      });
}); 
