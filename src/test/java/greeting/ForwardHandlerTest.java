package greeting;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class ForwardHandlerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void helloPageIsForwardedToIndexPage() throws Exception {
        ForwardHandler handler = new ForwardHandler("/pageView");
        Request request = context.mock(Request.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);

        context.checking(new Expectations() {{
            oneOf(request).forward("/pageView", response);
        }});

        handler.handle(request, response);

    }
}