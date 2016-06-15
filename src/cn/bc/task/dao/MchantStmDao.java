package cn.bc.task.dao;

import java.util.Date;

import cn.bc.common.support.page.Page;
import cn.bc.settlement.vo.StmMchDetailFilter;

public interface MchantStmDao {
	//根据限额和周期生成商户结算表
	public void createMchantPayment(Date date) throws Exception;
	//生成每日结算信息
	public void createMchantStm(Date date) throws Exception;
	//生成微信结算数据
	public void setStmWechat(Date date) throws Exception;
	
}
