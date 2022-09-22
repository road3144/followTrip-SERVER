package com.road3144.followtrip.infra;

import com.road3144.followtrip.domain.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FileHandler {

    public List<Image> parseFiles(List<MultipartFile> multipartFiles) throws Exception {

        List<Image> images = new ArrayList<>();

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            for (MultipartFile multipartFile : multipartFiles) {
                Image image = parseFile(multipartFile);
                images.add(image);
            }
        }
        return images;
    }

    public Image parseFile(MultipartFile multipartFile) throws Exception {

        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
        String path = getPath();
        File file = new File(path);

        makeDir(file);

        String originalFileName = multipartFile.getOriginalFilename();
        String originalFileExtension = makeExtension(originalFileName);
        String newFileName = System.nanoTime() + originalFileExtension;

        Image image = Image.builder()
                .originName(multipartFile.getOriginalFilename())
                .path(path + File.separator + newFileName)
                .size(multipartFile.getSize())
                .build();

        file = new File(absolutePath + path + File.separator + newFileName);
        multipartFile.transferTo(file);

        file.setWritable(true);
        file.setReadable(true);
        return image;
    }

    private String getPath() {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String path = "images" + File.separator + currentDate;
        return path;
    }

    private void makeDir(File file) {
        if (!file.exists() && file.mkdirs()) {
            log.error("디렉토리 생성 실패");
        }
    }

    private String makeExtension(String originalFileName) throws Exception{
        System.out.println("originalFileName = " + originalFileName);
        if (ObjectUtils.isEmpty(originalFileName)) {
            log.error("확장자 없음");
            throw new Exception();
        } else {
            if (originalFileName.endsWith(".jpg") || originalFileName.endsWith(".jpeg")) {
                return ".jpg";
            } else if (originalFileName.endsWith(".png")) {
                return ".png";
            } else {
                log.error("지원하지 않는 확장자");
                throw new Exception();
            }
        }
    }
}
