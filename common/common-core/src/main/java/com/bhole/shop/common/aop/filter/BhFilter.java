package com.bhole.shop.common.aop.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.bhole.shop.common.base.entity.UserInfo;
import com.bhole.shop.common.base.entity.UserLocal;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @program: bhole-shop-common-core
 * @description:
 * @author: joke
 * @date: 2023/6/5 15:33
 * @version: 1.0
 */
@WebFilter(urlPatterns = "/*", filterName = "BhFilter")
public class BhFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(BhFilter.class);

    public static final String AUTHORIZATION = "authorization";
    private static final String USER_KEY = "user";
    public static final String HEADER_USER_ID = "userId";
    public static final String HEADER_USER_NAME = "userName";
    public static final String HEADER_USER_TYPE = "userType";


    public static final String HEADER_EMP_ID = "empId";
    public static final String HEADER_EMP_CODE = "empCode";

    public static final String HEADER_ORG_ID = "orgId";
    public static final String HEADER_ORG_CODE = "orgCode";
    public static final String HEADER_ORG_TYPE = "orgType";

    public static final String HEADER_TRACE_ID = "traceId";

    private static final Long DEFAULT_L = 1L;

    private static final List<String> IGNORE_URLS = CollUtil.newArrayList("/metadata/client", "/actuator/", "/swagger-ui/", "/druid/", "/init/");


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init BhFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        if (isIgnore.apply(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Map<String, String> headerMap = this.getHeaderInfo.apply((HttpServletRequest) servletRequest);
        UserInfo user = new UserInfo();
        user.setToken(headerMap.get(AUTHORIZATION));
        user.setUserId(StrUtil.isEmpty(headerMap.get(HEADER_USER_ID)) ? DEFAULT_L : Long.parseLong(headerMap.get(HEADER_USER_ID)));
        user.setUserName(headerMap.get(HEADER_USER_NAME));
        user.setUserType(headerMap.get(HEADER_USER_TYPE));
        user.setEmpCode(headerMap.get(HEADER_EMP_CODE));
        user.setEmpId(StrUtil.isEmpty(headerMap.get(HEADER_EMP_ID)) ? DEFAULT_L : Long.parseLong(headerMap.get(HEADER_EMP_ID)));
        user.setOrgCode(headerMap.get(HEADER_ORG_CODE));
        user.setOrgId(StrUtil.isEmpty(headerMap.get(HEADER_ORG_ID)) ? DEFAULT_L : Long.parseLong(headerMap.get(HEADER_ORG_ID)));
        user.setOrgType(headerMap.get(HEADER_ORG_TYPE));

        MDC.put(USER_KEY, headerMap.get(HEADER_USER_NAME));
        UserLocal.set(user);

        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
        // FIXME skywalking
//            responseWrapper.setHeader(HEADER_TRACE_ID, TraceContext.traceId());
        filterChain.doFilter(servletRequest, servletResponse);

        MDC.remove(USER_KEY);
        UserLocal.remove();
    }

    @Override
    public void destroy() {

    }

    private final Function<String, Boolean> isIgnore = url -> IGNORE_URLS.stream().anyMatch(url::contains);

    private final Function<HttpServletRequest, Map<String, String>> getHeaderInfo= httpServletRequest -> {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            map.put(key, value);
        }
        return map;
    };
}