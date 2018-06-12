package servlet;

import dao.Dao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "delete",
        urlPatterns = "/delete"
)
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String first = req.getParameter("first");
        String name = req.getParameter("name");
        String table = req.getParameter("table");

        Dao dao = Dao.getInstance();
        int row = 0;

        try {
            row = dao.deleteByFirst(table, name, first);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("delete error");
            req.setAttribute("cause", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("row", row);
        req.setAttribute("table", table);
        req.getRequestDispatcher("delete.jsp").forward(req, resp);
    }
}
