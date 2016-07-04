package crawler;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Link {
    public static Link toLink(final String rawUrl) {
        return toLink(rawUrl, 0);
    }

    public static Link toLink(final String rawUrl, final int depth) {
        return new Link(toUrl(rawUrl), 0);
    }

    private final URL url;
    public final int depth;

    public Link(final URL url, final int depth) {
        this.url = url;
        this.depth = depth;
    }

    public boolean onSameDomainAs(final Link other) {
        return url.getHost().equals(other.url.getHost());
    }

    public boolean onDifferentDomainAs(final Link other) {
        return !onSameDomainAs(other);
    }

    @Override public String toString() {
        return url.toString();
    }

    @Override public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Link link = (Link) o;

        return new EqualsBuilder()
                .append(url, link.url)
                .isEquals();
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(url)
                .toHashCode();
    }

    private static URL toUrl(final String rawUrl) {
        try {
            final URL url = new URL(rawUrl);
            String port = url.getPort() == -1 ? "" : ":" + url.getPort();
            return new URL(url.getProtocol() + "://" + url.getHost() + port + url.getPath());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
