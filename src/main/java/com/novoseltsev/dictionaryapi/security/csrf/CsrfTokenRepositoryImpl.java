package com.novoseltsev.dictionaryapi.security.csrf;

import com.novoseltsev.dictionaryapi.security.jwt.JwtProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Component;

@Component
public class CsrfTokenRepositoryImpl implements CsrfTokenRepository {

    private final JwtProvider jwtProvider;

    public CsrfTokenRepositoryImpl(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public void saveToken(CsrfToken csrfToken,
                          HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse
    ) {

    }

    @Override
    public CsrfToken loadToken(HttpServletRequest httpServletRequest) {
        return null;
    }
}
