<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    pageContext.setAttribute("ctp", request.getContextPath());
%>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<link rel="shortcut icon" href="/static/favicon.ico">
<style>
    *{
        margin: 0; padding: 0;
    }
    ul{
        width:100%;
    }
    li{
        text-align:center;
        text-decoration:none;
    }
</style>
<div id="menu">
    <ul class="list-group">
        <li><a class="list-group-item active">系统菜单</a></li>
            <c:if test="${user.userType=='管理员'}">
            <li class="list-group-item"><a href="${ctp}/stuList" target="main">学生信息管理</a></li>
            <li class="list-group-item"><a href="${ctp}/teaList" target="main">教师信息管理</a></li>
            <li class="list-group-item"><a href="${ctp}/courseList" target="main">课程信息管理</a></li>
            <li class="list-group-item"><a href="${ctp}/logout" target="_parent">安全退出</a></li>
            </c:if>
            <c:if test="${user.userType=='教师'}">
            <li class="list-group-item"><a href="${ctp}/teacher/courseList?id=${user.id}" target="main">查看课程</a></li>
            <li class="list-group-item"><a href="${ctp}/teacher/selectCourseList?id=${user.id}" target="main">选课管理</a></li>
            <li class="list-group-item"><a href="${ctp}/teacher/scoreList?id=${user.id}" target="main">成绩录入</a></li>
            <li class="list-group-item"><a href="${ctp}/teacher/info?name=${user.userName}&pwd=${user.password}&teaNo=${user.teaNo}" target="main">个人信息查看</a></li>
            <li class="list-group-item"><a href="${ctp}/logout" target="_parent">安全退出</a></li>
            </c:if>
            <c:if test="${user.userType=='学生'}">
            <li class="list-group-item"><a href="${ctp}/student/courseList?id=${user.id}" target="main">课程信息查看</a></li>
            <li class="list-group-item"><a href="${ctp}/student/selectList?id=${user.id}" target="main">选择课程</a></li>
            <li class="list-group-item"><a href="${ctp}/student/recommendSelectList?id=${user.id}" target="main">选择推荐课程</a></li>
            <li class="list-group-item"><a href="${ctp}/student/scoreList?id=${user.id}" target="main">成绩查询</a></li>
            <li class="list-group-item"><a href="${ctp}/student/info?name=${user.userName}&pwd=${user.password}&stuNo=${user.stuNo}&pro=${user.professional}" target="main">个人信息查看</a></li>
            <li class="list-group-item"><a href="${ctp}/logout" target="_parent">安全退出</a></li>
            </c:if>
    </ul>
</div>