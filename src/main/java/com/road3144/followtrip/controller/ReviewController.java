package com.road3144.followtrip.controller;

import com.road3144.followtrip.dto.review.ReviewInsertRequestDto;
import com.road3144.followtrip.dto.review.ReviewInsertResponseDto;
import com.road3144.followtrip.infra.ApiResponse;
import com.road3144.followtrip.infra.jwt.PrincipalDetails;
import com.road3144.followtrip.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/insert")
    public ApiResponse<ReviewInsertResponseDto> insert(
            @AuthenticationPrincipal PrincipalDetails details,
            @RequestBody ReviewInsertRequestDto req) {
        return ApiResponse.success(HttpStatus.OK, reviewService.insert(details.getUsername(), req));
    }
}
