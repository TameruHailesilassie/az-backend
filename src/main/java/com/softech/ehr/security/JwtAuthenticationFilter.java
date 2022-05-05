package com.softech.ehr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtTokenProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        //get token from request
        String jwt = getJwtFromRequest(httpServletRequest);
        log.debug(jwt);
        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {

            String username = jwtProvider.getUsername(jwt);
            UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken
                    (userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(
                    httpServletRequest));
            SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        }

    }

    private String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) &&
            bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;


    }
}
