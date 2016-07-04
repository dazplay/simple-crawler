package crawler;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static crawler.Link.toLink;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class CrawlTaskTest {
    @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final Link linkToCrawl = toLink("http://myDomain.com");
    private final Link scrapedLink1 = toLink("http://myDomain.com/anInternalLink");
    private final Link scrapedLink2 = toLink("http://myDomain.com/anotherInternalLink");

    private final CrawlReporter reporter = new CrawlReporter();

    @Test public void reportsInternalLinksAsSeenWhenScrapedFromPage() {
        new CrawlTask(linkToCrawl, reporter, dummyDownloader(), dummyLogger(),1).compute();

        assertThat(reporter.internalLinksSeen, containsInAnyOrder(scrapedLink1, scrapedLink2));
    }

    private CrawlerLogger dummyLogger() {
        return new CrawlerLogger() {
            @Override public void crawling(final int id, final Link link) {
            }

            @Override public void doneCrawling(final int id, final Link link) {
            }

            @Override public PrintStream out() {
                return null;
            }
        };
    }

    private Downloader dummyDownloader() {
        return link -> new Page(linkToCrawl, content());
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