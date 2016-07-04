package crawler;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

import static java.util.stream.Collectors.toSet;

public class CrawlTask extends RecursiveAction {
    private final int id = new Random().nextInt(10000);

    private final Link link;
    private final CrawlReporter reporter;
    private Downloader downloader;
    private CrawlerLogger log;
    private int depthToCrawlTo;

    public CrawlTask(final Link link, final CrawlReporter reporter, final Downloader downloader, final CrawlerLogger log, final int depthToCrawlTo) {
        this.link = link;
        this.reporter = reporter;
        this.downloader = downloader;
        this.log = log;
        this.depthToCrawlTo = depthToCrawlTo;
    }

    @Override protected void compute() {
        if (reporter.hasCrawled(link)) {
            return;
        }
        if (link.depth > depthToCrawlTo) {
            return;
        }
        log.crawling(id, link);

        DownloadResult result = downloader.download(link);
        reporter.reportCrawled(link);
        result.process(this::reportInternalLinksSeen);
        result.process(this::reportExternalLinksSeen);
        result.process(this::reportStaticLinksSeen);
        result.process(this::submitTasksToCrawlUnseenInternalLinksOn);

        log.doneCrawling(id, link);
    }

    private void submitTasksToCrawlUnseenInternalLinksOn(final Page page) {
        final Set<CrawlTask> tasks = page.getLinksFromTagsOfType("a").stream()
                .filter(reporter::notYetCrawled)
                .filter(link::onSameDomainAs)
                .map(link -> new CrawlTask(link, reporter, downloader, log, depthToCrawlTo))
                .collect(toSet());
        invokeAll(tasks);
    }

    private void reportInternalLinksSeen(final Page page) {
        page.getLinksFromTagsOfType("a").stream()
                .filter(link::onSameDomainAs)
                .forEach(reporter::seenInternal);
    }

    private void reportExternalLinksSeen(final Page page) {
        page.getLinksFromTagsOfType("a").stream()
                .filter(link::onDifferentDomainAs)
                .forEach(reporter::seenExternal);
    }

    private void reportStaticLinksSeen(final Page page) {
        page.getLinksFromTagsOfType("link").forEach(reporter::seenStatic);
    }

}
