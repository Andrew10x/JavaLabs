package letscode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/temp-serv")
public class JspServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<User> users = new ArrayList<User>() {{
            add(new User("Ігор", "England", 25));
            add(new User("Andrew", "USA", 98));
            add(new User("Masya", "Ukraine", 25));
        }};
        req.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/first-jsp.jsp").forward(req, resp);
    }
}
