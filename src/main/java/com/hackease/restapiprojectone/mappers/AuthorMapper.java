package com.hackease.restapiprojectone.mappers;

import com.hackease.restapiprojectone.domain.dtos.AuthorDto;
import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    
    public AuthorDto toDto(AuthorEntity authorEntity) {
        return new AuthorDto(
                authorEntity.getId(),
                authorEntity.getName(),
                authorEntity.getAge()
        );
    }
    
    public AuthorEntity toEntity(AuthorDto authorDto) {
        return new AuthorEntity(
                authorDto.getId(),
                authorDto.getName(),
                authorDto.getAge()
        );
    }
    
}
