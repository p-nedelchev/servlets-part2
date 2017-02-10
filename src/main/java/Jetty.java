
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceFilter;
import greeting.MainPageServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;


/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public final class Jetty {
    private final Server server;
    private static final int SHUTDOWN_TIMEOUT = 30000;

    @Inject
    public Jetty(@Named("Port") int port) {
        this.server = new Server(port);
    }

    public void start() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        servletContextHandler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        servletContextHandler.addServlet(MainPageServlet.class, "/");

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        server.setGracefulShutdown(SHUTDOWN_TIMEOUT);
    }

}
