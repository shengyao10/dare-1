package com.dare.modules.apqp.service.impl;

import com.dare.modules.apqp.entity.Customer;
import com.dare.modules.apqp.mapper.CustomerMapper;
import com.dare.modules.apqp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public List<Customer> findAll() {
        List<Customer> customerList=customerMapper.findAll();
        return customerList;
    }

    @Override
    public void save(Customer customer) {
        customerMapper.save(customer);
    }
}
