package crawler;

import java.io.IOException;

import org.jsoup.Jsoup;

public class JsoupDownloader implements Downloader {
    @Override public Page download(final Link link) {
        try {
            final String content = Jsoup.connect(link.toString()).get().html();
            return new Page(link, content);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
