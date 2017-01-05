package newcomer;

import newcomer.servlet.NewcomerServlet;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class NewcomerServletTest {
    private JUnit4Mockery context = new JUnit4Mockery();
    private HttpServletRequest request = context.mock(HttpServletRequest.class);
    private HttpServletResponse response = context.mock(HttpServletResponse.class);
    private StringWriter writer = new StringWriter();
    private PrintWriter printWriter = new PrintWriter(writer);
    private NewcomerServlet servlet = new NewcomerServlet();
    private FakeSession session = new FakeSession();

    @Test
    public void happyPath() throws Exception {
        context.checking(new Expectations() {{
            oneOf(request).getRequestURI();
            will(returnValue("/index.html"));

            oneOf(response).setContentType("text/html");

            oneOf(request).getSession();
            will(returnValue(session));

            oneOf(request).getParameter("button");
            will(returnValue(null));

            oneOf(response).getWriter();
            will(returnValue(printWriter));
        }});
        servlet.doGet(request, response);
        String actual = writer.toString();
        assertThat(actual, containsString("Greetings, Newcomer!"));
    }

    @Test
    public void visitedIndexPage() throws Exception {
        context.checking(new Expectations() {{
            atLeast(2).of(request).getRequestURI();
            will(returnValue("/index.html"));

            atLeast(2).of(response).setContentType("text/html");

            atLeast(2).of(response).getWriter();
            will(returnValue(printWriter));

            atLeast(2).of(request).getSession();
            will(returnValue(session));

            atLeast(2).of(request).getParameter("button");
            will(returnValue("alreadyVisited"));
        }});
        servlet.doGet(request, response);
        servlet.doGet(request, response);
        String actual = writer.toString();
        assertThat(actual, containsString("Welcome back!"));
    }
}
