package letscode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/session-serv")
public class SessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(true);
            PrintWriter out = response.getWriter();
            StringBuffer url = request.getRequestURL();
            session.setAttribute("URL", url);
            out.print("My session counter: ");
            out.println(String.valueOf(prepareSessionCounter(session)));

            out.println("Creation Time : " + new Date(session.getCreationTime()));

            out.print("Time of last access : " + new Date(session.getLastAccessedTime()));
            out.println(", session ID : " + session.getId());
            out.println("Your URL: " + url);
            int timeLive = 60 * 30;
            session.setMaxInactiveInterval(timeLive);
            out.println("Set max inactive interval : " + timeLive + " sec");

            out.flush();
            out.close();
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed : " + e);
        }
    }

    private static int prepareSessionCounter(

            HttpSession session) {

        Integer counter =

                (Integer)session.getAttribute("counter");

        if (counter == null) {
            session.setAttribute("counter", 1);
            return 1;
        } else {
            counter++;
            session.setAttribute("counter", counter);
            return counter;
        }
    }
}
