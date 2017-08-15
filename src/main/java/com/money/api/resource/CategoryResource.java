package com.money.api.resource;

import com.money.api.model.Category;
import com.money.api.repository.CategoryRepository;
import com.money.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Created by vic on 14/08/17.
 */
@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> listAll(){
        return categoryRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category,
                                    HttpServletResponse response){
        Category savedCategory = categoryService.save(category);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(savedCategory.getId())
                .toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(savedCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Category category = categoryRepository.findOne(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

}
