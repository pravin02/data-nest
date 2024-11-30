package org.pk.datanest.commons.constant;


public enum FileType {

    CSV, JSON, HTML, XLS;

    public static FileType getFileType(String fileType) {
        for (FileType ft : FileType.values()) {
            if (ft.name().equalsIgnoreCase(fileType)) {
                return ft;
            }
        }
        return null;
    }
}
