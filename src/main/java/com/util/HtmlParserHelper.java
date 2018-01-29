package com.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlParserHelper {

	private static final Logger LOG = LoggerFactory.getLogger(HtmlParserHelper.class);

	/**
	 * Parse HTML and look for {@literal <!DOCTYPE>} element
	 * 
	 * @param doc
	 * @return String: version of HTML
	 */
	public String getHtmlVersion(Document doc) {
		return doc.childNodes().stream().filter(node -> node instanceof DocumentType).map(node -> (DocumentType) node)
				.map(dt -> dt.attr("publicid") == "" ? "HTML 5" : dt.attr("publicid")).collect(Collectors.joining());
	}

	/**
	 * Look for fist {@literal <title>} element in HTML. In case element not found
	 * return empty String
	 * 
	 * @param doc
	 * @return String: first page title
	 */
	public String getTitle(Document doc) {
		Element titleElement = doc.selectFirst("title");
		return titleElement == null ? "" : doc.selectFirst("title").text();
	}

	/**
	 * Look HTML for {@literal 
	 * 
	<h1> 
	 * 
	<h2> 
	 * 
	<h3> 
	 * 
	<h4> 
	 * 
	<h5> 
	 * 
	<h6>} elements one by one
	 * 
	 * @param doc
	 * @return {@code Map<String, Integer>} containing headings h1 to h6
	 */
	public Map<String, Integer> getHeadingCount(Document doc) {
		Map<String, Integer> retMap = new HashMap<>();
		retMap.put("h1", doc.select("h1").size());
		retMap.put("h2", doc.select("h2").size());
		retMap.put("h3", doc.select("h3").size());
		retMap.put("h4", doc.select("h4").size());
		retMap.put("h5", doc.select("h5").size());
		retMap.put("h6", doc.select("h6").size());
		return retMap;
	}

	/**
	 * Logic is simple, if page contain single {@literal <input>} with type
	 * {@literal password} then it will be login page. We try to make this logic
	 * more precise by looking for keywords 'sign in', 'login' etc
	 * 
	 * @param doc
	 * @return boolean
	 */
	public boolean isLoginPage(Document doc) {
		return doc.select("input[type=password]").size() == 1;
	}

	/**
	 * Extract hypermedia links from HTML. Method will look for
	 * {@literal [href] or [src]} attributes of {@literal [a/link] or [img]}
	 * elements respectively
	 * 
	 * @param doc
	 * @return {@code List<String>}
	 */
	public List<String> getAllHypermediaLinks(Document doc) {
		return Stream
				.concat(doc.select("[src]").stream().map(node -> node.attr("abs:src")),
						doc.select("a[href],link[href]").stream().map(node -> node.attr("abs:href")))
				.collect(Collectors.toList());
	}

	/**
	 * Use java 8 stream parallel processing to determine health check of links
	 * 
	 * @param links
	 * @return {@code Map<String, Integer>} URL and code
	 */
	public Map<String, Integer> healthCheckOnLinks(List<String> links) {
		LOG.trace("healthCheckOnLinks is called with {} links", links.size());
		Map<String, Integer> retMap = new ConcurrentHashMap<>();
		links.parallelStream().forEach(link -> retMap.put(link, getUrlStatusCode(link)));
		return retMap;
	}

	/**
	 * Try to connect with URL using HttpURLConnection
	 * 
	 * @param url
	 * @return Integer: Response Code
	 */
	public Integer getUrlStatusCode(String url) {
		LOG.trace("getUrlStatusCode called with {}", url);
		try {
			URL siteURL = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
			connection.connect();
			return connection.getResponseCode();
		} catch (Exception e) {
			LOG.error(url + " got exception returning default 404 to user", e);
		}
		return 404;
	}

}
