package com.road3144.followtrip.repository;

import com.road3144.followtrip.domain.Buy;
import com.road3144.followtrip.domain.Schedule;
import com.road3144.followtrip.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyRepository extends JpaRepository<Buy, Long> {
    Optional<Buy> findByUserAndSchedule(User user, Schedule schedule);
}
