package com.sergiosantiago.conectados.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

public class CustomMapper {

    private final ModelMapper modelMapper;

    public CustomMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        modelMapper.addConverter(dtoListToEntityListConverter());
        modelMapper.addConverter(dtoSetToEntitySetConverter());
    }

    public <S, D> D map(S source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    private AbstractConverter<List<Object>, List<Object>> dtoListToEntityListConverter() {
        return new AbstractConverter<List<Object>, List<Object>>() {
            @Override
            protected List<Object> convert(List<Object> source) {
                return (source != null) ? source : new ArrayList<>();
            }
        };
    }

    private AbstractConverter<Set<Object>, Set<Object>> dtoSetToEntitySetConverter() {
        return new AbstractConverter<Set<Object>, Set<Object>>() {
            @Override
            protected Set<Object> convert(Set<Object> source) {
                return (source != null) ? source : new HashSet<>();
            }
        };
    }
}
