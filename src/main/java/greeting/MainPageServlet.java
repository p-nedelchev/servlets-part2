package greeting;

import com.google.common.io.ByteStreams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class MainPageServlet extends HttpServlet{

    public MainPageServlet() {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClassLoader loader = getClass().getClassLoader();
        InputStream is = loader.getResourceAsStream("greeting/index.html");
        ByteStreams.copy(is, resp.getOutputStream());
    }
}
