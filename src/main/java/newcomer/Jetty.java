package newcomer;

import newcomer.servlet.NewcomerServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class Jetty {
    private final Server server;

    public Jetty(Server server) {
        this.server = server;
    }

    public void start() {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addEventListener(new ServletContextListener() {

            @Override
            public void contextInitialized(ServletContextEvent sce) {
                ServletContext servletContext = sce.getServletContext();
                servletContext.addServlet("newcomer", new NewcomerServlet()).addMapping("/index.html", "/newcomer.html");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {

            }
        });

        server.setHandler(contextHandler);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
