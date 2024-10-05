package com.hackease.restapiprojectone.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    
    private Integer id;
    
    private String name;
    
    private Integer age;

}
