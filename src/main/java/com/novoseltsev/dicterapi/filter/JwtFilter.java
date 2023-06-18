package com.novoseltsev.dicterapi.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novoseltsev.dicterapi.exception.JwtAuthenticationException;
import com.novoseltsev.dicterapi.exception.model.ErrorResponse;
import com.novoseltsev.dicterapi.security.jwt.JwtProvider;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class JwtFilter implements Filter {

    private final JwtProvider jwtProvider;

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
        var errorResponse = new ObjectMapper().writeValueAsString(new ErrorResponse(HttpStatus.UNAUTHORIZED, e));
        var resp = (HttpServletResponse) response;
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.getWriter().write(errorResponse);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
