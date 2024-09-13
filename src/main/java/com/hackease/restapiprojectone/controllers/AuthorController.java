package com.hackease.restapiprojectone.controllers;

import com.hackease.restapiprojectone.domains.dtos.AuthorDto;
import com.hackease.restapiprojectone.domains.entities.AuthorEntity;
import com.hackease.restapiprojectone.mappers.Mapper;
import com.hackease.restapiprojectone.mappers.impl.AuthorMapper;
import com.hackease.restapiprojectone.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class AuthorController {
    
    private final AuthorService authorService;
    
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;
    
    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }
    
    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> save(@RequestBody AuthorDto authorDto) {
        AuthorEntity author = authorMapper.mapToEntity(authorDto);
        AuthorEntity savedAuthor = authorService.save(author);
        return new ResponseEntity<>(authorMapper.mapToDto(savedAuthor), HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getOne(@PathVariable(name = "id") Integer id) {
        if (authorService.getOne(id).isPresent()) {
            AuthorEntity authorFound = authorService.getOne(id).get();
            return new ResponseEntity<>(
                    authorMapper.mapToDto(authorFound),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(path = "/authors")
    public Page<AuthorDto> getAll(Pageable pageable) {
        Page<AuthorEntity> authorList =  authorService.getAll(pageable);
        return authorList.map(authorMapper::mapToDto);
    }
    
    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdate(
            @PathVariable(name = "id") Integer id,
            @RequestBody AuthorDto authorDto) {
        if (authorService.isExist(id)) {
            authorDto.setId(id);
            AuthorEntity author = authorMapper.mapToEntity(authorDto);
            AuthorEntity savedAuthor = authorService.save(author);
            return new ResponseEntity<>(
                    authorMapper.mapToDto(savedAuthor),
                    HttpStatus.OK
            );
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(
            @PathVariable(name = "id") Integer id,
            @RequestBody AuthorDto authorDto) {
        if (authorService.isExist(id)) {
            authorDto.setId(id);
            AuthorEntity author = authorMapper.mapToEntity(authorDto);
            AuthorEntity savedAuthor = authorService.partialUpdate(id, author);
            return new ResponseEntity<>(
                    authorMapper.mapToDto(savedAuthor),
                    HttpStatus.OK
            );
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
