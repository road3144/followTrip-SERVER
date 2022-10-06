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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "plan")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<Image> images = new ArrayList<>();

    private Integer planOrder;

    private String category;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String address;

    private String startAt;

    private String endAt;

    private Integer sumItemPrice;

    @Builder
    public Plan(Schedule schedule, List<Item> items, Integer planOrder, String category, String name, String description, String address, String startAt, String endAt, Integer sumItemPrice) {
        this.schedule = schedule;
        this.items = items;
        this.planOrder = planOrder;
        this.category = category;
        this.name = name;
        this.description = description;
        this.address = address;
        this.startAt = startAt;
        this.endAt = endAt;
        this.sumItemPrice = sumItemPrice;
    }
}
