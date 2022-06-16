package Servlets;

import letscode.DBQueries;
import model.CityModel;
import model.ConstantsModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/")
public class CalcPriceServ extends HttpServlet {
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

        getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Float> res = calcPriceAndGetValues(req, resp);

        req.setAttribute("price", (int) (float) res.get(0));
        req.setAttribute("distance", res.get(1));
        req.setAttribute("weight", res.get(2));
        req.setAttribute("evLength", (int) (float) (res.get(3)));
        req.setAttribute("pCost", (int) (float) (res.get(4)));
        getServletContext().getRequestDispatcher("/showCalcPrice.jsp").forward(req, resp);
    }

    public List<Float> calcPriceAndGetValues(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String[]> mp = req.getParameterMap();
        DBQueries dbq = new DBQueries();
        ConstantsModel distance, weight, avLength;
        try {
            distance = dbq.getConstants("distance");
            weight = dbq.getConstants("weight");
            avLength = dbq.getConstants("avLength");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Вартість доставки = вц0 + к1*(в/в0-1)*вк + мц0 + к2*(м/м0-1)*мк + оц0 + к3*(о/о0-1)*ок
        int k1 = 1, k2 = 1, k3 = 1;
        int v;
        try {
            v = dbq.getDistance(Integer.parseInt((mp.get("cityFrom"))[0]), Integer.parseInt((mp.get("cityTo"))[0]));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        List<Float> l = new ArrayList<>();
        l.add(price);
        l.add((float) v);
        l.add(m);
        l.add(o);
        l.add(pCost);
        return l;
    }
}
