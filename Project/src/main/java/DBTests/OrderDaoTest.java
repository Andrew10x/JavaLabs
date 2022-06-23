package DBTests;

import DAO.DirectionDAO;
import DAO.OrderDAO;
import DB.DBSingleton;
import model.OrderModel;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderDaoTest {
    @NotNull
    private OrderDAO od;

    @NotNull
    private Connection connection;

    @Test
    public void addTest() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        od = new OrderDAO(connection);
        OrderModel om = new OrderModel(0, 56, 111,
                "2022-06-17 13:48:28.0", 1, 12.0f,
                12, 14, 40, 1, 1,
                120.0f, "dsf", 133.0f);
        int res = od.add(om);
        Assert.assertNotEquals(res, 0);
        connection.close();
    }

    @Test
    public void updateTest() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        od = new OrderDAO(connection);
        OrderModel om = new OrderModel();
        om.setId(16);
        om.setStatusId(3);
        int res = od.update(om);
        Assert.assertNotEquals(res, 0);
        connection.close();
    }

}
