package org.example.intuitetllapplication.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.example.intuitetllapplication.model.SourceType;

import java.util.List;
@Getter
@Setter
public class MappingDTO extends BaseDTO {
    private String mapping_name;
    private List<String> columnsOrder;
}
