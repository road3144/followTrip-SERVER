package com.road3144.followtrip.dto.buy;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BuyInsertResponseDto {

    private String isSuccess;

    @Builder
    public BuyInsertResponseDto(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public static BuyInsertResponseDto from(String isSuccess) {
        return BuyInsertResponseDto.builder()
                .isSuccess(isSuccess)
                .build();
    }
}
