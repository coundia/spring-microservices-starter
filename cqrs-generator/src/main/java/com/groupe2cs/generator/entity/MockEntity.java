package com.groupe2cs.generator.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MockEntity {
    private String id;
    private String name;
    private String description;
    private Integer number;
    private Date createdAt;
}