package com.road3144.followtrip.service;

import com.road3144.followtrip.domain.User;
import com.road3144.followtrip.dto.user.UserJoinRequestDto;
import com.road3144.followtrip.dto.user.UserJoinResponseDto;
import com.road3144.followtrip.dto.user.UserUpdateRequestDto;
import com.road3144.followtrip.dto.user.UserUpdateResponseDto;
import com.road3144.followtrip.exception.EntityNotFoundException;
import com.road3144.followtrip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserJoinResponseDto signUp(UserJoinRequestDto req) {
        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .birth(req.getBirth())
                .tel(req.getTel())
                .address(req.getAddress())
                .roles("ROLE_USER")
                .point(0)
                .memAgree(req.getMemAgree())
                .informAgree(req.getInformAgree())
                .marketingAgree(req.getMarketingAgree())
                .build();
        userRepository.save(user);
        return UserJoinResponseDto.from(user);
    }

    public UserUpdateResponseDto update(String username, UserUpdateRequestDto req) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        req.setPassword(passwordEncoder.encode(req.getPassword()));
        user.update(req);
        userRepository.save(user);
        return UserUpdateResponseDto.from("updated");
    }

}
