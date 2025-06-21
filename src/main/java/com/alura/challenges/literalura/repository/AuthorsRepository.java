package com.alura.challenges.literalura.repository;

import com.alura.challenges.literalura.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorsRepository extends JpaRepository<AuthorEntity,Long> {
    @Query("SELECT a FROM AuthorEntity a WHERE a.birth <= :year AND (a.death IS NULL OR a.death >= :year) ORDER BY a.name DESC")
    List<AuthorEntity> findAuthorsAliveInYear(@Param("year") Integer year);
}
