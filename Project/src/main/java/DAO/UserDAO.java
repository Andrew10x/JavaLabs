package DAO;

import model.UserModel;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAOGetBYField<UserModel, String>, DAOAdd<UserModel> {
    @NotNull
    private final Connection con;

    public UserDAO(@NotNull Connection con) {
        this.con = con;
    }

    @Override
    public UserModel get(String email) {
        UserModel um = new UserModel();
        try (PreparedStatement pr = con.prepareStatement(UserModelSQL.GET.QUERY)) {
            pr.setString(1, email);
            ResultSet res = pr.executeQuery();
            if (res.next()) {
                um.setUserId(res.getInt(1));
                um.setUserName(res.getString(2));
                um.setPhone(res.getString(3));
                um.setEmail(res.getString(4));
                um.setPasswordUsr(res.getString(5));
                um.setRoleId(res.getInt(6));
                um.setRoleName(res.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return um;
    }

    @Override
    public int add(UserModel um) {
        int res = 0;
        try (PreparedStatement pr = con.prepareStatement(UserModelSQL.ADD.QUERY)) {
            pr.setString(1,um.getUserName());
            pr.setString(2,um.getPhone());
            pr.setString(3,um.getEmail());
            pr.setString(4,um.getPasswordUsr());
            pr.setInt(5, um.getRoleId());
            ResultSet resSet = pr.executeQuery();
            if(resSet.next()) {
                res = resSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}

enum UserModelSQL {
    GET("select u.userid, u.username, u.phone, u.email, u.passwordusr, r.roleid, r.rolename" +
            " from users u join roles r on u.roleid = r.roleid where Email = ?"),
    ADD("INSERT INTO USERS (UserName, Phone, Email, PasswordUsr, RoleId)\n" +
            "\tVALUES (?, ?, ?, ?, ?) returning userid");
    final String QUERY;
    UserModelSQL(String QUERY) { this.QUERY = QUERY;}
}
