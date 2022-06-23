package ServletsTest;

import Servlets.SingInServ;
import Servlets.SingUpServ;
import org.testng.annotations.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static org.mockito.Mockito.*;


public class SingUpServTest {
    private final static String path = "/auth/singupError.jsp";

    @Test
    public void doGetTest() throws ServletException, IOException {

        final SingUpServ servlet = new SingUpServ();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(dispatcher).forward(request, response);
    }
}
