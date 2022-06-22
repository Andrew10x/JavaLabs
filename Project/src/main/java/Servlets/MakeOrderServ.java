package Servlets;

import DAO.*;
import DB.DBSingleton;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet("/MakeOrder")
public class MakeOrderServ extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CityDAO cd = null;
        try {
            cd = new CityDAO(DBSingleton.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<CityModel> cities = cd.getAll();
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
            resp.getWriter().write("Please singup or singin");
            return;
        }

        UserDAO ud;
        DirectionDAO dd;
        StatusDAO sd;
        RecipientDAO rd;
        OrderDAO od;
        try {
            Connection con = DBSingleton.getInstance().getConnection();
            ud = new UserDAO(con);
            dd = new DirectionDAO(con);
            sd = new StatusDAO(con);
            rd = new RecipientDAO(con);
            od = new OrderDAO(con);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        OrderModel om = new OrderModel();
        om.setUserId(ud.get(UserEmail).getUserId());
        om.setDirectionId(dd.getByTwoFields( Integer.parseInt(mp.get("cityFrom")[0]),
                Integer.parseInt(mp.get("cityTo")[0])).getDirectionId());
        om.setStatusId(sd.get("Створено").getStatusid());
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
        int recId = rd.add(rm);
        om.setRecipientId(recId);

        int resId = od.add(om);
        req.setAttribute("id", resId);
        getServletContext().getRequestDispatcher("/makeOrder/makeOrderSuccess.jsp").forward(req, resp);

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
