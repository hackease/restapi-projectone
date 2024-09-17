package com.hackease.restapiprojectone.repositories;

import com.hackease.restapiprojectone.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer>, PagingAndSortingRepository<AuthorEntity, Integer> {
}
