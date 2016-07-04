package crawler;

import java.util.function.Consumer;

public interface DownloadResult {
    void process(Consumer<Page> function);
}
