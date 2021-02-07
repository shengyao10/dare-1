package com.dare.modules.apqp.mapper;

import com.dare.modules.apqp.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "customerMapper")
public interface CustomerMapper {
    List<Customer> findAll();

    void save(Customer customer);

}
