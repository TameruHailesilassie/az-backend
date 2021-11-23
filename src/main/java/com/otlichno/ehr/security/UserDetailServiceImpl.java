package com.otlichno.ehr.security;

import com.otlichno.ehr.user.User;
import com.otlichno.ehr.user.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email:" + s));
        return modelMapper.map(user, UserPrincipal.class);
    }
}
