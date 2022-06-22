package letscode;

import DAO.*;
import model.*;
import org.jasypt.util.password.BasicPasswordEncryptor;

import DB.DBSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        updateOrder();
    }

    public static void updateOrder() throws SQLException, ClassNotFoundException {
        OrderDAO od = new OrderDAO(DBSingleton.getInstance().getConnection());
        OrderModel om = new OrderModel();
        om.setId(16);
        om.setStatusId(3);
        od.update(om);
    }
    public static void addOrder() throws SQLException, ClassNotFoundException {
        OrderDAO od = new OrderDAO(DBSingleton.getInstance().getConnection());
        OrderModel om = new OrderModel(0, 56, 111, "2022-06-17 13:48:28.0", 1, 12.0f, 12, 14, 40, 1, 1, 120.0f, "dsf", 133.0f);
        System.out.println(od.add(om));
    }
    public static void addRecipient1() throws SQLException, ClassNotFoundException {
        RecipientDAO rd = new RecipientDAO(DBSingleton.getInstance().getConnection());
        RecipientModel rm = new RecipientModel();
        rm.setRecipientPhone("+380991111111");
        rm.setRecipientName("Дмитро Вітер Васильович");
        System.out.println(rd.add(rm));
    }
    public static void getOrderJWithF() throws SQLException, ClassNotFoundException {
        OrderJoinedDAO oj1d = new OrderJoinedDAO(DBSingleton.getInstance().getConnection());
        List<OrderJoinedModel> ojml = oj1d.getAllWithFilter(0, "svt.andrey1631@gmail.com", "",
                "", "", "");
        OrderJoinedModel ojm = ojml.get(0);
        System.out.println(ojm.getEmail());
        System.out.println(ojm.getRecipientName());
        System.out.println(ojm.getCityFrom() + ojm.getCityTo());
    }

    public static void getOrderJById() throws SQLException, ClassNotFoundException {
        OrderJoinedDAO oj1d = new OrderJoinedDAO(DBSingleton.getInstance().getConnection());
        OrderJoinedModel oj1m = oj1d.getById(12);
        DBSingleton.getInstance().getConnection().close();
        OrderJoinedDAO ojd = new OrderJoinedDAO(DBSingleton.getInstance().getConnection());
        OrderJoinedModel ojm = ojd.getById(12);
            System.out.println(ojm.getAddress());
            System.out.println(ojm.getRecipientName());
            System.out.println(ojm.getCityFrom() + ojm.getCityTo());
    }

    public static void getDirection() throws SQLException, ClassNotFoundException {
        DirectionDAO dd = new DirectionDAO(DBSingleton.getInstance().getConnection());
        DirectionModel dm = dd.getByTwoFields(2, 5);
        System.out.println(dm.getDirectionId() + " " + dm.getDistance());
    }
    public static void getRole() throws SQLException, ClassNotFoundException {
        RoleDAO rd = new RoleDAO(DBSingleton.getInstance().getConnection());
        RoleModel rm = rd.get("Manager");
        System.out.println(rm.getRoleId() + rm.getRoleName());
    }

    public static void getStatus() throws SQLException, ClassNotFoundException {
        StatusDAO sd = new StatusDAO(DBSingleton.getInstance().getConnection());
        StatusesModel sm = sd.get("Підтверджено");
        System.out.println(sm.getStatusid() + " " + sm.getStatusname());

    }

    public static void addUser() throws SQLException, ClassNotFoundException {
        UserDAO ud = new UserDAO(DBSingleton.getInstance().getConnection());
        UserModel um = ud.get("svt.andrey1631@gmail.com");
        um.setEmail("qwewqewqew");
        um.setUserName("qwewqe");
        System.out.println(ud.add(um));
    }
    public static void getUser() throws SQLException, ClassNotFoundException {
        UserDAO ud = new UserDAO(DBSingleton.getInstance().getConnection());
        UserModel um = ud.get("svt.andrey1631@gmail.com");
        System.out.println(um.getEmail() + " " + um.getUserName() + " " + um.getRoleName());
        System.out.println(um.getPasswordUsr());
    }

    public static void getConstants() throws SQLException, ClassNotFoundException {
        ConstantsDAO cd = new ConstantsDAO(DBSingleton.getInstance().getConnection());
        ConstantsModel cm = cd.get("distance");
                System.out.println(cm.getId());
                System.out.println(cm.getName());
                System.out.println(cm.getMinPrice());
    }

    public static void getCities() throws SQLException, ClassNotFoundException {
        CityDAO cd = new CityDAO(DBSingleton.getInstance().getConnection());
        List<CityModel> cm = cd.getAll();

        for(CityModel c: cm) {
            System.out.println(c.getCityId() + " " + c.getCityName());
        }

    }

    public static void changeStatus() throws SQLException {
        DBQueries dbq = new DBQueries();
        System.out.println(dbq.changeStatus(1, 1));
    }

    public static String transfDate(String date) {
        if(Objects.equals(date, ""))
            return "";
        return date.substring(6, 10) + '-' + date.substring(3, 5) + '-' + date.substring(0, 2);
    }

    public static void getStatuses() throws SQLException, ClassNotFoundException {
        StatusDAO sd = new StatusDAO(DBSingleton.getInstance().getConnection());
        List<StatusesModel> sml = sd.getAll();
        for(StatusesModel sm: sml) {
            System.out.println(sm.getStatusid() + " " + sm.getStatusname());
        }
    }

    public static void getOrdersJoined() throws SQLException {
        DBQueries dbq = new DBQueries();
        List<OrderJoinedModel> ojml =  dbq.getOrdersJoined(0, "",
                "Створено", "", "", "");
        if(ojml.size() > 0) {
            OrderJoinedModel ojm = ojml.get(0);

            System.out.println(ojm.getAddress());
            System.out.println(ojm.getRecipientName());
            System.out.println(ojm.getCityFrom() + ojm.getCityTo());
        }
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
