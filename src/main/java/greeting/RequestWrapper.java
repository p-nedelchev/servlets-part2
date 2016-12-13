package greeting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
class RequestWrapper implements Request {
    private final HttpServletRequest request;

    public RequestWrapper(HttpServletRequest request) {
        this.request = request;
    }

    public void forward(String path, HttpServletResponse response) throws IOException {
        try {
            request.getRequestDispatcher(path).forward(request, response);
        } catch (ServletException e) {
            throw new IOException("could not forward request using the servlet API", e);
        }
    }
}
