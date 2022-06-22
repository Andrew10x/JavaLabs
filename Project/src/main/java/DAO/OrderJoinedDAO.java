package DAO;

import model.OrderJoinedModel;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderJoinedDAO implements DAOGetOrdersListWithFilter<OrderJoinedModel>,
        DAOGetById<OrderJoinedModel, Integer>, DAOGetList<OrderJoinedModel>
{

    @NotNull
    private final Connection con;

    public OrderJoinedDAO(@NotNull Connection con) {
        this.con = con;
    }

    @Override
    public OrderJoinedModel getById(Integer id) {
        OrderJoinedModel ojm = new OrderJoinedModel();
        try (PreparedStatement pr = con.prepareStatement(OrderJoinedModelSQL.GETBYID.QUERY)) {
            pr.setInt(1, id);
            ResultSet res = pr.executeQuery();
            if (res.next()) {
                setOjm(ojm, res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ojm;
    }

    @Override
    public List<OrderJoinedModel> getAllWithFilter(int id, String email, String status, String date, String cityFrom, String cityTo) {
        List<OrderJoinedModel> ojml = new ArrayList<>();
        String s = OrderJoinedModelSQL.GETALLWITHFILTER.QUERY;
        if(id > 0)
            s += " and o.id = '" + id + "' ";

        if(!Objects.equals(email, ""))
            s += " and u.email = '" + email + "' ";

        if(!Objects.equals(status, ""))
            s += " and s.statusname = '" + status + "' ";

        if(!Objects.equals(date, ""))
            s += " and date(o.dateord) = '" + date + "' ";

        if(!Objects.equals(cityFrom, ""))
            s += " and cit.cityname = '" + cityFrom + "' ";

        if(!Objects.equals(cityTo, ""))
            s += " and cit2.cityname = '" + cityTo + "' ";

        try (PreparedStatement pr = con.prepareStatement(s)) {
            ResultSet res = pr.executeQuery();
            while (res.next()) {
                OrderJoinedModel ojm = new OrderJoinedModel();
                setOjm(ojm, res);
                ojml.add(ojm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ojml;
    }

    public void setOjm(OrderJoinedModel ojm, ResultSet res) throws SQLException {
        ojm.setId(res.getInt(1));
        ojm.setDateOrd((res.getString(2)).substring(0, 19));
        ojm.setStatusId(res.getInt(3));
        ojm.setWeightOrd(res.getFloat(4));
        ojm.setLengthOrd(res.getInt(5));

        ojm.setWidthOrd(res.getInt(6));
        ojm.setHeightOrd(res.getInt(7));
        ojm.setSumInsured(res.getFloat(8));
        ojm.setAddress(res.getString(9));

        ojm.setDeliveryCost(res.getFloat(10));
        ojm.setUserName(res.getString(11));
        ojm.setPhone(res.getString(12));
        ojm.setEmail(res.getString(13));
        ojm.setCityFrom(res.getString(14));

        ojm.setCityTo(res.getString(15));
        ojm.setStatusName(res.getString(16));

        ojm.setTypeName(res.getString(17));
        ojm.setRecipientName(res.getString(18));
        ojm.setRecipientPhone(res.getString(19));
    }

    @Override
    public List<OrderJoinedModel> getAll() {
        List<OrderJoinedModel> ojml = new ArrayList<>();
        try (PreparedStatement pr = con.prepareStatement(OrderJoinedModelSQL.GET.QUERY)) {
            ResultSet res = pr.executeQuery();
            while (res.next()) {
                OrderJoinedModel ojm = new OrderJoinedModel();
                setOjm(ojm, res);
                ojml.add(ojm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ojml;
    }
}

enum OrderJoinedModelSQL {

    GET("select o.id, o.dateord, o.statusid, o.weightord, o.lengthord," +
            " o.widthord, o.heightord, o.suminsured, o.adress, \n" +
            "o.deliverycost, u.username, u.phone, u.email, cit.cityname as cityfrom, " +
            "cit2.cityname as cityto, s.statusname,\n" +
            "ct.typename, r.recipientname, r.recipientphone\n" +
            "from Orders o \n" +
            "join Users u on o.userid = u.userid \n" +
            "join Directions d on o.directionid = d.directionid \n" +
            "join Cities cit on d.cityfromid = cit.cityid\n" +
            "join Cities cit2 on d.citytoid = cit2.cityid\n" +
            "join Statuses s on o.statusid = s.statusid\n" +
            "join Cargotypes ct on o.typeid = ct.typeid\n" +
            "join Recipients r on o.recipientid = r.recipientid\n"),
    GETBYID(GET.QUERY + " where o.id = ?"),
    GETALLWITHFILTER(GET.QUERY + " where true ");
    final String QUERY;
    OrderJoinedModelSQL(String QUERY) { this.QUERY = QUERY;}
}

