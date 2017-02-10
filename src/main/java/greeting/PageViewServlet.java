package greeting;

import com.google.common.io.ByteStreams;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
@Singleton
public class PageViewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String templateAsString = new String(ByteStreams.toByteArray(PageViewServlet.class.getResourceAsStream("template.html")));

        Enumeration<String> attributeNames = req.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            String attributeValue = (String) req.getAttribute(attributeName);
            templateAsString = templateAsString.replaceAll("\\$\\{" + attributeName + "\\}", attributeValue);
        }

        ByteStreams.copy(new ByteArrayInputStream(templateAsString.getBytes("UTF-8")), resp.getOutputStream());
    }
}
