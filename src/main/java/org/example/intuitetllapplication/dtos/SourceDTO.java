package org.example.intuitetllapplication.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.intuitetllapplication.model.Mapping;
import org.example.intuitetllapplication.model.SourceType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SourceDTO extends BaseDTO {
    private String name;
    private int source_type;
    private String source_path;
    private String bucketName;
    private String fileName;
    private List<Long> allowedMappings = new ArrayList<>();
}
