package com.hackease.restapiprojectone.services.impl;

import com.hackease.restapiprojectone.Exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.Exceptions.ValidationException;
import com.hackease.restapiprojectone.domain.dtos.BookDto;
import com.hackease.restapiprojectone.domain.entities.BookEntity;
import com.hackease.restapiprojectone.repositories.BookRepository;
import com.hackease.restapiprojectone.services.BookService;
import com.hackease.restapiprojectone.services.helper.BookServiceHelper;
import com.hackease.restapiprojectone.services.helper.RequestValidationChecker;
import com.hackease.restapiprojectone.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookServiceImpl implements BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BookServiceHelper bookServiceHelper;
    
    @Autowired
    private RequestValidationChecker requestValidationChecker;
    
    @Override
    public BookDto saveUpdate(String isbn, BookEntity book) throws ValidationException {
        book.setIsbn(isbn);
        
        if (book.getTitle() != null)
            requestValidationChecker.validationCheck(book.getTitle());
        if (book.getAuthor() != null) {
            if (book.getAuthor().getName() != null)
                requestValidationChecker.validationCheck(book.getAuthor().getName());
            if (book.getAuthor().getAge() != null)
                requestValidationChecker.validationCheck(book.getAuthor().getAge());
        }
        
        return bookServiceHelper.toDto(bookRepository.save(book));
    }
    
    @Override
    public BookDto getOne(String isbn) throws DataNotFoundException {
        BookEntity bookEntity = bookRepository.findById(isbn).orElseThrow(
                () -> new DataNotFoundException(Constants.BOOK_NOT_FOUND)
        );
        return bookServiceHelper.toDto(bookEntity);
    }
    
    @Override
    public List<BookDto> getAll(Pageable pageable) throws DataNotFoundException {
        Stream<BookEntity> bookEntityStream = bookRepository.findAll(pageable).stream();
        Stream<BookDto> bookDtoStream = bookEntityStream.map(bookServiceHelper::toDto);
        List<BookDto> bookDtoList = bookDtoStream.collect(Collectors.toList());
        if (bookDtoList.isEmpty())
            throw new DataNotFoundException(Constants.BOOKS_NOT_FOUND);
        return bookDtoList;
    }
    
    @Override
    public boolean isExist(String isbn) {
        return bookRepository.existsById(isbn);
    }
    
    @Override
    public BookDto partialUpdate(
            String isbn,
            BookEntity book
    ) throws DataNotFoundException, ValidationException {
        BookEntity existingBook = bookRepository.findById(isbn).orElseThrow(
                () -> new DataNotFoundException(Constants.BOOK_NOT_FOUND)
        );
        
        if (book.getTitle() != null) {
            requestValidationChecker.validationCheck(book.getTitle());
            existingBook.setTitle(book.getTitle());
        }
        if (book.getAuthor() != null) {
            if (book.getAuthor().getName() != null) {
                requestValidationChecker.validationCheck(book.getAuthor().getName());
                existingBook.getAuthor().setName(book.getAuthor().getName());
            }
            if (book.getAuthor().getAge() != null) {
                requestValidationChecker.validationCheck(book.getAuthor().getAge());
                existingBook.getAuthor().setAge(book.getAuthor().getAge());
            }
        }
        
        return bookServiceHelper.toDto(bookRepository.save(existingBook));
    }
    
    @Override
    public void delete(String isbn) throws DataNotFoundException {
        BookEntity existingBook = bookRepository.findById(isbn).orElseThrow(
                () -> new DataNotFoundException(Constants.BOOK_NOT_FOUND)
        );
        
        bookRepository.delete(existingBook);
    }
}
