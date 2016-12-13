package greeting;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
interface HttpHandler {
    void handle(Request request, HttpServletResponse response) throws IOException;
}
