package crawler;

import java.io.IOException;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;

import static crawler.Link.toLink;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class PageTest {

    private final Link linkToPage = toLink("http://myDomain.com");
    private final Link scrapedLink1 = toLink("http://myDomain.com/anInternalLink");
    private final Link scrapedLink2 = toLink("http://myDomain.com/anotherInternalLink");
    private final Link scrapedLink3 = toLink("http://external-from-page1.com");

    @Test public void canScrapeAnchorsAsLinksFromPage() {
        Page page = new Page(linkToPage, content());
        assertThat(page.getLinksFromTagsOfType("a"),
                containsInAnyOrder(scrapedLink1, scrapedLink2, scrapedLink3));
    }

    private String content() {
        URL url = Resources.getResource("sample_site/dummyPage");
        try {
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}