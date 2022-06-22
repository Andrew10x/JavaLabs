package DAO;

import model.OrderModel;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO implements DAOAdd<OrderModel>, DAOUpdate<OrderModel> {

    @NotNull
    private final Connection con;

    public OrderDAO(@NotNull Connection con) {
        this.con = con;
    }

    @Override
    public int add(OrderModel om) {
        int res = 0;
        try (PreparedStatement pr = con.prepareStatement(OrderModelSQL.ADD.QUERY)) {
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
            ResultSet resSet = pr.executeQuery();
            if(resSet.next()) {
                res = resSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public int update(OrderModel om) {
        int res = 0;
        try (PreparedStatement pr = con.prepareStatement(OrderModelSQL.UPDATE.QUERY)) {
            pr.setInt(1, om.getStatusId());
            pr.setInt(2, om.getId());
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

enum OrderModelSQL {
    ADD("INSERT INTO ORDERS (UserId, DirectionId, StatusId, WeightOrd, " +
            "LengthOrd, WidthOrd, HeightOrd, TypeId, SumInsured, Adress, DeliveryCost, RecipientId) " +
            "Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id"),
    UPDATE("UPDATE orders Set statusid = ? WHERE id = ? RETURNING id");
    final String QUERY;
    OrderModelSQL(String QUERY) { this.QUERY = QUERY;}
}
