package crawler;

import java.util.Set;
import java.util.function.Consumer;

import org.jsoup.Jsoup;

import static java.util.stream.Collectors.toSet;

public class Page implements DownloadResult {
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
                .map(this::toLink)
                .collect(toSet());
        return linksFound;
    }

    private Link toLink(final String rawUrl) {
        return Link.toLink(rawUrl, link.depth + 1);
    }

    @Override public void process(final Consumer<Page> action) {
        action.accept(this);
    }

}
