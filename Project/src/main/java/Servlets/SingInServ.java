package Servlets;
import DAO.UserDAO;
import DB.DBSingleton;
import HelperClasses.Encryptor;
import HelperClasses.Session;
import model.UserModel;


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

        UserDAO ud;
        try {
            ud = new UserDAO(DBSingleton.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        UserModel um = ud.get(login);

        if(um.getPasswordUsr() == null) {
            doGet(req, resp);
            return;
        }

        Encryptor enc = new Encryptor();
        if(enc.checkPassword(password, um.getPasswordUsr())) {
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

        try {
            DBSingleton.getInstance().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
