package com.traningsprint1.service.impl;

import com.traningsprint1.models.Category;
import com.traningsprint1.repository.ICategoryRepository;
import com.traningsprint1.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    ICategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategory() {
        return this.categoryRepository.findAll();
    }
}
