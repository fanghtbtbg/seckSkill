package com.seckill.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/spring-dao.xml","classpath*:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    //注入
    @Autowired
    private SeckillService seckillService;
	//@Test
	public void testGetSeckillService() {
	   List<Seckill> list=seckillService.getSeckillService();
	   //{}为站位符
	   logger.info("list={}",list);
	}

	//@Test
	public void testFindById() {
		long id=1000;
		Seckill seckill=seckillService.findById(id);
		logger.info("sekill={}",seckill);
	}

	//@Test
	public void testExportSeckillUrl() {
		long id=1000;
		Exposer exposer=seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()){
		logger.info("exposer={}",exposer);
		// exposer=Exposer [exposed=true, md5=4b158b5b9d6cd2437e8838e9e3511b15, seckillId=1000, nowTime=0, startTime=0, endTime=0] 
		}else{
			//秒杀未开启
		 logger.warn("exposer={}",exposer);
		}
	}

	@Test
	public void testExcuteSeckill() {
		long id=1000;
		long phone=18502383898L;
		String md5="4b158b5b9d6cd2437e8838e9e3511b15";
		try{
	    SeckillExecution sket=seckillService.excuteSeckill(id, phone, md5);
	        logger.info("sket={}",sket);
		}catch(RepeatKillException e1){
			logger.info(e1.getMessage());
		}catch(SeckillCloseException e2){
			logger.info(e2.getMessage());
		}
	 
	}

}
