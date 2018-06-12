<%--
Created by IntelliJ IDEA.
User: roy
Date: 5/21/18
Time: 3:54 PM
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>登录</title>
    <link type="text/css" rel="stylesheet" href="asserts/css/login.css">
    <link type="text/css" rel="stylesheet" href="asserts/css/normalize.css">
</head>
<body>
<form action="login" method="POST">
    <label>用户名：</label>
    <input type="text" name="user" required>
    <label>密码：</label>
    <input type="password" name="pwd" required>
    <input type="submit" value="登录">
</form>
</body>
</html>
