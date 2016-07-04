package crawler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CrawlReporter {
    protected final Set<Link> crawledLinks = ConcurrentHashMap.newKeySet();
    protected final Set<Link> internalLinksSeen = ConcurrentHashMap.newKeySet();

    public boolean hasCrawled(final Link link) {
        return crawledLinks.contains(link);
    }

    public boolean notYetCrawled(final Link link) {
        return !hasCrawled(link);
    }

    public void reportCrawled(final Link link) {
        crawledLinks.add(link);
    }

    public void seenInternal(final Link link) {
        internalLinksSeen.add(link);
    }

    public void printReport() {
        System.out.println("DONE! Crawled: " + crawledLinks.size() + " pages");
        System.out.println("Crawled: " + crawledLinks);
    }
}
