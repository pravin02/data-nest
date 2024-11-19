package org.pk.datanest.strategy.executor.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static void saveFile(String path, String content) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
        }
    }
}
