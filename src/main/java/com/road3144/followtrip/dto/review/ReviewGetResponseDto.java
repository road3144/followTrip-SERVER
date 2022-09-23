package com.road3144.followtrip.dto.review;

import com.road3144.followtrip.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewGetResponseDto {

    private Long reviewId;

    private String context;

    private Double grade;

    private String name;

    @Builder
    public ReviewGetResponseDto(Long reviewId, String context, Double grade, String name) {
        this.reviewId = reviewId;
        this.context = context;
        this.grade = grade;
        this.name = name;
    }

    public static ReviewGetResponseDto from(Review review) {
        return ReviewGetResponseDto.builder()
                .reviewId(review.getId())
                .context(review.getContext())
                .grade(review.getGrade())
                .name(review.getUser().getName())
                .build();
    }
}
