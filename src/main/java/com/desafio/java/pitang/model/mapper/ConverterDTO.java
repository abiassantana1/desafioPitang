package com.desafio.java.pitang.model.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public final class ConverterDTO <T, T2>{

    private final ModelMApperConfig modelMApperConfig;

    public T convertObject(T object, Class<T> type) {
        return this.modelMApperConfig.modelMapper().map(object, type);
    }

    public List<T2> converterListObjects(List<T> objects, Class<T2> type) {
        return objects.stream().map(response -> new ModelMapper().map(response, type))
                .collect(Collectors.toList());
    }
}
