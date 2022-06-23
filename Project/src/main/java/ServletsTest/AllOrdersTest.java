package ServletsTest;

import Servlets.AllOrders;
import org.testng.annotations.Test;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static org.mockito.Mockito.*;

public class AllOrdersTest {

    private final static String path = "/allOrders.jsp";

    @Test
    public void doGetTest() throws ServletException, IOException {

        final AllOrders servlet = new AllOrders();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostTest() throws ServletException, IOException {

        final AllOrders servlet = new AllOrders();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        Map<String, String[]> mp = new HashMap<>();
        String[] f = {""};
        String[] id = {"0"};
        mp.put("orderId", id);
        mp.put("email", f);
        mp.put("status", f);
        mp.put("date", f);
        mp.put("cityFrom", f);
        mp.put("cityTo", f);
        when(request.getParameterMap()).thenReturn(mp);
        servlet.doPost(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(dispatcher).forward(request, response);
    }

}
