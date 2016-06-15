package cn.bc.business.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.bc.business.dao.YhNoticeLogsDao;
import cn.bc.business.po.YhNoticeLogs;
import cn.bc.business.vo.YhNoticeLogsFilter;
import cn.bc.common.dao.impl.BaseHibernateDao;
import cn.bc.common.support.page.Page;

public class YhNoticeLogsDaoImpl extends BaseHibernateDao<YhNoticeLogs> implements YhNoticeLogsDao{

	@Override
	public Page searchPage(YhNoticeLogsFilter filter) throws Exception {

		String hql = " from YhNoticeLogs y ";
		List listParamter = new ArrayList();
		String condtion = "";
		String orderStr = "";
		if (filter != null) {
			if (StringUtils.isNotBlank(filter.getToAddress())) {

				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " y.toAddress = ? ";

				listParamter.add(filter.getToAddress());

			}
			if (StringUtils.isNotBlank(filter.getNoticeType())) {

				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " y.noticeType = ? ";

				listParamter.add(filter.getNoticeType());
			}
			if (filter.getBeginDate() != null) {

				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " y.noticeDate >= ? ";
				listParamter.add(filter.getBeginDate());
			}
			if (filter.getEndDate() != null) {

				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " y.noticeDate <= ? ";
				listParamter.add(filter.getEndDate());
			}
		}
		
		if (condtion.length() > 0)
			hql = hql + " where " + condtion;

		orderStr = " order by y.id desc";

		if (orderStr.length() > 0)
			hql = hql + orderStr;

		return super.pagedQuery(hql.toString(), filter.getPageNo(), filter
				.getPageSize(), listParamter.toArray());

	}

	@Override
	public void save(YhNoticeLogs yhmsg) {
		super.save(yhmsg);
		
	}

}
