package com.hackease.restapiprojectone.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    
    private String isbn;
    
    private String title;
    
    private AuthorDto author;
    
}
