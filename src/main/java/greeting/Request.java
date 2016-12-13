package greeting;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
interface Request {
    void forward(String path, HttpServletResponse response) throws IOException;
}
