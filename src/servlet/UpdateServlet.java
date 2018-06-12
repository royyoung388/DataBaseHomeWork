package servlet;

import dao.Dao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(
        name = "update",
        urlPatterns = "/update"
)
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //<a href="update?first=<%=set.getString(1)%>&name=<%=firstName%>&table=<%=table%>">Update</a>

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("update do post");

        req.setCharacterEncoding("UTF-8");

        Enumeration paramNames = req.getParameterNames();
        Map<String, String> paramMap = new HashMap<>();
        String table = null, first = null, firstName = null;

        //获取所有参数
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String param = req.getParameter(paramName);
            System.out.println("paramName: " + paramName + " , param: " + param);

            if (paramName.equals("_table_")) {
                table = param;
            } else if (paramName.equals("_first_name_")) {
                firstName = param;
            } else if (paramName.equals("_first_")) {
                first = param;
            } else {
                paramMap.put(paramName, param);
            }
        }

        int row;
        try {
            row = Dao.getInstance().updateByTableFirst(table, firstName, first, paramMap);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("update error");
            req.setAttribute("cause", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("row", row);
        req.setAttribute("table", table);
        req.getRequestDispatcher("update.jsp").forward(req, resp);
    }
}
