package com.road3144.followtrip.controller;

import com.road3144.followtrip.dto.buy.BuyInsertRequestDto;
import com.road3144.followtrip.dto.buy.BuyInsertResponseDto;
import com.road3144.followtrip.infra.ApiResponse;
import com.road3144.followtrip.infra.jwt.PrincipalDetails;
import com.road3144.followtrip.service.BuyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/buy")
public class BuyController {

    private final BuyService buyService;

    @PostMapping("/insert")
    public ApiResponse<BuyInsertResponseDto> insert(
            @AuthenticationPrincipal PrincipalDetails details,
            @RequestBody BuyInsertRequestDto req) {
        return ApiResponse.success(HttpStatus.OK, buyService.insert(details.getUsername(), req));
    }
}
