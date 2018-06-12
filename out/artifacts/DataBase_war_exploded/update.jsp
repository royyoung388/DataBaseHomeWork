<%--
  Created by IntelliJ IDEA.
  User: roy
  Date: 6/3/18
  Time: 4:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>更新数据</title>
    <link type="text/css" rel="stylesheet" href="asserts/css/normalize.css">
</head>
<body>
<p>更新成功!</p>
<P><%=request.getAttribute("table")%>表中<%=request.getAttribute("row")%>条数据被更新</P>
<br>
<input type="button" value="返回" onclick="self.location=document.referrer;">
</body>
</html>