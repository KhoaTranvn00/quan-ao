package com.example.quanlycuahang.mapper;

import com.example.quanlycuahang.dto.ProductDto;
import com.example.quanlycuahang.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

//    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductDto productRequest);
}
