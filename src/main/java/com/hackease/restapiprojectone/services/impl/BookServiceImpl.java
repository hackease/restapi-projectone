package com.hackease.restapiprojectone.services.impl;

import com.hackease.restapiprojectone.exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.exceptions.ValidationException;
import com.hackease.restapiprojectone.domain.dtos.BookDto;
import com.hackease.restapiprojectone.domain.entities.BookEntity;
import com.hackease.restapiprojectone.repositories.BookRepository;
import com.hackease.restapiprojectone.services.BookService;
import com.hackease.restapiprojectone.mappers.BookMapper;
import com.hackease.restapiprojectone.services.helper.RequestValidationChecker;
import com.hackease.restapiprojectone.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookServiceImpl implements BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BookMapper bookMapper;
    
    @Autowired
    private RequestValidationChecker requestValidationChecker;
    
    @Override
    public BookDto saveUpdate(String isbn, BookDto bookDto) throws ValidationException {
        
        BookEntity bookEntity = bookMapper.toEntity(bookDto);
        
        bookEntity.setIsbn(isbn);
        
        if (bookEntity.getTitle() != null)
            requestValidationChecker.validationCheck(bookEntity.getTitle());
        if (bookEntity.getAuthor() != null) {
            if (bookEntity.getAuthor().getName() != null)
                requestValidationChecker.validationCheck(bookEntity.getAuthor().getName());
            if (bookEntity.getAuthor().getAge() != null)
                requestValidationChecker.validationCheck(bookEntity.getAuthor().getAge());
        }
        
        return bookMapper.toDto(bookRepository.save(bookEntity));
    }
    
    @Override
    public BookDto getOne(String isbn) throws DataNotFoundException {
        BookEntity bookEntity = bookRepository.findById(isbn).orElseThrow(
                () -> new DataNotFoundException(Constants.BOOK_NOT_FOUND)
        );
        return bookMapper.toDto(bookEntity);
    }
    
    @Override
    public List<BookDto> getAll() throws DataNotFoundException {
        List<BookEntity> bookEntityList = bookRepository.findAll();
        
        if (bookEntityList.isEmpty())
            throw new DataNotFoundException(Constants.BOOKS_NOT_FOUND);
        
        Stream<BookEntity> bookEntityStream = bookEntityList.stream();
        Stream<BookDto> bookDtoStream = bookEntityStream.map(bookMapper::toDto);
        
        return bookDtoStream.collect(Collectors.toList());
    }
    
    @Override
    public boolean isExist(String isbn) {
        return bookRepository.existsById(isbn);
    }
    
    @Override
    public BookDto partialUpdate(
            String isbn,
            BookDto bookDto
    ) throws DataNotFoundException, ValidationException {
        BookEntity existingBook = bookRepository.findById(isbn).orElseThrow(
                () -> new DataNotFoundException(Constants.BOOK_NOT_FOUND)
        );
        
        BookEntity bookEntity = bookMapper.toEntity(bookDto);
        
        if (bookEntity.getTitle() != null) {
            requestValidationChecker.validationCheck(bookEntity.getTitle());
            existingBook.setTitle(bookEntity.getTitle());
        }
        if (bookEntity.getAuthor() != null) {
            if (bookEntity.getAuthor().getName() != null) {
                requestValidationChecker.validationCheck(bookEntity.getAuthor().getName());
                existingBook.getAuthor().setName(bookEntity.getAuthor().getName());
            }
            if (bookEntity.getAuthor().getAge() != null) {
                requestValidationChecker.validationCheck(bookEntity.getAuthor().getAge());
                existingBook.getAuthor().setAge(bookEntity.getAuthor().getAge());
            }
        }
        
        return bookMapper.toDto(bookRepository.save(existingBook));
    }
    
    @Override
    public void delete(String isbn) throws DataNotFoundException {
        BookEntity existingBook = bookRepository.findById(isbn).orElseThrow(
                () -> new DataNotFoundException(Constants.BOOK_NOT_FOUND)
        );
        
        bookRepository.delete(existingBook);
    }
}
