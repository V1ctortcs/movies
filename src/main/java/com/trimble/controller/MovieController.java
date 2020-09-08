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

    private MovieModel movieModel;
    private LetterMetricsModel letterMetricsModel;


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
                this.movieModel = MovieModel.builder()
                        .codMovie(movieModel.getCodMovie())
                        .releaseDateMovie(movieModel.getReleaseDateMovie())
                        .synopsisMovie(movieModel.getSynopsisMovie())
                        .titleMovie(movieModel.getTitleMovie())
                        .userRatingMovie(movieModel.getUserRatingMovie())
                        .build();
                repository.save(converter.toEntity(this.movieModel));
                return new ResponseEntity<>(movieModel, HttpStatus.OK);
            }
            return new ResponseEntity<>(MESSAGE_MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Returns a list of the ten most repeated letters in the titles of all films")
    @GetMapping("/movies/letter_metrics_top10")
    public ResponseEntity<?> getMoviesLetterMetrics() {

        List<MovieEntity> entities = repository.findAll();
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
                    l = ValidatorConsonants(models, letterMetricsModels, i, cont, l, c);
                    cont = 0;
                }
            }
            return new ResponseEntity<>(letterMetricsModels, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String ValidatorConsonants(List<MovieModel> models, List<LetterMetricsModel> letterMetricsModels, int i,
                                       Integer cont, String l, Character c) {
        if (c.equals('b') || c.equals('c')  || c.equals('d') || c.equals('f') || c.equals('g') || c.equals('j')
                || c.equals('k') || c.equals('l') || c.equals('m')|| c.equals('n') || c.equals('p') || c.equals('q')
                || c.equals('r') || c.equals('s') || c.equals('t') || c.equals('v') || c.equals('w') || c.equals('x')
                || c.equals('y') || c.equals('z')
                && !l.contains("" + c)) {
            l = l + c;
            letterMetricsModel = LetterMetricsModel.builder()
                    .letter(models.get(0).getTitleMovie().toLowerCase().charAt(i))
                    .amount(cont)
                    .build();
            letterMetricsModels.add(letterMetricsModel);
        }
        return l;
    }
}
