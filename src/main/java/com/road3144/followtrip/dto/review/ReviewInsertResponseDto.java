package com.road3144.followtrip.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewInsertResponseDto {

    private String isSuccess;

    @Builder
    public ReviewInsertResponseDto(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public static ReviewInsertResponseDto from(String isSuccess) {
        return ReviewInsertResponseDto.builder()
                .isSuccess(isSuccess)
                .build();
    }
}
