package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/PayOrder")
public class PayOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> mp = req.getParameterMap();
        if(mp.get("year") != null)
            resp.sendRedirect(req.getContextPath() + "/payForm.jsp?success=true");
        else
            resp.sendRedirect(req.getContextPath() + "/payForm.jsp?success=false");
    }
}
