//ָ������������ڵ����ֵ
function showMaxControls(timeId) {
	WdatePicker({dateFmt:"yyyyMMdd", maxDate:"#F{$dp.$D('" + timeId + "',{d:0});}"});
}

//ָ������������ڵ���Сֵ
function showMinControls(timeId) {
	WdatePicker({dateFmt:"yyyyMMdd", minDate:"#F{$dp.$D('" + timeId + "',{d:0});}"});
}
//显示所有
function showAllControls(timeId) {
		WdatePicker({el:$dp.$(timeId),dateFmt:"yyyyMMdd"});
}
//显示当天以前的
function showTodayControls() {
	WdatePicker({dateFmt:"yyyy-MM-dd", maxDate:"%y-%M-#{%d}"});
}

//显示当天以后的
function showTodayAfterControlsNew() {
	WdatePicker({minDate:'%y-%M-#{%d+1}', dateFmt:'yyyy-MM-dd'});
}

//显示当天以后的
function showTodayAfterControls() {
	WdatePicker({minDate:'%y-%M-#{%d+1}',dateFmt:'yyyyMMdd'});
}
//显示当天以前的
function showMinTodayControls() {
	WdatePicker({dateFmt:"yyyyMMdd", maxDate:"%y-%M-#{%d}"});
}

//ͬʱָ�����ڸ�ʽ����ʾ����Сֵ
function showMinFmtControls(fmt, timeId) {
	WdatePicker({dateFmt:fmt, minDate:"#F{$dp.$D('" + timeId + "',{d:0});}"});
}
	 
//ͬʱָ�����ڸ�ʽ����ʾ�����ֵ
function showMaxFmtControls(fmt, timeId) {
	WdatePicker({dateFmt:fmt, maxDate:"#F{$dp.$D('" + timeId + "',{d:0});}"});
}
//通过按钮的点击控制时间输入框
function showFmtControlsOnBtnClick(timeId,fmt)
{
	WdatePicker({el:$dp.$(timeId),dateFmt:fmt});
}

//ָ����ʽ
function showFmtControls(fmt) {
	WdatePicker({dateFmt:fmt});
}

function showFmtControlsByFunName(fmt,fnName) {
	WdatePicker({dateFmt:fmt});
	
	if(fnName.trim()!="")
	{
			eval(fnName+"();");
	}
	
}


//֧������ʾ
function showWeekControls() {
	WdatePicker({isShowWeek:true});
}

//ָ����ʽͬʱ��ʾ��
function showWFControls(fmt) {
	WdatePicker({isShowWeek:true, dateFmt:fmt});
}

//������հ�ť
function showNCControls() {
	WdatePicker({isShowClear:false, readOnly:true});
}

//显示某一个区域的时间
function showAreaControls(min,max) {
	WdatePicker({minDate:min,maxDate:max, dateFmt:'yyyy-MM-dd'});
}