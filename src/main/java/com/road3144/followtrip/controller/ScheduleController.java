package com.road3144.followtrip.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.road3144.followtrip.dto.schedule.ScheduleBuyResponseDto;
import com.road3144.followtrip.dto.schedule.ScheduleGetRequestDto;
import com.road3144.followtrip.dto.schedule.ScheduleGetResponseDto;
import com.road3144.followtrip.dto.schedule.ScheduleInsertRequestDto;
import com.road3144.followtrip.dto.schedule.ScheduleInsertResponseDto;
import com.road3144.followtrip.dto.schedule.ScheduleListRequestDto;
import com.road3144.followtrip.dto.schedule.ScheduleListResponseDto;
import com.road3144.followtrip.dto.schedule.ScheduleTopResponseDto;
import com.road3144.followtrip.infra.ApiResponse;
import com.road3144.followtrip.infra.jwt.PrincipalDetails;
import com.road3144.followtrip.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping(value = "/insert", consumes = "multipart/form-data")
    public ApiResponse<ScheduleInsertResponseDto> insert(@AuthenticationPrincipal PrincipalDetails details,
                                                         @RequestParam String reqString,
                                                         @RequestParam List<MultipartFile> images,
                                                         @RequestParam MultipartFile thumbnail) throws JsonProcessingException {
        ScheduleInsertRequestDto req = new ObjectMapper().readValue(reqString, ScheduleInsertRequestDto.class);
        return ApiResponse.success(HttpStatus.OK, scheduleService.insert(details.getUsername(), req, images, thumbnail));
    }

    @PutMapping("/search")
    public ApiResponse<ScheduleListResponseDto> search(@RequestBody ScheduleListRequestDto req) {
        return ApiResponse.success(HttpStatus.OK, scheduleService.search(req));
    }

    @PutMapping("/top")
    public ApiResponse<ScheduleTopResponseDto> top() {
        return ApiResponse.success(HttpStatus.OK, scheduleService.top());
    }

    @PutMapping("/get")
    public ApiResponse<ScheduleGetResponseDto> get(
            @AuthenticationPrincipal PrincipalDetails details,
            @RequestBody ScheduleGetRequestDto req) {
        String username = "";
        if (details != null) {
            username = details.getUsername();
        }
        return ApiResponse.success(HttpStatus.OK, scheduleService.get(username, req.getScheduleId()));
    }

    @PutMapping("/buy")
    public ApiResponse<ScheduleBuyResponseDto> buy(@RequestBody ScheduleGetRequestDto req) {
        return ApiResponse.success(HttpStatus.OK, scheduleService.buy(req.getScheduleId()));
    }
}
