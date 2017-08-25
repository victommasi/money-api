package com.money.api.service;

import com.money.api.exception.PersonNullOrInactiveException;
import com.money.api.model.Person;
import com.money.api.model.Sale;
import com.money.api.repository.PersonRepository;
import com.money.api.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private PersonRepository personRepository;

    public Sale save(Sale sale) {
        Person person = personRepository.findOne(sale.getPerson().getId());
        if (person == null || person.isInactive()){
            throw new PersonNullOrInactiveException();
        }
        return saleRepository.save(sale);
    }

    public void remove(Long saleId) {
        Sale savedSale = saleRepository.findOne(saleId);
        if (savedSale == null) {
            throw new EmptyResultDataAccessException(1);
        }
        saleRepository.delete(savedSale);
    }
}
