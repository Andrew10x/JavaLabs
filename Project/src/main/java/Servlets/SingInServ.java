package Servlets;
import HelperClasses.Encryptor;
import HelperClasses.Session;
import letscode.DBQueries;
import model.PasswordAndRoleModel;
import org.jasypt.util.password.BasicPasswordEncryptor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/SingInServ")
public class SingInServ extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/auth/singinError.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> mp = req.getParameterMap();
        String login = mp.get("login")[0];
        String password = mp.get("password")[0];

        DBQueries dbq = new DBQueries();
        PasswordAndRoleModel prm = null;
        try {
            prm = dbq.getPasswordAndRole(login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(prm.getPasswordUsr() == null) {
            doGet(req, resp);
            return;
        }

        Encryptor enc = new Encryptor();
        if(enc.checkPassword(password, prm.getPasswordUsr())) {
            Session session = new Session(req);
            session.createSession();

            Cookie c = new Cookie("UserRole", "Manager");
            c.setMaxAge(-1);
            resp.addCookie(c);
            Cookie c1 = new Cookie("UserEmail", login);
            c1.setMaxAge(-1);
            resp.addCookie(c1);
            resp.sendRedirect(req.getContextPath() + "/");
        }
        else {
            doGet(req, resp);
        }

    }
}
