package com.road3144.followtrip.infra.file;

import com.road3144.followtrip.domain.Image;
import com.road3144.followtrip.domain.Plan;
import com.road3144.followtrip.domain.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileHandler {

    private final AmazonS3ResourceStorage s3ResourceStorage;

    public List<Image> parseFiles(List<MultipartFile> multipartFiles, Plan plan) throws Exception {

        List<Image> images = new ArrayList<>();

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            for (MultipartFile multipartFile : multipartFiles) {
                Image image = parseFile(multipartFile, plan);
                images.add(image);
            }
        }
        return images;
    }

    private Image parseFile(MultipartFile multipartFile, Plan plan) throws Exception {

        FileDetail fileDetail = FileDetail.from(multipartFile);

        Image image = Image.builder()
                .originName(fileDetail.getName())
                .path(fileDetail.getPath())
                .size(fileDetail.getSize())
                .plan(plan)
                .build();

        s3ResourceStorage.store(fileDetail.getPath(), multipartFile);
        return image;
    }

    public Image parseFile(MultipartFile multipartFile, Schedule schedule) throws Exception {
        FileDetail fileDetail = FileDetail.from(multipartFile);

        Image image = Image.builder()
                .originName(fileDetail.getName())
                .path(fileDetail.getPath())
                .size(fileDetail.getSize())
                .schedule(schedule)
                .build();

        s3ResourceStorage.store(fileDetail.getPath(), multipartFile);
        return image;
    }
}
