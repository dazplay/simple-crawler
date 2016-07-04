package crawler;

import java.util.stream.Stream;

public class DisallowFilesPolicy implements DownloadPolicy {
    private static String[] DISALLOWED_EXTENSIONS = {"jpg", "pdf", "png", "tar", "deb", "bin", "zip", "rpm", "xz", "gz", "sha1", "md5", "jar", "asc"};

    @Override public boolean allows(final Link link) {
        String rawUrl = link.toString();
        return !Stream.of(DISALLOWED_EXTENSIONS).anyMatch(rawUrl::endsWith);
    }
}
