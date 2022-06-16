package letscode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class CookieAction {
    public static void setCookie(HttpServletResponse resp) {
        String name = "Spiridonov";
        String role = "MegaAdmin";
        Cookie c = new Cookie(name, role);
        c.setMaxAge(3600);
        resp.addCookie(c);
        String loc = resp.getLocale().toString();
        Cookie c1 = new Cookie("locale", loc);
        resp.addCookie(c1);
    }
    public static ArrayList<String> addToRequest(HttpServletRequest request) {

        ArrayList<String> mes = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            mes.add("Number cookies : " + cookies.length);
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                mes.add(c.getName() + " = " + c.getValue());
            }
        }
        return mes;
    }
}
