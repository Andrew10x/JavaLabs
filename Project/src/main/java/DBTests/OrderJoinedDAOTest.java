package DBTests;

import DAO.OrderDAO;
import DAO.OrderJoinedDAO;
import DB.DBSingleton;
import model.OrderJoinedModel;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderJoinedDAOTest {
    @NotNull
    private OrderJoinedDAO ojd;

    @NotNull
    private Connection connection;

    @Test
    public void getByIdTest() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        ojd = new OrderJoinedDAO(connection);
        OrderJoinedModel ojm = ojd.getById(12);
        Assert.assertEquals(ojm.getAddress(), "ул. Клавдиевская 23/15");
        Assert.assertEquals(ojm.getRecipientName(), "Передерій Віталій Володимирович");
        connection.close();
    }

    @Test
    public void getAllWithFilterTest() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        ojd = new OrderJoinedDAO(connection);
        List<OrderJoinedModel> ojml = ojd.getAllWithFilter(0, "svt.andrey1631@gmail.com", "",
                "", "", "");
        OrderJoinedModel ojm = ojml.get(0);
        Assert.assertEquals(ojm.getRecipientName(), "Передерій Віталій Володимирович");
        Assert.assertEquals(ojm.getCityFrom(), "Київ");
        connection.close();
    }

    @Test
    public void getAllTest() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        ojd = new OrderJoinedDAO(connection);
        List<OrderJoinedModel> ojml = ojd.getAll();
        OrderJoinedModel ojm = ojml.get(0);
        Assert.assertNotEquals(ojm.getId(), 0);
        Assert.assertNotNull(ojm.getUserName());
        connection.close();
    }
}
