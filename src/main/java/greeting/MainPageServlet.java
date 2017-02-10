package greeting;

import com.google.common.io.ByteStreams;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
@Singleton
public class MainPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ByteStreams.copy(MainPageServlet.class.getResourceAsStream("index.html"), resp.getOutputStream());
    }
}
