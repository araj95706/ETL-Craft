package org.example.intuitetllapplication.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseDTO {
    private Long id;
    private Date createdAt;
    private Date lastModifiedAt;
}
