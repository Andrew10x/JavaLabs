package letscode;

import DB.DBSingleton;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBQueries {
    private Connection con = null;
    private PreparedStatement pr = null;

    public List<CityModel> getCities() throws SQLException {
        List<CityModel> cml;
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select * from cities");
            ResultSet res = pr.executeQuery();
            cml = new ArrayList<>();
            while (res.next()) {
                CityModel cm = new CityModel(res.getInt(1), res.getString(2));
                cml.add(cm);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return cml;
    }

    public List<StatusesModel> getStatuses() throws SQLException {
        List<StatusesModel> sml;
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select * from statuses");
            ResultSet res = pr.executeQuery();
            sml = new ArrayList<>();
            while (res.next()) {
                StatusesModel cm = new StatusesModel(res.getInt(1), res.getString(2));
                sml.add(cm);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return sml;
    }



    public ConstantsModel getConstants(String name) throws SQLException {
        ConstantsModel cm = new ConstantsModel();
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select * from Constants where Name = ?");
            pr.setString(1, name);
            ResultSet res = pr.executeQuery();
            if (res.next()) {
               cm.setId(res.getInt(1));
               cm.setName(name);
               cm.setMinValue(res.getFloat(3));
               cm.setMinPrice(res.getFloat(4));
               cm.setCoef(res.getFloat(5));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return cm;

    }

    public int getDistance(int cityFrom, int cityTo) throws SQLException {
        int distance = 0;
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select * from Directions where cityfromid = ? and citytoid = ?");
            pr.setInt(1, cityFrom);
            pr.setInt(2, cityTo);
            ResultSet res = pr.executeQuery();
            if (res.next()) {
                distance = res.getInt("Distance");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return distance;
    }

    public boolean addUser(UserModel user) throws SQLException {
        boolean result;
        int RoleId = getRoleId("Registered user");
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("INSERT INTO USERS (UserName, Phone, Email, PasswordUsr, RoleId)\n" +
                    "\tVALUES (?, ?, ?, ?, ?) returning userid");
            pr.setString(1,user.getUserName());
            pr.setString(2,user.getPhone());
            pr.setString(3,user.getEmail());
            pr.setString(4,user.getPasswordUsr());
            pr.setInt(5, RoleId);
            result = pr.executeQuery().next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return result;
    }

    public int getRoleId(String RoleName) throws SQLException {
        int RoleId = 2;
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select roleid from roles where rolename = ?");
            pr.setString(1, RoleName);
            ResultSet res = pr.executeQuery();
            if (res.next()) {
                RoleId = res.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return RoleId;
    }


    public PasswordAndRoleModel getPasswordAndRole(String email) throws SQLException {
        PasswordAndRoleModel prm = new PasswordAndRoleModel();
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select u.passwordusr, r.rolename from users u join roles r on" +
                    " u.roleid = r.roleid where Email = ?");
            pr.setString(1, email);
            ResultSet res = pr.executeQuery();
            if (res.next()) {
                 prm.setPasswordUsr(res.getString(1));
                 prm.setRoleName(res.getString(2));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return prm;
    }

    public int addOrder(OrderModel om) throws SQLException {
        int resultId = 0;
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("INSERT INTO ORDERS (UserId, DirectionId, StatusId, WeightOrd, " +
                    "LengthOrd, WidthOrd, HeightOrd, TypeId, SumInsured, Adress, DeliveryCost, RecipientId) " +
                    "Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id");
            pr.setInt(1, om.getUserId());
            pr.setInt(2, om.getDirectionId());
            pr.setInt(3, om.getStatusId());
            pr.setFloat(4, om.getWeightOrd());
            pr.setInt(5, om.getLengthOrd());
            pr.setInt(6, om.getWidthOrd());
            pr.setInt(7,om.getHeightOrd());
            pr.setInt(8, om.getTypeId());
            pr.setFloat(9, om.getSumInsured());
            pr.setString(10, om.getAdress());
            pr.setFloat(11, om.getDeliveryCost());
            pr.setInt(12, om.getRecipientId());
            ResultSet res = pr.executeQuery();
            if(res.next()) {
                resultId = res.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return resultId;
    }

    public int getUserIdByEmail(String email) throws SQLException {
        int UserId = 0;
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select UserId from Users where Email = ?");
            pr.setString(1, email);
            ResultSet res = pr.executeQuery();
            if (res.next()) {
                UserId = res.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return UserId;
    }

    public int addRecipient(RecipientModel rm) throws SQLException {
        int resultId = 0;
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("INSERT INTO Recipients (RecipientName, RecipientPhone)" +
                    "VALUES (?, ?) returning RecipientId");

            pr.setString(1, rm.getRecipientName());
            pr.setString(2, rm.getRecipientPhone());

            ResultSet res = pr.executeQuery();
            if(res.next()) {
                resultId = res.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return resultId;
    }

    public int getDirectionId(int CityFromId, int CityToId) throws SQLException {
        int DirectionId = 0;
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select directionid from directions where cityFromId=? and cityToId=?");
            pr.setInt(1, CityFromId);
            pr.setInt(2, CityToId);

            ResultSet res = pr.executeQuery();
            if (res.next()) {
                DirectionId = res.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return DirectionId;
    }

    public int getStatusId(String StatusName) throws SQLException {
        int DirectionId = 0;
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select statusid from statuses where statusname = ?");
            pr.setString(1, StatusName);

            ResultSet res = pr.executeQuery();
            if (res.next()) {
                DirectionId = res.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return DirectionId;
    }

    public List<OrderJoinedModel> getOrdersJoined(int id, String email, String status, String date,
                                                  String cityFrom,
                                                  String cityTo) throws SQLException {
        List<OrderJoinedModel> resList = new ArrayList<>();

        String s = "select o.id, o.dateord, o.statusid, o.weightord, o.lengthord," +
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
                "join Recipients r on o.recipientid = r.recipientid\n" +
                "where true ";
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

        System.out.println(s);
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement(s);
            ResultSet res = pr.executeQuery();
            while (res.next()) {
                OrderJoinedModel ojm = new OrderJoinedModel();
                setOjm(ojm, res);
                resList.add(ojm);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return resList;
    }

    public OrderJoinedModel getOrderJoined(int id) throws SQLException {
        OrderJoinedModel ojm = new OrderJoinedModel();
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select o.id, o.dateord, o.statusid, o.weightord, o.lengthord," +
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
                    "join Recipients r on o.recipientid = r.recipientid\n" +
                    "where o.id = ?");
            pr.setInt(1, id);
            ResultSet res = pr.executeQuery();
            if (res.next()) {
                setOjm(ojm, res);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return ojm;
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

    public boolean changeStatus(int orderId, int statusId) throws SQLException {
        boolean result;
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("UPDATE orders Set statusid = ? WHERE id = ? RETURNING id");
            pr.setInt(1, statusId);
            pr.setInt(2, orderId);
            result = pr.executeQuery().next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return result;
    }

    public String getUserNameByOrderId(int orderId) throws SQLException {
        String UserName = "";
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select u.username from Orders o join Users u on o.userid = u.userid where o.id = ?");
            pr.setInt(1, orderId);

            ResultSet res = pr.executeQuery();
            if (res.next()) {
                 UserName = res.getString(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (pr != null)
                pr.close();
            if (con != null)
                con.close();
        }
        return UserName;
    }

}


