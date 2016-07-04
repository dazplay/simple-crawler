package crawler;

import java.util.concurrent.ForkJoinPool;

import static crawler.Link.toLink;
import static java.util.concurrent.ForkJoinPool.defaultForkJoinWorkerThreadFactory;
import static java.util.concurrent.TimeUnit.SECONDS;

public class MultithreadedCrawler {
    public static final long TIMEOUT = 100;
    public static final int PARALLELISM = 10;
    private CrawlReporter reporter;
    private Downloader downloader;

    public MultithreadedCrawler(final CrawlReporter reporter, final Downloader downloader) {
        this.reporter = reporter;
        this.downloader = downloader;
    }

    public void startCrawlingFrom(final Link startLink) {
        ForkJoinPool pool = new ForkJoinPool(PARALLELISM, defaultForkJoinWorkerThreadFactory, null, true);

        reporter.seenInternal(startLink);
        final CrawlTask seedTask = new CrawlTask(startLink, reporter, downloader);

        pool.submit(seedTask);
        pool.awaitQuiescence(TIMEOUT, SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        CrawlReporter reporter = new CrawlReporter();
        MultithreadedCrawler.createUsing(reporter).startCrawlingFrom(toLink("https://goshawkdb.io"));
        reporter.printReport();
    }

    public static MultithreadedCrawler createUsing(final CrawlReporter reporter) {
        return new MultithreadedCrawler(reporter, new JsoupDownloader(new DisallowFilesPolicy()));
    }
}
