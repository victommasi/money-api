package com.money.api.repository;

import com.money.api.model.Sale;
import com.money.api.repository.sale.SaleRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>, SaleRepositoryQuery{
}
