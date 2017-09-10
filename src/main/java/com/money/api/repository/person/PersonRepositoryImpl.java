package com.money.api.repository.person;

import com.money.api.model.Person;
import com.money.api.repository.filter.PersonFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Person> filter(PersonFilter personFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = builder.createQuery(Person.class);
        Root<Person> root = criteria.from(Person.class);

        Predicate[] predicates = createFilterRestrictions(personFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Person> query = manager.createQuery(criteria);
        createPagingRestrictions(query, pageable);

        return new PageImpl(query.getResultList(), pageable, total(personFilter));
    }

    private Predicate[] createFilterRestrictions(PersonFilter personFilter, CriteriaBuilder builder, Root<Person> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(personFilter.getName())){
            predicates.add(builder.like(builder.lower(root.get("name")), "%" + personFilter.getName().toLowerCase() + "%"));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private Long total(PersonFilter personFilter) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Person> root = criteriaQuery.from(Person.class);

        Predicate[] predicates = createFilterRestrictions(personFilter, criteriaBuilder, root);
        criteriaQuery.where(predicates);

        criteriaQuery.select(criteriaBuilder.count(root));
        return manager.createQuery(criteriaQuery).getSingleResult();
    }

    private void createPagingRestrictions(TypedQuery<?> query, Pageable pageable) {
        int currentPage = pageable.getPageNumber();
        int totalPerPage = pageable.getPageSize();
        int first = currentPage * totalPerPage;

        query.setFirstResult(first);
        query.setMaxResults(totalPerPage);
    }

    @Override
    public Page<Person> digest(PersonFilter personFilter, Pageable pageable) {
        return null;
    }
}
