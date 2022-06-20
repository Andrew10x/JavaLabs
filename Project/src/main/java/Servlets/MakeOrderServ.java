package Servlets;

import letscode.DBQueries;
import model.CityModel;
import model.OrderModel;
import model.RecipientModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet("/MakeOrder")
public class MakeOrderServ extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DBQueries dbq = new DBQueries();
        List<CityModel> cities = null;
        try {
            cities = dbq.getCities();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("cities", cities);

        getServletContext().getRequestDispatcher("/makeOrder.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> mp = req.getParameterMap();
        CalcPriceServ cps = new CalcPriceServ();
        List<Float> res = cps.calcPriceAndGetValues(req, resp);
        Cookie[] cookies = req.getCookies();
        String UserEmail = "";
        for(Cookie c: cookies) {
            if(Objects.equals(c.getName(), "UserEmail"))
                UserEmail = c.getValue();
        }
        if(Objects.equals(UserEmail, "")) {
            resp.getWriter().write("Ви не авторизовані");
            return;
        }

        DBQueries dbq = new DBQueries();
        OrderModel om = new OrderModel();
        try {
            om.setUserId(dbq.getUserIdByEmail(UserEmail));
            om.setDirectionId(dbq.getDirectionId(Integer.parseInt(mp.get("cityFrom")[0]),
                    Integer.parseInt(mp.get("cityTo")[0])));
            om.setStatusId(dbq.getStatusId("Створено"));
            om.setWeightOrd(res.get(2));
            om.setLengthOrd(Integer.parseInt(mp.get("length")[0]));
            om.setHeightOrd(Integer.parseInt(mp.get("height")[0]));
            om.setWidthOrd(Integer.parseInt(mp.get("width")[0]));
            om.setTypeId(Integer.parseInt(mp.get("pType")[0]));
            om.setSumInsured(Float.parseFloat(mp.get("pCost")[0]));
            om.setAdress(mp.get("addressRec")[0]);
            om.setDeliveryCost((int) (float) res.get(0));

            RecipientModel rm = new RecipientModel();
            rm.setRecipientName(mp.get("pibRec")[0]);
            rm.setRecipientPhone(mp.get("telRec")[0]);
            int recId = dbq.addRecipient(rm);
            om.setRecipientId(recId);

            int resId = dbq.addOrder(om);
            req.setAttribute("id", resId);
            getServletContext().getRequestDispatcher("/makeOrder/makeOrderSuccess.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
