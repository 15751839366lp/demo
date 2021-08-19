<%@ page isErrorPage="true"  language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    pageContext.setAttribute("ctp", request.getContextPath());
%>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<link rel="shortcut icon" href="/static/favicon.ico"><script type="text/javascript">

    function resetValue() {
        $("#courseName").val("");
        $("#courseType").val("");
        $("#credit").val("");
        $("#selectNum").val("");
    }
    $(function () {
        $("#add").click(function () {
            var courseName = $("#courseName").val();
            var courseType = $("#courseType").val();
            var credit = $("#credit").val();
            var selectNum = $("#selectNum").val();
            if (courseName == null || courseName == "") {
                $("#error").html("课程名称不能为空！");
                return false;
            }
            if (courseType == null || courseType == "") {
                $("#error").html("课程类型不能为空！");
                return false;
            }

            if (credit == null || credit == "") {
                $("#error").html("学分不能为空！");
                return false;
            }

            if (teaNo == null || teaNo == "") {
                $("#error").html("教师编号不能为空！");
                return false;
            }
            if (selectNum == null || selectNum == "") {
                $("#error").html("可选人数不能为空！");
                return false;
            }
            $("#addForm").submit();
        });
        
        window.onload = function(){
            if(${user.userType=='教师'}){
                $("#teaNo").prop("readonly","readonly");
                $("#addForm").attr("action","${ctp}/teacher/course");
            } else if(${user.userType=='管理员'}){
                $("#addForm").attr("action","${ctp}/course");
            } else {
                return false;
            }
        };
    });
</script>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">添加课程</h3>
    </div>
    <div class="panel-body">
        <form class="form-horizontal" role="form" method="post" id="addForm">
            <div class="form-group">
                <label  class="col-md-2 control-label">课程名称：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="courseName"  name="courseName" style="width: 300px;"  value="">
                </div>
            </div>
            <div class="form-group">
                <label  class="col-md-2 control-label">课程类型：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="courseType"  name="courseType" style="width: 300px;"  value="">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">学分：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="credit"  name="credit" style="width: 300px;"  value="">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">教师编号：</label>
                <div class="col-md-10">
                    <c:if test="${teaNo==null}">
                        <input type="text" class="form-control" id="teaNo"  name="teaNo" style="width: 300px;">
                    </c:if>
                    <c:if test="${teaNo!=null}">
                        <input type="text" class="form-control" id="teaNo"  name="teaNo" style="width: 300px;"  value="${teaNo}">
                    </c:if>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">可选人数：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="selectNum"  name="selectNum" style="width: 300px;"  value="">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">                 
                    <button type="button" class="btn btn-primary" id="add">添加</button>&nbsp;&nbsp;
                    <button type="button" class="btn btn-primary" onclick="resetValue()">重置</button>&nbsp;&nbsp;
                    <font color="red" id="error"></font>
                </div>
            </div>
        </form>
    </div>
</div>
