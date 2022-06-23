package DBTests;

import DAO.RecipientDAO;
import DAO.RoleDAO;
import DB.DBSingleton;
import model.RoleModel;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class RoleDAOTest {

    @NotNull
    private RoleDAO rd;

    @NotNull
    private Connection connection;

    @Test
    public void getTest() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        rd = new RoleDAO(connection);
        RoleModel rm = rd.get("Manager");
        Assert.assertEquals(rm.getRoleId(), 1);
        Assert.assertEquals(rm.getRoleName(), "Manager");
        connection.close();
    }
}
