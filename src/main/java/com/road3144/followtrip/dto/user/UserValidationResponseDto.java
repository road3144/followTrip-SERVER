package com.road3144.followtrip.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserValidationResponseDto {

    private String isValidate;

    @Builder
    public UserValidationResponseDto(String isValidate) {
        this.isValidate = isValidate;
    }

    public static UserValidationResponseDto from(String isValidate) {
        return UserValidationResponseDto.builder()
                .isValidate(isValidate)
                .build();
    }
}
