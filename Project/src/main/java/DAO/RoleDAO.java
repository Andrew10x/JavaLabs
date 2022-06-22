package DAO;

import model.RoleModel;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO implements DAOGetBYField<RoleModel, String> {
    @NotNull
    private final Connection con;

    public RoleDAO(@NotNull Connection con) {
        this.con = con;
    }

    @Override
    public RoleModel get(String RoleName) {
        RoleModel rm = new RoleModel();
        try (PreparedStatement pr = con.prepareStatement(RoleModelSQL.GET.QUERY)) {
            pr.setString(1, RoleName);
            ResultSet res = pr.executeQuery();
            if (res.next()) {
                rm.setRoleId(res.getInt(1));
                rm.setRoleName(res.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rm;
    }
}

enum RoleModelSQL {
    GET("select * from roles where rolename = ?");
    final String QUERY;
    RoleModelSQL(String QUERY) { this.QUERY = QUERY;}
}
