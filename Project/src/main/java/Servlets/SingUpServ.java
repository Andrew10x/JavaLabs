package Servlets;

import DAO.RoleDAO;
import DAO.UserDAO;
import DB.DBSingleton;
import HelperClasses.Encryptor;
import HelperClasses.Session;
import model.RoleModel;
import model.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/SingUpServ")
public class SingUpServ extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/auth/singupError.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> mp = req.getParameterMap();
        UserDAO ud;
        try {
            ud = new UserDAO(DBSingleton.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(ud.get(mp.get("login")[0]).getPasswordUsr()  != null){
            doGet(req, resp);
            return;
        }

        Encryptor enc = new Encryptor();

        String hashPassword = enc.encryptPassword(mp.get("password")[0]);
        UserModel userModel = new UserModel();
        userModel.setUserName(mp.get("pib")[0]);
        userModel.setPhone(mp.get("phoneNumber")[0]);
        userModel.setEmail(mp.get("login")[0]);
        userModel.setPasswordUsr(hashPassword);

        RoleDAO rd;
        try {
            rd = new RoleDAO(DBSingleton.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        RoleModel rm = rd.get("Registered user");
        userModel.setRoleName(rm.getRoleName());
        userModel.setRoleId(rm.getRoleId());


        int res;
        res = ud.add(userModel);

        if(res == 0){
            resp.getWriter().write("Add user error");
        }
        else {
            Session session = new Session(req);
            session.createSession();
            String name = "UserRole";
            String role = "RegisteredUser";
            Cookie c = new Cookie(name, role);
            resp.addCookie(c);
            resp.sendRedirect(req.getContextPath() + "/");
        }

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}