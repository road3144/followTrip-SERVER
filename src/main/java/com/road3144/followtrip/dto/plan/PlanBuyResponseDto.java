package com.road3144.followtrip.dto.plan;

import com.road3144.followtrip.domain.Plan;
import com.road3144.followtrip.dto.item.ItemBuyResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PlanBuyResponseDto {

    private Long planId;

    private Integer planOrder;

    private String category;

    private String name;

    private String description;

    private String address;

    private String startAt;

    private String endAt;

    private Integer sumItemPrice;

    private List<String> images;

    private List<ItemBuyResponseDto> items;

    @Builder
    public PlanBuyResponseDto(Long planId, Integer planOrder, String category, String name, String description, String address, String startAt, String endAt, Integer sumItemPrice, List<String> images, List<ItemBuyResponseDto> items) {
        this.planId = planId;
        this.planOrder = planOrder;
        this.category = category;
        this.name = name;
        this.description = description;
        this.address = address;
        this.startAt = startAt;
        this.endAt = endAt;
        this.sumItemPrice = sumItemPrice;
        this.images = images;
        this.items = items;
    }

    public static PlanBuyResponseDto from(Plan plan, List<ItemBuyResponseDto> items, List<String> images) {
        return PlanBuyResponseDto.builder()
                .planId(plan.getId())
                .planOrder(plan.getPlanOrder())
                .category(plan.getCategory())
                .name(plan.getName())
                .description(plan.getDescription())
                .address(plan.getAddress())
                .startAt(plan.getStartAt())
                .endAt(plan.getEndAt())
                .sumItemPrice(plan.getSumItemPrice())
                .images(images)
                .items(items)
                .build();
    }
}
