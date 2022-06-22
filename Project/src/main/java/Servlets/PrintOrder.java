package Servlets;

import DAO.OrderJoinedDAO;
import DB.DBSingleton;
import model.OrderJoinedModel;

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
        OrderJoinedDAO ojd;
        try {
            ojd = new OrderJoinedDAO(DBSingleton.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        OrderJoinedModel ojm = ojd.getById(orderId);
        req.setAttribute("orderId", mp.get("orderId")[0]);
        req.setAttribute("userName", ojm.getUserName());
        req.setAttribute("price", (int) ojm.getDeliveryCost());

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        getServletContext().getRequestDispatcher("/printOrder.jsp").forward(req, resp);
    }
}
