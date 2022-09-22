package com.road3144.followtrip.service;

import com.road3144.followtrip.domain.Hash;
import com.road3144.followtrip.domain.Item;
import com.road3144.followtrip.domain.Plan;
import com.road3144.followtrip.domain.Schedule;
import com.road3144.followtrip.domain.Tag;
import com.road3144.followtrip.domain.User;
import com.road3144.followtrip.dto.item.ItemInsertRequestDto;
import com.road3144.followtrip.dto.plan.PlanInsertRequestDto;
import com.road3144.followtrip.dto.schedule.ScheduleInsertRequestDto;
import com.road3144.followtrip.dto.schedule.ScheduleInsertResponseDto;
import com.road3144.followtrip.dto.schedule.ScheduleListElementDto;
import com.road3144.followtrip.dto.schedule.ScheduleListRequestDto;
import com.road3144.followtrip.dto.schedule.ScheduleListResponseDto;
import com.road3144.followtrip.dto.schedule.ScheduleTopResponseDto;
import com.road3144.followtrip.exception.EntityNotFoundException;
import com.road3144.followtrip.infra.FileHandler;
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

    public ScheduleTopResponseDto top() {
        List<Schedule> schedules = scheduleRepository.getScheduleTop();
        List<ScheduleListElementDto> scheduleElements = new ArrayList<>();
        for (Schedule schedule : schedules) {
            List<String> hashes = new ArrayList<>();
            schedule.getTags().forEach(tag -> hashes.add(tag.getHash().getName()));
            ScheduleListElementDto scheduleElement = ScheduleListElementDto.from(schedule, hashes);
            scheduleElements.add(scheduleElement);
        }
        return ScheduleTopResponseDto.from(scheduleElements);
    }

    public ScheduleListResponseDto search(ScheduleListRequestDto req) {
        List<Schedule> schedules = scheduleRepository.getScheduleList(req);
        List<ScheduleListElementDto> scheduleElements = new ArrayList<>();
        for (Schedule schedule : schedules) {
            List<String> hashes = new ArrayList<>();
            schedule.getTags().forEach(tag -> hashes.add(tag.getHash().getName()));
            ScheduleListElementDto scheduleElement = ScheduleListElementDto.from(schedule, hashes);
            scheduleElements.add(scheduleElement);
        }
        return ScheduleListResponseDto.from(scheduleElements);
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
            imageRepository.save(fileHandler.parseFile(thumbnail));
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
                    .sumItemPrice(reqPlan.getSumItemPrice())
                    .schedule(schedule)
                    .build();
            planRepository.save(plan);

            List<MultipartFile> thisFiles = multipartFiles.subList(lastIndex, lastIndex + reqPlan.getImageCnt());
            lastIndex += reqPlan.getImageCnt();

            try {
                imageRepository.saveAll(fileHandler.parseFiles(thisFiles));
            } catch (Exception e) {
                log.error("파일 입출력 에러");
                log.error(e.getMessage());
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
