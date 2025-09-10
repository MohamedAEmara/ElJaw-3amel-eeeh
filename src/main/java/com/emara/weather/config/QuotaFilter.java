package com.emara.weather.config;

import com.emara.weather.entity.User;
import com.emara.weather.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class QuotaFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/weather")) {
            var auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null || !auth.isAuthenticated()) {
                filterChain.doFilter(request, response);
                return;
            }

            if (!(auth.getPrincipal() instanceof OidcUser oidcUser)) {
                filterChain.doFilter(request, response);
                return;
            }

            String userId = oidcUser.getSubject();

            try {
                User user = userService.findByUserId(userId);

                if(userService.isQuotaExceeded(user)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Daily quota consumed\"}");
                    return;
                }
                userService.logSearch(user, request.getRequestURI());
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Internal server error.\"}");
                return;
            }

        }

        filterChain.doFilter(request, response);
    }
}
