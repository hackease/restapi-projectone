package com.hackease.restapiprojectone.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    
    private Integer status;
    
    private String message;
    
    private T data;
    
}
