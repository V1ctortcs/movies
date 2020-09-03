package com.trimble.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    private ModelMapper modelMapper;

    public MapperUtil(){
        this.modelMapper = new ModelMapper();
    }

    public ModelMapper use(){
        return this.modelMapper;
    }

}
