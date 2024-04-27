package com.sergiosantiago.conectados.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.sergiosantiago.conectados.config.CustomMapper;
import com.sergiosantiago.conectados.models.ProductCategory;
import com.sergiosantiago.conectados.repository.ProductCategoryRepository;
import com.sergiosantiago.conectados.services.base.BaseServiceImpl;

@Service
public class ProductCategoryService
        extends BaseServiceImpl<ProductCategory, Long, ProductCategoryRepository> {

    public ProductCategoryService(ProductCategoryRepository repository, CustomMapper modelMapper) {
        super(repository, modelMapper);
    }

    public List<ProductCategory> findProductCategoryByName(String name) {
        return repository.findLikeByName(name);
    }

    public Set<ProductCategory> findAllByIds(Collection<Long> ids) {
        Set<ProductCategory> categories = new HashSet<>();
        if (ids != null && !ids.isEmpty()) {
            categories.addAll(repository.findAllById(ids));
        }
        return categories;
    }

}
