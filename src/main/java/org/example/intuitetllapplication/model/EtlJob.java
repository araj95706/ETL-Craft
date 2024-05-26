package org.example.intuitetllapplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class EtlJob extends BaseModel {
    private Long job_id;
    private String job_name;
    @Enumerated(EnumType.ORDINAL)
    private Status jobStatus;
    @ManyToOne
    private Source Source;
    @ManyToOne
    private Mapping Mapping;
    // how many records we wanna process in one batch
    private Long batch_size;
    private String bucket_name;
    private String file_name;
}

