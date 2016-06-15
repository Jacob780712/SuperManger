package cn.bc.business.services.impl;

import java.util.ArrayList;
import java.util.List;

import cn.bc.business.dao.YhNoticeLogsDao;
import cn.bc.business.po.YhNoticeLogs;
import cn.bc.business.services.MessageLogsService;
import cn.bc.business.vo.MessageLogsInfo;
import cn.bc.business.vo.YhNoticeLogsFilter;
import cn.bc.common.service.BaseService;
import cn.bc.common.support.page.Page;
import cn.bc.common.util.MyBeanUtils;

public class MessageLogsServiceImpl extends BaseService implements MessageLogsService {

	private YhNoticeLogsDao yhNoticeLogsDao;
	
	@Override
	public Page searchPage(YhNoticeLogsFilter filter) throws Exception {
		return yhNoticeLogsDao.searchPage(filter);
	}

	public void setYhNoticeLogsDao(YhNoticeLogsDao yhNoticeLogsDao) {
		this.yhNoticeLogsDao = yhNoticeLogsDao;
	}

	@Override
	public void save(YhNoticeLogs yhmsg) {
		this.yhNoticeLogsDao.save(yhmsg);
	}

	
}
  