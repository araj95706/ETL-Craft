package org.example.intuitetllapplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Source extends BaseModel {
    private String name;
    @ManyToMany
    private List<Mapping> allowedMappings = new ArrayList<Mapping>();
    private String source_path;
    private String bucketName;
    private String fileName;
    @Enumerated(EnumType.ORDINAL)
    private SourceType sourceType;
}
