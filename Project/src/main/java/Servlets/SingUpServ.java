package Servlets;

import HelperClasses.Encryptor;
import HelperClasses.Session;
import letscode.CookieAction;
import letscode.DBQueries;
import model.UserModel;
import org.jasypt.util.password.BasicPasswordEncryptor;

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
        DBQueries dbq = new DBQueries();

        try {
            if(dbq.getPasswordAndRole(mp.get("login")[0]).getPasswordUsr() != null){
                doGet(req, resp);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Encryptor enc = new Encryptor();

        String hashPassword = enc.encryptPassword(mp.get("password")[0]);
        UserModel userModel = new UserModel();
        userModel.setUserName(mp.get("pib")[0]);
        userModel.setPhone(mp.get("phoneNumber")[0]);
        userModel.setEmail(mp.get("login")[0]);
        userModel.setPasswordUsr(hashPassword);

        boolean res;
        try {
            res = dbq.addUser(userModel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(!res){
            resp.getWriter().write("Не вдалося додати користувача до БД");
        }
        else {
            Session session = new Session(req);
            session.createSession();
            String name = "UserRole";
            String role = "RegisteredUser";
            Cookie c = new Cookie(name, role);
            c.setMaxAge(-1);
            resp.addCookie(c);
            resp.sendRedirect(req.getContextPath() + "/");
        }

    }
}