package com.hackease.restapiprojectone.services.impl;

import com.hackease.restapiprojectone.Exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.domain.dtos.AuthorDto;
import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import com.hackease.restapiprojectone.repositories.AuthorRepository;
import com.hackease.restapiprojectone.services.AuthorService;
import com.hackease.restapiprojectone.services.helper.AuthorServiceHelper;
import com.hackease.restapiprojectone.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuthorServiceImpl implements AuthorService {
    
    @Autowired
    private AuthorServiceHelper authorServiceHelper;
    
    @Autowired
    private AuthorRepository authorRepository;
    
    @Override
    public AuthorDto save(AuthorEntity author) {
        // TODO: Add Request validation
        return authorServiceHelper.toDto(authorRepository.save(author));
    }
    
    @Override
    public AuthorDto getOne(Integer id) throws DataNotFoundException {
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow(() -> new DataNotFoundException(Constants.AUTHOR_NOT_FOUND));
        return authorServiceHelper.toDto(authorEntity);
    }
    
    @Override
    public List<AuthorDto> getAll(Pageable pageable) {
        Stream<AuthorEntity> authorEntityStream = authorRepository.findAll(pageable).stream();
        Stream<AuthorDto> authorDtoStream = authorEntityStream.map(authorServiceHelper::toDto);
        return authorDtoStream.collect(Collectors.toList());
    }
    
    @Override
    public AuthorDto partialUpdate(
            Integer id,
            AuthorEntity author
    ) throws DataNotFoundException {
        AuthorEntity existingAuthor = authorRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(Constants.BOOK_NOT_FOUND)
        );
        
        if (author.getName() != null) existingAuthor.setName(author.getName());
        if (author.getAge() != null) existingAuthor.setAge(author.getAge());
        
        AuthorEntity authorEntity = authorRepository.save(existingAuthor);
        return authorServiceHelper.toDto(authorEntity);
    }
    
    @Override
    public void delete(Integer id) throws DataNotFoundException {
        AuthorEntity existingAuthor = authorRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(Constants.BOOK_NOT_FOUND)
        );
        
        authorRepository.delete(existingAuthor);
    }
}
