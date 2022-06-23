package DBTests;

import DAO.CityDAO;
import DAO.ConstantsDAO;
import DB.DBSingleton;
import model.CityModel;
import model.ConstantsModel;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ConstantsDAOTest {
    @NotNull
    private ConstantsDAO cd;

    @NotNull
    private Connection connection;


    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        cd = new ConstantsDAO(connection);
        ConstantsModel cm = cd.get("distance");
        Assert.assertEquals(cm.getMinPrice(), 30, 0);
        Assert.assertEquals(cm.getMinValue(), 50, 0);
        Assert.assertEquals(cm.getCoef(), 5, 0);
        connection.close();
    }
}
