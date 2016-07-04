package crawler;

import java.util.concurrent.ForkJoinPool;

import static crawler.Link.toLink;
import static java.util.concurrent.ForkJoinPool.defaultForkJoinWorkerThreadFactory;
import static java.util.concurrent.TimeUnit.SECONDS;

public class MultithreadedCrawler {
    public static final long TIMEOUT = 100;
    public static final int PARALLELISM = 5;
    private CrawlReporter reporter;
    private Downloader downloader;
    private CrawlerLogger logger;

    public MultithreadedCrawler(final CrawlReporter reporter, final Downloader downloader, final CrawlerLogger logger) {
        this.reporter = reporter;
        this.downloader = downloader;
        this.logger = logger;
    }

    public void startCrawlingFrom(final Link startLink, int depthToCrawlTo) {
        ForkJoinPool pool = new ForkJoinPool(PARALLELISM, defaultForkJoinWorkerThreadFactory, null, true);

        reporter.seenInternal(startLink);
        final CrawlTask seedTask = new CrawlTask(startLink, reporter, downloader, logger, depthToCrawlTo);

        pool.submit(seedTask);
        pool.awaitQuiescence(TIMEOUT, SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            System.out.println("please give a seed URL and depth to crawl to as parameters.");
            return;
        }

        CrawlReporter reporter = new CrawlReporter();
        int depth = Integer.valueOf(args[1]);
        MultithreadedCrawler.createUsing(reporter).startCrawlingFrom(toLink(args[0], 0), depth);
        reporter.printReport();
    }

    public static MultithreadedCrawler createUsing(final CrawlReporter reporter) {
        return new MultithreadedCrawler(
                reporter,
                new JsoupDownloader(new DisallowFilesPolicy()),
                stdOutLogger());
    }


    private static CrawlerLogger stdOutLogger() {
        return () -> System.out;
    }
}
