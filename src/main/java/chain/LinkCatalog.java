package chain;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class LinkCatalog {
    private final List<Link> links;

    public LinkCatalog(List<Link> links) {
        this.links = links;
    }

    public List<Link> getLinksExcluding(String excludedLinkName) {
        return links.stream().filter(link -> !excludedLinkName.equals(link.linkName)).collect(Collectors.toList());
    }

}
