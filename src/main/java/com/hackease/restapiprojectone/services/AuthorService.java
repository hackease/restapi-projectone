package com.hackease.restapiprojectone.services;

import com.hackease.restapiprojectone.domains.entities.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AuthorService {
    
    AuthorEntity save(AuthorEntity author);
    
    Optional<AuthorEntity> getOne(Integer id);
    
    List<AuthorEntity> getAll();
    
    Page<AuthorEntity> getAll(Pageable pageable);
    
    boolean isExist(Integer id);
    
    AuthorEntity partialUpdate(Integer id, AuthorEntity author);
    
    void delete(Integer id);
}
