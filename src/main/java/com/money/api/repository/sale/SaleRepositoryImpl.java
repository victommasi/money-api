package com.money.api.repository.sale;

import com.money.api.model.Sale;
import com.money.api.repository.filter.SaleFilter;
import com.money.api.repository.projection.SaleVO;
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

public class SaleRepositoryImpl implements SaleRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Sale> filter(SaleFilter saleFilter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Sale> criteriaQuery = criteriaBuilder.createQuery(Sale.class);
        Root<Sale> root = criteriaQuery.from(Sale.class);

        Predicate[] predicates = createFilterRestrictions(saleFilter, criteriaBuilder, root);
        criteriaQuery.where(predicates);

        TypedQuery<Sale> query = manager.createQuery(criteriaQuery);
        createPagingRestrictions(query, pageable);

        return new PageImpl(query.getResultList(), pageable, total(saleFilter));
    }

    @Override
    public Page<SaleVO> digest(SaleFilter saleFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<SaleVO> criteria = builder.createQuery(SaleVO.class);
        Root<Sale> root = criteria.from(Sale.class);

        criteria.select(builder.construct(SaleVO.class
                , root.get("id"), root.get("description")
                , root.get("dueDate"), root.get("payDate")
                , root.get("price"), root.get("type")
                , root.get("category").get("name")
                , root.get("person").get("name")));

        Predicate[] predicates = createFilterRestrictions(saleFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<SaleVO> query = manager.createQuery(criteria);
        createPagingRestrictions(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(saleFilter));
    }

    private Long total(SaleFilter saleFilter) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Sale> root = criteriaQuery.from(Sale.class);

        Predicate[] predicates = createFilterRestrictions(saleFilter, criteriaBuilder, root);
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

    private Predicate[] createFilterRestrictions(SaleFilter saleFilter, CriteriaBuilder criteriaBuilder, Root<Sale> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(saleFilter.getDescription())){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + saleFilter.getDescription().toLowerCase() + "%"));
        }

        if (saleFilter.getDueDateFrom() != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo((root.get("dueDate")), saleFilter.getDueDateFrom()));
        }

        if (saleFilter.getDueDateTo() != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo((root.get("dueDate")), saleFilter.getDueDateTo()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
