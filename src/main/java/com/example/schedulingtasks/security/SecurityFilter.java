package com.example.schedulingtasks.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends HttpFilter {

//    private final Base64.Encoder encoder;
//    private final AuthenticationManager authenticationManager;
    
    @Override
    protected void doFilter(final HttpServletRequest rq,
                            final HttpServletResponse rs,
                            final FilterChain chain
    ) throws IOException, ServletException {
        if (Objects.nonNull(rq.getHeader("Authorization"))) {
            final String authorization = rq.getHeader("Authorization").split(" ")[1];
            final String userAndPassword = new String(Base64.getDecoder().decode(authorization));
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userAndPassword.split(":")[0], null, null)
            );

        }
        chain.doFilter(rq, rs);
    }
}
