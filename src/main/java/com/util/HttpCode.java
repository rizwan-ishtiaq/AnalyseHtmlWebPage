package com.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Provide the meaning for HTTP response code for humans readability. This class
 * should be use to read only and meant to be thread safe class
 */
public final class HttpCode {

	private static final Map<Integer, String> codeMap = new HashMap<>();

	static {
		// codes are taken from https://httpstatuses.com/
		codeMap.put(1, "Informational");
		codeMap.put(100, "Continue");
		codeMap.put(101, "Switching Protocols");
		codeMap.put(102, "Processing");
		codeMap.put(2, "Success");
		codeMap.put(200, "OK");
		codeMap.put(201, "Created");
		codeMap.put(202, "Accepted");
		codeMap.put(203, "Non-authoritative Information");
		codeMap.put(204, "NO CONTENT");
		codeMap.put(205, "Reset Content");
		codeMap.put(206, "Partial Content");
		codeMap.put(207, "Multi-Status");
		codeMap.put(208, "Already Reported");
		codeMap.put(226, "IM Used");
		codeMap.put(3, "Redirection");
		codeMap.put(300, "Multiple Choices");
		codeMap.put(301, "Moved Permanently");
		codeMap.put(302, "Found");
		codeMap.put(303, "See Other");
		codeMap.put(304, "Not Modified");
		codeMap.put(305, "Use Proxy");
		codeMap.put(307, "Temporary Redirect");
		codeMap.put(308, "Permanent Redirect");
		codeMap.put(4, "Client Error");
		codeMap.put(400, "Bad Request");
		codeMap.put(401, "Unauthorized");
		codeMap.put(402, "Payment Required");
		codeMap.put(403, "Forbidden");
		codeMap.put(404, "Not Found");
		codeMap.put(405, "Method Not Allowed");
		codeMap.put(406, "Not Acceptable");
		codeMap.put(407, "Proxy Authentication Required");
		codeMap.put(408, "Request Timeout");
		codeMap.put(409, "Conflict");
		codeMap.put(410, "Gone");
		codeMap.put(411, "Length Required");
		codeMap.put(412, "Precondition Failed");
		codeMap.put(413, "Payload Too Large");
		codeMap.put(414, "Request-URI Too Long");
		codeMap.put(415, "Unsupported Media Type");
		codeMap.put(416, "Requested Range Not Satisfiable");
		codeMap.put(417, "Expectation Failed");
		codeMap.put(418, "I'm a teapot");
		codeMap.put(421, "Misdirected Request");
		codeMap.put(422, "Unprocessable Entity");
		codeMap.put(423, "Locked");
		codeMap.put(424, "Failed Dependency");
		codeMap.put(426, "Upgrade Required");
		codeMap.put(428, "Precondition Required");
		codeMap.put(429, "Too Many Requests");
		codeMap.put(431, "Request Header Fields Too Large");
		codeMap.put(444, "Connection Closed Without Response");
		codeMap.put(451, "Unavailable For Legal Reasons");
		codeMap.put(499, "Client Closed Request");
		codeMap.put(5, "Server Error");
		codeMap.put(500, "Internal Server Error");
		codeMap.put(501, "Not Implemented");
		codeMap.put(502, "Bad Gateway");
		codeMap.put(503, "Service Unavailable");
		codeMap.put(504, "Gateway Timeout");
		codeMap.put(505, "HTTP Version Not Supported");
		codeMap.put(506, "Variant Also Negotiates");
		codeMap.put(507, "Insufficient Storage");
		codeMap.put(508, "Loop Detected");
		codeMap.put(510, "Not Extended");
		codeMap.put(511, "Network Authentication Required");
		codeMap.put(599, "Network Connect Timeout Error");
	}

	public static String get(Integer code) {
		return codeMap.get(code);
	}

}
