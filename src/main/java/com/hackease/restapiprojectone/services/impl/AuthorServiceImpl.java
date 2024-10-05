package com.hackease.restapiprojectone.services.impl;

import com.hackease.restapiprojectone.exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.exceptions.ValidationException;
import com.hackease.restapiprojectone.domain.dtos.AuthorDto;
import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import com.hackease.restapiprojectone.repositories.AuthorRepository;
import com.hackease.restapiprojectone.services.AuthorService;
import com.hackease.restapiprojectone.mappers.AuthorMapper;
import com.hackease.restapiprojectone.services.helper.RequestValidationChecker;
import com.hackease.restapiprojectone.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuthorServiceImpl implements AuthorService {
    
    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private AuthorMapper authorMapper;
    
    @Autowired
    private RequestValidationChecker requestValidationChecker;
    
    @Override
    public AuthorDto save(AuthorDto authorDto) throws ValidationException {
        AuthorEntity authorEntity = authorMapper.toEntity(authorDto);
        
        if (authorEntity.getName() != null)
            requestValidationChecker.validationCheck(authorEntity.getName());
        if (authorEntity.getAge() != null)
            requestValidationChecker.validationCheck(authorEntity.getAge());
        
        return authorMapper.toDto(authorRepository.save(authorEntity));
    }
    
    @Override
    public AuthorDto getOne(Integer id) throws DataNotFoundException {
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(Constants.AUTHOR_NOT_FOUND)
        );
        
        return authorMapper.toDto(authorEntity);
    }
    
    @Override
    public List<AuthorDto> getAll() throws DataNotFoundException {
        List<AuthorEntity> authorEntityList = authorRepository.findAll();
        
        if (authorEntityList.isEmpty())
            throw new DataNotFoundException(Constants.AUTHORS_NOT_FOUND);
        
        Stream<AuthorEntity> authorEntityStream = authorEntityList.stream();
        Stream<AuthorDto> authorDtoStream = authorEntityStream.map(authorMapper::toDto);
        
        return authorDtoStream.collect(Collectors.toList());
    }
    
    @Override
    public AuthorDto partialUpdate(
            Integer id,
            AuthorDto authorDto
    ) throws DataNotFoundException, ValidationException {
        AuthorEntity existingAuthor = authorRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(Constants.BOOK_NOT_FOUND)
        );
        
        AuthorEntity authorEntity = authorMapper.toEntity(authorDto);
        
        if (authorEntity.getName() != null) {
            requestValidationChecker.validationCheck(authorEntity.getName());
            existingAuthor.setName(authorEntity.getName());
        }
        if (authorEntity.getAge() != null) {
            requestValidationChecker.validationCheck(authorEntity.getAge());
            existingAuthor.setAge(authorEntity.getAge());
        }
        
        return authorMapper.toDto(authorRepository.save(existingAuthor));
    }
    
    @Override
    public void delete(Integer id) throws DataNotFoundException {
        AuthorEntity existingAuthor = authorRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(Constants.AUTHOR_NOT_FOUND)
        );
        
        authorRepository.delete(existingAuthor);
    }
    
}
