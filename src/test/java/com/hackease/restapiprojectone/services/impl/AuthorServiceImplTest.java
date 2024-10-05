package com.hackease.restapiprojectone.services.impl;

import com.hackease.restapiprojectone.domain.dtos.AuthorDto;
import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import com.hackease.restapiprojectone.exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.exceptions.ValidationException;
import com.hackease.restapiprojectone.mappers.AuthorMapper;
import com.hackease.restapiprojectone.repositories.AuthorRepository;
import com.hackease.restapiprojectone.services.helper.RequestValidationChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {
    
    @InjectMocks
    private AuthorServiceImpl authorService;
    
    @Mock
    private AuthorRepository authorRepository;
    
    @Mock
    private AuthorMapper authorMapper;
    
    @Mock
    private RequestValidationChecker requestValidationChecker;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testThatShouldSaveAnAuthor() throws ValidationException {
        // Given
        AuthorDto authorDto = createNewAuthorDto();
        
        AuthorEntity authorEntity = createNewAuthorEntity();
        AuthorEntity savedAuthorEntity = createNewAuthorEntity();
        AuthorDto savedAuthorDto = createNewAuthorDto();
        
        // Mock the calls
        when(authorMapper.toEntity(authorDto)).thenReturn(authorEntity);
        doNothing().when(requestValidationChecker).validationCheck(authorEntity.getName());
        doNothing().when(requestValidationChecker).validationCheck(authorEntity.getAge());
        when(authorRepository.save(authorEntity)).thenReturn(savedAuthorEntity);
        when(authorMapper.toDto(savedAuthorEntity)).thenReturn(savedAuthorDto);
        
        // When
        AuthorDto actualSavedAuthorDto = authorService.save(savedAuthorDto);
        
        // Then
        assertEquals(savedAuthorDto.getId(), actualSavedAuthorDto.getId());
        assertEquals(savedAuthorDto.getName(), actualSavedAuthorDto.getName());
        assertEquals(savedAuthorDto.getAge(), actualSavedAuthorDto.getAge());
        
        verify(authorMapper, times(1)).toEntity(authorDto);
        verify(authorRepository, times(1)).save(authorEntity);
        verify(authorMapper, times(1)).toDto(authorEntity);
        
    }
    
    @Test
    public void testThatShouldRetrieveAnAuthorById() throws DataNotFoundException {
        // Given
        Integer authorId = 1;
        
        AuthorEntity retrievedAuthorEntity = createNewAuthorEntity();
        AuthorDto authorDto = createNewAuthorDto();
        
        // Mock the calls
        when(authorRepository.findById(authorId))
                .thenReturn(Optional.of(retrievedAuthorEntity));
        when(authorMapper.toDto(retrievedAuthorEntity)).thenReturn(authorDto);
        
        // When
        AuthorDto retrievedAuthorDto = authorService.getOne(authorId);
        
        // Then
        assertEquals(authorDto.getId(), retrievedAuthorDto.getId());
        assertEquals(authorDto.getName(), retrievedAuthorDto.getName());
        assertEquals(authorDto.getAge(), retrievedAuthorDto.getAge());
        
        verify(authorRepository, times(1)).findById(authorId);
        verify(authorMapper, times(1)).toDto(retrievedAuthorEntity);
        
    }
    
    @Test
    public void testThatShouldRetrieveAllAuthors() throws DataNotFoundException {
        // Given
        AuthorEntity authorEntity = createNewAuthorEntity();
        AuthorDto authorDto = createNewAuthorDto();
        
        // Mock the calls
        when(authorRepository.findAll()).thenReturn(List.of(authorEntity));
        when(authorMapper.toDto(any(AuthorEntity.class))).thenReturn(authorDto);
        
        // When
        List<AuthorDto> retrievedAuthorDtoPage = authorService.getAll();
        
        // Then
        assertNotNull(retrievedAuthorDtoPage);
        assertEquals(1, retrievedAuthorDtoPage.size());
        assertEquals(
                authorDto.getId(),
                retrievedAuthorDtoPage.getFirst().getId()
        );
        assertEquals(
                authorDto.getName(),
                retrievedAuthorDtoPage.getFirst().getName()
        );
        assertEquals(
                authorDto.getAge(),
                retrievedAuthorDtoPage.getFirst().getAge()
        );
        
        verify(authorRepository, times(1)).findAll();
        verify(authorMapper, times(1)).toDto(any(AuthorEntity.class));
        
    }
    
    @Test
    public void testThatShouldUpdateAnAuthor() throws DataNotFoundException, ValidationException {
        // Given
        Integer authorId = 1;
        AuthorDto authorDto = createNewAuthorDto();
        
        AuthorEntity retrievedExistingAuthorEntity = createNewAuthorEntity();
        AuthorEntity authorEntity = createNewAuthorEntity();
        AuthorEntity savedAuthorEntity = createNewAuthorEntity();
        AuthorDto savedAuthorDto = createNewAuthorDto();
        
        // Mock the calls
        when(authorRepository.findById(authorId))
                .thenReturn(Optional.of(retrievedExistingAuthorEntity));
        when(authorMapper.toEntity(authorDto)).thenReturn(authorEntity);
        doNothing().when(requestValidationChecker).validationCheck(authorEntity.getName());
        doNothing().when(requestValidationChecker).validationCheck(authorEntity.getAge());
        when(authorRepository.save(authorEntity)).thenReturn(savedAuthorEntity);
        when(authorMapper.toDto(savedAuthorEntity)).thenReturn(savedAuthorDto);
        
        // When
        AuthorDto updatedAuthorDto = authorService.partialUpdate(authorId, savedAuthorDto);
        
        // Then
        assertEquals(savedAuthorDto.getId(), updatedAuthorDto.getId());
        assertEquals(savedAuthorDto.getName(), updatedAuthorDto.getName());
        assertEquals(savedAuthorDto.getAge(), updatedAuthorDto.getAge());
        
        verify(authorRepository, times(1)).findById(authorId);
        verify(authorMapper, times(1)).toEntity(authorDto);
        verify(authorRepository, times(1)).save(authorEntity);
        verify(authorMapper, times(1)).toDto(authorEntity);
        
    }
    
    @Test
    public void testThatShouldDeleteAnAuthor() throws DataNotFoundException {
        // Given
        Integer authorId = 1;
        
        AuthorEntity existingAuthorEntity = createNewAuthorEntity();
        
        // Mock the calls
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthorEntity));
        doNothing().when(authorRepository).delete(existingAuthorEntity);
        
        // When
        authorService.delete(authorId);
        
        // Then
        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(1)).delete(existingAuthorEntity);
        
    }
    
    private AuthorEntity createNewAuthorEntity() {
        return new AuthorEntity(1, "Hack Ease", 26);
    }
    
    private AuthorDto createNewAuthorDto() {
        return new AuthorDto(1, "Hack Ease", 26);
    }
    
}