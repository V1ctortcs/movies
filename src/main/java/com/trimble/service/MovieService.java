package com.trimble.service;

import com.trimble.entity.MovieEntity;
import com.trimble.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository repository;

    public List<MovieEntity> getMovies(){
        return repository.findAll();
    }

    public MovieEntity getMovieById(Integer id){
        return repository.findByCodMovie(id);
    }

    public MovieEntity postMovies(MovieEntity entity){ return  repository.save(entity); }

}
