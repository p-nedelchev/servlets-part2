import com.google.inject.Guice;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class Demo {

    public static void main(String[] args) {
        Guice.createInjector(new ApplicationServletModule()).getInstance(Jetty.class).start();
    }
}
