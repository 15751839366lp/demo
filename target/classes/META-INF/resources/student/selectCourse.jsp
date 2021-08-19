<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    pageContext.setAttribute("ctp", request.getContextPath());
%>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<link rel="shortcut icon" href="/static/favicon.ico">
<script type="text/javascript">

    // 退选课程

    $(function () {
        $(".mcl").click(function () {
            var checkbox = $(this).find("input[type=checkbox]");
            $(this).siblings().find("input[type=checkbox]").prop("checked", false);
            $(".scl").find("input[type=checkbox]").prop("checked", false);
            if (checkbox.prop("checked")) {
                checkbox.prop("checked", false);
            } else {
                checkbox.prop("checked", true);
            }
        });

        $(".scl").click(function () {
            var checkbox = $(this).find("input[type=checkbox]");
            $(this).siblings().find("input[type=checkbox]").prop("checked", false);
            $(".mcl").find("input[type=checkbox]").prop("checked", false);
            if (checkbox.prop("checked")) {
                checkbox.prop("checked", false);
            } else {
                checkbox.prop("checked", true);
            }
        });

        $("#choose").click(function () {
            if ($("#selectCourseTable").find("input:checkbox:checked").length >= 1) {
                var len = $("#myCourseTable").find("tr").length - 1;
                if (len >= 3) {
                    alert("选择失败！最多只能选三门课。");
                } else {
                    var id = $("#selectCourseTable").find("input:checkbox:checked").attr("name");
                    if (confirm("确定要选择这门课程吗？")) {
                        $.post("${ctp}/student/choose", {courseId: id, stuId:${user.id}},
                            function (data) {
                                alert(data);
                                window.location.href = "${ctp}/student/selectList?id=${user.id}";
                            });
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        });

        $("#cancel").click(function () {
            if ($("#myCourseTable").find("input:checkbox:checked").length >= 1) {
                var id = $("#myCourseTable").find("input:checkbox:checked").attr("name");
                if (confirm("确定要删除这门课程吗？")) {
                    $.post("${ctp}/student/cancel", {courseId: id, stuId:${user.id}},
                        function (data) {
                            alert(data);
                            window.location.href = "${ctp}/student/selectList?id=${user.id}";
                        });
                } else {
                    return false;
                }
            } else {
                return false;
            }
        });
    });
</script>
<div class="panel panel-default">
    <div class="panel-heading">已选课程</div>
    <table id="myCourseTable" class="table table-hover  table-bordered table-striped">
        <thead>
        <tr>
            <th>&nbsp;</th>
            <th>序号</th>
            <th>课程名称</th>
            <th>课程类型</th>
            <th>学分</th>
            <th>授课老师</th>
            <th>可选人数</th>
        </tr>
        <c:forEach var="course" items="${map.myCourseList }" varStatus="status">
        <tr class="mcl">
            <td><input type="checkbox" name="${course.id}" value="${course.id}"/></td>
            <td>${status.index+1 }</td>
            <td>${course.courseName }</td>
            <td>${course.courseType }</td>
            <td>${course.credit }</td>
            <td>${course.tea.userName }</td>
            <td>${course.stuNum }/${course.selectNum}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    <button id="cancel" type="button" class="btn btn-danger" style="margin: 5px">退选课程</button>
</div>
<div class="panel panel-default">
    <div class="panel-heading">可选课程</div>
    <table id="selectCourseTable" class="table table-hover  table-bordered table-striped">
        <tr>
            <th>&nbsp;</th>
            <th>序号</th>
            <th>课程名称</th>
            <th>课程类型</th>
            <th>学分</th>
            <th>授课老师</th>
            <th>可选人数</th>
        </tr>
        <c:forEach var="selectCourse" items="${map.selectCourseList.list }" varStatus="status">
            <tr class="scl">
                <td><input type="checkbox" name="${selectCourse.id}" value="${selectCourse.id}"/></td>
                <td>${status.index+1+(map.selectCourseList.pageNum-1)*7}</td>
                <td>${selectCourse.courseName }</td>
                <td>${selectCourse.courseType }</td>
                <td>${selectCourse.credit }</td>
                <td>${selectCourse.tea.userName }</td>
                <td>${selectCourse.stuNum }/${selectCourse.selectNum }</td>
            </tr>
        </c:forEach>
        <tr>
            <td style="margin: 0;border: 0" colspan="7">
                <button type="button" class="btn btn-success" style="margin: 5px;" id="choose">选择课程</button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="${ctp}/student/selectList?id=${user.id}">首页</a>
                <a class="prePage" href="${ctp}/student/selectList?page=${map.selectCourseList.prePage}&id=${user.id}">上一页</a>
                ${map.selectCourseList.pageNum}
                <a class="nextPage"
                   href="${ctp}/student/selectList?page=${map.selectCourseList.nextPage}&id=${user.id}">下一页</a>
                <a href="${ctp}/student/selectList?page=${map.selectCourseList.pages}&id=${user.id}">末页</a>
            </td>
        </tr>
        </tbody>
    </table>

</div>
<script>
    $(function () {
        if (${map.selectCourseList.pageNum} == ${map.selectCourseList.pages}) {
            $(".nextPage").hide();
        }
        if (${map.selectCourseList.pageNum} == 1
    )
        {
            $(".prePage").hide();
        }
    });
</script>





