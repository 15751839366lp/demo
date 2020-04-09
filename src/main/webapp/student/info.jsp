<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    pageContext.setAttribute("ctp", request.getContextPath());
%>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<link rel="shortcut icon" href="/static/favicon.ico"><style>
    *{
        margin: 0;padding: 0;
    }
</style>
<div class="panel panel-default" style="width: 100%">
    <div class="panel-heading" style="width: 100%">
        <h3 class="panel-title">个人信息</h3>
    </div>
    <div class="panel-body" style="width: 100%">
        <form class="form-horizontal" role="form" action="">
            <div class="form-group">
                <label  class="col-md-2 control-label">姓名：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="userName"  name="userName" style="width: 300px;"  value="${map.name}"  readonly="readonly">
                </div>
            </div>
            <div class="form-group">
                <label  class="col-md-2 control-label">密码：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="password"  name="password" style="width: 300px;"  value="${map.pwd}"  readonly="readonly">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">学号：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="stuNo"  name="stuNo" style="width: 300px;"  value="${map.stuNo}" readonly="readonly" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">专业：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="professional"  name="professional" style="width: 300px;"  value="${map.pro}" readonly="readonly" >
                </div>
            </div>
        </form>
    </div>
</div>
