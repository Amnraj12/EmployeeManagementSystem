package com.company.employeemanagement.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter implements Filter {

    private static final List<String> excludedUrls = Arrays.asList("/login", "/register", "/css/", "/js/");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest  = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();
        System.out.println("DEBUG AuthFilter: Requested URL: " + path);

        // Add cache control headers for non-static resources.
        if (!path.startsWith("/css/") && !path.startsWith("/js/")) {
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpResponse.setHeader("Pragma", "no-cache");
            httpResponse.setDateHeader("Expires", 0);
        }

        // Exclude if path exactly equals "/" OR if it starts with one of the excluded prefixes.
        boolean excluded = path.equals("/") ||
                           excludedUrls.stream().anyMatch(prefix -> path.startsWith(prefix));

        if (!excluded) {
            HttpSession session = httpRequest.getSession(false);
            if (session == null) {
                System.out.println("DEBUG AuthFilter: No session found. Redirecting to /login");
                httpResponse.sendRedirect("/login");
                return;
            }
            Object role = session.getAttribute("role");
            System.out.println("DEBUG AuthFilter: Session role is: " + role);
            if (role == null) {
                System.out.println("DEBUG AuthFilter: No role in session. Redirecting to /login");
                httpResponse.sendRedirect("/login");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}