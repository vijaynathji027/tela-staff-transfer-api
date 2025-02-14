package com.planetsystems.api.school.staffTransfer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
    final EntityManager entityManager;

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

//    @Bean
//    public JPAQuery<?> jpaQuery(){
//      return  new JPAQuery<>(entityManager);
//    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
    return new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
    }



}
