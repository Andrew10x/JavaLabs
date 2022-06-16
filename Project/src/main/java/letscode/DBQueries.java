package letscode;

import DB.DBSingleton;
import model.CityModel;
import model.ConstantsModel;
import model.PasswordAndRoleModel;
import model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            pr.setString(4, user.getPasswordUsr());
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

}


