package com.hackease.restapiprojectone.services.helper;

import com.hackease.restapiprojectone.domain.dtos.AuthorDto;
import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthorServiceHelper {
    
    public AuthorDto toDto(AuthorEntity authorEntity) {
        return new AuthorDto(
                authorEntity.getId(),
                authorEntity.getName(),
                authorEntity.getAge()
        );
    }
    
}
