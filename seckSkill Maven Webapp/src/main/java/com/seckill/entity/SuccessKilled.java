package com.seckill.entity;

import java.util.Date;

public class SuccessKilled {//交易成功记录表
   private long seckill_id;//id
   private long user_phone;//用户电话号码
   private short state;//状态   状态标示符：-1无效，0成功，1已付款， 2已发货，3已收获
   private Date create_time;//生成时间
   //多对一 (一个秒杀对应多个交易成功记录)
   private Seckill seckill;
	public long getSeckill_id() {
		return seckill_id;
	}
	public void setSeckill_id(long seckill_id) {
		this.seckill_id = seckill_id;
	}
	public long getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(long user_phone) {
		this.user_phone = user_phone;
	}
	public short getState() {
		return state;
	}
	public void setState(short state) {
		this.state = state;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Seckill getSeckill() {
		return seckill;
	}
	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}
	//重写toString方法，方便单元测试
	@Override
	public String toString() {
		return "SuccessKilled [seckill_id=" + seckill_id + ", user_phone="
				+ user_phone + ", state=" + state + ", create_time="
				+ create_time + "]";
	}
}
