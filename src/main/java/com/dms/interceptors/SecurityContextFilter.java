package com.dms.interceptors;

import com.dms.model.User;
import com.dms.repository.UserRepository;
import com.dms.utils.DMSConstants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by vamsikrishna on 19/10/14.
 */
@Component("securityContextFilter")
public class SecurityContextFilter implements Filter{

    @Resource
    private UserRepository userRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // Allow calls to login controller since it is where authentication happens.
        String requestURI = httpServletRequest.getRequestURI();

        if(!requestURI.contains("/login") && !requestURI.contains("index.jsp")){
            HttpSession httpSession = httpServletRequest.getSession(false);
            if(httpSession != null){
                User user = (User) httpSession.getAttribute(DMSConstants.SESSION_USER);
                if( user != null){
                    System.out.println("Valid session for User with Name: +" + user.getName());
                } else {
                    ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } else {
                ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
