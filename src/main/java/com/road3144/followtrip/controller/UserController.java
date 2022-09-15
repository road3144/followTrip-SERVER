package com.road3144.followtrip.controller;

import com.road3144.followtrip.dto.user.UserJoinRequestDto;
import com.road3144.followtrip.dto.user.UserJoinResponseDto;
import com.road3144.followtrip.infra.ApiResponse;
import com.road3144.followtrip.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ApiResponse<UserJoinResponseDto> join(@RequestBody UserJoinRequestDto requestDto) {
        log.info("회원가입 요청");
        return ApiResponse.success(HttpStatus.OK, userService.signUp(requestDto));
    }
}
