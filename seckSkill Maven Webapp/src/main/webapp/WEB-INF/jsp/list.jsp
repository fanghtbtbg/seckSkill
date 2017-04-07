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
          <div class="panel panel-default">
               <div class="panel-heading text-center">
                    <h2>秒杀列表</h2>
               </div>
               <div class="pane-body">
                   <table class="table table-hover">
                      <thead>
                         <tr>
                           <th>名称</th>
                           <th>库存</th>
                           <th>开始时间</th>
                           <th>结束时间</th>
                           <th>创建时间</th>
                           <th>详情页面</th>
                         </tr>
                         <tr>
                      </thead>
                      <tbody>
                         <c:forEach var="sk" items="${list}">
                            <tr>
                                <td>${sk.name}</td>
                                <td>${sk.number}</td>    
                                <td>
                                    <fmt:formatDate value="${sk.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td>
                                     <fmt:formatDate value="${sk.end_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                               <td>
                                     <fmt:formatDate value="${sk.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                               </td>  
                               <td>
                                  <a class="btn btn-info" href="${sk.seckill_id}/detail" target="_blank">link</a>
                               </td>                                                                        
                            </tr>
                         </c:forEach>
                      </tbody>
                   </table>
               </div>
          </div>
     </div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/js/bootstrap.min.js"></script>
</body>
</html>
