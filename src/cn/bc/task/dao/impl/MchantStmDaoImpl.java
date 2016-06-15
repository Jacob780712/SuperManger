package cn.bc.task.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import cn.bc.common.dao.impl.BaseHibernateDao;
import cn.bc.mchant.po.MchMerchant;
import cn.bc.mchant.po.MchStmConfig;
import cn.bc.settlement.po.StmMchDetail;
import cn.bc.settlement.po.StmMerchant;
import cn.bc.settlement.po.StmWechat;
import cn.bc.task.dao.MchantStmDao;
import cn.bc.util.LastDate;


public class MchantStmDaoImpl extends BaseHibernateDao implements MchantStmDao {
	
	//根据限额和周期生成商户结算表
	@Override
	public void createMchantPayment(Date date) throws Exception {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		Calendar calendar=Calendar.getInstance();  
		calendar.setTime(date);  
	    int day = calendar.get(Calendar.DAY_OF_MONTH);
		//前一天的日期
		String lastDate = df.format(new Date(date.getTime() - 1 * 24 * 60 * 60 * 1000));
		Date start_date;
		Date end_date = df.parse(lastDate);
		String batch_number = df.format(date);  //本次处理的批次号
		//找到截止目前未结算的商户售卡数据
		String hqlStmMchant = "select m.mch_number as 商户编号,sum(m.sale_card_number)as 售卡数量 ,sum(m.sale_card_amount) as 售卡金额,"+
							  " sum(m.sale_card_fee) as 结算手续费,sum(m.settlement_amount) as 结算金额 "+
							  " from stm_merchant m where m.status='1' and type='1' and m.batch_number is null  "+
							  " and m.date<='"+lastDate+"' GROUP BY m.mch_number";
		List listStmMchant = getSession().createSQLQuery(hqlStmMchant).list();		
		List StmMchNumber = listStmMchant;
		String hqlMchantNo = " select DISTINCT m.mch_number from mch_merchant m";
		List listMchantNo = getSession().createSQLQuery(hqlMchantNo).list();
		for(int i=0;i<listMchantNo.size();i++){
			String mchantNo = listMchantNo.get(i).toString().trim();
			boolean flag = true;
			for(int j=0;j<StmMchNumber.size();j++){
				Object[] ary = (Object[]) StmMchNumber.get(j);
				//商户编号
				String mchantNo2 = ary[0].toString().trim();
				if(mchantNo.equals(mchantNo2)){
					flag = false;
					break;
				}
			}
			if(flag){
				listStmMchant.add(new Object[]{listMchantNo.get(i),0,0,0});
			}
		}

		if(listStmMchant.size()>0){
			//本次结算周期内有购卡
			for(int i=0;i<listStmMchant.size();i++){
				Object[] ary = (Object[]) listStmMchant.get(i);
				//商户编号
				String mchantNo = ary[0].toString().trim();
				//商户信息
				String hql = "from MchMerchant m where mch_number='"+mchantNo+"'";
				List<MchMerchant> MchMerchantList = super.find(hql);
				MchMerchant mchant = MchMerchantList.get(0);
				//商户结算配置
				String hqlStmConfig = " from MchStmConfig m where m.mch_number='"+mchantNo+"'";
				List<MchStmConfig> listStmConfig = super.find(hqlStmConfig);
				if(listStmConfig==null||listStmConfig.size()<=0){
					continue;
				}
				MchStmConfig config = listStmConfig.get(0);
				String mch_setment_type = config.getMch_setment_type();//结算类型：1全额  2非全额
				int mch_settlement_period = config.getMch_settlement_period();//结算周期
				Long mch_advance_quota = Long.valueOf(config.getMch_advance_quota().trim());//垫款限额，单位分
				List<Integer> listDate = new ArrayList<Integer>();
				if(mch_settlement_period==1){
					listDate.add(1);
				}
				if(mch_settlement_period==2){
					listDate.add(1);
					listDate.add(16);
				}
				if(mch_settlement_period==3){
					listDate.add(1);
					listDate.add(11);
					listDate.add(21);
				}
				
				//查询上期结算信息
				String hhql = " from StmMchDetail s where s.mch_number='"+mchantNo+"' order by s.create_date desc";
				List<StmMchDetail> listStmMchDetail = super.find(hhql);
				//有上期结算信息，本次结算为周期型结算或者提前结算
				StmMchDetail StmMchDetailLast = null;
				if(listStmMchDetail!=null&&listStmMchDetail.size()>0){
					StmMchDetailLast = listStmMchDetail.get(0);		
					int c = StmMchDetailLast.getCur_prepay();
					System.out.println("上期预付金额："+c+"分");
					if(c<0){
						StmMchDetailLast.setCur_prepay(0);//取绝对值，因为有可能是负数
					}
					
				}else{
					StmMchDetailLast = new StmMchDetail();
					StmMchDetailLast.setType("0");
					StmMchDetailLast.setEnd_date(df.parse(df.format(mchant.getCreate_date())));//默认上次结算结束日期为商户创建日期
					StmMchDetailLast.setCur_prepay(0);//上期预付0
					StmMchDetailLast.setCreate_date(mchant.getCreate_date());//默认上次结算日期为商户创建日期
					StmMchDetailLast.setAll_dps_balance(0);//上期储值总额0
				}
				StmMchDetail StmMchDetail = new StmMchDetail();
				if("0".equals(StmMchDetailLast.getType())){
					start_date = new Date(StmMchDetailLast.getEnd_date().getTime());//首次购卡结算后，当日的其他购卡和消费参与其他结算
				}else{
					//上次结算非首次购卡结算，则本次结算的开始日期为上次结算的后一天
					start_date = new Date(StmMchDetailLast.getEnd_date().getTime() + 1 * 24 * 60 * 60 * 1000);
				}
				StmMchDetail.setMch_number(mchantNo);
				//本期消费
				String cur_hql = "select sum(o.actual_amount) from on_svc_spend_bill o "+
						         " where o.status='1' and (o.type='0' or o.type='1') and o.create_date>='"+df.format(start_date)+" 00:00:00'"+
						         " and o.create_date<='"+df.format(end_date)+" 23:59:59'"+
						         " and (o.svc_id in (select id from on_deposit_card d where d.mch_number='"+mchantNo+"')"
						         + " or o.svc_id in (select id from co_deposit_card d where d.mch_number='"+mchantNo+"'))";
				List listActual = getSession().createSQLQuery(cur_hql).list();	
				
				java.math.BigDecimal ary2 = (java.math.BigDecimal) listActual.get(0);
				//本期消费金额
				int cur_amount = 0;
				if(ary2!=null){
					cur_amount = Integer.valueOf(ary2.toString());
				}
				if(cur_amount-StmMchDetailLast.getCur_prepay()+StmMchDetailLast.getCur_arrears()>=mch_advance_quota&&!listDate.contains(day)){
					//提前结算
					int cur_dps_balance = Integer.valueOf(ary[2].toString());//本期储值总额
					Date lastStmDate = df.parse(df.format(StmMchDetailLast.getCreate_date()));//上期结算日
					StmMchDetail.setType("2");//提前结算
					StmMchDetail.setStart_date(start_date);//开始日期
					StmMchDetail.setEnd_date(end_date);//结束日期
					StmMchDetail.setUpper_dps_balance(StmMchDetailLast.getAll_dps_balance());//上期储值余额=上期结算时的储值总余额
					StmMchDetail.setCur_add_dps_amout(cur_dps_balance);//本期新增储值
					StmMchDetail.setUpper_dps_date(lastStmDate);//上期结算日
					StmMchDetail.setUpper_prepay(StmMchDetailLast.getCur_prepay());//上期预付
					StmMchDetail.setCur_consume(cur_amount);//本期消费
					int cur_arrears = cur_amount-StmMchDetailLast.getCur_prepay()+StmMchDetailLast.getCur_arrears();//本期商户垫款（商户本期消费-上期预付）
					StmMchDetail.setCur_arrears(cur_arrears);//本期商户垫款（商户本期消费-上期预付）
					int last_to_this_day = (int) ((df.parse(df.format(date)).getTime()-
							df.parse(df.format(StmMchDetailLast.getCreate_date())).getTime())/(1000*60*60*24));
					StmMchDetail.setLast_to_this_day(last_to_this_day+"");//距上个结算日（天）
					//计算距离下个结算日多少天
					int this_to_next_day = LastDate.lastDate(date, listDate);
					StmMchDetail.setThis_to_next_day(this_to_next_day+"");//距离下个结算日多少天
					int cur_prepay = cur_arrears+(int)((cur_amount)*((double)this_to_next_day/(double)last_to_this_day));//本期实际预付(付款）
					if(cur_prepay>cur_dps_balance+StmMchDetailLast.getAll_dps_balance()){
						cur_prepay = cur_dps_balance+StmMchDetailLast.getAll_dps_balance();//结算金额大于本期储值金额，则结算本期储值金额
					}
					StmMchDetail.setCur_prepay(cur_prepay);//本期实际预付(付款）
					StmMchDetail.setAll_dps_balance(StmMchDetailLast.getAll_dps_balance()+cur_dps_balance-cur_prepay);//未结算储值余额（总数）
					Double cur_fee = Double.valueOf(mchant.getSale_poundage())*cur_prepay;
					int cur_fee_int = cur_fee.intValue();
					StmMchDetail.setCur_fee(cur_fee_int);//本期结算手续费
					StmMchDetail.setCur_prepay_net(cur_prepay-cur_fee_int);//本期预付净额
					StmMchDetail.setStatus("0");//结算状态0未结算 1已结算
					StmMchDetail.setCreate_date(date);
					StmMchDetail.setCreate_person("system");
					//生成结算数据
					super.save(StmMchDetail);
					//修改StmMerchant表
					String updateSql = " update StmMerchant s set s.batch_number='"+batch_number+
									   "',s.status='0' where s.mch_number='"+mchantNo+"' and s.batch_number is null";
					Query query = getSession().createQuery(updateSql);
					query.executeUpdate();
					
				}else{
					//按周期结算
					if(mch_settlement_period!=4&&mch_settlement_period!=5){
						for(int k=0;k<listDate.size();k++){
							int cycleDate = listDate.get(k);
							if(cycleDate==day){
								//本次跑批日期是结算周期中的日期
								if("2".equals(mch_setment_type)){
									//非全额结算
									StmMchDetail.setType("1");//结算类型 0 商户开通首次购卡 1按周期结算 2提前结算 3全额结算
									StmMchDetail.setStart_date(start_date);//开始日期
									StmMchDetail.setEnd_date(end_date);//结算日期
									StmMchDetail.setUpper_dps_balance(StmMchDetailLast.getAll_dps_balance());//上期储值余额=上期结算时未结算的储值总余额
									int cur_dps_balance = Integer.valueOf(ary[2].toString());//本期储值总额
									StmMchDetail.setCur_add_dps_amout(cur_dps_balance);//本期新增储值
									Date lastStmDate = df.parse(df.format(StmMchDetailLast.getCreate_date()));//上期结算日(上期的结算日期）
									StmMchDetail.setUpper_dps_date(lastStmDate);//上期结算日(上期的结算日期)
									StmMchDetail.setUpper_prepay(StmMchDetailLast.getCur_prepay());//上期预付
									StmMchDetail.setCur_consume(cur_amount);//本期消费
									int cur_arrears = cur_amount-StmMchDetailLast.getCur_prepay()+StmMchDetailLast.getCur_arrears();//本期商户垫款（商户本期消费-上期预付）
									StmMchDetail.setCur_arrears(cur_arrears);//本期商户垫款（商户本期消费-上期预付）
									int last_to_this_day = (int) ((df.parse(df.format(date)).getTime()-
											df.parse(df.format(StmMchDetailLast.getCreate_date())).getTime())/(1000*60*60*24));
									StmMchDetail.setLast_to_this_day(last_to_this_day+"");//距上个结算日（天）
									int this_to_next_day = LastDate.lastDate(date, listDate);
									StmMchDetail.setThis_to_next_day(this_to_next_day+"");//距离下个结算日多少天
									int cur_prepay = cur_arrears+cur_amount;//本期实际预付(付款）
									if("2".equals(StmMchDetailLast.getType())){
										cur_prepay=0;//本期实际预付(付款），根据往前的结算数据重新计算
										//上次为提前结算，查找这个周期内有几个提前结算
										for(int m=0;m<listStmMchDetail.size();m++){
											if("2".equals(listStmMchDetail.get(m).getType())){
												//找到往前的最近一个周期结算
												StmMchDetail StmMchDetailLast2 = listStmMchDetail.get(m);
												cur_prepay = cur_prepay+StmMchDetailLast2.getCur_consume();//上个周期到本次周期的消费金额
											}else{
												break;
											}
										}
										//本次结算的商户垫款+上个周期结算到现在的提前计算的消费金额+本期消费金额
										cur_prepay = cur_arrears+cur_prepay+cur_amount;
									}
									
									if(cur_prepay>cur_dps_balance+StmMchDetailLast.getAll_dps_balance()){
										cur_prepay = cur_dps_balance+StmMchDetailLast.getAll_dps_balance();//结算金额大于未结算储值总金额，则结算本期储值金额
									}
									if(cur_prepay<0){
										cur_prepay=0;//预付金额如果小于0，则为0
									}
									StmMchDetail.setCur_prepay(cur_prepay);//本期实际预付
									StmMchDetail.setAll_dps_balance(StmMchDetailLast.getAll_dps_balance()+cur_dps_balance-cur_prepay);//未结算储值余额（总数）
									Double cur_fee = Double.valueOf(mchant.getSale_poundage())*cur_prepay;
									int cur_fee_int = cur_fee.intValue();
									StmMchDetail.setCur_fee(cur_fee_int);//本期结算手续费
									StmMchDetail.setCur_prepay_net(cur_prepay-cur_fee_int);//本期预付净额
									StmMchDetail.setStatus("0");//结算状态0未结算 1已结算
									StmMchDetail.setCreate_date(date);
									StmMchDetail.setCreate_person("system");
									//生成结算数据
									super.save(StmMchDetail);
									//修改StmMerchant表
									String updateSql = " update StmMerchant s set s.batch_number='"+batch_number+
													   "',s.status='0' where s.mch_number='"+mchantNo+"' and s.batch_number is null";
									Query query = getSession().createQuery(updateSql);
									query.executeUpdate();
								}else{
									//全额结算
									StmMchDetail.setType("1");//结算类型 0 商户开通首次购卡 1按周期结算 2提前结算 
									StmMchDetail.setSet_type("1");//1为全额结算，其他为非全额结算
									StmMchDetail.setStart_date(start_date);//开始日期
									StmMchDetail.setEnd_date(end_date);//结算日期
									StmMchDetail.setUpper_dps_balance(StmMchDetailLast.getAll_dps_balance());//上期储值余额=上期结算时的储值总余额
									int cur_dps_balance = Integer.valueOf(ary[2].toString());//本期储值总额
									StmMchDetail.setCur_add_dps_amout(cur_dps_balance);//本期新增储值
									Date lastStmDate = df.parse(df.format(StmMchDetailLast.getCreate_date()));//上期结算日(上期的结算日期）
									StmMchDetail.setUpper_dps_date(lastStmDate);//上期结算日(上期的结算日期)
									StmMchDetail.setUpper_prepay(StmMchDetailLast.getCur_prepay());//上期预付
									StmMchDetail.setCur_consume(cur_amount);//本期消费
//									int cur_arrears = 0;//本期商户垫款
//									int upper_net = 0;//往期商户垫款
//									if(StmMchDetailLast.getCur_prepay()>0){
//										cur_arrears = cur_amount-StmMchDetailLast.getCur_prepay();//本期商户垫款（商户本期消费-上期预付）
//									}else{
//											//上期预付0元，则要把预付0元的所有结算周期内的商户垫款统计出来+本期消费=本期预付
//										for(int jk=0;jk<listStmMchDetail.size();jk++){
//											if(listStmMchDetail.get(jk).getCur_prepay()==0){
//												upper_net = upper_net + listStmMchDetail.get(jk).getCur_arrears();
//											}
//											if(listStmMchDetail.get(jk).getCur_prepay()>0){
//												break;
//											}
//										}
//										cur_arrears = cur_amount+upper_net;//本期商户垫款（本期消费+往期商户垫款（负数））
//									}
									int cur_arrears = cur_amount-StmMchDetailLast.getCur_prepay()+StmMchDetailLast.getCur_arrears();//本期商户垫款（商户本期消费-上期预付）
									StmMchDetail.setCur_arrears(cur_arrears);//本期商户垫款（商户本期消费-上期预付）
									int last_to_this_day = (int) ((df.parse(df.format(date)).getTime()-
											df.parse(df.format(StmMchDetailLast.getCreate_date())).getTime())/(1000*60*60*24));
									StmMchDetail.setLast_to_this_day(last_to_this_day+"");//距上个结算日（天）
									int this_to_next_day = LastDate.lastDate(date, listDate);
									StmMchDetail.setThis_to_next_day(this_to_next_day+"");//距离下个结算日多少天
									int cur_prepay = cur_arrears+cur_amount;//本期实际预付(付款）
									cur_prepay = cur_dps_balance+StmMchDetailLast.getAll_dps_balance();//全额结算，结算本期储值金额
									StmMchDetail.setAll_dps_balance(StmMchDetailLast.getAll_dps_balance()+cur_dps_balance-cur_prepay);//未结算储值余额（总数）
									Double cur_fee = Double.valueOf(mchant.getSale_poundage())*cur_prepay;
									int cur_fee_int = cur_fee.intValue();
									StmMchDetail.setCur_prepay(cur_prepay);//本期实际预付
									StmMchDetail.setCur_fee(cur_fee_int);//本期结算手续费
									StmMchDetail.setCur_prepay_net(cur_prepay-cur_fee_int);//本期预付净额
									StmMchDetail.setStatus("0");//结算状态0未结算 1已结算
									StmMchDetail.setCreate_date(date);
									StmMchDetail.setCreate_person("system");
									//生成结算数据
									super.save(StmMchDetail);
									//修改StmMerchant表
									String updateSql = " update StmMerchant s set s.batch_number='"+batch_number+
													   "',s.status='0' where s.mch_number='"+mchantNo+"' and s.batch_number is null";
									Query query = getSession().createQuery(updateSql);
									query.executeUpdate();
								}
							}
						}
					}
					if(mch_settlement_period==4){
						int dayOfWeek =calendar.get(Calendar.DAY_OF_WEEK)-1;
						if(dayOfWeek==2){
							//本次跑批日期是结算周期中的日期
							if("2".equals(mch_setment_type)){
								//非全额结算
								StmMchDetail.setType("1");//结算类型 0 商户开通首次购卡 1按周期结算 2提前结算 3全额结算
								StmMchDetail.setStart_date(start_date);//开始日期
								StmMchDetail.setEnd_date(end_date);//结算日期
								StmMchDetail.setUpper_dps_balance(StmMchDetailLast.getAll_dps_balance());//上期储值余额=上期结算时未结算的储值总余额
								int cur_dps_balance = Integer.valueOf(ary[2].toString());//本期储值总额
								StmMchDetail.setCur_add_dps_amout(cur_dps_balance);//本期新增储值
								Date lastStmDate = df.parse(df.format(StmMchDetailLast.getCreate_date()));//上期结算日(上期的结算日期）
								StmMchDetail.setUpper_dps_date(lastStmDate);//上期结算日(上期的结算日期)
								StmMchDetail.setUpper_prepay(StmMchDetailLast.getCur_prepay());//上期预付
								StmMchDetail.setCur_consume(cur_amount);//本期消费
								int cur_arrears = cur_amount-StmMchDetailLast.getCur_prepay()+StmMchDetailLast.getCur_arrears();//本期商户垫款（商户本期消费-上期预付）
								StmMchDetail.setCur_arrears(cur_arrears);//本期商户垫款（商户本期消费-上期预付）
								int last_to_this_day = (int) ((df.parse(df.format(date)).getTime()-
										df.parse(df.format(StmMchDetailLast.getCreate_date())).getTime())/(1000*60*60*24));
								StmMchDetail.setLast_to_this_day(last_to_this_day+"");//距上个结算日（天）
								int this_to_next_day = 7;
								StmMchDetail.setThis_to_next_day(this_to_next_day+"");//距离下个结算日多少天
								int cur_prepay = cur_arrears+cur_amount;//本期实际预付(付款）
								if("2".equals(StmMchDetailLast.getType())){
									cur_prepay=0;//本期实际预付(付款），根据往前的结算数据重新计算
									//上次为提前结算，查找这个周期内有几个提前结算
									for(int m=0;m<listStmMchDetail.size();m++){
										if("2".equals(listStmMchDetail.get(m).getType())){
											//找到往前的最近一个周期结算
											StmMchDetail StmMchDetailLast2 = listStmMchDetail.get(m);
											cur_prepay = cur_prepay+StmMchDetailLast2.getCur_consume();//上个周期到本次周期的消费金额
										}else{
											break;
										}
									}
									//本次结算的商户垫款+上个周期结算到现在的提前计算的消费金额+本期消费金额
									cur_prepay = cur_arrears+cur_prepay+cur_amount;
								}
								
								if(cur_prepay>cur_dps_balance+StmMchDetailLast.getAll_dps_balance()){
									cur_prepay = cur_dps_balance+StmMchDetailLast.getAll_dps_balance();//结算金额大于未结算储值总金额，则结算本期储值金额
								}
								if(cur_prepay<0){
									cur_prepay=0;//预付金额如果小于0，则为0
								}
								StmMchDetail.setCur_prepay(cur_prepay);//本期实际预付
								StmMchDetail.setAll_dps_balance(StmMchDetailLast.getAll_dps_balance()+cur_dps_balance-cur_prepay);//未结算储值余额（总数）
								Double cur_fee = Double.valueOf(mchant.getSale_poundage())*cur_prepay;
								int cur_fee_int = cur_fee.intValue();
								StmMchDetail.setCur_fee(cur_fee_int);//本期结算手续费
								StmMchDetail.setCur_prepay_net(cur_prepay-cur_fee_int);//本期预付净额
								StmMchDetail.setStatus("0");//结算状态0未结算 1已结算
								StmMchDetail.setCreate_date(date);
								StmMchDetail.setCreate_person("system");
								//生成结算数据
								super.save(StmMchDetail);
								//修改StmMerchant表
								String updateSql = " update StmMerchant s set s.batch_number='"+batch_number+
												   "',s.status='0' where s.mch_number='"+mchantNo+"' and s.batch_number is null";
								Query query = getSession().createQuery(updateSql);
								query.executeUpdate();
							}else{
								//全额结算
								StmMchDetail.setType("1");//结算类型 0 商户开通首次购卡 1按周期结算 2提前结算 
								StmMchDetail.setSet_type("1");//1为全额结算，其他为非全额结算
								StmMchDetail.setStart_date(start_date);//开始日期
								StmMchDetail.setEnd_date(end_date);//结算日期
								StmMchDetail.setUpper_dps_balance(StmMchDetailLast.getAll_dps_balance());//上期储值余额=上期结算时的储值总余额
								int cur_dps_balance = Integer.valueOf(ary[2].toString());//本期储值总额
								StmMchDetail.setCur_add_dps_amout(cur_dps_balance);//本期新增储值
								Date lastStmDate = df.parse(df.format(StmMchDetailLast.getCreate_date()));//上期结算日(上期的结算日期）
								StmMchDetail.setUpper_dps_date(lastStmDate);//上期结算日(上期的结算日期)
								StmMchDetail.setUpper_prepay(StmMchDetailLast.getCur_prepay());//上期预付
								StmMchDetail.setCur_consume(cur_amount);//本期消费
//								int cur_arrears = 0;//本期商户垫款
//								int upper_net = 0;//往期商户垫款
//								if(StmMchDetailLast.getCur_prepay()>0){
//									cur_arrears = cur_amount-StmMchDetailLast.getCur_prepay();//本期商户垫款（商户本期消费-上期预付）
//								}else{
//										//上期预付0元，则要把预付0元的所有结算周期内的商户垫款统计出来+本期消费=本期预付
//									for(int jk=0;jk<listStmMchDetail.size();jk++){
//										if(listStmMchDetail.get(jk).getCur_prepay()==0){
//											upper_net = upper_net + listStmMchDetail.get(jk).getCur_arrears();
//										}
//										if(listStmMchDetail.get(jk).getCur_prepay()>0){
//											break;
//										}
//									}
//									cur_arrears = cur_amount+upper_net;//本期商户垫款（本期消费+往期商户垫款（负数））
//								}
								int cur_arrears = cur_amount-StmMchDetailLast.getCur_prepay()+StmMchDetailLast.getCur_arrears();//本期商户垫款（商户本期消费-上期预付）
								StmMchDetail.setCur_arrears(cur_arrears);//本期商户垫款（商户本期消费-上期预付）
								int last_to_this_day = (int) ((df.parse(df.format(date)).getTime()-
										df.parse(df.format(StmMchDetailLast.getCreate_date())).getTime())/(1000*60*60*24));
								StmMchDetail.setLast_to_this_day(last_to_this_day+"");//距上个结算日（天）
								int this_to_next_day = 7;
								StmMchDetail.setThis_to_next_day(this_to_next_day+"");//距离下个结算日多少天
								int cur_prepay = cur_arrears+cur_amount;//本期实际预付(付款）
								cur_prepay = cur_dps_balance+StmMchDetailLast.getAll_dps_balance();//全额结算，结算本期储值金额
								StmMchDetail.setAll_dps_balance(StmMchDetailLast.getAll_dps_balance()+cur_dps_balance-cur_prepay);//未结算储值余额（总数）
								Double cur_fee = Double.valueOf(mchant.getSale_poundage())*cur_prepay;
								int cur_fee_int = cur_fee.intValue();
								StmMchDetail.setCur_prepay(cur_prepay);//本期实际预付
								StmMchDetail.setCur_fee(cur_fee_int);//本期结算手续费
								StmMchDetail.setCur_prepay_net(cur_prepay-cur_fee_int);//本期预付净额
								StmMchDetail.setStatus("0");//结算状态0未结算 1已结算
								StmMchDetail.setCreate_date(date);
								StmMchDetail.setCreate_person("system");
								//生成结算数据
								super.save(StmMchDetail);
								//修改StmMerchant表
								String updateSql = " update StmMerchant s set s.batch_number='"+batch_number+
												   "',s.status='0' where s.mch_number='"+mchantNo+"' and s.batch_number is null";
								Query query = getSession().createQuery(updateSql);
								query.executeUpdate();
							}
						}
					}
					if(mch_settlement_period==5){
						int dayOfWeek =calendar.get(Calendar.DAY_OF_WEEK)-1;
						if(dayOfWeek==2||dayOfWeek==5){
							//本次跑批日期是结算周期中的日期
							if("2".equals(mch_setment_type)){
								//非全额结算
								StmMchDetail.setType("1");//结算类型 0 商户开通首次购卡 1按周期结算 2提前结算 3全额结算
								StmMchDetail.setStart_date(start_date);//开始日期
								StmMchDetail.setEnd_date(end_date);//结算日期
								StmMchDetail.setUpper_dps_balance(StmMchDetailLast.getAll_dps_balance());//上期储值余额=上期结算时未结算的储值总余额
								int cur_dps_balance = Integer.valueOf(ary[2].toString());//本期储值总额
								StmMchDetail.setCur_add_dps_amout(cur_dps_balance);//本期新增储值
								Date lastStmDate = df.parse(df.format(StmMchDetailLast.getCreate_date()));//上期结算日(上期的结算日期）
								StmMchDetail.setUpper_dps_date(lastStmDate);//上期结算日(上期的结算日期)
								StmMchDetail.setUpper_prepay(StmMchDetailLast.getCur_prepay());//上期预付
								StmMchDetail.setCur_consume(cur_amount);//本期消费
								int cur_arrears = cur_amount-StmMchDetailLast.getCur_prepay()+StmMchDetailLast.getCur_arrears();//本期商户垫款（商户本期消费-上期预付）
								StmMchDetail.setCur_arrears(cur_arrears);//本期商户垫款（商户本期消费-上期预付）
								int last_to_this_day = (int) ((df.parse(df.format(date)).getTime()-
										df.parse(df.format(StmMchDetailLast.getCreate_date())).getTime())/(1000*60*60*24));
								StmMchDetail.setLast_to_this_day(last_to_this_day+"");//距上个结算日（天）
								int this_to_next_day = 7;
								StmMchDetail.setThis_to_next_day(this_to_next_day+"");//距离下个结算日多少天
								int cur_prepay = cur_arrears+cur_amount;//本期实际预付(付款）
								if("2".equals(StmMchDetailLast.getType())){
									cur_prepay=0;//本期实际预付(付款），根据往前的结算数据重新计算
									//上次为提前结算，查找这个周期内有几个提前结算
									for(int m=0;m<listStmMchDetail.size();m++){
										if("2".equals(listStmMchDetail.get(m).getType())){
											//找到往前的最近一个周期结算
											StmMchDetail StmMchDetailLast2 = listStmMchDetail.get(m);
											cur_prepay = cur_prepay+StmMchDetailLast2.getCur_consume();//上个周期到本次周期的消费金额
										}else{
											break;
										}
									}
									//本次结算的商户垫款+上个周期结算到现在的提前计算的消费金额+本期消费金额
									cur_prepay = cur_arrears+cur_prepay+cur_amount;
								}
								
								if(cur_prepay>cur_dps_balance+StmMchDetailLast.getAll_dps_balance()){
									cur_prepay = cur_dps_balance+StmMchDetailLast.getAll_dps_balance();//结算金额大于未结算储值总金额，则结算本期储值金额
								}
								if(cur_prepay<0){
									cur_prepay=0;//预付金额如果小于0，则为0
								}
								StmMchDetail.setCur_prepay(cur_prepay);//本期实际预付
								StmMchDetail.setAll_dps_balance(StmMchDetailLast.getAll_dps_balance()+cur_dps_balance-cur_prepay);//未结算储值余额（总数）
								Double cur_fee = Double.valueOf(mchant.getSale_poundage())*cur_prepay;
								int cur_fee_int = cur_fee.intValue();
								StmMchDetail.setCur_fee(cur_fee_int);//本期结算手续费
								StmMchDetail.setCur_prepay_net(cur_prepay-cur_fee_int);//本期预付净额
								StmMchDetail.setStatus("0");//结算状态0未结算 1已结算
								StmMchDetail.setCreate_date(date);
								StmMchDetail.setCreate_person("system");
								//生成结算数据
								super.save(StmMchDetail);
								//修改StmMerchant表
								String updateSql = " update StmMerchant s set s.batch_number='"+batch_number+
												   "',s.status='0' where s.mch_number='"+mchantNo+"' and s.batch_number is null";
								Query query = getSession().createQuery(updateSql);
								query.executeUpdate();
							}else{
								//全额结算
								StmMchDetail.setType("1");//结算类型 0 商户开通首次购卡 1按周期结算 2提前结算 
								StmMchDetail.setSet_type("1");//1为全额结算，其他为非全额结算
								StmMchDetail.setStart_date(start_date);//开始日期
								StmMchDetail.setEnd_date(end_date);//结算日期
								StmMchDetail.setUpper_dps_balance(StmMchDetailLast.getAll_dps_balance());//上期储值余额=上期结算时的储值总余额
								int cur_dps_balance = Integer.valueOf(ary[2].toString());//本期储值总额
								StmMchDetail.setCur_add_dps_amout(cur_dps_balance);//本期新增储值
								Date lastStmDate = df.parse(df.format(StmMchDetailLast.getCreate_date()));//上期结算日(上期的结算日期）
								StmMchDetail.setUpper_dps_date(lastStmDate);//上期结算日(上期的结算日期)
								StmMchDetail.setUpper_prepay(StmMchDetailLast.getCur_prepay());//上期预付
								StmMchDetail.setCur_consume(cur_amount);//本期消费
//								int cur_arrears = 0;//本期商户垫款
//								int upper_net = 0;//往期商户垫款
//								if(StmMchDetailLast.getCur_prepay()>0){
//									cur_arrears = cur_amount-StmMchDetailLast.getCur_prepay();//本期商户垫款（商户本期消费-上期预付）
//								}else{
//										//上期预付0元，则要把预付0元的所有结算周期内的商户垫款统计出来+本期消费=本期预付
//									for(int jk=0;jk<listStmMchDetail.size();jk++){
//										if(listStmMchDetail.get(jk).getCur_prepay()==0){
//											upper_net = upper_net + listStmMchDetail.get(jk).getCur_arrears();
//										}
//										if(listStmMchDetail.get(jk).getCur_prepay()>0){
//											break;
//										}
//									}
//									cur_arrears = cur_amount+upper_net;//本期商户垫款（本期消费+往期商户垫款（负数））
//								}
								int cur_arrears = cur_amount-StmMchDetailLast.getCur_prepay()+StmMchDetailLast.getCur_arrears();//本期商户垫款（商户本期消费-上期预付）
								StmMchDetail.setCur_arrears(cur_arrears);//本期商户垫款（商户本期消费-上期预付）
								int last_to_this_day = (int) ((df.parse(df.format(date)).getTime()-
										df.parse(df.format(StmMchDetailLast.getCreate_date())).getTime())/(1000*60*60*24));
								StmMchDetail.setLast_to_this_day(last_to_this_day+"");//距上个结算日（天）
								int this_to_next_day = 7;
								StmMchDetail.setThis_to_next_day(this_to_next_day+"");//距离下个结算日多少天
								int cur_prepay = cur_arrears+cur_amount;//本期实际预付(付款）
								cur_prepay = cur_dps_balance+StmMchDetailLast.getAll_dps_balance();//全额结算，结算本期储值金额
								StmMchDetail.setAll_dps_balance(StmMchDetailLast.getAll_dps_balance()+cur_dps_balance-cur_prepay);//未结算储值余额（总数）
								Double cur_fee = Double.valueOf(mchant.getSale_poundage())*cur_prepay;
								int cur_fee_int = cur_fee.intValue();
								StmMchDetail.setCur_prepay(cur_prepay);//本期实际预付
								StmMchDetail.setCur_fee(cur_fee_int);//本期结算手续费
								StmMchDetail.setCur_prepay_net(cur_prepay-cur_fee_int);//本期预付净额
								StmMchDetail.setStatus("0");//结算状态0未结算 1已结算
								StmMchDetail.setCreate_date(date);
								StmMchDetail.setCreate_person("system");
								//生成结算数据
								super.save(StmMchDetail);
								//修改StmMerchant表
								String updateSql = " update StmMerchant s set s.batch_number='"+batch_number+
												   "',s.status='0' where s.mch_number='"+mchantNo+"' and s.batch_number is null";
								Query query = getSession().createQuery(updateSql);
								query.executeUpdate();
							}
						}
					}
				}
			}
		}
	}
	
	//生成每日购卡信息
	@Override
	public void createMchantStm(Date date) throws Exception {
	    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
	    //前一天的日期
	    String lastDate = df.format(new Date(date.getTime() - 1 * 24 * 60 * 60 * 1000));
	    Date DateLast = null;
	    DateLast = df.parse(lastDate);
		String hql = "select o.mch_number as mch_number,o.mch_name as mch_name,"
					+ "sum(o.purchase_number) as sale_card_number,"
					+ "sum(o.purchase_amount) as sale_card_amount "
					+ " from order_pay_card o where o.status='1' and left(o.create_date,10)='"+lastDate+"'"+
					 "group by o.mch_number";
		List<Object> list = getSession().createSQLQuery(hql).list();
		//每日购卡数据
		
		for(int i=0;i<list.size();i++){
			Object[] ary = (Object[]) list.get(i);
			StmMerchant stm = new StmMerchant();
			stm.setDate(DateLast);
			stm.setMch_number((String) ary[0]);
			stm.setMch_name((String) ary[1]);
			stm.setSale_card_number(((Double) ary[2]).intValue()+"");
			stm.setSale_card_amount(Integer.valueOf(ary[3].toString()));
			String checkSql = " from StmMerchant where mch_number='"+stm.getMch_number()+"' and type='0' and "+
							  " date='"+lastDate+"'";
			List<StmMerchant> ls = super.find(checkSql);//商户开通的购卡统计
			if(ls!=null&&ls.size()>0){
				stm.setSale_card_number(Integer.valueOf(stm.getSale_card_number())-
						Integer.valueOf(ls.get(0).getSale_card_number())+"");
				stm.setSale_card_amount(stm.getSale_card_amount()-ls.get(0).getSale_card_amount());
				stm.setSale_card_fee(stm.getSale_card_fee()-ls.get(0).getSale_card_fee());
				stm.setSettlement_amount(stm.getSettlement_amount()-ls.get(0).getSettlement_amount());
			}
			String sql = " from MchMerchant m where m.mch_number='"+stm.getMch_number().trim()+"'";
			List<MchMerchant> mchantList = super.find(sql);
			MchMerchant mchant = null;
			if(mchantList!=null&&mchantList.size()>0){
				mchant = mchantList.get(0);
				String sale_poundage = mchant.getSale_poundage();
				Double salePoundage = Double.valueOf(sale_poundage);
				int sale_card_fee = (int) (stm.getSale_card_amount()*salePoundage);
				stm.setSale_card_fee(sale_card_fee);
				int settlement_amount = stm.getSale_card_amount() - sale_card_fee;
				stm.setSettlement_amount(settlement_amount);
				stm.setCreate_date(date);
				stm.setCreate_person("system");
				stm.setStatus("1");
				stm.setType("1");
				//查看该结算是否发生过
				super.save(stm);
			}else{
				logger.error("每日商户结算报错，由于没有找到商户的结算手续费，时间"+date);
			}
		}
	}

	@Override
	public void setStmWechat(Date date) throws Exception {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
	 	    //前一天的日期
	 	String lastDate = df.format(new Date(date.getTime() - 1 * 24 * 60 * 60 * 1000));
		String hql = "select smt.date,smt.cc,smt.aa "
				+ " from(select alls.date as date , sum(count) as cc ,sum(alls.amount) as aa from " +
					" (select left(o.create_date,10) date,sum(o.pay_amount) amount,count(*) count from order_pay_card o "+
					" where o.status='1' and o.pay_source='1' group by left(o.create_date,10) UNION "+
					" select left(b.create_date,10) date,sum(b.pay_amount) amount,count(*) count from order_pay_bill b "+
					" where b.status='1' and b.pay_amount>0 and b.pay_source='1' group by left(b.create_date,10) "+
					" ) alls  group by alls.date order by alls.date desc) smt "
					+ " where smt.date='"+lastDate+"'";
		List list = getSession().createSQLQuery(hql).list();
		for(int i=0;i<list.size();i++){
			Object[] ary = (Object[]) list.get(i);
			StmWechat sw = new StmWechat();
			sw.setDate(df.parse(ary[0].toString()));
			sw.setBusiness_pen(Integer.valueOf(ary[1].toString())+"");
			int amount = Integer.valueOf(ary[2].toString());
			sw.setBusiness_amount(amount);
			int fee = ((Double)(amount*0.006)).intValue();
			sw.setBusiness_fee(fee);
			int settlement_amount = amount-fee;
			sw.setSettlement_amount(settlement_amount);
			sw.setStatus("1");
			sw.setCreate_date(date);
			sw.setCreate_person("system");
			super.save(sw);
		}
	}
}
