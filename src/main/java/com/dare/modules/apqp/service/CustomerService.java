package com.dare.modules.apqp.service;

import com.dare.modules.apqp.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    void save(Customer customer);
}
