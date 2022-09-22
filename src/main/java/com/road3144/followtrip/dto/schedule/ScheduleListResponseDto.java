package com.road3144.followtrip.dto.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ScheduleListResponseDto {

    private List<ScheduleListElementDto> schedules;

    @Builder
    public ScheduleListResponseDto(List<ScheduleListElementDto> schedules) {
        this.schedules = schedules;
    }

    public static ScheduleListResponseDto from(List<ScheduleListElementDto> schedules) {
        return ScheduleListResponseDto.builder()
                .schedules(schedules)
                .build();
    }
}
