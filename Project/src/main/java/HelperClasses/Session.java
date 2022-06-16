package HelperClasses;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Session {

    private final HttpSession session;

    public Session(HttpServletRequest req) {
        session = req.getSession(true);
    }
    public void createSession() {
        int timeLive = 60 * 30;
        session.setMaxInactiveInterval(timeLive);
    }
    public void closeSession() {
        session.invalidate();
    }
}
