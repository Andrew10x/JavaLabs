package DBTests;

import DAO.CityDAO;
import DB.DBSingleton;
import model.CityModel;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CityDAOTest {
    @NotNull
    private CityDAO cd;

    @NotNull
    private Connection connection;


    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
        connection = DBSingleton.getInstance().getConnection();
        cd = new CityDAO(connection);
        List<CityModel> cml = cd.getAll();
        if(cml.size() > 0) {
            CityModel cm = cml.get(0);
            Assert.assertNotEquals(cm.getCityId(), 0);
            Assert.assertNotNull(cm.getCityName());
        }
        connection.close();
    }

}
