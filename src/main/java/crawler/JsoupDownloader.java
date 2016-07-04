package crawler;

import java.io.IOException;

import org.jsoup.Jsoup;

public class JsoupDownloader implements Downloader {
    private DownloadPolicy downloadPolicy;

    public JsoupDownloader(final DownloadPolicy downloadPolicy) {
        this.downloadPolicy = downloadPolicy;
    }

    @Override public DownloadResult download(final Link link) {
        if (!downloadPolicy.allows(link)) return unscrapableDownloadResult();
        try {
            final String content = Jsoup.connect(link.toString()).get().html();
            return new Page(link, content);
        } catch (IOException e) {
            e.printStackTrace();
            return failedDownloadResult();
        }
    }

    private DownloadResult unscrapableDownloadResult() {
        return page -> {
            //doNothing
        };
    }

    private DownloadResult failedDownloadResult() {
        return page -> {
            //doNothing
        };
    }

}
