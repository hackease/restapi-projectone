package com.hackease.restapiprojectone.services.impl;

import com.hackease.restapiprojectone.domains.entities.BookEntity;
import com.hackease.restapiprojectone.repositories.BookRepository;
import com.hackease.restapiprojectone.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;
    
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Override
    public BookEntity saveUpdate(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
    
    @Override
    public Optional<BookEntity> getOne(String isbn) {
        return bookRepository.findById(isbn);
    }
    
    @Override
    public List<BookEntity> getAll() {
        return bookRepository.findAll();
    }
    
    @Override
    public Page<BookEntity> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    
    @Override
    public boolean isExist(String isbn) {
        return bookRepository.existsById(isbn);
    }
    
    @Override
    public BookEntity partialUpdate(String isbn, BookEntity book) {
        BookEntity existingBook = bookRepository.findById(isbn).orElseThrow();
        
        if (book.getTitle() != null) existingBook.setTitle(book.getTitle());
        if (book.getAuthor() != null) existingBook.setAuthor(book.getAuthor());
        
        return bookRepository.save(existingBook);
    }
    
    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
