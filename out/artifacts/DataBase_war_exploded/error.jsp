<%--
  Created by IntelliJ IDEA.
  User: roy
  Date: 5/28/18
  Time: 5:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误</title>
</head>
<body>
<%
    String cause = (String) request.getAttribute("cause");
%>
<p>错误原因：<%=cause%></p>
<br>
<input type="button" value="返回" onclick="self.location=document.referrer;"></body>
</html>
