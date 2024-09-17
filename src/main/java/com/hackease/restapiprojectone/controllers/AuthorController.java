package com.hackease.restapiprojectone.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hackease.restapiprojectone.Exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.Exceptions.ValidationException;
import com.hackease.restapiprojectone.domain.dtos.AuthorDto;
import com.hackease.restapiprojectone.domain.dtos.ResponseDto;
import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import com.hackease.restapiprojectone.services.AuthorService;
import com.hackease.restapiprojectone.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {
    
    @Autowired
    private AuthorService authorService;
    
    @PostMapping(path = "/authors")
    public ResponseEntity<ResponseDto<AuthorDto>> save(
            @RequestBody AuthorDto authorDto
    ) throws DataNotFoundException, ValidationException {
        AuthorEntity author = authorDto.toEntity();
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        Constants.AUTHOR_CREATE_SUCCESS,
                        authorService.save(author)
                ), HttpStatus.CREATED
        );
    }
    
    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<ResponseDto<AuthorDto>> getOne(
            @PathVariable(name = "id") Integer id
    ) throws DataNotFoundException {
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        Constants.AUTHOR_FETCH_SUCCESS,
                        authorService.getOne(id)
                ), HttpStatus.OK
        );
    }
    
    @GetMapping(path = "/authors")
    public ResponseEntity<ResponseDto<List<AuthorDto>>> getAll(
            Pageable pageable
    ) {
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        Constants.AUTHOR_FETCH_SUCCESS,
                        authorService.getAll(pageable)
                ), HttpStatus.OK
        );
    }
    
    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<ResponseDto<AuthorDto>> fullUpdate(
            @PathVariable(name = "id") Integer id,
            @RequestBody AuthorDto authorDto
    ) throws DataNotFoundException, ValidationException {
        authorDto.setId(id);
        AuthorEntity author = authorDto.toEntity();
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        Constants.AUTHOR_CHANGED_SUCCESS,
                        authorService.save(author)
                ), HttpStatus.OK
        );
    }
    
    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<ResponseDto<AuthorDto>> partialUpdate(
            @PathVariable(name = "id") Integer id,
            @RequestBody AuthorDto authorDto
    ) throws DataNotFoundException, ValidationException {
        authorDto.setId(id);
        AuthorEntity author = authorDto.toEntity();
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        Constants.AUTHOR_UPDATE_SUCCESS,
                        authorService.partialUpdate(id, author)
                ), HttpStatus.OK
        );
    }
    
    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<ResponseDto<Void>> delete(
            @PathVariable(name = "id") Integer id
    ) throws DataNotFoundException {
        authorService.delete(id);
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.NO_CONTENT.value(),
                        Constants.AUTHOR_DELETE_SUCCESS,
                        null
                ), HttpStatus.NO_CONTENT
        );
    }
    
}
