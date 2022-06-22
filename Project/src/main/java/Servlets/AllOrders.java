package Servlets;

import DAO.CityDAO;
import DAO.OrderJoinedDAO;
import DAO.StatusDAO;
import DB.DBSingleton;
import model.CityModel;
import model.OrderJoinedModel;
import model.StatusesModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet("/AllOrders")
public class AllOrders extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderJoinedDAO ojd;
        StatusDAO sd;
        CityDAO cd;
        try {
            Connection connection = DBSingleton.getInstance().getConnection();
            ojd = new OrderJoinedDAO(connection);
            sd = new StatusDAO(connection);
            cd = new CityDAO(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<OrderJoinedModel> data;
        List<StatusesModel> statuses;
        List<CityModel> cities;
        data = ojd.getAll();
        statuses = sd.getAll();
        cities = cd.getAll();

        sendToAllOrders(data, statuses, cities, req, resp);

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatusDAO sd;
        OrderJoinedDAO ojd;
        CityDAO cd;
        try {
            Connection connection = DBSingleton.getInstance().getConnection();
            sd = new StatusDAO(connection);
            ojd = new OrderJoinedDAO(connection);
            cd = new CityDAO(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Map<String, String[]> mp = req.getParameterMap();
        List<OrderJoinedModel> data;
        List<StatusesModel> statuses;
        List<CityModel> cities;

        int orderId = 0;
        if(!Objects.equals(mp.get("orderId")[0], "")) {
            orderId = Integer.parseInt(mp.get("orderId")[0]);
        }
        data = ojd.getAllWithFilter(orderId, mp.get("email")[0], mp.get("status")[0],
                mp.get("date")[0], mp.get("cityFrom")[0], mp.get("cityTo")[0]);
        statuses = sd.getAll();
        cities = cd.getAll();
        sendToAllOrders(data, statuses, cities, req, resp);

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendToAllOrders(List<OrderJoinedModel> data, List<StatusesModel> statuses,
    List<CityModel> cities, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("data", data);
        req.setAttribute("statuses", statuses);
        req.setAttribute("cities", cities);
        getServletContext().getRequestDispatcher("/allOrders.jsp").forward(req, resp);
    }
}
