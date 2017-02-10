import chain.Chainlet;
import chain.Link;
import chain.LinkCatalog;
import com.google.common.collect.ImmutableList;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import counter.Counter;
import greeting.MainPageServlet;
import greeting.PageCatalog;
import greeting.PageControllerServlet;
import greeting.PageViewServlet;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class ApplicationServletModule extends ServletModule {
    private final int port;

    public ApplicationServletModule(Integer port) {
        this.port = port;
    }

    @Override
    protected void configureServlets() {
        bind(MainPageServlet.class);
        bind(PageControllerServlet.class);
        bind(PageViewServlet.class);
        bind(PageCatalog.class);

        serve("/chain", "/first", "/second", "/third").with(Chainlet.class);
        serve("/counter").with(Counter.class);
        serve("/greet").with(MainPageServlet.class);
        serve("/page").with(PageControllerServlet.class);
        serve("/pageView").with(PageViewServlet.class);

        bind(Integer.class).annotatedWith(Names.named("Port")).toInstance(port);

        bind(LinkCatalog.class).toInstance(new LinkCatalog(ImmutableList.of(
                new Link("first", "<a href=\"/first\" >Go to page 1</a><br>"),
                new Link("second", "<a href=\"/second\" >Go to page 2</a><br>"),
                new Link("third", "<a href=\"/third\" >Go to page 3</a><br>"))));
    }

}
