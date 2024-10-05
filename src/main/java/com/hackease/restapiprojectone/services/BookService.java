package com.hackease.restapiprojectone.services;

import com.hackease.restapiprojectone.exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.exceptions.ValidationException;
import com.hackease.restapiprojectone.domain.dtos.BookDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    
    BookDto saveUpdate(
            String isbn,
            BookDto bookDto
    ) throws ValidationException;
    
    BookDto getOne(String isbn) throws DataNotFoundException;
    
    List<BookDto> getAll() throws DataNotFoundException;
    
    boolean isExist(String isbn);
    
    BookDto partialUpdate(
            String isbn,
            BookDto bookDto
    ) throws DataNotFoundException, ValidationException;
    
    void delete(String isbn) throws DataNotFoundException;
    
}
