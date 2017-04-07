package com.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.seckill.entity.Seckill;

@Repository
public interface SeckillDao {
	/*
	 * 减库存
	 * */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);
    /*
     * 根据id查询秒杀对象
     * */
    Seckill queryById(long seckillId);
    /*
     * 根据偏移量查询秒杀商品列表
     * */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
}
