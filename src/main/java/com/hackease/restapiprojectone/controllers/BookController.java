package com.hackease.restapiprojectone.controllers;

import com.hackease.restapiprojectone.domains.dtos.BookDto;
import com.hackease.restapiprojectone.domains.entities.BookEntity;
import com.hackease.restapiprojectone.mappers.Mapper;
import com.hackease.restapiprojectone.mappers.impl.BookMapper;
import com.hackease.restapiprojectone.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class BookController {
    
    private final BookService bookService;
    
    private final Mapper<BookEntity, BookDto> bookMapper;
    
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }
    
    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> saveUpdate(
            @PathVariable(name = "isbn") String isbn,
            @RequestBody BookDto bookDto) {
        BookEntity book = bookMapper.mapToEntity(bookDto);
        
        boolean isExist = bookService.isExist(isbn);
        
        BookEntity savedBook = bookService.saveUpdate(isbn, book);
        BookDto savedUpdatedBook = bookMapper.mapToDto(savedBook);
        
        if (isExist) return new ResponseEntity<>(savedUpdatedBook, HttpStatus.OK);
        return new ResponseEntity<>(savedUpdatedBook, HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getOne(@PathVariable(name = "isbn") String isbn) {
        if (bookService.getOne(isbn).isPresent()) {
            BookEntity bookFound = bookService.getOne(isbn).get();
            return new ResponseEntity<>(
                    bookMapper.mapToDto(bookFound),
                    HttpStatusCode.valueOf(200)
            );
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }
    
    @GetMapping(path = "/books")
    public Page<BookDto> getAll(Pageable pageable) {
        Page<BookEntity> bookList = bookService.getAll(pageable);
        return bookList.map(bookMapper::mapToDto);
    }
    
    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdate(
            @PathVariable(name = "isbn") String isbn,
            @RequestBody BookDto bookDto) {
        if (bookService.isExist(isbn)) {
            bookDto.setIsbn(isbn);
            BookEntity book = bookMapper.mapToEntity(bookDto);
            BookEntity savedBook = bookService.partialUpdate(isbn, book);
            return new ResponseEntity<>(bookMapper.mapToDto(savedBook), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<Void> delete(@PathVariable(name = "isbn") String isbn) {
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
