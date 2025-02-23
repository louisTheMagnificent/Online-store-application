package com.comp5348_project_prac12_group2.store.filter;

import com.comp5348_project_prac12_group2.store.util.AuthenticationHelper;
import com.comp5348_project_prac12_group2.store.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    private AuthenticationHelper authenticationHelper;

    public AuthenticationFilter(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //do not filter login page
        String url = httpRequest.getRequestURL().toString();
        if (url.contains("login")){
            chain.doFilter(request, response);
            return;
        }

        String userIdParam = httpRequest.getParameter("user_id");
        LoggerUtil.logInfo("user id is: \n"+userIdParam);

        if (userIdParam != null) {
            Long userId = Long.parseLong(userIdParam);
            if (!authenticationHelper.isUserLoggedIn(userId)) {
                // If the user is not logged in, return a 401 Unauthorized response
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authenticated");
                return; // Prevent further processing of the request
            }
        }

        // Proceed with the next filter or the target resource
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}


