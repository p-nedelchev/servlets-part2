
import com.google.common.primitives.Ints;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class Demo {
    public static void main(String[] args) {
        Integer port = Ints.tryParse(args[0]);
        if (port == null) {
            Logger.getLogger("DefaultPort").log(Level.INFO, "Server started at default port: 8080");
            port = 8080;
        }

        Injector injector = Guice.createInjector(new ApplicationServletModule(port));
        Jetty jetty = injector.getInstance(Jetty.class);
        Runtime.getRuntime().addShutdownHook(new Thread(jetty::stop));
        jetty.start();

    }


}
