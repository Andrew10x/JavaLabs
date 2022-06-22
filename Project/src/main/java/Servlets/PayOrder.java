package Servlets;

import DAO.OrderDAO;
import DAO.StatusDAO;
import DB.DBSingleton;
import model.OrderModel;
import model.StatusesModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/PayOrder")
public class PayOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> mp = req.getParameterMap();
        req.setAttribute("orderId", mp.get("orderId")[0]);
        req.setAttribute("success", false);
        if(mp.get("year") != null) {
            StatusDAO sd;
            OrderDAO od;
            try {
                sd = new StatusDAO(DBSingleton.getInstance().getConnection());
                od = new OrderDAO(DBSingleton.getInstance().getConnection());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            StatusesModel sm = sd.get("Оплачено");
            OrderModel om = new OrderModel();
            om.setStatusId(sm.getStatusid());
            om.setId(Integer.parseInt(mp.get("orderId")[0]));
            od.update(om);
            req.setAttribute("success", true);
        }
        getServletContext().getRequestDispatcher("/payForm.jsp").forward(req, resp);

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
