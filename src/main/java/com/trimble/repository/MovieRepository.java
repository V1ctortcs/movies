package com.trimble.repository;

import com.trimble.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    MovieEntity findByCodMovie(Integer id);
}
