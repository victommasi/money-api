package com.money.api.resource;


import com.money.api.event.CreatedResourceEvent;
import com.money.api.exception.ApiExceptionHandler;
import com.money.api.exception.PersonNullOrInactiveException;
import com.money.api.model.Sale;
import com.money.api.repository.SaleRepository;
import com.money.api.repository.filter.SaleFilter;
import com.money.api.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ROLE_FIND_SALE') and #oauth2.hasScope('read')")
    public Page<Sale> listAll(SaleFilter saleFilter, Pageable pageable){
        return saleRepository.filter(saleFilter, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_FIND_SALE') and #oauth2.hasScope('read')")
    public ResponseEntity<Sale> findOne(@PathVariable Long id){
        Sale sale = saleRepository.findOne(id);
        return sale != null ? ResponseEntity.ok(sale) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SAVE_SALE') and #oauth2.hasScope('write')")
    public ResponseEntity<Sale> create(@Valid @RequestBody Sale sale, HttpServletResponse response){
        Sale savedSale = saleService.save(sale);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedSale.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SAVE_SALE') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id){
        saleService.remove(id);
    }

    @ExceptionHandler({PersonNullOrInactiveException.class})
    public ResponseEntity<Object> handlePersonNullOrInactiveException(PersonNullOrInactiveException ex){
        String userMessage = messageSource.getMessage("person.null-or-inactive", null, LocaleContextHolder.getLocale());
        String devMessage = ex.toString();
        List<ApiExceptionHandler.Error> errors = Arrays.asList(new ApiExceptionHandler.Error(userMessage,devMessage));
        return ResponseEntity.badRequest().body(errors);
    }

}
