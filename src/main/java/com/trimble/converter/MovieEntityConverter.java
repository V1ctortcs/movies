package com.trimble.converter;

import com.trimble.entity.MovieEntity;
import com.trimble.model.MovieModel;
import com.trimble.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieEntityConverter extends ModelEntityConverter<MovieModel, MovieEntity> {

    private final MapperUtil mapperUtil;

    @Override
    protected MovieModel toModelImp(MovieEntity entity){
        return mapperUtil.use().map(entity, MovieModel.class);
    }

    @Override
    protected MovieEntity toEntityImp(MovieModel model){
        return mapperUtil.use().map(model, MovieEntity.class);
    }
}
