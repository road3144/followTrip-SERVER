package com.road3144.followtrip.dto.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.road3144.followtrip.dto.plan.PlanInsertRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleInsertRequestDto {

    private String name;

    private String region;

    private String description;

    private List<PlanInsertRequestDto> plans;

    private List<String> hashes;

    private Integer isGuide;

    private Integer totalPrice;
}
