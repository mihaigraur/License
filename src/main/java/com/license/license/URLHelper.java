package com.license.license;

import javax.servlet.http.HttpServletRequest;

public class URLHelper {
    public static String getUrlSite(HttpServletRequest request) {
        String urlSite = request.getRequestURL().toString();
        return urlSite.replace(request.getServletPath(), "");
    }
}