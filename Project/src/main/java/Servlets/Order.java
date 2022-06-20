package Servlets;

import letscode.DBQueries;
import model.OrderJoinedModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@WebServlet("/Order")
public class Order extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> mp = req.getParameterMap();
        int id = Integer.parseInt(mp.get("id")[0]);

        DBQueries dbq = new DBQueries();
        OrderJoinedModel ojm;
        try {
            ojm = dbq.getOrderJoined(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("ojm", ojm);
        if(mp.containsKey("print")) {
            req.setAttribute("print", true);
        }
        getServletContext().getRequestDispatcher("/order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(req.getRequestURI() + "\n");
        String uri = req.getRequestURI();
        Map<String, String[]> mp = req.getParameterMap();
        resp.getWriter().write(mp.get("id")[0]);
    }

    public String getEmail(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (Objects.equals(c.getName(), "UserEmail")) {
                    return c.getValue();
                }
            }
        }
        return "";
    }
}
