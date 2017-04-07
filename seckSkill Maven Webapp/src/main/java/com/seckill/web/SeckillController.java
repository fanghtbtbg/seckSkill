package com.seckill.web;

import java.util.Date;
import java.util.List;

import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.dto.SeckillResult;
import com.seckill.entity.Seckill;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.service.SeckillService;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	//注入service层
	@Autowired
	private SeckillService seckillService;
	
	/*
	 *1. 展示页
	 * */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model){
		//获取列表页
		List<Seckill> list=seckillService.getSeckillService();
		model.addAttribute("list", list);
		return "list";
	}
	/*
	 * 2.根据id 详情展示页
	 * */
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId,Model model){
		//如果用户未传递参数,重定向到list
		if(seckillId==null){
			return "redirect:/seckill/list";
		}
		Seckill seckill=seckillService.findById(seckillId);
		//如果查询的对象为空
		if(seckill==null){
			return "forward:/serkill/list";
		}
		model.addAttribute("seckill",seckill);
		return "detail";
	}
  /*
   *3. 暴露秒杀地址:ajax请求，返回json
   * */
	@RequestMapping(value="/{seckillId}/exposer",method=RequestMethod.POST,
			produces={"application/json;charset=UTF-8"})//只能通过post方式请求
	//通过注解告知返回 json
	@ResponseBody
  public SeckillResult<Exposer> exposer(@PathVariable Long seckillId){
	  SeckillResult<Exposer> result;
	  try{
		  Exposer exposer=seckillService.exportSeckillUrl(seckillId);
		  result =new SeckillResult<Exposer>(true,exposer);
		  
	  }catch(Exception e){
		  logger.error(e.getMessage(),e);
		  result=new SeckillResult<Exposer>(false,e.getLocalizedMessage());
	  }
	  return result;
  }
	/*
	 * 4.执行秒杀
	 * 
	 * 只能通过post方式请求
	 *通过注解告知返回 json
	 * */
	@RequestMapping(value="/execute/{seckillId}/{md5}",method={RequestMethod.POST,RequestMethod.GET},
			produces={"application/json;charset=UTF-8"})
	@ResponseBody
  public SeckillResult<SeckillExecution> execute(
		  @PathVariable("seckillId") Long seckilId,
		  @PathVariable("md5") String md5,
		  @CookieValue(value="killPhone",required=false) Long phone){//手机号码不是必须的，通过cookie传递
	  //如果电话号码为空
	  if(phone==null){
		  return new SeckillResult<SeckillExecution>(false,"未注册");
	  }
	  SeckillResult<SeckillExecution> result;
	  try{
	  SeckillExecution execution=seckillService.excuteSeckill(seckilId, phone, md5);
	  return new SeckillResult<SeckillExecution>(true,execution);
	  }catch(RepeatKillException e1){//重复秒杀
		  SeckillExecution execution=new SeckillExecution(seckilId,SeckillStateEnum.REAPEAT_KILL);
		  return new SeckillResult<SeckillExecution>(true,execution);
	  }catch(SeckillCloseException e2){//秒杀已经结算或者关闭
		  SeckillExecution execution=new SeckillExecution(seckilId,SeckillStateEnum.END);
		  return new SeckillResult<SeckillExecution>(true,execution);
	  }catch(Exception e3){//系统错误，请稍后！
		  logger.error(e3.getMessage(),e3);
		  SeckillExecution execution=new SeckillExecution(seckilId,SeckillStateEnum.INNER_ERROR);
		  return new SeckillResult<SeckillExecution>(true,execution);
	  }
	}
	  /*
	   * 5.获取系统时间
	   * */
	  @RequestMapping(value="/time/now")
	  //返回json字符串
	  @ResponseBody
	  public SeckillResult<Long> time(){
		  Date now =new Date();
		  return new SeckillResult(true,now.getTime());
	  }
  
}
