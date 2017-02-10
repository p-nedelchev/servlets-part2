package chain;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */

@Singleton
public class Chainlet extends HttpServlet {
    private final LinkCatalog linkCatalog;

    @Inject
    public Chainlet(LinkCatalog linkCatalog) {

        this.linkCatalog = linkCatalog;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String origin = req.getHeader("referer");

        if (origin == null) {
            origin = "home";
        }

        final String pageName = req.getRequestURI().substring(1);


        List<Link> links = linkCatalog.getLinksExcluding(pageName);

        StringBuilder pageContent = new StringBuilder();
        pageContent.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>I came from " + origin + "</div>\n");


        for (Link each : links) {
            pageContent.append(each.linkContent);
        }

        pageContent.append("</body>\n" +
                "</html>");

        PrintWriter writer = resp.getWriter();
        writer.println(pageContent);
        writer.flush();
    }


}
