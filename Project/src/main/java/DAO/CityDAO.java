package DAO;

import model.CityModel;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAO implements DAOGetList<CityModel> {
    @NotNull
    private final Connection con;

    public CityDAO(@NotNull Connection connection) {
        this.con = connection;
    }

    @Override
    public List<CityModel> getAll() {
        List<CityModel> cml = new ArrayList<>();

        try (PreparedStatement pr = con.prepareStatement(CityModelSQL.GETALL.QUERY)) {
            ResultSet res = pr.executeQuery();
            while (res.next()) {
                CityModel cm = new CityModel(res.getInt(1), res.getString(2));
                cml.add(cm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cml;
    }
}

enum CityModelSQL {
    GETALL("select * from cities");
    final String QUERY;
    CityModelSQL(String QUERY) { this.QUERY = QUERY;}
}
