package Servlets;

import DAO.OrderDAO;
import DB.DBSingleton;
import model.OrderModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/ChangeStatus")
public class ChangeStatus extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> mp = req.getParameterMap();
        OrderDAO od;
        try {
            od = new OrderDAO(DBSingleton.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        OrderModel om = new OrderModel();
        om.setId(Integer.parseInt(mp.get("orderId")[0]));
        om.setStatusId(Integer.parseInt(mp.get("statusId")[0]));
        od.update(om);

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
