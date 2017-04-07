package com.seckill.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.entity.SuccessKilled;

@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
public class SuccessKilledDaoTest {
	//注入Dao实现依赖
		@Autowired
		private SuccessKilledDao sd;
	//@Test
	public void testInsertSuccessKilled() {
		int tt=sd.insertSuccessKilled(1000L, 18502383878L);
		System.out.println(tt);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		SuccessKilled ske=sd.queryByIdWithSeckill(1000L,18502383878L);
		System.out.println(ske.getSeckill().getName());
		System.out.println(ske.getUser_phone());
		System.out.println(ske.getCreate_time());
		System.out.println(ske.getSeckill());
	}

}
