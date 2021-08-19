<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
<%
    pageContext.setAttribute("ctp", request.getContextPath());
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>学生选课系统登录</title>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <link rel="shortcut icon" href="/static/favicon.ico">
    <STYLE type=text/css>
        BODY {
            TEXT-ALIGN: center;
            PADDING-BOTTOM: 0px;
            BACKGROUND-COLOR: #ddeef2;
            MARGIN: 0px;
            PADDING-LEFT: 0px;
            PADDING-RIGHT: 0px;
            PADDING-TOP: 0px
        }

        A:link {
            COLOR: #000000;
            TEXT-DECORATION: none
        }

        A:visited {
            COLOR: #000000;
            TEXT-DECORATION: none
        }

        A:hover {
            COLOR: #ff0000;
            TEXT-DECORATION: underline
        }

        A:active {
            TEXT-DECORATION: none
        }

        .input {
            BORDER-BOTTOM: #ccc 1px solid;
            BORDER-LEFT: #ccc 1px solid;
            LINE-HEIGHT: 20px;
            WIDTH: 182px;
            HEIGHT: 20px;
            BORDER-TOP: #ccc 1px solid;
            BORDER-RIGHT: #ccc 1px solid
        }

        .input1 {
            BORDER-BOTTOM: #ccc 1px solid;
            BORDER-LEFT: #ccc 1px solid;
            LINE-HEIGHT: 20px;
            WIDTH: 120px;
            HEIGHT: 20px;
            BORDER-TOP: #ccc 1px solid;
            BORDER-RIGHT: #ccc 1px solid;
        }
    </STYLE>
    <script type=text/javascript>
        $(function () {
            $("#btnLogin").click(function () {
                if ($("#userNo").val() == "") {
                    alert("学号不能为空");
                    return false;
                } else if ($("#password").val() == "") {
                    alert("密码不能为空");
                    return false;
                } else {
                    $("#form").submit();
                }
            });

            $("#btnReset").click(function () {
                $("#userNo").val("");
                $("#password").val("");
                $("#userType").val("");
            });
        });
    </script>
</head>
<body>
<FORM id="form" method="post"
      name="login" action="userLogin">
    <DIV></DIV>
    <TABLE style="MARGIN: auto; WIDTH: 100%; HEIGHT: 100%" border=0
           cellSpacing=0 cellPadding=0>
        <TBODY>
        <TR>
            <TD height=150>&nbsp;</TD>
        </TR>
        <TR style="HEIGHT: 254px">
            <TD>
                <DIV style="MARGIN: 0px auto; WIDTH: 936px"><IMG
                        style="DISPLAY: block" src="/static/body_03.jpg"></DIV>
                <DIV style="BACKGROUND-COLOR: #278296">
                    <DIV style="MARGIN: 0px auto; WIDTH: 936px">
                        <DIV
                                style="BACKGROUND:url(/static/body_05.jpg) no-repeat; HEIGHT: 155px">
                            <DIV
                                    style="TEXT-ALIGN: left; WIDTH: 265px; FLOAT: right; HEIGHT: 125px; _height: 95px">

                                <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
                                    <TBODY>
                                    <TR>
                                        <TD style="HEIGHT: 14px"></TD>
                                    </TR>
                                    <TR>
                                        <TD height="42" style="HEIGHT: 33px"><INPUT type="text" class=input
                                                                                    name="userNo" id="userNo"></TD>
                                    </TR>
                                    <TR>
                                        <TD height="26"><INPUT type="password" class="input" name="password"
                                                               id="password"/></TD>
                                    </TR>
                                    <TR>
                                        <td>
                                            <select id="userType" name="userType" class="input"
                                                    style="margin-top: 10px;height: 24px">
                                                <option value="" selected="selected">--请选择用户类型--</option>
                                                <option value="管理员">管理员</option>
                                                <option value="教师">教师</option>
                                                <option value="学生">学生</option>
                                            </select>
                                        </td>
                                    </TR>
                                    </TBODY>
                                </TABLE>

                            </DIV>
                            <DIV style="HEIGHT: 1px; CLEAR: both"></DIV>
                            <DIV style="WIDTH: 380px; FLOAT: right; CLEAR: both">
                                <TABLE border=0 cellSpacing=0 cellPadding=0 width=300>
                                    <TBODY>

                                    <TR>
                                        <TD width=150 align=right><INPUT
                                                style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px"
                                                id="btnLogin" src="/static/btn1.jpg" type="image" name="btnLogin"></TD>
                                        <TD width=50 align=middle><INPUT
                                                style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px"
                                                id="btnReset" src="/static/btn2.jpg" type="image" name="btnReset"></TD>
                                    </TR>
                                    </TBODY>
                                </TABLE>
                            </DIV>
                        </DIV>
                    </DIV>
                </DIV>
                <DIV style="MARGIN: 0px auto; WIDTH: 936px"><IMG
                        src="/static/body_06.jpg"></DIV>
            </TD>
        </TR>
        <TR style="HEIGHT: 30%">
            <TD>&nbsp;</TD>
        </TR>
        </TBODY>
    </TABLE>
</FORM>
</body>
</html>
