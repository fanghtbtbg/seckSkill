//package com.seckill.dao;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.seckill.entity.Seckill;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
///**
// *@author fqc
// *@date 2016-11-27
// *@project seckSkill Maven Webapp
// *@package com.seckill.dao
// *@des Redis缓存，处理高并发
// */
//public class RedisDao {
//	private final Logger logger=LoggerFactory.getLogger(this.getClass());
//	private final JedisPool jedisPool;
//	public RedisDao(String ip,int port){
//		jedisPool=new JedisPool(ip,port);
//	}
//	private RuntimeSchema<Seckill> schma=RuntimeSchema.createFrom(Seckill.class);
//	public Seckill getSeckill(long seckillId){
//		//redis操作逻辑
//		try{	
//			Jedis jedis=jedisPool.getResource();
//			try{
//			String key="seckill"+seckillId;
//			/*
//			 * 没有实现内部序列化操作，
//			 * get->byte[]->反序列话->Object[seckillId]
//			 * 采用自定义序列化
//			 * protostuff:pojo
//			 * */
//			 byte[] bytes=jedis.get(key.getBytes());
//			 //获取缓存
//			 if(bytes!=null){
//				 //空对象
//				 Seckill seckill=schame.newMessage();
//				 //seckill 被饭序列化，压缩不到使用jdk的10%，性能效率更高;更节省cpu
//				 ProtostuffOUtill.mergeForm(bytes,seckill,schame);
//				 retrun seckill;
//			 }
//			}finally{
//				jedis.close();
//			}
//		}catch(Exception e){
//			logger.error(e.getMessage(),e);
//		}
//		return null;
//	}
//	
//	private String putSeckill(Seckill seckill){
//		//对象序列化成字节数组
//		//set Object[Seckill]->序列化->byte[]
//		try {
//			Jedis jedis=jedisPool.getResource();
//			try {
//				String key="seckill:"+seckill.getSeckill_id();
//				byte[] bytes=ProtostuffOUtil.toByteArray(seckill,schema,LinkedBuffer.allocate(LinkedBuffer.DEAFLUT_BUFFER_SIZE));
//				//超时缓存
//				int timeout=60*60;
//				String result=jedis.setex(key.getBytes(),t imeout,bytes);
//				return result;
//			} finally  {
//				jedis.close();
//			}
//			
//		} catch (Exception e) {
//           logger.error(e.getMessage(),e);   
//		}
//		
//		return null;
//	}
//}
