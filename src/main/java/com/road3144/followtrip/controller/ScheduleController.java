package com.road3144.followtrip.controller;

import com.road3144.followtrip.dto.schedule.ScheduleInsertRequestDto;
import com.road3144.followtrip.dto.schedule.ScheduleInsertResponseDto;
import com.road3144.followtrip.infra.ApiResponse;
import com.road3144.followtrip.infra.jwt.PrincipalDetails;
import com.road3144.followtrip.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/insert")
    public ApiResponse<ScheduleInsertResponseDto> insert(@AuthenticationPrincipal PrincipalDetails details,
                                                         @RequestBody ScheduleInsertRequestDto req) {
        return ApiResponse.success(HttpStatus.OK, scheduleService.insert(details.getUsername(), req));
    }
}
