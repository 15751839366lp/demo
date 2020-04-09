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
<script type="text/javascript">
    function checkForm() {
        var userName = $("#userName").val();
        var password = $("#password").val();
        var stuNo = $("#teaNo").val();
        if (userName == null || userName == "") {
            $("#error").html("姓名不能为空！");
            return false;
        }
        if (password == null || password == "") {
            $("#error").html("密码不能为空！");
            return false;
        }

        if (teaNo == null || teaNo == "") {
            $("#error").html("教师编号不能为空！");
            return false;
        }
        return true;
    }

    function resetValue() {
        $("#userName").val("");
        $("#password").val("");
        $("#teaNo").val("");
    }
    $(function () {
        $("#edit").click(function () {
            $("#editForm").submit();
        });
    });
</script>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">修改信息</h3>
    </div>
    <div class="panel-body">
        <form class="form-horizontal" role="form" method="post" action="${ctp}/tea/${tea.id}" onsubmit="checkForm()" id="editForm">
            <input type="hidden" name="_method" value="put"/>
            <div class="form-group">
                <label  class="col-md-2 control-label">姓名：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="userName"  name="userName" style="width: 300px;"  value="${tea.userName}">
                </div>
            </div>
            <div class="form-group">
                <label  class="col-md-2 control-label">密码：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="password"  name="password" style="width: 300px;"  value="${tea.password}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">教师编号：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="teaNo"  name="teaNo" style="width: 300px;"  value="${tea.teaNo}">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                    <!--<input type="submit" value="修改" class="btn btn-primary" id="edit"/>-->
                    <button type="button" class="btn btn-primary" id="edit">修改</button>&nbsp;&nbsp;
                    <button type="button" class="btn btn-primary" onclick="resetValue()">重置</button>&nbsp;&nbsp;
                    <font color="red" id="error"></font>
                </div>
            </div>
        </form>
    </div>
</div>