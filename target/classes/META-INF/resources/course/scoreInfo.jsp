<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    pageContext.setAttribute("ctp", request.getContextPath());
%>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<link rel="shortcut icon" href="/static/favicon.ico"><script type="text/javascript">
	$(function(){
            $("#edit").click(function(){
                $("#editForm").submit();
            });
        });
</script>

<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">成绩信息</h3>
    </div>
    <div class="panel-body">
        <form class="form-horizontal" role="form" id="editForm" method="post" action="${ctp}/course/score">
            <input type="hidden" name="id" value="${score.id}"/>
            <input type="hidden" name="teaId" value="${user.id}"/>
            <input type="hidden" name="courseId" value="${score.course.id}"/>
            <div class="form-group">
                <label  class="col-md-2 control-label">学生姓名：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="stuName"  name="stuName" style="width: 300px;"  value="${score.stu.userName }"  readonly="readonly">
                </div>
            </div>
            <div class="form-group">
                <label  class="col-md-2 control-label">课程名称：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="courseName"  name="courseName" style="width: 300px;"  value="${score.course.courseName }"  readonly="readonly">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">学分：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="credit"  name="credit" style="width: 300px;"  value="${score.course.credit }" readonly="readonly" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">分数：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="score"  name="score" style="width: 300px;"  value="${score.score }" >
                </div>
            </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" class="btn btn-primary" id="edit">提交</button>
                    </div>
                </div>
        </form>
    </div>
</div>