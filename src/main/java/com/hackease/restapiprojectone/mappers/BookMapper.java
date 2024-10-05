package com.hackease.restapiprojectone.mappers;

import com.hackease.restapiprojectone.domain.dtos.BookDto;
import com.hackease.restapiprojectone.domain.entities.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    
    @Autowired
    private AuthorMapper authorMapper;
    
    public BookDto toDto(BookEntity bookEntity) {
        return new BookDto(
                bookEntity.getIsbn(),
                bookEntity.getTitle(),
                authorMapper.toDto(bookEntity.getAuthor())
        );
    }
    
    public BookEntity toEntity(BookDto bookDto) {
        return new BookEntity(
                bookDto.getIsbn(),
                bookDto.getTitle(),
                authorMapper.toEntity(bookDto.getAuthor())
        );
    }
    
}
