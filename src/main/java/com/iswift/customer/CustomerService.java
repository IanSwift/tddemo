package com.iswift.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCustomer(Customer customer) throws ConflictExcpetion {
        List<Customer> customers = findCustomerWithFirstNameAndLastName(customer);

        if (customers != null && !customers.isEmpty()) {
            throw new ConflictExcpetion();
        }

        saveCustomerToDatabase(customer);
    }

    public List<Customer> findCustomerWithFirstNameAndLastName(Customer customer) {
        return customerRepository.findByFirstNameAndLastName(customer.getFirstName(), customer.getLastName());
    }

    public void saveCustomerToDatabase(Customer customer) {
        customerRepository.save(customer);
    }
}
