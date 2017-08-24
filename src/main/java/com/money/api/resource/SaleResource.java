package com.money.api.resource;


import com.money.api.event.CreatedResourceEvent;
import com.money.api.exception.ApiExceptionHandler;
import com.money.api.exception.PersonNullOrInactiveException;
import com.money.api.model.Sale;
import com.money.api.repository.SaleRepository;
import com.money.api.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleResource {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private SaleService saleService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public List<Sale> listAll(){
        return saleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> findOne(@PathVariable Long id){
        Sale sale = saleRepository.findOne(id);
        return sale != null ? ResponseEntity.ok(sale) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Sale> create(@Valid @RequestBody Sale sale, HttpServletResponse response){
        Sale savedSale = saleService.save(sale);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedSale.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
    }

    @ExceptionHandler({PersonNullOrInactiveException.class})
    public ResponseEntity<Object> handlePersonNullOrInactiveException(PersonNullOrInactiveException ex){
        String userMessage = messageSource.getMessage("person.null-or-inactive", null, LocaleContextHolder.getLocale());
        String devMessage = ex.toString();
        List<ApiExceptionHandler.Error> errors = Arrays.asList(new ApiExceptionHandler.Error(userMessage,devMessage));
        return ResponseEntity.badRequest().body(errors);
    }

}
