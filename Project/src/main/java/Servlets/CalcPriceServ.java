package Servlets;

import DAO.CityDAO;
import DAO.ConstantsDAO;
import DAO.DirectionDAO;
import DB.DBSingleton;
import model.CityModel;
import model.ConstantsModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/")
public class CalcPriceServ extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CityDAO cd;
        try {
            cd = new CityDAO(DBSingleton.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<CityModel> cities = cd.getAll();
        req.setAttribute("cities", cities);

        req.getRequestDispatcher("/main.jsp").forward(req, resp);

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Float> res = calcPriceAndGetValues(req, resp);

        req.setAttribute("price", (int) (float) res.get(0));
        req.setAttribute("distance", res.get(1));
        req.setAttribute("weight", res.get(2));
        req.setAttribute("evLength", (int) (float) (res.get(3)));
        req.setAttribute("pCost", (int) (float) (res.get(4)));
        req.getRequestDispatcher("/showCalcPrice.jsp").forward(req, resp);
    }

    public List<Float> calcPriceAndGetValues(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String[]> mp = req.getParameterMap();
        ConstantsDAO cd;
        DirectionDAO dd;
        try {
            cd = new ConstantsDAO(DBSingleton.getInstance().getConnection());
            dd = new DirectionDAO(DBSingleton.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        ConstantsModel distance, weight, avLength;
        distance = cd.get("distance");
        weight = cd.get("weight");
        avLength = cd.get("avLength");

        //Вартість доставки = вц0 + к1*(в/в0-1)*вк + мц0 + к2*(м/м0-1)*мк + оц0 + к3*(о/о0-1)*ок
        int k1 = 1, k2 = 1, k3 = 1;
        int v;
        v = dd.getByTwoFields(Integer.parseInt((mp.get("cityFrom"))[0]), Integer.parseInt((mp.get("cityTo"))[0])).getDistance();
        float m = Float.parseFloat((mp.get("weight"))[0]);
        int length = Integer.parseInt((mp.get("length"))[0]);
        int width = Integer.parseInt((mp.get("width"))[0]);
        int height = Integer.parseInt((mp.get("height"))[0]);
        float o = ((float) length + width + height)/3;
        float pCost = Float.parseFloat((mp.get("pCost"))[0]);
        if(v <= distance.getMinValue())
            k1 = 0;
        if(m <= weight.getMinValue())
            k2 = 0;
        if(o <= avLength.getMinValue())
            k3 = 0;

        float price = (distance.getMinPrice() + k1*(v/distance.getMinValue() - 1)*distance.getCoef() +
                weight.getMinPrice() + k2*(m/weight.getMinValue() - 1)* weight.getCoef() + avLength.getMinPrice() +
                k3*(o/ avLength.getMinValue() - 1)* avLength.getCoef());

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        List<Float> l = new ArrayList<>();
        l.add(price);
        l.add((float) v);
        l.add(m);
        l.add(o);
        l.add(pCost);
        return l;

    }
}
