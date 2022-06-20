package Servlets;

import letscode.DBQueries;
import model.CityModel;
import model.OrderJoinedModel;
import model.StatusesModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet("/AllOrders")
public class AllOrders extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBQueries dbq = new DBQueries();
        List<OrderJoinedModel> data;
        List<StatusesModel> statuses;
        List<CityModel> cities;
        try {
            data = dbq.getOrdersJoined(0,"", "", "", "", "");
            statuses = dbq.getStatuses();
            cities = dbq.getCities();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sendToAllOrders(data, statuses, cities, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> mp = req.getParameterMap();
        List<OrderJoinedModel> data;
        List<StatusesModel> statuses;
        List<CityModel> cities;
        DBQueries dbq = new DBQueries();
        int orderId = 0;
        if(!Objects.equals(mp.get("orderId")[0], "")) {
            orderId = Integer.parseInt(mp.get("orderId")[0]);
        }
        try {
            data = dbq.getOrdersJoined(orderId, mp.get("email")[0], mp.get("status")[0],
                    mp.get("date")[0], mp.get("cityFrom")[0], mp.get("cityTo")[0]);
            statuses = dbq.getStatuses();
            cities = dbq.getCities();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sendToAllOrders(data, statuses, cities, req, resp);
    }

    public void sendToAllOrders(List<OrderJoinedModel> data, List<StatusesModel> statuses,
    List<CityModel> cities, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("data", data);
        req.setAttribute("statuses", statuses);
        req.setAttribute("cities", cities);
        getServletContext().getRequestDispatcher("/allOrders.jsp").forward(req, resp);
    }
}
