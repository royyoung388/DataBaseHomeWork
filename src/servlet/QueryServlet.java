package servlet;

import beans.Student;
import dao.Dao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet(
        name = "query",
        urlPatterns = "/query"
)
public class QueryServlet extends HttpServlet {

    private Connection conn;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Dao dao = Dao.getInstance();

        ResultSet set = null;
        try {
            set = dao.queryByTableName(req.getParameter("table"));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("query error");
            req.setAttribute("cause", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("set", set);
        req.getRequestDispatcher("query.jsp").forward(req, resp);
    }
}
