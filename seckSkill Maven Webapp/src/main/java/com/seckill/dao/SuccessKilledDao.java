package com.seckill.dao;

import org.apache.ibatis.annotations.Param;

import com.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {
	/*
	 * 插入购买明细，可通过联合主键过滤重复
	 * */
	 int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userphone") long userphone);
	 /*
	  * 根据id查询SuccessKilled对象 并携带Seckill实体队形
	  * */
	 SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userphone") long userphone);

}
