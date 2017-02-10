package greeting;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class PageCatalog {
    private static final Map<String, String> pageCatalog = ImmutableMap.of(
            "page1", "First Page",
            "page2", "Second Page",
            "page3", "Third Page"
    );

    public String getPageByName(String name) {
        return pageCatalog.get(name);
    }
}
