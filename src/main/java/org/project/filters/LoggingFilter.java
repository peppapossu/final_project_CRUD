package org.project.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@WebFilter("/*")
@Slf4j
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("Request received: {} {}", request.getRemoteAddr(), request.getRemotePort());
        chain.doFilter(request, response);
        log.info("Response sent to: {} {}", request.getRemoteAddr(), request.getRemotePort());
    }
}
