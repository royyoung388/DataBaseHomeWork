<%--
  Created by IntelliJ IDEA.
  User: roy
  Date: 5/28/18
  Time: 2:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>删除数据</title>
    <link type="text/css" rel="stylesheet" href="asserts/css/normalize.css">
</head>
<body>
<p>删除成功!</p>
<P><%=request.getAttribute("table")%>表中<%=request.getAttribute("row")%>条数据被删除</P>
<br>
<input type="button" value="返回" onclick="self.location=document.referrer;">
</body>
</html>
