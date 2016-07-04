package crawler;

import org.junit.Test;

import static crawler.Link.toLink;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class LinkTest {

    @Test public void discardsFragments() {
        final String rawUrl = "https://myDomain.com#aFragment";
        assertThat(toLink(rawUrl).toString(), equalTo("https://myDomain.com"));
    }

    @Test public void discardsQueryParams() {
        final String rawUrl = "https://myDomain.com?q1=foo";
        assertThat(toLink(rawUrl).toString(), equalTo("https://myDomain.com"));
    }

    @Test public void preservesFilePathComponentOfUrl() {
        final String rawUrl = "https://goshawkdb.io/features.html";
        assertThat(toLink(rawUrl).toString(), equalTo(rawUrl));
    }

    @Test public void preservesPortsIfPresent() {
        final String rawUrl = "https://myDomain.com:8181";
        assertThat(toLink(rawUrl).toString(), equalTo("https://myDomain.com:8181"));
    }

    @Test public void handlesNonPresentPort() {
        final String rawUrl = "https://myDomain.com";
        assertThat(toLink(rawUrl).toString(), equalTo("https://myDomain.com"));
    }

    @Test public void twoLinksWithSameURLAreEqual() {
        final String rawUrl = "https://goshawkdb.io/features.html";
        assertThat(toLink(rawUrl), equalTo(toLink(rawUrl)));
    }
}