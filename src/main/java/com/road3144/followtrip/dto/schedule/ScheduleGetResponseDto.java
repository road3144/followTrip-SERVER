package com.road3144.followtrip.dto.schedule;

import com.road3144.followtrip.domain.Schedule;
import com.road3144.followtrip.dto.review.ReviewGetResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ScheduleGetResponseDto {

    private Long scheduleId;

    private String name;

    private List<String> hashes;

    private String description;

    private Integer totalPrice;

    private Integer isBuy;

    private List<ReviewGetResponseDto> reviews;

    @Builder
    public ScheduleGetResponseDto(Long scheduleId, String name, List<String> hashes, String description, Integer totalPrice, Integer isBuy, List<ReviewGetResponseDto> reviews) {
        this.scheduleId = scheduleId;
        this.name = name;
        this.hashes = hashes;
        this.description = description;
        this.totalPrice = totalPrice;
        this.isBuy = isBuy;
        this.reviews = reviews;
    }

    public static ScheduleGetResponseDto from(Schedule schedule, List<ReviewGetResponseDto> reviews, List<String> hashes, Integer isBuy) {
        return ScheduleGetResponseDto.builder()
                .scheduleId(schedule.getId())
                .name(schedule.getName())
                .description(schedule.getDescription())
                .totalPrice(schedule.getTotalPrice())
                .reviews(reviews)
                .isBuy(isBuy)
                .hashes(hashes)
                .build();
    }
}
