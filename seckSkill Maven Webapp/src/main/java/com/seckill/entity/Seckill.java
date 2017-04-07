package com.seckill.entity;

import java.util.Date;

public class Seckill {//秒杀库存类
	private long seckill_id;//id
	private String name;//商品名称
	private int number;//商品库存数量
	private Date start_time;//秒杀开始时间
	private Date end_time;//秒杀结算时间
	private Date create_time;//秒杀创建时间
	public long getSeckill_id() {
		return seckill_id;
	}
	public void setSeckill_id(long seckill_id) {
		this.seckill_id = seckill_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	
	//重写to_string方法，方便单元测试
	@Override
	public String toString() {
		return "Seckill [seckill_id=" + seckill_id + ", name=" + name
				+ ", number=" + number + ", start_time=" + start_time
				+ ", end_time=" + end_time + ", create_time=" + create_time
				+ "]";
	}
	
}
