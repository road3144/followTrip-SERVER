package com.road3144.followtrip.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateResponseDto {

    private String updateDone;

    @Builder
    public UserUpdateResponseDto(String updateDone) {
        this.updateDone = updateDone;
    }

    public static UserUpdateResponseDto from(String updateDone) {
        return UserUpdateResponseDto.builder()
                .updateDone(updateDone)
                .build();
    }
}
