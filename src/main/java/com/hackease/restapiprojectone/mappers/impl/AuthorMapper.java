package com.hackease.restapiprojectone.mappers.impl;

import com.hackease.restapiprojectone.domains.dtos.AuthorDto;
import com.hackease.restapiprojectone.domains.entities.AuthorEntity;
import com.hackease.restapiprojectone.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {
    
    private final ModelMapper modelMapper;
    
    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    @Override
    public AuthorDto mapToDto(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }
    
    @Override
    public AuthorEntity mapToEntity(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
    
}
