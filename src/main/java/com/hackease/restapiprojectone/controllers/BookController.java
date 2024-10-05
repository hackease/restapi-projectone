package com.hackease.restapiprojectone.controllers;

import com.hackease.restapiprojectone.exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.exceptions.ValidationException;
import com.hackease.restapiprojectone.domain.dtos.BookDto;
import com.hackease.restapiprojectone.domain.dtos.ResponseDto;
import com.hackease.restapiprojectone.services.BookService;
import com.hackease.restapiprojectone.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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
    ) throws ValidationException {
        
        boolean isExist = bookService.isExist(isbn);
        BookDto savedUpdatedBook = bookService.saveUpdate(isbn, bookDto);
        
        if (isExist) {
            return new ResponseEntity<>(
                    new ResponseDto<>(
                            HttpStatus.OK.value(),
                            Constants.BOOK_CHANGED_SUCCESS,
                            savedUpdatedBook
                    ), HttpStatus.OK
            );
        }
        
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
    public ResponseEntity<ResponseDto<List<BookDto>>> getAll()
            throws DataNotFoundException {
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        Constants.BOOKS_FETCH_SUCCESS,
                        bookService.getAll()
                ), HttpStatus.OK
        );
    }
    
    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<ResponseDto<BookDto>> partialUpdate(
            @PathVariable(name = "isbn") String isbn,
            @RequestBody BookDto bookDto
    ) throws DataNotFoundException, ValidationException {
        bookDto.setIsbn(isbn);
        
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        Constants.BOOK_UPDATE_SUCCESS,
                        bookService.partialUpdate(isbn, bookDto)
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
                        HttpStatus.OK.value(),
                        Constants.BOOK_DELETE_SUCCESS,
                        null
                ), HttpStatus.OK
        );
    }
    
}
