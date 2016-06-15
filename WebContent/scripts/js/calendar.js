/*
		var selectDate = new Date();
		selectDate.setFullYear(2007); // 2007年
		selectDate.setMonth(11 - 1); //  11月
		selectDate.setDate(19); // 19日
		*/

		function myDateSelecter() {
			this.fromYear = 2000;
			this.toYear = 2050;
			this.months = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
			this.weeks = ["日", "一", "二", "三", "四", "五", "六"];
			this.selectDate = new Date();

			this.write_css();
			this.write_html();
			this.write_calendar();

			this.layout = document.getElementById('dateSelecter');

			var obj = this;

			this.month_sel = document.getElementById('month_sel');
			this.month_sel.onchange = function() {
			    obj.on_month_change(this.value);
			}

			this.year_sel = document.getElementById('year_sel');
			this.year_sel.onchange = function() {
			    obj.on_year_change(this.value);
			}

			this.clear_but = document.getElementById('clear');
			this.clear_but.onclick= function() {
			    if( obj.formatObj )
				    obj.formatObj.value = '';
			}

			this.close_but = document.getElementById('close');
			this.close_but.onclick= function() {
			    obj.hide();
			}

		}

		myDateSelecter.prototype.write = function(str) {
			document.write(str);
		}

		myDateSelecter.prototype.write_css = function() {
			this.write("<style type='text/css'>");
			this.write(".div_input { border: 1px #cccccc solid; width: 36px; height: 18px; text-align: center; cursor: hand; }");
			this.write(".div_table { border: 0px; background: #cccccc; width: 160px; }");
			this.write(".div_table TR { background: #ffffff; height: 18px; text-align: center; }");
			this.write(".div_table TD { padding: 2px; font-size: 12px; height: 18px; text-align: center; }");
			this.write(".td_day { cursor: hand; }");
			this.write(".td_selected { background: #cccccc; }");
			this.write(".div_table SELECT { height: 18px; width: 100%; }");
			this.write(".div_left { float: left; width: 70px; }");
			this.write(".div_right { float: right; width: 70px; }");
			this.write("</style>");
		}

		myDateSelecter.prototype.write_html = function() {
			this.write("    <div id='dateSelecter' style='display: none; width: 160px; position:absolute; z-index:1; left: 0px; top: 0px;'>");
			this.write("		<table class='div_table' cellspacing='1' cellpadding='0'>");
			this.write("			<tr>");
			this.write("				<td colspan='7'>");
			this.write("					<div class='div_left'>");
			this.write("						<select id='month_sel' size='1'>");
											for( var c=0; c<this.months.length; c++ ) {
												this.write("<option value='" + (c + 1) + "' " + (this.selectDate.getMonth()==c ? "selected='selected'" : "") + ">" + this.months[c] + "</option>");
											}
			this.write("						</select>");
			this.write("					</div>");
			this.write("					<div class='div_right'>");
			this.write("						<select id='year_sel' size='1'");
											for( var c=this.fromYear; c<=this.toYear; c++ ) {
												this.write("<option value='" + c + "' " + (this.selectDate.getFullYear()==c ? "selected='selected'" : "") + ">" + c + "</option>");
											}
			this.write("						</select>");
			this.write("					</div>");
			this.write("				</td>");
			this.write("			</tr>");
			this.write("			<tr>");
								for( var c=0; c<this.weeks.length; c++ ) {
									this.write("<td>" + this.weeks[c] + "</td>");
								}
			this.write("			</tr>");
							for( var r=0; r<6; r++ ) {
								this.write("<tr>");
								for( var c=0; c<7; c++ ) {
									this.write("<td id='td_" + r + "_" + c + "'> </td>");
								}
								this.write("</tr>");
							}
			this.write("		<tr><td colspan='7'><input class='div_input' type='button' id='clear' value='清除' /> <input class='div_input' type='button' id='close' value='关闭' /></td></tr>");
			this.write("		</table>");
			this.write("		</div>");
			this.write("	</div>");
		}
				
		myDateSelecter.prototype.td_on_click = function(td) {
					var tds = document.getElementsByTagName("TD");
					for( var c=0; c<tds.length; c++ ) {
						if(tds[c].className == 'td_day td_selected')
						   tds[c].className = 'td_day';
					}
					td.className = 'td_day td_selected';
					this.selectDate.setDate( parseInt(td.innerHTML) );
					//this.dateSelecter.style.display = 'none';
					if( this.formatObj ) {
					    //this.formatObj.value = this.selectDate;
						// DateUtil.js
						this.formatObj.value = DateUtil.formatDate(this.selectDate, this.formatType);
					}
					this.hide();
		}

		myDateSelecter.prototype.on_month_change = function(m) {
					 this.selectDate.setMonth( parseInt(m) - 1 );
					 this.write_calendar();
		}

		myDateSelecter.prototype.on_year_change = function(y) {
					 this.selectDate.setFullYear( parseInt(y) );
					 this.write_calendar();
		}

		myDateSelecter.prototype.write_calendar = function() {
		            var obj = this;
					var d = this.selectDate;
					var month_day_count = get_month_day_count(d);
					var row = get_row(d);
					var col = get_col(d);
					var month_first_day_col = get_month_first_day_col(d);

					var cell_count = 0;
					var cur_notempty_cell = 0;
					for( var r=1; r<=6; r++ ) {
						for( var c=1; c<=7; c++ ) {
							cell_count ++;
							var td = document.getElementById( "td_" + (r-1) + "_" + (c-1) );
							if(cell_count >= month_first_day_col && cur_notempty_cell < month_day_count) {
								cur_notempty_cell ++;
								if(r == row && c == col) {
									td.className = 'td_day td_selected';
								}
								else
									td.className = 'td_day';
								td.onclick = function() { obj.td_on_click(this); };
								td.ondblclick = function() { obj.td_on_dblclick(this); };
								td.innerHTML = "" + cur_notempty_cell;
							}
							else {
								try {
									td.innerHTML = "";
									td.className = '';
									td.onclick = function() {};
									td.ondblclick = function() {};
								} catch(e) {}
							}
						}
					}
		}

		// 修改这个函数，可以返回不同格式的日期,懒得再写了
		myDateSelecter.prototype.show = function(input, format) {
		    this.formatObj = document.getElementById(input);
			this.formatType = format;

			if( this.formatObj.value != '' ) {
			    // DateUtil.js
				//alert(1);
				var d = DateUtil.parseDate(this.formatObj.value, this.formatType);
				//alert(d);
				this.selectDate = d;
				this.month_sel.selectedIndex = this.selectDate.getMonth();
				//this.year_sel.selectedIndex = this.selectDate.getMonth();
				var ops = this.year_sel.options;
				for( var c=0; c<ops.length; c++ ) {
				    if( ops[c].value == (this.selectDate.getFullYear() + '') ) {
					    this.year_sel.selectedIndex = c;
						break ;
					}
				}
				this.write_calendar();
			}

		    this.layout.style.left = window.event.clientX;
			this.layout.style.top = window.event.clientY;
		    this.layout.style.display = "block";
		}

		myDateSelecter.prototype.hide = function() {
		    this.layout.style.display = "none";
		}

		function get_row(d) { // 当前天的所在行,从1开始
			return Math.ceil( (d.getDate() + get_month_first_day_col(d) - 1) / 7 );
		}

		function get_col(d) { // 当前天的所在列,从1~7
			return d.getDay() + 1;
		}

		function get_month_day_count(d) { // 当前月的天数
			var md = d.getMonth() + 1;
			if( md == 1 || md == 3 || md == 5 || md == 7 || md == 8 || md == 10 || md == 12)
				return 31;
			else if( d.getFullYear() % 4 > 0 && md == 2)
				return 28;
			else if( d.getFullYear() % 4 == 0 && md == 2)
				return 29;
			else
				return 30;
		}

		function get_month_first_day_col(d) { // 当前月第一天的所在列,肯定是第一行
			var t = d.getDate();
			d.setDate(1);
			var r = get_col(d);
			d.setDate(t);
			return r;
		}

		function get_total_row(d) {
			return Math.ceil( (get_month_first_day_col(d) + get_month_day_count(d)) / 7 );
		}



// DateUtil 是来自别人的代码

function DateUtil() {}

DateUtil.parseDate = function(str, fmt) {
	var today = new Date();
	var y = 0;
	var m = -1;
	var d = 0;
	var a = str.split(/\W+/);
	var b = fmt.match(/%./g);
	var i = 0, j = 0;
	var hr = 0;
	var min = 0;
	for (i = 0; i < a.length; ++i) {
		if (!a[i])
			continue;
		switch (b[i]) {
		    case "%d":
		    case "%e":
			d = parseInt(a[i], 10);
			break;

		    case "%m":
			m = parseInt(a[i], 10) - 1;
			break;

		    case "%Y":
		    case "%y":
			y = parseInt(a[i], 10);
			(y < 100) && (y += (y > 29) ? 1900 : 2000);
			break;

		    case "%b":
		    case "%B":
			for (j = 0; j < 12; ++j) {
				if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { m = j; break; }
			}
			break;

		    case "%H":
		    case "%I":
		    case "%k":
		    case "%l":
			hr = parseInt(a[i], 10);
			break;

		    case "%P":
		    case "%p":
			if (/pm/i.test(a[i]) && hr < 12)
				hr += 12;
			else if (/am/i.test(a[i]) && hr >= 12)
				hr -= 12;
			break;

		    case "%M":
			min = parseInt(a[i], 10);
			break;
		}
	}
	if (isNaN(y)) y = today.getFullYear();
	if (isNaN(m)) m = today.getMonth();
	if (isNaN(d)) d = today.getDate();
	if (isNaN(hr)) hr = today.getHours();
	if (isNaN(min)) min = today.getMinutes();
	if (y != 0 && m != -1 && d != 0)
		return new Date(y, m, d, hr, min, 0);
	y = 0; m = -1; d = 0;
	for (i = 0; i < a.length; ++i) {
		if (a[i].search(/[a-zA-Z]+/) != -1) {
			var t = -1;
			for (j = 0; j < 12; ++j) {
				if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { t = j; break; }
			}
			if (t != -1) {
				if (m != -1) {
					d = m+1;
				}
				m = t;
			}
		} else if (parseInt(a[i], 10) <= 12 && m == -1) {
			m = a[i]-1;
		} else if (parseInt(a[i], 10) > 31 && y == 0) {
			y = parseInt(a[i], 10);
			(y < 100) && (y += (y > 29) ? 1900 : 2000);
		} else if (d == 0) {
			d = a[i];
		}
	}
	if (y == 0)
		y = today.getFullYear();
	if (m != -1 && d != 0)
		return new Date(y, m, d, hr, min, 0);
	return today;
}

DateUtil.formatDate = function(date, fmt) {
	var today = date;
	var y = 0;
	var m = -1;
	var d = 0;
	var mm="";
	var dd="";
	//var a = str.split(/\W+/);
	var b = fmt.match(/%./g);
	var i = 0, j = 0;
	var hr = 0;
	var min = 0;
	var sec = 0;
	for (i = 0; i < b.length; ++i) {
		switch (b[i]) {
		    case "%d":
		    case "%e":
			//d = parseInt(a[i], 10);
			d = today.getDate();
			break;

		    case "%m":
			//m = parseInt(a[i], 10) - 1;
			m = today.getMonth() + 1;
			break;

		    case "%Y":
		    case "%y":
			//y = parseInt(a[i], 10);
			//(y < 100) && (y += (y > 29) ? 1900 : 2000);
			y = today.getFullYear();
			break;

		    case "%b":
		    case "%B":
			for (j = 0; j < 12; ++j) {
				if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { m = j; break; }
			}
			break;

		    case "%H":
		    case "%I":
		    case "%k":
		    case "%l":
			//hr = parseInt(a[i], 10);
			hr = today.getHours() + 1;
			break;

		    case "%P":
		    case "%p":
			if (/pm/i.test(a[i]) && hr < 12)
				hr += 12;
			else if (/am/i.test(a[i]) && hr >= 12)
				hr -= 12;
			break;

		    case "%M":
			//min = parseInt(a[i], 10);
			min = today.getMinutes() + 1;
			break;

			case "%s":
			case "%S":
				sec = today.getSeconds() + 1;
				break;
		}
	}
	if (isNaN(y)) y = today.getFullYear();
	if (isNaN(m)) m = today.getMonth();
	      if(m<10){
	          mm="0"+m;
	      }else{
	          mm=""+m;
	      }
	if (isNaN(d)) d = today.getDate();
	    if(d<10){
	        dd="0"+d;
	    }else{
	        dd=""+d;
	    }
	if (isNaN(hr)) hr = today.getHours();
	if (isNaN(min)) min = today.getMinutes();


	return fmt.replace(/%(y|Y)/, y + '').replace(/%m/, mm + '').replace(/%(d|D)/, dd + '').replace(/%(H|h)/,hr+'').replace(/%M/,min+'').replace(/%(S|s)/,sec+'');

}

var mydser = new myDateSelecter();

function showdate(input,format){
    mydser.show(input,format);
}