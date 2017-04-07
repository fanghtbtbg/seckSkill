package com.seckill.service;

import java.util.List;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

/*
 * 业务接口
 * */
public interface SeckillService {
 /*
  * 查询所有秒杀记录
  * */
	List<Seckill> getSeckillService();
  /*
   * 查询单个记录
	 * */
	Seckill findById(long seckillId);
	
	/*
	 * 秒杀开始时输出秒杀接口的地址；否则输出系统时间和秒杀时间
	 * */
	 Exposer exportSeckillUrl(long seckillId);
	 /*
	  * 验证用户，执行秒杀操作
	  * */
	 SeckillExecution excuteSeckill(long seckillId,long userPhone,String md5) throws SeckillException, SeckillCloseException,RepeatKillException;
}
