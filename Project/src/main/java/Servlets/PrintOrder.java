package Servlets;

import letscode.DBQueries;
import model.OrderJoinedModel;
import model.OrderModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/PrintOrder")
public class PrintOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> mp = req.getParameterMap();
        int orderId = Integer.parseInt(mp.get("orderId")[0]);
        DBQueries dbq = new DBQueries();
        OrderJoinedModel ojm;
        try {
            ojm = dbq.getOrderJoined(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("orderId", mp.get("orderId")[0]);
        req.setAttribute("userName", ojm.getUserName());
        req.setAttribute("price", (int) ojm.getDeliveryCost());
        getServletContext().getRequestDispatcher("/printOrder.jsp").forward(req, resp);
    }
}
