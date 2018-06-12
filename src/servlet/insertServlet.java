package servlet;

import dao.Dao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(
        name = "insert",
        urlPatterns = "/insert"
)
public class insertServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("insert do post");

        req.setCharacterEncoding("UTF-8");

        Enumeration paramNames = req.getParameterNames();
        Map<String, String> paramMap = new HashMap<>();
        String table = null, first = null, firstName = null;

        //获取所有参数
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String param = req.getParameterValues(paramName)[0];
            System.out.println("paramName: " + paramName + " , param: " + param);

            if (paramName.equals("_table_")) {
                table = param;
            } else {
                paramMap.put(paramName, param);
            }
        }

        int row;
        try {
            row = Dao.getInstance().insertByTable(table, paramMap);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("insert error");
            req.setAttribute("cause", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("row", row);
        req.setAttribute("table", table);
        req.getRequestDispatcher("insert.jsp").forward(req, resp);
    }
}
