package org.pk.datanest.puller.utils;

import org.springframework.util.Assert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static void saveFile(String path, String content) throws IOException {
        Assert.notNull(content, "Content should not be null");
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
        }
    }
}
