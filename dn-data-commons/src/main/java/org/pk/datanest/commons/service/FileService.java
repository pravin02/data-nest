package org.pk.datanest.commons.service;

import java.io.IOException;

public interface FileService {

    void save(String fileName, String content) throws IOException;

    void save(String location, String fileName, String content) throws IOException;
}
