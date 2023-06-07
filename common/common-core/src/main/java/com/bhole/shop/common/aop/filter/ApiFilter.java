package com.bhole.shop.common.aop.filter;

import cn.hutool.core.collection.CollUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

/**
 * @program: bhole-shop-common-core
 * @description:
 * @author: joke
 * @date: 2023/6/5 13:52
 * @version: 1.0
 */
@Component
@Order(-10)
public class ApiFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiFilter.class);

    private static final List<String>  IGNORE_URLS = CollUtil.newArrayList("/metadata/client", "/actuator/", "/swagger-ui/", "/druid/", "/init/");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        String path = request.getQueryString();
        String url = request.getRequestURI();

        if (isIgnore.apply(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        // FIXME skywalking
//            String requestId = StrUtil.isEmpty(TraceContext.traceId()) ? StrUtil.uuid() : TraceContext.traceId();
        String requestId = "skywalking.traceId";
        logger.info(">>>>>>>>> Request begin url: {} , tid: {}", url, requestId);
        logger.info("> Request QueryString {} , ContentType: {}", path, request.getContentType());

        filterChain.doFilter(request, response);

        long end = System.currentTimeMillis();
        long elapsedMilliseconds = end - start;

        logger.info("<<<<<<<<< Request end url: {} , cost Time: {}ms  , tid: {}\n", url, elapsedMilliseconds, requestId);
    }

    private final Function<String, Boolean> isIgnore = url -> IGNORE_URLS.stream().anyMatch(url::contains);
}