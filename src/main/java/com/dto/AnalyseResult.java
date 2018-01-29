package com.dto;

import java.util.Map;

public class AnalyseResult {

	private String baseUrl;
	private String htmlVersion;
	private String pageTitle;
	private Map<String, Integer> headings;
	private Integer internalLinksCount;
	private Integer externalLinksCount;
	private boolean loginForm;
	private Map<String, Integer> linksCheck;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getHtmlVersion() {
		return htmlVersion;
	}

	public void setHtmlVersion(String htmlVersion) {
		this.htmlVersion = htmlVersion;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public Map<String, Integer> getHeadings() {
		return headings;
	}

	public void setHeadings(Map<String, Integer> headings) {
		this.headings = headings;
	}

	public Integer getInternalLinksCount() {
		return internalLinksCount;
	}

	public void setInternalLinksCount(Integer internalLinksCount) {
		this.internalLinksCount = internalLinksCount;
	}

	public Integer getExternalLinksCount() {
		return externalLinksCount;
	}

	public void setExternalLinksCount(Integer externalLinksCount) {
		this.externalLinksCount = externalLinksCount;
	}

	public boolean isLoginForm() {
		return loginForm;
	}

	public void setLoginForm(boolean loginForm) {
		this.loginForm = loginForm;
	}

	public Map<String, Integer> getLinksCheck() {
		return linksCheck;
	}

	public void setLinksCheck(Map<String, Integer> linksCheck) {
		this.linksCheck = linksCheck;
	}

}
