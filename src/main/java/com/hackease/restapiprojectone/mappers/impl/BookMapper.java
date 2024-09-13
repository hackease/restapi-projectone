package com.hackease.restapiprojectone.mappers.impl;

import com.hackease.restapiprojectone.domains.dtos.BookDto;
import com.hackease.restapiprojectone.domains.entities.BookEntity;
import com.hackease.restapiprojectone.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {
    
    private final ModelMapper modelMapper;
    
    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    @Override
    public BookDto mapToDto(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }
    
    @Override
    public BookEntity mapToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
    
}
