package com.money.api.repository.sale;

import com.money.api.model.Sale;
import com.money.api.repository.filter.SaleFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaleRepositoryQuery {

    public Page<Sale> filter(SaleFilter saleFilter, Pageable pageable);
}
