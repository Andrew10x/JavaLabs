package Servlets;

import DAO.OrderJoinedDAO;
import DB.DBSingleton;
import model.OrderJoinedModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@WebServlet("/MyOrders")
public class MyOrders extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderJoinedDAO ojd;
        try {
            ojd = new OrderJoinedDAO(DBSingleton.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String email = getEmail(req);
        List<OrderJoinedModel> data;
        data = ojd.getAllWithFilter(0, email, "", "", "", "");

        req.setAttribute("data", data);
        getServletContext().getRequestDispatcher("/myOrders.jsp").forward(req, resp);

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
