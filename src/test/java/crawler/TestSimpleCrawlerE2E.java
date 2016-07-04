package crawler;

import java.io.File;
import java.io.IOException;

import fi.iki.elonen.SimpleWebServer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static crawler.Link.toLink;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class TestSimpleCrawlerE2E {
    private static final Link START_LINK = toLink("http://localhost:8181/page1.html");
    private static final Link OTHER_INTERNAL_LINK = toLink("http://localhost:8181/page2.html");

    private final CrawlReporter reporter = null;

    private final Crawler crawler = new MultithreadedCrawler(reporter);

    @Ignore
    @Test public void crawlsLinksFromSameDomain() throws IOException {
        crawler.startCrawlingFrom(START_LINK);

        assertThat(reporter.crawledLinks, containsInAnyOrder(START_LINK, OTHER_INTERNAL_LINK));
    }

    @Before public void setupTestWebServer() throws IOException {
        File base = new File(this.getClass().getClassLoader().getResource("sample_site").getFile());
        new SimpleWebServer("", 8181, base, true).start();
    }

}