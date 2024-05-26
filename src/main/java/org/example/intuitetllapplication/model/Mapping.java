package org.example.intuitetllapplication.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Mapping extends BaseModel{
    private String mapping_name;
    private List<String> columnsOrder;
}
