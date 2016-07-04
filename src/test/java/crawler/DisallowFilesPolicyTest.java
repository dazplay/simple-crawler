package crawler;

import java.util.stream.Stream;

import org.junit.Test;

import static crawler.Link.toLink;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DisallowFilesPolicyTest {

    String[] disallowed = {
            "https://goshawkdb.io/talks/20160308_goshawkdb_qcon.pdf",
            "https://goshawkdb.io/talks/20160308_goshawkdb_qcon.tar",
            "https://goshawkdb.io/talks/20160308_goshawkdb_qcon.png",
            "https://goshawkdb.io/talks/20160308_goshawkdb_qcon.deb",
            "https://goshawkdb.io/talks/20160308_goshawkdb_qcon.bin",
            "https://goshawkdb.io/talks/20160308_goshawkdb_qcon.zip",
            "https://goshawkdb.io/talks/20160308_goshawkdb_qcon.rpm",
            "https://goshawkdb.io/talks/20160308_goshawkdb_qcon.gz"
    };

    String[] allowed = {
            "https://goshawkdb.io",
            "https://goshawkdb.io/talks",
            "https://goshawkdb.io/talks/",
            "https://goshawkdb.io/talks.html",
            "https://goshawkdb.io/talks.htm"
    };

    DownloadPolicy policy = new DisallowFilesPolicy();

    @Test public void disallowFiles() {
        Stream.of(allowed).forEach(rawUrl -> assertThat(policy.allows(toLink(rawUrl)), is(true)));
    }

    @Test public void allowNonFiles() {
        Stream.of(disallowed).forEach(rawUrl -> assertThat(policy.allows(toLink(rawUrl)), is(false)));
    }

}