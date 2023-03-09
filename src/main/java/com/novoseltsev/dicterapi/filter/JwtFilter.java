package com.novoseltsev.dicterapi.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novoseltsev.dicterapi.exception.JwtAuthenticationException;
import com.novoseltsev.dicterapi.exception.util.ExceptionUtils;
import com.novoseltsev.dicterapi.security.jwt.JwtProvider;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtFilter implements Filter {

    private final JwtProvider jwtProvider;

    @Autowired
    public JwtFilter(@Lazy JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = jwtProvider.resolveToken((HttpServletRequest) request);
            if (token != null && !token.isBlank()) {
                Authentication auth = jwtProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            chain.doFilter(request, response);
        } catch (JwtAuthenticationException e) {
            handleBadTokenResponse(response, e);
        }
    }

    private void handleBadTokenResponse(ServletResponse response, JwtAuthenticationException e) throws IOException {
        String errorResponse = new ObjectMapper().writeValueAsString(ExceptionUtils.getErrorResponse(e));
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("application/json; charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.getWriter().write(errorResponse);
        response.getWriter().flush();
        response.getWriter().close();
    }
}