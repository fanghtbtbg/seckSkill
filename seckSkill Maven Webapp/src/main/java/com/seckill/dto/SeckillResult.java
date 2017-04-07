package com.seckill.dto;
//所有ajax请求返回类型，封装json结果
public class SeckillResult<T> {
	private boolean success;
	private T data;
	private String err;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public SeckillResult(boolean success, T data) {
		this.success = success;
		this.data = data;
	}
	public SeckillResult(boolean success, String err) {
		this.success = success;
		this.err = err;
	}
	

}
