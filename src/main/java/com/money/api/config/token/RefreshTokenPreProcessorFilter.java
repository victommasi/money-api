package com.money.api.config.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenPreProcessorFilter implements Filter{

    private static final String TOKEN_URI = "/oauth/token";
    private static final String REFRESHTOKEN = "refreshToken";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String GRANT_TYPE = "grant_type";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if(TOKEN_URI.equalsIgnoreCase(request.getRequestURI())
                && REFRESH_TOKEN.equalsIgnoreCase(request.getParameter(GRANT_TYPE))
                && request.getCookies() != null){
            for (Cookie cookie : request.getCookies()){
                if (cookie.getName().equals(REFRESHTOKEN)){
                    String refreshToken = cookie.getValue();
                    request = new MyServletRequestWrapper(request, refreshToken);
                }
            }
        }
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    static class MyServletRequestWrapper extends HttpServletRequestWrapper {

        private String refreshToken;

        public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
            super(request);
            this.refreshToken = refreshToken;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
            map.put(REFRESH_TOKEN, new String[] { refreshToken });
            map.setLocked(true);
            return map;
        }
    }
}
