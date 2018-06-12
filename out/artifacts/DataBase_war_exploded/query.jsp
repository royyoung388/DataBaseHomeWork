<%@ page import="dao.Dao" %>
<%@ page import="beans.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.ResultSetMetaData" %>
<%@ page import="java.sql.Types" %>
<%--
  Created by IntelliJ IDEA.
  User: roy
  Date: 5/27/18
  Time: 3:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询结果</title>
    <script src="asserts/js/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="asserts/css/normalize.css">
    <link type="text/css" rel="stylesheet" href="asserts/css/query.css">

</head>
<body>
<%
    ResultSet set = (ResultSet) request.getAttribute("set");
    set.next();
    ResultSetMetaData metaData = set.getMetaData();
    set.previous();
    int count = metaData.getColumnCount();
    String firstName = metaData.getColumnName(1).toLowerCase();
    String table = request.getParameter("table");
%>

<script type="text/javascript">
    //更新数据
    function update_record(obj) {
        //创建表单
        // 创建一个 form
        var form = document.createElement("form");
        form.id = "update_form";
        // form.name = "form_name";
        form.method = "POST";
        form.action = "update";
        document.body.appendChild(form);

        //添加第一列的原始数据
        var input = document.createElement("input");
        input.type = "text";
        input.name = "_first_name_";
        input.value = "<%=firstName%>";
        form.appendChild(input);

        var input = document.createElement("input");
        input.type = "text";
        input.name = "_first_";
        input.value = $(obj).data("first");
        form.appendChild(input);

        //添加表名
        var input = document.createElement("input");
        input.type = "text";
        input.name = "_table_";
        input.value = "<%=table%>";
        form.appendChild(input);

        //从向前的两个开始
        var e = $(obj).parent().prev().prev();
        <%
        for (int i = count; i >= 1 ; i--) {%>

        var input = document.createElement("input");
        input.type = "text";
        input.name = "<%=metaData.getColumnName(i)%>";
        input.value = $.trim(e.text());
        form.appendChild(input);

        e = e.prev();

        <%
        }
        %>

        // 执行提交
        form.submit();
        // 删除该 form
        document.body.removeChild(form);
    }
</script>

<table class="bordered">
    <thead>
    <tr>
        <%
            for (int i = 1; i <= count; i++) {
        %>
        <th><%=metaData.getColumnName(i)%>
        </th>
        <%
            }
        %>
        <th>Delete</th>
        <th>Update</th>
    </tr>
    </thead>
    <tbody>
    <%
        while (set.next()) {
    %>
    <tr>
        <%
            for (int i = 1; i <= count; i++) {
        %>
        <td contentEditable="true"><%=set.getString(i) %>
        </td>
        <%
            }
        %>
        <td>
            <a href="delete?first=<%=set.getString(1)%>&name=<%=firstName%>&table=<%=table%>">Delete</a>
        </td>
        <td>
            <a data-first="<%=set.getString(1)%>" onclick="update_record(this)" href="javascript:void(0);">Update</a>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<br>
<br>
<br>

<input type="button" value="返回" onclick="self.location=document.referrer;"></body>

<br>
<br>

<form action="insert" method="POST">
    <input type="hidden" name="_table_" value="<%=table%>">
    <%
        for (int i = 1; i <= count; i++) {
    %>
    <%=metaData.getColumnName(i)%>:<input type="text" name="<%=metaData.getColumnName(i)%>">
    <br>
    <%
        }
    %>
    <input type="submit" value="添加">
</form>

</html>
