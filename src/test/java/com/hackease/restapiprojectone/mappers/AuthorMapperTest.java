package com.hackease.restapiprojectone.mappers;

import com.hackease.restapiprojectone.domain.dtos.AuthorDto;
import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperTest {
    
    private AuthorMapper authorMapper;
    
    @BeforeEach
    void setUp() {
        authorMapper = new AuthorMapper();
    }
    
    @Test
    public void testThatShouldMapAuthorEntityToAuthorDto() {
        // Given
        AuthorEntity authorEntity = new AuthorEntity(1, "Hack Ease", 26);
        
        // When
        AuthorDto authorDto = authorMapper.toDto(authorEntity);
        
        // Then
        assertEquals(authorEntity.getId(), authorDto.getId());
        assertEquals(authorEntity.getName(), authorDto.getName());
        assertEquals(authorEntity.getAge(), authorDto.getAge());
        
    }
    
    @Test
    public void testThatShouldMapAuthorDtoToAuthorEntity() {
        // Given
        AuthorDto authorDto = new AuthorDto(1, "Hack Ease", 26);
        
        // When
        AuthorEntity authorEntity = authorMapper.toEntity(authorDto);
        
        // Then
        assertEquals(authorDto.getId(), authorEntity.getId());
        assertEquals(authorDto.getName(), authorEntity.getName());
        assertEquals(authorDto.getAge(), authorEntity.getAge());
        
    }
    
}