package com.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.util.HtmlParserHelper;

public class HtmlParserHelperTest {

	private HtmlParserHelper hph = new HtmlParserHelper();

	// Version test
	@Test
	public void htmlVersion5Test() {
		Document doc = Jsoup.parse("<!DOCTYPE html>");
		assertEquals("Should return version 5", "HTML 5", hph.getHtmlVersion(doc));
	}

	@Test
	public void htmlVersionTest() {
		Document doc = Jsoup.parse(
				"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		assertTrue("Should return version", hph.getHtmlVersion(doc).contains("4.01"));
	}

	// Heading test
	@Test
	public void headingCaseSensitiveTest() {
		Document doc = Jsoup.parse("<H1></H1><h2>heading 2</h2>");
		Map<String, Integer> hMap = hph.getHeadingCount(doc);
		assertTrue("Should be return single h1 heading", hMap.get("h1") == 1);
		assertTrue("Should be return single h2 heading", hMap.get("h2") == 1);
		assertNotNull("Should have h6 heading", hMap.get("h6"));
	}

	@Test
	public void allHeadingTest() {
		Document doc = Jsoup.parse("<H1></H1><h2></h2><h3></h3><h4></h4><h5></h5><h6></h6><h7></h7>");
		Map<String, Integer> hMap = hph.getHeadingCount(doc);
		assertTrue("Should be return single h1 heading", hMap.get("h1") == 1);
		assertTrue("Should be return single h2 heading", hMap.get("h2") == 1);
		assertTrue("Should be return single h3 heading", hMap.get("h3") == 1);
		assertTrue("Should be return single h4 heading", hMap.get("h4") == 1);
		assertTrue("Should be return single h5 heading", hMap.get("h5") == 1);
		assertTrue("Should be return single h6 heading", hMap.get("h6") == 1);
		assertNull("Should not have h7 heading", hMap.get("h7"));
	}

	// page title test
	@Test
	public void pageTitleTest() {
		Document doc = Jsoup.parse("<title>hello</title>");
		assertEquals("Title should return", "hello", hph.getTitle(doc));
	}

	@Test
	public void pageTitleNotExistTest() {
		Document doc = Jsoup.parse("<html><head></head></html>");
		assertEquals("No page title", "", hph.getTitle(doc));
	}

	// Detect login page
	@Test
	public void loginFormTest() {
		Document doc = Jsoup.parse("<html><head></head><body><input/><input type='password'/><body></html>");
		assertTrue("Signle password field consider it login page", hph.isLoginPage(doc));
	}

	@Test
	public void twoPasswordFieldsTest() {
		Document doc = Jsoup.parse("<html><body><input type='password'/><input type='password'/><body></html>");
		assertFalse("Two password field consider it non login page", hph.isLoginPage(doc));
	}

	@Test
	public void noFieldsTest() {
		Document doc = Jsoup.parse("<html><body><body></html>");
		assertFalse("No password field in page", hph.isLoginPage(doc));
	}

	// Hypermedia Links
	@Test
	public void extractHyperLinkTest() {
		StringBuilder links = new StringBuilder("<link href='http://www.abc.com' />")
				.append("<a href='https://www.google.com.bh/' />").append("<img src='http://images/abc.png' />");
		Document doc = Jsoup.parse(links.toString());
		List<String> allHypermediaLinks = hph.getAllHypermediaLinks(doc);
		assertNotNull("Should found links", allHypermediaLinks);
		assertEquals("Should found exactly 3 links", 3, allHypermediaLinks.size());
		assertTrue("http://www.abc.com should exist into list", allHypermediaLinks.contains("http://www.abc.com"));
		assertTrue("https://www.google.com.bh/ should exist into list",
				allHypermediaLinks.contains("https://www.google.com.bh/"));
		assertTrue("http://images/abc.png should exist into list",
				allHypermediaLinks.contains("http://images/abc.png"));
	}

	@Test
	public void protocolMissingLinksTest() {
		Document doc = Jsoup.parse("<a href='www.google.com.bh' />");
		List<String> allHypermediaLinks = hph.getAllHypermediaLinks(doc);
		assertEquals("Should found exactly 1 element", 1, allHypermediaLinks.size());
		assertFalse("www.google.com.bh should not exist into list", allHypermediaLinks.contains("www.google.com.bh"));
		assertTrue("Jsoup will put empty item into list", allHypermediaLinks.get(0).isEmpty());
	}

	@Test
	public void emptyLinkTest() {
		Document doc = Jsoup.parse("<html><body><body></html>");
		List<String> allHypermediaLinks = hph.getAllHypermediaLinks(doc);
		assertNotNull("At least object should exist", allHypermediaLinks);
		assertTrue("Should be empty", allHypermediaLinks.isEmpty());
	}

}
