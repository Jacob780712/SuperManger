package cn.bc.business.dao;

import cn.bc.business.po.YhNoticeLogs;
import cn.bc.business.vo.YhNoticeLogsFilter;
import cn.bc.common.support.page.Page;

public interface YhNoticeLogsDao {

	public Page searchPage(YhNoticeLogsFilter filter)throws Exception;
	
	public void save(YhNoticeLogs yhmsg);
}
