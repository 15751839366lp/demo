<%@ page isErrorPage="true" isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    pageContext.setAttribute("ctp", request.getContextPath());
%>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<link rel="shortcut icon" href="/static/favicon.ico"><script type="text/javascript">

    $(function () {

        /*$(".deleteStu").click(function () {
         $(".deleteForm").submit();
         });*/

        $(".choice").click(function () {
            var context = $(this).text();
            window.location.href = "${ctp}/teacher/getScoreByCourseName?courseName=" + context + "&teaId=${user.id}";
        })

        $("#checkStu").click(function () {
            var checkContext = $("#checkContext").val();
            window.location.href = "${ctp}/teacher/getScoreByName?name=" + checkContext + "&teaId=${user.id}";
        });
    });


</script>
<style>
    .row search{
        margin: 0;padding: 0;
    }
    .deleteForm{
        height: 20px;
        width:20px;
        margin: 0;
        padding: 0;
        display: inline;
    }
    .input-group-btn{
        display: inline;
    }
    #checkStu{
        display: inline;
    }
</style>
<div class="row search" >
    <div class="col-md-6" >
        <div class="input-group" style="width: 600px">
            <input type="text" style="width:250px" id="checkContext" class="form-control" name=""  value="" placeholder="请输入要查询的学生姓名...">
            <span class="input-group-btn">
                <button id="checkStu" class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button>
            </span>
            <div class="btn-group dropdown" id="dropdown">
                <button style="width:130px" id="choices" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    请选择课程
                    <span class="caret"></span>
                </button>
                <input type="hidden" name="hidedrop_1" id="hidedrop_1" value="" />
                <ul class="dropdown-menu">
                    <li><a href="#" class="choice">所有课程</a></li>
                    <c:forEach var="course" items="${map.courseSet}">
                        <li role="separator" class="divider"></li>
                        <li><a href="#" class="choice">${course}</a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
<div>
    <table class="table table-hover  table-bordered table-striped" style="margin-bottom: 0px;">
        <tr>
            <th>序号</th>
            <th>学生姓名</th>
            <th>课程名称</th>
            <th>学分</th>
            <th>分数</th>
            <th>操作</th>
        </tr>
        <c:forEach var="score" items="${map.scoreList.list}" varStatus="status">
            <tr class="info">
                <td>${status.index+1+(map.scoreList.pageNum-1)*12}</td>
                <td>${score.stu.userName }</td>
                <td>${score.course.courseName }</td>
                <td>${score.course.credit }</td>
                <td>${score.score }</td>
                <td>
                    <button type="button" class="btn btn-success btn-xs" onclick="javascript:window.location.href = '${ctp}/teacher/toAddScore?id=${score.id}&teaId=${user.id}'">成绩录入</button> 
                    <button type="button" class="btn btn-info btn-xs" onclick="javascript:window.location.href = '${ctp}/teacher/toEditScore?id=${score.id}&teaId=${user.id}'">修改成绩</button>                   
                </td>
                <!--<form hidden="" action="" method="post">
                        <input type="hidden" name="_method" value="DELETE"/>
                        <input type="submit" value="删除"/>
                </form>-->
            </tr>
        </c:forEach>
        <tr>
            <td style="margin: 0;border: 0;text-align: center" colspan="6">
                <c:if test="${map.method=='name'}">
                    <a href="${ctp}/teacher/getScoreByName?name=${map.value}&teaId=${user.id}">首页</a>
                    <a class="prePage" href="${ctp}/teacher/getScoreByName?page=${map.scoreList.prePage}&name=${map.value}&teaId=${user.id}">上一页</a>
                    ${map.scoreList.pageNum}
                    <a class="nextPage" href="${ctp}/teacher/getScoreByName?page=${map.scoreList.nextPage}&name=${map.value}&teaId=${user.id}">下一页</a>
                    <a href="${ctp}/teacher/getScoreByName?page=${map.scoreList.pages}&name=${map.value}&teaId=${user.id}">末页</a>
                </c:if>
                <c:if test="${map.method=='course'}">
                    <a href="${ctp}/teacher/getScoreByCourseName?courseName=${map.value}&teaId=${user.id}">首页</a>
                    <a class="prePage" href="${ctp}/teacher/getScoreByCourseName?page=${map.scoreList.prePage}&courseName=${map.value}&teaId=${user.id}">上一页</a>
                    ${map.scoreList.pageNum}
                    <a class="nextPage" href="${ctp}/teacher/getScoreByCourseName?page=${map.scoreList.nextPage}&courseName=${map.value}&teaId=${user.id}">下一页</a>
                    <a href="${ctp}/teacher/getScoreByCourseName?page=${map.scoreList.pages}&courseName=${map.value}&teaId=${user.id}">末页</a>
                </c:if>
                <c:if test="${map.method==null}">
                    <a href="${ctp}/teacher/scoreList?id=${user.id}">首页</a>
                    <a class="prePage" href="${ctp}/teacher/scoreList?id=${user.id}&page=${map.scoreList.prePage}">上一页</a>
                    ${map.scoreList.pageNum}
                    <a class="nextPage" href="${ctp}/teacher/scoreList?id=${user.id}&page=${map.scoreList.nextPage}">下一页</a>
                    <a href="${ctp}/teacher/scoreList?id=${user.id}&page=${map.scoreList.pages}">末页</a>
                </c:if>
            </td>
        </tr>
    </table>
    <script>
        $(function () {
            if (${map.scoreList.pageNum} == ${map.scoreList.pages}) {
                $(".nextPage").hide();
            }
            if (${map.scoreList.pageNum} == 1) {
                $(".prePage").hide();
            }
        });
    </script>

</div>




