package crawler;

public interface DownloadPolicy {
    boolean allows(Link link);
}
