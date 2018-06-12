<%@ page import="dao.Dao" %><%--
  Created by IntelliJ IDEA.
  User: roy
  Date: 6/4/18
  Time: 3:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>oracle数据库</title>
    <link type="text/css" rel="stylesheet" href="asserts/css/tables.css">
    <link type="text/css" rel="stylesheet" href="asserts/css/normalize.css">
</head>
<body>
<h3>当前用户表：</h3>
<ul class="live">
    <%
        for (String table : Dao.getInstance().listAllTables()) {
    %>
    <li><a href="query?table=<%=table%>"><%=table%>
    </a>
    </li>
    <%
        }
    %>
</ul>

<br>

<input type="button" value="创建新表" onclick="location.href='create.jsp'">

</body>
</html>
