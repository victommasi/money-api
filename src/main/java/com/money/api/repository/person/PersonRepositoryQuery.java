package com.money.api.repository.person;

import com.money.api.model.Person;
import com.money.api.repository.filter.PersonFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonRepositoryQuery {

    public Page<Person> filter(PersonFilter personFilter, Pageable pageable);
    public Page<Person> digest(PersonFilter personFilter, Pageable pageable);
}
