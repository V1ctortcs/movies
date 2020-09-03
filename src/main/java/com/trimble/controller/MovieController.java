package com.trimble.controller;

import com.trimble.converter.MovieEntityConverter;
import com.trimble.entity.MovieEntity;
import com.trimble.model.LetterMetricsModel;
import com.trimble.model.MovieModel;
import com.trimble.repository.MovieRepository;
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

    private final MovieRepository repository;
    private final MovieEntityConverter converter;

    private MovieModel model;

    @ApiOperation("Returns all registered movies")
    @GetMapping(value = "/movies")
    public ResponseEntity<List<MovieModel>> getMovies() {
        try {
            List<MovieEntity> entities = repository.findAll();
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
            MovieEntity entity = repository.findByCodMovie(id);
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
            repository.save(converter.toEntity(model));
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
            MovieEntity entity = repository.findByCodMovie(id);
            if (entity != null) {
                model = MovieModel.builder()
                        .codMovie(movieModel.getCodMovie())
                        .releaseDateMovie(movieModel.getReleaseDateMovie())
                        .synopsisMovie(movieModel.getSynopsisMovie())
                        .titleMovie(movieModel.getTitleMovie())
                        .userRatingMovie(movieModel.getUserRatingMovie())
                        .build();
                repository.save(converter.toEntity(model));
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

        List<MovieEntity> entities = repository.findAll();
        List<MovieModel> models = converter.toModelList(entities);

        for (int i = 0, j = 0; i < models.size(); i++) {
            Integer cont = 0;
            String v = "";
            LetterMetricsModel letterMetricsModel = new LetterMetricsModel();
            List<LetterMetricsModel>letterMetricsModels = new ArrayList<>();
            models.get(i).getTitleMovie().toLowerCase();
            for (i = 0; i < models.get(0).getTitleMovie().toLowerCase().length(); i++) {
                for (j = 0; j < models.get(0).getTitleMovie().toLowerCase().length(); j++) {
                    if (models.get(0).getTitleMovie().toLowerCase().charAt(i) ==
                            models.get(0).getTitleMovie().toLowerCase().charAt(j)) {
                        cont++;
                    }
                }
                char c = models.get(0).getTitleMovie().toLowerCase().charAt(i);
                if (c >= 'a' && c <= 'z' && !v.contains("" + c)) {
                    v = v + c;
                    letterMetricsModel.setLetter(models.get(0).getTitleMovie().toLowerCase().charAt(i));
                    letterMetricsModel.setAmount(cont);
                    letterMetricsModels.add(letterMetricsModel);
                }
                cont = 0;
            }
            return new ResponseEntity<>(letterMetricsModels, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
