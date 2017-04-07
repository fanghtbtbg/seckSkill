

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.dao.SeckillDao;
import com.seckill.entity.Seckill;
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件

public class SeckillDaoTest {
    //注入Dao实现依赖
	@Autowired
	private SeckillDao seckillDao;
	
	
	//@Test
	public void testReduceNumber() {
		Date killTime=new Date();
		int updateCount=seckillDao.reduceNumber(1000L, killTime);
		System.out.println(updateCount);
	}

	//@Test
	public void testQueryById() {
		Long id=(long) 1000;
	    Seckill seckill=seckillDao.queryById(id);	
	    System.out.println(seckill.getName());
	    System.out.println(seckill);
	}

	@Test
	public void testQueryAll() {
		//java没有保存形参的记录,及无法识别参数
		//接口用@Param解决 
		List<Seckill> seckills=seckillDao.queryAll(0, 100);
		for(Seckill seckill:seckills){
			System.out.println(seckill);
		}
	}
	

}
