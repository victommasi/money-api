package com.money.api.repository;

import com.money.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import sun.rmi.runtime.Log;

/**
 * Created by vic on 14/08/17.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
