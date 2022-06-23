package ServletsTest;

import Servlets.AllOrders;
import Servlets.CalcPriceServ;
import org.testng.annotations.Test;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static org.mockito.Mockito.*;


public class CalcPriceServTest {
    private final static String path1 = "/main.jsp";
    private final static String path2 = "/showCalcPrice.jsp";

    @Test
    public void doGetTest() throws ServletException, IOException {

        final CalcPriceServ servlet = new CalcPriceServ();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(path1)).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path1);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostTest() throws ServletException, IOException {

        final CalcPriceServ servlet = new CalcPriceServ();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(path2)).thenReturn(dispatcher);
        Map<String, String[]> mp = new HashMap<>();
        String[] city = {"2"};
        mp.put("cityFrom", city);
        mp.put("cityTo", city);
        String[] weight = {"12"};
        mp.put("weight", weight);
        String[] l = {"40"};
        mp.put("length", l);
        mp.put("width", l);
        mp.put("height", l);
        mp.put("pCost", l);
        when(request.getParameterMap()).thenReturn(mp);
        servlet.doPost(request, response);

        verify(request, times(1)).getRequestDispatcher(path2);
        verify(dispatcher).forward(request, response);
    }


}
