package com.trimble.converter;

import com.trimble.entity.MovieEntity;
import com.trimble.model.MovieModel;
import com.trimble.utils.MapperUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieEntityConverterTest {

    @InjectMocks
    private MovieEntityConverter converter;

    @Mock
    private MapperUtil mapper;

    private MovieModel model;
    private MovieEntity entity;

    @Before
    public void setup(){
        when(mapper.use()).thenReturn(new ModelMapper());

        model = MovieModel.builder()
                .codMovie(01)
                .releaseDateMovie(LocalDate.now())
                .synopsisMovie("Synopsis Test")
                .titleMovie("Title Test")
                .userRatingMovie(9)
                .build();

        entity = MovieEntity.builder()
                .codMovie(01)
                .releaseDateMovie(LocalDate.now())
                .synopsisMovie("Synopsis Test")
                .titleMovie("Title Test")
                .userRatingMovie(9)
                .build();
    }

    @Test
    public void toModel() {
        MovieModel model = converter.toModel(entity);
        assertNotNull(model);
    }

    @Test
    public void toModelNull() {
        MovieModel model = converter.toModel(null);
        assertNull(model);
    }

    @Test
    public void toEntity() {
        MovieEntity entity = converter.toEntity(model);
        assertNotNull(entity);
    }

    @Test
    public void toModelList() {
        List<MovieModel> models = converter.toModelList(Arrays.asList(entity));
        assertNotNull(models);
        assertFalse(models.isEmpty());
    }

    @Test
    public void toModelListEmpty() {
        List<MovieModel> models = converter.toModelList(null);
        assertNotNull(models);
        assertTrue(models.isEmpty());
    }

    @Test
    public void toEntityNull() {
        MovieEntity entity = converter.toEntity(null);
        assertNull(entity);
    }

    @Test
    public void toEntityList() {
        List<MovieEntity> entities = converter.toEntityList(Arrays.asList(model));
        assertNotNull(entities);
        assertFalse(entities.isEmpty());
    }

    @Test
    public void toEntityListEmpty() {
        List<MovieEntity> entities = converter.toEntityList(null);
        assertNotNull(entities);
        assertTrue(entities.isEmpty());
    }
}