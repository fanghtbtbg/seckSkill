package com.seckill.dto;

import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnum;

/*
 * 执行秒杀后果
 * */
public class SeckillExecution {
	private long seckillId;//执行秒杀商品id
	private int state;// 秒杀执行结果状态
	private String stateInfo;//状态表示
	private SuccessKilled successKilled;//秒杀成功对象
	public long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}
	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}
	//利用枚举
	public SeckillExecution(long seckillId, SeckillStateEnum eum,
			SuccessKilled successKilled) {
		this.seckillId = seckillId;
		this.state = eum.getState();
		this.stateInfo = eum.getStateInfo();
		this.successKilled = successKilled;
	}
	//利用枚举
	public SeckillExecution(long seckillId, SeckillStateEnum eum) {
		this.seckillId = seckillId;
		this.state = eum.getState();
		this.stateInfo = eum.getStateInfo();
	}
	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state
				+ ", stateInfo=" + stateInfo + ", successKilled="
				+ successKilled + "]";
	}

}
