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

    private Integer planOrder;

    private String category;

    private String name;

    private String description;

    @Builder
    public Plan(Schedule schedule, List<Item> items, Integer planOrder, String category, String name, String description) {
        this.schedule = schedule;
        this.items = items;
        this.planOrder = planOrder;
        this.category = category;
        this.name = name;
        this.description = description;
    }
}
