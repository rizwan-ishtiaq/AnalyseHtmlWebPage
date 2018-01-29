package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dto.AnalyseResult;
import com.service.HtmlParse;

@Controller
public class RootController {

	@Autowired
	private HtmlParse service;

	@RequestMapping("/")
	public String showAnalysePage() {
		return "index";
	}

	@RequestMapping(value = "/", params = { "analyse" }, method = RequestMethod.POST)
	public String examinePage(final String inputUrl, final ModelMap model) {
		AnalyseResult result;
		try {
			result = service.startAnalysing(inputUrl);
			model.addAttribute("result", result);
		} catch (Exception e) {
			model.addAttribute("message", "Not able to analyse URL " + inputUrl + " '" + e.getMessage() + "'");
		}
		return "index";
	}

}
