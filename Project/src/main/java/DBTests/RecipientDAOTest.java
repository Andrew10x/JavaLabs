package DBTests;

import DAO.OrderJoinedDAO;
import DAO.RecipientDAO;
import DB.DBSingleton;
import model.RecipientModel;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;
import org.junit.Assert;

import java.sql.Connection;
import java.sql.SQLException;

public class RecipientDAOTest {
    @NotNull
    private RecipientDAO rd;

    @NotNull
    private Connection connection;

    @Test
    public void addRecipientTest() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        rd = new RecipientDAO(connection);
        RecipientModel rm = new RecipientModel();
        rm.setRecipientPhone("+380991111111");
        rm.setRecipientName("Дмитро Вітер Васильович");
        int res = rd.add(rm);
        Assert.assertNotEquals(res, 0);
        connection.close();
    }
}
