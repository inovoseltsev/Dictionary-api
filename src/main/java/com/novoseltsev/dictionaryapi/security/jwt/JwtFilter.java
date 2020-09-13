package com.novoseltsev.dictionaryapi.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novoseltsev.dictionaryapi.exception.JwtAuthenticationException;
import com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtFilter implements Filter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            String token = jwtProvider.resolveToken((HttpServletRequest) request);
            if (!StringUtils.isEmpty(token)) {
                Authentication auth = jwtProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            chain.doFilter(request, response);
        } catch (JwtAuthenticationException e) {
            handleErrorResponse(response, e);
        }
    }

    private void handleErrorResponse(
            ServletResponse response, JwtAuthenticationException e
    ) throws IOException {
        String errorResponse = new ObjectMapper()
                .writeValueAsString(ExceptionUtils.getErrorResponse(e));
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("application/json; charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.getWriter().write(errorResponse);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
