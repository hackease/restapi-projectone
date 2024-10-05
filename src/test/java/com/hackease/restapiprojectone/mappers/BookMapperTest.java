package com.hackease.restapiprojectone.mappers;

import com.hackease.restapiprojectone.domain.dtos.AuthorDto;
import com.hackease.restapiprojectone.domain.dtos.BookDto;
import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import com.hackease.restapiprojectone.domain.entities.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    
    @InjectMocks
    private BookMapper bookMapper;
    
    @Mock
    private AuthorMapper authorMapper;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testThatShouldMapBookEntityToBookDto() {
        // Given
        AuthorEntity authorEntity = new AuthorEntity(1, "Hack Ease",26);
        BookEntity bookEntity = new BookEntity("1234", "Hacks Never Ends", authorEntity);
        AuthorDto authorDto = new AuthorDto(1, "Hack Ease",26);
        
        // Mock the calls
        Mockito.when(authorMapper.toDto(authorEntity)).thenReturn(authorDto);
        
        // When
        BookDto bookDto = bookMapper.toDto(bookEntity);
        
        // Then
        assertEquals(bookEntity.getIsbn(), bookDto.getIsbn());
        assertEquals(bookEntity.getTitle(), bookDto.getTitle());
        assertNotNull(bookDto.getAuthor());
        assertEquals(bookEntity.getAuthor().getId(), bookDto.getAuthor().getId());
        assertEquals(bookEntity.getAuthor().getName(), bookDto.getAuthor().getName());
        assertEquals(bookEntity.getAuthor().getAge(), bookDto.getAuthor().getAge());
        
    }
    
    @Test
    public void testThatShouldMapBookDtoToBookEntity() {
        // Given
        AuthorDto authorDto = new AuthorDto(1, "Hack Ease", 26);
        BookDto bookDto = new BookDto("1234", "Hacks Never Ends", authorDto);
        AuthorEntity authorEntity = new AuthorEntity(1, "Hack Ease", 26);
        
        // Mock the calls
        Mockito.when(authorMapper.toEntity(authorDto)).thenReturn(authorEntity);
        
        // When
        BookEntity bookEntity = bookMapper.toEntity(bookDto);
        
        // Then
        assertEquals(bookDto.getIsbn(), bookEntity.getIsbn());
        assertEquals(bookDto.getTitle(), bookEntity.getTitle());
        assertNotNull(bookEntity.getAuthor());
        assertEquals(bookDto.getAuthor().getId(), bookEntity.getAuthor().getId());
        assertEquals(bookDto.getAuthor().getName(), bookEntity.getAuthor().getName());
        assertEquals(bookDto.getAuthor().getAge(), bookEntity.getAuthor().getAge());
        
    }
    
}