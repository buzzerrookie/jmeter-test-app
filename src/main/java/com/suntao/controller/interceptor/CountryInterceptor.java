package com.suntao.controller.interceptor;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class CountryInterceptor implements HandlerInterceptor {
    private static final String APP_SECRET = "appSecret";
    private static final String HEADER_SIGN = "sign";
    private static final String PARAM_TIMESTAMP = "timestamp";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String method = request.getMethod();
        String signField = request.getHeader(HEADER_SIGN);
        if (signField == null) {
            throw new IllegalArgumentException("Sign error");
        }
        String timestamp = request.getParameter(PARAM_TIMESTAMP);
        if (timestamp == null) {
            throw new IllegalArgumentException("Invalid Request");
        }
        long now = Instant.now().toEpochMilli();
        if (Math.abs(now - Long.parseLong(timestamp)) > 1000 * 60 * 5) {
            throw new IllegalArgumentException("Invalid Request");
        }
        Enumeration<String> params = request.getParameterNames();
        List<String> strs = Collections.list(params).stream().filter(
                        name -> !name.equals(HEADER_SIGN)).map(name -> name + request.getParameter(name))
                .sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        String strToSign = APP_SECRET + String.join("", strs);
        if ("POST".equals(method)) {
            strToSign += IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        }
        strToSign += APP_SECRET;
        String sign = DigestUtils.md5Hex(strToSign.getBytes(StandardCharsets.UTF_8));
        boolean result = sign.equalsIgnoreCase(signField);
        if (!result) {
            throw new IllegalArgumentException("Sign error");
        }
        return true;
    }
}
