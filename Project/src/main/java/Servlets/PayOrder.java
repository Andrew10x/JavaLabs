package Servlets;

import letscode.DBQueries;

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
            DBQueries dbq = new DBQueries();
            try {
                int statusId = dbq.getStatusId("Оплачено");
                dbq.changeStatus(Integer.parseInt(mp.get("orderId")[0]), statusId);
                req.setAttribute("success", true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        getServletContext().getRequestDispatcher("/payForm.jsp").forward(req, resp);
    }
}
