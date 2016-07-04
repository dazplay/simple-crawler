package crawler;

import java.io.PrintStream;

public interface CrawlerLogger {

    default void crawling(int id, Link link) {
        out().printf("%d Crawling %s\n", id, link);
    }

    default void doneCrawling(int id, final Link link) {
        out().println(id + " done crawling " + link);
    }

    PrintStream out();
}
