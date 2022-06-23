package DBTests;

import DAO.ConstantsDAO;
import DAO.DirectionDAO;
import DB.DBSingleton;
import model.DirectionModel;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DirectionDAOTest {
    @NotNull
    private DirectionDAO dd;

    @NotNull
    private Connection connection;

    @Test
    public void getByTwoFieldsTest() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        dd = new DirectionDAO(connection);
        DirectionModel dm = dd.getByTwoFields(3, 4);
        Assert.assertEquals(dm.getDistance(), 218);
        Assert.assertNotEquals(dm.getDirectionId(), 0);
        connection.close();
    }
}
