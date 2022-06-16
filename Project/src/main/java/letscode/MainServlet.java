package letscode;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@WebServlet(urlPatterns = {"/cool-servlet"})
public class MainServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log("Method init =)");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Method service enter\n");
        super.service(req, resp);
        resp.getWriter().write("Method service exit\n");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //doWork(req, resp);
        Cookie[] a = req.getCookies();

        for(int i=0; i<a.length; i++)
            resp.getWriter().write(a[i].getName() + " " + a[i].getValue() + '\n');
        Cookie c = new Cookie("fds", "qqq");
        c.setMaxAge(0);
        for(int i=0; i<a.length; i++)
            resp.getWriter().write(a[i].getName() + " " + a[i].getValue() + '\n');
        resp.addCookie(c);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doWork(req, resp);
        String s = "";
        log(s);
        //resp.sendRedirect("/my-app/static/form.html");
    }

    private void doWork(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Map<String, String[]> mp = req.getParameterMap();
        String s = "", s1 = "";
        //mp.get("df");
        for(Map.Entry<String, String[]> pair : mp.entrySet())
        {
            s += pair.getKey() + " ";
            s1 += pair.getValue()[0] + " ";
        }
        resp.getWriter().write("Method doPost\n" + s + "\n" + s1 + "\n");
    }

    @Override
    public void destroy() {
        log("Method destroy =)");
    }
}
