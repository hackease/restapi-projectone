package com.hackease.restapiprojectone.services;

import com.hackease.restapiprojectone.Exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.Exceptions.ValidationException;
import com.hackease.restapiprojectone.domain.dtos.BookDto;
import com.hackease.restapiprojectone.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    
    BookDto saveUpdate(
            String isbn,
            BookEntity book
    ) throws ValidationException;
    
    BookDto getOne(String isbn) throws DataNotFoundException;
    
    List<BookDto> getAll(Pageable pageable) throws DataNotFoundException;
    
    boolean isExist(String isbn);
    
    BookDto partialUpdate(
            String isbn,
            BookEntity book
    ) throws DataNotFoundException, ValidationException;
    
    void delete(String isbn) throws DataNotFoundException;
    
}
