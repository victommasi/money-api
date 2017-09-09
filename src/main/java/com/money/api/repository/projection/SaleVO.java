package com.money.api.repository.projection;


import com.money.api.model.SaleType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SaleVO {

    private Long id;
    private String description;
    private LocalDate dueDate;
    private LocalDate payDate;
    private BigDecimal price;
    private SaleType type;
    private String category;
    private String person;

    public SaleVO(Long id, String description, LocalDate dueDate, LocalDate payDate, BigDecimal price, SaleType type, String category, String person) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.payDate = payDate;
        this.price = price;
        this.type = type;
        this.category = category;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SaleType getType() {
        return type;
    }

    public void setType(SaleType type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
