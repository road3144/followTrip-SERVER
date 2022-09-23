package com.road3144.followtrip.domain;

import com.road3144.followtrip.dto.user.UserUpdateRequestDto;
import com.road3144.followtrip.exception.EntityNotFoundException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String roles;

    private String password;

    private String name;

    private LocalDate birth;

    private String tel;

    private String address;

    private Integer point;

    private Integer memAgree;

    private Integer informAgree;

    private Integer marketingAgree;

    @OneToMany(mappedBy = "user")
    private List<Buy> buys;

    @Builder
    public User(String username, String roles, String password, String name, LocalDate birth, String tel, String address, Integer point, Integer memAgree, Integer informAgree, Integer marketingAgree) {
        this.username = username;
        this.roles = roles;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.tel = tel;
        this.address = address;
        this.point = point;
        this.memAgree = memAgree;
        this.informAgree = informAgree;
        this.marketingAgree = marketingAgree;
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public void update(UserUpdateRequestDto req) {
        this.password = req.getPassword();
        this.name = req.getName();
        this.birth = req.getBirth();
        this.tel = req.getTel();
        this.address = req.getAddress();
        this.memAgree = req.getMemAgree();
        this.informAgree = req.getInformAgree();
        this.marketingAgree = req.getMarketingAgree();
    }

    public void deduction(Integer pointPrice) {
        if (this.point - pointPrice < 0) {
            throw new EntityNotFoundException();
        }
        this.point -= pointPrice;
    }
}
