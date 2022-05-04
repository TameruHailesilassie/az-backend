package com.softech.ehr.user;

import com.softech.ehr.dto.UserDTO;
import com.softech.ehr.exception.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(
                "User not Found with email:" + email));
    }

    public Collection<UserDTO> fetchAllUsers() {
        try {
            return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EntityNotFoundException(
                "users List could not be fetched");
        }
    }


}
