package com.road3144.followtrip.dto.plan;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.road3144.followtrip.dto.item.ItemInsertRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class PlanInsertRequestDto {

    private Integer planOrder;

    private Integer imageCnt;

    private String category;

    private String name;

    private String description;

    private String startAt;

    private String endAt;

    private Integer sumItemPrice;

    private List<ItemInsertRequestDto> items;
}
