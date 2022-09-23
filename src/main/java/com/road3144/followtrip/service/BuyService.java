package com.road3144.followtrip.service;

import com.road3144.followtrip.domain.Buy;
import com.road3144.followtrip.domain.Schedule;
import com.road3144.followtrip.domain.User;
import com.road3144.followtrip.dto.buy.BuyInsertRequestDto;
import com.road3144.followtrip.dto.buy.BuyInsertResponseDto;
import com.road3144.followtrip.exception.EntityNotFoundException;
import com.road3144.followtrip.repository.BuyRepository;
import com.road3144.followtrip.repository.ScheduleRepository;
import com.road3144.followtrip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyService {

    private final BuyRepository buyRepository;

    private final UserRepository userRepository;

    private final ScheduleRepository scheduleRepository;

    public BuyInsertResponseDto insert(String username, BuyInsertRequestDto req) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        Schedule schedule = scheduleRepository.findById(req.getScheduleId())
                .orElseThrow(EntityNotFoundException::new);

        user.deduction(schedule.getPointPrice());

        Buy buy = Buy.builder()
                .schedule(schedule)
                .user(user)
                .build();
        buyRepository.save(buy);
        return BuyInsertResponseDto.from("success");
    }
}
