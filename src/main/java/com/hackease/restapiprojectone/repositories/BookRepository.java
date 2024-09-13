package com.hackease.restapiprojectone.repositories;

import com.hackease.restapiprojectone.domains.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String>, PagingAndSortingRepository<BookEntity, String> {
}
