package greeting;

import com.google.common.io.ByteStreams;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class MainPageServletTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void indexPageContentRendered() throws Exception {
        MainPageServlet mainServlet = new MainPageServlet();
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);

        FakeOutputStream outputStream = new FakeOutputStream();
        context.checking(new Expectations() {{
            oneOf(response).getOutputStream();
            will(returnValue(outputStream));
        }});

        mainServlet.doGet(request, response);
        assertThat(outputStream.getWrittenBytes(), (equalTo(ByteStreams.toByteArray(getClass().getResourceAsStream("index.html")))));
    }

    class FakeOutputStream extends ServletOutputStream {

        private ByteArrayOutputStream bout = new ByteArrayOutputStream();

        @Override
        public void write(int b) throws IOException {
            bout.write(b);
        }

        public byte[] getWrittenBytes() {
            return bout.toByteArray();
        }
    }
}
