<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="common/tags.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <title>秒杀列表</title>
    <%@include file="common/head.jsp"%>
</head>

<body>
<div class="container">
        <div class="panel panel-default text-center">
             <div class="pannel-heading">
                   ${seckill.name}
             </div>
        <div class="panel-body">
           <h2 class="text-danger">
               <!-- 显示time图标 -->
               <span class="glyphicon glyphicon-time"></span>
               <!-- 展示倒计时 -->
               <span class="glyphicon" id="seckill-box"></span>
           </h2>
        </div>
     </div>
</div>

<!-- 弹出框，输入电话 -->
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
         <div class="modal-content">
              <div class="modal-header">
                   <h3 class="modal-title text-center">
                       <span class="glyphicon glyphicon-phone"></span>填写秒杀电话：
                   </h3>
              </div>
              <div class="modal-body">
                   <div class="row">
                       <div class="col-xs-8 col-xs-offset-2">
                           <input type="text" name="killPhone" id="killPhoneKey" placeholder="填写手机号+_+" class="form-control"/>
                       </div>
                   </div>
              </div>
              <div class="modal-footer">
                  <!-- 验证信息 -->
                  <span id="killPhoneMessage" class="glyphicon"></span>
                  <button type="button" id="killPhoneBtn" class="btn btn-success">
                        <span class="glyphicon glyphicon-phone">
                          Submit
                        </span>
                  </button>
              </div>
         </div>
    </div>
</div>



    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	<!-- 使用CDN 获取公共js http://www.bootcdn.cn/ -->
	<!-- jQuery cookie操作插件 -->
	<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
	<!-- jQuery countDown倒计时插件 -->
	<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
   
   <script type="text/javascript">
       //存放主要交互逻辑js代码
		// javascript 模块化
		// seckill.detail.init(params);
		 var seckill = {
		     //封装秒杀相关ajax的url
		     URL:{
		         now:function(){
		             return '/seckSkill/seckill/time/now';
		         },
		         exposer:function(seckillId){
		             return '/seckSkill/seckill/'+seckillId+'/exposer';
		         },
		         execution:function(seckillId,md5){
		             return '/seckSkill/seckill/execute/'+seckillId+'/'+md5+'';
		         }
		
		     },
		     handleSeckillKill:function(seckillId,node){
		         //处理秒杀逻辑
		         node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
             //$.ajax({
			 // type: 'POST',
			 // url: url,
			 //  data: data,
			 // success: success,
			 // dataType: dataType
			 //});
		         $.post(seckill.URL.exposer(seckillId),{},function(result){
		            //在回调函数中执行交互流程
		             if(result && result['success']){
		                 var exposer = result['data'];
		                 if(exposer['exposed']){
		                     //开启秒杀
		                     //后去秒杀地址
		                     var md5 = exposer['md5'];
		                     var killUrl = seckill.URL.execution(seckillId,md5);
		                     console.log("killUrl:"+killUrl);
		                     //绑定一次点击事件，防止重复点击
		                     $("#killBtn").one('click',function(){
		                         //执行秒杀请求的操作
		                         //1:禁用按钮
		                         $(this).addClass('disabled');
		                         //2:发送秒杀请求
		                         $.get(killUrl,{},function(result){
		                            if(result && result['success']){
		                                var killResult = result['data'];
		                                var state = killResult['state'];
		                                var stateInfo = killResult['stateInfo'];
		                                //3:显示秒杀结果
		                                node.html('<span class="label label-success">' + stateInfo + '</span>');
		                            }
		                         });
		                     });
		                     node.show();
		                 }else{
		                     //未开启秒杀
		                     var now= exposer['now'];
		                     var start = exposer['start'];
		                     var end = exposer['end'];
		                     //重新进入计时逻辑
		                     seckill.countdown(seckillId,now,start,end);
		                 }
		             }else{
		                 console.log("result:"+result);
		             }
		         });
		     },
		     //验证手机号:不为空，长度为11，数值组成
		     validatePhone:function(phone){
		         if(phone && phone.length == 11 && !isNaN(phone)){
		             return true;
		         }else{
		             return false;
		         }
		     },
		     countdown:function(seckillId,nowTime,startTime,endTime){//定时器
		         var seckillBox = $('#seckill-box');
		         if(nowTime>endTime){
		             seckillBox.html('秒杀结束');
		             console.log('秒杀结算');
		         }else if(nowTime<startTime){
		             //秒杀未开始，计时事件绑定
		             var killTime = new Date(startTime+1000);
		             seckillBox.countdown(killTime,function(event){
		                 //时间的格式
		                 var format = event.strftime('秒杀倒计时：%D天  %H时  %M分  %S秒');
		                 seckillBox.html(format);
		             }).on('finish.countdown',function(){//时间完成后回调事件
		                 //控制地址，控制实现逻辑，执行秒杀
		                 seckill.handleSeckillKill(seckillId,seckillBox);
		             })
		         }else{
		            //执行秒杀
		              alert('执行秒杀');
		              seckill.handleSeckillKill(seckillId,seckillBox);
		         }
		     },
		     //详情页秒杀逻辑
		     detail:{
		         //详情页初始化
		        init:function(params){
		            //手机验证和登录 , 计时交互
		            //规划我们的交互流程
		            //在cookie中查找手机号
		            var killPhone = $.cookie('killPhone');
		
		            //验证手机号
		            if(!seckill.validatePhone(killPhone)){
		                //绑定phone
		                //控制输出
		                var killPhoneModal = $('#killPhoneModal');
		                //显示弹出层
		                killPhoneModal.modal({
		                    show:true,//显示弹出层
		                    backdrop:'static',//禁止位置关闭
		                    keyboard:false//关闭键盘事件
		                });
		                $('#killPhoneBtn').click(function(){
		                    var inputPhone = $('#killPhoneKey').val();
		                    if(seckill.validatePhone(inputPhone)){
		                        //电话写入cookie
		                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckSkill/seckill'});
		                        //刷新页面
		                        window.location.reload();
		                    }else{
		                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号码错误！</label>').show(300);
		                    }
		                });
		            }
		            //已经登录
		            //及时交互
		            var startTime = params['startTime'];
		            var endTime = params['endTime'];
		            var seckillId = params['seckillId'];
		            $.get(seckill.URL.now(),{},function(result){
		                if(result && result['success']){
		                    var nowTime = result['data'];
		                    //时间判断，计时交互
		                    seckill.countdown(seckillId,nowTime,startTime,endTime);
		                }else{
		                    console.log('result:'+result);
		                }
		            });
		        }
		     }
		 }
   </script>
   <script type="text/javascript">
         $(function(){
            //EL表达式传入参数
            seckill.detail.init({
               seckillId:${seckill.seckill_id},
               startTime:${seckill.start_time.time},
               endTime:${seckill.end_time.time}
            });
         });
   </script>
   
   
   
</body>
</html>
