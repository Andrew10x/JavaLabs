package letscode;

import model.*;
import org.jasypt.util.password.BasicPasswordEncryptor;
import java.security.NoSuchAlgorithmException;

import DB.DBSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String s = "12345678901234567890123456789";
        System.out.println(s.substring(0, 5));
    }

    public static void getOrdersJoined() throws SQLException {
        DBQueries dbq = new DBQueries();
        List<OrderJoinedModel> ojml =  dbq.gerOrdersJoined("svt.andrey1631@gmail.com");
        OrderJoinedModel ojm = ojml.get(0);

        System.out.println(ojm.getAddress());
        System.out.println(ojm.getRecipientName());
    }

    public static void getStatusId() throws SQLException {
        DBQueries dbq = new DBQueries();
        int resId = dbq.getStatusId("Підтверджено");
        System.out.println(resId);
    }
    public static void getDirectionId() throws SQLException {
        DBQueries dbq = new DBQueries();
        int resId = dbq.getDirectionId(3, 5);
        System.out.println(resId);
    }
    public static void addRecipient() throws SQLException {
        DBQueries dbq = new DBQueries();
        RecipientModel rm = new RecipientModel();
        rm.setRecipientName("Дяченко Максим Євгенович");
        rm.setRecipientPhone("+380933345412");
        int resId = dbq.addRecipient(rm);
        System.out.println(resId);
    }
    public static void getUserId() throws SQLException {
        DBQueries dbq = new DBQueries();
        int resId = dbq.getUserIdByEmail("svt.andrey1631@gmail.com");
        System.out.println(resId);
    }

    public static void setOrder() throws SQLException {
        DBQueries dbq = new DBQueries();
        OrderModel om = new OrderModel();
        om.setUserId(1);
        om.setDirectionId(2);
        om.setStatusId(1);
        om.setWeightOrd(20f);
        om.setLengthOrd(30);
        om.setHeightOrd(40);
        om.setWidthOrd(20);
        om.setTypeId(1);
        om.setSumInsured(100f);
        om.setAdress("м. Київ, вулиця Підлісна 5, 3 під'їзд, кв. 155");
        om.setDeliveryCost(75f);
        om.setRecipientId(1);
        int resId = dbq.addOrder(om);
        System.out.println(resId);
    }

    public static void getPR() throws SQLException {
        DBQueries dbq = new DBQueries();
        PasswordAndRoleModel prm = dbq.getPasswordAndRole("petrenko.myola@fastdelivery.ua");
        System.out.println(prm.getRoleName());
    }

    public static void test() {
        BasicPasswordEncryptor spe = new BasicPasswordEncryptor();
        String originalPassword = "Pavel123";
        String hashPassword = spe.encryptPassword(originalPassword);

        System.out.println(hashPassword);
        if (spe.checkPassword(originalPassword, hashPassword)) System.out.println("Matched");
    }

    public static void test1() throws SQLException {
        DBQueries dbq = new DBQueries();
        UserModel user = new UserModel();
        user.setUserName("John J D Укр");
        user.setPhone("0993212455");
        user.setEmail("sdf@gmail.com");
        user.setPasswordUsr("1234");
        System.out.println(dbq.addUser(user));

        /*int distance = dbq.getDistance(2, 5);
        System.out.println(distance);

        /*ConstantsModel cm = dbq.getConstants("distance");
        System.out.println(cm.getId());
        System.out.println(cm.getName());
        System.out.println(cm.getMinPrice());*/
        /*List<CityModel> cml = dbq.getCities();
        for (CityModel cityModel : cml) {
            System.out.println(cityModel.getCityId() + " " + cityModel.getCityName());
        }*/
    }

    public void date() {
        /*Date d2 = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d2);
        System.out.println(d2);
        System.out.println(cal.get(Calendar.YEAR) + " " + cal.get(Calendar.MONTH)  + " "
                + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + " "
        + cal.get(Calendar.MINUTE));
        System.out.println();*/
    }

    public static void conn() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement pr = null;
        try {
            con = DBSingleton.getInstance().getConnection();
            pr = con.prepareStatement("select * from users");
            ResultSet res = pr.executeQuery();
        while(res.next()) {
            System.out.println(res.getInt(1));
            System.out.println(res.getString(2));
            System.out.println(res.getString(3));
            System.out.println(res.getString(4));
            System.out.println(res.getString(5));
            System.out.println(res.getInt(6));
            System.out.println();
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(pr != null)
                pr.close();
            if(con != null)
                con.close();
        }

        //pr.close();
        //con.close();

        System.out.println("================================");

    }
}
