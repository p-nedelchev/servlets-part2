package greeting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class PageControllerServlet extends HttpServlet {

    public PageControllerServlet() {}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();


        String greetingMessage = "Welcome you visit this page for the first time";
        String message = "You have been here before";

        Map<String, String> linkToTemplateMap = new HashMap<>();
        linkToTemplateMap.put("page1", "greeting/template.html");
        linkToTemplateMap.put("page2", "greeting/template-2.html");
        linkToTemplateMap.put("page3", "greeting/template-3.html");

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

