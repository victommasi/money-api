package com.money.api.repository;

import com.money.api.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vic on 16/08/17.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
