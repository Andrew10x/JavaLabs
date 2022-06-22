package DAO;

import model.CityModel;
import model.ConstantsModel;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConstantsDAO implements DAOGetBYField<ConstantsModel, String> {
    @NotNull
    private final Connection con;

    public ConstantsDAO(@NotNull Connection con) {
        this.con = con;
    }

    @Override
    public ConstantsModel get(String name) {
        ConstantsModel cm = new ConstantsModel();
        try (PreparedStatement pr = con.prepareStatement(ConstantsModelSQL.GET.QUERY)) {
            pr.setString(1, name);
            ResultSet res = pr.executeQuery();
            if (res.next()) {
                cm.setId(res.getInt(1));
                cm.setName(name);
                cm.setMinValue(res.getFloat(3));
                cm.setMinPrice(res.getFloat(4));
                cm.setCoef(res.getFloat(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cm;
    }
}

enum ConstantsModelSQL {
    GET("select * from Constants where Name = ?");
    final String QUERY;
    ConstantsModelSQL(String QUERY) { this.QUERY = QUERY;}
}
