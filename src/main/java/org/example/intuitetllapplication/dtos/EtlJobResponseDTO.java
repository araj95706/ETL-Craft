package org.example.intuitetllapplication.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.intuitetllapplication.model.Mapping;
import org.example.intuitetllapplication.model.Source;

@Getter
@Setter
public class EtlJobResponseDTO extends BaseDTO{
    private Long job_id;
    private String job_name;
    private Long source_id;
    private Long mapping_id;
    private Long batch_size;
    private String bucket_name;
    // file name for file which will be uploaded on S3
    private String file_name;
}
