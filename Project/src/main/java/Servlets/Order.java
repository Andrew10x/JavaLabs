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

@WebServlet("/Order")
public class Order extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> mp = req.getParameterMap();
        int id = Integer.parseInt(mp.get("id")[0]);

        OrderJoinedDAO ojd;
        try {
            ojd = new OrderJoinedDAO(DBSingleton.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        OrderJoinedModel ojm = ojd.getById(id);

        req.setAttribute("ojm", ojm);
        if(mp.containsKey("print")) {
            req.setAttribute("print", true);
        }
        getServletContext().getRequestDispatcher("/order.jsp").forward(req, resp);

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
