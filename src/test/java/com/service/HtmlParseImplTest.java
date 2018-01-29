package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.dto.AnalyseResult;

public class HtmlParseImplTest {

	private final static HtmlParseImpl obj = new HtmlParseImpl();

	@Test
	public void analyseDocumentTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Document doc = Jsoup.parse("<title>test</title>");
		doc.setBaseUri("http://www.testurl.com.uk");

		Method analyseDocumentMethod = HtmlParseImpl.class.getDeclaredMethod("analyseDocument", Document.class);
		analyseDocumentMethod.setAccessible(true);
		AnalyseResult result = (AnalyseResult) analyseDocumentMethod.invoke(obj, doc);

		assertNotNull("AnalyseResult should return", result);
		assertEquals("Base url match", "testurl.com.uk", result.getBaseUrl());
		assertEquals("page title should return", "test", result.getPageTitle());
		assertNotNull("Html Version will always present", result.getHtmlVersion());
		assertEquals("Version will empty, if DOCTYPE not define", "", result.getHtmlVersion());
	}

}
