package com.money.api.service;

import com.money.api.model.Category;
import com.money.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vic on 14/08/17.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Category save(Category category) {
        return categoryRepository.save(category);
    }

}
