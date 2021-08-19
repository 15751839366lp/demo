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
    
    function show(id){
        window.location.href = "${ctp}/showStudent?id=" + id;
    }
    
    function courseDelete(id) {
        if (confirm("确认要删除这条记录吗？")) {
            $.ajax({
                url: "${ctp}/course/" + id,
                type: "POST",
                data: {_method: "DELETE"},
                success: function (data) {
                    window.location.href = "${ctp}/courseList";
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


    $(function () {

        $("#addCourse").click(function () {
            window.location.href = "${ctp}/course/toAddCourse?teaNo=null";
        });

        /*$(".deleteStu").click(function () {
         $(".deleteForm").submit();
         });*/

        $(".choice").click(function () {
            var context = $(this).text();
            $("#choices").html(context + "<span class='caret'></span>");
        })

        $("#checkCourse").click(function () {
            var check = $("#choices").text();
            var checkContext = $("#checkContext").val();
            if (check == "课程名称") {
                window.location.href = "${ctp}/getCourseByName?name=" + checkContext;
            } else if (check == "课程类型") {
                window.location.href = "${ctp}/getCourseByCourseType?type=" + checkContext;
            } else if (check == "授课教师") {
                window.location.href = "${ctp}/getCourseByTeaName?teaName=" + checkContext;
            } else if (check == "学分") {
                window.location.href = "${ctp}/getCourseByCredit?credit=" + checkContext;
            } else {
                return false;
            }
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
    #checkCourse{
        display: inline;
    }
</style>
<div class="row search" >
    <div class="col-md-6" >
        <div class="input-group" style="width: 600px">
            <div class="btn-group dropdown" id="dropdown">
                <button style="width:130px" id="choices" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    请选择查询方式
                    <span class="caret"></span>
                </button>
                <input type="hidden" name="hidedrop_1" id="hidedrop_1" value="课程名称" />
                <ul class="dropdown-menu">
                    <li><a href="#" class="choice">课程名称</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="#" class="choice">课程类型</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="#" class="choice">授课教师</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="#" class="choice">学分</a></li>
                </ul>
            </div>

            <input type="text" style="width:250px" id="checkContext" class="form-control" name=""  value="" placeholder="请输入要查询的信息...">
            <span class="input-group-btn">
                <button id="checkCourse" class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button>
            </span>
        </div>
    </div>
    <div class="col-md-6"  >
        <button type="button"  style="float: right;" class="btn btn-primary" id="addCourse">添加</button>
    </div>
</div>
<div>
    <table class="table table-hover  table-bordered table-striped" style="margin-bottom: 0px;">
        <tr>
            <th>序号</th>
            <th>课程名称</th>
            <th>课程类型</th>
            <th>学分</th>
            <th>授课教师</th>
            <th>选课人数</th>
            <th>操作</th>
        </tr>
        <c:forEach var="course" items="${map.courseList.list}" varStatus="status">
            <tr class="info">
                <td>${status.index+1+(map.courseList.pageNum-1)*12}</td>
                <td>${course.courseName }</td>
                <td>${course.courseType }</td>
                <td>${course.credit }</td>
                <td>${course.tea.userName }</td>
                <td>${course.stuNum }/${course.selectNum}</td>
                <td>
                    <button type="submit" class="btn btn-success btn-xs" class="deleteCourse" onclick="show(${course.id})" >查看</button>                      
                    <button type="button" class="btn btn-info btn-xs" onclick="javascript:window.location.href = '${ctp}/course/${course.id}'">修改</button>
                    <button type="button" class="btn btn-danger btn-xs" class="deleteCourse" onclick="courseDelete(${course.id})" >删除</button>                      
                </td>
                <!--<form hidden="" action="" method="post">
                        <input type="hidden" name="_method" value="DELETE"/>
                        <input type="submit" value="删除"/>
                </form>-->
            </tr>
        </c:forEach>
        <tr>
            <td style="margin: 0;border: 0;text-align: center" colspan="7">
                <c:if test="${map.method=='name'}">
                    <a href="${ctp}/getCourseByName?name=${map.value}">首页</a>
                    <a class="prePage" href="${ctp}/getCourseByName?page=${map.courseList.prePage}&name=${map.value}">上一页</a>
                    ${map.courseList.pageNum}
                    <a class="nextPage" href="${ctp}/getCourseByName?page=${map.courseList.nextPage}&name=${map.value}">下一页</a>
                    <a href="${ctp}/getCourseByName?page=${map.courseList.pages}&name=${map.value}">末页</a>
                </c:if>
                <c:if test="${map.method=='type'}">
                    <a href="${ctp}/getCourseByCourseType?type=${map.value}">首页</a>
                    <a class="prePage" href="${ctp}/getCourseByCourseType?page=${map.courseList.prePage}&type=${map.value}">上一页</a>
                    ${map.courseList.pageNum}
                    <a class="nextPage" href="${ctp}/getCourseByCourseType?page=${map.courseList.nextPage}&type=${map.value}">下一页</a>
                    <a href="${ctp}/getCourseByCourseType?page=${map.courseList.pages}&type=${map.value}">末页</a>
                </c:if>
                <c:if test="${map.method=='teaName'}">
                    <a href="${ctp}/getcourseByTeaName?teaName=${map.value}">首页</a>
                    <a class="prePage" href="${ctp}/getcourseByTeaName?page=${map.courseList.prePage}&teaName=${map.value}">上一页</a>
                    ${map.courseList.pageNum}
                    <a class="nextPage" href="${ctp}/getcourseByTeaName?page=${map.courseList.nextPage}&teaName=${map.value}">下一页</a>
                    <a href="${ctp}/getcourseByTeaName?page=${map.courseList.pages}&teaName=${map.value}">末页</a>
                </c:if>
                <c:if test="${map.method=='credit'}">
                    <a href="${ctp}/getCourseByCredit?credit=${map.value}">首页</a>
                    <a class="prePage" href="${ctp}/getCourseByCredit?page=${map.courseList.prePage}&credit=${map.value}">上一页</a>
                    ${map.courseList.pageNum}
                    <a class="nextPage" href="${ctp}/getCourseByCredit?page=${map.courseList.nextPage}&credit=${map.value}">下一页</a>
                    <a href="${ctp}/getCourseByCredit?page=${map.courseList.pages}&credit=${map.value}">末页</a>
                </c:if>
                <c:if test="${map.method==null}">
                    <a href="${ctp}/courseList">首页</a>
                    <a class="prePage" href="${ctp}/courseList?page=${map.courseList.prePage}">上一页</a>
                    ${map.courseList.pageNum}
                    <a class="nextPage" href="${ctp}/courseList?page=${map.courseList.nextPage}">下一页</a>
                    <a href="${ctp}/courseList?page=${map.courseList.pages}">末页</a>
                </c:if>
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



