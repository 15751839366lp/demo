<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <link rel="shortcut icon" href="/static/favicon.ico">
    <script>
        $(function(){
            $("#a").click(function () {
                $.ajax({
                    url: "test01",
                    type: "get",
                    data: {a:"a",b:"b"},
                    success: function (data) {
                        alert(data);
                    }
                });
            });
        })
    </script>
</head>

<body>
<button id="a" type="submit"></button>
</body>
</html>
