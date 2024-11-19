package org.pk.datanest.strategy.executor.constant;

import org.springframework.util.Assert;

public enum FileType {

    CSV, JSON, HTML, XLS;

    public static FileType getFileType(String fileType) {
        Assert.notNull(fileType, "File Type must not be null");
        for (FileType ft : FileType.values()) {
            if (ft.name().equalsIgnoreCase(fileType)) {
                return ft;
            }
        }
        return null;
    }
}
