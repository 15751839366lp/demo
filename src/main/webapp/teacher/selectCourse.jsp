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
<script type="text/javascript">
    function courseInput(id) {
        if (confirm("确认要录入这门课程吗？")) {
            $.ajax({
                url: "${ctp}/teacher/courseInput/" + id,
                type: "POST",
                data: {teaId: "${user.id}"},
                success: function (data) {
                    alert("录入成功");
                    window.location.href = "${ctp}/teacher/selectCourseList?id=${user.id}";
                    //window.location.assign("${ctp}/teaList");
                    //$(window).attr("location","${ctp}/teaList");
                    /*var result = eval('(' + result + ')');
                     if (result.success) {
                     alert("删除成功!");
                     window.location.href = "
                    ${pageContext.request.contextPath}/teacher?action=list";
                                         } else {
                                         alert("改学生有选课记录，不能删除！");
                                         }*/
                }
            });
        } else {
            return false;
        }
    }


    function courseDelete(id) {
        if (confirm("确认要删除这条记录吗？")) {
            $.ajax({
                url: "${ctp}/teacher/selectCourse/" + id,
                type: "POST",
                data: {_method: "DELETE", teaId: "${user.id}"},
                success: function (data) {
                    window.location.href = "${ctp}/teacher/selectCourseList?id=${user.id}";
                    //window.location.assign("${ctp}/teaList");
                    //$(window).attr("location","${ctp}/teaList");
                    /*var result = eval('(' + result + ')');
                     if (result.success) {
                     alert("删除成功!");
                     window.location.href = "
                    ${pageContext.request.contextPath}/teacher?action=list";
                                                             } else {
                                                             alert("改学生有选课记录，不能删除！");
                                                             }*/
                }
            });
        } else {
            return false;
        }
    }


    $(function () {

        $("#addCourse").click(function () {
            window.location.href = "${ctp}/teacher/course/toAddSelectCourse?teaNo=${user.teaNo}";
        });
        /*$(".deleteStu").click(function () {
         $(".deleteForm").submit();
         });*/


    });</script>
<style>
    .row search {
        margin: 0;
        padding: 0;
    }

    .deleteForm {
        height: 20px;
        width: 20px;
        margin: 0;
        padding: 0;
        display: inline;
    }

    .input-group-btn {
        display: inline;
    }

    #checkCourse {
        display: inline;
    }
</style>
<div class="row search" style="align-self: right;">
    <div class="col-md-7" style="float: right;">
        <button type="button" style="float: right;" class="btn btn-primary" id="addCourse">添加</button>
    </div>
</div>
<div>
    <table class="table table-hover  table-bordered table-striped" style="margin-bottom: 0px;">
        <tr>
            <th>序号</th>
            <th>课程名称</th>
            <th>课程类型</th>
            <th>学分</th>
            <th>选课人数</th>
            <th>操作</th>
        </tr>
        <c:forEach var="course" items="${map.courseList.list}" varStatus="status">
            <tr class="info">
                <td>${status.index+1+(map.courseList.pageNum-1)*12}</td>
                <td>${course.courseName }</td>
                <td>${course.courseType }</td>
                <td>${course.credit }</td>
                <td>${course.stuNum }/${course.selectNum}</td>
                <td>
                    <button type="button" class="btn btn-success btn-xs" class="deleteCourse"
                            onclick="courseInput(${course.id})">录入课程
                    </button>
                    <button type="button" class="btn btn-info btn-xs"
                            onclick="javascript:window.location.href = '${ctp}/teacher/selectCourse/${course.id}'">修改
                    </button>
                    <button type="button" class="btn btn-danger btn-xs" class="deleteCourse"
                            onclick="courseDelete(${course.id})">删除
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
                <a href="${ctp}/teacher/selectCourseList?id=${user.id}">首页</a>
                <a class="prePage" href="${ctp}/teacher/selectCourseList?page=${map.courseList.prePage}&id=${user.id}">上一页</a>
                ${map.courseList.pageNum}
                <a class="nextPage"
                   href="${ctp}/teacher/selectCourseList?page=${map.courseList.nextPage}&id=${user.id}">下一页</a>
                <a href="${ctp}/teacher/selectCourseList?page=${map.courseList.pages}&id=${user.id}">末页</a>
            </td>
        </tr>
    </table>
    <script>
        $(function () {
            if (${map.courseList.pageNum} == ${map.courseList.pages}) {
                $(".nextPage").hide();
            }
            if (${map.courseList.pageNum} == 1
        )
            {
                $(".prePage").hide();
            }
        });
    </script>

</div>