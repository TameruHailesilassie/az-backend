package com.softech.ehr.security;

import com.softech.ehr.user.User;
import com.softech.ehr.user.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String s)
        throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s)
            .orElseThrow(() -> new UsernameNotFoundException(
                "User not found with email:" + s));
        return modelMapper.map(user, UserPrincipal.class);
    }
}
