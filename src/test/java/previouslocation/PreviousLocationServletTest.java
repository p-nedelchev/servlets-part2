package previouslocation;

import com.google.common.net.HttpHeaders;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import previouslocation.servlet.PreviousLocationServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class PreviousLocationServletTest {
    private JUnit4Mockery context = new JUnit4Mockery();
    private HttpServletRequest request = context.mock(HttpServletRequest.class);
    private HttpServletResponse response = context.mock(HttpServletResponse.class);
    private PreviousLocationServlet servlet = new PreviousLocationServlet();
    private StringWriter writer = new StringWriter();
    private PrintWriter printWriter = new PrintWriter(writer);

    @Test
    public void happyPath() throws Exception {
        context.checking(new Expectations() {{
            oneOf(request).getHeader(HttpHeaders.REFERER);
            will(returnValue(null));

            oneOf(request).getRequestURI();
            will(returnValue("/page1.html"));

            oneOf(response).setContentType("text/html");

            oneOf(response).getWriter();
            will(returnValue(printWriter));
        }});
        servlet.doGet(request, response);
        String actual = writer.toString();
        assertThat(actual, containsString("You are now on: Page1"));
        assertThat(actual, containsString("The last page you were on was: N/A"));
    }

    @Test
    public void comingFromSamePage() throws Exception {
        context.checking(new Expectations() {{
            oneOf(request).getHeader(HttpHeaders.REFERER);
            will(returnValue("localhost:8080/page1.html"));

            oneOf(request).getRequestURI();
            will(returnValue("/page1.html"));
            oneOf(response).setContentType("text/html");

            oneOf(response).getWriter();
            will(returnValue(printWriter));
        }});
        servlet.doGet(request, response);
        String actual = writer.toString();
        assertThat(actual, containsString("You are now on: Page1"));
        assertThat(actual, containsString("The last page you were on was: localhost:8080/page1.html"));
    }

    @Test
    public void comingFromDifferentPage() throws Exception {
        context.checking(new Expectations() {{
            oneOf(request).getHeader(HttpHeaders.REFERER);
            will(returnValue("localhost:8080/page2.html"));

            oneOf(request).getRequestURI();
            will(returnValue("/page3.html"));
            oneOf(response).setContentType("text/html");

            oneOf(response).getWriter();
            will(returnValue(printWriter));

        }});
        servlet.doGet(request, response);
        String actual = writer.toString();
        assertThat(actual, containsString("You are now on: Page3"));
        assertThat(actual, containsString("The last page you were on was: localhost:8080/page2.html"));
    }
}
