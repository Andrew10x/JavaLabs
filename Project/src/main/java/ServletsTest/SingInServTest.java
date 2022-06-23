package ServletsTest;

import Servlets.PrintOrder;
import Servlets.SingInServ;
import org.testng.annotations.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static org.mockito.Mockito.*;


public class SingInServTest {
    private final static String path = "/auth/singinError.jsp";

    @Test
    public void doGetTest() throws ServletException, IOException {

        final SingInServ servlet = new SingInServ();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(dispatcher).forward(request, response);
    }
}
