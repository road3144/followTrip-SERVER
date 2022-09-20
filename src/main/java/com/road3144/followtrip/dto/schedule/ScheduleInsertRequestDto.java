package com.road3144.followtrip.dto.schedule;

import com.road3144.followtrip.domain.Plan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ScheduleInsertRequestDto {

    private String name;

    private String region;

    private String description;

    private List<Plan> plans;
}
