package DAO;

import model.StatusesModel;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO implements DAOGetList<StatusesModel>, DAOGetBYField<StatusesModel, String> {
    @NotNull
    private final Connection con;

    public StatusDAO(@NotNull Connection con) {
        this.con = con;
    }

    @Override
    public StatusesModel get(String StatusName) {
        StatusesModel sm = new StatusesModel();
        try (PreparedStatement pr = con.prepareStatement(StatusModelSQL.GET.QUERY)) {
            pr.setString(1, StatusName);

            ResultSet res = pr.executeQuery();
            if (res.next()) {
                 sm.setStatusid(res.getInt(1));
                 sm.setStatusname(res.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sm;
    }

    @Override
    public List<StatusesModel> getAll() {
        List<StatusesModel> sml = new ArrayList<>();
        try (PreparedStatement pr = con.prepareStatement(StatusModelSQL.GETALL.QUERY)) {

            ResultSet res = pr.executeQuery();
            while (res.next()) {
                StatusesModel cm = new StatusesModel(res.getInt(1), res.getString(2));
                sml.add(cm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sml;
    }
}

enum StatusModelSQL {
    GET("select * from statuses where statusname = ?"),
    GETALL("select * from statuses");
    final String QUERY;
    StatusModelSQL(String QUERY) { this.QUERY = QUERY;}
}

