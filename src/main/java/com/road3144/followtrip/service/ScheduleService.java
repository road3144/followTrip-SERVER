package com.road3144.followtrip.service;

import com.road3144.followtrip.domain.Hash;
import com.road3144.followtrip.domain.Item;
import com.road3144.followtrip.domain.Plan;
import com.road3144.followtrip.domain.Schedule;
import com.road3144.followtrip.domain.Tag;
import com.road3144.followtrip.domain.User;
import com.road3144.followtrip.dto.item.ItemBuyResponseDto;
import com.road3144.followtrip.dto.item.ItemInsertRequestDto;
import com.road3144.followtrip.dto.plan.PlanBuyResponseDto;
import com.road3144.followtrip.dto.plan.PlanInsertRequestDto;
import com.road3144.followtrip.dto.review.ReviewGetResponseDto;
import com.road3144.followtrip.dto.schedule.ScheduleBuyResponseDto;
import com.road3144.followtrip.dto.schedule.ScheduleGetResponseDto;
import com.road3144.followtrip.dto.schedule.ScheduleInsertRequestDto;
import com.road3144.followtrip.dto.schedule.ScheduleInsertResponseDto;
import com.road3144.followtrip.dto.schedule.ScheduleListElementDto;
import com.road3144.followtrip.dto.schedule.ScheduleListRequestDto;
import com.road3144.followtrip.dto.schedule.ScheduleListResponseDto;
import com.road3144.followtrip.dto.schedule.ScheduleTopResponseDto;
import com.road3144.followtrip.exception.EntityNotFoundException;
import com.road3144.followtrip.infra.file.FileHandler;
import com.road3144.followtrip.repository.BuyRepository;
import com.road3144.followtrip.repository.HashRepository;
import com.road3144.followtrip.repository.ImageRepository;
import com.road3144.followtrip.repository.ItemRepository;
import com.road3144.followtrip.repository.PlanRepository;
import com.road3144.followtrip.repository.ScheduleRepository;
import com.road3144.followtrip.repository.TagRepository;
import com.road3144.followtrip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final UserRepository userRepository;

    private final ScheduleRepository scheduleRepository;

    private final PlanRepository planRepository;

    private final ItemRepository itemRepository;

    private final ImageRepository imageRepository;

    private final TagRepository tagRepository;

    private final HashRepository hashRepository;

    private final FileHandler fileHandler;

    private final BuyRepository buyRepository;

    public List<ScheduleListElementDto> buyList(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        List<Schedule> schedules = new ArrayList<>();
        user.getBuys().forEach(buy -> schedules.add(buy.getSchedule()));

        return getScheduleElements(schedules);
    }

    public ScheduleBuyResponseDto buy(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(EntityNotFoundException::new);

        List<PlanBuyResponseDto> plans = getPlans(schedule);

        List<String> hashes = new ArrayList<>();
        schedule.getTags().forEach(tag -> hashes.add(tag.getHash().getName()));

        return ScheduleBuyResponseDto.from(schedule, hashes, plans);
    }

    private List<PlanBuyResponseDto> getPlans(Schedule schedule) {
        List<PlanBuyResponseDto> plans = new ArrayList<>();
        for (Plan plan : schedule.getPlans()) {
            List<ItemBuyResponseDto> items = new ArrayList<>();
            plan.getItems().forEach(item -> items.add(ItemBuyResponseDto.from(item)));

            List<String> images = new ArrayList<>();
            plan.getImages().forEach(image -> images.add(image.getPath()));

            plans.add(PlanBuyResponseDto.from(plan, items, images));
        }
        return plans;
    }

    public ScheduleGetResponseDto get(String username, Long scheduleId) {
        User user = userRepository.findByUsername(username)
                .orElse(null);
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(EntityNotFoundException::new);

        List<ReviewGetResponseDto> reviews = new ArrayList<>();
        schedule.getReviews().forEach(review -> reviews.add(ReviewGetResponseDto.from(review)));
        List<String> hashes = new ArrayList<>();
        schedule.getTags().forEach(tag -> hashes.add(tag.getHash().getName()));

        int isBuy = 0;
        if (user != null && buyRepository.findByUserAndSchedule(user, schedule).isPresent()) {
            isBuy = 1;
        }

        return ScheduleGetResponseDto.from(schedule, reviews, hashes, isBuy);
    }

    public ScheduleTopResponseDto top() {
        List<Schedule> schedules = scheduleRepository.getScheduleTop();
        List<ScheduleListElementDto> scheduleElements = getScheduleElements(schedules);
        return ScheduleTopResponseDto.from(scheduleElements);
    }

    public ScheduleListResponseDto search(ScheduleListRequestDto req) {
        List<Schedule> schedules = scheduleRepository.getScheduleList(req);
        List<ScheduleListElementDto> scheduleElements = getScheduleElements(schedules);
        return ScheduleListResponseDto.from(scheduleElements);
    }

    private List<ScheduleListElementDto> getScheduleElements(List<Schedule> schedules) {
        List<ScheduleListElementDto> scheduleElements = new ArrayList<>();
        for (Schedule schedule : schedules) {
            List<String> hashes = new ArrayList<>();
            schedule.getTags().forEach(tag -> hashes.add(tag.getHash().getName()));
            ScheduleListElementDto scheduleElement = ScheduleListElementDto.from(schedule, hashes);
            scheduleElements.add(scheduleElement);
        }
        return scheduleElements;
    }

    @Transactional
    public ScheduleInsertResponseDto insert(String username, ScheduleInsertRequestDto req, List<MultipartFile> multipartFiles, MultipartFile thumbnail) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        Schedule schedule = Schedule.builder()
                .user(user)
                .name(req.getName())
                .region(req.getRegion())
                .pointPrice(300)
                .description(req.getDescription())
                .isGuide(req.getIsGuide())
                .totalPrice(req.getTotalPrice())
                .build();

        scheduleRepository.save(schedule);
        saveTags(req.getHashes(), schedule);

        try {
            imageRepository.save(fileHandler.parseFile(thumbnail, schedule));
        } catch (Exception e) {
            log.error("파일 입출력 에러");
            log.error(e.getMessage());
        }

        savePlans(req.getPlans(), schedule, multipartFiles);
        log.info("스케줄 등록");

        return ScheduleInsertResponseDto.from("success");
    }

    public void savePlans(List<PlanInsertRequestDto> reqPlans, Schedule schedule, List<MultipartFile> multipartFiles) {
        int lastIndex = 0;
        for (PlanInsertRequestDto reqPlan : reqPlans) {
            Plan plan = Plan.builder()
                    .name(reqPlan.getName())
                    .category(reqPlan.getCategory())
                    .planOrder(reqPlan.getPlanOrder())
                    .description(reqPlan.getDescription())
                    .address(reqPlan.getAddress())
                    .startAt(reqPlan.getStartAt())
                    .endAt(reqPlan.getEndAt())
                    .sumItemPrice(reqPlan.getSumItemPrice())
                    .schedule(schedule)
                    .build();
            planRepository.save(plan);

            List<MultipartFile> thisFiles = multipartFiles.subList(lastIndex, lastIndex + reqPlan.getImageCnt());
            lastIndex += reqPlan.getImageCnt();

            try {
                imageRepository.saveAll(fileHandler.parseFiles(thisFiles, plan));
            } catch (Exception e) {
                log.error("파일 입출력 에러");
                log.error(e.getMessage());
                throw new RuntimeException();
            }
            saveItems(reqPlan.getItems(), plan);
        }
    }

    public void saveItems(List<ItemInsertRequestDto> reqItems, Plan plan) {
        for (ItemInsertRequestDto reqItem : reqItems) {
            Item item = Item.builder()
                    .name(reqItem.getName())
                    .price(reqItem.getPrice())
                    .plan(plan)
                    .build();
            itemRepository.save(item);
        }
    }

    public void saveTags(List<String> hashes, Schedule schedule) {
        for (String name : hashes) {
            Hash hash = hashRepository.findByName(name)
                    .orElse(Hash.builder().name(name).build());
            hashRepository.save(hash);

            Tag tag = Tag.builder()
                    .hash(hash)
                    .schedule(schedule)
                    .build();
            tagRepository.save(tag);
        }
    }
}
