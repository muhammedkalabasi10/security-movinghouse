package com.movinghouse.security.service;

import com.movinghouse.security.model.Customer;
import com.movinghouse.security.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found with the given ID."));
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @PreAuthorize("#id == authentication.principal.id")
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
