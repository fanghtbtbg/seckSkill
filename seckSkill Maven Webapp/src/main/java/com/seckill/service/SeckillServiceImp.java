package com.seckill.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
@Service
public class SeckillServiceImp implements SeckillService{
	//日志
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	//注入service依赖
	@Autowired//
    private SeckillDao seckillDao;
	@Autowired
    //kfjsj 
	private SuccessKilledDao successkillDao;
    //md5盐值字符串，用于混淆md5
    private final String salt="asfjasklfjsaklfjsadklfjskfsadkf1646547564dfsf";
	@Override
	public List<Seckill> getSeckillService() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill findById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}
    /*暴露秒杀地址*/
	@Override 
	public Exposer exportSeckillUrl(long seckillId) {
		//缓存优化
		Seckill seckill=seckillDao.queryById(seckillId);
		//秒杀对象那个不存在
		if(seckill==null){
			return new Exposer(false,seckillId);
		}
		Date startTime=seckill.getStart_time();
		Date endTime=seckill.getEnd_time();
		//系统当前时间
 		Date nowDate=new Date();
 		//当前时间不在秒杀时间区间内，不执行秒杀
		if(nowDate.getTime()<startTime.getTime()||nowDate.getTime()>endTime.getTime()){
			return new Exposer(false, seckillId, nowDate.getTime(), startTime.getTime(), endTime.getTime());
		}
		//转化特定字符串的过程，不可逆
		String md5=getMd5(seckillId);
		return new Exposer(true, md5, seckillId);
	}
    /*
     * 执行秒杀
     * */
	@Override
	@Transactional
	/*
	 * 使用注解控制事务方法的注意要点：
	 * 1.开发团队达成一致，明确标注事务方法的编程风格；
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，如：RPC/HTTP请求或者剥离到事务方法外部
	 * 3.不是所有的方法都需要事务，如果只有一条修改操作，只有只读操作不需要事务控制
	 * 
	 * */
	public SeckillExecution excuteSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, SeckillCloseException,
			RepeatKillException {
		//如果用户传入过来的md5为空，或者与后天不匹配，则抛出异常
		if(md5==null||!md5.endsWith(getMd5(seckillId))){
			throw new SeckillException("秒杀数据重写！"); 
		} 
		//执行秒杀逻辑：减库存+记录行为
		Date nowTime=new Date();
		try{//对数据操作全局进行异常处理：放置数据库连接关闭等造成的异常
		//减少库存
		int updateCount=seckillDao.reduceNumber(seckillId, nowTime);
	    //秒杀未成功
		if(updateCount<=0){
			throw new SeckillCloseException("秒杀已经结束！");
		}else{//成功
		    //记录购买行为
			int insertCount=successkillDao.insertSuccessKilled(seckillId, userPhone);
			if(insertCount<=0){//seckillId, userPhone形成联合组建，有重复会插入失败
				throw new RepeatKillException("重复秒杀！");
			}else{//秒杀成功
				SuccessKilled successKilled=successkillDao.queryByIdWithSeckill(seckillId, userPhone);
			    //返回秒杀对象
				return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
			}
		}//抛出异常，mysql执行事务回滚
		}catch(SeckillCloseException e1){
        	throw e1;
		}catch(RepeatKillException e2){
        	throw e2;
        }catch(Exception e){
        	logger.error(e.getMessage(),e);
        	//所有编译期异常，转化成运行期异常
			throw new SeckillException("秒杀出现错误！："+e.getMessage());
		}
	}
	/*
	 * 私有方法，生成md5
	 * */
	private String getMd5(long seckillId){
		String base=seckillId+"/"+salt;
		//Spring专有方法，把二进制生成md5
		String md5=DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
		
	}

}
