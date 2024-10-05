package com.hackease.restapiprojectone.services;

import com.hackease.restapiprojectone.exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.exceptions.ValidationException;
import com.hackease.restapiprojectone.domain.dtos.AuthorDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    
    AuthorDto save(
            AuthorDto authorDto
    ) throws ValidationException;
    
    AuthorDto getOne(Integer id) throws DataNotFoundException;
    
    List<AuthorDto> getAll() throws DataNotFoundException;
    
    AuthorDto partialUpdate(
            Integer id,
            AuthorDto authorDto
    ) throws DataNotFoundException, ValidationException;
    
    void delete(Integer id) throws DataNotFoundException;
}
