package com.kevdeto.tiendalibre.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.kevdeto.tiendalibre.domain.dto.response.ReviewProductResponseDTO;
import com.kevdeto.tiendalibre.domain.entity.ReviewEntity;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "username", source = "user.username") //asume relacion con user
    ReviewProductResponseDTO toReviewProductDTO(ReviewEntity entity);
}
