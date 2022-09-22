package com.road3144.followtrip.repository;

import com.road3144.followtrip.domain.Schedule;
import com.road3144.followtrip.dto.schedule.ScheduleListRequestDto;

import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> getScheduleList(ScheduleListRequestDto req);
}
