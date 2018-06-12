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

@WebServlet(
        name = "create",
        urlPatterns = "/create"
)
public class CreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration paramNames = req.getParameterNames();
        String table = req.getParameter("table");
        String[] names = req.getParameterValues("name");
        String[] type = req.getParameterValues("type");
        String[] pk = req.getParameterValues("pk");
        String[] notnull = req.getParameterValues("notnull");
        String[] fk_name = req.getParameterValues("fk_name");
        String[] fk_table = req.getParameterValues("fk_table");
        String[] fk_col = req.getParameterValues("fk_col");

        try {
            Dao.getInstance().createTable(table, names, type, pk, notnull, fk_name, fk_table, fk_col);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("create error");
            req.setAttribute("cause", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        resp.setStatus(resp.SC_MOVED_TEMPORARILY);
        resp.setHeader("Location", "tables.jsp");    }
}
