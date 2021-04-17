package org.azerabshv.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public void init();

    public void save(MultipartFile file);

    public Resource load(String filename);

    public void deleteAll();
}
