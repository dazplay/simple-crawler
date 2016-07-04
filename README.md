# Basic web-crawler exercise

A maven app, so can be run using:
```shell
mvn clean compile exec:java -Dexec.args="<seedUrl> <max depth to crawl>"
eg: mvn clean compile exec:java -Dexec.args="https://goshawkdb.io/ 2"
```

## Spec

Your task is to write a simple web crawler in a language of your choice. Could be Java8, Scala, NodeJS, Clojure etc (I and Buildit would recommend using your strongest development language)

The crawler should be limited to one domain. Given a starting URL – say wiprodigital.com - it should visit all pages within the domain, but not follow the links to external sites such as Google or Twitter.

The output should be a simple site map, showing links to other pages under the same domain, links to static content such as images, and to external URLs.



