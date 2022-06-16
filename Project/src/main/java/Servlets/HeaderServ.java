package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet("/HeaderServ")
public class HeaderServ extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String UserRole = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if(Objects.equals(c.getName(), "UserRole"))
                    UserRole = c.getValue();
            }

        }
        req.setAttribute("UserRole", UserRole);
        getServletContext().getRequestDispatcher("/header.jsp").forward(req, resp);
    }
}
