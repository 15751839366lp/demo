<%@ page isErrorPage="true" isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    pageContext.setAttribute("ctp", request.getContextPath());
%>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<link rel="shortcut icon" href="/static/favicon.ico"><style>
    .row search{
        margin: 0;padding: 0;
    }
    .input-group-btn{
        display: inline;
    }
</style>
<div class="panel panel-default" style="width: 100%">
    <div class="panel-heading" style="width: 100%">
        <h3 class="panel-title">成绩信息</h3>
    </div>
    <div>
        <table class="table table-hover  table-bordered table-striped" style="margin-bottom: 0px;">
            <tr>
                <th>序号</th>
                <th>课程名称</th>
                <th>课程类型</th>
                <th>授课教师</th>
                <th>学分</th>
                <th>分数</th>
            </tr>
            <c:forEach var="score" items="${map.courseList.list}" varStatus="status">
                <tr class="info">
                    <td>${status.index+1+(map.courseList.pageNum-1)*12}</td>
                    <td>${score.course.courseName }</td>
                    <td>${score.course.courseType }</td>
                    <td>${score.course.tea.userName }</td>
                    <td>${score.course.credit }</td>
                    <c:if test="${score.score==null}">
                        <td>未录入</td>
                    </c:if>
                    <c:if test="${score.score!=null}">
                        <td>${score.score }</td>
                    </c:if>
                    <!--<form hidden="" action="" method="post">
                            <input type="hidden" name="_method" value="DELETE"/>
                            <input type="submit" value="删除"/>
                    </form>-->
                </tr>
            </c:forEach>
            <tr>
                <td style="margin: 0;border: 0;text-align: center" colspan="6">
                    <a href="${ctp}/student/scoreList?id=${user.id}">首页</a>
                    <a class="prePage" href="${ctp}/student/scoreList?page=${map.courseList.prePage}&id=${user.id}">上一页</a>
                    ${map.courseList.pageNum}
                    <a class="nextPage" href="${ctp}/student/scoreList?page=${map.courseList.nextPage}&id=${user.id}">下一页</a>
                    <a href="${ctp}/student/scoreList?page=${map.courseList.pages}&id=${user.id}">末页</a>
                </td>
            </tr>
        </table>
        <script>
            $(function () {
                if (${map.courseList.pageNum} == ${map.courseList.pages}) {
                    $(".nextPage").hide();
                }
                if (${map.courseList.pageNum} == 1) {
                    $(".prePage").hide();
                }
            });
        </script>

    </div>
</div>