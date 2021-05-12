package org.azerabshv.models;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FileInfo {
    private String name;
    private String url;
}
