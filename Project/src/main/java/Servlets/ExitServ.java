package Servlets;
import HelperClasses.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ExitServ")
public class ExitServ extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie c1 = new Cookie("UserRole", "");
        resp.addCookie(c1);
        Session session = new Session(req);
        session.closeSession();
        /*if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                resp.getWriter().write(c.getName() + " = " + c.getValue());
            }
        }*/
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
