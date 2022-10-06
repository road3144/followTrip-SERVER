package com.road3144.followtrip.infra.file;

import java.util.UUID;

public final class MultipartUtil {
    private static final String BASE_DIR = "images";

    public static String getLocalHomeDirectory() {
        return System.getProperty("user.home");
    }

    public static String createFileId() {
        return UUID.randomUUID().toString();
    }

    public static String getFormat(String originalFileName) {
        if (originalFileName.toLowerCase().endsWith(".jpg") ||
            originalFileName.toLowerCase().endsWith(".jpeg")) {
            return "jpg";
        } else if (originalFileName.toLowerCase().endsWith(".png")) {
            return "png";
        }
        throw new RuntimeException("지원하지 않는 확장자");
    }

    public static String createPath(String fileId, String format) {
        return String.format("%s/%s.%s", BASE_DIR, fileId, format);
    }
}