package com.hackease.restapiprojectone.services;

import com.hackease.restapiprojectone.Exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.Exceptions.ValidationException;
import com.hackease.restapiprojectone.domain.dtos.AuthorDto;
import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    
    AuthorDto save(
            AuthorEntity author
    ) throws ValidationException;
    
    AuthorDto getOne(Integer id) throws DataNotFoundException;
    
    List<AuthorDto> getAll(Pageable pageable) throws DataNotFoundException;
    
    AuthorDto partialUpdate(
            Integer id,
            AuthorEntity author
    ) throws DataNotFoundException, ValidationException;
    
    void delete(Integer id) throws DataNotFoundException;
}
