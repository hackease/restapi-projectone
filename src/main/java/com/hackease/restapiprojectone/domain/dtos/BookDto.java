package com.hackease.restapiprojectone.domain.dtos;

import com.hackease.restapiprojectone.domain.entities.BookEntity;
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
    
    public BookEntity toEntity() {
        return new BookEntity(
                this.isbn,
                this.title,
                this.author.toEntity()
        );
    }
    
}
