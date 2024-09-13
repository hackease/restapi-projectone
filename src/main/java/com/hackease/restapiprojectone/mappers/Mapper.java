package com.hackease.restapiprojectone.mappers;

public interface Mapper<Entity, Dto> {
    
    Dto mapToDto(Entity entity);
    
    Entity mapToEntity(Dto dto);
    
}
