package crawler;

import java.io.File;
import java.io.IOException;

import fi.iki.elonen.SimpleWebServer;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static crawler.Link.toLink;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

public class TestSimpleCrawlerE2E {
    private static final Link START_LINK = toLink("http://localhost:8181/page1.html");
    private static final Link OTHER_INTERNAL_LINK = toLink("http://localhost:8181/page2.html");
    private static final Link LINK_TO_FILE = toLink("http://localhost:8181/someFileWeDontWantToScrape.pdf");

    private final CrawlReporter reporter = new CrawlReporter();

    private final MultithreadedCrawler crawler = MultithreadedCrawler.createUsing(reporter);

    @Ignore
    @Test public void doesNotCrawlFiles() {
        crawler.startCrawlingFrom(START_LINK, 1);

        assertThat(reporter.crawledLinks, not(hasItem(LINK_TO_FILE)));
    }

    @Test public void crawlsLinksFromSameDomain() throws IOException {
        crawler.startCrawlingFrom(START_LINK, 1);

        assertThat(reporter.crawledLinks, hasItems(START_LINK, OTHER_INTERNAL_LINK));
    }

    @BeforeClass public static void setupTestWebServer() throws IOException {
        File base = new File(TestSimpleCrawlerE2E.class.getClassLoader().getResource("sample_site").getFile());
        new SimpleWebServer("", 8181, base, true).start();
    }

}