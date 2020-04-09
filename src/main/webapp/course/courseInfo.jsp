<%@ page isErrorPage="true" isELIgnored="false" language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    pageContext.setAttribute("ctp", request.getContextPath());
%>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<link rel="shortcut icon" href="/static/favicon.ico">
<script>
    $(function(){
        $("#addScore").click(function(){
            window.location.href = "${ctp}/toAddScore?courseId=${map.courseId}";
        });
    })

    function scoreDelete(id) {
        if (confirm("确认要删除这条记录吗？")) {
            $.ajax({
                url: "${ctp}/score/" + id,
                type: "POST",
                data: {_method: "DELETE",courseId: ${map.courseId}},
                success: function (data) {
                    window.location.href = "${ctp}/showStudent?id=${map.course.id}";
                    //window.location.assign("${ctp}/teaList");
                    //$(window).attr("location","${ctp}/teaList");
                    /*var result = eval('(' + result + ')');
                     if (result.success) {
                     alert("删除成功!");
                     window.location.href = "${pageContext.request.contextPath}/teacher?action=list";
                     } else {
                     alert("改学生有选课记录，不能删除！");
                     }*/
                }
            });
        } else {
            return false;
        }
    }
</script>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">课程信息</h3>
    </div>
    <div class="panel-body">
        <div class="form-group">
            <label class="col-md-2 control-label">课程名称：</label>
            <div class="col-md-10">
                <input readonly="readonly" type="text" class="form-control" id="courseName" name="courseName" style="width: 300px;"
                       value="${map.course.courseName}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label">课程类型：</label>
            <div class="col-md-10">
                <input readonly="readonly" type="text" class="form-control" id="courseType" name="courseType" style="width: 300px;"
                       value="${map.course.courseType}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label">学分：</label>
            <div class="col-md-10">
                <input readonly="readonly" type="text" class="form-control" id="credit" name="credit" style="width: 300px;"
                       value="${map.course.credit}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label">授课教师：</label>
            <div class="col-md-10">
                <input readonly="readonly" type="text" class="form-control" id="teaNo" name="teaNo" style="width: 300px;"
                       value="${map.course.tea.userName}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label">学生人数：</label>
            <div class="col-md-10">
                <input readonly="readonly" type="text" class="form-control" id="num" name="num" style="width: 300px;"
                       value="${map.course.stuNum}/${map.course.selectNum}">
            </div>
        </div>
    </div>
    <div class="panel-heading" style="width: 100%;height: 45px">
        <button type="button" style="float: right;" class="btn btn-primary" id="addScore">添加学生</button>
    </div>
    <div>
        <table class="table table-hover  table-bordered table-striped" style="margin-bottom: 0px;">
            <tr>
                <th>序号</th>
                <th>学生姓名</th>
                <th>学号</th>
                <th>专业</th>
                <th>分数</th>
                <th>操作</th>
            </tr>
            <c:forEach var="score" items="${map.scoreList.list}" varStatus="status">
                <tr class="info">
                    <td>${status.index+1+(map.scoreList.pageNum-1)*5}</td>
                    <td>${score.stu.userName }</td>
                    <td>${score.stu.stuNo }</td>
                    <td>${score.stu.professional }</td>
                    <td>${score.score }</td>
                    <td>
                        <button type="button" class="btn btn-info btn-xs"
                                onclick="javascript:window.location.href = '${ctp}/course/toEditScore?id=${score.id}&teaId=${score.course.tea.id}'">
                            修改成绩
                        </button>
                        <button type="button" class="btn btn-danger btn-xs"
                                onclick="scoreDelete(${score.id})">
                            删除学生
                        </button>
                    </td>
                    <!--<form hidden="" action="" method="post">
                            <input type="hidden" name="_method" value="DELETE"/>
                            <input type="submit" value="删除"/>
                    </form>-->
                </tr>
            </c:forEach>
            <tr>
                <td style="margin: 0;border: 0;text-align: center" colspan="6">
                    <a href="${ctp}/showStudent?id=${map.course.id}">首页</a>
                    <a class="prePage"
                       href="${ctp}/showStudent?id=${map.course.id}&page=${map.scoreList.prePage}">上一页</a>
                    ${map.scoreList.pageNum}
                    <a class="nextPage"
                       href="${ctp}/showStudent?id=${map.course.id}&page=${map.scoreList.nextPage}">下一页</a>
                    <a href="${ctp}/showStudent?id=${map.course.id}&page=${map.scoreList.pages}">末页</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<script>
    $(function () {
        if (${map.scoreList.pageNum} == ${map.scoreList.pages}) {
            $(".nextPage").hide();
        }
        if (${map.scoreList.pageNum} == 1
    )
        {
            $(".prePage").hide();
        }
    });
</script>


