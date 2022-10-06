package com.road3144.followtrip.infra.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Getter
@ToString
@NoArgsConstructor
@Builder
public class FileDetail {

    private String name;

    private String format;

    private String path;

    private long size;

    public FileDetail(String name, String format, String path, long size) {
        this.name = name;
        this.format = format;
        this.path = path;
        this.size = size;
    }

    public static FileDetail from(MultipartFile multipartFile) {
        final String fileId = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        return FileDetail.builder()
                .name(multipartFile.getOriginalFilename())
                .format(format)
                .path(MultipartUtil.createPath(fileId, format))
                .size(multipartFile.getSize())
                .build();
    }
}
