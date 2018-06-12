<%@ page import="dao.Dao" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: roy
  Date: 6/8/18
  Time: 2:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>创建表</title>

    <link type="text/css" rel="stylesheet" href="asserts/css/normalize.css">
    <script src="asserts/js/jquery.min.js"></script>
    <script>
        var dic = new Array();

        <%
        for (String table : Dao.getInstance().listAllTables()) {
        %>

        dic["<%=table%>"] = [
            <%
            List<String> cols = Dao.getInstance().listAllColumnsByTable(table);
            for(int i = 0; i < cols.size(); i++) {
            %>

            "<%=cols.get(i)%>"

            <%
            if (i != cols.size() - 1) {%>

            ,

            <%
            }
            }
            %>
        ]
        <%
        }
        %>

        $(document).ready(function () {
            $('#add_col').click(function () {
                console.log(("add_col"));
                $(".column:last").after($(".column:last").clone());
            });
            $("#add_fk").click(function () {
                console.log("add_fk");
                $(".fk:last").after($(".fk:last").clone());
            });

            $("form").submit(function(e){
                $("input[type=checkbox]").prop('checked', true);
            });
        });

        //当参照表选择时
        function selectTableClick(obj) {
            console.log(("selectTableChanged"));
            var select = $(obj).parent().children("#sel_fk_col");
            select.empty();
            for (var value in dic[$(obj).val()]) {
                select.append("<option value='" + dic[$(obj).val()][value] + "'>" + dic[$(obj).val()][value] + "</option>");
            }

            var input = $(obj).parent().children("#fk_table");
            input.val($(obj).val());
        }

        //当参照列选择时
        function selectColClick(obj) {
            console.log(("selectColClick"));

            var input = $(obj).parent().children("#fk_col");
            input.val($(obj).val());
        }

        //复选框改变时
        function checkChange(obj) {
            if ($(obj).prop('checked')) {
                $(obj).val("true");
            } else {
                $(obj).val("false");
            }
        }
    </script>
</head>
<body>
<form id="create" method="post" action="create">
    <label>表名：</label>
    <input id="table_name" name="table">

    <div class="column">
        <label>列名：</label>
        <input class="column_name" name="name">
        <label>类型:</label>
        <input id="type" name="type">
        <input type="checkbox" name="pk" value="false" onchange="checkChange(this)">主键
        <input type="checkbox" name="notnull" value="true" checked="checked" onchange="checkChange(this)">非空
    </div>

    <br>

    <button type="button" id="add_col">添加列</button>

    <br>
    <br>

    <label>外键:</label>
    <div class="fk">
        <label>列名：</label>
        <input class="fk_name" name="fk_name">

        <label>参照表：</label>
        <input id="fk_table" type="hidden" name="fk_table">
        <select id="sel_fk_table" onchange="selectTableClick(this)">
            <%
                for (String table : Dao.getInstance().listAllTables()) {
            %>
            <option value="<%=table%>"><%=table%>
            </option>
            <%
                }
            %>
        </select>

        <label>参照列：</label>
        <input id="fk_col" type="hidden" name="fk_col">
        <select id="sel_fk_col" onclick="selectColClick(this)"></select>
    </div>


    <br>

    <button type="button" id="add_fk">添加外键</button>

    <br>
    <br>
    <br>

    <input type="submit" value="创建">

</form>


</body>
</html>
