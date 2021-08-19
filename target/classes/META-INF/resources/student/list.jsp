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
    function studentDelete(id) {
        if (confirm("确认要删除这条记录吗？")) {
            $.ajax({
                url: "${ctp}/stu/" + id,
                type: "POST",
                data: {_method: "DELETE"},
                success: function (data) {
                    window.location.href = "${ctp}/stuList";
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

        $("#addStu").click(function () {
            window.location.href = "${ctp}/student/save.jsp";
        });

        /*$(".deleteStu").click(function () {
         $(".deleteForm").submit();
         });*/

        $(".choice").click(function () {
            var context = $(this).text();
            $("#choices").html(context + "<span class='caret'></span>");
        })

        $("#checkStu").click(function () {
            var check = $("#choices").text();
            var checkContext = $("#checkContext").val();
            if (check == "姓名") {
                window.location.href = "${ctp}/getStuByName?name=" + checkContext;
            } else if (check == "学号") {
                window.location.href = "${ctp}/getStuByStuNo?stuNo=" + checkContext;
            } else if (check == "专业") {
                window.location.href = "${ctp}/getStuByPro?pro=" + checkContext;
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
    #checkStu{
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
                <input type="hidden" name="hidedrop_1" id="hidedrop_1" value="姓名" />
                <ul class="dropdown-menu">
                    <li><a href="#" class="choice">姓名</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="#" class="choice">学号</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="#" class="choice">专业</a></li>
                </ul>
            </div>

            <input type="text" style="width:250px" id="checkContext" class="form-control" name=""  value="" placeholder="请输入要查询的信息...">
            <span class="input-group-btn">
                <button id="checkStu" class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button>
            </span>
        </div>
    </div>
    <div class="col-md-6"  >
        <button type="button"  style="float: right;" class="btn btn-primary" id="addStu">添加</button>
    </div>
</div>
<div>
    <table class="table table-hover  table-bordered table-striped" style="margin-bottom: 0px;">
        <tr>
            <th>序号</th>
            <th>姓名</th>
            <th>密码</th>
            <th>学号</th>
            <th>专业</th>
            <th>操作</th>
        </tr>
        <c:forEach var="stu" items="${map.stuList.list}" varStatus="status">
            <tr class="info">
                <td>${status.index+1+(map.stuList.pageNum-1)*12}</td>
                <td>${stu.userName }</td>
                <td>${stu.password }</td>
                <td>${stu.stuNo }</td>
                <td>${stu.professional }</td>
                <td><button type="button" class="btn btn-info btn-xs" onclick="javascript:window.location.href = '${ctp}/stu/${stu.id}'">修改</button>
                    <button type="button" class="btn btn-danger btn-xs" class="deleteStu" onclick="studentDelete(${stu.id})" >删除</button>                      
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
                    <a href="${ctp}/getStuByName?name=${map.value}">首页</a>
                    <a class="prePage" href="${ctp}/getStuByName?page=${map.stuList.prePage}&name=${map.value}">上一页</a>
                    ${map.stuList.pageNum}
                    <a class="nextPage" href="${ctp}/getStuByName?page=${map.stuList.nextPage}&name=${map.value}">下一页</a>
                    <a href="${ctp}/getStuByName?page=${map.stuList.pages}&name=${map.value}">末页</a>
                </c:if>
                <c:if test="${map.method=='stuNo'}">
                    <a href="${ctp}/getStuByStuNo?stuNo=${map.value}">首页</a>
                    <a class="prePage" href="${ctp}/getStuByStuNo?page=${map.stuList.prePage}&stuNo=${map.value}">上一页</a>
                    ${map.stuList.pageNum}
                    <a class="nextPage" href="${ctp}/getStuByStuNo?page=${map.stuList.nextPage}&stuNo=${map.value}">下一页</a>
                    <a href="${ctp}/getStuByStuNo?page=${map.stuList.pages}&stuNo=${map.value}">末页</a>
                </c:if>
                <c:if test="${map.method=='pro'}">
                    <a href="${ctp}/getStuByPro?pro=${map.value}">首页</a>
                    <a class="prePage" href="${ctp}/getStuByPro?page=${map.stuList.prePage}&pro=${map.value}">上一页</a>
                    ${map.stuList.pageNum}
                    <a class="nextPage" href="${ctp}/getStuByPro?page=${map.stuList.nextPage}&pro=${map.value}">下一页</a>
                    <a href="${ctp}/getStuByPro?page=${map.stuList.pages}&pro=${map.value}">末页</a>
                </c:if>
                <c:if test="${map.method==null}">
                    <a href="${ctp}/stuList">首页</a>
                    <a class="prePage" href="${ctp}/stuList?page=${map.stuList.prePage}">上一页</a>
                    ${map.stuList.pageNum}
                    <a class="nextPage" href="${ctp}/stuList?page=${map.stuList.nextPage}">下一页</a>
                    <a href="${ctp}/stuList?page=${map.stuList.pages}">末页</a>
                </c:if>
            </td>
        </tr>
    </table>
    <script>
        $(function () {
            if (${map.stuList.pageNum} == ${map.stuList.pages}) {
                $(".nextPage").hide();
            }
            if (${map.stuList.pageNum} == 1) {
                $(".prePage").hide();
            }
        });
    </script>

</div>



