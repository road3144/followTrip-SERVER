package com.road3144.followtrip.service;

import com.road3144.followtrip.domain.Buy;
import com.road3144.followtrip.domain.Review;
import com.road3144.followtrip.domain.Schedule;
import com.road3144.followtrip.domain.User;
import com.road3144.followtrip.dto.review.ReviewInsertRequestDto;
import com.road3144.followtrip.dto.review.ReviewInsertResponseDto;
import com.road3144.followtrip.exception.EntityNotFoundException;
import com.road3144.followtrip.repository.BuyRepository;
import com.road3144.followtrip.repository.ReviewRepository;
import com.road3144.followtrip.repository.ScheduleRepository;
import com.road3144.followtrip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;

    private final ScheduleRepository scheduleRepository;

    private final BuyRepository buyRepository;

    private final ReviewRepository reviewRepository;

    public ReviewInsertResponseDto insert(String username, ReviewInsertRequestDto req) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        Schedule schedule = scheduleRepository.findById(req.getScheduleId())
                .orElseThrow(EntityNotFoundException::new);
        Buy buy = buyRepository.findByUserAndSchedule(user, schedule)
                .orElseThrow(EntityNotFoundException::new);
        Review review = Review.builder()
                .user(user)
                .schedule(schedule)
                .context(req.getContext())
                .grade(req.getGrade())
                .build();
        reviewRepository.save(review);

        return ReviewInsertResponseDto.from("success");
    }
}
