package com.hackease.restapiprojectone.services.impl;

import com.hackease.restapiprojectone.domains.entities.AuthorEntity;
import com.hackease.restapiprojectone.repositories.AuthorRepository;
import com.hackease.restapiprojectone.repositories.BookRepository;
import com.hackease.restapiprojectone.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    
    private final AuthorRepository authorRepository;
    
    private final BookRepository bookRepository;
    
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }
    
    @Override
    public AuthorEntity save(AuthorEntity author) {
        return authorRepository.save(author);
    }
    
    @Override
    public Optional<AuthorEntity> getOne(Integer id) {
         return authorRepository.findById(id);
    }
    
    @Override
    public List<AuthorEntity> getAll() {
        return authorRepository.findAll();
    }
    
    @Override
    public Page<AuthorEntity> getAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }
    
    @Override
    public boolean isExist(Integer id) {
        return authorRepository.existsById(id);
    }
    
    @Override
    public AuthorEntity partialUpdate(Integer id, AuthorEntity author) {
        AuthorEntity existingAuthor = authorRepository.findById(id).orElseThrow();
        
        if (author.getName() != null) existingAuthor.setName(author.getName());
        if (author.getAge() != null) existingAuthor.setAge(author.getAge());
        
        return authorRepository.save(existingAuthor);
    }
    
    @Override
    public void delete(Integer id) {
        authorRepository.deleteById(id);
    }
}
