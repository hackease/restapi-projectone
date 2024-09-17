package com.hackease.restapiprojectone.domain.dtos;

import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    
    private Integer id;
    
    private String name;
    
    private Integer age;
    
    public AuthorEntity toEntity(){
        return new AuthorEntity(
                this.id,
                this.name,
                this.age
        );
    }

}
