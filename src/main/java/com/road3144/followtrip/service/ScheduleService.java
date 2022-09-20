package com.road3144.followtrip.service;

import com.road3144.followtrip.domain.Item;
import com.road3144.followtrip.domain.Plan;
import com.road3144.followtrip.domain.Schedule;
import com.road3144.followtrip.domain.User;
import com.road3144.followtrip.dto.schedule.ScheduleInsertRequestDto;
import com.road3144.followtrip.dto.schedule.ScheduleInsertResponseDto;
import com.road3144.followtrip.exception.EntityNotFoundException;
import com.road3144.followtrip.repository.ItemRepository;
import com.road3144.followtrip.repository.PlanRepository;
import com.road3144.followtrip.repository.ScheduleRepository;
import com.road3144.followtrip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final UserRepository userRepository;

    private final ScheduleRepository scheduleRepository;

    private final PlanRepository planRepository;

    private final ItemRepository itemRepository;

    @Transactional
    public ScheduleInsertResponseDto insert(String username, ScheduleInsertRequestDto req) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        Schedule schedule = Schedule.builder()
                .user(user)
                .name(req.getName())
                .region(req.getRegion())
                .pointPrice(300)
                .description(req.getDescription())
                .build();
        scheduleRepository.save(schedule);
        savePlans(req.getPlans(), schedule);
        log.info("스케줄 등록");
        return ScheduleInsertResponseDto.from("success");
    }

    public void savePlans(List<Plan> reqPlans, Schedule schedule) {
        for (Plan reqPlan : reqPlans) {
            Plan plan = Plan.builder()
                    .name(reqPlan.getName())
                    .category(reqPlan.getCategory())
                    .planOrder(reqPlan.getPlanOrder())
                    .description(reqPlan.getDescription())
                    .schedule(schedule)
                    .build();
            planRepository.save(plan);
            saveItems(reqPlan.getItems(), plan);
        }
    }

    public void saveItems(List<Item> reqItems, Plan plan) {
        for (Item reqItem : reqItems) {
            Item item = Item.builder()
                    .name(reqItem.getName())
                    .price(reqItem.getPrice())
                    .plan(plan)
                    .build();
            itemRepository.save(item);
        }
    }
}
