package com.road3144.followtrip.dto.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleInsertResponseDto {

    private String isSuccess;

    @Builder
    public ScheduleInsertResponseDto(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public static ScheduleInsertResponseDto from(String isSuccess) {
        return ScheduleInsertResponseDto.builder()
                .isSuccess(isSuccess)
                .build();
    }
}
