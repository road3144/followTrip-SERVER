package com.road3144.followtrip.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.road3144.followtrip.dto.schedule.ScheduleInsertRequestDto;
import com.road3144.followtrip.dto.schedule.ScheduleInsertResponseDto;
import com.road3144.followtrip.infra.ApiResponse;
import com.road3144.followtrip.infra.jwt.PrincipalDetails;
import com.road3144.followtrip.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
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
}
