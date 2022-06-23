package DBTests;

import DAO.StatusDAO;
import DAO.UserDAO;
import DB.DBSingleton;
import model.StatusesModel;
import model.UserModel;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAOTest {
    @NotNull
    private UserDAO ud;

    @NotNull
    private Connection connection;

    @Test
    public void getTest() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        ud = new UserDAO(connection);
        UserModel um = ud.get("svt.andrey1631@gmail.com");
        Assert.assertEquals(um.getPhone(), "0993433122");
        Assert.assertEquals(um.getRoleName(), "Registered user");
        connection.close();
    }

    @Test
    public void addUser() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        ud = new UserDAO(connection);
        UserModel um = ud.get("svt.andrey1631@gmail.com");
        um.setEmail("qwewqewqew");
        um.setUserName("qwewqe");
        int res = ud.add(um);
        Assert.assertNotEquals(res, 0);
        connection.close();
    }
}
