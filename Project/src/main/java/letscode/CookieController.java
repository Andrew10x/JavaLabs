package letscode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cookie-test")
public class CookieController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CookieAction.setCookie(resp);
        resp.addCookie( new Cookie("fds", "qqq"));

        req.setAttribute("messages", CookieAction.addToRequest(req));
        req.getRequestDispatcher("/mainCookie.jsp").forward(req, resp);
    }
}
