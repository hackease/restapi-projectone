package com.hackease.restapiprojectone.services.helper;

import com.hackease.restapiprojectone.domain.dtos.BookDto;
import com.hackease.restapiprojectone.domain.entities.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookServiceHelper {
    
    @Autowired
    AuthorServiceHelper authorServiceHelper;
    
    public BookDto toDto(BookEntity bookEntity) {
        return new BookDto(
                bookEntity.getIsbn(),
                bookEntity.getTitle(),
                authorServiceHelper.toDto(bookEntity.getAuthor())
        );
    }
    
}
