package greeting;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
class ForwardHandler implements HttpHandler {
    private final String uri;

    ForwardHandler(String uri) {
        this.uri = uri;
    }

    @Override
    public void handle(Request request, HttpServletResponse response) throws IOException {
        request.forward(uri, response);
    }
}
