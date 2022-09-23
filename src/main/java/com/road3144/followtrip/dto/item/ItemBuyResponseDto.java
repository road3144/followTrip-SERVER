package com.road3144.followtrip.dto.item;

import com.road3144.followtrip.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemBuyResponseDto {

    private Long itemId;

    private String name;

    private Integer price;

    @Builder
    public ItemBuyResponseDto(Long itemId, String name, Integer price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    public static ItemBuyResponseDto from(Item item) {
        return ItemBuyResponseDto.builder()
                .itemId(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }
}
