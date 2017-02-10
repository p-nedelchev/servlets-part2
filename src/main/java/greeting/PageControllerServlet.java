package greeting;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
@Singleton
public class PageControllerServlet extends HttpServlet {
    private final Map<String, String> linkToTemplateMap;

    @Inject
    public PageControllerServlet(@Named("TemplateMap") Map<String, String> linkToTemplateMap) {
        this.linkToTemplateMap = linkToTemplateMap;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String greetingMessage = "Welcome you visit this page for the first time";
        String message = "You have been here before";

        String linkName = req.getParameter("name");

        req.setAttribute("pageName", linkToTemplateMap.get(linkName));
        String visited = (String) session.getAttribute(linkName);

        if (visited == null) {
            req.setAttribute("value", greetingMessage);
            session.setAttribute(linkName, "yes");
        } else {
            req.setAttribute("value", message);
        }

        ForwardHandler forwardHandler = new ForwardHandler("pageView");
        forwardHandler.handle(new RequestWrapper(req), resp);
    }
}

