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

    function resetValue() {
        $("#stuName").val("");
        $("#stuNo").val("");
    }
    $(function () {
        $("#add").click(function () {
            var stuName = $("#stuName").val();
            var stuNo = $("#stuNo").val();
            if (stuName == null || stuName == "") {
                $("#error").html("学生名称不能为空！");
                return false;
            }
            if (stuNo == null || stuNo == "") {
                $("#error").html("学号不能为空！");
                return false;
            }
            $.ajax({
                type: "POST",
                url: "${ctp}/score" ,
                data: $('#addForm').serialize(),
                success: function (result) {
                    if ("添加成功！" == result) {
                        alert(result);
                        window.location.href = "${ctp}/showStudent?id=${courseId}";
                    } else {
                        alert(result);
                        window.location.href = "${ctp}/toAddScore?courseId=${courseId}";
                    }
                }
            });
        });
    });
</script>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">添加学生</h3>
    </div>
    <div class="panel-body">
        <form class="form-horizontal" action="${ctp}/score" role="form" method="post" id="addForm">
            <input type="hidden" name="cid" value="${courseId}"/>
            <div class="form-group">
                <label  class="col-md-2 control-label">学生名称：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="stuName"  name="stuName" style="width: 300px;" >
                </div>
            </div>
            <div class="form-group">
                <label  class="col-md-2 control-label">学号：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="stuNo"  name="stuNo" style="width: 300px;" >
                </div>
            </div>
            <div class="form-group">
                <label  class="col-md-2 control-label">分数：</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="score"  name="score" style="width: 300px;" >
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">

                    <!--<input type="submit" value="修改" class="btn btn-primary" id="edit"/>-->
                    <button type="button" class="btn btn-primary" id="add">提交</button>&nbsp;&nbsp;
                    <button type="button" class="btn btn-primary" onclick="resetValue()">重置</button>&nbsp;&nbsp;
                    <font color="red" id="error"></font>
                </div>
            </div>
        </form>
    </div>
</div>