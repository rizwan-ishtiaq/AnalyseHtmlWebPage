package com.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dto.AnalyseResult;
import com.util.HtmlParserHelper;

@Service
public class HtmlParseImpl implements HtmlParse {

	private static final Logger LOG = LoggerFactory.getLogger(HtmlParseImpl.class);
	private static final HtmlParserHelper HPH = new HtmlParserHelper();

	@Override
	public AnalyseResult startAnalysing(String url) throws IOException {
		if (url == null || url.trim().isEmpty()) {
			IllegalArgumentException exp = new IllegalArgumentException("URL can't be empty");
			LOG.error(exp.getMessage(), exp);
			throw exp;
		}
		LOG.debug("Input url {}", url);
		url = url.toLowerCase().startsWith("http") ? url.toLowerCase() : "http://" + url.toLowerCase();
		LOG.info("Starting URL {}", url);
		try {
			Document doc = Jsoup.connect(url).get();
			LOG.debug("URL get request completed");
			return analyseDocument(doc);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	private AnalyseResult analyseDocument(Document doc) throws MalformedURLException {
		LOG.trace("analyseDocument called with {}", doc);
		AnalyseResult retObj = new AnalyseResult();

		retObj.setHeadings(HPH.getHeadingCount(doc));
		retObj.setHtmlVersion(HPH.getHtmlVersion(doc));
		retObj.setLoginForm(HPH.isLoginPage(doc));
		retObj.setPageTitle(HPH.getTitle(doc));

		LOG.info("Collecting link from documetn ");
		List<String> allHypermediaLinks = HPH.getAllHypermediaLinks(doc);
		String host = new URL(doc.baseUri()).getHost();
		String nakedHost = host.startsWith("www.") ? host.substring(4) : host;
		Map<Boolean, List<String>> collect = allHypermediaLinks.stream()
				.collect(Collectors.groupingBy(str -> ((String) str).contains(nakedHost)));

		retObj.setBaseUrl(nakedHost);
		retObj.setExternalLinksCount(collect.containsKey(false) ? collect.get(false).size() : 0);
		retObj.setInternalLinksCount(collect.containsKey(true) ? collect.get(true).size() : 0);

		LOG.info("Health check on links {}", allHypermediaLinks);
		retObj.setLinksCheck(HPH.healthCheckOnLinks(allHypermediaLinks));
		LOG.trace("analyseDocument returning {}", retObj);
		return retObj;
	}

}
