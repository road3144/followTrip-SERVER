package com.road3144.followtrip.dto.user;

import com.road3144.followtrip.domain.User;
import com.road3144.followtrip.dto.schedule.ScheduleListElementDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserGetResponseDto {

    private String username;

    private String name;

    private LocalDate birth;

    private String tel;

    private String address;

    private Integer point;

    private Integer memAgree;

    private Integer informAgree;

    private Integer marketingAgree;

    private List<ScheduleListElementDto> schedules;

    @Builder
    public UserGetResponseDto(String username, String name, LocalDate birth, String tel, String address, Integer point, Integer memAgree, Integer informAgree, Integer marketingAgree, List<ScheduleListElementDto> schedules) {
        this.username = username;
        this.name = name;
        this.birth = birth;
        this.tel = tel;
        this.address = address;
        this.point = point;
        this.memAgree = memAgree;
        this.informAgree = informAgree;
        this.marketingAgree = marketingAgree;
        this.schedules = schedules;
    }

    public static UserGetResponseDto from(User user, List<ScheduleListElementDto> schedules) {
        return UserGetResponseDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .birth(user.getBirth())
                .tel(user.getTel())
                .address(user.getAddress())
                .point(user.getPoint())
                .memAgree(user.getMemAgree())
                .informAgree(user.getInformAgree())
                .marketingAgree(user.getMarketingAgree())
                .schedules(schedules)
                .build();
    }
}
