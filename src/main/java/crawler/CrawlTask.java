package crawler;

import java.util.Set;
import java.util.concurrent.RecursiveAction;

import static java.util.stream.Collectors.toSet;

public class CrawlTask extends RecursiveAction {
    private final Link link;
    private final CrawlReporter reporter;
    private Downloader downloader;

    public CrawlTask(final Link link, final CrawlReporter reporter, final Downloader downloader) {
        this.link = link;
        this.reporter = reporter;
        this.downloader = downloader;
    }

    @Override protected void compute() {
        if (reporter.hasCrawled(link)) {
            return;
        }

        DownloadResult result = downloader.download(link);
        reporter.reportCrawled(link);
        result.process(this::submitTasksToCrawlUnseenInternalLinksOn);
        result.process(this::reportInternalLinksSeen);
    }

    private void submitTasksToCrawlUnseenInternalLinksOn(final Page page) {
        final Set<CrawlTask> tasks = page.getLinksFromTagsOfType("a").stream()
                .filter(reporter::notYetCrawled)
                .filter(link::onSameDomainAs)
                .map(link -> new CrawlTask(link, reporter, downloader))
                .collect(toSet());
        invokeAll(tasks);
    }

    private void reportInternalLinksSeen(final Page page) {
        page.getLinksFromTagsOfType("a").stream()
                .filter(link::onSameDomainAs)
                .forEach(reporter::seenInternal);
    }

}
