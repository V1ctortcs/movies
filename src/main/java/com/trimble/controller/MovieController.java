package com.trimble.controller;

import com.trimble.converter.MovieEntityConverter;
import com.trimble.entity.MovieEntity;
import com.trimble.model.LetterMetricsModel;
import com.trimble.model.MovieModel;
import com.trimble.repository.MovieRepository;
import com.trimble.service.MovieService;
import com.trimble.utils.ValidatorLetters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"Directions for viewing movies"})
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class MovieController {

    public static final String MESSAGE_MOVIE_NOT_FOUND = "{\n  \"message\": \"movie not found\"\n}";

    private final ValidatorLetters validatorLetters;
    private final MovieEntityConverter converter;
    private final MovieService service;

    private MovieModel movieModel;


    @ApiOperation("Returns all registered movies")
    @GetMapping(value = "/movies")
    public ResponseEntity<List<MovieModel>> getMovies() {
        try {
            List<MovieEntity> entities = service.getMovies();
            List<MovieModel> models = converter.toModelList(entities);
            return new ResponseEntity<>(models, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Returns films registered according to Id")
    @GetMapping(value = "/movies/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable("id") Integer id) {
        try {
            MovieEntity entity = service.getMovieById(id);
            if (entity != null) {
                MovieModel model = converter.toModel(entity);
                return new ResponseEntity<>(model, HttpStatus.OK);
            }
            return new ResponseEntity<>(MESSAGE_MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Register a new movie")
    @PostMapping(value = "/movies")
    public ResponseEntity<MovieModel> postMovies(@Validated @RequestBody MovieModel model) {
        try {
           service.postMovies(converter.toEntity(model));
            return new ResponseEntity<>(model, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Updates a Movie according to Id")
    @PutMapping(value = "/movies/{id}")
    public ResponseEntity<?> putMoviesById(@PathVariable("id") Integer id,
                                           @Validated @RequestBody MovieModel movieModel) {
        try {
            MovieEntity entity = service.getMovieById(id);
            if (entity != null) {
                this.movieModel = MovieModel.builder()
                        .codMovie(movieModel.getCodMovie())
                        .releaseDateMovie(movieModel.getReleaseDateMovie())
                        .synopsisMovie(movieModel.getSynopsisMovie())
                        .titleMovie(movieModel.getTitleMovie())
                        .userRatingMovie(movieModel.getUserRatingMovie())
                        .build();
                service.postMovies(converter.toEntity(this.movieModel));
                return new ResponseEntity<>(movieModel, HttpStatus.OK);
            }
            return new ResponseEntity<>(MESSAGE_MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Returns a list of the ten most repeated letters in the titles of all films")
    @GetMapping("/movies/letter_metrics_top10")
    public ResponseEntity<List<LetterMetricsModel>> getMoviesLetterMetrics() {

        List<MovieEntity> entities = service.getMovies();
        List<MovieModel> models = converter.toModelList(entities);
        List<LetterMetricsModel>letterMetricsModels = new ArrayList<>();

        try {
            for (int i = 0, j = 0; i < models.size(); i++) {
                Integer cont = 0;
                String l = "";
                models.get(i).getTitleMovie().toLowerCase();
                for (i = 0; i < models.get(0).getTitleMovie().toLowerCase().length(); i++) {
                    for (j = 0; j < models.get(0).getTitleMovie().toLowerCase().length(); j++) {
                        if (models.get(0).getTitleMovie().toLowerCase().charAt(i) ==
                                models.get(0).getTitleMovie().toLowerCase().charAt(j)) {
                            cont++;
                        }
                    }
                    Character c = models.get(0).getTitleMovie().toLowerCase().charAt(i);
                    l = validatorLetters.validatorConsonants(models, letterMetricsModels, i, cont, l, c);
                    cont = 0;
                }
            }
            return new ResponseEntity<>(letterMetricsModels, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}