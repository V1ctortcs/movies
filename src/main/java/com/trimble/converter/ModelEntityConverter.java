package com.trimble.converter;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelEntityConverter<M, E> {

    public M toModel(E entity) {
        if (entity == null) {
            return null;
        }
        return toModelImp(entity);
    }

    public E toEntity(M model) {
        if (model == null) {
            return null;
        }
        return toEntityImp(model);
    }

    protected abstract M toModelImp(E entity);

    protected abstract E toEntityImp(M model);

    public List<M> toModelList(List<E> entities) {
        List<M> models = new ArrayList<>();

        if (entities != null) {
            for (E e : entities) {
                models.add(toModel(e));
            }
        }
        return models;
    }

    public List<E> toEntityList(List<M> models) {
        List<E> entities = new ArrayList<>();

        if (models != null) {
            for (M m : models) {
                entities.add(toEntity(m));
            }
        }
        return entities;
    }

}
