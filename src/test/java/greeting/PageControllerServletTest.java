package greeting;

import com.google.common.collect.ImmutableMap;
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
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class PageControllerServletTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();


    @Test
    public void allAttributesSet() throws Exception {

        PageControllerServlet servlet1 = Guice.createInjector(new PageControllerModule()).getInstance(PageControllerServlet.class);

        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);

        FakeHttpSession session = new FakeHttpSession(new LinkedHashMap<>());

        context.checking(new Expectations() {{
            oneOf(request).getSession();
            will(returnValue(session));

            oneOf(request).getParameter("name");
            will(returnValue("page1"));

            oneOf(request).setAttribute("pageName", "First Page");

            oneOf(request).setAttribute("value", "Welcome you visit this page for the first time");

            oneOf(request).getRequestDispatcher("pageView");
        }});

        servlet1.doPost(request, response);

        assertThat(session.getAttribute("page1"), is("yes"));
    }

    private class PageControllerModule extends ServletModule {
        @Override
        protected void configureServlets() {
            bind(new TypeLiteral<Map<String, String>>() {}).annotatedWith(Names.named("TemplateMap")).toInstance(ImmutableMap.of(
                    "page1", "First Page"
            ));
        }
    }


}
