package com.service;

import java.io.IOException;

import com.dto.AnalyseResult;

public interface HtmlParse {

	/**
	 * Analyze HTML version, page title, headings used in page, number of external
	 * and internal links used in the page and whether page contain login form. It
	 * will also provide the detail health check on links used into web page using
	 * multi-threading
	 * 
	 * @param url
	 *            mandatory String URL to analyze
	 * @return POJO object AnalyseResult
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	AnalyseResult startAnalysing(String url) throws IOException;

}
