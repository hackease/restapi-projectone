package com.hackease.restapiprojectone.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hackease.restapiprojectone.Exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.Exceptions.ValidationException;
import com.hackease.restapiprojectone.domain.dtos.BookDto;
import com.hackease.restapiprojectone.domain.dtos.ResponseDto;
import com.hackease.restapiprojectone.domain.entities.BookEntity;
import com.hackease.restapiprojectone.services.BookService;
import com.hackease.restapiprojectone.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<ResponseDto<BookDto>> saveUpdate(
            @PathVariable(name = "isbn") String isbn,
            @RequestBody BookDto bookDto
    ) throws DataNotFoundException, ValidationException {
        BookEntity book = bookDto.toEntity();
        boolean isExist = bookService.isExist(isbn);
        BookDto savedUpdatedBook = bookService.saveUpdate(isbn, book);
        
        if (isExist) return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        Constants.BOOK_CHANGED_SUCCESS,
                        savedUpdatedBook
                ), HttpStatus.OK
        );
        
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        Constants.BOOK_CREATE_SUCCESS,
                        savedUpdatedBook
                ), HttpStatus.CREATED
        );
    }
    
    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<ResponseDto<BookDto>> getOne(
            @PathVariable(name = "isbn") String isbn
    ) throws DataNotFoundException {
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        Constants.BOOK_FETCH_SUCCESS,
                        bookService.getOne(isbn)
                ), HttpStatus.OK
        );
    }
    
    @GetMapping(path = "/books")
    public ResponseEntity<ResponseDto<List<BookDto>>> getAll(Pageable pageable) {
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        Constants.BOOK_FETCH_SUCCESS,
                        bookService.getAll(pageable)
                ), HttpStatus.OK
        );
    }
    
    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<ResponseDto<BookDto>> partialUpdate(
            @PathVariable(name = "isbn") String isbn,
            @RequestBody BookDto bookDto
    ) throws DataNotFoundException, ValidationException {
        bookDto.setIsbn(isbn);
        BookEntity book = bookDto.toEntity();
        return new ResponseEntity<>(
                new ResponseDto<BookDto>(
                        HttpStatus.OK.value(),
                        Constants.BOOK_UPDATE_SUCCESS,
                        bookService.partialUpdate(isbn, book)
                ), HttpStatus.OK
        );
    }
    
    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<ResponseDto<Void>> delete(
            @PathVariable(name = "isbn") String isbn
    ) throws DataNotFoundException {
        bookService.delete(isbn);
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.NO_CONTENT.value(),
                        Constants.BOOK_DELETE_SUCCESS,
                        null
                ), HttpStatus.NO_CONTENT
        );
    }
    
}
