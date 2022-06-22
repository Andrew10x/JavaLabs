package DAO;

import model.RecipientModel;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipientDAO implements DAOAdd<RecipientModel> {

    @NotNull
    private final Connection con;

    public RecipientDAO(@NotNull Connection con) {
        this.con = con;
    }

    @Override
    public int add(RecipientModel rm) {
        int res = 0;
        try (PreparedStatement pr = con.prepareStatement(RecipientModelSQL.ADD.QUERY)) {
            pr.setString(1, rm.getRecipientName());
            pr.setString(2, rm.getRecipientPhone());

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

enum RecipientModelSQL {
    ADD("INSERT INTO Recipients (RecipientName, RecipientPhone)" +
            "VALUES (?, ?) returning RecipientId");
    final String QUERY;
    RecipientModelSQL(String QUERY) { this.QUERY = QUERY;}
}

