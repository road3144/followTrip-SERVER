package com.road3144.followtrip.controller;

import com.road3144.followtrip.dto.user.UserGetResponseDto;
import com.road3144.followtrip.dto.user.UserJoinRequestDto;
import com.road3144.followtrip.dto.user.UserJoinResponseDto;
import com.road3144.followtrip.dto.user.UserUpdateRequestDto;
import com.road3144.followtrip.dto.user.UserUpdateResponseDto;
import com.road3144.followtrip.infra.ApiResponse;
import com.road3144.followtrip.infra.jwt.PrincipalDetails;
import com.road3144.followtrip.service.ScheduleService;
import com.road3144.followtrip.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final ScheduleService scheduleService;

    @PostMapping("/join")
    public ApiResponse<UserJoinResponseDto> join(@RequestBody UserJoinRequestDto requestDto) {
        log.info("회원가입 요청");
        return ApiResponse.success(HttpStatus.OK, userService.signUp(requestDto));
    }

    @GetMapping("/user")
    public ApiResponse<UserGetResponseDto> user(@AuthenticationPrincipal PrincipalDetails details) {
        return ApiResponse.success(HttpStatus.OK, UserGetResponseDto.from(details.getUser(), scheduleService.buyList(details.getUsername())));
    }

    @PutMapping("/user/update")
    public ApiResponse<UserUpdateResponseDto> update(@AuthenticationPrincipal PrincipalDetails details, @RequestBody UserUpdateRequestDto req) {
        return ApiResponse.success(HttpStatus.OK, userService.update(details.getUsername(), req));
    }
}
