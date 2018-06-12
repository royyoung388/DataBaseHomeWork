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
        name = "login",
        urlPatterns = "/login"
)
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("login do post");

        req.setCharacterEncoding("UTF-8");
        String user = req.getParameter("user");
        String pwd = req.getParameter("pwd");

        System.out.println("user = " + user + " , pwd = " + pwd);

        try {
            Dao.getInstance().login(user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("login error");
            req.setAttribute("cause", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        resp.setStatus(resp.SC_MOVED_TEMPORARILY);
        resp.setHeader("Location", "tables.jsp");
    }
}
