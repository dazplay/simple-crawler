package crawler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CrawlReporter {
    protected final Set<Link> crawledLinks = ConcurrentHashMap.newKeySet();
    protected final Set<Link> staticLinksSeen = ConcurrentHashMap.newKeySet();
    protected final Set<Link> internalLinksSeen = ConcurrentHashMap.newKeySet();
    protected final Set<Link> externalLinksSeen = ConcurrentHashMap.newKeySet();

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

    public void seenExternal(final Link link) {
        externalLinksSeen.add(link);
    }

    public void seenStatic(final Link link) {
        staticLinksSeen.add(link);
    }

    public void printReport() {
        System.out.println("\n----------------------DONE!");
        System.out.println("Number of crawled links: " + crawledLinks.size());
        System.out.println("Number of links on same domain as seed seen: " + internalLinksSeen.size());
        System.out.println("Number of static links seen: " + staticLinksSeen.size());
        System.out.println("Number of external links seen: " + externalLinksSeen.size());

        System.out.println("Crawled: " + crawledLinks);
        System.out.println("Links on same domain seen: " + internalLinksSeen);
        System.out.println("Static links seen: " + staticLinksSeen);
        System.out.println("External links seen: " + externalLinksSeen);
    }
}
