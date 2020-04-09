<%@page isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>系统首页</title>
        <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
        <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
        <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        <link rel="shortcut icon" href="/static/favicon.ico">
    </head>
    <body>
        <iframe name="head" id="head" width="100%" height="50px" scrolling="no" frameborder="0" src="common/head.jsp"></iframe><br>
        <iframe name="menu" id="menu" width="20%" height="90%" scrolling="no" src="common/menu.jsp" frameborder="0" ></iframe>
        <iframe name="main" id="main" width="79%" height="90%" scrolling="no" frameborder="0" src="common/default.jsp"></iframe>
    </body>
</html>
