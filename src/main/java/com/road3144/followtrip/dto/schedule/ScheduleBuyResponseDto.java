package com.road3144.followtrip.dto.schedule;

import com.road3144.followtrip.domain.Schedule;
import com.road3144.followtrip.dto.plan.PlanBuyResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ScheduleBuyResponseDto {

    private Long scheduleId;

    private String name;

    private List<String> hashes;

    private String description;

    private Integer totalPrice;

    private List<PlanBuyResponseDto> plans;

    @Builder
    public ScheduleBuyResponseDto(Long scheduleId, String name, List<String> hashes, String description, Integer totalPrice, List<PlanBuyResponseDto> plans) {
        this.scheduleId = scheduleId;
        this.name = name;
        this.hashes = hashes;
        this.description = description;
        this.totalPrice = totalPrice;
        this.plans = plans;
    }

    public static ScheduleBuyResponseDto from(Schedule schedule,List<String> hashes, List<PlanBuyResponseDto> plans) {
        return ScheduleBuyResponseDto.builder()
                .scheduleId(schedule.getId())
                .name(schedule.getName())
                .hashes(hashes)
                .description(schedule.getDescription())
                .totalPrice(schedule.getTotalPrice())
                .plans(plans)
                .build();
    }
}
