package greeting;

import com.google.common.collect.Lists;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Enumeration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class PageViewServletTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void firstTimeVisit() throws Exception {
        PageViewServlet viewServlet = new PageViewServlet();

        InputStream resourceStream = getClass().getResourceAsStream("template.html");

        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        StringWriter writer = new StringWriter();
        FakePrintStream outputStream = new FakePrintStream(writer);
        Enumeration<String> attributeNames = Collections.enumeration(Lists.newArrayList("pageName", "value"));

        context.checking(new Expectations() {{

            oneOf(request).getAttributeNames();
            will(returnValue(attributeNames));

            atLeast(1).of(request).getAttribute("pageName");
            will(returnValue("First Page"));

            atLeast(1).of(request).getAttribute("value");
            will(returnValue("Welcome you visit this page for the first time"));

            oneOf(response).getOutputStream();
            will(returnValue(outputStream));
        }});

        viewServlet.doPost(request, response);

        assertThat(writer.toString(), containsString("Welcome you visit this page for the first time"));
    }

    @Test
    public void pageAlreadyVisited() throws Exception {
        PageViewServlet pageViewServlet = new PageViewServlet();

        InputStream resourceStream = getClass().getResourceAsStream("template.html");

        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);

        StringWriter writer = new StringWriter();
        FakePrintStream outputStream = new FakePrintStream(writer);
        Enumeration<String> attributeNames = Collections.enumeration(Lists.newArrayList("pageName", "value"));

        context.checking(new Expectations() {{

            oneOf(request).getAttributeNames();
            will(returnValue(attributeNames));

            atLeast(1).of(request).getAttribute("pageName");
            will(returnValue("First Page"));

            atLeast(1).of(request).getAttribute("value");
            will(returnValue("You have been here before"));

            oneOf(response).getOutputStream();
            will(returnValue(outputStream));
        }});

        pageViewServlet.doPost(request, response);

        assertThat(writer.toString(), containsString("You have been here before"));
    }

    class FakePrintStream extends ServletOutputStream {
        PrintWriter out;

        public FakePrintStream(StringWriter writer) {
            out = new PrintWriter(writer);
        }

        @Override
        public void write(int b) throws IOException {
            out.write(b);
        }
    }
}
