package com.softech.ehr.service.impl;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.exception.NoUserFoundException;
import com.softech.ehr.model.factory.UserFactory;
import com.softech.ehr.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(
        UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {

        User aUser =
            this.userRepository.findByPhoneNumber(username)
                .orElseThrow(
                    () -> new NoUserFoundException(String.format(
                        "No user Account found with phone number '%s'.",
                        username)));
        return UserFactory.create(aUser);
    }

}
