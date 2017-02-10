import chain.Chainlet;
import chain.Link;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import counter.Counter;
import greeting.MainPageServlet;
import greeting.PageControllerServlet;
import greeting.PageViewServlet;

import java.util.List;
import java.util.Map;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class ApplicationServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        bind(MainPageServlet.class);
        bind(PageControllerServlet.class);
        bind(PageViewServlet.class);

        serve("/chain","/first", "/second", "/third").with(Chainlet.class);
        serve("/counter").with(Counter.class);
        serve("/greet").with(MainPageServlet.class);
        serve("/page").with(PageControllerServlet.class);
        serve("/pageView").with(PageViewServlet.class);

        bind(Integer.class).annotatedWith(Names.named("Port")).toInstance(8080);
        bind(new TypeLiteral<Map<String, String>>() {
        }).annotatedWith(Names.named("TemplateMap")).toInstance(ImmutableMap.of(
                "page1", "First Page",
                "page2", "Second Page",
                "page3", "Third Page"
        ));

        bind(new TypeLiteral<List<Link>>() {
        }).annotatedWith(Names.named("AllLinks")).toInstance(ImmutableList.of(
                new Link("first", "<a href=\"/first\" >Go to page 1</a><br>"),
                new Link("second", "<a href=\"/second\" >Go to page 2</a><br>"),
                new Link("third", "<a href=\"/third\" >Go to page 3</a><br>")
        ));
    }

}
