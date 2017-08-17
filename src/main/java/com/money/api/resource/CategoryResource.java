package com.money.api.resource;

import com.money.api.event.CreatedResourceEvent;
import com.money.api.model.Category;
import com.money.api.repository.CategoryRepository;
import com.money.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Category> listAll(){
        return categoryRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category,
                                    HttpServletResponse response){
        Category savedCategory = categoryService.save(category);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedCategory.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Category category = categoryRepository.findOne(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

}
