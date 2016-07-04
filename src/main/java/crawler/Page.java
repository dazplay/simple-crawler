package crawler;

import java.util.Set;

import org.jsoup.Jsoup;

import static java.util.stream.Collectors.toSet;

public class Page {
    private Link link;
    private String content;

    public Page(final Link link, final String content) {
        this.link = link;
        this.content = content;
    }

    public Set<Link> getLinksFromTagsOfType(final String tagType) {
        final Set<Link> linksFound = Jsoup.parse(content, link.toString()).select(tagType).stream()
                .map(element -> element.absUrl("href"))
                .filter(url -> !url.equals(""))
                .map(Link::toLink)
                .collect(toSet());
        return linksFound;
    }

}