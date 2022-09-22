package com.road3144.followtrip.dto.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ScheduleTopResponseDto {

    private List<ScheduleListElementDto> schedules;

    @Builder
    public ScheduleTopResponseDto(List<ScheduleListElementDto> schedules) {
        this.schedules = schedules;
    }

    public static ScheduleTopResponseDto from(List<ScheduleListElementDto> schedules) {
        return ScheduleTopResponseDto.builder()
                .schedules(schedules)
                .build();
    }
}
