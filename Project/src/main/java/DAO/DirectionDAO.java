package DAO;

import model.DirectionModel;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectionDAO implements DAOGetByTwoFields<DirectionModel, Integer, Integer> {

    @NotNull
    private final Connection con;

    public DirectionDAO(@NotNull Connection con) {
        this.con = con;
    }

    @Override
    public DirectionModel getByTwoFields(Integer cityFromId, Integer cityToId) {
        DirectionModel dm = new DirectionModel();
        try (PreparedStatement pr = con.prepareStatement(DirectionModelSQL.GETBYTWOFIELDS.QUERY)) {
            pr.setInt(1, cityFromId);
            pr.setInt(2, cityToId);
            ResultSet res = pr.executeQuery();
            if (res.next()) {
                dm.setDirectionId(res.getInt(1));
                dm.setCityFromId(res.getInt(2));
                dm.setCityToId(res.getInt(3));
                dm.setDistance(res.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dm;
    }
}

enum DirectionModelSQL {
    GETBYTWOFIELDS("select * from Directions where cityfromid = ? and citytoid = ?");
    final String QUERY;
    DirectionModelSQL(String QUERY) { this.QUERY = QUERY;}
}
