package cn.bc.task.services.impl;

import java.util.Date;

import cn.bc.task.dao.MchantStmDao;
import cn.bc.task.services.MchantStmService;

public class MchantStmServiceImpl implements MchantStmService{
	MchantStmDao dao;
	
	public void setDao(MchantStmDao dao) {
		this.dao = dao;
	}

	@Override
	public void createMchantPayment(Date date) throws Exception{
		dao.createMchantPayment(date);
	}

	@Override
	public void createMchantStm(Date date) throws Exception{
		dao.createMchantStm(date);
	}

	@Override
	public void setStmWechat(Date date) throws Exception {
		dao.setStmWechat(date);
	}

}
