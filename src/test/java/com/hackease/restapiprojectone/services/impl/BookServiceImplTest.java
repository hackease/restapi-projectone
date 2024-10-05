package com.hackease.restapiprojectone.services.impl;

import com.hackease.restapiprojectone.domain.dtos.AuthorDto;
import com.hackease.restapiprojectone.domain.dtos.BookDto;
import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import com.hackease.restapiprojectone.domain.entities.BookEntity;
import com.hackease.restapiprojectone.exceptions.DataNotFoundException;
import com.hackease.restapiprojectone.exceptions.ValidationException;
import com.hackease.restapiprojectone.mappers.BookMapper;
import com.hackease.restapiprojectone.repositories.BookRepository;
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

class BookServiceImplTest {
    
    @InjectMocks
    private BookServiceImpl bookService;
    
    @Mock
    private BookRepository bookRepository;
    
    @Mock
    private BookMapper bookMapper;
    
    @Mock
    private RequestValidationChecker requestValidationChecker;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testThatShouldSaveAndUpdateABook() throws ValidationException {
        // Given
        String bookIsbn = "1234";
        BookDto bookDto = createNewBookDto();
        
        BookEntity bookEntity = createNewBookEntity();
        BookEntity savedBookEntity = createNewBookEntity();
        BookDto savedBookDto = createNewBookDto();
        
        // Mock the calls
        when(bookMapper.toEntity(bookDto)).thenReturn(bookEntity);
        doNothing().when(requestValidationChecker)
                .validationCheck(bookEntity.getTitle());
        doNothing().when(requestValidationChecker)
                .validationCheck(bookEntity.getAuthor().getName());
        doNothing().when(requestValidationChecker)
                .validationCheck(bookEntity.getAuthor().getAge());
        when(bookRepository.save(bookEntity)).thenReturn(savedBookEntity);
        when(bookMapper.toDto(savedBookEntity)).thenReturn(savedBookDto);
        
        // When
        BookDto actualSavedBookDto = bookService.saveUpdate(bookIsbn, savedBookDto);
        
        // Then
        assertEquals(savedBookDto.getIsbn(), actualSavedBookDto.getIsbn());
        assertEquals(savedBookDto.getTitle(), actualSavedBookDto.getTitle());
        assertEquals(savedBookDto.getAuthor(), actualSavedBookDto.getAuthor());
        
        verify(bookMapper, times(1)).toDto(bookEntity);
        verify(bookRepository, times(1)).save(bookEntity);
        verify(bookMapper, times(1)).toEntity(bookDto);
        
    }
    
    @Test
    public void testThatShouldRetrieveABookByIsbn() throws DataNotFoundException {
        // Given
        String bookIsbn = "1234";
        
        BookEntity retrievedBookEntity = createNewBookEntity();
        BookDto bookDto = createNewBookDto();
        
        // Mock the calls
        when(bookRepository.findById(bookIsbn)).thenReturn(Optional.of(retrievedBookEntity));
        when(bookMapper.toDto(retrievedBookEntity)).thenReturn(bookDto);
        
        // When
        BookDto retrievedBookDto = bookService.getOne(bookIsbn);
        
        // Then
        assertEquals(bookDto.getIsbn(), retrievedBookDto.getIsbn());
        assertEquals(bookDto.getTitle(), retrievedBookDto.getTitle());
        assertEquals(bookDto.getAuthor().getId(), retrievedBookDto.getAuthor().getId());
        assertEquals(bookDto.getAuthor().getName(), retrievedBookDto.getAuthor().getName());
        assertEquals(bookDto.getAuthor().getAge(), retrievedBookDto.getAuthor().getAge());
        
        verify(bookRepository, times(1)).findById(bookIsbn);
        verify(bookMapper, times(1)).toDto(retrievedBookEntity);
        
    }
    
    @Test
    public void testThatShouldRetrieveAllBooks() throws DataNotFoundException {
        // Given
        BookEntity bookEntity = createNewBookEntity();
        BookDto bookDto = createNewBookDto();
        
        // Mock the calls
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        when(bookMapper.toDto(any(BookEntity.class))).thenReturn(bookDto);
        
        // When
        List<BookDto> retrievedBookDtoList = bookService.getAll();
        
        // Then
        assertNotNull(retrievedBookDtoList);
        assertEquals(1, retrievedBookDtoList.size());
        assertEquals(
                bookDto.getIsbn(),
                retrievedBookDtoList.getFirst().getIsbn()
        );
        assertEquals(
                bookDto.getTitle(),
                retrievedBookDtoList.getFirst().getTitle()
        );
        assertEquals(
                bookDto.getAuthor().getId(),
                retrievedBookDtoList.getFirst().getAuthor().getId()
        );
        assertEquals(
                bookDto.getAuthor().getName(),
                retrievedBookDtoList.getFirst().getAuthor().getName()
        );
        assertEquals(
                bookDto.getAuthor().getAge(),
                retrievedBookDtoList.getFirst().getAuthor().getAge()
        );
        
        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, times(1)).toDto(any(BookEntity.class));
        
    }
    
    @Test
    public void testThatShouldCheckIfABookExists() {
        // Given
        String bookIsbn = "1234";
        
        // Mock the calls
        when(bookRepository.existsById(bookIsbn)).thenReturn(true);
        
        // When
        boolean isExists = bookService.isExist(bookIsbn);
        
        // Then
        assertTrue(isExists);
        
        verify(bookRepository, times(1)).existsById(bookIsbn);
        
    }
    
    @Test
    public void testThatShouldUpdateABook()
            throws DataNotFoundException, ValidationException {
        // Given
        String bookIsbn = "1234";
        BookDto bookDto = createNewBookDto();
        
        BookEntity retrievedExistingBookEntity = createNewBookEntity();
        BookEntity bookEntity = createNewBookEntity();
        BookEntity savedBookEntity = createNewBookEntity();
        BookDto savedBookDto = createNewBookDto();
        
        // Mock the calls
        when(bookRepository.findById(bookIsbn))
                .thenReturn(Optional.of(retrievedExistingBookEntity));
        when(bookMapper.toEntity(bookDto)).thenReturn(bookEntity);
        doNothing().when(requestValidationChecker)
                .validationCheck(bookEntity.getTitle());
        doNothing().when(requestValidationChecker)
                .validationCheck(bookEntity.getAuthor().getName());
        doNothing().when(requestValidationChecker)
                .validationCheck(bookEntity.getAuthor().getName());
        when(bookRepository.save(bookEntity)).thenReturn(savedBookEntity);
        when(bookMapper.toDto(savedBookEntity)).thenReturn(savedBookDto);
        
        // When
        BookDto updatedBookDto = bookService.partialUpdate(bookIsbn, savedBookDto);
        
        // Then
        assertEquals(savedBookDto.getIsbn(), updatedBookDto.getIsbn());
        assertEquals(savedBookDto.getTitle(), updatedBookDto.getTitle());
        assertEquals(savedBookDto.getAuthor().getId(), updatedBookDto.getAuthor().getId());
        assertEquals(savedBookDto.getAuthor().getName(), updatedBookDto.getAuthor().getName());
        assertEquals(savedBookDto.getAuthor().getAge(), updatedBookDto.getAuthor().getAge());
        
        verify(bookRepository, times(1)).findById(bookIsbn);
        verify(bookMapper, times(1)).toEntity(bookDto);
        verify(bookRepository, times(1)).save(bookEntity);
        verify(bookMapper, times(1)).toDto(savedBookEntity);
        
    }
    
    @Test
    public void testThatShouldDeleteABook() throws DataNotFoundException {
        // Given
        String bookIsbn = "1234";
        
        BookEntity existingBookEntity = createNewBookEntity();
        
        // Mock the calls
        when(bookRepository.findById(bookIsbn)).thenReturn(Optional.of(existingBookEntity));
        doNothing().when(bookRepository).delete(existingBookEntity);
        
        // When
        bookService.delete(bookIsbn);
        
        // Then
        verify(bookRepository, times(1)).findById(bookIsbn);
        verify(bookRepository, times(1)).delete(existingBookEntity);
        
    }
    
    private AuthorEntity createNewAuthorEntity() {
        return new AuthorEntity(1, "Hack Ease", 26);
    }
    
    private AuthorDto createNewAuthorDto() {
        return new AuthorDto(1, "Hack Ease", 26);
    }
    
    private BookEntity createNewBookEntity() {
        return new BookEntity("1234", "Hacks Never Ends", createNewAuthorEntity());
    }
    
    private BookDto createNewBookDto() {
        return new BookDto("1234", "Hacks Never Ends", createNewAuthorDto());
    }
    
}