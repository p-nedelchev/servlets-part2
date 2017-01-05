package newcomer.servlet;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import newcomer.Template;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class NewcomerServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        render(request.getRequestURI().substring(1), request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void render(String resourceName, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            URL url = NewcomerServlet.class.getResource(resourceName);
            if (url == null) {
                response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                return;
            }

            String html = Resources.toString(url, Charset.defaultCharset());
            Template template = new Template(html);
            HttpSession session = request.getSession();
            if ((session.getAttribute("visited") == null) || (session.getAttribute("visited") == "Welcome back!")) {
                session.setAttribute("visited", "Greetings, Newcomer!");
            }
            String button = request.getParameter("button");
            if (session.getAttribute(button) == null) {
                session.setAttribute(button, "alreadyVisited");
            } else {
                session.setAttribute("visited", "Welcome back!");
            }
            String visited = String.valueOf(session.getAttribute("visited"));
            template.put("visited", visited);
            template.put("pageName", button);
            writer.print(template.evaluate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
