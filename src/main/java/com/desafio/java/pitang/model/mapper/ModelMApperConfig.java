package com.desafio.java.pitang.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMApperConfig<D, E> {

    @Bean
    public ModelMapper modelMapper() { return new ModelMapper();};
}
