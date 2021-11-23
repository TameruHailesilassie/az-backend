package com.otlichno.ehr.security;

import com.otlichno.ehr.exception.AuthenticationException;
import com.otlichno.ehr.user.UserAuthDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;


    public String authenticate(UserAuthDTO authUser) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authUser.getEmail(), authUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenProvider.createToken(authentication);
        } catch (Exception e) {
           // e.printStackTrace();
            throw new AuthenticationException(e.getMessage());
        }

    }


}
