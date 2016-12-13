package greeting;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class FakeRequestDispatcher implements RequestDispatcher{

    @Override
    public void forward(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        this.forward(servletRequest, servletResponse, DispatcherType.FORWARD);
    }

    @Override
    public void include(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    private void forward(ServletRequest servletRequest, ServletResponse servletResponse, DispatcherType type) {

    }
}
