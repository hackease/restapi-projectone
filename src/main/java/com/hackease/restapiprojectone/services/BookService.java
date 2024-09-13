package com.hackease.restapiprojectone.services;

import com.hackease.restapiprojectone.domains.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    
    BookEntity saveUpdate(String isbn, BookEntity book);
    
    Optional<BookEntity> getOne(String isbn);
    
    List<BookEntity> getAll();
    
    Page<BookEntity> getAll(Pageable pageable);
    
    boolean isExist(String isbn);
    
    BookEntity partialUpdate(String isbn, BookEntity book);
    
    void delete(String isbn);
    
}
