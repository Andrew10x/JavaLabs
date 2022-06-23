package DBTests;

import DAO.RoleDAO;
import DAO.StatusDAO;
import DB.DBSingleton;
import model.StatusesModel;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StatusDAOTest {
    @NotNull
    private StatusDAO sd;

    @NotNull
    private Connection connection;

    @Test
    public void getTest() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        sd = new StatusDAO(connection);
        StatusesModel sm = sd.get("Доставлено");
        Assert.assertNotEquals(sm.getStatusid(), 0);
        Assert.assertEquals(sm.getStatusname(), "Доставлено");
        connection.close();
    }

    @Test
    public void getAllTest() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        sd = new StatusDAO(connection);
        List<StatusesModel> sml = sd.getAll();
        StatusesModel sm = sml.get(0);
        Assert.assertNotEquals(sm.getStatusid(), 0);
        Assert.assertNotNull(sm.getStatusname());
        connection.close();
    }
}
