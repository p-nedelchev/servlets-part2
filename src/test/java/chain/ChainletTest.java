package chain;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class ChainletTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void noSpecificPageRequested() throws Exception {
        Chainlet chainlet = Guice.createInjector(new ChainletModule()).getInstance(Chainlet.class);
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        context.checking(new Expectations() {{
            oneOf(request).getHeader("referer");
            will(returnValue("home"));

            oneOf(request).getRequestURI();
            will(returnValue("page1"));

            oneOf(response).getWriter();
            will(returnValue(printWriter));
        }});

        chainlet.doGet(request, response);

        assertThat(writer.toString(), containsString("I came from home"));
        assertThat(writer.toString(), containsString("Go to page 2"));
        assertThat(writer.toString(), containsString("Go to page 3"));
    }

    @Test
    public void oneLinkClicked() throws Exception {
        Chainlet chainlet = Guice.createInjector(new ChainletModule()).getInstance(Chainlet.class);
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        context.checking(new Expectations() {{
            atLeast(1).of(request).getHeader("referer");
            will(returnValue("http://localhost:8080/page1"));

            atLeast(1).of(request).getRequestURI();
            will(returnValue("page2"));

            exactly(2).of(response).getWriter();
            will(returnValue(printWriter));
        }});

        chainlet.doGet(request, response);
        chainlet.doGet(request, response);

        assertThat(writer.toString(), containsString("I came from http://localhost:8080/page1"));
        assertThat(writer.toString(), containsString("Go to page 1"));
        assertThat(writer.toString(), containsString("Go to page 3"));
    }

    private class ChainletModule extends ServletModule {
        @Override
        protected void configureServlets() {
            bind(new TypeLiteral<List<Link>>() {}).annotatedWith(Names.named("AllLinks")).toInstance(ImmutableList.of(
                    new Link("page1", "<a href=\"/page1\" >Go to page 1</a><br>"),
                    new Link("page2", "<a href=\"/page2\" >Go to page 2</a><br>"),
                    new Link("page3", "<a href=\"/page3\" >Go to page 3</a><br>")
            ));
        }
    }
}
