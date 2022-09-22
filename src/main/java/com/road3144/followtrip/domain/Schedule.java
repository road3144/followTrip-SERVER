package com.road3144.followtrip.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "schedule")
    private List<Plan> plans = new ArrayList<>();

    @OneToMany(mappedBy = "schedule")
    private List<Tag> tags = new ArrayList<>();

    @OneToOne(mappedBy = "schedule")
    private Image thumbnail;

    @OneToMany(mappedBy = "schedule")
    private List<Review> reviews;

    private String name;

    private String region;

    private Integer pointPrice;

    private String description;

    private Integer isGuide;

    private Integer totalPrice;

    @Builder
    public Schedule(User user, List<Plan> plans, List<Tag> tags, Image thumbnail , String name, String region, Integer pointPrice, String description, Integer isGuide, Integer totalPrice) {
        this.user = user;
        this.plans = plans;
        this.tags = tags;
        this.thumbnail = thumbnail;
        this.name = name;
        this.region = region;
        this.pointPrice = pointPrice;
        this.description = description;
        this.isGuide = isGuide;
        this.totalPrice = totalPrice;
    }
}
