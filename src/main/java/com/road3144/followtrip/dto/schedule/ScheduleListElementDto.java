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

    private String region;

    private String thumbnail;

    @Builder
    public ScheduleListElementDto(Long scheduleId, List<String> hashes, String name, Integer totalPrice, String region, String thumbnail) {
        this.scheduleId = scheduleId;
        this.hashes = hashes;
        this.name = name;
        this.totalPrice = totalPrice;
        this.region = region;
        this.thumbnail = thumbnail;
    }

    public static ScheduleListElementDto from(Schedule schedule, List<String> hashes) {
        return ScheduleListElementDto.builder()
                .scheduleId(schedule.getId())
                .hashes(hashes)
                .name(schedule.getName())
                .totalPrice(schedule.getTotalPrice())
                .region(schedule.getRegion())
                .thumbnail(schedule.getThumbnail().getPath())
                .build();
    }
}
