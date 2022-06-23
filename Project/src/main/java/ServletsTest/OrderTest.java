package ServletsTest;

import Servlets.Order;
import org.testng.annotations.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static org.mockito.Mockito.*;


public class OrderTest {
    private final static String path = "/order.jsp";

    @Test
    public void doGetTest() throws ServletException, IOException {

        final Order servlet = new Order();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        Map<String, String[]> mp = new HashMap<>();
       String[] id = {"13"};
        mp.put("id", id);
        when(request.getParameterMap()).thenReturn(mp);
        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(dispatcher).forward(request, response);
    }
}