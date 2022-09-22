package com.road3144.followtrip.dto.schedule;

import com.road3144.followtrip.domain.Schedule;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ScheduleListElementDto {

    private Long scheduleId;

    private List<String> hashes;

    private String name;

    private Integer totalPrice;

    @Builder
    public ScheduleListElementDto(Long scheduleId, List<String> hashes, String name, Integer totalPrice) {
        this.scheduleId = scheduleId;
        this.hashes = hashes;
        this.name = name;
        this.totalPrice = totalPrice;
    }

    public static ScheduleListElementDto from(Schedule schedule, List<String> hashes) {
        return ScheduleListElementDto.builder()
                .scheduleId(schedule.getId())
                .hashes(hashes)
                .name(schedule.getName())
                .totalPrice(schedule.getTotalPrice())
                .build();
    }
}
