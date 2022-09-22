package com.road3144.followtrip.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.road3144.followtrip.domain.QHash;
import com.road3144.followtrip.domain.QSchedule;
import com.road3144.followtrip.domain.QTag;
import com.road3144.followtrip.domain.Schedule;
import com.road3144.followtrip.dto.schedule.ScheduleListRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    QSchedule schedule = QSchedule.schedule;
    QTag tag = QTag.tag;
    QHash hash = QHash.hash;

    @Override
    public List<Schedule> getScheduleList(ScheduleListRequestDto req) {

        return queryFactory
                .select(schedule)
                .from(schedule)
                .leftJoin(schedule.tags, tag)
                .fetchJoin()
                .leftJoin(tag.hash, hash)
                .fetchJoin()
                .where(regionEq(req.getRegion()))
                .where(wordLike(req.getWord()))
                .where(hashEq(req.getHashes()))
                .distinct()
                .fetch();
    }

    private BooleanExpression regionEq(String region) {
        if (region.isEmpty()) {
            return null;
        }
        return schedule.region.eq(region);
    }

    private BooleanExpression wordLike(String word) {
        if (word.isEmpty()) {
            return null;
        }
        return schedule.name.like(word);
    }

    private BooleanExpression hashEq(List<String> hashes) {
        if (hashes.isEmpty()) {
            return null;
        }
        return hash.name.in(hashes);
    }
}
