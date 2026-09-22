package com.aitenant.web_service.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
public class History {

    private int seq;

    @Field("role")
    private String role;

    private String content;

    @Field("timestamp")
    private LocalDateTime time;

}
