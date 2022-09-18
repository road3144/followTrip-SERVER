package com.road3144.followtrip.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String password;

    private String name;

    private LocalDate birth;

    private String tel;

    private String address;

    private Integer memAgree;

    private Integer informAgree;

    private Integer marketingAgree;

    public void setPassword(String password) {
        this.password = password;
    }
}
