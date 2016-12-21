package previouslocation.servlet;

import com.google.common.io.Files;
import com.google.common.net.HttpHeaders;
import previouslocation.HtmlTemplate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class PreviousLocationServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            response.setContentType("text/html");
            render(request.getRequestURI().substring(1), writer, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void render(String resourceName, PrintWriter writer, HttpServletRequest request) {
        try {
            URL url = PreviousLocationServlet.class.getClassLoader().getResource(resourceName);
            String html = Files.toString(new File(url.getPath()), Charset.defaultCharset());
            String previousPage = request.getHeader(HttpHeaders.REFERER);
            if (previousPage == null) {
                previousPage = "N/A";
            }
            HtmlTemplate template = new HtmlTemplate(html);
            template.put("page", previousPage);
            writer.print(template.evaluate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
